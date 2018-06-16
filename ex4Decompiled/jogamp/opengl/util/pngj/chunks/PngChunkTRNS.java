// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkTRNS extends PngChunkSingle
{
    public static final String ID = "tRNS";
    private int gray;
    private int red;
    private int green;
    private int blue;
    private int[] paletteAlpha;
    
    public PngChunkTRNS(final ImageInfo imageInfo) {
        super("tRNS", imageInfo);
        this.paletteAlpha = new int[0];
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRaw;
        if (this.imgInfo.greyscale) {
            chunkRaw = this.createEmptyChunk(2, true);
            PngHelperInternal.writeInt2tobytes(this.gray, chunkRaw.data, 0);
        }
        else if (this.imgInfo.indexed) {
            chunkRaw = this.createEmptyChunk(this.paletteAlpha.length, true);
            for (int i = 0; i < chunkRaw.len; ++i) {
                chunkRaw.data[i] = (byte)this.paletteAlpha[i];
            }
        }
        else {
            chunkRaw = this.createEmptyChunk(6, true);
            PngHelperInternal.writeInt2tobytes(this.red, chunkRaw.data, 0);
            PngHelperInternal.writeInt2tobytes(this.green, chunkRaw.data, 0);
            PngHelperInternal.writeInt2tobytes(this.blue, chunkRaw.data, 0);
        }
        return chunkRaw;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (this.imgInfo.greyscale) {
            this.gray = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 0);
        }
        else if (this.imgInfo.indexed) {
            final int length = chunkRaw.data.length;
            this.paletteAlpha = new int[length];
            for (int i = 0; i < length; ++i) {
                this.paletteAlpha[i] = (chunkRaw.data[i] & 0xFF);
            }
        }
        else {
            this.red = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 0);
            this.green = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 2);
            this.blue = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 4);
        }
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkTRNS pngChunkTRNS = (PngChunkTRNS)pngChunk;
        this.gray = pngChunkTRNS.gray;
        this.red = pngChunkTRNS.red;
        this.green = pngChunkTRNS.green;
        this.blue = pngChunkTRNS.blue;
        if (pngChunkTRNS.paletteAlpha != null) {
            this.paletteAlpha = new int[pngChunkTRNS.paletteAlpha.length];
            System.arraycopy(pngChunkTRNS.paletteAlpha, 0, this.paletteAlpha, 0, this.paletteAlpha.length);
        }
    }
    
    public void setRGB(final int red, final int green, final int blue) {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public int[] getRGB() {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        return new int[] { this.red, this.green, this.blue };
    }
    
    public void setGray(final int gray) {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only grayscale images support this");
        }
        this.gray = gray;
    }
    
    public int getGray() {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only grayscale images support this");
        }
        return this.gray;
    }
    
    public void setPalletteAlpha(final int[] paletteAlpha) {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed images support this");
        }
        this.paletteAlpha = paletteAlpha;
    }
    
    public void setIndexEntryAsTransparent(final int n) {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed images support this");
        }
        this.paletteAlpha = new int[] { n + 1 };
        for (int i = 0; i < n; ++i) {
            this.paletteAlpha[i] = 255;
        }
        this.paletteAlpha[n] = 0;
    }
    
    public int[] getPalletteAlpha() {
        return this.paletteAlpha;
    }
}
