// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public abstract class Program
{
    private short[] instructions;
    
    public short[] getInstructions() {
        return this.instructions;
    }
    
    protected void readInstructions(final DataInput dataInput, final int n) throws IOException {
        this.instructions = new short[n];
        for (int i = 0; i < n; ++i) {
            this.instructions[i] = (short)dataInput.readUnsignedByte();
        }
    }
}
