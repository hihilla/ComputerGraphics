// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class GaspTable implements Table
{
    private final DirectoryEntry de;
    private final int version;
    private final int numRanges;
    private final GaspRange[] gaspRange;
    
    protected GaspTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.version = dataInput.readUnsignedShort();
        this.numRanges = dataInput.readUnsignedShort();
        this.gaspRange = new GaspRange[this.numRanges];
        for (int i = 0; i < this.numRanges; ++i) {
            this.gaspRange[i] = new GaspRange(dataInput);
        }
    }
    
    @Override
    public int getType() {
        return 1734439792;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'gasp' Table - Grid-fitting And Scan-conversion Procedure\n---------------------------------------------------------");
        sb.append("\n  'gasp' version:      ").append(this.version);
        sb.append("\n  numRanges:           ").append(this.numRanges);
        for (int i = 0; i < this.numRanges; ++i) {
            sb.append("\n\n  gasp Range ").append(i).append("\n");
            sb.append(this.gaspRange[i].toString());
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
}
