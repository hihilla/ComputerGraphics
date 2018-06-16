// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.nativewindow.x11.X11Util;
import jogamp.opengl.GLContextImpl;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.common.util.VersionNumber;
import jogamp.opengl.Debug;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.MutableSurface;
import com.jogamp.nativewindow.GenericUpstreamSurfacelessHook;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.opengl.GLCapabilitiesChooser;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.nativewindow.NativeSurface;
import java.nio.IntBuffer;
import java.util.ArrayList;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.common.nio.PointerBuffer;
import com.jogamp.opengl.egl.EGL;
import com.jogamp.common.nio.Buffers;
import java.util.List;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import java.util.Iterator;
import java.util.Map;
import com.jogamp.opengl.GLProfile;
import com.jogamp.common.os.DynamicLookupHelper;
import com.jogamp.opengl.GLException;
import jogamp.opengl.GLDynamicLibraryBundleInfo;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.nativewindow.NativeWindowFactory;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.common.os.Platform;
import jogamp.opengl.SharedResourceRunner;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import jogamp.opengl.GLDynamicLookupHelper;
import jogamp.opengl.GLDrawableFactoryImpl;

public class EGLDrawableFactory extends GLDrawableFactoryImpl
{
    protected static final boolean DEBUG;
    private static final boolean DEBUG_SHAREDCTX;
    private static boolean eglDynamicLookupHelperInit;
    private static GLDynamicLookupHelper eglES1DynamicLookupHelper;
    private static GLDynamicLookupHelper eglES2DynamicLookupHelper;
    private static GLDynamicLookupHelper eglGLnDynamicLookupHelper;
    private static boolean isANGLE;
    private static boolean hasX11;
    private static String defaultConnection;
    private static EGLGraphicsDevice defaultDevice;
    private static EGLFeatures defaultDeviceEGLFeatures;
    private static SharedResource defaultSharedResource;
    static final String eglInitializeFuncName = "eglInitialize";
    private SharedResourceImplementation sharedResourceImplementation;
    private SharedResourceRunner sharedResourceRunner;
    
    private static final boolean isANGLE(final GLDynamicLookupHelper glDynamicLookupHelper) {
        return Platform.OSType.WINDOWS == PlatformPropsImpl.OS_TYPE && (glDynamicLookupHelper.isFunctionAvailable("eglQuerySurfacePointerANGLE") || glDynamicLookupHelper.isFunctionAvailable("glBlitFramebufferANGLE") || glDynamicLookupHelper.isFunctionAvailable("glRenderbufferStorageMultisampleANGLE"));
    }
    
    private static final boolean includesES1(final GLDynamicLookupHelper glDynamicLookupHelper) {
        return glDynamicLookupHelper.isFunctionAvailable("glLoadIdentity") && glDynamicLookupHelper.isFunctionAvailable("glEnableClientState") && glDynamicLookupHelper.isFunctionAvailable("glColorPointer");
    }
    
