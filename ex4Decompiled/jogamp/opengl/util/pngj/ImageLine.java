// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

public class ImageLine
{
    public final ImageInfo imgInfo;
    private int rown;
    public final int[] scanline;
    public final byte[] scanlineb;
    protected FilterType filterUsed;
    final int channels;
    final int bitDepth;
    final int elementsPerRow;
    public final SampleType sampleType;
    public final boolean samplesUnpacked;
    
    public ImageLine(final ImageInfo imageInfo) {
        this(imageInfo, SampleType.INT, false);
    }
    
    public ImageLine(final ImageInfo imageInfo, final SampleType sampleType, final boolean b) {
        this(imageInfo, sampleType, b, null, null);
    }
    
    ImageLine(final ImageInfo imgInfo, final SampleType sampleType, final boolean b, final int[] array, final byte[] array2) {
        this.rown = 0;
        this.imgInfo = imgInfo;
        this.channels = imgInfo.channels;
        this.bitDepth = imgInfo.bitDepth;
        this.filterUsed = FilterType.FILTER_UNKNOWN;
        this.sampleType = sampleType;
        this.samplesUnpacked = (b || !imgInfo.packed);
        this.elementsPerRow = (this.samplesUnpacked ? imgInfo.samplesPerRow : imgInfo.samplesPerRowPacked);
        if (sampleType == SampleType.INT) {
            this.scanline = ((array != null) ? array : new int[this.elementsPerRow]);
            this.scanlineb = null;
        }
        else {
            if (sampleType != SampleType.BYTE) {
                throw new PngjExceptionInternal("bad ImageLine initialization");
            }
            this.scanlineb = ((array2 != null) ? array2 : new byte[this.elementsPerRow]);
            this.scanline = null;
        }
        this.rown = -1;
    }
    
    public int getRown() {
        return this.rown;
    }
    
    public void setRown(final int rown) {
        this.rown = rown;
    }
    
    static void unpackInplaceInt(final ImageInfo imageInfo, final int[] array, final int[] array2, final boolean b) {
        final int bitDepth = imageInfo.bitDepth;
        if (bitDepth >= 8) {
            return;
        }
        final int maskForPackedFormatsLs = ImageLineHelper.getMaskForPackedFormatsLs(bitDepth);
        final int n = 8 - bitDepth;
        final int n2 = 8 * imageInfo.samplesPerRowPacked - bitDepth * imageInfo.samplesPerRow;
        int n3;
        int n4;
        if (n2 != 8) {
            n3 = maskForPackedFormatsLs << n2;
            n4 = n2;
        }
        else {
            n3 = maskForPackedFormatsLs;
            n4 = 0;
        }
        int i = imageInfo.samplesPerRow - 1;
        int n5 = imageInfo.samplesPerRowPacked - 1;
        while (i >= 0) {
            int n6 = (array[n5] & n3) >> n4;
            if (b) {
                n6 <<= n;
            }
            array2[i] = n6;
            n3 <<= bitDepth;
            n4 += bitDepth;
            if (n4 == 8) {
                n3 = maskForPackedFormatsLs;
                n4 = 0;
                --n5;
            }
            --i;
        }
    }
    
    static void packInplaceInt(final ImageInfo imageInfo, final int[] array, final int[] array2, final boolean b) {
        final int bitDepth = imageInfo.bitDepth;
        if (bitDepth >= 8) {
            return;
        }
        final int maskForPackedFormatsLs = ImageLineHelper.getMaskForPackedFormatsLs(bitDepth);
        final int n = 8 - bitDepth;
        final int n2 = 8 - bitDepth;
        int n3 = 8 - bitDepth;
        int n4 = array[0];
        array2[0] = 0;
        if (b) {
            n4 >>= n;
        }
        final int n5 = (n4 & maskForPackedFormatsLs) << n3;
        int n6 = 0;
        for (int i = 0; i < imageInfo.samplesPerRow; ++i) {
            int n7 = array[i];
            if (b) {
                n7 >>= n;
            }
            final int n8 = n6;
            array2[n8] |= (n7 & maskForPackedFormatsLs) << n3;
            n3 -= bitDepth;
            if (n3 < 0) {
                n3 = n2;
                ++n6;
                array2[n6] = 0;
            }
        }
        final int n9 = 0;
        array2[n9] |= n5;
    }
    
