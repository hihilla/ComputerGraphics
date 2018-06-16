// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.bcm.vc.iv;

import jogamp.newt.PointerIconImpl;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.nativewindow.Capabilities;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import jogamp.opengl.egl.EGLDisplayUtil;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.common.util.Bitfield;
import jogamp.newt.driver.KeyTracker;
import jogamp.newt.driver.MouseTracker;
import jogamp.newt.driver.x11.X11UnderlayTracker;
import jogamp.newt.driver.linux.LinuxEventDeviceTracker;
import jogamp.newt.driver.linux.LinuxMouseTracker;
import jogamp.newt.WindowImpl;

public class WindowDriver extends WindowImpl
{
    private static final String WINDOW_CLASS_NAME = "NewtWindow";
    private LinuxMouseTracker linuxMouseTracker;
    private LinuxEventDeviceTracker linuxEventDeviceTracker;
    private X11UnderlayTracker x11UnderlayTracker;
    private MouseTracker mouseTracker;
    private KeyTracker keyTracker;
    private int layer;
    private long nativeWindowHandle;
    private long windowHandleClose;
    private static int nextLayer;
    private static int layerCount;
    private static final int MAX_LAYERS = 32;
    private static final Bitfield usedLayers;
    private static final Object layerSync;
    
    public WindowDriver() {
        try {
            this.x11UnderlayTracker = X11UnderlayTracker.getSingleton();
            this.mouseTracker = this.x11UnderlayTracker;
            this.keyTracker = this.x11UnderlayTracker;
        }
        catch (ExceptionInInitializerError exceptionInInitializerError) {
            this.linuxMouseTracker = LinuxMouseTracker.getSingleton();
            this.linuxEventDeviceTracker = LinuxEventDeviceTracker.getSingleton();
            this.mouseTracker = this.linuxMouseTracker;
            this.keyTracker = this.linuxEventDeviceTracker;
        }
        this.layer = -1;
        this.nativeWindowHandle = 0L;
        this.windowHandleClose = 0L;
    }
    
    private RectangleImmutable clampRect(final ScreenDriver screenDriver, final RectangleImmutable rectangleImmutable, final boolean b) {
        int x = rectangleImmutable.getX();
        int y = rectangleImmutable.getY();
        int width = rectangleImmutable.getWidth();
        int height = rectangleImmutable.getHeight();
        final int width2 = screenDriver.getWidth();
        final int height2 = screenDriver.getHeight();
        boolean b2 = false;
        boolean b3 = false;
        if (0 > x) {
            x = 0;
            b2 = true;
        }
        if (0 > y) {
            y = 0;
            b2 = true;
        }
        if (width2 < x + width) {
            if (0 < x) {
                x = 0;
                b2 = true;
            }
            if (width2 < width) {
                width = width2;
                b3 = true;
            }
        }
        if (height2 < y + height) {
            if (0 < y) {
                y = 0;
                b2 = true;
            }
            if (height2 < height) {
                height = height2;
                b3 = true;
            }
        }
        if (b2 || b3) {
            if (b) {
                if (b2) {
                    this.definePosition(x, y);
                }
                if (b3) {
                    this.defineSize(width, height);
                }
            }
            return new Rectangle(x, y, width, height);
        }
        return rectangleImmutable;
    }
    
