// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

import java.util.List;

public class Rectangle implements Cloneable, RectangleImmutable
{
    int x;
    int y;
    int width;
    int height;
    
    public Rectangle() {
        this(0, 0, 0, 0);
    }
    
    public Rectangle(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Rectangle(final RectangleImmutable rectangleImmutable) {
        this.set(rectangleImmutable);
    }
    
    @Override
    public Object cloneMutable() {
        return this.clone();
    }
    
    @Override
    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
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
    public final int getWidth() {
        return this.width;
    }
    
    @Override
    public final int getHeight() {
        return this.height;
    }
    
    public final void set(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public final void set(final Rectangle rectangle) {
        this.x = rectangle.x;
        this.y = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
    }
    
    public final void set(final RectangleImmutable rectangleImmutable) {
        this.x = rectangleImmutable.getX();
        this.y = rectangleImmutable.getY();
        this.width = rectangleImmutable.getWidth();
        this.height = rectangleImmutable.getHeight();
    }
    
    public final void setX(final int x) {
        this.x = x;
    }
    
    public final void setY(final int y) {
        this.y = y;
    }
    
    public final void setWidth(final int width) {
        this.width = width;
    }
    
    public final void setHeight(final int height) {
        this.height = height;
    }
    
    @Override
    public final RectangleImmutable union(final RectangleImmutable rectangleImmutable) {
        return this.union(rectangleImmutable.getX(), rectangleImmutable.getY(), rectangleImmutable.getX() + rectangleImmutable.getWidth(), rectangleImmutable.getY() + rectangleImmutable.getHeight());
    }
    
    @Override
    public final RectangleImmutable union(final int n, final int n2, final int n3, final int n4) {
        final int min = Math.min(this.x, n);
        final int min2 = Math.min(this.y, n2);
        return new Rectangle(min, min2, Math.max(this.x + this.width, n3) - min, Math.max(this.y + this.height, n4) - min2);
    }
    
    public final Rectangle union(final List<RectangleImmutable> list) {
        int min = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        for (int i = list.size() - 1; i >= 0; --i) {
            final RectangleImmutable rectangleImmutable = list.get(i);
            min = Math.min(min, rectangleImmutable.getX());
            max = Math.max(max, rectangleImmutable.getX() + rectangleImmutable.getWidth());
            min2 = Math.min(min2, rectangleImmutable.getY());
            max2 = Math.max(max2, rectangleImmutable.getY() + rectangleImmutable.getHeight());
        }
        this.set(min, min2, max - min, max2 - min2);
        return this;
    }
    
    @Override
    public final RectangleImmutable intersection(final RectangleImmutable rectangleImmutable) {
        return this.intersection(rectangleImmutable.getX(), rectangleImmutable.getY(), rectangleImmutable.getX() + rectangleImmutable.getWidth(), rectangleImmutable.getY() + rectangleImmutable.getHeight());
    }
    
    @Override
    public final RectangleImmutable intersection(final int n, final int n2, final int n3, final int n4) {
        final int max = Math.max(this.x, n);
        final int max2 = Math.max(this.y, n2);
        final int min = Math.min(this.x + this.width, n3);
        final int min2 = Math.min(this.y + this.height, n4);
        int n5;
        int n6;
        if (min < max) {
            n5 = 0;
            n6 = 0;
        }
        else {
            n5 = max;
            n6 = min - max;
        }
        int n7;
        int n8;
        if (min2 < max2) {
            n7 = 0;
            n8 = 0;
        }
        else {
            n7 = max2;
            n8 = min2 - max2;
        }
        return new Rectangle(n5, n7, n6, n8);
    }
    
    @Override
    public final float coverage(final RectangleImmutable rectangleImmutable) {
        final RectangleImmutable intersection = this.intersection(rectangleImmutable);
        return intersection.getWidth() * intersection.getHeight() / (this.width * this.height);
    }
    
    public final Rectangle scale(final int n, final int n2) {
        this.x *= n;
        this.y *= n2;
        this.width *= n;
        this.height *= n2;
        return this;
    }
    
    public final Rectangle scaleInv(final int n, final int n2) {
        this.x /= n;
        this.y /= n2;
        this.width /= n;
        this.height /= n2;
        return this;
    }
    
    @Override
    public int compareTo(final RectangleImmutable rectangleImmutable) {
        final int n = this.width * this.height;
        final int n2 = rectangleImmutable.getWidth() * rectangleImmutable.getHeight();
        if (n > n2) {
            return 1;
        }
        if (n < n2) {
            return -1;
        }
        final int n3 = this.x * this.y;
        final int n4 = rectangleImmutable.getX() * rectangleImmutable.getY();
        if (n3 > n4) {
            return 1;
        }
        if (n3 < n4) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Rectangle) {
            final Rectangle rectangle = (Rectangle)o;
            return this.y == rectangle.y && this.x == rectangle.x && this.height == rectangle.height && this.width == rectangle.width;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int n = this.x + this.height;
        final int n2 = this.width + this.y;
        final int n3 = n * (n + 1) / 2 + this.x;
        final int n4 = n2 * (n2 + 1) / 2 + this.y;
        final int n5 = n3 + n4;
        return n5 * (n5 + 1) / 2 + n4;
    }
    
    @Override
    public String toString() {
        return "[ " + this.x + " / " + this.y + "  " + this.width + " x " + this.height + " ]";
    }
}
