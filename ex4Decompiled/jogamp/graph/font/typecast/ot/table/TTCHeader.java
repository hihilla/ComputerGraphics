// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class TTCHeader
{
    public static final int ttcf = 1953784678;
    private final int ttcTag;
    private final int version;
    private final int directoryCount;
    private final int[] tableDirectory;
    private int dsigTag;
    private final int dsigLength;
    private final int dsigOffset;
    
    public TTCHeader(final DataInput dataInput) throws IOException {
        this.ttcTag = dataInput.readInt();
        this.version = dataInput.readInt();
        this.directoryCount = dataInput.readInt();
        this.tableDirectory = new int[this.directoryCount];
        for (int i = 0; i < this.directoryCount; ++i) {
            this.tableDirectory[i] = dataInput.readInt();
        }
        if (this.version == 65536) {
            this.dsigTag = dataInput.readInt();
        }
        this.dsigLength = dataInput.readInt();
        this.dsigOffset = dataInput.readInt();
    }
    
    public int getDirectoryCount() {
        return this.directoryCount;
    }
    
    public int getTableDirectory(final int n) {
        return this.tableDirectory[n];
    }
    
    public static boolean isTTC(final DataInput dataInput) throws IOException {
        return dataInput.readInt() == 1953784678;
    }
}
