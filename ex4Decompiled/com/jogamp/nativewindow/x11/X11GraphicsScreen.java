// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.x11;

import jogamp.nativewindow.x11.X11Lib;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.DefaultGraphicsScreen;

public class X11GraphicsScreen extends DefaultGraphicsScreen implements Cloneable
{
    public X11GraphicsScreen(final X11GraphicsDevice x11GraphicsDevice, final int n) {
        super(x11GraphicsDevice, x11GraphicsDevice.isXineramaEnabled() ? 0 : n);
    }
    
    public static AbstractGraphicsScreen createScreenDevice(final long n, final int n2, final boolean b) {
        if (0L == n) {
            throw new NativeWindowException("display is null");
        }
        return new X11GraphicsScreen(new X11GraphicsDevice(n, 0, b), n2);
    }
    
    public int getVisualID() {
        return X11Lib.DefaultVisualID(this.getDevice().getHandle(), this.getIndex());
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
}
