// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.nativewindow.CapabilitiesImmutable;

public interface GLCapabilitiesImmutable extends CapabilitiesImmutable
{
    public static final String DEFAULT_SAMPLE_EXTENSION = "default";
    
    GLProfile getGLProfile();
    
    int getAccumAlphaBits();
    
    int getAccumBlueBits();
    
    int getAccumGreenBits();
    
    int getAccumRedBits();
    
    int getDepthBits();
    
    boolean getDoubleBuffered();
    
    boolean getHardwareAccelerated();
    
    String getSampleExtension();
    
    boolean getSampleBuffers();
    
    int getNumSamples();
    
    int getStencilBits();
    
    boolean getStereo();
    
    boolean isPBuffer();
    
    boolean isFBO();
    
    boolean equals(final Object p0);
    
    int hashCode();
    
    String toString();
}
