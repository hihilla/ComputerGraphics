// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import java.nio.CharBuffer;
import java.nio.ShortBuffer;
import java.nio.LongBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.DoubleBuffer;
import java.nio.ByteBuffer;

public class CachedBufferFactory
{
    public static final int DEFAULT_ALLOCATION_SIZE = 1048576;
    private final int ALLOCATION_SIZE;
    private ByteBuffer currentBuffer;
    
    private CachedBufferFactory() {
        this(1048576, 1048576);
    }
    
    private CachedBufferFactory(final int n, final int allocation_SIZE) {
        this.currentBuffer = Buffers.newDirectByteBuffer(n);
        this.ALLOCATION_SIZE = allocation_SIZE;
    }
    
    public static CachedBufferFactory create() {
        return new CachedBufferFactory();
    }
    
    public static CachedBufferFactory create(final int n) {
        return new CachedBufferFactory(n, 1048576);
    }
    
    public static CachedBufferFactory create(final int n, final boolean b) {
        return new CachedBufferFactory(n, b ? -1 : 1048576);
    }
    
    public static CachedBufferFactory create(final int n, final int n2) {
        return new CachedBufferFactory(n, n2);
    }
    
    public static CachedBufferFactory createSynchronized() {
        return new SynchronizedCachedBufferFactory();
    }
    
    public static CachedBufferFactory createSynchronized(final int n) {
        return new SynchronizedCachedBufferFactory(n, 1048576);
    }
    
    public static CachedBufferFactory createSynchronized(final int n, final boolean b) {
        return new SynchronizedCachedBufferFactory(n, b ? -1 : 1048576);
    }
    
    public static CachedBufferFactory createSynchronized(final int n, final int n2) {
        return new CachedBufferFactory(n, n2);
    }
    
    public boolean isFixed() {
        return this.ALLOCATION_SIZE == -1;
    }
    
    public int getAllocationSize() {
        return this.ALLOCATION_SIZE;
    }
    
    private void checkIfFixed() {
        if (this.ALLOCATION_SIZE == 0) {
            throw new RuntimeException("fixed size buffer factory ran out ouf bounds.");
        }
    }
    
    public void destroy() {
        if (null != this.currentBuffer) {
            this.currentBuffer.clear();
            this.currentBuffer = null;
        }
    }
    
    public ByteBuffer newDirectByteBuffer(final int n) {
        if (n > this.currentBuffer.capacity()) {
            this.checkIfFixed();
            return Buffers.newDirectByteBuffer(n);
        }
        if (n > this.currentBuffer.remaining()) {
            this.checkIfFixed();
            this.currentBuffer = Buffers.newDirectByteBuffer(this.ALLOCATION_SIZE);
        }
        this.currentBuffer.limit(this.currentBuffer.position() + n);
        final ByteBuffer order = this.currentBuffer.slice().order(this.currentBuffer.order());
        this.currentBuffer.position(this.currentBuffer.limit());
        this.currentBuffer.limit(this.currentBuffer.capacity());
        return order;
    }
    
    public ByteBuffer newDirectByteBuffer(final byte[] array, final int n, final int n2) {
        return (ByteBuffer)this.newDirectByteBuffer(n2).put(array, n, n2).rewind();
    }
    
    public ByteBuffer newDirectByteBuffer(final byte[] array, final int n) {
        return this.newDirectByteBuffer(array, n, array.length - n);
    }
    
    public ByteBuffer newDirectByteBuffer(final byte[] array) {
        return this.newDirectByteBuffer(array, 0);
    }
    
    public DoubleBuffer newDirectDoubleBuffer(final int n) {
        return this.newDirectByteBuffer(n * 8).asDoubleBuffer();
    }
    
    public DoubleBuffer newDirectDoubleBuffer(final double[] array, final int n, final int n2) {
        return (DoubleBuffer)this.newDirectDoubleBuffer(n2).put(array, n, n2).rewind();
    }
    
    public DoubleBuffer newDirectDoubleBuffer(final double[] array, final int n) {
        return this.newDirectDoubleBuffer(array, n, array.length - n);
    }
    
    public DoubleBuffer newDirectDoubleBuffer(final double[] array) {
        return this.newDirectDoubleBuffer(array, 0);
    }
    
