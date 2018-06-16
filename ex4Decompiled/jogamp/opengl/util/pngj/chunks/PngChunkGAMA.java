// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkGAMA extends PngChunkSingle
{
    public static final String ID = "gAMA";
    private double gamma;
    
    public PngChunkGAMA(final ImageInfo imageInfo) {
        super("gAMA", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(4, true);
        PngHelperInternal.writeInt4tobytes((int)(this.gamma * 100000.0 + 0.5), emptyChunk.data, 0);
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != 4) {
            throw new PngjException("bad chunk " + chunkRaw);
        }
        this.gamma = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 0) / 100000.0;
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        this.gamma = ((PngChunkGAMA)pngChunk).gamma;
    }
    
    public double getGamma() {
        return this.gamma;
    }
    
    public void setGamma(final double gamma) {
        this.gamma = gamma;
    }
}
