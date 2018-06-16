// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class NameRecord
{
    private final short _platformId;
    private final short _encodingId;
    private final short _languageId;
    private final short _nameId;
    private final short _stringLength;
    private final short _stringOffset;
    private String _record;
    
    protected NameRecord(final DataInput dataInput) throws IOException {
        this._platformId = dataInput.readShort();
        this._encodingId = dataInput.readShort();
        this._languageId = dataInput.readShort();
        this._nameId = dataInput.readShort();
        this._stringLength = dataInput.readShort();
        this._stringOffset = dataInput.readShort();
    }
    
    public short getEncodingId() {
        return this._encodingId;
    }
    
    public short getLanguageId() {
        return this._languageId;
    }
    
    public short getNameId() {
        return this._nameId;
    }
    
    public short getPlatformId() {
        return this._platformId;
    }
    
    public StringBuilder getRecordString(final StringBuilder sb) {
        sb.append(this._record);
        return sb;
    }
    
    protected void loadString(final DataInput dataInput) throws IOException {
        final StringBuilder sb = new StringBuilder();
        dataInput.skipBytes(this._stringOffset);
        if (this._platformId == 0) {
            for (short n = 0; n < this._stringLength / 2; ++n) {
                sb.append(dataInput.readChar());
            }
        }
        else if (this._platformId == 1) {
            for (short n2 = 0; n2 < this._stringLength; ++n2) {
                sb.append((char)dataInput.readByte());
            }
        }
        else if (this._platformId == 2) {
            for (short n3 = 0; n3 < this._stringLength; ++n3) {
                sb.append((char)dataInput.readByte());
            }
        }
        else if (this._platformId == 3) {
            for (short n4 = 0; n4 < this._stringLength / 2; ++n4) {
                sb.append(dataInput.readChar());
            }
        }
        this._record = sb.toString();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("             Platform ID:       ").append(this._platformId).append("\n             Specific ID:       ").append(this._encodingId).append("\n             Language ID:       ").append(this._languageId).append("\n             Name ID:           ").append(this._nameId).append("\n             Length:            ").append(this._stringLength).append("\n             Offset:            ").append(this._stringOffset).append("\n\n").append(this._record);
        return sb.toString();
    }
}
