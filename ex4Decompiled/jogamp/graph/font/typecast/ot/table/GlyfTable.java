// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;

public class GlyfTable implements Table
{
    private final DirectoryEntry _de;
    private final GlyfDescript[] _descript;
    
    protected GlyfTable(final DirectoryEntry directoryEntry, final DataInput dataInput, final MaxpTable maxpTable, final LocaTable locaTable) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._descript = new GlyfDescript[maxpTable.getNumGlyphs()];
        final byte[] array = new byte[directoryEntry.getLength()];
        dataInput.readFully(array);
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
        for (int i = 0; i < maxpTable.getNumGlyphs(); ++i) {
            if (locaTable.getOffset(i + 1) - locaTable.getOffset(i) > 0) {
                byteArrayInputStream.reset();
                byteArrayInputStream.skip(locaTable.getOffset(i));
                final DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                final short short1 = dataInputStream.readShort();
                if (short1 >= 0) {
                    this._descript[i] = new GlyfSimpleDescript(this, i, short1, dataInputStream);
                }
            }
            else {
                this._descript[i] = null;
            }
        }
        for (int j = 0; j < maxpTable.getNumGlyphs(); ++j) {
            if (locaTable.getOffset(j + 1) - locaTable.getOffset(j) > 0) {
                byteArrayInputStream.reset();
                byteArrayInputStream.skip(locaTable.getOffset(j));
                final DataInputStream dataInputStream2 = new DataInputStream(byteArrayInputStream);
                if (dataInputStream2.readShort() < 0) {
                    this._descript[j] = new GlyfCompositeDescript(this, j, dataInputStream2);
                }
            }
        }
    }
    
    public GlyfDescript getDescription(final int n) {
        if (n < this._descript.length) {
            return this._descript[n];
        }
        return null;
    }
    
    @Override
    public int getType() {
        return 1735162214;
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
