// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.opengl.GL;
import com.jogamp.nativewindow.util.DimensionImmutable;

public interface StereoDeviceRenderer
{
    public static final int DISTORTION_BARREL = 1;
    public static final int DISTORTION_CHROMATIC = 2;
    public static final int DISTORTION_VIGNETTE = 4;
    public static final int DISTORTION_TIMEWARP = 8;
    
    StereoDevice getDevice();
    
    Eye getEye(final int p0);
    
    ViewerPose updateViewerPose();
    
    ViewerPose getLastViewerPose();
    
    int getDistortionBits();
    
    boolean usesSideBySideStereo();
    
    DimensionImmutable[] getEyeSurfaceSize();
    
    DimensionImmutable getTotalSurfaceSize();
    
    int getTextureCount();
    
    int getTextureUnit();
    
    void init(final GL p0);
    
    void dispose(final GL p0);
    
    void beginFrame(final GL p0);
    
    void endFrame(final GL p0);
    
    boolean ppAvailable();
    
    void ppBegin(final GL p0);
    
    void ppOneEye(final GL p0, final int p1);
    
    void ppEnd(final GL p0);
    
    public interface Eye
    {
        RectangleImmutable getViewport();
        
        EyeParameter getEyeParameter();
    }
}
