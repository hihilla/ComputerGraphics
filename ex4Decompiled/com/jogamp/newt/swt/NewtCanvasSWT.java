// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.swt;

import jogamp.nativewindow.windows.GDIUtil;
import jogamp.nativewindow.x11.X11Lib;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.SurfaceUpdatedListener;
import com.jogamp.nativewindow.util.Insets;
import jogamp.nativewindow.macosx.OSXUtil;
import com.jogamp.nativewindow.util.InsetsImmutable;
import jogamp.newt.Debug;
import com.jogamp.newt.Display;
import com.jogamp.newt.util.EDTUtil;
import jogamp.newt.swt.SWTEDTUtil;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.Capabilities;
import com.jogamp.nativewindow.NativeWindowFactory;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Control;
import com.jogamp.nativewindow.swt.SWTAccessor;
import org.eclipse.swt.widgets.Composite;
import com.jogamp.newt.Window;
import org.eclipse.swt.graphics.Rectangle;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.WindowClosingProtocol;
import org.eclipse.swt.widgets.Canvas;

public class NewtCanvasSWT extends Canvas implements WindowClosingProtocol
{
    private static final boolean DEBUG;
    private final AbstractGraphicsScreen screen;
    private WindowClosingMode newtChildCloseOp;
    private volatile Rectangle clientArea;
    private volatile SWTNativeWindow nativeWindow;
    private volatile Window newtChild;
    private volatile boolean newtChildReady;
    private volatile boolean postSetSize;
    private volatile boolean postSetPos;
    
    public static NewtCanvasSWT create(final Composite composite, final int n, final Window window) {
        final NewtCanvasSWT[] array = { null };
        composite.getDisplay().syncExec((Runnable)new Runnable() {
            @Override
            public void run() {
                array[0] = new NewtCanvasSWT(composite, n, window);
            }
        });
        return array[0];
    }
    
    public NewtCanvasSWT(final Composite composite, final int n, final Window newtChild) {
        super(composite, n | 0x40000);
        this.newtChildCloseOp = WindowClosingMode.DISPOSE_ON_CLOSE;
        this.newtChild = null;
        this.newtChildReady = false;
        this.postSetSize = false;
        this.postSetPos = false;
        SWTAccessor.setRealized((Control)this, true);
        this.clientArea = this.getClientArea();
        this.screen = SWTAccessor.getScreen(SWTAccessor.getDevice((Control)this), -1);
        this.nativeWindow = null;
        if (null != newtChild) {
            this.setNEWTChild(newtChild);
        }
        final Listener listener = (Listener)new Listener() {
            public void handleEvent(final Event event) {
                switch (event.type) {
                    case 9: {
                        if (NewtCanvasSWT.DEBUG) {
                            System.err.println("NewtCanvasSWT.Event.PAINT, " + event);
                        }
                        if ((null != NewtCanvasSWT.this.nativeWindow || NewtCanvasSWT.this.validateNative()) && NewtCanvasSWT.this.newtChildReady) {
                            if (NewtCanvasSWT.this.postSetSize) {
                                NewtCanvasSWT.this.newtChild.setSize(NewtCanvasSWT.this.clientArea.width, NewtCanvasSWT.this.clientArea.height);
                                NewtCanvasSWT.this.postSetSize = false;
                            }
                            if (NewtCanvasSWT.this.postSetPos) {
                                NewtCanvasSWT.this.newtChild.setPosition(NewtCanvasSWT.this.clientArea.x, NewtCanvasSWT.this.clientArea.y);
                                NewtCanvasSWT.this.postSetPos = false;
                            }
                            NewtCanvasSWT.this.newtChild.windowRepaint(0, 0, NewtCanvasSWT.this.clientArea.width, NewtCanvasSWT.this.clientArea.height);
                            break;
                        }
                        break;
                    }
                    case 10: {
                        if (NewtCanvasSWT.DEBUG) {
                            System.err.println("NewtCanvasSWT.Event.MOVE, " + event);
                            break;
                        }
                        break;
                    }
                    case 11: {
                        if (NewtCanvasSWT.DEBUG) {
                            System.err.println("NewtCanvasSWT.Event.RESIZE, " + event);
                        }
                        NewtCanvasSWT.this.updateSizeCheck();
                        break;
                    }
                    case 12: {
                        if (NewtCanvasSWT.DEBUG) {
                            System.err.println("NewtCanvasSWT.Event.DISPOSE, " + event);
                        }
                        NewtCanvasSWT.this.dispose();
                        break;
                    }
                    default: {
                        if (NewtCanvasSWT.DEBUG) {
                            System.err.println("NewtCanvasSWT.Event.misc: " + event.type + ", " + event);
                            break;
                        }
                        break;
                    }
                }
            }
        };
        this.addListener(11, (Listener)listener);
        this.addListener(9, (Listener)listener);
        this.addListener(12, (Listener)listener);
    }
    
