// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class LocaTable implements Table
{
    private final DirectoryEntry _de;
    private int[] _offsets;
    private short _factor;
    
    protected LocaTable(final DirectoryEntry directoryEntry, final DataInput dataInput, final HeadTable headTable, final MaxpTable maxpTable) throws IOException {
        this._offsets = null;
        this._factor = 0;
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._offsets = new int[maxpTable.getNumGlyphs() + 1];
        if (headTable.getIndexToLocFormat() == 0) {
            this._factor = 2;
            for (int i = 0; i <= maxpTable.getNumGlyphs(); ++i) {
                this._offsets[i] = dataInput.readUnsignedShort();
            }
        }
        else {
            this._factor = 1;
            for (int j = 0; j <= maxpTable.getNumGlyphs(); ++j) {
                this._offsets[j] = dataInput.readInt();
            }
        }
    }
    
    public int getOffset(final int n) {
        if (this._offsets == null) {
            return 0;
        }
        return this._offsets[n] * this._factor;
    }
    
    @Override
    public int getType() {
        return 1819239265;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'loca' Table - Index To Location Table\n--------------------------------------\n").append("Size = ").append(this._de.getLength()).append(" bytes, ").append(this._offsets.length).append(" entries\n");
        for (int i = 0; i < this._offsets.length; ++i) {
            sb.append("        Idx ").append(i).append(" -> glyfOff 0x").append(this.getOffset(i)).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
