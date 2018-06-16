// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import jogamp.opengl.util.pngj.chunks.PngChunkTRNS;
import jogamp.opengl.util.pngj.chunks.PngChunkPLTE;

public class ImageLineHelper
{
    private static final double BIG_VALUE = 8.988465674311579E307;
    private static final double BIG_VALUE_NEG = -8.988465674311579E307;
    
    public static int[] palette2rgb(ImageLine unpackToNewImageLine, final PngChunkPLTE pngChunkPLTE, final PngChunkTRNS pngChunkTRNS, int[] array) {
        final boolean b = pngChunkTRNS != null;
        final int n = b ? 4 : 3;
        final int n2 = unpackToNewImageLine.imgInfo.cols * n;
        if (array == null || array.length < n2) {
            array = new int[n2];
        }
        if (!unpackToNewImageLine.samplesUnpacked) {
            unpackToNewImageLine = unpackToNewImageLine.unpackToNewImageLine();
        }
        final boolean b2 = unpackToNewImageLine.sampleType == ImageLine.SampleType.BYTE;
        final int n3 = (pngChunkTRNS != null) ? pngChunkTRNS.getPalletteAlpha().length : 0;
        for (int i = 0; i < unpackToNewImageLine.imgInfo.cols; ++i) {
            final int n4 = b2 ? (unpackToNewImageLine.scanlineb[i] & 0xFF) : unpackToNewImageLine.scanline[i];
            pngChunkPLTE.getEntryRgb(n4, array, i * n);
            if (b) {
                array[i * n + 3] = ((n4 < n3) ? pngChunkTRNS.getPalletteAlpha()[n4] : 255);
            }
        }
        return array;
    }
    
    public static int[] palette2rgb(final ImageLine imageLine, final PngChunkPLTE pngChunkPLTE, final int[] array) {
        return palette2rgb(imageLine, pngChunkPLTE, null, array);
    }
    
    public static String infoFirstLastPixels(final ImageLine imageLine) {
        return (imageLine.imgInfo.channels == 1) ? String.format("first=(%d) last=(%d)", imageLine.scanline[0], imageLine.scanline[imageLine.scanline.length - 1]) : String.format("first=(%d %d %d) last=(%d %d %d)", imageLine.scanline[0], imageLine.scanline[1], imageLine.scanline[2], imageLine.scanline[imageLine.scanline.length - imageLine.imgInfo.channels], imageLine.scanline[imageLine.scanline.length - imageLine.imgInfo.channels + 1], imageLine.scanline[imageLine.scanline.length - imageLine.imgInfo.channels + 2]);
    }
    
    public static String infoFull(final ImageLine imageLine) {
        return "row=" + imageLine.getRown() + " " + new ImageLineStats(imageLine).toString() + "\n  " + infoFirstLastPixels(imageLine);
    }
    
    public static int getPixelRGB8(final ImageLine imageLine, final int n) {
        final int n2 = n * imageLine.channels;
        return (imageLine.scanline[n2] << 16) + (imageLine.scanline[n2 + 1] << 8) + imageLine.scanline[n2 + 2];
    }
    
    public static int getPixelARGB8(final ImageLine imageLine, final int n) {
        final int n2 = n * imageLine.channels;
        return (imageLine.scanline[n2 + 3] << 24) + (imageLine.scanline[n2] << 16) + (imageLine.scanline[n2 + 1] << 8) + imageLine.scanline[n2 + 2];
    }
    
    public static void setPixelsRGB8(final ImageLine imageLine, final int[] array) {
        int i = 0;
        int n = 0;
        while (i < imageLine.imgInfo.cols) {
            imageLine.scanline[n++] = (array[i] >> 16 & 0xFF);
            imageLine.scanline[n++] = (array[i] >> 8 & 0xFF);
            imageLine.scanline[n++] = (array[i] & 0xFF);
            ++i;
        }
    }
    
    public static void setPixelRGB8(final ImageLine imageLine, int n, final int n2, final int n3, final int n4) {
        n *= imageLine.channels;
        imageLine.scanline[n++] = n2;
        imageLine.scanline[n++] = n3;
        imageLine.scanline[n] = n4;
    }
    
    public static void setPixelRGB8(final ImageLine imageLine, final int n, final int n2) {
        setPixelRGB8(imageLine, n, n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF);
    }
    
    public static void setPixelsRGBA8(final ImageLine imageLine, final int[] array) {
        int i = 0;
        int n = 0;
        while (i < imageLine.imgInfo.cols) {
            imageLine.scanline[n++] = (array[i] >> 16 & 0xFF);
            imageLine.scanline[n++] = (array[i] >> 8 & 0xFF);
            imageLine.scanline[n++] = (array[i] & 0xFF);
            imageLine.scanline[n++] = (array[i] >> 24 & 0xFF);
            ++i;
        }
    }
    
