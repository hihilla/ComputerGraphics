// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class KernTable implements Table
{
    private final DirectoryEntry de;
    private final int version;
    private final int nTables;
    private final KernSubtable[] tables;
    
    protected KernTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.version = dataInput.readUnsignedShort();
        this.nTables = dataInput.readUnsignedShort();
        this.tables = new KernSubtable[this.nTables];
        for (int i = 0; i < this.nTables; ++i) {
            this.tables[i] = KernSubtable.read(dataInput);
        }
    }
    
    public int getSubtableCount() {
        return this.nTables;
    }
    
    public KernSubtable getSubtable(final int n) {
        return this.tables[n];
    }
    
    @Override
    public int getType() {
        return 1801810542;
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
}
