// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.IntBuffer;

public class IntegerStack implements PrimitiveStack
{
    private int position;
    private int[] buffer;
    private int growSize;
    
    public IntegerStack(final int n, final int growSize) {
        this.position = 0;
        this.growSize = growSize;
        this.buffer = new int[n];
    }
    
    @Override
    public final int capacity() {
        return this.buffer.length;
    }
    
    @Override
    public final int position() {
        return this.position;
    }
    
    @Override
    public final void position(final int position) throws IndexOutOfBoundsException {
        if (0 > this.position || this.position >= this.buffer.length) {
            throw new IndexOutOfBoundsException("Invalid new position " + position + ", " + this.toString());
        }
        this.position = position;
    }
    
    @Override
    public final int remaining() {
        return this.buffer.length - this.position;
    }
    
    @Override
    public final int getGrowSize() {
        return this.growSize;
    }
    
    @Override
    public final void setGrowSize(final int growSize) {
        this.growSize = growSize;
    }
    
    @Override
    public final String toString() {
        return "IntegerStack[0..(pos " + this.position + ").." + this.buffer.length + ", remaining " + this.remaining() + "]";
    }
    
    public final int[] buffer() {
        return this.buffer;
    }
    
    private final void growIfNecessary(final int n) throws IndexOutOfBoundsException {
        if (this.position + n > this.buffer.length) {
            if (0 >= this.growSize) {
                throw new IndexOutOfBoundsException("Out of fixed stack size: " + this);
            }
            final int[] buffer = new int[this.buffer.length + this.growSize];
            System.arraycopy(this.buffer, 0, buffer, 0, this.position);
            this.buffer = buffer;
        }
    }
    
    public final int[] putOnTop(final int[] array, final int n, final int n2) throws IndexOutOfBoundsException {
        this.growIfNecessary(n2);
        System.arraycopy(array, n, this.buffer, this.position, n2);
        this.position += n2;
        return array;
    }
    
    public final IntBuffer putOnTop(final IntBuffer intBuffer, final int n) throws IndexOutOfBoundsException, BufferUnderflowException {
        this.growIfNecessary(n);
        intBuffer.get(this.buffer, this.position, n);
        this.position += n;
        return intBuffer;
    }
    
    public final int[] getFromTop(final int[] array, final int n, final int n2) throws IndexOutOfBoundsException {
        System.arraycopy(this.buffer, this.position - n2, array, n, n2);
        this.position -= n2;
        return array;
    }
    
    public final IntBuffer getFromTop(final IntBuffer intBuffer, final int n) throws IndexOutOfBoundsException, BufferOverflowException {
        intBuffer.put(this.buffer, this.position - n, n);
        this.position -= n;
        return intBuffer;
    }
}
