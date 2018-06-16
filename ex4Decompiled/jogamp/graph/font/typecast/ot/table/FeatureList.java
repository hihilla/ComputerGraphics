// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;

public class FeatureList
{
    private final int _featureCount;
    private final FeatureRecord[] _featureRecords;
    private final Feature[] _features;
    
    public FeatureList(final DataInputStream dataInputStream, final int n) throws IOException {
        dataInputStream.reset();
        dataInputStream.skipBytes(n);
        this._featureCount = dataInputStream.readUnsignedShort();
        this._featureRecords = new FeatureRecord[this._featureCount];
        this._features = new Feature[this._featureCount];
        for (int i = 0; i < this._featureCount; ++i) {
            this._featureRecords[i] = new FeatureRecord(dataInputStream);
        }
        for (int j = 0; j < this._featureCount; ++j) {
            dataInputStream.reset();
            dataInputStream.skipBytes(n + this._featureRecords[j].getOffset());
            this._features[j] = new Feature(dataInputStream);
        }
    }
    
    public int getFeatureCount() {
        return this._featureCount;
    }
    
    public FeatureRecord getFeatureRecord(final int n) {
        return this._featureRecords[n];
    }
    
    public Feature getFeature(final int n) {
        return this._features[n];
    }
    
    public Feature findFeature(final LangSys langSys, final String s) {
        if (s.length() != 4) {
            return null;
        }
        final char c = (char)(s.charAt(0) << 24 | s.charAt(1) << 16 | s.charAt(2) << 8 | s.charAt(3));
        for (int i = 0; i < this._featureCount; ++i) {
            if (this._featureRecords[i].getTag() == c && langSys.isFeatureIndexed(i)) {
                return this._features[i];
            }
        }
        return null;
    }
}
