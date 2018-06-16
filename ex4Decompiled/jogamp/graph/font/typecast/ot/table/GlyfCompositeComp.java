// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class GlyfCompositeComp
{
    public static final short ARG_1_AND_2_ARE_WORDS = 1;
    public static final short ARGS_ARE_XY_VALUES = 2;
    public static final short ROUND_XY_TO_GRID = 4;
    public static final short WE_HAVE_A_SCALE = 8;
    public static final short MORE_COMPONENTS = 32;
    public static final short WE_HAVE_AN_X_AND_Y_SCALE = 64;
    public static final short WE_HAVE_A_TWO_BY_TWO = 128;
    public static final short WE_HAVE_INSTRUCTIONS = 256;
    public static final short USE_MY_METRICS = 512;
    private final int _firstIndex;
    private final int _firstContour;
    private short _argument1;
    private short _argument2;
    private final int _flags;
    private final int _glyphIndex;
    private double _xscale;
    private double _yscale;
    private double _scale01;
    private double _scale10;
    private int _xtranslate;
    private int _ytranslate;
    private int _point1;
    private int _point2;
    
    protected GlyfCompositeComp(final int firstIndex, final int firstContour, final DataInput dataInput) throws IOException {
        this._xscale = 1.0;
        this._yscale = 1.0;
        this._scale01 = 0.0;
        this._scale10 = 0.0;
        this._xtranslate = 0;
        this._ytranslate = 0;
        this._point1 = 0;
        this._point2 = 0;
        this._firstIndex = firstIndex;
        this._firstContour = firstContour;
        this._flags = dataInput.readUnsignedShort();
        this._glyphIndex = dataInput.readUnsignedShort();
        if ((this._flags & 0x1) != 0x0) {
            this._argument1 = dataInput.readShort();
            this._argument2 = dataInput.readShort();
        }
        else {
            this._argument1 = dataInput.readByte();
            this._argument2 = dataInput.readByte();
        }
        if ((this._flags & 0x2) != 0x0) {
            this._xtranslate = this._argument1;
            this._ytranslate = this._argument2;
        }
        else {
            this._point1 = this._argument1;
            this._point2 = this._argument2;
        }
        if ((this._flags & 0x8) != 0x0) {
            final double n = dataInput.readShort() / 16384.0;
            this._yscale = n;
            this._xscale = n;
        }
        else if ((this._flags & 0x40) != 0x0) {
            this._xscale = dataInput.readShort() / 16384.0;
            this._yscale = dataInput.readShort() / 16384.0;
        }
        else if ((this._flags & 0x80) != 0x0) {
            this._xscale = dataInput.readShort() / 16384.0;
            this._scale01 = dataInput.readShort() / 16384.0;
            this._scale10 = dataInput.readShort() / 16384.0;
            this._yscale = dataInput.readShort() / 16384.0;
        }
    }
    
    public int getFirstIndex() {
        return this._firstIndex;
    }
    
    public int getFirstContour() {
        return this._firstContour;
    }
    
    public short getArgument1() {
        return this._argument1;
    }
    
    public short getArgument2() {
        return this._argument2;
    }
    
    public int getFlags() {
        return this._flags;
    }
    
    public int getGlyphIndex() {
        return this._glyphIndex;
    }
    
    public double getScale01() {
        return this._scale01;
    }
    
    public double getScale10() {
        return this._scale10;
    }
    
    public double getXScale() {
        return this._xscale;
    }
    
    public double getYScale() {
        return this._yscale;
    }
    
    public int getXTranslate() {
        return this._xtranslate;
    }
    
    public int getYTranslate() {
        return this._ytranslate;
    }
    
    public int scaleX(final int n, final int n2) {
        return (int)(n * this._xscale + n2 * this._scale10);
    }
    
    public int scaleY(final int n, final int n2) {
        return (int)(n * this._scale01 + n2 * this._yscale);
    }
}
