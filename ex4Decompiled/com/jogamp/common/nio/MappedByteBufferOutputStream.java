// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import java.nio.ByteBuffer;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.io.OutputStream;

public class MappedByteBufferOutputStream extends OutputStream
{
    private final MappedByteBufferInputStream parent;
    
    MappedByteBufferOutputStream(final MappedByteBufferInputStream parent, final MappedByteBufferInputStream.FileResizeOp fileResizeOp) throws IOException {
        if (FileChannel.MapMode.READ_ONLY == parent.getMapMode()) {
            throw new IOException("FileChannel map-mode is read-only");
        }
        (this.parent = parent).setFileResizeOp(fileResizeOp);
    }
    
    public MappedByteBufferOutputStream(final FileChannel fileChannel, final FileChannel.MapMode mapMode, final MappedByteBufferInputStream.CacheMode cacheMode, final int n, final MappedByteBufferInputStream.FileResizeOp fileResizeOp) throws IOException {
        this(new MappedByteBufferInputStream(fileChannel, mapMode, cacheMode, n, fileChannel.size(), 0), fileResizeOp);
    }
    
    public final synchronized void setSynchronous(final boolean synchronous) {
        this.parent.setSynchronous(synchronous);
    }
    
    public final synchronized boolean getSynchronous() {
        return this.parent.getSynchronous();
    }
    
    public final synchronized void setLength(final long length) throws IOException {
        this.parent.setLength(length);
    }
    
    public final synchronized void notifyLengthChange(final long n) throws IOException {
        this.parent.notifyLengthChange(n);
    }
    
    public final synchronized long length() {
        return this.parent.length();
    }
    
    public final synchronized long remaining() throws IOException {
        return this.parent.remaining();
    }
    
    public final synchronized long position() throws IOException {
        return this.parent.position();
    }
    
    public final synchronized MappedByteBufferInputStream position(final long n) throws IOException {
        return this.parent.position(n);
    }
    
    public final synchronized long skip(final long n) throws IOException {
        return this.parent.skip(n);
    }
    
    @Override
    public final synchronized void flush() throws IOException {
        this.parent.flush(true);
    }
    
    public final synchronized void flush(final boolean b) throws IOException {
        this.parent.flush(b);
    }
    
    @Override
    public final synchronized void close() throws IOException {
        this.parent.close();
    }
    
    @Override
    public final synchronized void write(final int n) throws IOException {
        this.parent.checkOpen();
        if (this.parent.remaining() < 1L) {
            this.parent.setLength(this.parent.length() + 1L);
        }
        ByteBuffer byteBuffer = this.parent.currentSlice();
        if (0 == byteBuffer.remaining() && null == (byteBuffer = this.parent.nextSlice())) {
            if (MappedByteBufferInputStream.DEBUG) {
                System.err.println("EOT write: " + this.parent.currentSlice());
                this.parent.dbgDump("EOT write:", System.err);
            }
            throw new IOException("EOT");
        }
        byteBuffer.put((byte)(n & 0xFF));
        if (null != byteBuffer) {
            this.parent.syncSlice(byteBuffer);
        }
    }
    
