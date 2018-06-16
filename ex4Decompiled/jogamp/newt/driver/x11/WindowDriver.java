// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.x11;

import com.jogamp.newt.NewtFactory;
import jogamp.newt.driver.PNGIcon;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.newt.event.KeyEvent;
import jogamp.nativewindow.x11.X11Lib;
import com.jogamp.nativewindow.util.Point;
import jogamp.newt.PointerIconImpl;
import com.jogamp.nativewindow.util.InsetsImmutable;
import jogamp.newt.DisplayImpl;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import jogamp.nativewindow.x11.X11Util;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import java.nio.Buffer;
import jogamp.newt.WindowImpl;

public class WindowDriver extends WindowImpl
{
    private static final String WINDOW_CLASS_NAME = "NewtWindow";
    private static final int X11_WHEEL_ONE_UP_BUTTON = 4;
    private static final int X11_WHEEL_ONE_DOWN_BUTTON = 5;
    private static final int X11_WHEEL_TWO_UP_BUTTON = 6;
    private static final int X11_WHEEL_TWO_DOWN_BUTTON = 7;
    private static final int defaultIconDataSize;
    private static final Buffer defaultIconData;
    volatile boolean tempFSAlwaysOnTop;
    private volatile long javaWindowHandle;
    private X11GraphicsDevice renderDevice;
    
    public WindowDriver() {
        this.tempFSAlwaysOnTop = false;
        this.javaWindowHandle = 0L;
    }
    
