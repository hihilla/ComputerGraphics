// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public abstract class CmapFormat
{
    protected int _format;
    protected int _length;
    protected int _language;
    
    protected CmapFormat(final DataInput dataInput) throws IOException {
        this._length = dataInput.readUnsignedShort();
        this._language = dataInput.readUnsignedShort();
    }
    
    protected static CmapFormat create(final int n, final DataInput dataInput) throws IOException {
        switch (n) {
            case 0: {
                return new CmapFormat0(dataInput);
            }
            case 2: {
                return new CmapFormat2(dataInput);
            }
            case 4: {
                return new CmapFormat4(dataInput);
            }
            case 6: {
                return new CmapFormat6(dataInput);
            }
            default: {
                return new CmapFormatUnknown(n, dataInput);
            }
        }
    }
    
    public int getFormat() {
        return this._format;
    }
    
    public int getLength() {
        return this._length;
    }
    
    public int getLanguage() {
        return this._language;
    }
    
    public abstract int getRangeCount();
    
    public abstract Range getRange(final int p0) throws ArrayIndexOutOfBoundsException;
    
    public abstract int mapCharCode(final int p0);
    
    @Override
    public String toString() {
        return "format: " + this._format + ", length: " + this._length + ", language: " + this._language;
    }
    
    public static class Range
    {
        private final int _startCode;
        private final int _endCode;
        
        protected Range(final int startCode, final int endCode) {
            this._startCode = startCode;
            this._endCode = endCode;
        }
        
        public int getStartCode() {
            return this._startCode;
        }
        
        public int getEndCode() {
            return this._endCode;
        }
    }
}
