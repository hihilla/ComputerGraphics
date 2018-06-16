// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import jogamp.opengl.ThreadingImpl;

public class Threading
{
    public static Mode getMode() {
        return ThreadingImpl.getMode();
    }
    
    public static final void disableSingleThreading() {
        ThreadingImpl.disableSingleThreading();
    }
    
    public static final boolean isSingleThreaded() {
        return ThreadingImpl.isSingleThreaded();
    }
    
    public static final boolean isToolkitThread() throws GLException {
        return ThreadingImpl.isToolkitThread();
    }
    
    public static final boolean isOpenGLThread() throws GLException {
        return ThreadingImpl.isOpenGLThread();
    }
    
    public static final void invokeOnOpenGLThread(final boolean b, final Runnable runnable) throws GLException {
        ThreadingImpl.invokeOnOpenGLThread(b, runnable);
    }
    
    public static final void invoke(final boolean b, final Runnable runnable, final Object o) throws GLException {
        if (!isOpenGLThread() && (null == o || !Thread.holdsLock(o))) {
            invokeOnOpenGLThread(b, runnable);
        }
        else {
            runnable.run();
        }
    }
    
    public enum Mode
    {
        MT(0), 
        ST_AWT(1), 
        ST_WORKER(2);
        
        public final int id;
        
        private Mode(final int id) {
            this.id = id;
        }
    }
}