    public void setBounds(final int n, final int n2, final int n3, final int n4) {
        super.setBounds(n, n2, n3, n4);
        if (NewtCanvasSWT.DEBUG) {
            System.err.println("NewtCanvasSWT.setBounds: " + n + "/" + n2 + " " + n3 + "x" + n4);
        }
        if (SWTAccessor.isOSX) {
            this.updatePosSizeCheck(n, n2, n3, n4, true);
        }
    }
    
    protected final boolean validateNative() {
        this.updateSizeCheck();
        final Rectangle clientArea = this.clientArea;
        if (0 >= clientArea.width || 0 >= clientArea.height) {
            return false;
        }
        this.screen.getDevice().open();
        final long windowHandle = SWTAccessor.getWindowHandle((Control)this);
        final int nativeVisualID = SWTAccessor.getNativeVisualID(this.screen.getDevice(), windowHandle);
        final boolean nativeVisualIDValidForProcessing = NativeWindowFactory.isNativeVisualIDValidForProcessing(nativeVisualID);
        if (NewtCanvasSWT.DEBUG) {
            System.err.println("NewtCanvasSWT.validateNative() windowHandle 0x" + Long.toHexString(windowHandle) + ", visualID 0x" + Integer.toHexString(nativeVisualID) + ", valid " + nativeVisualIDValidForProcessing);
        }
        if (nativeVisualIDValidForProcessing) {
            final Capabilities capabilities = new Capabilities();
            final GraphicsConfigurationFactory factory = GraphicsConfigurationFactory.getFactory(this.screen.getDevice(), capabilities);
            final AbstractGraphicsConfiguration chooseGraphicsConfiguration = factory.chooseGraphicsConfiguration(capabilities, capabilities, null, this.screen, nativeVisualID);
            if (NewtCanvasSWT.DEBUG) {
                System.err.println("NewtCanvasSWT.validateNative() factory: " + factory + ", windowHandle 0x" + Long.toHexString(windowHandle) + ", visualID 0x" + Integer.toHexString(nativeVisualID) + ", chosen config: " + chooseGraphicsConfiguration);
            }
            if (null == chooseGraphicsConfiguration) {
                throw new NativeWindowException("Error choosing GraphicsConfiguration creating window: " + this);
            }
            this.nativeWindow = new SWTNativeWindow(chooseGraphicsConfiguration, windowHandle);
            this.reparentWindow(true);
            if (SWTAccessor.isOSX && this.newtChildReady) {
                this.newtChild.setPosition(this.getLocation().x, this.getLocation().y);
            }
        }
        return null != this.nativeWindow;
    }
    
    protected final void updateSizeCheck() {
        final Rectangle clientArea = this.getClientArea();
        if (null != clientArea) {
            this.updatePosSizeCheck(clientArea.x, clientArea.y, clientArea.width, clientArea.height, false);
        }
    }
    
    protected final void updatePosSizeCheck() {
        final Rectangle clientArea = this.getClientArea();
        if (null != clientArea) {
            this.updatePosSizeCheck(clientArea.x, clientArea.y, clientArea.width, clientArea.height, true);
        }
    }
    
    protected final void updatePosSizeCheck(final int n, final int n2, final int n3, final int n4, final boolean b) {
        final Rectangle clientArea = this.clientArea;
        final boolean b2 = n3 != clientArea.width || n4 != clientArea.height;
        final boolean b3 = n != clientArea.x || n2 != clientArea.y;
        Rectangle clientArea2;
        if (b2 || b3) {
            clientArea2 = new Rectangle(b ? n : clientArea.x, b ? n2 : clientArea.y, n3, n4);
            this.clientArea = clientArea2;
        }
        else {
            clientArea2 = this.clientArea;
        }
        if (NewtCanvasSWT.DEBUG) {
            System.err.println("NewtCanvasSWT.updatePosSizeCheck: sizeChanged " + b2 + ", posChanged " + b3 + ", updatePos " + b + ", (" + Thread.currentThread().getName() + "): newtChildReady " + this.newtChildReady + ", " + clientArea2.x + "/" + clientArea2.y + " " + clientArea2.width + "x" + clientArea2.height + " - surfaceHandle 0x" + Long.toHexString(this.newtChildReady ? this.newtChild.getSurfaceHandle() : 0L));
        }
        if (b2) {
            if (this.newtChildReady) {
                this.newtChild.setSize(clientArea2.width, clientArea2.height);
            }
            else {
                this.postSetSize = true;
            }
        }
        if (b && b3) {
            if (this.newtChildReady) {
                this.newtChild.setPosition(clientArea2.x, clientArea2.y);
            }
            else {
                this.postSetPos = true;
            }
        }
    }
    
