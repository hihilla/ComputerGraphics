// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.util.PropertyAccess;
import com.jogamp.common.util.locks.RecursiveLock;
import java.util.List;

public interface GLAutoDrawable extends GLDrawable
{
    public static final boolean SCREEN_CHANGE_ACTION_ENABLED = PropertyAccess.getBooleanProperty("jogl.screenchange.action", true);
    
    GLDrawable getDelegatedDrawable();
    
    GLContext getContext();
    
    GLContext setContext(final GLContext p0, final boolean p1);
    
    void addGLEventListener(final GLEventListener p0);
    
    void addGLEventListener(final int p0, final GLEventListener p1) throws IndexOutOfBoundsException;
    
    int getGLEventListenerCount();
    
    boolean areAllGLEventListenerInitialized();
    
    GLEventListener getGLEventListener(final int p0) throws IndexOutOfBoundsException;
    
    boolean getGLEventListenerInitState(final GLEventListener p0);
    
    void setGLEventListenerInitState(final GLEventListener p0, final boolean p1);
    
    GLEventListener disposeGLEventListener(final GLEventListener p0, final boolean p1);
    
    GLEventListener removeGLEventListener(final GLEventListener p0);
    
    void setAnimator(final GLAnimatorControl p0) throws GLException;
    
    GLAnimatorControl getAnimator();
    
    Thread setExclusiveContextThread(final Thread p0) throws GLException;
    
    Thread getExclusiveContextThread();
    
    boolean invoke(final boolean p0, final GLRunnable p1) throws IllegalStateException;
    
    boolean invoke(final boolean p0, final List<GLRunnable> p1) throws IllegalStateException;
    
    void flushGLRunnables();
    
    void destroy();
    
    void display();
    
    void setAutoSwapBufferMode(final boolean p0);
    
    boolean getAutoSwapBufferMode();
    
    void setContextCreationFlags(final int p0);
    
    int getContextCreationFlags();
    
    GLContext createContext(final GLContext p0);
    
    GL getGL();
    
    GL setGL(final GL p0);
    
    Object getUpstreamWidget();
    
    RecursiveLock getUpstreamLock();
    
    boolean isThreadGLCapable();
}
