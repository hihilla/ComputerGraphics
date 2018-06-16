// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkIEND extends PngChunkSingle
{
    public static final String ID = "IEND";
    
    public PngChunkIEND(final ImageInfo imageInfo) {
        super("IEND", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NA;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        return new ChunkRaw(0, ChunkHelper.b_IEND, false);
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
    }
}
