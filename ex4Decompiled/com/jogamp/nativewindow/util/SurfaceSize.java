// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

public class SurfaceSize implements Comparable<SurfaceSize>
{
    final DimensionImmutable resolution;
    final int bitsPerPixel;
    
    public SurfaceSize(final DimensionImmutable resolution, final int bitsPerPixel) {
        if (null == resolution || bitsPerPixel <= 0) {
            throw new IllegalArgumentException("resolution must be set and bitsPerPixel greater 0");
        }
        this.resolution = resolution;
        this.bitsPerPixel = bitsPerPixel;
    }
    
    public final DimensionImmutable getResolution() {
        return this.resolution;
    }
    
    public final int getBitsPerPixel() {
        return this.bitsPerPixel;
    }
    
    @Override
    public final String toString() {
        return "[ " + this.resolution + " pixels x " + this.bitsPerPixel + " bpp ]";
    }
    
    @Override
    public int compareTo(final SurfaceSize surfaceSize) {
        final int compareTo = this.resolution.compareTo(surfaceSize.getResolution());
        if (compareTo != 0) {
            return compareTo;
        }
        final int bitsPerPixel = surfaceSize.getBitsPerPixel();
        if (this.bitsPerPixel > bitsPerPixel) {
            return 1;
        }
        if (this.bitsPerPixel < bitsPerPixel) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof SurfaceSize) {
            final SurfaceSize surfaceSize = (SurfaceSize)o;
            return this.getResolution().equals(surfaceSize.getResolution()) && this.getBitsPerPixel() == surfaceSize.getBitsPerPixel();
        }
        return false;
    }
    
    @Override
    public final int hashCode() {
        final int hashCode = this.getResolution().hashCode();
        return (hashCode << 5) - hashCode + this.getBitsPerPixel();
    }
}
