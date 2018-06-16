// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkPHYS extends PngChunkSingle
{
    public static final String ID = "pHYs";
    private long pixelsxUnitX;
    private long pixelsxUnitY;
    private int units;
    
    public PngChunkPHYS(final ImageInfo imageInfo) {
        super("pHYs", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_IDAT;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(9, true);
        PngHelperInternal.writeInt4tobytes((int)this.pixelsxUnitX, emptyChunk.data, 0);
        PngHelperInternal.writeInt4tobytes((int)this.pixelsxUnitY, emptyChunk.data, 4);
        emptyChunk.data[8] = (byte)this.units;
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != 9) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        this.pixelsxUnitX = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 0);
        if (this.pixelsxUnitX < 0L) {
            this.pixelsxUnitX += 4294967296L;
        }
        this.pixelsxUnitY = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 4);
        if (this.pixelsxUnitY < 0L) {
            this.pixelsxUnitY += 4294967296L;
        }
        this.units = PngHelperInternal.readInt1fromByte(chunkRaw.data, 8);
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkPHYS pngChunkPHYS = (PngChunkPHYS)pngChunk;
        this.pixelsxUnitX = pngChunkPHYS.pixelsxUnitX;
        this.pixelsxUnitY = pngChunkPHYS.pixelsxUnitY;
        this.units = pngChunkPHYS.units;
    }
    
    public long getPixelsxUnitX() {
        return this.pixelsxUnitX;
    }
    
    public void setPixelsxUnitX(final long pixelsxUnitX) {
        this.pixelsxUnitX = pixelsxUnitX;
    }
    
    public long getPixelsxUnitY() {
        return this.pixelsxUnitY;
    }
    
    public void setPixelsxUnitY(final long pixelsxUnitY) {
        this.pixelsxUnitY = pixelsxUnitY;
    }
    
    public int getUnits() {
        return this.units;
    }
    
    public void setUnits(final int units) {
        this.units = units;
    }
    
    public double getAsDpi() {
        if (this.units != 1 || this.pixelsxUnitX != this.pixelsxUnitY) {
            return -1.0;
        }
        return this.pixelsxUnitX * 0.0254;
    }
    
    public double[] getAsDpi2() {
        if (this.units != 1) {
            return new double[] { -1.0, -1.0 };
        }
        return new double[] { this.pixelsxUnitX * 0.0254, this.pixelsxUnitY * 0.0254 };
    }
    
    public void setAsDpi(final double n) {
        this.units = 1;
        this.pixelsxUnitX = (long)(n / 0.0254 + 0.5);
        this.pixelsxUnitY = this.pixelsxUnitX;
    }
    
    public void setAsDpi2(final double n, final double n2) {
        this.units = 1;
        this.pixelsxUnitX = (long)(n / 0.0254 + 0.5);
        this.pixelsxUnitY = (long)(n2 / 0.0254 + 0.5);
    }
}
