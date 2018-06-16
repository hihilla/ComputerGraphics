// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import com.jogamp.common.util.InterruptSource;
import com.jogamp.common.util.PropertyAccess;
import jogamp.nativewindow.Debug;
import java.util.ArrayList;
import jogamp.nativewindow.macosx.OSXUtil;
import jogamp.nativewindow.windows.GDIUtil;
import jogamp.nativewindow.x11.X11Lib;
import com.jogamp.nativewindow.util.PointImmutable;
import jogamp.nativewindow.WrappedWindow;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.nativewindow.macosx.MacOSXGraphicsDevice;
import com.jogamp.nativewindow.windows.WindowsGraphicsDevice;
import jogamp.nativewindow.x11.X11Util;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import jogamp.nativewindow.ResourceToolkitLock;
import jogamp.nativewindow.NativeWindowFactoryImpl;
import java.util.Collections;
import java.util.HashMap;
import java.lang.reflect.Method;
import com.jogamp.common.os.Platform;
import com.jogamp.common.util.ReflectionUtil;
import jogamp.common.os.PlatformPropsImpl;
import java.security.AccessController;
import java.io.File;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.Map;

public abstract class NativeWindowFactory
{
    protected static final boolean DEBUG;
    public static final String TYPE_EGL;
    public static final String TYPE_WINDOWS;
    public static final String TYPE_X11;
    public static final String TYPE_BCM_VC_IV;
    public static final String TYPE_ANDROID;
    public static final String TYPE_MACOSX;
    public static final String TYPE_AWT;
    public static final String TYPE_DEFAULT;
    private static final String nativeWindowingTypePure;
    private static final String nativeWindowingTypeCustom;
    private static NativeWindowFactory defaultFactory;
    private static Map<Class<?>, NativeWindowFactory> registeredFactories;
    private static Class<?> nativeWindowClass;
    private static boolean isAWTAvailable;
    private static final String JAWTUtilClassName = "jogamp.nativewindow.jawt.JAWTUtil";
    private static final String X11UtilClassName = "jogamp.nativewindow.x11.X11Util";
    private static final String OSXUtilClassName = "jogamp.nativewindow.macosx.OSXUtil";
    private static final String GDIClassName = "jogamp.nativewindow.windows.GDIUtil";
    private static ToolkitLock jawtUtilJAWTToolkitLock;
    private static boolean requiresToolkitLock;
    private static boolean desktopHasThreadingIssues;
    private static volatile boolean isJVMShuttingDown;
    private static final List<Runnable> customShutdownHooks;
    private static boolean initialized;
    
