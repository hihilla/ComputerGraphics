// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.common.nio.PointerBuffer;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLRendererQuirks;
import jogamp.opengl.GLContextImpl;
import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import java.nio.Buffer;
import java.nio.ShortBuffer;
import jogamp.nativewindow.windows.GDI;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.nativewindow.GenericUpstreamSurfacelessHook;
import jogamp.nativewindow.windows.GDISurface;
import jogamp.nativewindow.windows.GDIDummyUpstreamSurfaceHook;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import jogamp.opengl.GLDynamicLookupHelper;
import jogamp.nativewindow.windows.RegisteredClassFactory;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.opengl.GLProfile;
import java.security.AccessController;
import com.jogamp.common.os.DynamicLookupHelper;
import jogamp.opengl.DesktopGLDynamicLibraryBundleInfo;
import java.security.PrivilegedAction;
import jogamp.opengl.SharedResourceRunner;
import com.jogamp.nativewindow.windows.WindowsGraphicsDevice;
import jogamp.opengl.DesktopGLDynamicLookupHelper;
import jogamp.opengl.GLDrawableFactoryImpl;

public class WindowsWGLDrawableFactory extends GLDrawableFactoryImpl
{
    private static final boolean DEBUG_SHAREDCTX;
    private static final int CPU_AFFINITY_MODE;
    private static DesktopGLDynamicLookupHelper windowsWGLDynamicLookupHelper;
    private final CPUAffinity cpuAffinity;
    private WindowsGraphicsDevice defaultDevice;
    private SharedResourceImplementation sharedResourceImplementation;
    private SharedResourceRunner sharedResourceRunner;
    static final String WGL_ARB_pbuffer = "WGL_ARB_pbuffer";
    static final String WGL_ARB_pixel_format = "WGL_ARB_pixel_format";
    static final String WGL_ARB_multisample = "WGL_ARB_multisample";
    static final String WGL_NV_float_buffer = "WGL_NV_float_buffer";
    static final String WGL_ARB_make_current_read = "WGL_ARB_make_current_read";
    static final String wglMakeContextCurrent = "wglMakeContextCurrent";
    private static final int GAMMA_RAMP_LENGTH = 256;
    
    public WindowsWGLDrawableFactory() {
        switch (WindowsWGLDrawableFactory.CPU_AFFINITY_MODE) {
            case 0: {
                this.cpuAffinity = new NopCPUAffinity();
                break;
            }
            default: {
                this.cpuAffinity = new WindowsProcessAffinity();
                break;
            }
        }
        synchronized (WindowsWGLDrawableFactory.class) {
            if (null == WindowsWGLDrawableFactory.windowsWGLDynamicLookupHelper) {
                WindowsWGLDrawableFactory.windowsWGLDynamicLookupHelper = AccessController.doPrivileged((PrivilegedAction<DesktopGLDynamicLookupHelper>)new PrivilegedAction<DesktopGLDynamicLookupHelper>() {
                    @Override
                    public DesktopGLDynamicLookupHelper run() {
                        DesktopGLDynamicLookupHelper desktopGLDynamicLookupHelper;
                        try {
                            desktopGLDynamicLookupHelper = new DesktopGLDynamicLookupHelper(new WindowsWGLDynamicLibraryBundleInfo());
                            if (null != desktopGLDynamicLookupHelper && desktopGLDynamicLookupHelper.isLibComplete()) {
                                WGL.getWGLProcAddressTable().reset(desktopGLDynamicLookupHelper);
                            }
                        }
                        catch (Exception ex) {
                            desktopGLDynamicLookupHelper = null;
                            if (WindowsWGLDrawableFactory.DEBUG) {
                                ex.printStackTrace();
                            }
                        }
                        return desktopGLDynamicLookupHelper;
                    }
                });
            }
        }
        this.defaultDevice = new WindowsGraphicsDevice(0);
        if (null != WindowsWGLDrawableFactory.windowsWGLDynamicLookupHelper) {
            WindowsWGLGraphicsConfigurationFactory.registerFactory();
            if (GLProfile.isAWTAvailable()) {
                try {
                    ReflectionUtil.callStaticMethod("jogamp.opengl.windows.wgl.awt.WindowsAWTWGLGraphicsConfigurationFactory", "registerFactory", null, null, this.getClass().getClassLoader());
                }
                catch (Exception ex) {}
            }
            this.sharedResourceImplementation = new SharedResourceImplementation();
            (this.sharedResourceRunner = new SharedResourceRunner(this.sharedResourceImplementation)).start();
        }
    }
    
