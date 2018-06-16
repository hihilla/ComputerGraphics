// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.util.HashMap;
import java.io.OutputStream;
import jogamp.opengl.util.pngj.PngjExceptionInternal;
import java.util.Map;
import jogamp.opengl.util.pngj.ImageInfo;

public abstract class PngChunk
{
    public final String id;
    public final boolean crit;
    public final boolean pub;
    public final boolean safe;
    protected final ImageInfo imgInfo;
    private boolean priority;
    protected int chunkGroup;
    protected int length;
    protected long offset;
    private static final Map<String, Class<? extends PngChunk>> factoryMap;
    
    public static void factoryRegister(final String s, final Class<? extends PngChunk> clazz) {
        PngChunk.factoryMap.put(s, clazz);
    }
    
    public static boolean isKnown(final String s) {
        return PngChunk.factoryMap.containsKey(s);
    }
    
    protected PngChunk(final String id, final ImageInfo imgInfo) {
        this.priority = false;
        this.chunkGroup = -1;
        this.length = -1;
        this.offset = 0L;
        this.id = id;
        this.imgInfo = imgInfo;
        this.crit = ChunkHelper.isCritical(id);
        this.pub = ChunkHelper.isPublic(id);
        this.safe = ChunkHelper.isSafeToCopy(id);
    }
    
    public static PngChunk factory(final ChunkRaw chunkRaw, final ImageInfo imageInfo) {
        final PngChunk factoryFromId = factoryFromId(ChunkHelper.toString(chunkRaw.idbytes), imageInfo);
        factoryFromId.length = chunkRaw.len;
        factoryFromId.parseFromRaw(chunkRaw);
        return factoryFromId;
    }
    
    public static PngChunk factoryFromId(final String s, final ImageInfo imageInfo) {
        PngChunk pngChunk = null;
        try {
            final Class<? extends PngChunk> clazz = PngChunk.factoryMap.get(s);
            if (clazz != null) {
                pngChunk = (PngChunk)clazz.getConstructor(ImageInfo.class).newInstance(imageInfo);
            }
        }
        catch (Exception ex) {}
        if (pngChunk == null) {
            pngChunk = new PngChunkUNKNOWN(s, imageInfo);
        }
        return pngChunk;
    }
    
    protected final ChunkRaw createEmptyChunk(final int n, final boolean b) {
        return new ChunkRaw(n, ChunkHelper.toBytes(this.id), b);
    }
    
    public static <T extends PngChunk> T cloneChunk(final T t, final ImageInfo imageInfo) {
        final PngChunk factoryFromId = factoryFromId(t.id, imageInfo);
        if (factoryFromId.getClass() != t.getClass()) {
            throw new PngjExceptionInternal("bad class cloning chunk: " + factoryFromId.getClass() + " " + t.getClass());
        }
        factoryFromId.cloneDataFromRead(t);
        return (T)factoryFromId;
    }
    
    public final int getChunkGroup() {
        return this.chunkGroup;
    }
    
    public final void setChunkGroup(final int chunkGroup) {
        this.chunkGroup = chunkGroup;
    }
    
    public boolean hasPriority() {
        return this.priority;
    }
    
    public void setPriority(final boolean priority) {
        this.priority = priority;
    }
    
    final void write(final OutputStream outputStream) {
        final ChunkRaw rawChunk = this.createRawChunk();
        if (rawChunk == null) {
            throw new PngjExceptionInternal("null chunk ! creation failed for " + this);
        }
        rawChunk.writeChunk(outputStream);
    }
    
    public int getLength() {
        return this.length;
    }
    
    public long getOffset() {
        return this.offset;
    }
    
    public void setOffset(final long offset) {
        this.offset = offset;
    }
    
    public abstract ChunkRaw createRawChunk();
    
    public abstract void parseFromRaw(final ChunkRaw p0);
    
    public abstract void cloneDataFromRead(final PngChunk p0);
    
    public abstract boolean allowsMultiple();
    
    public abstract ChunkOrderingConstraint getOrderingConstraint();
    
    @Override
    public String toString() {
        return "chunk id= " + this.id + " (len=" + this.length + " offset=" + this.offset + ") c=" + this.getClass().getSimpleName();
    }
    
    static {
        (factoryMap = new HashMap<String, Class<? extends PngChunk>>()).put("IDAT", PngChunkIDAT.class);
        PngChunk.factoryMap.put("IHDR", PngChunkIHDR.class);
        PngChunk.factoryMap.put("PLTE", PngChunkPLTE.class);
        PngChunk.factoryMap.put("IEND", PngChunkIEND.class);
        PngChunk.factoryMap.put("tEXt", PngChunkTEXT.class);
        PngChunk.factoryMap.put("iTXt", PngChunkITXT.class);
        PngChunk.factoryMap.put("zTXt", PngChunkZTXT.class);
        PngChunk.factoryMap.put("bKGD", PngChunkBKGD.class);
        PngChunk.factoryMap.put("gAMA", PngChunkGAMA.class);
        PngChunk.factoryMap.put("pHYs", PngChunkPHYS.class);
        PngChunk.factoryMap.put("iCCP", PngChunkICCP.class);
        PngChunk.factoryMap.put("tIME", PngChunkTIME.class);
        PngChunk.factoryMap.put("tRNS", PngChunkTRNS.class);
        PngChunk.factoryMap.put("cHRM", PngChunkCHRM.class);
        PngChunk.factoryMap.put("sBIT", PngChunkSBIT.class);
        PngChunk.factoryMap.put("sRGB", PngChunkSRGB.class);
        PngChunk.factoryMap.put("hIST", PngChunkHIST.class);
        PngChunk.factoryMap.put("sPLT", PngChunkSPLT.class);
        PngChunk.factoryMap.put("oFFs", PngChunkOFFS.class);
        PngChunk.factoryMap.put("sTER", PngChunkSTER.class);
    }
    
    public enum ChunkOrderingConstraint
    {
        NONE, 
        BEFORE_PLTE_AND_IDAT, 
        AFTER_PLTE_BEFORE_IDAT, 
        BEFORE_IDAT, 
        NA;
        
        public boolean mustGoBeforePLTE() {
            return this == ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
        }
        
        public boolean mustGoBeforeIDAT() {
            return this == ChunkOrderingConstraint.BEFORE_IDAT || this == ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT || this == ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
        }
        
        public boolean mustGoAfterPLTE() {
            return this == ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
        }
    }
}
