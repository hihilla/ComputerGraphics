// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkSRGB extends PngChunkSingle
{
    public static final String ID = "sRGB";
    public static final int RENDER_INTENT_Perceptual = 0;
    public static final int RENDER_INTENT_Relative_colorimetric = 1;
    public static final int RENDER_INTENT_Saturation = 2;
    public static final int RENDER_INTENT_Absolute_colorimetric = 3;
    private int intent;
    
    public PngChunkSRGB(final ImageInfo imageInfo) {
        super("sRGB", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != 1) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        this.intent = PngHelperInternal.readInt1fromByte(chunkRaw.data, 0);
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(1, true);
        emptyChunk.data[0] = (byte)this.intent;
        return emptyChunk;
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        this.intent = ((PngChunkSRGB)pngChunk).intent;
    }
    
    public int getIntent() {
        return this.intent;
    }
    
    public void setIntent(final int intent) {
        this.intent = intent;
    }
}
