// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

public class GLException extends RuntimeException
{
    public GLException() {
    }
    
    public GLException(final String s) {
        super(s);
    }
    
    public GLException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public GLException(final Throwable t) {
        super(t);
    }
    
    public static GLException newGLException(final Throwable t) {
        return new GLException("Caught " + t.getClass().getSimpleName() + ": " + t.getMessage() + " on thread " + Thread.currentThread().getName(), t);
    }
}
