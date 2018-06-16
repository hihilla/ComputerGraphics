// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class RangeRecord
{
    private final int _start;
    private final int _end;
    private final int _startCoverageIndex;
    
    public RangeRecord(final DataInput dataInput) throws IOException {
        this._start = dataInput.readUnsignedShort();
        this._end = dataInput.readUnsignedShort();
        this._startCoverageIndex = dataInput.readUnsignedShort();
    }
    
    public boolean isInRange(final int n) {
        return this._start <= n && n <= this._end;
    }
    
    public int getCoverageIndex(final int n) {
        if (this.isInRange(n)) {
            return this._startCoverageIndex + n - this._start;
        }
        return -1;
    }
}
