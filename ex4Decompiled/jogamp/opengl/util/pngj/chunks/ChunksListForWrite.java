// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.util.Iterator;
import java.io.OutputStream;
import jogamp.opengl.util.pngj.PngjOutputException;
import jogamp.opengl.util.pngj.PngjException;
import java.util.ArrayList;
import jogamp.opengl.util.pngj.ImageInfo;
import java.util.HashMap;
import java.util.List;

public class ChunksListForWrite extends ChunksList
{
    private final List<PngChunk> queuedChunks;
    private final HashMap<String, Integer> alreadyWrittenKeys;
    
    public ChunksListForWrite(final ImageInfo imageInfo) {
        super(imageInfo);
        this.queuedChunks = new ArrayList<PngChunk>();
        this.alreadyWrittenKeys = new HashMap<String, Integer>();
    }
    
    public List<? extends PngChunk> getQueuedById(final String s) {
        return this.getQueuedById(s, null);
    }
    
    public List<? extends PngChunk> getQueuedById(final String s, final String s2) {
        return ChunksList.getXById(this.queuedChunks, s, s2);
    }
    
    public PngChunk getQueuedById1(final String s, final String s2, final boolean b) {
        final List<? extends PngChunk> queuedById = this.getQueuedById(s, s2);
        if (queuedById.isEmpty()) {
            return null;
        }
        if (queuedById.size() > 1 && (b || !((PngChunk)queuedById.get(0)).allowsMultiple())) {
            throw new PngjException("unexpected multiple chunks id=" + s);
        }
        return (PngChunk)queuedById.get(queuedById.size() - 1);
    }
    
    public PngChunk getQueuedById1(final String s, final boolean b) {
        return this.getQueuedById1(s, null, b);
    }
    
    public PngChunk getQueuedById1(final String s) {
        return this.getQueuedById1(s, false);
    }
    
    public boolean removeChunk(final PngChunk pngChunk) {
        return this.queuedChunks.remove(pngChunk);
    }
    
    public boolean queue(final PngChunk pngChunk) {
        this.queuedChunks.add(pngChunk);
        return true;
    }
    
    private static boolean shouldWrite(final PngChunk pngChunk, final int n) {
        if (n == 2) {
            return pngChunk.id.equals("PLTE");
        }
        if (n % 2 == 0) {
            throw new PngjOutputException("bad chunk group?");
        }
        int n3;
        int n2;
        if (pngChunk.getOrderingConstraint().mustGoBeforePLTE()) {
            n2 = (n3 = 1);
        }
        else if (pngChunk.getOrderingConstraint().mustGoBeforeIDAT()) {
            n2 = 3;
            n3 = (pngChunk.getOrderingConstraint().mustGoAfterPLTE() ? 3 : 1);
        }
        else {
            n2 = 5;
            n3 = 1;
        }
        int chunkGroup = n2;
        if (pngChunk.hasPriority()) {
            chunkGroup = n3;
        }
        if (ChunkHelper.isUnknown(pngChunk) && pngChunk.getChunkGroup() > 0) {
            chunkGroup = pngChunk.getChunkGroup();
        }
        return n == chunkGroup || (n > chunkGroup && n <= n2);
    }
    
    public int writeChunks(final OutputStream outputStream, final int chunkGroup) {
        int n = 0;
        final Iterator<PngChunk> iterator = this.queuedChunks.iterator();
        while (iterator.hasNext()) {
            final PngChunk pngChunk = iterator.next();
            if (!shouldWrite(pngChunk, chunkGroup)) {
                continue;
            }
            if (ChunkHelper.isCritical(pngChunk.id) && !pngChunk.id.equals("PLTE")) {
                throw new PngjOutputException("bad chunk queued: " + pngChunk);
            }
            if (this.alreadyWrittenKeys.containsKey(pngChunk.id) && !pngChunk.allowsMultiple()) {
                throw new PngjOutputException("duplicated chunk does not allow multiple: " + pngChunk);
            }
            pngChunk.write(outputStream);
            this.chunks.add(pngChunk);
            this.alreadyWrittenKeys.put(pngChunk.id, this.alreadyWrittenKeys.containsKey(pngChunk.id) ? (this.alreadyWrittenKeys.get(pngChunk.id) + 1) : 1);
            pngChunk.setChunkGroup(chunkGroup);
            iterator.remove();
            ++n;
        }
        return n;
    }
    
    public List<PngChunk> getQueuedChunks() {
        return this.queuedChunks;
    }
    
    @Override
    public String toString() {
        return "ChunkList: written: " + this.chunks.size() + " queue: " + this.queuedChunks.size();
    }
    
    @Override
    public String toStringFull() {
        final StringBuilder sb = new StringBuilder(this.toString());
        sb.append("\n Written:\n");
        for (final PngChunk pngChunk : this.chunks) {
            sb.append(pngChunk).append(" G=" + pngChunk.getChunkGroup() + "\n");
        }
        if (!this.queuedChunks.isEmpty()) {
            sb.append(" Queued:\n");
            final Iterator<PngChunk> iterator2 = this.queuedChunks.iterator();
            while (iterator2.hasNext()) {
                sb.append(iterator2.next()).append("\n");
            }
        }
        return sb.toString();
    }
}
