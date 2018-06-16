// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot;

import jogamp.graph.font.typecast.ot.mac.ResourceReference;
import jogamp.graph.font.typecast.ot.mac.ResourceType;
import jogamp.graph.font.typecast.ot.mac.ResourceMap;
import java.io.DataInput;
import jogamp.graph.font.typecast.ot.mac.ResourceHeader;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import jogamp.graph.font.typecast.ot.table.DirectoryEntry;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import jogamp.graph.font.typecast.ot.table.Table;
import java.util.ArrayList;
import jogamp.graph.font.typecast.ot.table.TTCHeader;

public class OTFontCollection
{
    private String _pathName;
    private String _fileName;
    private TTCHeader _ttcHeader;
    private OTFont[] _fonts;
    private final ArrayList<Table> _tables;
    private boolean _resourceFork;
    
    protected OTFontCollection() {
        this._tables = new ArrayList<Table>();
        this._resourceFork = false;
    }
    
    public static OTFontCollection create(final File file) throws IOException {
        final OTFontCollection collection = new OTFontCollection();
        collection.read(file);
        return collection;
    }
    
    public static OTFontCollection create(final InputStream inputStream, final int n) throws IOException {
        final OTFontCollection collection = new OTFontCollection();
        collection.read(inputStream, n);
        return collection;
    }
    
    public String getPathName() {
        return this._pathName;
    }
    
    public String getFileName() {
        return this._fileName;
    }
    
    public OTFont getFont(final int n) {
        return this._fonts[n];
    }
    
    public int getFontCount() {
        return this._fonts.length;
    }
    
    public TTCHeader getTtcHeader() {
        return this._ttcHeader;
    }
    
    public Table getTable(final DirectoryEntry directoryEntry) {
        for (int i = 0; i < this._tables.size(); ++i) {
            final Table table = this._tables.get(i);
            if (table.getDirectoryEntry().getTag() == directoryEntry.getTag() && table.getDirectoryEntry().getOffset() == directoryEntry.getOffset()) {
                return table;
            }
        }
        return null;
    }
    
    public void addTable(final Table table) {
        this._tables.add(table);
    }
    
    protected void read(File file) throws IOException {
        this._pathName = file.getPath();
        this._fileName = file.getName();
        if (!file.exists()) {
            throw new IOException("File <" + file.getName() + "> doesn't exist.");
        }
        if (file.length() == 0L) {
            file = new File(file, "..namedfork/rsrc");
            if (!file.exists()) {
                throw new IOException();
            }
            this._resourceFork = true;
        }
        final int n = (int)file.length();
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file), n);
        try {
            this.readImpl(bufferedInputStream, n);
        }
        finally {
            bufferedInputStream.close();
        }
    }
    
    protected void read(final InputStream inputStream, final int n) throws IOException {
        this._pathName = "";
        this._fileName = "";
        InputStream inputStream2;
        if (inputStream.markSupported()) {
            inputStream2 = inputStream;
        }
        else {
            inputStream2 = new BufferedInputStream(inputStream, n);
        }
        this.readImpl(inputStream2, n);
    }
    
    private void readImpl(final InputStream inputStream, final int n) throws IOException {
        if (!inputStream.markSupported()) {
            throw new IllegalArgumentException("stream of type " + inputStream.getClass().getName() + " doesn't support mark");
        }
        inputStream.mark(n);
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        if (this._resourceFork || this._pathName.endsWith(".dfont")) {
            final ResourceHeader resourceHeader = new ResourceHeader(dataInputStream);
            dataInputStream.reset();
            dataInputStream.skip(resourceHeader.getMapOffset());
            final ResourceType resourceType = new ResourceMap(dataInputStream).getResourceType("sfnt");
            this._fonts = new OTFont[resourceType.getCount()];
            for (int i = 0; i < resourceType.getCount(); ++i) {
                final ResourceReference reference = resourceType.getReference(i);
                this._fonts[i] = new OTFont(this);
                final int n2 = resourceHeader.getDataOffset() + reference.getDataOffset() + 4;
                this._fonts[i].read(dataInputStream, n2, n2);
            }
        }
        else if (TTCHeader.isTTC(dataInputStream)) {
            dataInputStream.reset();
            this._ttcHeader = new TTCHeader(dataInputStream);
            this._fonts = new OTFont[this._ttcHeader.getDirectoryCount()];
            for (int j = 0; j < this._ttcHeader.getDirectoryCount(); ++j) {
                (this._fonts[j] = new OTFont(this)).read(dataInputStream, this._ttcHeader.getTableDirectory(j), 0);
            }
        }
        else {
            this._fonts = new OTFont[1];
            (this._fonts[0] = new OTFont(this)).read(dataInputStream, 0, 0);
        }
    }
}
