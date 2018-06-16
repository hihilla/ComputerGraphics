// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import java.util.TimerTask;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.Timer;

public class FPSAnimator extends AnimatorBase
{
    private Timer timer;
    private MainTask task;
    private int fps;
    private final boolean scheduleAtFixedRate;
    private boolean isAnimating;
    private volatile boolean pauseIssued;
    private volatile boolean stopIssued;
    static int timerNo;
    private final Condition waitForStartedAddedCondition;
    private final Condition waitForStartedEmptyCondition;
    private final Condition waitForStoppedCondition;
    private final Condition waitForPausedCondition;
    private final Condition waitForResumeCondition;
    
    @Override
    protected String getBaseName(final String s) {
        return "FPS" + s + "Animator";
    }
    
    public FPSAnimator(final int n) {
        this(null, n);
    }
    
    public FPSAnimator(final int n, final boolean b) {
        this(null, n, b);
    }
    
    public FPSAnimator(final GLAutoDrawable glAutoDrawable, final int n) {
        this(glAutoDrawable, n, false);
    }
    
    public FPSAnimator(final GLAutoDrawable glAutoDrawable, final int fps, final boolean scheduleAtFixedRate) {
        this.timer = null;
        this.task = null;
        this.waitForStartedAddedCondition = new Condition() {
            @Override
            public boolean eval() {
                return !FPSAnimator.this.isStarted() || !FPSAnimator.this.isAnimating;
            }
        };
        this.waitForStartedEmptyCondition = new Condition() {
            @Override
            public boolean eval() {
                return !FPSAnimator.this.isStarted() || FPSAnimator.this.isAnimating;
            }
        };
        this.waitForStoppedCondition = new Condition() {
            @Override
            public boolean eval() {
                return FPSAnimator.this.isStarted();
            }
        };
        this.waitForPausedCondition = new Condition() {
            @Override
            public boolean eval() {
                return FPSAnimator.this.isStarted() && FPSAnimator.this.isAnimating;
            }
        };
        this.waitForResumeCondition = new Condition() {
            @Override
            public boolean eval() {
                return !FPSAnimator.this.drawablesEmpty && !FPSAnimator.this.isAnimating && FPSAnimator.this.isStarted();
            }
        };
        this.fps = fps;
        if (glAutoDrawable != null) {
            this.add(glAutoDrawable);
        }
        this.scheduleAtFixedRate = scheduleAtFixedRate;
    }
    
    public final void setFPS(final int fps) throws GLException {
        if (this.isStarted()) {
            throw new GLException("Animator already started.");
        }
        this.fps = fps;
    }
    
    public final int getFPS() {
        return this.fps;
    }
    
    private final boolean isAnimatingImpl() {
        return this.animThread != null && this.isAnimating;
    }
    
    @Override
    public final synchronized boolean isAnimating() {
        return this.animThread != null && this.isAnimating;
    }
    
    @Override
    public final synchronized boolean isPaused() {
        return this.animThread != null && this.pauseIssued;
    }
    
    @Override
    public final synchronized boolean start() {
        if (null != this.timer || null != this.task || this.isStarted()) {
            return false;
        }
        this.timer = new Timer(AnimatorBase.getThreadName() + "-" + this.baseName + "-Timer" + FPSAnimator.timerNo++);
        this.task = new MainTask();
        if (FPSAnimator.DEBUG) {
            System.err.println("FPSAnimator.start() START: " + this.task + ", " + Thread.currentThread() + ": " + this.toString());
        }
        this.task.start(this.timer);
        final boolean finishLifecycleAction = this.finishLifecycleAction(this.drawablesEmpty ? this.waitForStartedEmptyCondition : this.waitForStartedAddedCondition, 32L);
        if (FPSAnimator.DEBUG) {
            System.err.println("FPSAnimator.start() END: " + this.task + ", " + Thread.currentThread() + ": " + this.toString());
        }
        if (this.drawablesEmpty) {
            this.task.cancel();
            this.task = null;
        }
        return finishLifecycleAction;
    }
    
