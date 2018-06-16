// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import jogamp.common.Debug;
import java.nio.ByteBuffer;
import java.io.IOException;

public class Bitstream<T>
{
    private static final boolean DEBUG;
    public static final int EOS = -1;
    private ByteStream<T> bytes;
    private int bitBuffer;
    private int bitsDataMark;
    private int bitCount;
    private int bitsCountMark;
    private boolean outputMode;
    private boolean throwIOExceptionOnEOF;
    private static final boolean useFastPathStream = true;
    private static final boolean useFastPathTypes = true;
    private static final String strZeroPadding = "0000000000000000000000000000000000000000000000000000000000000000";
    
    public Bitstream(final ByteStream<T> bytes, final boolean outputMode) throws IllegalArgumentException {
        this.bytes = bytes;
        this.outputMode = outputMode;
        this.resetLocal();
        this.validateMode();
        this.throwIOExceptionOnEOF = false;
    }
    
    private final void resetLocal() {
        this.bitBuffer = 0;
        this.bitCount = 0;
        this.bitsDataMark = 0;
        this.bitsCountMark = -1;
    }
    
    private final void validateMode() throws IllegalArgumentException {
        if (!this.canInput() && !this.canOutput()) {
            throw new IllegalArgumentException("stream can neither input nor output: " + this);
        }
        if (this.outputMode && !this.canOutput()) {
            throw new IllegalArgumentException("stream cannot output as requested: " + this);
        }
        if (!this.outputMode && !this.canInput()) {
            throw new IllegalArgumentException("stream cannot input as requested: " + this);
        }
    }
    
    public final void setThrowIOExceptionOnEOF(final boolean throwIOExceptionOnEOF) {
        this.throwIOExceptionOnEOF = throwIOExceptionOnEOF;
    }
    
    public final boolean getThrowIOExceptionOnEOF() {
        return this.throwIOExceptionOnEOF;
    }
    
    public final void setStream(final T stream, final boolean outputMode) throws IllegalArgumentException, IOException {
        if (null != this.bytes && this.outputMode) {
            this.flush();
        }
        this.bytes.setStream(stream);
        this.outputMode = outputMode;
        this.resetLocal();
        this.validateMode();
    }
    
    public final ByteStream<T> getStream() {
        return this.bytes;
    }
    
    public final T getSubStream() {
        return this.bytes.getStream();
    }
    
    public final void close() throws IOException {
        if (null != this.bytes && this.outputMode) {
            this.flush();
        }
        this.bytes.close();
        this.bytes = null;
        this.resetLocal();
    }
    
    public final int flush() throws IllegalStateException, IOException {
        if (!this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in output-mode: " + this);
        }
        this.bytes.flush();
        if (0 != this.bitCount) {
            final int write = this.bytes.write((byte)this.bitBuffer);
            this.bitBuffer = 0;
            this.bitCount = 0;
            if (-1 == write) {
                if (this.throwIOExceptionOnEOF) {
                    throw new IOException("EOS " + this);
                }
                return -1;
            }
        }
        return 0;
    }
    
    public final boolean canInput() {
        return null != this.bytes && this.bytes.canInput();
    }
    
    public final boolean canOutput() {
        return null != this.bytes && this.bytes.canOutput();
    }
    
    public final void mark(final int n) throws IllegalStateException {
        if (this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in input-mode: " + this);
        }
        this.bytes.mark(n);
        this.bitsDataMark = this.bitBuffer;
        this.bitsCountMark = this.bitCount;
    }
    
    public final void reset() throws IllegalStateException, IOException {
        if (this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in input-mode: " + this);
        }
        if (0 > this.bitsCountMark) {
            throw new IllegalStateException("markpos not set: " + this);
        }
        this.bytes.reset();
        this.bitBuffer = this.bitsDataMark;
        this.bitCount = this.bitsCountMark;
    }
    
    public final int getBitCount() {
        return this.bitCount;
    }
    
