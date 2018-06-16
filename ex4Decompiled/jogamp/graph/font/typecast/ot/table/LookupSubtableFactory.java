// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInputStream;

public interface LookupSubtableFactory
{
    LookupSubtable read(final int p0, final DataInputStream p1, final int p2) throws IOException;
}
