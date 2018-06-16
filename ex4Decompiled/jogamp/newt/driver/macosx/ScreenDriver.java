// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.macosx;

import com.jogamp.opengl.math.FloatUtil;
import jogamp.nativewindow.macosx.OSXUtil;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.Display;
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
    
    private MonitorMode getMonitorModeImpl(final MonitorModeProps.Cache cache, final int n, final int n2) {
        final int[] monitorMode0 = this.getMonitorMode0(n, n2);
        MonitorMode streamInMonitorMode;
        if (null == monitorMode0 || 0 >= monitorMode0.length) {
            streamInMonitorMode = null;
        }
        else {
            streamInMonitorMode = MonitorModeProps.streamInMonitorMode(null, cache, monitorMode0, 0);
        }
        return streamInMonitorMode;
    }
    
    @Override
    protected final void collectNativeMonitorModesAndDevicesImpl(final MonitorModeProps.Cache cache) {
        final CrtProps crtProps = new CrtProps();
        for (int i = 0; i < crtProps.count; ++i) {
            final int n = crtProps.crtIDs[i];
            final ArrayHashSet<MonitorMode> set = new ArrayHashSet<MonitorMode>(false, 16, 0.75f);
            int n2 = 0;
            while (true) {
                final MonitorMode monitorModeImpl = this.getMonitorModeImpl(cache, n, n2);
                if (null == monitorModeImpl) {
                    break;
                }
                if (monitorModeImpl.getSurfaceSize().getBitsPerPixel() >= 24) {
                    set.getOrAdd(monitorModeImpl);
                }
                ++n2;
            }
            if (0 >= n2) {
                throw new InternalError("Could not gather single mode of device " + i + "/" + crtProps.count + " -> " + Display.toHexString(n));
            }
            final MonitorMode monitorModeImpl2 = this.getMonitorModeImpl(cache, n, -1);
            if (null == monitorModeImpl2) {
                throw new InternalError("Could not gather current mode of device " + i + "/" + crtProps.count + " -> " + Display.toHexString(n) + ", but gathered " + n2 + " modes");
            }
            final float n3 = crtProps.pixelScaleArray[i];
            MonitorModeProps.streamInMonitorDevice(cache, this, monitorModeImpl2, new float[] { n3, n3 }, set, crtProps.propsFixedArray[i], 0, null);
        }
    }
    
    @Override
    protected boolean updateNativeMonitorDeviceViewportImpl(final MonitorDevice monitorDevice, final float[] array, final Rectangle rectangle, final Rectangle rectangle2) {
        final CrtProps crtProps = new CrtProps();
        final int id = monitorDevice.getId();
        if (id == 0) {
            throw new IllegalArgumentException("Invalid monitor id " + Display.toHexString(id));
        }
        final int index = crtProps.getIndex(id);
        if (0 > index || index >= crtProps.count) {
            throw new IndexOutOfBoundsException("monitor id " + index + " not within [0.." + (crtProps.count - 1) + "]");
        }
        final int[] array2 = crtProps.propsFixedArray[index];
        int n = 6;
        rectangle.set(array2[n++], array2[n++], array2[n++], array2[n++]);
        rectangle2.set(array2[n++], array2[n++], array2[n++], array2[n++]);
        array[1] = (array[0] = crtProps.pixelScaleArray[index]);
        return true;
    }
    
    @Override
    protected MonitorMode queryCurrentMonitorModeImpl(final MonitorDevice monitorDevice) {
        return this.getMonitorModeImpl(null, monitorDevice.getId(), -1);
    }
    
    @Override
    protected boolean setCurrentMonitorModeImpl(final MonitorDevice monitorDevice, final MonitorMode monitorMode) {
        return this.setMonitorMode0(monitorDevice.getId(), monitorMode.getId(), monitorMode.getRotation());
    }
    
    @Override
    protected int validateScreenIndex(final int n) {
        return 0;
    }
    
    private native int[] getMonitorDeviceIds0();
    
    private native int[] getMonitorProps0(final int p0);
    
    private native int[] getMonitorMode0(final int p0, final int p1);
    
    private native boolean setMonitorMode0(final int p0, final int p1, final int p2);
    
    static {
        DisplayDriver.initSingleton();
    }
    
    class CrtProps
    {
        final int count;
        final int[] crtIDs;
        final float[] pixelScaleArray;
        final int[][] propsOrigArray;
        final int[][] propsFixedArray;
        
        CrtProps() {
            this.crtIDs = ScreenDriver.this.getMonitorDeviceIds0();
            this.count = this.crtIDs.length;
            this.pixelScaleArray = new float[this.count];
            this.propsOrigArray = new int[this.count][];
            this.propsFixedArray = new int[this.count][];
            for (int i = 0; i < this.count; ++i) {
                final int n = this.crtIDs[i];
                final float n2 = (float)OSXUtil.GetPixelScaleByDisplayID(n);
                this.pixelScaleArray[i] = (FloatUtil.isZero(n2, 1.1920929E-7f) ? 1.0f : n2);
                this.propsOrigArray[i] = ScreenDriver.this.getMonitorProps0(n);
                if (null == this.propsOrigArray[i]) {
                    throw new InternalError("Could not gather device props " + i + "/" + this.count + " -> " + Display.toHexString(n));
                }
                final int length = this.propsOrigArray[i].length;
                this.propsFixedArray[i] = new int[length];
                System.arraycopy(this.propsOrigArray[i], 0, this.propsFixedArray[i], 0, length);
            }
            for (int j = 0; j < this.count; ++j) {
                final int[] array = this.propsFixedArray[j];
                final int n3 = array[6];
                final int n4 = array[7];
                final float n5 = this.pixelScaleArray[j];
                final int[] array2 = array;
                final int n6 = 8;
                array2[n6] *= (int)n5;
                final int[] array3 = array;
                final int n7 = 9;
                array3[n7] *= (int)n5;
                if (n3 != 0) {
                    for (int k = 0; k < this.count; ++k) {
                        if (k != j && n3 == this.propsOrigArray[k][8]) {
                            final int[] array4 = array;
                            final int n8 = 6;
                            array4[n8] *= (int)this.pixelScaleArray[k];
                            break;
                        }
                    }
                }
                if (n4 != 0) {
                    for (int l = 0; l < this.count; ++l) {
                        if (l != j && n4 == this.propsOrigArray[l][9]) {
                            final int[] array5 = array;
                            final int n9 = 7;
                            array5[n9] *= (int)this.pixelScaleArray[l];
                            break;
                        }
                    }
                }
            }
        }
        
        int getIndex(final int n) {
            for (int i = 0; i < this.count; ++i) {
                if (n == this.crtIDs[i]) {
                    return i;
                }
            }
            return -1;
        }
    }
}
