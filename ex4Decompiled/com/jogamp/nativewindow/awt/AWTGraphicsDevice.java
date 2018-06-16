// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.awt;

import java.awt.GraphicsEnvironment;
import com.jogamp.nativewindow.NativeWindowFactory;
import java.awt.GraphicsDevice;
import com.jogamp.nativewindow.DefaultGraphicsDevice;

public class AWTGraphicsDevice extends DefaultGraphicsDevice implements Cloneable
{
    private final GraphicsDevice device;
    
    public AWTGraphicsDevice(final GraphicsDevice device, final int n) {
        super(NativeWindowFactory.TYPE_AWT, device.getIDstring(), n);
        this.device = device;
    }
    
    public static AWTGraphicsDevice createDefault() {
        return new AWTGraphicsDevice(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), 0);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    public GraphicsDevice getGraphicsDevice() {
        return this.device;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[type " + this.getType() + ", connection " + this.getConnection() + ", unitID " + this.getUnitID() + ", awtDevice " + this.device + ", handle 0x" + Long.toHexString(this.getHandle()) + "]";
    }
}
