// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.io.DataOutputStream;
import java.util.zip.Deflater;
import java.io.OutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;

public class CustomCompress
{
    public static final int MAGIC = -554588192;
    
    public static byte[] inflateFromStream(final InputStream inputStream) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        final int int1 = dataInputStream.readInt();
        if (int1 != -554588192) {
            throw new IOException("wrong magic: " + Integer.toHexString(int1) + ", expected " + Integer.toHexString(-554588192));
        }
        final int int2 = dataInputStream.readInt();
        final int int3 = dataInputStream.readInt();
        return inflateFromStream(inputStream, int2, int3, new byte[int3], 0);
    }
    
    public static byte[] inflateFromStream(final InputStream inputStream, final int n, final int n2, final byte[] array, final int n3) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (n <= 0 || n2 <= 0) {
            throw new IllegalArgumentException("Length[input " + n + ", output " + n2 + "]");
        }
        if (n3 < 0 || array.length < n3 + n2) {
            throw new ArrayIndexOutOfBoundsException("output.length " + array.length + ", offset " + n3 + ", length " + n2);
        }
        final byte[] array2 = new byte[n];
        int n4 = 0;
        try {
            while (true) {
                final int n5 = n - n4;
                final int read;
                if (0 >= n5 || (read = inputStream.read(array2, n4, n5)) == -1) {
                    break;
                }
                n4 += read;
            }
        }
        finally {
            inputStream.close();
        }
        if (n != n4) {
            throw new IOException("Got " + n4 + " bytes != expected " + n);
        }
        try {
            final Inflater inflater = new Inflater();
            inflater.setInput(array2, 0, n);
            final int inflate = inflater.inflate(array, n3, n2);
            inflater.end();
            if (n2 != inflate) {
                throw new IOException("Got inflated " + inflate + " bytes != expected " + n2);
            }
        }
        catch (DataFormatException ex) {
            throw new IOException(ex);
        }
        return array;
    }
    
    public static int deflateToStream(final byte[] array, final int n, final int n2, final int n3, final OutputStream outputStream) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (n2 <= 0) {
            throw new IllegalArgumentException("Length[input " + n2 + "]");
        }
        if (n < 0 || array.length < n + n2) {
            throw new ArrayIndexOutOfBoundsException("input.length " + array.length + ", offset " + n + ", length " + n2);
        }
        final byte[] array2 = new byte[n2];
        final Deflater deflater = new Deflater(n3);
        deflater.setInput(array, n, n2);
        deflater.finish();
        final int deflate = deflater.deflate(array2, 0, n2);
        deflater.end();
        final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(-554588192);
        dataOutputStream.writeInt(deflate);
        dataOutputStream.writeInt(n2);
        outputStream.write(array2, 0, deflate);
        return deflate;
    }
}
