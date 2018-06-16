// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.awt;

import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.common.os.Platform;
import java.awt.Insets;
import jogamp.nativewindow.awt.AWTMisc;
import java.awt.Dimension;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.newt.event.awt.AWTWindowAdapter;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTMouseAdapter;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import com.jogamp.newt.event.WindowListener;
import java.awt.Frame;
import java.awt.Container;
import jogamp.newt.WindowImpl;

public class WindowDriver extends WindowImpl
{
    private boolean owningFrame;
    private Container awtContainer;
    private Frame awtFrame;
    private AWTCanvas awtCanvas;
    private volatile boolean withinLocalDispose;
    private final AWTCanvas.UpstreamScalable upstreamScalable;
    
    public WindowDriver() {
        this(null);
    }
    
    public static Class<?>[] getCustomConstructorArgumentTypes() {
        return (Class<?>[])new Class[] { Container.class };
    }
    
    public WindowDriver(final Container awtContainer) {
        this.awtContainer = null;
        this.awtFrame = null;
        this.upstreamScalable = new AWTCanvas.UpstreamScalable() {
            @Override
            public float[] getReqPixelScale() {
                return WindowDriver.this.reqPixelScale;
            }
            
            @Override
            public void setHasPixelScale(final float[] array) {
                System.arraycopy(array, 0, WindowDriver.this.hasPixelScale, 0, 2);
            }
        };
        this.withinLocalDispose = false;
        this.addWindowListener(0, new NEWTWindowListener());
        this.awtContainer = awtContainer;
        if (awtContainer instanceof Frame) {
            this.awtFrame = (Frame)awtContainer;
        }
    }
    
    @Override
    protected void requestFocusImpl(final boolean b) {
        this.awtContainer.requestFocus();
    }
    
    @Override
    protected void setTitleImpl(final String title) {
        if (this.awtFrame != null) {
            this.awtFrame.setTitle(title);
        }
    }
    
    @Override
    protected void createNativeImpl() {
        if (this.withinLocalDispose) {
            this.setupHandleAndGC();
            this.definePosition(this.getX(), this.getY());
            this.visibleChanged(false, true);
            this.withinLocalDispose = false;
        }
        else {
            if (0L != this.getParentWindowHandle()) {
                throw new RuntimeException("Window parenting not supported in AWT, use AWTWindow(Frame) cstr for wrapping instead");
            }
            if (null == this.awtContainer) {
                this.awtFrame = new Frame();
                this.awtContainer = this.awtFrame;
                this.owningFrame = true;
            }
            else {
                this.owningFrame = false;
                this.defineSize(this.awtContainer.getWidth(), this.awtContainer.getHeight());
                this.definePosition(this.awtContainer.getX(), this.awtContainer.getY());
            }
            if (null != this.awtFrame) {
                this.awtFrame.setTitle(this.getTitle());
            }
            this.awtContainer.setLayout(new BorderLayout());
            if (null == this.awtCanvas) {
                this.awtCanvas = new AWTCanvas(this, this.capsRequested, this.capabilitiesChooser, this.upstreamScalable);
                this.awtContainer.add(this.awtCanvas, "Center");
                new AWTMouseAdapter(this).addTo(this.awtCanvas);
                new AWTKeyAdapter(this).addTo(this.awtCanvas);
                new AWTWindowAdapter(new AWTWindowListener(), this).addTo(this.awtCanvas);
            }
            else {
                this.awtContainer.add(this.awtCanvas, "Center");
            }
            this.reconfigureWindowImpl(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getReconfigureMask(-1879048192, true));
        }
    }
    
    private void setupHandleAndGC() {
        if (null != this.awtCanvas) {
            final NativeWindow nativeWindow = this.awtCanvas.getNativeWindow();
            if (null != nativeWindow) {
                this.setGraphicsConfiguration(this.awtCanvas.getAWTGraphicsConfiguration());
                this.setWindowHandle(nativeWindow.getWindowHandle());
            }
        }
    }
    
    void localCreate() {
        if (this.withinLocalDispose) {
            this.setVisible(true);
        }
        else {
            this.setupHandleAndGC();
        }
    }
    
    void localDestroy() {
        this.withinLocalDispose = true;
        super.destroy();
    }
    
