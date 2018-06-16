// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import jogamp.opengl.Debug;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLEventListenerState;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.opengl.GLRunnable;

public class GLDrawableUtil
{
    protected static final boolean DEBUG;
    private static final GLRunnable setViewport;
    
    public static final boolean isAnimatorStartedOnOtherThread(final GLAnimatorControl glAnimatorControl) {
        return null != glAnimatorControl && (glAnimatorControl.isStarted() && glAnimatorControl.getThread() != Thread.currentThread());
    }
    
    public static final boolean isAnimatorStarted(final GLAnimatorControl glAnimatorControl) {
        return null != glAnimatorControl && glAnimatorControl.isStarted();
    }
    
    public static final boolean isAnimatorAnimatingOnOtherThread(final GLAnimatorControl glAnimatorControl) {
        return null != glAnimatorControl && (glAnimatorControl.isAnimating() && glAnimatorControl.getThread() != Thread.currentThread());
    }
    
    public static final boolean isAnimatorAnimating(final GLAnimatorControl glAnimatorControl) {
        return null != glAnimatorControl && glAnimatorControl.isAnimating();
    }
    
    public static final void moveGLEventListener(final GLAutoDrawable glAutoDrawable, final GLAutoDrawable glAutoDrawable2, final GLEventListener glEventListener, final boolean b) {
        final boolean glEventListenerInitState = glAutoDrawable.getGLEventListenerInitState(glEventListener);
        if (b) {
            glAutoDrawable.removeGLEventListener(glEventListener);
            glAutoDrawable2.addGLEventListener(glEventListener);
            if (glEventListenerInitState) {
                glAutoDrawable2.setGLEventListenerInitState(glEventListener, true);
                glAutoDrawable2.invoke(false, new ReshapeGLEventListener(glEventListener, true));
            }
        }
        else {
            glAutoDrawable.disposeGLEventListener(glEventListener, true);
            glAutoDrawable2.addGLEventListener(glEventListener);
        }
    }
    
    public static final void moveAllGLEventListener(final GLAutoDrawable glAutoDrawable, final GLAutoDrawable glAutoDrawable2, final boolean b) {
        for (int glEventListenerCount = glAutoDrawable.getGLEventListenerCount(); 0 < glEventListenerCount; --glEventListenerCount) {
            moveGLEventListener(glAutoDrawable, glAutoDrawable2, glAutoDrawable.getGLEventListener(0), b);
        }
    }
    
    public static boolean isSwapGLContextSafe(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesImmutable glCapabilitiesImmutable3) {
        final boolean b = glCapabilitiesImmutable.getAccumAlphaBits() > 0 || glCapabilitiesImmutable.getAccumRedBits() > 0 || glCapabilitiesImmutable.getAccumGreenBits() > 0 || glCapabilitiesImmutable.getAccumBlueBits() > 0;
        return ((!glCapabilitiesImmutable2.isOnscreen() || glCapabilitiesImmutable3.isOnscreen()) && (glCapabilitiesImmutable2.isOnscreen() || !glCapabilitiesImmutable3.isOnscreen())) || (!glCapabilitiesImmutable2.getSampleBuffers() && !glCapabilitiesImmutable3.getSampleBuffers() && !glCapabilitiesImmutable2.getStereo() && !glCapabilitiesImmutable3.getStereo() && !b);
    }
    
    public static final void swapGLContextAndAllGLEventListener(final GLAutoDrawable glAutoDrawable, final GLAutoDrawable glAutoDrawable2) {
        final GLEventListenerState move = GLEventListenerState.moveFrom(glAutoDrawable, true);
        final GLEventListenerState move2 = GLEventListenerState.moveFrom(glAutoDrawable2, true);
        final Runnable unlockSurfaceOp = move.getUnlockSurfaceOp();
        final Runnable unlockSurfaceOp2 = move2.getUnlockSurfaceOp();
        try {
            move.moveTo(glAutoDrawable2, unlockSurfaceOp2);
            move2.moveTo(glAutoDrawable, unlockSurfaceOp);
        }
        finally {
            unlockSurfaceOp2.run();
            unlockSurfaceOp.run();
        }
    }
    
