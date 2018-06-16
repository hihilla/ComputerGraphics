// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class DelegatedUpstreamSurfaceHookMutableSize extends UpstreamSurfaceHookMutableSize
{
    final UpstreamSurfaceHook upstream;
    
    public DelegatedUpstreamSurfaceHookMutableSize(final UpstreamSurfaceHook upstream, final int n, final int n2) {
        super(n, n2);
        this.upstream = upstream;
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
    public String toString() {
        return this.getClass().getSimpleName() + "[ " + this.pixWidth + "x" + this.pixHeight + ", " + this.upstream + "]";
    }
}
