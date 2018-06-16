// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.awt;

import com.jogamp.newt.MonitorDevice;
import java.awt.GraphicsDevice;
import com.jogamp.newt.MonitorMode;
import java.awt.DisplayMode;
import jogamp.newt.MonitorModeProps;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import jogamp.newt.ScreenImpl;

public class ScreenDriver extends ScreenImpl
{
    @Override
    protected void createNativeImpl() {
        this.aScreen = new AWTGraphicsScreen((AWTGraphicsDevice)this.display.getGraphicsDevice());
    }
    
    protected void setAWTGraphicsScreen(final AWTGraphicsScreen aScreen) {
        this.aScreen = aScreen;
    }
    
    @Override
    protected void updateVirtualScreenOriginAndSize() {
        super.updateVirtualScreenOriginAndSize();
    }
    
    @Override
    protected void closeNativeImpl() {
    }
    
    @Override
    protected int validateScreenIndex(final int n) {
        return n;
    }
    
    private static MonitorMode getModeProps(final MonitorModeProps.Cache cache, final DisplayMode displayMode) {
        int refreshRate = displayMode.getRefreshRate();
        if (refreshRate == 0) {
            refreshRate = 60;
        }
        int bitDepth = displayMode.getBitDepth();
        if (-1 == bitDepth) {
            bitDepth = 32;
        }
        final int[] array = new int[8];
        int n = 0;
        array[n++] = 8;
        array[n++] = displayMode.getWidth();
        array[n++] = displayMode.getHeight();
        array[n++] = bitDepth;
        array[n++] = refreshRate * 100;
        array[n++] = 0;
        array[n++] = 0;
        array[n++] = 0;
        return MonitorModeProps.streamInMonitorMode(null, cache, array, 0);
    }
    
    @Override
    protected void collectNativeMonitorModesAndDevicesImpl(final MonitorModeProps.Cache cache) {
        final GraphicsDevice graphicsDevice = ((AWTGraphicsDevice)this.getDisplay().getGraphicsDevice()).getGraphicsDevice();
        final DisplayMode[] displayModes = graphicsDevice.getDisplayModes();
        for (int i = 0; i < displayModes.length; ++i) {
            getModeProps(cache, displayModes[i]);
        }
        final MonitorMode modeProps = getModeProps(cache, graphicsDevice.getDisplayMode());
        final int[] array = new int[14];
        int n = 0;
        array[n++] = array.length;
        array[n++] = 0;
        array[n++] = 0;
        array[n++] = 1;
        array[n++] = 519;
        array[n++] = 324;
        array[n++] = 0;
        array[n++] = 0;
        array[n++] = modeProps.getRotatedWidth();
        array[n++] = modeProps.getRotatedHeight();
        array[n++] = 0;
        array[n++] = 0;
        array[n++] = modeProps.getRotatedWidth();
        array[n++] = modeProps.getRotatedHeight();
        MonitorModeProps.streamInMonitorDevice(cache, this, modeProps, null, cache.monitorModes, array, 0, null);
    }
    
    @Override
    protected MonitorMode queryCurrentMonitorModeImpl(final MonitorDevice monitorDevice) {
        return null;
    }
    
    @Override
    protected boolean setCurrentMonitorModeImpl(final MonitorDevice monitorDevice, final MonitorMode monitorMode) {
        return false;
    }
}
