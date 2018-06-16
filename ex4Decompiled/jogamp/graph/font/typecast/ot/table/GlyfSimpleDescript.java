// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Disassembler;
import java.io.IOException;
import java.io.DataInput;

public class GlyfSimpleDescript extends GlyfDescript
{
    private final int[] _endPtsOfContours;
    private final byte[] _flags;
    private final short[] _xCoordinates;
    private final short[] _yCoordinates;
    private final int _count;
    
    public GlyfSimpleDescript(final GlyfTable glyfTable, final int n, final short n2, final DataInput dataInput) throws IOException {
        super(glyfTable, n, n2, dataInput);
        this._endPtsOfContours = new int[n2];
        for (short n3 = 0; n3 < n2; ++n3) {
            this._endPtsOfContours[n3] = dataInput.readShort();
        }
        this._count = this._endPtsOfContours[n2 - 1] + 1;
        this._flags = new byte[this._count];
        this._xCoordinates = new short[this._count];
        this._yCoordinates = new short[this._count];
        this.readInstructions(dataInput, dataInput.readShort());
        this.readFlags(this._count, dataInput);
        this.readCoords(this._count, dataInput);
    }
    
    @Override
    public int getEndPtOfContours(final int n) {
        return this._endPtsOfContours[n];
    }
    
    @Override
    public byte getFlags(final int n) {
        return this._flags[n];
    }
    
    @Override
    public short getXCoordinate(final int n) {
        return this._xCoordinates[n];
    }
    
    @Override
    public short getYCoordinate(final int n) {
        return this._yCoordinates[n];
    }
    
    @Override
    public boolean isComposite() {
        return false;
    }
    
    @Override
    public int getPointCount() {
        return this._count;
    }
    
    @Override
    public int getContourCount() {
        return this.getNumberOfContours();
    }
    
    private void readCoords(final int n, final DataInput dataInput) throws IOException {
        short n2 = 0;
        short n3 = 0;
        for (int i = 0; i < n; ++i) {
            if ((this._flags[i] & 0x10) != 0x0) {
                if ((this._flags[i] & 0x2) != 0x0) {
                    n2 += (short)dataInput.readUnsignedByte();
                }
            }
            else if ((this._flags[i] & 0x2) != 0x0) {
                n2 += (short)(-(short)dataInput.readUnsignedByte());
            }
            else {
                n2 += dataInput.readShort();
            }
            this._xCoordinates[i] = n2;
        }
        for (int j = 0; j < n; ++j) {
            if ((this._flags[j] & 0x20) != 0x0) {
                if ((this._flags[j] & 0x4) != 0x0) {
                    n3 += (short)dataInput.readUnsignedByte();
                }
            }
            else if ((this._flags[j] & 0x4) != 0x0) {
                n3 += (short)(-(short)dataInput.readUnsignedByte());
            }
            else {
                n3 += dataInput.readShort();
            }
            this._yCoordinates[j] = n3;
        }
    }
    
    private void readFlags(final int n, final DataInput dataInput) throws IOException {
        try {
            for (int i = 0; i < n; ++i) {
                this._flags[i] = dataInput.readByte();
                if ((this._flags[i] & 0x8) != 0x0) {
                    final byte byte1 = dataInput.readByte();
                    for (byte b = 1; b <= byte1; ++b) {
                        this._flags[i + b] = this._flags[i];
                    }
                    i += byte1;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("error: array index out of bounds");
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n\n        EndPoints\n        ---------");
        for (int i = 0; i < this._endPtsOfContours.length; ++i) {
            sb.append("\n          ").append(i).append(": ").append(this._endPtsOfContours[i]);
        }
        sb.append("\n\n          Length of Instructions: ");
        sb.append(this.getInstructions().length).append("\n");
        sb.append(Disassembler.disassemble(this.getInstructions(), 8));
        sb.append("\n        Flags\n        -----");
        for (int j = 0; j < this._flags.length; ++j) {
            sb.append("\n          ").append(j).append(":  ");
            if ((this._flags[j] & 0x20) != 0x0) {
                sb.append("YDual ");
            }
            else {
                sb.append("      ");
            }
            if ((this._flags[j] & 0x10) != 0x0) {
                sb.append("XDual ");
            }
            else {
                sb.append("      ");
            }
            if ((this._flags[j] & 0x8) != 0x0) {
                sb.append("Repeat ");
            }
            else {
                sb.append("       ");
            }
            if ((this._flags[j] & 0x4) != 0x0) {
                sb.append("Y-Short ");
            }
            else {
                sb.append("        ");
            }
            if ((this._flags[j] & 0x2) != 0x0) {
                sb.append("X-Short ");
            }
            else {
                sb.append("        ");
            }
            if ((this._flags[j] & 0x1) != 0x0) {
                sb.append("On");
            }
            else {
                sb.append("  ");
            }
        }
        sb.append("\n\n        Coordinates\n        -----------");
        short n = 0;
        short n2 = 0;
        for (int k = 0; k < this._xCoordinates.length; ++k) {
            sb.append("\n          ").append(k).append(": Rel (").append(this._xCoordinates[k] - n).append(", ").append(this._yCoordinates[k] - n2).append(")  ->  Abs (").append(this._xCoordinates[k]).append(", ").append(this._yCoordinates[k]).append(")");
            n = this._xCoordinates[k];
            n2 = this._yCoordinates[k];
        }
        return sb.toString();
    }
}
