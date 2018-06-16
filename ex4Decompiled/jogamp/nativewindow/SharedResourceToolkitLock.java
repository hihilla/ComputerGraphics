// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.common.util.locks.LockFactory;
import java.util.Iterator;
import com.jogamp.common.ExceptionUtils;
import java.util.concurrent.atomic.AtomicInteger;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.common.util.LongObjectHashMap;
import com.jogamp.nativewindow.ToolkitLock;

public class SharedResourceToolkitLock implements ToolkitLock
{
    private static final LongObjectHashMap handle2Lock;
    private final RecursiveLock lock;
    private final long handle;
    private final AtomicInteger refCount;
    
    public static int shutdown(final boolean b) {
        if (SharedResourceToolkitLock.DEBUG || b || SharedResourceToolkitLock.handle2Lock.size() > 0) {
            System.err.println("SharedResourceToolkitLock: Shutdown (open: " + SharedResourceToolkitLock.handle2Lock.size() + ")");
            if (SharedResourceToolkitLock.DEBUG) {
                ExceptionUtils.dumpStack(System.err);
            }
            if (SharedResourceToolkitLock.handle2Lock.size() > 0) {
                dumpOpenDisplayConnections();
            }
        }
        return SharedResourceToolkitLock.handle2Lock.size();
    }
    
    public static void dumpOpenDisplayConnections() {
        System.err.println("SharedResourceToolkitLock: Open ResourceToolkitLock's: " + SharedResourceToolkitLock.handle2Lock.size());
        int n = 0;
        final Iterator<LongObjectHashMap.Entry> iterator = SharedResourceToolkitLock.handle2Lock.iterator();
        while (iterator.hasNext()) {
            System.err.println("SharedResourceToolkitLock: Open[" + n + "]: " + iterator.next().value);
            ++n;
        }
    }
    
    public static final SharedResourceToolkitLock get(final long n) {
        SharedResourceToolkitLock sharedResourceToolkitLock;
        synchronized (SharedResourceToolkitLock.handle2Lock) {
            sharedResourceToolkitLock = (SharedResourceToolkitLock)SharedResourceToolkitLock.handle2Lock.get(n);
            if (null == sharedResourceToolkitLock) {
                sharedResourceToolkitLock = new SharedResourceToolkitLock(n);
                sharedResourceToolkitLock.refCount.incrementAndGet();
                SharedResourceToolkitLock.handle2Lock.put(n, sharedResourceToolkitLock);
                if (SharedResourceToolkitLock.DEBUG || SharedResourceToolkitLock.TRACE_LOCK) {
                    System.err.println("SharedResourceToolkitLock.get() * NEW   *: " + sharedResourceToolkitLock);
                }
            }
            else {
                sharedResourceToolkitLock.refCount.incrementAndGet();
                if (SharedResourceToolkitLock.DEBUG || SharedResourceToolkitLock.TRACE_LOCK) {
                    System.err.println("SharedResourceToolkitLock.get() * EXIST *: " + sharedResourceToolkitLock);
                }
            }
        }
        return sharedResourceToolkitLock;
    }
    
    private SharedResourceToolkitLock(final long handle) {
        this.lock = LockFactory.createRecursiveLock();
        this.handle = handle;
        this.refCount = new AtomicInteger(0);
    }
    
    @Override
    public final void lock() {
        this.lock.lock();
        if (SharedResourceToolkitLock.TRACE_LOCK) {
            System.err.println(Thread.currentThread() + " SharedResourceToolkitLock: lock() " + this.toStringImpl());
        }
    }
    
    @Override
    public final void unlock() {
        if (SharedResourceToolkitLock.TRACE_LOCK) {
            System.err.println(Thread.currentThread() + " SharedResourceToolkitLock: unlock() " + this.toStringImpl());
        }
        this.lock.unlock();
    }
    
    @Override
    public final void validateLocked() throws RuntimeException {
        this.lock.validateLocked();
    }
    
    @Override
    public final void dispose() {
        if (0 < this.refCount.get()) {
            synchronized (SharedResourceToolkitLock.handle2Lock) {
                if (0 == this.refCount.decrementAndGet()) {
                    if (SharedResourceToolkitLock.DEBUG || SharedResourceToolkitLock.TRACE_LOCK) {
                        System.err.println("SharedResourceToolkitLock.dispose() * REMOV *: " + this);
                    }
                    SharedResourceToolkitLock.handle2Lock.remove(this.handle);
                }
                else if (SharedResourceToolkitLock.DEBUG || SharedResourceToolkitLock.TRACE_LOCK) {
                    System.err.println("SharedResourceToolkitLock.dispose() * DOWN  *: " + this);
                }
            }
        }
        else if (SharedResourceToolkitLock.DEBUG || SharedResourceToolkitLock.TRACE_LOCK) {
            System.err.println("SharedResourceToolkitLock.dispose() * NULL  *: " + this);
        }
    }
    
    @Override
    public String toString() {
        return "SharedResourceToolkitLock[" + this.toStringImpl() + "]";
    }
    
    private String toStringImpl() {
        return "refCount " + this.refCount + ", handle 0x" + Long.toHexString(this.handle) + ", obj 0x" + Integer.toHexString(this.hashCode()) + ", isOwner " + this.lock.isOwner(Thread.currentThread()) + ", " + this.lock.toString();
    }
    
    static {
        (handle2Lock = new LongObjectHashMap()).setKeyNotFoundValue(null);
    }
}
