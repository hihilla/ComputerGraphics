// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkUNKNOWN extends PngChunkMultiple
{
    private byte[] data;
    
    public PngChunkUNKNOWN(final String s, final ImageInfo imageInfo) {
        super(s, imageInfo);
    }
    
    private PngChunkUNKNOWN(final PngChunkUNKNOWN pngChunkUNKNOWN, final ImageInfo imageInfo) {
        super(pngChunkUNKNOWN.id, imageInfo);
        System.arraycopy(pngChunkUNKNOWN.data, 0, this.data, 0, pngChunkUNKNOWN.data.length);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NONE;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(this.data.length, false);
        emptyChunk.data = this.data;
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        this.data = chunkRaw.data;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public void setData(final byte[] data) {
        this.data = data;
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        this.data = ((PngChunkUNKNOWN)pngChunk).data;
    }
}
