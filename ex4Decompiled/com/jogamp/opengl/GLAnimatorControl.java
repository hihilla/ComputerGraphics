// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

public interface GLAnimatorControl extends FPSCounter
{
    boolean isStarted();
    
    boolean isAnimating();
    
    boolean isPaused();
    
    Thread getThread();
    
    boolean start();
    
    boolean stop();
    
    boolean pause();
    
    boolean resume();
    
    void add(final GLAutoDrawable p0);
    
    void remove(final GLAutoDrawable p0);
    
    UncaughtExceptionHandler getUncaughtExceptionHandler();
    
    void setUncaughtExceptionHandler(final UncaughtExceptionHandler p0);
    
    public interface UncaughtExceptionHandler
    {
        void uncaughtException(final GLAnimatorControl p0, final GLAutoDrawable p1, final Throwable p2);
    }
}
