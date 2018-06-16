// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.windows;

import com.jogamp.newt.Screen;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.common.util.ArrayHashSet;
import com.jogamp.newt.MonitorMode;
import jogamp.newt.MonitorModeProps;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import jogamp.newt.ScreenImpl;

public class ScreenDriver extends ScreenImpl
{
    @Override
    protected void createNativeImpl() {
        this.aScreen = new DefaultGraphicsScreen(this.getDisplay().getGraphicsDevice(), this.screen_idx);
    }
    
    @Override
    protected void closeNativeImpl() {
    }
    
    private final String getAdapterName(final int n) {
        return this.getAdapterName0(n);
    }
    
    private final String getMonitorName(final String s, final int n, final boolean b) {
        return this.getMonitorName0(s, n, b);
    }
    
    private final MonitorMode getMonitorModeImpl(final MonitorModeProps.Cache cache, final String s, final int n) {
        if (null == s) {
            return null;
        }
        final int[] monitorMode0 = this.getMonitorMode0(s, n);
        if (null == monitorMode0 || 0 >= monitorMode0.length) {
            return null;
        }
        return MonitorModeProps.streamInMonitorMode(null, cache, monitorMode0, 0);
    }
    
    private static final int getMonitorId(final int n, final int n2) {
        if (n > 255) {
            throw new InternalError("Unsupported adapter idx > 0xff: " + n);
        }
        if (n2 > 255) {
            throw new InternalError("Unsupported monitor idx > 0xff: " + n2);
        }
        return (n & 0xFF) << 8 | (n2 & 0xFF);
    }
    
    private static final int getAdapterIndex(final int n) {
        return n >>> 8 & 0xFF;
    }
    
    private static final int getMonitorIndex(final int n) {
        return n & 0xFF;
    }
    
    @Override
    protected void collectNativeMonitorModesAndDevicesImpl(final MonitorModeProps.Cache cache) {
        ArrayHashSet<MonitorMode> set = new ArrayHashSet<MonitorMode>(false, 16, 0.75f);
        String adapterName;
        for (int n = 0; null != (adapterName = this.getAdapterName(n)); ++n) {
            for (int n2 = 0; null != this.getMonitorName(adapterName, n2, true); ++n2) {
                int n3 = 0;
                MonitorMode monitorModeImpl;
                do {
                    monitorModeImpl = this.getMonitorModeImpl(cache, adapterName, n3);
                    if (null != monitorModeImpl) {
                        set.getOrAdd(monitorModeImpl);
                        ++n3;
                    }
                } while (null != monitorModeImpl);
                if (0 < n3) {
                    final MonitorMode monitorModeImpl2 = this.getMonitorModeImpl(cache, adapterName, -1);
                    if (null != monitorModeImpl2) {
                        MonitorModeProps.streamInMonitorDevice(cache, this, monitorModeImpl2, null, set, this.getMonitorDevice0(n, n2, getMonitorId(n, n2)), 0, null);
                        set = new ArrayHashSet<MonitorMode>(false, 16, 0.75f);
                    }
                }
            }
        }
    }
    
    @Override
    protected boolean updateNativeMonitorDeviceViewportImpl(final MonitorDevice monitorDevice, final float[] array, final Rectangle rectangle, final Rectangle rectangle2) {
        final int id = monitorDevice.getId();
        final int adapterIndex = getAdapterIndex(id);
        final int monitorIndex = getMonitorIndex(id);
        final String adapterName = this.getAdapterName(adapterIndex);
        if (null != adapterName && null != this.getMonitorName(adapterName, monitorIndex, true)) {
            final int[] monitorDevice2 = this.getMonitorDevice0(adapterIndex, monitorIndex, getMonitorId(adapterIndex, monitorIndex));
            int n = 6;
            rectangle.set(monitorDevice2[n++], monitorDevice2[n++], monitorDevice2[n++], monitorDevice2[n++]);
            rectangle2.set(monitorDevice2[n++], monitorDevice2[n++], monitorDevice2[n++], monitorDevice2[n++]);
            return true;
        }
        return false;
    }
    
    @Override
    protected MonitorMode queryCurrentMonitorModeImpl(final MonitorDevice monitorDevice) {
        return this.getMonitorModeImpl(null, this.getAdapterName(getAdapterIndex(monitorDevice.getId())), -1);
    }
    
    @Override
    protected boolean setCurrentMonitorModeImpl(final MonitorDevice monitorDevice, final MonitorMode monitorMode) {
        return this.setMonitorMode0(getAdapterIndex(monitorDevice.getId()), -1, -1, monitorMode.getSurfaceSize().getResolution().getWidth(), monitorMode.getSurfaceSize().getResolution().getHeight(), monitorMode.getSurfaceSize().getBitsPerPixel(), (int)monitorMode.getRefreshRate(), monitorMode.getFlags(), monitorMode.getRotation());
    }
    
    @Override
    protected int validateScreenIndex(final int n) {
        return 0;
    }
    
    @Override
    protected void calcVirtualScreenOriginAndSize(final Rectangle rectangle, final Rectangle rectangle2) {
        rectangle.set(this.getVirtualOriginX0(), this.getVirtualOriginY0(), this.getVirtualWidthImpl0(), this.getVirtualHeightImpl0());
        rectangle2.set(rectangle);
    }
    
    private native int getVirtualOriginX0();
    
    private native int getVirtualOriginY0();
    
    private native int getVirtualWidthImpl0();
    
    private native int getVirtualHeightImpl0();
    
    private static native void dumpMonitorInfo0();
    
    private native String getAdapterName0(final int p0);
    
    private native String getMonitorName0(final String p0, final int p1, final boolean p2);
    
    private native int[] getMonitorMode0(final String p0, final int p1);
    
    private native int[] getMonitorDevice0(final int p0, final int p1, final int p2);
    
    private native boolean setMonitorMode0(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    static {
        DisplayDriver.initSingleton();
        if (Screen.DEBUG) {
            dumpMonitorInfo0();
        }
    }
}
