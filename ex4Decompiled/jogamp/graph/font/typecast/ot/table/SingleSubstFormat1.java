// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;

public class SingleSubstFormat1 extends SingleSubst
{
    private final int _coverageOffset;
    private final short _deltaGlyphID;
    private final Coverage _coverage;
    
    protected SingleSubstFormat1(final DataInputStream dataInputStream, final int n) throws IOException {
        this._coverageOffset = dataInputStream.readUnsignedShort();
        this._deltaGlyphID = dataInputStream.readShort();
        dataInputStream.reset();
        dataInputStream.skipBytes(n + this._coverageOffset);
        this._coverage = Coverage.read(dataInputStream);
    }
    
    @Override
    public int getFormat() {
        return 1;
    }
    
    @Override
    public int substitute(final int n) {
        if (this._coverage.findGlyph(n) > -1) {
            return n + this._deltaGlyphID;
        }
        return n;
    }
    
    @Override
    public String getTypeAsString() {
        return "SingleSubstFormat1";
    }
}
