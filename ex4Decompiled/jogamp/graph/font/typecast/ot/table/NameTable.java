// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;

public class NameTable implements Table
{
    private final DirectoryEntry _de;
    private final short _formatSelector;
    private final short _numberOfNameRecords;
    private final short _stringStorageOffset;
    private final NameRecord[] _records;
    
    protected NameTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._formatSelector = dataInput.readShort();
        this._numberOfNameRecords = dataInput.readShort();
        this._stringStorageOffset = dataInput.readShort();
        this._records = new NameRecord[this._numberOfNameRecords];
        for (short n = 0; n < this._numberOfNameRecords; ++n) {
            this._records[n] = new NameRecord(dataInput);
        }
        final byte[] array = new byte[this._de.getLength() - this._stringStorageOffset];
        dataInput.readFully(array);
        for (short n2 = 0; n2 < this._numberOfNameRecords; ++n2) {
            this._records[n2].loadString(new DataInputStream(new ByteArrayInputStream(array)));
        }
    }
    
    public short getNumberOfNameRecords() {
        return this._numberOfNameRecords;
    }
    
    public NameRecord getRecord(final int n) {
        if (this._numberOfNameRecords > n) {
            return this._records[n];
        }
        return null;
    }
    
    public StringBuilder getRecordsRecordString(final StringBuilder sb, final int n) {
        if (this._numberOfNameRecords > n) {
            this._records[n].getRecordString(sb);
        }
        else {
            sb.append("n/a");
        }
        return sb;
    }
    
    public StringBuilder getNamedRecordString(final StringBuilder sb, final short n) {
        int n2 = 0;
        for (short n3 = 0; n2 == 0 && n3 < this._numberOfNameRecords; ++n3) {
            if (this._records[n3].getNameId() == n) {
                this._records[n3].getRecordString(sb);
                n2 = 1;
            }
        }
        if (n2 == 0) {
            sb.append("n/a");
        }
        return sb;
    }
    
    @Override
    public int getType() {
        return 1851878757;
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
