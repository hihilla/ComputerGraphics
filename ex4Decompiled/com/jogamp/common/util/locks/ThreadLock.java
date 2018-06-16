// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.locks;

public interface ThreadLock extends Lock
{
    boolean isLockedByOtherThread();
    
    boolean isOwner(final Thread p0);
    
    Thread getOwner();
    
    void validateLocked() throws RuntimeException;
    
    void unlock(final Runnable p0);
}
