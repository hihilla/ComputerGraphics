// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.DataInput;
import java.io.FilterInputStream;

public class LEDataInputStream extends FilterInputStream implements DataInput
{
    DataInputStream dataIn;
    
    public LEDataInputStream(final InputStream inputStream) {
        super(inputStream);
        this.dataIn = new DataInputStream(inputStream);
    }
    
    @Override
    public void close() throws IOException {
        this.dataIn.close();
    }
    
    @Override
    public final synchronized int read(final byte[] array) throws IOException {
        return this.dataIn.read(array, 0, array.length);
    }
    
    @Override
    public final synchronized int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.dataIn.read(array, n, n2);
    }
    
    @Override
    public final void readFully(final byte[] array) throws IOException {
        this.dataIn.readFully(array, 0, array.length);
    }
    
    @Override
    public final void readFully(final byte[] array, final int n, final int n2) throws IOException {
        this.dataIn.readFully(array, n, n2);
    }
    
    @Override
    public final int skipBytes(final int n) throws IOException {
        return this.dataIn.skipBytes(n);
    }
    
    @Override
    public final boolean readBoolean() throws IOException {
        final int read = this.dataIn.read();
        if (read < 0) {
            throw new EOFException();
        }
        return read != 0;
    }
    
    @Override
    public final byte readByte() throws IOException {
        final int read = this.dataIn.read();
        if (read < 0) {
            throw new EOFException();
        }
        return (byte)read;
    }
    
    @Override
    public final int readUnsignedByte() throws IOException {
        final int read = this.dataIn.read();
        if (read < 0) {
            throw new EOFException();
        }
        return read;
    }
    
    @Override
    public final short readShort() throws IOException {
        final int read = this.dataIn.read();
        final int read2 = this.dataIn.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (short)((read << 0) + (read2 << 8));
    }
    
    @Override
    public final int readUnsignedShort() throws IOException {
        final int read = this.dataIn.read();
        final int read2 = this.dataIn.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (read << 0) + (read2 << 8);
    }
    
    @Override
    public final char readChar() throws IOException {
        final int read = this.dataIn.read();
        final int read2 = this.dataIn.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (char)((read << 0) + (read2 << 8));
    }
    
    @Override
    public final int readInt() throws IOException {
        final int read = this.dataIn.read();
        final int read2 = this.dataIn.read();
        final int read3 = this.dataIn.read();
        final int read4 = this.dataIn.read();
        if ((read | read2 | read3 | read4) < 0) {
            throw new EOFException();
        }
        return (read << 0) + (read2 << 8) + (read3 << 16) + (read4 << 24);
    }
    
    @Override
    public final long readLong() throws IOException {
        return (this.readInt() & 0xFFFFFFFFL) + (this.readInt() << 32);
    }
    
    @Override
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    @Override
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    @Override
    public final String readLine() throws IOException {
        return "";
    }
    
    @Override
    public final String readUTF() throws IOException {
        return "";
    }
    
    public static final String readUTF(final DataInput dataInput) throws IOException {
        return "";
    }
}
