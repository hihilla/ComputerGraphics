// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.util;

public interface EDTUtil
{
    public static final long defaultEDTPollPeriod = 10L;
    
    long getPollPeriod();
    
    void setPollPeriod(final long p0);
    
    void start() throws IllegalStateException;
    
    boolean isCurrentThreadEDT();
    
    boolean isCurrentThreadNEDT();
    
    boolean isCurrentThreadEDTorNEDT();
    
    boolean isRunning();
    
    boolean invokeStop(final boolean p0, final Runnable p1);
    
    boolean invoke(final boolean p0, final Runnable p1);
    
    boolean waitUntilIdle();
    
    boolean waitUntilStopped();
}
