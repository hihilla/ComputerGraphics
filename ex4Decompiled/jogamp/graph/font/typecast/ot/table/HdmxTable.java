// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class HdmxTable implements Table
{
    private final DirectoryEntry _de;
    private final int _version;
    private final short _numRecords;
    private final int _sizeDeviceRecords;
    private final DeviceRecord[] _records;
    
    protected HdmxTable(final DirectoryEntry directoryEntry, final DataInput dataInput, final MaxpTable maxpTable) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        this._version = dataInput.readUnsignedShort();
        this._numRecords = dataInput.readShort();
        this._sizeDeviceRecords = dataInput.readInt();
        this._records = new DeviceRecord[this._numRecords];
        for (short n = 0; n < this._numRecords; ++n) {
            this._records[n] = new DeviceRecord(maxpTable.getNumGlyphs(), dataInput);
        }
    }
    
    public int getNumberOfRecords() {
        return this._numRecords;
    }
    
    public DeviceRecord getRecord(final int n) {
        return this._records[n];
    }
    
    @Override
    public int getType() {
        return 1751412088;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'hdmx' Table - Horizontal Device Metrics\n----------------------------------------\n");
        sb.append("Size = ").append(this._de.getLength()).append(" bytes\n").append("\t'hdmx' version:         ").append(this._version).append("\n").append("\t# device records:       ").append(this._numRecords).append("\n").append("\tRecord length:          ").append(this._sizeDeviceRecords).append("\n");
        for (short n = 0; n < this._numRecords; ++n) {
            sb.append("\tDevRec ").append(n).append(": ppem = ").append(this._records[n].getPixelSize()).append(", maxWid = ").append(this._records[n].getMaxWidth()).append("\n");
            for (int i = 0; i < this._records[n].getWidths().length; ++i) {
                sb.append("    ").append(i).append(".   ").append(this._records[n].getWidths()[i]).append("\n");
            }
            sb.append("\n\n");
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
    
    public static class DeviceRecord
    {
        private final short _pixelSize;
        private final short _maxWidth;
        private final short[] _widths;
        
        protected DeviceRecord(final int n, final DataInput dataInput) throws IOException {
            this._pixelSize = dataInput.readByte();
            this._maxWidth = dataInput.readByte();
            this._widths = new short[n];
            for (int i = 0; i < n; ++i) {
                this._widths[i] = dataInput.readByte();
            }
        }
        
        public short getPixelSize() {
            return this._pixelSize;
        }
        
        public short getMaxWidth() {
            return this._maxWidth;
        }
        
        public short[] getWidths() {
            return this._widths;
        }
        
        public short getWidth(final int n) {
            return this._widths[n];
        }
    }
}
