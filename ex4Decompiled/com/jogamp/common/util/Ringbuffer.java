// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.io.PrintStream;

public interface Ringbuffer<T>
{
    String toString();
    
    void dump(final PrintStream p0, final String p1);
    
    int capacity();
    
    void clear();
    
    void resetFull(final T[] p0) throws IllegalArgumentException;
    
    int size();
    
    int getFreeSlots();
    
    boolean isEmpty();
    
    boolean isFull();
    
    T get();
    
    T getBlocking() throws InterruptedException;
    
    T peek();
    
    T peekBlocking() throws InterruptedException;
    
    boolean put(final T p0);
    
    void putBlocking(final T p0) throws InterruptedException;
    
    boolean putSame(final boolean p0) throws InterruptedException;
    
    void waitForFreeSlots(final int p0) throws InterruptedException;
    
    void growEmptyBuffer(final T[] p0) throws IllegalStateException, IllegalArgumentException;
    
    void growFullBuffer(final int p0) throws IllegalStateException, IllegalArgumentException;
}
