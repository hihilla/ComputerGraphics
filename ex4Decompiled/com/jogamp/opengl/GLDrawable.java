// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.NativeSurfaceHolder;

public interface GLDrawable extends NativeSurfaceHolder
{
    GLContext createContext(final GLContext p0);
    
    void setRealized(final boolean p0);
    
    boolean isRealized();
    
    int getSurfaceWidth();
    
    int getSurfaceHeight();
    
    boolean isGLOriented();
    
    void swapBuffers() throws GLException;
    
    GLCapabilitiesImmutable getChosenGLCapabilities();
    
    GLCapabilitiesImmutable getRequestedGLCapabilities();
    
    GLProfile getGLProfile();
    
    NativeSurface getNativeSurface();
    
    long getHandle();
    
    GLDrawableFactory getFactory();
    
    String toString();
}
