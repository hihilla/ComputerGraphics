// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class CvtTable implements Table
{
    private final DirectoryEntry de;
    private final short[] values;
    
    protected CvtTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        final int n = directoryEntry.getLength() / 2;
        this.values = new short[n];
        for (int i = 0; i < n; ++i) {
            this.values[i] = dataInput.readShort();
        }
    }
    
    @Override
    public int getType() {
        return 1668707360;
    }
    
    public short[] getValues() {
        return this.values;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'cvt ' Table - Control Value Table\n----------------------------------\n");
        sb.append("Size = ").append(0).append(" bytes, ").append(this.values.length).append(" entries\n");
        sb.append("        Values\n        ------\n");
        for (int i = 0; i < this.values.length; ++i) {
            sb.append("        ").append(i).append(": ").append(this.values[i]).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
}
