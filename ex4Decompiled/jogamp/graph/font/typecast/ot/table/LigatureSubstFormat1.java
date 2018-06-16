// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;

public class LigatureSubstFormat1 extends LigatureSubst
{
    private final int _coverageOffset;
    private final int _ligSetCount;
    private final int[] _ligatureSetOffsets;
    private final Coverage _coverage;
    private final LigatureSet[] _ligatureSets;
    
    protected LigatureSubstFormat1(final DataInputStream dataInputStream, final int n) throws IOException {
        this._coverageOffset = dataInputStream.readUnsignedShort();
        this._ligSetCount = dataInputStream.readUnsignedShort();
        this._ligatureSetOffsets = new int[this._ligSetCount];
        this._ligatureSets = new LigatureSet[this._ligSetCount];
        for (int i = 0; i < this._ligSetCount; ++i) {
            this._ligatureSetOffsets[i] = dataInputStream.readUnsignedShort();
        }
        dataInputStream.reset();
        dataInputStream.skipBytes(n + this._coverageOffset);
        this._coverage = Coverage.read(dataInputStream);
        for (int j = 0; j < this._ligSetCount; ++j) {
            this._ligatureSets[j] = new LigatureSet(dataInputStream, n + this._ligatureSetOffsets[j]);
        }
    }
    
    public int getFormat() {
        return 1;
    }
    
    @Override
    public String getTypeAsString() {
        return "LigatureSubstFormat1";
    }
}
