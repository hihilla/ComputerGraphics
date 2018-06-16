// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import jogamp.opengl.Debug;
import java.nio.IntBuffer;
import java.io.Closeable;
import com.jogamp.common.util.IOUtil;
import jogamp.opengl.util.pngj.PngWriter;
import java.io.OutputStream;
import com.jogamp.nativewindow.util.PixelFormatUtil;
import java.io.IOException;
import jogamp.opengl.util.pngj.ImageLine;
import jogamp.opengl.util.pngj.chunks.PngChunkTRNS;
import jogamp.opengl.util.pngj.chunks.PngChunkPLTE;
import jogamp.opengl.util.pngj.ImageInfo;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.nativewindow.util.Dimension;
import jogamp.opengl.util.pngj.ImageLineHelper;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import jogamp.opengl.util.pngj.PngReader;
import java.io.BufferedInputStream;
import com.jogamp.nativewindow.util.PixelFormat;
import java.io.InputStream;
import com.jogamp.nativewindow.util.PixelRectangle;

public class PNGPixelRect extends PixelRectangle.GenericPixelRect
{
    private static final boolean DEBUG;
    private final double[] dpi;
    
    public static PNGPixelRect read(final InputStream inputStream, final PixelFormat pixelFormat, final boolean b, final int n, final boolean b2) throws IOException {
        final PngReader pngReader = new PngReader((inputStream instanceof BufferedInputStream) ? inputStream : new BufferedInputStream(inputStream), null);
        final ImageInfo imgInfo = pngReader.imgInfo;
        final PngChunkPLTE plte = pngReader.getMetadata().getPLTE();
        final PngChunkTRNS trns = pngReader.getMetadata().getTRNS();
        final boolean indexed = imgInfo.indexed;
        final boolean b3 = indexed ? (trns != null) : imgInfo.alpha;
        if (PNGPixelRect.DEBUG) {
            System.err.println("PNGPixelRect: " + imgInfo);
        }
        final int n2 = indexed ? (b3 ? 4 : 3) : imgInfo.channels;
        final boolean b4 = 2 == n2 && imgInfo.greyscale && imgInfo.alpha;
        if (n2 == 0 && 3 != n2 && 4 != n2 && !b4) {
            throw new RuntimeException("PNGPixelRect can only handle Lum/RGB/RGBA [1/3/4 channels] or Lum+A (GA) images for now. Channels " + n2 + " Paletted: " + indexed);
        }
        final int n3 = indexed ? n2 : imgInfo.bytesPixel;
        if (n3 == 0 && 3 != n3 && 4 != n3 && !b4) {
            throw new RuntimeException("PNGPixelRect can only handle Lum/RGB/RGBA [1/3/4 bpp] images for now. BytesPerPixel " + n3);
        }
        if (n2 != n3) {
            throw new RuntimeException("PNGPixelRect currently only handles Channels [1/3/4] == BytePerPixel [1/3/4], channels: " + n2 + ", bytesPerPixel " + n3);
        }
        final int cols = imgInfo.cols;
        final int rows = imgInfo.rows;
        final double[] dpi = pngReader.getMetadata().getDpi();
        final double n4 = dpi[0];
        final double n5 = dpi[1];
        PixelFormat pixelFormat2 = null;
        if (indexed) {
            if (b3) {
                pixelFormat2 = PixelFormat.RGBA8888;
            }
            else {
                pixelFormat2 = PixelFormat.RGB888;
            }
        }
        else {
            switch (n2) {
                case 1: {
                    pixelFormat2 = PixelFormat.LUMINANCE;
                    break;
                }
                case 2: {
                    pixelFormat2 = (b4 ? PixelFormat.LUMINANCE : null);
                    break;
                }
                case 3: {
                    pixelFormat2 = PixelFormat.RGB888;
                    break;
                }
                case 4: {
                    pixelFormat2 = PixelFormat.RGBA8888;
                    break;
                }
                default: {
                    pixelFormat2 = null;
                    break;
                }
            }
            if (null == pixelFormat2) {
                throw new InternalError("XXX: channels: " + n2 + ", bytesPerPixel " + n3);
            }
        }
        PixelFormat bgra8888;
        if (null == pixelFormat) {
            if (b4) {
                bgra8888 = PixelFormat.BGRA8888;
            }
            else {
                bgra8888 = pixelFormat2;
            }
        }
        else {
            bgra8888 = pixelFormat;
        }
        final int max = Math.max(n, bgra8888.comp.bytesPerPixel() * cols);
        final ByteBuffer byteBuffer = b ? Buffers.newDirectByteBuffer(max * rows) : ByteBuffer.allocate(max * rows);
        final int n6 = max * rows;
        if (byteBuffer.limit() < n6) {
            throw new IndexOutOfBoundsException("Dest buffer has insufficient bytes left, needs " + n6 + ": " + byteBuffer);
        }
        int[] palette2rgb = (int[])(indexed ? new int[cols * n2] : null);
        if (PNGPixelRect.DEBUG) {
            System.err.println("PNGPixelRect: indexed " + indexed + ", alpha " + b3 + ", grayscale " + imgInfo.greyscale + ", channels " + n2 + "/" + imgInfo.channels + ", bytesPerPixel " + n3 + "/" + imgInfo.bytesPixel + ", grayAlpha " + b4 + ", pixels " + cols + "x" + rows + ", dpi " + n4 + "x" + n5 + ", format " + pixelFormat2);
            System.err.println("PNGPixelRect: destFormat " + bgra8888 + " (" + pixelFormat + ", fast-path " + (bgra8888 == pixelFormat2) + "), destDirectBuffer " + b + ", destIsGLOriented (flip) " + b2);
            System.err.println("PNGPixelRect: destStrideInBytes " + max + " (destMinStrideInBytes " + n + ")");
        }
        for (int i = 0; i < rows; ++i) {
            final ImageLine row = pngReader.readRow(i);
            int n7 = 0;
            int n8 = b2 ? ((rows - 1 - i) * max) : (i * max);
            if (indexed) {
                for (int j = cols - 1; j >= 0; --j) {
                    palette2rgb = ImageLineHelper.palette2rgb(row, plte, trns, palette2rgb);
                    n8 = getPixelRGBA8ToAny(bgra8888, byteBuffer, n8, palette2rgb, n7, b3);
                    n7 += n3;
                }
            }
            else if (n2 != 0) {
                for (int k = cols - 1; k >= 0; --k) {
                    n8 = getPixelLUMToAny(bgra8888, byteBuffer, n8, (byte)row.scanline[n7++], (byte)(-1));
                }
            }
            else if (b4) {
                for (int l = cols - 1; l >= 0; --l) {
                    n8 = getPixelLUMToAny(bgra8888, byteBuffer, n8, (byte)row.scanline[n7++], (byte)row.scanline[n7++]);
                }
            }
            else if (pixelFormat2 == bgra8888) {
                for (int n9 = cols - 1; n9 >= 0; --n9) {
                    n8 = getPixelRGBSame(byteBuffer, n8, row.scanline, n7, n3);
                    n7 += n3;
                }
            }
            else {
                for (int n10 = cols - 1; n10 >= 0; --n10) {
                    n8 = getPixelRGBA8ToAny(bgra8888, byteBuffer, n8, row.scanline, n7, b3);
                    n7 += n3;
                }
            }
        }
        pngReader.end();
        return new PNGPixelRect(bgra8888, new Dimension(cols, rows), max, b2, byteBuffer, n4, n5);
    }
    
