// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import java.util.Random;

class PngDeinterlacer
{
    private final ImageInfo imi;
    private int pass;
    private int rows;
    private int cols;
    private int dY;
    private int dX;
    private int oY;
    private int oX;
    private int oXsamples;
    private int dXsamples;
    private int currRowSubimg;
    private int currRowReal;
    private final int packedValsPerPixel;
    private final int packedMask;
    private final int packedShift;
    private int[][] imageInt;
    private short[][] imageShort;
    private byte[][] imageByte;
    
    PngDeinterlacer(final ImageInfo imi) {
        this.currRowSubimg = -1;
        this.currRowReal = -1;
        this.imi = imi;
        this.pass = 0;
        if (this.imi.packed) {
            this.packedValsPerPixel = 8 / this.imi.bitDepth;
            this.packedShift = this.imi.bitDepth;
            if (this.imi.bitDepth == 1) {
                this.packedMask = 128;
            }
            else if (this.imi.bitDepth == 2) {
                this.packedMask = 192;
            }
            else {
                this.packedMask = 240;
            }
        }
        else {
            final boolean packedMask = true;
            this.packedValsPerPixel = (packedMask ? 1 : 0);
            this.packedShift = (packedMask ? 1 : 0);
            this.packedMask = (packedMask ? 1 : 0);
        }
        this.setPass(1);
        this.setRow(0);
    }
    
    void setRow(final int currRowSubimg) {
        this.currRowSubimg = currRowSubimg;
        this.currRowReal = currRowSubimg * this.dY + this.oY;
        if (this.currRowReal < 0 || this.currRowReal >= this.imi.rows) {
            throw new PngjExceptionInternal("bad row - this should not happen");
        }
    }
    
    void setPass(final int pass) {
        if (this.pass == pass) {
            return;
        }
        switch (this.pass = pass) {
            case 1: {
                final int n = 8;
                this.dX = n;
                this.dY = n;
                final boolean b = false;
                this.oY = (b ? 1 : 0);
                this.oX = (b ? 1 : 0);
                break;
            }
            case 2: {
                final int n2 = 8;
                this.dX = n2;
                this.dY = n2;
                this.oX = 4;
                this.oY = 0;
                break;
            }
            case 3: {
                this.dX = 4;
                this.dY = 8;
                this.oX = 0;
                this.oY = 4;
                break;
            }
            case 4: {
                final int n3 = 4;
                this.dY = n3;
                this.dX = n3;
                this.oX = 2;
                this.oY = 0;
                break;
            }
            case 5: {
                this.dX = 2;
                this.dY = 4;
                this.oX = 0;
                this.oY = 2;
                break;
            }
            case 6: {
                final int n4 = 2;
                this.dY = n4;
                this.dX = n4;
                this.oX = 1;
                this.oY = 0;
                break;
            }
            case 7: {
                this.dX = 1;
                this.dY = 2;
                this.oX = 0;
                this.oY = 1;
                break;
            }
            default: {
                throw new PngjExceptionInternal("bad interlace pass" + this.pass);
            }
        }
        this.rows = (this.imi.rows - this.oY) / this.dY + 1;
        if ((this.rows - 1) * this.dY + this.oY >= this.imi.rows) {
            --this.rows;
        }
        this.cols = (this.imi.cols - this.oX) / this.dX + 1;
        if ((this.cols - 1) * this.dX + this.oX >= this.imi.cols) {
            --this.cols;
        }
        if (this.cols == 0) {
            this.rows = 0;
        }
        this.dXsamples = this.dX * this.imi.channels;
        this.oXsamples = this.oX * this.imi.channels;
    }
    
    void deinterlaceInt(final int[] array, final int[] array2, final boolean b) {
        if (!this.imi.packed || !b) {
            for (int i = 0, oXsamples = this.oXsamples; i < this.cols * this.imi.channels; i += this.imi.channels, oXsamples += this.dXsamples) {
                for (int j = 0; j < this.imi.channels; ++j) {
                    array2[oXsamples + j] = array[i + j];
                }
            }
        }
        else {
            this.deinterlaceIntPacked(array, array2);
        }
    }
    
