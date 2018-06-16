// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.mac;

import java.io.IOException;
import java.io.DataInput;

public class ResourceMap
{
    private final byte[] headerCopy;
    private final ResourceType[] types;
    
    public ResourceMap(final DataInput dataInput) throws IOException {
        dataInput.readFully(this.headerCopy = new byte[16]);
        dataInput.readInt();
        dataInput.readUnsignedShort();
        dataInput.readUnsignedShort();
        dataInput.readUnsignedShort();
        dataInput.readUnsignedShort();
        final int n = dataInput.readUnsignedShort() + 1;
        this.types = new ResourceType[n];
        for (int i = 0; i < n; ++i) {
            this.types[i] = new ResourceType(dataInput);
        }
        for (int j = 0; j < n; ++j) {
            this.types[j].readRefs(dataInput);
        }
        for (int k = 0; k < n; ++k) {
            this.types[k].readNames(dataInput);
        }
    }
    
    public ResourceType getResourceType(final String s) {
        for (int i = 0; i < this.types.length; ++i) {
            if (this.types[i].getTypeAsString().equals(s)) {
                return this.types[i];
            }
        }
        return null;
    }
    
    public ResourceType getResourceType(final int n) {
        return this.types[n];
    }
    
    public int getResourceTypeCount() {
        return this.types.length;
    }
}
