// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class CoverageFormat2 extends Coverage
{
    private final int _rangeCount;
    private final RangeRecord[] _rangeRecords;
    
    protected CoverageFormat2(final DataInput dataInput) throws IOException {
        this._rangeCount = dataInput.readUnsignedShort();
        this._rangeRecords = new RangeRecord[this._rangeCount];
        for (int i = 0; i < this._rangeCount; ++i) {
            this._rangeRecords[i] = new RangeRecord(dataInput);
        }
    }
    
    @Override
    public int getFormat() {
        return 2;
    }
    
    @Override
    public int findGlyph(final int n) {
        for (int i = 0; i < this._rangeCount; ++i) {
            final int coverageIndex = this._rangeRecords[i].getCoverageIndex(n);
            if (coverageIndex > -1) {
                return coverageIndex;
            }
        }
        return -1;
    }
}
