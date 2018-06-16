// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Disassembler;
import java.io.IOException;
import java.io.DataInput;

public class FpgmTable extends Program implements Table
{
    private final DirectoryEntry de;
    
    protected FpgmTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.readInstructions(dataInput, directoryEntry.getLength());
    }
    
    @Override
    public int getType() {
        return 1718642541;
    }
    
    @Override
    public String toString() {
        return Disassembler.disassemble(this.getInstructions(), 0);
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
}