    public static final void swapGLContext(final GLAutoDrawable glAutoDrawable, final GLAutoDrawable glAutoDrawable2) {
        final GLAnimatorControl animator = glAutoDrawable.getAnimator();
        final GLAnimatorControl animator2 = glAutoDrawable2.getAnimator();
        final boolean b = isAnimatorAnimatingOnOtherThread(animator) && animator.pause();
        final boolean b2 = isAnimatorAnimatingOnOtherThread(animator2) && animator2.pause();
        final RecursiveLock upstreamLock = glAutoDrawable.getUpstreamLock();
        final RecursiveLock upstreamLock2 = glAutoDrawable2.getUpstreamLock();
        upstreamLock.lock();
        upstreamLock2.lock();
        try {
            final NativeSurface nativeSurface = glAutoDrawable.getNativeSurface();
            final boolean b3 = 1 < nativeSurface.lockSurface();
            if (glAutoDrawable.isRealized() && !b3) {
                throw new GLException("Could not lock realized a surface " + glAutoDrawable);
            }
            final NativeSurface nativeSurface2 = glAutoDrawable2.getNativeSurface();
            final boolean b4 = 1 < nativeSurface2.lockSurface();
            if (glAutoDrawable2.isRealized() && !b4) {
                throw new GLException("Could not lock realized b surface " + glAutoDrawable2);
            }
            try {
                for (int n = glAutoDrawable.getGLEventListenerCount() - 1; 0 <= n; --n) {
                    glAutoDrawable.disposeGLEventListener(glAutoDrawable.getGLEventListener(n), false);
                }
                for (int n2 = glAutoDrawable2.getGLEventListenerCount() - 1; 0 <= n2; --n2) {
                    glAutoDrawable2.disposeGLEventListener(glAutoDrawable2.getGLEventListener(n2), false);
                }
                glAutoDrawable2.setContext(glAutoDrawable.setContext(glAutoDrawable2.getContext(), false), false);
            }
            finally {
                if (b4) {
                    nativeSurface2.unlockSurface();
                }
                if (b3) {
                    nativeSurface.unlockSurface();
                }
            }
        }
        finally {
            upstreamLock2.unlock();
            upstreamLock.unlock();
        }
        glAutoDrawable.invoke(true, GLDrawableUtil.setViewport);
        glAutoDrawable2.invoke(true, GLDrawableUtil.setViewport);
        if (b) {
            animator.resume();
        }
        if (b2) {
            animator2.resume();
        }
    }
    
    public static final boolean swapBuffersBeforeRead(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        return glCapabilitiesImmutable.isFBO() && glCapabilitiesImmutable.getSampleBuffers();
    }
    
    static {
        DEBUG = Debug.debug("GLDrawable");
        setViewport = new GLRunnable() {
            @Override
            public boolean run(final GLAutoDrawable glAutoDrawable) {
                glAutoDrawable.getGL().glViewport(0, 0, glAutoDrawable.getSurfaceWidth(), glAutoDrawable.getSurfaceHeight());
                return false;
            }
        };
    }
    
    public static class ReshapeGLEventListener implements GLRunnable
    {
        private final GLEventListener listener;
        private final boolean displayAfterReshape;
        
        public ReshapeGLEventListener(final GLEventListener listener, final boolean displayAfterReshape) {
            this.listener = listener;
            this.displayAfterReshape = displayAfterReshape;
        }
        
        @Override
        public boolean run(final GLAutoDrawable glAutoDrawable) {
            this.listener.reshape(glAutoDrawable, 0, 0, glAutoDrawable.getSurfaceWidth(), glAutoDrawable.getSurfaceHeight());
            if (this.displayAfterReshape) {
                this.listener.display(glAutoDrawable);
            }
            return true;
        }
    }
}
