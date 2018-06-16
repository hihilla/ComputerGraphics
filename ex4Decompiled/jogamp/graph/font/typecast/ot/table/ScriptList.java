// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;

public class ScriptList
{
    private int _scriptCount;
    private final ScriptRecord[] _scriptRecords;
    private final Script[] _scripts;
    
    protected ScriptList(final DataInputStream dataInputStream, final int n) throws IOException {
        this._scriptCount = 0;
        dataInputStream.reset();
        dataInputStream.skipBytes(n);
        this._scriptCount = dataInputStream.readUnsignedShort();
        this._scriptRecords = new ScriptRecord[this._scriptCount];
        this._scripts = new Script[this._scriptCount];
        for (int i = 0; i < this._scriptCount; ++i) {
            this._scriptRecords[i] = new ScriptRecord(dataInputStream);
        }
        for (int j = 0; j < this._scriptCount; ++j) {
            this._scripts[j] = new Script(dataInputStream, n + this._scriptRecords[j].getOffset());
        }
    }
    
    public int getScriptCount() {
        return this._scriptCount;
    }
    
    public ScriptRecord getScriptRecord(final int n) {
        return this._scriptRecords[n];
    }
    
    public Script getScript(final int n) {
        return this._scripts[n];
    }
    
    public Script findScript(final String s) {
        if (s.length() != 4) {
            return null;
        }
        final char c = (char)(s.charAt(0) << 24 | s.charAt(1) << 16 | s.charAt(2) << 8 | s.charAt(3));
        for (int i = 0; i < this._scriptCount; ++i) {
            if (this._scriptRecords[i].getTag() == c) {
                return this._scripts[i];
            }
        }
        return null;
    }
}
