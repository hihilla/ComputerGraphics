// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.os;

import jogamp.common.os.MachineDataInfoRuntime;
import java.security.AccessController;
import com.jogamp.common.net.Uri;
import com.jogamp.common.util.ReflectionUtil;
import jogamp.common.jvm.JVMUtil;
import com.jogamp.common.jvm.JNILibLoaderBase;
import jogamp.common.Debug;
import com.jogamp.common.util.cache.TempJarCache;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.common.util.JarUtil;
import java.security.PrivilegedAction;
import java.util.concurrent.TimeUnit;
import com.jogamp.common.util.VersionNumber;
import jogamp.common.os.PlatformPropsImpl;

public class Platform extends PlatformPropsImpl
{
    private static final String useTempJarCachePropName = "jogamp.gluegen.UseTempJarCache";
    private static final String libBaseName = "gluegen-rt";
    public static final boolean USE_TEMP_JAR_CACHE;
    private static final MachineDataInfo machineDescription;
    public static final boolean AWT_AVAILABLE;
    private static final boolean isRunningFromJarURL;
    
    public static final boolean isRunningFromJarURL() {
        return Platform.isRunningFromJarURL;
    }
    
    public static void initSingleton() {
    }
    
    public static boolean isLittleEndian() {
        return Platform.LITTLE_ENDIAN;
    }
    
    public static String getOSName() {
        return Platform.OS;
    }
    
    public static String getOSVersion() {
        return Platform.OS_VERSION;
    }
    
    public static VersionNumber getOSVersionNumber() {
        return Platform.OS_VERSION_NUMBER;
    }
    
    public static String getArchName() {
        return Platform.ARCH;
    }
    
    public static OSType getOSType() {
        return Platform.OS_TYPE;
    }
    
    public static CPUFamily getCPUFamily() {
        return Platform.CPU_ARCH.family;
    }
    
    public static CPUType getCPUType() {
        return Platform.CPU_ARCH;
    }
    
    public static boolean is32Bit() {
        return Platform.CPU_ARCH.is32Bit;
    }
    
    public static boolean is64Bit() {
        return !Platform.CPU_ARCH.is32Bit;
    }
    
    public static ABIType getABIType() {
        return Platform.ABI_TYPE;
    }
    
    public static String getOSAndArch() {
        return Platform.os_and_arch;
    }
    
    public static String getJavaVendor() {
        return Platform.JAVA_VENDOR;
    }
    
    public static String getJavaVMName() {
        return Platform.JAVA_VM_NAME;
    }
    
    public static String getJavaRuntimeName() {
        return Platform.JAVA_RUNTIME_NAME;
    }
    
    public static String getJavaVendorURL() {
        return Platform.JAVA_VENDOR_URL;
    }
    
    public static String getJavaVersion() {
        return Platform.JAVA_VERSION;
    }
    
    public static VersionNumber getJavaVersionNumber() {
        return Platform.JAVA_VERSION_NUMBER;
    }
    
    public static String getNewline() {
        return Platform.NEWLINE;
    }
    
    public static MachineDataInfo getMachineDataInfo() {
        return Platform.machineDescription;
    }
    
    public static boolean isAWTAvailable() {
        return Platform.AWT_AVAILABLE;
    }
    
    public static native long currentTimeMillis();
    
    public static native long currentTimeMicros();
    
    public static synchronized long getCurrentSleepJitter() {
        getCurrentSleepJitterImpl(TimeUnit.MILLISECONDS.toNanos(10L), 10);
        return getCurrentSleepJitterImpl(TimeUnit.MILLISECONDS.toNanos(10L), 10);
    }
    
    private static long getCurrentSleepJitterImpl(final long n, final int n2) {
        final long n3 = n / n2;
        final long nanoTime = System.nanoTime();
        for (int i = n2; i > 0; --i) {
            try {
                TimeUnit.NANOSECONDS.sleep(n3);
            }
            catch (InterruptedException ex) {}
        }
        return (System.nanoTime() - nanoTime - n) / n2;
    }
    