    @Override
    protected void createNativeImpl() {
        final ScreenDriver screenDriver = (ScreenDriver)this.getScreen();
        final DisplayDriver displayDriver = (DisplayDriver)screenDriver.getDisplay();
        final AbstractGraphicsDevice graphicsDevice = displayDriver.getGraphicsDevice();
        final long openDisplay = X11Util.openDisplay(graphicsDevice.getConnection());
        if (0L == openDisplay) {
            throw new RuntimeException("Error creating display(GfxCfg/Render): " + graphicsDevice.getConnection());
        }
        this.renderDevice = new X11GraphicsDevice(openDisplay, 0, true);
        final X11GraphicsScreen x11GraphicsScreen = new X11GraphicsScreen(this.renderDevice, screenDriver.getIndex());
        final GraphicsConfigurationFactory factory = GraphicsConfigurationFactory.getFactory(displayDriver.getGraphicsDevice(), this.capsRequested);
        final AbstractGraphicsConfiguration chooseGraphicsConfiguration = factory.chooseGraphicsConfiguration(this.capsRequested, this.capsRequested, this.capabilitiesChooser, x11GraphicsScreen, 0);
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("X11Window.createNativeImpl() factory: " + factory + ", chosen config: " + chooseGraphicsConfiguration);
        }
        if (null == chooseGraphicsConfiguration) {
            throw new NativeWindowException("Error choosing GraphicsConfiguration creating window: " + this);
        }
        final int visualID = chooseGraphicsConfiguration.getVisualID(VisualIDHolder.VIDType.NATIVE);
        if (visualID == 0) {
            throw new NativeWindowException("Chosen Configuration w/o native visual ID: " + chooseGraphicsConfiguration);
        }
        this.setGraphicsConfiguration(chooseGraphicsConfiguration);
        final int n = this.getReconfigureMask(0, true) & 0x7FF;
        graphicsDevice.lock();
        try {
            final long[] createWindow = this.CreateWindow(this.getParentWindowHandle(), graphicsDevice.getHandle(), screenDriver.getIndex(), visualID, displayDriver.getJavaObjectAtom(), displayDriver.getWindowDeleteAtom(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), n, WindowDriver.defaultIconDataSize, WindowDriver.defaultIconData, WindowDriver.DEBUG_IMPLEMENTATION);
            if (null == createWindow || 2 != createWindow.length || 0L == createWindow[0] || 0L == createWindow[1]) {
                throw new NativeWindowException("Error creating window");
            }
            if (WindowDriver.DEBUG_IMPLEMENTATION) {
                System.err.println("X11Window.createNativeImpl() handles " + WindowImpl.toHexString(createWindow[0]) + ", " + WindowImpl.toHexString(createWindow[1]));
            }
            this.setWindowHandle(createWindow[0]);
            this.javaWindowHandle = createWindow[1];
        }
        finally {
            graphicsDevice.unlock();
        }
    }
    
    @Override
    protected void closeNativeImpl() {
        if (0L != this.javaWindowHandle && null != this.getScreen()) {
            final DisplayDriver displayDriver = (DisplayDriver)this.getScreen().getDisplay();
            final AbstractGraphicsDevice graphicsDevice = displayDriver.getGraphicsDevice();
            graphicsDevice.lock();
            try {
                this.CloseWindow0(graphicsDevice.getHandle(), this.javaWindowHandle, displayDriver.getRandREventBase(), displayDriver.getRandRErrorBase());
            }
            catch (Throwable t) {
                if (WindowDriver.DEBUG_IMPLEMENTATION) {
                    new Exception("Warning: closeNativeImpl failed - " + Thread.currentThread().getName(), t).printStackTrace();
                }
            }
            finally {
                graphicsDevice.unlock();
                this.javaWindowHandle = 0L;
            }
        }
        if (null != this.renderDevice) {
            this.renderDevice.close();
            this.renderDevice = null;
        }
    }
    
    @Override
    protected final int getSupportedReconfigMaskImpl() {
        return (0x809 | GetSupportedReconfigMask0(this.javaWindowHandle)) & 0x7FFF;
    }
    
    @Override
    protected boolean reconfigureWindowImpl(final int n, final int n2, final int n3, final int n4, int n5) {
        InsetsImmutable insets;
        int n6;
        int n7;
        if (0x0 == (0x10 & n5)) {
            insets = this.getInsets();
            n6 = n - insets.getLeftWidth();
            n7 = n2 - insets.getTopHeight();
        }
        else {
            insets = null;
            n6 = n;
            n7 = n2;
        }
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("X11Window reconfig.0: " + n + "/" + n2 + " -> " + n6 + "/" + n7 + " " + n3 + "x" + n4 + ", insets " + insets + ", " + WindowImpl.getReconfigStateMaskString(n5));
        }
        if (0x0 != (0x200000 & n5)) {
            if (0x0 != (0x800 & n5) && 0x0 == (0x20 & n5) && 0x0 == (0x40 & n5)) {
                this.tempFSAlwaysOnTop = true;
                n5 |= 0x20;
                if (WindowDriver.DEBUG_IMPLEMENTATION) {
                    System.err.println("X11Window reconfig.2: temporary " + WindowImpl.getReconfigStateMaskString(n5));
                }
            }
            else {
                this.tempFSAlwaysOnTop = false;
            }
        }
        this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Object>)new DisplayImpl.DisplayRunnable<Object>() {
            @Override
            public Object run(final long n) {
                reconfigureWindow0(n, WindowDriver.this.getScreenIndex(), WindowDriver.this.getParentWindowHandle(), WindowDriver.this.javaWindowHandle, n6, n7, n3, n4, n5);
                return null;
            }
        });
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("X11Window reconfig.X: " + this.getX() + "/" + this.getY() + " " + this.getWidth() + "x" + this.getHeight() + ", insets " + this.getInsets() + ", " + this.getStateMaskString());
        }
        return true;
    }
    
    @Override
    protected void focusChanged(final boolean b, final boolean b2) {
        if (this.isNativeValid() && this.isFullscreen() && !this.isAlwaysOnBottom() && this.tempFSAlwaysOnTop && this.hasFocus() != b2) {
            final int n = this.getReconfigureMask(134217728, this.isVisible()) | (b2 ? 32 : 0);
            if (WindowDriver.DEBUG_IMPLEMENTATION) {
                System.err.println("X11Window reconfig.3 (focus): temporary " + WindowImpl.getReconfigStateMaskString(n));
            }
            this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Object>)new DisplayImpl.DisplayRunnable<Object>() {
                @Override
                public Object run(final long n) {
                    reconfigureWindow0(n, WindowDriver.this.getScreenIndex(), WindowDriver.this.getParentWindowHandle(), WindowDriver.this.javaWindowHandle, WindowDriver.this.getX(), WindowDriver.this.getY(), WindowDriver.this.getWidth(), WindowDriver.this.getHeight(), n);
                    return null;
                }
            });
        }
        super.focusChanged(b, b2);
    }
    
    protected void reparentNotify(final long n) {
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.reparentNotify (" + WindowImpl.getThreadName() + "): " + WindowImpl.toHexString(this.getParentWindowHandle()) + " -> " + WindowImpl.toHexString(n));
        }
    }
    
    @Override
    protected void requestFocusImpl(final boolean b) {
        this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Object>)new DisplayImpl.DisplayRunnable<Object>() {
            @Override
            public Object run(final long n) {
                requestFocus0(n, WindowDriver.this.javaWindowHandle, b);
                return null;
            }
        });
    }
    
    @Override
    protected void setTitleImpl(final String s) {
        this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Object>)new DisplayImpl.DisplayRunnable<Object>() {
            @Override
            public Object run(final long n) {
                setTitle0(n, WindowDriver.this.javaWindowHandle, s);
                return null;
            }
        });
    }
    
    @Override
    protected void setPointerIconImpl(final PointerIconImpl pointerIconImpl) {
        this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Object>)new DisplayImpl.DisplayRunnable<Object>() {
            @Override
            public Object run(final long n) {
                try {
                    setPointerIcon0(n, WindowDriver.this.javaWindowHandle, (null != pointerIconImpl) ? pointerIconImpl.validatedHandle() : 0L);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        });
    }
    
    @Override
    protected boolean setPointerVisibleImpl(final boolean b) {
        return this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Boolean>)new DisplayImpl.DisplayRunnable<Boolean>() {
            @Override
            public Boolean run(final long n) {
                final PointerIconImpl pointerIconImpl = (PointerIconImpl)WindowDriver.this.getPointerIcon();
                boolean access$700;
                if (b && null != pointerIconImpl) {
                    setPointerIcon0(n, WindowDriver.this.javaWindowHandle, pointerIconImpl.validatedHandle());
                    access$700 = true;
                }
                else {
                    access$700 = setPointerVisible0(n, WindowDriver.this.javaWindowHandle, b);
                }
                return access$700;
            }
        });
    }
    
    @Override
    protected boolean confinePointerImpl(final boolean b) {
        return this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Boolean>)new DisplayImpl.DisplayRunnable<Boolean>() {
            @Override
            public Boolean run(final long n) {
                return confinePointer0(n, WindowDriver.this.javaWindowHandle, b);
            }
        });
    }
    
    @Override
    protected void warpPointerImpl(final int n, final int n2) {
        this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Object>)new DisplayImpl.DisplayRunnable<Object>() {
            @Override
            public Object run(final long n) {
                warpPointer0(n, WindowDriver.this.javaWindowHandle, n, n2);
                return null;
            }
        });
    }
    
    @Override
    protected Point getLocationOnScreenImpl(final int n, final int n2) {
        return this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Point>)new DisplayImpl.DisplayRunnable<Point>() {
            @Override
            public Point run(final long n) {
                return X11Lib.GetRelativeLocation(n, WindowDriver.this.getScreenIndex(), WindowDriver.this.getWindowHandle(), 0L, n, n2);
            }
        });
    }
    
    @Override
    protected final void doMouseEvent(final boolean b, final boolean b2, short n, int n2, final int n3, final int n4, short n5, final float[] array, final float n6) {
        Label_0201: {
            switch (n) {
                case 203: {
                    switch (n5) {
                        case 4:
                        case 5:
                        case 6:
                        case 7: {
                            return;
                        }
                        default: {
                            break Label_0201;
                        }
                    }
                    break;
                }
                case 204: {
                    final boolean b3 = 0x0 != (n2 & 0x1);
                    switch (n5) {
                        case 4: {
                            n = 207;
                            n5 = 1;
                            array[!b3] = 1.0f;
                            break Label_0201;
                        }
                        case 5: {
                            n = 207;
                            n5 = 1;
                            array[!b3] = -1.0f;
                            break Label_0201;
                        }
                        case 6: {
                            n = 207;
                            n5 = 1;
                            array[0] = 1.0f;
                            n2 |= 0x1;
                            break Label_0201;
                        }
                        case 7: {
                            n = 207;
                            n5 = 1;
                            array[0] = -1.0f;
                            n2 |= 0x1;
                            break Label_0201;
                        }
                    }
                    break;
                }
            }
        }
        super.doMouseEvent(b, b2, n, n2, n3, n4, n5, array, n6);
    }
    
    protected final void sendKeyEvent(final short n, final int n2, final short n3, final short n4, final char c, final String s) {
        final boolean modifierKey = KeyEvent.isModifierKey(n3);
        final boolean b = 0x0 != (0x20000000 & n2);
        final char c2 = (null != s) ? s.charAt(0) : c;
        if (!b || !modifierKey) {
            switch (n) {
                case 300: {
                    super.sendKeyEvent((short)300, n2, n3, n4, c2);
                    break;
                }
                case 301: {
                    super.sendKeyEvent((short)301, n2, n3, n4, c2);
                    break;
                }
            }
        }
    }
    
    @Override
    public final void sendKeyEvent(final short n, final int n2, final short n3, final short n4, final char c) {
        throw new InternalError("XXX: Adapt Java Code to Native Code Changes");
    }
    
    @Override
    public final void enqueueKeyEvent(final boolean b, final short n, final int n2, final short n3, final short n4, final char c) {
        throw new InternalError("XXX: Adapt Java Code to Native Code Changes");
    }
    
    private static final String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
    
    private static final void dumpStack() {
        ExceptionUtils.dumpStack(System.err);
    }
    
    private final <T> T runWithLockedDisplayDevice(final DisplayImpl.DisplayRunnable<T> displayRunnable) {
        return ((DisplayDriver)this.getScreen().getDisplay()).runWithLockedDisplayDevice(displayRunnable);
    }
    
    protected static native boolean initIDs0();
    
    private long[] CreateWindow(final long n, final long n2, final int n3, final int n4, final long n5, final long n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final Buffer buffer, final boolean b) {
        if (!Buffers.isDirect(buffer)) {
            throw new IllegalArgumentException("data buffer is not direct " + buffer);
        }
        return this.CreateWindow0(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, buffer, Buffers.getDirectBufferByteOffset(buffer), true, b);
    }
    
    private native long[] CreateWindow0(final long p0, final long p1, final int p2, final int p3, final long p4, final long p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final Object p12, final int p13, final boolean p14, final boolean p15);
    
    private static native int GetSupportedReconfigMask0(final long p0);
    
    private native void CloseWindow0(final long p0, final long p1, final int p2, final int p3);
    
    private static native void reconfigureWindow0(final long p0, final int p1, final long p2, final long p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    private static native void requestFocus0(final long p0, final long p1, final boolean p2);
    
    private static native void setTitle0(final long p0, final long p1, final String p2);
    
    private static native void setPointerIcon0(final long p0, final long p1, final long p2);
    
    private static native boolean setPointerVisible0(final long p0, final long p1, final boolean p2);
    
    private static native boolean confinePointer0(final long p0, final long p1, final boolean p2);
    
    private static native void warpPointer0(final long p0, final long p1, final int p2, final int p3);
    
    static {
        ScreenDriver.initSingleton();
        int defaultIconDataSize2 = 0;
        int n = 0;
        Buffer arrayToX11BGRAImages = null;
        if (PNGIcon.isAvailable()) {
            try {
                final int[] array = { 0 };
                final int[] array2 = { 0 };
                arrayToX11BGRAImages = PNGIcon.arrayToX11BGRAImages(NewtFactory.getWindowIcons(), array, array2);
                defaultIconDataSize2 = array[0];
                n = array2[0];
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        defaultIconDataSize = defaultIconDataSize2;
        defaultIconData = arrayToX11BGRAImages;
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("Def. Icon: data_size " + WindowDriver.defaultIconDataSize + " * elem_size " + n + " = data " + WindowDriver.defaultIconData);
        }
    }
}
