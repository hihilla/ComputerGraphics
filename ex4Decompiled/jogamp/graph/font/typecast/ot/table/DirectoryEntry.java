// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class DirectoryEntry implements Cloneable
{
    private final int _tag;
    private final int _checksum;
    private final int _offset;
    private final int _length;
    
    protected DirectoryEntry(final DataInput dataInput) throws IOException {
        this._tag = dataInput.readInt();
        this._checksum = dataInput.readInt();
        this._offset = dataInput.readInt();
        this._length = dataInput.readInt();
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
    
    public int getChecksum() {
        return this._checksum;
    }
    
    public int getLength() {
        return this._length;
    }
    
    public int getOffset() {
        return this._offset;
    }
    
    public int getTag() {
        return this._tag;
    }
    
    public String getTagAsString() {
        return new StringBuilder().append((char)(this._tag >> 24 & 0xFF)).append((char)(this._tag >> 16 & 0xFF)).append((char)(this._tag >> 8 & 0xFF)).append((char)(this._tag & 0xFF)).toString();
    }
    
    @Override
    public String toString() {
        return "'" + this.getTagAsString() + "' - chksm = 0x" + Integer.toHexString(this._checksum) + ", off = 0x" + Integer.toHexString(this._offset) + ", len = " + this._length;
    }
}
