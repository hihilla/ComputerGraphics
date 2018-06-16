// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.windows;

import java.nio.ByteBuffer;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.nativewindow.util.Point;
import jogamp.newt.PointerIconImpl;
import com.jogamp.nativewindow.util.InsetsImmutable;
import com.jogamp.common.util.VersionNumber;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.windows.GDIUtil;
import com.jogamp.common.os.Platform;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import jogamp.nativewindow.windows.GDI;
import jogamp.newt.WindowImpl;

public class WindowDriver extends WindowImpl
{
    private long hmon;
    private long hdc;
    private long hdc_old;
    private long windowHandleClose;
    private short repeatedKey;
    
    public WindowDriver() {
        this.repeatedKey = 0;
    }
    
    @Override
    protected int lockSurfaceImpl() {
        if (0L != this.hdc) {
            throw new InternalError("surface not released");
        }
        final long windowHandle = this.getWindowHandle();
        this.hdc = GDI.GetDC(windowHandle);
        if (0L == this.hdc) {
            return 1;
        }
        this.hmon = this.MonitorFromWindow0(windowHandle);
        return 3;
    }
    
    @Override
    protected void unlockSurfaceImpl() {
        if (0L != this.hdc) {
            GDI.ReleaseDC(this.getWindowHandle(), this.hdc);
            this.hdc_old = this.hdc;
            this.hdc = 0L;
        }
    }
    
    @Override
    public final long getSurfaceHandle() {
        return this.hdc;
    }
    
