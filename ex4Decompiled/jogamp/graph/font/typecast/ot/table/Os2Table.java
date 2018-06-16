// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class Os2Table implements Table
{
    private final DirectoryEntry _de;
    private final int _version;
    private final short _xAvgCharWidth;
    private final int _usWeightClass;
    private final int _usWidthClass;
    private final short _fsType;
    private final short _ySubscriptXSize;
    private final short _ySubscriptYSize;
    private final short _ySubscriptXOffset;
    private final short _ySubscriptYOffset;
    private final short _ySuperscriptXSize;
    private final short _ySuperscriptYSize;
    private final short _ySuperscriptXOffset;
    private final short _ySuperscriptYOffset;
    private final short _yStrikeoutSize;
    private final short _yStrikeoutPosition;
    private final short _sFamilyClass;
    private final Panose _panose;
    private final int _ulUnicodeRange1;
    private final int _ulUnicodeRange2;
    private final int _ulUnicodeRange3;
    private final int _ulUnicodeRange4;
    private final int _achVendorID;
    private final short _fsSelection;
    private final int _usFirstCharIndex;
    private final int _usLastCharIndex;
    private final short _sTypoAscender;
    private final short _sTypoDescender;
    private final short _sTypoLineGap;
    private final int _usWinAscent;
    private final int _usWinDescent;
    private final int _ulCodePageRange1;
    private final int _ulCodePageRange2;
    private short _sxHeight;
    private short _sCapHeight;
    private int _usDefaultChar;
    private int _usBreakChar;
    private int _usMaxContext;
    
    protected Os2Table(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._version = dataInput.readUnsignedShort();
        this._xAvgCharWidth = dataInput.readShort();
        this._usWeightClass = dataInput.readUnsignedShort();
        this._usWidthClass = dataInput.readUnsignedShort();
        this._fsType = dataInput.readShort();
        this._ySubscriptXSize = dataInput.readShort();
        this._ySubscriptYSize = dataInput.readShort();
        this._ySubscriptXOffset = dataInput.readShort();
        this._ySubscriptYOffset = dataInput.readShort();
        this._ySuperscriptXSize = dataInput.readShort();
        this._ySuperscriptYSize = dataInput.readShort();
        this._ySuperscriptXOffset = dataInput.readShort();
        this._ySuperscriptYOffset = dataInput.readShort();
        this._yStrikeoutSize = dataInput.readShort();
        this._yStrikeoutPosition = dataInput.readShort();
        this._sFamilyClass = dataInput.readShort();
        final byte[] array = new byte[10];
        dataInput.readFully(array);
        this._panose = new Panose(array);
        this._ulUnicodeRange1 = dataInput.readInt();
        this._ulUnicodeRange2 = dataInput.readInt();
        this._ulUnicodeRange3 = dataInput.readInt();
        this._ulUnicodeRange4 = dataInput.readInt();
        this._achVendorID = dataInput.readInt();
        this._fsSelection = dataInput.readShort();
        this._usFirstCharIndex = dataInput.readUnsignedShort();
        this._usLastCharIndex = dataInput.readUnsignedShort();
        this._sTypoAscender = dataInput.readShort();
        this._sTypoDescender = dataInput.readShort();
        this._sTypoLineGap = dataInput.readShort();
        this._usWinAscent = dataInput.readUnsignedShort();
        this._usWinDescent = dataInput.readUnsignedShort();
        this._ulCodePageRange1 = dataInput.readInt();
        this._ulCodePageRange2 = dataInput.readInt();
        if (this._version == 2) {
            this._sxHeight = dataInput.readShort();
            this._sCapHeight = dataInput.readShort();
            this._usDefaultChar = dataInput.readUnsignedShort();
            this._usBreakChar = dataInput.readUnsignedShort();
            this._usMaxContext = dataInput.readUnsignedShort();
        }
    }
    
    public int getVersion() {
        return this._version;
    }
    
    public short getAvgCharWidth() {
        return this._xAvgCharWidth;
    }
    
    public int getWeightClass() {
        return this._usWeightClass;
    }
    
    public int getWidthClass() {
        return this._usWidthClass;
    }
    
    public short getLicenseType() {
        return this._fsType;
    }
    
    public short getSubscriptXSize() {
        return this._ySubscriptXSize;
    }
    
    public short getSubscriptYSize() {
        return this._ySubscriptYSize;
    }
    
    public short getSubscriptXOffset() {
        return this._ySubscriptXOffset;
    }
    
    public short getSubscriptYOffset() {
        return this._ySubscriptYOffset;
    }
    
    public short getSuperscriptXSize() {
        return this._ySuperscriptXSize;
    }
    
    public short getSuperscriptYSize() {
        return this._ySuperscriptYSize;
    }
    
    public short getSuperscriptXOffset() {
        return this._ySuperscriptXOffset;
    }
    
    public short getSuperscriptYOffset() {
        return this._ySuperscriptYOffset;
    }
    
    public short getStrikeoutSize() {
        return this._yStrikeoutSize;
    }
    
    public short getStrikeoutPosition() {
        return this._yStrikeoutPosition;
    }
    
    public short getFamilyClass() {
        return this._sFamilyClass;
    }
    
    public Panose getPanose() {
        return this._panose;
    }
    
    public int getUnicodeRange1() {
        return this._ulUnicodeRange1;
    }
    
    public int getUnicodeRange2() {
        return this._ulUnicodeRange2;
    }
    
    public int getUnicodeRange3() {
        return this._ulUnicodeRange3;
    }
    
    public int getUnicodeRange4() {
        return this._ulUnicodeRange4;
    }
    
    public int getVendorID() {
        return this._achVendorID;
    }
    
    public short getSelection() {
        return this._fsSelection;
    }
    
    public int getFirstCharIndex() {
        return this._usFirstCharIndex;
    }
    
    public int getLastCharIndex() {
        return this._usLastCharIndex;
    }
    
    public short getTypoAscender() {
        return this._sTypoAscender;
    }
    
    public short getTypoDescender() {
        return this._sTypoDescender;
    }
    
    public short getTypoLineGap() {
        return this._sTypoLineGap;
    }
    
    public int getWinAscent() {
        return this._usWinAscent;
    }
    
    public int getWinDescent() {
        return this._usWinDescent;
    }
    
    public int getCodePageRange1() {
        return this._ulCodePageRange1;
    }
    
    public int getCodePageRange2() {
        return this._ulCodePageRange2;
    }
    
    public short getXHeight() {
        return this._sxHeight;
    }
    
    public short getCapHeight() {
        return this._sCapHeight;
    }
    
    public int getDefaultChar() {
        return this._usDefaultChar;
    }
    
    public int getBreakChar() {
        return this._usBreakChar;
    }
    
    public int getMaxContext() {
        return this._usMaxContext;
    }
    
    @Override
    public int getType() {
        return 1330851634;
    }
    
    @Override
    public String toString() {
        return "'OS/2' Table - OS/2 and Windows Metrics\n---------------------------------------" + "\n  'OS/2' version:      " + this._version + "\n  xAvgCharWidth:       " + this._xAvgCharWidth + "\n  usWeightClass:       " + this._usWeightClass + "\n  usWidthClass:        " + this._usWidthClass + "\n  fsType:              0x" + Integer.toHexString(this._fsType).toUpperCase() + "\n  ySubscriptXSize:     " + this._ySubscriptXSize + "\n  ySubscriptYSize:     " + this._ySubscriptYSize + "\n  ySubscriptXOffset:   " + this._ySubscriptXOffset + "\n  ySubscriptYOffset:   " + this._ySubscriptYOffset + "\n  ySuperscriptXSize:   " + this._ySuperscriptXSize + "\n  ySuperscriptYSize:   " + this._ySuperscriptYSize + "\n  ySuperscriptXOffset: " + this._ySuperscriptXOffset + "\n  ySuperscriptYOffset: " + this._ySuperscriptYOffset + "\n  yStrikeoutSize:      " + this._yStrikeoutSize + "\n  yStrikeoutPosition:  " + this._yStrikeoutPosition + "\n  sFamilyClass:        " + (this._sFamilyClass >> 8) + "    subclass = " + (this._sFamilyClass & 0xFF) + "\n  PANOSE:              " + this._panose.toString() + "\n  Unicode Range 1( Bits 0 - 31 ): " + Integer.toHexString(this._ulUnicodeRange1).toUpperCase() + "\n  Unicode Range 2( Bits 32- 63 ): " + Integer.toHexString(this._ulUnicodeRange2).toUpperCase() + "\n  Unicode Range 3( Bits 64- 95 ): " + Integer.toHexString(this._ulUnicodeRange3).toUpperCase() + "\n  Unicode Range 4( Bits 96-127 ): " + Integer.toHexString(this._ulUnicodeRange4).toUpperCase() + "\n  achVendID:           '" + this.getVendorIDAsString() + "'\n  fsSelection:         0x" + Integer.toHexString(this._fsSelection).toUpperCase() + "\n  usFirstCharIndex:    0x" + Integer.toHexString(this._usFirstCharIndex).toUpperCase() + "\n  usLastCharIndex:     0x" + Integer.toHexString(this._usLastCharIndex).toUpperCase() + "\n  sTypoAscender:       " + this._sTypoAscender + "\n  sTypoDescender:      " + this._sTypoDescender + "\n  sTypoLineGap:        " + this._sTypoLineGap + "\n  usWinAscent:         " + this._usWinAscent + "\n  usWinDescent:        " + this._usWinDescent + "\n  CodePage Range 1( Bits 0 - 31 ): " + Integer.toHexString(this._ulCodePageRange1).toUpperCase() + "\n  CodePage Range 2( Bits 32- 63 ): " + Integer.toHexString(this._ulCodePageRange2).toUpperCase();
    }
    
    private String getVendorIDAsString() {
        return new StringBuilder().append((char)(this._achVendorID >> 24 & 0xFF)).append((char)(this._achVendorID >> 16 & 0xFF)).append((char)(this._achVendorID >> 8 & 0xFF)).append((char)(this._achVendorID & 0xFF)).toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