    public final int getLastBitPos() {
        return 7 - this.bitCount;
    }
    
    public final int getBitPosition() {
        if (0 == this.bitCount) {
            return 0;
        }
        return 8 - this.bitCount;
    }
    
    public final int getBitBuffer() {
        return this.bitBuffer;
    }
    
    public final long position() {
        if (null == this.bytes) {
            return -1L;
        }
        if (0 == this.bitCount) {
            return this.bytes.position() << 3;
        }
        return (this.bytes.position() - (this.outputMode ? 0 : 1) << 3) + 8L - this.bitCount;
    }
    
    public final long position(final long n) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException, IOException {
        if (0L > n) {
            throw new IllegalArgumentException("new position not positive: " + n);
        }
        this.bytes.position(0L);
        this.resetLocal();
        if (n > this.skip(n)) {
            return -1L;
        }
        return n;
    }
    
    public final int readBit(final boolean b) throws UnsupportedOperationException, IllegalStateException, IOException {
        if (this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in input-mode: " + this);
        }
        if (0 < this.bitCount) {
            --this.bitCount;
            if (b) {
                return this.bitBuffer >>> this.bitCount & 0x1;
            }
            return this.bitBuffer >>> 7 - this.bitCount & 0x1;
        }
        else {
            this.bitBuffer = this.bytes.read();
            if (-1 == this.bitBuffer) {
                if (this.throwIOExceptionOnEOF) {
                    throw new IOException("EOS " + this);
                }
                return -1;
            }
            else {
                this.bitCount = 7;
                if (b) {
                    return this.bitBuffer >>> 7;
                }
                return this.bitBuffer & 0x1;
            }
        }
    }
    
    public final int writeBit(final boolean b, final int n) throws IllegalStateException, IOException {
        if (!this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in output-mode: " + this);
        }
        if (0 < this.bitCount) {
            --this.bitCount;
            if (b) {
                this.bitBuffer |= (0x1 & n) << this.bitCount;
            }
            else {
                this.bitBuffer |= (0x1 & n) << 7 - this.bitCount;
            }
            if (0 == this.bitCount) {
                final int write = this.bytes.write((byte)this.bitBuffer);
                if (this.throwIOExceptionOnEOF && -1 == write) {
                    throw new IOException("EOS " + this);
                }
                return write;
            }
        }
        else {
            this.bitCount = 7;
            if (b) {
                this.bitBuffer = (0x1 & n) << 7;
            }
            else {
                this.bitBuffer = (0x1 & n);
            }
        }
        return this.bitBuffer;
    }
    
    public long skip(final long n) throws IllegalStateException, IOException {
        if (null == this.bytes) {
            throw new IllegalStateException("closed: " + this);
        }
        if (Bitstream.DEBUG) {
            System.err.println("Bitstream.skip.0: " + n + " - " + this.toStringImpl());
        }
        if (n <= 0L) {
            return 0L;
        }
        if (n <= this.bitCount) {
            this.bitCount -= (int)n;
            if (Bitstream.DEBUG) {
                System.err.println("Bitstream.skip.F_N1: " + n + " - " + this.toStringImpl());
            }
            return n;
        }
        if (this.outputMode) {
            if (0 < this.bitCount && -1 == this.bytes.write((byte)this.bitBuffer)) {
                return 0L;
            }
            this.bitBuffer = 0;
        }
        final long n2 = n - this.bitCount;
        final long n3 = n2 >>> 3;
        final long skip = this.bytes.skip(n3);
        final int n4 = (int)(n2 - (n3 << 3));
        final long n5 = (skip << 3) + n4 + this.bitCount;
        if (n5 >= n) {
            this.bitCount = (8 - n4 & 0x7);
            int bitCount = 0;
            if (!this.outputMode && 0 < this.bitCount) {
                this.bitBuffer = this.bytes.read();
                if (-1 == this.bitBuffer) {
                    bitCount = this.bitCount;
                    this.bitCount = 0;
                }
            }
            if (Bitstream.DEBUG) {
                System.err.println("Bitstream.skip.F_N2: " + n + ", notReadBits " + bitCount + " - " + this.toStringImpl());
            }
            return n5 - bitCount;
        }
        this.bitCount = 0;
        this.bitBuffer = 0;
        if (Bitstream.DEBUG) {
            System.err.println("Bitstream.skip.F_EOS: " + n + " - " + this.toStringImpl());
        }
        if (this.throwIOExceptionOnEOF) {
            throw new IOException("EOS " + this);
        }
        return n5;
    }
    
