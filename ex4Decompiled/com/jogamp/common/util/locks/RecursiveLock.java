// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.locks;

public interface RecursiveLock extends ThreadLock
{
    int getHoldCount();
    
    int getQueueLength();
}