    private static final int getPixelLUMToAny(final PixelFormat pixelFormat, final ByteBuffer byteBuffer, int n, final byte b, final byte b2) {
        switch (pixelFormat) {
            case LUMINANCE: {
                byteBuffer.put(n++, b);
                break;
            }
            case BGR888:
            case RGB888: {
                byteBuffer.put(n++, b);
                byteBuffer.put(n++, b);
                byteBuffer.put(n++, b);
                break;
            }
            case ABGR8888:
            case ARGB8888: {
                byteBuffer.put(n++, b2);
                byteBuffer.put(n++, b);
                byteBuffer.put(n++, b);
                byteBuffer.put(n++, b);
                break;
            }
            case BGRA8888:
            case RGBA8888: {
                byteBuffer.put(n++, b);
                byteBuffer.put(n++, b);
                byteBuffer.put(n++, b);
                byteBuffer.put(n++, b2);
                break;
            }
            default: {
                throw new InternalError("Unhandled format " + pixelFormat);
            }
        }
        return n;
    }
    
    private static final int getPixelRGBA8ToAny(final PixelFormat pixelFormat, final ByteBuffer byteBuffer, int n, final int[] array, final int n2, final boolean b) {
        final int convertToInt32 = PixelFormatUtil.convertToInt32(pixelFormat, (byte)array[n2], (byte)array[n2 + 1], (byte)array[n2 + 2], (byte)(b ? ((byte)array[n2 + 3]) : -1));
        final int bytesPerPixel = pixelFormat.comp.bytesPerPixel();
        byteBuffer.put(n++, (byte)convertToInt32);
        if (1 < bytesPerPixel) {
            byteBuffer.put(n++, (byte)(convertToInt32 >>> 8));
            byteBuffer.put(n++, (byte)(convertToInt32 >>> 16));
            if (4 == bytesPerPixel) {
                byteBuffer.put(n++, (byte)(convertToInt32 >>> 24));
            }
        }
        return n;
    }
    
