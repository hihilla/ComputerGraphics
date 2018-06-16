// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Fixed;
import java.io.IOException;
import java.io.DataInput;

public class TableDirectory
{
    private int _version;
    private short _numTables;
    private short _searchRange;
    private short _entrySelector;
    private short _rangeShift;
    private final DirectoryEntry[] _entries;
    
    public TableDirectory(final DataInput dataInput) throws IOException {
        this._version = 0;
        this._numTables = 0;
        this._searchRange = 0;
        this._entrySelector = 0;
        this._rangeShift = 0;
        this._version = dataInput.readInt();
        this._numTables = dataInput.readShort();
        this._searchRange = dataInput.readShort();
        this._entrySelector = dataInput.readShort();
        this._rangeShift = dataInput.readShort();
        this._entries = new DirectoryEntry[this._numTables];
        for (short n = 0; n < this._numTables; ++n) {
            this._entries[n] = new DirectoryEntry(dataInput);
        }
    }
    
    public DirectoryEntry getEntry(final int n) {
        return this._entries[n];
    }
    
    public DirectoryEntry getEntryByTag(final int n) {
        for (short n2 = 0; n2 < this._numTables; ++n2) {
            if (this._entries[n2].getTag() == n) {
                return this._entries[n2];
            }
        }
        return null;
    }
    
    public short getEntrySelector() {
        return this._entrySelector;
    }
    
    public short getNumTables() {
        return this._numTables;
    }
    
    public short getRangeShift() {
        return this._rangeShift;
    }
    
    public short getSearchRange() {
        return this._searchRange;
    }
    
    public int getVersion() {
        return this._version;
    }
    
    @Override
    public String toString() {
        final StringBuilder append = new StringBuilder().append("Offset Table\n------ -----").append("\n  sfnt version:     ").append(Fixed.floatValue(this._version)).append("\n  numTables =       ").append(this._numTables).append("\n  searchRange =     ").append(this._searchRange).append("\n  entrySelector =   ").append(this._entrySelector).append("\n  rangeShift =      ").append(this._rangeShift).append("\n\n");
        for (short n = 0; n < this._numTables; ++n) {
            append.append(n).append(". ").append(this._entries[n].toString()).append("\n");
        }
        return append.toString();
    }
}
