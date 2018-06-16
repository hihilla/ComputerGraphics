// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class KerningPair
{
    private final int left;
    private final int right;
    private final short value;
    
    protected KerningPair(final DataInput dataInput) throws IOException {
        this.left = dataInput.readUnsignedShort();
        this.right = dataInput.readUnsignedShort();
        this.value = dataInput.readShort();
    }
    
    public int getLeft() {
        return this.left;
    }
    
    public int getRight() {
        return this.right;
    }
    
    public short getValue() {
        return this.value;
    }
}
