// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GLProfile;
import jogamp.opengl.Debug;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.util.InterruptedRuntimeException;
import java.io.PrintStream;
import com.jogamp.opengl.GLException;
import jogamp.opengl.FPSCounterImpl;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import com.jogamp.opengl.GLAnimatorControl;

public abstract class AnimatorBase implements GLAnimatorControl
{
    protected static final boolean DEBUG;
    protected static final long TO_WAIT_FOR_FINISH_LIFECYCLE_ACTION = 1000L;
    protected static final long POLLP_WAIT_FOR_FINISH_LIFECYCLE_ACTION = 32L;
    public static final int MODE_EXPECT_AWT_RENDERING_THREAD = 1;
    private static int seqInstanceNumber;
    protected int modeBits;
    protected AnimatorImpl impl;
    protected String baseName;
    protected ArrayList<GLAutoDrawable> drawables;
    protected boolean drawablesEmpty;
    protected Thread animThread;
    protected boolean ignoreExceptions;
    protected boolean printExceptions;
    protected boolean exclusiveContext;
    protected Thread userExclusiveContextThread;
    protected UncaughtExceptionHandler uncaughtExceptionHandler;
    protected FPSCounterImpl fpsCounter;
    private static final Class<?> awtAnimatorImplClazz;
    private final Condition waitForNotAnimatingIfEmptyCondition;
    
    public AnimatorBase() {
        this.drawables = new ArrayList<GLAutoDrawable>();
        this.fpsCounter = new FPSCounterImpl();
        this.waitForNotAnimatingIfEmptyCondition = new Condition() {
            @Override
            public boolean eval() {
                return AnimatorBase.this.isStarted() && AnimatorBase.this.drawablesEmpty && AnimatorBase.this.isAnimating();
            }
        };
        this.modeBits = 1;
        this.drawablesEmpty = true;
    }
    
    private static final boolean useAWTAnimatorImpl(final int n) {
        return 0x0 != (0x1 & n) && null != AnimatorBase.awtAnimatorImplClazz;
    }
    
