// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInput;

public class BaseTable implements Table
{
    private final DirectoryEntry _de;
    private final int _version;
    private final int _horizAxisOffset;
    private final int _vertAxisOffset;
    private Axis _horizAxis;
    private Axis _vertAxis;
    private byte[] _buf;
    
    protected BaseTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        dataInput.readFully(this._buf = new byte[directoryEntry.getLength()]);
        final DataInput dataInputForOffset = this.getDataInputForOffset(0);
        this._version = dataInputForOffset.readInt();
        this._horizAxisOffset = dataInputForOffset.readUnsignedShort();
        this._vertAxisOffset = dataInputForOffset.readUnsignedShort();
        if (this._horizAxisOffset != 0) {
            this._horizAxis = new Axis(this._horizAxisOffset);
        }
        if (this._vertAxisOffset != 0) {
            this._vertAxis = new Axis(this._vertAxisOffset);
        }
        this._buf = null;
    }
    
    private DataInput getDataInputForOffset(final int n) {
        return new DataInputStream(new ByteArrayInputStream(this._buf, n, this._de.getLength() - n));
    }
    
    protected static String tagAsString(final int n) {
        return String.valueOf(new char[] { (char)(n >> 24 & 0xFF), (char)(n >> 16 & 0xFF), (char)(n >> 8 & 0xFF), (char)(n & 0xFF) });
    }
    
    @Override
    public int getType() {
        return 1111577413;
    }
    
    @Override
    public String toString() {
        final StringBuilder append = new StringBuilder().append("; 'BASE' Table - Baseline\n;-------------------------------------\n\n").append("BASEHeader BASEHeaderT").append(Integer.toHexString(0)).append("\n").append(Integer.toHexString(this._version)).append("\nAxisT").append(Integer.toHexString(this._horizAxisOffset)).append("\nAxisT").append(Integer.toHexString(this._vertAxisOffset));
        if (this._horizAxis != null) {
            append.append("\n").append(this._horizAxis.toString());
        }
        if (this._vertAxis != null) {
            append.append("\n").append(this._vertAxis.toString());
        }
        return append.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
    
    abstract class BaseCoord
    {
        abstract int getBaseCoordFormat();
        
        abstract short getCoordinate();
    }
    
    class BaseCoordFormat1 extends BaseCoord
    {
        private final short _coordinate;
        
        protected BaseCoordFormat1(final DataInput dataInput) throws IOException {
            this._coordinate = dataInput.readShort();
        }
        
        @Override
        int getBaseCoordFormat() {
            return 1;
        }
        
        @Override
        short getCoordinate() {
            return this._coordinate;
        }
    }
    
    class BaseCoordFormat2 extends BaseCoord
    {
        private final short _coordinate;
        
        protected BaseCoordFormat2(final DataInput dataInput) throws IOException {
            this._coordinate = dataInput.readShort();
            dataInput.readUnsignedShort();
            dataInput.readUnsignedShort();
        }
        
        @Override
        int getBaseCoordFormat() {
            return 2;
        }
        
        @Override
        short getCoordinate() {
            return this._coordinate;
        }
    }
    
    class BaseCoordFormat3 extends BaseCoord
    {
        private final short _coordinate;
        
        protected BaseCoordFormat3(final DataInput dataInput) throws IOException {
            this._coordinate = dataInput.readShort();
            dataInput.readUnsignedShort();
        }
        
        @Override
        int getBaseCoordFormat() {
            return 2;
        }
        
        @Override
        short getCoordinate() {
            return this._coordinate;
        }
    }
    
    static class FeatMinMaxRecord
    {
        protected FeatMinMaxRecord(final DataInput dataInput) throws IOException {
            dataInput.readInt();
            dataInput.readUnsignedShort();
            dataInput.readUnsignedShort();
        }
    }
    
    class MinMax
    {
        private final int _featMinMaxCount;
        private final FeatMinMaxRecord[] _featMinMaxRecord;
        
        protected MinMax(final int n) throws IOException {
            final DataInput access$000 = BaseTable.this.getDataInputForOffset(n);
            access$000.readUnsignedShort();
            access$000.readUnsignedShort();
            this._featMinMaxCount = access$000.readUnsignedShort();
            this._featMinMaxRecord = new FeatMinMaxRecord[this._featMinMaxCount];
            for (int i = 0; i < this._featMinMaxCount; ++i) {
                this._featMinMaxRecord[i] = new FeatMinMaxRecord(access$000);
            }
        }
    }
    
    class BaseValues
    {
        private final int _baseCoordCount;
        private final int[] _baseCoordOffset;
        private final BaseCoord[] _baseCoords;
        
        protected BaseValues(final int n) throws IOException {
            final DataInput access$000 = BaseTable.this.getDataInputForOffset(n);
            access$000.readUnsignedShort();
            this._baseCoordCount = access$000.readUnsignedShort();
            this._baseCoordOffset = new int[this._baseCoordCount];
            for (int i = 0; i < this._baseCoordCount; ++i) {
                this._baseCoordOffset[i] = access$000.readUnsignedShort();
            }
            this._baseCoords = new BaseCoord[this._baseCoordCount];
            for (int j = 0; j < this._baseCoordCount; ++j) {
                switch (access$000.readUnsignedShort()) {
                    case 1: {
                        this._baseCoords[j] = new BaseCoordFormat1(access$000);
                        break;
                    }
                    case 2: {
                        this._baseCoords[j] = new BaseCoordFormat2(access$000);
                        break;
                    }
                    case 3: {
                        this._baseCoords[j] = new BaseCoordFormat3(access$000);
                        break;
                    }
                }
            }
        }
    }
    
    static class BaseLangSysRecord
    {
        private final int _minMaxOffset;
        
        protected BaseLangSysRecord(final DataInput dataInput) throws IOException {
            dataInput.readInt();
            this._minMaxOffset = dataInput.readUnsignedShort();
        }
        
        int getMinMaxOffset() {
            return this._minMaxOffset;
        }
    }
    
    class BaseScript
    {
        private final int _thisOffset;
        private final int _baseValuesOffset;
        private final int _defaultMinMaxOffset;
        private final int _baseLangSysCount;
        private final BaseLangSysRecord[] _baseLangSysRecord;
        private BaseValues _baseValues;
        private MinMax[] _minMax;
        
        protected BaseScript(final int thisOffset) throws IOException {
            this._thisOffset = thisOffset;
            final DataInput access$000 = BaseTable.this.getDataInputForOffset(thisOffset);
            this._baseValuesOffset = access$000.readUnsignedShort();
            this._defaultMinMaxOffset = access$000.readUnsignedShort();
            this._baseLangSysCount = access$000.readUnsignedShort();
            this._baseLangSysRecord = new BaseLangSysRecord[this._baseLangSysCount];
            for (int i = 0; i < this._baseLangSysCount; ++i) {
                this._baseLangSysRecord[i] = new BaseLangSysRecord(access$000);
            }
            if (this._baseValuesOffset > 0) {
                this._baseValues = new BaseValues(thisOffset + this._baseValuesOffset);
            }
            for (int j = 0; j < this._baseLangSysCount; ++j) {
                this._minMax[j] = new MinMax(thisOffset + this._baseLangSysRecord[j].getMinMaxOffset());
            }
        }
        
        @Override
        public String toString() {
            final StringBuilder append = new StringBuilder().append("\nBaseScript BaseScriptT").append(Integer.toHexString(this._thisOffset)).append("\nBaseValuesT").append(Integer.toHexString(this._thisOffset + this._baseValuesOffset)).append("\nMinMaxT").append(Integer.toHexString(this._thisOffset + this._defaultMinMaxOffset)).append("\n").append(Integer.toHexString(this._baseLangSysCount));
            if (this._baseValues != null) {
                append.append("\n").append(this._baseValues.toString());
            }
            return append.toString();
        }
    }
    
    static class BaseScriptRecord
    {
        private final int _baseScriptTag;
        private final int _baseScriptOffset;
        
        protected BaseScriptRecord(final DataInput dataInput) throws IOException {
            this._baseScriptTag = dataInput.readInt();
            this._baseScriptOffset = dataInput.readUnsignedShort();
        }
        
        int getBaseScriptTag() {
            return this._baseScriptTag;
        }
        
        int getBaseScriptOffset() {
            return this._baseScriptOffset;
        }
    }
    
    class BaseScriptList
    {
        private final int _thisOffset;
        private final int _baseScriptCount;
        private final BaseScriptRecord[] _baseScriptRecord;
        private final BaseScript[] _baseScripts;
        
        protected BaseScriptList(final int thisOffset) throws IOException {
            this._thisOffset = thisOffset;
            final DataInput access$000 = BaseTable.this.getDataInputForOffset(thisOffset);
            this._baseScriptCount = access$000.readUnsignedShort();
            this._baseScriptRecord = new BaseScriptRecord[this._baseScriptCount];
            for (int i = 0; i < this._baseScriptCount; ++i) {
                this._baseScriptRecord[i] = new BaseScriptRecord(access$000);
            }
            this._baseScripts = new BaseScript[this._baseScriptCount];
            for (int j = 0; j < this._baseScriptCount; ++j) {
                this._baseScripts[j] = new BaseScript(thisOffset + this._baseScriptRecord[j].getBaseScriptOffset());
            }
        }
        
        @Override
        public String toString() {
            final StringBuilder append = new StringBuilder().append("\nBaseScriptList BaseScriptListT").append(Integer.toHexString(this._thisOffset)).append("\n").append(Integer.toHexString(this._baseScriptCount));
            for (int i = 0; i < this._baseScriptCount; ++i) {
                append.append("\n                          ; BaseScriptRecord[").append(i);
                append.append("]\n'").append(BaseTable.tagAsString(this._baseScriptRecord[i].getBaseScriptTag())).append("'");
                append.append("\nBaseScriptT").append(Integer.toHexString(this._thisOffset + this._baseScriptRecord[i].getBaseScriptOffset()));
            }
            for (int j = 0; j < this._baseScriptCount; ++j) {
                append.append("\n").append(this._baseScripts[j].toString());
            }
            return append.toString();
        }
    }
    
    class BaseTagList
    {
        private final int _thisOffset;
        private final int _baseTagCount;
        private final int[] _baselineTag;
        
        protected BaseTagList(final int thisOffset) throws IOException {
            this._thisOffset = thisOffset;
            final DataInput access$000 = BaseTable.this.getDataInputForOffset(thisOffset);
            this._baseTagCount = access$000.readUnsignedShort();
            this._baselineTag = new int[this._baseTagCount];
            for (int i = 0; i < this._baseTagCount; ++i) {
                this._baselineTag[i] = access$000.readInt();
            }
        }
        
        @Override
        public String toString() {
            final StringBuilder append = new StringBuilder().append("\nBaseTagList BaseTagListT").append(Integer.toHexString(this._thisOffset)).append("\n").append(Integer.toHexString(this._baseTagCount));
            for (int i = 0; i < this._baseTagCount; ++i) {
                append.append("\n'").append(BaseTable.tagAsString(this._baselineTag[i])).append("'");
            }
            return append.toString();
        }
    }
    
    class Axis
    {
        private final int _thisOffset;
        private final int _baseTagListOffset;
        private final int _baseScriptListOffset;
        private BaseTagList _baseTagList;
        private BaseScriptList _baseScriptList;
        
        protected Axis(final int thisOffset) throws IOException {
            this._thisOffset = thisOffset;
            final DataInput access$000 = BaseTable.this.getDataInputForOffset(thisOffset);
            this._baseTagListOffset = access$000.readUnsignedShort();
            this._baseScriptListOffset = access$000.readUnsignedShort();
            if (this._baseTagListOffset != 0) {
                this._baseTagList = new BaseTagList(thisOffset + this._baseTagListOffset);
            }
            if (this._baseScriptListOffset != 0) {
                this._baseScriptList = new BaseScriptList(thisOffset + this._baseScriptListOffset);
            }
        }
        
        @Override
        public String toString() {
            return "\nAxis AxisT" + Integer.toHexString(this._thisOffset) + "\nBaseTagListT" + Integer.toHexString(this._thisOffset + this._baseTagListOffset) + "\nBaseScriptListT" + Integer.toHexString(this._thisOffset + this._baseScriptListOffset) + "\n" + this._baseTagList + "\n" + this._baseScriptList;
        }
    }
}
