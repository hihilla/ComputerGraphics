// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkCHRM extends PngChunkSingle
{
    public static final String ID = "cHRM";
    private double whitex;
    private double whitey;
    private double redx;
    private double redy;
    private double greenx;
    private double greeny;
    private double bluex;
    private double bluey;
    
    public PngChunkCHRM(final ImageInfo imageInfo) {
        super("cHRM", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(32, true);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.whitex), emptyChunk.data, 0);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.whitey), emptyChunk.data, 4);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.redx), emptyChunk.data, 8);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.redy), emptyChunk.data, 12);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.greenx), emptyChunk.data, 16);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.greeny), emptyChunk.data, 20);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.bluex), emptyChunk.data, 24);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.bluey), emptyChunk.data, 28);
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != 32) {
            throw new PngjException("bad chunk " + chunkRaw);
        }
        this.whitex = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 0));
        this.whitey = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 4));
        this.redx = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 8));
        this.redy = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 12));
        this.greenx = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 16));
        this.greeny = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 20));
        this.bluex = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 24));
        this.bluey = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 28));
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkCHRM pngChunkCHRM = (PngChunkCHRM)pngChunk;
        this.whitex = pngChunkCHRM.whitex;
        this.whitey = pngChunkCHRM.whitex;
        this.redx = pngChunkCHRM.redx;
        this.redy = pngChunkCHRM.redy;
        this.greenx = pngChunkCHRM.greenx;
        this.greeny = pngChunkCHRM.greeny;
        this.bluex = pngChunkCHRM.bluex;
        this.bluey = pngChunkCHRM.bluey;
    }
    
    public void setChromaticities(final double whitex, final double whitey, final double redx, final double redy, final double greenx, final double greeny, final double bluex, final double bluey) {
        this.whitex = whitex;
        this.redx = redx;
        this.greenx = greenx;
        this.bluex = bluex;
        this.whitey = whitey;
        this.redy = redy;
        this.greeny = greeny;
        this.bluey = bluey;
    }
    
    public double[] getChromaticities() {
        return new double[] { this.whitex, this.whitey, this.redx, this.redy, this.greenx, this.greeny, this.bluex, this.bluey };
    }
}
