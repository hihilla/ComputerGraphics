// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import com.jogamp.common.type.WriteCloneable;

public interface CapabilitiesImmutable extends VisualIDHolder, WriteCloneable, Comparable<CapabilitiesImmutable>
{
    int getRedBits();
    
    int getGreenBits();
    
    int getBlueBits();
    
    int getAlphaBits();
    
    boolean isBackgroundOpaque();
    
    boolean isOnscreen();
    
    boolean isBitmap();
    
    int getTransparentRedValue();
    
    int getTransparentGreenValue();
    
    int getTransparentBlueValue();
    
    int getTransparentAlphaValue();
    
    boolean equals(final Object p0);
    
    int hashCode();
    
    StringBuilder toString(final StringBuilder p0);
    
    String toString();
}
