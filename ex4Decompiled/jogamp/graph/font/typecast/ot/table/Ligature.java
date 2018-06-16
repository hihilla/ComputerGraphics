// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class Ligature
{
    private final int _ligGlyph;
    private final int _compCount;
    private final int[] _components;
    
    public Ligature(final DataInput dataInput) throws IOException {
        this._ligGlyph = dataInput.readUnsignedShort();
        this._compCount = dataInput.readUnsignedShort();
        this._components = new int[this._compCount - 1];
        for (int i = 0; i < this._compCount - 1; ++i) {
            this._components[i] = dataInput.readUnsignedShort();
        }
    }
    
    public int getGlyphCount() {
        return this._compCount;
    }
    
    public int getGlyphId(final int n) {
        return (n == 0) ? this._ligGlyph : this._components[n - 1];
    }
}
