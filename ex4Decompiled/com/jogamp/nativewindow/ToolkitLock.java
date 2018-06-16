// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import com.jogamp.common.util.PropertyAccess;
import jogamp.nativewindow.Debug;

public interface ToolkitLock
{
    public static final boolean DEBUG = Debug.debug("ToolkitLock");
    public static final boolean TRACE_LOCK = PropertyAccess.isPropertyDefined("nativewindow.debug.ToolkitLock.TraceLock", true);
    
    void lock();
    
    void unlock();
    
    void validateLocked() throws RuntimeException;
    
    void dispose();
}