    public FloatBuffer newDirectFloatBuffer(final int n) {
        return this.newDirectByteBuffer(n * 4).asFloatBuffer();
    }
    
    public FloatBuffer newDirectFloatBuffer(final float[] array, final int n, final int n2) {
        return (FloatBuffer)this.newDirectFloatBuffer(n2).put(array, n, n2).rewind();
    }
    
    public FloatBuffer newDirectFloatBuffer(final float[] array, final int n) {
        return this.newDirectFloatBuffer(array, n, array.length - n);
    }
    
    public FloatBuffer newDirectFloatBuffer(final float[] array) {
        return this.newDirectFloatBuffer(array, 0);
    }
    
    public IntBuffer newDirectIntBuffer(final int n) {
        return this.newDirectByteBuffer(n * 4).asIntBuffer();
    }
    
    public IntBuffer newDirectIntBuffer(final int[] array, final int n, final int n2) {
        return (IntBuffer)this.newDirectIntBuffer(n2).put(array, n, n2).rewind();
    }
    
    public IntBuffer newDirectIntBuffer(final int[] array, final int n) {
        return this.newDirectIntBuffer(array, n, array.length - n);
    }
    
    public IntBuffer newDirectIntBuffer(final int[] array) {
        return this.newDirectIntBuffer(array, 0);
    }
    
    public LongBuffer newDirectLongBuffer(final int n) {
        return this.newDirectByteBuffer(n * 8).asLongBuffer();
    }
    
    public LongBuffer newDirectLongBuffer(final long[] array, final int n, final int n2) {
        return (LongBuffer)this.newDirectLongBuffer(n2).put(array, n, n2).rewind();
    }
    
    public LongBuffer newDirectLongBuffer(final long[] array, final int n) {
        return this.newDirectLongBuffer(array, n, array.length - n);
    }
    
    public LongBuffer newDirectLongBuffer(final long[] array) {
        return this.newDirectLongBuffer(array, 0);
    }
    
    public ShortBuffer newDirectShortBuffer(final int n) {
        return this.newDirectByteBuffer(n * 2).asShortBuffer();
    }
    
    public ShortBuffer newDirectShortBuffer(final short[] array, final int n, final int n2) {
        return (ShortBuffer)this.newDirectShortBuffer(n2).put(array, n, n2).rewind();
    }
    
    public ShortBuffer newDirectShortBuffer(final short[] array, final int n) {
        return this.newDirectShortBuffer(array, n, array.length - n);
    }
    
    public ShortBuffer newDirectShortBuffer(final short[] array) {
        return this.newDirectShortBuffer(array, 0);
    }
    
    public CharBuffer newDirectCharBuffer(final int n) {
        return this.newDirectByteBuffer(n * 2).asCharBuffer();
    }
    
    public CharBuffer newDirectCharBuffer(final char[] array, final int n, final int n2) {
        return (CharBuffer)this.newDirectCharBuffer(n2).put(array, n, n2).rewind();
    }
    
    public CharBuffer newDirectCharBuffer(final char[] array, final int n) {
        return this.newDirectCharBuffer(array, n, array.length - n);
    }
    
    public CharBuffer newDirectCharBuffer(final char[] array) {
        return this.newDirectCharBuffer(array, 0);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final CachedBufferFactory cachedBufferFactory = (CachedBufferFactory)o;
        return this.ALLOCATION_SIZE == cachedBufferFactory.ALLOCATION_SIZE && (this.currentBuffer == cachedBufferFactory.currentBuffer || (this.currentBuffer != null && this.currentBuffer.equals(cachedBufferFactory.currentBuffer)));
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[static:" + this.isFixed() + " alloc size:" + this.getAllocationSize() + "]";
    }
    
    private static class SynchronizedCachedBufferFactory extends CachedBufferFactory
    {
        private SynchronizedCachedBufferFactory() {
            super(null);
        }
        
        private SynchronizedCachedBufferFactory(final int n, final int n2) {
            super(n, n2, null);
        }
        
        @Override
        public synchronized ByteBuffer newDirectByteBuffer(final int n) {
            return super.newDirectByteBuffer(n);
        }
    }
}
