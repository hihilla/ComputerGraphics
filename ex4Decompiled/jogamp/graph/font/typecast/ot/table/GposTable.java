// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class GposTable implements Table
{
    private final DirectoryEntry _de;
    
    protected GposTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        dataInput.readInt();
        dataInput.readInt();
        dataInput.readInt();
        dataInput.readInt();
    }
    
    @Override
    public int getType() {
        return 1196445523;
    }
    
    @Override
    public String toString() {
        return "GPOS";
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
