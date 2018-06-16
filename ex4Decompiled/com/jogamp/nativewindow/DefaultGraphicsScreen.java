// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class DefaultGraphicsScreen implements Cloneable, AbstractGraphicsScreen
{
    private final AbstractGraphicsDevice device;
    private final int idx;
    
    public DefaultGraphicsScreen(final AbstractGraphicsDevice device, final int idx) {
        this.device = device;
        this.idx = idx;
    }
    
    public static AbstractGraphicsScreen createDefault(final String s) {
        return new DefaultGraphicsScreen(new DefaultGraphicsDevice(s, DefaultGraphicsDevice.getDefaultDisplayConnection(s), 0), 0);
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
    public AbstractGraphicsDevice getDevice() {
        return this.device;
    }
    
    @Override
    public int getIndex() {
        return this.idx;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.device + ", idx " + this.idx + "]";
    }
}
