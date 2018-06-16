// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.util.Arrays;
import java.io.DataInput;

public class CmapTable implements Table
{
    private final DirectoryEntry _de;
    private final int _version;
    private final int _numTables;
    private final CmapIndexEntry[] _entries;
    
    protected CmapTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._version = dataInput.readUnsignedShort();
        this._numTables = dataInput.readUnsignedShort();
        long n = 4L;
        this._entries = new CmapIndexEntry[this._numTables];
        for (int i = 0; i < this._numTables; ++i) {
            this._entries[i] = new CmapIndexEntry(dataInput);
            n += 8L;
        }
        Arrays.sort(this._entries);
        int offset = 0;
        CmapFormat create = null;
        for (int j = 0; j < this._numTables; ++j) {
            if (this._entries[j].getOffset() == offset) {
                this._entries[j].setFormat(create);
            }
            else {
                if (this._entries[j].getOffset() > n) {
                    dataInput.skipBytes(this._entries[j].getOffset() - (int)n);
                }
                else if (this._entries[j].getOffset() != n) {
                    throw new IOException();
                }
                create = CmapFormat.create(dataInput.readUnsignedShort(), dataInput);
                offset = this._entries[j].getOffset();
                this._entries[j].setFormat(create);
                n += create.getLength();
            }
        }
    }
    
    public int getVersion() {
        return this._version;
    }
    
    public int getNumTables() {
        return this._numTables;
    }
    
    public CmapIndexEntry getCmapIndexEntry(final int n) {
        return this._entries[n];
    }
    
    public CmapFormat getCmapFormat(final short n, final short n2) {
        for (int i = 0; i < this._numTables; ++i) {
            if (this._entries[i].getPlatformId() == n && this._entries[i].getEncodingId() == n2) {
                return this._entries[i].getFormat();
            }
        }
        return null;
    }
    
    @Override
    public int getType() {
        return 1668112752;
    }
    
    @Override
    public String toString() {
        final StringBuilder append = new StringBuilder().append("cmap\n");
        for (int i = 0; i < this._numTables; ++i) {
            append.append("\t").append(this._entries[i].toString()).append("\n");
        }
        return append.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
