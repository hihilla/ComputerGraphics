// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class NativeWindowException extends RuntimeException
{
    public NativeWindowException() {
    }
    
    public NativeWindowException(final String s) {
        super(s);
    }
    
    public NativeWindowException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public NativeWindowException(final Throwable t) {
        super(t);
    }
}
