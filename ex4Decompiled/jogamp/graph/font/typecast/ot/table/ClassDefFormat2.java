// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.io.RandomAccessFile;

public class ClassDefFormat2 extends ClassDef
{
    private final int classRangeCount;
    private final RangeRecord[] classRangeRecords;
    
    public ClassDefFormat2(final RandomAccessFile randomAccessFile) throws IOException {
        this.classRangeCount = randomAccessFile.readUnsignedShort();
        this.classRangeRecords = new RangeRecord[this.classRangeCount];
        for (int i = 0; i < this.classRangeCount; ++i) {
            this.classRangeRecords[i] = new RangeRecord(randomAccessFile);
        }
    }
    
    @Override
    public int getFormat() {
        return 2;
    }
}
