// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Fixed;
import java.io.IOException;
import java.io.DataInput;

public class HheaTable implements Table
{
    private final DirectoryEntry de;
    private final int version;
    private final short ascender;
    private final short descender;
    private final short lineGap;
    private final short advanceWidthMax;
    private final short minLeftSideBearing;
    private final short minRightSideBearing;
    private final short xMaxExtent;
    private final short caretSlopeRise;
    private final short caretSlopeRun;
    private final short metricDataFormat;
    private final int numberOfHMetrics;
    
    protected HheaTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.version = dataInput.readInt();
        this.ascender = dataInput.readShort();
        this.descender = dataInput.readShort();
        this.lineGap = dataInput.readShort();
        this.advanceWidthMax = dataInput.readShort();
        this.minLeftSideBearing = dataInput.readShort();
        this.minRightSideBearing = dataInput.readShort();
        this.xMaxExtent = dataInput.readShort();
        this.caretSlopeRise = dataInput.readShort();
        this.caretSlopeRun = dataInput.readShort();
        for (int i = 0; i < 5; ++i) {
            dataInput.readShort();
        }
        this.metricDataFormat = dataInput.readShort();
        this.numberOfHMetrics = dataInput.readUnsignedShort();
    }
    
    public short getAdvanceWidthMax() {
        return this.advanceWidthMax;
    }
    
    public short getAscender() {
        return this.ascender;
    }
    
    public short getCaretSlopeRise() {
        return this.caretSlopeRise;
    }
    
    public short getCaretSlopeRun() {
        return this.caretSlopeRun;
    }
    
    public short getDescender() {
        return this.descender;
    }
    
    public short getLineGap() {
        return this.lineGap;
    }
    
    public short getMetricDataFormat() {
        return this.metricDataFormat;
    }
    
    public short getMinLeftSideBearing() {
        return this.minLeftSideBearing;
    }
    
    public short getMinRightSideBearing() {
        return this.minRightSideBearing;
    }
    
    public int getNumberOfHMetrics() {
        return this.numberOfHMetrics;
    }
    
    @Override
    public int getType() {
        return 1751672161;
    }
    
    public short getXMaxExtent() {
        return this.xMaxExtent;
    }
    
    @Override
    public String toString() {
        return "'hhea' Table - Horizontal Header\n--------------------------------" + "\n        'hhea' version:       " + Fixed.floatValue(this.version) + "\n        yAscender:            " + this.ascender + "\n        yDescender:           " + this.descender + "\n        yLineGap:             " + this.lineGap + "\n        advanceWidthMax:      " + this.advanceWidthMax + "\n        minLeftSideBearing:   " + this.minLeftSideBearing + "\n        minRightSideBearing:  " + this.minRightSideBearing + "\n        xMaxExtent:           " + this.xMaxExtent + "\n        horizCaretSlopeNum:   " + this.caretSlopeRise + "\n        horizCaretSlopeDenom: " + this.caretSlopeRun + "\n        reserved0:            0" + "\n        reserved1:            0" + "\n        reserved2:            0" + "\n        reserved3:            0" + "\n        reserved4:            0" + "\n        metricDataFormat:     " + this.metricDataFormat + "\n        numOf_LongHorMetrics: " + this.numberOfHMetrics;
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
}
