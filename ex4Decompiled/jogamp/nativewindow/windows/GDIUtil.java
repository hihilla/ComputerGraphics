// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.nativewindow.Debug;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.os.Platform;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.nativewindow.NWJNILibLoader;
import com.jogamp.common.util.VersionNumber;
import jogamp.nativewindow.ToolkitProperties;

public class GDIUtil implements ToolkitProperties
{
    private static final boolean DEBUG;
    private static final String dummyWindowClassNameBase = "_dummyWindow_clazz";
    private static RegisteredClassFactory dummyWindowClassFactory;
    private static volatile boolean isInit;
    private static RegisteredClass dummyWindowClass;
    private static Object dummyWindowSync;
    public static final VersionNumber Win8Version;
    public static final VersionNumber Win10Version;
    
    public static synchronized void initSingleton() {
        if (!GDIUtil.isInit) {
            synchronized (GDIUtil.class) {
                if (!GDIUtil.isInit) {
                    if (GDIUtil.DEBUG) {
                        System.out.println("GDI.initSingleton()");
                    }
                    if (!NWJNILibLoader.loadNativeWindow("win32")) {
                        throw new NativeWindowException("NativeWindow Windows native library load error.");
                    }
                    if (!initIDs0()) {
                        throw new NativeWindowException("GDI: Could not initialized native stub");
                    }
                    GDIUtil.dummyWindowClassFactory = new RegisteredClassFactory("_dummyWindow_clazz", getDummyWndProc0(), true, 0L, 0L);
                    if (GDIUtil.DEBUG) {
                        System.out.println("GDI.initSingleton() dummyWindowClassFactory " + GDIUtil.dummyWindowClassFactory);
                    }
                    GDIUtil.isInit = true;
                }
            }
        }
    }
    
    public static void shutdown() {
    }
    
    public static boolean requiresToolkitLock() {
        return false;
    }
    
    public static final boolean hasThreadingIssues() {
        return false;
    }
    
    public static long CreateDummyWindow(final int n, final int n2, final int n3, final int n4) {
        synchronized (GDIUtil.dummyWindowSync) {
            GDIUtil.dummyWindowClass = GDIUtil.dummyWindowClassFactory.getSharedClass();
            if (GDIUtil.DEBUG) {
                System.out.println("GDI.CreateDummyWindow() dummyWindowClassFactory " + GDIUtil.dummyWindowClassFactory);
                System.out.println("GDI.CreateDummyWindow() dummyWindowClass " + GDIUtil.dummyWindowClass);
            }
            return CreateDummyWindow0(GDIUtil.dummyWindowClass.getHInstance(), GDIUtil.dummyWindowClass.getName(), GDIUtil.dummyWindowClass.getHDispThreadContext(), GDIUtil.dummyWindowClass.getName(), n, n2, n3, n4);
        }
    }
    
    public static boolean DestroyDummyWindow(final long n) {
        final boolean destroyWindow0;
        synchronized (GDIUtil.dummyWindowSync) {
            if (null == GDIUtil.dummyWindowClass) {
                throw new InternalError("GDI Error (" + GDIUtil.dummyWindowClassFactory.getSharedRefCount() + "): SharedClass is null");
            }
            destroyWindow0 = DestroyWindow0(GDIUtil.dummyWindowClass.getHDispThreadContext(), n);
            GDIUtil.dummyWindowClassFactory.releaseSharedClass();
        }
        return destroyWindow0;
    }
    
    public static Point GetRelativeLocation(final long n, final long n2, final int n3, final int n4) {
        return (Point)GetRelativeLocation0(n, n2, n3, n4);
    }
    
    public static boolean DwmIsCompositionEnabled() {
        return Platform.getOSVersionNumber().compareTo(GDIUtil.Win8Version) >= 0 || GDI.DwmIsCompositionEnabled();
    }
    