    private static final int getPixelRGBSame(final ByteBuffer byteBuffer, int n, final int[] array, final int n2, final int n3) {
        byteBuffer.put(n++, (byte)array[n2]);
        if (1 < n3) {
            byteBuffer.put(n++, (byte)array[n2 + 1]);
            byteBuffer.put(n++, (byte)array[n2 + 2]);
            if (4 == n3) {
                byteBuffer.put(n++, (byte)array[n2 + 3]);
            }
        }
        return n;
    }
    
    private int setPixelRGBA8(final ImageLine imageLine, final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final boolean b) {
        final int n4 = b ? 3 : 2;
        if (byteBuffer.limit() <= n2 + n4) {
            throw new IndexOutOfBoundsException("Buffer has unsufficient bytes left, needs [" + n2 + ".." + (n2 + n4) + "]: " + byteBuffer);
        }
        final int convertToInt32 = PixelFormatUtil.convertToInt32(b ? PixelFormat.RGBA8888 : PixelFormat.RGB888, this.pixelformat, byteBuffer, n2);
        imageLine.scanline[n] = (0xFF & convertToInt32);
        imageLine.scanline[n + 1] = (0xFF & convertToInt32 >>> 8);
        imageLine.scanline[n + 2] = (0xFF & convertToInt32 >>> 16);
        if (b) {
            imageLine.scanline[n + 3] = (0xFF & convertToInt32 >>> 24);
        }
        return n2 + this.pixelformat.comp.bytesPerPixel();
    }
    
    private static void setPixelRGBA8(final PixelFormat pixelFormat, final ImageLine imageLine, final int n, final int n2, final int n3, final boolean b) {
        final int convertToInt32 = PixelFormatUtil.convertToInt32(b ? PixelFormat.RGBA8888 : PixelFormat.RGB888, pixelFormat, n2);
        imageLine.scanline[n] = (0xFF & convertToInt32);
        imageLine.scanline[n + 1] = (0xFF & convertToInt32 >>> 8);
        imageLine.scanline[n + 2] = (0xFF & convertToInt32 >>> 16);
        if (b) {
            imageLine.scanline[n + 3] = (0xFF & convertToInt32 >>> 24);
        }
    }
    
    public PNGPixelRect(final PixelFormat pixelFormat, final DimensionImmutable dimensionImmutable, final int n, final boolean b, final ByteBuffer byteBuffer, final double n2, final double n3) {
        super(pixelFormat, dimensionImmutable, n, b, byteBuffer);
        this.dpi = new double[] { n2, n3 };
    }
    
    public PNGPixelRect(final PixelRectangle pixelRectangle, final double n, final double n2) {
        super(pixelRectangle);
        this.dpi = new double[] { n, n2 };
    }
    
    public double[] getDpi() {
        return this.dpi;
    }
    
