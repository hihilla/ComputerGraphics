// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class PcltTable implements Table
{
    private final DirectoryEntry de;
    private final int version;
    private final long fontNumber;
    private final int pitch;
    private final int xHeight;
    private final int style;
    private final int typeFamily;
    private final int capHeight;
    private final int symbolSet;
    private final char[] typeface;
    private final short[] characterComplement;
    private final char[] fileName;
    private final short strokeWeight;
    private final short widthType;
    private final byte serifStyle;
    private final byte reserved;
    
    protected PcltTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.typeface = new char[16];
        this.characterComplement = new short[8];
        this.fileName = new char[6];
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.version = dataInput.readInt();
        this.fontNumber = dataInput.readInt();
        this.pitch = dataInput.readUnsignedShort();
        this.xHeight = dataInput.readUnsignedShort();
        this.style = dataInput.readUnsignedShort();
        this.typeFamily = dataInput.readUnsignedShort();
        this.capHeight = dataInput.readUnsignedShort();
        this.symbolSet = dataInput.readUnsignedShort();
        for (int i = 0; i < 16; ++i) {
            this.typeface[i] = (char)dataInput.readUnsignedByte();
        }
        for (int j = 0; j < 8; ++j) {
            this.characterComplement[j] = (short)dataInput.readUnsignedByte();
        }
        for (int k = 0; k < 6; ++k) {
            this.fileName[k] = (char)dataInput.readUnsignedByte();
        }
        this.strokeWeight = (short)dataInput.readUnsignedByte();
        this.widthType = (short)dataInput.readUnsignedByte();
        this.serifStyle = dataInput.readByte();
        this.reserved = dataInput.readByte();
    }
    
    @Override
    public int getType() {
        return 1346587732;
    }
    
    @Override
    public String toString() {
        return "'PCLT' Table - Printer Command Language Table\n---------------------------------------------" + "\n        version:             0x" + Integer.toHexString(this.version).toUpperCase() + "\n        fontNumber:          " + this.fontNumber + " (0x" + Long.toHexString(this.fontNumber).toUpperCase() + ")\n        pitch:               " + this.pitch + "\n        xHeight:             " + this.xHeight + "\n        style:               0x" + this.style + "\n        typeFamily:          0x" + (this.typeFamily >> 12) + " " + (this.typeFamily & 0xFFF) + "\n        capHeight:           " + this.capHeight + "\n        symbolSet:           " + this.symbolSet + "\n        typeFace:            " + new String(this.typeface) + "\n        characterComplement  0x" + Integer.toHexString(this.characterComplement[0]).toUpperCase() + "\n        fileName:            " + new String(this.fileName) + "\n        strokeWeight:        " + this.strokeWeight + "\n        widthType:           " + this.widthType + "\n        serifStyle:          " + this.serifStyle + "\n        reserved:            " + this.reserved;
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
}
