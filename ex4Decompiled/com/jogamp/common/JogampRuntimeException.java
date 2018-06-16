// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common;

public class JogampRuntimeException extends RuntimeException
{
    public JogampRuntimeException() {
    }
    
    public JogampRuntimeException(final String s) {
        super(s);
    }
    
    public JogampRuntimeException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public JogampRuntimeException(final Throwable t) {
        super(t);
    }
}
