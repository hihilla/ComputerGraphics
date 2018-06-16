// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import java.util.Arrays;
import java.io.IOException;
import jogamp.opengl.util.pngj.chunks.ChunkHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.io.InputStream;

class PngIDatChunkInputStream extends InputStream
{
    private final InputStream inputStream;
    private final CRC32 crcEngine;
    private boolean checkCrc;
    private int lenLastChunk;
    private final byte[] idLastChunk;
    private int toReadThisChunk;
    private boolean ended;
    private long offset;
    List<IdatChunkInfo> foundChunksInfo;
    
    PngIDatChunkInputStream(final InputStream inputStream, final int n, final long offset) {
        this.checkCrc = true;
        this.idLastChunk = new byte[4];
        this.toReadThisChunk = 0;
        this.ended = false;
        this.foundChunksInfo = new ArrayList<IdatChunkInfo>();
        this.offset = offset;
        this.inputStream = inputStream;
        this.lenLastChunk = n;
        this.toReadThisChunk = n;
        System.arraycopy(ChunkHelper.b_IDAT, 0, this.idLastChunk, 0, 4);
        (this.crcEngine = new CRC32()).update(this.idLastChunk, 0, 4);
        this.foundChunksInfo.add(new IdatChunkInfo(this.lenLastChunk, offset - 8L));
        if (this.lenLastChunk == 0) {
            this.endChunkGoForNext();
        }
    }
    
    @Override
    public void close() throws IOException {
        super.close();
    }
    
    private void endChunkGoForNext() {
        do {
            final int int4 = PngHelperInternal.readInt4(this.inputStream);
            this.offset += 4L;
            if (this.checkCrc) {
                final int n = (int)this.crcEngine.getValue();
                if (this.lenLastChunk > 0 && int4 != n) {
                    throw new PngjBadCrcException("error reading idat; offset: " + this.offset);
                }
                this.crcEngine.reset();
            }
            this.lenLastChunk = PngHelperInternal.readInt4(this.inputStream);
            this.toReadThisChunk = this.lenLastChunk;
            PngHelperInternal.readBytes(this.inputStream, this.idLastChunk, 0, 4);
            this.offset += 8L;
            if (!(this.ended = !Arrays.equals(this.idLastChunk, ChunkHelper.b_IDAT))) {
                this.foundChunksInfo.add(new IdatChunkInfo(this.lenLastChunk, this.offset - 8L));
                if (!this.checkCrc) {
                    continue;
                }
                this.crcEngine.update(this.idLastChunk, 0, 4);
            }
        } while (this.lenLastChunk == 0 && !this.ended);
    }
    
    void forceChunkEnd() {
        if (!this.ended) {
            final byte[] array = new byte[this.toReadThisChunk];
            PngHelperInternal.readBytes(this.inputStream, array, 0, this.toReadThisChunk);
            if (this.checkCrc) {
                this.crcEngine.update(array, 0, this.toReadThisChunk);
            }
            this.endChunkGoForNext();
        }
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.ended) {
            return -1;
        }
        if (this.toReadThisChunk == 0) {
            throw new PngjExceptionInternal("this should not happen");
        }
        final int read = this.inputStream.read(array, n, (n2 >= this.toReadThisChunk) ? this.toReadThisChunk : n2);
        if (read > 0) {
            if (this.checkCrc) {
                this.crcEngine.update(array, n, read);
            }
            this.offset += read;
            this.toReadThisChunk -= read;
        }
        if (this.toReadThisChunk == 0) {
            this.endChunkGoForNext();
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read() throws IOException {
        final byte[] array = { 0 };
        return (this.read(array, 0, 1) < 0) ? -1 : array[0];
    }
    
    int getLenLastChunk() {
        return this.lenLastChunk;
    }
    
    byte[] getIdLastChunk() {
        return this.idLastChunk;
    }
    
    long getOffset() {
        return this.offset;
    }
    
    boolean isEnded() {
        return this.ended;
    }
    
    void disableCrcCheck() {
        this.checkCrc = false;
    }
    
    static class IdatChunkInfo
    {
        public final int len;
        public final long offset;
        
        private IdatChunkInfo(final int len, final long offset) {
            this.len = len;
            this.offset = offset;
        }
    }
}
