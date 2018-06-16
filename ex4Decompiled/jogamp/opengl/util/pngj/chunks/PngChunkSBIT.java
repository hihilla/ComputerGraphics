// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkSBIT extends PngChunkSingle
{
    public static final String ID = "sBIT";
    private int graysb;
    private int alphasb;
    private int redsb;
    private int greensb;
    private int bluesb;
    
    public PngChunkSBIT(final ImageInfo imageInfo) {
        super("sBIT", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }
    
    private int getLen() {
        int n = this.imgInfo.greyscale ? 1 : 3;
        if (this.imgInfo.alpha) {
            ++n;
        }
        return n;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != this.getLen()) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        if (this.imgInfo.greyscale) {
            this.graysb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 0);
            if (this.imgInfo.alpha) {
                this.alphasb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 1);
            }
        }
        else {
            this.redsb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 0);
            this.greensb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 1);
            this.bluesb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 2);
            if (this.imgInfo.alpha) {
                this.alphasb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 3);
            }
        }
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(this.getLen(), true);
        if (this.imgInfo.greyscale) {
            emptyChunk.data[0] = (byte)this.graysb;
            if (this.imgInfo.alpha) {
                emptyChunk.data[1] = (byte)this.alphasb;
            }
        }
        else {
            emptyChunk.data[0] = (byte)this.redsb;
            emptyChunk.data[1] = (byte)this.greensb;
            emptyChunk.data[2] = (byte)this.bluesb;
            if (this.imgInfo.alpha) {
                emptyChunk.data[3] = (byte)this.alphasb;
            }
        }
        return emptyChunk;
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkSBIT pngChunkSBIT = (PngChunkSBIT)pngChunk;
        this.graysb = pngChunkSBIT.graysb;
        this.redsb = pngChunkSBIT.redsb;
        this.greensb = pngChunkSBIT.greensb;
        this.bluesb = pngChunkSBIT.bluesb;
        this.alphasb = pngChunkSBIT.alphasb;
    }
    
    public void setGraysb(final int graysb) {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only greyscale images support this");
        }
        this.graysb = graysb;
    }
    
    public int getGraysb() {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only greyscale images support this");
        }
        return this.graysb;
    }
    
    public void setAlphasb(final int alphasb) {
        if (!this.imgInfo.alpha) {
            throw new PngjException("only images with alpha support this");
        }
        this.alphasb = alphasb;
    }
    
    public int getAlphasb() {
        if (!this.imgInfo.alpha) {
            throw new PngjException("only images with alpha support this");
        }
        return this.alphasb;
    }
    
    public void setRGB(final int redsb, final int greensb, final int bluesb) {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        this.redsb = redsb;
        this.greensb = greensb;
        this.bluesb = bluesb;
    }
    
    public int[] getRGB() {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        return new int[] { this.redsb, this.greensb, this.bluesb };
    }
}