    @Override
    protected final boolean isComplete() {
        return null != WindowsWGLDrawableFactory.windowsWGLDynamicLookupHelper;
    }
    
    @Override
    protected final void shutdownImpl() {
        if (WindowsWGLDrawableFactory.DEBUG) {
            System.err.println("WindowsWGLDrawableFactory.shutdown");
        }
        if (null != this.sharedResourceRunner) {
            this.sharedResourceRunner.stop();
            this.sharedResourceRunner = null;
        }
        if (null != this.sharedResourceImplementation) {
            this.sharedResourceImplementation.clear();
            this.sharedResourceImplementation = null;
        }
        this.defaultDevice = null;
        WindowsWGLDrawableFactory.windowsWGLDynamicLookupHelper = null;
        RegisteredClassFactory.shutdownSharedClasses();
    }
    
    @Override
    public final GLDynamicLookupHelper getGLDynamicLookupHelper(final int n, final int n2) {
        return WindowsWGLDrawableFactory.windowsWGLDynamicLookupHelper;
    }
    
    static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    @Override
    protected void enterThreadCriticalZone() {
        synchronized (this.cpuAffinity) {
            this.cpuAffinity.set(1);
        }
    }
    
    @Override
    protected void leaveThreadCriticalZone() {
        synchronized (this.cpuAffinity) {
            this.cpuAffinity.reset();
        }
    }
    
    @Override
    public final AbstractGraphicsDevice getDefaultDevice() {
        return this.defaultDevice;
    }
    
    @Override
    public final boolean getIsDeviceCompatible(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return null != WindowsWGLDrawableFactory.windowsWGLDynamicLookupHelper && abstractGraphicsDevice instanceof WindowsGraphicsDevice;
    }
    
    @Override
    protected final Thread getSharedResourceThread() {
        return this.sharedResourceRunner.start();
    }
    
