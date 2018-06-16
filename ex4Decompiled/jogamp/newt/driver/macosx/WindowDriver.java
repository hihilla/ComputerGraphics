// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.macosx;

import com.jogamp.newt.event.KeyEvent;
import jogamp.newt.PointerIconImpl;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.newt.Window;
import com.jogamp.nativewindow.util.PointImmutable;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.nativewindow.ScalableSurface;
import com.jogamp.newt.event.MonitorEvent;
import jogamp.newt.ScreenImpl;
import jogamp.nativewindow.macosx.OSXUtil;
import jogamp.nativewindow.SurfaceScaleUtils;
import com.jogamp.opengl.math.FloatUtil;
import jogamp.newt.driver.DriverUpdatePosition;
import jogamp.newt.driver.DriverClearFocus;
import com.jogamp.nativewindow.MutableSurface;
import jogamp.newt.WindowImpl;

public class WindowDriver extends WindowImpl implements MutableSurface, DriverClearFocus, DriverUpdatePosition
{
    private static final int NSBorderlessWindowMask = 0;
    private static final int NSTitledWindowMask = 1;
    private static final int NSClosableWindowMask = 2;
    private static final int NSMiniaturizableWindowMask = 4;
    private static final int NSResizableWindowMask = 8;
    private static final int NSBackingStoreRetained = 0;
    private static final int NSBackingStoreNonretained = 1;
    private static final int NSBackingStoreBuffered = 2;
    private volatile long surfaceHandle;
    private long sscSurfaceHandle;
    private boolean isOffscreenInstance;
    
    public WindowDriver() {
        this.surfaceHandle = 0L;
        this.sscSurfaceHandle = 0L;
        this.isOffscreenInstance = false;
    }
    
    private boolean updatePixelScale(final boolean b, final boolean b2, final boolean b3, final float n, final float n2) {
        final float[] array = new float[2];
        array[1] = (array[0] = (FloatUtil.isZero(n, 1.1920929E-7f) ? 1.0f : n));
        final float n3 = FloatUtil.isZero(n2, 1.1920929E-7f) ? 1.0f : n2;
        this.maxPixelScale[0] = n3;
        this.maxPixelScale[1] = n3;
        if (SurfaceScaleUtils.setNewPixelScale(this.hasPixelScale, this.hasPixelScale, array, this.minPixelScale, this.maxPixelScale, WindowDriver.DEBUG_IMPLEMENTATION ? this.getClass().getName() : null)) {
            if (b) {
                if (b3) {
                    this.superSizeChangedOffThread(b2, this.getWidth(), this.getHeight(), true);
                }
                else {
                    super.sizeChanged(b2, this.getWidth(), this.getHeight(), true);
                }
            }
            else {
                this.defineSize(this.getWidth(), this.getHeight());
            }
            return true;
        }
        return false;
    }
    
