// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class LangSys
{
    private final int _lookupOrder;
    private final int _reqFeatureIndex;
    private final int _featureCount;
    private final int[] _featureIndex;
    
    protected LangSys(final DataInput dataInput) throws IOException {
        this._lookupOrder = dataInput.readUnsignedShort();
        this._reqFeatureIndex = dataInput.readUnsignedShort();
        this._featureCount = dataInput.readUnsignedShort();
        this._featureIndex = new int[this._featureCount];
        for (int i = 0; i < this._featureCount; ++i) {
            this._featureIndex[i] = dataInput.readUnsignedShort();
        }
    }
    
    public int getLookupOrder() {
        return this._lookupOrder;
    }
    
    public int getReqFeatureIndex() {
        return this._reqFeatureIndex;
    }
    
    public int getFeatureCount() {
        return this._featureCount;
    }
    
    public int getFeatureIndex(final int n) {
        return this._featureIndex[n];
    }
    
    protected boolean isFeatureIndexed(final int n) {
        for (int i = 0; i < this._featureCount; ++i) {
            if (this._featureIndex[i] == n) {
                return true;
            }
        }
        return false;
    }
}
