// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class CmapFormat4 extends CmapFormat
{
    private final int _segCountX2;
    private final int _searchRange;
    private final int _entrySelector;
    private final int _rangeShift;
    private final int[] _endCode;
    private final int[] _startCode;
    private final int[] _idDelta;
    private final int[] _idRangeOffset;
    private final int[] _glyphIdArray;
    private final int _segCount;
    
    protected CmapFormat4(final DataInput dataInput) throws IOException {
        super(dataInput);
        this._format = 4;
        this._segCountX2 = dataInput.readUnsignedShort();
        this._segCount = this._segCountX2 / 2;
        this._endCode = new int[this._segCount];
        this._startCode = new int[this._segCount];
        this._idDelta = new int[this._segCount];
        this._idRangeOffset = new int[this._segCount];
        this._searchRange = dataInput.readUnsignedShort();
        this._entrySelector = dataInput.readUnsignedShort();
        this._rangeShift = dataInput.readUnsignedShort();
        for (int i = 0; i < this._segCount; ++i) {
            this._endCode[i] = dataInput.readUnsignedShort();
        }
        dataInput.readUnsignedShort();
        for (int j = 0; j < this._segCount; ++j) {
            this._startCode[j] = dataInput.readUnsignedShort();
        }
        for (int k = 0; k < this._segCount; ++k) {
            this._idDelta[k] = dataInput.readUnsignedShort();
        }
        for (int l = 0; l < this._segCount; ++l) {
            this._idRangeOffset[l] = dataInput.readUnsignedShort();
        }
        final int n = (this._length - (8 * this._segCount + 16)) / 2;
        this._glyphIdArray = new int[n];
        for (int n2 = 0; n2 < n; ++n2) {
            this._glyphIdArray[n2] = dataInput.readUnsignedShort();
        }
    }
    
    @Override
    public int getRangeCount() {
        return this._segCount;
    }
    
    @Override
    public Range getRange(final int n) throws ArrayIndexOutOfBoundsException {
        if (n < 0 || n >= this._segCount) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return new Range(this._startCode[n], this._endCode[n]);
    }
    
    @Override
    public int mapCharCode(final int n) {
        try {
            int i = 0;
            while (i < this._segCount) {
                if (this._endCode[i] >= n) {
                    if (this._startCode[i] > n) {
                        break;
                    }
                    if (this._idRangeOffset[i] > 0) {
                        return this._glyphIdArray[this._idRangeOffset[i] / 2 + (n - this._startCode[i]) - (this._segCount - i)];
                    }
                    return (this._idDelta[i] + n) % 65536;
                }
                else {
                    ++i;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("error: Array out of bounds - " + ex.getMessage());
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", segCountX2: " + this._segCountX2 + ", searchRange: " + this._searchRange + ", entrySelector: " + this._entrySelector + ", rangeShift: " + this._rangeShift + ", endCodeLen: " + this._endCode.length + ", startCodeLen: " + this._endCode.length + ", idDeltaLen: " + this._idDelta.length + ", idRangeOffsetLen: " + this._idRangeOffset.length;
    }
}
