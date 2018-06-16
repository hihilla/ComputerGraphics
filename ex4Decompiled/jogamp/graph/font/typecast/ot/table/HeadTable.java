// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Fixed;
import java.io.IOException;
import java.io.DataInput;

public class HeadTable implements Table
{
    private final DirectoryEntry _de;
    private final int _versionNumber;
    private final int _fontRevision;
    private final int _checkSumAdjustment;
    private final int _magicNumber;
    private final short _flags;
    private final short _unitsPerEm;
    private final long _created;
    private final long _modified;
    private final short _xMin;
    private final short _yMin;
    private final short _xMax;
    private final short _yMax;
    private final short _macStyle;
    private final short _lowestRecPPEM;
    private final short _fontDirectionHint;
    private final short _indexToLocFormat;
    private final short _glyphDataFormat;
    
    protected HeadTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._versionNumber = dataInput.readInt();
        this._fontRevision = dataInput.readInt();
        this._checkSumAdjustment = dataInput.readInt();
        this._magicNumber = dataInput.readInt();
        this._flags = dataInput.readShort();
        this._unitsPerEm = dataInput.readShort();
        this._created = dataInput.readLong();
        this._modified = dataInput.readLong();
        this._xMin = dataInput.readShort();
        this._yMin = dataInput.readShort();
        this._xMax = dataInput.readShort();
        this._yMax = dataInput.readShort();
        this._macStyle = dataInput.readShort();
        this._lowestRecPPEM = dataInput.readShort();
        this._fontDirectionHint = dataInput.readShort();
        this._indexToLocFormat = dataInput.readShort();
        this._glyphDataFormat = dataInput.readShort();
    }
    
    public int getCheckSumAdjustment() {
        return this._checkSumAdjustment;
    }
    
    public long getCreated() {
        return this._created;
    }
    
    public short getFlags() {
        return this._flags;
    }
    
    public short getFontDirectionHint() {
        return this._fontDirectionHint;
    }
    
    public int getFontRevision() {
        return this._fontRevision;
    }
    
    public short getGlyphDataFormat() {
        return this._glyphDataFormat;
    }
    
    public short getIndexToLocFormat() {
        return this._indexToLocFormat;
    }
    
    public short getLowestRecPPEM() {
        return this._lowestRecPPEM;
    }
    
    public short getMacStyle() {
        return this._macStyle;
    }
    
    public long getModified() {
        return this._modified;
    }
    
    @Override
    public int getType() {
        return 1751474532;
    }
    
    public short getUnitsPerEm() {
        return this._unitsPerEm;
    }
    
    public int getVersionNumber() {
        return this._versionNumber;
    }
    
    public short getXMax() {
        return this._xMax;
    }
    
    public short getXMin() {
        return this._xMin;
    }
    
    public short getYMax() {
        return this._yMax;
    }
    
    public short getYMin() {
        return this._yMin;
    }
    
    @Override
    public String toString() {
        return "'head' Table - Font Header\n--------------------------" + "\n  'head' version:      " + Fixed.floatValue(this._versionNumber) + "\n  fontRevision:        " + Fixed.roundedFloatValue(this._fontRevision, 8) + "\n  checkSumAdjustment:  0x" + Integer.toHexString(this._checkSumAdjustment).toUpperCase() + "\n  magicNumber:         0x" + Integer.toHexString(this._magicNumber).toUpperCase() + "\n  flags:               0x" + Integer.toHexString(this._flags).toUpperCase() + "\n  unitsPerEm:          " + this._unitsPerEm + "\n  created:             " + this._created + "\n  modified:            " + this._modified + "\n  xMin:                " + this._xMin + "\n  yMin:                " + this._yMin + "\n  xMax:                " + this._xMax + "\n  yMax:                " + this._yMax + "\n  macStyle bits:       " + Integer.toHexString(this._macStyle).toUpperCase() + "\n  lowestRecPPEM:       " + this._lowestRecPPEM + "\n  fontDirectionHint:   " + this._fontDirectionHint + "\n  indexToLocFormat:    " + this._indexToLocFormat + "\n  glyphDataFormat:     " + this._glyphDataFormat;
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
