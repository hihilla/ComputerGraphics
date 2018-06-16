// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class Feature
{
    private final int _featureParams;
    private final int _lookupCount;
    private final int[] _lookupListIndex;
    
    protected Feature(final DataInput dataInput) throws IOException {
        this._featureParams = dataInput.readUnsignedShort();
        this._lookupCount = dataInput.readUnsignedShort();
        this._lookupListIndex = new int[this._lookupCount];
        for (int i = 0; i < this._lookupCount; ++i) {
            this._lookupListIndex[i] = dataInput.readUnsignedShort();
        }
    }
    
    public int getLookupCount() {
        return this._lookupCount;
    }
    
    public int getLookupListIndex(final int n) {
        return this._lookupListIndex[n];
    }
}
