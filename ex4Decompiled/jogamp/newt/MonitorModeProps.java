// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.newt.Screen;
import com.jogamp.opengl.math.FloatUtil;
import java.util.ArrayList;
import com.jogamp.common.util.ArrayHashSet;
import java.util.List;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import com.jogamp.nativewindow.util.SurfaceSize;
import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.nativewindow.util.DimensionImmutable;

public class MonitorModeProps
{
    public static final int NUM_RESOLUTION_PROPERTIES = 2;
    public static final int NUM_SURFACE_SIZE_PROPERTIES = 1;
    public static final int NUM_SIZEANDRATE_PROPERTIES = 2;
    public static final int NUM_MONITOR_MODE_PROPERTIES = 2;
    public static final int NUM_MONITOR_MODE_PROPERTIES_ALL = 8;
    public static final int IDX_MONITOR_MODE_BPP = 3;
    public static final int IDX_MONITOR_MODE_ROT = 7;
    public static final int MIN_MONITOR_DEVICE_PROPERTIES = 17;
    public static final int IDX_MONITOR_DEVICE_VIEWPORT = 6;
    
    private static DimensionImmutable streamInResolution(final int[] array, int n) {
        return new Dimension(array[n++], array[n++]);
    }
    
    private static SurfaceSize streamInSurfaceSize(final DimensionImmutable dimensionImmutable, final int[] array, final int n) {
        return new SurfaceSize(dimensionImmutable, array[n]);
    }
    
    private static MonitorMode.SizeAndRRate streamInSizeAndRRate(final SurfaceSize surfaceSize, final int[] array, int n) {
        return new MonitorMode.SizeAndRRate(surfaceSize, array[n++] / 100.0f, array[n++]);
    }
    
    private static MonitorMode streamInMonitorMode0(final MonitorMode.SizeAndRRate sizeAndRRate, final int[] array, int n) {
        return new MonitorMode(array[n++], sizeAndRRate, array[n++]);
    }
    
    public static MonitorMode streamInMonitorMode(final int[] array, final Cache cache, final int[] array2, int n) {
        final int n2 = array2[n];
        if (8 != n2) {
            throw new RuntimeException("property count should be 8, but is " + n2 + ", len " + (array2.length - n));
        }
        if (8 > array2.length - n) {
            throw new RuntimeException("properties array too short, should be >= 8, is " + (array2.length - n));
        }
        ++n;
        DimensionImmutable streamInResolution = streamInResolution(array2, n);
        n += 2;
        if (null != cache) {
            streamInResolution = cache.resolutions.getOrAdd(streamInResolution);
        }
        SurfaceSize streamInSurfaceSize = streamInSurfaceSize(streamInResolution, array2, n);
        ++n;
        if (null != cache) {
            streamInSurfaceSize = cache.surfaceSizes.getOrAdd(streamInSurfaceSize);
        }
        MonitorMode.SizeAndRRate streamInSizeAndRRate = streamInSizeAndRRate(streamInSurfaceSize, array2, n);
        n += 2;
        if (null != cache) {
            streamInSizeAndRRate = cache.sizeAndRates.getOrAdd(streamInSizeAndRRate);
        }
        MonitorMode streamInMonitorMode0 = streamInMonitorMode0(streamInSizeAndRRate, array2, n);
        if (null != cache) {
            streamInMonitorMode0 = cache.monitorModes.getOrAdd(streamInMonitorMode0);
        }
        if (null != array && null != cache) {
            final int index = cache.monitorModes.indexOf(streamInMonitorMode0);
            if (0 > index) {
                throw new InternalError("Invalid index of current unified mode " + streamInMonitorMode0);
            }
            array[0] = index;
        }
        return streamInMonitorMode0;
    }
    
    public static int[] streamOutMonitorMode(final MonitorMode monitorMode) {
        final int[] array = new int[8];
        int n = 0;
        array[n++] = 8;
        array[n++] = monitorMode.getSurfaceSize().getResolution().getWidth();
        array[n++] = monitorMode.getSurfaceSize().getResolution().getHeight();
        array[n++] = monitorMode.getSurfaceSize().getBitsPerPixel();
        array[n++] = (int)(monitorMode.getRefreshRate() * 100.0f);
        array[n++] = monitorMode.getFlags();
        array[n++] = monitorMode.getId();
        array[n++] = monitorMode.getRotation();
        if (8 != n) {
            throw new InternalError("wrong number of attributes: got " + n + " != should " + 8);
        }
        return array;
    }
    
