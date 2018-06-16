// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public interface ScalableSurface
{
    public static final float IDENTITY_PIXELSCALE = 1.0f;
    public static final float AUTOMAX_PIXELSCALE = 0.0f;
    
    boolean setSurfaceScale(final float[] p0);
    
    float[] getRequestedSurfaceScale(final float[] p0);
    
    float[] getCurrentSurfaceScale(final float[] p0);
    
    float[] getMinimumSurfaceScale(final float[] p0);
    
    float[] getMaximumSurfaceScale(final float[] p0);
}
