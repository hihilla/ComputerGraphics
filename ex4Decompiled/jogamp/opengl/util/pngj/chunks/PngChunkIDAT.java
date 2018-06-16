// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkIDAT extends PngChunkMultiple
{
    public static final String ID = "IDAT";
    
    public PngChunkIDAT(final ImageInfo imageInfo, final int length, final long offset) {
        super("IDAT", imageInfo);
        this.length = length;
        this.offset = offset;
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NA;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        return null;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
    }
}
