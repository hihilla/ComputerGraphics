// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import jogamp.opengl.SharedResourceRunner;
import java.nio.ShortBuffer;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.nativewindow.GenericUpstreamSurfacelessHook;
import jogamp.nativewindow.macosx.OSXDummyUpstreamSurfaceHook;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLDrawableImpl;
import java.util.List;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.GL;
import jogamp.opengl.GLContextImpl;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import jogamp.opengl.GLDynamicLookupHelper;
import com.jogamp.common.os.DynamicLibraryBundle;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;
import jogamp.opengl.DesktopGLDynamicLibraryBundleInfo;
import java.util.HashSet;
import com.jogamp.nativewindow.macosx.MacOSXGraphicsDevice;
import java.util.HashMap;
import jogamp.opengl.DesktopGLDynamicLookupHelper;
import jogamp.opengl.GLDrawableFactoryImpl;

public class MacOSXCGLDrawableFactory extends GLDrawableFactoryImpl
{
    private static final boolean DEBUG_SHAREDCTX;
    private static DesktopGLDynamicLookupHelper macOSXCGLDynamicLookupHelper;
    private HashMap<String, SharedResource> sharedMap;
    private MacOSXGraphicsDevice defaultDevice;
    private final HashSet<String> devicesTried;
    private static final int GAMMA_RAMP_LENGTH = 256;
    
    public MacOSXCGLDrawableFactory() {
        this.sharedMap = new HashMap<String, SharedResource>();
        this.devicesTried = new HashSet<String>();
        synchronized (MacOSXCGLDrawableFactory.class) {
            if (null == MacOSXCGLDrawableFactory.macOSXCGLDynamicLookupHelper) {
                DynamicLibraryBundle macOSXCGLDynamicLookupHelper = null;
                try {
                    macOSXCGLDynamicLookupHelper = new DesktopGLDynamicLookupHelper(new MacOSXCGLDynamicLibraryBundleInfo());
                }
                catch (GLException ex) {
                    if (MacOSXCGLDrawableFactory.DEBUG) {
                        ex.printStackTrace();
                    }
                }
                if (null != macOSXCGLDynamicLookupHelper && macOSXCGLDynamicLookupHelper.isLibComplete()) {
                    MacOSXCGLDrawableFactory.macOSXCGLDynamicLookupHelper = (DesktopGLDynamicLookupHelper)macOSXCGLDynamicLookupHelper;
                }
            }
        }
        this.defaultDevice = new MacOSXGraphicsDevice(0);
        if (null != MacOSXCGLDrawableFactory.macOSXCGLDynamicLookupHelper) {
            MacOSXCGLGraphicsConfigurationFactory.registerFactory();
            if (GLProfile.isAWTAvailable()) {
                try {
                    ReflectionUtil.callStaticMethod("jogamp.opengl.macosx.cgl.awt.MacOSXAWTCGLGraphicsConfigurationFactory", "registerFactory", null, null, this.getClass().getClassLoader());
                }
                catch (Exception ex2) {}
            }
            this.sharedMap = new HashMap<String, SharedResource>();
        }
    }
    
    @Override
    protected final boolean isComplete() {
        return null != MacOSXCGLDrawableFactory.macOSXCGLDynamicLookupHelper;
    }
    
    @Override
    protected final void shutdownImpl() {
        if (MacOSXCGLDrawableFactory.DEBUG) {
            System.err.println("MacOSXCGLDrawableFactory.shutdown");
        }
        if (null != this.sharedMap) {
            this.sharedMap.clear();
            this.sharedMap = null;
        }
        this.defaultDevice = null;
        MacOSXCGLDrawableFactory.macOSXCGLDynamicLookupHelper = null;
    }
    
    @Override
    public final GLDynamicLookupHelper getGLDynamicLookupHelper(final int n, final int n2) {
        return MacOSXCGLDrawableFactory.macOSXCGLDynamicLookupHelper;
    }
    
    @Override
    public final AbstractGraphicsDevice getDefaultDevice() {
        return this.defaultDevice;
    }
    
