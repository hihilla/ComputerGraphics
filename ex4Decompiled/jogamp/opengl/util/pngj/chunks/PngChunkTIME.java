// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.util.Calendar;
import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkTIME extends PngChunkSingle
{
    public static final String ID = "tIME";
    private int year;
    private int mon;
    private int day;
    private int hour;
    private int min;
    private int sec;
    
    public PngChunkTIME(final ImageInfo imageInfo) {
        super("tIME", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NONE;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(7, true);
        PngHelperInternal.writeInt2tobytes(this.year, emptyChunk.data, 0);
        emptyChunk.data[2] = (byte)this.mon;
        emptyChunk.data[3] = (byte)this.day;
        emptyChunk.data[4] = (byte)this.hour;
        emptyChunk.data[5] = (byte)this.min;
        emptyChunk.data[6] = (byte)this.sec;
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        if (chunkRaw.len != 7) {
            throw new PngjException("bad chunk " + chunkRaw);
        }
        this.year = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 0);
        this.mon = PngHelperInternal.readInt1fromByte(chunkRaw.data, 2);
        this.day = PngHelperInternal.readInt1fromByte(chunkRaw.data, 3);
        this.hour = PngHelperInternal.readInt1fromByte(chunkRaw.data, 4);
        this.min = PngHelperInternal.readInt1fromByte(chunkRaw.data, 5);
        this.sec = PngHelperInternal.readInt1fromByte(chunkRaw.data, 6);
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkTIME pngChunkTIME = (PngChunkTIME)pngChunk;
        this.year = pngChunkTIME.year;
        this.mon = pngChunkTIME.mon;
        this.day = pngChunkTIME.day;
        this.hour = pngChunkTIME.hour;
        this.min = pngChunkTIME.min;
        this.sec = pngChunkTIME.sec;
    }
    
    public void setNow(final int n) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis() - 1000L * n);
        this.year = instance.get(1);
        this.mon = instance.get(2) + 1;
        this.day = instance.get(5);
        this.hour = instance.get(11);
        this.min = instance.get(12);
        this.sec = instance.get(13);
    }
    
    public void setYMDHMS(final int year, final int mon, final int day, final int hour, final int min, final int sec) {
        this.year = year;
        this.mon = mon;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }
    
    public int[] getYMDHMS() {
        return new int[] { this.year, this.mon, this.day, this.hour, this.min, this.sec };
    }
    
    public String getAsString() {
        return String.format("%04d/%02d/%02d %02d:%02d:%02d", this.year, this.mon, this.day, this.hour, this.min, this.sec);
    }
}
