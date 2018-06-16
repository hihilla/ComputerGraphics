// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.nativewindow.ToolkitLock;

public class ResourceToolkitLock implements ToolkitLock
{
    private final RecursiveLock lock;
    
    public static final ResourceToolkitLock create() {
        return new ResourceToolkitLock();
    }
    
    private ResourceToolkitLock() {
        this.lock = LockFactory.createRecursiveLock();
    }
    
    @Override
    public final void lock() {
        this.lock.lock();
        if (ResourceToolkitLock.TRACE_LOCK) {
            System.err.println(Thread.currentThread() + " ResourceToolkitLock: lock() " + this.toStringImpl());
        }
    }
    
    @Override
    public final void unlock() {
        if (ResourceToolkitLock.TRACE_LOCK) {
            System.err.println(Thread.currentThread() + " ResourceToolkitLock: unlock() " + this.toStringImpl());
        }
        this.lock.unlock();
    }
    
    @Override
    public final void validateLocked() throws RuntimeException {
        this.lock.validateLocked();
    }
    
    @Override
    public final void dispose() {
    }
    
    @Override
    public String toString() {
        return "ResourceToolkitLock[" + this.toStringImpl() + "]";
    }
    
    private String toStringImpl() {
        return "obj 0x" + Integer.toHexString(this.hashCode()) + ", isOwner " + this.lock.isOwner(Thread.currentThread()) + ", " + this.lock.toString();
    }
}
