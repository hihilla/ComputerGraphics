// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.io.PrintStream;
import com.jogamp.common.ExceptionUtils;

public class SourcedInterruptedException extends InterruptedException implements ExceptionUtils.CustomStackTrace
{
    final Throwable interruptSource;
    
    public static InterruptedException wrap(final InterruptedException ex) {
        return wrap(ex, InterruptSource.Util.currentThread());
    }
    
    public static InterruptedException wrap(final InterruptedException ex, final InterruptSource interruptSource) {
        if (!(ex instanceof SourcedInterruptedException) && null != interruptSource) {
            return new SourcedInterruptedException(ex, interruptSource.getInterruptSource(true));
        }
        return ex;
    }
    
    public SourcedInterruptedException(final String s, final InterruptedException ex, final Throwable interruptSource) {
        super(s);
        if (null != ex) {
            this.initCause(ex);
        }
        this.interruptSource = interruptSource;
    }
    
    public SourcedInterruptedException(final InterruptedException ex, final Throwable interruptSource) {
        super(ex.getMessage());
        this.initCause(ex);
        this.interruptSource = interruptSource;
    }
    
    public final Throwable getInterruptSource() {
        return this.interruptSource;
    }
    
    @Override
    public InterruptedException getCause() {
        return (InterruptedException)super.getCause();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(256);
        sb.append(this.getClass().getSimpleName()).append(": ");
        if (null != this.interruptSource) {
            sb.append("[sourced]");
        }
        else {
            sb.append("[unknown]");
        }
        final String localizedMessage = this.getLocalizedMessage();
        if (null != localizedMessage) {
            sb.append(" ").append(localizedMessage);
        }
        return sb.toString();
    }
    
    @Override
    public final void printCauseStack(final PrintStream printStream, final String s, final int n, final int n2) {
        final String string = s + "[" + n + "]";
        printStream.println(string + " by " + this.getClass().getSimpleName() + ": " + this.getMessage() + " on thread " + Thread.currentThread().getName());
        ExceptionUtils.dumpStack(printStream, this.getStackTrace(), 0, n2);
        if (null != this.interruptSource) {
            ExceptionUtils.printCause(printStream, string, this.interruptSource, 0, 1, n2);
        }
    }
    
    @Override
    public final void printStackTrace(final PrintStream printStream, final int n, final int n2) {
        printStream.println(this.getClass().getSimpleName() + ": " + this.getMessage() + " on thread " + Thread.currentThread().getName());
        ExceptionUtils.dumpStack(printStream, this.getStackTrace(), 0, n2);
        ExceptionUtils.printCause(printStream, "Caused", this.getCause(), 0, n, n2);
        if (null != this.interruptSource) {
            ExceptionUtils.printCause(printStream, "InterruptSource", this.interruptSource, 0, n, n2);
        }
    }
}