    @Override
    protected void closeNativeImpl() {
        this.setWindowHandle(0L);
        if (this.withinLocalDispose) {
            if (null != this.awtCanvas) {
                this.awtCanvas.dispose();
            }
        }
        else {
            if (null != this.awtContainer) {
                this.awtContainer.setVisible(false);
                this.awtContainer.remove(this.awtCanvas);
                this.awtContainer.setEnabled(false);
                this.awtCanvas.setEnabled(false);
                this.awtCanvas.dispose();
            }
            if (this.owningFrame && null != this.awtFrame) {
                this.awtFrame.dispose();
                this.owningFrame = false;
            }
            this.awtCanvas = null;
            this.awtFrame = null;
            this.awtContainer = null;
        }
    }
    
    @Override
    public boolean hasDeviceChanged() {
        final boolean hasDeviceChanged = this.awtCanvas.hasDeviceChanged();
        if (hasDeviceChanged) {
            final AWTGraphicsConfiguration awtGraphicsConfiguration = this.awtCanvas.getAWTGraphicsConfiguration();
            if (null == awtGraphicsConfiguration) {
                throw new NativeWindowException("Error Device change null GraphicsConfiguration: " + this);
            }
            this.setGraphicsConfiguration(awtGraphicsConfiguration);
            ((ScreenDriver)this.getScreen()).setAWTGraphicsScreen((AWTGraphicsScreen)awtGraphicsConfiguration.getScreen());
            ((DisplayDriver)this.getScreen().getDisplay()).setAWTGraphicsDevice((AWTGraphicsDevice)awtGraphicsConfiguration.getScreen().getDevice());
            ((ScreenDriver)this.getScreen()).updateVirtualScreenOriginAndSize();
        }
        return hasDeviceChanged;
    }
    
