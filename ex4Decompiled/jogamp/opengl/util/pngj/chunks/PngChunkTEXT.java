// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkTEXT extends PngChunkTextVar
{
    public static final String ID = "tEXt";
    
    public PngChunkTEXT(final ImageInfo imageInfo) {
        super("tEXt", imageInfo);
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        if (this.key.isEmpty()) {
            throw new PngjException("Text chunk key must be non empty");
        }
        final byte[] bytes = (this.key + "\u0000" + this.val).getBytes(PngHelperInternal.charsetLatin1);
        final ChunkRaw emptyChunk = this.createEmptyChunk(bytes.length, false);
        emptyChunk.data = bytes;
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        int n;
        for (n = 0; n < chunkRaw.data.length && chunkRaw.data[n] != 0; ++n) {}
        this.key = new String(chunkRaw.data, 0, n, PngHelperInternal.charsetLatin1);
        ++n;
        this.val = ((n < chunkRaw.data.length) ? new String(chunkRaw.data, n, chunkRaw.data.length - n, PngHelperInternal.charsetLatin1) : "");
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkTEXT pngChunkTEXT = (PngChunkTEXT)pngChunk;
        this.key = pngChunkTEXT.key;
        this.val = pngChunkTEXT.val;
    }
}
