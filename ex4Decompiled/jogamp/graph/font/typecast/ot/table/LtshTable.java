// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class LtshTable implements Table
{
    private final DirectoryEntry de;
    private final int version;
    private final int numGlyphs;
    private final int[] yPels;
    
    protected LtshTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.version = dataInput.readUnsignedShort();
        this.numGlyphs = dataInput.readUnsignedShort();
        this.yPels = new int[this.numGlyphs];
        for (int i = 0; i < this.numGlyphs; ++i) {
            this.yPels[i] = dataInput.readUnsignedByte();
        }
    }
    
    @Override
    public int getType() {
        return 1280594760;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'LTSH' Table - Linear Threshold Table\n-------------------------------------").append("\n 'LTSH' Version:       ").append(this.version).append("\n Number of Glyphs:     ").append(this.numGlyphs).append("\n\n   Glyph #   Threshold\n   -------   ---------\n");
        for (int i = 0; i < this.numGlyphs; ++i) {
            sb.append("   ").append(i).append(".        ").append(this.yPels[i]).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
}
