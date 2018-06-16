// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.io.ByteArrayInputStream;
import jogamp.opengl.util.pngj.PngjBadCrcException;
import java.io.InputStream;
import jogamp.opengl.util.pngj.PngjOutputException;
import java.io.OutputStream;
import java.util.zip.CRC32;
import jogamp.opengl.util.pngj.PngHelperInternal;

public class ChunkRaw
{
    public final int len;
    public final byte[] idbytes;
    public byte[] data;
    private int crcval;
    
    public ChunkRaw(final int len, final byte[] array, final boolean b) {
        this.idbytes = new byte[4];
        this.data = null;
        this.crcval = 0;
        this.len = len;
        System.arraycopy(array, 0, this.idbytes, 0, 4);
        if (b) {
            this.allocData();
        }
    }
    
    private void allocData() {
        if (this.data == null || this.data.length < this.len) {
            this.data = new byte[this.len];
        }
    }
    
    private int computeCrc() {
        final CRC32 crc = PngHelperInternal.getCRC();
        crc.reset();
        crc.update(this.idbytes, 0, 4);
        if (this.len > 0) {
            crc.update(this.data, 0, this.len);
        }
        return (int)crc.getValue();
    }
    
    public void writeChunk(final OutputStream outputStream) {
        if (this.idbytes.length != 4) {
            throw new PngjOutputException("bad chunkid [" + ChunkHelper.toString(this.idbytes) + "]");
        }
        this.crcval = this.computeCrc();
        PngHelperInternal.writeInt4(outputStream, this.len);
        PngHelperInternal.writeBytes(outputStream, this.idbytes);
        if (this.len > 0) {
            PngHelperInternal.writeBytes(outputStream, this.data, 0, this.len);
        }
        PngHelperInternal.writeInt4(outputStream, this.crcval);
    }
    
    public int readChunkData(final InputStream inputStream, final boolean b) {
        PngHelperInternal.readBytes(inputStream, this.data, 0, this.len);
        this.crcval = PngHelperInternal.readInt4(inputStream);
        if (b) {
            final int computeCrc = this.computeCrc();
            if (computeCrc != this.crcval) {
                throw new PngjBadCrcException("chunk: " + this + " crc calc=" + computeCrc + " read=" + this.crcval);
            }
        }
        return this.len + 4;
    }
    
    ByteArrayInputStream getAsByteStream() {
        return new ByteArrayInputStream(this.data);
    }
    
    @Override
    public String toString() {
        return "chunkid=" + ChunkHelper.toString(this.idbytes) + " len=" + this.len;
    }
}
