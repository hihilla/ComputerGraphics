// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common;

import java.io.PrintStream;

public class ExceptionUtils
{
    public static void dumpStack(final PrintStream printStream) {
        dumpStack(printStream, 1, -1);
    }
    
    public static void dumpStack(final PrintStream printStream, final int n, final int n2) {
        dumpStack(printStream, new Exception(""), n + 1, n2);
    }
    
    public static void dumpStack(final PrintStream printStream, final Throwable t, final int n, final int n2) {
        dumpStack(printStream, t.getStackTrace(), n, n2);
    }
    
    public static void dumpStack(final PrintStream printStream, final StackTraceElement[] array, final int n, final int n2) {
        if (null == array) {
            return;
        }
        int n3;
        if (0 > n2) {
            n3 = array.length;
        }
        else {
            n3 = Math.min(n2 + n, array.length);
        }
        for (int i = n; i < n3; ++i) {
            printStream.println("    [" + i + "]: " + array[i]);
        }
    }
    
    public static int printCause(final PrintStream printStream, final String s, Throwable cause, final int n, final int n2, final int n3) {
        int n4;
        for (n4 = n; null != cause && (-1 == n2 || n4 < n2); ++n4, cause = cause.getCause()) {
            if (cause instanceof CustomStackTrace) {
                ((CustomStackTrace)cause).printCauseStack(printStream, s, n4, n3);
            }
            else {
                printStream.println(s + "[" + n4 + "] by " + cause.getClass().getSimpleName() + ": " + cause.getMessage() + " on thread " + Thread.currentThread().getName());
                dumpStack(printStream, cause.getStackTrace(), 0, n3);
            }
        }
        return n4;
    }
    
    public static void printStackTrace(final PrintStream printStream, final Throwable t, final int n, final int n2) {
        if (t instanceof CustomStackTrace) {
            ((CustomStackTrace)t).printStackTrace(printStream, n, n2);
        }
        else {
            printStream.println(t.getClass().getSimpleName() + ": " + t.getMessage() + " on thread " + Thread.currentThread().getName());
            dumpStack(printStream, t.getStackTrace(), 0, n2);
            printCause(printStream, "Caused", t.getCause(), 0, n, n2);
        }
    }
    
    public static void dumpThrowable(final String s, final Throwable t) {
        dumpThrowable(s, t, -1, -1);
    }
    
    public static void dumpThrowable(final String s, final Throwable t, final int n, final int n2) {
        System.err.print("Caught " + s + " ");
        printStackTrace(System.err, t, n, n2);
    }
    
    public interface CustomStackTrace
    {
        void printCauseStack(final PrintStream p0, final String p1, final int p2, final int p3);
        
        void printStackTrace(final PrintStream p0, final int p1, final int p2);
    }
}
