// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

public class Point implements Cloneable, PointImmutable
{
    int x;
    int y;
    
    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public Point() {
        this(0, 0);
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
    public int compareTo(final PointImmutable pointImmutable) {
        final int n = this.x * this.y;
        final int n2 = pointImmutable.getX() * pointImmutable.getY();
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
        if (o instanceof Point) {
            final Point point = (Point)o;
            return this.y == point.y && this.x == point.x;
        }
        return false;
    }
    
    @Override
    public final int getX() {
        return this.x;
    }
    
    @Override
    public final int getY() {
        return this.y;
    }
    
    @Override
    public int hashCode() {
        final int n = 31 + this.x;
        return (n << 5) - n + this.y;
    }
    
    @Override
    public String toString() {
        return this.x + " / " + this.y;
    }
    
    public final void set(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public final void setX(final int x) {
        this.x = x;
    }
    
    public final void setY(final int y) {
        this.y = y;
    }
    
    public final Point translate(final Point point) {
        this.x += point.x;
        this.y += point.y;
        return this;
    }
    
    public final Point translate(final int n, final int n2) {
        this.x += n;
        this.y += n2;
        return this;
    }
    
    public final Point scale(final int n, final int n2) {
        this.x *= n;
        this.y *= n2;
        return this;
    }
    
    public final Point scale(final float n, final float n2) {
        this.x = (int)(this.x * n + 0.5f);
        this.y = (int)(this.y * n2 + 0.5f);
        return this;
    }
    
    public final Point scaleInv(final int n, final int n2) {
        this.x /= n;
        this.y /= n2;
        return this;
    }
    
    public final Point scaleInv(final float n, final float n2) {
        this.x = (int)(this.x / n + 0.5f);
        this.y = (int)(this.y / n2 + 0.5f);
        return this;
    }
}