    public int readBits31(final int n) throws IllegalArgumentException, IOException {
        if (31 < n) {
            throw new IllegalArgumentException("n > 31: " + n);
        }
        if (this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in input-mode: " + this);
        }
        if (0 == n) {
            return 0;
        }
        int n2 = n;
        final int min = Math.min(n, this.bitCount);
        int n5;
        if (0 < min) {
            final int n3 = (1 << min) - 1;
            final int n4 = 7 - this.bitCount + 1;
            this.bitCount -= min;
            n2 -= min;
            n5 = (n3 & this.bitBuffer >>> n4);
            if (n2 == 0) {
                return n5;
            }
        }
        else {
            n5 = 0;
        }
        assert 0 == this.bitCount;
        int n6 = min;
        do {
            this.bitBuffer = this.bytes.read();
            if (-1 == this.bitBuffer) {
                if (this.throwIOExceptionOnEOF) {
                    throw new IOException("EOS " + this);
                }
                return -1;
            }
            else {
                final int min2 = Math.min(n2, 8);
                final int n7 = (1 << min2) - 1;
                this.bitCount = 8 - min2;
                n2 -= min2;
                n5 |= (n7 & this.bitBuffer) << n6;
                n6 += min2;
            }
        } while (0 < n2);
        return n5;
    }
    
    public int writeBits31(final int n, final int n2) throws IllegalStateException, IllegalArgumentException, IOException {
        if (31 < n) {
            throw new IllegalArgumentException("n > 31: " + n);
        }
        if (!this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in output-mode: " + this);
        }
        if (0 < n) {
            int n3 = n;
            final int min = Math.min(n, this.bitCount);
            if (0 < min) {
                final int n4 = (1 << min) - 1;
                final int n5 = 7 - this.bitCount + 1;
                this.bitCount -= min;
                n3 -= min;
                this.bitBuffer |= (n4 & n2) << n5;
                if (0 == this.bitCount && -1 == this.bytes.write((byte)this.bitBuffer)) {
                    if (this.throwIOExceptionOnEOF) {
                        throw new IOException("EOS " + this);
                    }
                    return -1;
                }
                else if (n3 == 0) {
                    return n2;
                }
            }
            assert 0 == this.bitCount;
            int n6 = min;
            do {
                final int min2 = Math.min(n3, 8);
                final int n7 = (1 << min2) - 1;
                this.bitCount = 8 - min2;
                n3 -= min2;
                this.bitBuffer = (n7 & n2 >>> n6);
                n6 += min2;
                if (0 == this.bitCount && -1 == this.bytes.write((byte)this.bitBuffer)) {
                    if (this.throwIOExceptionOnEOF) {
                        throw new IOException("EOS " + this);
                    }
                    return -1;
                }
            } while (0 < n3);
        }
        return n2;
    }
    
    public final int readUInt8() throws IllegalStateException, IOException {
        if (0 != this.bitCount) {
            return this.readBits31(8);
        }
        if (this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in input-mode: " + this);
        }
        final int read = this.bytes.read();
        if (this.throwIOExceptionOnEOF && -1 == read) {
            throw new IOException("EOS " + this);
        }
        return read;
    }
    
    public final int writeInt8(final byte b) throws IllegalStateException, IOException {
        if (0 != this.bitCount) {
            return this.writeBits31(8, b);
        }
        if (!this.outputMode || null == this.bytes) {
            throw new IllegalStateException("not in output-mode: " + this);
        }
        final int write = this.bytes.write(b);
        if (this.throwIOExceptionOnEOF && -1 == write) {
            throw new IOException("EOS " + this);
        }
        return write;
    }
    