    static void unpackInplaceByte(final ImageInfo imageInfo, final byte[] array, final byte[] array2, final boolean b) {
        final int bitDepth = imageInfo.bitDepth;
        if (bitDepth >= 8) {
            return;
        }
        final int maskForPackedFormatsLs = ImageLineHelper.getMaskForPackedFormatsLs(bitDepth);
        final int n = 8 - bitDepth;
        final int n2 = 8 * imageInfo.samplesPerRowPacked - bitDepth * imageInfo.samplesPerRow;
        int n3;
        int n4;
        if (n2 != 8) {
            n3 = maskForPackedFormatsLs << n2;
            n4 = n2;
        }
        else {
            n3 = maskForPackedFormatsLs;
            n4 = 0;
        }
        int i = imageInfo.samplesPerRow - 1;
        int n5 = imageInfo.samplesPerRowPacked - 1;
        while (i >= 0) {
            int n6 = (array[n5] & n3) >> n4;
            if (b) {
                n6 <<= n;
            }
            array2[i] = (byte)n6;
            n3 <<= bitDepth;
            n4 += bitDepth;
            if (n4 == 8) {
                n3 = maskForPackedFormatsLs;
                n4 = 0;
                --n5;
            }
            --i;
        }
    }
    
    static void packInplaceByte(final ImageInfo imageInfo, final byte[] array, final byte[] array2, final boolean b) {
        final int bitDepth = imageInfo.bitDepth;
        if (bitDepth >= 8) {
            return;
        }
        final int maskForPackedFormatsLs = ImageLineHelper.getMaskForPackedFormatsLs(bitDepth);
        final int n = 8 - bitDepth;
        final int n2 = 8 - bitDepth;
        int n3 = 8 - bitDepth;
        int n4 = array[0];
        array2[0] = 0;
        if (b) {
            n4 >>= n;
        }
        final int n5 = (n4 & maskForPackedFormatsLs) << n3;
        int n6 = 0;
        for (int i = 0; i < imageInfo.samplesPerRow; ++i) {
            int n7 = array[i];
            if (b) {
                n7 >>= n;
            }
            final int n8 = n6;
            array2[n8] |= (byte)((n7 & maskForPackedFormatsLs) << n3);
            n3 -= bitDepth;
            if (n3 < 0) {
                n3 = n2;
                ++n6;
                array2[n6] = 0;
            }
        }
        final int n9 = 0;
        array2[n9] |= (byte)n5;
    }
    
    public ImageLine unpackToNewImageLine() {
        final ImageLine imageLine = new ImageLine(this.imgInfo, this.sampleType, true);
        if (this.sampleType == SampleType.INT) {
            unpackInplaceInt(this.imgInfo, this.scanline, imageLine.scanline, false);
        }
        else {
            unpackInplaceByte(this.imgInfo, this.scanlineb, imageLine.scanlineb, false);
        }
        return imageLine;
    }
    
    public ImageLine packToNewImageLine() {
        final ImageLine imageLine = new ImageLine(this.imgInfo, this.sampleType, false);
        if (this.sampleType == SampleType.INT) {
            packInplaceInt(this.imgInfo, this.scanline, imageLine.scanline, false);
        }
        else {
            packInplaceByte(this.imgInfo, this.scanlineb, imageLine.scanlineb, false);
        }
        return imageLine;
    }
    
    public FilterType getFilterUsed() {
        return this.filterUsed;
    }
    
    public void setFilterUsed(final FilterType filterUsed) {
        this.filterUsed = filterUsed;
    }
    
    public int[] getScanlineInt() {
        return this.scanline;
    }
    
    public byte[] getScanlineByte() {
        return this.scanlineb;
    }
    
    @Override
    public String toString() {
        return "row=" + this.rown + " cols=" + this.imgInfo.cols + " bpc=" + this.imgInfo.bitDepth + " size=" + this.scanline.length;
    }
    
    public static void showLineInfo(final ImageLine imageLine) {
        System.out.println(imageLine);
        System.out.println(new ImageLineHelper.ImageLineStats(imageLine));
        System.out.println(ImageLineHelper.infoFirstLastPixels(imageLine));
    }
    
    public enum SampleType
    {
        INT, 
        BYTE;
    }
}
