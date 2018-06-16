// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

public class ImageLines
{
    public final ImageInfo imgInfo;
    public final int channels;
    public final int bitDepth;
    public final ImageLine.SampleType sampleType;
    public final boolean samplesUnpacked;
    public final int elementsPerRow;
    public final int rowOffset;
    public final int nRows;
    public final int rowStep;
    public final int[][] scanlines;
    public final byte[][] scanlinesb;
    
    public ImageLines(final ImageInfo imgInfo, final ImageLine.SampleType sampleType, final boolean b, final int rowOffset, final int nRows, final int rowStep) {
        this.imgInfo = imgInfo;
        this.channels = imgInfo.channels;
        this.bitDepth = imgInfo.bitDepth;
        this.sampleType = sampleType;
        this.samplesUnpacked = (b || !imgInfo.packed);
        this.elementsPerRow = (b ? imgInfo.samplesPerRow : imgInfo.samplesPerRowPacked);
        this.rowOffset = rowOffset;
        this.nRows = nRows;
        this.rowStep = rowStep;
        if (sampleType == ImageLine.SampleType.INT) {
            this.scanlines = new int[nRows][this.elementsPerRow];
            this.scanlinesb = null;
        }
        else {
            if (sampleType != ImageLine.SampleType.BYTE) {
                throw new PngjExceptionInternal("bad ImageLine initialization");
            }
            this.scanlinesb = new byte[nRows][this.elementsPerRow];
            this.scanlines = null;
        }
    }
    
    public int imageRowToMatrixRow(final int n) {
        final int n2 = (n - this.rowOffset) / this.rowStep;
        return (n2 < 0) ? 0 : ((n2 < this.nRows) ? n2 : (this.nRows - 1));
    }
    
    public int imageRowToMatrixRowStrict(int n) {
        n -= this.rowOffset;
        final int n2 = (n >= 0 && n % this.rowStep == 0) ? (n / this.rowStep) : -1;
        return (n2 < this.nRows) ? n2 : -1;
    }
    
    public int matrixRowToImageRow(final int n) {
        return n * this.rowStep + this.rowOffset;
    }
    
    public ImageLine getImageLineAtMatrixRow(final int n) {
        if (n < 0 || n > this.nRows) {
            throw new PngjException("Bad row " + n + ". Should be positive and less than " + this.nRows);
        }
        final ImageLine imageLine = (this.sampleType == ImageLine.SampleType.INT) ? new ImageLine(this.imgInfo, this.sampleType, this.samplesUnpacked, this.scanlines[n], null) : new ImageLine(this.imgInfo, this.sampleType, this.samplesUnpacked, null, this.scanlinesb[n]);
        imageLine.setRown(this.matrixRowToImageRow(n));
        return imageLine;
    }
}
