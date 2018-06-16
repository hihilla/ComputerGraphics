// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkSTER extends PngChunkSingle
{
    public static final String ID = "sTER";
    private byte mode;
    
    public PngChunkSTER(final ImageInfo imageInfo) {
        super("sTER", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_IDAT;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(1, true);
        emptyChunk.data[0] = this.mode;
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != 1) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        this.mode = chunkRaw.data[0];
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        this.mode = ((PngChunkSTER)pngChunk).mode;
    }
    
    public byte getMode() {
        return this.mode;
    }
    
    public void setMode(final byte mode) {
        this.mode = mode;
    }
}
