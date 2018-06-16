// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

import jogamp.common.Debug;
import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.RandomAccessFile;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.os.Platform;

public class ElfHeaderPart1
{
    static final boolean DEBUG;
    public static int EI_NIDENT;
    public static final byte ELFMAG0 = Byte.MAX_VALUE;
    public static final byte ELFMAG1 = 69;
    public static final byte ELFMAG2 = 76;
    public static final byte ELFMAG3 = 70;
    public static final int EI_CLASS = 4;
    public static final byte ELFCLASSNONE = 0;
    public static final byte ELFCLASS32 = 1;
    public static final byte ELFCLASS64 = 2;
    public static final int EI_DATA = 5;
    public static final byte ELFDATANONE = 0;
    public static final byte ELFDATA2LSB = 1;
    public static final byte ELFDATA2MSB = 2;
    public static final int EI_VERSION = 6;
    public static final byte EV_NONE = 0;
    public static final byte EV_CURRENT = 1;
    public static final int EI_OSABI = 7;
    public static final byte ELFOSABI_SYSV = 0;
    public static final byte ELFOSABI_NONE = 0;
    public static final byte ELFOSABI_HPUX = 1;
    public static final byte ELFOSABI_NETBSD = 2;
    public static final byte ELFOSABI_LINUX = 3;
    public static final byte ELFOSABI_SOLARIS = 6;
    public static final byte ELFOSABI_IRIX = 7;
    public static final byte ELFOSABI_FREEBSD = 8;
    public static final byte ELFOSABI_ARM = 8;
    public static final byte ELFOSABI_STANDALONE = 9;
    public static final byte ELFOSABI_TRU64 = 10;
    public static final byte ELFOSABI_MODESTO = 11;
    public static final byte ELFOSABI_OPENBSD = 12;
    public static final byte ELFOSABI_OPENVMS = 13;
    public static final byte ELFOSABI_NSK = 14;
    public static final byte ELFOSABI_AROS = 15;
    public static final byte ELFOSABI_FENIXOS = 16;
    public static final int EI_ABIVERSION = 8;
    public static final int EI_PAD = 9;
    public static final short ET_NONE = 0;
    public static final short ET_REL = 1;
    public static final short ET_EXEC = 2;
    public static final short ET_DYN = 3;
    public static final short ET_CORE = 4;
    public static final short EM_NONE = 0;
    public static final short EM_M32 = 1;
    public static final short EM_SPARC = 2;
    public static final short EM_386 = 3;
    public static final short EM_68K = 4;
    public static final short EM_88K = 5;
    public static final short EM_486 = 6;
    public static final short EM_860 = 7;
    public static final short EM_MIPS = 8;
    public static final short EM_S370 = 9;
    public static final short EM_MIPS_RS3_LE = 10;
    public static final short EM_PARISC = 15;
    public static final short EM_res016 = 16;
    public static final short EM_VPP550 = 17;
    public static final short EM_SPARC32PLUS = 18;
    public static final short EM_960 = 19;
    public static final short EM_PPC = 20;
    public static final short EM_PPC64 = 21;
    public static final short EM_S390 = 22;
    public static final short EM_SPU = 23;
    public static final short EM_V800 = 36;
    public static final short EM_FR20 = 37;
    public static final short EM_RH32 = 38;
    public static final short EM_MCORE = 39;
    public static final short EM_RCE = 39;
    public static final short EM_ARM = 40;
    public static final short EM_OLD_ALPHA = 41;
    public static final short EM_SH = 42;
    public static final short EM_SPARCV9 = 43;
    public static final short EM_TRICORE = 44;
    public static final short EM_ARC = 45;
    public static final short EM_H8_300 = 46;
    public static final short EM_H8_300H = 47;
    public static final short EM_H8S = 48;
    public static final short EM_H8_500 = 49;
    public static final short EM_IA_64 = 50;
    public static final short EM_MIPS_X = 51;
    public static final short EM_COLDFIRE = 52;
    public static final short EM_68HC12 = 53;
    public static final short EM_MMA = 54;
    public static final short EM_PCP = 55;
    public static final short EM_NCPU = 56;
    public static final short EM_NDR1 = 57;
    public static final short EM_STARCORE = 58;
    public static final short EM_ME16 = 59;
    public static final short EM_ST100 = 60;
    public static final short EM_TINYJ = 61;
    public static final short EM_X86_64 = 62;
    public static final short EM_PDSP = 63;
    public static final short EM_PDP10 = 64;
    public static final short EM_PDP11 = 65;
    public static final short EM_FX66 = 66;
    public static final short EM_ST9PLUS = 67;
    public static final short EM_ST7 = 68;
    public static final short EM_68HC16 = 69;
    public static final short EM_68HC11 = 70;
    public static final short EM_68HC08 = 71;
    public static final short EM_68HC05 = 72;
    public static final short EM_SVX = 73;
    public static final short EM_ST19 = 74;
    public static final short EM_VAX = 75;
    public static final short EM_CRIS = 76;
    public static final short EM_JAVELIN = 77;
    public static final short EM_FIREPATH = 78;
    public static final short EM_ZSP = 79;
    public static final short EM_MMIX = 80;
    public static final short EM_HUANY = 81;
    public static final short EM_PRISM = 82;
    public static final short EM_AVR = 83;
    public static final short EM_FR30 = 84;
    public static final short EM_D10V = 85;
    public static final short EM_D30V = 86;
    public static final short EM_V850 = 87;
    public static final short EM_M32R = 88;
    public static final short EM_MN10300 = 89;
    public static final short EM_MN10200 = 90;
    public static final short EM_PJ = 91;
    public static final short EM_OPENRISC = 92;
    public static final short EM_ARC_A5 = 93;
    public static final short EM_XTENSA = 94;
    public static final short EM_VIDEOCORE = 95;
    public static final short EM_TMM_GPP = 96;
    public static final short EM_NS32K = 97;
    public static final short EM_TPC = 98;
    public static final short EM_SNP1K = 99;
    public static final short EM_ST200 = 100;
    public static final short EM_IP2K = 101;
    public static final short EM_MAX = 102;
    public static final short EM_CR = 103;
    public static final short EM_F2MC16 = 104;
    public static final short EM_MSP430 = 105;
    public static final short EM_BLACKFIN = 106;
    public static final short EM_SE_C33 = 107;
    public static final short EM_SEP = 108;
    public static final short EM_ARCA = 109;
    public static final short EM_UNICORE = 110;
    public static final short EM_EXCESS = 111;
    public static final short EM_DXP = 112;
    public static final short EM_ALTERA_NIOS2 = 113;
    public static final short EM_CRX = 114;
    public static final short EM_XGATE = 115;
    public static final short EM_C166 = 116;
    public static final short EM_M16C = 117;
    public static final short EM_DSPIC30F = 118;
    public static final short EM_CE = 119;
    public static final short EM_M32C = 120;
    public static final short EM_TSK3000 = 131;
    public static final short EM_RS08 = 132;
    public static final short EM_res133 = 133;
    public static final short EM_ECOG2 = 134;
    public static final short EM_SCORE = 135;
    public static final short EM_SCORE7 = 135;
    public static final short EM_DSP24 = 136;
    public static final short EM_VIDEOCORE3 = 137;
    public static final short EM_LATTICEMICO32 = 138;
    public static final short EM_SE_C17 = 139;
    public static final short EM_TI_C6000 = 140;
    public static final short EM_TI_C2000 = 141;
    public static final short EM_TI_C5500 = 142;
    public static final short EM_MMDSP_PLUS = 160;
    public static final short EM_CYPRESS_M8C = 161;
    public static final short EM_R32C = 162;
    public static final short EM_TRIMEDIA = 163;
    public static final short EM_QDSP6 = 164;
    public static final short EM_8051 = 165;
    public static final short EM_STXP7X = 166;
    public static final short EM_NDS32 = 167;
    public static final short EM_ECOG1 = 168;
    public static final short EM_ECOG1X = 168;
    public static final short EM_MAXQ30 = 169;
    public static final short EM_XIMO16 = 170;
    public static final short EM_MANIK = 171;
    public static final short EM_CRAYNV2 = 172;
    public static final short EM_RX = 173;
    public static final short EM_METAG = 174;
    public static final short EM_MCST_ELBRUS = 175;
    public static final short EM_ECOG16 = 176;
    public static final short EM_CR16 = 177;
    public static final short EM_ETPU = 178;
    public static final short EM_SLE9X = 179;
    public static final short EM_L1OM = 180;
    public static final short EM_INTEL181 = 181;
    public static final short EM_INTEL182 = 182;
    public static final short EM_AARCH64 = 183;
    public static final short EM_ARM184 = 184;
    public static final short EM_AVR32 = 185;
    public static final short EM_STM8 = 186;
    public static final short EM_TILE64 = 187;
    public static final short EM_TILEPRO = 188;
    public static final short EM_MICROBLAZE = 189;
    public static final short EM_CUDA = 190;
    public final Ehdr_p1 raw;
    private final byte[] E_ident;
    public final String cpuName;
    public final Platform.CPUType cpuType;
    public final Platform.ABIType abiType;
    public final MachineDataInfo.StaticConfig machDesc;
    
