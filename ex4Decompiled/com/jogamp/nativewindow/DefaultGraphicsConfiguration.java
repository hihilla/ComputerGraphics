// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import jogamp.nativewindow.Debug;

public class DefaultGraphicsConfiguration implements Cloneable, AbstractGraphicsConfiguration
{
    protected static final boolean DEBUG;
    private AbstractGraphicsScreen screen;
    protected CapabilitiesImmutable capabilitiesChosen;
    protected CapabilitiesImmutable capabilitiesRequested;
    
    public DefaultGraphicsConfiguration(final AbstractGraphicsScreen screen, final CapabilitiesImmutable capabilitiesChosen, final CapabilitiesImmutable capabilitiesRequested) {
        if (null == screen) {
            throw new IllegalArgumentException("Null screen");
        }
        if (null == capabilitiesChosen) {
            throw new IllegalArgumentException("Null chosen caps");
        }
        if (null == capabilitiesRequested) {
            throw new IllegalArgumentException("Null requested caps");
        }
        this.screen = screen;
        this.capabilitiesChosen = capabilitiesChosen;
        this.capabilitiesRequested = capabilitiesRequested;
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new NativeWindowException(ex);
        }
    }
    
    @Override
    public final AbstractGraphicsScreen getScreen() {
        return this.screen;
    }
    
    @Override
    public final CapabilitiesImmutable getChosenCapabilities() {
        return this.capabilitiesChosen;
    }
    
    @Override
    public final CapabilitiesImmutable getRequestedCapabilities() {
        return this.capabilitiesRequested;
    }
    
    @Override
    public AbstractGraphicsConfiguration getNativeGraphicsConfiguration() {
        return this;
    }
    
    @Override
    public final int getVisualID(final VisualIDHolder.VIDType vidType) throws NativeWindowException {
        return this.capabilitiesChosen.getVisualID(vidType);
    }
    
    protected void setChosenCapabilities(final CapabilitiesImmutable capabilitiesChosen) {
        this.capabilitiesChosen = capabilitiesChosen;
    }
    
    protected void setScreen(final AbstractGraphicsScreen screen) {
        this.screen = screen;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.screen + ",\n\tchosen    " + this.capabilitiesChosen + ",\n\trequested " + this.capabilitiesRequested + "]";
    }
    
    public static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    public static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    static {
        DEBUG = Debug.debug("GraphicsConfiguration");
    }
}
