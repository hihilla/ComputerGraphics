// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Fixed;
import java.io.IOException;
import java.io.DataInput;

public class MaxpTable implements Table
{
    private final DirectoryEntry de;
    private final int versionNumber;
    private int numGlyphs;
    private int maxPoints;
    private int maxContours;
    private int maxCompositePoints;
    private int maxCompositeContours;
    private int maxZones;
    private int maxTwilightPoints;
    private int maxStorage;
    private int maxFunctionDefs;
    private int maxInstructionDefs;
    private int maxStackElements;
    private int maxSizeOfInstructions;
    private int maxComponentElements;
    private int maxComponentDepth;
    
    protected MaxpTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.versionNumber = dataInput.readInt();
        if (this.versionNumber == 20480) {
            this.numGlyphs = dataInput.readUnsignedShort();
        }
        else if (this.versionNumber == 65536) {
            this.numGlyphs = dataInput.readUnsignedShort();
            this.maxPoints = dataInput.readUnsignedShort();
            this.maxContours = dataInput.readUnsignedShort();
            this.maxCompositePoints = dataInput.readUnsignedShort();
            this.maxCompositeContours = dataInput.readUnsignedShort();
            this.maxZones = dataInput.readUnsignedShort();
            this.maxTwilightPoints = dataInput.readUnsignedShort();
            this.maxStorage = dataInput.readUnsignedShort();
            this.maxFunctionDefs = dataInput.readUnsignedShort();
            this.maxInstructionDefs = dataInput.readUnsignedShort();
            this.maxStackElements = dataInput.readUnsignedShort();
            this.maxSizeOfInstructions = dataInput.readUnsignedShort();
            this.maxComponentElements = dataInput.readUnsignedShort();
            this.maxComponentDepth = dataInput.readUnsignedShort();
        }
    }
    
    public int getVersionNumber() {
        return this.versionNumber;
    }
    
    public int getMaxComponentDepth() {
        return this.maxComponentDepth;
    }
    
    public int getMaxComponentElements() {
        return this.maxComponentElements;
    }
    
    public int getMaxCompositeContours() {
        return this.maxCompositeContours;
    }
    
    public int getMaxCompositePoints() {
        return this.maxCompositePoints;
    }
    
    public int getMaxContours() {
        return this.maxContours;
    }
    
    public int getMaxFunctionDefs() {
        return this.maxFunctionDefs;
    }
    
    public int getMaxInstructionDefs() {
        return this.maxInstructionDefs;
    }
    
    public int getMaxPoints() {
        return this.maxPoints;
    }
    
    public int getMaxSizeOfInstructions() {
        return this.maxSizeOfInstructions;
    }
    
    public int getMaxStackElements() {
        return this.maxStackElements;
    }
    
    public int getMaxStorage() {
        return this.maxStorage;
    }
    
    public int getMaxTwilightPoints() {
        return this.maxTwilightPoints;
    }
    
    public int getMaxZones() {
        return this.maxZones;
    }
    
    public int getNumGlyphs() {
        return this.numGlyphs;
    }
    
    @Override
    public int getType() {
        return 1835104368;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'maxp' Table - Maximum Profile\n------------------------------").append("\n        'maxp' version:         ").append(Fixed.floatValue(this.versionNumber)).append("\n        numGlyphs:              ").append(this.numGlyphs);
        if (this.versionNumber == 65536) {
            sb.append("\n        maxPoints:              ").append(this.maxPoints).append("\n        maxContours:            ").append(this.maxContours).append("\n        maxCompositePoints:     ").append(this.maxCompositePoints).append("\n        maxCompositeContours:   ").append(this.maxCompositeContours).append("\n        maxZones:               ").append(this.maxZones).append("\n        maxTwilightPoints:      ").append(this.maxTwilightPoints).append("\n        maxStorage:             ").append(this.maxStorage).append("\n        maxFunctionDefs:        ").append(this.maxFunctionDefs).append("\n        maxInstructionDefs:     ").append(this.maxInstructionDefs).append("\n        maxStackElements:       ").append(this.maxStackElements).append("\n        maxSizeOfInstructions:  ").append(this.maxSizeOfInstructions).append("\n        maxComponentElements:   ").append(this.maxComponentElements).append("\n        maxComponentDepth:      ").append(this.maxComponentDepth);
        }
        else {
            sb.append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
}
