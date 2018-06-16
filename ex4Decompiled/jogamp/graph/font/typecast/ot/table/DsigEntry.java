// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class DsigEntry
{
    private final int format;
    private final int length;
    private final int offset;
    
    protected DsigEntry(final DataInput dataInput) throws IOException {
        this.format = dataInput.readInt();
        this.length = dataInput.readInt();
        this.offset = dataInput.readInt();
    }
    
    public int getFormat() {
        return this.format;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public int getOffset() {
        return this.offset;
    }
}