    public static boolean DwmSetupTranslucency(final long n, final boolean fEnable) {
        if (!GDI.DwmIsExtensionAvailable()) {
            if (GDIUtil.DEBUG) {
                System.err.println("GDIUtil.DwmSetupTranslucency on wnd 0x" + Long.toHexString(n) + ": enable " + fEnable + " -> failed, extension not available");
            }
            return !fEnable;
        }
        final VersionNumber osVersionNumber = Platform.getOSVersionNumber();
        final boolean b = osVersionNumber.compareTo(GDIUtil.Win8Version) >= 0;
        if (!b && !GDI.DwmIsCompositionEnabled()) {
            if (GDIUtil.DEBUG) {
                System.err.println("GDIUtil.DwmSetupTranslucency on wnd 0x" + Long.toHexString(n) + ": enable " + fEnable + " -> failed, composition disabled");
            }
            return !fEnable;
        }
        final boolean isWindowCompositionExtensionAvailable = GDI.IsWindowCompositionExtensionAvailable();
        final boolean b2 = b && isWindowCompositionExtensionAvailable;
        final boolean isUndecorated = IsUndecorated(n);
        boolean b3;
        if (b2 && !isUndecorated) {
            final AccentPolicy create = AccentPolicy.create();
            if (fEnable) {
                create.setAccentState(3);
            }
            else {
                create.setAccentState(0);
            }
            b3 = GDI.SetWindowCompositionAccentPolicy(n, create);
        }
        else {
            final DWM_BLURBEHIND create2 = DWM_BLURBEHIND.create();
            create2.setDwFlags(fEnable ? 7 : 1);
            create2.setFEnable(fEnable ? 1 : 0);
            create2.setHRgnBlur(0L);
            create2.setFTransitionOnMaximized(1);
            b3 = GDI.DwmEnableBlurBehindWindow(n, create2);
            if (b3) {
                final MARGINS create3 = MARGINS.create();
                create3.setCxLeftWidth(-1);
                create3.setCxRightWidth(-1);
                create3.setCyBottomHeight(-1);
                create3.setCyTopHeight(-1);
                b3 = GDI.DwmExtendFrameIntoClientArea(n, create3);
            }
        }
        if (GDIUtil.DEBUG) {
            System.err.println("GDIUtil.DwmSetupTranslucency on wnd 0x" + Long.toHexString(n) + ": enable " + fEnable + ", isUndecorated " + isUndecorated + ", isChild " + IsChild(n) + ", version " + osVersionNumber + ", isWin8 " + b + ", hasWinCompEXT " + isWindowCompositionExtensionAvailable + ", useWinCompEXT " + b2 + " -> ok: " + b3);
        }
        return b3;
    }
    
    public static boolean IsUndecorated(final long n) {
        return IsUndecorated0(n);
    }
    
    public static boolean IsChild(final long n) {
        return IsChild0(n);
    }
    
    public static void SetProcessThreadsAffinityMask(final long n, final boolean b) {
        SetProcessThreadsAffinityMask0(n, b);
    }
    
    private static final void dumpStack() {
        ExceptionUtils.dumpStack(System.err);
    }
    
    static native boolean CreateWindowClass0(final long p0, final String p1, final long p2, final long p3, final long p4);
    
    static native boolean DestroyWindowClass0(final long p0, final String p1, final long p2);
    
    static native long CreateDummyDispatchThread0();
    
    private static native boolean initIDs0();
    
    private static native long getDummyWndProc0();
    
    private static native Object GetRelativeLocation0(final long p0, final long p1, final int p2, final int p3);
    
    private static native boolean IsChild0(final long p0);
    
    private static native boolean IsUndecorated0(final long p0);
    
    private static native void SetProcessThreadsAffinityMask0(final long p0, final boolean p1);
    
    private static native long CreateDummyWindow0(final long p0, final String p1, final long p2, final String p3, final int p4, final int p5, final int p6, final int p7);
    
    private static native boolean DestroyWindow0(final long p0, final long p1);
    
    static {
        DEBUG = Debug.debug("GDIUtil");
        GDIUtil.isInit = false;
        GDIUtil.dummyWindowClass = null;
        GDIUtil.dummyWindowSync = new Object();
        Win8Version = new VersionNumber(6, 2, 0);
        Win10Version = new VersionNumber(10, 0, 0);
    }
}
