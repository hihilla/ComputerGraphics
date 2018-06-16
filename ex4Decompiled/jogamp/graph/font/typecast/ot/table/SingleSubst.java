// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInputStream;

public abstract class SingleSubst extends LookupSubtable
{
    public abstract int getFormat();
    
    public abstract int substitute(final int p0);
    
    public static SingleSubst read(final DataInputStream dataInputStream, final int n) throws IOException {
        SingleSubst singleSubst = null;
        dataInputStream.reset();
        dataInputStream.skipBytes(n);
        final int unsignedShort = dataInputStream.readUnsignedShort();
        if (unsignedShort == 1) {
            singleSubst = new SingleSubstFormat1(dataInputStream, n);
        }
        else if (unsignedShort == 2) {
            singleSubst = new SingleSubstFormat2(dataInputStream, n);
        }
        return singleSubst;
    }
}
