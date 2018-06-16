// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.ToolkitLock;

public class NullToolkitLock implements ToolkitLock
{
    @Override
    public final void lock() {
        if (NullToolkitLock.TRACE_LOCK) {
            System.err.println(Thread.currentThread() + " NullToolkitLock: lock() " + this.toStringImpl());
        }
    }
    
    @Override
    public final void unlock() {
        if (NullToolkitLock.TRACE_LOCK) {
            System.err.println(Thread.currentThread() + " NullToolkitLock: unlock() " + this.toStringImpl());
        }
    }
    
    @Override
    public final void validateLocked() throws RuntimeException {
        if (NativeWindowFactory.requiresToolkitLock()) {
            throw new RuntimeException("NullToolkitLock does not lock, but locking is required.");
        }
    }
    
    @Override
    public final void dispose() {
    }
    
    @Override
    public String toString() {
        return "NullToolkitLock[" + this.toStringImpl() + "]";
    }
    
    private String toStringImpl() {
        return "obj 0x" + Integer.toHexString(this.hashCode());
    }
}
