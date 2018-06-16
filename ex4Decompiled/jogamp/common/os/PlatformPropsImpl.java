// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os;

import java.io.IOException;
import com.jogamp.common.os.AndroidVersion;
import jogamp.common.Debug;
import java.util.List;
import jogamp.common.os.elf.ElfHeaderPart2;
import jogamp.common.os.elf.ElfHeaderPart1;
import java.io.RandomAccessFile;
import com.jogamp.common.os.NativeLibrary;
import java.io.File;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import java.security.AccessController;
import java.security.PrivilegedAction;
import com.jogamp.common.os.Platform;
import com.jogamp.common.util.VersionNumber;

public abstract class PlatformPropsImpl
{
    static final boolean DEBUG;
    public static final VersionNumber Version16;
    public static final VersionNumber Version17;
    public static final VersionNumber Version18;
    public static final VersionNumber Version19;
    public static final String OS;
    public static final String OS_lower;
    public static final String OS_VERSION;
    public static final VersionNumber OS_VERSION_NUMBER;
    public static final String ARCH;
    public static final String ARCH_lower;
    public static final String JAVA_VENDOR;
    public static final String JAVA_VENDOR_URL;
    public static final String JAVA_VERSION;
    public static final VersionNumber JAVA_VERSION_NUMBER;
    public static final int JAVA_VERSION_UPDATE;
    public static final String JAVA_VM_NAME;
    public static final String JAVA_RUNTIME_NAME;
    public static final boolean JAVA_SE;
    public static final boolean JAVA_6;
    public static final String NEWLINE;
    public static final boolean LITTLE_ENDIAN;
    public static final Platform.CPUType CPU_ARCH;
    public static final Platform.ABIType ABI_TYPE;
    public static final Platform.OSType OS_TYPE;
    public static final String os_and_arch;
    
    public static final boolean isCompatible(final Platform.CPUType cpuType, final Platform.ABIType abiType, final Platform.CPUType cpuType2, final Platform.ABIType abiType2) {
        return cpuType.isCompatible(cpuType2) && abiType.isCompatible(abiType2);
    }
    
