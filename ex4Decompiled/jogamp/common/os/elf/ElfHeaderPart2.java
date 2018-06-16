// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.RandomAccessFile;
import com.jogamp.common.os.Platform;

public class ElfHeaderPart2
{
    public static final int EF_ARM_ABIMASK = -16777216;
    public static final int EF_ARM_ABISHIFT = 24;
    public static final int EF_ARM_ABI5 = 83886080;
    public static final int EF_ARM_BE8 = 8388608;
    public static final int EF_ARM_GCCMASK = 4198399;
    public static final int EF_ARM_ABI_FLOAT_HARD = 1024;
    public static final int EF_ARM_ABI_FLOAT_SOFT = 512;
    public final ElfHeaderPart1 eh1;
    public final Ehdr_p2 raw;
    public final String cpuName;
    public final Platform.CPUType cpuType;
    public final Platform.ABIType abiType;
    public final SectionHeader[] sht;
    
    public static ElfHeaderPart2 read(final ElfHeaderPart1 elfHeaderPart1, final RandomAccessFile randomAccessFile) throws IOException, IllegalArgumentException {
        return new ElfHeaderPart2(elfHeaderPart1, randomAccessFile);
    }
    
    ElfHeaderPart2(final ElfHeaderPart1 eh1, final RandomAccessFile randomAccessFile) throws IllegalArgumentException, IOException {
        this.eh1 = eh1;
        final byte[] array = new byte[Ehdr_p2.size(eh1.machDesc.ordinal())];
        IOUtils.readBytes(randomAccessFile, array, 0, array.length);
        this.raw = Ehdr_p2.create(eh1.machDesc.ordinal(), ByteBuffer.wrap(array, 0, array.length));
        this.sht = this.readSectionHeaderTable(randomAccessFile);
        if (Platform.CPUFamily.ARM == eh1.cpuType.family && eh1.cpuType.is32Bit) {
            String ntbs = null;
            String ntbs2 = null;
            boolean abiVFPArgsAcceptsVFPVariant = false;
            final SectionHeader sectionHeader = this.getSectionHeader(1879048195);
            if (ElfHeaderPart1.DEBUG) {
                System.err.println("ELF-2: Got ARM Attribs Section Header: " + sectionHeader);
            }
            if (null != sectionHeader) {
                final SectionArmAttributes sectionArmAttributes = (SectionArmAttributes)sectionHeader.readSection(randomAccessFile);
                if (ElfHeaderPart1.DEBUG) {
                    System.err.println("ELF-2: Got ARM Attribs Section Block : " + sectionArmAttributes);
                }
                final SectionArmAttributes.Attribute value = sectionArmAttributes.get(SectionArmAttributes.Tag.CPU_name);
                if (null != value && value.isNTBS()) {
                    ntbs = value.getNTBS();
                }
                final SectionArmAttributes.Attribute value2 = sectionArmAttributes.get(SectionArmAttributes.Tag.CPU_raw_name);
                if (null != value2 && value2.isNTBS()) {
                    ntbs2 = value2.getNTBS();
                }
                final SectionArmAttributes.Attribute value3 = sectionArmAttributes.get(SectionArmAttributes.Tag.ABI_VFP_args);
                if (null != value3) {
                    abiVFPArgsAcceptsVFPVariant = SectionArmAttributes.abiVFPArgsAcceptsVFPVariant(value3.getULEB128());
                }
            }
            String cpuName;
            if (null != ntbs && ntbs.length() > 0) {
                cpuName = ntbs.toLowerCase().replace(' ', '-');
            }
            else if (null != ntbs2 && ntbs2.length() > 0) {
                cpuName = ntbs2.toLowerCase().replace(' ', '-');
            }
            else {
                cpuName = eh1.cpuName;
            }
            Platform.CPUType cpuType = queryCPUTypeSafe(cpuName);
            if (null == cpuType) {
                cpuName = "arm-" + cpuName;
                cpuType = queryCPUTypeSafe(cpuName);
                if (null == cpuType) {
                    cpuName = eh1.cpuName;
                    cpuType = queryCPUTypeSafe(cpuName);
                    if (null == cpuType) {
                        throw new InternalError("XXX: " + cpuName + ", " + eh1);
                    }
                }
            }
            this.cpuName = cpuName;
            this.cpuType = cpuType;
            if (ElfHeaderPart1.DEBUG) {
                System.err.println("ELF-2: abiARM cpuName " + cpuName + "[armCpuName " + ntbs + ", armCpuRawName " + ntbs2 + "] -> " + this.cpuName + " -> " + this.cpuType + ", abiVFPArgsAcceptsVFPVariant " + abiVFPArgsAcceptsVFPVariant);
            }
            if (this.cpuType.is32Bit) {
                this.abiType = (abiVFPArgsAcceptsVFPVariant ? Platform.ABIType.EABI_GNU_ARMHF : Platform.ABIType.EABI_GNU_ARMEL);
            }
            else {
                this.abiType = eh1.abiType;
            }
        }
        else {
            this.cpuName = eh1.cpuName;
            this.cpuType = eh1.cpuType;
            this.abiType = eh1.abiType;
        }
        if (ElfHeaderPart1.DEBUG) {
            System.err.println("ELF-2: cpuName " + this.cpuName + " -> " + this.cpuType + ", " + this.abiType);
        }
    }
    
