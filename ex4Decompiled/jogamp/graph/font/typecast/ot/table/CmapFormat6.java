// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class CmapFormat6 extends CmapFormat
{
    private short _firstCode;
    private short _entryCount;
    private short[] _glyphIdArray;
    
    protected CmapFormat6(final DataInput dataInput) throws IOException {
        super(dataInput);
        this._format = 6;
    }
    
    @Override
    public int getRangeCount() {
        return 0;
    }
    
    @Override
    public Range getRange(final int n) throws ArrayIndexOutOfBoundsException {
        throw new ArrayIndexOutOfBoundsException();
    }
    
    @Override
    public int mapCharCode(final int n) {
        return 0;
    }
}
