// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

public interface GLSharedContextSetter extends GLAutoDrawable
{
    void setSharedContext(final GLContext p0) throws IllegalStateException;
    
    void setSharedAutoDrawable(final GLAutoDrawable p0) throws IllegalStateException;
}
