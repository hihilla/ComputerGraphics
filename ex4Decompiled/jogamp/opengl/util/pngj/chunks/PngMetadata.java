// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import jogamp.opengl.util.pngj.PngjException;

public class PngMetadata
{
    private final ChunksList chunkList;
    private final boolean readonly;
    
    public PngMetadata(final ChunksList chunkList) {
        this.chunkList = chunkList;
        if (chunkList instanceof ChunksListForWrite) {
            this.readonly = false;
        }
        else {
            this.readonly = true;
        }
    }
    
    public void queueChunk(final PngChunk pngChunk, final boolean b) {
        final ChunksListForWrite chunkListW = this.getChunkListW();
        if (this.readonly) {
            throw new PngjException("cannot set chunk : readonly metadata");
        }
        if (b) {
            ChunkHelper.trimList(chunkListW.getQueuedChunks(), new ChunkPredicate() {
                @Override
                public boolean match(final PngChunk pngChunk) {
                    return ChunkHelper.equivalent(pngChunk, pngChunk);
                }
            });
        }
        chunkListW.queue(pngChunk);
    }
    
    public void queueChunk(final PngChunk pngChunk) {
        this.queueChunk(pngChunk, true);
    }
    
    private ChunksListForWrite getChunkListW() {
        return (ChunksListForWrite)this.chunkList;
    }
    
    public double[] getDpi() {
        final PngChunk byId1 = this.chunkList.getById1("pHYs", true);
        if (byId1 == null) {
            return new double[] { -1.0, -1.0 };
        }
        return ((PngChunkPHYS)byId1).getAsDpi2();
    }
    
    public void setDpi(final double n) {
        this.setDpi(n, n);
    }
    
    public void setDpi(final double n, final double n2) {
        final PngChunkPHYS pngChunkPHYS = new PngChunkPHYS(this.chunkList.imageInfo);
        pngChunkPHYS.setAsDpi2(n, n2);
        this.queueChunk(pngChunkPHYS);
    }
    
    public PngChunkTIME setTimeNow(final int now) {
        final PngChunkTIME pngChunkTIME = new PngChunkTIME(this.chunkList.imageInfo);
        pngChunkTIME.setNow(now);
        this.queueChunk(pngChunkTIME);
        return pngChunkTIME;
    }
    
    public PngChunkTIME setTimeNow() {
        return this.setTimeNow(0);
    }
    
    public PngChunkTIME setTimeYMDHMS(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final PngChunkTIME pngChunkTIME = new PngChunkTIME(this.chunkList.imageInfo);
        pngChunkTIME.setYMDHMS(n, n2, n3, n4, n5, n6);
        this.queueChunk(pngChunkTIME, true);
        return pngChunkTIME;
    }
    
    public PngChunkTIME getTime() {
        return (PngChunkTIME)this.chunkList.getById1("tIME");
    }
    
    public String getTimeAsString() {
        final PngChunkTIME time = this.getTime();
        return (time == null) ? "" : time.getAsString();
    }
    
    public PngChunkTextVar setText(final String langtag, final String s, final boolean b, final boolean b2) {
        if (b2 && !b) {
            throw new PngjException("cannot compress non latin text");
        }
        PngChunkTextVar pngChunkTextVar;
        if (b) {
            if (b2) {
                pngChunkTextVar = new PngChunkZTXT(this.chunkList.imageInfo);
            }
            else {
                pngChunkTextVar = new PngChunkTEXT(this.chunkList.imageInfo);
            }
        }
        else {
            pngChunkTextVar = new PngChunkITXT(this.chunkList.imageInfo);
            ((PngChunkITXT)pngChunkTextVar).setLangtag(langtag);
        }
        pngChunkTextVar.setKeyVal(langtag, s);
        this.queueChunk(pngChunkTextVar, true);
        return pngChunkTextVar;
    }
    
    public PngChunkTextVar setText(final String s, final String s2) {
        return this.setText(s, s2, false, false);
    }
    
    public List<? extends PngChunkTextVar> getTxtsForKey(final String s) {
        final ArrayList<Object> list = (ArrayList<Object>)new ArrayList<PngChunkTextVar>();
        list.addAll(this.chunkList.getById("tEXt", s));
        list.addAll(this.chunkList.getById("zTXt", s));
        list.addAll(this.chunkList.getById("iTXt", s));
        return (List<? extends PngChunkTextVar>)list;
    }
    
    public String getTxtForKey(final String s) {
        final List<? extends PngChunkTextVar> txtsForKey = this.getTxtsForKey(s);
        if (txtsForKey.isEmpty()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        final Iterator<? extends PngChunkTextVar> iterator = txtsForKey.iterator();
        while (iterator.hasNext()) {
            sb.append(((PngChunkTextVar)iterator.next()).getVal()).append("\n");
        }
        return sb.toString().trim();
    }
    
    public PngChunkPLTE getPLTE() {
        return (PngChunkPLTE)this.chunkList.getById1("PLTE");
    }
    
    public PngChunkPLTE createPLTEChunk() {
        final PngChunkPLTE pngChunkPLTE = new PngChunkPLTE(this.chunkList.imageInfo);
        this.queueChunk(pngChunkPLTE);
        return pngChunkPLTE;
    }
    
    public PngChunkTRNS getTRNS() {
        return (PngChunkTRNS)this.chunkList.getById1("tRNS");
    }
    
    public PngChunkTRNS createTRNSChunk() {
        final PngChunkTRNS pngChunkTRNS = new PngChunkTRNS(this.chunkList.imageInfo);
        this.queueChunk(pngChunkTRNS);
        return pngChunkTRNS;
    }
}
