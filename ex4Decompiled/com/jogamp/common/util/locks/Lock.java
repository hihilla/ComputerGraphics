// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.locks;

import com.jogamp.common.util.PropertyAccess;
import jogamp.common.Debug;

public interface Lock
{
    public static final boolean DEBUG = Debug.debug("Lock");
    public static final boolean TRACE_LOCK = PropertyAccess.isPropertyDefined("jogamp.debug.Lock.TraceLock", true);
    public static final long DEFAULT_TIMEOUT = 5000L;
    public static final long TIMEOUT = PropertyAccess.getLongProperty("jogamp.common.utils.locks.Lock.timeout", true, 5000L);
    
    void lock() throws RuntimeException;
    
    boolean tryLock(final long p0) throws InterruptedException;
    
    void unlock() throws RuntimeException;
    
    boolean isLocked();
}