    public static void setPixelRGBA8(final ImageLine imageLine, int n, final int n2, final int n3, final int n4, final int n5) {
        n *= imageLine.channels;
        imageLine.scanline[n++] = n2;
        imageLine.scanline[n++] = n3;
        imageLine.scanline[n++] = n4;
        imageLine.scanline[n] = n5;
    }
    
    public static void setPixelRGBA8(final ImageLine imageLine, final int n, final int n2) {
        setPixelRGBA8(imageLine, n, n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF, n2 >> 24 & 0xFF);
    }
    
    public static void setValD(final ImageLine imageLine, final int n, final double n2) {
        imageLine.scanline[n] = double2int(imageLine, n2);
    }
    
    public static int interpol(final int n, final int n2, final int n3, final int n4, final double n5, final double n6) {
        return (int)((n * (1.0 - n5) + n2 * n5) * (1.0 - n6) + (n3 * (1.0 - n5) + n4 * n5) * n6 + 0.5);
    }
    
    public static double int2double(final ImageLine imageLine, final int n) {
        return (imageLine.bitDepth == 16) ? (n / 65535.0) : (n / 255.0);
    }
    
    public static double int2doubleClamped(final ImageLine imageLine, final int n) {
        final double n2 = (imageLine.bitDepth == 16) ? (n / 65535.0) : (n / 255.0);
        return (n2 <= 0.0) ? 0.0 : ((n2 >= 1.0) ? 1.0 : n2);
    }
    
    public static int double2int(final ImageLine imageLine, double n) {
        n = ((n <= 0.0) ? 0.0 : ((n >= 1.0) ? 1.0 : n));
        return (imageLine.bitDepth == 16) ? ((int)(n * 65535.0 + 0.5)) : ((int)(n * 255.0 + 0.5));
    }
    
    public static int double2intClamped(final ImageLine imageLine, double n) {
        n = ((n <= 0.0) ? 0.0 : ((n >= 1.0) ? 1.0 : n));
        return (imageLine.bitDepth == 16) ? ((int)(n * 65535.0 + 0.5)) : ((int)(n * 255.0 + 0.5));
    }
    
    public static int clampTo_0_255(final int n) {
        return (n > 255) ? 255 : ((n < 0) ? 0 : n);
    }
    
    public static int clampTo_0_65535(final int n) {
        return (n > 65535) ? 65535 : ((n < 0) ? 0 : n);
    }
    
    public static int clampTo_128_127(final int n) {
        return (n > 127) ? 127 : ((n < -128) ? -128 : n);
    }
    
    public static int[] unpack(final ImageInfo imageInfo, final int[] array, int[] array2, final boolean b) {
        final int samplesPerRow = imageInfo.samplesPerRow;
        final int samplesPerRowPacked = imageInfo.samplesPerRowPacked;
        if (array2 == null || array2.length < samplesPerRow) {
            array2 = new int[samplesPerRow];
        }
        if (imageInfo.packed) {
            ImageLine.unpackInplaceInt(imageInfo, array, array2, b);
        }
        else {
            System.arraycopy(array, 0, array2, 0, samplesPerRowPacked);
        }
        return array2;
    }
    
    public static byte[] unpack(final ImageInfo imageInfo, final byte[] array, byte[] array2, final boolean b) {
        final int samplesPerRow = imageInfo.samplesPerRow;
        final int samplesPerRowPacked = imageInfo.samplesPerRowPacked;
        if (array2 == null || array2.length < samplesPerRow) {
            array2 = new byte[samplesPerRow];
        }
        if (imageInfo.packed) {
            ImageLine.unpackInplaceByte(imageInfo, array, array2, b);
        }
        else {
            System.arraycopy(array, 0, array2, 0, samplesPerRowPacked);
        }
        return array2;
    }
    
    public static int[] pack(final ImageInfo imageInfo, final int[] array, int[] array2, final boolean b) {
        final int samplesPerRowPacked = imageInfo.samplesPerRowPacked;
        if (array2 == null || array2.length < samplesPerRowPacked) {
            array2 = new int[samplesPerRowPacked];
        }
        if (imageInfo.packed) {
            ImageLine.packInplaceInt(imageInfo, array, array2, b);
        }
        else {
            System.arraycopy(array, 0, array2, 0, samplesPerRowPacked);
        }
        return array2;
    }
    
