// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import jogamp.opengl.GLContextImpl;
import com.jogamp.common.nio.Buffers;
import java.nio.Buffer;
import java.nio.ShortBuffer;
import com.jogamp.opengl.GLDrawable;
import jogamp.nativewindow.x11.X11Lib;
import com.jogamp.nativewindow.GenericUpstreamSurfacelessHook;
import jogamp.nativewindow.x11.X11DummyUpstreamSurfaceHook;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import jogamp.opengl.GLDynamicLookupHelper;
import jogamp.nativewindow.x11.X11Util;
import java.security.AccessController;
import com.jogamp.common.os.DynamicLookupHelper;
import jogamp.opengl.DesktopGLDynamicLibraryBundleInfo;
import java.security.PrivilegedAction;
import jogamp.opengl.SharedResourceRunner;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import jogamp.opengl.DesktopGLDynamicLookupHelper;
import com.jogamp.common.util.VersionNumber;
import jogamp.opengl.GLDrawableFactoryImpl;

public class X11GLXDrawableFactory extends GLDrawableFactoryImpl
{
    private static final boolean DEBUG_SHAREDCTX;
    public static final VersionNumber versionOneZero;
    public static final VersionNumber versionOneOne;
    public static final VersionNumber versionOneTwo;
    public static final VersionNumber versionOneThree;
    public static final VersionNumber versionOneFour;
    static final String GLX_SGIX_pbuffer = "GLX_SGIX_pbuffer";
    private static DesktopGLDynamicLookupHelper x11GLXDynamicLookupHelper;
    private X11GraphicsDevice defaultDevice;
    private SharedResourceImplementation sharedResourceImplementation;
    private SharedResourceRunner sharedResourceRunner;
    private boolean gotGammaRampLength;
    private int gammaRampLength;
    
    public X11GLXDrawableFactory() {
        synchronized (X11GLXDrawableFactory.class) {
            if (null == X11GLXDrawableFactory.x11GLXDynamicLookupHelper) {
                X11GLXDrawableFactory.x11GLXDynamicLookupHelper = AccessController.doPrivileged((PrivilegedAction<DesktopGLDynamicLookupHelper>)new PrivilegedAction<DesktopGLDynamicLookupHelper>() {
                    @Override
                    public DesktopGLDynamicLookupHelper run() {
                        DesktopGLDynamicLookupHelper desktopGLDynamicLookupHelper;
                        try {
                            desktopGLDynamicLookupHelper = new DesktopGLDynamicLookupHelper(new X11GLXDynamicLibraryBundleInfo());
                            if (null != desktopGLDynamicLookupHelper && desktopGLDynamicLookupHelper.isLibComplete()) {
                                GLX.getGLXProcAddressTable().reset(desktopGLDynamicLookupHelper);
                            }
                        }
                        catch (Exception ex) {
                            desktopGLDynamicLookupHelper = null;
                            if (X11GLXDrawableFactory.DEBUG) {
                                ex.printStackTrace();
                            }
                        }
                        return desktopGLDynamicLookupHelper;
                    }
                });
            }
        }
        this.defaultDevice = new X11GraphicsDevice(X11Util.getNullDisplayName(), 0);
        if (null != X11GLXDrawableFactory.x11GLXDynamicLookupHelper) {
            X11GLXGraphicsConfigurationFactory.registerFactory();
            this.sharedResourceImplementation = new SharedResourceImplementation();
            (this.sharedResourceRunner = new SharedResourceRunner(this.sharedResourceImplementation)).start();
        }
    }
    
    @Override
    protected final boolean isComplete() {
        return null != X11GLXDrawableFactory.x11GLXDynamicLookupHelper;
    }
    
