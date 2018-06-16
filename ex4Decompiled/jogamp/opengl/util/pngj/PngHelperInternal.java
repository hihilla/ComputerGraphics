// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.nio.charset.Charset;

public class PngHelperInternal
{
    public static final Charset charsetLatin1;
    public static final Charset charsetUTF8;
    static final boolean DEBUG = false;
    private static final ThreadLocal<CRC32> crcProvider;
    
    public static byte[] getPngIdSignature() {
        return new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
    }
    
    public static int doubleToInt100000(final double n) {
        return (int)(n * 100000.0 + 0.5);
    }
    
    public static double intToDouble100000(final int n) {
        return n / 100000.0;
    }
    
    public static int readByte(final InputStream inputStream) {
        try {
            return inputStream.read();
        }
        catch (IOException ex) {
            throw new PngjInputException("error reading byte", ex);
        }
    }
    
    public static int readInt2(final InputStream inputStream) {
        try {
            final int read = inputStream.read();
            final int read2 = inputStream.read();
            if (read == -1 || read2 == -1) {
                return -1;
            }
            return (read << 8) + read2;
        }
        catch (IOException ex) {
            throw new PngjInputException("error reading readInt2", ex);
        }
    }
    
    public static int readInt4(final InputStream inputStream) {
        try {
            final int read = inputStream.read();
            final int read2 = inputStream.read();
            final int read3 = inputStream.read();
            final int read4 = inputStream.read();
            if (read == -1 || read2 == -1 || read3 == -1 || read4 == -1) {
                return -1;
            }
            return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
        }
        catch (IOException ex) {
            throw new PngjInputException("error reading readInt4", ex);
        }
    }
    
    public static int readInt1fromByte(final byte[] array, final int n) {
        return array[n] & 0xFF;
    }
    
    public static int readInt2fromBytes(final byte[] array, final int n) {
        return (array[n] & 0xFF) << 16 | (array[n + 1] & 0xFF);
    }
    
    public static int readInt4fromBytes(final byte[] array, final int n) {
        return (array[n] & 0xFF) << 24 | (array[n + 1] & 0xFF) << 16 | (array[n + 2] & 0xFF) << 8 | (array[n + 3] & 0xFF);
    }
    
    public static void writeByte(final OutputStream outputStream, final byte b) {
        try {
            outputStream.write(b);
        }
        catch (IOException ex) {
            throw new PngjOutputException(ex);
        }
    }
    
    public static void writeInt2(final OutputStream outputStream, final int n) {
        writeBytes(outputStream, new byte[] { (byte)(n >> 8 & 0xFF), (byte)(n & 0xFF) });
    }
    
    public static void writeInt4(final OutputStream outputStream, final int n) {
        final byte[] array = new byte[4];
        writeInt4tobytes(n, array, 0);
        writeBytes(outputStream, array);
    }
    
    public static void writeInt2tobytes(final int n, final byte[] array, final int n2) {
        array[n2] = (byte)(n >> 8 & 0xFF);
        array[n2 + 1] = (byte)(n & 0xFF);
    }
    
    public static void writeInt4tobytes(final int n, final byte[] array, final int n2) {
        array[n2] = (byte)(n >> 24 & 0xFF);
        array[n2 + 1] = (byte)(n >> 16 & 0xFF);
        array[n2 + 2] = (byte)(n >> 8 & 0xFF);
        array[n2 + 3] = (byte)(n & 0xFF);
    }
    
    public static void readBytes(final InputStream inputStream, final byte[] array, final int n, final int n2) {
        if (n2 == 0) {
            return;
        }
        try {
            int read;
            for (int i = 0; i < n2; i += read) {
                read = inputStream.read(array, n + i, n2 - i);
                if (read < 1) {
                    throw new PngjInputException("error reading bytes, " + read + " !=" + n2);
                }
            }
        }
        catch (IOException ex) {
            throw new PngjInputException("error reading", ex);
        }
    }
    
    public static void skipBytes(final InputStream inputStream, long n) {
        try {
            while (n > 0L) {
                final long skip = inputStream.skip(n);
                if (skip > 0L) {
                    n -= skip;
                }
                else {
                    if (skip != 0L) {
                        throw new IOException("skip() returned a negative value ???");
                    }
                    if (inputStream.read() == -1) {
                        break;
                    }
                    --n;
                }
            }
        }
        catch (IOException ex) {
            throw new PngjInputException(ex);
        }
    }
    
    public static void writeBytes(final OutputStream outputStream, final byte[] array) {
        try {
            outputStream.write(array);
        }
        catch (IOException ex) {
            throw new PngjOutputException(ex);
        }
    }
    
    public static void writeBytes(final OutputStream outputStream, final byte[] array, final int n, final int n2) {
        try {
            outputStream.write(array, n, n2);
        }
        catch (IOException ex) {
            throw new PngjOutputException(ex);
        }
    }
    
    public static void logdebug(final String s) {
    }
    
    public static CRC32 getCRC() {
        return PngHelperInternal.crcProvider.get();
    }
    
    public static int filterRowNone(final int n) {
        return n & 0xFF;
    }
    
    public static int filterRowSub(final int n, final int n2) {
        return n - n2 & 0xFF;
    }
    
    public static int filterRowUp(final int n, final int n2) {
        return n - n2 & 0xFF;
    }
    
    public static int filterRowAverage(final int n, final int n2, final int n3) {
        return n - (n2 + n3) / 2 & 0xFF;
    }
    
    public static int filterRowPaeth(final int n, final int n2, final int n3, final int n4) {
        return n - filterPaethPredictor(n2, n3, n4) & 0xFF;
    }
    
    public static int unfilterRowNone(final int n) {
        return n & 0xFF;
    }
    
    public static int unfilterRowSub(final int n, final int n2) {
        return n + n2 & 0xFF;
    }
    
    public static int unfilterRowUp(final int n, final int n2) {
        return n + n2 & 0xFF;
    }
    
    public static int unfilterRowAverage(final int n, final int n2, final int n3) {
        return n + (n2 + n3) / 2 & 0xFF;
    }
    
    public static int unfilterRowPaeth(final int n, final int n2, final int n3, final int n4) {
        return n + filterPaethPredictor(n2, n3, n4) & 0xFF;
    }
    
    static final int filterPaethPredictor(final int n, final int n2, final int n3) {
        final int n4 = n + n2 - n3;
        final int n5 = (n4 >= n) ? (n4 - n) : (n - n4);
        final int n6 = (n4 >= n2) ? (n4 - n2) : (n2 - n4);
        final int n7 = (n4 >= n3) ? (n4 - n3) : (n3 - n4);
        if (n5 <= n6 && n5 <= n7) {
            return n;
        }
        if (n6 <= n7) {
            return n2;
        }
        return n3;
    }
    
    public static final void initCrcForTests(final PngReader pngReader) {
        pngReader.initCrctest();
    }
    
    public static final long getCrctestVal(final PngReader pngReader) {
        return pngReader.getCrctestVal();
    }
    
    static {
        charsetLatin1 = Charset.forName("ISO-8859-1");
        charsetUTF8 = Charset.forName("UTF-8");
        crcProvider = new ThreadLocal<CRC32>() {
            @Override
            protected CRC32 initialValue() {
                return new CRC32();
            }
        };
    }
}
