// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.util.Map;
import java.awt.EventQueue;
import com.jogamp.common.util.locks.LockFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import jogamp.nativewindow.NWJNILibLoader;
import java.awt.GraphicsEnvironment;
import com.jogamp.common.util.PropertyAccess;
import jogamp.nativewindow.Debug;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import jogamp.nativewindow.x11.X11Lib;
import jogamp.nativewindow.jawt.x11.X11SunJDKReflection;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import jogamp.nativewindow.macosx.OSXUtil;
import java.awt.GraphicsDevice;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.os.Platform;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.nativewindow.ToolkitLock;
import com.jogamp.common.util.locks.RecursiveLock;
import java.lang.reflect.Method;
import com.jogamp.common.util.VersionNumber;

public class JAWTUtil
{
    public static final boolean DEBUG;
    private static final boolean SKIP_AWT_HIDPI;
    public static final int JAWT_MACOSX_USE_CALAYER = Integer.MIN_VALUE;
    public static final VersionNumber JAWT_MacOSXCALayerMinVersion;
    public static final VersionNumber JAWT_MacOSXCALayerRequiredForJavaVersion;
    private static final boolean headlessMode;
    private static final JAWT jawtLockObject;
    private static final Method isQueueFlusherThread;
    private static final boolean j2dExist;
    private static final Method sunToolkitAWTLockMethod;
    private static final Method sunToolkitAWTUnlockMethod;
    private static final boolean hasSunToolkitAWTLock;
    private static final RecursiveLock jawtLock;
    private static final ToolkitLock jawtToolkitLock;
    private static final Method getScaleFactorMethod;
    private static final Method getCGDisplayIDMethodOnOSX;
    public static final int JAWT_OSX_CALAYER_QUIRK_SIZE = 1;
    public static final int JAWT_OSX_CALAYER_QUIRK_POSITION = 2;
    public static final int JAWT_OSX_CALAYER_QUIRK_LAYOUT = 4;
    
    public static boolean isOffscreenLayerSupported() {
        return PlatformPropsImpl.OS_TYPE == Platform.OSType.MACOS && PlatformPropsImpl.OS_VERSION_NUMBER.compareTo(JAWTUtil.JAWT_MacOSXCALayerMinVersion) >= 0;
    }
    
    public static boolean isOffscreenLayerRequired() {
        return PlatformPropsImpl.OS_TYPE == Platform.OSType.MACOS && PlatformPropsImpl.JAVA_VERSION_NUMBER.compareTo(JAWTUtil.JAWT_MacOSXCALayerRequiredForJavaVersion) >= 0;
    }
    
    public static int getOSXCALayerQuirks() {
        int n = 0;
        if (PlatformPropsImpl.OS_TYPE == Platform.OSType.MACOS && PlatformPropsImpl.OS_VERSION_NUMBER.compareTo(JAWTUtil.JAWT_MacOSXCALayerMinVersion) >= 0) {
            final int n2 = n | 0x1;
            final int compareTo = PlatformPropsImpl.JAVA_VERSION_NUMBER.compareTo(PlatformPropsImpl.Version17);
            if (compareTo < 0 || (compareTo == 0 && PlatformPropsImpl.JAVA_VERSION_UPDATE < 40)) {
                n = (n2 | 0x2);
            }
            else {
                n = (n2 | 0x4);
            }
        }
        return n;
    }
    
    public static JAWT getJAWT(final boolean b) {
        final JAWT create = JAWT.create();
        int n = 65540;
        boolean b2;
        boolean b3;
        if (isOffscreenLayerRequired()) {
            if (PlatformPropsImpl.OS_TYPE != Platform.OSType.MACOS) {
                throw new InternalError("offscreen required, but n/a for: " + PlatformPropsImpl.OS_TYPE);
            }
            if (PlatformPropsImpl.OS_VERSION_NUMBER.compareTo(JAWTUtil.JAWT_MacOSXCALayerMinVersion) < 0) {
                throw new RuntimeException("OSX: Invalid version of Java (" + PlatformPropsImpl.JAVA_VERSION_NUMBER + ") / OS X (" + PlatformPropsImpl.OS_VERSION_NUMBER + ")");
            }
            n |= Integer.MIN_VALUE;
            b2 = true;
            b3 = false;
        }
        else if (b && isOffscreenLayerSupported()) {
            if (PlatformPropsImpl.OS_TYPE != Platform.OSType.MACOS) {
                throw new InternalError("offscreen requested and supported, but n/a for: " + PlatformPropsImpl.OS_TYPE);
            }
            n |= Integer.MIN_VALUE;
            b2 = true;
            b3 = true;
        }
        else {
            b2 = false;
            b3 = true;
        }
        if (JAWTUtil.DEBUG) {
            System.err.println("JAWTUtil.getJAWT(tryOffscreenLayer " + b2 + ", tryOnscreen " + b3 + ")");
        }
        final StringBuilder sb = new StringBuilder();
        if (b2) {
            sb.append("Offscreen 0x").append(Integer.toHexString(n));
            if (JAWT.getJAWT(create, n)) {
                return create;
            }
        }
        if (b3) {
            if (b2) {
                sb.append(", ");
            }
            sb.append("Onscreen 0x").append(Integer.toHexString(65540));
            if (JAWT.getJAWT(create, 65540)) {
                return create;
            }
        }
        throw new RuntimeException("Unable to initialize JAWT, trials: " + sb.toString());
    }
    
