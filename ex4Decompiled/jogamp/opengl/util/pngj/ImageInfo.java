// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

public class ImageInfo
{
    private static final int MAX_COLS_ROWS_VAL = 1000000;
    public final int cols;
    public final int rows;
    public final int bitDepth;
    public final int channels;
    public final boolean alpha;
    public final boolean greyscale;
    public final boolean indexed;
    public final boolean packed;
    public final int bitspPixel;
    public final int bytesPixel;
    public final int bytesPerRow;
    public final int samplesPerRow;
    public final int samplesPerRowPacked;
    
    public ImageInfo(final int n, final int n2, final int n3, final boolean b) {
        this(n, n2, n3, b, false, false);
    }
    
    public ImageInfo(final int cols, final int rows, final int bitDepth, final boolean alpha, final boolean greyscale, final boolean indexed) {
        this.cols = cols;
        this.rows = rows;
        this.alpha = alpha;
        this.indexed = indexed;
        this.greyscale = greyscale;
        if (this.greyscale && indexed) {
            throw new PngjException("palette and greyscale are mutually exclusive");
        }
        this.channels = ((greyscale || indexed) ? (alpha ? 2 : 1) : (alpha ? 4 : 3));
        this.bitDepth = bitDepth;
        this.packed = (bitDepth < 8);
        this.bitspPixel = this.channels * this.bitDepth;
        this.bytesPixel = (this.bitspPixel + 7) / 8;
        this.bytesPerRow = (this.bitspPixel * cols + 7) / 8;
        this.samplesPerRow = this.channels * this.cols;
        this.samplesPerRowPacked = (this.packed ? this.bytesPerRow : this.samplesPerRow);
        switch (this.bitDepth) {
            case 1:
            case 2:
            case 4: {
                if (!this.indexed && !this.greyscale) {
                    throw new PngjException("only indexed or grayscale can have bitdepth=" + this.bitDepth);
                }
                break;
            }
            case 8: {
                break;
            }
            case 16: {
                if (this.indexed) {
                    throw new PngjException("indexed can't have bitdepth=" + this.bitDepth);
                }
                break;
            }
            default: {
                throw new PngjException("invalid bitdepth=" + this.bitDepth);
            }
        }
        if (cols < 1 || cols > 1000000) {
            throw new PngjException("invalid cols=" + cols + " ???");
        }
        if (rows < 1 || rows > 1000000) {
            throw new PngjException("invalid rows=" + rows + " ???");
        }
    }
    
    @Override
    public String toString() {
        return "ImageInfo [cols=" + this.cols + ", rows=" + this.rows + ", bitDepth=" + this.bitDepth + ", channels=" + this.channels + ", bitspPixel=" + this.bitspPixel + ", bytesPixel=" + this.bytesPixel + ", bytesPerRow=" + this.bytesPerRow + ", samplesPerRow=" + this.samplesPerRow + ", samplesPerRowP=" + this.samplesPerRowPacked + ", alpha=" + this.alpha + ", greyscale=" + this.greyscale + ", indexed=" + this.indexed + ", packed=" + this.packed + "]";
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * (31 * 1 + (this.alpha ? 1231 : 1237)) + this.bitDepth) + this.channels) + this.cols) + (this.greyscale ? 1231 : 1237)) + (this.indexed ? 1231 : 1237)) + this.rows;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final ImageInfo imageInfo = (ImageInfo)o;
        return this.alpha == imageInfo.alpha && this.bitDepth == imageInfo.bitDepth && this.channels == imageInfo.channels && this.cols == imageInfo.cols && this.greyscale == imageInfo.greyscale && this.indexed == imageInfo.indexed && this.rows == imageInfo.rows;
    }
}
