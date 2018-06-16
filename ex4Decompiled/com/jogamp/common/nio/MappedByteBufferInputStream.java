// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import jogamp.common.Debug;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.nio.MappedByteBuffer;
import com.jogamp.common.os.Platform;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.InputStream;

public class MappedByteBufferInputStream extends InputStream
{
    private static final FileResizeOp NoFileResize;
    public static final int DEFAULT_SLICE_SHIFT;
    static final boolean DEBUG;
    private final int sliceShift;
    private final FileChannel fc;
    private final FileChannel.MapMode mmode;
    private FileResizeOp fileResizeOp;
    private int sliceCount;
    private ByteBuffer[] slices;
    private WeakReference<ByteBuffer>[] slices2GC;
    private long totalSize;
    private int slicesEntries;
    private int slices2GCEntries;
    private boolean synchronous;
    private int refCount;
    private Method mbbCleaner;
    private Method cClean;
    private boolean cleanerInit;
    private boolean hasCleaner;
    private CacheMode cmode;
    private int sliceIdx;
    private long mark;
    
    final void dbgDump(final String s, final PrintStream printStream) {
        int n = 0;
        for (int i = 0; i < this.sliceCount; ++i) {
            if (null != this.slices[i]) {
                ++n;
            }
        }
        int n2 = 0;
        int n3 = 0;
        for (int j = 0; j < this.sliceCount; ++j) {
            final WeakReference<ByteBuffer> weakReference = this.slices2GC[j];
            if (null != weakReference) {
                ++n2;
                if (null != weakReference.get()) {
                    ++n3;
                }
            }
        }
        long size = 0L;
        long position = 0L;
        long n4 = 0L;
        try {
            size = this.fc.size();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (0 < this.refCount) {
            try {
                position = this.position();
                n4 = this.totalSize - position;
            }
            catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        final int n5 = (null != this.slices) ? this.slices.length : 0;
        printStream.println(s + " refCount " + this.refCount + ", fcSize " + size + ", totalSize " + this.totalSize);
        printStream.println(s + " position " + position + ", remaining " + n4);
        printStream.println(s + " mmode " + this.mmode + ", cmode " + this.cmode + ", fileResizeOp " + this.fileResizeOp);
        printStream.println(s + " slice " + this.sliceIdx + " / " + this.sliceCount + " (" + n5 + "), synchronous " + this.synchronous);
        printStream.println(s + "   mapped   " + this.slicesEntries + " / " + n);
        printStream.println(s + "   GC-queue " + this.slices2GCEntries + " / " + n2 + " (alive " + n3 + ")");
        printStream.println(s + " sliceShift " + this.sliceShift + " -> " + (1L << this.sliceShift));
    }
    
    MappedByteBufferInputStream(final FileChannel fc, final FileChannel.MapMode mmode, final CacheMode cmode, final int sliceShift, final long n, final int sliceIdx) throws IOException {
        this.fileResizeOp = MappedByteBufferInputStream.NoFileResize;
        this.sliceShift = sliceShift;
        this.fc = fc;
        this.mmode = mmode;
        if (0L > n) {
            throw new IllegalArgumentException("Negative size " + n);
        }
        this.totalSize = -1L;
        this.sliceCount = 0;
        this.notifyLengthChange(n);
        this.refCount = 1;
        this.cleanerInit = false;
        this.hasCleaner = false;
        this.cmode = cmode;
        this.sliceIdx = sliceIdx;
        this.mark = -1L;
        this.currentSlice().position(0);
    }
    
    public MappedByteBufferInputStream(final FileChannel fileChannel, final FileChannel.MapMode mapMode, final CacheMode cacheMode, final int n) throws IOException {
        this(fileChannel, mapMode, cacheMode, n, fileChannel.size(), 0);
    }
    
    public MappedByteBufferInputStream(final FileChannel fileChannel, final FileChannel.MapMode mapMode, final CacheMode cacheMode) throws IOException {
        this(fileChannel, mapMode, cacheMode, MappedByteBufferInputStream.DEFAULT_SLICE_SHIFT);
    }
    
    public MappedByteBufferInputStream(final FileChannel fileChannel) throws IOException {
        this(fileChannel, FileChannel.MapMode.READ_ONLY, CacheMode.FLUSH_PRE_HARD, MappedByteBufferInputStream.DEFAULT_SLICE_SHIFT);
    }
    
    public final synchronized void setSynchronous(final boolean synchronous) {
        this.synchronous = synchronous;
    }
    
    public final synchronized boolean getSynchronous() {
        return this.synchronous;
    }
    
    final synchronized void checkOpen() throws IOException {
        if (0 == this.refCount) {
            throw new IOException("stream closed");
        }
    }
    
    @Override
    public final synchronized void close() throws IOException {
        if (0 < this.refCount) {
            --this.refCount;
            if (0 == this.refCount) {
                try {
                    this.cleanAllSlices(true);
                }
                finally {
                    this.flushImpl(true, false);
                    this.fc.close();
                    this.mark = -1L;
                    this.sliceIdx = -1;
                    super.close();
                }
            }
        }
    }
    
    final FileChannel.MapMode getMapMode() {
        return this.mmode;
    }
    
    public final synchronized void setFileResizeOp(final FileResizeOp fileResizeOp) throws IllegalStateException {
        if (MappedByteBufferInputStream.NoFileResize != this.fileResizeOp && this.fileResizeOp != fileResizeOp) {
            throw new IllegalStateException("FileResizeOp already set, this value differs");
        }
        this.fileResizeOp = ((null != fileResizeOp) ? fileResizeOp : MappedByteBufferInputStream.NoFileResize);
    }
    
    public final synchronized void setLength(final long length) throws IOException {
        long position;
        if (0L != length && this.totalSize != length) {
            position = this.position();
        }
        else {
            position = -1L;
        }
        if (this.fc.size() != length) {
            if (Platform.OSType.WINDOWS == Platform.getOSType()) {
                this.cleanAllSlices(this.synchronous);
            }
            this.fileResizeOp.setLength(length);
            if (this.synchronous) {
                this.flushImpl(true, false);
            }
        }
        this.notifyLengthChangeImpl(length, position);
    }
    
    public final synchronized void notifyLengthChange(final long n) throws IOException {
        this.notifyLengthChangeImpl(n, -1L);
    }
    
    private final synchronized void notifyLengthChangeImpl(final long totalSize, final long n) throws IOException {
        if (this.totalSize == totalSize) {
            return;
        }
        if (0L == totalSize) {
            this.cleanAllSlices(this.synchronous);
            this.slices2GC = (WeakReference<ByteBuffer>[])new WeakReference[1];
            (this.slices = new ByteBuffer[1])[0] = ByteBuffer.allocate(0);
            this.sliceCount = 0;
            this.totalSize = 0L;
            this.mark = -1L;
            this.sliceIdx = 0;
        }
        else {
            final long n2 = (0L <= n) ? n : this.position();
            final long n3 = 1L << this.sliceShift;
            final int sliceCount = (int)((totalSize + (n3 - 1L)) / n3);
            final WeakReference[] slices2GC = new WeakReference[sliceCount];
            final ByteBuffer[] slices = new ByteBuffer[sliceCount];
            final int min = Math.min(sliceCount, this.sliceCount - 1);
            if (0 <= min) {
                if (0 < min) {
                    System.arraycopy(this.slices2GC, 0, slices2GC, 0, min);
                    System.arraycopy(this.slices, 0, slices, 0, min);
                }
                for (int i = min; i < this.sliceCount; ++i) {
                    this.cleanSlice(i, this.synchronous);
                }
            }
            this.slices2GC = (WeakReference<ByteBuffer>[])slices2GC;
            this.slices = slices;
            this.sliceCount = sliceCount;
            this.totalSize = totalSize;
            if (totalSize < this.mark) {
                this.mark = -1L;
            }
            this.position2(Math.min(n2, totalSize));
        }
    }
    
    public final synchronized void flush(final boolean b) throws IOException {
        this.checkOpen();
        this.flushImpl(b, true);
    }
    
    private final synchronized void flushImpl(final boolean b, final boolean b2) throws IOException {
        if (FileChannel.MapMode.READ_ONLY != this.mmode) {
            if (b2 && FileChannel.MapMode.READ_WRITE == this.mmode) {
                for (int i = 0; i < this.sliceCount; ++i) {
                    this.syncSlice(this.slices[i], true);
                }
                for (int j = 0; j < this.sliceCount; ++j) {
                    final WeakReference<ByteBuffer> weakReference = this.slices2GC[j];
                    if (null != weakReference) {
                        this.syncSlice(weakReference.get(), true);
                    }
                }
            }
            this.fc.force(b);
        }
    }
    
    public final synchronized MappedByteBufferOutputStream getOutputStream(final FileResizeOp fileResizeOp) throws IllegalStateException, IOException {
        this.checkOpen();
        final MappedByteBufferOutputStream mappedByteBufferOutputStream = new MappedByteBufferOutputStream(this, fileResizeOp);
        ++this.refCount;
        return mappedByteBufferOutputStream;
    }
    
    public final synchronized ByteBuffer currentSlice() throws IOException {
        final ByteBuffer byteBuffer = this.slices[this.sliceIdx];
        if (null != byteBuffer) {
            return byteBuffer;
        }
        if (CacheMode.FLUSH_PRE_SOFT == this.cmode) {
            final WeakReference<ByteBuffer> weakReference = this.slices2GC[this.sliceIdx];
            if (null != weakReference) {
                final ByteBuffer byteBuffer2 = weakReference.get();
                this.slices2GC[this.sliceIdx] = null;
                --this.slices2GCEntries;
                if (null != byteBuffer2) {
                    this.slices[this.sliceIdx] = byteBuffer2;
                    ++this.slicesEntries;
                    return byteBuffer2;
                }
            }
        }
        final long n = this.sliceIdx << this.sliceShift;
        final MappedByteBuffer map = this.fc.map(this.mmode, n, Math.min(1L << this.sliceShift, this.totalSize - n));
        this.slices[this.sliceIdx] = map;
        ++this.slicesEntries;
        return map;
    }
    
    public final synchronized ByteBuffer nextSlice() throws IOException {
        if (this.sliceIdx < this.sliceCount - 1) {
            this.flushSlice(this.sliceIdx, this.synchronous);
            ++this.sliceIdx;
            final ByteBuffer currentSlice = this.currentSlice();
            currentSlice.position(0);
            return currentSlice;
        }
        return null;
    }
    
    synchronized void syncSlice(final ByteBuffer byteBuffer) throws IOException {
        this.syncSlice(byteBuffer, this.synchronous);
    }
    
    synchronized void syncSlice(final ByteBuffer byteBuffer, final boolean b) throws IOException {
        if (b && null != byteBuffer && FileChannel.MapMode.READ_WRITE == this.mmode) {
            try {
                ((MappedByteBuffer)byteBuffer).force();
            }
            catch (Throwable t) {
                if (MappedByteBufferInputStream.DEBUG) {
                    System.err.println("Caught " + t.getMessage());
                    t.printStackTrace();
                }
            }
        }
    }
    
    private synchronized void flushSlice(final int n, final boolean b) throws IOException {
        final ByteBuffer byteBuffer = this.slices[n];
        if (null != byteBuffer) {
            if (CacheMode.FLUSH_NONE != this.cmode) {
                this.slices[n] = null;
                --this.slicesEntries;
                if (CacheMode.FLUSH_PRE_HARD == this.cmode) {
                    if (!this.cleanBuffer(byteBuffer, b)) {
                        this.slices2GC[n] = new WeakReference<ByteBuffer>(byteBuffer);
                        ++this.slices2GCEntries;
                    }
                }
                else {
                    this.syncSlice(byteBuffer, b);
                    this.slices2GC[n] = new WeakReference<ByteBuffer>(byteBuffer);
                    ++this.slices2GCEntries;
                }
            }
            else {
                this.syncSlice(byteBuffer, b);
            }
        }
    }
    
    private synchronized void cleanAllSlices(final boolean b) throws IOException {
        if (null != this.slices) {
            for (int i = 0; i < this.sliceCount; ++i) {
                this.cleanSlice(i, b);
            }
            if (0 != this.slicesEntries || 0 != this.slices2GCEntries) {
                final String string = "mappedSliceCount " + this.slicesEntries + ", slices2GCEntries " + this.slices2GCEntries;
                this.dbgDump(string + ": ", System.err);
                throw new InternalError(string);
            }
        }
    }
    
    private synchronized void cleanSlice(final int n, final boolean b) throws IOException {
        final ByteBuffer byteBuffer = this.slices[n];
        final WeakReference<ByteBuffer> weakReference = this.slices2GC[n];
        this.slices2GC[n] = null;
        ByteBuffer byteBuffer2;
        if (null != weakReference) {
            --this.slices2GCEntries;
            byteBuffer2 = weakReference.get();
        }
        else {
            byteBuffer2 = null;
        }
        if (null != byteBuffer) {
            this.slices[n] = null;
            --this.slicesEntries;
            this.cleanBuffer(byteBuffer, b);
            if (null != byteBuffer2) {
                throw new InternalError("XXX");
            }
        }
        else if (null != byteBuffer2) {
            this.cleanBuffer(byteBuffer2, b);
        }
    }
    
    private synchronized boolean cleanBuffer(final ByteBuffer byteBuffer, final boolean b) throws IOException {
        if (!this.cleanerInit) {
            this.initCleaner(byteBuffer);
        }
        this.syncSlice(byteBuffer, b);
        if (!byteBuffer.isDirect()) {
            return false;
        }
        boolean b2 = false;
        if (this.hasCleaner) {
            try {
                this.cClean.invoke(this.mbbCleaner.invoke(byteBuffer, new Object[0]), new Object[0]);
                b2 = true;
            }
            catch (Throwable t) {
                this.hasCleaner = false;
                if (MappedByteBufferInputStream.DEBUG) {
                    System.err.println("Caught " + t.getMessage());
                    t.printStackTrace();
                }
            }
        }
        if (!b2 && CacheMode.FLUSH_PRE_HARD == this.cmode) {
            this.cmode = CacheMode.FLUSH_PRE_SOFT;
        }
        return b2;
    }
    
    private synchronized void initCleaner(final ByteBuffer byteBuffer) {
        final Method[] array = { null };
        final Method[] array2 = { null };
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    (array[0] = byteBuffer.getClass().getMethod("cleaner", (Class<?>[])new Class[0])).setAccessible(true);
                    (array2[0] = Class.forName("sun.misc.Cleaner").getMethod("clean", (Class<?>[])new Class[0])).setAccessible(true);
                }
                catch (Throwable t) {
                    if (MappedByteBufferInputStream.DEBUG) {
                        System.err.println("Caught " + t.getMessage());
                        t.printStackTrace();
                    }
                }
                return null;
            }
        });
        this.mbbCleaner = array[0];
        this.cClean = array2[0];
        final boolean hasCleaner = null != this.mbbCleaner && null != this.cClean;
        if (MappedByteBufferInputStream.DEBUG) {
            System.err.println("initCleaner: Has cleaner: " + hasCleaner + ", mbbCleaner " + this.mbbCleaner + ", cClean " + this.cClean);
        }
        this.hasCleaner = hasCleaner;
        this.cleanerInit = true;
    }
    
    public final synchronized CacheMode getCacheMode() {
        return this.cmode;
    }
    
    public final synchronized long length() {
        return this.totalSize;
    }
    
    public final synchronized long remaining() throws IOException {
        return (0 < this.refCount) ? (this.totalSize - this.position()) : 0L;
    }
    
    @Override
    public final synchronized int available() throws IOException {
        final long remaining = this.remaining();
        return (remaining <= 2147483647L) ? ((int)remaining) : Integer.MAX_VALUE;
    }
    
    public final synchronized long position() throws IOException {
        if (0 < this.refCount) {
            return (this.sliceIdx << this.sliceShift) + this.currentSlice().position();
        }
        return 0L;
    }
    
    public final synchronized MappedByteBufferInputStream position(final long n) throws IOException {
        this.checkOpen();
        if (this.totalSize < n || 0L > n) {
            throw new IllegalArgumentException("new position " + n + " not within [0.." + this.totalSize + "]");
        }
        final int sliceIdx = this.sliceIdx;
        if (this.totalSize == n) {
            this.sliceIdx = Math.max(0, this.sliceCount - 1);
            if (sliceIdx != this.sliceIdx) {
                this.flushSlice(sliceIdx, this.synchronous);
            }
            final ByteBuffer currentSlice = this.currentSlice();
            currentSlice.position(currentSlice.capacity());
        }
        else {
            this.sliceIdx = (int)(n >>> this.sliceShift);
            if (sliceIdx != this.sliceIdx) {
                this.flushSlice(sliceIdx, this.synchronous);
            }
            this.currentSlice().position((int)(n - (this.sliceIdx << this.sliceShift)));
        }
        return this;
    }
    
    private final synchronized void position2(final long n) throws IOException {
        if (this.totalSize == n) {
            this.sliceIdx = Math.max(0, this.sliceCount - 1);
            final ByteBuffer currentSlice = this.currentSlice();
            currentSlice.position(currentSlice.capacity());
        }
        else {
            this.sliceIdx = (int)(n >>> this.sliceShift);
            this.currentSlice().position((int)(n - (this.sliceIdx << this.sliceShift)));
        }
    }
    
    @Override
    public final boolean markSupported() {
        return true;
    }
    
    @Override
    public final synchronized void mark(final int n) {
        if (0 < this.refCount) {
            try {
                this.mark = this.position();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    @Override
    public final synchronized void reset() throws IOException {
        this.checkOpen();
        if (this.mark == -1L) {
            throw new IOException("mark not set");
        }
        this.position(this.mark);
    }
    
    @Override
    public final synchronized long skip(final long n) throws IOException {
        this.checkOpen();
        if (0L > n) {
            return 0L;
        }
        final long position = this.position();
        final long min = Math.min(this.totalSize - position, n);
        this.position(position + min);
        return min;
    }
    
    @Override
    public final synchronized int read() throws IOException {
        this.checkOpen();
        ByteBuffer byteBuffer = this.currentSlice();
        if (!byteBuffer.hasRemaining() && null == (byteBuffer = this.nextSlice())) {
            return -1;
        }
        return byteBuffer.get() & 0xFF;
    }
    
    @Override
    public final synchronized int read(final byte[] array, final int n, final int n2) throws IOException {
        this.checkOpen();
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0 || n > array.length || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException("offset " + n + ", length " + n2 + ", b.length " + array.length);
        }
        if (0 == n2) {
            return 0;
        }
        final long remaining = this.remaining();
        if (0L == remaining) {
            return -1;
        }
        final int n3 = (int)Math.min(remaining, n2);
        int min;
        for (int i = 0; i < n3; i += min) {
            ByteBuffer byteBuffer = this.currentSlice();
            int n4 = byteBuffer.remaining();
            if (n4 == 0) {
                if (null == (byteBuffer = this.nextSlice())) {
                    throw new InternalError("Unexpected EOT");
                }
                n4 = byteBuffer.remaining();
            }
            min = Math.min(n3 - i, n4);
            byteBuffer.get(array, n + i, min);
        }
        return n3;
    }
    
    public final synchronized int read(final ByteBuffer byteBuffer, final int n) throws IOException {
        this.checkOpen();
        if (byteBuffer == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n > byteBuffer.remaining()) {
            throw new IndexOutOfBoundsException("length " + n + ", b " + byteBuffer);
        }
        if (0 == n) {
            return 0;
        }
        final long remaining = this.remaining();
        if (0L == remaining) {
            return -1;
        }
        final int n2 = (int)Math.min(remaining, n);
        int min;
        for (int i = 0; i < n2; i += min) {
            ByteBuffer byteBuffer2 = this.currentSlice();
            int n3 = byteBuffer2.remaining();
            if (n3 == 0) {
                if (null == (byteBuffer2 = this.nextSlice())) {
                    throw new InternalError("Unexpected EOT");
                }
                n3 = byteBuffer2.remaining();
            }
            min = Math.min(n2 - i, n3);
            if (byteBuffer2.hasArray() && byteBuffer.hasArray()) {
                System.arraycopy(byteBuffer2.array(), byteBuffer2.arrayOffset() + byteBuffer2.position(), byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), min);
                byteBuffer2.position(byteBuffer2.position() + min);
                byteBuffer.position(byteBuffer.position() + min);
            }
            else if (min == n3) {
                byteBuffer.put(byteBuffer2);
            }
            else {
                final int limit = byteBuffer2.limit();
                byteBuffer2.limit(min);
                try {
                    byteBuffer.put(byteBuffer2);
                }
                finally {
                    byteBuffer2.limit(limit);
                }
            }
        }
        return n2;
    }
    
    static {
        NoFileResize = new FileResizeOp() {
            @Override
            public void setLength(final long n) throws IOException {
                throw new IOException("file size change not supported");
            }
        };
        Platform.initSingleton();
        if (Platform.is32Bit()) {
            DEFAULT_SLICE_SHIFT = 29;
        }
        else {
            DEFAULT_SLICE_SHIFT = 30;
        }
        DEBUG = Debug.debug("ByteBufferInputStream");
    }
    
    public enum CacheMode
    {
        FLUSH_NONE, 
        FLUSH_PRE_SOFT, 
        FLUSH_PRE_HARD;
    }
    
    public interface FileResizeOp
    {
        void setLength(final long p0) throws IOException;
    }
}
