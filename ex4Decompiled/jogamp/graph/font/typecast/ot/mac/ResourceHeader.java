// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.mac;

import java.io.IOException;
import java.io.DataInput;

public class ResourceHeader
{
    private final int dataOffset;
    private final int mapOffset;
    private final int dataLen;
    private final int mapLen;
    
    public ResourceHeader(final DataInput dataInput) throws IOException {
        this.dataOffset = dataInput.readInt();
        this.mapOffset = dataInput.readInt();
        this.dataLen = dataInput.readInt();
        this.mapLen = dataInput.readInt();
    }
    
    public int getDataOffset() {
        return this.dataOffset;
    }
    
    public int getMapOffset() {
        return this.mapOffset;
    }
    
    public int getDataLength() {
        return this.dataLen;
    }
    
    public int getMapLength() {
        return this.mapLen;
    }
}