    @Override
    protected boolean canCreateNativeImpl() {
        this.clampRect((ScreenDriver)this.getScreen(), new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()), true);
        return true;
    }
    
    @Override
    protected void createNativeImpl() {
        if (0L != this.getParentWindowHandle()) {
            throw new RuntimeException("Window parenting not supported (yet)");
        }
        synchronized (WindowDriver.layerSync) {
            if (WindowDriver.layerCount >= 32) {
                throw new RuntimeException("Max windows reached: " + WindowDriver.layerCount + " ( " + 32 + " )");
            }
            for (int n = 0; 0 > this.layer && n < 32; ++n) {
                if (!WindowDriver.usedLayers.get(WindowDriver.nextLayer)) {
                    this.layer = WindowDriver.nextLayer;
                    WindowDriver.usedLayers.set(this.layer);
                    ++WindowDriver.layerCount;
                }
                ++WindowDriver.nextLayer;
                if (32 == WindowDriver.nextLayer) {
                    WindowDriver.nextLayer = 0;
                }
            }
        }
        if (0 > this.layer) {
            throw new InternalError("Could not find a free layer: count " + WindowDriver.layerCount + ", max " + 32);
        }
        final ScreenDriver screenDriver = (ScreenDriver)this.getScreen();
        final DisplayDriver displayDriver = (DisplayDriver)screenDriver.getDisplay();
        final AbstractGraphicsScreen graphicsScreen = screenDriver.getGraphicsScreen();
        final EGLGraphicsDevice eglGraphicsDevice = (EGLGraphicsDevice)graphicsScreen.getDevice();
        final EGLGraphicsDevice eglCreateEGLGraphicsDevice = EGLDisplayUtil.eglCreateEGLGraphicsDevice(eglGraphicsDevice.getNativeDisplayID(), eglGraphicsDevice.getConnection(), eglGraphicsDevice.getUnitID());
        eglCreateEGLGraphicsDevice.open();
        final AbstractGraphicsConfiguration chooseGraphicsConfiguration = GraphicsConfigurationFactory.getFactory(this.getScreen().getDisplay().getGraphicsDevice(), this.capsRequested).chooseGraphicsConfiguration(this.capsRequested, this.capsRequested, this.capabilitiesChooser, new DefaultGraphicsScreen(eglCreateEGLGraphicsDevice, graphicsScreen.getIndex()), 0);
        if (null == chooseGraphicsConfiguration) {
            throw new NativeWindowException("Error choosing GraphicsConfiguration creating window: " + this);
        }
        final Capabilities capabilities = (Capabilities)chooseGraphicsConfiguration.getChosenCapabilities();
        if (this.capsRequested.isBackgroundOpaque() != capabilities.isBackgroundOpaque()) {
            capabilities.setBackgroundOpaque(this.capsRequested.isBackgroundOpaque());
        }
        this.setGraphicsConfiguration(chooseGraphicsConfiguration);
        this.nativeWindowHandle = this.CreateWindow0(displayDriver.getBCMHandle(), this.layer, this.getX(), this.getY(), this.getWidth(), this.getHeight(), capabilities.isBackgroundOpaque(), capabilities.getAlphaBits());
        if (this.nativeWindowHandle == 0L) {
            throw new NativeWindowException("Error creating egl window: " + chooseGraphicsConfiguration);
        }
        this.setWindowHandle(this.nativeWindowHandle);
        if (0L == this.getWindowHandle()) {
            throw new NativeWindowException("Error native Window Handle is null");
        }
        this.windowHandleClose = this.nativeWindowHandle;
        this.addWindowListener(this.keyTracker);
        this.addWindowListener(this.mouseTracker);
        this.focusChanged(false, true);
    }
    
    @Override
    protected void closeNativeImpl() {
        final DisplayDriver displayDriver = (DisplayDriver)this.getScreen().getDisplay();
        final EGLGraphicsDevice eglGraphicsDevice = (EGLGraphicsDevice)this.getGraphicsConfiguration().getScreen().getDevice();
        this.removeWindowListener(this.mouseTracker);
        this.removeWindowListener(this.keyTracker);
        if (0L != this.windowHandleClose) {
            this.CloseWindow0(displayDriver.getBCMHandle(), this.windowHandleClose);
        }
        eglGraphicsDevice.close();
        synchronized (WindowDriver.layerSync) {
            WindowDriver.usedLayers.clear(this.layer);
            --WindowDriver.layerCount;
            this.layer = -1;
        }
    }
    
    @Override
    protected void requestFocusImpl(final boolean b) {
        this.focusChanged(false, true);
    }
    
    @Override
    protected final int getSupportedReconfigMaskImpl() {
        return 14345;
    }
    
    @Override
    protected boolean reconfigureWindowImpl(final int n, final int n2, final int n3, final int n4, final int n5) {
        final RectangleImmutable clampRect = this.clampRect((ScreenDriver)this.getScreen(), new Rectangle(n, n2, n3, n4), false);
        this.reconfigure0(this.nativeWindowHandle, clampRect.getX(), clampRect.getY(), clampRect.getWidth(), clampRect.getHeight(), n5);
        return true;
    }
    
    @Override
    protected Point getLocationOnScreenImpl(final int n, final int n2) {
        return new Point(n, n2);
    }
    
    @Override
    protected final void doMouseEvent(final boolean b, final boolean b2, final short n, final int n2, final int n3, final int n4, final short n5, final float[] array, final float n6) {
        if (205 == n || 206 == n) {
            ((DisplayDriver)this.getScreen().getDisplay()).moveActivePointerIcon(this.getX() + n3, this.getY() + n4);
        }
        super.doMouseEvent(b, b2, n, n2, n3, n4, n5, array, n6);
    }
    
    @Override
    protected void setPointerIconImpl(final PointerIconImpl pointerIconImpl) {
        ((DisplayDriver)this.getScreen().getDisplay()).setPointerIconActive((null != pointerIconImpl) ? pointerIconImpl.validatedHandle() : 0L, this.mouseTracker.getLastX(), this.mouseTracker.getLastY());
    }
    
    @Override
    protected boolean setPointerVisibleImpl(final boolean b) {
        ((DisplayDriver)this.getScreen().getDisplay()).setActivePointerIconVisible(b, this.mouseTracker.getLastX(), this.mouseTracker.getLastY());
        return true;
    }
    
    protected static native boolean initIDs();
    
    private native long CreateWindow0(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final int p7);
    
    private native void CloseWindow0(final long p0, final long p1);
    
    private native void reconfigure0(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    static {
        DisplayDriver.initSingleton();
        WindowDriver.nextLayer = 0;
        WindowDriver.layerCount = 0;
        usedLayers = Bitfield.Factory.create(32);
        layerSync = new Object();
    }
}
