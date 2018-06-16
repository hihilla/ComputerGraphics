// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class CoverageFormat1 extends Coverage
{
    private final int _glyphCount;
    private final int[] _glyphIds;
    
    protected CoverageFormat1(final DataInput dataInput) throws IOException {
        this._glyphCount = dataInput.readUnsignedShort();
        this._glyphIds = new int[this._glyphCount];
        for (int i = 0; i < this._glyphCount; ++i) {
            this._glyphIds[i] = dataInput.readUnsignedShort();
        }
    }
    
    @Override
    public int getFormat() {
        return 1;
    }
    
    @Override
    public int findGlyph(final int n) {
        for (int i = 0; i < this._glyphCount; ++i) {
            if (this._glyphIds[i] == n) {
                return i;
            }
        }
        return -1;
    }
}