    public final int readUInt16(final boolean b) throws IllegalStateException, IOException {
        if (0 == this.bitCount) {
            if (this.outputMode || null == this.bytes) {
                throw new IllegalStateException("not in input-mode: " + this);
            }
            final int read = this.bytes.read();
            final int n = (-1 != read) ? this.bytes.read() : -1;
            if (-1 == n) {
                if (this.throwIOExceptionOnEOF) {
                    throw new IOException("EOS " + this);
                }
                return -1;
            }
            else {
                if (b) {
                    return read << 8 | n;
                }
                return n << 8 | read;
            }
        }
        else {
            final int bits31 = this.readBits31(16);
            if (-1 == bits31) {
                return -1;
            }
            if (b) {
                return (0xFF & bits31) << 8 | (0xFF & bits31 >>> 8);
            }
            return bits31;
        }
    }
    
    public static final int readUInt16(final boolean b, final byte[] array, final int n) throws IndexOutOfBoundsException {
        checkBounds(array, n, 2);
        final int n2 = array[n] & 0xFF;
        final int n3 = array[n + 1] & 0xFF;
        if (b) {
            return n2 << 8 | n3;
        }
        return n3 << 8 | n2;
    }
    
    public final int writeInt16(final boolean b, final short n) throws IllegalStateException, IOException {
        if (0 == this.bitCount) {
            if (!this.outputMode || null == this.bytes) {
                throw new IllegalStateException("not in output-mode: " + this);
            }
            final byte b2 = (byte)(0xFF & n >>> 8);
            final byte b3 = (byte)(0xFF & n);
            byte b4;
            byte b5;
            if (b) {
                b4 = b2;
                b5 = b3;
            }
            else {
                b4 = b3;
                b5 = b2;
            }
            if (-1 != this.bytes.write(b4) && -1 != this.bytes.write(b5)) {
                return n;
            }
            if (this.throwIOExceptionOnEOF) {
                throw new IOException("EOS " + this);
            }
            return -1;
        }
        else {
            if (b) {
                return this.writeBits31(16, (0xFF & n) << 8 | (0xFF & n >>> 8));
            }
            return this.writeBits31(16, n);
        }
    }
    
    public final long readUInt32(final boolean b) throws IllegalStateException, IOException {
        if (0 == this.bitCount) {
            if (this.outputMode || null == this.bytes) {
                throw new IllegalStateException("not in input-mode: " + this);
            }
            final int read = this.bytes.read();
            final int n = (-1 != read) ? this.bytes.read() : -1;
            final int n2 = (-1 != n) ? this.bytes.read() : -1;
            final int n3 = (-1 != n2) ? this.bytes.read() : -1;
            if (-1 == n3) {
                if (this.throwIOExceptionOnEOF) {
                    throw new IOException("EOS " + this);
                }
                return -1L;
            }
            else {
                if (b) {
                    return 0xFFFFFFFFL & (read << 24 | n << 16 | n2 << 8 | n3);
                }
                return 0xFFFFFFFFL & (n3 << 24 | n2 << 16 | n << 8 | read);
            }
        }
        else {
            final int bits31 = this.readBits31(16);
            final int n4 = (-1 != bits31) ? this.readBits31(16) : -1;
            if (-1 == n4) {
                return -1L;
            }
            if (b) {
                return 0xFFFFFFFFL & ((0xFF & bits31) << 24 | (0xFF & bits31 >>> 8) << 16 | (0xFF & n4) << 8 | (0xFF & n4 >>> 8));
            }
            return 0xFFFFFFFFL & (n4 << 16 | bits31);
        }
    }
    
