// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkHIST extends PngChunkSingle
{
    public static final String ID = "hIST";
    private int[] hist;
    
    public PngChunkHIST(final ImageInfo imageInfo) {
        super("hIST", imageInfo);
        this.hist = new int[0];
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed images accept a HIST chunk");
        }
        this.hist = new int[chunkRaw.data.length / 2];
        for (int i = 0; i < this.hist.length; ++i) {
            this.hist[i] = PngHelperInternal.readInt2fromBytes(chunkRaw.data, i * 2);
        }
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed images accept a HIST chunk");
        }
        final ChunkRaw emptyChunk = this.createEmptyChunk(this.hist.length * 2, true);
        for (int i = 0; i < this.hist.length; ++i) {
            PngHelperInternal.writeInt2tobytes(this.hist[i], emptyChunk.data, i * 2);
        }
        return emptyChunk;
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkHIST pngChunkHIST = (PngChunkHIST)pngChunk;
        this.hist = new int[pngChunkHIST.hist.length];
        System.arraycopy(pngChunkHIST.hist, 0, this.hist, 0, pngChunkHIST.hist.length);
    }
    
    public int[] getHist() {
        return this.hist;
    }
    
    public void setHist(final int[] hist) {
        this.hist = hist;
    }
}
