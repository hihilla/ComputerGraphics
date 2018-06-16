// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.io.FileOutputStream;
import java.nio.ByteOrder;
import com.jogamp.common.util.IOUtil;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import com.jogamp.opengl.GLContext;
import java.io.IOException;
import com.jogamp.opengl.GLProfile;
import java.nio.ByteBuffer;

public class TGAImage
{
    private final Header header;
    private int format;
    private int bpp;
    private ByteBuffer data;
    
    private TGAImage(final Header header) {
        this.header = header;
    }
    
    private void decodeImage(final GLProfile glProfile, final LEDataInputStream leDataInputStream) throws IOException {
        switch (this.header.imageType()) {
            case 1: {
                throw new IOException("TGADecoder Uncompressed Colormapped images not supported");
            }
            case 2: {
                switch (this.header.pixelDepth) {
                    case 16: {
                        throw new IOException("TGADecoder Compressed 16-bit True Color images not supported");
                    }
                    case 24:
                    case 32: {
                        this.decodeRGBImageU24_32(glProfile, leDataInputStream);
                        break;
                    }
                }
                break;
            }
            case 3: {
                throw new IOException("TGADecoder Uncompressed Grayscale images not supported");
            }
            case 9: {
                throw new IOException("TGADecoder Compressed Colormapped images not supported");
            }
            case 10: {
                switch (this.header.pixelDepth) {
                    case 16: {
                        throw new IOException("TGADecoder Compressed 16-bit True Color images not supported");
                    }
                    case 24:
                    case 32: {
                        this.decodeRGBImageRLE24_32(glProfile, leDataInputStream);
                        break;
                    }
                }
                break;
            }
            case 11: {
                throw new IOException("TGADecoder Compressed Grayscale images not supported");
            }
        }
    }
    
    private void decodeRGBImageU24_32(final GLProfile glProfile, final LEDataInputStream leDataInputStream) throws IOException {
        this.setupImage24_32(glProfile);
        final int n = this.header.width() * this.bpp;
        final byte[] array = new byte[n];
        final byte[] array2 = new byte[n * this.header.height()];
        for (int i = 0; i < this.header.height(); ++i) {
            leDataInputStream.readFully(array, 0, n);
            int n2;
            if (this.header.topToBottom()) {
                n2 = this.header.height - i - 1;
            }
            else {
                n2 = i;
            }
            System.arraycopy(array, 0, array2, n2 * n, array.length);
        }
        if (this.format == 6407 || this.format == 6408) {
            swapBGR(array2, n, this.header.height(), this.bpp);
        }
        this.data = ByteBuffer.wrap(array2);
    }
    
    private void decodeRGBImageRLE24_32(final GLProfile glProfile, final LEDataInputStream leDataInputStream) throws IOException {
        this.setupImage24_32(glProfile);
        final byte[] array = new byte[this.bpp];
        final int n = this.header.width() * this.bpp;
        final byte[] array2 = new byte[n * this.header.height()];
        int n2;
        for (int i = 0; i < array2.length; i += this.bpp * n2) {
            final int unsignedByte = leDataInputStream.readUnsignedByte();
            n2 = (unsignedByte & 0x7F) + 1;
            if ((unsignedByte & 0x80) != 0x0) {
                leDataInputStream.read(array);
                for (int j = 0; j < n2; ++j) {
                    System.arraycopy(array, 0, array2, i + j * this.bpp, this.bpp);
                }
            }
            else {
                leDataInputStream.read(array2, i, n2 * this.bpp);
            }
        }
        if (this.format == 6407 || this.format == 6408) {
            swapBGR(array2, n, this.header.height(), this.bpp);
        }
        this.data = ByteBuffer.wrap(array2);
    }
    
