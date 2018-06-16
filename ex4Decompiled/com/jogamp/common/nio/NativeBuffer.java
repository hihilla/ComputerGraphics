// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import java.nio.Buffer;

public interface NativeBuffer<B extends NativeBuffer>
{
    int elementSize();
    
    int limit();
    
    int capacity();
    
    int position();
    
    B position(final int p0);
    
    int remaining();
    
    boolean hasRemaining();
    
    boolean hasArray();
    
    int arrayOffset();
    
    Object array() throws UnsupportedOperationException;
    
    Buffer getBuffer();
    
    boolean isDirect();
    
    B rewind();
    
    B put(final int p0, final long p1);
    
    B put(final long p0);
    
    B put(final B p0);
    
    long get();
    
    long get(final int p0);
}