    public void update() {
    }
    
    public void dispose() {
        if (null != this.newtChild) {
            if (NewtCanvasSWT.DEBUG) {
                System.err.println("NewtCanvasSWT.dispose.0: EDTUtil cur " + this.newtChild.getScreen().getDisplay().getEDTUtil() + ",\n\t" + this.newtChild);
            }
            this.configureNewtChild(false);
            this.newtChild.setVisible(false);
            this.newtChild.reparentWindow(null, -1, -1, 0);
            this.newtChild.destroy();
            this.newtChild = null;
        }
        this.screen.getDevice().close();
        this.nativeWindow = null;
        super.dispose();
    }
    
    private Point getParentLocationOnScreen() {
        final org.eclipse.swt.graphics.Point[] array = { null };
        SWTAccessor.invoke(true, new Runnable() {
            @Override
            public void run() {
                array[0] = NewtCanvasSWT.this.getParent().toDisplay(0, 0);
            }
        });
        return new Point(array[0].x, array[0].y);
    }
    
    public NativeWindow getNativeWindow() {
        return this.nativeWindow;
    }
    
    public WindowClosingMode getDefaultCloseOperation() {
        return this.newtChildCloseOp;
    }
    
    public WindowClosingMode setDefaultCloseOperation(final WindowClosingMode newtChildCloseOp) {
        return this.newtChildCloseOp = newtChildCloseOp;
    }
    
    boolean isParent() {
        return null != this.newtChild;
    }
    
    boolean isFullscreen() {
        return null != this.newtChild && this.newtChild.isFullscreen();
    }
    
    public Window setNEWTChild(final Window newtChild) {
        final Window newtChild2 = this.newtChild;
        if (NewtCanvasSWT.DEBUG) {
            System.err.println("NewtCanvasSWT.setNEWTChild.0: win " + newtWinHandleToHexString(newtChild2) + " -> " + newtWinHandleToHexString(newtChild));
        }
        if (null != this.newtChild) {
            this.reparentWindow(false);
            this.newtChild = null;
        }
        this.newtChild = newtChild;
        if (null != this.nativeWindow && null != newtChild) {
            this.reparentWindow(true);
        }
        return newtChild2;
    }
    
    public Window getNEWTChild() {
        return this.newtChild;
    }
    
    public boolean setParent(final Composite parent) {
        return super.setParent(parent);
    }
    
    void configureNewtChild(final boolean newtChildReady) {
        this.newtChildReady = newtChildReady;
        if (null != this.newtChild) {
            this.newtChild.setKeyboardFocusHandler(null);
            if (newtChildReady) {
                this.newtChildCloseOp = this.newtChild.setDefaultCloseOperation(WindowClosingMode.DO_NOTHING_ON_CLOSE);
            }
            else {
                this.newtChild.setFocusAction(null);
                this.newtChild.setDefaultCloseOperation(this.newtChildCloseOp);
            }
        }
    }
    
    void reparentWindow(final boolean b) {
        if (null == this.newtChild) {
            return;
        }
        if (NewtCanvasSWT.DEBUG) {
            System.err.println("NewtCanvasSWT.reparentWindow.0: add=" + b + ", win " + newtWinHandleToHexString(this.newtChild) + ", EDTUtil: cur " + this.newtChild.getScreen().getDisplay().getEDTUtil());
        }
        this.newtChild.setFocusAction(null);
        if (b) {
            this.updateSizeCheck();
            final int width = this.clientArea.width;
            final int height = this.clientArea.height;
            final Display display = this.newtChild.getScreen().getDisplay();
            final SWTEDTUtil edtUtil = new SWTEDTUtil(display, this.getDisplay());
            edtUtil.start();
            display.setEDTUtil(edtUtil);
            this.newtChild.setSize(width, height);
            this.newtChild.reparentWindow(this.nativeWindow, -1, -1, 2);
            this.newtChild.setVisible(true);
            this.configureNewtChild(true);
            this.newtChild.sendWindowEvent(100);
            this.setEnabled(true);
        }
        else {
            this.configureNewtChild(false);
            this.newtChild.setVisible(false);
            this.newtChild.reparentWindow(null, -1, -1, 0);
        }
        if (NewtCanvasSWT.DEBUG) {
            System.err.println("NewtCanvasSWT.reparentWindow.X: add=" + b + ", win " + newtWinHandleToHexString(this.newtChild) + ", EDTUtil: cur " + this.newtChild.getScreen().getDisplay().getEDTUtil());
        }
    }
    
