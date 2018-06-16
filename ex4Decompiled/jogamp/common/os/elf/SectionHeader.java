// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class SectionHeader
{
    public static final int SHT_NULL = 0;
    public static final int SHT_PROGBITS = 1;
    public static final int SHT_SYMTAB = 2;
    public static final int SHT_STRTAB = 3;
    public static final int SHT_RELA = 4;
    public static final int SHT_HASH = 5;
    public static final int SHT_DYNAMIC = 6;
    public static final int SHT_NOTE = 7;
    public static final int SHT_NOBITS = 8;
    public static final int SHT_REL = 9;
    public static final int SHT_SHLIB = 10;
    public static final int SHT_DYNSYM = 11;
    public static final int SHT_NUM = 12;
    public static final int SHT_LOPROC = 1879048192;
    public static final int SHT_HIPROC = Integer.MAX_VALUE;
    public static final int SHT_LOUSER = Integer.MIN_VALUE;
    public static final int SHT_HIUSER = -1;
    public static final int SHT_ARM_EXIDX = 1879048193;
    public static final int SHT_ARM_PREEMPTMAP = 1879048194;
    public static final int SHT_ARM_ATTRIBUTES = 1879048195;
    public static final int SHT_AARCH64_ATTRIBUTES = 1879048195;
    public static final int SHT_ARM_DEBUGOVERLAY = 1879048196;
    public static final int SHT_ARM_OVERLAYSECTION = 1879048197;
    public static final short SHN_UNDEF = 0;
    public static final short SHN_LORESERVE = -256;
    public static final short SHN_LOPROC = -256;
    public static final short SHN_HIPROC = -225;
    public static final short SHN_ABS = -15;
    public static final short SHN_COMMON = -14;
    public static final short SHN_HIRESERVE = -1;
    public final ElfHeaderPart2 eh2;
    public final Shdr raw;
    private final int idx;
    private String name;
    
    SectionHeader(final ElfHeaderPart2 elfHeaderPart2, final byte[] array, final int n, final int n2, final int n3) {
        this(elfHeaderPart2, ByteBuffer.wrap(array, 0, array.length), n3);
    }
    
    SectionHeader(final ElfHeaderPart2 eh2, final ByteBuffer byteBuffer, final int idx) {
        this.eh2 = eh2;
        this.raw = Shdr.create(eh2.eh1.machDesc.ordinal(), byteBuffer);
        this.idx = idx;
        this.name = null;
    }
    
    @Override
    public String toString() {
        return "SectionHeader[idx " + this.idx + ", name " + this.name + ", type " + IOUtils.toHexString(this.getType()) + ", link " + this.raw.getSh_link() + ", info " + IOUtils.toHexString(this.raw.getSh_info()) + ", flags " + IOUtils.toHexString(this.getFlags()) + "]";
    }
    
    void initName(final Section section, final int n) throws IndexOutOfBoundsException {
        this.name = IOUtils.getString(section.data, section.offset + n, section.length - n, null);
    }
    
    public int getIndex() {
        return this.idx;
    }
    
    public int getType() {
        return this.raw.getSh_type();
    }
    
    public long getFlags() {
        return this.raw.getSh_flags();
    }
    
    public long getSize() {
        return this.raw.getSh_size();
    }
    
    public String getName() {
        return this.name;
    }
    
    public Section readSection(final RandomAccessFile randomAccessFile) throws IOException, IllegalArgumentException {
        final int long2Int = IOUtils.long2Int(this.raw.getSh_size());
        if (long2Int == 0 || 0 > long2Int) {
            throw new IllegalArgumentException("Shdr[" + this.idx + "] has invalid int size: " + this.raw.getSh_size() + " -> " + long2Int);
        }
        return this.readSectionImpl(randomAccessFile, new byte[long2Int], 0, long2Int);
    }
    
    public Section readSection(final RandomAccessFile randomAccessFile, final byte[] array, final int n, final int n2) throws IOException, IllegalArgumentException {
        final int long2Int = IOUtils.long2Int(this.raw.getSh_size());
        if (long2Int == 0 || 0 > long2Int) {
            throw new IllegalArgumentException("Shdr[" + this.idx + "] has invalid int size: " + this.raw.getSh_size() + " -> " + long2Int);
        }
        if (n2 > long2Int) {
            throw new IllegalArgumentException("Shdr[" + this.idx + "] has only " + long2Int + " bytes, while read request is of " + n2 + " bytes");
        }
        return this.readSectionImpl(randomAccessFile, array, n, n2);
    }
    
    Section readSectionImpl(final RandomAccessFile randomAccessFile, final byte[] array, final int n, final int n2) throws IOException, IllegalArgumentException {
        IOUtils.seek(randomAccessFile, this.raw.getSh_offset());
        IOUtils.readBytes(randomAccessFile, array, n, n2);
        if (1879048195 == this.getType()) {
            return new SectionArmAttributes(this, array, n, n2);
        }
        return new Section(this, array, n, n2);
    }
}
