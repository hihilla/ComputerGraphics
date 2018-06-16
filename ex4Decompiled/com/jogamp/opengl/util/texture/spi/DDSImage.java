// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import java.io.PrintStream;
import com.jogamp.common.nio.Buffers;
import java.io.FileOutputStream;
import java.nio.ByteOrder;
import com.jogamp.common.util.IOUtil;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;

public class DDSImage
{
    private FileInputStream fis;
    private FileChannel chan;
    private ByteBuffer buf;
    private Header header;
    public static final int DDSD_CAPS = 1;
    public static final int DDSD_HEIGHT = 2;
    public static final int DDSD_WIDTH = 4;
    public static final int DDSD_PITCH = 8;
    public static final int DDSD_BACKBUFFERCOUNT = 32;
    public static final int DDSD_ZBUFFERBITDEPTH = 64;
    public static final int DDSD_ALPHABITDEPTH = 128;
    public static final int DDSD_LPSURFACE = 2048;
    public static final int DDSD_PIXELFORMAT = 4096;
    public static final int DDSD_MIPMAPCOUNT = 131072;
    public static final int DDSD_LINEARSIZE = 524288;
    public static final int DDSD_DEPTH = 8388608;
    public static final int DDPF_ALPHAPIXELS = 1;
    public static final int DDPF_ALPHA = 2;
    public static final int DDPF_FOURCC = 4;
    public static final int DDPF_PALETTEINDEXED4 = 8;
    public static final int DDPF_PALETTEINDEXEDTO8 = 16;
    public static final int DDPF_PALETTEINDEXED8 = 32;
    public static final int DDPF_RGB = 64;
    public static final int DDPF_COMPRESSED = 128;
    public static final int DDPF_RGBTOYUV = 256;
    public static final int DDPF_YUV = 512;
    public static final int DDPF_ZBUFFER = 1024;
    public static final int DDPF_PALETTEINDEXED1 = 2048;
    public static final int DDPF_PALETTEINDEXED2 = 4096;
    public static final int DDPF_ZPIXELS = 8192;
    public static final int DDSCAPS_TEXTURE = 4096;
    public static final int DDSCAPS_MIPMAP = 4194304;
    public static final int DDSCAPS_COMPLEX = 8;
    public static final int DDSCAPS2_CUBEMAP = 512;
    public static final int DDSCAPS2_CUBEMAP_POSITIVEX = 1024;
    public static final int DDSCAPS2_CUBEMAP_NEGATIVEX = 2048;
    public static final int DDSCAPS2_CUBEMAP_POSITIVEY = 4096;
    public static final int DDSCAPS2_CUBEMAP_NEGATIVEY = 8192;
    public static final int DDSCAPS2_CUBEMAP_POSITIVEZ = 16384;
    public static final int DDSCAPS2_CUBEMAP_NEGATIVEZ = 32768;
    public static final int D3DFMT_UNKNOWN = 0;
    public static final int D3DFMT_R8G8B8 = 20;
    public static final int D3DFMT_A8R8G8B8 = 21;
    public static final int D3DFMT_X8R8G8B8 = 22;
    public static final int D3DFMT_DXT1 = 827611204;
    public static final int D3DFMT_DXT2 = 844388420;
    public static final int D3DFMT_DXT3 = 861165636;
    public static final int D3DFMT_DXT4 = 877942852;
    public static final int D3DFMT_DXT5 = 894720068;
    private static final int MAGIC = 542327876;
    
    public static DDSImage read(final String s) throws IOException {
        return read(new File(s));
    }
    
    public static DDSImage read(final File file) throws IOException {
        final DDSImage ddsImage = new DDSImage();
        ddsImage.readFromFile(file);
        return ddsImage;
    }
    
    public static DDSImage read(final ByteBuffer byteBuffer) throws IOException {
        final DDSImage ddsImage = new DDSImage();
        ddsImage.readFromBuffer(byteBuffer);
        return ddsImage;
    }
    
