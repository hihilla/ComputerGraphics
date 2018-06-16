// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import java.io.OutputStream;
import java.io.BufferedOutputStream;
import com.jogamp.common.util.IOUtil;
import java.io.DataOutputStream;
import java.io.File;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

public class SGIImage
{
    private final Header header;
    private int format;
    private byte[] data;
    private int[] rowStart;
    private int[] rowSize;
    private int rleEnd;
    private byte[] tmpData;
    private byte[] tmpRead;
    private static final int MAGIC = 474;
    
    private SGIImage(final Header header) {
        this.header = header;
    }
    
    public static SGIImage read(final String s) throws IOException {
        return read(new FileInputStream(s));
    }
    
    public static SGIImage read(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
        final SGIImage sgiImage = new SGIImage(new Header(dataInputStream));
        sgiImage.decodeImage(dataInputStream);
        return sgiImage;
    }
    
    public void write(final String s, final boolean b) throws IOException {
        this.write(new File(s), b);
    }
    
    public void write(final File file, final boolean b) throws IOException {
        this.writeImage(file, this.data, this.header.xsize, this.header.ysize, this.header.zsize, b);
    }
    
    public static SGIImage createFromData(final int n, final int n2, final boolean b, final byte[] data) {
        final Header header = new Header();
        header.xsize = (short)n;
        header.ysize = (short)n2;
        header.zsize = (short)(b ? 4 : 3);
        final SGIImage sgiImage = new SGIImage(header);
        sgiImage.data = data;
        return sgiImage;
    }
    
