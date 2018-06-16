// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.FloatBuffer;

public class FloatStack implements PrimitiveStack
{
    private int position;
    private float[] buffer;
    private int growSize;
    
    public FloatStack(final int n, final int growSize) {
        this.position = 0;
        this.growSize = growSize;
        this.buffer = new float[n];
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
        return "FloatStack[0..(pos " + this.position + ").." + this.buffer.length + ", remaining " + this.remaining() + "]";
    }
    
    public final float[] buffer() {
        return this.buffer;
    }
    
    private final void growIfNecessary(final int n) throws IndexOutOfBoundsException {
        if (this.position + n > this.buffer.length) {
            if (0 >= this.growSize) {
                throw new IndexOutOfBoundsException("Out of fixed stack size: " + this);
            }
            final float[] buffer = new float[this.buffer.length + this.growSize];
            System.arraycopy(this.buffer, 0, buffer, 0, this.position);
            this.buffer = buffer;
        }
    }
    
    public final float[] putOnTop(final float[] array, final int n, final int n2) throws IndexOutOfBoundsException {
        this.growIfNecessary(n2);
        System.arraycopy(array, n, this.buffer, this.position, n2);
        this.position += n2;
        return array;
    }
    
    public final FloatBuffer putOnTop(final FloatBuffer floatBuffer, final int n) throws IndexOutOfBoundsException, BufferUnderflowException {
        this.growIfNecessary(n);
        floatBuffer.get(this.buffer, this.position, n);
        this.position += n;
        return floatBuffer;
    }
    
    public final float[] getFromTop(final float[] array, final int n, final int n2) throws IndexOutOfBoundsException {
        System.arraycopy(this.buffer, this.position - n2, array, n, n2);
        this.position -= n2;
        return array;
    }
    
    public final FloatBuffer getFromTop(final FloatBuffer floatBuffer, final int n) throws IndexOutOfBoundsException, BufferOverflowException {
        floatBuffer.put(this.buffer, this.position - n, n);
        this.position -= n;
        return floatBuffer;
    }
}