    public void close() {
        try {
            if (this.chan != null) {
                this.chan.close();
                this.chan = null;
            }
            if (this.fis != null) {
                this.fis.close();
                this.fis = null;
            }
            this.buf = null;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static DDSImage createFromData(final int n, final int n2, final int n3, final ByteBuffer[] array) throws IllegalArgumentException {
        final DDSImage ddsImage = new DDSImage();
        ddsImage.initFromData(n, n2, n3, array);
        return ddsImage;
    }
    
    @Deprecated
    public static boolean isDDSImage(InputStream inputStream) throws IOException {
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!inputStream.markSupported()) {
            throw new IOException("Can not test non-destructively whether given InputStream is a DDS image");
        }
        inputStream.mark(4);
        int n = 0;
        for (int i = 0; i < 4; ++i) {
            final int read = inputStream.read();
            if (read < 0) {
                inputStream.reset();
                return false;
            }
            n = (n >>> 8 | read << 24);
        }
        inputStream.reset();
        return n == 542327876;
    }
    
    public void write(final String s) throws IOException {
        this.write(new File(s));
    }
    
    public void write(final File file) throws IOException {
        final FileOutputStream fileOutputStream = IOUtil.getFileOutputStream(file, true);
        final FileChannel channel = fileOutputStream.getChannel();
        final ByteBuffer allocate = ByteBuffer.allocate(writtenSize());
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        this.header.write(allocate);
        allocate.rewind();
        channel.write(allocate);
        this.buf.position(writtenSize());
        channel.write(this.buf);
        channel.force(true);
        channel.close();
        fileOutputStream.close();
    }
    
    public boolean isSurfaceDescFlagSet(final int n) {
        return (this.header.flags & n) != 0x0;
    }
    
    public boolean isPixelFormatFlagSet(final int n) {
        return (this.header.pfFlags & n) != 0x0;
    }
    
    public int getPixelFormat() {
        if (this.isCompressed()) {
            return this.getCompressionFormat();
        }
        if (this.isPixelFormatFlagSet(64)) {
            if (this.isPixelFormatFlagSet(1)) {
                if (this.getDepth() == 32 && this.header.pfRBitMask == 16711680 && this.header.pfGBitMask == 65280 && this.header.pfBBitMask == 255 && this.header.pfABitMask == -16777216) {
                    return 21;
                }
            }
            else {
                if (this.getDepth() == 24 && this.header.pfRBitMask == 16711680 && this.header.pfGBitMask == 65280 && this.header.pfBBitMask == 255) {
                    return 20;
                }
                if (this.getDepth() == 32 && this.header.pfRBitMask == 16711680 && this.header.pfGBitMask == 65280 && this.header.pfBBitMask == 255) {
                    return 22;
                }
            }
        }
        return 0;
    }
    
    public boolean isCubemap() {
        return (this.header.ddsCaps1 & 0x8) != 0x0 && (this.header.ddsCaps2 & 0x200) != 0x0;
    }
    
    public boolean isCubemapSidePresent(final int n) {
        return this.isCubemap() && (this.header.ddsCaps2 & n) != 0x0;
    }
    
    public boolean isCompressed() {
        return this.isPixelFormatFlagSet(4);
    }
    
    public int getCompressionFormat() {
        return this.header.pfFourCC;
    }
    
    public int getWidth() {
        return this.header.width;
    }
    
    public int getHeight() {
        return this.header.height;
    }
    
    public int getDepth() {
        return this.header.pfRGBBitCount;
    }
    
    public int getNumMipMaps() {
        if (!this.isSurfaceDescFlagSet(131072)) {
            return 0;
        }
        return this.header.mipMapCountOrAux;
    }
    
    public ImageInfo getMipMap(final int n) {
        return this.getMipMap(0, n);
    }
    
    public ImageInfo getMipMap(final int n, final int n2) {
        if (!this.isCubemap() && n != 0) {
            throw new RuntimeException("Illegal side for 2D texture: " + n);
        }
        if (this.isCubemap() && !this.isCubemapSidePresent(n)) {
            throw new RuntimeException("Illegal side, side not present: " + n);
        }
        if (this.getNumMipMaps() > 0 && (n2 < 0 || n2 >= this.getNumMipMaps())) {
            throw new RuntimeException("Illegal mipmap number " + n2 + " (0.." + (this.getNumMipMaps() - 1) + ")");
        }
        int access$000 = writtenSize();
        if (this.isCubemap()) {
            access$000 += this.sideShiftInBytes(n);
        }
        for (int i = 0; i < n2; ++i) {
            access$000 += this.mipMapSizeInBytes(i);
        }
        this.buf.limit(access$000 + this.mipMapSizeInBytes(n2));
        this.buf.position(access$000);
        final ByteBuffer slice = this.buf.slice();
        this.buf.position(0);
        this.buf.limit(this.buf.capacity());
        return new ImageInfo(slice, this.mipMapWidth(n2), this.mipMapHeight(n2), this.isCompressed(), this.getCompressionFormat());
    }
    
    public ImageInfo[] getAllMipMaps() {
        return this.getAllMipMaps(0);
    }
    
    public ImageInfo[] getAllMipMaps(final int n) {
        int numMipMaps = this.getNumMipMaps();
        if (numMipMaps == 0) {
            numMipMaps = 1;
        }
        final ImageInfo[] array = new ImageInfo[numMipMaps];
        for (int i = 0; i < numMipMaps; ++i) {
            array[i] = this.getMipMap(n, i);
        }
        return array;
    }
    
    public static String getCompressionFormatName(int n) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            sb.append((char)(n & 0xFF));
            n >>= 8;
        }
        return sb.toString();
    }
    
    public static ByteBuffer allocateBlankBuffer(final int n, final int n2, final int n3) {
        int n4 = n * n2;
        switch (n3) {
            case 33776:
            case 33777: {
                n4 /= 2;
                break;
            }
            case 33778:
            case 33779: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Illegal OpenGL texture internal format " + n3);
            }
        }
        if (n4 == 0) {
            n4 = 1;
        }
        return Buffers.newDirectByteBuffer(n4);
    }
    
    public void debugPrint() {
        final PrintStream err = System.err;
        err.println("Compressed texture: " + this.isCompressed());
        if (this.isCompressed()) {
            final int compressionFormat = this.getCompressionFormat();
            err.println("Compression format: 0x" + Integer.toHexString(compressionFormat) + " (" + getCompressionFormatName(compressionFormat) + ")");
        }
        err.println("Width: " + this.header.width + " Height: " + this.header.height);
        err.println("header.pitchOrLinearSize: " + this.header.pitchOrLinearSize);
        err.println("header.pfRBitMask: 0x" + Integer.toHexString(this.header.pfRBitMask));
        err.println("header.pfGBitMask: 0x" + Integer.toHexString(this.header.pfGBitMask));
        err.println("header.pfBBitMask: 0x" + Integer.toHexString(this.header.pfBBitMask));
        err.println("SurfaceDesc flags:");
        if (!(false | this.printIfRecognized(err, this.header.flags, 1, "DDSD_CAPS") | this.printIfRecognized(err, this.header.flags, 2, "DDSD_HEIGHT") | this.printIfRecognized(err, this.header.flags, 4, "DDSD_WIDTH") | this.printIfRecognized(err, this.header.flags, 8, "DDSD_PITCH") | this.printIfRecognized(err, this.header.flags, 32, "DDSD_BACKBUFFERCOUNT") | this.printIfRecognized(err, this.header.flags, 64, "DDSD_ZBUFFERBITDEPTH") | this.printIfRecognized(err, this.header.flags, 128, "DDSD_ALPHABITDEPTH") | this.printIfRecognized(err, this.header.flags, 2048, "DDSD_LPSURFACE") | this.printIfRecognized(err, this.header.flags, 4096, "DDSD_PIXELFORMAT") | this.printIfRecognized(err, this.header.flags, 131072, "DDSD_MIPMAPCOUNT") | this.printIfRecognized(err, this.header.flags, 524288, "DDSD_LINEARSIZE") | this.printIfRecognized(err, this.header.flags, 8388608, "DDSD_DEPTH"))) {
            err.println("(none)");
        }
        err.println("Raw SurfaceDesc flags: 0x" + Integer.toHexString(this.header.flags));
        err.println("Pixel format flags:");
        if (!(false | this.printIfRecognized(err, this.header.pfFlags, 1, "DDPF_ALPHAPIXELS") | this.printIfRecognized(err, this.header.pfFlags, 2, "DDPF_ALPHA") | this.printIfRecognized(err, this.header.pfFlags, 4, "DDPF_FOURCC") | this.printIfRecognized(err, this.header.pfFlags, 8, "DDPF_PALETTEINDEXED4") | this.printIfRecognized(err, this.header.pfFlags, 16, "DDPF_PALETTEINDEXEDTO8") | this.printIfRecognized(err, this.header.pfFlags, 32, "DDPF_PALETTEINDEXED8") | this.printIfRecognized(err, this.header.pfFlags, 64, "DDPF_RGB") | this.printIfRecognized(err, this.header.pfFlags, 128, "DDPF_COMPRESSED") | this.printIfRecognized(err, this.header.pfFlags, 256, "DDPF_RGBTOYUV") | this.printIfRecognized(err, this.header.pfFlags, 512, "DDPF_YUV") | this.printIfRecognized(err, this.header.pfFlags, 1024, "DDPF_ZBUFFER") | this.printIfRecognized(err, this.header.pfFlags, 2048, "DDPF_PALETTEINDEXED1") | this.printIfRecognized(err, this.header.pfFlags, 4096, "DDPF_PALETTEINDEXED2") | this.printIfRecognized(err, this.header.pfFlags, 8192, "DDPF_ZPIXELS"))) {
            err.println("(none)");
        }
        err.println("Raw pixel format flags: 0x" + Integer.toHexString(this.header.pfFlags));
        err.println("Depth: " + this.getDepth());
        err.println("Number of mip maps: " + this.getNumMipMaps());
        final int pixelFormat = this.getPixelFormat();
        err.print("Pixel format: ");
        switch (pixelFormat) {
            case 20: {
                err.println("D3DFMT_R8G8B8");
                break;
            }
            case 21: {
                err.println("D3DFMT_A8R8G8B8");
                break;
            }
            case 22: {
                err.println("D3DFMT_X8R8G8B8");
                break;
            }
            case 827611204: {
                err.println("D3DFMT_DXT1");
                break;
            }
            case 844388420: {
                err.println("D3DFMT_DXT2");
                break;
            }
            case 861165636: {
                err.println("D3DFMT_DXT3");
                break;
            }
            case 877942852: {
                err.println("D3DFMT_DXT4");
                break;
            }
            case 894720068: {
                err.println("D3DFMT_DXT5");
                break;
            }
            case 0: {
                err.println("D3DFMT_UNKNOWN");
                break;
            }
            default: {
                err.println("(unknown pixel format " + pixelFormat + ")");
                break;
            }
        }
    }
    
    private void readFromFile(final File file) throws IOException {
        this.fis = new FileInputStream(file);
        this.chan = this.fis.getChannel();
        this.readFromBuffer(this.chan.map(FileChannel.MapMode.READ_ONLY, 0L, (int)file.length()));
    }
    
    private void readFromBuffer(final ByteBuffer buf) throws IOException {
        (this.buf = buf).order(ByteOrder.LITTLE_ENDIAN);
        (this.header = new Header()).read(buf);
        this.fixupHeader();
    }
    
    private void initFromData(final int pfFourCC, final int width, final int height, final ByteBuffer[] array) throws IllegalArgumentException {
        final int n = width * height;
        boolean b = false;
        int n2 = 0;
        int computeCompressedBlockSize = 0;
        switch (pfFourCC) {
            case 20: {
                n2 = n * 3;
                computeCompressedBlockSize = width * 3;
                break;
            }
            case 21: {
                n2 = n * 4;
                computeCompressedBlockSize = width * 4;
                break;
            }
            case 22: {
                n2 = n * 4;
                computeCompressedBlockSize = width * 4;
                break;
            }
            case 827611204:
            case 844388420:
            case 861165636:
            case 877942852:
            case 894720068: {
                n2 = (computeCompressedBlockSize = computeCompressedBlockSize(width, height, 1, pfFourCC));
                b = true;
                break;
            }
            default: {
                throw new IllegalArgumentException("d3dFormat must be one of the known formats");
            }
        }
        int computeBlockSize = n2;
        int n3 = width;
        int n4 = height;
        int n5 = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i].remaining() != computeBlockSize) {
                throw new IllegalArgumentException("Mipmap level " + i + " didn't match expected data size (expected " + computeBlockSize + ", got " + array[i].remaining() + ")");
            }
            if (n3 > 1) {
                n3 /= 2;
            }
            if (n4 > 1) {
                n4 /= 2;
            }
            computeBlockSize = computeBlockSize(n3, n4, 1, pfFourCC);
            n5 += array[i].remaining();
        }
        final ByteBuffer allocate = ByteBuffer.allocate(n5 + writtenSize());
        allocate.position(writtenSize());
        for (int j = 0; j < array.length; ++j) {
            allocate.put(array[j]);
        }
        this.buf = allocate;
        this.header = new Header();
        this.header.size = size();
        this.header.flags = 4103;
        if (array.length > 1) {
            final Header header = this.header;
            header.flags |= 0x20000;
            this.header.mipMapCountOrAux = array.length;
        }
        this.header.width = width;
        this.header.height = height;
        if (b) {
            final Header header2 = this.header;
            header2.flags |= 0x80000;
            final Header header3 = this.header;
            header3.pfFlags |= 0x4;
            this.header.pfFourCC = pfFourCC;
        }
        else {
            final Header header4 = this.header;
            header4.flags |= 0x8;
            final Header header5 = this.header;
            header5.pfFlags |= 0x40;
            switch (pfFourCC) {
                case 20: {
                    this.header.pfRGBBitCount = 24;
                    break;
                }
                case 21: {
                    this.header.pfRGBBitCount = 32;
                    final Header header6 = this.header;
                    header6.pfFlags |= 0x1;
                    break;
                }
                case 22: {
                    this.header.pfRGBBitCount = 32;
                    break;
                }
            }
            this.header.pfRBitMask = 16711680;
            this.header.pfGBitMask = 65280;
            this.header.pfBBitMask = 255;
            if (pfFourCC == 21) {
                this.header.pfABitMask = -16777216;
            }
        }
        this.header.pitchOrLinearSize = computeCompressedBlockSize;
        this.header.pfSize = pfSize();
    }
    
    private void fixupHeader() {
        if (this.isCompressed() && !this.isSurfaceDescFlagSet(524288)) {
            int backBufferCountOrDepth = this.header.backBufferCountOrDepth;
            if (backBufferCountOrDepth == 0) {
                backBufferCountOrDepth = 1;
            }
            this.header.pitchOrLinearSize = computeCompressedBlockSize(this.getWidth(), this.getHeight(), backBufferCountOrDepth, this.getCompressionFormat());
            final Header header = this.header;
            header.flags |= 0x80000;
        }
    }
    
    private static int computeCompressedBlockSize(final int n, final int n2, final int n3, final int n4) {
        final int n5 = (n + 3) / 4 * ((n2 + 3) / 4) * ((n3 + 3) / 4);
        int n6 = 0;
        switch (n4) {
            case 827611204: {
                n6 = n5 * 8;
                break;
            }
            default: {
                n6 = n5 * 16;
                break;
            }
        }
        return n6;
    }
    
    private static int computeBlockSize(final int n, final int n2, final int n3, final int n4) {
        int computeCompressedBlockSize = 0;
        switch (n4) {
            case 20: {
                computeCompressedBlockSize = n * n2 * 3;
                break;
            }
            case 21:
            case 22: {
                computeCompressedBlockSize = n * n2 * 4;
                break;
            }
            case 827611204:
            case 844388420:
            case 861165636:
            case 877942852:
            case 894720068: {
                computeCompressedBlockSize = computeCompressedBlockSize(n, n2, 1, n4);
                break;
            }
            default: {
                throw new IllegalArgumentException("d3dFormat must be one of the known formats");
            }
        }
        return computeCompressedBlockSize;
    }
    
    private int mipMapWidth(final int n) {
        int width = this.getWidth();
        for (int i = 0; i < n; ++i) {
            width >>= 1;
        }
        return Math.max(width, 1);
    }
    
    private int mipMapHeight(final int n) {
        int height = this.getHeight();
        for (int i = 0; i < n; ++i) {
            height >>= 1;
        }
        return Math.max(height, 1);
    }
    
    private int mipMapSizeInBytes(final int n) {
        final int mipMapWidth = this.mipMapWidth(n);
        final int mipMapHeight = this.mipMapHeight(n);
        if (this.isCompressed()) {
            return (mipMapWidth + 3) / 4 * ((mipMapHeight + 3) / 4) * ((this.getCompressionFormat() == 827611204) ? 8 : 16);
        }
        return mipMapWidth * mipMapHeight * (this.getDepth() / 8);
    }
    
    private int sideSizeInBytes() {
        int numMipMaps = this.getNumMipMaps();
        if (numMipMaps == 0) {
            numMipMaps = 1;
        }
        int n = 0;
        for (int i = 0; i < numMipMaps; ++i) {
            n += this.mipMapSizeInBytes(i);
        }
        return n;
    }
    
    private int sideShiftInBytes(final int n) {
        final int[] array = { 1024, 2048, 4096, 8192, 16384, 32768 };
        int n2 = 0;
        final int sideSizeInBytes = this.sideSizeInBytes();
        for (int i = 0; i < array.length; ++i) {
            if ((array[i] & n) != 0x0) {
                return n2;
            }
            n2 += sideSizeInBytes;
        }
        throw new RuntimeException("Illegal side: " + n);
    }
    
    private boolean printIfRecognized(final PrintStream printStream, final int n, final int n2, final String s) {
        if ((n & n2) != 0x0) {
            printStream.println(s);
            return true;
        }
        return false;
    }
    
    public static class ImageInfo
    {
        private final ByteBuffer data;
        private final int width;
        private final int height;
        private final boolean isCompressed;
        private final int compressionFormat;
        
        public ImageInfo(final ByteBuffer data, final int width, final int height, final boolean isCompressed, final int compressionFormat) {
            this.data = data;
            this.width = width;
            this.height = height;
            this.isCompressed = isCompressed;
            this.compressionFormat = compressionFormat;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        public ByteBuffer getData() {
            return this.data;
        }
        
        public boolean isCompressed() {
            return this.isCompressed;
        }
        
        public int getCompressionFormat() {
            if (!this.isCompressed()) {
                throw new RuntimeException("Should not call unless compressed");
            }
            return this.compressionFormat;
        }
    }
    
    static class Header
    {
        int size;
        int flags;
        int height;
        int width;
        int pitchOrLinearSize;
        int backBufferCountOrDepth;
        int mipMapCountOrAux;
        int alphaBitDepth;
        int reserved1;
        int surface;
        int colorSpaceLowValue;
        int colorSpaceHighValue;
        int destBltColorSpaceLowValue;
        int destBltColorSpaceHighValue;
        int srcOverlayColorSpaceLowValue;
        int srcOverlayColorSpaceHighValue;
        int srcBltColorSpaceLowValue;
        int srcBltColorSpaceHighValue;
        int pfSize;
        int pfFlags;
        int pfFourCC;
        int pfRGBBitCount;
        int pfRBitMask;
        int pfGBitMask;
        int pfBBitMask;
        int pfABitMask;
        int ddsCaps1;
        int ddsCaps2;
        int ddsCapsReserved1;
        int ddsCapsReserved2;
        int textureStage;
        
        void read(final ByteBuffer byteBuffer) throws IOException {
            final int int1 = byteBuffer.getInt();
            if (int1 != 542327876) {
                throw new IOException("Incorrect magic number 0x" + Integer.toHexString(int1) + " (expected " + 542327876 + ")");
            }
            this.size = byteBuffer.getInt();
            this.flags = byteBuffer.getInt();
            this.height = byteBuffer.getInt();
            this.width = byteBuffer.getInt();
            this.pitchOrLinearSize = byteBuffer.getInt();
            this.backBufferCountOrDepth = byteBuffer.getInt();
            this.mipMapCountOrAux = byteBuffer.getInt();
            this.alphaBitDepth = byteBuffer.getInt();
            this.reserved1 = byteBuffer.getInt();
            this.surface = byteBuffer.getInt();
            this.colorSpaceLowValue = byteBuffer.getInt();
            this.colorSpaceHighValue = byteBuffer.getInt();
            this.destBltColorSpaceLowValue = byteBuffer.getInt();
            this.destBltColorSpaceHighValue = byteBuffer.getInt();
            this.srcOverlayColorSpaceLowValue = byteBuffer.getInt();
            this.srcOverlayColorSpaceHighValue = byteBuffer.getInt();
            this.srcBltColorSpaceLowValue = byteBuffer.getInt();
            this.srcBltColorSpaceHighValue = byteBuffer.getInt();
            this.pfSize = byteBuffer.getInt();
            this.pfFlags = byteBuffer.getInt();
            this.pfFourCC = byteBuffer.getInt();
            this.pfRGBBitCount = byteBuffer.getInt();
            this.pfRBitMask = byteBuffer.getInt();
            this.pfGBitMask = byteBuffer.getInt();
            this.pfBBitMask = byteBuffer.getInt();
            this.pfABitMask = byteBuffer.getInt();
            this.ddsCaps1 = byteBuffer.getInt();
            this.ddsCaps2 = byteBuffer.getInt();
            this.ddsCapsReserved1 = byteBuffer.getInt();
            this.ddsCapsReserved2 = byteBuffer.getInt();
            this.textureStage = byteBuffer.getInt();
        }
        
        void write(final ByteBuffer byteBuffer) {
            byteBuffer.putInt(542327876);
            byteBuffer.putInt(this.size);
            byteBuffer.putInt(this.flags);
            byteBuffer.putInt(this.height);
            byteBuffer.putInt(this.width);
            byteBuffer.putInt(this.pitchOrLinearSize);
            byteBuffer.putInt(this.backBufferCountOrDepth);
            byteBuffer.putInt(this.mipMapCountOrAux);
            byteBuffer.putInt(this.alphaBitDepth);
            byteBuffer.putInt(this.reserved1);
            byteBuffer.putInt(this.surface);
            byteBuffer.putInt(this.colorSpaceLowValue);
            byteBuffer.putInt(this.colorSpaceHighValue);
            byteBuffer.putInt(this.destBltColorSpaceLowValue);
            byteBuffer.putInt(this.destBltColorSpaceHighValue);
            byteBuffer.putInt(this.srcOverlayColorSpaceLowValue);
            byteBuffer.putInt(this.srcOverlayColorSpaceHighValue);
            byteBuffer.putInt(this.srcBltColorSpaceLowValue);
            byteBuffer.putInt(this.srcBltColorSpaceHighValue);
            byteBuffer.putInt(this.pfSize);
            byteBuffer.putInt(this.pfFlags);
            byteBuffer.putInt(this.pfFourCC);
            byteBuffer.putInt(this.pfRGBBitCount);
            byteBuffer.putInt(this.pfRBitMask);
            byteBuffer.putInt(this.pfGBitMask);
            byteBuffer.putInt(this.pfBBitMask);
            byteBuffer.putInt(this.pfABitMask);
            byteBuffer.putInt(this.ddsCaps1);
            byteBuffer.putInt(this.ddsCaps2);
            byteBuffer.putInt(this.ddsCapsReserved1);
            byteBuffer.putInt(this.ddsCapsReserved2);
            byteBuffer.putInt(this.textureStage);
        }
        
        private static int size() {
            return 124;
        }
        
        private static int pfSize() {
            return 32;
        }
        
        private static int writtenSize() {
            return 128;
        }
    }
}