    @Override
    public final boolean getIsDeviceCompatible(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return null != MacOSXCGLDrawableFactory.macOSXCGLDynamicLookupHelper && abstractGraphicsDevice instanceof MacOSXGraphicsDevice;
    }
    
    private boolean getDeviceTried(final String s) {
        synchronized (this.devicesTried) {
            return this.devicesTried.contains(s);
        }
    }
    
    private void addDeviceTried(final String s) {
        synchronized (this.devicesTried) {
            this.devicesTried.add(s);
        }
    }
    
    private void removeDeviceTried(final String s) {
        synchronized (this.devicesTried) {
            this.devicesTried.remove(s);
        }
    }
    
    @Override
    protected final SharedResource getOrCreateSharedResourceImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final String connection = abstractGraphicsDevice.getConnection();
        SharedResource sharedResource;
        synchronized (this.sharedMap) {
            sharedResource = this.sharedMap.get(connection);
        }
        if (null == sharedResource && !this.getDeviceTried(connection)) {
            this.addDeviceTried(connection);
            final MacOSXGraphicsDevice macOSXGraphicsDevice = new MacOSXGraphicsDevice(abstractGraphicsDevice.getUnitID());
            GLDrawable onscreenDrawableImpl = null;
            GLDrawable glDrawable = null;
            GLContextImpl glContextImpl = null;
            macOSXGraphicsDevice.lock();
            try {
                final GLProfile value = GLProfile.get(macOSXGraphicsDevice, GLProfile.GL_PROFILE_LIST_MIN_DESKTOP, false);
                if (null == value) {
                    throw new GLException("Couldn't get default GLProfile for device: " + macOSXGraphicsDevice);
                }
                final GLCapabilities glCapabilities = new GLCapabilities(value);
                onscreenDrawableImpl = this.createOnscreenDrawableImpl(this.createDummySurfaceImpl(macOSXGraphicsDevice, false, glCapabilities, glCapabilities, null, 64, 64));
                onscreenDrawableImpl.setRealized(true);
                glContextImpl = (MacOSXCGLContext)onscreenDrawableImpl.createContext(null);
                if (null == glContextImpl) {
                    throw new GLException("Couldn't create shared context for drawable: " + onscreenDrawableImpl);
                }
                final boolean b = 0 != glContextImpl.makeCurrent();
                boolean b2;
                boolean npotTextureAvailable;
                boolean extensionAvailable;
                boolean extensionAvailable2;
                GLRendererQuirks rendererQuirks;
                if (b) {
                    if (this.probeSurfacelessCtx(glContextImpl, false)) {
                        b2 = true;
                        glDrawable = glContextImpl.getGLDrawable();
                    }
                    else {
                        b2 = false;
                    }
                    final GL gl = glContextImpl.getGL();
                    npotTextureAvailable = gl.isNPOTTextureAvailable();
                    extensionAvailable = gl.isExtensionAvailable("GL_EXT_texture_rectangle");
                    extensionAvailable2 = gl.isExtensionAvailable("GL_APPLE_float_pixels");
                    rendererQuirks = glContextImpl.getRendererQuirks();
                }
                else {
                    b2 = false;
                    npotTextureAvailable = false;
                    extensionAvailable = false;
                    extensionAvailable2 = false;
                    rendererQuirks = null;
                }
                sharedResource = new SharedResource(macOSXGraphicsDevice, b, npotTextureAvailable, extensionAvailable, extensionAvailable2, rendererQuirks);
                if (MacOSXCGLDrawableFactory.DEBUG_SHAREDCTX) {
                    System.err.println("SharedDevice:  " + macOSXGraphicsDevice);
                    System.err.println("SharedContext: " + glContextImpl + ", madeCurrent " + b);
                    System.err.println("  NPOT " + npotTextureAvailable + ", RECT " + extensionAvailable + ", FloatPixels " + extensionAvailable2);
                    System.err.println("  allowsSurfacelessCtx " + b2);
                    System.err.println("  glRendererQuirks " + rendererQuirks);
                }
                synchronized (this.sharedMap) {
                    this.sharedMap.put(connection, sharedResource);
                }
            }
            catch (Throwable t) {
                throw new GLException("MacOSXCGLDrawableFactory - Could not initialize shared resources for " + abstractGraphicsDevice, t);
            }
            finally {
                if (null != glContextImpl) {
                    try {
                        glContextImpl.destroy();
                    }
                    catch (GLException ex) {
                        if (MacOSXCGLDrawableFactory.DEBUG_SHAREDCTX) {
                            System.err.println("MacOSXCGLDrawableFactory.createShared: INFO: destroy caught exception:");
                            ex.printStackTrace();
                        }
                    }
                }
                if (null != glDrawable) {
                    glDrawable.setRealized(false);
                }
                if (null != onscreenDrawableImpl) {
                    onscreenDrawableImpl.setRealized(false);
                }
                macOSXGraphicsDevice.unlock();
                this.removeDeviceTried(connection);
            }
        }
        return sharedResource;
    }
    
    @Override
    protected final Thread getSharedResourceThread() {
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
        return MacOSXCGLGraphicsConfiguration.getAvailableCapabilities(this, abstractGraphicsDevice);
    }
    
    @Override
    protected GLDrawableImpl createOnscreenDrawableImpl(final NativeSurface nativeSurface) {
        if (nativeSurface == null) {
            throw new IllegalArgumentException("Null target");
        }
        return new MacOSXOnscreenCGLDrawable(this, nativeSurface);
    }
    
    @Override
    protected GLDrawableImpl createOffscreenDrawableImpl(final NativeSurface nativeSurface) {
        final MutableGraphicsConfiguration mutableGraphicsConfiguration = (MutableGraphicsConfiguration)nativeSurface.getGraphicsConfiguration();
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)mutableGraphicsConfiguration.getChosenCapabilities();
        if (!glCapabilitiesImmutable.isPBuffer()) {
            final GLCapabilities chosenCapabilities = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
            chosenCapabilities.setPBuffer(true);
            chosenCapabilities.setBitmap(false);
            mutableGraphicsConfiguration.setChosenCapabilities(chosenCapabilities);
            return new MacOSXOffscreenCGLDrawable(this, nativeSurface);
        }
        return new MacOSXPbufferCGLDrawable(this, nativeSurface);
    }
    
    @Override
    public boolean canCreateGLPbuffer(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile) {
        return glProfile.isGL2();
    }
    
    @Override
    protected ProxySurface createMutableSurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        MacOSXGraphicsDevice macOSXGraphicsDevice;
        if (b || !(abstractGraphicsDevice instanceof MacOSXGraphicsDevice)) {
            macOSXGraphicsDevice = new MacOSXGraphicsDevice(abstractGraphicsDevice.getUnitID());
        }
        else {
            macOSXGraphicsDevice = (MacOSXGraphicsDevice)abstractGraphicsDevice;
        }
        final DefaultGraphicsScreen defaultGraphicsScreen = new DefaultGraphicsScreen(macOSXGraphicsDevice, 0);
        final MacOSXCGLGraphicsConfiguration chooseGraphicsConfigurationStatic = MacOSXCGLGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable2, glCapabilitiesChooser, defaultGraphicsScreen, true);
        if (null == chooseGraphicsConfigurationStatic) {
            throw new GLException("Choosing GraphicsConfiguration failed w/ " + glCapabilitiesImmutable + " on " + defaultGraphicsScreen);
        }
        return new WrappedSurface(chooseGraphicsConfigurationStatic, 0L, upstreamSurfaceHook, b);
    }
    
    @Override
    public final ProxySurface createDummySurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, GLCapabilitiesImmutable fixOnscreenGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        fixOnscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOnscreenGLCapabilities(fixOnscreenGLCapabilities);
        return this.createMutableSurfaceImpl(abstractGraphicsDevice, b, fixOnscreenGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, new OSXDummyUpstreamSurfaceHook(n, n2));
    }
    
    @Override
    public final ProxySurface createSurfacelessImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, GLCapabilitiesImmutable fixOnscreenGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        fixOnscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOnscreenGLCapabilities(fixOnscreenGLCapabilities);
        return this.createMutableSurfaceImpl(abstractGraphicsDevice, b, fixOnscreenGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, new GenericUpstreamSurfacelessHook(n, n2));
    }
    
    @Override
    protected ProxySurface createProxySurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final long n2, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        return new WrappedSurface(MacOSXCGLGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable, glCapabilitiesChooser, new DefaultGraphicsScreen(new MacOSXGraphicsDevice(abstractGraphicsDevice.getUnitID()), n), true), n2, upstreamSurfaceHook, true);
    }
    
    @Override
    protected GLContext createExternalGLContextImpl() {
        return MacOSXExternalCGLContext.create(this);
    }
    
    @Override
    public boolean canCreateExternalGLDrawable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return false;
    }
    
    @Override
    protected GLDrawable createExternalGLDrawableImpl() {
        throw new GLException("Not yet implemented");
    }
    
    @Override
    protected int getGammaRampLength(final NativeSurface nativeSurface) {
        return 256;
    }
    
    @Override
    protected boolean setGammaRamp(final NativeSurface nativeSurface, final float[] array) {
        final FloatBuffer directFloatBuffer = Buffers.newDirectFloatBuffer(array);
        return CGL.setGammaRamp(array.length, directFloatBuffer, directFloatBuffer, directFloatBuffer);
    }
    
    @Override
    protected Buffer getGammaRamp(final NativeSurface nativeSurface) {
        return ShortBuffer.allocate(0);
    }
    
    @Override
    protected void resetGammaRamp(final NativeSurface nativeSurface, final Buffer buffer) {
        CGL.resetGammaRamp();
    }
    
    @Override
    protected final void resetGammaRamp(final DeviceScreenID deviceScreenID, final Buffer buffer) {
        CGL.resetGammaRamp();
    }
    
    static {
        DEBUG_SHAREDCTX = (MacOSXCGLDrawableFactory.DEBUG || GLContext.DEBUG);
        MacOSXCGLDrawableFactory.macOSXCGLDynamicLookupHelper = null;
    }
    
    static class SharedResource implements SharedResourceRunner.Resource
    {
        private final GLRendererQuirks glRendererQuirks;
        MacOSXGraphicsDevice device;
        boolean valid;
        boolean hasNPOTTextures;
        boolean hasRECTTextures;
        boolean hasAppleFloatPixels;
        
        SharedResource(final MacOSXGraphicsDevice device, final boolean valid, final boolean hasNPOTTextures, final boolean hasRECTTextures, final boolean hasAppleFloatPixels, final GLRendererQuirks glRendererQuirks) {
            this.glRendererQuirks = glRendererQuirks;
            this.device = device;
            this.valid = valid;
            this.hasNPOTTextures = hasNPOTTextures;
            this.hasRECTTextures = hasRECTTextures;
            this.hasAppleFloatPixels = hasAppleFloatPixels;
        }
        
        @Override
        public final boolean isAvailable() {
            return this.valid;
        }
        
        @Override
        public final MacOSXGraphicsDevice getDevice() {
            return this.device;
        }
        
        final boolean isNPOTTextureAvailable() {
            return this.hasNPOTTextures;
        }
        
        final boolean isRECTTextureAvailable() {
            return this.hasRECTTextures;
        }
        
        final boolean isAppleFloatPixelsAvailable() {
            return this.hasAppleFloatPixels;
        }
        
        @Override
        public final AbstractGraphicsScreen getScreen() {
            return null;
        }
        
        @Override
        public final GLDrawableImpl getDrawable() {
            return null;
        }
        
        @Override
        public GLContextImpl getContext() {
            return null;
        }
        
        @Override
        public GLRendererQuirks getRendererQuirks(final GLProfile glProfile) {
            return this.glRendererQuirks;
        }
    }
}
