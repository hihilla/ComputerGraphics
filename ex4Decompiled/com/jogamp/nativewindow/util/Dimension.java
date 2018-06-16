// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

public class Dimension implements Cloneable, DimensionImmutable
{
    int width;
    int height;
    
    public Dimension() {
        this(0, 0);
    }
    
    public Dimension(final int[] array) {
        this(array[0], array[1]);
    }
    
    public Dimension(final int width, final int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("width and height must be within: [0..2147483647]");
        }
        this.width = width;
        this.height = height;
    }
    
    @Override
    public Object cloneMutable() {
        return this.clone();
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }
    
    @Override
    public final int getWidth() {
        return this.width;
    }
    
    @Override
    public final int getHeight() {
        return this.height;
    }
    
    public final void set(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    
    public final void setWidth(final int width) {
        this.width = width;
    }
    
    public final void setHeight(final int height) {
        this.height = height;
    }
    
    public final Dimension scale(final int n) {
        this.width *= n;
        this.height *= n;
        return this;
    }
    
    public final Dimension add(final Dimension dimension) {
        this.width += dimension.width;
        this.height += dimension.height;
        return this;
    }
    
    @Override
    public String toString() {
        return this.width + " x " + this.height;
    }
    
    @Override
    public int compareTo(final DimensionImmutable dimensionImmutable) {
        final int n = this.width * this.height;
        final int n2 = dimensionImmutable.getWidth() * dimensionImmutable.getHeight();
        if (n > n2) {
            return 1;
        }
        if (n < n2) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Dimension) {
            final Dimension dimension = (Dimension)o;
            return this.height == dimension.height && this.width == dimension.width;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int n = 31 + this.width;
        return (n << 5) - n + this.height;
    }
}
