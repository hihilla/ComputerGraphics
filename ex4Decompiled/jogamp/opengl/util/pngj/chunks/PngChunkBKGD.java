// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkBKGD extends PngChunkSingle
{
    public static final String ID = "bKGD";
    private int gray;
    private int red;
    private int green;
    private int blue;
    private int paletteIndex;
    
    public PngChunkBKGD(final ImageInfo imageInfo) {
        super("bKGD", imageInfo);
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
            chunkRaw = this.createEmptyChunk(1, true);
            chunkRaw.data[0] = (byte)this.paletteIndex;
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
            this.paletteIndex = (chunkRaw.data[0] & 0xFF);
        }
        else {
            this.red = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 0);
            this.green = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 2);
            this.blue = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 4);
        }
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkBKGD pngChunkBKGD = (PngChunkBKGD)pngChunk;
        this.gray = pngChunkBKGD.gray;
        this.red = pngChunkBKGD.red;
        this.green = pngChunkBKGD.red;
        this.blue = pngChunkBKGD.red;
        this.paletteIndex = pngChunkBKGD.paletteIndex;
    }
    
    public void setGray(final int gray) {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only gray images support this");
        }
        this.gray = gray;
    }
    
    public int getGray() {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only gray images support this");
        }
        return this.gray;
    }
    
    public void setPaletteIndex(final int paletteIndex) {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed (pallete) images support this");
        }
        this.paletteIndex = paletteIndex;
    }
    
    public int getPaletteIndex() {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed (pallete) images support this");
        }
        return this.paletteIndex;
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
}
