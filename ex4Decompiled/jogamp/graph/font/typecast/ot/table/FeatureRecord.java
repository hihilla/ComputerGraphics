// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class FeatureRecord
{
    private final int _tag;
    private final int _offset;
    
    protected FeatureRecord(final DataInput dataInput) throws IOException {
        this._tag = dataInput.readInt();
        this._offset = dataInput.readUnsignedShort();
    }
    
    public int getTag() {
        return this._tag;
    }
    
    public int getOffset() {
        return this._offset;
    }
    
    public String getTagAsString() {
        return new StringBuilder().append((char)(this._tag >> 24 & 0xFF)).append((char)(this._tag >> 16 & 0xFF)).append((char)(this._tag >> 8 & 0xFF)).append((char)(this._tag & 0xFF)).toString();
    }
}