    private void deinterlaceIntPacked(final int[] array, final int[] array2) {
        int n = this.packedMask;
        int n2 = -1;
        for (int i = 0, ox = this.oX; i < this.cols; ++i, ox += this.dX) {
            final int n3 = i / this.packedValsPerPixel;
            if (++n2 >= this.packedValsPerPixel) {
                n2 = 0;
            }
            n >>= this.packedShift;
            if (n2 == 0) {
                n = this.packedMask;
            }
            final int n4 = ox / this.packedValsPerPixel;
            final int n5 = ox % this.packedValsPerPixel;
            int n6 = array[n3] & n;
            final int n7 = n5 - n2;
            if (n7 > 0) {
                n6 >>= n7 * this.packedShift;
            }
            else if (n7 < 0) {
                n6 <<= -n7 * this.packedShift;
            }
            final int n8 = n4;
            array2[n8] |= n6;
        }
    }
    
    void deinterlaceByte(final byte[] array, final byte[] array2, final boolean b) {
        if (!this.imi.packed || !b) {
            for (int i = 0, oXsamples = this.oXsamples; i < this.cols * this.imi.channels; i += this.imi.channels, oXsamples += this.dXsamples) {
                for (int j = 0; j < this.imi.channels; ++j) {
                    array2[oXsamples + j] = array[i + j];
                }
            }
        }
        else {
            this.deinterlacePackedByte(array, array2);
        }
    }
    
    private void deinterlacePackedByte(final byte[] array, final byte[] array2) {
        int n = this.packedMask;
        int n2 = -1;
        for (int i = 0, ox = this.oX; i < this.cols; ++i, ox += this.dX) {
            final int n3 = i / this.packedValsPerPixel;
            if (++n2 >= this.packedValsPerPixel) {
                n2 = 0;
            }
            n >>= this.packedShift;
            if (n2 == 0) {
                n = this.packedMask;
            }
            final int n4 = ox / this.packedValsPerPixel;
            final int n5 = ox % this.packedValsPerPixel;
            int n6 = array[n3] & n;
            final int n7 = n5 - n2;
            if (n7 > 0) {
                n6 >>= n7 * this.packedShift;
            }
            else if (n7 < 0) {
                n6 <<= -n7 * this.packedShift;
            }
            final int n8 = n4;
            array2[n8] |= (byte)n6;
        }
    }
    
    boolean isAtLastRow() {
        return this.pass == 7 && this.currRowSubimg == this.rows - 1;
    }
    
    int getCurrRowSubimg() {
        return this.currRowSubimg;
    }
    
    int getCurrRowReal() {
        return this.currRowReal;
    }
    
    int getPass() {
        return this.pass;
    }
    
    int getRows() {
        return this.rows;
    }
    
    int getCols() {
        return this.cols;
    }
    
    public int getPixelsToRead() {
        return this.getCols();
    }
    
    int[][] getImageInt() {
        return this.imageInt;
    }
    
    void setImageInt(final int[][] imageInt) {
        this.imageInt = imageInt;
    }
    
    short[][] getImageShort() {
        return this.imageShort;
    }
    
    void setImageShort(final short[][] imageShort) {
        this.imageShort = imageShort;
    }
    
    byte[][] getImageByte() {
        return this.imageByte;
    }
    
    void setImageByte(final byte[][] imageByte) {
        this.imageByte = imageByte;
    }
    
    static void test() {
        final Random random = new Random();
        final PngDeinterlacer pngDeinterlacer = new PngDeinterlacer(new ImageInfo(random.nextInt(35) + 1, random.nextInt(52) + 1, 8, true));
        int n = pngDeinterlacer.imi.cols * pngDeinterlacer.imi.rows;
        System.out.println(pngDeinterlacer.imi);
        for (int i = 1; i <= 7; ++i) {
            pngDeinterlacer.setPass(i);
            for (int j = 0; j < pngDeinterlacer.getRows(); ++j) {
                pngDeinterlacer.setRow(j);
                final int cols = pngDeinterlacer.getCols();
                n -= cols;
                System.out.printf("Read %d pixels. Pass:%d Realline:%d cols=%d dX=%d oX=%d last:%b\n", cols, pngDeinterlacer.pass, pngDeinterlacer.currRowReal, pngDeinterlacer.cols, pngDeinterlacer.dX, pngDeinterlacer.oX, pngDeinterlacer.isAtLastRow());
            }
        }
        if (n != 0) {
            throw new PngjExceptionInternal("wtf??" + pngDeinterlacer.imi);
        }
    }
    
    public static void main(final String[] array) {
        test();
    }
}
