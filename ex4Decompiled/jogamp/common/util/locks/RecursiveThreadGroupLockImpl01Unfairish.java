// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util.locks;

import java.util.Arrays;
import com.jogamp.common.util.locks.RecursiveThreadGroupLock;

public class RecursiveThreadGroupLockImpl01Unfairish extends RecursiveLockImpl01Unfairish implements RecursiveThreadGroupLock
{
    public RecursiveThreadGroupLockImpl01Unfairish() {
        super(new ThreadGroupSync());
    }
    
    @Override
    public final boolean isOriginalOwner() {
        return this.isOriginalOwner(Thread.currentThread());
    }
    
    @Override
    public final boolean isOriginalOwner(final Thread thread) {
        synchronized (this.sync) {
            return ((ThreadGroupSync)this.sync).isOriginalOwner(thread);
        }
    }
    
    @Override
    public final void addOwner(final Thread thread) throws RuntimeException, IllegalArgumentException {
        this.validateLocked();
        final Thread currentThread = Thread.currentThread();
        final ThreadGroupSync threadGroupSync = (ThreadGroupSync)this.sync;
        if (!threadGroupSync.isOriginalOwner(currentThread)) {
            throw new IllegalArgumentException("Current thread is not the original owner: orig-owner: " + threadGroupSync.getOwner() + ", current " + currentThread + ": " + this.toString());
        }
        if (threadGroupSync.isOriginalOwner(thread)) {
            throw new IllegalArgumentException("Passed thread is original owner: " + thread + ", " + this.toString());
        }
        threadGroupSync.addOwner(thread);
    }
    
    @Override
    public final void unlock(final Runnable runnable) {
        synchronized (this.sync) {
            final Thread currentThread = Thread.currentThread();
            final ThreadGroupSync threadGroupSync = (ThreadGroupSync)this.sync;
            if (threadGroupSync.getAddOwnerCount() > 0) {
                if (RecursiveThreadGroupLockImpl01Unfairish.TRACE_LOCK) {
                    System.err.println("--- LOCK XR (tg) " + this.toString() + ", cur " + this.threadName(currentThread) + " -> owner...");
                }
                if (threadGroupSync.isOriginalOwner(currentThread)) {
                    if (threadGroupSync.getHoldCount() - threadGroupSync.getAdditionalOwnerHoldCount() == 1) {
                        threadGroupSync.setWaitingOrigOwner(currentThread);
                        try {
                            while (threadGroupSync.getAdditionalOwnerHoldCount() > 0) {
                                try {
                                    this.sync.wait();
                                }
                                catch (InterruptedException ex) {}
                            }
                        }
                        finally {
                            threadGroupSync.setWaitingOrigOwner(null);
                            Thread.interrupted();
                        }
                        threadGroupSync.removeAllOwners();
                    }
                }
                else if (threadGroupSync.getAdditionalOwnerHoldCount() == 1) {
                    final Thread waitingOrigOwner = threadGroupSync.getWaitingOrigOwner();
                    if (null != waitingOrigOwner) {
                        waitingOrigOwner.interrupt();
                    }
                }
            }
            if (RecursiveThreadGroupLockImpl01Unfairish.TRACE_LOCK) {
                System.err.println("++ unlock(X): currentThread " + currentThread.getName() + ", lock: " + this.toString());
                System.err.println("--- LOCK X0 (tg) " + this.toString() + ", cur " + this.threadName(currentThread) + " -> unlock!");
            }
            super.unlock(runnable);
        }
    }
    
    @Override
    public final void removeOwner(final Thread thread) throws RuntimeException, IllegalArgumentException {
        this.validateLocked();
        ((ThreadGroupSync)this.sync).removeOwner(thread);
    }
    
    @Override
    public String toString() {
        final ThreadGroupSync threadGroupSync = (ThreadGroupSync)this.sync;
        final int holdCount = this.sync.getHoldCount();
        final int additionalOwnerHoldCount = threadGroupSync.getAdditionalOwnerHoldCount();
        return this.syncName() + "[count " + holdCount + " [ add. " + additionalOwnerHoldCount + ", orig " + (holdCount - additionalOwnerHoldCount) + "], qsz " + this.sync.getQSz() + ", owner " + this.threadName(this.sync.getOwner()) + ", add.owner " + threadGroupSync.addOwnerToString() + "]";
    }
    
    static class ThreadGroupSync extends SingleThreadSync
    {
        private int holdCountAdditionOwner;
        private Thread[] threads;
        private int threadNum;
        private Thread waitingOrigOwner;
        
        ThreadGroupSync() {
            this.threadNum = 0;
            this.threads = null;
            this.holdCountAdditionOwner = 0;
            this.waitingOrigOwner = null;
        }
        
        @Override
        public final void incrHoldCount(final Thread thread) {
            super.incrHoldCount(thread);
            if (!this.isOriginalOwner(thread)) {
                ++this.holdCountAdditionOwner;
            }
        }
        
        @Override
        public final void decrHoldCount(final Thread thread) {
            super.decrHoldCount(thread);
            if (!this.isOriginalOwner(thread)) {
                --this.holdCountAdditionOwner;
            }
        }
        
        public final int getAdditionalOwnerHoldCount() {
            return this.holdCountAdditionOwner;
        }
        
        public final boolean isOriginalOwner(final Thread thread) {
            return super.isOwner(thread);
        }
        
        public final void setWaitingOrigOwner(final Thread waitingOrigOwner) {
            this.waitingOrigOwner = waitingOrigOwner;
        }
        
        public final Thread getWaitingOrigOwner() {
            return this.waitingOrigOwner;
        }
        
        @Override
        public final boolean isOwner(final Thread thread) {
            if (this.getExclusiveOwnerThread() == thread) {
                return true;
            }
            for (int n = this.threadNum - 1; 0 <= n; --n) {
                if (this.threads[n] == thread) {
                    return true;
                }
            }
            return false;
        }
        
        public final int getAddOwnerCount() {
            return this.threadNum;
        }
        
        public final void addOwner(final Thread thread) throws IllegalArgumentException {
            if (null == this.threads) {
                if (this.threadNum > 0) {
                    throw new InternalError("XXX");
                }
                this.threads = new Thread[4];
            }
            for (int n = this.threadNum - 1; 0 <= n; --n) {
                if (this.threads[n] == thread) {
                    throw new IllegalArgumentException("Thread already added: " + thread);
                }
            }
            if (this.threadNum == this.threads.length) {
                this.threads = Arrays.copyOf(this.threads, this.threadNum * 2);
            }
            this.threads[this.threadNum] = thread;
            ++this.threadNum;
        }
        
        public final void removeAllOwners() {
            for (int n = this.threadNum - 1; 0 <= n; --n) {
                this.threads[n] = null;
            }
            this.threadNum = 0;
        }
        
        public final void removeOwner(final Thread thread) throws IllegalArgumentException {
            for (int i = 0; i < this.threadNum; ++i) {
                if (this.threads[i] == thread) {
                    --this.threadNum;
                    System.arraycopy(this.threads, i + 1, this.threads, i, this.threadNum - i);
                    this.threads[this.threadNum] = null;
                    return;
                }
            }
            throw new IllegalArgumentException("Not an owner: " + thread);
        }
        
        String addOwnerToString() {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.threadNum; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.threads[i].getName());
            }
            return sb.toString();
        }
    }
}
