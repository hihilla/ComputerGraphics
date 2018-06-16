// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import com.jogamp.common.os.Platform;
import java.nio.Buffer;

public abstract class AbstractBuffer<B extends AbstractBuffer> implements NativeBuffer<B>
{
    protected final Buffer buffer;
    protected final int elementSize;
    protected final int capacity;
    protected int position;
    
    protected AbstractBuffer(final Buffer buffer, final int elementSize, final int capacity) {
        this.buffer = buffer;
        this.elementSize = elementSize;
        this.capacity = capacity;
        this.position = 0;
    }
    
    @Override
    public final int elementSize() {
        return this.elementSize;
    }
    
    @Override
    public final int limit() {
        return this.capacity;
    }
    
    @Override
    public final int capacity() {
        return this.capacity;
    }
    
    @Override
    public final int position() {
        return this.position;
    }
    
    @Override
    public final B position(final int position) {
        if (0 > position || position >= this.capacity) {
            throw new IndexOutOfBoundsException("Sorry to interrupt, but the position " + position + " was out of bounds. " + "My capacity is " + this.capacity() + ".");
        }
        this.position = position;
        return (B)this;
    }
    
    @Override
    public final int remaining() {
        return this.capacity - this.position;
    }
    
    @Override
    public final boolean hasRemaining() {
        return this.position < this.capacity;
    }
    
    @Override
    public final B rewind() {
        this.position = 0;
        return (B)this;
    }
    
    @Override
    public final Buffer getBuffer() {
        return this.buffer;
    }
    
    @Override
    public final boolean isDirect() {
        return this.buffer.isDirect();
    }
    
    @Override
    public final boolean hasArray() {
        return this.buffer.hasArray();
    }
    
    @Override
    public final int arrayOffset() {
        if (this.hasArray()) {
            return this.buffer.arrayOffset();
        }
        return 0;
    }
    
    @Override
    public Object array() throws UnsupportedOperationException {
        return this.buffer.array();
    }
    
    @Override
    public String toString() {
        return "AbstractBuffer[direct " + this.isDirect() + ", hasArray " + this.hasArray() + ", capacity " + this.capacity + ", position " + this.position + ", elementSize " + this.elementSize + ", buffer[capacity " + this.buffer.capacity() + ", lim " + this.buffer.limit() + ", pos " + this.buffer.position() + "]]";
    }
    
    static {
        Platform.initSingleton();
    }
}