    private static final String getJavaRuntimeNameImpl() {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getProperty("java.runtime.name");
            }
        });
    }
    
    private static final boolean initIsJavaSE() {
        if (null != PlatformPropsImpl.JAVA_RUNTIME_NAME && PlatformPropsImpl.JAVA_RUNTIME_NAME.indexOf("Java SE") != -1) {
            return true;
        }
        try {
            Class.forName("java.nio.LongBuffer");
            Class.forName("java.nio.DoubleBuffer");
            return true;
        }
        catch (ClassNotFoundException ex) {
            return false;
        }
    }
    
    private static final boolean queryIsLittleEndianImpl() {
        final ByteBuffer directByteBuffer = Buffers.newDirectByteBuffer(4);
        final IntBuffer intBuffer = directByteBuffer.asIntBuffer();
        final ShortBuffer shortBuffer = directByteBuffer.asShortBuffer();
        intBuffer.put(0, 168496141);
        return 3085 == shortBuffer.get(0);
    }
    
    private static final boolean contains(final String s, final String[] array) {
        if (null != s && null != array) {
            for (int i = 0; i < array.length; ++i) {
                if (s.indexOf(array[i]) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static final File queryElfFile(final Platform.OSType osType) {
        File file = null;
        try {
            if (Platform.OSType.ANDROID == osType) {
                file = new File(NativeLibrary.findLibrary("gluegen-rt", PlatformPropsImpl.class.getClassLoader()));
            }
            else {
                if (Platform.OSType.LINUX == osType) {
                    file = new File("/proc/self/exe");
                    if (!checkFileReadAccess(file)) {
                        file = null;
                    }
                }
                if (null == file) {
                    file = findSysLib("java");
                }
                if (null == file) {
                    file = findSysLib("jvm");
                }
            }
        }
        catch (Throwable t) {
            if (PlatformPropsImpl.DEBUG) {
                t.printStackTrace();
            }
        }
        return file;
    }
    
    private static final ElfHeaderPart1 readElfHeaderPart1(final Platform.OSType osType, final RandomAccessFile randomAccessFile) {
        ElfHeaderPart1 read = null;
        try {
            read = ElfHeaderPart1.read(osType, randomAccessFile);
        }
        catch (Throwable t) {
            if (PlatformPropsImpl.DEBUG) {
                System.err.println("Caught: " + t.getMessage());
                t.printStackTrace();
            }
        }
        return read;
    }
    
    private static final ElfHeaderPart2 readElfHeaderPart2(final ElfHeaderPart1 elfHeaderPart1, final RandomAccessFile randomAccessFile) {
        ElfHeaderPart2 read = null;
        try {
            read = ElfHeaderPart2.read(elfHeaderPart1, randomAccessFile);
        }
        catch (Throwable t) {
            if (PlatformPropsImpl.DEBUG) {
                System.err.println("Caught: " + t.getMessage());
                t.printStackTrace();
            }
        }
        return read;
    }
    
    private static boolean checkFileReadAccess(final File file) {
        try {
            return file.isFile() && file.canRead();
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    private static File findSysLib(final String s) {
        final List<String> enumerateLibraryPaths = NativeLibrary.enumerateLibraryPaths(s, s, s, true, PlatformPropsImpl.class.getClassLoader());
        for (int i = 0; i < enumerateLibraryPaths.size(); ++i) {
            final File file = new File(enumerateLibraryPaths.get(i));
            if (PlatformPropsImpl.DEBUG) {
                System.err.println("findSysLib #" + i + ": test " + file);
            }
            if (checkFileReadAccess(file)) {
                return file;
            }
            if (PlatformPropsImpl.DEBUG) {
                System.err.println("findSysLib #" + i + ": " + file + " not readable");
            }
        }
        return null;
    }
    
    private static final Platform.OSType getOSTypeImpl(final String s, final boolean b) throws RuntimeException {
        if (b) {
            return Platform.OSType.ANDROID;
        }
        if (s.startsWith("linux")) {
            return Platform.OSType.LINUX;
        }
        if (s.startsWith("freebsd")) {
            return Platform.OSType.FREEBSD;
        }
        if (s.startsWith("android")) {
            return Platform.OSType.ANDROID;
        }
        if (s.startsWith("mac os x") || s.startsWith("darwin")) {
            return Platform.OSType.MACOS;
        }
        if (s.startsWith("sunos")) {
            return Platform.OSType.SUNOS;
        }
        if (s.startsWith("hp-ux")) {
            return Platform.OSType.HPUX;
        }
        if (s.startsWith("windows")) {
            return Platform.OSType.WINDOWS;
        }
        if (s.startsWith("kd")) {
            return Platform.OSType.OPENKODE;
        }
        throw new RuntimeException("Please port OS detection to your platform (" + PlatformPropsImpl.OS_lower + "/" + PlatformPropsImpl.ARCH_lower + ")");
    }
    
    public static void initSingleton() {
    }
    
    public static final String getOSAndArch(final Platform.OSType osType, final Platform.CPUType cpuType, final Platform.ABIType abiType, final boolean b) {
        String s = null;
        switch (cpuType) {
            case ARM:
            case ARMv5:
            case ARMv6:
            case ARMv7: {
                if (Platform.ABIType.EABI_GNU_ARMHF == abiType) {
                    s = "armv6hf";
                    break;
                }
                s = "armv6";
                break;
            }
            case X86_32: {
                s = "i586";
                break;
            }
            case PPC: {
                s = "ppc";
                break;
            }
            case MIPS_32: {
                s = (b ? "mipsel" : "mips");
                break;
            }
            case SuperH: {
                s = "superh";
                break;
            }
            case SPARC_32: {
                s = "sparc";
                break;
            }
            case ARM64:
            case ARMv8_A: {
                s = "aarch64";
                break;
            }
            case X86_64: {
                s = "amd64";
                break;
            }
            case PPC64: {
                s = "ppc64";
                break;
            }
            case MIPS_64: {
                s = "mips64";
                break;
            }
            case IA64: {
                s = "ia64";
                break;
            }
            case SPARCV9_64: {
                s = "sparcv9";
                break;
            }
            case PA_RISC2_0: {
                s = "risc2.0";
                break;
            }
            default: {
                throw new InternalError("Unhandled CPUType: " + cpuType);
            }
        }
        String s2 = null;
        String s3 = null;
        switch (osType) {
            case ANDROID: {
                s2 = "android";
                s3 = s;
                break;
            }
            case MACOS: {
                s2 = "macosx";
                s3 = "universal";
                break;
            }
            case WINDOWS: {
                s2 = "windows";
                s3 = s;
                break;
            }
            case OPENKODE: {
                s2 = "openkode";
                s3 = s;
                break;
            }
            case LINUX: {
                s2 = "linux";
                s3 = s;
                break;
            }
            case FREEBSD: {
                s2 = "freebsd";
                s3 = s;
                break;
            }
            case SUNOS: {
                s2 = "solaris";
                s3 = s;
                break;
            }
            case HPUX: {
                s2 = "hpux";
                s3 = "hppa";
                break;
            }
            default: {
                throw new InternalError("Unhandled OSType: " + osType);
            }
        }
        return s2 + "-" + s3;
    }
    
    static {
        DEBUG = Debug.debug("Platform");
        Version16 = new VersionNumber(1, 6, 0);
        Version17 = new VersionNumber(1, 7, 0);
        Version18 = new VersionNumber(1, 8, 0);
        Version19 = new VersionNumber(1, 9, 0);
        final boolean isAvailable = AndroidVersion.isAvailable;
        JAVA_VENDOR = System.getProperty("java.vendor");
        JAVA_VENDOR_URL = System.getProperty("java.vendor.url");
        JAVA_VERSION = System.getProperty("java.version");
        JAVA_VERSION_NUMBER = new VersionNumber(PlatformPropsImpl.JAVA_VERSION);
        int n = PlatformPropsImpl.JAVA_VERSION.lastIndexOf("-u");
        int n2;
        if (0 < n) {
            n2 = 2;
        }
        else {
            n = PlatformPropsImpl.JAVA_VERSION.lastIndexOf("_");
            n2 = 1;
        }
        if (0 < n) {
            JAVA_VERSION_UPDATE = new VersionNumber(PlatformPropsImpl.JAVA_VERSION.substring(n + n2)).getMajor();
        }
        else {
            JAVA_VERSION_UPDATE = 0;
        }
        JAVA_VM_NAME = System.getProperty("java.vm.name");
        JAVA_RUNTIME_NAME = getJavaRuntimeNameImpl();
        JAVA_SE = initIsJavaSE();
        JAVA_6 = (PlatformPropsImpl.JAVA_SE && (isAvailable || PlatformPropsImpl.JAVA_VERSION_NUMBER.compareTo(PlatformPropsImpl.Version16) >= 0));
        NEWLINE = System.getProperty("line.separator");
        OS = System.getProperty("os.name");
        OS_lower = PlatformPropsImpl.OS.toLowerCase();
        OS_VERSION = System.getProperty("os.version");
        OS_VERSION_NUMBER = new VersionNumber(PlatformPropsImpl.OS_VERSION);
        OS_TYPE = getOSTypeImpl(PlatformPropsImpl.OS_lower, isAvailable);
        final String[] array = { null };
        final Platform.CPUType[] array2 = { null };
        final Platform.ABIType[] array3 = { null };
        final int[] array4 = { 0 };
        final boolean[] array5 = { false };
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                RandomAccessFile randomAccessFile = null;
                try {
                    final File access$000 = queryElfFile(PlatformPropsImpl.OS_TYPE);
                    if (PlatformPropsImpl.DEBUG) {
                        System.err.println("ELF-1: Using " + access$000);
                    }
                    randomAccessFile = new RandomAccessFile(access$000, "r");
                    final ElfHeaderPart1 access$2 = readElfHeaderPart1(PlatformPropsImpl.OS_TYPE, randomAccessFile);
                    if (PlatformPropsImpl.DEBUG) {
                        System.err.println("ELF-1: Got " + access$2);
                    }
                    if (null != access$2) {
                        final ElfHeaderPart2 access$3 = readElfHeaderPart2(access$2, randomAccessFile);
                        if (PlatformPropsImpl.DEBUG) {
                            System.err.println("ELF-2: Got " + access$3);
                        }
                        if (null != access$3) {
                            array[0] = access$3.cpuName;
                            array2[0] = access$3.cpuType;
                            array3[0] = access$3.abiType;
                            if (access$2.isLittleEndian()) {
                                array4[0] = 1;
                            }
                            else if (access$2.isBigEndian()) {
                                array4[0] = 2;
                            }
                            array5[0] = true;
                        }
                    }
                }
                catch (Throwable t) {
                    if (PlatformPropsImpl.DEBUG) {
                        t.printStackTrace();
                    }
                }
                finally {
                    if (null != randomAccessFile) {
                        try {
                            randomAccessFile.close();
                        }
                        catch (IOException ex) {}
                    }
                }
                return null;
            }
        });
        final String s = array[0];
        final Platform.CPUType cpuType = array2[0];
        final Platform.ABIType abiType = array3[0];
        final int n3 = array4[0];
        final boolean b = array5[0];
        if (PlatformPropsImpl.DEBUG) {
            System.err.println("Platform.Elf: valid " + b + ", elfCpuName " + s + ", cpuType " + cpuType + ", abiType " + abiType + ", elfLittleEndian " + n3);
        }
        final boolean queryIsLittleEndianImpl = queryIsLittleEndianImpl();
        if (b) {
            switch (n3) {
                case 1: {
                    LITTLE_ENDIAN = true;
                    break;
                }
                case 2: {
                    LITTLE_ENDIAN = false;
                    break;
                }
                default: {
                    LITTLE_ENDIAN = queryIsLittleEndianImpl;
                    break;
                }
            }
        }
        else {
            LITTLE_ENDIAN = queryIsLittleEndianImpl;
        }
        if (PlatformPropsImpl.DEBUG) {
            System.err.println("Platform.Endian: test-little " + queryIsLittleEndianImpl + ", elf[valid " + b + ", val " + n3 + "] -> LITTLE_ENDIAN " + PlatformPropsImpl.LITTLE_ENDIAN);
        }
        final String property = System.getProperty("os.arch");
        final String lowerCase = property.toLowerCase();
        final Platform.CPUType query = Platform.CPUType.query(lowerCase);
        final Platform.ABIType query2 = Platform.ABIType.query(query, lowerCase);
        if (PlatformPropsImpl.DEBUG) {
            System.err.println("Platform.Property: ARCH " + property + ", CpuType " + query + ", ABIType " + query2);
        }
        int n4;
        if (isAvailable) {
            if (PlatformPropsImpl.DEBUG) {
                System.err.println("Android: CPU_ABI1 str " + AndroidVersion.CPU_ABI + ", CPU_TYPE " + AndroidVersion.CPU_TYPE + ", ABI_TYPE " + AndroidVersion.ABI_TYPE);
                System.err.println("Android: CPU_ABI2 str " + AndroidVersion.CPU_ABI2 + ", CPU_TYPE2 " + AndroidVersion.CPU_TYPE2 + ", ABI_TYPE2 " + AndroidVersion.ABI_TYPE2);
            }
            if (b) {
                if (null != AndroidVersion.CPU_TYPE && isCompatible(cpuType, abiType, AndroidVersion.CPU_TYPE, AndroidVersion.ABI_TYPE)) {
                    ARCH = AndroidVersion.CPU_ABI;
                    ARCH_lower = PlatformPropsImpl.ARCH;
                    CPU_ARCH = AndroidVersion.CPU_TYPE;
                    n4 = 110;
                }
                else if (null != AndroidVersion.CPU_TYPE2 && isCompatible(cpuType, abiType, AndroidVersion.CPU_TYPE2, AndroidVersion.ABI_TYPE2)) {
                    ARCH = AndroidVersion.CPU_ABI2;
                    ARCH_lower = PlatformPropsImpl.ARCH;
                    CPU_ARCH = AndroidVersion.CPU_TYPE2;
                    n4 = 111;
                }
                else {
                    ARCH = cpuType.toString();
                    ARCH_lower = PlatformPropsImpl.ARCH.toLowerCase();
                    CPU_ARCH = cpuType;
                    n4 = 112;
                }
                ABI_TYPE = abiType;
            }
            else if (AndroidVersion.CPU_TYPE.family == Platform.CPUFamily.ARM || null == AndroidVersion.CPU_TYPE2) {
                ARCH = AndroidVersion.CPU_ABI;
                ARCH_lower = PlatformPropsImpl.ARCH;
                CPU_ARCH = AndroidVersion.CPU_TYPE;
                ABI_TYPE = AndroidVersion.ABI_TYPE;
                n4 = 120;
            }
            else {
                ARCH = AndroidVersion.CPU_ABI2;
                ARCH_lower = PlatformPropsImpl.ARCH;
                CPU_ARCH = AndroidVersion.CPU_TYPE2;
                ABI_TYPE = AndroidVersion.ABI_TYPE2;
                n4 = 121;
            }
        }
        else if (b) {
            if (isCompatible(cpuType, abiType, query, query2)) {
                ARCH = property;
                ARCH_lower = lowerCase;
                CPU_ARCH = query;
                ABI_TYPE = query2;
                n4 = 210;
            }
            else {
                ARCH = s;
                ARCH_lower = s;
                CPU_ARCH = cpuType;
                ABI_TYPE = abiType;
                n4 = 211;
            }
        }
        else {
            ARCH = property;
            ARCH_lower = lowerCase;
            CPU_ARCH = query;
            ABI_TYPE = query2;
            n4 = 220;
        }
        if (PlatformPropsImpl.DEBUG) {
            System.err.println("Platform.Hard: ARCH " + PlatformPropsImpl.ARCH + ", CPU_ARCH " + PlatformPropsImpl.CPU_ARCH + ", ABI_TYPE " + PlatformPropsImpl.ABI_TYPE + " - strategy " + n4 + "(isAndroid " + isAvailable + ", elfValid " + b + ")");
        }
        os_and_arch = getOSAndArch(PlatformPropsImpl.OS_TYPE, PlatformPropsImpl.CPU_ARCH, PlatformPropsImpl.ABI_TYPE, PlatformPropsImpl.LITTLE_ENDIAN);
    }
    
    public static class OSXVersion
    {
        public static final VersionNumber Tiger;
        public static final VersionNumber Lion;
        public static final VersionNumber Mavericks;
        
        static {
            Tiger = new VersionNumber(10, 4, 0);
            Lion = new VersionNumber(10, 7, 0);
            Mavericks = new VersionNumber(10, 9, 0);
        }
    }
}
