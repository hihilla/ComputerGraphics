// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ClassDefFormat1 extends ClassDef
{
    private final int startGlyph;
    private final int glyphCount;
    private final int[] classValues;
    
    public ClassDefFormat1(final RandomAccessFile randomAccessFile) throws IOException {
        this.startGlyph = randomAccessFile.readUnsignedShort();
        this.glyphCount = randomAccessFile.readUnsignedShort();
        this.classValues = new int[this.glyphCount];
        for (int i = 0; i < this.glyphCount; ++i) {
            this.classValues[i] = randomAccessFile.readUnsignedShort();
        }
    }
    
    @Override
    public int getFormat() {
        return 1;
    }
}
