// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import jogamp.opengl.util.pngj.ImageInfo;
import java.util.List;

public class ChunksList
{
    public static final int CHUNK_GROUP_0_IDHR = 0;
    public static final int CHUNK_GROUP_1_AFTERIDHR = 1;
    public static final int CHUNK_GROUP_2_PLTE = 2;
    public static final int CHUNK_GROUP_3_AFTERPLTE = 3;
    public static final int CHUNK_GROUP_4_IDAT = 4;
    public static final int CHUNK_GROUP_5_AFTERIDAT = 5;
    public static final int CHUNK_GROUP_6_END = 6;
    protected List<PngChunk> chunks;
    final ImageInfo imageInfo;
    
    public ChunksList(final ImageInfo imageInfo) {
        this.chunks = new ArrayList<PngChunk>();
        this.imageInfo = imageInfo;
    }
    
    public HashMap<String, Integer> getChunksKeys() {
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        for (final PngChunk pngChunk : this.chunks) {
            hashMap.put(pngChunk.id, hashMap.containsKey(pngChunk.id) ? (hashMap.get(pngChunk.id) + 1) : 1);
        }
        return hashMap;
    }
    
    public ArrayList<PngChunk> getChunks() {
        return new ArrayList<PngChunk>(this.chunks);
    }
    
    protected static List<PngChunk> getXById(final List<PngChunk> list, final String s, final String s2) {
        if (s2 == null) {
            return ChunkHelper.filterList(list, new ChunkPredicate() {
                @Override
                public boolean match(final PngChunk pngChunk) {
                    return pngChunk.id.equals(s);
                }
            });
        }
        return ChunkHelper.filterList(list, new ChunkPredicate() {
            @Override
            public boolean match(final PngChunk pngChunk) {
                return pngChunk.id.equals(s) && (!(pngChunk instanceof PngChunkTextVar) || ((PngChunkTextVar)pngChunk).getKey().equals(s2)) && (!(pngChunk instanceof PngChunkSPLT) || ((PngChunkSPLT)pngChunk).getPalName().equals(s2));
            }
        });
    }
    
    public void appendReadChunk(final PngChunk pngChunk, final int chunkGroup) {
        pngChunk.setChunkGroup(chunkGroup);
        this.chunks.add(pngChunk);
    }
    
    public List<? extends PngChunk> getById(final String s) {
        return this.getById(s, null);
    }
    
    public List<? extends PngChunk> getById(final String s, final String s2) {
        return getXById(this.chunks, s, s2);
    }
    
    public PngChunk getById1(final String s) {
        return this.getById1(s, false);
    }
    
    public PngChunk getById1(final String s, final boolean b) {
        return this.getById1(s, null, b);
    }
    
    public PngChunk getById1(final String s, final String s2, final boolean b) {
        final List<? extends PngChunk> byId = this.getById(s, s2);
        if (byId.isEmpty()) {
            return null;
        }
        if (byId.size() > 1 && (b || !((PngChunk)byId.get(0)).allowsMultiple())) {
            throw new PngjException("unexpected multiple chunks id=" + s);
        }
        return (PngChunk)byId.get(byId.size() - 1);
    }
    
    public List<PngChunk> getEquivalent(final PngChunk pngChunk) {
        return ChunkHelper.filterList(this.chunks, new ChunkPredicate() {
            @Override
            public boolean match(final PngChunk pngChunk) {
                return ChunkHelper.equivalent(pngChunk, pngChunk);
            }
        });
    }
    
    @Override
    public String toString() {
        return "ChunkList: read: " + this.chunks.size();
    }
    
    public String toStringFull() {
        final StringBuilder sb = new StringBuilder(this.toString());
        sb.append("\n Read:\n");
        for (final PngChunk pngChunk : this.chunks) {
            sb.append(pngChunk).append(" G=" + pngChunk.getChunkGroup() + "\n");
        }
        return sb.toString();
    }
}