    @Override
    protected final void shutdownImpl() {
        if (X11GLXDrawableFactory.DEBUG) {
            System.err.println("X11GLXDrawableFactory.shutdown");
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
        X11GLXDrawableFactory.x11GLXDynamicLookupHelper = null;
    }
    
    @Override
    public final GLDynamicLookupHelper getGLDynamicLookupHelper(final int n, final int n2) {
        return X11GLXDrawableFactory.x11GLXDynamicLookupHelper;
    }
    
    @Override
    public final AbstractGraphicsDevice getDefaultDevice() {
        return this.defaultDevice;
    }
    
    @Override
    public final boolean getIsDeviceCompatible(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return null != X11GLXDrawableFactory.x11GLXDynamicLookupHelper && abstractGraphicsDevice instanceof X11GraphicsDevice;
    }
    
    @Override
    protected final Thread getSharedResourceThread() {
        return this.sharedResourceRunner.start();
    }
    
    @Override
    protected final SharedResource getOrCreateSharedResourceImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return (SharedResource)this.sharedResourceRunner.getOrCreateShared(abstractGraphicsDevice);
    }
    
    protected final long getOrCreateSharedDpy(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final SharedResourceRunner.Resource orCreateSharedResource = this.getOrCreateSharedResource(abstractGraphicsDevice);
        if (null != orCreateSharedResource) {
            return orCreateSharedResource.getDevice().getHandle();
        }
        return 0L;
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
        return X11GLXGraphicsConfigurationFactory.getAvailableCapabilities(this, abstractGraphicsDevice);
    }
    
    @Override
    protected final GLDrawableImpl createOnscreenDrawableImpl(final NativeSurface nativeSurface) {
        if (nativeSurface == null) {
            throw new IllegalArgumentException("Null target");
        }
        return new X11OnscreenGLXDrawable(this, nativeSurface, false);
    }
    
    @Override
    protected final GLDrawableImpl createOffscreenDrawableImpl(final NativeSurface nativeSurface) {
        if (nativeSurface == null) {
            throw new IllegalArgumentException("Null target");
        }
        final AbstractGraphicsConfiguration graphicsConfiguration = nativeSurface.getGraphicsConfiguration();
        if (!((GLCapabilitiesImmutable)graphicsConfiguration.getChosenCapabilities()).isPBuffer()) {
            return new X11PixmapGLXDrawable(this, nativeSurface);
        }
        final SharedResource sharedResource = (SharedResource)this.sharedResourceRunner.getOrCreateShared(graphicsConfiguration.getScreen().getDevice());
        X11PbufferGLXDrawable x11PbufferGLXDrawable;
        if (null != sharedResource && sharedResource.isGLXVendorATI() && null == GLContext.getCurrent()) {
            sharedResource.getContext().makeCurrent();
            try {
                x11PbufferGLXDrawable = new X11PbufferGLXDrawable(this, nativeSurface);
            }
            finally {
                sharedResource.getContext().release();
            }
        }
        else {
            x11PbufferGLXDrawable = new X11PbufferGLXDrawable(this, nativeSurface);
        }
        return x11PbufferGLXDrawable;
    }
    
