// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInputStream;

public abstract class LigatureSubst extends LookupSubtable
{
    public static LigatureSubst read(final DataInputStream dataInputStream, final int n) throws IOException {
        dataInputStream.reset();
        dataInputStream.skipBytes(n);
        if (dataInputStream.readUnsignedShort() == 1) {
            return new LigatureSubstFormat1(dataInputStream, n);
        }
        return null;
    }
}
