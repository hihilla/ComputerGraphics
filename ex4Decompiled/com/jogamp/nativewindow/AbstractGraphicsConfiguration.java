// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public interface AbstractGraphicsConfiguration extends VisualIDHolder, Cloneable
{
    Object clone();
    
    AbstractGraphicsScreen getScreen();
    
    CapabilitiesImmutable getChosenCapabilities();
    
    CapabilitiesImmutable getRequestedCapabilities();
    
    AbstractGraphicsConfiguration getNativeGraphicsConfiguration();
}
