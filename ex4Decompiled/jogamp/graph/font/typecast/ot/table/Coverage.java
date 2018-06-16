// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public abstract class Coverage
{
    public abstract int getFormat();
    
    public abstract int findGlyph(final int p0);
    
    protected static Coverage read(final DataInput dataInput) throws IOException {
        Coverage coverage = null;
        final int unsignedShort = dataInput.readUnsignedShort();
        if (unsignedShort == 1) {
            coverage = new CoverageFormat1(dataInput);
        }
        else if (unsignedShort == 2) {
            coverage = new CoverageFormat2(dataInput);
        }
        return coverage;
    }
}
