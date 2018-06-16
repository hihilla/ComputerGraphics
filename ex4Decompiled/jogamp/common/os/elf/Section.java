// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

public class Section
{
    public SectionHeader sh;
    public byte[] data;
    public int offset;
    public int length;
    
    Section(final SectionHeader sh, final byte[] data, final int offset, final int length) {
        this.sh = sh;
        this.data = data;
        this.offset = offset;
        this.length = length;
    }
    
    @Override
    public String toString() {
        return "Section[" + this.toSubString() + "]";
    }
    
    String toSubString() {
        return this.sh + ", data[off " + this.offset + ", len " + this.length + "/" + this.data.length + "]";
    }
}