    @Deprecated
    public static boolean isSGIImage(InputStream inputStream) throws IOException {
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!inputStream.markSupported()) {
            throw new IOException("Can not test non-destructively whether given InputStream is an SGI RGB image");
        }
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        dataInputStream.mark(4);
        final short short1 = dataInputStream.readShort();
        dataInputStream.reset();
        return short1 == 474;
    }
    
    public int getWidth() {
        return this.header.xsize;
    }
    
    public int getHeight() {
        return this.header.ysize;
    }
    
    public int getFormat() {
        return this.format;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    @Override
    public String toString() {
        return this.header.toString();
    }
    
    private void decodeImage(final DataInputStream dataInputStream) throws IOException {
        if (this.header.storage == 1) {
            final short n = (short)(this.header.ysize * this.header.zsize);
            this.rowStart = new int[n];
            this.rowSize = new int[n];
            this.rleEnd = 8 * n + 512;
            for (short n2 = 0; n2 < n; ++n2) {
                this.rowStart[n2] = dataInputStream.readInt();
            }
            for (short n3 = 0; n3 < n; ++n3) {
                this.rowSize[n3] = dataInputStream.readInt();
            }
            this.tmpRead = new byte[this.header.xsize * 256];
        }
        this.tmpData = this.readAll(dataInputStream);
        final short xsize = this.header.xsize;
        final short ysize = this.header.ysize;
        final short zsize = this.header.zsize;
        short n4 = 0;
        this.data = new byte[xsize * ysize * 4];
        final byte[] array = new byte[xsize];
        final byte[] array2 = new byte[xsize];
        final byte[] array3 = new byte[xsize];
        final byte[] array4 = new byte[xsize];
        for (short n5 = 0; n5 < ysize; ++n5) {
            if (zsize >= 4) {
                this.getRow(array, n5, 0);
                this.getRow(array2, n5, 1);
                this.getRow(array3, n5, 2);
                this.getRow(array4, n5, 3);
                this.rgbatorgba(array, array2, array3, array4, this.data, n4);
            }
            else if (zsize == 3) {
                this.getRow(array, n5, 0);
                this.getRow(array2, n5, 1);
                this.getRow(array3, n5, 2);
                this.rgbtorgba(array, array2, array3, this.data, n4);
            }
            else if (zsize == 2) {
                this.getRow(array, n5, 0);
                this.getRow(array4, n5, 1);
                this.latorgba(array, array4, this.data, n4);
            }
            else {
                this.getRow(array, n5, 0);
                this.bwtorgba(array, this.data, n4);
            }
            n4 += (short)(4 * xsize);
        }
        this.rowStart = null;
        this.rowSize = null;
        this.tmpData = null;
        this.tmpRead = null;
        this.format = 6408;
        this.header.zsize = 4;
    }
    
    private void getRow(final byte[] array, final int n, final int n2) {
        if (this.header.storage == 1) {
            System.arraycopy(this.tmpData, this.rowStart[n + n2 * this.header.ysize] - this.rleEnd, this.tmpRead, 0, this.rowSize[n + n2 * this.header.ysize]);
            int n3 = 0;
            int n4 = 0;
            while (true) {
                final byte b = this.tmpRead[n3++];
                int n5 = b & 0x7F;
                if (n5 == 0) {
                    break;
                }
                if ((b & 0x80) != 0x0) {
                    while (n5-- > 0) {
                        array[n4++] = this.tmpRead[n3++];
                    }
                }
                else {
                    final byte b2 = this.tmpRead[n3++];
                    while (n5-- > 0) {
                        array[n4++] = b2;
                    }
                }
            }
            return;
        }
        System.arraycopy(this.tmpData, n * this.header.xsize + n2 * this.header.xsize * this.header.ysize, array, 0, this.header.xsize);
    }
    
    private void bwtorgba(final byte[] array, final byte[] array2, final int n) {
        for (int i = 0; i < array.length; ++i) {
            array2[4 * i + n + 0] = array[i];
            array2[4 * i + n + 1] = array[i];
            array2[4 * i + n + 2] = array[i];
            array2[4 * i + n + 3] = -1;
        }
    }
    
    private void latorgba(final byte[] array, final byte[] array2, final byte[] array3, final int n) {
        for (int i = 0; i < array.length; ++i) {
            array3[4 * i + n + 0] = array[i];
            array3[4 * i + n + 1] = array[i];
            array3[4 * i + n + 2] = array[i];
            array3[4 * i + n + 3] = array2[i];
        }
    }
    
    private void rgbtorgba(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4, final int n) {
        for (int i = 0; i < array3.length; ++i) {
            array4[4 * i + n + 0] = array[i];
            array4[4 * i + n + 1] = array2[i];
            array4[4 * i + n + 2] = array3[i];
            array4[4 * i + n + 3] = -1;
        }
    }
    
    private void rgbatorgba(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4, final byte[] array5, final int n) {
        for (int i = 0; i < array3.length; ++i) {
            array5[4 * i + n + 0] = array[i];
            array5[4 * i + n + 1] = array2[i];
            array5[4 * i + n + 2] = array3[i];
            array5[4 * i + n + 3] = array4[i];
        }
    }
    
    private static byte imgref(final byte[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return array[n4 * n5 * n3 + n4 * n2 + n];
    }
    
    private void writeHeader(final DataOutputStream dataOutputStream, final int n, final int n2, final int n3, final boolean b) throws IOException {
        dataOutputStream.writeShort(474);
        dataOutputStream.write(b ? 1 : 0);
        dataOutputStream.write(1);
        dataOutputStream.writeShort(3);
        dataOutputStream.writeShort(n);
        dataOutputStream.writeShort(n2);
        dataOutputStream.writeShort(n3);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(255);
        dataOutputStream.writeInt(0);
        for (int i = 0; i < 80; ++i) {
            dataOutputStream.write(0);
        }
        dataOutputStream.writeInt(0);
        for (int j = 0; j < 404; ++j) {
            dataOutputStream.write(0);
        }
    }
    
    private void writeImage(final File file, byte[] array, final int n, final int n2, final int n3, final boolean b) throws IOException {
        final byte[] array2 = new byte[n * n2 * n3];
        int n4 = 0;
        for (int i = 0; i < n3; ++i) {
            for (int j = i; j < n * n2 * n3; j += n3) {
                array2[n4++] = array[j];
            }
        }
        array = array2;
        final int[] array3 = new int[n2 * n3];
        final int[] array4 = new int[n2 * n3];
        final byte[] array5 = new byte[2 * n * n2 * n3];
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 1;
        int n9 = n2;
        if (b) {
            n7 = n2 - 1;
            n9 = -1;
            n8 = -1;
        }
        for (int k = 0; k < n3; ++k) {
            for (int l = n7; l != n9; l += n8) {
                int n10 = 0;
                byte b2 = 0;
                int n11 = 0;
                final int n12 = n6;
                int n13 = n6++;
                byte imgref = 0;
                while (n10 < n) {
                    boolean b3 = false;
                    if (n11 != 0) {
                        if (imgref(array, n10, l, k, n, n2, n3) != imgref) {
                            b3 = true;
                        }
                    }
                    else if (n10 + 3 < n) {
                        b3 = true;
                        for (int n14 = 1; n14 <= 3; ++n14) {
                            if (imgref(array, n10, l, k, n, n2, n3) != imgref(array, n10 + n14, l, k, n, n2, n3)) {
                                b3 = false;
                            }
                        }
                    }
                    if (b3 || b2 == 127) {
                        if (n10 > 0) {
                            if (n11 != 0) {
                                array5[n13] = b2;
                            }
                            else {
                                array5[n13] = (byte)(b2 | 0x80);
                            }
                        }
                        if (n11 != 0) {
                            if (b3) {
                                n11 = 0;
                            }
                            array5[n6++] = imgref;
                        }
                        else {
                            if (b3) {
                                n11 = 1;
                            }
                            imgref = imgref(array, n10, l, k, n, n2, n3);
                        }
                        if (n10 > 0) {
                            n13 = n6++;
                            b2 = 0;
                        }
                    }
                    if (n11 == 0) {
                        array5[n6++] = imgref(array, n10, l, k, n, n2, n3);
                    }
                    ++b2;
                    if (n10 == n - 1) {
                        if (n11 != 0) {
                            array5[n13] = b2;
                            array5[n6++] = imgref;
                        }
                        else {
                            array5[n13] = (byte)(b2 | 0x80);
                        }
                        array5[n6++] = 0;
                    }
                    ++n10;
                }
                final int n15 = n6 - n12;
                if (b) {
                    array4[n2 * k + (n2 - l - 1)] = n15;
                }
                else {
                    array4[n2 * k + l] = n15;
                }
                if (b) {
                    array3[n2 * k + (n2 - l - 1)] = n5;
                }
                else {
                    array3[n2 * k + l] = n5;
                }
                n5 += n15;
            }
        }
        final int n16 = n6;
        final DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(IOUtil.getFileOutputStream(file, true)));
        this.writeHeader(dataOutputStream, n, n2, n3, true);
        for (int n17 = 0; n17 < n2 * n3; ++n17) {
            dataOutputStream.writeInt(array3[n17] + 512 + 2 * n2 * n3 * 4);
        }
        for (int n18 = 0; n18 < n2 * n3; ++n18) {
            dataOutputStream.writeInt(array4[n18]);
        }
        for (int n19 = 0; n19 < n16; ++n19) {
            dataOutputStream.write(array5[n19]);
        }
        dataOutputStream.close();
    }
    
    private byte[] readAll(final DataInputStream dataInputStream) throws IOException {
        byte[] array = new byte[16384];
        int n = 0;
        int read;
        do {
            read = dataInputStream.read(array, n, array.length - n);
            if (n == array.length) {
                final byte[] array2 = new byte[2 * array.length];
                System.arraycopy(array, 0, array2, 0, n);
                array = array2;
            }
            if (read > 0) {
                n += read;
            }
        } while (read != -1 && dataInputStream.available() != 0);
        if (n != array.length) {
            final byte[] array3 = new byte[n];
            System.arraycopy(array, 0, array3, 0, n);
            array = array3;
        }
        return array;
    }
    
    static class Header
    {
        short magic;
        byte storage;
        byte bpc;
        short dimension;
        short xsize;
        short ysize;
        short zsize;
        int pixmin;
        int pixmax;
        int dummy;
        String imagename;
        int colormap;
        
        Header() {
            this.magic = 474;
        }
        
        Header(final DataInputStream dataInputStream) throws IOException {
            this.magic = dataInputStream.readShort();
            this.storage = dataInputStream.readByte();
            this.bpc = dataInputStream.readByte();
            this.dimension = dataInputStream.readShort();
            this.xsize = dataInputStream.readShort();
            this.ysize = dataInputStream.readShort();
            this.zsize = dataInputStream.readShort();
            this.pixmin = dataInputStream.readInt();
            this.pixmax = dataInputStream.readInt();
            this.dummy = dataInputStream.readInt();
            final byte[] array = new byte[80];
            dataInputStream.read(array);
            int n = 0;
            while (array[n++] != 0) {}
            this.imagename = new String(array, 0, n);
            this.colormap = dataInputStream.readInt();
            dataInputStream.read(new byte[404]);
        }
        
        @Override
        public String toString() {
            return "magic: " + this.magic + " storage: " + this.storage + " bpc: " + this.bpc + " dimension: " + this.dimension + " xsize: " + this.xsize + " ysize: " + this.ysize + " zsize: " + this.zsize + " pixmin: " + this.pixmin + " pixmax: " + this.pixmax + " imagename: " + this.imagename + " colormap: " + this.colormap;
        }
    }
}
