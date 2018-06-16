// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class MutableGraphicsConfiguration extends DefaultGraphicsConfiguration
{
    public MutableGraphicsConfiguration(final AbstractGraphicsScreen abstractGraphicsScreen, final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2) {
        super(abstractGraphicsScreen, capabilitiesImmutable, capabilitiesImmutable2);
    }
    
    public void setChosenCapabilities(final CapabilitiesImmutable chosenCapabilities) {
        super.setChosenCapabilities(chosenCapabilities);
    }
    
    public void setScreen(final AbstractGraphicsScreen screen) {
        super.setScreen(screen);
    }
}
