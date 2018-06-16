// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.InputStream;

public class ByteBufferInputStream extends InputStream
{
    private final ByteBuffer buf;
    private int mark;
    
    public ByteBufferInputStream(final ByteBuffer buf) {
        this.buf = buf;
        this.mark = -1;
    }
    
    @Override
    public final int available() {
        return this.buf.remaining();
    }
    
    @Override
    public final boolean markSupported() {
        return true;
    }
    
    @Override
    public final synchronized void mark(final int n) {
        this.mark = this.buf.position();
    }
    
    @Override
    public final synchronized void reset() throws IOException {
        if (this.mark == -1) {
            throw new IOException();
        }
        this.buf.position(this.mark);
    }
    
    @Override
    public final long skip(final long n) throws IOException {
        if (0L > n) {
            return 0L;
        }
        final int n2 = (int)Math.min(this.buf.remaining(), n);
        this.buf.position(this.buf.position() + n2);
        return n2;
    }
    
    @Override
    public final int read() {
        if (!this.buf.hasRemaining()) {
            return -1;
        }
        return this.buf.get() & 0xFF;
    }
    
    @Override
    public final int read(final byte[] array, final int n, final int n2) {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n > array.length || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException("offset " + n + ", length " + n2 + ", b.length " + array.length);
        }
        if (0 == n2) {
            return 0;
        }
        final int remaining = this.buf.remaining();
        if (remaining == 0) {
            return -1;
        }
        final int min = Math.min(remaining, n2);
        if (this.buf.hasArray()) {
            System.arraycopy(this.buf.array(), this.buf.arrayOffset() + this.buf.position(), array, n, min);
            this.buf.position(this.buf.position() + min);
        }
        else {
            this.buf.get(array, n, min);
        }
        return min;
    }
    
    public final int read(final ByteBuffer byteBuffer, final int n) {
        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n > byteBuffer.remaining()) {
            throw new IndexOutOfBoundsException("length " + n + ", b " + byteBuffer);
        }
        if (0 == n) {
            return 0;
        }
        final int remaining = this.buf.remaining();
        if (remaining == 0) {
            return -1;
        }
        final int min = Math.min(remaining, n);
        if (this.buf.hasArray() && byteBuffer.hasArray()) {
            System.arraycopy(this.buf.array(), this.buf.arrayOffset() + this.buf.position(), byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), min);
            this.buf.position(this.buf.position() + min);
            byteBuffer.position(byteBuffer.position() + min);
        }
        else if (min == remaining) {
            byteBuffer.put(this.buf);
        }
        else {
            final int limit = this.buf.limit();
            this.buf.limit(min);
            try {
                byteBuffer.put(this.buf);
            }
            finally {
                this.buf.limit(limit);
            }
        }
        return min;
    }
    
    public final ByteBuffer getBuffer() {
        return this.buf;
    }
}
