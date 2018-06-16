// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkIHDR extends PngChunkSingle
{
    public static final String ID = "IHDR";
    private int cols;
    private int rows;
    private int bitspc;
    private int colormodel;
    private int compmeth;
    private int filmeth;
    private int interlaced;
    
    public PngChunkIHDR(final ImageInfo imageInfo) {
        super("IHDR", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NA;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw chunkRaw = new ChunkRaw(13, ChunkHelper.b_IHDR, true);
        int n = 0;
        PngHelperInternal.writeInt4tobytes(this.cols, chunkRaw.data, n);
        n += 4;
        PngHelperInternal.writeInt4tobytes(this.rows, chunkRaw.data, n);
        n += 4;
        chunkRaw.data[n++] = (byte)this.bitspc;
        chunkRaw.data[n++] = (byte)this.colormodel;
        chunkRaw.data[n++] = (byte)this.compmeth;
        chunkRaw.data[n++] = (byte)this.filmeth;
        chunkRaw.data[n++] = (byte)this.interlaced;
        return chunkRaw;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != 13) {
            throw new PngjException("Bad IDHR len " + chunkRaw.len);
        }
        final ByteArrayInputStream asByteStream = chunkRaw.getAsByteStream();
        this.cols = PngHelperInternal.readInt4(asByteStream);
        this.rows = PngHelperInternal.readInt4(asByteStream);
        this.bitspc = PngHelperInternal.readByte(asByteStream);
        this.colormodel = PngHelperInternal.readByte(asByteStream);
        this.compmeth = PngHelperInternal.readByte(asByteStream);
        this.filmeth = PngHelperInternal.readByte(asByteStream);
        this.interlaced = PngHelperInternal.readByte(asByteStream);
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkIHDR pngChunkIHDR = (PngChunkIHDR)pngChunk;
        this.cols = pngChunkIHDR.cols;
        this.rows = pngChunkIHDR.rows;
        this.bitspc = pngChunkIHDR.bitspc;
        this.colormodel = pngChunkIHDR.colormodel;
        this.compmeth = pngChunkIHDR.compmeth;
        this.filmeth = pngChunkIHDR.filmeth;
        this.interlaced = pngChunkIHDR.interlaced;
    }
    
    public int getCols() {
        return this.cols;
    }
    
    public void setCols(final int cols) {
        this.cols = cols;
    }
    
    public int getRows() {
        return this.rows;
    }
    
    public void setRows(final int rows) {
        this.rows = rows;
    }
    
    public int getBitspc() {
        return this.bitspc;
    }
    
    public void setBitspc(final int bitspc) {
        this.bitspc = bitspc;
    }
    
    public int getColormodel() {
        return this.colormodel;
    }
    
    public void setColormodel(final int colormodel) {
        this.colormodel = colormodel;
    }
    
    public int getCompmeth() {
        return this.compmeth;
    }
    
    public void setCompmeth(final int compmeth) {
        this.compmeth = compmeth;
    }
    
    public int getFilmeth() {
        return this.filmeth;
    }
    
    public void setFilmeth(final int filmeth) {
        this.filmeth = filmeth;
    }
    
    public int getInterlaced() {
        return this.interlaced;
    }
    
    public void setInterlaced(final int interlaced) {
        this.interlaced = interlaced;
    }
}
