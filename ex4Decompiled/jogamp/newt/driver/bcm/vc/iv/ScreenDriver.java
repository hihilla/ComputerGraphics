// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.bcm.vc.iv;

import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import jogamp.newt.MonitorModeProps;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import jogamp.newt.ScreenImpl;

public class ScreenDriver extends ScreenImpl
{
    private static int cachedWidth;
    private static int cachedHeight;
    
    @Override
    protected void createNativeImpl() {
        this.aScreen = new DefaultGraphicsScreen(this.getDisplay().getGraphicsDevice(), this.screen_idx);
        this.initNative();
    }
    
    @Override
    protected void closeNativeImpl() {
    }
    
    @Override
    protected int validateScreenIndex(final int n) {
        return 0;
    }
    
    @Override
    protected final void collectNativeMonitorModesAndDevicesImpl(final MonitorModeProps.Cache cache) {
        final int[] array = new int[8];
        int n = 0;
        array[n++] = 8;
        array[n++] = ScreenDriver.cachedWidth;
        array[n++] = ScreenDriver.cachedHeight;
        array[n++] = 32;
        array[n++] = 6000;
        array[n++] = 0;
        array[n++] = 0;
        array[n++] = 0;
        final MonitorMode streamInMonitorMode = MonitorModeProps.streamInMonitorMode(null, cache, array, 0);
        final int[] array2 = new int[14];
        int n2 = 0;
        array2[n2++] = array2.length;
        array2[n2++] = 0;
        array2[n2++] = 0;
        array2[n2++] = 1;
        array2[n2++] = 519;
        array2[n2++] = 324;
        array2[n2++] = 0;
        array2[n2++] = 0;
        array2[n2++] = ScreenDriver.cachedWidth;
        array2[n2++] = ScreenDriver.cachedWidth;
        array2[n2++] = 0;
        array2[n2++] = 0;
        array2[n2++] = ScreenDriver.cachedWidth;
        array2[n2++] = ScreenDriver.cachedWidth;
        MonitorModeProps.streamInMonitorDevice(cache, this, streamInMonitorMode, null, cache.monitorModes, array2, 0, null);
    }
    
    @Override
    protected MonitorMode queryCurrentMonitorModeImpl(final MonitorDevice monitorDevice) {
        return monitorDevice.getSupportedModes().get(0);
    }
    
    @Override
    protected boolean setCurrentMonitorModeImpl(final MonitorDevice monitorDevice, final MonitorMode monitorMode) {
        return false;
    }
    
    @Override
    protected void calcVirtualScreenOriginAndSize(final Rectangle rectangle, final Rectangle rectangle2) {
        rectangle.set(0, 0, ScreenDriver.cachedWidth, ScreenDriver.cachedHeight);
        rectangle2.set(rectangle);
    }
    
    protected void setScreenSize(final int cachedWidth, final int cachedHeight) {
        ScreenDriver.cachedWidth = cachedWidth;
        ScreenDriver.cachedHeight = cachedHeight;
    }
    
    protected static native boolean initIDs();
    
    protected native void initNative();
    
    static {
        DisplayDriver.initSingleton();
        ScreenDriver.cachedWidth = 0;
        ScreenDriver.cachedHeight = 0;
    }
}
