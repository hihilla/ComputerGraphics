// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.nativewindow.ToolkitLock;

public class GlobalToolkitLock implements ToolkitLock
{
    private static final RecursiveLock globalLock;
    private static GlobalToolkitLock singleton;
    
    public static final GlobalToolkitLock getSingleton() {
        return GlobalToolkitLock.singleton;
    }
    
    @Override
    public final void lock() {
        GlobalToolkitLock.globalLock.lock();
        if (GlobalToolkitLock.TRACE_LOCK) {
            System.err.println(Thread.currentThread() + " GlobalToolkitLock: lock() " + this.toStringImpl());
        }
    }
    
    @Override
    public final void unlock() {
        if (GlobalToolkitLock.TRACE_LOCK) {
            System.err.println(Thread.currentThread() + " GlobalToolkitLock: unlock() " + this.toStringImpl());
        }
        GlobalToolkitLock.globalLock.unlock();
    }
    
    @Override
    public final void validateLocked() throws RuntimeException {
        GlobalToolkitLock.globalLock.validateLocked();
    }
    
    @Override
    public final void dispose() {
    }
    
    @Override
    public String toString() {
        return "GlobalToolkitLock[" + this.toStringImpl() + "]";
    }
    
    private String toStringImpl() {
        return "obj 0x" + Integer.toHexString(this.hashCode()) + ", isOwner " + GlobalToolkitLock.globalLock.isOwner(Thread.currentThread()) + ", " + GlobalToolkitLock.globalLock.toString();
    }
    
    static {
        globalLock = LockFactory.createRecursiveLock();
        GlobalToolkitLock.singleton = new GlobalToolkitLock();
    }
}
