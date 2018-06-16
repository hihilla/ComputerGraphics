// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.util.SourcedInterruptedException;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLAutoDrawable;

public class Animator extends AnimatorBase
{
    private ThreadGroup threadGroup;
    private Runnable runnable;
    private boolean runAsFastAsPossible;
    boolean isAnimating;
    volatile boolean pauseIssued;
    volatile boolean stopIssued;
    private final Condition waitForStartedCondition;
    private final Condition waitForStoppedCondition;
    private final Condition waitForPausedCondition;
    private final Condition waitForResumeCondition;
    
    public Animator() {
        this.waitForStartedCondition = new Condition() {
            @Override
            public boolean eval() {
                return !Animator.this.isStarted() || (!Animator.this.drawablesEmpty && !Animator.this.isAnimating);
            }
        };
        this.waitForStoppedCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted();
            }
        };
        this.waitForPausedCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted() && Animator.this.isAnimating;
            }
        };
        this.waitForResumeCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted() && ((!Animator.this.drawablesEmpty && !Animator.this.isAnimating) || (Animator.this.drawablesEmpty && !Animator.this.pauseIssued));
            }
        };
        if (Animator.DEBUG) {
            System.err.println("Animator created");
        }
    }
    
    public Animator(final ThreadGroup threadGroup) {
        this.waitForStartedCondition = new Condition() {
            @Override
            public boolean eval() {
                return !Animator.this.isStarted() || (!Animator.this.drawablesEmpty && !Animator.this.isAnimating);
            }
        };
        this.waitForStoppedCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted();
            }
        };
        this.waitForPausedCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted() && Animator.this.isAnimating;
            }
        };
        this.waitForResumeCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted() && ((!Animator.this.drawablesEmpty && !Animator.this.isAnimating) || (Animator.this.drawablesEmpty && !Animator.this.pauseIssued));
            }
        };
        this.setThreadGroup(threadGroup);
        if (Animator.DEBUG) {
            System.err.println("Animator created, ThreadGroup: " + this.threadGroup);
        }
    }
    
    public Animator(final GLAutoDrawable glAutoDrawable) {
        this.waitForStartedCondition = new Condition() {
            @Override
            public boolean eval() {
                return !Animator.this.isStarted() || (!Animator.this.drawablesEmpty && !Animator.this.isAnimating);
            }
        };
        this.waitForStoppedCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted();
            }
        };
        this.waitForPausedCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted() && Animator.this.isAnimating;
            }
        };
        this.waitForResumeCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted() && ((!Animator.this.drawablesEmpty && !Animator.this.isAnimating) || (Animator.this.drawablesEmpty && !Animator.this.pauseIssued));
            }
        };
        this.add(glAutoDrawable);
        if (Animator.DEBUG) {
            System.err.println("Animator created, w/ " + glAutoDrawable);
        }
    }
    
    public Animator(final ThreadGroup threadGroup, final GLAutoDrawable glAutoDrawable) {
        this.waitForStartedCondition = new Condition() {
            @Override
            public boolean eval() {
                return !Animator.this.isStarted() || (!Animator.this.drawablesEmpty && !Animator.this.isAnimating);
            }
        };
        this.waitForStoppedCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted();
            }
        };
        this.waitForPausedCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted() && Animator.this.isAnimating;
            }
        };
        this.waitForResumeCondition = new Condition() {
            @Override
            public boolean eval() {
                return Animator.this.isStarted() && ((!Animator.this.drawablesEmpty && !Animator.this.isAnimating) || (Animator.this.drawablesEmpty && !Animator.this.pauseIssued));
            }
        };
        this.setThreadGroup(threadGroup);
        this.add(glAutoDrawable);
        if (Animator.DEBUG) {
            System.err.println("Animator created, ThreadGroup: " + this.threadGroup + " and " + glAutoDrawable);
        }
    }
    
    @Override
    protected final String getBaseName(final String s) {
        return s + "Animator";
    }
    
    public final synchronized void setRunAsFastAsPossible(final boolean runAsFastAsPossible) {
        this.runAsFastAsPossible = runAsFastAsPossible;
    }
    
    @Override
    public final synchronized boolean isAnimating() {
        return this.animThread != null && this.isAnimating;
    }
    
    @Override
    public final synchronized boolean isPaused() {
        return this.animThread != null && this.pauseIssued;
    }
    
    public final synchronized void setThreadGroup(final ThreadGroup threadGroup) throws GLException {
        if (this.isStarted()) {
            throw new GLException("Animator already started.");
        }
        this.threadGroup = threadGroup;
    }
    
    @Override
    public final synchronized boolean start() {
        if (this.isStarted()) {
            return false;
        }
        if (this.runnable == null) {
            this.runnable = new MainLoop();
        }
        this.fpsCounter.resetFPSCounter();
        final InterruptSource.Thread thread = new InterruptSource.Thread(this.threadGroup, this.runnable, AnimatorBase.getThreadName() + "-" + this.baseName);
        thread.setDaemon(false);
        if (Animator.DEBUG) {
            final Thread currentThread = Thread.currentThread();
            System.err.println("Animator " + currentThread.getName() + "[daemon " + currentThread.isDaemon() + "]: starting " + thread.getName() + "[daemon " + thread.isDaemon() + "]");
        }
        thread.start();
        return this.finishLifecycleAction(this.waitForStartedCondition, 0L);
    }
    
    @Override
    public final synchronized boolean stop() {
        if (!this.isStarted()) {
            return false;
        }
        this.stopIssued = true;
        return this.finishLifecycleAction(this.waitForStoppedCondition, 0L);
    }
    
    @Override
    public final synchronized boolean pause() {
        if (!this.isStarted() || this.pauseIssued) {
            return false;
        }
        this.pauseIssued = true;
        return this.finishLifecycleAction(this.waitForPausedCondition, 0L);
    }
    
    @Override
    public final synchronized boolean resume() {
        if (!this.isStarted() || !this.pauseIssued) {
            return false;
        }
        this.pauseIssued = false;
        return this.finishLifecycleAction(this.waitForResumeCondition, 0L);
    }
    
    class MainLoop implements Runnable
    {
        @Override
        public String toString() {
            return "[started " + Animator.this.isStarted() + ", animating " + Animator.this.isAnimating() + ", paused " + Animator.this.isPaused() + ", drawable " + Animator.this.drawables.size() + ", drawablesEmpty " + Animator.this.drawablesEmpty + "]";
        }
        
        @Override
        public void run() {
            ThreadDeath threadDeath = null;
            UncaughtAnimatorException ex = null;
            try {
                synchronized (Animator.this) {
                    if (AnimatorBase.DEBUG) {
                        System.err.println("Animator start on " + AnimatorBase.getThreadName() + ": " + this.toString());
                    }
                    Animator.this.fpsCounter.resetFPSCounter();
                    Animator.this.animThread = Thread.currentThread();
                    Animator.this.isAnimating = false;
                }
                while (!Animator.this.stopIssued) {
                    synchronized (Animator.this) {
                        int n = 0;
                        while (!Animator.this.stopIssued && (Animator.this.pauseIssued || Animator.this.drawablesEmpty)) {
                            if (Animator.this.drawablesEmpty) {
                                Animator.this.pauseIssued = true;
                            }
                            final boolean pauseIssued = Animator.this.pauseIssued;
                            if (AnimatorBase.DEBUG) {
                                System.err.println("Animator pause on " + Animator.this.animThread.getName() + ": " + this.toString());
                            }
                            if (Animator.this.exclusiveContext && !Animator.this.drawablesEmpty && n == 0) {
                                n = 1;
                                Animator.this.setDrawablesExclCtxState(false);
                                try {
                                    Animator.this.display();
                                }
                                catch (UncaughtAnimatorException ex2) {
                                    ex = ex2;
                                    Animator.this.stopIssued = true;
                                    break;
                                }
                            }
                            Animator.this.isAnimating = false;
                            Animator.this.notifyAll();
                            try {
                                Animator.this.wait();
                            }
                            catch (InterruptedException ex3) {
                                ex = new UncaughtAnimatorException((GLAutoDrawable)null, SourcedInterruptedException.wrap(ex3));
                                Animator.this.stopIssued = true;
                                break;
                            }
                            if (pauseIssued) {
                                Animator.this.fpsCounter.resetFPSCounter();
                                if (!AnimatorBase.DEBUG) {
                                    continue;
                                }
                                System.err.println("Animator resume on " + Animator.this.animThread.getName() + ": " + this.toString());
                            }
                        }
                        if (!Animator.this.stopIssued && !Animator.this.isAnimating) {
                            Animator.this.isAnimating = true;
                            Animator.this.setDrawablesExclCtxState(Animator.this.exclusiveContext);
                            Animator.this.notifyAll();
                        }
                    }
                    if (!Animator.this.pauseIssued && !Animator.this.stopIssued) {
                        try {
                            Animator.this.display();
                        }
                        catch (UncaughtAnimatorException ex4) {
                            ex = ex4;
                            Animator.this.stopIssued = true;
                            break;
                        }
                        if (Animator.this.runAsFastAsPossible) {
                            continue;
                        }
                        Thread.yield();
                    }
                }
            }
            catch (ThreadDeath threadDeath2) {
                if (AnimatorBase.DEBUG) {
                    ExceptionUtils.dumpThrowable("", threadDeath2);
                }
                threadDeath = threadDeath2;
            }
            if (Animator.this.exclusiveContext && !Animator.this.drawablesEmpty) {
                Animator.this.setDrawablesExclCtxState(false);
                try {
                    Animator.this.display();
                }
                catch (UncaughtAnimatorException ex5) {
                    if (null == ex) {
                        ex = ex5;
                    }
                    else {
                        ExceptionUtils.dumpThrowable("(setExclusiveContextThread)", ex5);
                    }
                }
            }
            boolean b = false;
            int n2 = 0;
            synchronized (Animator.this) {
                if (AnimatorBase.DEBUG) {
                    System.err.println("Animator stop on " + Animator.this.animThread.getName() + ": " + this.toString());
                    if (null != ex) {
                        ExceptionUtils.dumpThrowable("", ex);
                    }
                }
                Animator.this.stopIssued = false;
                Animator.this.pauseIssued = false;
                Animator.this.isAnimating = false;
                if (null != ex) {
                    b = true;
                    n2 = (Animator.this.handleUncaughtException(ex) ? 0 : 1);
                }
                Animator.this.animThread = null;
                Animator.this.notifyAll();
            }
            if (b) {
                Animator.this.flushGLRunnables();
            }
            if (n2 != 0) {
                throw ex;
            }
            if (null != threadDeath) {
                throw threadDeath;
            }
        }
    }
}