    @Override
    public final synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        this.parent.checkOpen();
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n > array.length || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException("offset " + n + ", length " + n2 + ", b.length " + array.length);
        }
        if (0 == n2) {
            return;
        }
        final long remaining = this.parent.remaining();
        if (remaining < n2) {
            this.parent.setLength(this.parent.length() + n2 - remaining);
        }
        int i = 0;
        ByteBuffer byteBuffer = null;
        while (i < n2) {
            byteBuffer = this.parent.currentSlice();
            int n3 = byteBuffer.remaining();
            if (n3 == 0) {
                if (null == (byteBuffer = this.parent.nextSlice())) {
                    if (MappedByteBufferInputStream.DEBUG) {
                        System.err.println("EOT write: offset " + n + ", length " + n2 + ", b.length " + array.length);
                        System.err.println("EOT write: written " + i + " / " + n2 + ", currRem " + n3);
                        System.err.println("EOT write: " + this.parent.currentSlice());
                        this.parent.dbgDump("EOT write:", System.err);
                    }
                    throw new InternalError("EOT");
                }
                n3 = byteBuffer.remaining();
            }
            final int min = Math.min(n2 - i, n3);
            byteBuffer.put(array, n + i, min);
            i += min;
        }
        if (null != byteBuffer) {
            this.parent.syncSlice(byteBuffer);
        }
    }
    
    public final synchronized void write(final ByteBuffer byteBuffer, final int n) throws IOException {
        this.parent.checkOpen();
        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n > byteBuffer.remaining()) {
            throw new IndexOutOfBoundsException("length " + n + ", b " + byteBuffer);
        }
        if (0 == n) {
            return;
        }
        final long remaining = this.parent.remaining();
        if (remaining < n) {
            this.parent.setLength(this.parent.length() + n - remaining);
        }
        int i = 0;
        ByteBuffer byteBuffer2 = null;
        while (i < n) {
            byteBuffer2 = this.parent.currentSlice();
            int n2 = byteBuffer2.remaining();
            if (n2 == 0) {
                if (null == (byteBuffer2 = this.parent.nextSlice())) {
                    if (MappedByteBufferInputStream.DEBUG) {
                        System.err.println("EOT write: length " + n + ", b " + byteBuffer);
                        System.err.println("EOT write: written " + i + " / " + n + ", currRem " + n2);
                        System.err.println("EOT write: " + this.parent.currentSlice());
                        this.parent.dbgDump("EOT write:", System.err);
                    }
                    throw new InternalError("EOT");
                }
                n2 = byteBuffer2.remaining();
            }
            final int min = Math.min(n - i, n2);
            if (byteBuffer2.hasArray() && byteBuffer.hasArray()) {
                System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer2.array(), byteBuffer2.arrayOffset() + byteBuffer2.position(), min);
                byteBuffer.position(byteBuffer.position() + min);
                byteBuffer2.position(byteBuffer2.position() + min);
            }
            else if (min == n2) {
                byteBuffer2.put(byteBuffer);
            }
            else {
                final int limit = byteBuffer.limit();
                byteBuffer.limit(min);
                try {
                    byteBuffer2.put(byteBuffer);
                }
                finally {
                    byteBuffer.limit(limit);
                }
            }
            i += min;
        }
        if (null != byteBuffer2) {
            this.parent.syncSlice(byteBuffer2);
        }
    }
    
    public final synchronized void write(final MappedByteBufferInputStream mappedByteBufferInputStream, final long n) throws IOException {
        this.parent.checkOpen();
        if (mappedByteBufferInputStream == null) {
            throw new NullPointerException();
        }
        if (n < 0L || n > mappedByteBufferInputStream.remaining()) {
            throw new IndexOutOfBoundsException("length " + n + ", b " + mappedByteBufferInputStream);
        }
        if (0L == n) {
            return;
        }
        final long remaining = this.parent.remaining();
        if (remaining < n) {
            this.parent.setLength(this.parent.length() + n - remaining);
        }
        long n2 = 0L;
        ByteBuffer byteBuffer = null;
        while (n2 < n) {
            byteBuffer = this.parent.currentSlice();
            int n3 = byteBuffer.remaining();
            if (n3 == 0) {
                if (null == (byteBuffer = this.parent.nextSlice())) {
                    if (MappedByteBufferInputStream.DEBUG) {
                        System.err.println("EOT write: length " + n + ", b " + mappedByteBufferInputStream);
                        System.err.println("EOT write: written " + n2 + " / " + n + ", currRem " + n3);
                        System.err.println("EOT write: " + this.parent.currentSlice());
                        this.parent.dbgDump("EOT write:", System.err);
                    }
                    throw new InternalError("EOT");
                }
                n3 = byteBuffer.remaining();
            }
            final int read = mappedByteBufferInputStream.read(byteBuffer, (int)Math.min(n - n2, n3));
            if (0 > read) {
                throw new InternalError("Unexpected InputStream EOT");
            }
            n2 += read;
        }
        if (null != byteBuffer) {
            this.parent.syncSlice(byteBuffer);
        }
    }
}
