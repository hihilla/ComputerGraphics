// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.io.IOException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import java.io.ByteArrayOutputStream;
import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkZTXT extends PngChunkTextVar
{
    public static final String ID = "zTXt";
    
    public PngChunkZTXT(final ImageInfo imageInfo) {
        super("zTXt", imageInfo);
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        if (this.key.isEmpty()) {
            throw new PngjException("Text chunk key must be non empty");
        }
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(this.key.getBytes(PngHelperInternal.charsetLatin1));
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(ChunkHelper.compressBytes(this.val.getBytes(PngHelperInternal.charsetLatin1), true));
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            final ChunkRaw emptyChunk = this.createEmptyChunk(byteArray.length, false);
            emptyChunk.data = byteArray;
            return emptyChunk;
        }
        catch (IOException ex) {
            throw new PngjException(ex);
        }
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        int n = -1;
        for (int i = 0; i < chunkRaw.data.length; ++i) {
            if (chunkRaw.data[i] == 0) {
                n = i;
                break;
            }
        }
        if (n < 0 || n > chunkRaw.data.length - 2) {
            throw new PngjException("bad zTXt chunk: no separator found");
        }
        this.key = new String(chunkRaw.data, 0, n, PngHelperInternal.charsetLatin1);
        if (chunkRaw.data[n + 1] != 0) {
            throw new PngjException("bad zTXt chunk: unknown compression method");
        }
        this.val = new String(ChunkHelper.compressBytes(chunkRaw.data, n + 2, chunkRaw.data.length - n - 2, false), PngHelperInternal.charsetLatin1);
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkZTXT pngChunkZTXT = (PngChunkZTXT)pngChunk;
        this.key = pngChunkZTXT.key;
        this.val = pngChunkZTXT.val;
    }
}
