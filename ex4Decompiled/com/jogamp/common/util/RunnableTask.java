// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import com.jogamp.common.JogampRuntimeException;
import java.io.PrintStream;

public class RunnableTask extends TaskBase
{
    protected final Runnable runnable;
    
    public static void invoke(final boolean b, final Runnable runnable) {
        runnable.run();
    }
    
    public static Thread invokeOnNewThread(final ThreadGroup threadGroup, final boolean b, final Runnable runnable, final String s) {
        return invokeOnNewThread(threadGroup, s, b, runnable).getExecutionThread();
    }
    
    public static RunnableTask invokeOnNewThread(final ThreadGroup threadGroup, final String s, final boolean b, final Runnable runnable) {
        RunnableTask runnableTask;
        if (!b) {
            runnableTask = new RunnableTask(runnable, null, true, System.err);
            InterruptSource.Thread.create(threadGroup, runnableTask, s).start();
        }
        else {
            final Object o = new Object();
            runnableTask = new RunnableTask(runnable, o, true, null);
            final InterruptSource.Thread create = InterruptSource.Thread.create(threadGroup, runnableTask, s);
            synchronized (o) {
                create.start();
                while (runnableTask.isInQueue()) {
                    try {
                        o.wait();
                    }
                    catch (InterruptedException ex) {
                        throw new InterruptedRuntimeException(ex);
                    }
                    final Throwable throwable = runnableTask.getThrowable();
                    if (null != throwable) {
                        throw new JogampRuntimeException(throwable);
                    }
                }
            }
        }
        return runnableTask;
    }
    
    public RunnableTask(final Runnable runnable, final Object o, final boolean b, final PrintStream printStream) {
        super(o, b, printStream);
        this.runnable = runnable;
    }
    
    public final Runnable getRunnable() {
        return this.runnable;
    }
    
    @Override
    public final void run() {
        this.execThread = Thread.currentThread();
        this.runnableException = null;
        this.tStarted = System.currentTimeMillis();
        if (null == this.syncObject) {
            try {
                this.runnable.run();
            }
            catch (Throwable runnableException) {
                this.runnableException = runnableException;
                if (null != this.exceptionOut) {
                    this.exceptionOut.println("RunnableTask.run(): " + this.getExceptionOutIntro() + " exception occured on thread " + Thread.currentThread().getName() + ": " + this.toString());
                    this.printSourceTrace();
                    this.runnableException.printStackTrace(this.exceptionOut);
                }
                if (!this.catchExceptions) {
                    throw new RuntimeException(this.runnableException);
                }
            }
            finally {
                this.tExecuted = System.currentTimeMillis();
                this.isExecuted = true;
            }
        }
        else {
            synchronized (this.syncObject) {
                try {
                    this.runnable.run();
                }
                catch (Throwable runnableException2) {
                    this.runnableException = runnableException2;
                    if (null != this.exceptionOut) {
                        this.exceptionOut.println("RunnableTask.run(): " + this.getExceptionOutIntro() + " exception occured on thread " + Thread.currentThread().getName() + ": " + this.toString());
                        this.printSourceTrace();
                        runnableException2.printStackTrace(this.exceptionOut);
                    }
                    if (!this.catchExceptions) {
                        throw new RuntimeException(this.runnableException);
                    }
                }
                finally {
                    this.tExecuted = System.currentTimeMillis();
                    this.isExecuted = true;
                    this.syncObject.notifyAll();
                }
            }
        }
    }
}
