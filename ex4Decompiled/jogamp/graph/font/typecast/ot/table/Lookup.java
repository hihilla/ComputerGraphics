// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInputStream;

public class Lookup
{
    public static final int IGNORE_BASE_GLYPHS = 2;
    public static final int IGNORE_BASE_LIGATURES = 4;
    public static final int IGNORE_BASE_MARKS = 8;
    public static final int MARK_ATTACHMENT_TYPE = 65280;
    private final int _type;
    private final int _flag;
    private final int _subTableCount;
    private final int[] _subTableOffsets;
    private final LookupSubtable[] _subTables;
    
    public Lookup(final LookupSubtableFactory lookupSubtableFactory, final DataInputStream dataInputStream, final int n) throws IOException {
        dataInputStream.reset();
        dataInputStream.skipBytes(n);
        this._type = dataInputStream.readUnsignedShort();
        this._flag = dataInputStream.readUnsignedShort();
        this._subTableCount = dataInputStream.readUnsignedShort();
        this._subTableOffsets = new int[this._subTableCount];
        this._subTables = new LookupSubtable[this._subTableCount];
        for (int i = 0; i < this._subTableCount; ++i) {
            this._subTableOffsets[i] = dataInputStream.readUnsignedShort();
        }
        for (int j = 0; j < this._subTableCount; ++j) {
            this._subTables[j] = lookupSubtableFactory.read(this._type, dataInputStream, n + this._subTableOffsets[j]);
        }
    }
    
    public int getType() {
        return this._type;
    }
    
    public int getSubtableCount() {
        return this._subTableCount;
    }
    
    public LookupSubtable getSubtable(final int n) {
        return this._subTables[n];
    }
}
