// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import com.jogamp.common.JogampRuntimeException;
import java.io.PrintStream;

public class FunctionTask<R, A> extends TaskBase implements Function<R, A>
{
    protected Function<R, A> runnable;
    protected R result;
    protected A[] args;
    
    public static <U, V> U invoke(final boolean b, final Function<U, V> function, final V... array) {
        return function.eval(array);
    }
    
    public static <U, V> FunctionTask<U, V> invokeOnNewThread(final ThreadGroup threadGroup, final String s, final boolean b, final Function<U, V> function, final V... array) {
        FunctionTask<U, V> functionTask;
        if (!b) {
            functionTask = new FunctionTask<U, V>(function, null, true, System.err);
            final InterruptSource.Thread create = InterruptSource.Thread.create(threadGroup, functionTask, s);
            functionTask.args = array;
            create.start();
        }
        else {
            final Object o = new Object();
            functionTask = new FunctionTask<U, V>((Function<Object, Object>)function, o, true, null);
            final InterruptSource.Thread create2 = InterruptSource.Thread.create(threadGroup, functionTask, s);
            synchronized (o) {
                functionTask.args = array;
                create2.start();
                while (functionTask.isInQueue()) {
                    try {
                        o.wait();
                    }
                    catch (InterruptedException ex) {
                        throw new InterruptedRuntimeException(ex);
                    }
                    final Throwable throwable = functionTask.getThrowable();
                    if (null != throwable) {
                        throw new JogampRuntimeException(throwable);
                    }
                }
            }
        }
        return functionTask;
    }
    
    public FunctionTask(final Function<R, A> runnable, final Object o, final boolean b, final PrintStream printStream) {
        super(o, b, printStream);
        this.runnable = runnable;
        this.result = null;
        this.args = null;
    }
    
    public final Function<R, A> getRunnable() {
        return this.runnable;
    }
    
    public final void setArgs(final A... args) {
        this.args = args;
    }
    
    public final R getResult() {
        final R result = this.result;
        this.result = null;
        return result;
    }
    
    @Override
    public final void run() {
        this.execThread = Thread.currentThread();
        final Object[] args = this.args;
        this.args = null;
        this.result = null;
        this.runnableException = null;
        this.tStarted = System.currentTimeMillis();
        if (null == this.syncObject) {
            try {
                this.result = this.runnable.eval((A[])args);
            }
            catch (Throwable runnableException) {
                this.runnableException = runnableException;
                if (null != this.exceptionOut) {
                    this.exceptionOut.println("FunctionTask.run(): " + this.getExceptionOutIntro() + " exception occured on thread " + Thread.currentThread().getName() + ": " + this.toString());
                    this.printSourceTrace();
                    runnableException.printStackTrace(this.exceptionOut);
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
                    this.result = this.runnable.eval((A[])args);
                }
                catch (Throwable runnableException2) {
                    this.runnableException = runnableException2;
                    if (null != this.exceptionOut) {
                        this.exceptionOut.println("FunctionTask.run(): " + this.getExceptionOutIntro() + " exception occured on thread " + Thread.currentThread().getName() + ": " + this.toString());
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
    
    @Override
    public final R eval(final A... args) {
        this.args = args;
        this.run();
        final R result = this.result;
        this.result = null;
        return result;
    }
}
