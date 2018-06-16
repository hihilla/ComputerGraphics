// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

public class Insets implements Cloneable, InsetsImmutable
{
    static final InsetsImmutable zeroInsets;
    private int l;
    private int r;
    private int t;
    private int b;
    
    public static final InsetsImmutable getZero() {
        return Insets.zeroInsets;
    }
    
    public Insets() {
        this(0, 0, 0, 0);
    }
    
    public Insets(final int l, final int r, final int t, final int b) {
        this.l = l;
        this.r = r;
        this.t = t;
        this.b = b;
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
    public final int getLeftWidth() {
        return this.l;
    }
    
    @Override
    public final int getRightWidth() {
        return this.r;
    }
    
    @Override
    public final int getTotalWidth() {
        return this.l + this.r;
    }
    
    @Override
    public final int getTopHeight() {
        return this.t;
    }
    
    @Override
    public final int getBottomHeight() {
        return this.b;
    }
    
    @Override
    public final int getTotalHeight() {
        return this.t + this.b;
    }
    
    public final void set(final int l, final int r, final int t, final int b) {
        this.l = l;
        this.r = r;
        this.t = t;
        this.b = b;
    }
    
    public final void setLeftWidth(final int l) {
        this.l = l;
    }
    
    public final void setRightWidth(final int r) {
        this.r = r;
    }
    
    public final void setTopHeight(final int t) {
        this.t = t;
    }
    
    public final void setBottomHeight(final int b) {
        this.b = b;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Insets) {
            final Insets insets = (Insets)o;
            return this.r == insets.r && this.l == insets.l && this.b == insets.b && this.t == insets.t;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int n = this.l + this.b;
        final int n2 = this.t + this.r;
        final int n3 = n * (n + 1) / 2 + this.l;
        final int n4 = n2 * (n2 + 1) / 2 + this.r;
        final int n5 = n3 + n4;
        return n5 * (n5 + 1) / 2 + n4;
    }
    
    @Override
    public String toString() {
        return "[ l " + this.l + ", r " + this.r + " - t " + this.t + ", b " + this.b + " - " + this.getTotalWidth() + "x" + this.getTotalHeight() + "]";
    }
    
    static {
        zeroInsets = new Insets();
    }
}
