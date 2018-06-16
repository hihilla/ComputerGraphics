// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;

public class SingleSubstFormat2 extends SingleSubst
{
    private final int _coverageOffset;
    private final int _glyphCount;
    private final int[] _substitutes;
    private final Coverage _coverage;
    
    protected SingleSubstFormat2(final DataInputStream dataInputStream, final int n) throws IOException {
        this._coverageOffset = dataInputStream.readUnsignedShort();
        this._glyphCount = dataInputStream.readUnsignedShort();
        this._substitutes = new int[this._glyphCount];
        for (int i = 0; i < this._glyphCount; ++i) {
            this._substitutes[i] = dataInputStream.readUnsignedShort();
        }
        dataInputStream.reset();
        dataInputStream.skipBytes(n + this._coverageOffset);
        this._coverage = Coverage.read(dataInputStream);
    }
    
    @Override
    public int getFormat() {
        return 2;
    }
    
    @Override
    public int substitute(final int n) {
        final int glyph = this._coverage.findGlyph(n);
        if (glyph > -1) {
            return this._substitutes[glyph];
        }
        return n;
    }
    
    @Override
    public String getTypeAsString() {
        return "SingleSubstFormat2";
    }
}
