// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

public interface InterruptSource
{
    Throwable getInterruptSource(final boolean p0);
    
    int getInterruptCounter(final boolean p0);
    
    void clearInterruptSource();
    
    public static class Util
    {
        public static InterruptSource get(final java.lang.Thread thread) {
            if (thread instanceof InterruptSource) {
                return (InterruptSource)thread;
            }
            return null;
        }
        
        public static InterruptSource currentThread() {
            return get(java.lang.Thread.currentThread());
        }
    }
    
    public static class Thread extends java.lang.Thread implements InterruptSource
    {
        volatile Throwable interruptSource;
        volatile int interruptCounter;
        final Object sync;
        
        public Thread() {
            this.interruptSource = null;
            this.interruptCounter = 0;
            this.sync = new Object();
        }
        
        public Thread(final ThreadGroup threadGroup, final Runnable runnable) {
            super(threadGroup, runnable);
            this.interruptSource = null;
            this.interruptCounter = 0;
            this.sync = new Object();
        }
        
        public Thread(final ThreadGroup threadGroup, final Runnable runnable, final String s) {
            super(threadGroup, runnable, s);
            this.interruptSource = null;
            this.interruptCounter = 0;
            this.sync = new Object();
        }
        
        public static Thread create(final ThreadGroup threadGroup, final Runnable runnable, final String s) {
            return (null != s) ? new Thread(threadGroup, runnable, s) : new Thread(threadGroup, runnable);
        }
        
        @Override
        public final Throwable getInterruptSource(final boolean b) {
            synchronized (this.sync) {
                final Throwable interruptSource = this.interruptSource;
                if (b) {
                    this.clearInterruptSource();
                }
                return interruptSource;
            }
        }
        
        @Override
        public final int getInterruptCounter(final boolean b) {
            synchronized (this.sync) {
                final int interruptCounter = this.interruptCounter;
                if (b) {
                    this.clearInterruptSource();
                }
                return interruptCounter;
            }
        }
        
        @Override
        public final void clearInterruptSource() {
            synchronized (this.sync) {
                this.interruptCounter = 0;
                this.interruptSource = null;
            }
        }
        
        @Override
        public final void interrupt() {
            synchronized (this.sync) {
                ++this.interruptCounter;
                this.interruptSource = new Throwable(this.getName() + ".interrupt() #" + this.interruptCounter);
            }
            super.interrupt();
        }
    }
}
