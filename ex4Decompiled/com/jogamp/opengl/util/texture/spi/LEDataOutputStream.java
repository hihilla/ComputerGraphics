// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.DataOutput;
import java.io.FilterOutputStream;

public class LEDataOutputStream extends FilterOutputStream implements DataOutput
{
    DataOutputStream dataOut;
    
    public LEDataOutputStream(final OutputStream outputStream) {
        super(outputStream);
        this.dataOut = new DataOutputStream(outputStream);
    }
    
    @Override
    public void close() throws IOException {
        this.dataOut.close();
    }
    
    @Override
    public final synchronized void write(final byte[] array) throws IOException {
        this.dataOut.write(array, 0, array.length);
    }
    
    @Override
    public final synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        this.dataOut.write(array, n, n2);
    }
    
    @Override
    public final void write(final int n) throws IOException {
        this.dataOut.write(n);
    }
    
    @Override
    public final void writeBoolean(final boolean b) throws IOException {
        this.dataOut.writeBoolean(b);
    }
    
    @Override
    public final void writeByte(final int n) throws IOException {
        this.dataOut.writeByte(n);
    }
    
    @Override
    public final void writeBytes(final String s) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void writeChar(final int n) throws IOException {
        this.dataOut.writeChar((n >> 8 & 0xFF) | (n & 0xFF) << 8);
    }
    
    @Override
    public final void writeChars(final String s) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void writeDouble(final double n) throws IOException {
        this.writeLong(Double.doubleToRawLongBits(n));
    }
    
    @Override
    public final void writeFloat(final float n) throws IOException {
        this.writeInt(Float.floatToRawIntBits(n));
    }
    
    @Override
    public final void writeInt(final int n) throws IOException {
        this.dataOut.writeInt(n >>> 24 | (n >>> 8 & 0xFF00) | (n << 8 & 0xFF00) | n << 24);
    }
    
    @Override
    public final void writeLong(final long n) throws IOException {
        this.writeInt((int)n);
        this.writeInt((int)(n >>> 32));
    }
    
    @Override
    public final void writeShort(final int n) throws IOException {
        this.dataOut.writeShort((n >> 8 & 0xFF) | (n & 0xFF) << 8);
    }
    
    @Override
    public final void writeUTF(final String s) throws IOException {
        throw new UnsupportedOperationException();
    }
}
