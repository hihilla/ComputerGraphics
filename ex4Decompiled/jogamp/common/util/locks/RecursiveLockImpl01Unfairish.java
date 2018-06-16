// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util.locks;

import java.util.List;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import com.jogamp.common.util.locks.RecursiveLock;

public class RecursiveLockImpl01Unfairish implements RecursiveLock
{
    protected final Sync sync;
    
    public RecursiveLockImpl01Unfairish(final Sync sync) {
        this.sync = sync;
    }
    
    public RecursiveLockImpl01Unfairish() {
        this(new SingleThreadSync());
    }
    
    public final Throwable getLockedStack() {
        synchronized (this.sync) {
            return this.sync.getLockedStack();
        }
    }
    
    @Override
    public final Thread getOwner() {
        synchronized (this.sync) {
            return this.sync.getOwner();
        }
    }
    
    @Override
    public final boolean isOwner(final Thread thread) {
        synchronized (this.sync) {
            return this.sync.isOwner(thread);
        }
    }
    
    @Override
    public final boolean isLocked() {
        synchronized (this.sync) {
            return null != this.sync.getOwner();
        }
    }
    
    @Override
    public final boolean isLockedByOtherThread() {
        synchronized (this.sync) {
            final Thread owner = this.sync.getOwner();
            return null != owner && Thread.currentThread() != owner;
        }
    }
    
    @Override
    public final int getHoldCount() {
        synchronized (this.sync) {
            return this.sync.getHoldCount();
        }
    }
    
    @Override
    public final void validateLocked() throws RuntimeException {
        synchronized (this.sync) {
            if (!this.sync.isOwner(Thread.currentThread())) {
                if (null == this.sync.getOwner()) {
                    throw new RuntimeException(this.threadName(Thread.currentThread()) + ": Not locked: " + this.toString());
                }
                if (null != this.sync.getLockedStack()) {
                    this.sync.getLockedStack().printStackTrace();
                }
                throw new RuntimeException(Thread.currentThread() + ": Not owner: " + this.toString());
            }
        }
    }
    
    @Override
    public final void lock() {
        synchronized (this.sync) {
            try {
                if (!this.tryLock(RecursiveLockImpl01Unfairish.TIMEOUT)) {
                    if (null != this.sync.getLockedStack()) {
                        this.sync.getLockedStack().printStackTrace();
                    }
                    throw new RuntimeException("Waited " + RecursiveLockImpl01Unfairish.TIMEOUT + "ms for: " + this.toString() + " - " + this.threadName(Thread.currentThread()));
                }
            }
            catch (InterruptedException ex) {
                throw new RuntimeException("Interrupted", ex);
            }
        }
    }
    
    @Override
    public final boolean tryLock(long n) throws InterruptedException {
        synchronized (this.sync) {
            final Thread currentThread = Thread.currentThread();
            if (RecursiveLockImpl01Unfairish.TRACE_LOCK) {
                System.err.println("+++ LOCK 0 " + this.toString() + ", timeout " + n + " ms, cur " + this.threadName(currentThread));
            }
            if (this.sync.isOwner(currentThread)) {
                this.sync.incrHoldCount(currentThread);
                if (RecursiveLockImpl01Unfairish.TRACE_LOCK) {
                    System.err.println("+++ LOCK XR " + this.toString() + ", cur " + this.threadName(currentThread));
                }
                return true;
            }
            if (this.sync.getOwner() != null || (0L < n && 0 < this.sync.getQSz())) {
                if (0L >= n) {
                    if (RecursiveLockImpl01Unfairish.TRACE_LOCK) {
                        System.err.println("+++ LOCK XY " + this.toString() + ", cur " + this.threadName(currentThread) + ", left " + n + " ms");
                    }
                    return false;
                }
                this.sync.incrQSz();
                do {
                    final long currentTimeMillis = System.currentTimeMillis();
                    this.sync.wait(n);
                    n -= System.currentTimeMillis() - currentTimeMillis;
                } while (null != this.sync.getOwner() && 0L < n);
                this.sync.decrQSz();
                if (0L >= n && this.sync.getOwner() != null) {
                    if (RecursiveLockImpl01Unfairish.TRACE_LOCK) {
                        System.err.println("+++ LOCK XX " + this.toString() + ", cur " + this.threadName(currentThread) + ", left " + n + " ms");
                    }
                    return false;
                }
                if (RecursiveLockImpl01Unfairish.TRACE_LOCK) {
                    System.err.println("+++ LOCK X1 " + this.toString() + ", cur " + this.threadName(currentThread) + ", left " + n + " ms");
                }
            }
            else if (RecursiveLockImpl01Unfairish.TRACE_LOCK) {
                System.err.println("+++ LOCK X0 " + this.toString() + ", cur " + this.threadName(currentThread));
            }
            this.sync.setOwner(currentThread);
            this.sync.incrHoldCount(currentThread);
            if (RecursiveLockImpl01Unfairish.DEBUG) {
                this.sync.setLockedStack(new Throwable("Previously locked by " + this.toString()));
            }
            return true;
        }
    }
    
