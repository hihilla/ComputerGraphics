// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class UpstreamWindowHookMutableSizePos extends UpstreamSurfaceHookMutableSize
{
    int winX;
    int winY;
    int winWidth;
    int winHeight;
    
    public UpstreamWindowHookMutableSizePos(final int winX, final int winY, final int winWidth, final int winHeight, final int n, final int n2) {
        super(n, n2);
        this.winX = winX;
        this.winY = winY;
        this.winWidth = winWidth;
        this.winHeight = winHeight;
    }
    
    public final void setWinPos(final int winX, final int winY) {
        this.winX = winX;
        this.winY = winY;
    }
    
    public final void setWinSize(final int winWidth, final int winHeight) {
        this.setSurfaceSize(this.winWidth = winWidth, this.winHeight = winHeight);
    }
    
    public final int getX() {
        return this.winX;
    }
    
    public final int getY() {
        return this.winY;
    }
    
    public final int getWidth() {
        return this.winWidth;
    }
    
    public final int getHeight() {
        return this.winHeight;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[window " + this.winX + "/" + this.winY + " " + this.winWidth + "x" + this.winHeight + ", pixel " + this.pixWidth + "x" + this.pixHeight + "]";
    }
}