    static {
        final boolean[] array = { false };
        final boolean[] array2 = { false };
        final boolean[] array3 = { false };
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                PlatformPropsImpl.initSingleton();
                final ClassLoader classLoader = Platform.class.getClassLoader();
                Uri jarUri = null;
                try {
                    jarUri = JarUtil.getJarUri(Platform.class.getName(), classLoader);
                }
                catch (Exception ex2) {}
                final Uri uri = jarUri;
                array[0] = (null != uri);
                array2[0] = (PlatformPropsImpl.OS_TYPE != OSType.ANDROID && null != uri && PropertyAccess.getBooleanProperty("jogamp.gluegen.UseTempJarCache", true, true));
                if (array2[0] && TempJarCache.initSingleton()) {
                    try {
                        JNILibLoaderBase.addNativeJarLibs(new Class[] { Debug.class }, null);
                    }
                    catch (Exception ex) {
                        System.err.println("Caught " + ex.getClass().getSimpleName() + ": " + ex.getMessage() + ", while JNILibLoaderBase.addNativeJarLibs(..)");
                    }
                }
                DynamicLibraryBundle.GlueJNILibLoader.loadLibrary("gluegen-rt", false, classLoader);
                JVMUtil.initSingleton();
                if (!PropertyAccess.getBooleanProperty("java.awt.headless", true) && ReflectionUtil.isClassAvailable("java.awt.Component", classLoader) && ReflectionUtil.isClassAvailable("java.awt.GraphicsEnvironment", classLoader)) {
                    try {
                        array3[0] = !(boolean)ReflectionUtil.callStaticMethod("java.awt.GraphicsEnvironment", "isHeadless", null, null, classLoader);
                    }
                    catch (Throwable t) {}
                }
                return null;
            }
        });
        isRunningFromJarURL = array[0];
        USE_TEMP_JAR_CACHE = array2[0];
        AWT_AVAILABLE = array3[0];
        MachineDataInfoRuntime.initialize();
        machineDescription = MachineDataInfoRuntime.getRuntime();
    }
    
    public enum OSType
    {
        LINUX, 
        FREEBSD, 
        ANDROID, 
        MACOS, 
        SUNOS, 
        HPUX, 
        WINDOWS, 
        OPENKODE;
    }
    
    public enum CPUFamily
    {
        X86, 
        ARM, 
        PPC, 
        SPARC, 
        MIPS, 
        PA_RISC, 
        IA64, 
        SuperH;
    }
    
    public enum CPUType
    {
        ARM(CPUFamily.ARM, true), 
        ARMv5(CPUFamily.ARM, true), 
        ARMv6(CPUFamily.ARM, true), 
        ARMv7(CPUFamily.ARM, true), 
        X86_32(CPUFamily.X86, true), 
        PPC(CPUFamily.PPC, true), 
        MIPS_32(CPUFamily.MIPS, true), 
        SuperH(CPUFamily.SuperH, true), 
        SPARC_32(CPUFamily.SPARC, true), 
        ARM64(CPUFamily.ARM, false), 
        ARMv8_A(CPUFamily.ARM, false), 
        X86_64(CPUFamily.X86, false), 
        PPC64(CPUFamily.PPC, false), 
        MIPS_64(CPUFamily.MIPS, false), 
        IA64(CPUFamily.IA64, false), 
        SPARCV9_64(CPUFamily.SPARC, false), 
        PA_RISC2_0(CPUFamily.PA_RISC, false);
        
        public final CPUFamily family;
        public final boolean is32Bit;
        
        private CPUType(final CPUFamily family, final boolean is32Bit) {
            this.family = family;
            this.is32Bit = is32Bit;
        }
        
        public final boolean isCompatible(final CPUType cpuType) {
            return null != cpuType && (cpuType == this || (this.family == cpuType.family && this.is32Bit == cpuType.is32Bit));
        }
        
        public static final CPUType query(final String s) {
            if (null == s) {
                throw new IllegalArgumentException("Null cpuABILower arg");
            }
            if (s.equals("x86") || s.equals("i386") || s.equals("i486") || s.equals("i586") || s.equals("i686")) {
                return CPUType.X86_32;
            }
            if (s.equals("x86_64") || s.equals("amd64")) {
                return CPUType.X86_64;
            }
            if (s.equals("ia64")) {
                return CPUType.IA64;
            }
            if (s.equals("aarch64")) {
                return CPUType.ARM64;
            }
            if (s.startsWith("arm")) {
                if (s.equals("armv8-a") || s.equals("arm-v8-a") || s.equals("arm-8-a") || s.equals("arm64-v8a")) {
                    return CPUType.ARMv8_A;
                }
                if (s.startsWith("arm64")) {
                    return CPUType.ARM64;
                }
                if (s.startsWith("armv7") || s.startsWith("arm-v7") || s.startsWith("arm-7") || s.startsWith("armeabi-v7")) {
                    return CPUType.ARMv7;
                }
                if (s.startsWith("armv5") || s.startsWith("arm-v5") || s.startsWith("arm-5")) {
                    return CPUType.ARMv5;
                }
                if (s.startsWith("armv6") || s.startsWith("arm-v6") || s.startsWith("arm-6")) {
                    return CPUType.ARMv6;
                }
                return CPUType.ARM;
            }
            else {
                if (s.equals("sparcv9")) {
                    return CPUType.SPARCV9_64;
                }
                if (s.equals("sparc")) {
                    return CPUType.SPARC_32;
                }
                if (s.equals("pa_risc2.0")) {
                    return CPUType.PA_RISC2_0;
                }
                if (s.startsWith("ppc64")) {
                    return CPUType.PPC64;
                }
                if (s.startsWith("ppc")) {
                    return CPUType.PPC;
                }
                if (s.startsWith("mips64")) {
                    return CPUType.MIPS_64;
                }
                if (s.startsWith("mips")) {
                    return CPUType.MIPS_32;
                }
                if (s.startsWith("superh")) {
                    return CPUType.SuperH;
                }
                throw new RuntimeException("Please port CPUType detection to your platform (CPU_ABI string '" + s + "')");
            }
        }
    }
    
    public enum ABIType
    {
        GENERIC_ABI(0), 
        EABI_GNU_ARMEL(1), 
        EABI_GNU_ARMHF(2), 
        EABI_AARCH64(3);
        
        public final int id;
        
        private ABIType(final int id) {
            this.id = id;
        }
        
        public final boolean isCompatible(final ABIType abiType) {
            return null != abiType && abiType == this;
        }
        
        public static final ABIType query(final CPUType cpuType, final String s) {
            if (null == cpuType) {
                throw new IllegalArgumentException("Null cpuType");
            }
            if (null == s) {
                throw new IllegalArgumentException("Null cpuABILower");
            }
            if (CPUFamily.ARM != cpuType.family) {
                return ABIType.GENERIC_ABI;
            }
            if (!cpuType.is32Bit) {
                return ABIType.EABI_AARCH64;
            }
            if (s.equals("armeabi-v7a-hard")) {
                return ABIType.EABI_GNU_ARMHF;
            }
            return ABIType.EABI_GNU_ARMEL;
        }
    }
}
