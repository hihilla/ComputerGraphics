// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class KernSubtableFormat2 extends KernSubtable
{
    private final int rowWidth;
    private final int leftClassTable;
    private final int rightClassTable;
    private final int array;
    
    protected KernSubtableFormat2(final DataInput dataInput) throws IOException {
        this.rowWidth = dataInput.readUnsignedShort();
        this.leftClassTable = dataInput.readUnsignedShort();
        this.rightClassTable = dataInput.readUnsignedShort();
        this.array = dataInput.readUnsignedShort();
    }
    
    @Override
    public int getKerningPairCount() {
        return 0;
    }
    
    @Override
    public KerningPair getKerningPair(final int n) {
        return null;
    }
}
