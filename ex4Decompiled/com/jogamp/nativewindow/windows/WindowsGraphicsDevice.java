// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.windows;

import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.DefaultGraphicsDevice;

public class WindowsGraphicsDevice extends DefaultGraphicsDevice implements Cloneable
{
    public WindowsGraphicsDevice(final int n) {
        this("decon", n);
    }
    
    public WindowsGraphicsDevice(final String s, final int n) {
        super(NativeWindowFactory.TYPE_WINDOWS, s, n);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
}
