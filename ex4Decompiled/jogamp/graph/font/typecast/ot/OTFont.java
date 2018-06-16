// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot;

import java.io.DataInput;
import java.io.IOException;
import jogamp.graph.font.typecast.ot.table.DirectoryEntry;
import jogamp.graph.font.typecast.ot.table.TableFactory;
import java.io.DataInputStream;
import jogamp.graph.font.typecast.ot.table.GlyfDescript;
import jogamp.graph.font.typecast.ot.table.GlyphDescription;
import jogamp.graph.font.typecast.ot.table.VheaTable;
import jogamp.graph.font.typecast.ot.table.PostTable;
import jogamp.graph.font.typecast.ot.table.NameTable;
import jogamp.graph.font.typecast.ot.table.MaxpTable;
import jogamp.graph.font.typecast.ot.table.LocaTable;
import jogamp.graph.font.typecast.ot.table.HmtxTable;
import jogamp.graph.font.typecast.ot.table.HdmxTable;
import jogamp.graph.font.typecast.ot.table.HheaTable;
import jogamp.graph.font.typecast.ot.table.HeadTable;
import jogamp.graph.font.typecast.ot.table.GlyfTable;
import jogamp.graph.font.typecast.ot.table.CmapTable;
import jogamp.graph.font.typecast.ot.table.Os2Table;
import jogamp.graph.font.typecast.ot.table.Table;
import jogamp.graph.font.typecast.ot.table.TableDirectory;

public class OTFont
{
    private final OTFontCollection _fc;
    private TableDirectory _tableDirectory;
    private Table[] _tables;
    private Os2Table _os2;
    private CmapTable _cmap;
    private GlyfTable _glyf;
    private HeadTable _head;
    private HheaTable _hhea;
    private HdmxTable _hdmx;
    private HmtxTable _hmtx;
    private LocaTable _loca;
    private MaxpTable _maxp;
    private NameTable _name;
    private PostTable _post;
    private VheaTable _vhea;
    
    public OTFont(final OTFontCollection fc) {
        this._tableDirectory = null;
        this._fc = fc;
    }
    
    public StringBuilder getName(final int n, StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        return this._name.getRecordsRecordString(sb, n);
    }
    
    public StringBuilder getAllNames(StringBuilder sb, final String s) {
        if (null != this._name) {
            if (null == sb) {
                sb = new StringBuilder();
            }
            for (short n = 0; n < this._name.getNumberOfNameRecords(); ++n) {
                this._name.getRecord(n).getRecordString(sb).append(s);
            }
        }
        return sb;
    }
    
    public Table getTable(final int n) {
        for (int i = 0; i < this._tables.length; ++i) {
            if (this._tables[i] != null && this._tables[i].getType() == n) {
                return this._tables[i];
            }
        }
        return null;
    }
    
    public Os2Table getOS2Table() {
        return this._os2;
    }
    
    public CmapTable getCmapTable() {
        return this._cmap;
    }
    
    public HeadTable getHeadTable() {
        return this._head;
    }
    
    public HheaTable getHheaTable() {
        return this._hhea;
    }
    
    public HdmxTable getHdmxTable() {
        return this._hdmx;
    }
    
    public HmtxTable getHmtxTable() {
        return this._hmtx;
    }
    
    public LocaTable getLocaTable() {
        return this._loca;
    }
    
    public MaxpTable getMaxpTable() {
        return this._maxp;
    }
    
    public NameTable getNameTable() {
        return this._name;
    }
    
    public PostTable getPostTable() {
        return this._post;
    }
    
    public VheaTable getVheaTable() {
        return this._vhea;
    }
    
    public int getAscent() {
        return this._hhea.getAscender();
    }
    
    public int getDescent() {
        return this._hhea.getDescender();
    }
    
    public int getNumGlyphs() {
        return this._maxp.getNumGlyphs();
    }
    
    public OTGlyph getGlyph(final int n) {
        final GlyfDescript description = this._glyf.getDescription(n);
        return (null != description) ? new OTGlyph(description, this._hmtx.getLeftSideBearing(n), this._hmtx.getAdvanceWidth(n)) : null;
    }
    
    public TableDirectory getTableDirectory() {
        return this._tableDirectory;
    }
    
    private Table readTable(final DataInputStream dataInputStream, final int n, final int n2) throws IOException {
        dataInputStream.reset();
        final DirectoryEntry entryByTag = this._tableDirectory.getEntryByTag(n2);
        if (entryByTag == null) {
            return null;
        }
        dataInputStream.skip(n + entryByTag.getOffset());
        return TableFactory.create(this._fc, this, entryByTag, dataInputStream);
    }
    
    protected void read(final DataInputStream dataInputStream, final int n, final int n2) throws IOException {
        dataInputStream.reset();
        dataInputStream.skip(n);
        this._tableDirectory = new TableDirectory(dataInputStream);
        this._tables = new Table[this._tableDirectory.getNumTables()];
        this._head = (HeadTable)this.readTable(dataInputStream, n2, 1751474532);
        this._hhea = (HheaTable)this.readTable(dataInputStream, n2, 1751672161);
        this._maxp = (MaxpTable)this.readTable(dataInputStream, n2, 1835104368);
        this._loca = (LocaTable)this.readTable(dataInputStream, n2, 1819239265);
        this._vhea = (VheaTable)this.readTable(dataInputStream, n2, 1986553185);
        int n3 = 0;
        this._tables[n3++] = this._head;
        this._tables[n3++] = this._hhea;
        this._tables[n3++] = this._maxp;
        if (this._loca != null) {
            this._tables[n3++] = this._loca;
        }
        if (this._vhea != null) {
            this._tables[n3++] = this._vhea;
        }
        for (short n4 = 0; n4 < this._tableDirectory.getNumTables(); ++n4) {
            final DirectoryEntry entry = this._tableDirectory.getEntry(n4);
            if (entry.getTag() != 1751474532 && entry.getTag() != 1751672161 && entry.getTag() != 1835104368 && entry.getTag() != 1819239265) {
                if (entry.getTag() != 1986553185) {
                    dataInputStream.reset();
                    dataInputStream.skip(n2 + entry.getOffset());
                    this._tables[n3] = TableFactory.create(this._fc, this, entry, dataInputStream);
                    ++n3;
                }
            }
        }
        this._cmap = (CmapTable)this.getTable(1668112752);
        this._hdmx = (HdmxTable)this.getTable(1751412088);
        this._hmtx = (HmtxTable)this.getTable(1752003704);
        this._name = (NameTable)this.getTable(1851878757);
        this._os2 = (Os2Table)this.getTable(1330851634);
        this._post = (PostTable)this.getTable(1886352244);
        this._glyf = (GlyfTable)this.getTable(1735162214);
    }
    
    @Override
    public String toString() {
        if (this._tableDirectory != null) {
            return this._tableDirectory.toString();
        }
        return "Empty font";
    }
}
