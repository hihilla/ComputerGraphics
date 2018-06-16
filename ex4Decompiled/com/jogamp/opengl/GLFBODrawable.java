// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.nativewindow.NativeWindowException;

public interface GLFBODrawable extends GLDrawable
{
    public static final int FBOMODE_USE_TEXTURE = 1;
    
    boolean isInitialized();
    
    void setFBOMode(final int p0) throws IllegalStateException;
    
    int getFBOMode();
    
    void resetSize(final GL p0) throws GLException;
    
    int getTextureUnit();
    
    void setTextureUnit(final int p0);
    
    void setNumSamples(final GL p0, final int p1) throws GLException;
    
    int getNumSamples();
    
    int setNumBuffers(final int p0) throws IllegalStateException, GLException;
    
    int getNumBuffers();
    
    FBObject getFBObject(final int p0) throws IllegalArgumentException;
    
    FBObject.Colorbuffer getColorbuffer(final int p0) throws IllegalArgumentException;
    
    public interface Resizeable extends GLFBODrawable
    {
        void setSurfaceSize(final GLContext p0, final int p1, final int p2) throws NativeWindowException, GLException;
    }
}
