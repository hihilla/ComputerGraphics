// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLDrawable;

public class Gamma
{
    public static boolean setDisplayGamma(final GLDrawable glDrawable, final float n, final float n2, final float n3) throws IllegalArgumentException {
        return GLDrawableFactory.getFactory(glDrawable.getGLProfile()).setDisplayGamma(glDrawable.getNativeSurface(), n, n2, n3);
    }
    
    public static boolean setDisplayGamma(final GLAutoDrawable glAutoDrawable, final float n, final float n2, final float n3) throws IllegalArgumentException {
        final RecursiveLock upstreamLock = glAutoDrawable.getUpstreamLock();
        upstreamLock.lock();
        try {
            return GLDrawableFactory.getFactory(glAutoDrawable.getGLProfile()).setDisplayGamma(glAutoDrawable.getNativeSurface(), n, n2, n3);
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    public static void resetDisplayGamma(final GLDrawable glDrawable) {
        GLDrawableFactory.getFactory(glDrawable.getGLProfile()).resetDisplayGamma(glDrawable.getNativeSurface());
    }
    
    public static void resetDisplayGamma(final GLAutoDrawable glAutoDrawable) {
        final RecursiveLock upstreamLock = glAutoDrawable.getUpstreamLock();
        upstreamLock.lock();
        try {
            GLDrawableFactory.getFactory(glAutoDrawable.getGLProfile()).resetDisplayGamma(glAutoDrawable.getNativeSurface());
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    public static void resetAllDisplayGamma(final GLDrawable glDrawable) {
        GLDrawableFactory.getFactory(glDrawable.getGLProfile()).resetAllDisplayGamma();
    }
}
