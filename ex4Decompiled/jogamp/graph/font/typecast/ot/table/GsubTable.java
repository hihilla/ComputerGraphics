// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;

public class GsubTable implements Table, LookupSubtableFactory
{
    private final DirectoryEntry _de;
    private final ScriptList _scriptList;
    private final FeatureList _featureList;
    private final LookupList _lookupList;
    
    protected GsubTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        final byte[] array = new byte[directoryEntry.getLength()];
        dataInput.readFully(array);
        final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(array));
        dataInputStream.readInt();
        final int unsignedShort = dataInputStream.readUnsignedShort();
        final int unsignedShort2 = dataInputStream.readUnsignedShort();
        final int unsignedShort3 = dataInputStream.readUnsignedShort();
        this._scriptList = new ScriptList(dataInputStream, unsignedShort);
        this._featureList = new FeatureList(dataInputStream, unsignedShort2);
        this._lookupList = new LookupList(dataInputStream, unsignedShort3, this);
    }
    
    @Override
    public LookupSubtable read(final int n, final DataInputStream dataInputStream, final int n2) throws IOException {
        LookupSubtable lookupSubtable = null;
        switch (n) {
            case 1: {
                lookupSubtable = SingleSubst.read(dataInputStream, n2);
            }
            case 2: {}
            case 4: {
                lookupSubtable = LigatureSubst.read(dataInputStream, n2);
            }
        }
        return lookupSubtable;
    }
    
    @Override
    public int getType() {
        return 1196643650;
    }
    
    public ScriptList getScriptList() {
        return this._scriptList;
    }
    
    public FeatureList getFeatureList() {
        return this._featureList;
    }
    
    public LookupList getLookupList() {
        return this._lookupList;
    }
    
    @Override
    public String toString() {
        return "GSUB";
    }
    
    public static String lookupTypeAsString(final int n) {
        switch (n) {
            case 1: {
                return "Single";
            }
            case 2: {
                return "Multiple";
            }
            case 3: {
                return "Alternate";
            }
            case 4: {
                return "Ligature";
            }
            case 5: {
                return "Context";
            }
            case 6: {
                return "Chaining";
            }
            default: {
                return "Unknown";
            }
        }
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
}
