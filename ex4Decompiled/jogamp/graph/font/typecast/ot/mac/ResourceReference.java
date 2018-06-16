// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.mac;

import java.io.IOException;
import java.io.DataInput;

public class ResourceReference
{
    private final int id;
    private final short nameOffset;
    private final short attributes;
    private final int dataOffset;
    private final int handle;
    private String name;
    
    protected ResourceReference(final DataInput dataInput) throws IOException {
        this.id = dataInput.readUnsignedShort();
        this.nameOffset = dataInput.readShort();
        this.attributes = (short)dataInput.readUnsignedByte();
        this.dataOffset = (dataInput.readUnsignedByte() << 16 | dataInput.readUnsignedShort());
        this.handle = dataInput.readInt();
    }
    
    protected void readName(final DataInput dataInput) throws IOException {
        if (this.nameOffset > -1) {
            final byte[] array = new byte[dataInput.readUnsignedByte()];
            dataInput.readFully(array);
            this.name = new String(array);
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public short getNameOffset() {
        return this.nameOffset;
    }
    
    public short getAttributes() {
        return this.attributes;
    }
    
    public int getDataOffset() {
        return this.dataOffset;
    }
    
    public int getHandle() {
        return this.handle;
    }
    
    public String getName() {
        return this.name;
    }
}
