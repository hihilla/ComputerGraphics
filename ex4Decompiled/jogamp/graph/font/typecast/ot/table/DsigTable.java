// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class DsigTable implements Table
{
    private final DirectoryEntry de;
    private final int version;
    private final int numSigs;
    private final int flag;
    private final DsigEntry[] dsigEntry;
    private final SignatureBlock[] sigBlocks;
    
    protected DsigTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.version = dataInput.readInt();
        this.numSigs = dataInput.readUnsignedShort();
        this.flag = dataInput.readUnsignedShort();
        this.dsigEntry = new DsigEntry[this.numSigs];
        this.sigBlocks = new SignatureBlock[this.numSigs];
        for (int i = 0; i < this.numSigs; ++i) {
            this.dsigEntry[i] = new DsigEntry(dataInput);
        }
        for (int j = 0; j < this.numSigs; ++j) {
            this.sigBlocks[j] = new SignatureBlock(dataInput);
        }
    }
    
    @Override
    public int getType() {
        return 1146308935;
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
    
    @Override
    public String toString() {
        final StringBuilder append = new StringBuilder().append("DSIG\n");
        for (int i = 0; i < this.numSigs; ++i) {
            append.append(this.sigBlocks[i].toString());
        }
        return append.toString();
    }
}
