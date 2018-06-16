// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import com.jogamp.common.util.locks.RecursiveLock;

public class RecursiveLockImplJava5 implements RecursiveLock
{
    volatile Thread owner;
    ReentrantLock lock;
    
    public RecursiveLockImplJava5(final boolean b) {
        this.owner = null;
        this.lock = new ReentrantLock(b);
    }
    
    @Override
    public void lock() {
        try {
            if (!this.tryLock(RecursiveLockImplJava5.TIMEOUT)) {
                throw new RuntimeException("Waited " + RecursiveLockImplJava5.TIMEOUT + "ms for: " + this.threadName(this.owner) + " - " + this.threadName(Thread.currentThread()) + ", with count " + this.getHoldCount() + ", lock: " + this);
            }
        }
        catch (InterruptedException ex) {
            throw new RuntimeException("Interrupted", ex);
        }
        this.owner = Thread.currentThread();
    }
    
    @Override
    public boolean tryLock(final long n) throws InterruptedException {
        if (this.lock.tryLock(n, TimeUnit.MILLISECONDS)) {
            this.owner = Thread.currentThread();
            return true;
        }
        return false;
    }
    
    @Override
    public void unlock() throws RuntimeException {
        this.unlock(null);
    }
    
    @Override
    public void unlock(final Runnable runnable) {
        this.validateLocked();
        this.owner = null;
        if (null != runnable) {
            runnable.run();
        }
        this.lock.unlock();
    }
    
    @Override
    public boolean isLocked() {
        return this.lock.isLocked();
    }
    
    @Override
    public Thread getOwner() {
        return this.owner;
    }
    
    @Override
    public boolean isLockedByOtherThread() {
        return this.lock.isLocked() && !this.lock.isHeldByCurrentThread();
    }
    
    @Override
    public boolean isOwner(final Thread thread) {
        return this.lock.isLocked() && this.owner == thread;
    }
    
    @Override
    public void validateLocked() throws RuntimeException {
        if (this.lock.isHeldByCurrentThread()) {
            return;
        }
        if (!this.lock.isLocked()) {
            throw new RuntimeException(Thread.currentThread() + ": Not locked");
        }
        throw new RuntimeException(Thread.currentThread() + ": Not owner, owner is " + this.owner);
    }
    
    @Override
    public int getHoldCount() {
        return this.lock.getHoldCount();
    }
    
    @Override
    public int getQueueLength() {
        return this.lock.getQueueLength();
    }
    
    private String threadName(final Thread thread) {
        return (null != thread) ? ("<" + thread.getName() + ">") : "<NULL>";
    }
}