    private boolean updatePixelScaleByDisplayID(final boolean b) {
        final float n = (float)OSXUtil.GetPixelScaleByDisplayID(this.getDisplayID());
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("WindowDriver.updatePixelScale.1: " + this.hasPixelScale[0] + ", " + n + " (max)");
        }
        return this.updatePixelScale(b, true, false, n, n);
    }
    
    private boolean updatePixelScaleByWindowHandle(final boolean b) {
        final long windowHandle = this.getWindowHandle();
        if (0L != windowHandle) {
            final float n = (float)OSXUtil.GetPixelScale(windowHandle);
            if (WindowDriver.DEBUG_IMPLEMENTATION) {
                System.err.println("WindowDriver.updatePixelScale.2: " + this.hasPixelScale[0] + ", " + n + " (max)");
            }
            return this.updatePixelScale(b, true, false, n, n);
        }
        return false;
    }
    
    protected void updatePixelScale(final boolean b, final float n, final float n2) {
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("WindowDriver.updatePixelScale.3: " + this.hasPixelScale[0] + " (has) -> " + n + " (new), " + n2 + " (max), drop " + !this.isNativeValid());
        }
        if (this.isNativeValid()) {
            this.updatePixelScale(true, b, true, n, n2);
        }
    }
    
    @Override
    protected final void instantiationFinishedImpl() {
        this.updatePixelScaleByDisplayID(false);
    }
    
    @Override
    protected void setScreen(final ScreenImpl screen) {
        super.setScreen(screen);
        this.updatePixelScaleByDisplayID(false);
    }
    
    @Override
    protected void monitorModeChanged(final MonitorEvent monitorEvent, final boolean b) {
        this.updatePixelScaleByWindowHandle(false);
    }
    
    @Override
    public final boolean setSurfaceScale(final float[] surfaceScale) {
        super.setSurfaceScale(surfaceScale);
        boolean b = false;
        if (this.isNativeValid()) {
            if (this.isOffscreenInstance) {
                final NativeWindow parent = this.getParent();
                if (parent instanceof ScalableSurface) {
                    final ScalableSurface scalableSurface = (ScalableSurface)parent;
                    scalableSurface.setSurfaceScale(this.reqPixelScale);
                    scalableSurface.getMaximumSurfaceScale(this.maxPixelScale);
                    scalableSurface.getMinimumSurfaceScale(this.minPixelScale);
                    b = this.updatePixelScale(true, true, true, scalableSurface.getCurrentSurfaceScale(new float[2])[0], this.maxPixelScale[0]);
                }
                else {
                    b = this.updatePixelScale(true, true, true, this.reqPixelScale[0], this.maxPixelScale[0]);
                }
            }
            else {
                final float[] array = new float[2];
                System.arraycopy(this.hasPixelScale, 0, array, 0, 2);
                if (SurfaceScaleUtils.setNewPixelScale(array, array, this.reqPixelScale, this.minPixelScale, this.maxPixelScale, WindowDriver.DEBUG_IMPLEMENTATION ? this.getClass().getName() : null)) {
                    OSXUtil.RunOnMainThread(true, false, new Runnable() {
                        @Override
                        public void run() {
                            WindowDriver.this.setPixelScale0(WindowDriver.this.getWindowHandle(), WindowDriver.this.surfaceHandle, array[0]);
                        }
                    });
                    b = true;
                }
            }
        }
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("WindowDriver.setPixelScale: min[" + this.minPixelScale[0] + ", " + this.minPixelScale[1] + "], max[" + this.maxPixelScale[0] + ", " + this.maxPixelScale[1] + "], req[" + this.reqPixelScale[0] + ", " + this.reqPixelScale[1] + "] -> result[" + this.hasPixelScale[0] + ", " + this.hasPixelScale[1] + "] - changed " + b + ", realized " + this.isNativeValid());
        }
        return b;
    }
    
    @Override
    protected void createNativeImpl() {
        final AbstractGraphicsConfiguration chooseGraphicsConfiguration = GraphicsConfigurationFactory.getFactory(this.getScreen().getDisplay().getGraphicsDevice(), this.capsRequested).chooseGraphicsConfiguration(this.capsRequested, this.capsRequested, this.capabilitiesChooser, this.getScreen().getGraphicsScreen(), 0);
        if (null == chooseGraphicsConfiguration) {
            throw new NativeWindowException("Error choosing GraphicsConfiguration creating window: " + this);
        }
        this.setGraphicsConfiguration(chooseGraphicsConfiguration);
        this.reconfigureWindowImpl(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getReconfigureMask(Integer.MIN_VALUE, true));
        if (!this.isNativeValid()) {
            throw new NativeWindowException("Error creating window");
        }
    }
    
    @Override
    protected void closeNativeImpl() {
        try {
            if (WindowDriver.DEBUG_IMPLEMENTATION) {
                System.err.println("MacWindow.CloseAction " + Thread.currentThread().getName());
            }
            final long windowHandle = this.getWindowHandle();
            this.visibleChanged(true, false);
            this.setWindowHandle(0L);
            this.surfaceHandle = 0L;
            this.sscSurfaceHandle = 0L;
            this.isOffscreenInstance = false;
            if (0L != windowHandle) {
                OSXUtil.RunOnMainThread(false, true, new Runnable() {
                    @Override
                    public void run() {
                        WindowDriver.this.close0(windowHandle);
                    }
                });
            }
        }
        catch (Throwable t) {
            if (WindowDriver.DEBUG_IMPLEMENTATION) {
                new Exception("Warning: closeNative failed - " + Thread.currentThread().getName(), t).printStackTrace();
            }
        }
    }
    
    @Override
    protected int lockSurfaceImpl() {
        final long windowHandle = this.getWindowHandle();
        final long surfaceHandle = this.surfaceHandle;
        if (0L != surfaceHandle && 0L != windowHandle) {
            return this.lockSurface0(windowHandle, surfaceHandle) ? 3 : 1;
        }
        return 1;
    }
    
    @Override
    protected void unlockSurfaceImpl() {
        final long windowHandle = this.getWindowHandle();
        final long surfaceHandle = this.surfaceHandle;
        if (0L != windowHandle && 0L != surfaceHandle && !this.unlockSurface0(windowHandle, surfaceHandle)) {
            throw new NativeWindowException("Failed to unlock surface, probably not locked!");
        }
    }
    
    @Override
    public final long getSurfaceHandle() {
        return (0L != this.sscSurfaceHandle) ? this.sscSurfaceHandle : this.surfaceHandle;
    }
    
    @Override
    public void setSurfaceHandle(final long sscSurfaceHandle) {
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("MacWindow.setSurfaceHandle(): 0x" + Long.toHexString(sscSurfaceHandle));
        }
        this.sscSurfaceHandle = sscSurfaceHandle;
        if (this.isNativeValid() && 0L != this.sscSurfaceHandle) {
            OSXUtil.RunOnMainThread(false, false, new Runnable() {
                @Override
                public void run() {
                    WindowDriver.this.orderOut0((0L != WindowDriver.this.getParentWindowHandle()) ? WindowDriver.this.getParentWindowHandle() : WindowDriver.this.getWindowHandle());
                }
            });
        }
    }
    
    @Override
    protected void setTitleImpl(final String s) {
        OSXUtil.RunOnMainThread(false, false, new Runnable() {
            @Override
            public void run() {
                WindowDriver.this.setTitle0(WindowDriver.this.getWindowHandle(), s);
            }
        });
    }
    
    @Override
    protected void requestFocusImpl(final boolean b) {
        final boolean fullscreen = this.isFullscreen();
        final boolean isOffscreenInstance = this.isOffscreenInstance;
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("MacWindow: requestFocusImpl(), isOffscreenInstance " + isOffscreenInstance + ", isFullscreen " + fullscreen);
        }
        if (!isOffscreenInstance) {
            OSXUtil.RunOnMainThread(false, false, new Runnable() {
                @Override
                public void run() {
                    WindowDriver.this.requestFocus0(WindowDriver.this.getWindowHandle(), b);
                    if (fullscreen) {
                        WindowDriver.this.focusChanged(false, true);
                    }
                }
            });
        }
        else {
            this.focusChanged(false, true);
        }
    }
    
    @Override
    public final void clearFocus() {
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("MacWindow: clearFocus(), isOffscreenInstance " + this.isOffscreenInstance);
        }
        if (!this.isOffscreenInstance) {
            OSXUtil.RunOnMainThread(false, false, new Runnable() {
                @Override
                public void run() {
                    WindowDriver.this.resignFocus0(WindowDriver.this.getWindowHandle());
                }
            });
        }
        else {
            this.focusChanged(false, false);
        }
    }
    
    private boolean useParent(final NativeWindow nativeWindow) {
        return null != nativeWindow && 0L != nativeWindow.getWindowHandle();
    }
    
    @Override
    public void updatePosition(final int n, final int n2) {
        final long windowHandle = this.getWindowHandle();
        if (0L != windowHandle && !this.isOffscreenInstance) {
            final NativeWindow parent = this.getParent();
            final boolean useParent = this.useParent(parent);
            Point locationOnScreenByParent;
            if (useParent) {
                locationOnScreenByParent = this.getLocationOnScreenByParent(n, n2, parent);
            }
            else {
                locationOnScreenByParent = (Point)getLocationOnScreen0(windowHandle, n, n2);
            }
            if (WindowDriver.DEBUG_IMPLEMENTATION) {
                System.err.println("MacWindow: updatePosition() parent[" + useParent + " " + parent.getX() + "/" + parent.getY() + "] " + n + "/" + n2 + " ->  " + n + "/" + n2 + " rel-client-pos, " + locationOnScreenByParent + " screen-client-pos");
            }
            OSXUtil.RunOnMainThread(false, false, new Runnable() {
                @Override
                public void run() {
                    WindowDriver.this.setWindowClientTopLeftPoint0(WindowDriver.this.getWindowHandle(), locationOnScreenByParent.getX(), locationOnScreenByParent.getY(), WindowDriver.this.isVisible());
                }
            });
            this.positionChanged(true, n, n2);
        }
    }
    
    @Override
    protected final int getSupportedReconfigMaskImpl() {
        return 16381;
    }
    
    @Override
    protected boolean reconfigureWindowImpl(int n, int n2, int n3, int n4, final int n5) {
        final boolean offscreenInstance = WindowImpl.isOffscreenInstance(this, this.getParent());
        this.isOffscreenInstance = (0L != this.sscSurfaceHandle || offscreenInstance);
        Point locationOnScreenByParent;
        if (this.isOffscreenInstance) {
            n = 0;
            n2 = 0;
            locationOnScreenByParent = new Point(0, 0);
        }
        else {
            final NativeWindow parent = this.getParent();
            if (this.useParent(parent)) {
                locationOnScreenByParent = this.getLocationOnScreenByParent(n, n2, parent);
            }
            else {
                if (0x0 != (0xC00000 & n5)) {
                    final int[] array = { n, n2, n3, n4 };
                    this.reconfigMaximizedManual(n5, array, this.getInsets());
                    n = array[0];
                    n2 = array[1];
                    n3 = array[2];
                    n4 = array[3];
                }
                locationOnScreenByParent = new Point(n, n2);
            }
        }
        final int n6 = n;
        final int n7 = n2;
        final int n8 = n3;
        final int n9 = n4;
        final boolean hasFocus = this.hasFocus();
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            final AbstractGraphicsConfiguration graphicsConfiguration = this.getGraphicsConfiguration();
            final NativeWindow parent2 = this.getParent();
            final AbstractGraphicsConfiguration abstractGraphicsConfiguration = (null != parent2) ? parent2.getGraphicsConfiguration() : null;
            System.err.println("MacWindow reconfig.0: " + n6 + "/" + n7 + " -> clientPosOnScreen " + locationOnScreenByParent + " - " + n8 + "x" + n9 + ", " + WindowImpl.getReconfigStateMaskString(n5) + ",\n\t parent type " + ((null != parent2) ? parent2.getClass().getName() : null) + ",\n\t   this-chosenCaps " + ((null != graphicsConfiguration) ? graphicsConfiguration.getChosenCapabilities() : null) + ",\n\t parent-chosenCaps " + ((null != abstractGraphicsConfiguration) ? abstractGraphicsConfiguration.getChosenCapabilities() : null) + ", isOffscreenInstance(sscSurfaceHandle " + WindowImpl.toHexString(this.sscSurfaceHandle) + ", ioi: " + offscreenInstance + ") -> " + this.isOffscreenInstance);
        }
        if (0x0 != (Integer.MIN_VALUE & n5) && 0x0 == (0x1 & n5)) {
            if (!this.isOffscreenInstance) {
                OSXUtil.RunOnMainThread(false, false, new Runnable() {
                    @Override
                    public void run() {
                        WindowDriver.this.orderOut0(WindowDriver.this.getWindowHandle());
                        WindowDriver.this.visibleChanged(true, false);
                    }
                });
            }
            else {
                this.visibleChanged(true, false);
            }
        }
        final long windowHandle = this.getWindowHandle();
        if ((0L == windowHandle && 0x0 != (0x1 & n5)) || 0x0 != (0x20000000 & n5) || 0x0 != (0x10000000 & n5) || 0x0 != (0x8000000 & n5) || 0x0 != (0x4000000 & n5) || 0x0 != (0x1000000 & n5) || 0x0 != (0x200000 & n5)) {
            if (this.isOffscreenInstance) {
                this.createWindow(true, 0L != windowHandle, locationOnScreenByParent, 64, 64, n5);
            }
            else {
                this.createWindow(false, 0L != windowHandle, locationOnScreenByParent, n8, n9, n5);
            }
            this.updatePixelScaleByWindowHandle(false);
            if (this.isOffscreenInstance) {
                super.sizeChanged(false, n8, n9, true);
                this.positionChanged(false, n6, n7);
            }
            else {
                this.updateSizePosInsets0(this.getWindowHandle(), false);
            }
            this.visibleChanged(false, 0x0 != (0x1 & n5));
            if (hasFocus) {
                this.requestFocusImpl(true);
            }
        }
        else {
            if (0L == windowHandle) {
                throw new InternalError("Null windowHandle but no re-creation triggered, check visibility: " + this.getStateMaskString());
            }
            if (n8 > 0 && n9 > 0) {
                if (!this.isOffscreenInstance) {
                    OSXUtil.RunOnMainThread(true, false, new Runnable() {
                        @Override
                        public void run() {
                            WindowDriver.this.setWindowClientTopLeftPointAndSize0(windowHandle, locationOnScreenByParent.getX(), locationOnScreenByParent.getY(), n8, n9, 0x0 != (0x1 & n5));
                        }
                    });
                    this.updateSizePosInsets0(windowHandle, false);
                }
                else {
                    super.sizeChanged(false, n8, n9, false);
                    this.positionChanged(false, n6, n7);
                }
            }
            if (0x0 != (Integer.MIN_VALUE & n5) && 0x0 != (0x1 & n5)) {
                if (!this.isOffscreenInstance) {
                    OSXUtil.RunOnMainThread(false, false, new Runnable() {
                        @Override
                        public void run() {
                            WindowDriver.this.orderFront0(WindowDriver.this.getWindowHandle());
                            WindowDriver.this.visibleChanged(true, true);
                        }
                    });
                }
                else {
                    this.visibleChanged(true, true);
                }
            }
        }
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("MacWindow reconfig.X: " + this.getLocationOnScreenImpl(0, 0) + " " + this.getWidth() + "x" + this.getHeight() + ", insets " + this.getInsets() + ", " + this.getStateMaskString());
        }
        return true;
    }
    
    @Override
    protected Point getLocationOnScreenImpl(final int n, final int n2) {
        final NativeWindow parent = this.getParent();
        if (this.useParent(parent)) {
            return this.getLocationOnScreenByParent(n, n2, parent);
        }
        final long windowHandle = this.getWindowHandle();
        if (!this.isOffscreenInstance && 0L != windowHandle) {
            return (Point)getLocationOnScreen0(windowHandle, n, n2);
        }
        return new Point(n, n2);
    }
    
    private Point getLocationOnScreenByParent(final int n, final int n2, final NativeWindow nativeWindow) {
        return new Point(n, n2).translate(nativeWindow.getLocationOnScreen(null));
    }
    
    protected void screenPositionChanged(final boolean b, final int n, final int n2) {
        if (this.isNativeValid()) {
            final NativeWindow parent = this.getParent();
            if (!this.useParent(parent) || this.isOffscreenInstance) {
                if (WindowDriver.DEBUG_IMPLEMENTATION) {
                    System.err.println("MacWindow.positionChanged.0 (Screen Pos - TOP): (" + WindowImpl.getThreadName() + "): (defer: " + b + ") " + this.getX() + "/" + this.getY() + " -> " + n + "/" + n2);
                }
                this.positionChanged(b, n, n2);
            }
            else {
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        final Point point = new Point(n, n2);
                        final Point locationOnScreen = parent.getLocationOnScreen(null);
                        point.translate(locationOnScreen.scale(-1, -1));
                        if (Window.DEBUG_IMPLEMENTATION) {
                            System.err.println("MacWindow.positionChanged.1 (Screen Pos - CHILD): (" + WindowImpl.getThreadName() + "): (defer: " + b + ") " + WindowDriver.this.getX() + "/" + WindowDriver.this.getY() + " -> absPos " + n + "/" + n2 + ", parentOnScreen " + locationOnScreen + " -> " + point);
                        }
                        WindowDriver.this.positionChanged(false, point.getX(), point.getY());
                    }
                };
                if (b) {
                    new InterruptSource.Thread(null, runnable).start();
                }
                else {
                    runnable.run();
                }
            }
        }
        else if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("MacWindow.positionChanged.2 (Screen Pos - IGN): (" + WindowImpl.getThreadName() + "): (defer: " + b + ") " + this.getX() + "/" + this.getY() + " -> " + n + "/" + n2);
        }
    }
    
    @Override
    protected void sizeChanged(final boolean b, final int n, final int n2, final boolean b2) {
        if (b2 || this.getWidth() != n || this.getHeight() != n2) {
            if (this.isNativeValid() && !this.isOffscreenInstance) {
                final NativeWindow parent = this.getParent();
                final boolean useParent = this.useParent(parent);
                if (useParent) {
                    final int x = this.getX();
                    final int y = this.getY();
                    final Point locationOnScreenByParent = this.getLocationOnScreenByParent(x, y, parent);
                    if (WindowDriver.DEBUG_IMPLEMENTATION) {
                        System.err.println("MacWindow: sizeChanged() parent[" + useParent + " " + x + "/" + y + "] " + this.getX() + "/" + this.getY() + " " + n + "x" + n2 + " ->  " + locationOnScreenByParent + " screen-client-pos");
                    }
                    OSXUtil.RunOnMainThread(false, false, new Runnable() {
                        @Override
                        public void run() {
                            WindowDriver.this.setWindowClientTopLeftPoint0(WindowDriver.this.getWindowHandle(), locationOnScreenByParent.getX(), locationOnScreenByParent.getY(), WindowDriver.this.isVisible());
                        }
                    });
                }
            }
            this.superSizeChangedOffThread(b, n, n2, b2);
        }
    }
    
    private void superSizeChangedOffThread(final boolean b, final int n, final int n2, final boolean b2) {
        if (b) {
            new InterruptSource.Thread() {
                @Override
                public void run() {
                    WindowDriver.this.sizeChanged(false, n, n2, b2);
                }
            }.start();
        }
        else {
            this.sizeChanged(false, n, n2, b2);
        }
    }
    
    protected void sizeScreenPosInsetsChanged(final boolean b, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b2) {
        this.sizeChanged(b, n3, n4, b2);
        this.screenPositionChanged(b, n, n2);
        this.insetsChanged(b, n5, n6, n7, n8);
    }
    
    @Override
    protected void setPointerIconImpl(final PointerIconImpl pointerIconImpl) {
        if (!this.isOffscreenInstance) {
            OSXUtil.RunOnMainThread(true, false, new Runnable() {
                final /* synthetic */ long val$piHandle = (null != pointerIconImpl) ? pointerIconImpl.validatedHandle() : 0L;
                
                @Override
                public void run() {
                    setPointerIcon0(WindowDriver.this.getWindowHandle(), this.val$piHandle);
                }
            });
        }
    }
    
    @Override
    protected boolean setPointerVisibleImpl(final boolean b) {
        if (!this.isOffscreenInstance) {
            OSXUtil.RunOnMainThread(false, false, new Runnable() {
                @Override
                public void run() {
                    setPointerVisible0(WindowDriver.this.getWindowHandle(), WindowDriver.this.hasFocus(), b);
                }
            });
            return true;
        }
        return false;
    }
    
    @Override
    protected boolean confinePointerImpl(final boolean b) {
        if (!this.isOffscreenInstance) {
            confinePointer0(this.getWindowHandle(), b);
            return true;
        }
        return false;
    }
    
    @Override
    protected void warpPointerImpl(final int n, final int n2) {
        if (!this.isOffscreenInstance) {
            warpPointer0(this.getWindowHandle(), SurfaceScaleUtils.scaleInv(n, this.getPixelScaleX()), SurfaceScaleUtils.scaleInv(n2, this.getPixelScaleY()));
        }
    }
    
    @Override
    protected final void doMouseEvent(final boolean b, final boolean b2, final short n, final int n2, final int n3, final int n4, final short n5, final float[] array, final float n6) {
        super.doMouseEvent(b, b2, n, n2, SurfaceScaleUtils.scale(n3, this.getPixelScaleX()), SurfaceScaleUtils.scale(n4, this.getPixelScaleY()), n5, array, n6);
    }
    
    @Override
    public final void sendKeyEvent(final short n, final int n2, final short n3, final short n4, final char c) {
        throw new InternalError("XXX: Adapt Java Code to Native Code Changes");
    }
    
    @Override
    public final void enqueueKeyEvent(final boolean b, final short n, final int n2, final short n3, final short n4, final char c) {
        throw new InternalError("XXX: Adapt Java Code to Native Code Changes");
    }
    
    protected final void enqueueKeyEvent(final boolean b, final short n, int n2, final short n3, final char c, final char c2) {
        final short validateKeyCode = MacKeyUtil.validateKeyCode(n3, c);
        final short n4 = (short)(('\0' != c2) ? KeyEvent.utf16ToVKey(c2) : 0);
        final int n5 = (n4 != 0) ? n4 : validateKeyCode;
        switch (n) {
            case 301: {
                if (this.isKeyCodeTracked(validateKeyCode)) {
                    this.setKeyPressed(validateKeyCode, false);
                    break;
                }
                break;
            }
            case 300: {
                if (this.isKeyCodeTracked(validateKeyCode) && this.setKeyPressed(validateKeyCode, true)) {
                    n2 |= 0x20000000;
                    super.enqueueKeyEvent(b, (short)301, n2, validateKeyCode, (short)n5, c);
                    break;
                }
                break;
            }
        }
        super.enqueueKeyEvent(b, n, n2, validateKeyCode, (short)n5, c);
    }
    
    protected int getDisplayID() {
        if (!this.isOffscreenInstance) {
            return this.getDisplayID0(this.getWindowHandle());
        }
        return 0;
    }
    
    private void createWindow(final boolean b, final boolean b2, final PointImmutable pointImmutable, final int n, final int n2, final int n3) {
        final long parentWindowHandle = this.getParentWindowHandle();
        final long windowHandle = this.getWindowHandle();
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("MacWindow.createWindow on thread " + Thread.currentThread().getName() + ": offscreen " + b + ", recreate " + b2 + ", pS " + pointImmutable + ", " + n + "x" + n2 + ", state " + WindowImpl.getReconfigStateMaskString(n3) + ", preWinHandle " + WindowImpl.toHexString(windowHandle) + ", parentWin " + WindowImpl.toHexString(parentWindowHandle) + ", surfaceHandle " + WindowImpl.toHexString(this.surfaceHandle));
        }
        try {
            if (0L != windowHandle) {
                this.setWindowHandle(0L);
                if (0L == this.surfaceHandle) {
                    throw new NativeWindowException("Internal Error - create w/ window, but no Newt NSView");
                }
                OSXUtil.RunOnMainThread(false, false, new Runnable() {
                    @Override
                    public void run() {
                        WindowDriver.this.changeContentView0(parentWindowHandle, windowHandle, 0L);
                        WindowDriver.this.close0(windowHandle);
                    }
                });
            }
            else {
                if (0L != this.surfaceHandle) {
                    throw new NativeWindowException("Internal Error - create w/o window, but has Newt NSView");
                }
                this.surfaceHandle = this.createView0(pointImmutable.getX(), pointImmutable.getY(), n, n2);
                if (0L == this.surfaceHandle) {
                    throw new NativeWindowException("Could not create native view " + Thread.currentThread().getName() + " " + this);
                }
            }
            int n4;
            if (0x0 != (0x10 & n3) || b) {
                n4 = 0;
            }
            else {
                n4 = 7;
                if (0x0 != (0x100 & n3)) {
                    n4 |= 0x8;
                }
            }
            final long window0 = this.createWindow0(pointImmutable.getX(), pointImmutable.getY(), n, n2, 0x0 != (0x800 & n3), n4, 2, this.surfaceHandle);
            if (window0 == 0L) {
                throw new NativeWindowException("Could not create native window " + Thread.currentThread().getName() + " " + this);
            }
            this.setWindowHandle(window0);
            OSXUtil.RunOnMainThread(true, false, new Runnable() {
                final /* synthetic */ boolean val$isOpaque = this.getGraphicsConfiguration().getChosenCapabilities().isBackgroundOpaque() && !b;
                
                @Override
                public void run() {
                    WindowDriver.this.initWindow0(parentWindowHandle, window0, pointImmutable.getX(), pointImmutable.getY(), n, n2, WindowDriver.this.reqPixelScale[0], this.val$isOpaque, !b && 0x0 != (0x20 & n3), !b && 0x0 != (0x40 & n3), !b && 0x0 != (0x1 & n3), WindowDriver.this.surfaceHandle);
                    if (b) {
                        WindowDriver.this.orderOut0((0L != parentWindowHandle) ? parentWindowHandle : window0);
                    }
                    else {
                        WindowDriver.this.setTitle0(window0, WindowDriver.this.getTitle());
                    }
                }
            });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    protected static native boolean initIDs0();
    
    private native long createView0(final int p0, final int p1, final int p2, final int p3);
    
    private native long createWindow0(final int p0, final int p1, final int p2, final int p3, final boolean p4, final int p5, final int p6, final long p7);
    
    private native void initWindow0(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final float p6, final boolean p7, final boolean p8, final boolean p9, final boolean p10, final long p11);
    
    private native int getDisplayID0(final long p0);
    
    private native void setPixelScale0(final long p0, final long p1, final float p2);
    
    private native boolean lockSurface0(final long p0, final long p1);
    
    private native boolean unlockSurface0(final long p0, final long p1);
    
    private native void requestFocus0(final long p0, final boolean p1);
    
    private native void resignFocus0(final long p0);
    
    private native void orderOut0(final long p0);
    
    private native void orderFront0(final long p0);
    
    private native void close0(final long p0);
    
    private native void setTitle0(final long p0, final String p1);
    
    private native long contentView0(final long p0);
    
    private native void changeContentView0(final long p0, final long p1, final long p2);
    
    private native void setWindowClientTopLeftPointAndSize0(final long p0, final int p1, final int p2, final int p3, final int p4, final boolean p5);
    
    private native void setWindowClientTopLeftPoint0(final long p0, final int p1, final int p2, final boolean p3);
    
    private native void updateSizePosInsets0(final long p0, final boolean p1);
    
    private static native Object getLocationOnScreen0(final long p0, final int p1, final int p2);
    
    private static native void setPointerIcon0(final long p0, final long p1);
    
    private static native void setPointerVisible0(final long p0, final boolean p1, final boolean p2);
    
    private static native void confinePointer0(final long p0, final boolean p1);
    
    private static native void warpPointer0(final long p0, final int p1, final int p2);
    
    static {
        DisplayDriver.initSingleton();
    }
}