    public EGLDrawableFactory() {
        synchronized (EGLDrawableFactory.class) {
            if (EGLDrawableFactory.eglDynamicLookupHelperInit) {
                return;
            }
            EGLDrawableFactory.eglDynamicLookupHelperInit = true;
            final String nativeWindowType = NativeWindowFactory.getNativeWindowType(true);
            if (NativeWindowFactory.TYPE_X11 == nativeWindowType) {
                EGLDrawableFactory.hasX11 = true;
                try {
                    ReflectionUtil.createInstance("jogamp.opengl.x11.glx.X11GLXGraphicsConfigurationFactory", EGLDrawableFactory.class.getClassLoader());
                }
                catch (Exception ex4) {}
            }
            else {
                EGLDrawableFactory.hasX11 = false;
            }
            EGLDrawableFactory.defaultConnection = NativeWindowFactory.getDefaultDisplayConnection(nativeWindowType);
            boolean b = false;
            long n = 0L;
            GLDynamicLookupHelper glDynamicLookupHelper = null;
            try {
                glDynamicLookupHelper = new GLDynamicLookupHelper(new EGLES2DynamicLibraryBundleInfo());
            }
            catch (GLException ex) {
                if (EGLDrawableFactory.DEBUG) {
                    ex.printStackTrace();
                }
            }
            if (null != glDynamicLookupHelper && glDynamicLookupHelper.isLibComplete() && (b = EGLAcc.resetProcAddressTable(glDynamicLookupHelper))) {
                n = glDynamicLookupHelper.dynamicLookupFunction("eglInitialize");
                EGLDrawableFactory.eglES2DynamicLookupHelper = glDynamicLookupHelper;
                final boolean b2 = null == EGLDrawableFactory.eglES1DynamicLookupHelper && includesES1(EGLDrawableFactory.eglES2DynamicLookupHelper);
                if (b2) {
                    EGLDrawableFactory.eglES1DynamicLookupHelper = glDynamicLookupHelper;
                }
                final boolean angle = isANGLE(EGLDrawableFactory.eglES2DynamicLookupHelper);
                EGLDrawableFactory.isANGLE |= angle;
                if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                    System.err.println("Info: EGLDrawableFactory: EGL ES2 - OK (includesES1 " + b2 + ", isANGLE: " + angle + ", eglInitialize 0x" + Long.toHexString(n) + ")");
                    if (b2) {
                        System.err.println("Info: EGLDrawableFactory: EGL ES1 - OK (ES2 lib)");
                    }
                }
            }
            else if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                System.err.println("Info: EGLDrawableFactory: EGL ES2 - NOPE");
            }
            if (null == EGLDrawableFactory.eglES1DynamicLookupHelper) {
                GLDynamicLookupHelper eglES1DynamicLookupHelper = null;
                try {
                    eglES1DynamicLookupHelper = new GLDynamicLookupHelper(new EGLES1DynamicLibraryBundleInfo());
                }
                catch (GLException ex2) {
                    if (EGLDrawableFactory.DEBUG) {
                        ex2.printStackTrace();
                    }
                }
                if (null != eglES1DynamicLookupHelper && eglES1DynamicLookupHelper.isLibComplete()) {
                    long dynamicLookupFunction;
                    int n2;
                    if (!b) {
                        if (b = EGLAcc.resetProcAddressTable(eglES1DynamicLookupHelper)) {
                            dynamicLookupFunction = (n = eglES1DynamicLookupHelper.dynamicLookupFunction("eglInitialize"));
                            n2 = 1;
                        }
                        else {
                            dynamicLookupFunction = 0L;
                            n2 = 0;
                        }
                    }
                    else {
                        dynamicLookupFunction = eglES1DynamicLookupHelper.dynamicLookupFunction("eglInitialize");
                        n2 = ((dynamicLookupFunction == n) ? 1 : 0);
                    }
                    if (n2 != 0) {
                        EGLDrawableFactory.eglES1DynamicLookupHelper = eglES1DynamicLookupHelper;
                        final boolean angle2 = isANGLE(EGLDrawableFactory.eglES1DynamicLookupHelper);
                        EGLDrawableFactory.isANGLE |= angle2;
                        if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                            System.err.println("Info: EGLDrawableFactory: EGL ES1 - OK (isANGLE: " + angle2 + ", eglTableReset " + b + ", eglInitialize 0x" + Long.toHexString(dynamicLookupFunction) + ")");
                        }
                    }
                    else if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                        System.err.println("Info: EGLDrawableFactory: EGL ES1 - NOPE (ES1 proc, eglTableReset " + b + ", eglInitialize 0x" + Long.toHexString(dynamicLookupFunction) + ")");
                    }
                }
                else if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                    System.err.println("Info: EGLDrawableFactory: EGL ES1 - NOPE (ES1 lib)");
                }
            }
            if (null == EGLDrawableFactory.eglGLnDynamicLookupHelper) {
                if (!GLProfile.disableOpenGLDesktop) {
                    GLDynamicLookupHelper eglGLnDynamicLookupHelper = null;
                    try {
                        eglGLnDynamicLookupHelper = new GLDynamicLookupHelper(new EGLGLnDynamicLibraryBundleInfo());
                    }
                    catch (GLException ex3) {
                        if (EGLDrawableFactory.DEBUG) {
                            ex3.printStackTrace();
                        }
                    }
                    if (null != eglGLnDynamicLookupHelper && eglGLnDynamicLookupHelper.isLibComplete()) {
                        long n3;
                        int n4;
                        if (!b) {
                            if (b = EGLAcc.resetProcAddressTable(eglGLnDynamicLookupHelper)) {
                                n3 = eglGLnDynamicLookupHelper.dynamicLookupFunction("eglInitialize");
                                n4 = 1;
                            }
                            else {
                                n3 = 0L;
                                n4 = 0;
                            }
                        }
                        else {
                            n3 = eglGLnDynamicLookupHelper.dynamicLookupFunction("eglInitialize");
                            n4 = ((n3 == n) ? 1 : 0);
                        }
                        if (n4 != 0) {
                            EGLDrawableFactory.eglGLnDynamicLookupHelper = eglGLnDynamicLookupHelper;
                            if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                                System.err.println("Info: EGLDrawableFactory: EGL GLn - OK (eglTableReset " + b + ", eglInitialize 0x" + Long.toHexString(n3) + ")");
                            }
                        }
                        else if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                            System.err.println("Info: EGLDrawableFactory: EGL GLn - NOPE (GLn proc, eglTableReset " + b + ", eglInitialize 0x" + Long.toHexString(n3) + ")");
                        }
                    }
                    else if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                        System.err.println("Info: EGLDrawableFactory: EGL GLn - NOPE (GLn lib)");
                    }
                }
                else if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                    System.err.println("Info: EGLDrawableFactory: EGL Gln - disabled!");
                }
            }
            if (null != EGLDrawableFactory.eglES2DynamicLookupHelper || null != EGLDrawableFactory.eglES1DynamicLookupHelper || null != EGLDrawableFactory.eglGLnDynamicLookupHelper) {
                if (EGLDrawableFactory.isANGLE && !GLProfile.enableANGLE) {
                    if (EGLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                        System.err.println("Info: EGLDrawableFactory.init - EGL/ES2 ANGLE disabled");
                    }
                }
                else {
                    if (EGLDrawableFactory.isANGLE && (EGLDrawableFactory.DEBUG || GLProfile.DEBUG)) {
                        System.err.println("Info: EGLDrawableFactory.init - EGL/ES2 ANGLE enabled");
                    }
                    EGLGraphicsConfigurationFactory.registerFactory();
                    EGLDrawableFactory.defaultDevice = EGLDisplayUtil.eglCreateEGLGraphicsDevice(0L, EGLDrawableFactory.defaultConnection, 0);
                    this.sharedResourceImplementation = new SharedResourceImplementation();
                    (this.sharedResourceRunner = new SharedResourceRunner(this.sharedResourceImplementation)).start();
                }
            }
        }
    }
    
    @Override
    protected final boolean isComplete() {
        return null != this.sharedResourceImplementation;
    }
    
    @Override
    protected final void shutdownImpl() {
        if (EGLDrawableFactory.DEBUG) {
            System.err.println("EGLDrawableFactory.shutdown");
        }
        if (null != this.sharedResourceRunner) {
            this.sharedResourceRunner.stop();
            this.sharedResourceRunner = null;
        }
        if (null != this.sharedResourceImplementation) {
            this.sharedResourceImplementation.clear();
            this.sharedResourceImplementation = null;
        }
        if (null != EGLDrawableFactory.defaultDevice) {
            EGLDrawableFactory.defaultDevice.close();
            EGLDrawableFactory.defaultDevice = null;
        }
        if (null != EGLDrawableFactory.eglES1DynamicLookupHelper) {
            EGLDrawableFactory.eglES1DynamicLookupHelper = null;
        }
        if (null != EGLDrawableFactory.eglES2DynamicLookupHelper) {
            EGLDrawableFactory.eglES2DynamicLookupHelper = null;
        }
        if (null != EGLDrawableFactory.eglGLnDynamicLookupHelper) {
            EGLDrawableFactory.eglGLnDynamicLookupHelper = null;
        }
        EGLGraphicsConfigurationFactory.unregisterFactory();
        EGLDisplayUtil.shutdown(EGLDrawableFactory.DEBUG);
    }
    
    private void dumpMap() {
        synchronized (this.sharedResourceImplementation) {
            final Map<String, SharedResourceRunner.Resource> sharedMap = this.sharedResourceImplementation.getSharedMap();
            System.err.println("EGLDrawableFactory.MapGLVersion.map " + sharedMap.size());
            int n = 0;
            for (final String s : sharedMap.keySet()) {
                final SharedResource sharedResource = sharedMap.get(s);
                System.err.println("EGLDrawableFactory.MapGLVersion.map[" + n + "] " + s + " -> " + sharedResource.getDevice() + ", avail " + sharedResource.isAvailable + ", " + "es1 [avail " + sharedResource.isAvailableES1 + ", quirks " + sharedResource.rendererQuirksES1 + ", ctp " + EGLContext.getGLVersion(1, 0, sharedResource.ctpES1, null) + "], " + "es2 [avail " + sharedResource.isAvailableES2 + ", quirks " + sharedResource.rendererQuirksES2 + ", ctp " + EGLContext.getGLVersion(2, 0, sharedResource.ctpES2, null) + "], " + "es3 [avail " + sharedResource.isAvailableES3 + ", quirks " + sharedResource.rendererQuirksES3 + ", ctp " + EGLContext.getGLVersion(2, 0, sharedResource.ctpES3, null) + "], " + "gln [avail " + sharedResource.isAvailableGLn + ", quirks " + sharedResource.rendererQuirksGLn + ", ctp " + EGLContext.getGLVersion(3, 0, sharedResource.ctpGLn, null) + "]");
                ++n;
            }
        }
    }
    
    public final boolean hasDefaultDeviceKHRCreateContext() {
        return EGLDrawableFactory.defaultDeviceEGLFeatures.hasKHRCreateContext;
    }
    
    @Override
    public final boolean hasOpenGLDesktopSupport() {
        return null != EGLDrawableFactory.eglGLnDynamicLookupHelper && EGLDrawableFactory.defaultDeviceEGLFeatures.hasGLAPI && EGLDrawableFactory.defaultDeviceEGLFeatures.hasKHRCreateContext;
    }
    
    @Override
    public final boolean hasOpenGLESSupport() {
        return true;
    }
    
    @Override
    public final boolean hasMajorMinorCreateContextARB() {
        return this.hasDefaultDeviceKHRCreateContext();
    }
    
    @Override
    public final AbstractGraphicsDevice getDefaultDevice() {
        return EGLDrawableFactory.defaultDevice;
    }
    
    @Override
    public final boolean getIsDeviceCompatible(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return null != this.sharedResourceImplementation;
    }
    
    private static List<GLCapabilitiesImmutable> getAvailableEGLConfigs(final EGLGraphicsDevice eglGraphicsDevice, final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        if (!EGL.eglGetConfigs(eglGraphicsDevice.getHandle(), null, 0, directIntBuffer)) {
            throw new GLException("EGLDrawableFactory.getAvailableEGLConfigs: Get maxConfigs (eglGetConfigs) call failed, error " + EGLContext.toHexString(EGL.eglGetError()));
        }
        if (0 < directIntBuffer.get(0)) {
            final PointerBuffer allocateDirect = PointerBuffer.allocateDirect(directIntBuffer.get(0));
            final IntBuffer glCapabilities2AttribList = EGLGraphicsConfiguration.GLCapabilities2AttribList(glCapabilitiesImmutable);
            final int exclusiveWinAttributeBits = GLGraphicsConfigurationUtil.getExclusiveWinAttributeBits(glCapabilitiesImmutable);
            if (EGL.eglChooseConfig(eglGraphicsDevice.getHandle(), glCapabilities2AttribList, allocateDirect, allocateDirect.capacity(), directIntBuffer) && directIntBuffer.get(0) > 0) {
                return EGLGraphicsConfigurationFactory.eglConfigs2GLCaps(eglGraphicsDevice, glCapabilitiesImmutable.getGLProfile(), allocateDirect, directIntBuffer.get(0), exclusiveWinAttributeBits, false, false);
            }
        }
        return new ArrayList<GLCapabilitiesImmutable>(0);
    }
    
    static void dumpEGLInfo(final String s, final long n) {
        System.err.println(s + "EGL vendor " + EGL.eglQueryString(n, 12371) + ", version [client " + EGL.eglQueryString(0L, 12372) + ", server " + EGL.eglQueryString(n, 12372) + "], clientAPIs " + EGL.eglQueryString(n, 12429));
    }
    
    @Override
    protected final SharedResource getOrCreateSharedResourceImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return (SharedResource)this.sharedResourceRunner.getOrCreateShared(abstractGraphicsDevice);
    }
    
    @Override
    protected final Thread getSharedResourceThread() {
        return this.sharedResourceRunner.start();
    }
    
    public final boolean isANGLE() {
        return EGLDrawableFactory.isANGLE;
    }
    
    @Override
    public final GLDynamicLookupHelper getGLDynamicLookupHelper(final int n, final int n2) {
        GLDynamicLookupHelper glDynamicLookupHelper;
        if (EGLContext.isGLES2ES3(n, n2)) {
            glDynamicLookupHelper = EGLDrawableFactory.eglES2DynamicLookupHelper;
        }
        else if (EGLContext.isGLES1(n, n2)) {
            glDynamicLookupHelper = EGLDrawableFactory.eglES1DynamicLookupHelper;
        }
        else {
            if (!EGLContext.isGLDesktop(n2)) {
                throw new IllegalArgumentException("neither GLES1, GLES2, GLES3 nor desktop GL has been specified: " + n + " (" + EGLContext.getGLProfile(new StringBuilder(), n2).toString());
            }
            glDynamicLookupHelper = EGLDrawableFactory.eglGLnDynamicLookupHelper;
        }
        if (EGLDrawableFactory.DEBUG_SHAREDCTX && null == glDynamicLookupHelper) {
            System.err.println("EGLDrawableFactory.getGLDynamicLookupHelper: NULL for profile " + n + " (" + EGLContext.getGLProfile(new StringBuilder(), n2).toString());
        }
        return glDynamicLookupHelper;
    }
    
    @Override
    protected List<GLCapabilitiesImmutable> getAvailableCapabilitiesImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        if (null == this.sharedResourceImplementation) {
            return new ArrayList<GLCapabilitiesImmutable>();
        }
        return EGLGraphicsConfigurationFactory.getAvailableCapabilities(this, abstractGraphicsDevice);
    }
    
    @Override
    protected GLDrawableImpl createOnscreenDrawableImpl(final NativeSurface nativeSurface) {
        if (nativeSurface == null) {
            throw new IllegalArgumentException("Null target");
        }
        return new EGLDrawable(this, EGLSurface.get(nativeSurface));
    }
    
    @Override
    protected GLDrawableImpl createOffscreenDrawableImpl(final NativeSurface nativeSurface) {
        if (nativeSurface == null) {
            throw new IllegalArgumentException("Null target");
        }
        if (!((GLCapabilitiesImmutable)nativeSurface.getGraphicsConfiguration().getChosenCapabilities()).isPBuffer()) {
            throw new GLException("Non pbuffer not yet implemented");
        }
        return new EGLDrawable(this, EGLSurface.get(nativeSurface));
    }
    
    @Override
    public boolean canCreateGLPbuffer(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile) {
        return true;
    }
    
    private final EGLGraphicsConfiguration evalConfig(final boolean[] array, final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser glCapabilitiesChooser) {
        EGLGraphicsDevice eglCreateEGLGraphicsDevice;
        if (b || !(abstractGraphicsDevice instanceof EGLGraphicsDevice)) {
            eglCreateEGLGraphicsDevice = EGLDisplayUtil.eglCreateEGLGraphicsDevice(abstractGraphicsDevice);
            eglCreateEGLGraphicsDevice.open();
            array[0] = true;
        }
        else {
            eglCreateEGLGraphicsDevice = (EGLGraphicsDevice)abstractGraphicsDevice;
            array[0] = false;
        }
        final DefaultGraphicsScreen defaultGraphicsScreen = new DefaultGraphicsScreen(eglCreateEGLGraphicsDevice, 0);
        final EGLGraphicsConfiguration chooseGraphicsConfigurationStatic = EGLGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable2, glCapabilitiesChooser, defaultGraphicsScreen, 0, false);
        if (null == chooseGraphicsConfigurationStatic) {
            throw new GLException("Choosing GraphicsConfiguration failed w/ " + glCapabilitiesImmutable + " on " + defaultGraphicsScreen);
        }
        return chooseGraphicsConfigurationStatic;
    }
    
    @Override
    protected final EGLSurface createMutableSurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        final boolean[] array = { false };
        return EGLSurface.createWrapped(this.evalConfig(array, abstractGraphicsDevice, b, glCapabilitiesImmutable, glCapabilitiesImmutable2, glCapabilitiesChooser), 0L, upstreamSurfaceHook, array[0]);
    }
    
    @Override
    public final EGLSurface createDummySurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, GLCapabilitiesImmutable fixGLPBufferGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        fixGLPBufferGLCapabilities = GLGraphicsConfigurationUtil.fixGLPBufferGLCapabilities(fixGLPBufferGLCapabilities);
        return this.createMutableSurfaceImpl(abstractGraphicsDevice, b, fixGLPBufferGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, new EGLDummyUpstreamSurfaceHook(n, n2));
    }
    
    @Override
    public final EGLSurface createSurfacelessImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, GLCapabilitiesImmutable fixOnscreenGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        fixOnscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOnscreenGLCapabilities(fixOnscreenGLCapabilities);
        final boolean[] array = { false };
        return EGLSurface.createSurfaceless(this.evalConfig(array, abstractGraphicsDevice, b, fixOnscreenGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser), new GenericUpstreamSurfacelessHook(n, n2), array[0]);
    }
    
    protected static MutableSurface createPBufferSurfaceImpl(final MutableSurface mutableSurface, final boolean b) {
        return null;
    }
    
    protected static long createPBufferSurfaceImpl(final EGLGraphicsConfiguration eglGraphicsConfiguration, final int n, final int n2, final boolean b) {
        final EGLGraphicsDevice eglGraphicsDevice = (EGLGraphicsDevice)eglGraphicsConfiguration.getScreen().getDevice();
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)eglGraphicsConfiguration.getChosenCapabilities();
        int n3;
        if (b) {
            n3 = ((glCapabilitiesImmutable.getAlphaBits() > 0) ? 12382 : 12381);
        }
        else {
            n3 = 12380;
        }
        if (EGLDrawableFactory.DEBUG) {
            System.out.println("Pbuffer config: " + eglGraphicsConfiguration);
        }
        final long eglCreatePbufferSurface = EGL.eglCreatePbufferSurface(eglGraphicsDevice.getHandle(), eglGraphicsConfiguration.getNativeConfig(), EGLGraphicsConfiguration.CreatePBufferSurfaceAttribList(n, n2, n3));
        if (0L == eglCreatePbufferSurface) {
            throw new GLException("Creation of window surface (eglCreatePbufferSurface) failed, dim " + n + "x" + n2 + ", " + eglGraphicsDevice + ", " + eglGraphicsConfiguration + ", error 0x" + Integer.toHexString(EGL.eglGetError()));
        }
        if (EGLDrawableFactory.DEBUG) {
            System.err.println("PBuffer setSurface result: eglSurface 0x" + Long.toHexString(eglCreatePbufferSurface));
        }
        return eglCreatePbufferSurface;
    }
    
    @Override
    protected EGLSurface createProxySurfaceImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final long n2, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        final EGLGraphicsDevice eglCreateEGLGraphicsDevice = EGLDisplayUtil.eglCreateEGLGraphicsDevice(abstractGraphicsDevice);
        eglCreateEGLGraphicsDevice.open();
        return EGLSurface.createWrapped(EGLGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable, glCapabilitiesChooser, new DefaultGraphicsScreen(eglCreateEGLGraphicsDevice, n), 0, false), n2, upstreamSurfaceHook, true);
    }
    
    @Override
    protected GLContext createExternalGLContextImpl() {
        return new EGLExternalContext(DefaultGraphicsScreen.createDefault(NativeWindowFactory.TYPE_EGL));
    }
    
    @Override
    public boolean canCreateExternalGLDrawable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return false;
    }
    
    @Override
    protected GLDrawable createExternalGLDrawableImpl() {
        throw new GLException("Not yet implemented");
    }
    
    static {
        DEBUG = GLDrawableFactoryImpl.DEBUG;
        DEBUG_SHAREDCTX = (EGLDrawableFactory.DEBUG || GLContext.DEBUG);
        Debug.initSingleton();
        EGLDrawableFactory.eglDynamicLookupHelperInit = false;
        EGLDrawableFactory.eglES1DynamicLookupHelper = null;
        EGLDrawableFactory.eglES2DynamicLookupHelper = null;
        EGLDrawableFactory.eglGLnDynamicLookupHelper = null;
        EGLDrawableFactory.isANGLE = false;
        EGLDrawableFactory.hasX11 = false;
        EGLDrawableFactory.defaultConnection = null;
        EGLDrawableFactory.defaultDevice = null;
        EGLDrawableFactory.defaultDeviceEGLFeatures = null;
        EGLDrawableFactory.defaultSharedResource = null;
    }
    
    private static class EGLFeatures
    {
        public final String vendor;
        public final VersionNumber version;
        public final boolean hasGLAPI;
        public final boolean hasKHRCreateContext;
        public final boolean hasKHRSurfaceless;
        
        public EGLFeatures(final EGLGraphicsDevice eglGraphicsDevice) {
            final long handle = eglGraphicsDevice.getHandle();
            this.vendor = EGL.eglQueryString(handle, 12371);
            if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("EGLFeatures on device " + eglGraphicsDevice + ", vendor " + this.vendor);
            }
            this.version = eglGraphicsDevice.getEGLVersion();
            final boolean b = this.version.compareTo(GLContext.Version1_4) >= 0;
            final boolean b2 = this.version.compareTo(GLContext.Version1_5) >= 0;
            boolean equals = false;
            final String eglQueryString = EGL.eglQueryString(handle, 12429);
            if (b) {
                final String[] split = eglQueryString.split("\\s");
                for (int n = split.length - 1; !equals && n >= 0; equals = split[n].equals("OpenGL"), --n) {}
            }
            this.hasGLAPI = equals;
            if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("  Client APIs: '" + eglQueryString + "'; has EGL 1.4 " + b + " -> has OpenGL " + this.hasGLAPI);
            }
            final String string = EGLContext.getPlatformExtensionsStringImpl(eglGraphicsDevice).toString();
            if (b2) {
                this.hasKHRCreateContext = true;
                this.hasKHRSurfaceless = true;
            }
            else {
                if (b) {
                    this.hasKHRCreateContext = string.contains("EGL_KHR_create_context");
                }
                else {
                    this.hasKHRCreateContext = false;
                }
                this.hasKHRSurfaceless = string.contains("EGL_KHR_surfaceless_context");
            }
            if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("  Extensions: " + string);
                System.err.println("  KHR_create_context: " + this.hasKHRCreateContext);
                System.err.println("  KHR_surfaceless_context: " + this.hasKHRSurfaceless);
            }
        }
        
        @Override
        public final String toString() {
            return "EGLFeatures[vendor " + this.vendor + ", version " + this.version + ", has[GL-API " + this.hasGLAPI + ", KHR[CreateContext " + this.hasKHRCreateContext + ", Surfaceless " + this.hasKHRSurfaceless + "]]]";
        }
    }
    
    static class EGLAcc extends EGL
    {
        protected static boolean resetProcAddressTable(final DynamicLookupHelper dynamicLookupHelper) {
            return EGL.resetProcAddressTable(dynamicLookupHelper);
        }
    }
    
    static class SharedResource implements SharedResourceRunner.Resource
    {
        private EGLGraphicsDevice device;
        final boolean isAvailable;
        final boolean isAvailableES1;
        final boolean isAvailableES2;
        final boolean isAvailableES3;
        final boolean isAvailableGLn;
        final GLRendererQuirks rendererQuirksES1;
        final GLRendererQuirks rendererQuirksES2;
        final GLRendererQuirks rendererQuirksES3;
        final GLRendererQuirks rendererQuirksGLn;
        final int ctpES1;
        final int ctpES2;
        final int ctpES3;
        final int ctpGLn;
        
        SharedResource(final EGLGraphicsDevice device, final boolean isAvailableES1, final GLRendererQuirks rendererQuirksES1, final int ctpES1, final boolean isAvailableES2, final GLRendererQuirks rendererQuirksES2, final int ctpES2, final boolean isAvailableES3, final GLRendererQuirks rendererQuirksES3, final int ctpES3, final boolean isAvailableGLn, final GLRendererQuirks rendererQuirksGLn, final int ctpGLn) {
            this.device = device;
            this.isAvailable = (isAvailableES1 || isAvailableES2 || isAvailableES3 || isAvailableGLn);
            this.isAvailableES1 = isAvailableES1;
            this.rendererQuirksES1 = rendererQuirksES1;
            this.ctpES1 = ctpES1;
            this.isAvailableES2 = isAvailableES2;
            this.rendererQuirksES2 = rendererQuirksES2;
            this.ctpES2 = ctpES2;
            this.isAvailableES3 = isAvailableES3;
            this.rendererQuirksES3 = rendererQuirksES3;
            this.ctpES3 = ctpES3;
            this.isAvailableGLn = isAvailableGLn;
            this.rendererQuirksGLn = rendererQuirksGLn;
            this.ctpGLn = ctpGLn;
        }
        
        @Override
        public final boolean isAvailable() {
            return this.isAvailable;
        }
        
        @Override
        public final EGLGraphicsDevice getDevice() {
            return this.device;
        }
        
        @Override
        public AbstractGraphicsScreen getScreen() {
            return null;
        }
        
        @Override
        public GLDrawableImpl getDrawable() {
            return null;
        }
        
        @Override
        public GLContextImpl getContext() {
            return null;
        }
        
        @Override
        public GLRendererQuirks getRendererQuirks(final GLProfile glProfile) {
            if (null == glProfile) {
                if (null != this.rendererQuirksES3) {
                    return this.rendererQuirksES3;
                }
                if (null != this.rendererQuirksES2) {
                    return this.rendererQuirksES2;
                }
                if (null != this.rendererQuirksES1) {
                    return this.rendererQuirksES1;
                }
                return this.rendererQuirksGLn;
            }
            else {
                if (!glProfile.isGLES()) {
                    return this.rendererQuirksGLn;
                }
                if (glProfile.isGLES1()) {
                    return this.rendererQuirksES1;
                }
                if (glProfile.isGLES2()) {
                    return this.rendererQuirksES2;
                }
                return this.rendererQuirksES3;
            }
        }
    }
    
    class SharedResourceImplementation extends SharedResourceRunner.AImplementation
    {
        @Override
        public boolean isDeviceSupported(final AbstractGraphicsDevice abstractGraphicsDevice) {
            return null != EGLDrawableFactory.this.sharedResourceImplementation;
        }
        
        @Override
        public SharedResourceRunner.Resource createSharedResource(final AbstractGraphicsDevice abstractGraphicsDevice) {
            abstractGraphicsDevice.lock();
            try {
                return this.createEGLSharedResourceImpl(abstractGraphicsDevice);
            }
            catch (Throwable t) {
                throw new GLException("EGLGLXDrawableFactory - Could not initialize shared resources for " + abstractGraphicsDevice, t);
            }
            finally {
                abstractGraphicsDevice.unlock();
            }
        }
        
        private SharedResource createEGLSharedResourceImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
            if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("EGLDrawableFactory.MapGLVersions: device " + abstractGraphicsDevice);
            }
            boolean b;
            if (0L == EGLDrawableFactory.defaultDevice.getHandle()) {
                b = true;
                EGLDrawableFactory.defaultDevice.open();
                EGLDrawableFactory.defaultDeviceEGLFeatures = new EGLFeatures(EGLDrawableFactory.defaultDevice);
                if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                    System.err.println("EGLDrawableFactory.MapGLVersions: defaultDevice " + EGLDrawableFactory.defaultDevice);
                    System.err.println("EGLDrawableFactory.MapGLVersions: defaultDevice EGLFeatures " + EGLDrawableFactory.defaultDeviceEGLFeatures);
                }
                boolean b2;
                boolean b3;
                if (EGLDrawableFactory.defaultDeviceEGLFeatures.vendor.contains("NVIDIA")) {
                    b2 = true;
                    b3 = false;
                }
                else {
                    b2 = false;
                    b3 = (0L == EGL.eglGetDisplay(0L));
                }
                if (b2 || b3) {
                    GLRendererQuirks.addStickyDeviceQuirk(abstractGraphicsDevice, 16);
                    EGLDisplayUtil.setSingletonEGLDisplayOnly(true);
                    if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                        if (b2) {
                            System.err.println("Quirk: " + GLRendererQuirks.toString(16) + ": cause: Vendor: " + EGLDrawableFactory.defaultDeviceEGLFeatures);
                        }
                        else if (b3) {
                            System.err.println("Quirk: " + GLRendererQuirks.toString(16) + ": cause: Second eglGetDisplay(EGL_DEFAULT_DISPLAY) failed");
                        }
                    }
                }
            }
            else {
                b = false;
                if (null == EGLDrawableFactory.defaultSharedResource) {
                    throw new InternalError("XXX: defaultDevice " + EGLDrawableFactory.defaultDevice + ", adevice " + abstractGraphicsDevice);
                }
            }
            final boolean[] array = { false };
            final GLRendererQuirks[] array2 = { null };
            final GLRendererQuirks[] array3 = { null };
            final GLRendererQuirks[] array4 = { null };
            final GLRendererQuirks[] array5 = { null };
            final int[] array6 = { 0 };
            final int[] array7 = { 0 };
            final int[] array8 = { 0 };
            final int[] array9 = { 0 };
            final boolean[] array10 = { false };
            final boolean[] array11 = { false };
            final boolean[] array12 = { false };
            final boolean[] array13 = { false };
            final GLContextImpl.MappedGLVersionListener mappedGLVersionListener = new GLContextImpl.MappedGLVersionListener() {
                @Override
                public void glVersionMapped(final GLContextImpl.MappedGLVersion mappedGLVersion) {
                    if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                        System.err.println("EGLDrawableFactory.MapGLVersions: Mapped: " + mappedGLVersion);
                    }
                    if (EGLContext.isGLES2ES3(mappedGLVersion.ctxVersion.getMajor(), mappedGLVersion.ctxOptions)) {
                        if (mappedGLVersion.ctxVersion.getMajor() == 3) {
                            array12[0] = true;
                            array4[0] = mappedGLVersion.quirks;
                            array8[0] = mappedGLVersion.ctxOptions;
                        }
                        array11[0] = true;
                        array3[0] = mappedGLVersion.quirks;
                        array7[0] = mappedGLVersion.ctxOptions;
                    }
                    else if (EGLContext.isGLES1(mappedGLVersion.ctxVersion.getMajor(), mappedGLVersion.ctxOptions)) {
                        array10[0] = true;
                        array2[0] = mappedGLVersion.quirks;
                        array6[0] = mappedGLVersion.ctxOptions;
                    }
                    else if (EGLContext.isGLDesktop(mappedGLVersion.ctxOptions)) {
                        array13[0] = true;
                        array5[0] = mappedGLVersion.quirks;
                        array9[0] = mappedGLVersion.ctxOptions;
                    }
                }
            };
            final EGLGraphicsDevice[] array14 = { null };
            EGLContext.setMappedGLVersionListener(mappedGLVersionListener);
            boolean mapAvailableEGLESConfig;
            try {
                mapAvailableEGLESConfig = this.mapAvailableEGLESConfig(abstractGraphicsDevice, array, array14);
            }
            finally {
                EGLContext.setMappedGLVersionListener(null);
            }
            SharedResource access$400;
            if (array[0]) {
                EGLContext.remapAvailableGLVersions(EGLDrawableFactory.defaultDevice, abstractGraphicsDevice);
                access$400 = EGLDrawableFactory.defaultSharedResource;
            }
            else {
                if (EGLDrawableFactory.hasX11) {
                    this.handleDontCloseX11DisplayQuirk(array2[0]);
                    this.handleDontCloseX11DisplayQuirk(array5[0]);
                    this.handleDontCloseX11DisplayQuirk(array4[0]);
                    this.handleDontCloseX11DisplayQuirk(array3[0]);
                }
                access$400 = new SharedResource(array14[0], array10[0], array2[0], array6[0], array11[0], array3[0], array7[0], array12[0], array4[0], array8[0], array13[0], array5[0], array9[0]);
                if (b) {
                    EGLDrawableFactory.defaultSharedResource = access$400;
                }
            }
            if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("EGLDrawableFactory.MapGLVersions: mapSuccess " + mapAvailableEGLESConfig + ", mappedToDefaultDevice " + array[0]);
                System.err.println("EGLDrawableFactory.MapGLVersions: defDevice  : " + EGLDrawableFactory.defaultDevice);
                System.err.println("EGLDrawableFactory.MapGLVersions: adevice    : " + abstractGraphicsDevice);
                System.err.println("EGLDrawableFactory.MapGLVersions: eglDevice  : " + access$400.device);
                System.err.println("EGLDrawableFactory.MapGLVersions: context ES1: " + access$400.isAvailableES1 + ", quirks " + access$400.rendererQuirksES1);
                System.err.println("EGLDrawableFactory.MapGLVersions: context ES2: " + access$400.isAvailableES2 + ", quirks " + access$400.rendererQuirksES2);
                System.err.println("EGLDrawableFactory.MapGLVersions: context ES3: " + access$400.isAvailableES3 + ", quirks " + access$400.rendererQuirksES3);
                System.err.println("EGLDrawableFactory.MapGLVersions: context GLn: " + access$400.isAvailableGLn + ", quirks " + access$400.rendererQuirksGLn);
                EGLDrawableFactory.this.dumpMap();
            }
            return access$400;
        }
        
        private void handleDontCloseX11DisplayQuirk(final GLRendererQuirks glRendererQuirks) {
            if (null != glRendererQuirks && glRendererQuirks.exist(8)) {
                X11Util.markAllDisplaysUnclosable();
            }
        }
        
        private boolean mapAvailableEGLESConfig(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array, final EGLGraphicsDevice[] array2) {
            final String glProfile = EGLContext.getGLProfile(2, 0, 8);
            if (!GLProfile.isAvailable(abstractGraphicsDevice, glProfile)) {
                if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                    System.err.println("EGLDrawableFactory.MapGLVersions: " + glProfile + " n/a on " + abstractGraphicsDevice);
                }
                return false;
            }
            final GLProfile value = GLProfile.get(abstractGraphicsDevice, glProfile);
            final GLDrawableFactoryImpl glDrawableFactoryImpl = (GLDrawableFactoryImpl)GLDrawableFactory.getDesktopFactory();
            final GLCapabilities glCapabilities = new GLCapabilities(value);
            glCapabilities.setRedBits(5);
            glCapabilities.setGreenBits(5);
            glCapabilities.setBlueBits(5);
            glCapabilities.setAlphaBits(0);
            glCapabilities.setDoubleBuffered(false);
            final GLCapabilitiesImmutable fixGLPBufferGLCapabilities = GLGraphicsConfigurationUtil.fixGLPBufferGLCapabilities(glCapabilities);
            final boolean b = getAvailableEGLConfigs(EGLDrawableFactory.defaultDevice, fixGLPBufferGLCapabilities).size() > 0;
            final boolean b2 = abstractGraphicsDevice == EGLDrawableFactory.defaultDevice;
            array[0] = (!b2 && null != EGLDrawableFactory.defaultSharedResource && EGLDrawableFactory.defaultSharedResource.isAvailable && EGLDrawableFactory.defaultConnection.equals(abstractGraphicsDevice.getConnection()));
            if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("EGLDrawableFactory.MapGLVersions: " + glProfile + " ( " + 2 + " ), " + "mapsADeviceToDefaultDevice " + array[0] + " (useDefaultDevice " + b2 + ", defaultDeviceHasPBuffer " + b + ", hasDesktopFactory " + (null != glDrawableFactoryImpl) + ", isEGLGraphicsDevice " + (abstractGraphicsDevice instanceof EGLGraphicsDevice) + ")");
            }
            if (array[0]) {
                return true;
            }
            final boolean existStickyDeviceQuirk = GLRendererQuirks.existStickyDeviceQuirk(EGLDrawableFactory.defaultDevice, 22);
            boolean b3 = false;
            boolean b4;
            if (EGLDrawableFactory.defaultDeviceEGLFeatures.hasKHRSurfaceless && !existStickyDeviceQuirk) {
                b4 = true;
                final EGLSurface surfacelessImpl = EGLDrawableFactory.this.createSurfacelessImpl(b2 ? EGLDrawableFactory.defaultDevice : abstractGraphicsDevice, false, glCapabilities, glCapabilities, null, 64, 64);
                array2[0] = (EGLGraphicsDevice)surfacelessImpl.getGraphicsConfiguration().getScreen().getDevice();
                if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                    System.err.println("EGLDrawableFactory-MapGLVersions.0: " + array2[0]);
                }
                EGLDrawable eglDrawable = null;
                GLContextImpl glContextImpl = null;
                boolean b5 = false;
                try {
                    eglDrawable = (EGLDrawable)EGLDrawableFactory.this.createOnscreenDrawableImpl(surfacelessImpl);
                    eglDrawable.setRealized(true);
                    glContextImpl = (EGLContext)eglDrawable.createContext(null);
                    if (null == glContextImpl) {
                        throw new GLException("Couldn't create shared context for drawable: " + eglDrawable);
                    }
                    if (0 != glContextImpl.makeCurrent()) {
                        if (null != glContextImpl.getGL().glGetString(7938)) {
                            b3 = true;
                        }
                        else {
                            EGLDrawableFactory.this.setNoSurfacelessCtxQuirk(glContextImpl);
                        }
                    }
                    else if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                        System.err.println("EGLDrawableFactory-MapGLVersions.0: NOT_CURRENT: " + array2[0] + ", " + glContextImpl);
                    }
                }
                catch (Throwable t) {
                    b5 = true;
                    if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                        System.err.println("EGLDrawableFactory-MapGLVersions.0: INFO: context create/makeCurrent failed");
                        t.printStackTrace();
                    }
                }
                finally {
                    if (null != glContextImpl) {
                        try {
                            glContextImpl.destroy();
                        }
                        catch (GLException ex) {
                            if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                                System.err.println("EGLDrawableFactory-MapGLVersions.0: INFO: destroy caught exception:");
                                ex.printStackTrace();
                            }
                        }
                    }
                    if (null != eglDrawable) {
                        eglDrawable.setRealized(false);
                    }
                    if (null != surfacelessImpl) {
                        surfacelessImpl.destroyNotify();
                    }
                    if ((b3 || b5) && EGLDrawableFactory.defaultDevice != array2[0] && null != array2[0]) {
                        array2[0].close();
                    }
                }
                if (b3) {
                    return true;
                }
            }
            else {
                b4 = false;
            }
            EGLFeatures access$300 = null;
            NativeSurface nativeSurface = null;
            EGLDrawable eglDrawable2 = null;
            GLDrawable glDrawable = null;
            GLContextImpl glContextImpl2 = null;
            NativeSurface dummySurface = null;
            NativeSurface dummySurfaceImpl = null;
            try {
                if (b2 && b) {
                    array2[0] = EGLDrawableFactory.defaultDevice;
                    access$300 = EGLDrawableFactory.defaultDeviceEGLFeatures;
                    if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                        System.err.println("EGLDrawableFactory-MapGLVersions.1: " + array2[0]);
                        System.err.println("EGLDrawableFactory-MapGLVersions.1: " + access$300);
                    }
                    dummySurfaceImpl = EGLDrawableFactory.this.createDummySurfaceImpl(array2[0], false, fixGLPBufferGLCapabilities, fixGLPBufferGLCapabilities, null, 64, 64);
                    if (null != dummySurfaceImpl) {
                        ((ProxySurface)dummySurfaceImpl).createNotify();
                        nativeSurface = dummySurfaceImpl;
                    }
                }
                else if (abstractGraphicsDevice != EGLDrawableFactory.defaultDevice) {
                    dummySurface = glDrawableFactoryImpl.createDummySurface(abstractGraphicsDevice, glCapabilities, null, 64, 64);
                    if (null != dummySurface) {
                        ((ProxySurface)dummySurface).createNotify();
                        (array2[0] = EGLDisplayUtil.eglCreateEGLGraphicsDevice(dummySurface)).open();
                        access$300 = new EGLFeatures(array2[0]);
                        if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                            System.err.println("EGLDrawableFactory-MapGLVersions.2: " + array2[0]);
                            System.err.println("EGLDrawableFactory-MapGLVersions.2: " + access$300);
                        }
                        nativeSurface = dummySurface;
                    }
                }
                if (null != nativeSurface) {
                    eglDrawable2 = (EGLDrawable)EGLDrawableFactory.this.createOnscreenDrawableImpl(nativeSurface);
                    eglDrawable2.setRealized(true);
                    glContextImpl2 = (EGLContext)eglDrawable2.createContext(null);
                    if (null == glContextImpl2) {
                        throw new GLException("Couldn't create shared context for drawable: " + eglDrawable2);
                    }
                    if (0 != glContextImpl2.makeCurrent()) {
                        if (null != glContextImpl2.getGL().glGetString(7938)) {
                            b3 = true;
                            if (!b4 && access$300.hasKHRSurfaceless && (glContextImpl2.isGLES() || glContextImpl2.getGLVersionNumber().compareTo(GLContext.Version3_0) >= 0)) {
                                if (EGLDrawableFactory.this.probeSurfacelessCtx(glContextImpl2, false)) {
                                    glDrawable = glContextImpl2.getGLDrawable();
                                }
                            }
                            else {
                                EGLDrawableFactory.this.setNoSurfacelessCtxQuirk(glContextImpl2);
                            }
                        }
                        else if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                            System.err.println("EGLDrawableFactory-MapGLVersions.12: NULL VERSION: " + array2[0] + ", " + glContextImpl2.getGLVersion());
                        }
                    }
                    else if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                        System.err.println("EGLDrawableFactory-MapGLVersions.12: NOT_CURRENT: " + array2[0] + ", " + glContextImpl2);
                    }
                }
            }
            catch (Throwable t2) {
                if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                    System.err.println("EGLDrawableFactory-MapGLVersions.12: INFO: context create/makeCurrent failed");
                    t2.printStackTrace();
                }
                b3 = false;
            }
            finally {
                if (null != glContextImpl2) {
                    try {
                        glContextImpl2.destroy();
                    }
                    catch (GLException ex2) {
                        if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                            System.err.println("EGLDrawableFactory-MapGLVersions.12: INFO: destroy caught exception:");
                            ex2.printStackTrace();
                        }
                    }
                }
                if (null != glDrawable) {
                    glDrawable.setRealized(false);
                }
                if (null != eglDrawable2) {
                    eglDrawable2.setRealized(false);
                }
                if (null != dummySurfaceImpl) {
                    ((ProxySurface)dummySurfaceImpl).destroyNotify();
                }
                if (EGLDrawableFactory.defaultDevice != array2[0] && null != array2[0]) {
                    array2[0].close();
                }
                if (null != dummySurface) {
                    ((ProxySurface)dummySurface).destroyNotify();
                }
            }
            return b3;
        }
        
        @Override
        public void releaseSharedResource(final SharedResourceRunner.Resource resource) {
            final SharedResource sharedResource = (SharedResource)resource;
            if (EGLDrawableFactory.DEBUG_SHAREDCTX) {
                System.err.println("Shutdown Shared:");
                System.err.println("Device  : " + sharedResource.device);
                ExceptionUtils.dumpStack(System.err);
            }
            if (null != sharedResource.device) {
                sharedResource.device.close();
                sharedResource.device = null;
            }
        }
    }
}
