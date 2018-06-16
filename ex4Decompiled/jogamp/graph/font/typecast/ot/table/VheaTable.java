// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Fixed;
import java.io.IOException;
import java.io.DataInput;

public class VheaTable implements Table
{
    private final DirectoryEntry _de;
    private final int _version;
    private final short _ascent;
    private final short _descent;
    private final short _lineGap;
    private final short _advanceHeightMax;
    private final short _minTopSideBearing;
    private final short _minBottomSideBearing;
    private final short _yMaxExtent;
    private final short _caretSlopeRise;
    private final short _caretSlopeRun;
    private final short _metricDataFormat;
    private final int _numberOfLongVerMetrics;
    
    protected VheaTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._version = dataInput.readInt();
        this._ascent = dataInput.readShort();
        this._descent = dataInput.readShort();
        this._lineGap = dataInput.readShort();
        this._advanceHeightMax = dataInput.readShort();
        this._minTopSideBearing = dataInput.readShort();
        this._minBottomSideBearing = dataInput.readShort();
        this._yMaxExtent = dataInput.readShort();
        this._caretSlopeRise = dataInput.readShort();
        this._caretSlopeRun = dataInput.readShort();
        for (int i = 0; i < 5; ++i) {
            dataInput.readShort();
        }
        this._metricDataFormat = dataInput.readShort();
        this._numberOfLongVerMetrics = dataInput.readUnsignedShort();
    }
    
    public short getAdvanceHeightMax() {
        return this._advanceHeightMax;
    }
    
    public short getAscent() {
        return this._ascent;
    }
    
    public short getCaretSlopeRise() {
        return this._caretSlopeRise;
    }
    
    public short getCaretSlopeRun() {
        return this._caretSlopeRun;
    }
    
    public short getDescent() {
        return this._descent;
    }
    
    public short getLineGap() {
        return this._lineGap;
    }
    
    public short getMetricDataFormat() {
        return this._metricDataFormat;
    }
    
    public short getMinTopSideBearing() {
        return this._minTopSideBearing;
    }
    
    public short getMinBottomSideBearing() {
        return this._minBottomSideBearing;
    }
    
    public int getNumberOfLongVerMetrics() {
        return this._numberOfLongVerMetrics;
    }
    
    @Override
    public int getType() {
        return 1986553185;
    }
    
    public short getYMaxExtent() {
        return this._yMaxExtent;
    }
    
    @Override
    public String toString() {
        return "'vhea' Table - Vertical Header\n------------------------------" + "\n        'vhea' version:       " + Fixed.floatValue(this._version) + "\n        xAscender:            " + this._ascent + "\n        xDescender:           " + this._descent + "\n        xLineGap:             " + this._lineGap + "\n        advanceHeightMax:     " + this._advanceHeightMax + "\n        minTopSideBearing:    " + this._minTopSideBearing + "\n        minBottomSideBearing: " + this._minBottomSideBearing + "\n        yMaxExtent:           " + this._yMaxExtent + "\n        horizCaretSlopeNum:   " + this._caretSlopeRise + "\n        horizCaretSlopeDenom: " + this._caretSlopeRun + "\n        reserved0:            0" + "\n        reserved1:            0" + "\n        reserved2:            0" + "\n        reserved3:            0" + "\n        reserved4:            0" + "\n        metricDataFormat:     " + this._metricDataFormat + "\n        numOf_LongVerMetrics: " + this._numberOfLongVerMetrics;
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
