// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkPLTE extends PngChunkSingle
{
    public static final String ID = "PLTE";
    private int nentries;
    private int[] entries;
    
    public PngChunkPLTE(final ImageInfo imageInfo) {
        super("PLTE", imageInfo);
        this.nentries = 0;
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NA;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final int n = 3 * this.nentries;
        final int[] array = new int[3];
        final ChunkRaw emptyChunk = this.createEmptyChunk(n, true);
        int i = 0;
        int n2 = 0;
        while (i < this.nentries) {
            this.getEntryRgb(i, array);
            emptyChunk.data[n2++] = (byte)array[0];
            emptyChunk.data[n2++] = (byte)array[1];
            emptyChunk.data[n2++] = (byte)array[2];
            ++i;
        }
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        this.setNentries(chunkRaw.len / 3);
        int i = 0;
        int n = 0;
        while (i < this.nentries) {
            this.setEntry(i, chunkRaw.data[n++] & 0xFF, chunkRaw.data[n++] & 0xFF, chunkRaw.data[n++] & 0xFF);
            ++i;
        }
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkPLTE pngChunkPLTE = (PngChunkPLTE)pngChunk;
        this.setNentries(pngChunkPLTE.getNentries());
        System.arraycopy(pngChunkPLTE.entries, 0, this.entries, 0, this.nentries);
    }
    
    public void setNentries(final int nentries) {
        this.nentries = nentries;
        if (this.nentries < 1 || this.nentries > 256) {
            throw new PngjException("invalid pallette - nentries=" + this.nentries);
        }
        if (this.entries == null || this.entries.length != this.nentries) {
            this.entries = new int[this.nentries];
        }
    }
    
    public int getNentries() {
        return this.nentries;
    }
    
    public void setEntry(final int n, final int n2, final int n3, final int n4) {
        this.entries[n] = (n2 << 16 | n3 << 8 | n4);
    }
    
    public int getEntry(final int n) {
        return this.entries[n];
    }
    
    public void getEntryRgb(final int n, final int[] array) {
        this.getEntryRgb(n, array, 0);
    }
    
    public void getEntryRgb(final int n, final int[] array, final int n2) {
        final int n3 = this.entries[n];
        array[n2 + 0] = (n3 & 0xFF0000) >> 16;
        array[n2 + 1] = (n3 & 0xFF00) >> 8;
        array[n2 + 2] = (n3 & 0xFF);
    }
    
    public int minBitDepth() {
        if (this.nentries <= 2) {
            return 1;
        }
        if (this.nentries <= 4) {
            return 2;
        }
        if (this.nentries <= 16) {
            return 4;
        }
        return 8;
    }
}
