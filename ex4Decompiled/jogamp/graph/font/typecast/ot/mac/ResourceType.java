// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.mac;

import java.io.IOException;
import java.io.DataInput;

public class ResourceType
{
    private final int type;
    private final int count;
    private final int offset;
    private final ResourceReference[] references;
    
    protected ResourceType(final DataInput dataInput) throws IOException {
        this.type = dataInput.readInt();
        this.count = dataInput.readUnsignedShort() + 1;
        this.offset = dataInput.readUnsignedShort();
        this.references = new ResourceReference[this.count];
    }
    
    protected void readRefs(final DataInput dataInput) throws IOException {
        for (int i = 0; i < this.count; ++i) {
            this.references[i] = new ResourceReference(dataInput);
        }
    }
    
    protected void readNames(final DataInput dataInput) throws IOException {
        for (int i = 0; i < this.count; ++i) {
            this.references[i].readName(dataInput);
        }
    }
    
    public int getType() {
        return this.type;
    }
    
    public String getTypeAsString() {
        return new StringBuilder().append((char)(this.type >> 24 & 0xFF)).append((char)(this.type >> 16 & 0xFF)).append((char)(this.type >> 8 & 0xFF)).append((char)(this.type & 0xFF)).toString();
    }
    
    public int getCount() {
        return this.count;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public ResourceReference getReference(final int n) {
        return this.references[n];
    }
}
