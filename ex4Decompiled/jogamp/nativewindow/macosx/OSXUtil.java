// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.macosx;

import jogamp.nativewindow.Debug;
import com.jogamp.common.util.FunctionTask;
import com.jogamp.common.util.Function;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.common.util.RunnableTask;
import com.jogamp.nativewindow.util.Insets;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.nativewindow.NWJNILibLoader;
import jogamp.nativewindow.ToolkitProperties;

public class OSXUtil implements ToolkitProperties
{
    private static boolean isInit;
    private static final boolean DEBUG;
    public static final int MAX_PIXELSCALE = 2;
    private static Runnable _nop;
    
    public static synchronized void initSingleton() {
        if (!OSXUtil.isInit) {
            if (OSXUtil.DEBUG) {
                System.out.println("OSXUtil.initSingleton()");
            }
            if (!NWJNILibLoader.loadNativeWindow("macosx")) {
                throw new NativeWindowException("NativeWindow MacOSX native library load error.");
            }
            if (!initIDs0()) {
                throw new NativeWindowException("MacOSX: Could not initialized native stub");
            }
            OSXUtil.isInit = true;
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
    
    public static boolean isNSView(final long n) {
        return 0L != n && isNSView0(n);
    }
    
    public static boolean isNSWindow(final long n) {
        return 0L != n && isNSWindow0(n);
    }
    
    public static Point GetLocationOnScreen(final long n, final int n2, final int n3) {
        return (Point)GetLocationOnScreen0(n, n2, n3);
    }
    
    public static Insets GetInsets(final long n) {
        return (Insets)GetInsets0(n);
    }
    
    public static double GetPixelScaleByDisplayID(final int n) {
        if (0 != n) {
            return GetPixelScale1(n);
        }
        return 1.0;
    }
    
    public static double GetPixelScale(final long n) {
        if (0L != n) {
            return GetPixelScale2(n);
        }
        return 1.0;
    }
    
    public static long CreateNSWindow(final int n, final int n2, final int n3, final int n4) {
        return CreateNSWindow0(n, n2, n3, n4);
    }
    
    public static void DestroyNSWindow(final long n) {
        DestroyNSWindow0(n);
    }
    
    public static long GetNSView(final long n) {
        return GetNSView0(n);
    }
    
    public static long GetNSWindow(final long n) {
        return GetNSWindow0(n);
    }
    
    public static long CreateCALayer(final int n, final int n2, final float n3) {
        final long createCALayer0 = CreateCALayer0(n, n2, n3);
        if (OSXUtil.DEBUG) {
            System.err.println("OSXUtil.CreateCALayer: 0x" + Long.toHexString(createCALayer0) + " - " + Thread.currentThread().getName());
        }
        return createCALayer0;
    }
    
    public static void AddCASublayer(final long n, final long n2, final int n3, final int n4, final int n5, final int n6, final float n7, final int n8) {
        if (0L == n || 0L == n2) {
            throw new IllegalArgumentException("rootCALayer 0x" + Long.toHexString(n) + ", subCALayer 0x" + Long.toHexString(n2));
        }
        if (OSXUtil.DEBUG) {
            System.err.println("OSXUtil.AttachCALayer: caLayerQuirks " + n8 + ", 0x" + Long.toHexString(n2) + " - " + Thread.currentThread().getName());
        }
        AddCASublayer0(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    public static void FixCALayerLayout(final long n, final long n2, final boolean b, final int n3, final int n4, final int n5, final int n6, final int n7) {
        if (0L == n && 0L == n2) {
            return;
        }
        FixCALayerLayout0(n, n2, b, n3, n4, n5, n6, n7);
    }
    
    public static void SetCALayerPixelScale(final long n, final long n2, final float n3) {
        if (0L == n && 0L == n2) {
            return;
        }
        SetCALayerPixelScale0(n, n2, n3);
    }
    
    public static void RemoveCASublayer(final long n, final long n2) {
        if (0L == n || 0L == n2) {
            throw new IllegalArgumentException("rootCALayer 0x" + Long.toHexString(n) + ", subCALayer 0x" + Long.toHexString(n2));
        }
        if (OSXUtil.DEBUG) {
            System.err.println("OSXUtil.DetachCALayer: 0x" + Long.toHexString(n2) + " - " + Thread.currentThread().getName());
        }
        RemoveCASublayer0(n, n2);
    }
    
    public static void DestroyCALayer(final long n) {
        if (0L == n) {
            throw new IllegalArgumentException("caLayer 0x" + Long.toHexString(n));
        }
        if (OSXUtil.DEBUG) {
            System.err.println("OSXUtil.DestroyCALayer: 0x" + Long.toHexString(n) + " - " + Thread.currentThread().getName());
        }
        DestroyCALayer0(n);
    }
    
    public static void RunOnMainThread(final boolean b, final boolean b2, final Runnable runnable) {
        if (IsMainThread0()) {
            runnable.run();
        }
        else {
            final Object o = new Object();
            final RunnableTask runnableTask = new RunnableTask(runnable, b ? o : null, true, b ? null : System.err);
            synchronized (o) {
                RunOnMainThread0(b2, runnableTask);
                if (b) {
                    while (runnableTask.isInQueue()) {
                        try {
                            o.wait();
                        }
                        catch (InterruptedException ex) {
                            throw new InterruptedRuntimeException(ex);
                        }
                        final Throwable throwable = runnableTask.getThrowable();
                        if (null != throwable) {
                            throw new RuntimeException(throwable);
                        }
                    }
                }
            }
        }
    }
    
    public static void RunLater(final boolean b, final Runnable runnable, final int n) {
        RunLater0(b, false, new RunnableTask(runnable, null, true, System.err), n);
    }
    
    public static void KickNSApp() {
        KickNSApp0();
    }
    
    public static void WaitUntilFinish() {
        RunOnMainThread(true, true, OSXUtil._nop);
    }
    
    public static <R, A> R RunOnMainThread(final boolean b, final boolean b2, final Function<R, A> function, final A... args) {
        if (IsMainThread0()) {
            return (R)function.eval(args);
        }
        final Object o = new Object();
        final FunctionTask functionTask = new FunctionTask<R, A>((Function<Object, Object>)function, b ? o : null, true, b ? null : System.err);
        synchronized (o) {
            functionTask.setArgs(args);
            RunOnMainThread0(b2, functionTask);
            if (b) {
                while (functionTask.isInQueue()) {
                    try {
                        o.wait();
                    }
                    catch (InterruptedException ex) {
                        throw new InterruptedRuntimeException(ex);
                    }
                    final Throwable throwable = functionTask.getThrowable();
                    if (null != throwable) {
                        throw new RuntimeException(throwable);
                    }
                }
            }
        }
        return functionTask.getResult();
    }
    
    public static boolean IsMainThread() {
        return IsMainThread0();
    }
    
    public static int GetScreenRefreshRate(final int n) {
        return GetScreenRefreshRate0(n);
    }
    
    private static native boolean initIDs0();
    
    private static native boolean isNSView0(final long p0);
    
    private static native boolean isNSWindow0(final long p0);
    
    private static native Object GetLocationOnScreen0(final long p0, final int p1, final int p2);
    
    private static native Object GetInsets0(final long p0);
    
    private static native double GetPixelScale1(final int p0);
    
    private static native double GetPixelScale2(final long p0);
    
    private static native long CreateNSWindow0(final int p0, final int p1, final int p2, final int p3);
    
    private static native void DestroyNSWindow0(final long p0);
    
    private static native long GetNSView0(final long p0);
    
    private static native long GetNSWindow0(final long p0);
    
    private static native long CreateCALayer0(final int p0, final int p1, final float p2);
    
    private static native void AddCASublayer0(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final float p6, final int p7);
    
    private static native void FixCALayerLayout0(final long p0, final long p1, final boolean p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    private static native void SetCALayerPixelScale0(final long p0, final long p1, final float p2);
    
    private static native void RemoveCASublayer0(final long p0, final long p1);
    
    private static native void DestroyCALayer0(final long p0);
    
    private static native void RunOnMainThread0(final boolean p0, final Runnable p1);
    
    private static native void RunLater0(final boolean p0, final boolean p1, final Runnable p2, final int p3);
    
    private static native void KickNSApp0();
    
    private static native boolean IsMainThread0();
    
    private static native int GetScreenRefreshRate0(final int p0);
    
    static {
        OSXUtil.isInit = false;
        DEBUG = Debug.debug("OSXUtil");
        OSXUtil._nop = new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}
