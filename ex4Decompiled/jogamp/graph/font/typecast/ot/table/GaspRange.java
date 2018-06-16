// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class GaspRange
{
    public static final int GASP_GRIDFIT = 1;
    public static final int GASP_DOGRAY = 2;
    private final int rangeMaxPPEM;
    private final int rangeGaspBehavior;
    
    protected GaspRange(final DataInput dataInput) throws IOException {
        this.rangeMaxPPEM = dataInput.readUnsignedShort();
        this.rangeGaspBehavior = dataInput.readUnsignedShort();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("  rangeMaxPPEM:        ").append(this.rangeMaxPPEM).append("\n  rangeGaspBehavior:   0x").append(this.rangeGaspBehavior);
        if ((this.rangeGaspBehavior & 0x1) != 0x0) {
            sb.append("- GASP_GRIDFIT ");
        }
        if ((this.rangeGaspBehavior & 0x2) != 0x0) {
            sb.append("- GASP_DOGRAY");
        }
        return sb.toString();
    }
}
