// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public abstract class KernSubtable
{
    public abstract int getKerningPairCount();
    
    public abstract KerningPair getKerningPair(final int p0);
    
    public static KernSubtable read(final DataInput dataInput) throws IOException {
        KernSubtable kernSubtable = null;
        dataInput.readUnsignedShort();
        dataInput.readUnsignedShort();
        switch (dataInput.readUnsignedShort() >> 8) {
            case 0: {
                kernSubtable = new KernSubtableFormat0(dataInput);
                break;
            }
            case 2: {
                kernSubtable = new KernSubtableFormat2(dataInput);
                break;
            }
        }
        return kernSubtable;
    }
}