    public static final long readUInt32(final boolean b, final byte[] array, final int n) throws IndexOutOfBoundsException {
        checkBounds(array, n, 4);
        final byte b2 = array[n];
        final byte b3 = array[n + 1];
        final byte b4 = array[n + 2];
        final byte b5 = array[n + 3];
        if (b) {
            return 0xFFFFFFFFL & (b2 << 24 | b3 << 16 | b4 << 8 | b5);
        }
        return 0xFFFFFFFFL & (b5 << 24 | b4 << 16 | b3 << 8 | b2);
    }
    
    public final int writeInt32(final boolean b, final int n) throws IllegalStateException, IOException {
        if (0 == this.bitCount) {
            if (!this.outputMode || null == this.bytes) {
                throw new IllegalStateException("not in output-mode: " + this);
            }
            final byte b2 = (byte)(0xFF & n >>> 24);
            final byte b3 = (byte)(0xFF & n >>> 16);
            final byte b4 = (byte)(0xFF & n >>> 8);
            final byte b5 = (byte)(0xFF & n);
            byte b6;
            byte b7;
            byte b8;
            byte b9;
            if (b) {
                b6 = b2;
                b7 = b3;
                b8 = b4;
                b9 = b5;
            }
            else {
                b6 = b5;
                b7 = b4;
                b8 = b3;
                b9 = b2;
            }
            if (-1 != this.bytes.write(b6) && -1 != this.bytes.write(b7) && -1 != this.bytes.write(b8) && -1 != this.bytes.write(b9)) {
                return n;
            }
            if (this.throwIOExceptionOnEOF) {
                throw new IOException("EOS " + this);
            }
            return -1;
        }
        else if (b) {
            final int n2 = 0xFF & n >>> 24;
            final int n3 = 0xFF & n >>> 16;
            final int n4 = 0xFF & n >>> 8;
            final int n5 = 0xFF & n;
            if (-1 != this.writeBits31(16, n3 << 8 | n2) && -1 != this.writeBits31(16, n5 << 8 | n4)) {
                return n;
            }
            return -1;
        }
        else {
            final int n6 = 0xFFFF & n >>> 16;
            if (-1 != this.writeBits31(16, 0xFFFF & n) && -1 != this.writeBits31(16, n6)) {
                return n;
            }
            return -1;
        }
    }
    
    public static final long toUInt32Long(final int n) {
        return 0xFFFFFFFFL & n;
    }
    
    public static final int toUInt32Int(final int n) {
        return uint32LongToInt(toUInt32Long(n));
    }
    
    public static final int uint32LongToInt(final long n) {
        if (2147483647L >= n) {
            return (int)n;
        }
        return -1;
    }
    
    @Override
    public String toString() {
        return String.format("Bitstream[%s]", this.toStringImpl());
    }
    
    protected String toStringImpl() {
        String s;
        long position;
        if (null == this.bytes) {
            s = "closed";
            position = -1L;
        }
        else {
            s = (this.outputMode ? "output" : "input");
            position = this.bytes.position();
        }
        return String.format("%s, pos %d [byteP %d, bitCnt %d], bitbuf %s", s, this.position(), position, this.bitCount, toHexBinString(true, this.bitBuffer, 8));
    }
    
    public static String toBinString(final boolean b, final int n, final int n2) {
        if (0 == n2) {
            return "";
        }
        if (b) {
            final String binaryString = Integer.toBinaryString((int)((1L << n2) - 1L) & n);
            return "0000000000000000000000000000000000000000000000000000000000000000".substring(0, n2 - binaryString.length()) + binaryString;
        }
        final char[] array = new char[32];
        for (int i = 0; i < n2; ++i) {
            array[i] = (char)((0x0 != (n & 1 << i)) ? 49 : 48);
        }
        final String s = new String(array, 0, n2);
        return s + "0000000000000000000000000000000000000000000000000000000000000000".substring(0, n2 - s.length());
    }
    
    public static String toHexBinString(final boolean b, final int n, final int n2) {
        return String.format("[0x%0" + ((0 == n2) ? 2 : ((n2 + 3) / 4)) + "X, msbFirst %b, %s]", n, b, toBinString(b, n, n2));
    }
    