    @Override
    public boolean hasDeviceChanged() {
        if (0L != this.getWindowHandle()) {
            final long monitorFromWindow0 = this.MonitorFromWindow0(this.getWindowHandle());
            if (this.hmon != monitorFromWindow0) {
                if (WindowDriver.DEBUG_IMPLEMENTATION) {
                    System.err.println("Info: Window Device Changed " + Thread.currentThread().getName() + ", HMON " + WindowImpl.toHexString(this.hmon) + " -> " + WindowImpl.toHexString(monitorFromWindow0));
                }
                this.hmon = monitorFromWindow0;
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected void createNativeImpl() {
        final ScreenDriver screenDriver = (ScreenDriver)this.getScreen();
        final DisplayDriver displayDriver = (DisplayDriver)screenDriver.getDisplay();
        final AbstractGraphicsConfiguration chooseGraphicsConfiguration = GraphicsConfigurationFactory.getFactory(displayDriver.getGraphicsDevice(), this.capsRequested).chooseGraphicsConfiguration(this.capsRequested, this.capsRequested, this.capabilitiesChooser, screenDriver.getGraphicsScreen(), 0);
        if (null == chooseGraphicsConfiguration) {
            throw new NativeWindowException("Error choosing GraphicsConfiguration creating window: " + this);
        }
        this.setGraphicsConfiguration(chooseGraphicsConfiguration);
        final VersionNumber osVersionNumber = Platform.getOSVersionNumber();
        int n = this.getReconfigureMask(0, true) & 0x7FF;
        int n2 = 0;
        if (0x0 != (0x400 & n)) {
            n |= 0x400000;
            ++n2;
        }
        if (0x0 != (0x200 & n)) {
            n |= 0x800000;
            ++n2;
        }
        final long createWindow0 = this.CreateWindow0(DisplayDriver.getHInstance(), displayDriver.getWindowClassName(), displayDriver.getWindowClassName(), osVersionNumber.getMajor(), osVersionNumber.getMinor(), this.getParentWindowHandle(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), n);
        if (0L == createWindow0) {
            throw new NativeWindowException("Error creating window");
        }
        if (!chooseGraphicsConfiguration.getChosenCapabilities().isBackgroundOpaque()) {
            GDIUtil.DwmSetupTranslucency(createWindow0, true);
        }
        this.InitWindow0(createWindow0, n);
        this.setWindowHandle(createWindow0);
        this.windowHandleClose = createWindow0;
        if (0x0 == (0x4 & n) && n2) {
            this.reconfigureWindowImpl(this.getX(), this.getY(), this.getWidth(), this.getHeight(), n);
        }
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            new Exception("Info: Window new window handle " + Thread.currentThread().getName() + " (Parent HWND " + WindowImpl.toHexString(this.getParentWindowHandle()) + ") : HWND " + WindowImpl.toHexString(createWindow0) + ", " + Thread.currentThread()).printStackTrace();
        }
    }
    
    @Override
    protected void closeNativeImpl() {
        if (0L != this.windowHandleClose) {
            if (0L != this.hdc) {
                try {
                    GDI.ReleaseDC(this.windowHandleClose, this.hdc);
                }
                catch (Throwable t) {
                    if (WindowDriver.DEBUG_IMPLEMENTATION) {
                        new Exception("Warning: closeNativeImpl failed - " + Thread.currentThread().getName(), t).printStackTrace();
                    }
                }
            }
            try {
                GDI.DestroyWindow(this.windowHandleClose);
            }
            catch (Throwable t2) {
                if (WindowDriver.DEBUG_IMPLEMENTATION) {
                    new Exception("Warning: closeNativeImpl failed - " + Thread.currentThread().getName(), t2).printStackTrace();
                }
            }
        }
        this.windowHandleClose = 0L;
        this.hdc = 0L;
        this.hdc_old = 0L;
    }
    
    @Override
    protected final int getSupportedReconfigMaskImpl() {
        return 16253;
    }
    
    @Override
    protected boolean reconfigureWindowImpl(int n, int n2, int n3, int n4, final int n5) {
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("WindowsWindow reconfig.0: " + n + "/" + n2 + " " + n3 + "x" + n4 + ", " + WindowImpl.getReconfigStateMaskString(n5));
        }
        final InsetsImmutable insets = this.getInsets();
        if (0x0 == (0x4 & n5) && 0x0 != (0xC00000 & n5)) {
            final int[] array = { n, n2, n3, n4 };
            if (0x0 != (0x400 & n5) == (0x0 != (0x200 & n5))) {
                this.resetMaximizedManual(array);
            }
            else {
                this.reconfigMaximizedManual(n5, array, insets);
            }
            n = array[0];
            n2 = array[1];
            n3 = array[2];
            n4 = array[3];
        }
        final boolean b = 0x0 != (0x10000000 & n5);
        final boolean b2 = !this.getChosenCapabilities().isBackgroundOpaque();
        if (b && b2) {
            GDIUtil.DwmSetupTranslucency(this.getWindowHandle(), false);
        }
        this.reconfigureWindow0(this.getParentWindowHandle(), this.getWindowHandle(), n, n2, n3, n4, n5);
        if (b && b2) {
            GDIUtil.DwmSetupTranslucency(this.getWindowHandle(), true);
        }
        if (0x0 != (Integer.MIN_VALUE & n5)) {
            this.visibleChanged(false, 0x0 != (0x1 & n5));
        }
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("WindowsWindow reconfig.X: " + this.getX() + "/" + this.getY() + " " + this.getWidth() + "x" + this.getHeight() + ", " + this.getStateMaskString());
        }
        return true;
    }
    
    @Override
    protected void requestFocusImpl(final boolean b) {
        this.requestFocus0(this.getWindowHandle(), b);
    }
    
    @Override
    protected void setTitleImpl(final String s) {
        setTitle0(this.getWindowHandle(), s);
    }
    
    @Override
    protected void setPointerIconImpl(final PointerIconImpl pointerIconImpl) {
        setPointerIcon0(this.getWindowHandle(), (null != pointerIconImpl) ? pointerIconImpl.validatedHandle() : 0L);
    }
    
    @Override
    protected boolean setPointerVisibleImpl(final boolean b) {
        final boolean[] array = { false };
        this.runOnEDTIfAvail(true, new Runnable() {
            @Override
            public void run() {
                array[0] = setPointerVisible0(WindowDriver.this.getWindowHandle(), b);
            }
        });
        return array[0];
    }
    
    @Override
    protected boolean confinePointerImpl(final boolean b) {
        final Boolean[] array = { Boolean.FALSE };
        this.runOnEDTIfAvail(true, new Runnable() {
            @Override
            public void run() {
                final Point access$100 = WindowDriver.this.convertToPixelUnits(WindowDriver.this.getLocationOnScreenImpl(0, 0));
                array[0] = confinePointer0(WindowDriver.this.getWindowHandle(), b, access$100.getX(), access$100.getY(), access$100.getX() + WindowDriver.this.getSurfaceWidth(), access$100.getY() + WindowDriver.this.getSurfaceHeight());
            }
        });
        return array[0];
    }
    
