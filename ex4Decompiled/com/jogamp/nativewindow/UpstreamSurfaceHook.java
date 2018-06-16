// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public interface UpstreamSurfaceHook
{
    void create(final ProxySurface p0);
    
    void destroy(final ProxySurface p0);
    
    NativeSurface getUpstreamSurface();
    
    int getSurfaceWidth(final ProxySurface p0);
    
    int getSurfaceHeight(final ProxySurface p0);
    
    public interface MutableSize extends UpstreamSurfaceHook
    {
        void setSurfaceSize(final int p0, final int p1);
    }
}
