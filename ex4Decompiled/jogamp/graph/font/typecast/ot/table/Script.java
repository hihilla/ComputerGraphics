// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;

public class Script
{
    private final int _defaultLangSysOffset;
    private final int _langSysCount;
    private LangSysRecord[] _langSysRecords;
    private LangSys _defaultLangSys;
    private LangSys[] _langSys;
    
    protected Script(final DataInputStream dataInputStream, final int n) throws IOException {
        dataInputStream.reset();
        dataInputStream.skipBytes(n);
        this._defaultLangSysOffset = dataInputStream.readUnsignedShort();
        this._langSysCount = dataInputStream.readUnsignedShort();
        if (this._langSysCount > 0) {
            this._langSysRecords = new LangSysRecord[this._langSysCount];
            for (int i = 0; i < this._langSysCount; ++i) {
                this._langSysRecords[i] = new LangSysRecord(dataInputStream);
            }
        }
        if (this._langSysCount > 0) {
            this._langSys = new LangSys[this._langSysCount];
            for (int j = 0; j < this._langSysCount; ++j) {
                dataInputStream.reset();
                dataInputStream.skipBytes(n + this._langSysRecords[j].getOffset());
                this._langSys[j] = new LangSys(dataInputStream);
            }
        }
        if (this._defaultLangSysOffset > 0) {
            dataInputStream.reset();
            dataInputStream.skipBytes(n + this._defaultLangSysOffset);
            this._defaultLangSys = new LangSys(dataInputStream);
        }
    }
    
    public int getLangSysCount() {
        return this._langSysCount;
    }
    
    public LangSysRecord getLangSysRecord(final int n) {
        return this._langSysRecords[n];
    }
    
    public LangSys getDefaultLangSys() {
        return this._defaultLangSys;
    }
    
    public LangSys getLangSys(final int n) {
        return this._langSys[n];
    }
}