    public static final boolean isIdentityValid(final byte[] array) {
        return 127 == array[0] && 69 == array[1] && 76 == array[2] && 70 == array[3];
    }
    
    public static ElfHeaderPart1 read(final Platform.OSType osType, final RandomAccessFile randomAccessFile) throws IOException, IllegalArgumentException {
        return new ElfHeaderPart1(osType, randomAccessFile);
    }
    
    ElfHeaderPart1(final Platform.OSType osType, final RandomAccessFile randomAccessFile) throws IllegalArgumentException, IOException {
        final byte[] array = new byte[Ehdr_p1.size()];
        IOUtils.readBytes(randomAccessFile, array, 0, array.length);
        this.raw = Ehdr_p1.create(ByteBuffer.wrap(array, 0, array.length));
        this.E_ident = this.raw.getE_ident(0, new byte[Ehdr_p1.getE_identArrayLength()]);
        if (!isIdentityValid(this.E_ident)) {
            throw new IllegalArgumentException("Buffer is not an ELF Header");
        }
        switch (this.getMachine()) {
            case 40: {
                this.cpuName = "arm";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 183: {
                this.cpuName = "aarch64";
                this.abiType = Platform.ABIType.EABI_AARCH64;
                break;
            }
            case 62: {
                this.cpuName = "x86_64";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 3: {
                this.cpuName = "i386";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 6: {
                this.cpuName = "i486";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 50: {
                this.cpuName = "ia64";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 8: {
                if (64 == this.getArchClassBits()) {
                    this.cpuName = (this.isLittleEndian() ? "mips64le" : "mips64");
                }
                else {
                    this.cpuName = (this.isLittleEndian() ? "mipsle" : "mips");
                }
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 10: {
                this.cpuName = "mipsle-rs3";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 51: {
                this.cpuName = (this.isLittleEndian() ? "mipsle-x" : "mips-x");
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 20: {
                this.cpuName = "ppc";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 21: {
                this.cpuName = "ppc64";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            case 42: {
                this.cpuName = "superh";
                this.abiType = Platform.ABIType.GENERIC_ABI;
                break;
            }
            default: {
                throw new IllegalArgumentException("CPUType and ABIType could not be determined");
            }
        }
        this.cpuType = Platform.CPUType.query(this.cpuName.toLowerCase());
        this.machDesc = MachineDataInfoRuntime.guessStaticMachineDataInfo(osType, this.cpuType);
        if (ElfHeaderPart1.DEBUG) {
            System.err.println("ELF-1: cpuName " + this.cpuName + " -> " + this.cpuType + ", " + this.abiType + ", machDesc " + this.machDesc.toShortString());
        }
    }
    
    public final int getArchClassBits() {
        switch (this.E_ident[4]) {
            case 1: {
                return 32;
            }
            case 2: {
                return 64;
            }
            default: {
                return 0;
            }
        }
    }
    
    public final byte getDataEncodingMode() {
        return this.E_ident[5];
    }
    
    public final boolean isLittleEndian() {
        return 1 == this.E_ident[5];
    }
    
    public final boolean isBigEndian() {
        return 2 == this.E_ident[5];
    }
    
    public final boolean isNoneEndian() {
        return 0 == this.E_ident[5];
    }
    
    public final byte getVersion() {
        return this.E_ident[6];
    }
    
    public final byte getOSABI() {
        return this.E_ident[7];
    }
    
    public final byte getOSABIVersion() {
        return this.E_ident[8];
    }
    
    public final short getType() {
        return this.raw.getE_type();
    }
    
    public final short getMachine() {
        return this.raw.getE_machine();
    }
    
    @Override
    public final String toString() {
        String s = null;
        switch (this.getDataEncodingMode()) {
            case 1: {
                s = "LSB";
                break;
            }
            case 2: {
                s = "MSB";
                break;
            }
            default: {
                s = "NON";
                break;
            }
        }
        String s2 = null;
        switch (this.getType()) {
            case 1: {
                s2 = "reloc";
                break;
            }
            case 2: {
                s2 = "exec";
                break;
            }
            case 3: {
                s2 = "shared";
                break;
            }
            case 4: {
                s2 = "core";
                break;
            }
            default: {
                s2 = "none";
                break;
            }
        }
        return "ELF-1[vers " + this.getVersion() + ", machine[" + this.getMachine() + ", " + this.cpuType + ", " + this.abiType + ", machDesc " + this.machDesc.toShortString() + "], bits " + this.getArchClassBits() + ", enc " + s + ", abi[os " + this.getOSABI() + ", vers " + this.getOSABIVersion() + "], type " + s2 + "]";
    }
    
    static {
        DEBUG = Debug.debug("Platform");
        ElfHeaderPart1.EI_NIDENT = 16;
    }
}