    public static boolean isJAWTUsingOffscreenLayer(final JAWT jawt) {
        return 0x0 != (jawt.getCachedVersion() & Integer.MIN_VALUE);
    }
    
    public static void initSingleton() {
    }
    
    public static void shutdown() {
    }
    
    public static boolean hasJava2D() {
        return JAWTUtil.j2dExist;
    }
    
    public static boolean isJava2DQueueFlusherThread() {
        boolean booleanValue = false;
        if (JAWTUtil.j2dExist) {
            try {
                booleanValue = (boolean)JAWTUtil.isQueueFlusherThread.invoke(null, (Object[])null);
            }
            catch (Exception ex) {}
        }
        return booleanValue;
    }
    
    public static boolean isHeadlessMode() {
        return JAWTUtil.headlessMode;
    }
    
    public static void lockToolkit() throws NativeWindowException {
        JAWTUtil.jawtLock.lock();
        Label_0071: {
            if (1 == JAWTUtil.jawtLock.getHoldCount() && !JAWTUtil.headlessMode && !isJava2DQueueFlusherThread()) {
                if (JAWTUtil.hasSunToolkitAWTLock) {
                    try {
                        JAWTUtil.sunToolkitAWTLockMethod.invoke(null, (Object[])null);
                        break Label_0071;
                    }
                    catch (Exception ex) {
                        throw new NativeWindowException("SunToolkit.awtLock failed", ex);
                    }
                }
                JAWTUtil.jawtLockObject.Lock();
            }
        }
        if (ToolkitLock.TRACE_LOCK) {
            System.err.println("JAWTUtil-ToolkitLock.lock(): " + JAWTUtil.jawtLock);
        }
    }
    
    public static void unlockToolkit() {
        JAWTUtil.jawtLock.validateLocked();
        if (ToolkitLock.TRACE_LOCK) {
            System.err.println("JAWTUtil-ToolkitLock.unlock(): " + JAWTUtil.jawtLock);
        }
        Label_0104: {
            if (1 == JAWTUtil.jawtLock.getHoldCount() && !JAWTUtil.headlessMode && !isJava2DQueueFlusherThread()) {
                if (JAWTUtil.hasSunToolkitAWTLock) {
                    try {
                        JAWTUtil.sunToolkitAWTUnlockMethod.invoke(null, (Object[])null);
                        break Label_0104;
                    }
                    catch (Exception ex) {
                        throw new NativeWindowException("SunToolkit.awtUnlock failed", ex);
                    }
                }
                JAWTUtil.jawtLockObject.Unlock();
            }
        }
        JAWTUtil.jawtLock.unlock();
    }
    
    public static final void validateLocked() throws RuntimeException {
        JAWTUtil.jawtLock.validateLocked();
    }
    
    public static ToolkitLock getJAWTToolkitLock() {
        return JAWTUtil.jawtToolkitLock;
    }
    
    public static final int getMonitorDisplayID(final GraphicsDevice graphicsDevice) {
        int intValue = 0;
        if (null != JAWTUtil.getCGDisplayIDMethodOnOSX) {
            try {
                final Object invoke = JAWTUtil.getCGDisplayIDMethodOnOSX.invoke(graphicsDevice, new Object[0]);
                if (invoke instanceof Integer) {
                    intValue = (int)invoke;
                }
            }
            catch (Throwable t) {}
        }
        return intValue;
    }
    
    public static final boolean getPixelScale(final GraphicsDevice graphicsDevice, final float[] array, final float[] array2) {
        final boolean b = array[0] != 1.0f || array[1] != 1.0f;
        array[1] = (array[0] = 1.0f);
        float n = 1.0f;
        float n2 = 1.0f;
        if (!JAWTUtil.SKIP_AWT_HIDPI) {
            if (null != JAWTUtil.getCGDisplayIDMethodOnOSX) {
                try {
                    final Object invoke = JAWTUtil.getCGDisplayIDMethodOnOSX.invoke(graphicsDevice, new Object[0]);
                    if (invoke instanceof Integer) {
                        n = (n2 = (float)OSXUtil.GetPixelScaleByDisplayID((int)invoke));
                    }
                }
                catch (Throwable t) {}
            }
            if (null != JAWTUtil.getScaleFactorMethod) {
                try {
                    final Object invoke2 = JAWTUtil.getScaleFactorMethod.invoke(graphicsDevice, new Object[0]);
                    if (invoke2 instanceof Integer) {
                        n = (float)invoke2;
                    }
                    else if (invoke2 instanceof Double) {
                        n = (float)invoke2;
                    }
                    n2 = n;
                }
                catch (Throwable t2) {}
            }
        }
        final boolean b2 = array2[0] != n || array2[1] != n2;
        array2[0] = n;
        array2[1] = n2;
        return b2;
    }
    