    @Override
    public final synchronized boolean stop() {
        if (null == this.timer || !this.isStarted()) {
            return false;
        }
        if (FPSAnimator.DEBUG) {
            System.err.println("FPSAnimator.stop() START: " + this.task + ", " + Thread.currentThread() + ": " + this.toString());
        }
        boolean finishLifecycleAction;
        if (null == this.task) {
            finishLifecycleAction = true;
        }
        else {
            this.stopIssued = true;
            finishLifecycleAction = this.finishLifecycleAction(this.waitForStoppedCondition, 32L);
        }
        if (FPSAnimator.DEBUG) {
            System.err.println("FPSAnimator.stop() END: " + this.task + ", " + Thread.currentThread() + ": " + this.toString());
        }
        if (null != this.task) {
            this.task.cancel();
            this.task = null;
        }
        if (null != this.timer) {
            this.timer.cancel();
            this.timer = null;
        }
        this.animThread = null;
        return finishLifecycleAction;
    }
    
    @Override
    public final synchronized boolean pause() {
        if (!this.isStarted() || this.pauseIssued) {
            return false;
        }
        if (FPSAnimator.DEBUG) {
            System.err.println("FPSAnimator.pause() START: " + this.task + ", " + Thread.currentThread() + ": " + this.toString());
        }
        boolean finishLifecycleAction;
        if (null == this.task) {
            finishLifecycleAction = true;
        }
        else {
            this.pauseIssued = true;
            finishLifecycleAction = this.finishLifecycleAction(this.waitForPausedCondition, 32L);
        }
        if (FPSAnimator.DEBUG) {
            System.err.println("FPSAnimator.pause() END: " + this.task + ", " + Thread.currentThread() + ": " + this.toString());
        }
        if (null != this.task) {
            this.task.cancel();
            this.task = null;
        }
        return finishLifecycleAction;
    }
    
    @Override
    public final synchronized boolean resume() {
        if (!this.isStarted() || !this.pauseIssued) {
            return false;
        }
        if (FPSAnimator.DEBUG) {
            System.err.println("FPSAnimator.resume() START: " + Thread.currentThread() + ": " + this.toString());
        }
        boolean finishLifecycleAction;
        if (this.drawablesEmpty) {
            finishLifecycleAction = true;
        }
        else {
            if (null != this.task) {
                if (FPSAnimator.DEBUG) {
                    System.err.println("FPSAnimator.resume() Ops: !pauseIssued, but task != null: " + this.toString());
                    ExceptionUtils.dumpStack(System.err);
                }
                this.task.cancel();
                this.task = null;
            }
            (this.task = new MainTask()).start(this.timer);
            finishLifecycleAction = this.finishLifecycleAction(this.waitForResumeCondition, 32L);
        }
        if (FPSAnimator.DEBUG) {
            System.err.println("FPSAnimator.resume() END: " + this.task + ", " + Thread.currentThread() + ": " + this.toString());
        }
        return finishLifecycleAction;
    }
    
    static {
        FPSAnimator.timerNo = 0;
    }
    
    class MainTask extends TimerTask
    {
        private boolean justStarted;
        private boolean alreadyStopped;
        private boolean alreadyPaused;
        
        public void start(final Timer timer) {
            FPSAnimator.this.fpsCounter.resetFPSCounter();
            FPSAnimator.this.pauseIssued = false;
            FPSAnimator.this.stopIssued = false;
            FPSAnimator.this.isAnimating = false;
            this.justStarted = true;
            this.alreadyStopped = false;
            this.alreadyPaused = false;
            final long n = (0 < FPSAnimator.this.fps) ? ((long)(1000.0f / FPSAnimator.this.fps)) : 1L;
            if (FPSAnimator.this.scheduleAtFixedRate) {
                timer.scheduleAtFixedRate(this, 0L, n);
            }
            else {
                timer.schedule(this, 0L, n);
            }
        }
        
        public boolean isActive() {
            return !this.alreadyStopped && !this.alreadyPaused;
        }
        
        @Override
        public final String toString() {
            return "Task[thread " + FPSAnimator.this.animThread + ", stopped " + this.alreadyStopped + ", paused " + this.alreadyPaused + " pauseIssued " + FPSAnimator.this.pauseIssued + ", stopIssued " + FPSAnimator.this.stopIssued + " -- started " + FPSAnimator.this.isStarted() + ", animating " + FPSAnimator.this.isAnimatingImpl() + ", paused " + FPSAnimator.this.isPaused() + ", drawable " + FPSAnimator.this.drawables.size() + ", drawablesEmpty " + FPSAnimator.this.drawablesEmpty + "]";
        }
        
