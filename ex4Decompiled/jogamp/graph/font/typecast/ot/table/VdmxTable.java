// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class VdmxTable implements Table
{
    private final DirectoryEntry _de;
    private final int _version;
    private final int _numRecs;
    private final int _numRatios;
    private final Ratio[] _ratRange;
    private final int[] _offset;
    private final Group[] _groups;
    
    protected VdmxTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._version = dataInput.readUnsignedShort();
        this._numRecs = dataInput.readUnsignedShort();
        this._numRatios = dataInput.readUnsignedShort();
        this._ratRange = new Ratio[this._numRatios];
        for (int i = 0; i < this._numRatios; ++i) {
            this._ratRange[i] = new Ratio(dataInput);
        }
        this._offset = new int[this._numRatios];
        for (int j = 0; j < this._numRatios; ++j) {
            this._offset[j] = dataInput.readUnsignedShort();
        }
        this._groups = new Group[this._numRecs];
        for (int k = 0; k < this._numRecs; ++k) {
            this._groups[k] = new Group(dataInput);
        }
    }
    
    @Override
    public int getType() {
        return 1447316824;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'VDMX' Table - Precomputed Vertical Device Metrics\n").append("--------------------------------------------------\n").append("  Version:                 ").append(this._version).append("\n").append("  Number of Hgt Records:   ").append(this._numRecs).append("\n").append("  Number of Ratio Records: ").append(this._numRatios).append("\n");
        for (int i = 0; i < this._numRatios; ++i) {
            sb.append("\n    Ratio Record #").append(i + 1).append("\n").append("\tCharSetId     ").append(this._ratRange[i].getBCharSet()).append("\n").append("\txRatio        ").append(this._ratRange[i].getXRatio()).append("\n").append("\tyStartRatio   ").append(this._ratRange[i].getYStartRatio()).append("\n").append("\tyEndRatio     ").append(this._ratRange[i].getYEndRatio()).append("\n").append("\tRecord Offset ").append(this._offset[i]).append("\n");
        }
        sb.append("\n   VDMX Height Record Groups\n").append("   -------------------------\n");
        for (int j = 0; j < this._numRecs; ++j) {
            final Group group = this._groups[j];
            sb.append("   ").append(j + 1).append(".   Number of Hgt Records  ").append(group.getRecs()).append("\n").append("        Starting Y Pel Height  ").append(group.getStartSZ()).append("\n").append("        Ending Y Pel Height    ").append(group.getEndSZ()).append("\n");
            for (int k = 0; k < group.getRecs(); ++k) {
                sb.append("\n            ").append(k + 1).append(". Pel Height= ").append(group.getEntry()[k].getYPelHeight()).append("\n").append("               yMax=       ").append(group.getEntry()[k].getYMax()).append("\n").append("               yMin=       ").append(group.getEntry()[k].getYMin()).append("\n");
            }
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
    
    static class Ratio
    {
        private final byte _bCharSet;
        private final byte _xRatio;
        private final byte _yStartRatio;
        private final byte _yEndRatio;
        
        protected Ratio(final DataInput dataInput) throws IOException {
            this._bCharSet = dataInput.readByte();
            this._xRatio = dataInput.readByte();
            this._yStartRatio = dataInput.readByte();
            this._yEndRatio = dataInput.readByte();
        }
        
        public byte getBCharSet() {
            return this._bCharSet;
        }
        
        public byte getXRatio() {
            return this._xRatio;
        }
        
        public byte getYStartRatio() {
            return this._yStartRatio;
        }
        
        public byte getYEndRatio() {
            return this._yEndRatio;
        }
    }
    
    static class VTableRecord
    {
        private final int _yPelHeight;
        private final short _yMax;
        private final short _yMin;
        
        protected VTableRecord(final DataInput dataInput) throws IOException {
            this._yPelHeight = dataInput.readUnsignedShort();
            this._yMax = dataInput.readShort();
            this._yMin = dataInput.readShort();
        }
        
        public int getYPelHeight() {
            return this._yPelHeight;
        }
        
        public short getYMax() {
            return this._yMax;
        }
        
        public short getYMin() {
            return this._yMin;
        }
    }
    
    static class Group
    {
        private final int _recs;
        private final int _startsz;
        private final int _endsz;
        private final VTableRecord[] _entry;
        
        protected Group(final DataInput dataInput) throws IOException {
            this._recs = dataInput.readUnsignedShort();
            this._startsz = dataInput.readUnsignedByte();
            this._endsz = dataInput.readUnsignedByte();
            this._entry = new VTableRecord[this._recs];
            for (int i = 0; i < this._recs; ++i) {
                this._entry[i] = new VTableRecord(dataInput);
            }
        }
        
        public int getRecs() {
            return this._recs;
        }
        
        public int getStartSZ() {
            return this._startsz;
        }
        
        public int getEndSZ() {
            return this._endsz;
        }
        
        public VTableRecord[] getEntry() {
            return this._entry;
        }
    }
}