    @Override
    public final void unlock() {
        synchronized (this.sync) {
            this.unlock(null);
        }
    }
    
    @Override
    public void unlock(final Runnable runnable) {
        synchronized (this.sync) {
            this.validateLocked();
            final Thread currentThread = Thread.currentThread();
            this.sync.decrHoldCount(currentThread);
            if (this.sync.getHoldCount() > 0) {
                if (RecursiveLockImpl01Unfairish.TRACE_LOCK) {
                    System.err.println("--- LOCK XR " + this.toString() + ", cur " + this.threadName(currentThread));
                }
                return;
            }
            this.sync.setOwner(null);
            if (RecursiveLockImpl01Unfairish.DEBUG) {
                this.sync.setLockedStack(null);
            }
            if (null != runnable) {
                runnable.run();
            }
            if (RecursiveLockImpl01Unfairish.TRACE_LOCK) {
                System.err.println("--- LOCK X0 " + this.toString() + ", cur " + this.threadName(currentThread) + ", signal any");
            }
            this.sync.notify();
        }
    }
    
    @Override
    public final int getQueueLength() {
        synchronized (this.sync) {
            return this.sync.getQSz();
        }
    }
    
    @Override
    public String toString() {
        return this.syncName() + "[count " + this.sync.getHoldCount() + ", qsz " + this.sync.getQSz() + ", owner " + this.threadName(this.sync.getOwner()) + "]";
    }
    
    final String syncName() {
        return "<" + Integer.toHexString(this.hashCode()) + ", " + Integer.toHexString(this.sync.hashCode()) + ">";
    }
    
    final String threadName(final Thread thread) {
        return (null != thread) ? ("<" + thread.getName() + ">") : "<NULL>";
    }
    
    static class SingleThreadSync extends AbstractOwnableSynchronizer implements Sync
    {
        private int holdCount;
        private int qsz;
        private Throwable lockedStack;
        
        SingleThreadSync() {
            this.holdCount = 0;
            this.qsz = 0;
            this.lockedStack = null;
        }
        
        @Override
        public final Thread getOwner() {
            return this.getExclusiveOwnerThread();
        }
        
        @Override
        public boolean isOwner(final Thread thread) {
            return this.getExclusiveOwnerThread() == thread;
        }
        
        @Override
        public final void setOwner(final Thread exclusiveOwnerThread) {
            this.setExclusiveOwnerThread(exclusiveOwnerThread);
        }
        
        @Override
        public final Throwable getLockedStack() {
            return this.lockedStack;
        }
        
        @Override
        public final void setLockedStack(final Throwable lockedStack) {
            final List<Throwable> recursiveLockTrace = LockDebugUtil.getRecursiveLockTrace();
            if (lockedStack == null) {
                recursiveLockTrace.remove(this.lockedStack);
            }
            else {
                recursiveLockTrace.add(lockedStack);
            }
            this.lockedStack = lockedStack;
        }
        
        @Override
        public final int getHoldCount() {
            return this.holdCount;
        }
        
        @Override
        public void incrHoldCount(final Thread thread) {
            ++this.holdCount;
        }
        
        @Override
        public void decrHoldCount(final Thread thread) {
            --this.holdCount;
        }
        
        @Override
        public final int getQSz() {
            return this.qsz;
        }
        
        @Override
        public final void incrQSz() {
            ++this.qsz;
        }
        
        @Override
        public final void decrQSz() {
            --this.qsz;
        }
    }
    
    interface Sync
    {
        Thread getOwner();
        
        boolean isOwner(final Thread p0);
        
        void setOwner(final Thread p0);
        
        Throwable getLockedStack();
        
        void setLockedStack(final Throwable p0);
        
        int getHoldCount();
        
        void incrHoldCount(final Thread p0);
        
        void decrHoldCount(final Thread p0);
        
        int getQSz();
        
        void incrQSz();
        
        void decrQSz();
    }
}
