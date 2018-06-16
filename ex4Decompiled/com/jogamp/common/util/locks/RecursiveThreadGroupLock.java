// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.locks;

public interface RecursiveThreadGroupLock extends RecursiveLock
{
    boolean isOriginalOwner();
    
    boolean isOriginalOwner(final Thread p0);
    
    void addOwner(final Thread p0) throws RuntimeException, IllegalArgumentException;
    
    void removeOwner(final Thread p0) throws RuntimeException, IllegalArgumentException;
    
    void unlock() throws RuntimeException;
    
    void unlock(final Runnable p0);
}
