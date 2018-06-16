// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util.locks;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import com.jogamp.common.util.SourcedInterruptedException;
import com.jogamp.common.util.locks.RecursiveLock;

public class RecursiveLockImpl01CompleteFair implements RecursiveLock
{
    private final Sync sync;
    
    public RecursiveLockImpl01CompleteFair() {
        this.sync = new Sync();
    }
    
    public final Throwable getLockedStack() {
        synchronized (this.sync) {
            return this.sync.lockedStack;
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
            return this.sync.getOwner() == thread;
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
            final Thread access$200 = this.sync.getOwner();
            return null != access$200 && Thread.currentThread() != access$200;
        }
    }
    
    @Override
    public final int getHoldCount() {
        synchronized (this.sync) {
            return this.sync.holdCount;
        }
    }
    
    @Override
    public final void validateLocked() throws RuntimeException {
        synchronized (this.sync) {
            if (Thread.currentThread() != this.sync.getOwner()) {
                if (null == this.sync.getOwner()) {
                    throw new RuntimeException(this.threadName(Thread.currentThread()) + ": Not locked: " + this.toString());
                }
                if (null != this.sync.lockedStack) {
                    this.sync.lockedStack.printStackTrace();
                }
                throw new RuntimeException(Thread.currentThread() + ": Not owner: " + this.toString());
            }
        }
    }
    
    @Override
    public final void lock() {
        synchronized (this.sync) {
            try {
                if (!this.tryLock(RecursiveLockImpl01CompleteFair.TIMEOUT)) {
                    if (null != this.sync.lockedStack) {
                        this.sync.lockedStack.printStackTrace();
                    }
                    throw new RuntimeException("Waited " + RecursiveLockImpl01CompleteFair.TIMEOUT + "ms for: " + this.toString() + " - " + this.threadName(Thread.currentThread()));
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
            if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                System.err.println("+++ LOCK 0 " + this.toString() + ", cur " + this.threadName(currentThread));
            }
            if (this.sync.getOwner() == currentThread) {
                ++this.sync.holdCount;
                if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                    System.err.println("+++ LOCK XR " + this.toString() + ", cur " + this.threadName(currentThread));
                }
                return true;
            }
            if (this.sync.getOwner() != null || (0L < n && 0 < this.sync.queue.size())) {
                if (0L >= n) {
                    if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                        System.err.println("+++ LOCK XY " + this.toString() + ", cur " + this.threadName(currentThread) + ", left " + n + " ms");
                    }
                    return false;
                }
                final WaitingThread waitingThread = new WaitingThread(currentThread);
                this.sync.queue.add(0, waitingThread);
                do {
                    final long currentTimeMillis = System.currentTimeMillis();
                    try {
                        this.sync.wait(n);
                        n -= System.currentTimeMillis() - currentTimeMillis;
                    }
                    catch (InterruptedException ex) {
                        if (!waitingThread.signaledByUnlock) {
                            this.sync.queue.remove(waitingThread);
                            throw SourcedInterruptedException.wrap(ex);
                        }
                        if (currentThread == this.sync.getOwner()) {
                            continue;
                        }
                        n -= System.currentTimeMillis() - currentTimeMillis;
                        if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                            System.err.println("+++ LOCK 1 " + this.toString() + ", cur " + this.threadName(currentThread) + ", left " + n + " ms, signaled: " + waitingThread.signaledByUnlock);
                        }
                        if (0L >= n) {
                            continue;
                        }
                        waitingThread.signaledByUnlock = false;
                        this.sync.queue.add(this.sync.queue.size(), waitingThread);
                    }
                } while (currentThread != this.sync.getOwner() && 0L < n);
                Thread.interrupted();
                if (0L >= n && currentThread != this.sync.getOwner()) {
                    if (!waitingThread.signaledByUnlock) {
                        this.sync.queue.remove(waitingThread);
                    }
                    if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                        System.err.println("+++ LOCK XX " + this.toString() + ", cur " + this.threadName(currentThread) + ", left " + n + " ms");
                    }
                    return false;
                }
                ++this.sync.holdCount;
                if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                    System.err.println("+++ LOCK X1 " + this.toString() + ", cur " + this.threadName(currentThread) + ", left " + n + " ms");
                }
            }
            else {
                ++this.sync.holdCount;
                if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                    System.err.println("+++ LOCK X0 " + this.toString() + ", cur " + this.threadName(currentThread));
                }
            }
            this.sync.setOwner(currentThread);
            if (RecursiveLockImpl01CompleteFair.DEBUG) {
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
    public final void unlock(final Runnable runnable) {
        synchronized (this.sync) {
            this.validateLocked();
            final Thread currentThread = Thread.currentThread();
            --this.sync.holdCount;
            if (this.sync.holdCount > 0) {
                if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                    System.err.println("--- LOCK XR " + this.toString() + ", cur " + this.threadName(currentThread));
                }
                return;
            }
            if (RecursiveLockImpl01CompleteFair.DEBUG) {
                this.sync.setLockedStack(null);
            }
            if (null != runnable) {
                runnable.run();
            }
            if (this.sync.queue.size() > 0) {
                final WaitingThread waitingThread = this.sync.queue.remove(this.sync.queue.size() - 1);
                this.sync.setOwner(waitingThread.thread);
                if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                    System.err.println("--- LOCK X1 " + this.toString() + ", cur " + this.threadName(currentThread) + ", signal: " + this.threadName(waitingThread.thread));
                }
                waitingThread.signaledByUnlock = true;
                waitingThread.thread.interrupt();
            }
            else {
                this.sync.setOwner(null);
                if (RecursiveLockImpl01CompleteFair.TRACE_LOCK) {
                    System.err.println("--- LOCK X0 " + this.toString() + ", cur " + this.threadName(currentThread) + ", signal any");
                }
                this.sync.notify();
            }
        }
    }
    
    @Override
    public final int getQueueLength() {
        synchronized (this.sync) {
            return this.sync.queue.size();
        }
    }
    
    @Override
    public String toString() {
        return this.syncName() + "[count " + this.sync.holdCount + ", qsz " + this.sync.queue.size() + ", owner " + this.threadName(this.sync.getOwner()) + "]";
    }
    
    private final String syncName() {
        return "<" + Integer.toHexString(this.hashCode()) + ", " + Integer.toHexString(this.sync.hashCode()) + ">";
    }
    
    private final String threadName(final Thread thread) {
        return (null != thread) ? ("<" + thread.getName() + ">") : "<NULL>";
    }
    
    private static class WaitingThread
    {
        final Thread thread;
        boolean signaledByUnlock;
        
        WaitingThread(final Thread thread) {
            this.thread = thread;
            this.signaledByUnlock = false;
        }
    }
    
    private static class Sync extends AbstractOwnableSynchronizer
    {
        private int holdCount;
        final ArrayList<WaitingThread> queue;
        private Throwable lockedStack;
        
        private Sync() {
            this.holdCount = 0;
            this.queue = new ArrayList<WaitingThread>();
            this.lockedStack = null;
        }
        
        private final Thread getOwner() {
            return this.getExclusiveOwnerThread();
        }
        
        private final void setOwner(final Thread exclusiveOwnerThread) {
            this.setExclusiveOwnerThread(exclusiveOwnerThread);
        }
        
        private final void setLockedStack(final Throwable lockedStack) {
            final List<Throwable> recursiveLockTrace = LockDebugUtil.getRecursiveLockTrace();
            if (lockedStack == null) {
                recursiveLockTrace.remove(this.lockedStack);
            }
            else {
                recursiveLockTrace.add(lockedStack);
            }
            this.lockedStack = lockedStack;
        }
    }
}
