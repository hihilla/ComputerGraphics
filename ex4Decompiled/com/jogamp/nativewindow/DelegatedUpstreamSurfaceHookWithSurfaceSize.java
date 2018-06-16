// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class DelegatedUpstreamSurfaceHookWithSurfaceSize implements UpstreamSurfaceHook
{
    final UpstreamSurfaceHook upstream;
    final NativeSurface surface;
    
    public DelegatedUpstreamSurfaceHookWithSurfaceSize(final UpstreamSurfaceHook upstream, final NativeSurface surface) {
        this.upstream = upstream;
        this.surface = surface;
        if (null == surface) {
            throw new IllegalArgumentException("given surface is null");
        }
    }
    
    @Override
    public final NativeSurface getUpstreamSurface() {
        return null;
    }
    
    @Override
    public final void create(final ProxySurface proxySurface) {
        if (null != this.upstream) {
            this.upstream.create(proxySurface);
        }
    }
    
    @Override
    public final void destroy(final ProxySurface proxySurface) {
        if (null != this.upstream) {
            this.upstream.destroy(proxySurface);
        }
    }
    
    @Override
    public final int getSurfaceWidth(final ProxySurface proxySurface) {
        return this.surface.getSurfaceWidth();
    }
    
    @Override
    public final int getSurfaceHeight(final ProxySurface proxySurface) {
        return this.surface.getSurfaceHeight();
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.upstream + ", " + ((null != this.surface) ? (this.surface.getClass().getName() + ": 0x" + Long.toHexString(this.surface.getSurfaceHandle()) + " " + this.surface.getSurfaceWidth() + "x" + this.surface.getSurfaceHeight()) : "nil") + "]";
    }
}