    public final boolean isGLXMultisampleAvailable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        if (null != abstractGraphicsDevice) {
            final SharedResource sharedResource = (SharedResource)this.sharedResourceRunner.getOrCreateShared(abstractGraphicsDevice);
            if (null != sharedResource) {
                return sharedResource.isGLXMultisampleAvailable();
            }
        }
        return false;
    }
    
    public final VersionNumber getGLXVersionNumber(final AbstractGraphicsDevice abstractGraphicsDevice) {
        if (null != abstractGraphicsDevice) {
            final SharedResource sharedResource = (SharedResource)this.sharedResourceRunner.getOrCreateShared(abstractGraphicsDevice);
            if (null != sharedResource) {
                return sharedResource.getGLXVersion();
            }
            if (abstractGraphicsDevice instanceof X11GraphicsDevice) {
                return GLXUtil.getGLXServerVersionNumber((X11GraphicsDevice)abstractGraphicsDevice);
            }
        }
        return null;
    }
    
    public final boolean isGLXVersionGreaterEqualOneOne(final AbstractGraphicsDevice abstractGraphicsDevice) {
        if (null != abstractGraphicsDevice) {
            final SharedResource sharedResource = (SharedResource)this.sharedResourceRunner.getOrCreateShared(abstractGraphicsDevice);
            if (null != sharedResource) {
                return sharedResource.isGLXVersionGreaterEqualOneOne();
            }
            if (abstractGraphicsDevice instanceof X11GraphicsDevice) {
                return GLXUtil.getGLXServerVersionNumber((X11GraphicsDevice)abstractGraphicsDevice).compareTo(X11GLXDrawableFactory.versionOneOne) >= 0;
            }
        }
        return false;
    }
    
    public final boolean isGLXVersionGreaterEqualOneThree(final AbstractGraphicsDevice abstractGraphicsDevice) {
        if (null != abstractGraphicsDevice) {
            final SharedResource sharedResource = (SharedResource)this.sharedResourceRunner.getOrCreateShared(abstractGraphicsDevice);
            if (null != sharedResource) {
                return sharedResource.isGLXVersionGreaterEqualOneThree();
            }
            if (abstractGraphicsDevice instanceof X11GraphicsDevice) {
                return GLXUtil.getGLXServerVersionNumber((X11GraphicsDevice)abstractGraphicsDevice).compareTo(X11GLXDrawableFactory.versionOneThree) >= 0;
            }
        }
        return false;
    }
    
    @Override
    public final boolean canCreateGLPbuffer(AbstractGraphicsDevice device, final GLProfile glProfile) {
        if (null == device) {
            final SharedResourceRunner.Resource orCreateShared = this.sharedResourceRunner.getOrCreateShared(this.defaultDevice);
            if (null != orCreateShared) {
                device = orCreateShared.getDevice();
            }
        }
        return this.isGLXVersionGreaterEqualOneThree(device);
    }
    
    @Override
    protected final ProxySurface createMutableSurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        X11GraphicsDevice x11GraphicsDevice;
        if (b || !(abstractGraphicsDevice instanceof X11GraphicsDevice)) {
            x11GraphicsDevice = new X11GraphicsDevice(X11Util.openDisplay(abstractGraphicsDevice.getConnection()), abstractGraphicsDevice.getUnitID(), true);
        }
        else {
            x11GraphicsDevice = (X11GraphicsDevice)abstractGraphicsDevice;
        }
        final X11GraphicsScreen x11GraphicsScreen = new X11GraphicsScreen(x11GraphicsDevice, x11GraphicsDevice.getDefaultScreen());
        final X11GLXGraphicsConfiguration chooseGraphicsConfigurationStatic = X11GLXGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable2, glCapabilitiesChooser, x11GraphicsScreen, 0);
        if (null == chooseGraphicsConfigurationStatic) {
            throw new GLException("Choosing GraphicsConfiguration failed w/ " + glCapabilitiesImmutable + " on " + x11GraphicsScreen);
        }
        return new WrappedSurface(chooseGraphicsConfigurationStatic, 0L, upstreamSurfaceHook, b);
    }
    
    @Override
    public final ProxySurface createDummySurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, GLCapabilitiesImmutable fixOnscreenGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        fixOnscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOnscreenGLCapabilities(fixOnscreenGLCapabilities);
        return this.createMutableSurfaceImpl(abstractGraphicsDevice, b, fixOnscreenGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, new X11DummyUpstreamSurfaceHook(n, n2));
    }
    
    @Override
    public final ProxySurface createSurfacelessImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, GLCapabilitiesImmutable fixOnscreenGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        fixOnscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOnscreenGLCapabilities(fixOnscreenGLCapabilities);
        return this.createMutableSurfaceImpl(abstractGraphicsDevice, b, fixOnscreenGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, new GenericUpstreamSurfacelessHook(n, n2));
    }
    
    @Override
    protected final ProxySurface createProxySurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final long n2, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        final X11GraphicsDevice x11GraphicsDevice = new X11GraphicsDevice(X11Util.openDisplay(abstractGraphicsDevice.getConnection()), abstractGraphicsDevice.getUnitID(), true);
        final X11GraphicsScreen x11GraphicsScreen = new X11GraphicsScreen(x11GraphicsDevice, n);
        final int getVisualIDFromWindow = X11Lib.GetVisualIDFromWindow(x11GraphicsDevice.getHandle(), n2);
        if (getVisualIDFromWindow == 0) {
            throw new GLException("Undefined VisualID of window 0x" + Long.toHexString(n2) + ", window probably invalid");
        }
        if (X11GLXDrawableFactory.DEBUG) {
            System.err.println("X11GLXDrawableFactory.createProxySurfaceImpl 0x" + Long.toHexString(n2) + ": visualID 0x" + Integer.toHexString(getVisualIDFromWindow));
        }
        final X11GLXGraphicsConfiguration chooseGraphicsConfigurationStatic = X11GLXGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable, glCapabilitiesChooser, x11GraphicsScreen, getVisualIDFromWindow);
        if (X11GLXDrawableFactory.DEBUG) {
            System.err.println("X11GLXDrawableFactory.createProxySurfaceImpl 0x" + Long.toHexString(n2) + ": " + chooseGraphicsConfigurationStatic);
        }
        return new WrappedSurface(chooseGraphicsConfigurationStatic, n2, upstreamSurfaceHook, true);
    }
    
    @Override
    protected final GLContext createExternalGLContextImpl() {
        return X11ExternalGLXContext.create(this, null);
    }
    
    @Override
    public final boolean canCreateExternalGLDrawable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return this.canCreateGLPbuffer(abstractGraphicsDevice, null);
    }
    
    @Override
    protected final GLDrawable createExternalGLDrawableImpl() {
        return X11ExternalGLXDrawable.create(this, null);
    }
    
    @Override
    protected final synchronized int getGammaRampLength(final NativeSurface nativeSurface) {
        if (this.gotGammaRampLength) {
            return this.gammaRampLength;
        }
        final long displayHandle = nativeSurface.getDisplayHandle();
        if (0L == displayHandle) {
            return 0;
        }
        final int screenIndex = nativeSurface.getScreenIndex();
        final int[] array = { 0 };
        if (!X11Lib.XF86VidModeGetGammaRampSize(displayHandle, screenIndex, array, 0)) {
            return 0;
        }
        this.gotGammaRampLength = true;
        this.gammaRampLength = array[0];
        System.err.println("XXX: Gamma ramp size: " + this.gammaRampLength);
        return this.gammaRampLength;
    }
    
    @Override
    protected final boolean setGammaRamp(final NativeSurface nativeSurface, final float[] array) {
        final long displayHandle = nativeSurface.getDisplayHandle();
        if (0L == displayHandle) {
            return false;
        }
        final int screenIndex = nativeSurface.getScreenIndex();
        final int length = array.length;
        final short[] array2 = new short[length];
        for (int i = 0; i < length; ++i) {
            array2[i] = (short)(array[i] * 65535.0f);
        }
        final boolean xf86VidModeSetGammaRamp = X11Lib.XF86VidModeSetGammaRamp(displayHandle, screenIndex, array2.length, array2, 0, array2, 0, array2, 0);
        if (X11GLXDrawableFactory.DEBUG) {
            dumpRamp("SET__", array2.length, array2, array2, array2);
        }
        return xf86VidModeSetGammaRamp;
    }
    
    private static void dumpRamp(final String s, final int n, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final ShortBuffer shortBuffer3) {
        for (int i = 0; i < n; ++i) {
            if (0 == i % 4) {
                System.err.printf("%n%4d/%4d %s: ", i, n, s);
            }
            System.err.printf(" [%04X %04X %04X], ", shortBuffer.get(i), shortBuffer2.get(i), shortBuffer3.get(i));
        }
        System.err.println();
    }
    
    private static void dumpRamp(final String s, final int n, final short[] array, final short[] array2, final short[] array3) {
        for (int i = 0; i < n; ++i) {
            if (0 == i % 4) {
                System.err.printf("%n%4d/%4d %s: ", i, n, s);
            }
            System.err.printf(" [%04X %04X %04X], ", array[i], array2[i], array3[i]);
        }
        System.err.println();
    }
    
    @Override
    protected final Buffer getGammaRamp(final NativeSurface nativeSurface) {
        final long displayHandle = nativeSurface.getDisplayHandle();
        if (0L == displayHandle) {
            return null;
        }
        final int screenIndex = nativeSurface.getScreenIndex();
        final int gammaRampLength = this.getGammaRampLength(nativeSurface);
        final ShortBuffer directShortBuffer = Buffers.newDirectShortBuffer(3 * gammaRampLength);
        final ShortBuffer shortBuffer = Buffers.slice(directShortBuffer, 0 * gammaRampLength, gammaRampLength);
        final ShortBuffer shortBuffer2 = Buffers.slice(directShortBuffer, 1 * gammaRampLength, gammaRampLength);
        final ShortBuffer shortBuffer3 = Buffers.slice(directShortBuffer, 2 * gammaRampLength, gammaRampLength);
        if (!X11Lib.XF86VidModeGetGammaRamp(displayHandle, screenIndex, gammaRampLength, shortBuffer, shortBuffer2, shortBuffer3)) {
            return null;
        }
        if (X11GLXDrawableFactory.DEBUG) {
            dumpRamp("GET__", gammaRampLength, shortBuffer, shortBuffer2, shortBuffer3);
        }
        return directShortBuffer;
    }
    
    @Override
    protected final void resetGammaRamp(final NativeSurface nativeSurface, final Buffer buffer) {
        if (buffer == null) {
            return;
        }
        final long displayHandle = nativeSurface.getDisplayHandle();
        if (0L == displayHandle) {
            return;
        }
        resetGammaRamp(displayHandle, nativeSurface.getScreenIndex(), buffer);
    }
    
    @Override
    protected final void resetGammaRamp(final DeviceScreenID deviceScreenID, final Buffer buffer) {
        if (buffer == null) {
            return;
        }
        final long openDisplay = X11Util.openDisplay(deviceScreenID.deviceConnection);
        if (0L == openDisplay) {
            return;
        }
        try {
            resetGammaRamp(openDisplay, deviceScreenID.screenIdx, buffer);
        }
        finally {
            X11Util.closeDisplay(openDisplay);
        }
    }
    
    private static final void resetGammaRamp(final long n, final int n2, final Buffer buffer) {
        final ShortBuffer shortBuffer = (ShortBuffer)buffer;
        final int capacity = shortBuffer.capacity();
        if (capacity % 3 != 0) {
            throw new IllegalArgumentException("Must not be the original gamma ramp");
        }
        final int n3 = capacity / 3;
        final ShortBuffer shortBuffer2 = Buffers.slice(shortBuffer, 0 * n3, n3);
        final ShortBuffer shortBuffer3 = Buffers.slice(shortBuffer, 1 * n3, n3);
        final ShortBuffer shortBuffer4 = Buffers.slice(shortBuffer, 2 * n3, n3);
        if (X11GLXDrawableFactory.DEBUG) {
            dumpRamp("RESET", n3, shortBuffer2, shortBuffer3, shortBuffer4);
        }
        X11Lib.XF86VidModeSetGammaRamp(n, n2, n3, shortBuffer2, shortBuffer3, shortBuffer4);
    }
    
    static {
        DEBUG_SHAREDCTX = (X11GLXDrawableFactory.DEBUG || GLContext.DEBUG);
        versionOneZero = new VersionNumber(1, 0, 0);
        versionOneOne = new VersionNumber(1, 1, 0);
        versionOneTwo = new VersionNumber(1, 2, 0);
        versionOneThree = new VersionNumber(1, 3, 0);
        versionOneFour = new VersionNumber(1, 4, 0);
        X11GLXDrawableFactory.x11GLXDynamicLookupHelper = null;
    }
    
    static class SharedResource implements SharedResourceRunner.Resource
    {
        private final String glXServerVendorName;
        private final boolean isGLXServerVendorATI;
        private final boolean isGLXServerVendorNVIDIA;
        private final VersionNumber glXServerVersion;
        private final boolean glXServerVersionOneOneCapable;
        private final boolean glXServerVersionOneThreeCapable;
        private final boolean glXMultisampleAvailable;
        X11GraphicsDevice device;
        X11GraphicsScreen screen;
        GLDrawableImpl drawable;
        GLContextImpl context;
        
        SharedResource(final X11GraphicsDevice device, final X11GraphicsScreen screen, final GLDrawableImpl drawable, final GLContextImpl context, final VersionNumber glXServerVersion, final String glXServerVendorName, final boolean glXMultisampleAvailable) {
            this.device = device;
            this.screen = screen;
            this.drawable = drawable;
            this.context = context;
            this.glXServerVersion = glXServerVersion;
            this.glXServerVersionOneOneCapable = (this.glXServerVersion.compareTo(X11GLXDrawableFactory.versionOneOne) >= 0);
            this.glXServerVersionOneThreeCapable = (this.glXServerVersion.compareTo(X11GLXDrawableFactory.versionOneThree) >= 0);
            this.glXServerVendorName = glXServerVendorName;
            this.isGLXServerVendorATI = GLXUtil.isVendorATI(this.glXServerVendorName);
            this.isGLXServerVendorNVIDIA = GLXUtil.isVendorNVIDIA(this.glXServerVendorName);
            this.glXMultisampleAvailable = glXMultisampleAvailable;
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
        
        final String getGLXVendorName() {
            return this.glXServerVendorName;
        }
        
        final boolean isGLXVendorATI() {
            return this.isGLXServerVendorATI;
        }
        
        final boolean isGLXVendorNVIDIA() {
            return this.isGLXServerVendorNVIDIA;
        }
        
        final VersionNumber getGLXVersion() {
            return this.glXServerVersion;
        }
        
        final boolean isGLXVersionGreaterEqualOneOne() {
            return this.glXServerVersionOneOneCapable;
        }
        
        final boolean isGLXVersionGreaterEqualOneThree() {
            return this.glXServerVersionOneThreeCapable;
        }
        
        final boolean isGLXMultisampleAvailable() {
            return this.glXMultisampleAvailable;
        }
    }
    
    class SharedResourceImplementation extends SharedResourceRunner.AImplementation
    {
        @Override
        public boolean isDeviceSupported(final AbstractGraphicsDevice abstractGraphicsDevice) {
            final X11GraphicsDevice x11GraphicsDevice = new X11GraphicsDevice(X11Util.openDisplay(abstractGraphicsDevice.getConnection()), abstractGraphicsDevice.getUnitID(), true);
            x11GraphicsDevice.lock();
            boolean glxAvailableOnServer;
            try {
                glxAvailableOnServer = GLXUtil.isGLXAvailableOnServer(x11GraphicsDevice);
            }
            finally {
                x11GraphicsDevice.unlock();
                x11GraphicsDevice.close();
            }
            if (X11GLXDrawableFactory.DEBUG) {
                System.err.println("GLX " + (glxAvailableOnServer ? "is" : "not") + " available on device/server: " + x11GraphicsDevice);
            }
            return glxAvailableOnServer;
        }
        
        @Override
        public SharedResourceRunner.Resource createSharedResource(final AbstractGraphicsDevice abstractGraphicsDevice) {
            final X11GraphicsDevice x11GraphicsDevice = new X11GraphicsDevice(X11Util.openDisplay(abstractGraphicsDevice.getConnection()), abstractGraphicsDevice.getUnitID(), true);
            GLContextImpl glContextImpl = null;
            boolean b = false;
            x11GraphicsDevice.lock();
            try {
                final X11GraphicsScreen x11GraphicsScreen = new X11GraphicsScreen(x11GraphicsDevice, x11GraphicsDevice.getDefaultScreen());
                GLXUtil.initGLXClientDataSingleton(x11GraphicsDevice);
                final String glXQueryServerString = GLX.glXQueryServerString(x11GraphicsDevice.getHandle(), 0, 1);
                final boolean multisampleAvailable = GLXUtil.isMultisampleAvailable(GLX.glXQueryServerString(x11GraphicsDevice.getHandle(), 0, 3));
                final GLProfile value = GLProfile.get(x11GraphicsDevice, GLProfile.GL_PROFILE_LIST_MIN_DESKTOP, false);
                if (null == value) {
                    throw new GLException("Couldn't get default GLProfile for device: " + x11GraphicsDevice);
                }
                final GLCapabilities glCapabilities = new GLCapabilities(value);
                final GLDrawableImpl onscreenDrawableImpl = X11GLXDrawableFactory.this.createOnscreenDrawableImpl(X11GLXDrawableFactory.this.createDummySurfaceImpl(x11GraphicsDevice, false, glCapabilities, glCapabilities, null, 64, 64));
                onscreenDrawableImpl.setRealized(true);
                final boolean b2 = !((X11GLCapabilities)onscreenDrawableImpl.getChosenGLCapabilities()).hasFBConfig();
                VersionNumber versionNumber;
                if (b2) {
                    versionNumber = X11GLXDrawableFactory.versionOneOne;
                }
                else {
                    versionNumber = GLXUtil.getGLXServerVersionNumber(x11GraphicsDevice);
                }
                glContextImpl = (GLContextImpl)onscreenDrawableImpl.createContext(null);
                if (null == glContextImpl) {
                    throw new GLException("Couldn't create shared context for drawable: " + onscreenDrawableImpl);
                }
                b = (0 != glContextImpl.makeCurrent());
                boolean access$200;
                if (b && glContextImpl.getGLVersionNumber().compareTo(GLContext.Version3_0) >= 0) {
                    access$200 = X11GLXDrawableFactory.this.probeSurfacelessCtx(glContextImpl, true);
                }
                else {
                    X11GLXDrawableFactory.this.setNoSurfacelessCtxQuirk(glContextImpl);
                    access$200 = false;
                }
                if (glContextImpl.hasRendererQuirk(8)) {
                    X11Util.markAllDisplaysUnclosable();
                }
                if (X11GLXDrawableFactory.DEBUG_SHAREDCTX) {
                    System.err.println("SharedDevice:  " + x11GraphicsDevice);
                    System.err.println("SharedScreen:  " + x11GraphicsScreen);
                    System.err.println("SharedContext: " + glContextImpl + ", madeCurrent " + b);
                    System.err.println("  allowsSurfacelessCtx " + access$200);
                    System.err.println("GLX Server Vendor:      " + glXQueryServerString);
                    System.err.println("GLX Server Version:     " + versionNumber + ", forced " + b2);
                    System.err.println("GLX Server Multisample: " + multisampleAvailable);
                    System.err.println("GLX Client Vendor:      " + GLXUtil.getClientVendorName());
                    System.err.println("GLX Client Version:     " + GLXUtil.getClientVersionNumber());
                    System.err.println("GLX Client Multisample: " + GLXUtil.isClientMultisampleAvailable());
                }
                return new SharedResource(x11GraphicsDevice, x11GraphicsScreen, onscreenDrawableImpl, glContextImpl, versionNumber, glXQueryServerString, multisampleAvailable && GLXUtil.isClientMultisampleAvailable());
            }
            catch (Throwable t) {
                throw new GLException("X11GLXDrawableFactory - Could not initialize shared resources for " + abstractGraphicsDevice, t);
            }
            finally {
                if (b) {
                    glContextImpl.release();
                }
                x11GraphicsDevice.unlock();
            }
        }
        
        @Override
        public void releaseSharedResource(final SharedResourceRunner.Resource resource) {
            final SharedResource sharedResource = (SharedResource)resource;
            if (X11GLXDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("Shutdown Shared:");
                System.err.println("Device  : " + sharedResource.device);
                System.err.println("Screen  : " + sharedResource.screen);
                System.err.println("Drawable: " + sharedResource.drawable);
                System.err.println("CTX     : " + sharedResource.context);
                ExceptionUtils.dumpStack(System.err);
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
}
