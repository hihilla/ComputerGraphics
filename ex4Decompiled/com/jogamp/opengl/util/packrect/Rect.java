// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.packrect;

public class Rect
{
    private int x;
    private int y;
    private int w;
    private int h;
    private Level level;
    private Object userData;
    private Rect nextLocation;
    
    public Rect() {
        this(null);
    }
    
    public Rect(final Object o) {
        this(0, 0, 0, 0, o);
    }
    
    public Rect(final int n, final int n2, final int n3, final int n4, final Object userData) {
        this.setPosition(n, n2);
        this.setSize(n3, n4);
        this.setUserData(userData);
    }
    
    public int x() {
        return this.x;
    }
    
    public int y() {
        return this.y;
    }
    
    public int w() {
        return this.w;
    }
    
    public int h() {
        return this.h;
    }
    
    public Object getUserData() {
        return this.userData;
    }
    
    public Rect getNextLocation() {
        return this.nextLocation;
    }
    
    public void setPosition(final int x, final int y) {
        if (x < 0) {
            throw new IllegalArgumentException("Negative x");
        }
        if (y < 0) {
            throw new IllegalArgumentException("Negative y");
        }
        this.x = x;
        this.y = y;
    }
    
    public void setSize(final int w, final int h) throws IllegalArgumentException {
        if (w < 0) {
            throw new IllegalArgumentException("Negative width");
        }
        if (h < 0) {
            throw new IllegalArgumentException("Negative height");
        }
        this.w = w;
        this.h = h;
    }
    
    public void setUserData(final Object userData) {
        this.userData = userData;
    }
    
    public void setNextLocation(final Rect nextLocation) {
        this.nextLocation = nextLocation;
    }
    
    public int maxX() {
        if (this.w() < 1) {
            return -1;
        }
        return this.x() + this.w() - 1;
    }
    
    public int maxY() {
        if (this.h() < 1) {
            return -1;
        }
        return this.y() + this.h() - 1;
    }
    
    public boolean canContain(final Rect rect) {
        return this.w() >= rect.w() && this.h() >= rect.h();
    }
    
    @Override
    public String toString() {
        return "[Rect x: " + this.x() + " y: " + this.y() + " w: " + this.w() + " h: " + this.h() + "]";
    }
}