    public static byte[] pack(final ImageInfo imageInfo, final byte[] array, byte[] array2, final boolean b) {
        final int samplesPerRowPacked = imageInfo.samplesPerRowPacked;
        if (array2 == null || array2.length < samplesPerRowPacked) {
            array2 = new byte[samplesPerRowPacked];
        }
        if (imageInfo.packed) {
            ImageLine.packInplaceByte(imageInfo, array, array2, b);
        }
        else {
            System.arraycopy(array, 0, array2, 0, samplesPerRowPacked);
        }
        return array2;
    }
    
    static int getMaskForPackedFormats(final int n) {
        if (n == 4) {
            return 240;
        }
        if (n == 2) {
            return 192;
        }
        return 128;
    }
    
    static int getMaskForPackedFormatsLs(final int n) {
        if (n == 4) {
            return 15;
        }
        if (n == 2) {
            return 3;
        }
        return 1;
    }
    
    static class ImageLineStats
    {
        public double[] prom;
        public double[] maxv;
        public double[] minv;
        public double promlum;
        public double maxlum;
        public double minlum;
        public double[] maxdif;
        public final int channels;
        
        @Override
        public String toString() {
            return (this.channels == 3) ? (String.format("prom=%.1f (%.1f %.1f %.1f) max=%.1f (%.1f %.1f %.1f) min=%.1f (%.1f %.1f %.1f)", this.promlum, this.prom[0], this.prom[1], this.prom[2], this.maxlum, this.maxv[0], this.maxv[1], this.maxv[2], this.minlum, this.minv[0], this.minv[1], this.minv[2]) + String.format(" maxdif=(%.1f %.1f %.1f)", this.maxdif[0], this.maxdif[1], this.maxdif[2])) : (String.format("prom=%.1f (%.1f %.1f %.1f %.1f) max=%.1f (%.1f %.1f %.1f %.1f) min=%.1f (%.1f %.1f %.1f %.1f)", this.promlum, this.prom[0], this.prom[1], this.prom[2], this.prom[3], this.maxlum, this.maxv[0], this.maxv[1], this.maxv[2], this.maxv[3], this.minlum, this.minv[0], this.minv[1], this.minv[2], this.minv[3]) + String.format(" maxdif=(%.1f %.1f %.1f %.1f)", this.maxdif[0], this.maxdif[1], this.maxdif[2], this.maxdif[3]));
        }
        
        public ImageLineStats(final ImageLine imageLine) {
            this.prom = new double[] { 0.0, 0.0, 0.0, 0.0 };
            this.maxv = new double[] { -8.988465674311579E307, -8.988465674311579E307, -8.988465674311579E307, -8.988465674311579E307 };
            this.minv = new double[] { 8.988465674311579E307, 8.988465674311579E307, 8.988465674311579E307, 8.988465674311579E307 };
            this.promlum = 0.0;
            this.maxlum = -8.988465674311579E307;
            this.minlum = 8.988465674311579E307;
            this.maxdif = new double[] { -8.988465674311579E307, -8.988465674311579E307, -8.988465674311579E307, 8.988465674311579E307 };
            this.channels = imageLine.channels;
            if (imageLine.channels < 3) {
                throw new PngjException("ImageLineStats only works for RGB - RGBA");
            }
            for (int i = 0; i < imageLine.imgInfo.cols; ++i) {
                double n = 0.0;
                for (int j = this.channels - 1; j >= 0; --j) {
                    final double int2double = ImageLineHelper.int2double(imageLine, imageLine.scanline[i * this.channels]);
                    if (j < 3) {
                        n += int2double;
                    }
                    final double[] prom = this.prom;
                    final int n2 = j;
                    prom[n2] += int2double;
                    if (int2double > this.maxv[j]) {
                        this.maxv[j] = int2double;
                    }
                    if (int2double < this.minv[j]) {
                        this.minv[j] = int2double;
                    }
                    if (i >= this.channels) {
                        final double abs = Math.abs(int2double - ImageLineHelper.int2double(imageLine, imageLine.scanline[i - this.channels]));
                        if (abs > this.maxdif[j]) {
                            this.maxdif[j] = abs;
                        }
                    }
                }
                this.promlum += n;
                if (n > this.maxlum) {
                    this.maxlum = n;
                }
                if (n < this.minlum) {
                    this.minlum = n;
                }
            }
            for (int k = 0; k < this.channels; ++k) {
                final double[] prom2 = this.prom;
                final int n3 = k;
                prom2[n3] /= imageLine.imgInfo.cols;
            }
            this.promlum /= imageLine.imgInfo.cols * 3.0;
            this.maxlum /= 3.0;
            this.minlum /= 3.0;
        }
    }
}
