// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;
import jogamp.graph.font.typecast.ot.OTFont;
import jogamp.graph.font.typecast.ot.OTFontCollection;

public class TableFactory
{
    public static Table create(final OTFontCollection collection, final OTFont otFont, final DirectoryEntry directoryEntry, final DataInputStream dataInputStream) throws IOException {
        Table table = null;
        if (collection != null) {
            table = collection.getTable(directoryEntry);
            if (table != null) {
                return table;
            }
        }
        switch (directoryEntry.getTag()) {
            case 1111577413: {
                table = new BaseTable(directoryEntry, dataInputStream);
                break;
            }
            case 1128678944: {
                table = new CffTable(directoryEntry, dataInputStream);
                break;
            }
            case 1146308935: {
                table = new DsigTable(directoryEntry, dataInputStream);
            }
            case 1161970772: {}
            case 1161972803: {}
            case 1161974595: {}
            case 1196445523: {
                table = new GposTable(directoryEntry, dataInputStream);
                break;
            }
            case 1196643650: {
                table = new GsubTable(directoryEntry, dataInputStream);
            }
            case 1280594760: {
                table = new LtshTable(directoryEntry, dataInputStream);
            }
            case 1296909912: {}
            case 1330851634: {
                table = new Os2Table(directoryEntry, dataInputStream);
                break;
            }
            case 1346587732: {
                table = new PcltTable(directoryEntry, dataInputStream);
                break;
            }
            case 1447316824: {
                table = new VdmxTable(directoryEntry, dataInputStream);
                break;
            }
            case 1668112752: {
                table = new CmapTable(directoryEntry, dataInputStream);
                break;
            }
            case 1668707360: {
                table = new CvtTable(directoryEntry, dataInputStream);
                break;
            }
            case 1718642541: {
                table = new FpgmTable(directoryEntry, dataInputStream);
            }
            case 1734439792: {
                table = new GaspTable(directoryEntry, dataInputStream);
                break;
            }
            case 1735162214: {
                table = new GlyfTable(directoryEntry, dataInputStream, otFont.getMaxpTable(), otFont.getLocaTable());
                break;
            }
            case 1751412088: {
                table = new HdmxTable(directoryEntry, dataInputStream, otFont.getMaxpTable());
                break;
            }
            case 1751474532: {
                table = new HeadTable(directoryEntry, dataInputStream);
                break;
            }
            case 1751672161: {
                table = new HheaTable(directoryEntry, dataInputStream);
                break;
            }
            case 1752003704: {
                table = new HmtxTable(directoryEntry, dataInputStream, otFont.getHheaTable(), otFont.getMaxpTable());
                break;
            }
            case 1801810542: {
                table = new KernTable(directoryEntry, dataInputStream);
                break;
            }
            case 1819239265: {
                table = new LocaTable(directoryEntry, dataInputStream, otFont.getHeadTable(), otFont.getMaxpTable());
                break;
            }
            case 1835104368: {
                table = new MaxpTable(directoryEntry, dataInputStream);
                break;
            }
            case 1851878757: {
                table = new NameTable(directoryEntry, dataInputStream);
                break;
            }
            case 1886545264: {
                table = new PrepTable(directoryEntry, dataInputStream);
                break;
            }
            case 1886352244: {
                table = new PostTable(directoryEntry, dataInputStream);
                break;
            }
            case 1986553185: {
                table = new VheaTable(directoryEntry, dataInputStream);
                break;
            }
            case 1986884728: {
                table = new VmtxTable(directoryEntry, dataInputStream, otFont.getVheaTable(), otFont.getMaxpTable());
                break;
            }
        }
        if (collection != null && table != null) {
            collection.addTable(table);
        }
        return table;
    }
}
