// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class CmapIndexEntry implements Comparable
{
    private final int _platformId;
    private final int _encodingId;
    private final int _offset;
    private CmapFormat _format;
    
    protected CmapIndexEntry(final DataInput dataInput) throws IOException {
        this._platformId = dataInput.readUnsignedShort();
        this._encodingId = dataInput.readUnsignedShort();
        this._offset = dataInput.readInt();
    }
    
    public int getPlatformId() {
        return this._platformId;
    }
    
    public int getEncodingId() {
        return this._encodingId;
    }
    
    public int getOffset() {
        return this._offset;
    }
    
    public CmapFormat getFormat() {
        return this._format;
    }
    
    public void setFormat(final CmapFormat format) {
        this._format = format;
    }
    
    @Override
    public String toString() {
        return "platform id: " + this._platformId + " (" + ID.getPlatformName((short)this._platformId) + "), encoding id: " + this._encodingId + " (" + ID.getEncodingName((short)this._platformId, (short)this._encodingId) + "), offset: " + this._offset;
    }
    
    @Override
    public int compareTo(final Object o) {
        final CmapIndexEntry cmapIndexEntry = (CmapIndexEntry)o;
        if (this.getOffset() < cmapIndexEntry.getOffset()) {
            return -1;
        }
        if (this.getOffset() > cmapIndexEntry.getOffset()) {
            return 1;
        }
        return 0;
    }
}
