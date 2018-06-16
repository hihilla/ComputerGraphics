// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.newt.MonitorDevice;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.MutableSurface;

public class OffscreenWindow extends WindowImpl implements MutableSurface
{
    long surfaceHandle;
    static long nextWindowHandle;
    
    public OffscreenWindow() {
        this.surfaceHandle = 0L;
    }
    
    @Override
    protected void createNativeImpl() {
        if (this.capsRequested.isOnscreen()) {
            throw new NativeWindowException("Capabilities is onscreen");
        }
        final AbstractGraphicsScreen graphicsScreen = this.getScreen().getGraphicsScreen();
        final AbstractGraphicsConfiguration chooseGraphicsConfiguration = GraphicsConfigurationFactory.getFactory(graphicsScreen.getDevice(), this.capsRequested).chooseGraphicsConfiguration(this.capsRequested, this.capsRequested, this.capabilitiesChooser, graphicsScreen, 0);
        if (null == chooseGraphicsConfiguration) {
            throw new NativeWindowException("Error choosing GraphicsConfiguration creating window: " + this);
        }
        this.setGraphicsConfiguration(chooseGraphicsConfiguration);
        synchronized (OffscreenWindow.class) {
            this.setWindowHandle(OffscreenWindow.nextWindowHandle++);
        }
        this.visibleChanged(false, true);
    }
    
    @Override
    protected void closeNativeImpl() {
    }
    
    @Override
    public synchronized void destroy() {
        super.destroy();
        this.surfaceHandle = 0L;
    }
    
    @Override
    public void setSurfaceHandle(final long surfaceHandle) {
        this.surfaceHandle = surfaceHandle;
    }
    
    @Override
    public long getSurfaceHandle() {
        return this.surfaceHandle;
    }
    
    @Override
    protected void requestFocusImpl(final boolean b) {
    }
    
    @Override
    public void setPosition(final int n, final int n2) {
    }
    
    @Override
    public boolean setFullscreen(final boolean b) {
        return false;
    }
    
    @Override
    public boolean setFullscreen(final List<MonitorDevice> list) {
        return false;
    }
    
    @Override
    protected final int getSupportedReconfigMaskImpl() {
        return 2057;
    }
    
    @Override
    protected boolean reconfigureWindowImpl(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.sizeChanged(false, n3, n4, false);
        if (0x0 != (Integer.MIN_VALUE & n5)) {
            this.visibleChanged(false, 0x0 != (0x1 & n5));
        }
        return true;
    }
    
    @Override
    public Point getLocationOnScreen(final Point point) {
        if (null != point) {
            point.set(0, 0);
            return point;
        }
        return new Point(0, 0);
    }
    
    @Override
    protected Point getLocationOnScreenImpl(final int n, final int n2) {
        return new Point(n, n2);
    }
    
    static {
        OffscreenWindow.nextWindowHandle = 256L;
    }
}
