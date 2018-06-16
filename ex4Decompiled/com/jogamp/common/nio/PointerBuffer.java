// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import java.nio.LongBuffer;
import java.nio.Buffer;
import java.nio.IntBuffer;
import com.jogamp.common.os.Platform;
import java.nio.ByteBuffer;
import com.jogamp.common.util.LongObjectHashMap;

public class PointerBuffer extends AbstractBuffer<PointerBuffer>
{
    public static final int ELEMENT_SIZE;
    protected LongObjectHashMap dataMap;
    
    static PointerBuffer create(final ByteBuffer byteBuffer) {
        return Platform.is32Bit() ? new PointerBuffer(byteBuffer.asIntBuffer()) : new PointerBuffer(byteBuffer.asLongBuffer());
    }
    
    PointerBuffer(final IntBuffer intBuffer) {
        super(intBuffer, PointerBuffer.ELEMENT_SIZE, intBuffer.capacity());
        this.dataMap = null;
    }
    
    PointerBuffer(final LongBuffer longBuffer) {
        super(longBuffer, PointerBuffer.ELEMENT_SIZE, longBuffer.capacity());
        this.dataMap = null;
    }
    
    private final void validateDataMap() {
        if (null == this.dataMap) {
            (this.dataMap = new LongObjectHashMap()).setKeyNotFoundValue(null);
        }
    }
    
    public static PointerBuffer allocate(final int n) {
        if (Platform.is32Bit()) {
            return new PointerBuffer(IntBuffer.wrap(new int[n]));
        }
        return new PointerBuffer(LongBuffer.wrap(new long[n]));
    }
    
    public static PointerBuffer allocateDirect(final int n) {
        return create(Buffers.newDirectByteBuffer(PointerBuffer.ELEMENT_SIZE * n));
    }
    
    public static PointerBuffer wrap(final ByteBuffer byteBuffer) {
        return create(byteBuffer);
    }
    
    public final PointerBuffer duplicate() {
        PointerBuffer pointerBuffer;
        if (Platform.is32Bit()) {
            pointerBuffer = new PointerBuffer((IntBuffer)this.buffer);
        }
        else {
            pointerBuffer = new PointerBuffer((LongBuffer)this.buffer);
        }
        if (null != this.dataMap) {
            pointerBuffer.dataMap = (LongObjectHashMap)this.dataMap.clone();
        }
        pointerBuffer.position = this.position;
        return pointerBuffer;
    }
    
    @Override
    public final PointerBuffer put(final PointerBuffer pointerBuffer) {
        if (this.remaining() < pointerBuffer.remaining()) {
            throw new IndexOutOfBoundsException();
        }
        if (null == pointerBuffer.dataMap && null == this.dataMap) {
            while (pointerBuffer.hasRemaining()) {
                this.put(pointerBuffer.get());
            }
        }
        else {
            while (pointerBuffer.hasRemaining()) {
                final long value = pointerBuffer.get();
                this.put(value);
                if (null != pointerBuffer.dataMap) {
                    final Buffer buffer = (Buffer)pointerBuffer.dataMap.get(value);
                    if (null != buffer) {
                        this.validateDataMap();
                        this.dataMap.put(value, buffer);
                    }
                    else {
                        if (null == this.dataMap) {
                            continue;
                        }
                        this.dataMap.remove(value);
                    }
                }
                else {
                    if (null == this.dataMap) {
                        continue;
                    }
                    this.dataMap.remove(value);
                }
            }
        }
        return this;
    }
    
    @Override
    public final long get() {
        final long value = this.get(this.position);
        ++this.position;
        return value;
    }
    
    @Override
    public final long get(final int n) {
        if (0 > n || n >= this.capacity) {
            throw new IndexOutOfBoundsException();
        }
        if (Platform.is32Bit()) {
            return ((IntBuffer)this.buffer).get(n) & 0xFFFFFFFFL;
        }
        return ((LongBuffer)this.buffer).get(n);
    }
    
    public final PointerBuffer get(final long[] array, int n, int i) {
        if (array.length < n + i) {
            throw new IndexOutOfBoundsException();
        }
        if (this.remaining() < i) {
            throw new IndexOutOfBoundsException();
        }
        while (i > 0) {
            array[n++] = this.get(this.position++);
            --i;
        }
        return this;
    }
    
    @Override
    public final PointerBuffer put(final int n, final long n2) {
        if (0 > n || n >= this.capacity) {
            throw new IndexOutOfBoundsException();
        }
        if (Platform.is32Bit()) {
            ((IntBuffer)this.buffer).put(n, (int)n2);
        }
        else {
            ((LongBuffer)this.buffer).put(n, n2);
        }
        return this;
    }
    
    @Override
    public final PointerBuffer put(final long n) {
        this.put(this.position, n);
        ++this.position;
        return this;
    }
    
    public final PointerBuffer put(final long[] array, int n, int i) {
        if (array.length < n + i) {
            throw new IndexOutOfBoundsException();
        }
        if (this.remaining() < i) {
            throw new IndexOutOfBoundsException();
        }
        while (i > 0) {
            this.put(this.position++, array[n++]);
            --i;
        }
        return this;
    }
    
    public final PointerBuffer referenceBuffer(final int n, final Buffer buffer) {
        if (null == buffer) {
            throw new IllegalArgumentException("Buffer is null");
        }
        if (!Buffers.isDirect(buffer)) {
            throw new IllegalArgumentException("Buffer is not direct");
        }
        final long n2 = this.getDirectBufferAddressImpl(buffer) & (Platform.is32Bit() ? 4294967295L : -1L);
        if (0L == n2) {
            throw new RuntimeException("Couldn't determine native address of given Buffer: " + buffer);
        }
        this.validateDataMap();
        this.put(n, n2);
        this.dataMap.put(n2, buffer);
        return this;
    }
    
    public final PointerBuffer referenceBuffer(final Buffer buffer) {
        this.referenceBuffer(this.position, buffer);
        ++this.position;
        return this;
    }
    
    public final Buffer getReferencedBuffer(final int n) {
        if (null != this.dataMap) {
            return (Buffer)this.dataMap.get(this.get(n));
        }
        return null;
    }
    
    public final Buffer getReferencedBuffer() {
        final Buffer referencedBuffer = this.getReferencedBuffer(this.position);
        ++this.position;
        return referencedBuffer;
    }
    
    private native long getDirectBufferAddressImpl(final Object p0);
    
    @Override
    public String toString() {
        return "PointerBuffer:" + super.toString();
    }
    
    static {
        ELEMENT_SIZE = (Platform.is32Bit() ? 4 : 8);
        Platform.initSingleton();
    }
}
