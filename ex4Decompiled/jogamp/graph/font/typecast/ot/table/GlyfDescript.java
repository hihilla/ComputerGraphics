// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public abstract class GlyfDescript extends Program implements GlyphDescription
{
    public static final byte onCurve = 1;
    public static final byte xShortVector = 2;
    public static final byte yShortVector = 4;
    public static final byte repeat = 8;
    public static final byte xDual = 16;
    public static final byte yDual = 32;
    protected GlyfTable _parentTable;
    private int _glyphIndex;
    private final int _numberOfContours;
    private final short _xMin;
    private final short _yMin;
    private final short _xMax;
    private final short _yMax;
    
    protected GlyfDescript(final GlyfTable parentTable, final int n, final short numberOfContours, final DataInput dataInput) throws IOException {
        this._parentTable = parentTable;
        this._numberOfContours = numberOfContours;
        this._xMin = dataInput.readShort();
        this._yMin = dataInput.readShort();
        this._xMax = dataInput.readShort();
        this._yMax = dataInput.readShort();
    }
    
    public int getNumberOfContours() {
        return this._numberOfContours;
    }
    
    @Override
    public int getGlyphIndex() {
        return this._glyphIndex;
    }
    
    @Override
    public short getXMaximum() {
        return this._xMax;
    }
    
    @Override
    public short getXMinimum() {
        return this._xMin;
    }
    
    @Override
    public short getYMaximum() {
        return this._yMax;
    }
    
    @Override
    public short getYMinimum() {
        return this._yMin;
    }
    
    @Override
    public String toString() {
        return "          numberOfContours: " + this._numberOfContours + "\n          xMin:             " + this._xMin + "\n          yMin:             " + this._yMin + "\n          xMax:             " + this._xMax + "\n          yMax:             " + this._yMax;
    }
}
