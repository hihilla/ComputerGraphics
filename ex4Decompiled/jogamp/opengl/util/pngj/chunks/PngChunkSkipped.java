// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkSkipped extends PngChunk
{
    public PngChunkSkipped(final String s, final ImageInfo imageInfo, final int length) {
        super(s, imageInfo);
        this.length = length;
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NONE;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        throw new PngjException("Non supported for a skipped chunk");
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        throw new PngjException("Non supported for a skipped chunk");
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        throw new PngjException("Non supported for a skipped chunk");
    }
    
    @Override
    public boolean allowsMultiple() {
        return true;
    }
}
