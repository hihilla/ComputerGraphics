// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class CmapFormat2 extends CmapFormat
{
    private final int[] _subHeaderKeys;
    private final SubHeader[] _subHeaders;
    private final int[] _glyphIndexArray;
    
    protected CmapFormat2(final DataInput dataInput) throws IOException {
        super(dataInput);
        this._subHeaderKeys = new int[256];
        this._format = 2;
        int n = 6;
        int max = 0;
        for (int i = 0; i < 256; ++i) {
            this._subHeaderKeys[i] = dataInput.readUnsignedShort();
            max = Math.max(max, this._subHeaderKeys[i]);
            n += 2;
        }
        final int n2 = max / 8 + 1;
        this._subHeaders = new SubHeader[n2];
        final int n3 = 8 * n2 + 518;
        int max2 = 0;
        for (int j = 0; j < this._subHeaders.length; ++j) {
            final SubHeader subHeader = new SubHeader();
            subHeader._firstCode = dataInput.readUnsignedShort();
            subHeader._entryCount = dataInput.readUnsignedShort();
            subHeader._idDelta = dataInput.readShort();
            subHeader._idRangeOffset = dataInput.readUnsignedShort();
            n += 8;
            subHeader._arrayIndex = (n - 2 + subHeader._idRangeOffset - n3) / 2;
            max2 = Math.max(max2, subHeader._arrayIndex + subHeader._entryCount);
            this._subHeaders[j] = subHeader;
        }
        this._glyphIndexArray = new int[max2];
        for (int k = 0; k < this._glyphIndexArray.length; ++k) {
            this._glyphIndexArray[k] = dataInput.readUnsignedShort();
        }
    }
    
    @Override
    public int getRangeCount() {
        return this._subHeaders.length;
    }
    
    @Override
    public Range getRange(final int n) throws ArrayIndexOutOfBoundsException {
        if (n < 0 || n >= this._subHeaders.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n2 = 0;
        if (n != 0) {
            for (int i = 0; i < 256; ++i) {
                if (this._subHeaderKeys[i] / 8 == n) {
                    n2 = i << 8;
                    break;
                }
            }
        }
        return new Range(n2 | this._subHeaders[n]._firstCode, n2 | this._subHeaders[n]._firstCode + this._subHeaders[n]._entryCount - 1);
    }
    
    @Override
    public int mapCharCode(final int n) {
        int n2 = 0;
        final int n3 = n >> 8;
        if (n3 != 0) {
            n2 = this._subHeaderKeys[n3] / 8;
        }
        final SubHeader subHeader = this._subHeaders[n2];
        final int n4 = n & 0xFF;
        if (n4 < subHeader._firstCode || n4 >= subHeader._firstCode + subHeader._entryCount) {
            return 0;
        }
        int n5 = this._glyphIndexArray[subHeader._arrayIndex + (n4 - subHeader._firstCode)];
        if (n5 != 0) {
            n5 = (n5 + subHeader._idDelta) % 65536;
        }
        return n5;
    }
    
    static class SubHeader
    {
        int _firstCode;
        int _entryCount;
        short _idDelta;
        int _idRangeOffset;
        int _arrayIndex;
    }
}
