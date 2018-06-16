// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class KernSubtableFormat0 extends KernSubtable
{
    private final int nPairs;
    private final int searchRange;
    private final int entrySelector;
    private final int rangeShift;
    private final KerningPair[] kerningPairs;
    
    protected KernSubtableFormat0(final DataInput dataInput) throws IOException {
        this.nPairs = dataInput.readUnsignedShort();
        this.searchRange = dataInput.readUnsignedShort();
        this.entrySelector = dataInput.readUnsignedShort();
        this.rangeShift = dataInput.readUnsignedShort();
        this.kerningPairs = new KerningPair[this.nPairs];
        for (int i = 0; i < this.nPairs; ++i) {
            this.kerningPairs[i] = new KerningPair(dataInput);
        }
    }
    
    @Override
    public int getKerningPairCount() {
        return this.nPairs;
    }
    
    @Override
    public KerningPair getKerningPair(final int n) {
        return this.kerningPairs[n];
    }
}