    private void setCanvasSizeImpl(final int n, final int n2) {
        final Dimension size = new Dimension(n, n2);
        final java.awt.Window window = AWTMisc.getWindow(this.awtCanvas);
        final Container container = (null != window) ? window : this.awtContainer;
        this.awtCanvas.setMinimumSize(size);
        this.awtCanvas.setPreferredSize(size);
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            final Insets insets = container.getInsets();
            System.err.println(WindowImpl.getThreadName() + ": AWTWindow setCanvasSize: szClient " + size + ", szCont " + new Dimension(n + insets.left + insets.right, n2 + insets.top + insets.bottom) + ", insets " + insets);
        }
        this.awtCanvas.setSize(size);
        this.awtCanvas.invalidate();
        if (null != window) {
            window.pack();
        }
        else {
            this.awtContainer.validate();
        }
    }
    
    private void setFrameSizeImpl(final int n, final int n2) {
        final Insets insets = this.awtContainer.getInsets();
        final Dimension size = new Dimension(n + insets.left + insets.right, n2 + insets.top + insets.bottom);
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println(WindowImpl.getThreadName() + ": AWTWindow setFrameSize: szClient " + new Dimension(n, n2) + ", szCont " + size + ", insets " + insets);
        }
        this.awtContainer.setSize(size);
        this.awtCanvas.invalidate();
        this.awtContainer.validate();
    }
    
    @Override
    protected final int getSupportedReconfigMaskImpl() {
        return 2057;
    }
    
    @Override
    protected boolean reconfigureWindowImpl(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (WindowDriver.DEBUG_IMPLEMENTATION) {
            System.err.println("AWTWindow reconfig: " + n + "/" + n2 + " " + n3 + "x" + n4 + ", " + WindowImpl.getReconfigStateMaskString(n5));
        }
        if (0x0 != (0x10000000 & n5) && null != this.awtFrame) {
            if (!this.awtContainer.isDisplayable()) {
                this.awtFrame.setUndecorated(this.isUndecorated());
            }
            else if (WindowDriver.DEBUG_IMPLEMENTATION) {
                System.err.println(WindowImpl.getThreadName() + ": AWTWindow can't undecorate already created frame");
            }
        }
        if (0x0 != (Integer.MIN_VALUE & n5)) {
            if (0x0 != (0x1 & n5)) {
                this.setCanvasSizeImpl(n3, n4);
                this.awtContainer.setVisible(true);
                final Insets insets = this.awtContainer.getInsets();
                this.insetsChanged(false, insets.left, insets.right, insets.top, insets.bottom);
            }
            else {
                this.awtContainer.setVisible(false);
            }
        }
        else if (this.awtCanvas.getWidth() != n3 || this.awtCanvas.getHeight() != n4) {
            if (Platform.OSType.MACOS == Platform.getOSType() && this.awtCanvas.isOffscreenLayerSurfaceEnabled()) {
                this.setFrameSizeImpl(n3, n4);
            }
            else {
                this.setCanvasSizeImpl(n3, n4);
            }
        }
        this.defineSize(n3, n4);
        if (this.awtContainer.getX() != n || this.awtContainer.getY() != n2) {
            this.awtContainer.setLocation(n, n2);
        }
        this.definePosition(n, n2);
        if (0x0 != (Integer.MIN_VALUE & n5)) {
            if (0x0 != (0x1 & n5) && !this.hasDeviceChanged()) {
                final AWTGraphicsConfiguration awtGraphicsConfiguration = this.awtCanvas.getAWTGraphicsConfiguration();
                if (null == awtGraphicsConfiguration) {
                    throw new NativeWindowException("Error: !hasDeviceChanged && null == GraphicsConfiguration: " + this);
                }
                this.setGraphicsConfiguration(awtGraphicsConfiguration);
            }
            this.visibleChanged(false, 0x0 != (0x1 & n5));
        }
        if (this.isVisible()) {
            this.windowRepaint(false, 0, 0, this.getSurfaceWidth(), this.getSurfaceHeight());
        }
        return true;
    }
    
    @Override
    protected Point getLocationOnScreenImpl(final int n, final int n2) {
        final java.awt.Point locationOnScreen = this.awtCanvas.getLocationOnScreen();
        locationOnScreen.translate(n, n2);
        return new Point((int)(locationOnScreen.getX() + 0.5), (int)(locationOnScreen.getY() + 0.5));
    }
    
    @Override
    public NativeSurface getWrappedSurface() {
        return (null != this.awtCanvas) ? this.awtCanvas.getNativeWindow() : null;
    }
    
    class AWTWindowListener implements WindowListener
    {
        @Override
        public void windowMoved(final WindowEvent windowEvent) {
            if (null != WindowDriver.this.awtContainer) {
                WindowDriver.this.positionChanged(false, WindowDriver.this.awtContainer.getX(), WindowDriver.this.awtContainer.getY());
            }
        }
        
        @Override
        public void windowResized(final WindowEvent windowEvent) {
            if (null != WindowDriver.this.awtCanvas) {
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window Resized: " + WindowDriver.this.awtCanvas);
                }
                WindowDriver.this.sizeChanged(false, WindowDriver.this.awtCanvas.getWidth(), WindowDriver.this.awtCanvas.getHeight(), true);
                WindowDriver.this.windowRepaint(false, 0, 0, WindowDriver.this.getSurfaceWidth(), WindowDriver.this.getSurfaceHeight());
            }
        }
        
        @Override
        public void windowDestroyNotify(final WindowEvent windowEvent) {
            WindowDriver.this.windowDestroyNotify(false);
        }
        
        @Override
        public void windowDestroyed(final WindowEvent windowEvent) {
        }
        
        @Override
        public void windowGainedFocus(final WindowEvent windowEvent) {
            WindowDriver.this.focusChanged(false, true);
        }
        
        @Override
        public void windowLostFocus(final WindowEvent windowEvent) {
            WindowDriver.this.focusChanged(false, false);
        }
        
        @Override
        public void windowRepaint(final WindowUpdateEvent windowUpdateEvent) {
            if (null != WindowDriver.this.awtCanvas) {
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window Repaint: " + WindowDriver.this.awtCanvas);
                }
                WindowDriver.this.windowRepaint(false, 0, 0, WindowDriver.this.getSurfaceWidth(), WindowDriver.this.getSurfaceHeight());
            }
        }
    }
    
    class NEWTWindowListener implements WindowListener
    {
        @Override
        public void windowMoved(final WindowEvent windowEvent) {
        }
        
        @Override
        public void windowResized(final WindowEvent windowEvent) {
        }
        
        @Override
        public void windowDestroyNotify(final WindowEvent windowEvent) {
            if (WindowDriver.this.withinLocalDispose) {
                windowEvent.setConsumed(true);
            }
        }
        
        @Override
        public void windowDestroyed(final WindowEvent windowEvent) {
            if (WindowDriver.this.withinLocalDispose) {
                windowEvent.setConsumed(true);
            }
        }
        
        @Override
        public void windowGainedFocus(final WindowEvent windowEvent) {
        }
        
        @Override
        public void windowLostFocus(final WindowEvent windowEvent) {
        }
        
        @Override
        public void windowRepaint(final WindowUpdateEvent windowUpdateEvent) {
        }
    }
}
