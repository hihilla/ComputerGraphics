// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class HmtxTable implements Table
{
    private final DirectoryEntry _de;
    private int[] _hMetrics;
    private short[] _leftSideBearing;
    
    protected HmtxTable(final DirectoryEntry directoryEntry, final DataInput dataInput, final HheaTable hheaTable, final MaxpTable maxpTable) throws IOException {
        this._hMetrics = null;
        this._leftSideBearing = null;
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._hMetrics = new int[hheaTable.getNumberOfHMetrics()];
        for (int i = 0; i < hheaTable.getNumberOfHMetrics(); ++i) {
            this._hMetrics[i] = (dataInput.readUnsignedByte() << 24 | dataInput.readUnsignedByte() << 16 | dataInput.readUnsignedByte() << 8 | dataInput.readUnsignedByte());
        }
        final int n = maxpTable.getNumGlyphs() - hheaTable.getNumberOfHMetrics();
        this._leftSideBearing = new short[n];
        for (int j = 0; j < n; ++j) {
            this._leftSideBearing[j] = dataInput.readShort();
        }
    }
    
    public int getAdvanceWidth(final int n) {
        if (this._hMetrics == null) {
            return 0;
        }
        if (n < this._hMetrics.length) {
            return this._hMetrics[n] >> 16;
        }
        return this._hMetrics[this._hMetrics.length - 1] >> 16;
    }
    
    public short getLeftSideBearing(final int n) {
        if (this._hMetrics == null) {
            return 0;
        }
        if (n < this._hMetrics.length) {
            return (short)(this._hMetrics[n] & 0xFFFF);
        }
        return this._leftSideBearing[n - this._hMetrics.length];
    }
    
    @Override
    public int getType() {
        return 1752003704;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'hmtx' Table - Horizontal Metrics\n---------------------------------\n");
        sb.append("Size = ").append(this._de.getLength()).append(" bytes, ").append(this._hMetrics.length).append(" entries\n");
        for (int i = 0; i < this._hMetrics.length; ++i) {
            sb.append("        ").append(i).append(". advWid: ").append(this.getAdvanceWidth(i)).append(", LSdBear: ").append(this.getLeftSideBearing(i)).append("\n");
        }
        for (int j = 0; j < this._leftSideBearing.length; ++j) {
            sb.append("        LSdBear ").append(j + this._hMetrics.length).append(": ").append(this._leftSideBearing[j]).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
