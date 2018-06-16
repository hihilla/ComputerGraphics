// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import jogamp.common.Debug;
import java.io.PrintStream;

public abstract class TaskBase implements Runnable
{
    private static final boolean TRACE_SOURCE;
    protected final Object syncObject;
    protected final boolean catchExceptions;
    protected final PrintStream exceptionOut;
    protected final Throwable sourceStack;
    protected Object attachment;
    protected Throwable runnableException;
    protected long tCreated;
    protected long tStarted;
    protected volatile long tExecuted;
    protected volatile boolean isExecuted;
    protected volatile boolean isFlushed;
    protected volatile Thread execThread;
    
    protected TaskBase(final Object syncObject, final boolean catchExceptions, final PrintStream exceptionOut) {
        this.syncObject = syncObject;
        this.catchExceptions = catchExceptions;
        this.exceptionOut = exceptionOut;
        this.sourceStack = (TaskBase.TRACE_SOURCE ? new Throwable("Creation @") : null);
        this.tCreated = System.currentTimeMillis();
        this.tStarted = 0L;
        this.tExecuted = 0L;
        this.isExecuted = false;
        this.isFlushed = false;
        this.execThread = null;
    }
    
    protected final String getExceptionOutIntro() {
        return this.catchExceptions ? "A caught" : "An uncaught";
    }
    
    protected final void printSourceTrace() {
        if (null != this.sourceStack && null != this.exceptionOut) {
            this.sourceStack.printStackTrace(this.exceptionOut);
        }
    }
    
    public final Thread getExecutionThread() {
        return this.execThread;
    }
    
    public final Object getSyncObject() {
        return this.syncObject;
    }
    
    public final void setAttachment(final Object attachment) {
        this.attachment = attachment;
    }
    
    public final Object getAttachment() {
        return this.attachment;
    }
    
    @Override
    public abstract void run();
    
    public final void flush(final Throwable runnableException) {
        if (!this.isExecuted() && this.hasWaiter()) {
            this.runnableException = runnableException;
            synchronized (this.syncObject) {
                this.isFlushed = true;
                this.syncObject.notifyAll();
            }
        }
    }
    
    public final boolean isInQueue() {
        return !this.isExecuted && !this.isFlushed;
    }
    
    public final boolean isExecuted() {
        return this.isExecuted;
    }
    
    public final boolean isFlushed() {
        return this.isFlushed;
    }
    
    public final boolean hasWaiter() {
        return null != this.syncObject;
    }
    
    public final Throwable getThrowable() {
        return this.runnableException;
    }
    
    public final long getTimestampCreate() {
        return this.tCreated;
    }
    
    public final long getTimestampBeforeExec() {
        return this.tStarted;
    }
    
    public final long getTimestampAfterExec() {
        return this.tExecuted;
    }
    
    public final long getDurationInQueue() {
        return this.tStarted - this.tCreated;
    }
    
    public final long getDurationInExec() {
        return (0L < this.tExecuted) ? (this.tExecuted - this.tStarted) : 0L;
    }
    
    public final long getDurationTotal() {
        return (0L < this.tExecuted) ? (this.tExecuted - this.tCreated) : (this.tStarted - this.tCreated);
    }
    
    @Override
    public String toString() {
        return "RunnableTask[enqueued " + this.isInQueue() + "[executed " + this.isExecuted() + ", flushed " + this.isFlushed() + "], tTotal " + this.getDurationTotal() + " ms, tExec " + this.getDurationInExec() + " ms, tQueue " + this.getDurationInQueue() + " ms, attachment " + this.attachment + ", throwable " + this.getThrowable() + "]";
    }
    
    static {
        Debug.initSingleton();
        TRACE_SOURCE = PropertyAccess.isPropertyDefined("jogamp.debug.TaskBase.TraceSource", true);
    }
}
