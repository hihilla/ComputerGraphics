// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.locks;

import jogamp.common.util.locks.SingletonInstanceServerSocket;
import java.io.File;
import jogamp.common.util.locks.SingletonInstanceFileLock;

public abstract class SingletonInstance implements Lock
{
    protected static final boolean DEBUG = true;
    private final long poll_ms;
    private boolean locked;
    
    public static SingletonInstance createFileLock(final long n, final String s) {
        return new SingletonInstanceFileLock(n, s);
    }
    
    public static SingletonInstance createFileLock(final long n, final File file) {
        return new SingletonInstanceFileLock(n, file);
    }
    
    public static SingletonInstance createServerSocket(final long n, final int n2) {
        return new SingletonInstanceServerSocket(n, n2);
    }
    
    protected SingletonInstance(final long n) {
        this.locked = false;
        this.poll_ms = Math.max(10L, n);
    }
    
    public final long getPollPeriod() {
        return this.poll_ms;
    }
    
    public abstract String getName();
    
    @Override
    public final String toString() {
        return this.getName();
    }
    
    @Override
    public synchronized void lock() throws RuntimeException {
        try {
            while (!this.tryLock(SingletonInstance.TIMEOUT)) {}
        }
        catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public synchronized boolean tryLock(long n) throws RuntimeException {
        if (this.locked) {
            return true;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        int n2 = 0;
        try {
            do {
                final long currentTimeMillis2 = System.currentTimeMillis();
                this.locked = this.tryLockImpl();
                if (this.locked) {
                    final long currentTimeMillis3 = System.currentTimeMillis();
                    System.err.println(this.infoPrefix(currentTimeMillis3) + " +++ " + this.getName() + " - Locked within " + (currentTimeMillis3 - currentTimeMillis) + " ms, " + (n2 + 1) + " attempts");
                    return true;
                }
                if (n2 == 0) {
                    System.err.println(this.infoPrefix(System.currentTimeMillis()) + " III " + this.getName() + " - Wait for lock");
                }
                Thread.sleep(this.poll_ms);
                n -= System.currentTimeMillis() - currentTimeMillis2;
                ++n2;
            } while (0L < n);
        }
        catch (InterruptedException ex) {
            final long currentTimeMillis4 = System.currentTimeMillis();
            throw new RuntimeException(this.infoPrefix(currentTimeMillis4) + " EEE (1) " + this.getName() + " - couldn't get lock within " + (currentTimeMillis4 - currentTimeMillis) + " ms, " + n2 + " attempts", ex);
        }
        final long currentTimeMillis5 = System.currentTimeMillis();
        System.err.println(this.infoPrefix(currentTimeMillis5) + " +++ EEE (2) " + this.getName() + " - couldn't get lock within " + (currentTimeMillis5 - currentTimeMillis) + " ms, " + n2 + " attempts");
        return false;
    }
    
    protected abstract boolean tryLockImpl();
    
    @Override
    public void unlock() throws RuntimeException {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.locked) {
            this.locked = !this.unlockImpl();
            final long currentTimeMillis2 = System.currentTimeMillis();
            System.err.println(this.infoPrefix(currentTimeMillis2) + " --- " + this.getName() + " - Unlock " + (this.locked ? "failed" : "ok") + " within " + (currentTimeMillis2 - currentTimeMillis) + " ms");
        }
    }
    
    protected abstract boolean unlockImpl();
    
    @Override
    public synchronized boolean isLocked() {
        return this.locked;
    }
    
    protected String infoPrefix(final long n) {
        return "SLOCK [T " + Thread.currentThread().getName() + " @ " + n + " ms";
    }
    
    protected String infoPrefix() {
        return this.infoPrefix(System.currentTimeMillis());
    }
}