    public static final boolean getPixelScale(final GraphicsConfiguration graphicsConfiguration, final float[] array, final float[] array2) {
        final GraphicsDevice graphicsDevice = (null != graphicsConfiguration) ? graphicsConfiguration.getDevice() : null;
        boolean pixelScale;
        if (null == graphicsDevice) {
            pixelScale = (array[0] != 1.0f || array[1] != 1.0f || array2[0] != 1.0f || array2[1] != 1.0f);
            array[1] = (array[0] = 1.0f);
            array2[1] = (array2[0] = 1.0f);
        }
        else {
            pixelScale = getPixelScale(graphicsDevice, array, array2);
        }
        return pixelScale;
    }
    
    private static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    private static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    public static AbstractGraphicsDevice createDevice(final Component component) throws IllegalArgumentException {
        if (!component.isDisplayable()) {
            throw new IllegalArgumentException("Given AWT-Component is not displayable: " + component);
        }
        final GraphicsConfiguration graphicsConfiguration = component.getGraphicsConfiguration();
        if (null != graphicsConfiguration) {
            final GraphicsDevice device = graphicsConfiguration.getDevice();
            String xDisplayString;
            if (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(true)) {
                final long graphicsDeviceGetDisplay = X11SunJDKReflection.graphicsDeviceGetDisplay(device);
                if (0L == graphicsDeviceGetDisplay) {
                    xDisplayString = null;
                    if (JAWTUtil.DEBUG) {
                        System.err.println(getThreadName() + " - JAWTUtil.createDevice: Null AWT dpy, default X11 display");
                    }
                }
                else {
                    xDisplayString = X11Lib.XDisplayString(graphicsDeviceGetDisplay);
                    if (JAWTUtil.DEBUG) {
                        System.err.println(getThreadName() + " - JAWTUtil.createDevice: AWT dpy " + xDisplayString + " / " + toHexString(graphicsDeviceGetDisplay));
                    }
                }
            }
            else {
                xDisplayString = null;
            }
            return NativeWindowFactory.createDevice(xDisplayString, true);
        }
        throw new IllegalArgumentException("Given AWT-Component has no GraphicsConfiguration set: " + component);
    }
    
    public static AbstractGraphicsScreen getAbstractGraphicsScreen(final Component component) throws IllegalArgumentException {
        return NativeWindowFactory.createScreen(createDevice(component), AWTGraphicsScreen.findScreenIndex(component.getGraphicsConfiguration().getDevice()));
    }
    
