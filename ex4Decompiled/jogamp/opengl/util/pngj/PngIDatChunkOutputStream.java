// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import jogamp.opengl.util.pngj.chunks.ChunkRaw;
import jogamp.opengl.util.pngj.chunks.ChunkHelper;
import java.io.OutputStream;

class PngIDatChunkOutputStream extends ProgressiveOutputStream
{
    private static final int SIZE_DEFAULT = 32768;
    private final OutputStream outputStream;
    
    PngIDatChunkOutputStream(final OutputStream outputStream) {
        this(outputStream, 0);
    }
    
    PngIDatChunkOutputStream(final OutputStream outputStream, final int n) {
        super((n > 0) ? n : 32768);
        this.outputStream = outputStream;
    }
    
    @Override
    protected final void flushBuffer(final byte[] data, final int n) {
        final ChunkRaw chunkRaw = new ChunkRaw(n, ChunkHelper.b_IDAT, false);
        chunkRaw.data = data;
        chunkRaw.writeChunk(this.outputStream);
    }
}
