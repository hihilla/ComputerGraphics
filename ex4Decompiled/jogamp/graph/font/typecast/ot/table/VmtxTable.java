// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class VmtxTable implements Table
{
    private final DirectoryEntry _de;
    private int[] _vMetrics;
    private short[] _topSideBearing;
    
    protected VmtxTable(final DirectoryEntry directoryEntry, final DataInput dataInput, final VheaTable vheaTable, final MaxpTable maxpTable) throws IOException {
        this._vMetrics = null;
        this._topSideBearing = null;
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._vMetrics = new int[vheaTable.getNumberOfLongVerMetrics()];
        for (int i = 0; i < vheaTable.getNumberOfLongVerMetrics(); ++i) {
            this._vMetrics[i] = (dataInput.readUnsignedByte() << 24 | dataInput.readUnsignedByte() << 16 | dataInput.readUnsignedByte() << 8 | dataInput.readUnsignedByte());
        }
        final int n = maxpTable.getNumGlyphs() - vheaTable.getNumberOfLongVerMetrics();
        this._topSideBearing = new short[n];
        for (int j = 0; j < n; ++j) {
            this._topSideBearing[j] = dataInput.readShort();
        }
    }
    
    public int getAdvanceHeight(final int n) {
        if (this._vMetrics == null) {
            return 0;
        }
        if (n < this._vMetrics.length) {
            return this._vMetrics[n] >> 16;
        }
        return this._vMetrics[this._vMetrics.length - 1] >> 16;
    }
    
    public short getTopSideBearing(final int n) {
        if (this._vMetrics == null) {
            return 0;
        }
        if (n < this._vMetrics.length) {
            return (short)(this._vMetrics[n] & 0xFFFF);
        }
        return this._topSideBearing[n - this._vMetrics.length];
    }
    
    @Override
    public int getType() {
        return 1986884728;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'vmtx' Table - Vertical Metrics\n-------------------------------\n");
        sb.append("Size = ").append(this._de.getLength()).append(" bytes, ").append(this._vMetrics.length).append(" entries\n");
        for (int i = 0; i < this._vMetrics.length; ++i) {
            sb.append("        ").append(i).append(". advHeight: ").append(this.getAdvanceHeight(i)).append(", TSdBear: ").append(this.getTopSideBearing(i)).append("\n");
        }
        for (int j = 0; j < this._topSideBearing.length; ++j) {
            sb.append("        TSdBear ").append(j + this._vMetrics.length).append(": ").append(this._topSideBearing[j]).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