    public static MonitorDevice streamInMonitorDevice(final Cache cache, final ScreenImpl screenImpl, final float[] array, final int[] array2, int i, final int[] array3) {
        final int n = array2[i];
        if (17 > n) {
            throw new RuntimeException("property count should be >= 17, but is " + n + ", len " + (array2.length - i));
        }
        if (17 > array2.length - i) {
            throw new RuntimeException("properties array too short (min), should be >= 17, is " + (array2.length - i));
        }
        if (n > array2.length - i) {
            throw new RuntimeException("properties array too short (count), should be >= " + n + ", is " + (array2.length - i));
        }
        final int n2 = i + n;
        ++i;
        final ArrayList<MonitorMode> data = cache.monitorModes.getData();
        final int n3 = array2[i++];
        final boolean b = 0 != array2[i++];
        final boolean b2 = 0 != array2[i++];
        final DimensionImmutable streamInResolution = streamInResolution(array2, i);
        i += 2;
        final Rectangle rectangle = new Rectangle(array2[i++], array2[i++], array2[i++], array2[i++]);
        final Rectangle rectangle2 = new Rectangle(array2[i++], array2[i++], array2[i++], array2[i++]);
        final MonitorMode byNativeIdAndRotation = getByNativeIdAndRotation(data, array2[i++], array2[i++]);
        final ArrayHashSet<MonitorMode> set = new ArrayHashSet<MonitorMode>(false, 16, 0.75f);
        while (i < n2) {
            final int n4 = array2[i++];
            for (int j = 0; j < data.size(); ++j) {
                final MonitorMode monitorMode = data.get(j);
                if (monitorMode.getId() == n4) {
                    set.add(monitorMode);
                }
            }
        }
        MonitorDevice primary = new MonitorDeviceImpl(screenImpl, n3, b, b2, streamInResolution, byNativeIdAndRotation, array, rectangle, rectangle2, set);
        if (null != cache) {
            primary = cache.monitorDevices.getOrAdd(primary);
            if (primary.isPrimary()) {
                cache.setPrimary(primary);
            }
        }
        if (null != array3) {
            final int index = cache.monitorDevices.indexOf(primary);
            if (0 > index) {
                throw new InternalError("Invalid index of current unified mode " + primary);
            }
            array3[0] = index;
        }
        return primary;
    }
    