    private final void requestFocusNEWTChild() {
        if (this.newtChildReady) {
            this.newtChild.setFocusAction(null);
            this.newtChild.requestFocus();
        }
    }
    
    public boolean forceFocus() {
        final boolean access$701 = access$701(this);
        this.requestFocusNEWTChild();
        return access$701;
    }
    
    static String newtWinHandleToHexString(final Window window) {
        return (null != window) ? toHexString(window.getWindowHandle()) : "nil";
    }
    
    static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    static /* synthetic */ boolean access$701(final NewtCanvasSWT newtCanvasSWT) {
        return newtCanvasSWT.forceFocus();
    }
    
    static {
        DEBUG = Debug.debug("Window");
    }
    
    private class SWTNativeWindow implements NativeWindow
    {
        private final AbstractGraphicsConfiguration config;
        private final long nativeWindowHandle;
        private final InsetsImmutable insets;
        
        public SWTNativeWindow(final AbstractGraphicsConfiguration config, final long nativeWindowHandle) {
            this.config = config;
            this.nativeWindowHandle = nativeWindowHandle;
            if (SWTAccessor.isOSX) {
                this.insets = OSXUtil.GetInsets(nativeWindowHandle);
            }
            else {
                this.insets = new Insets(0, 0, 0, 0);
            }
        }
        
        @Override
        public int lockSurface() throws NativeWindowException, RuntimeException {
            return 3;
        }
        
        @Override
        public void unlockSurface() {
        }
        
        @Override
        public boolean isSurfaceLockedByOtherThread() {
            return false;
        }
        
        @Override
        public Thread getSurfaceLockOwner() {
            return null;
        }
        
        @Override
        public boolean surfaceSwap() {
            return false;
        }
        
        @Override
        public void addSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        }
        
        @Override
        public void addSurfaceUpdatedListener(final int n, final SurfaceUpdatedListener surfaceUpdatedListener) throws IndexOutOfBoundsException {
        }
        
        @Override
        public void removeSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        }
        
        @Override
        public long getSurfaceHandle() {
            return 0L;
        }
        
        @Override
        public int getWidth() {
            return this.getSurfaceWidth();
        }
        
        @Override
        public int getHeight() {
            return this.getSurfaceHeight();
        }
        
        @Override
        public final int[] convertToWindowUnits(final int[] array) {
            return array;
        }
        
        @Override
        public final int[] convertToPixelUnits(final int[] array) {
            return array;
        }
        
        @Override
        public int getSurfaceWidth() {
            return NewtCanvasSWT.this.clientArea.width;
        }
        
        @Override
        public int getSurfaceHeight() {
            return NewtCanvasSWT.this.clientArea.height;
        }
        
        @Override
        public final NativeSurface getNativeSurface() {
            return this;
        }
        
        @Override
        public AbstractGraphicsConfiguration getGraphicsConfiguration() {
            return this.config;
        }
        
        @Override
        public long getDisplayHandle() {
            return this.config.getScreen().getDevice().getHandle();
        }
        
        @Override
        public int getScreenIndex() {
            return this.config.getScreen().getIndex();
        }
        
        @Override
        public void surfaceUpdated(final Object o, final NativeSurface nativeSurface, final long n) {
        }
        
        @Override
        public void destroy() {
        }
        
        @Override
        public NativeWindow getParent() {
            return null;
        }
        
        @Override
        public long getWindowHandle() {
            return this.nativeWindowHandle;
        }
        
        @Override
        public InsetsImmutable getInsets() {
            return this.insets;
        }
        
        @Override
        public int getX() {
            return 0;
        }
        
        @Override
        public int getY() {
            return 0;
        }
        
        @Override
        public Point getLocationOnScreen(final Point point) {
            Point point2;
            if (SWTAccessor.isOSX) {
                point2 = NewtCanvasSWT.this.getParentLocationOnScreen();
            }
            else if (SWTAccessor.isX11) {
                final AbstractGraphicsScreen screen = this.config.getScreen();
                point2 = X11Lib.GetRelativeLocation(screen.getDevice().getHandle(), screen.getIndex(), this.nativeWindowHandle, 0L, 0, 0);
            }
            else if (SWTAccessor.isWindows) {
                point2 = GDIUtil.GetRelativeLocation(this.nativeWindowHandle, 0L, 0, 0);
            }
            else {
                point2 = new Point(0, 0);
            }
            if (null != point) {
                return point.translate(point2);
            }
            return point2;
        }
        
        @Override
        public boolean hasFocus() {
            return NewtCanvasSWT.this.isFocusControl();
        }
    }
}
