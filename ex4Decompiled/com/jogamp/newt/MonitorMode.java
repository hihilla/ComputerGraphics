// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt;

import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.nativewindow.util.SurfaceSize;
import java.util.Comparator;

public class MonitorMode implements Comparable<MonitorMode>
{
    public static final Comparator<MonitorMode> monitorModeComparator;
    public static final Comparator<MonitorMode> monitorModeComparatorInv;
    public static final int ROTATE_0 = 0;
    public static final int ROTATE_90 = 90;
    public static final int ROTATE_180 = 180;
    public static final int ROTATE_270 = 270;
    public static final int FLAG_INTERLACE = 1;
    public static final int FLAG_DOUBLESCAN = 2;
    private final int nativeId;
    private final SizeAndRRate sizeAndRRate;
    private final int rotation;
    private final int hashCode;
    
    public static boolean isRotationValid(final int n) {
        return n == 0 || n == 90 || n == 180 || n == 270;
    }
    
    public MonitorMode(final int nativeId, final SizeAndRRate sizeAndRRate, final int rotation) {
        if (!isRotationValid(rotation)) {
            throw new RuntimeException("invalid rotation: " + rotation);
        }
        this.nativeId = nativeId;
        this.sizeAndRRate = sizeAndRRate;
        this.rotation = rotation;
        this.hashCode = this.getHashCode();
    }
    
    public MonitorMode(final SurfaceSize surfaceSize, final float n, final int n2, final int n3) {
        this(0, new SizeAndRRate(surfaceSize, n, n2), n3);
    }
    
    public final int getId() {
        return this.nativeId;
    }
    
    public final SizeAndRRate getSizeAndRRate() {
        return this.sizeAndRRate;
    }
    
    public final SurfaceSize getSurfaceSize() {
        return this.sizeAndRRate.surfaceSize;
    }
    
    public final float getRefreshRate() {
        return this.sizeAndRRate.refreshRate;
    }
    
    public final int getFlags() {
        return this.sizeAndRRate.flags;
    }
    
    public final int getRotation() {
        return this.rotation;
    }
    
    public final int getRotatedWidth() {
        return this.getRotatedWH(true);
    }
    
    public final int getRotatedHeight() {
        return this.getRotatedWH(false);
    }
    
    @Override
    public final String toString() {
        return "[Id " + Display.toHexString(this.nativeId) + ", " + this.sizeAndRRate + ", " + this.rotation + " degr]";
    }
    
    @Override
    public int compareTo(final MonitorMode monitorMode) {
        final int compareTo = this.sizeAndRRate.compareTo(monitorMode.sizeAndRRate);
        if (compareTo != 0) {
            return compareTo;
        }
        final int n = 360 - this.rotation;
        final int n2 = 360 - monitorMode.rotation;
        if (n > n2) {
            return 1;
        }
        if (n < n2) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof MonitorMode) {
            final MonitorMode monitorMode = (MonitorMode)o;
            return monitorMode.nativeId == this.nativeId && monitorMode.sizeAndRRate.equals(this.sizeAndRRate) && monitorMode.rotation == this.rotation;
        }
        return false;
    }
    
    @Override
    public final int hashCode() {
        return this.hashCode;
    }
    
    private final int getHashCode() {
        final int n = 31 + this.getId();
        final int n2 = (n << 5) - n + this.sizeAndRRate.hashCode();
        return (n2 << 5) - n2 + this.getRotation();
    }
    
    private final int getRotatedWH(final boolean b) {
        final DimensionImmutable resolution = this.sizeAndRRate.surfaceSize.getResolution();
        final boolean b2 = 90 == this.rotation || 270 == this.rotation;
        if ((b && b2) || (!b && !b2)) {
            return resolution.getHeight();
        }
        return resolution.getWidth();
    }
    
    static {
        monitorModeComparator = new Comparator<MonitorMode>() {
            @Override
            public int compare(final MonitorMode monitorMode, final MonitorMode monitorMode2) {
                return monitorMode.compareTo(monitorMode2);
            }
        };
        monitorModeComparatorInv = new Comparator<MonitorMode>() {
            @Override
            public int compare(final MonitorMode monitorMode, final MonitorMode monitorMode2) {
                return monitorMode2.compareTo(monitorMode);
            }
        };
    }
    
    public static class SizeAndRRate implements Comparable<SizeAndRRate>
    {
        public final SurfaceSize surfaceSize;
        public final int flags;
        public final float refreshRate;
        public final int hashCode;
        private static final String STR_INTERLACE = "Interlace";
        private static final String STR_DOUBLESCAN = "DoubleScan";
        private static final String STR_SEP = ", ";
        
        public SizeAndRRate(final SurfaceSize surfaceSize, final float refreshRate, final int flags) {
            if (null == surfaceSize) {
                throw new IllegalArgumentException("surfaceSize must be set (" + surfaceSize + ")");
            }
            this.surfaceSize = surfaceSize;
            this.flags = flags;
            this.refreshRate = refreshRate;
            this.hashCode = this.getHashCode();
        }
        
        public static final StringBuilder flags2String(final int n) {
            final StringBuilder sb = new StringBuilder();
            boolean b = false;
            if (0x0 != (n & 0x1)) {
                sb.append("Interlace");
                b = true;
            }
            if (0x0 != (n & 0x2)) {
                if (b) {
                    sb.append(", ");
                }
                sb.append("DoubleScan");
            }
            return sb;
        }
        
        @Override
        public final String toString() {
            return this.surfaceSize + " @ " + this.refreshRate + " Hz, flags [" + flags2String(this.flags).toString() + "]";
        }
        
        @Override
        public int compareTo(final SizeAndRRate sizeAndRRate) {
            final int compareTo = this.surfaceSize.compareTo(sizeAndRRate.surfaceSize);
            if (compareTo != 0) {
                return compareTo;
            }
            final int n = (0 == this.flags) ? Integer.MAX_VALUE : this.flags;
            final int n2 = (0 == sizeAndRRate.flags) ? Integer.MAX_VALUE : sizeAndRRate.flags;
            if (n == n2) {
                final float n3 = this.refreshRate - sizeAndRRate.refreshRate;
                if (Math.abs(n3) < 0.01f) {
                    return 0;
                }
                if (n3 > 0.01f) {
                    return 1;
                }
                return -1;
            }
            else {
                if (n > n2) {
                    return 1;
                }
                if (n < n2) {
                    return -1;
                }
                return 0;
            }
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof SizeAndRRate) {
                final SizeAndRRate sizeAndRRate = (SizeAndRRate)o;
                return this.surfaceSize.equals(sizeAndRRate.surfaceSize) && this.flags == sizeAndRRate.flags && this.refreshRate == sizeAndRRate.refreshRate;
            }
            return false;
        }
        
        @Override
        public final int hashCode() {
            return this.hashCode;
        }
        
        private final int getHashCode() {
            final int n = 31 + this.surfaceSize.hashCode();
            final int n2 = (n << 5) - n + this.flags;
            return (n2 << 5) - n2 + (int)(this.refreshRate * 100.0f);
        }
    }
}