        @Override
        public void run() {
            UncaughtAnimatorException ex = null;
            if (this.justStarted) {
                this.justStarted = false;
                synchronized (FPSAnimator.this) {
                    FPSAnimator.this.animThread = Thread.currentThread();
                    if (AnimatorBase.DEBUG) {
                        System.err.println("FPSAnimator start/resume:" + Thread.currentThread() + ": " + this.toString());
                    }
                    FPSAnimator.this.isAnimating = true;
                    if (FPSAnimator.this.drawablesEmpty) {
                        FPSAnimator.this.pauseIssued = true;
                    }
                    else {
                        FPSAnimator.this.pauseIssued = false;
                        FPSAnimator.this.setDrawablesExclCtxState(FPSAnimator.this.exclusiveContext);
                    }
                    FPSAnimator.this.notifyAll();
                    if (AnimatorBase.DEBUG) {
                        System.err.println("FPSAnimator P1:" + Thread.currentThread() + ": " + this.toString());
                    }
                }
            }
            if (!FPSAnimator.this.pauseIssued && !FPSAnimator.this.stopIssued) {
                try {
                    FPSAnimator.this.display();
                }
                catch (UncaughtAnimatorException ex2) {
                    ex = ex2;
                    FPSAnimator.this.stopIssued = true;
                }
            }
            else if (FPSAnimator.this.pauseIssued && !FPSAnimator.this.stopIssued) {
                if (AnimatorBase.DEBUG) {
                    System.err.println("FPSAnimator pausing: " + this.alreadyPaused + ", " + Thread.currentThread() + ": " + this.toString());
                }
                this.cancel();
                if (!this.alreadyPaused) {
                    this.alreadyPaused = true;
                    if (FPSAnimator.this.exclusiveContext && !FPSAnimator.this.drawablesEmpty) {
                        FPSAnimator.this.setDrawablesExclCtxState(false);
                        try {
                            FPSAnimator.this.display();
                        }
                        catch (UncaughtAnimatorException ex3) {
                            ex = ex3;
                            FPSAnimator.this.stopIssued = true;
                        }
                    }
                    if (null == ex) {
                        synchronized (FPSAnimator.this) {
                            if (AnimatorBase.DEBUG) {
                                System.err.println("FPSAnimator pause " + Thread.currentThread() + ": " + this.toString());
                            }
                            FPSAnimator.this.isAnimating = false;
                            FPSAnimator.this.notifyAll();
                        }
                    }
                }
            }
            if (FPSAnimator.this.stopIssued) {
                if (AnimatorBase.DEBUG) {
                    System.err.println("FPSAnimator stopping: " + this.alreadyStopped + ", " + Thread.currentThread() + ": " + this.toString());
                }
                this.cancel();
                if (!this.alreadyStopped) {
                    this.alreadyStopped = true;
                    if (FPSAnimator.this.exclusiveContext && !FPSAnimator.this.drawablesEmpty) {
                        FPSAnimator.this.setDrawablesExclCtxState(false);
                        try {
                            FPSAnimator.this.display();
                        }
                        catch (UncaughtAnimatorException ex4) {
                            if (null == ex) {
                                ex = ex4;
                            }
                            else {
                                System.err.println("FPSAnimator.setExclusiveContextThread: caught: " + ex4.getMessage());
                                ex4.printStackTrace();
                            }
                        }
                    }
                    boolean b = false;
                    int n = 0;
                    synchronized (FPSAnimator.this) {
                        if (AnimatorBase.DEBUG) {
                            System.err.println("FPSAnimator stop " + Thread.currentThread() + ": " + this.toString());
                            if (null != ex) {
                                System.err.println("Animator caught: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                        FPSAnimator.this.isAnimating = false;
                        if (null != ex) {
                            b = true;
                            n = (FPSAnimator.this.handleUncaughtException(ex) ? 0 : 1);
                        }
                        FPSAnimator.this.animThread = null;
                        FPSAnimator.this.notifyAll();
                    }
                    if (b) {
                        FPSAnimator.this.flushGLRunnables();
                    }
                    if (n != 0) {
                        throw ex;
                    }
                }
            }
        }
    }
}