    protected final synchronized void initImpl(final boolean b) {
        if (b || null == this.impl) {
            final String format = String.format("#%02d", AnimatorBase.seqInstanceNumber++);
            if (useAWTAnimatorImpl(this.modeBits)) {
                try {
                    this.impl = (AnimatorImpl)AnimatorBase.awtAnimatorImplClazz.newInstance();
                    this.baseName = this.getBaseName("AWT") + format;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (null == this.impl) {
                this.impl = new DefaultAnimatorImpl();
                this.baseName = this.getBaseName("") + format;
            }
            if (AnimatorBase.DEBUG) {
                System.err.println("Animator.initImpl: baseName " + this.baseName + ", implClazz " + this.impl.getClass().getName() + " - " + this.toString() + " - " + getThreadName());
            }
        }
    }
    
    protected abstract String getBaseName(final String p0);
    
    public final synchronized void setModeBits(final boolean b, final int n) throws GLException {
        final int modeBits = this.modeBits;
        if (b) {
            this.modeBits |= n;
        }
        else {
            this.modeBits &= ~n;
        }
        if (useAWTAnimatorImpl(modeBits) != useAWTAnimatorImpl(this.modeBits)) {
            if (this.isStarted()) {
                throw new GLException("Animator already started");
            }
            this.initImpl(true);
        }
    }
    
    public synchronized int getModeBits() {
        return this.modeBits;
    }
    
    @Override
    public final synchronized void add(final GLAutoDrawable glAutoDrawable) {
        if (AnimatorBase.DEBUG) {
            System.err.println("Animator add: 0x" + Integer.toHexString(glAutoDrawable.hashCode()) + " - " + this.toString() + " - " + getThreadName());
        }
        if (this.drawables.contains(glAutoDrawable)) {
            throw new IllegalArgumentException("Drawable already added to animator: " + this + ", " + glAutoDrawable);
        }
        this.initImpl(false);
        this.pause();
        if (this.isStarted()) {
            glAutoDrawable.setExclusiveContextThread(this.exclusiveContext ? this.getExclusiveContextThread() : null);
        }
        this.drawables.add(glAutoDrawable);
        this.drawablesEmpty = (this.drawables.size() == 0);
        glAutoDrawable.setAnimator(this);
        if (this.isPaused()) {
            this.resume();
        }
        final boolean finishLifecycleAction = this.finishLifecycleAction(new Condition() {
            @Override
            public boolean eval() {
                final Thread exclusiveContextThread = glAutoDrawable.getExclusiveContextThread();
                return AnimatorBase.this.isStarted() && !AnimatorBase.this.isPaused() && !AnimatorBase.this.isAnimating() && ((AnimatorBase.this.exclusiveContext && null == exclusiveContextThread) || (!AnimatorBase.this.exclusiveContext && null != exclusiveContextThread));
            }
        }, 0L);
        if (AnimatorBase.DEBUG) {
            System.err.println("Animator add: Wait for Animating/ECT OK: " + finishLifecycleAction + ", " + this.toString() + ", dect " + glAutoDrawable.getExclusiveContextThread());
        }
        this.notifyAll();
    }
    
    @Override
    public final synchronized void remove(final GLAutoDrawable glAutoDrawable) {
        if (AnimatorBase.DEBUG) {
            System.err.println("Animator remove: 0x" + Integer.toHexString(glAutoDrawable.hashCode()) + " - " + this.toString() + " - " + getThreadName());
        }
        if (!this.drawables.contains(glAutoDrawable)) {
            throw new IllegalArgumentException("Drawable not added to animator: " + this + ", " + glAutoDrawable);
        }
        if (this.exclusiveContext && this.isAnimating()) {
            glAutoDrawable.setExclusiveContextThread(null);
            final boolean finishLifecycleAction = this.finishLifecycleAction(new Condition() {
                @Override
                public boolean eval() {
                    return null != glAutoDrawable.getExclusiveContextThread();
                }
            }, 32L);
            if (AnimatorBase.DEBUG) {
                System.err.println("Animator remove: Wait for Null-ECT OK: " + finishLifecycleAction + ", " + this.toString() + ", dect " + glAutoDrawable.getExclusiveContextThread());
            }
        }
        final boolean pause = this.pause();
        this.drawables.remove(glAutoDrawable);
        this.drawablesEmpty = (this.drawables.size() == 0);
        glAutoDrawable.setAnimator(null);
        if (pause) {
            this.resume();
        }
        final boolean finishLifecycleAction2 = this.finishLifecycleAction(this.waitForNotAnimatingIfEmptyCondition, 0L);
        if (AnimatorBase.DEBUG) {
            System.err.println("Animator remove: Wait for !Animating-if-empty OK: " + finishLifecycleAction2 + ", " + this.toString());
        }
        this.notifyAll();
    }
    
    public final synchronized Thread setExclusiveContext(final Thread userExclusiveContextThread) {
        final boolean exclusiveContext = null != userExclusiveContextThread;
        final Thread userExclusiveContextThread2 = this.userExclusiveContextThread;
        if (exclusiveContext && userExclusiveContextThread != this.animThread) {
            this.userExclusiveContextThread = userExclusiveContextThread;
        }
        this.setExclusiveContext(exclusiveContext);
        return userExclusiveContextThread2;
    }
    
    public final boolean setExclusiveContext(final boolean b) {
        final boolean b2;
        final Thread userExclusiveContextThread;
        final boolean exclusiveContext;
        synchronized (this) {
            b2 = (this.isStarted() && !this.drawablesEmpty);
            userExclusiveContextThread = this.userExclusiveContextThread;
            exclusiveContext = this.exclusiveContext;
            this.exclusiveContext = b;
            if (AnimatorBase.DEBUG) {
                System.err.println("AnimatorBase.setExclusiveContextThread: " + exclusiveContext + " -> " + this.exclusiveContext + ", propagateState " + b2 + ", " + this);
            }
        }
        final Thread thread = b ? ((null != userExclusiveContextThread) ? userExclusiveContextThread : this.animThread) : null;
        Throwable t = null;
        if (b2) {
            this.setDrawablesExclCtxState(b);
            if (!b) {
                Label_0261: {
                    if (Thread.currentThread() != this.getThread()) {
                        if (Thread.currentThread() != userExclusiveContextThread) {
                            final boolean b3 = !this.isAnimating() && this.resume();
                            for (int n = 10; 0 < n && this.isAnimating() && !this.validateDrawablesExclCtxState(thread); --n) {
                                try {
                                    Thread.sleep(20L);
                                }
                                catch (InterruptedException ex2) {}
                            }
                            if (b3) {
                                this.pause();
                            }
                            break Label_0261;
                        }
                    }
                    try {
                        this.display();
                    }
                    catch (UncaughtAnimatorException ex) {
                        t = ex;
                    }
                }
                synchronized (this) {
                    this.userExclusiveContextThread = null;
                }
            }
        }
        if (AnimatorBase.DEBUG) {
            System.err.println("AnimatorBase.setExclusiveContextThread: all-GLAD Ok: " + this.validateDrawablesExclCtxState(thread) + ", " + this);
            if (null != t) {
                System.err.println("AnimatorBase.setExclusiveContextThread: caught: " + t.getMessage());
                t.printStackTrace();
            }
        }
        if (null != t) {
            throw t;
        }
        return exclusiveContext;
    }
    
    public final synchronized boolean isExclusiveContextEnabled() {
        return this.exclusiveContext;
    }
    
    public final synchronized Thread getExclusiveContextThread() {
        return (this.isStarted() && this.exclusiveContext) ? ((null != this.userExclusiveContextThread) ? this.userExclusiveContextThread : this.animThread) : null;
    }
    
    protected final synchronized void setDrawablesExclCtxState(final boolean b) {
        if (AnimatorBase.DEBUG) {
            System.err.println("AnimatorBase.setExclusiveContextImpl exlusive " + this.exclusiveContext + ": Enable " + b + " for " + this + " - " + Thread.currentThread());
        }
        final Thread exclusiveContextThread = this.getExclusiveContextThread();
        for (int i = 0; i < this.drawables.size(); ++i) {
            try {
                this.drawables.get(i).setExclusiveContextThread(b ? exclusiveContextThread : null);
            }
            catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    protected final boolean validateDrawablesExclCtxState(final Thread thread) {
        for (int i = 0; i < this.drawables.size(); ++i) {
            if (thread != this.drawables.get(i).getExclusiveContextThread()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public final synchronized Thread getThread() {
        return this.animThread;
    }
    
    protected final void display() throws UncaughtAnimatorException {
        this.impl.display(this.drawables, this.ignoreExceptions, this.printExceptions);
        this.fpsCounter.tickFPS();
    }
    
    @Override
    public final void setUpdateFPSFrames(final int n, final PrintStream printStream) {
        this.fpsCounter.setUpdateFPSFrames(n, printStream);
    }
    
    @Override
    public final void resetFPSCounter() {
        this.fpsCounter.resetFPSCounter();
    }
    
    @Override
    public final int getUpdateFPSFrames() {
        return this.fpsCounter.getUpdateFPSFrames();
    }
    
    @Override
    public final long getFPSStartTime() {
        return this.fpsCounter.getFPSStartTime();
    }
    
    @Override
    public final long getLastFPSUpdateTime() {
        return this.fpsCounter.getLastFPSUpdateTime();
    }
    
    @Override
    public final long getLastFPSPeriod() {
        return this.fpsCounter.getLastFPSPeriod();
    }
    
    @Override
    public final float getLastFPS() {
        return this.fpsCounter.getLastFPS();
    }
    
    @Override
    public final int getTotalFPSFrames() {
        return this.fpsCounter.getTotalFPSFrames();
    }
    
    @Override
    public final long getTotalFPSDuration() {
        return this.fpsCounter.getTotalFPSDuration();
    }
    
    @Override
    public final float getTotalFPS() {
        return this.fpsCounter.getTotalFPS();
    }
    
    public final void setIgnoreExceptions(final boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }
    
    public final void setPrintExceptions(final boolean printExceptions) {
        this.printExceptions = printExceptions;
    }
    
    @Override
    public final UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.uncaughtExceptionHandler;
    }
    
    @Override
    public final void setUncaughtExceptionHandler(final UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }
    
    protected final synchronized boolean handleUncaughtException(final UncaughtAnimatorException ex) {
        if (null != this.uncaughtExceptionHandler) {
            try {
                this.uncaughtExceptionHandler.uncaughtException(this, ex.getGLAutoDrawable(), ex.getCause());
            }
            catch (Throwable t) {}
            return true;
        }
        return false;
    }
    
    protected final void flushGLRunnables() {
        for (int i = 0; i < this.drawables.size(); ++i) {
            this.drawables.get(i).flushGLRunnables();
        }
    }
    
    protected final synchronized boolean finishLifecycleAction(final Condition condition, long n) {
        this.initImpl(false);
        boolean b;
        long n2;
        boolean b2;
        if (this.impl.blockUntilDone(this.animThread)) {
            b = true;
            n2 = 1000L;
            if (0L >= n) {
                n = n2;
            }
            long currentTimeMillis;
            for (b2 = condition.eval(); b2 && n2 > 0L; n2 -= System.currentTimeMillis() - currentTimeMillis, b2 = condition.eval()) {
                currentTimeMillis = System.currentTimeMillis();
                if (n > n2) {
                    n = n2;
                }
                this.notifyAll();
                try {
                    this.wait(n);
                }
                catch (InterruptedException ex) {
                    throw new InterruptedRuntimeException(ex);
                }
            }
        }
        else {
            b = false;
            n2 = 0L;
            b2 = condition.eval();
            if (b2) {
                this.notifyAll();
                b2 = condition.eval();
            }
        }
        final boolean b3 = !b2 || !b;
        if (AnimatorBase.DEBUG || (b && b2)) {
            if (b && n2 <= 0L && b2) {
                System.err.println("finishLifecycleAction(" + condition.getClass().getName() + "): ++++++ timeout reached ++++++ " + getThreadName());
            }
            System.err.println("finishLifecycleAction(" + condition.getClass().getName() + "): OK " + !b2 + "- pollPeriod " + n + ", blocking " + b + " -> res " + b3 + ", waited " + (b ? (1000L - n2) : 0L) + "/" + 1000L + " - " + getThreadName());
            System.err.println(" - " + this.toString());
            if (b2) {
                ExceptionUtils.dumpStack(System.err);
            }
        }
        return b3;
    }
    
    @Override
    public synchronized boolean isStarted() {
        return this.animThread != null;
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[started " + this.isStarted() + ", animating " + this.isAnimating() + ", paused " + this.isPaused() + ", drawable " + this.drawables.size() + ", totals[dt " + this.getTotalFPSDuration() + ", frames " + this.getTotalFPSFrames() + ", fps " + this.getTotalFPS() + "], modeBits " + this.modeBits + ", init'ed " + (null != this.impl) + ", animThread " + this.getThread() + ", exclCtxThread " + this.exclusiveContext + "(" + this.getExclusiveContextThread() + ")]";
    }
    
    static {
        DEBUG = Debug.debug("Animator");
        AnimatorBase.seqInstanceNumber = 0;
        GLProfile.initSingleton();
        if (GLProfile.isAWTAvailable()) {
            Class<?> forName;
            try {
                forName = Class.forName("com.jogamp.opengl.util.AWTAnimatorImpl");
            }
            catch (Exception ex) {
                forName = null;
            }
            awtAnimatorImplClazz = forName;
        }
        else {
            awtAnimatorImplClazz = null;
        }
    }
    
    public static class UncaughtAnimatorException extends RuntimeException
    {
        final GLAutoDrawable drawable;
        
        public UncaughtAnimatorException(final GLAutoDrawable drawable, final Throwable t) {
            super(t);
            this.drawable = drawable;
        }
        
        public final GLAutoDrawable getGLAutoDrawable() {
            return this.drawable;
        }
    }
    
    protected interface Condition
    {
        boolean eval();
    }
    
    public interface AnimatorImpl
    {
        void display(final ArrayList<GLAutoDrawable> p0, final boolean p1, final boolean p2) throws UncaughtAnimatorException;
        
        boolean blockUntilDone(final Thread p0);
    }
}