    private static final boolean guessBroadcomVCIV() {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            private final File vcliblocation = new File("/opt/vc/lib/libbcm_host.so");
            
            @Override
            public Boolean run() {
                if (this.vcliblocation.isFile()) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        });
    }
    
    private static String _getNativeWindowingType() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case ANDROID: {
                return NativeWindowFactory.TYPE_ANDROID;
            }
            case MACOS: {
                return NativeWindowFactory.TYPE_MACOSX;
            }
            case WINDOWS: {
                return NativeWindowFactory.TYPE_WINDOWS;
            }
            case OPENKODE: {
                return NativeWindowFactory.TYPE_EGL;
            }
            default: {
                if (guessBroadcomVCIV()) {
                    return NativeWindowFactory.TYPE_BCM_VC_IV;
                }
                return NativeWindowFactory.TYPE_X11;
            }
        }
    }
    
    private static void initSingletonNativeImpl(final ClassLoader classLoader) {
        String s;
        if (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.nativeWindowingTypePure) {
            s = "jogamp.nativewindow.x11.X11Util";
        }
        else if (NativeWindowFactory.TYPE_WINDOWS == NativeWindowFactory.nativeWindowingTypePure) {
            s = "jogamp.nativewindow.windows.GDIUtil";
        }
        else if (NativeWindowFactory.TYPE_MACOSX == NativeWindowFactory.nativeWindowingTypePure) {
            s = "jogamp.nativewindow.macosx.OSXUtil";
        }
        else {
            s = null;
        }
        if (null != s) {
            ReflectionUtil.callStaticMethod(s, "initSingleton", null, null, classLoader);
            NativeWindowFactory.requiresToolkitLock = (boolean)ReflectionUtil.callStaticMethod(s, "requiresToolkitLock", null, null, classLoader);
            NativeWindowFactory.desktopHasThreadingIssues = (boolean)ReflectionUtil.callStaticMethod(s, "hasThreadingIssues", null, null, classLoader);
        }
        else {
            NativeWindowFactory.requiresToolkitLock = false;
            NativeWindowFactory.desktopHasThreadingIssues = false;
        }
    }
    
    public static final boolean isJVMShuttingDown() {
        return NativeWindowFactory.isJVMShuttingDown;
    }
    
    public static void addCustomShutdownHook(final boolean b, final Runnable runnable) {
        synchronized (NativeWindowFactory.customShutdownHooks) {
            if (!NativeWindowFactory.customShutdownHooks.contains(runnable)) {
                if (b) {
                    NativeWindowFactory.customShutdownHooks.add(0, runnable);
                }
                else {
                    NativeWindowFactory.customShutdownHooks.add(runnable);
                }
            }
        }
    }
    
    public static synchronized void shutdown(final boolean isJVMShuttingDown) {
        NativeWindowFactory.isJVMShuttingDown = isJVMShuttingDown;
        if (NativeWindowFactory.DEBUG) {
            System.err.println("NativeWindowFactory.shutdown() START: JVM Shutdown " + NativeWindowFactory.isJVMShuttingDown + ", on thread " + Thread.currentThread().getName());
        }
        synchronized (NativeWindowFactory.customShutdownHooks) {
            for (int size = NativeWindowFactory.customShutdownHooks.size(), i = 0; i < size; ++i) {
                try {
                    if (NativeWindowFactory.DEBUG) {
                        System.err.println("NativeWindowFactory.shutdown - customShutdownHook #" + (i + 1) + "/" + size);
                    }
                    NativeWindowFactory.customShutdownHooks.get(i).run();
                }
                catch (Throwable t) {
                    System.err.println("NativeWindowFactory.shutdown: Caught " + t.getClass().getName() + " during customShutdownHook #" + (i + 1) + "/" + size);
                    if (NativeWindowFactory.DEBUG) {
                        t.printStackTrace();
                    }
                }
            }
            NativeWindowFactory.customShutdownHooks.clear();
        }
        if (NativeWindowFactory.DEBUG) {
            System.err.println("NativeWindowFactory.shutdown(): Post customShutdownHook");
        }
        if (NativeWindowFactory.initialized) {
            NativeWindowFactory.initialized = false;
            if (null != NativeWindowFactory.registeredFactories) {
                NativeWindowFactory.registeredFactories.clear();
                NativeWindowFactory.registeredFactories = null;
            }
            GraphicsConfigurationFactory.shutdown();
        }
        shutdownNativeImpl(NativeWindowFactory.class.getClassLoader());
        if (NativeWindowFactory.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - NativeWindowFactory.shutdown() END JVM Shutdown " + NativeWindowFactory.isJVMShuttingDown);
        }
    }
    
    private static void shutdownNativeImpl(final ClassLoader classLoader) {
        String s;
        if (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.nativeWindowingTypePure) {
            s = "jogamp.nativewindow.x11.X11Util";
        }
        else if (NativeWindowFactory.TYPE_WINDOWS == NativeWindowFactory.nativeWindowingTypePure) {
            s = "jogamp.nativewindow.windows.GDIUtil";
        }
        else if (NativeWindowFactory.TYPE_MACOSX == NativeWindowFactory.nativeWindowingTypePure) {
            s = "jogamp.nativewindow.macosx.OSXUtil";
        }
        else {
            s = null;
        }
        if (null != s) {
            ReflectionUtil.callStaticMethod(s, "shutdown", null, null, classLoader);
        }
    }
    
    public static synchronized boolean isInitialized() {
        return NativeWindowFactory.initialized;
    }
    
    public static synchronized void initSingleton() {
        if (!NativeWindowFactory.initialized) {
            NativeWindowFactory.initialized = true;
            if (NativeWindowFactory.DEBUG) {
                System.err.println(Thread.currentThread().getName() + " - NativeWindowFactory.initSingleton()");
            }
            final ClassLoader classLoader = NativeWindowFactory.class.getClassLoader();
            NativeWindowFactory.isAWTAvailable = false;
            if (Platform.AWT_AVAILABLE && ReflectionUtil.isClassAvailable("com.jogamp.nativewindow.awt.AWTGraphicsDevice", classLoader)) {
                final Method[] array = AccessController.doPrivileged((PrivilegedAction<Method[]>)new PrivilegedAction<Method[]>() {
                    @Override
                    public Method[] run() {
                        try {
                            final Class<?> forName = Class.forName("jogamp.nativewindow.jawt.JAWTUtil", true, NativeWindowFactory.class.getClassLoader());
                            final Method declaredMethod = forName.getDeclaredMethod("isHeadlessMode", (Class<?>[])null);
                            declaredMethod.setAccessible(true);
                            final Method declaredMethod2 = forName.getDeclaredMethod("initSingleton", (Class<?>[])null);
                            declaredMethod2.setAccessible(true);
                            final Method declaredMethod3 = forName.getDeclaredMethod("getJAWTToolkitLock", (Class<?>[])new Class[0]);
                            declaredMethod3.setAccessible(true);
                            return new Method[] { declaredMethod2, declaredMethod, declaredMethod3 };
                        }
                        catch (Exception ex) {
                            if (NativeWindowFactory.DEBUG) {
                                ex.printStackTrace();
                            }
                            return null;
                        }
                    }
                });
                if (null != array) {
                    final Method method = array[0];
                    final Method method2 = array[1];
                    final Method method3 = array[2];
                    ReflectionUtil.callMethod(null, method, new Object[0]);
                    final Object callMethod = ReflectionUtil.callMethod(null, method2, new Object[0]);
                    if (!(callMethod instanceof Boolean)) {
                        throw new RuntimeException("JAWTUtil.isHeadlessMode() didn't return a Boolean");
                    }
                    NativeWindowFactory.isAWTAvailable = ((Boolean)callMethod).equals(Boolean.FALSE);
                    final Object callMethod2 = ReflectionUtil.callMethod(null, method3, new Object[0]);
                    if (!(callMethod2 instanceof ToolkitLock)) {
                        throw new RuntimeException("JAWTUtil.getJAWTToolkitLock() didn't return a ToolkitLock");
                    }
                    NativeWindowFactory.jawtUtilJAWTToolkitLock = (ToolkitLock)callMethod2;
                }
            }
            initSingletonNativeImpl(classLoader);
            NativeWindowFactory.registeredFactories = Collections.synchronizedMap(new HashMap<Class<?>, NativeWindowFactory>());
            final NativeWindowFactoryImpl defaultFactory = new NativeWindowFactoryImpl();
            registerFactory(NativeWindowFactory.nativeWindowClass = NativeWindow.class, defaultFactory);
            NativeWindowFactory.defaultFactory = defaultFactory;
            if (NativeWindowFactory.isAWTAvailable) {
                registerFactory(ReflectionUtil.getClass("java.awt.Component", false, classLoader), defaultFactory);
            }
            if (NativeWindowFactory.DEBUG) {
                System.err.println("NativeWindowFactory requiresToolkitLock " + NativeWindowFactory.requiresToolkitLock + ", desktopHasThreadingIssues " + NativeWindowFactory.desktopHasThreadingIssues);
                System.err.println("NativeWindowFactory isAWTAvailable " + NativeWindowFactory.isAWTAvailable + ", defaultFactory " + defaultFactory);
            }
            GraphicsConfigurationFactory.initSingleton();
        }
    }
    
    public static boolean requiresToolkitLock() {
        return NativeWindowFactory.requiresToolkitLock;
    }
    
    public static boolean isAWTAvailable() {
        return NativeWindowFactory.isAWTAvailable;
    }
    
    public static String getNativeWindowType(final boolean b) {
        return b ? NativeWindowFactory.nativeWindowingTypeCustom : NativeWindowFactory.nativeWindowingTypePure;
    }
    
    public static void setDefaultFactory(final NativeWindowFactory defaultFactory) {
        NativeWindowFactory.defaultFactory = defaultFactory;
    }
    
    public static NativeWindowFactory getDefaultFactory() {
        return NativeWindowFactory.defaultFactory;
    }
    
    public static ToolkitLock getAWTToolkitLock() {
        return NativeWindowFactory.jawtUtilJAWTToolkitLock;
    }
    
    public static ToolkitLock getNullToolkitLock() {
        return NativeWindowFactoryImpl.getNullToolkitLock();
    }
    
    public static ToolkitLock getDefaultToolkitLock() {
        return getDefaultToolkitLock(NativeWindowFactory.nativeWindowingTypePure);
    }
    
    public static ToolkitLock getDefaultToolkitLock(final String s) {
        if (!NativeWindowFactory.requiresToolkitLock) {
            return NativeWindowFactoryImpl.getNullToolkitLock();
        }
        if (NativeWindowFactory.TYPE_AWT == s && isAWTAvailable()) {
            return getAWTToolkitLock();
        }
        return ResourceToolkitLock.create();
    }
    
    public static ToolkitLock getDefaultToolkitLock(final String s, final long n) {
        if (!NativeWindowFactory.requiresToolkitLock) {
            return NativeWindowFactoryImpl.getNullToolkitLock();
        }
        if (NativeWindowFactory.TYPE_AWT == s && isAWTAvailable()) {
            return getAWTToolkitLock();
        }
        return ResourceToolkitLock.create();
    }
    
    public static AbstractGraphicsScreen createScreen(final AbstractGraphicsDevice abstractGraphicsDevice, int defaultScreen) {
        final String type = abstractGraphicsDevice.getType();
        if (NativeWindowFactory.TYPE_X11 == type) {
            final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)abstractGraphicsDevice;
            if (0 > defaultScreen) {
                defaultScreen = x11GraphicsDevice.getDefaultScreen();
            }
            return new X11GraphicsScreen(x11GraphicsDevice, defaultScreen);
        }
        if (0 > defaultScreen) {
            defaultScreen = 0;
        }
        if (NativeWindowFactory.TYPE_AWT == type) {
            return new AWTGraphicsScreen((AWTGraphicsDevice)abstractGraphicsDevice);
        }
        return new DefaultGraphicsScreen(abstractGraphicsDevice, defaultScreen);
    }
    
    public static NativeWindowFactory getFactory(final Class<?> clazz) throws IllegalArgumentException {
        if (NativeWindowFactory.nativeWindowClass.isAssignableFrom(clazz)) {
            return NativeWindowFactory.registeredFactories.get(NativeWindowFactory.nativeWindowClass);
        }
        for (Class<?> superclass = clazz; superclass != null; superclass = superclass.getSuperclass()) {
            final NativeWindowFactory nativeWindowFactory = NativeWindowFactory.registeredFactories.get(superclass);
            if (nativeWindowFactory != null) {
                return nativeWindowFactory;
            }
        }
        throw new IllegalArgumentException("No registered NativeWindowFactory for class " + clazz.getName());
    }
    
    protected static void registerFactory(final Class<?> clazz, final NativeWindowFactory nativeWindowFactory) {
        if (NativeWindowFactory.DEBUG) {
            System.err.println("NativeWindowFactory.registerFactory() " + clazz + " -> " + nativeWindowFactory);
        }
        NativeWindowFactory.registeredFactories.put(clazz, nativeWindowFactory);
    }
    
    public static NativeWindow getNativeWindow(final Object o, final AbstractGraphicsConfiguration abstractGraphicsConfiguration) throws IllegalArgumentException, NativeWindowException {
        if (o == null) {
            throw new IllegalArgumentException("Null window object");
        }
        return getFactory(o.getClass()).getNativeWindowImpl(o, abstractGraphicsConfiguration);
    }
    
    protected abstract NativeWindow getNativeWindowImpl(final Object p0, final AbstractGraphicsConfiguration p1) throws IllegalArgumentException;
    
    public static OffscreenLayerSurface getOffscreenLayerSurface(final NativeSurface nativeSurface, final boolean b) {
        if (nativeSurface instanceof OffscreenLayerSurface && (!b || nativeSurface instanceof OffscreenLayerOption)) {
            final OffscreenLayerSurface offscreenLayerSurface = (OffscreenLayerSurface)nativeSurface;
            return (!b || ((OffscreenLayerOption)offscreenLayerSurface).isOffscreenLayerSurfaceEnabled()) ? offscreenLayerSurface : null;
        }
        if (nativeSurface instanceof NativeWindow) {
            for (NativeWindow nativeWindow = ((NativeWindow)nativeSurface).getParent(); null != nativeWindow; nativeWindow = nativeWindow.getParent()) {
                if (nativeWindow instanceof OffscreenLayerSurface && (!b || nativeWindow instanceof OffscreenLayerOption)) {
                    final OffscreenLayerSurface offscreenLayerSurface2 = (OffscreenLayerSurface)nativeWindow;
                    return (!b || ((OffscreenLayerOption)offscreenLayerSurface2).isOffscreenLayerSurfaceEnabled()) ? offscreenLayerSurface2 : null;
                }
            }
        }
        return null;
    }
    
    public static boolean isNativeVisualIDValidForProcessing(final int n) {
        return NativeWindowFactory.TYPE_X11 != getNativeWindowType(false) || 0 != n;
    }
    
    public static String getDefaultDisplayConnection() {
        return getDefaultDisplayConnection(getNativeWindowType(true));
    }
    
    public static String getDefaultDisplayConnection(final String s) {
        if (NativeWindowFactory.TYPE_X11 == s) {
            return X11Util.getNullDisplayName();
        }
        return "decon";
    }
    
    public static AbstractGraphicsDevice createDevice(final String s, final boolean b) {
        return createDevice(getNativeWindowType(true), s, b);
    }
    
    public static AbstractGraphicsDevice createDevice(final String s, final String s2, final boolean b) {
        if (NativeWindowFactory.TYPE_X11 == s) {
            if (b) {
                return new X11GraphicsDevice(s2, 0, null);
            }
            return new X11GraphicsDevice(s2, 0);
        }
        else {
            if (NativeWindowFactory.TYPE_WINDOWS == s) {
                return new WindowsGraphicsDevice(0);
            }
            if (NativeWindowFactory.TYPE_MACOSX == s) {
                return new MacOSXGraphicsDevice(0);
            }
            if (NativeWindowFactory.TYPE_EGL == s) {
                EGLGraphicsDevice eglGraphicsDevice;
                if (b) {
                    Object callStaticMethod;
                    try {
                        callStaticMethod = ReflectionUtil.callStaticMethod("jogamp.opengl.egl.EGLDisplayUtil", "eglCreateEGLGraphicsDevice", new Class[] { Long.class, String.class, Integer.class }, new Object[] { 0L, DefaultGraphicsDevice.getDefaultDisplayConnection(s), 0 }, NativeWindowFactory.class.getClassLoader());
                    }
                    catch (Exception ex) {
                        throw new NativeWindowException("EGLDisplayUtil.eglCreateEGLGraphicsDevice failed", ex);
                    }
                    if (!(callStaticMethod instanceof EGLGraphicsDevice)) {
                        throw new NativeWindowException("EGLDisplayUtil.eglCreateEGLGraphicsDevice failed");
                    }
                    eglGraphicsDevice = (EGLGraphicsDevice)callStaticMethod;
                    eglGraphicsDevice.open();
                }
                else {
                    eglGraphicsDevice = new EGLGraphicsDevice(0L, 0L, s2, 0, null);
                }
                return eglGraphicsDevice;
            }
            if (NativeWindowFactory.TYPE_AWT == s) {
                throw new UnsupportedOperationException("n/a for windowing system: " + s);
            }
            return new DefaultGraphicsDevice(s, s2, 0);
        }
    }
    
    public static NativeWindow createWrappedWindow(final AbstractGraphicsScreen abstractGraphicsScreen, final long n, final long n2, final UpstreamWindowHookMutableSizePos upstreamWindowHookMutableSizePos) {
        final Capabilities capabilities = new Capabilities();
        return new WrappedWindow(new DefaultGraphicsConfiguration(abstractGraphicsScreen, capabilities, capabilities), n, upstreamWindowHookMutableSizePos, true, n2);
    }
    
    public static PointImmutable getLocationOnScreen(final NativeWindow nativeWindow) {
        final String nativeWindowType = getNativeWindowType(true);
        if (NativeWindowFactory.TYPE_X11 == nativeWindowType) {
            return X11Lib.GetRelativeLocation(nativeWindow.getDisplayHandle(), nativeWindow.getScreenIndex(), nativeWindow.getWindowHandle(), 0L, 0, 0);
        }
        if (NativeWindowFactory.TYPE_WINDOWS == nativeWindowType) {
            return GDIUtil.GetRelativeLocation(nativeWindow.getWindowHandle(), 0L, 0, 0);
        }
        if (NativeWindowFactory.TYPE_MACOSX == nativeWindowType) {
            return OSXUtil.GetLocationOnScreen(nativeWindow.getWindowHandle(), 0, 0);
        }
        throw new UnsupportedOperationException("n/a for windowing system: " + nativeWindowType);
    }
    
    static {
        TYPE_EGL = ".egl".intern();
        TYPE_WINDOWS = ".windows".intern();
        TYPE_X11 = ".x11".intern();
        TYPE_BCM_VC_IV = ".bcm.vc.iv".intern();
        TYPE_ANDROID = ".android".intern();
        TYPE_MACOSX = ".macosx".intern();
        TYPE_AWT = ".awt".intern();
        TYPE_DEFAULT = ".default".intern();
        NativeWindowFactory.isJVMShuttingDown = false;
        customShutdownHooks = new ArrayList<Runnable>();
        final boolean[] array = { false };
        final String[] array2 = { null };
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                Platform.initSingleton();
                array[0] = Debug.debug("NativeWindow");
                array2[0] = PropertyAccess.getProperty("nativewindow.ws.name", true);
                Runtime.getRuntime().addShutdownHook(new InterruptSource.Thread(null, new Runnable() {
                    @Override
                    public void run() {
                        NativeWindowFactory.shutdown(true);
                    }
                }, "NativeWindowFactory_ShutdownHook"));
                return null;
            }
        });
        DEBUG = array[0];
        if (NativeWindowFactory.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - Info: NativeWindowFactory.<init>");
        }
        nativeWindowingTypePure = _getNativeWindowingType();
        if (null == array2[0] || array2[0].length() == 0) {
            nativeWindowingTypeCustom = NativeWindowFactory.nativeWindowingTypePure;
        }
        else {
            nativeWindowingTypeCustom = array2[0].intern();
        }
        NativeWindowFactory.initialized = false;
    }
}