    @Override
    protected final SharedResource getOrCreateSharedResourceImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return (SharedResource)this.sharedResourceRunner.getOrCreateShared(abstractGraphicsDevice);
    }
    
    protected final WindowsWGLDrawable getOrCreateSharedDrawable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final SharedResource orCreateSharedResourceImpl = this.getOrCreateSharedResourceImpl(abstractGraphicsDevice);
        if (null != orCreateSharedResourceImpl) {
            return (WindowsWGLDrawable)orCreateSharedResourceImpl.getDrawable();
        }
        return null;
    }
    
    @Override
    public final boolean hasOpenGLDesktopSupport() {
        return true;
    }
    
    @Override
    public final boolean hasOpenGLESSupport() {
        return false;
    }
    
    @Override
    public final boolean hasMajorMinorCreateContextARB() {
        return true;
    }
    
    @Override
    protected List<GLCapabilitiesImmutable> getAvailableCapabilitiesImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return WindowsWGLGraphicsConfigurationFactory.getAvailableCapabilities(this, abstractGraphicsDevice);
    }
    
    @Override
    protected final GLDrawableImpl createOnscreenDrawableImpl(final NativeSurface nativeSurface) {
        if (nativeSurface == null) {
            throw new IllegalArgumentException("Null target");
        }
        return new WindowsOnscreenWGLDrawable(this, nativeSurface);
    }
    
    @Override
    protected final GLDrawableImpl createOffscreenDrawableImpl(final NativeSurface nativeSurface) {
        if (nativeSurface == null) {
            throw new IllegalArgumentException("Null target");
        }
        final AbstractGraphicsConfiguration graphicsConfiguration = nativeSurface.getGraphicsConfiguration();
        if (!((GLCapabilitiesImmutable)graphicsConfiguration.getChosenCapabilities()).isPBuffer()) {
            return WindowsBitmapWGLDrawable.create(this, nativeSurface);
        }
        final SharedResource orCreateSharedResourceImpl = this.getOrCreateSharedResourceImpl(graphicsConfiguration.getScreen().getDevice());
        WindowsPbufferWGLDrawable windowsPbufferWGLDrawable;
        if (null != orCreateSharedResourceImpl) {
            final GLContext current = GLContext.getCurrent();
            if (current != null) {
                current.release();
            }
            orCreateSharedResourceImpl.context.makeCurrent();
            try {
                windowsPbufferWGLDrawable = new WindowsPbufferWGLDrawable(this, nativeSurface);
            }
            finally {
                orCreateSharedResourceImpl.context.release();
                if (current != null) {
                    current.makeCurrent();
                }
            }
        }
        else {
            windowsPbufferWGLDrawable = new WindowsPbufferWGLDrawable(this, nativeSurface);
        }
        return windowsPbufferWGLDrawable;
    }
    
    public final int isReadDrawableAvailable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final SharedResource orCreateSharedResourceImpl = this.getOrCreateSharedResourceImpl((null != abstractGraphicsDevice) ? abstractGraphicsDevice : this.defaultDevice);
        if (null != orCreateSharedResourceImpl) {
            return orCreateSharedResourceImpl.hasReadDrawable() ? 1 : 0;
        }
        return -1;
    }
    
    @Override
    public final boolean canCreateGLPbuffer(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile) {
        final SharedResource orCreateSharedResourceImpl = this.getOrCreateSharedResourceImpl((null != abstractGraphicsDevice) ? abstractGraphicsDevice : this.defaultDevice);
        return null != orCreateSharedResourceImpl && orCreateSharedResourceImpl.hasARBPBuffer();
    }
    
    @Override
    protected final ProxySurface createMutableSurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        WindowsGraphicsDevice windowsGraphicsDevice;
        if (b || !(abstractGraphicsDevice instanceof WindowsGraphicsDevice)) {
            windowsGraphicsDevice = new WindowsGraphicsDevice(abstractGraphicsDevice.getConnection(), abstractGraphicsDevice.getUnitID());
        }
        else {
            windowsGraphicsDevice = (WindowsGraphicsDevice)abstractGraphicsDevice;
        }
        final DefaultGraphicsScreen defaultGraphicsScreen = new DefaultGraphicsScreen(windowsGraphicsDevice, 0);
        final WindowsWGLGraphicsConfiguration chooseGraphicsConfigurationStatic = WindowsWGLGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable2, glCapabilitiesChooser, defaultGraphicsScreen);
        if (null == chooseGraphicsConfigurationStatic) {
            throw new GLException("Choosing GraphicsConfiguration failed w/ " + glCapabilitiesImmutable + " on " + defaultGraphicsScreen);
        }
        return new WrappedSurface(chooseGraphicsConfigurationStatic, 0L, upstreamSurfaceHook, b);
    }
    
    @Override
    public final ProxySurface createDummySurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, GLCapabilitiesImmutable fixOnscreenGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        WindowsGraphicsDevice windowsGraphicsDevice;
        if (b || !(abstractGraphicsDevice instanceof WindowsGraphicsDevice)) {
            windowsGraphicsDevice = new WindowsGraphicsDevice(abstractGraphicsDevice.getConnection(), abstractGraphicsDevice.getUnitID());
        }
        else {
            windowsGraphicsDevice = (WindowsGraphicsDevice)abstractGraphicsDevice;
        }
        final DefaultGraphicsScreen defaultGraphicsScreen = new DefaultGraphicsScreen(windowsGraphicsDevice, 0);
        fixOnscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOnscreenGLCapabilities(fixOnscreenGLCapabilities);
        final WindowsWGLGraphicsConfiguration chooseGraphicsConfigurationStatic = WindowsWGLGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(fixOnscreenGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, defaultGraphicsScreen);
        if (null == chooseGraphicsConfigurationStatic) {
            throw new GLException("Choosing GraphicsConfiguration failed w/ " + fixOnscreenGLCapabilities + " on " + defaultGraphicsScreen);
        }
        return new GDISurface(chooseGraphicsConfigurationStatic, 0L, new GDIDummyUpstreamSurfaceHook(n, n2), b);
    }
    
    @Override
    public final ProxySurface createSurfacelessImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, GLCapabilitiesImmutable fixOnscreenGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        fixOnscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOnscreenGLCapabilities(fixOnscreenGLCapabilities);
        return this.createMutableSurfaceImpl(abstractGraphicsDevice, b, fixOnscreenGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, new GenericUpstreamSurfacelessHook(n, n2));
    }
    
    @Override
    protected final ProxySurface createProxySurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final long n2, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        return new GDISurface(WindowsWGLGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable, glCapabilitiesChooser, new DefaultGraphicsScreen(new WindowsGraphicsDevice(abstractGraphicsDevice.getConnection(), abstractGraphicsDevice.getUnitID()), n)), n2, upstreamSurfaceHook, true);
    }
    
    @Override
    protected final GLContext createExternalGLContextImpl() {
        return WindowsExternalWGLContext.create(this, null);
    }
    
    @Override
    public final boolean canCreateExternalGLDrawable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return true;
    }
    
    @Override
    protected final GLDrawable createExternalGLDrawableImpl() {
        return WindowsExternalWGLDrawable.create(this, null);
    }
    
    static String wglGetLastError() {
        final long n = GDI.GetLastError();
        String string = null;
        switch ((int)n) {
            case 0: {
                string = "ERROR_SUCCESS";
                break;
            }
            case 2000: {
                string = "ERROR_INVALID_PIXEL_FORMAT";
                break;
            }
            case 1450: {
                string = "ERROR_NO_SYSTEM_RESOURCES";
                break;
            }
            case 13: {
                string = "ERROR_INVALID_DATA";
                break;
            }
            case 127: {
                string = "ERROR_PROC_NOT_FOUND";
                break;
            }
            case 1400: {
                string = "ERROR_INVALID_WINDOW_HANDLE";
                break;
            }
            default: {
                string = "(Unknown error code " + n + ")";
                break;
            }
        }
        return string;
    }
    
    @Override
    protected final int getGammaRampLength(final NativeSurface nativeSurface) {
        return 256;
    }
    
    @Override
    protected final boolean setGammaRamp(final NativeSurface nativeSurface, final float[] array) {
        final short[] array2 = new short[768];
        for (int i = 0; i < 256; ++i) {
            final short n = (short)(array[i] * 65535.0f);
            array2[i] = n;
            array2[i + 512] = (array2[i + 256] = n);
        }
        final long surfaceHandle = nativeSurface.getSurfaceHandle();
        return 0L != surfaceHandle && GDI.SetDeviceGammaRamp(surfaceHandle, ShortBuffer.wrap(array2));
    }
    
    @Override
    protected final Buffer getGammaRamp(final NativeSurface nativeSurface) {
        final ShortBuffer wrap = ShortBuffer.wrap(new short[768]);
        final long surfaceHandle = nativeSurface.getSurfaceHandle();
        if (0L == surfaceHandle) {
            return null;
        }
        if (!GDI.GetDeviceGammaRamp(surfaceHandle, wrap)) {
            return null;
        }
        return wrap;
    }
    
    @Override
    protected final void resetGammaRamp(final NativeSurface nativeSurface, final Buffer buffer) {
        if (buffer == null) {
            return;
        }
        final long surfaceHandle = nativeSurface.getSurfaceHandle();
        if (0L == surfaceHandle) {
            return;
        }
        GDI.SetDeviceGammaRamp(surfaceHandle, buffer);
    }
    
    @Override
    protected final void resetGammaRamp(final DeviceScreenID deviceScreenID, final Buffer buffer) {
        if (buffer == null) {
            return;
        }
        final long getDC = GDI.GetDC(0L);
        GDI.SetDeviceGammaRamp(getDC, buffer);
        GDI.ReleaseDC(0L, getDC);
    }
    
    static {
        DEBUG_SHAREDCTX = (WindowsWGLDrawableFactory.DEBUG || GLContext.DEBUG);
        Debug.initSingleton();
        CPU_AFFINITY_MODE = PropertyAccess.getIntProperty("jogl.windows.cpu_affinity_mode", true, 1);
        WindowsWGLDrawableFactory.windowsWGLDynamicLookupHelper = null;
    }
    
    static class SharedResource implements SharedResourceRunner.Resource
    {
        private final boolean hasARBPixelFormat;
        private final boolean hasARBMultisample;
        private final boolean hasARBPBuffer;
        private final boolean hasARBReadDrawable;
        private WindowsGraphicsDevice device;
        private AbstractGraphicsScreen screen;
        private GLDrawableImpl drawable;
        private GLContextImpl context;
        
        SharedResource(final WindowsGraphicsDevice device, final AbstractGraphicsScreen screen, final GLDrawableImpl drawable, final GLContextImpl context, final boolean hasARBPixelFormat, final boolean hasARBMultisample, final boolean hasARBPBuffer, final boolean hasARBReadDrawable) {
            this.device = device;
            this.screen = screen;
            this.drawable = drawable;
            this.context = context;
            this.hasARBPixelFormat = hasARBPixelFormat;
            this.hasARBMultisample = hasARBMultisample;
            this.hasARBPBuffer = hasARBPBuffer;
            this.hasARBReadDrawable = hasARBReadDrawable;
        }
        
        @Override
        public final boolean isAvailable() {
            return null != this.context;
        }
        
        @Override
        public final AbstractGraphicsDevice getDevice() {
            return this.device;
        }
        
        @Override
        public final AbstractGraphicsScreen getScreen() {
            return this.screen;
        }
        
        @Override
        public final GLDrawableImpl getDrawable() {
            return this.drawable;
        }
        
        @Override
        public final GLContextImpl getContext() {
            return this.context;
        }
        
        @Override
        public GLRendererQuirks getRendererQuirks(final GLProfile glProfile) {
            return (null != this.context) ? this.context.getRendererQuirks() : null;
        }
        
        final boolean hasARBPixelFormat() {
            return this.hasARBPixelFormat;
        }
        
        final boolean hasARBMultisample() {
            return this.hasARBMultisample;
        }
        
        final boolean hasARBPBuffer() {
            return this.hasARBPBuffer;
        }
        
        final boolean hasReadDrawable() {
            return this.hasARBReadDrawable;
        }
    }
    
    class SharedResourceImplementation extends SharedResourceRunner.AImplementation
    {
        @Override
        public boolean isDeviceSupported(final AbstractGraphicsDevice abstractGraphicsDevice) {
            return true;
        }
        
        @Override
        public SharedResourceRunner.Resource createSharedResource(final AbstractGraphicsDevice abstractGraphicsDevice) {
            final WindowsGraphicsDevice windowsGraphicsDevice = new WindowsGraphicsDevice(abstractGraphicsDevice.getConnection(), abstractGraphicsDevice.getUnitID());
            GLContextImpl glContextImpl = null;
            boolean b = false;
            windowsGraphicsDevice.lock();
            try {
                final DefaultGraphicsScreen defaultGraphicsScreen = new DefaultGraphicsScreen(windowsGraphicsDevice, 0);
                final GLProfile value = GLProfile.get(windowsGraphicsDevice, GLProfile.GL_PROFILE_LIST_MIN_DESKTOP, false);
                if (null == value) {
                    throw new GLException("Couldn't get default GLProfile for device: " + windowsGraphicsDevice);
                }
                final GLCapabilities glCapabilities = new GLCapabilities(value);
                final GLDrawableImpl onscreenDrawableImpl = WindowsWGLDrawableFactory.this.createOnscreenDrawableImpl(WindowsWGLDrawableFactory.this.createDummySurfaceImpl(windowsGraphicsDevice, false, glCapabilities, glCapabilities, null, 64, 64));
                onscreenDrawableImpl.setRealized(true);
                glContextImpl = (GLContextImpl)onscreenDrawableImpl.createContext(null);
                if (null == glContextImpl) {
                    throw new GLException("Couldn't create shared context for drawable: " + onscreenDrawableImpl);
                }
                b = (0 != glContextImpl.makeCurrent());
                boolean access$100;
                boolean extensionAvailable;
                boolean extensionAvailable2;
                boolean extensionAvailable3;
                boolean b2;
                if (b) {
                    if (glContextImpl.getGLVersionNumber().compareTo(GLContext.Version3_0) >= 0) {
                        access$100 = WindowsWGLDrawableFactory.this.probeSurfacelessCtx(glContextImpl, true);
                    }
                    else {
                        WindowsWGLDrawableFactory.this.setNoSurfacelessCtxQuirk(glContextImpl);
                        access$100 = false;
                    }
                    extensionAvailable = glContextImpl.isExtensionAvailable("WGL_ARB_pixel_format");
                    extensionAvailable2 = glContextImpl.isExtensionAvailable("WGL_ARB_multisample");
                    extensionAvailable3 = glContextImpl.isExtensionAvailable("GL_ARB_pbuffer");
                    b2 = (glContextImpl.isExtensionAvailable("WGL_ARB_make_current_read") && glContextImpl.isFunctionAvailable("wglMakeContextCurrent"));
                }
                else {
                    access$100 = false;
                    extensionAvailable = false;
                    extensionAvailable2 = false;
                    extensionAvailable3 = false;
                    b2 = false;
                }
                if (WindowsWGLDrawableFactory.DEBUG_SHAREDCTX) {
                    System.err.println("SharedDevice:  " + windowsGraphicsDevice);
                    System.err.println("SharedScreen:  " + defaultGraphicsScreen);
                    System.err.println("SharedContext: " + glContextImpl + ", madeCurrent " + b);
                    System.err.println("  allowsSurfacelessCtx " + access$100);
                    System.err.println("pixelformat:   " + extensionAvailable);
                    System.err.println("multisample:   " + extensionAvailable2);
                    System.err.println("pbuffer:       " + extensionAvailable3);
                    System.err.println("readDrawable:  " + b2);
                }
                return new SharedResource(windowsGraphicsDevice, defaultGraphicsScreen, onscreenDrawableImpl, glContextImpl, extensionAvailable, extensionAvailable2, extensionAvailable3, b2);
            }
            catch (Throwable t) {
                throw new GLException("WindowsWGLDrawableFactory - Could not initialize shared resources for " + abstractGraphicsDevice, t);
            }
            finally {
                if (b) {
                    glContextImpl.release();
                }
                windowsGraphicsDevice.unlock();
            }
        }
        
        @Override
        public void releaseSharedResource(final SharedResourceRunner.Resource resource) {
            final SharedResource sharedResource = (SharedResource)resource;
            if (WindowsWGLDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("Shutdown Shared:");
                System.err.println("Device  : " + sharedResource.device);
                System.err.println("Screen  : " + sharedResource.screen);
                System.err.println("Drawable: " + sharedResource.drawable);
                System.err.println("CTX     : " + sharedResource.context);
            }
            if (null != sharedResource.context) {
                sharedResource.context.destroy();
                sharedResource.context = null;
            }
            if (null != sharedResource.drawable) {
                sharedResource.drawable.setRealized(false);
                sharedResource.drawable = null;
            }
            if (null != sharedResource.screen) {
                sharedResource.screen = null;
            }
            if (null != sharedResource.device) {
                sharedResource.device.close();
                sharedResource.device = null;
            }
        }
    }
    
    static final class WindowsThreadAffinity implements CPUAffinity
    {
        private long threadHandle;
        private long threadOrigAffinity;
        private long threadNewAffinity;
        
        public WindowsThreadAffinity() {
            this.threadHandle = 0L;
            this.threadOrigAffinity = 0L;
            this.threadNewAffinity = 0L;
        }
        
        @Override
        public boolean set(final int n) {
            final long getCurrentThread = GDI.GetCurrentThread();
            if (0L != this.threadHandle) {
                throw new IllegalStateException("Affinity already set");
            }
            final long setThreadAffinityMask = GDI.SetThreadAffinityMask(getCurrentThread, n);
            final int getLastError = GDI.GetLastError();
            boolean b;
            if (0L != setThreadAffinityMask) {
                b = true;
                this.threadHandle = getCurrentThread;
                this.threadNewAffinity = n;
                this.threadOrigAffinity = setThreadAffinityMask;
            }
            else {
                b = false;
            }
            if (WindowsWGLDrawableFactory.DEBUG) {
                System.err.println("WindowsThreadAffinity.set() - tid " + WindowsWGLDrawableFactory.toHexString(getCurrentThread) + " - " + GLDrawableFactory.getThreadName() + ": OK " + b + " (werr " + getLastError + "), Affinity: " + WindowsWGLDrawableFactory.toHexString(this.threadOrigAffinity) + " -> " + WindowsWGLDrawableFactory.toHexString(n));
            }
            return b;
        }
        
        @Override
        public boolean reset() {
            if (0L == this.threadHandle) {
                return true;
            }
            final long getCurrentThread = GDI.GetCurrentThread();
            if (getCurrentThread != this.threadHandle) {
                throw new IllegalStateException("TID doesn't match: set TID " + WindowsWGLDrawableFactory.toHexString(this.threadHandle) + " this TID " + WindowsWGLDrawableFactory.toHexString(getCurrentThread));
            }
            final boolean b = 0L != GDI.SetThreadAffinityMask(this.threadHandle, this.threadOrigAffinity);
            if (WindowsWGLDrawableFactory.DEBUG) {
                System.err.println("WindowsThreadAffinity.reset() - tid " + WindowsWGLDrawableFactory.toHexString(this.threadHandle) + " - " + GLDrawableFactory.getThreadName() + ": OK " + b + " (werr " + GDI.GetLastError() + "), Affinity: " + WindowsWGLDrawableFactory.toHexString(this.threadNewAffinity) + " -> orig " + WindowsWGLDrawableFactory.toHexString(this.threadOrigAffinity));
            }
            this.threadHandle = 0L;
            this.threadNewAffinity = this.threadOrigAffinity;
            return b;
        }
    }
    
    static final class WindowsProcessAffinity implements CPUAffinity
    {
        private long processHandle;
        private long newAffinity;
        private final PointerBuffer procMask;
        private final PointerBuffer sysMask;
        
        public WindowsProcessAffinity() {
            this.processHandle = 0L;
            this.newAffinity = 0L;
            this.procMask = PointerBuffer.allocateDirect(1);
            this.sysMask = PointerBuffer.allocateDirect(1);
        }
        
        @Override
        public boolean set(final int n) {
            if (0L != this.processHandle) {
                throw new IllegalStateException("Affinity already set");
            }
            final long getCurrentProcess = GDI.GetCurrentProcess();
            boolean b;
            if (GDI.GetProcessAffinityMask(getCurrentProcess, this.procMask, this.sysMask)) {
                if (GDI.SetProcessAffinityMask(getCurrentProcess, n)) {
                    this.processHandle = getCurrentProcess;
                    this.newAffinity = n;
                    b = true;
                }
                else {
                    b = false;
                }
                if (WindowsWGLDrawableFactory.DEBUG) {
                    System.err.println("WindowsProcessAffinity.set() - pid " + WindowsWGLDrawableFactory.toHexString(getCurrentProcess) + " - " + GLDrawableFactory.getThreadName() + ": OK " + b + " (werr " + GDI.GetLastError() + "), Affinity: procMask " + WindowsWGLDrawableFactory.toHexString(this.procMask.get(0)) + ", sysMask " + WindowsWGLDrawableFactory.toHexString(this.sysMask.get(0)) + " -> " + WindowsWGLDrawableFactory.toHexString(n));
                }
            }
            else {
                if (WindowsWGLDrawableFactory.DEBUG) {
                    System.err.println("WindowsProcessAffinity.set() - pid " + WindowsWGLDrawableFactory.toHexString(getCurrentProcess) + " - " + GLDrawableFactory.getThreadName() + ": Error, could not GetProcessAffinityMask, werr " + GDI.GetLastError());
                }
                b = false;
            }
            return b;
        }
        
        @Override
        public boolean reset() {
            if (0L == this.processHandle) {
                return true;
            }
            final long getCurrentProcess = GDI.GetCurrentProcess();
            if (getCurrentProcess != this.processHandle) {
                throw new IllegalStateException("PID doesn't match: set PID " + WindowsWGLDrawableFactory.toHexString(this.processHandle) + " this PID " + WindowsWGLDrawableFactory.toHexString(getCurrentProcess));
            }
            final long value = this.procMask.get(0);
            final boolean setProcessAffinityMask = GDI.SetProcessAffinityMask(this.processHandle, value);
            if (WindowsWGLDrawableFactory.DEBUG) {
                System.err.println("WindowsProcessAffinity.reset() - pid " + WindowsWGLDrawableFactory.toHexString(this.processHandle) + " - " + GLDrawableFactory.getThreadName() + ": OK " + setProcessAffinityMask + " (werr " + GDI.GetLastError() + "), Affinity: " + WindowsWGLDrawableFactory.toHexString(this.newAffinity) + " -> procMask " + WindowsWGLDrawableFactory.toHexString(value));
            }
            this.processHandle = 0L;
            this.newAffinity = value;
            return setProcessAffinityMask;
        }
    }
    
    static final class NopCPUAffinity implements CPUAffinity
    {
        @Override
        public boolean set(final int n) {
            if (WindowsWGLDrawableFactory.DEBUG) {
                System.err.println("NopCPUAffinity.set() - " + GLDrawableFactory.getThreadName());
            }
            return false;
        }
        
        @Override
        public boolean reset() {
            if (WindowsWGLDrawableFactory.DEBUG) {
                System.err.println("NopCPUAffinity.reset() - " + GLDrawableFactory.getThreadName());
            }
            return false;
        }
    }
    
    interface CPUAffinity
    {
        boolean set(final int p0);
        
        boolean reset();
    }
}