    private void setupImage24_32(final GLProfile glProfile) {
        this.bpp = this.header.pixelDepth / 8;
        switch (this.header.pixelDepth) {
            case 24: {
                this.format = (glProfile.isGL2GL3() ? 32992 : 6407);
                break;
            }
            case 32: {
                boolean gl2GL3 = glProfile.isGL2GL3();
                if (!gl2GL3) {
                    final GLContext current = GLContext.getCurrent();
                    gl2GL3 = (null != current && current.isTextureFormatBGRA8888Available());
                }
                this.format = (gl2GL3 ? 32993 : 6408);
                break;
            }
            default: {
                assert false;
                break;
            }
        }
    }
    
    private static void swapBGR(final byte[] array, final int n, final int n2, final int n3) {
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; j += n3) {
                final int n4 = i * n + j;
                final byte b = array[n4 + 0];
                array[n4 + 0] = array[n4 + 2];
                array[n4 + 2] = b;
            }
        }
    }
    
    public int getWidth() {
        return this.header.width();
    }
    
    public int getHeight() {
        return this.header.height();
    }
    
    public int getGLFormat() {
        return this.format;
    }
    
    public int getBytesPerPixel() {
        return this.bpp;
    }
    
    public ByteBuffer getData() {
        return this.data;
    }
    
    public static TGAImage read(final GLProfile glProfile, final String s) throws IOException {
        return read(glProfile, new FileInputStream(s));
    }
    
    public static TGAImage read(final GLProfile glProfile, final InputStream inputStream) throws IOException {
        final LEDataInputStream leDataInputStream = new LEDataInputStream(new BufferedInputStream(inputStream));
        final TGAImage tgaImage = new TGAImage(new Header(leDataInputStream));
        tgaImage.decodeImage(glProfile, leDataInputStream);
        return tgaImage;
    }
    
    public void write(final String s) throws IOException {
        this.write(new File(s));
    }
    
    public void write(final File file) throws IOException {
        final FileOutputStream fileOutputStream = IOUtil.getFileOutputStream(file, true);
        final FileChannel channel = fileOutputStream.getChannel();
        final ByteBuffer allocate = ByteBuffer.allocate(this.header.size());
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        this.header.write(allocate);
        allocate.rewind();
        channel.write(allocate);
        channel.write(this.data);
        channel.force(true);
        channel.close();
        fileOutputStream.close();
        this.data.rewind();
    }
    
    public static TGAImage createFromData(final int n, final int n2, final boolean b, final boolean b2, final ByteBuffer data) {
        final Header header = new Header();
        header.imageType = 2;
        header.width = n;
        header.height = n2;
        header.pixelDepth = (byte)(b ? 32 : 24);
        header.imageDescriptor = (byte)(b2 ? 32 : 0);
        final TGAImage tgaImage = new TGAImage(header);
        tgaImage.data = data;
        return tgaImage;
    }
    
    public static class Header
    {
        public static final int TYPE_NEW = 0;
        public static final int TYPE_OLD = 1;
        public static final int TYPE_UNK = 2;
        public static final int NO_IMAGE = 0;
        public static final int UCOLORMAPPED = 1;
        public static final int UTRUECOLOR = 2;
        public static final int UBLACKWHITE = 3;
        public static final int COLORMAPPED = 9;
        public static final int TRUECOLOR = 10;
        public static final int BLACKWHITE = 11;
        public static final int ID_ATTRIBPERPIXEL = 15;
        public static final int ID_RIGHTTOLEFT = 16;
        public static final int ID_TOPTOBOTTOM = 32;
        public static final int ID_INTERLEAVE = 192;
        public static final int I_NOTINTERLEAVED = 0;
        public static final int I_TWOWAY = 1;
        public static final int I_FOURWAY = 2;
        private final int tgaType;
        private int idLength;
        private int colorMapType;
        private int imageType;
        private int firstEntryIndex;
        private int colorMapLength;
        private byte colorMapEntrySize;
        private int xOrigin;
        private int yOrigin;
        private int width;
        private int height;
        private byte pixelDepth;
        private byte imageDescriptor;
        private byte[] imageIDbuf;
        private String imageID;
        
        Header() {
            this.tgaType = 1;
        }
        
        Header(final LEDataInputStream leDataInputStream) throws IOException {
            this.tgaType = 1;
            this.idLength = leDataInputStream.readUnsignedByte();
            this.colorMapType = leDataInputStream.readUnsignedByte();
            this.imageType = leDataInputStream.readUnsignedByte();
            this.firstEntryIndex = leDataInputStream.readUnsignedShort();
            this.colorMapLength = leDataInputStream.readUnsignedShort();
            this.colorMapEntrySize = leDataInputStream.readByte();
            this.xOrigin = leDataInputStream.readUnsignedShort();
            this.yOrigin = leDataInputStream.readUnsignedShort();
            this.width = leDataInputStream.readUnsignedShort();
            this.height = leDataInputStream.readUnsignedShort();
            this.pixelDepth = leDataInputStream.readByte();
            this.imageDescriptor = leDataInputStream.readByte();
            if (this.idLength > 0) {
                leDataInputStream.read(this.imageIDbuf = new byte[this.idLength], 0, this.idLength);
                this.imageID = new String(this.imageIDbuf, "US-ASCII");
            }
        }
        
        public int tgaType() {
            return this.tgaType;
        }
        
        public int idLength() {
            return this.idLength;
        }
        
        public int colorMapType() {
            return this.colorMapType;
        }
        
        public int imageType() {
            return this.imageType;
        }
        
        public int firstEntryIndex() {
            return this.firstEntryIndex;
        }
        
        public int colorMapLength() {
            return this.colorMapLength;
        }
        
        public byte colorMapEntrySize() {
            return this.colorMapEntrySize;
        }
        
        public int xOrigin() {
            return this.xOrigin;
        }
        
        public int yOrigin() {
            return this.yOrigin;
        }
        
        public int width() {
            return this.width;
        }
        
        public int height() {
            return this.height;
        }
        
        public byte pixelDepth() {
            return this.pixelDepth;
        }
        
        public byte imageDescriptor() {
            return this.imageDescriptor;
        }
        
        public byte attribPerPixel() {
            return (byte)(this.imageDescriptor & 0xF);
        }
        
        public boolean rightToLeft() {
            return (this.imageDescriptor & 0x10) != 0x0;
        }
        
        public boolean topToBottom() {
            return (this.imageDescriptor & 0x20) != 0x0;
        }
        
        public byte interleave() {
            return (byte)((this.imageDescriptor & 0xC0) >> 6);
        }
        
        public byte[] imageIDbuf() {
            return this.imageIDbuf;
        }
        
        public String imageID() {
            return this.imageID;
        }
        
        @Override
        public String toString() {
            return "TGA Header  id length: " + this.idLength + " color map type: " + this.colorMapType + " image type: " + this.imageType + " first entry index: " + this.firstEntryIndex + " color map length: " + this.colorMapLength + " color map entry size: " + this.colorMapEntrySize + " x Origin: " + this.xOrigin + " y Origin: " + this.yOrigin + " width: " + this.width + " height: " + this.height + " pixel depth: " + this.pixelDepth + " image descriptor: " + this.imageDescriptor + ((this.imageIDbuf == null) ? "" : (" ID String: " + this.imageID));
        }
        
        public int size() {
            return 18 + this.idLength;
        }
        
        private void write(final ByteBuffer byteBuffer) {
            byteBuffer.put((byte)this.idLength);
            byteBuffer.put((byte)this.colorMapType);
            byteBuffer.put((byte)this.imageType);
            byteBuffer.putShort((short)this.firstEntryIndex);
            byteBuffer.putShort((short)this.colorMapLength);
            byteBuffer.put(this.colorMapEntrySize);
            byteBuffer.putShort((short)this.xOrigin);
            byteBuffer.putShort((short)this.yOrigin);
            byteBuffer.putShort((short)this.width);
            byteBuffer.putShort((short)this.height);
            byteBuffer.put(this.pixelDepth);
            byteBuffer.put(this.imageDescriptor);
            if (this.idLength > 0) {
                try {
                    byteBuffer.put(this.imageID.getBytes("US-ASCII"));
                }
                catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