    public void write(final OutputStream outputStream, final boolean b) throws IOException {
        final int width = this.size.getWidth();
        final int height = this.size.getHeight();
        final int bytesPerPixel = this.pixelformat.comp.bytesPerPixel();
        final ImageInfo imageInfo = new ImageInfo(width, height, 8, 4 == bytesPerPixel, bytesPerPixel, false);
        try {
            final PngWriter pngWriter = new PngWriter(outputStream, imageInfo);
            pngWriter.getMetadata().setDpi(this.dpi[0], this.dpi[1]);
            pngWriter.getMetadata().setTimeNow(0);
            pngWriter.getMetadata().setText("Title", "JogAmp PNGPixelRect");
            final boolean b2 = 4 == bytesPerPixel;
            final ImageLine imageLine = new ImageLine(imageInfo);
            for (int i = 0; i < height; ++i) {
                int setPixelRGBA8 = this.isGLOriented ? ((height - 1 - i) * this.strideInBytes) : (i * this.strideInBytes);
                int n = 0;
                if (bytesPerPixel != 0) {
                    for (int j = width - 1; j >= 0; --j) {
                        imageLine.scanline[n++] = this.pixels.get(setPixelRGBA8++);
                    }
                }
                else {
                    for (int k = width - 1; k >= 0; --k) {
                        setPixelRGBA8 = this.setPixelRGBA8(imageLine, n, this.pixels, setPixelRGBA8, bytesPerPixel, b2);
                        n += bytesPerPixel;
                    }
                }
                pngWriter.writeRow(imageLine, i);
            }
            pngWriter.end();
        }
        finally {
            if (b) {
                IOUtil.close(outputStream, false);
            }
        }
    }
    
    public static void write(final PixelFormat pixelFormat, final DimensionImmutable dimensionImmutable, int width, final boolean b, final IntBuffer intBuffer, final double n, final double n2, final OutputStream outputStream, final boolean b2) throws IOException {
        final int width2 = dimensionImmutable.getWidth();
        final int height = dimensionImmutable.getHeight();
        final int bytesPerPixel = pixelFormat.comp.bytesPerPixel();
        final ImageInfo imageInfo = new ImageInfo(width2, height, 8, 4 == bytesPerPixel, bytesPerPixel, false);
        if (0 != width) {
            if (width < dimensionImmutable.getWidth()) {
                throw new IllegalArgumentException("Invalid stride " + bytesPerPixel + ", must be greater than width " + dimensionImmutable.getWidth());
            }
        }
        else {
            width = dimensionImmutable.getWidth();
        }
        final int n3 = width * dimensionImmutable.getHeight();
        if (intBuffer.limit() < n3) {
            throw new IndexOutOfBoundsException("Dest buffer has insufficient pixels left, needs " + n3 + ": " + intBuffer);
        }
        try {
            final PngWriter pngWriter = new PngWriter(outputStream, imageInfo);
            pngWriter.getMetadata().setDpi(n, n2);
            pngWriter.getMetadata().setTimeNow(0);
            pngWriter.getMetadata().setText("Title", "JogAmp PNGPixelRect");
            final boolean b3 = 4 == bytesPerPixel;
            final ImageLine imageLine = new ImageLine(imageInfo);
            for (int i = 0; i < height; ++i) {
                int n4 = b ? ((height - 1 - i) * width) : (i * width);
                int n5 = 0;
                if (bytesPerPixel != 0) {
                    for (int j = width2 - 1; j >= 0; --j) {
                        imageLine.scanline[n5++] = intBuffer.get(n4++);
                    }
                }
                else {
                    for (int k = width2 - 1; k >= 0; --k) {
                        setPixelRGBA8(pixelFormat, imageLine, n5, intBuffer.get(n4++), bytesPerPixel, b3);
                        n5 += bytesPerPixel;
                    }
                }
                pngWriter.writeRow(imageLine, i);
            }
            pngWriter.end();
        }
        finally {
            if (b2) {
                IOUtil.close(outputStream, false);
            }
        }
    }
    
    static {
        DEBUG = Debug.debug("PNG");
    }
}