    private static MonitorMode getByNativeIdAndRotation(final List<MonitorMode> list, final int n, final int n2) {
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                final MonitorMode monitorMode = list.get(i);
                if (monitorMode.getId() == n && monitorMode.getRotation() == n2) {
                    return monitorMode;
                }
            }
        }
        return null;
    }
    
    public static MonitorDevice streamInMonitorDevice(final Cache cache, final ScreenImpl screenImpl, final MonitorMode monitorMode, final float[] array, final ArrayHashSet<MonitorMode> set, final int[] array2, int n, final int[] array3) {
        final int n2 = array2[n];
        if (14 != n2) {
            throw new RuntimeException("property count should be == 14, but is " + n2 + ", len " + (array2.length - n));
        }
        if (14 > array2.length - n) {
            throw new RuntimeException("properties array too short (min), should be >= 14, is " + (array2.length - n));
        }
        if (n2 > array2.length - n) {
            throw new RuntimeException("properties array too short (count), should be >= " + n2 + ", is " + (array2.length - n));
        }
        ++n;
        final int n3 = array2[n++];
        final boolean b = 0 != array2[n++];
        final boolean b2 = 0 != array2[n++];
        final DimensionImmutable streamInResolution = streamInResolution(array2, n);
        n += 2;
        MonitorDeviceImpl primary = new MonitorDeviceImpl(screenImpl, n3, b, b2, streamInResolution, monitorMode, array, new Rectangle(array2[n++], array2[n++], array2[n++], array2[n++]), new Rectangle(array2[n++], array2[n++], array2[n++], array2[n++]), set);
        if (null != cache) {
            primary = cache.monitorDevices.getOrAdd(primary);
            if (primary.isPrimary()) {
                cache.setPrimary(primary);
            }
        }
        if (null != array3) {
            final int index = cache.monitorDevices.indexOf(primary);
            if (0 > index) {
                throw new InternalError("Invalid index of current unified mode " + primary);
            }
            array3[0] = index;
        }
        return primary;
    }
    
    public static int[] streamOutMonitorDevice(final MonitorDevice monitorDevice) {
        final int size = monitorDevice.getSupportedModes().size();
        if (size == 0) {
            throw new RuntimeException("no supported modes: " + monitorDevice);
        }
        final int[] array = new int[17 + size - 1];
        int n = 0;
        array[n++] = array.length;
        array[n++] = monitorDevice.getId();
        array[n++] = (monitorDevice.isClone() ? 1 : 0);
        array[n++] = (monitorDevice.isPrimary() ? 1 : 0);
        array[n++] = monitorDevice.getSizeMM().getWidth();
        array[n++] = monitorDevice.getSizeMM().getHeight();
        array[n++] = monitorDevice.getViewport().getX();
        array[n++] = monitorDevice.getViewport().getY();
        array[n++] = monitorDevice.getViewport().getWidth();
        array[n++] = monitorDevice.getViewport().getHeight();
        array[n++] = monitorDevice.getViewportInWindowUnits().getX();
        array[n++] = monitorDevice.getViewportInWindowUnits().getY();
        array[n++] = monitorDevice.getViewportInWindowUnits().getWidth();
        array[n++] = monitorDevice.getViewportInWindowUnits().getHeight();
        array[n++] = monitorDevice.getCurrentMode().getId();
        array[n++] = monitorDevice.getCurrentMode().getRotation();
        final List<MonitorMode> supportedModes = monitorDevice.getSupportedModes();
        for (int i = 0; i < supportedModes.size(); ++i) {
            array[n++] = supportedModes.get(i).getId();
        }
        if (array.length != n) {
            throw new InternalError("wrong number of attributes: got " + n + " != should " + array.length);
        }
        return array;
    }
    
    static void identifyMonitorDevices(final Cache cache) {
        final ArrayList<MonitorDevice> arrayList = cache.monitorDevices.toArrayList();
        for (int size = arrayList.size(), i = 0; i < size; ++i) {
            final MonitorDevice monitorDevice = arrayList.get(i);
            if (!monitorDevice.isClone()) {
                for (int j = i + 1; j < size; ++j) {
                    final MonitorDevice monitorDevice2 = arrayList.get(j);
                    if (!monitorDevice2.isClone()) {
                        final float coverage = monitorDevice2.getViewport().coverage(monitorDevice.getViewport());
                        if (FloatUtil.isZero(1.0f - coverage, 1.1920929E-7f)) {
                            ((MonitorDeviceImpl)monitorDevice2).setIsClone(true);
                            if (Screen.DEBUG) {
                                System.err.printf("MonitorCloneTest[%d of %d]: %f -> _is_ covered%n", j, i, coverage);
                                System.err.printf("  Monitor[%d] %s%n", j, monitorDevice2.toString());
                                System.err.printf("  Monitor[%d] %s%n", i, monitorDevice.toString());
                            }
                        }
                        else if (Screen.DEBUG) {
                            System.err.printf("MonitorDevice-CloneTest[%d of %d]: %f -> not covered%n", j, i, coverage);
                            System.err.printf("  Monitor[%d] %s%n", j, monitorDevice2.toString());
                            System.err.printf("  Monitor[%d] %s%n", i, monitorDevice.toString());
                        }
                    }
                }
            }
        }
    }
    
    public static final void swapRotatePair(final int n, final int[] array, int n2, final int n3) {
        if (0 == n || 180 == n) {
            return;
        }
        for (int i = 0; i < n3; ++i, n2 += 2) {
            final int n4 = array[n2];
            array[n2] = array[n2 + 1];
            array[n2 + 1] = n4;
        }
    }
    
    public static class Cache
    {
        public final ArrayHashSet<DimensionImmutable> resolutions;
        public final ArrayHashSet<SurfaceSize> surfaceSizes;
        public final ArrayHashSet<MonitorMode.SizeAndRRate> sizeAndRates;
        public final ArrayHashSet<MonitorMode> monitorModes;
        public final ArrayHashSet<MonitorDevice> monitorDevices;
        private MonitorDevice primary;
        
        public Cache() {
            this.resolutions = new ArrayHashSet<DimensionImmutable>(false, 16, 0.75f);
            this.surfaceSizes = new ArrayHashSet<SurfaceSize>(false, 16, 0.75f);
            this.sizeAndRates = new ArrayHashSet<MonitorMode.SizeAndRRate>(false, 16, 0.75f);
            this.monitorModes = new ArrayHashSet<MonitorMode>(false, 16, 0.75f);
            this.monitorDevices = new ArrayHashSet<MonitorDevice>(false, 16, 0.75f);
            this.primary = null;
        }
        
        public final void setPrimary(final MonitorDevice primary) {
            this.primary = primary;
        }
        
        public final MonitorDevice getPrimary() {
            return this.primary;
        }
    }
}
