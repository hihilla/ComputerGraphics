// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.swt;

import java.io.Serializable;
import com.jogamp.common.os.Platform;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import jogamp.nativewindow.macosx.OSXUtil;
import org.eclipse.swt.graphics.GCData;
import jogamp.nativewindow.x11.X11Lib;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.macosx.MacOSXGraphicsDevice;
import com.jogamp.nativewindow.windows.WindowsGraphicsDevice;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import org.eclipse.swt.widgets.Control;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.common.util.VersionNumber;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class SWTAccessor
{
    private static final boolean DEBUG = true;
    private static final Field swt_control_handle;
    private static final boolean swt_uses_long_handles;
    private static Object swt_osx_init;
    private static Field swt_osx_control_view;
    private static Field swt_osx_view_id;
    private static final String nwt;
    public static final boolean isOSX;
    public static final boolean isWindows;
    public static final boolean isX11;
    public static final boolean isX11GTK;
    private static final String str_handle = "handle";
    private static final String str_osx_view = "view";
    private static final String str_osx_id = "id";
    private static final Method swt_control_internal_new_GC;
    private static final Method swt_control_internal_dispose_GC;
    private static final String str_internal_new_GC = "internal_new_GC";
    private static final String str_internal_dispose_GC = "internal_dispose_GC";
    private static final String str_OS_gtk_class = "org.eclipse.swt.internal.gtk.OS";
    public static final Class<?> OS_gtk_class;
    private static final String str_OS_gtk_version = "GTK_VERSION";
    public static final VersionNumber OS_gtk_version;
    private static final Method OS_gtk_widget_realize;
    private static final Method OS_gtk_widget_unrealize;
    private static final Method OS_GTK_WIDGET_WINDOW;
    private static final Method OS_gtk_widget_get_window;
    private static final Method OS_gdk_x11_drawable_get_xdisplay;
    private static final Method OS_gdk_x11_display_get_xdisplay;
    private static final Method OS_gdk_window_get_display;
    private static final Method OS_gdk_x11_drawable_get_xid;
    private static final Method OS_gdk_x11_window_get_xid;
    private static final Method OS_gdk_window_set_back_pixmap;
    private static final String str_gtk_widget_realize = "gtk_widget_realize";
    private static final String str_gtk_widget_unrealize = "gtk_widget_unrealize";
    private static final String str_GTK_WIDGET_WINDOW = "GTK_WIDGET_WINDOW";
    private static final String str_gtk_widget_get_window = "gtk_widget_get_window";
    private static final String str_gdk_x11_drawable_get_xdisplay = "gdk_x11_drawable_get_xdisplay";
    private static final String str_gdk_x11_display_get_xdisplay = "gdk_x11_display_get_xdisplay";
    private static final String str_gdk_window_get_display = "gdk_window_get_display";
    private static final String str_gdk_x11_drawable_get_xid = "gdk_x11_drawable_get_xid";
    private static final String str_gdk_x11_window_get_xid = "gdk_x11_window_get_xid";
    private static final String str_gdk_window_set_back_pixmap = "gdk_window_set_back_pixmap";
    private static final VersionNumber GTK_VERSION_2_14_0;
    private static final VersionNumber GTK_VERSION_2_24_0;
    private static final VersionNumber GTK_VERSION_3_0_0;
    
    private static VersionNumber GTK_VERSION(final int n) {
        return new VersionNumber(n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF);
    }
    
    private static Number getIntOrLong(final long n) {
        if (SWTAccessor.swt_uses_long_handles) {
            return n;
        }
        return (int)n;
    }
    
    private static void callStaticMethodL2V(final Method method, final long n) {
        ReflectionUtil.callMethod(null, method, getIntOrLong(n));
    }
    
    private static void callStaticMethodLLZ2V(final Method method, final long n, final long n2, final boolean b) {
        ReflectionUtil.callMethod(null, method, getIntOrLong(n), getIntOrLong(n2), b);
    }
    
    private static long callStaticMethodL2L(final Method method, final long n) {
        final Object callMethod = ReflectionUtil.callMethod(null, method, getIntOrLong(n));
        if (callMethod instanceof Number) {
            return ((Number)callMethod).longValue();
        }
        throw new InternalError("SWT method " + method.getName() + " didn't return int or long but " + ((Number)callMethod).getClass());
    }
    
    public static boolean isUsingLongHandles() {
        return SWTAccessor.swt_uses_long_handles;
    }
    
    public static boolean useX11GTK() {
        return SWTAccessor.isX11GTK;
    }
    
    public static VersionNumber GTK_VERSION() {
        return SWTAccessor.OS_gtk_version;
    }
    
    public static long gdk_widget_get_window(final long n) {
        long n2;
        if (SWTAccessor.OS_gtk_version.compareTo(SWTAccessor.GTK_VERSION_2_14_0) >= 0) {
            n2 = callStaticMethodL2L(SWTAccessor.OS_gtk_widget_get_window, n);
        }
        else {
            n2 = callStaticMethodL2L(SWTAccessor.OS_GTK_WIDGET_WINDOW, n);
        }
        if (0L == n2) {
            throw new NativeWindowException("Null gtk-window-handle of SWT handle 0x" + Long.toHexString(n));
        }
        return n2;
    }
    
    public static long gdk_window_get_xdisplay(final long n) {
        long n2;
        if (SWTAccessor.OS_gtk_version.compareTo(SWTAccessor.GTK_VERSION_2_24_0) >= 0) {
            final long callStaticMethodL2L = callStaticMethodL2L(SWTAccessor.OS_gdk_window_get_display, n);
            if (0L == callStaticMethodL2L) {
                throw new NativeWindowException("Null display-handle of gtk-window-handle 0x" + Long.toHexString(n));
            }
            n2 = callStaticMethodL2L(SWTAccessor.OS_gdk_x11_display_get_xdisplay, callStaticMethodL2L);
        }
        else {
            n2 = callStaticMethodL2L(SWTAccessor.OS_gdk_x11_drawable_get_xdisplay, n);
        }
        if (0L == n2) {
            throw new NativeWindowException("Null x11-display-handle of gtk-window-handle 0x" + Long.toHexString(n));
        }
        return n2;
    }
    
    public static long gdk_window_get_xwindow(final long n) {
        long n2;
        if (SWTAccessor.OS_gtk_version.compareTo(SWTAccessor.GTK_VERSION_3_0_0) >= 0) {
            n2 = callStaticMethodL2L(SWTAccessor.OS_gdk_x11_window_get_xid, n);
        }
        else {
            n2 = callStaticMethodL2L(SWTAccessor.OS_gdk_x11_drawable_get_xid, n);
        }
        if (0L == n2) {
            throw new NativeWindowException("Null x11-window-handle of gtk-window-handle 0x" + Long.toHexString(n));
        }
        return n2;
    }
    
    public static void gdk_window_set_back_pixmap(final long n, final long n2, final boolean b) {
        callStaticMethodLLZ2V(SWTAccessor.OS_gdk_window_set_back_pixmap, n, n2, b);
    }
    
    public static long getHandle(final Control control) throws NativeWindowException {
        long n = 0L;
        if (SWTAccessor.isOSX) {
            synchronized (SWTAccessor.swt_osx_init) {
                try {
                    if (null == SWTAccessor.swt_osx_view_id) {
                        SWTAccessor.swt_osx_control_view = Control.class.getField("view");
                        final Object value = SWTAccessor.swt_osx_control_view.get(control);
                        SWTAccessor.swt_osx_view_id = value.getClass().getField("id");
                        n = SWTAccessor.swt_osx_view_id.getLong(value);
                    }
                    else {
                        n = SWTAccessor.swt_osx_view_id.getLong(SWTAccessor.swt_osx_control_view.get(control));
                    }
                }
                catch (Exception ex) {
                    throw new NativeWindowException(ex);
                }
            }
        }
        else {
            try {
                n = SWTAccessor.swt_control_handle.getLong(control);
            }
            catch (Exception ex2) {
                throw new NativeWindowException(ex2);
            }
        }
        if (0L == n) {
            throw new NativeWindowException("Null widget-handle of SWT " + control.getClass().getName() + ": " + control.toString());
        }
        return n;
    }
    
    public static void setRealized(final Control control, final boolean b) throws NativeWindowException {
        if (!b && control.isDisposed()) {
            return;
        }
        final long handle = getHandle(control);
        if (null != SWTAccessor.OS_gtk_class) {
            invoke(true, new Runnable() {
                @Override
                public void run() {
                    if (b) {
                        callStaticMethodL2V(SWTAccessor.OS_gtk_widget_realize, handle);
                    }
                    else if (null != SWTAccessor.OS_gtk_widget_unrealize) {
                        callStaticMethodL2V(SWTAccessor.OS_gtk_widget_unrealize, handle);
                    }
                }
            });
        }
    }
    
    public static AbstractGraphicsDevice getDevice(final Control control) throws NativeWindowException, UnsupportedOperationException {
        final long handle = getHandle(control);
        if (SWTAccessor.isX11GTK) {
            return new X11GraphicsDevice(gdk_window_get_xdisplay(gdk_widget_get_window(handle)), 0, false);
        }
        if (SWTAccessor.isWindows) {
            return new WindowsGraphicsDevice(0);
        }
        if (SWTAccessor.isOSX) {
            return new MacOSXGraphicsDevice(0);
        }
        throw new UnsupportedOperationException("n/a for this windowing system: " + SWTAccessor.nwt);
    }
    
    public static AbstractGraphicsScreen getScreen(final AbstractGraphicsDevice abstractGraphicsDevice, final int n) {
        return NativeWindowFactory.createScreen(abstractGraphicsDevice, n);
    }
    
    public static int getNativeVisualID(final AbstractGraphicsDevice abstractGraphicsDevice, final long n) {
        if (SWTAccessor.isX11) {
            return X11Lib.GetVisualIDFromWindow(abstractGraphicsDevice.getHandle(), n);
        }
        if (SWTAccessor.isWindows || SWTAccessor.isOSX) {
            return 0;
        }
        throw new UnsupportedOperationException("n/a for this windowing system: " + SWTAccessor.nwt);
    }
    
    public static long getWindowHandle(final Control control) throws NativeWindowException, UnsupportedOperationException {
        final long handle = getHandle(control);
        if (0L == handle) {
            throw new NativeWindowException("Null SWT handle of SWT control " + control);
        }
        if (SWTAccessor.isX11GTK) {
            return gdk_window_get_xwindow(gdk_widget_get_window(handle));
        }
        if (SWTAccessor.isWindows || SWTAccessor.isOSX) {
            return handle;
        }
        throw new UnsupportedOperationException("n/a for this windowing system: " + SWTAccessor.nwt);
    }
    
    public static long newGC(final Control control, final GCData gcData) {
        final Object[] array = { null };
        invoke(true, new Runnable() {
            @Override
            public void run() {
                array[0] = ReflectionUtil.callMethod(control, SWTAccessor.swt_control_internal_new_GC, gcData);
            }
        });
        if (array[0] instanceof Number) {
            return ((Number)array[0]).longValue();
        }
        throw new InternalError("SWT internal_new_GC did not return int or long but " + array[0].getClass());
    }
    
    public static void disposeGC(final Control control, final long n, final GCData gcData) {
        invoke(true, new Runnable() {
            @Override
            public void run() {
                if (SWTAccessor.swt_uses_long_handles) {
                    ReflectionUtil.callMethod(control, SWTAccessor.swt_control_internal_dispose_GC, n, gcData);
                }
                else {
                    ReflectionUtil.callMethod(control, SWTAccessor.swt_control_internal_dispose_GC, (int)n, gcData);
                }
            }
        });
    }
    
    public static void invoke(final boolean b, final Runnable runnable) {
        if (SWTAccessor.isOSX) {
            OSXUtil.RunOnMainThread(b, false, runnable);
        }
        else {
            runnable.run();
        }
    }
    
    public static void invoke(final Display display, final boolean b, final Runnable runnable) {
        if (display.isDisposed() || Thread.currentThread() == display.getThread()) {
            invoke(b, runnable);
        }
        else if (b) {
            display.syncExec(runnable);
        }
        else {
            display.asyncExec(runnable);
        }
    }
    
    public static long createCompatibleX11ChildWindow(final AbstractGraphicsScreen abstractGraphicsScreen, final Control control, final int n, final int n2, final int n3) {
        final long gdk_widget_get_window = gdk_widget_get_window(getHandle(control));
        gdk_window_set_back_pixmap(gdk_widget_get_window, 0L, false);
        return X11Lib.CreateWindow(gdk_window_get_xwindow(gdk_widget_get_window), abstractGraphicsScreen.getDevice().getHandle(), abstractGraphicsScreen.getIndex(), n, n2, n3, true, true);
    }
    
    public static void resizeX11Window(final AbstractGraphicsDevice abstractGraphicsDevice, final Rectangle rectangle, final long n) {
        X11Lib.SetWindowPosSize(abstractGraphicsDevice.getHandle(), n, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    
    public static void destroyX11Window(final AbstractGraphicsDevice abstractGraphicsDevice, final long n) {
        X11Lib.DestroyWindow(abstractGraphicsDevice.getHandle(), n);
    }
    
    public static long createCompatibleGDKChildWindow(final Control control, final int n, final int n2, final int n3) {
        return 0L;
    }
    
    public static void showGDKWindow(final long n) {
    }
    
    public static void focusGDKWindow(final long n) {
    }
    
    public static void resizeGDKWindow(final Rectangle rectangle, final long n) {
    }
    
    public static void destroyGDKWindow(final long n) {
    }
    
    static {
        SWTAccessor.swt_osx_init = new Object();
        SWTAccessor.swt_osx_control_view = null;
        SWTAccessor.swt_osx_view_id = null;
        GTK_VERSION_2_14_0 = new VersionNumber(2, 14, 0);
        GTK_VERSION_2_24_0 = new VersionNumber(2, 24, 0);
        GTK_VERSION_3_0_0 = new VersionNumber(3, 0, 0);
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                NativeWindowFactory.initSingleton();
                return null;
            }
        });
        nwt = NativeWindowFactory.getNativeWindowType(false);
        isOSX = (NativeWindowFactory.TYPE_MACOSX == SWTAccessor.nwt);
        isWindows = (NativeWindowFactory.TYPE_WINDOWS == SWTAccessor.nwt);
        isX11 = (NativeWindowFactory.TYPE_X11 == SWTAccessor.nwt);
        Field field = null;
        if (!SWTAccessor.isOSX) {
            try {
                field = Control.class.getField("handle");
            }
            catch (Exception ex) {
                throw new NativeWindowException(ex);
            }
        }
        swt_control_handle = field;
        boolean swt_uses_long_handles2;
        if (null != SWTAccessor.swt_control_handle) {
            swt_uses_long_handles2 = SWTAccessor.swt_control_handle.getGenericType().toString().equals(Long.TYPE.toString());
        }
        else {
            swt_uses_long_handles2 = Platform.is64Bit();
        }
        swt_uses_long_handles = swt_uses_long_handles2;
        Method method;
        try {
            method = ReflectionUtil.getMethod(Control.class, "internal_new_GC", GCData.class);
        }
        catch (Exception ex2) {
            throw new NativeWindowException(ex2);
        }
        swt_control_internal_new_GC = method;
        Method swt_control_internal_dispose_GC2;
        try {
            if (SWTAccessor.swt_uses_long_handles) {
                swt_control_internal_dispose_GC2 = Control.class.getDeclaredMethod("internal_dispose_GC", Long.TYPE, GCData.class);
            }
            else {
                swt_control_internal_dispose_GC2 = Control.class.getDeclaredMethod("internal_dispose_GC", Integer.TYPE, GCData.class);
            }
        }
        catch (NoSuchMethodException ex3) {
            throw new NativeWindowException(ex3);
        }
        swt_control_internal_dispose_GC = swt_control_internal_dispose_GC2;
        Class<?> class1 = null;
        VersionNumber gtk_VERSION = new VersionNumber(0, 0, 0);
        Method declaredMethod = null;
        Method declaredMethod2 = null;
        Method declaredMethod3 = null;
        Method declaredMethod4 = null;
        Method declaredMethod5 = null;
        Method declaredMethod6 = null;
        Method declaredMethod7 = null;
        Method declaredMethod8 = null;
        Method declaredMethod9 = null;
        Method declaredMethod10 = null;
        final Serializable s = SWTAccessor.swt_uses_long_handles ? Long.TYPE : Integer.TYPE;
        if (SWTAccessor.isX11) {
            try {
                class1 = ReflectionUtil.getClass("org.eclipse.swt.internal.gtk.OS", false, SWTAccessor.class.getClassLoader());
                gtk_VERSION = GTK_VERSION(class1.getField("GTK_VERSION").getInt(null));
                declaredMethod = class1.getDeclaredMethod("gtk_widget_realize", s);
                if (gtk_VERSION.compareTo(SWTAccessor.GTK_VERSION_2_14_0) >= 0) {
                    declaredMethod4 = class1.getDeclaredMethod("gtk_widget_get_window", s);
                }
                else {
                    declaredMethod3 = class1.getDeclaredMethod("GTK_WIDGET_WINDOW", s);
                }
                if (gtk_VERSION.compareTo(SWTAccessor.GTK_VERSION_2_24_0) >= 0) {
                    declaredMethod6 = class1.getDeclaredMethod("gdk_x11_display_get_xdisplay", s);
                    declaredMethod7 = class1.getDeclaredMethod("gdk_window_get_display", s);
                }
                else {
                    declaredMethod5 = class1.getDeclaredMethod("gdk_x11_drawable_get_xdisplay", s);
                }
                if (gtk_VERSION.compareTo(SWTAccessor.GTK_VERSION_3_0_0) >= 0) {
                    declaredMethod9 = class1.getDeclaredMethod("gdk_x11_window_get_xid", s);
                }
                else {
                    declaredMethod8 = class1.getDeclaredMethod("gdk_x11_drawable_get_xid", s);
                }
                declaredMethod10 = class1.getDeclaredMethod("gdk_window_set_back_pixmap", s, s, Boolean.TYPE);
            }
            catch (Exception ex4) {
                throw new NativeWindowException(ex4);
            }
            try {
                declaredMethod2 = class1.getDeclaredMethod("gtk_widget_unrealize", s);
            }
            catch (Exception ex5) {}
        }
        OS_gtk_class = class1;
        OS_gtk_version = gtk_VERSION;
        OS_gtk_widget_realize = declaredMethod;
        OS_gtk_widget_unrealize = declaredMethod2;
        OS_GTK_WIDGET_WINDOW = declaredMethod3;
        OS_gtk_widget_get_window = declaredMethod4;
        OS_gdk_x11_drawable_get_xdisplay = declaredMethod5;
        OS_gdk_x11_display_get_xdisplay = declaredMethod6;
        OS_gdk_window_get_display = declaredMethod7;
        OS_gdk_x11_drawable_get_xid = declaredMethod8;
        OS_gdk_x11_window_get_xid = declaredMethod9;
        OS_gdk_window_set_back_pixmap = declaredMethod10;
        isX11GTK = (SWTAccessor.isX11 && null != SWTAccessor.OS_gtk_class);
        System.err.println("SWTAccessor.<init>: GTK Version: " + SWTAccessor.OS_gtk_version);
    }
}
