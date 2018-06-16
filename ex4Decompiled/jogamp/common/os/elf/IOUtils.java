// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

import com.jogamp.common.util.Bitstream;
import java.io.IOException;
import java.io.RandomAccessFile;

class IOUtils
{
    static final long MAX_INT_VALUE = 2147483647L;
    
    static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    static int shortToInt(final short n) {
        return n & 0xFFFF;
    }
    
    static int long2Int(final long n) {
        if (2147483647L < n) {
            throw new IllegalArgumentException("Read uint32 value " + toHexString(n) + " > int32-max " + toHexString(2147483647L));
        }
        return (int)n;
    }
    
    static void readBytes(final RandomAccessFile randomAccessFile, final byte[] array, final int n, final int n2) throws IOException, IllegalArgumentException {
        randomAccessFile.readFully(array, n, n2);
    }
    
    static void seek(final RandomAccessFile randomAccessFile, final long n) throws IOException {
        randomAccessFile.seek(n);
    }
    
    static int readUInt32(final boolean b, final byte[] array, final int n) {
        final int uint32LongToInt = Bitstream.uint32LongToInt(Bitstream.readUInt32(b, array, n));
        if (0 > uint32LongToInt) {
            throw new IllegalArgumentException("Read uint32 value " + toHexString(uint32LongToInt) + " > int32-max " + toHexString(2147483647L));
        }
        return uint32LongToInt;
    }
    
    static String getString(final byte[] array, final int n, final int n2, final int[] array2) throws IndexOutOfBoundsException {
        Bitstream.checkBounds(array, n, n2);
        int n3;
        for (n3 = 0; n3 < n2 && array[n3 + n] != 0; ++n3) {}
        final String s = (0 < n3) ? new String(array, n, n3) : "";
        if (null != array2) {
            array2[0] = n + n3 + 1;
        }
        return s;
    }
    
    static int getStringCount(final byte[] array, final int n, final int n2) throws IndexOutOfBoundsException {
        Bitstream.checkBounds(array, n, n2);
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            while (i < n2 && array[i + n] != 0) {
                ++i;
            }
            ++n3;
        }
        return n3;
    }
    
    public static String[] getStrings(final byte[] array, final int n, final int n2) throws IndexOutOfBoundsException {
        final int stringCount = getStringCount(array, n, n2);
        final String[] array2 = new String[stringCount];
        final int[] array3 = { n };
        for (int i = 0; i < stringCount; ++i) {
            array2[i] = getString(array, array3[0], n2 - array3[0], array3);
        }
        return array2;
    }
}
