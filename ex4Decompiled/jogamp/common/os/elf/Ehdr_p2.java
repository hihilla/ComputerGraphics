// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class Ehdr_p2
{
    StructAccessor accessor;
    private final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] Ehdr_p2_size;
    private static final int[] e_entry_offset;
    private static final int[] e_phoff_offset;
    private static final int[] e_shoff_offset;
    private static final int[] e_flags_offset;
    private static final int[] e_ehsize_offset;
    private static final int[] e_phentsize_offset;
    private static final int[] e_phnum_offset;
    private static final int[] e_shentsize_offset;
    private static final int[] e_shnum_offset;
    private static final int[] e_shstrndx_offset;
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public Ehdr_p2 setE_entry(final long n) {
        this.accessor.setLongAt(Ehdr_p2.e_entry_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getE_entry() {
        return this.accessor.getLongAt(Ehdr_p2.e_entry_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public Ehdr_p2 setE_phoff(final long n) {
        this.accessor.setLongAt(Ehdr_p2.e_phoff_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getE_phoff() {
        return this.accessor.getLongAt(Ehdr_p2.e_phoff_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public Ehdr_p2 setE_shoff(final long n) {
        this.accessor.setLongAt(Ehdr_p2.e_shoff_offset[this.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getE_shoff() {
        return this.accessor.getLongAt(Ehdr_p2.e_shoff_offset[this.mdIdx], this.md.longSizeInBytes());
    }
    
    public Ehdr_p2 setE_flags(final int n) {
        this.accessor.setIntAt(Ehdr_p2.e_flags_offset[this.mdIdx], n);
        return this;
    }
    
    public int getE_flags() {
        return this.accessor.getIntAt(Ehdr_p2.e_flags_offset[this.mdIdx]);
    }
    
    public Ehdr_p2 setE_ehsize(final short n) {
        this.accessor.setShortAt(Ehdr_p2.e_ehsize_offset[this.mdIdx], n);
        return this;
    }
    
    public short getE_ehsize() {
        return this.accessor.getShortAt(Ehdr_p2.e_ehsize_offset[this.mdIdx]);
    }
    
    public Ehdr_p2 setE_phentsize(final short n) {
        this.accessor.setShortAt(Ehdr_p2.e_phentsize_offset[this.mdIdx], n);
        return this;
    }
    
    public short getE_phentsize() {
        return this.accessor.getShortAt(Ehdr_p2.e_phentsize_offset[this.mdIdx]);
    }
    
    public Ehdr_p2 setE_phnum(final short n) {
        this.accessor.setShortAt(Ehdr_p2.e_phnum_offset[this.mdIdx], n);
        return this;
    }
    
    public short getE_phnum() {
        return this.accessor.getShortAt(Ehdr_p2.e_phnum_offset[this.mdIdx]);
    }
    
    public Ehdr_p2 setE_shentsize(final short n) {
        this.accessor.setShortAt(Ehdr_p2.e_shentsize_offset[this.mdIdx], n);
        return this;
    }
    
    public short getE_shentsize() {
        return this.accessor.getShortAt(Ehdr_p2.e_shentsize_offset[this.mdIdx]);
    }
    
    public Ehdr_p2 setE_shnum(final short n) {
        this.accessor.setShortAt(Ehdr_p2.e_shnum_offset[this.mdIdx], n);
        return this;
    }
    
    public short getE_shnum() {
        return this.accessor.getShortAt(Ehdr_p2.e_shnum_offset[this.mdIdx]);
    }
    
    public Ehdr_p2 setE_shstrndx(final short n) {
        this.accessor.setShortAt(Ehdr_p2.e_shstrndx_offset[this.mdIdx], n);
        return this;
    }
    
    public short getE_shstrndx() {
        return this.accessor.getShortAt(Ehdr_p2.e_shstrndx_offset[this.mdIdx]);
    }
    
    public static int size(final int n) {
        return Ehdr_p2.Ehdr_p2_size[n];
    }
    
    public static Ehdr_p2 create(final int n) {
        return create(n, Buffers.newDirectByteBuffer(size(n)));
    }
    
    public static Ehdr_p2 create(final int n, final ByteBuffer byteBuffer) {
        return new Ehdr_p2(n, byteBuffer);
    }
    
    Ehdr_p2(final int mdIdx, final ByteBuffer byteBuffer) {
        this.mdIdx = mdIdx;
        this.md = MachineDataInfo.StaticConfig.values()[mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    static {
        Ehdr_p2_size = new int[] { 28, 28, 28, 28, 28, 28, 40, 40 };
        e_entry_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        e_phoff_offset = new int[] { 4, 4, 4, 4, 4, 4, 8, 8 };
        e_shoff_offset = new int[] { 8, 8, 8, 8, 8, 8, 16, 16 };
        e_flags_offset = new int[] { 12, 12, 12, 12, 12, 12, 24, 24 };
        e_ehsize_offset = new int[] { 16, 16, 16, 16, 16, 16, 28, 28 };
        e_phentsize_offset = new int[] { 18, 18, 18, 18, 18, 18, 30, 30 };
        e_phnum_offset = new int[] { 20, 20, 20, 20, 20, 20, 32, 32 };
        e_shentsize_offset = new int[] { 22, 22, 22, 22, 22, 22, 34, 34 };
        e_shnum_offset = new int[] { 24, 24, 24, 24, 24, 24, 36, 36 };
        e_shstrndx_offset = new int[] { 26, 26, 26, 26, 26, 26, 38, 38 };
    }
}
