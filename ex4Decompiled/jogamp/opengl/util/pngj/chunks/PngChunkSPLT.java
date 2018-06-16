// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.io.IOException;
import jogamp.opengl.util.pngj.PngjException;
import java.io.OutputStream;
import jogamp.opengl.util.pngj.PngHelperInternal;
import java.io.ByteArrayOutputStream;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkSPLT extends PngChunkMultiple
{
    public static final String ID = "sPLT";
    private String palName;
    private int sampledepth;
    private int[] palette;
    
    public PngChunkSPLT(final ImageInfo imageInfo) {
        super("sPLT", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_IDAT;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(this.palName.getBytes(PngHelperInternal.charsetLatin1));
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write((byte)this.sampledepth);
            for (int nentries = this.getNentries(), i = 0; i < nentries; ++i) {
                for (int j = 0; j < 4; ++j) {
                    if (this.sampledepth == 8) {
                        PngHelperInternal.writeByte(byteArrayOutputStream, (byte)this.palette[i * 5 + j]);
                    }
                    else {
                        PngHelperInternal.writeInt2(byteArrayOutputStream, this.palette[i * 5 + j]);
                    }
                }
                PngHelperInternal.writeInt2(byteArrayOutputStream, this.palette[i * 5 + 4]);
            }
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
        if (n <= 0 || n > chunkRaw.data.length - 2) {
            throw new PngjException("bad sPLT chunk: no separator found");
        }
        this.palName = new String(chunkRaw.data, 0, n, PngHelperInternal.charsetLatin1);
        this.sampledepth = PngHelperInternal.readInt1fromByte(chunkRaw.data, n + 1);
        n += 2;
        final int n2 = (chunkRaw.data.length - n) / ((this.sampledepth == 8) ? 6 : 10);
        this.palette = new int[n2 * 5];
        int n3 = 0;
        for (int j = 0; j < n2; ++j) {
            int n4;
            int n5;
            int n6;
            int n7;
            if (this.sampledepth == 8) {
                n4 = PngHelperInternal.readInt1fromByte(chunkRaw.data, n++);
                n5 = PngHelperInternal.readInt1fromByte(chunkRaw.data, n++);
                n6 = PngHelperInternal.readInt1fromByte(chunkRaw.data, n++);
                n7 = PngHelperInternal.readInt1fromByte(chunkRaw.data, n++);
            }
            else {
                n4 = PngHelperInternal.readInt2fromBytes(chunkRaw.data, n);
                n += 2;
                n5 = PngHelperInternal.readInt2fromBytes(chunkRaw.data, n);
                n += 2;
                n6 = PngHelperInternal.readInt2fromBytes(chunkRaw.data, n);
                n += 2;
                n7 = PngHelperInternal.readInt2fromBytes(chunkRaw.data, n);
                n += 2;
            }
            final int int2fromBytes = PngHelperInternal.readInt2fromBytes(chunkRaw.data, n);
            n += 2;
            this.palette[n3++] = n4;
            this.palette[n3++] = n5;
            this.palette[n3++] = n6;
            this.palette[n3++] = n7;
            this.palette[n3++] = int2fromBytes;
        }
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkSPLT pngChunkSPLT = (PngChunkSPLT)pngChunk;
        this.palName = pngChunkSPLT.palName;
        this.sampledepth = pngChunkSPLT.sampledepth;
        this.palette = new int[pngChunkSPLT.palette.length];
        System.arraycopy(pngChunkSPLT.palette, 0, this.palette, 0, this.palette.length);
    }
    
    public int getNentries() {
        return this.palette.length / 5;
    }
    
    public String getPalName() {
        return this.palName;
    }
    
    public void setPalName(final String palName) {
        this.palName = palName;
    }
    
    public int getSampledepth() {
        return this.sampledepth;
    }
    
    public void setSampledepth(final int sampledepth) {
        this.sampledepth = sampledepth;
    }
    
    public int[] getPalette() {
        return this.palette;
    }
    
    public void setPalette(final int[] palette) {
        this.palette = palette;
    }
}