    private static Platform.CPUType queryCPUTypeSafe(final String s) {
        Platform.CPUType query = null;
        try {
            query = Platform.CPUType.query(s);
        }
        catch (Throwable t) {
            if (ElfHeaderPart1.DEBUG) {
                System.err.println("ELF-2: queryCPUTypeSafe(" + s + "): " + t.getMessage());
            }
        }
        return query;
    }
    
    public final short getSize() {
        return this.raw.getE_ehsize();
    }
    
    public final int getFlags() {
        return this.raw.getE_flags();
    }
    
    public byte getArmABI() {
        return (byte)((0xFF000000 & this.raw.getE_flags()) >> 24 & 0xFF);
    }
    
    public int getArmLegacyGCCFlags() {
        final int e_flags = this.raw.getE_flags();
        return (0x0 != (0xFF000000 & e_flags)) ? (0x400FFF & e_flags) : 0;
    }
    
    public byte getArmFloatMode() {
        final int e_flags = this.raw.getE_flags();
        if (0x0 != (0xFF000000 & e_flags)) {
            if ((0x400 & e_flags) != 0x0) {
                return 2;
            }
            if ((0x200 & e_flags) != 0x0) {
                return 1;
            }
        }
        return 0;
    }
    
    public final SectionHeader getSectionHeader(final int n) {
        for (int i = 0; i < this.sht.length; ++i) {
            final SectionHeader sectionHeader = this.sht[i];
            if (sectionHeader.getType() == n) {
                return sectionHeader;
            }
        }
        return null;
    }
    
    public final SectionHeader getSectionHeader(final String s) {
        for (int i = 0; i < this.sht.length; ++i) {
            final SectionHeader sectionHeader = this.sht[i];
            if (sectionHeader.getName().equals(s)) {
                return sectionHeader;
            }
        }
        return null;
    }
    
    @Override
    public final String toString() {
        final byte armABI = this.getArmABI();
        String string;
        if (armABI != 0) {
            string = ", arm[abi " + armABI + ", lGCC " + this.getArmLegacyGCCFlags() + ", float " + this.getArmFloatMode() + "]";
        }
        else {
            string = "";
        }
        return "ELF-2[" + this.cpuType + ", " + this.abiType + ", flags[" + IOUtils.toHexString(this.getFlags()) + string + "], sh-num " + this.sht.length + "]";
    }
    
    final SectionHeader[] readSectionHeaderTable(final RandomAccessFile randomAccessFile) throws IOException, IllegalArgumentException {
        final long e_shoff = this.raw.getE_shoff();
        if (0L == e_shoff) {
            return new SectionHeader[0];
        }
        IOUtils.seek(randomAccessFile, e_shoff);
        final short e_shstrndx = this.raw.getE_shstrndx();
        final short e_shentsize = this.raw.getE_shentsize();
        int e_shnum;
        SectionHeader[] array2;
        int i;
        if (0 == this.raw.getE_shnum()) {
            final byte[] array = new byte[e_shentsize];
            IOUtils.readBytes(randomAccessFile, array, 0, e_shentsize);
            final SectionHeader sectionHeader = new SectionHeader(this, array, 0, e_shentsize, 0);
            e_shnum = (int)sectionHeader.raw.getSh_size();
            if (0 >= e_shnum) {
                throw new IllegalArgumentException("EHdr sh_num == 0 and 1st SHdr size == 0");
            }
            array2 = new SectionHeader[e_shnum];
            array2[0] = sectionHeader;
            i = 1;
        }
        else {
            e_shnum = this.raw.getE_shnum();
            array2 = new SectionHeader[e_shnum];
            i = 0;
        }
        while (i < e_shnum) {
            final byte[] array3 = new byte[e_shentsize];
            IOUtils.readBytes(randomAccessFile, array3, 0, e_shentsize);
            array2[i] = new SectionHeader(this, array3, 0, e_shentsize, i);
            ++i;
        }
        if (e_shstrndx != 0) {
            if (IOUtils.shortToInt((short)(-256)) <= e_shstrndx) {
                throw new InternalError("TODO strndx: -256 < " + e_shstrndx);
            }
            final SectionHeader sectionHeader2 = array2[e_shstrndx];
            if (3 != sectionHeader2.getType()) {
                throw new IllegalArgumentException("Ref. string Shdr[" + e_shstrndx + "] is of type " + sectionHeader2.raw.getSh_type());
            }
            final Section section = sectionHeader2.readSection(randomAccessFile);
            for (int j = 0; j < e_shnum; ++j) {
                array2[j].initName(section, array2[j].raw.getSh_name());
            }
        }
        return array2;
    }
}
