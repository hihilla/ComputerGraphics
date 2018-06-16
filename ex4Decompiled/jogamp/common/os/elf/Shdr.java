// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class Shdr
{
    StructAccessor accessor;
    private final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] Shdr_size;
    private static final int[] sh_name_offset;
    private static final int[] sh_type_offset;
    private static final int[] sh_flags_offset;
    private static final int[] sh_addr_offset;
    private static final int[] sh_offset_offset;
    private static final int[] sh_size_offset;
    private static final int[] sh_link_offset;
    private static final int[] sh_info_offset;
    private static final int[] sh_addralign_offset;
    private static final int[] sh_entsize_offset;
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public Shdr setSh_name(final int n) {
        this.accessor.setIntAt(Shdr.sh_name_offset[this.mdIdx], n);
        return this;
    }
    
    public int getSh_name() {
        return this.accessor.getIntAt(Shdr.sh_name_offset[this.mdIdx]);
    }
    
    public Shdr setSh_type(final int n) {
        this.accessor.setIntAt(Shdr.sh_type_offset[this.mdIdx], n);
        return this;
    }
    
    public int getSh_type() {
        return this.accessor.getIntAt(Shdr.sh_type_offset[this.mdIdx]);
    }
    
    public Shdr setSh_flags(final long n) {
        this.accessor.setLongAt(Shdr.sh_flags_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getSh_flags() {
        return this.accessor.getLongAt(Shdr.sh_flags_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public Shdr setSh_addr(final long n) {
        this.accessor.setLongAt(Shdr.sh_addr_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getSh_addr() {
        return this.accessor.getLongAt(Shdr.sh_addr_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public Shdr setSh_offset(final long n) {
        this.accessor.setLongAt(Shdr.sh_offset_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getSh_offset() {
        return this.accessor.getLongAt(Shdr.sh_offset_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public Shdr setSh_size(final long n) {
        this.accessor.setLongAt(Shdr.sh_size_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getSh_size() {
        return this.accessor.getLongAt(Shdr.sh_size_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public Shdr setSh_link(final int n) {
        this.accessor.setIntAt(Shdr.sh_link_offset[this.mdIdx], n);
        return this;
    }
    
    public int getSh_link() {
        return this.accessor.getIntAt(Shdr.sh_link_offset[this.mdIdx]);
    }
    
    public Shdr setSh_info(final int n) {
        this.accessor.setIntAt(Shdr.sh_info_offset[this.mdIdx], n);
        return this;
    }
    
    public int getSh_info() {
        return this.accessor.getIntAt(Shdr.sh_info_offset[this.mdIdx]);
    }
    
    public Shdr setSh_addralign(final long n) {
        this.accessor.setLongAt(Shdr.sh_addralign_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getSh_addralign() {
        return this.accessor.getLongAt(Shdr.sh_addralign_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public Shdr setSh_entsize(final long n) {
        this.accessor.setLongAt(Shdr.sh_entsize_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getSh_entsize() {
        return this.accessor.getLongAt(Shdr.sh_entsize_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public static int size(final int n) {
        return Shdr.Shdr_size[n];
    }
    
    public static Shdr create(final int n) {
        return create(n, Buffers.newDirectByteBuffer(size(n)));
    }
    
    public static Shdr create(final int n, final ByteBuffer byteBuffer) {
        return new Shdr(n, byteBuffer);
    }
    
    Shdr(final int mdIdx, final ByteBuffer byteBuffer) {
        this.mdIdx = mdIdx;
        this.md = MachineDataInfo.StaticConfig.values()[mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    static {
        Shdr_size = new int[] { 40, 40, 40, 40, 40, 40, 64, 64 };
        sh_name_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        sh_type_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        sh_flags_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        sh_addr_offset = new int[] { 12, 12, 12, 12, 12, 12, 16, 16 };
        sh_offset_offset = new int[] { 16, 16, 16, 16, 16, 16, 24, 24 };
        sh_size_offset = new int[] { 20, 20, 20, 20, 20, 20, 32, 32 };
        sh_link_offset = new int[] { 24, 24, 24, 24, 24, 24, 40, 40 };
        sh_info_offset = new int[] { 28, 28, 28, 28, 28, 28, 44, 44 };
        sh_addralign_offset = new int[] { 32, 32, 32, 32, 32, 32, 48, 48 };
        sh_entsize_offset = new int[] { 36, 36, 36, 36, 36, 36, 56, 56 };
    }
}
