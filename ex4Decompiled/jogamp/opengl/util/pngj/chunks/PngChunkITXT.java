// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngHelperInternal;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkITXT extends PngChunkTextVar
{
    public static final String ID = "iTXt";
    private boolean compressed;
    private String langTag;
    private String translatedTag;
    
    public PngChunkITXT(final ImageInfo imageInfo) {
        super("iTXt", imageInfo);
        this.compressed = false;
        this.langTag = "";
        this.translatedTag = "";
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        if (this.key.isEmpty()) {
            throw new PngjException("Text chunk key must be non empty");
        }
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(ChunkHelper.toBytes(this.key));
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(this.compressed ? 1 : 0);
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(ChunkHelper.toBytes(this.langTag));
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(ChunkHelper.toBytesUTF8(this.translatedTag));
            byteArrayOutputStream.write(0);
            byte[] array = ChunkHelper.toBytesUTF8(this.val);
            if (this.compressed) {
                array = ChunkHelper.compressBytes(array, true);
            }
            byteArrayOutputStream.write(array);
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
        int n = 0;
        final int[] array = new int[3];
        for (int i = 0; i < chunkRaw.data.length; ++i) {
            if (chunkRaw.data[i] == 0) {
                array[n] = i;
                if (++n == 1) {
                    i += 2;
                }
                if (n == 3) {
                    break;
                }
            }
        }
        if (n != 3) {
            throw new PngjException("Bad formed PngChunkITXT chunk");
        }
        this.key = ChunkHelper.toString(chunkRaw.data, 0, array[0]);
        int n2 = array[0] + 1;
        this.compressed = (chunkRaw.data[n2] != 0);
        ++n2;
        if (this.compressed && chunkRaw.data[n2] != 0) {
            throw new PngjException("Bad formed PngChunkITXT chunk - bad compression method ");
        }
        this.langTag = new String(chunkRaw.data, n2, array[1] - n2, PngHelperInternal.charsetLatin1);
        this.translatedTag = new String(chunkRaw.data, array[1] + 1, array[2] - array[1] - 1, PngHelperInternal.charsetUTF8);
        final int n3 = array[2] + 1;
        if (this.compressed) {
            this.val = ChunkHelper.toStringUTF8(ChunkHelper.compressBytes(chunkRaw.data, n3, chunkRaw.data.length - n3, false));
        }
        else {
            this.val = ChunkHelper.toStringUTF8(chunkRaw.data, n3, chunkRaw.data.length - n3);
        }
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkITXT pngChunkITXT = (PngChunkITXT)pngChunk;
        this.key = pngChunkITXT.key;
        this.val = pngChunkITXT.val;
        this.compressed = pngChunkITXT.compressed;
        this.langTag = pngChunkITXT.langTag;
        this.translatedTag = pngChunkITXT.translatedTag;
    }
    
    public boolean isCompressed() {
        return this.compressed;
    }
    
    public void setCompressed(final boolean compressed) {
        this.compressed = compressed;
    }
    
    public String getLangtag() {
        return this.langTag;
    }
    
    public void setLangtag(final String langTag) {
        this.langTag = langTag;
    }
    
    public String getTranslatedTag() {
        return this.translatedTag;
    }
    
    public void setTranslatedTag(final String translatedTag) {
        this.translatedTag = translatedTag;
    }
}
