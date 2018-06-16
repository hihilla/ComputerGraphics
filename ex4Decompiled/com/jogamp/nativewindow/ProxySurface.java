// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import jogamp.nativewindow.Debug;

public interface ProxySurface extends MutableSurface
{
    public static final boolean DEBUG = Debug.debug("ProxySurface");
    public static final int OPT_PROXY_OWNS_UPSTREAM_SURFACE = 64;
    public static final int OPT_PROXY_OWNS_UPSTREAM_DEVICE = 128;
    public static final int OPT_UPSTREAM_WINDOW_INVISIBLE = 256;
    public static final int OPT_UPSTREAM_SURFACELESS = 512;
    
    void setGraphicsConfiguration(final AbstractGraphicsConfiguration p0);
    
    NativeSurface getUpstreamSurface();
    
    UpstreamSurfaceHook getUpstreamSurfaceHook();
    
    void setUpstreamSurfaceHook(final UpstreamSurfaceHook p0);
    
    void enableUpstreamSurfaceHookLifecycle(final boolean p0);
    
    void createNotify();
    
    void destroyNotify();
    
    StringBuilder getUpstreamOptionBits(final StringBuilder p0);
    
    int getUpstreamOptionBits();
    
    boolean containsUpstreamOptionBits(final int p0);
    
    void addUpstreamOptionBits(final int p0);
    
    void clearUpstreamOptionBits(final int p0);
    
    StringBuilder toString(final StringBuilder p0);
    
    String toString();
}