    public static final String toHexBinString(final boolean b, final byte[] array, final int n, final int n2) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < n2; ++i) {
            sb.append(toHexBinString(b, 0xFF & array[n + i], 8)).append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static final String toHexBinString(final boolean b, final ByteBuffer byteBuffer, final int n, final int n2) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < n2; ++i) {
            sb.append(toHexBinString(b, 0xFF & byteBuffer.get(n + i), 8)).append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static void checkBounds(final byte[] array, final int n, final int n2) throws IndexOutOfBoundsException {
        if (n + n2 > array.length) {
            throw new IndexOutOfBoundsException("Buffer of size " + array.length + " cannot hold offset " + n + " + remaining " + n2);
        }
    }
    
    static {
        DEBUG = Debug.debug("Bitstream");
    }
    
    public static class ByteArrayStream implements ByteStream<byte[]>
    {
        private byte[] media;
        private int pos;
        private int posMark;
        
        public ByteArrayStream(final byte[] stream) {
            this.setStream(stream);
        }
        
        @Override
        public void setStream(final byte[] media) {
            this.media = media;
            this.pos = 0;
            this.posMark = -1;
        }
        
        @Override
        public byte[] getStream() {
            return this.media;
        }
        
        @Override
        public void close() {
            this.media = null;
        }
        
        @Override
        public void flush() {
        }
        
        @Override
        public boolean canInput() {
            return true;
        }
        
        @Override
        public boolean canOutput() {
            return true;
        }
        
        @Override
        public long position() {
            return this.pos;
        }
        
        @Override
        public long position(final long n) throws UnsupportedOperationException, IllegalArgumentException {
            if (n >= this.media.length) {
                return -1L;
            }
            this.pos = (int)n;
            if (this.posMark > this.pos) {
                this.posMark = -1;
            }
            return this.pos;
        }
        
        @Override
        public long skip(final long n) {
            long n2;
            if (n >= 0L) {
                n2 = Math.min(this.media.length - this.pos, (int)n);
            }
            else {
                n2 = -1 * Math.min(this.pos, (int)n * -1);
            }
            this.pos += (int)n2;
            return n2;
        }
        
        @Override
        public void mark(final int n) {
            this.posMark = this.pos;
        }
        
        @Override
        public void reset() throws IllegalStateException {
            if (0 > this.posMark) {
                throw new IllegalStateException("markpos not set");
            }
            if (Bitstream.DEBUG) {
                System.err.println("rewind: " + this.pos + " -> " + this.posMark);
            }
            this.pos = this.posMark;
        }
        
        @Override
        public int read() {
            int n;
            if (this.media.length > this.pos) {
                n = (0xFF & this.media[this.pos++]);
            }
            else {
                n = -1;
            }
            if (Bitstream.DEBUG) {
                if (-1 != n) {
                    System.err.println("u8[" + (this.pos - 1) + "] -> " + Bitstream.toHexBinString(true, n, 8));
                }
                else {
                    System.err.println("u8[" + (this.pos - 0) + "] -> EOS");
                }
            }
            return n;
        }
        
        @Override
        public int write(final byte b) {
            int n;
            if (this.media.length > this.pos) {
                this.media[this.pos++] = b;
                n = (0xFF & b);
            }
            else {
                n = -1;
            }
            if (Bitstream.DEBUG) {
                if (-1 != n) {
                    System.err.println("u8[" + (this.pos - 1) + "] <- " + Bitstream.toHexBinString(true, n, 8));
                }
                else {
                    System.err.println("u8[" + (this.pos - 0) + "] <- EOS");
                }
            }
            return n;
        }
    }
    
    public static class ByteBufferStream implements ByteStream<ByteBuffer>
    {
        private ByteBuffer media;
        private int pos;
        private int posMark;
        
        public ByteBufferStream(final ByteBuffer stream) {
            this.setStream(stream);
        }
        
        @Override
        public void setStream(final ByteBuffer media) {
            this.media = media;
            this.pos = 0;
            this.posMark = -1;
        }
        
        @Override
        public ByteBuffer getStream() {
            return this.media;
        }
        
        @Override
        public void close() {
            this.media = null;
        }
        
        @Override
        public void flush() {
        }
        
        @Override
        public boolean canInput() {
            return true;
        }
        
        @Override
        public boolean canOutput() {
            return true;
        }
        
        @Override
        public long position() {
            return this.pos;
        }
        
        @Override
        public long position(final long n) throws UnsupportedOperationException, IllegalArgumentException {
            if (n >= this.media.limit()) {
                return -1L;
            }
            this.media.position((int)n);
            this.pos = (int)n;
            if (this.posMark > this.pos) {
                this.posMark = -1;
            }
            return this.pos;
        }
        
        @Override
        public long skip(final long n) {
            long n2;
            if (n >= 0L) {
                n2 = Math.min(this.media.limit() - this.pos, (int)n);
            }
            else {
                n2 = -1 * Math.min(this.pos, (int)n * -1);
            }
            this.pos += (int)n2;
            return n2;
        }
        
        @Override
        public void mark(final int n) {
            this.posMark = this.pos;
        }
        
        @Override
        public void reset() throws IllegalStateException {
            if (0 > this.posMark) {
                throw new IllegalStateException("markpos not set");
            }
            if (Bitstream.DEBUG) {
                System.err.println("rewind: " + this.pos + " -> " + this.posMark);
            }
            this.media.position(this.posMark);
            this.pos = this.posMark;
        }
        
        @Override
        public int read() {
            int n;
            if (this.media.limit() > this.pos) {
                n = (0xFF & this.media.get(this.pos++));
            }
            else {
                n = -1;
            }
            if (Bitstream.DEBUG) {
                if (-1 != n) {
                    System.err.println("u8[" + (this.pos - 1) + "] -> " + Bitstream.toHexBinString(true, n, 8));
                }
                else {
                    System.err.println("u8[" + (this.pos - 0) + "] -> EOS");
                }
            }
            return n;
        }
        
        @Override
        public int write(final byte b) {
            int n;
            if (this.media.limit() > this.pos) {
                this.media.put(this.pos++, b);
                n = (0xFF & b);
            }
            else {
                n = -1;
            }
            if (Bitstream.DEBUG) {
                if (-1 != n) {
                    System.err.println("u8[" + (this.pos - 1) + "] <- " + Bitstream.toHexBinString(true, n, 8));
                }
                else {
                    System.err.println("u8[" + (this.pos - 0) + "] <- EOS");
                }
            }
            return n;
        }
    }
    
    public static class ByteInputStream implements ByteStream<InputStream>
    {
        private BufferedInputStream media;
        private long pos;
        private long posMark;
        
        public ByteInputStream(final InputStream stream) {
            this.setStream(stream);
        }
        
        @Override
        public void setStream(final InputStream inputStream) {
            if (inputStream instanceof BufferedInputStream) {
                this.media = (BufferedInputStream)inputStream;
            }
            else if (null != inputStream) {
                this.media = new BufferedInputStream(inputStream);
            }
            else {
                this.media = null;
            }
            this.pos = 0L;
            this.posMark = -1L;
        }
        
        @Override
        public InputStream getStream() {
            return this.media;
        }
        
        @Override
        public void close() throws IOException {
            if (null != this.media) {
                this.media.close();
                this.media = null;
            }
        }
        
        @Override
        public void flush() {
        }
        
        @Override
        public boolean canInput() {
            return true;
        }
        
        @Override
        public boolean canOutput() {
            return false;
        }
        
        @Override
        public long position() {
            return this.pos;
        }
        
        @Override
        public long position(final long n) throws UnsupportedOperationException, IllegalArgumentException {
            throw new UnsupportedOperationException("N/a for " + this.getClass().getCanonicalName());
        }
        
        @Override
        public long skip(final long n) throws IOException {
            final long skip = this.media.skip(n);
            this.pos += skip;
            return skip;
        }
        
        @Override
        public void mark(final int n) {
            this.media.mark(n);
            this.posMark = this.pos;
        }
        
        @Override
        public void reset() throws IllegalStateException, IOException {
            if (0L > this.posMark) {
                throw new IllegalStateException("markpos not set");
            }
            if (Bitstream.DEBUG) {
                System.err.println("rewind: " + this.pos + " -> " + this.posMark);
            }
            this.media.reset();
            this.pos = this.posMark;
        }
        
        @Override
        public int read() throws IOException {
            final int read = this.media.read();
            if (Bitstream.DEBUG) {
                if (-1 != read) {
                    System.err.println("u8[" + this.pos + "] -> " + Bitstream.toHexBinString(true, read, 8));
                }
                else {
                    System.err.println("u8[" + this.pos + "] -> EOS");
                }
            }
            if (-1 != read) {
                ++this.pos;
            }
            return read;
        }
        
        @Override
        public int write(final byte b) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("not allowed with input stream");
        }
    }
    
    public static class ByteOutputStream implements ByteStream<OutputStream>
    {
        private BufferedOutputStream media;
        private long pos;
        
        public ByteOutputStream(final OutputStream stream) {
            this.pos = 0L;
            this.setStream(stream);
        }
        
        @Override
        public void setStream(final OutputStream outputStream) {
            if (outputStream instanceof BufferedOutputStream) {
                this.media = (BufferedOutputStream)outputStream;
            }
            else if (null != outputStream) {
                this.media = new BufferedOutputStream(outputStream);
            }
            else {
                this.media = null;
            }
            this.pos = 0L;
        }
        
        @Override
        public void close() throws IOException {
            if (null != this.media) {
                this.media.close();
                this.media = null;
            }
        }
        
        @Override
        public void flush() throws IOException {
            if (null != this.media) {
                this.media.flush();
            }
        }
        
        @Override
        public boolean canInput() {
            return false;
        }
        
        @Override
        public boolean canOutput() {
            return true;
        }
        
        @Override
        public long position() {
            return this.pos;
        }
        
        @Override
        public long position(final long n) throws UnsupportedOperationException, IllegalArgumentException {
            throw new UnsupportedOperationException("N/a for " + this.getClass().getCanonicalName());
        }
        
        @Override
        public long skip(final long n) throws IOException {
            long n2;
            for (n2 = n; n2 > 0L; --n2) {
                this.media.write(0);
            }
            final long n3 = n - n2;
            this.pos += n3;
            return n3;
        }
        
        @Override
        public OutputStream getStream() {
            return this.media;
        }
        
        @Override
        public void mark(final int n) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("not allowed with output stream");
        }
        
        @Override
        public void reset() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("not allowed with output stream");
        }
        
        @Override
        public int read() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("not allowed with output stream");
        }
        
        @Override
        public int write(final byte b) throws IOException {
            final int n = 0xFF & b;
            this.media.write(n);
            if (Bitstream.DEBUG) {
                System.err.println("u8[" + this.pos + "] <- " + Bitstream.toHexBinString(true, n, 8));
            }
            ++this.pos;
            return n;
        }
    }
    
    public interface ByteStream<T>
    {
        void setStream(final T p0);
        
        T getStream();
        
        void close() throws IOException;
        
        void flush() throws IOException;
        
        boolean canInput();
        
        boolean canOutput();
        
        long position();
        
        long position(final long p0) throws UnsupportedOperationException, IllegalArgumentException;
        
        long skip(final long p0) throws IOException;
        
        void mark(final int p0) throws UnsupportedOperationException;
        
        void reset() throws UnsupportedOperationException, IllegalStateException, IOException;
        
        int read() throws UnsupportedOperationException, IOException;
        
        int write(final byte p0) throws UnsupportedOperationException, IOException;
    }
}
