// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class UpstreamSurfaceHookMutableSize implements UpstreamSurfaceHook.MutableSize
{
    int pixWidth;
    int pixHeight;
    
    public UpstreamSurfaceHookMutableSize(final int pixWidth, final int pixHeight) {
        this.pixWidth = pixWidth;
        this.pixHeight = pixHeight;
    }
    
    @Override
    public final void setSurfaceSize(final int pixWidth, final int pixHeight) {
        this.pixWidth = pixWidth;
        this.pixHeight = pixHeight;
    }
    
    @Override
    public final int getSurfaceWidth(final ProxySurface proxySurface) {
        return this.pixWidth;
    }
    
    @Override
    public final int getSurfaceHeight(final ProxySurface proxySurface) {
        return this.pixHeight;
    }
    
    @Override
    public void create(final ProxySurface proxySurface) {
    }
    
    @Override
    public void destroy(final ProxySurface proxySurface) {
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[pixel " + this.pixWidth + "x" + this.pixHeight + "]";
    }
    
    @Override
    public final NativeSurface getUpstreamSurface() {
        return null;
    }
}
