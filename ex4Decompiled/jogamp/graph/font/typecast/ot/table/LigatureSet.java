// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;

public class LigatureSet
{
    private final int _ligatureCount;
    private final int[] _ligatureOffsets;
    private final Ligature[] _ligatures;
    
    public LigatureSet(final DataInputStream dataInputStream, final int n) throws IOException {
        dataInputStream.reset();
        dataInputStream.skipBytes(n);
        this._ligatureCount = dataInputStream.readUnsignedShort();
        this._ligatureOffsets = new int[this._ligatureCount];
        this._ligatures = new Ligature[this._ligatureCount];
        for (int i = 0; i < this._ligatureCount; ++i) {
            this._ligatureOffsets[i] = dataInputStream.readUnsignedShort();
        }
        for (int j = 0; j < this._ligatureCount; ++j) {
            dataInputStream.reset();
            dataInputStream.skipBytes(n + this._ligatureOffsets[j]);
            this._ligatures[j] = new Ligature(dataInputStream);
        }
    }
}
