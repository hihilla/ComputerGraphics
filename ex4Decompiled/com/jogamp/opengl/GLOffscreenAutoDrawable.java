// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.nativewindow.NativeWindowException;

public interface GLOffscreenAutoDrawable extends GLAutoDrawable, GLSharedContextSetter
{
    void setSurfaceSize(final int p0, final int p1) throws NativeWindowException, GLException;
    
    void setUpstreamWidget(final Object p0);
    
    public interface FBO extends GLOffscreenAutoDrawable, GLFBODrawable
    {
    }
}
