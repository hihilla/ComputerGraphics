// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Disassembler;
import java.io.IOException;
import java.io.DataInput;

public class PrepTable extends Program implements Table
{
    private final DirectoryEntry de;
    
    public PrepTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.readInstructions(dataInput, directoryEntry.getLength());
    }
    
    @Override
    public int getType() {
        return 1886545264;
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
