// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public interface WindowClosingProtocol
{
    WindowClosingMode getDefaultCloseOperation();
    
    WindowClosingMode setDefaultCloseOperation(final WindowClosingMode p0);
    
    public enum WindowClosingMode
    {
        DO_NOTHING_ON_CLOSE, 
        DISPOSE_ON_CLOSE;
    }
}
