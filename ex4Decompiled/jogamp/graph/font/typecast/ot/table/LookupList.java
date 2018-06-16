// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInputStream;

public class LookupList
{
    private final int _lookupCount;
    private final int[] _lookupOffsets;
    private final Lookup[] _lookups;
    
    public LookupList(final DataInputStream dataInputStream, final int n, final LookupSubtableFactory lookupSubtableFactory) throws IOException {
        dataInputStream.reset();
        dataInputStream.skipBytes(n);
        this._lookupCount = dataInputStream.readUnsignedShort();
        this._lookupOffsets = new int[this._lookupCount];
        this._lookups = new Lookup[this._lookupCount];
        for (int i = 0; i < this._lookupCount; ++i) {
            this._lookupOffsets[i] = dataInputStream.readUnsignedShort();
        }
        for (int j = 0; j < this._lookupCount; ++j) {
            this._lookups[j] = new Lookup(lookupSubtableFactory, dataInputStream, n + this._lookupOffsets[j]);
        }
    }
    
    public int getLookupCount() {
        return this._lookupCount;
    }
    
    public int getLookupOffset(final int n) {
        return this._lookupOffsets[n];
    }
    
    public Lookup getLookup(final int n) {
        return this._lookups[n];
    }
    
    public Lookup getLookup(final Feature feature, final int n) {
        if (feature.getLookupCount() > n) {
            return this._lookups[feature.getLookupListIndex(n)];
        }
        return null;
    }
}
