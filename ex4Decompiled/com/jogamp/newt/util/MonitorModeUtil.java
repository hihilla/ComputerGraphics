// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.util;

import com.jogamp.nativewindow.util.DimensionImmutable;
import java.util.ArrayList;
import com.jogamp.nativewindow.util.SurfaceSize;
import java.util.Comparator;
import java.util.Collections;
import com.jogamp.newt.MonitorMode;
import java.util.List;

public class MonitorModeUtil
{
    public static int getIndex(final List<MonitorMode> list, final MonitorMode monitorMode) {
        return list.indexOf(monitorMode);
    }
    
    public static int getIndexByHashCode(final List<MonitorMode> list, final MonitorMode monitorMode) {
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                if (monitorMode.hashCode() == list.get(i).hashCode()) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static MonitorMode getByNativeSizeRateIdAndRotation(final List<MonitorMode> list, final MonitorMode.SizeAndRRate sizeAndRRate, final int n, final int n2) {
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                final MonitorMode monitorMode = list.get(i);
                if (monitorMode.getSizeAndRRate().equals(sizeAndRRate) && monitorMode.getId() == n && monitorMode.getRotation() == n2) {
                    return monitorMode;
                }
            }
        }
        return null;
    }
    
    public static void sort(final List<MonitorMode> list, final boolean b) {
        if (b) {
            Collections.sort((List<Comparable>)list);
        }
        else {
            Collections.sort((List<T>)list, (Comparator<? super T>)MonitorMode.monitorModeComparatorInv);
        }
    }
    
    public static List<MonitorMode> filterBySurfaceSize(final List<MonitorMode> list, final SurfaceSize surfaceSize) {
        final ArrayList<MonitorMode> list2 = new ArrayList<MonitorMode>();
        if (null != list && list.size() > 0) {
            for (int n = 0; null != list && n < list.size(); ++n) {
                final MonitorMode monitorMode = list.get(n);
                if (monitorMode.getSurfaceSize().equals(surfaceSize)) {
                    list2.add(monitorMode);
                }
            }
        }
        return list2;
    }
    
    public static List<MonitorMode> filterByRotation(final List<MonitorMode> list, final int n) {
        final ArrayList<MonitorMode> list2 = new ArrayList<MonitorMode>();
        if (null != list && list.size() > 0) {
            for (int n2 = 0; null != list && n2 < list.size(); ++n2) {
                final MonitorMode monitorMode = list.get(n2);
                if (monitorMode.getRotation() == n) {
                    list2.add(monitorMode);
                }
            }
        }
        return list2;
    }
    
    public static List<MonitorMode> filterByBpp(final List<MonitorMode> list, final int n) {
        final ArrayList<MonitorMode> list2 = new ArrayList<MonitorMode>();
        if (null != list && list.size() > 0) {
            for (int n2 = 0; null != list && n2 < list.size(); ++n2) {
                final MonitorMode monitorMode = list.get(n2);
                if (monitorMode.getSurfaceSize().getBitsPerPixel() == n) {
                    list2.add(monitorMode);
                }
            }
        }
        return list2;
    }
    
    public static List<MonitorMode> filterByFlags(final List<MonitorMode> list, final int n) {
        final ArrayList<MonitorMode> list2 = new ArrayList<MonitorMode>();
        if (null != list && list.size() > 0) {
            for (int n2 = 0; null != list && n2 < list.size(); ++n2) {
                final MonitorMode monitorMode = list.get(n2);
                if (monitorMode.getFlags() == n) {
                    list2.add(monitorMode);
                }
            }
        }
        return list2;
    }
    
    public static List<MonitorMode> filterByResolution(final List<MonitorMode> list, final DimensionImmutable dimensionImmutable) {
        final ArrayList<MonitorMode> list2 = new ArrayList<MonitorMode>();
        if (null != list && list.size() > 0) {
            final int n = dimensionImmutable.getHeight() * dimensionImmutable.getWidth();
            int n2 = Integer.MAX_VALUE;
            int n3 = 0;
            for (int n4 = 0; null != list && n4 < list.size(); ++n4) {
                final MonitorMode monitorMode = list.get(n4);
                final DimensionImmutable resolution = monitorMode.getSurfaceSize().getResolution();
                final int abs = Math.abs(n - resolution.getHeight() * resolution.getWidth());
                if (abs < n2) {
                    n2 = abs;
                    n3 = n4;
                }
                if (resolution.equals(dimensionImmutable)) {
                    list2.add(monitorMode);
                }
            }
            if (list2.size() == 0 && 0 <= n3) {
                list2.add(list.get(n3));
            }
        }
        return list2;
    }
    
    public static List<MonitorMode> filterByRate(final List<MonitorMode> list, final float n) {
        final ArrayList<MonitorMode> list2 = new ArrayList<MonitorMode>();
        if (null != list && list.size() > 0) {
            float n2 = Float.MAX_VALUE;
            int n3 = -1;
            for (int n4 = 0; null != list && n4 < list.size(); ++n4) {
                final MonitorMode monitorMode = list.get(n4);
                final float abs = Math.abs(n - monitorMode.getRefreshRate());
                if (abs < n2) {
                    n2 = abs;
                    n3 = n4;
                }
                if (0.0f == abs) {
                    list2.add(monitorMode);
                }
            }
            if (list2.size() == 0 && 0 <= n3) {
                list2.add(list.get(n3));
            }
        }
        return list2;
    }
    
    public static List<MonitorMode> getHighestAvailableBpp(final List<MonitorMode> list) {
        if (null != list && list.size() > 0) {
            int n = -1;
            for (int n2 = 0; null != list && n2 < list.size(); ++n2) {
                final int bitsPerPixel = list.get(n2).getSurfaceSize().getBitsPerPixel();
                if (bitsPerPixel > n) {
                    n = bitsPerPixel;
                }
            }
            return filterByBpp(list, n);
        }
        return new ArrayList<MonitorMode>();
    }
    
    public static List<MonitorMode> getHighestAvailableRate(final List<MonitorMode> list) {
        if (null != list && list.size() > 0) {
            float n = -1.0f;
            for (int n2 = 0; null != list && n2 < list.size(); ++n2) {
                final float refreshRate = list.get(n2).getRefreshRate();
                if (refreshRate > n) {
                    n = refreshRate;
                }
            }
            return filterByRate(list, n);
        }
        return new ArrayList<MonitorMode>();
    }
}
