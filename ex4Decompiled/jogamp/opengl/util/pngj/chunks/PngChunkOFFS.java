// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkOFFS extends PngChunkSingle
{
    public static final String ID = "oFFs";
    private long posX;
    private long posY;
    private int units;
    
    public PngChunkOFFS(final ImageInfo imageInfo) {
        super("oFFs", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_IDAT;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(9, true);
        PngHelperInternal.writeInt4tobytes((int)this.posX, emptyChunk.data, 0);
        PngHelperInternal.writeInt4tobytes((int)this.posY, emptyChunk.data, 4);
        emptyChunk.data[8] = (byte)this.units;
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != 9) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        this.posX = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 0);
        if (this.posX < 0L) {
            this.posX += 4294967296L;
        }
        this.posY = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 4);
        if (this.posY < 0L) {
            this.posY += 4294967296L;
        }
        this.units = PngHelperInternal.readInt1fromByte(chunkRaw.data, 8);
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkOFFS pngChunkOFFS = (PngChunkOFFS)pngChunk;
        this.posX = pngChunkOFFS.posX;
        this.posY = pngChunkOFFS.posY;
        this.units = pngChunkOFFS.units;
    }
    
    public int getUnits() {
        return this.units;
    }
    
    public void setUnits(final int units) {
        this.units = units;
    }
    
    public long getPosX() {
        return this.posX;
    }
    
    public void setPosX(final long posX) {
        this.posX = posX;
    }
    
    public long getPosY() {
        return this.posY;
    }
    
    public void setPosY(final long posY) {
        this.posY = posY;
    }
}
