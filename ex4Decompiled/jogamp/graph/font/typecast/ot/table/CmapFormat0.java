// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class CmapFormat0 extends CmapFormat
{
    private final int[] _glyphIdArray;
    
    protected CmapFormat0(final DataInput dataInput) throws IOException {
        super(dataInput);
        this._glyphIdArray = new int[256];
        this._format = 0;
        for (int i = 0; i < 256; ++i) {
            this._glyphIdArray[i] = dataInput.readUnsignedByte();
        }
    }
    
    @Override
    public int getRangeCount() {
        return 1;
    }
    
    @Override
    public Range getRange(final int n) throws ArrayIndexOutOfBoundsException {
        if (n != 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new Range(0, 255);
    }
    
    @Override
    public int mapCharCode(final int n) {
        if (0 <= n && n < 256) {
            return this._glyphIdArray[n];
        }
        return 0;
    }
}
