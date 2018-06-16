// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

public interface GLStateKeeper
{
    Listener setGLStateKeeperListener(final Listener p0);
    
    boolean isGLStatePreservationSupported();
    
    boolean preserveGLStateAtDestroy(final boolean p0);
    
    GLEventListenerState getPreservedGLState();
    
    GLEventListenerState clearPreservedGLState();
    
    public interface Listener
    {
        void glStatePreserveNotify(final GLStateKeeper p0);
        
        void glStateRestored(final GLStateKeeper p0);
    }
}
