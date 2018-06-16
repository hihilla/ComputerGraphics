// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import com.jogamp.common.JogampRuntimeException;

public class InterruptedRuntimeException extends JogampRuntimeException
{
    public InterruptedRuntimeException(final String s, final InterruptedException ex) {
        super(s, SourcedInterruptedException.wrap(ex));
    }
    
    public InterruptedRuntimeException(final InterruptedException ex) {
        super(SourcedInterruptedException.wrap(ex));
    }
    
    @Override
    public InterruptedException getCause() {
        return (InterruptedException)super.getCause();
    }
}