    @Override
    protected void warpPointerImpl(final int n, final int n2) {
        this.runOnEDTIfAvail(true, new Runnable() {
            @Override
            public void run() {
                final Point access$300 = WindowDriver.this.convertToPixelUnits(WindowDriver.this.getLocationOnScreenImpl(n, n2));
                warpPointer0(WindowDriver.this.getWindowHandle(), access$300.getX(), access$300.getY());
            }
        });
    }
    
    @Override
    protected Point getLocationOnScreenImpl(final int n, final int n2) {
        return GDIUtil.GetRelativeLocation(this.getWindowHandle(), 0L, n, n2);
    }
    
    public final void sendTouchScreenEvent(final short n, final int n2, final int n3, final int[] array, final int[] array2, final int[] array3, final float[] array4, final float n4) {
        final int length = array.length;
        final MouseEvent.PointerType[] array5 = new MouseEvent.PointerType[length];
        for (int i = length - 1; i >= 0; --i) {
            array5[i] = MouseEvent.PointerType.TouchScreen;
        }
        this.doPointerEvent(false, false, array5, n, n2, n3, false, array, array2, array3, array4, n4, new float[] { 0.0f, 0.0f, 0.0f }, 1.0f);
    }
    
    private final boolean handlePressTypedAutoRepeat(final boolean b, int n, final short repeatedKey, final short n2, final char c) {
        if (this.setKeyPressed(repeatedKey, true)) {
            final boolean b2 = this.repeatedKey == repeatedKey;
            this.repeatedKey = repeatedKey;
            if (!b) {
                n |= 0x20000000;
                if (b2) {
                    super.sendKeyEvent((short)300, n, repeatedKey, n2, c);
                }
                super.sendKeyEvent((short)301, n, repeatedKey, n2, c);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public final void sendKeyEvent(final short n, final int n2, final short n3, final short n4, final char c) {
        final boolean modifierKey = KeyEvent.isModifierKey(n4);
        switch (n) {
            case 301: {
                if (this.isKeyCodeTracked(n3)) {
                    if (this.repeatedKey == n3 && !modifierKey) {
                        super.sendKeyEvent((short)300, n2 | 0x20000000, n3, n4, c);
                    }
                    this.setKeyPressed(n3, false);
                    this.repeatedKey = 0;
                }
                super.sendKeyEvent((short)301, n2, n3, n4, c);
                break;
            }
            case 300: {
                if (!this.handlePressTypedAutoRepeat(modifierKey, n2, n3, n4, c)) {
                    super.sendKeyEvent((short)300, n2, n3, n4, c);
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public final void enqueueKeyEvent(final boolean b, final short n, final int n2, final short n3, final short n4, final char c) {
        throw new InternalError("XXX: Adapt Java Code to Native Code Changes");
    }
    
    protected static native long getNewtWndProc0();
    
    protected static native boolean initIDs0(final long p0);
    
    private native long CreateWindow0(final long p0, final String p1, final String p2, final int p3, final int p4, final long p5, final int p6, final int p7, final int p8, final int p9, final int p10);
    
    private native void InitWindow0(final long p0, final int p1);
    
    private native long MonitorFromWindow0(final long p0);
    
    private native void reconfigureWindow0(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    private static native void setTitle0(final long p0, final String p1);
    
    private native void requestFocus0(final long p0, final boolean p1);
    
    private static native boolean setPointerVisible0(final long p0, final boolean p1);
    
    private static native boolean confinePointer0(final long p0, final boolean p1, final int p2, final int p3, final int p4, final int p5);
    
    private static native void warpPointer0(final long p0, final int p1, final int p2);
    
    private static native ByteBuffer newDirectByteBuffer(final long p0, final long p1);
    
    private static native void setPointerIcon0(final long p0, final long p1);
    
    static {
        DisplayDriver.initSingleton();
    }
}