    static {
        DEBUG = Debug.debug("JAWT");
        JAWT_MacOSXCALayerMinVersion = new VersionNumber(10, 6, 4);
        JAWT_MacOSXCALayerRequiredForJavaVersion = Platform.Version17;
        SKIP_AWT_HIDPI = PropertyAccess.isPropertyDefined("nativewindow.awt.nohidpi", true);
        if (JAWTUtil.DEBUG) {
            System.err.println("JAWTUtil initialization (JAWT/JNI/...); SKIP_AWT_HIDPI " + JAWTUtil.SKIP_AWT_HIDPI);
        }
        headlessMode = GraphicsEnvironment.isHeadless();
        if (JAWTUtil.headlessMode) {
            jawtLockObject = null;
            isQueueFlusherThread = null;
            j2dExist = false;
            sunToolkitAWTLockMethod = null;
            sunToolkitAWTUnlockMethod = null;
            hasSunToolkitAWTLock = false;
            getScaleFactorMethod = null;
            getCGDisplayIDMethodOnOSX = null;
        }
        else {
            JAWTJNILibLoader.initSingleton();
            if (!NWJNILibLoader.loadNativeWindow("awt")) {
                throw new NativeWindowException("NativeWindow AWT native library load error.");
            }
            jawtLockObject = getJAWT(false);
            boolean j2dExist2 = false;
            Method method = null;
            try {
                method = Class.forName("jogamp.opengl.awt.Java2D").getMethod("isQueueFlusherThread", (Class<?>[])null);
                j2dExist2 = true;
            }
            catch (Exception ex3) {}
            isQueueFlusherThread = method;
            j2dExist = j2dExist2;
            final PrivilegedDataBlob1 privilegedDataBlob1 = AccessController.doPrivileged((PrivilegedAction<PrivilegedDataBlob1>)new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    final PrivilegedDataBlob1 privilegedDataBlob1 = new PrivilegedDataBlob1();
                    try {
                        final Class<?> forName = Class.forName("sun.awt.SunToolkit");
                        (privilegedDataBlob1.sunToolkitAWTLockMethod = forName.getDeclaredMethod("awtLock", (Class<?>[])new Class[0])).setAccessible(true);
                        (privilegedDataBlob1.sunToolkitAWTUnlockMethod = forName.getDeclaredMethod("awtUnlock", (Class<?>[])new Class[0])).setAccessible(true);
                        privilegedDataBlob1.ok = true;
                    }
                    catch (Exception ex) {}
                    try {
                        final Class<? extends GraphicsDevice> class1 = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getClass();
                        (privilegedDataBlob1.getScaleFactorMethod = class1.getDeclaredMethod("getScaleFactor", (Class<?>[])new Class[0])).setAccessible(true);
                        if (Platform.OSType.MACOS == PlatformPropsImpl.OS_TYPE) {
                            (privilegedDataBlob1.getCGDisplayIDMethodOnOSX = class1.getDeclaredMethod("getCGDisplayID", (Class<?>[])new Class[0])).setAccessible(true);
                        }
                    }
                    catch (Throwable t) {}
                    return privilegedDataBlob1;
                }
            });
            sunToolkitAWTLockMethod = privilegedDataBlob1.sunToolkitAWTLockMethod;
            sunToolkitAWTUnlockMethod = privilegedDataBlob1.sunToolkitAWTUnlockMethod;
            getScaleFactorMethod = privilegedDataBlob1.getScaleFactorMethod;
            getCGDisplayIDMethodOnOSX = privilegedDataBlob1.getCGDisplayIDMethodOnOSX;
            boolean hasSunToolkitAWTLock2 = false;
            if (privilegedDataBlob1.ok) {
                try {
                    JAWTUtil.sunToolkitAWTLockMethod.invoke(null, (Object[])null);
                    JAWTUtil.sunToolkitAWTUnlockMethod.invoke(null, (Object[])null);
                    hasSunToolkitAWTLock2 = true;
                }
                catch (Exception ex4) {}
            }
            hasSunToolkitAWTLock = hasSunToolkitAWTLock2;
        }
        jawtLock = LockFactory.createRecursiveLock();
        jawtToolkitLock = new ToolkitLock() {
            @Override
            public final void lock() {
                JAWTUtil.lockToolkit();
            }
            
            @Override
            public final void unlock() {
                JAWTUtil.unlockToolkit();
            }
            
            @Override
            public final void validateLocked() throws RuntimeException {
                JAWTUtil.validateLocked();
            }
            
            @Override
            public final void dispose() {
            }
            
            @Override
            public String toString() {
                return "JAWTToolkitLock[obj 0x" + Integer.toHexString(this.hashCode()) + ", isOwner " + JAWTUtil.jawtLock.isOwner(Thread.currentThread()) + ", " + JAWTUtil.jawtLock + "]";
            }
        };
        Map map = null;
        try {
            if (EventQueue.isDispatchThread()) {
                map = (Map)Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
            }
            else {
                final ArrayList<Map> list = new ArrayList<Map>(1);
                EventQueue.invokeAndWait(new Runnable() {
                    final /* synthetic */ ArrayList val$desktophintsBucket = list;
                    
                    @Override
                    public void run() {
                        final Map map = (Map)Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
                        if (null != map) {
                            this.val$desktophintsBucket.add(map);
                        }
                    }
                });
                map = ((list.size() > 0) ? list.get(0) : null);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        catch (InvocationTargetException ex2) {
            ex2.printStackTrace();
        }
        if (JAWTUtil.DEBUG) {
            System.err.println("JAWTUtil: Has sun.awt.SunToolkit.awtLock/awtUnlock " + JAWTUtil.hasSunToolkitAWTLock);
            System.err.println("JAWTUtil: Has Java2D " + JAWTUtil.j2dExist);
            System.err.println("JAWTUtil: Is headless " + JAWTUtil.headlessMode);
            System.err.println("JAWTUtil: AWT Desktop hints " + ((null != map) ? map.size() : 0));
            System.err.println("JAWTUtil: OffscreenLayer Supported: " + isOffscreenLayerSupported() + " - Required " + isOffscreenLayerRequired());
        }
    }
    
    private static class PrivilegedDataBlob1
    {
        Method sunToolkitAWTLockMethod;
        Method sunToolkitAWTUnlockMethod;
        Method getScaleFactorMethod;
        Method getCGDisplayIDMethodOnOSX;
        boolean ok;
        
        PrivilegedDataBlob1() {
            this.ok = false;
        }
    }
}
