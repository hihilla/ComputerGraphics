// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os;

import com.jogamp.common.os.Platform;
import com.jogamp.common.os.MachineDataInfo;

public class MachineDataInfoRuntime
{
    static volatile boolean initialized;
    static volatile MachineDataInfo runtimeMD;
    static volatile MachineDataInfo.StaticConfig staticMD;
    
    public static void initialize() {
        if (!MachineDataInfoRuntime.initialized) {
            synchronized (MachineDataInfo.class) {
                if (!MachineDataInfoRuntime.initialized) {
                    MachineDataInfo.StaticConfig.validateUniqueMachineDataInfo();
                    final MachineDataInfo runtimeImpl = getRuntimeImpl();
                    final MachineDataInfo.StaticConfig compatible = MachineDataInfo.StaticConfig.findCompatible(runtimeImpl);
                    if (null == compatible) {
                        throw new RuntimeException("No compatible MachineDataInfo.StaticConfig for runtime:" + PlatformPropsImpl.NEWLINE + runtimeImpl);
                    }
                    if (!compatible.md.compatible(runtimeImpl)) {
                        throw new RuntimeException("Incompatible MachineDataInfo:" + PlatformPropsImpl.NEWLINE + " Static " + compatible + PlatformPropsImpl.NEWLINE + " Runtime " + runtimeImpl);
                    }
                    MachineDataInfoRuntime.runtimeMD = runtimeImpl;
                    MachineDataInfoRuntime.staticMD = compatible;
                    MachineDataInfoRuntime.initialized = true;
                    if (PlatformPropsImpl.DEBUG) {
                        System.err.println("MachineDataInfoRuntime.initialize():" + PlatformPropsImpl.NEWLINE + " Static " + compatible + PlatformPropsImpl.NEWLINE + " Runtime " + runtimeImpl);
                    }
                    return;
                }
            }
        }
        throw new InternalError("Already initialized");
    }
    
    public static MachineDataInfo.StaticConfig getStatic() {
        if (!MachineDataInfoRuntime.initialized) {
            synchronized (MachineDataInfo.class) {
                if (!MachineDataInfoRuntime.initialized) {
                    throw new InternalError("Not set");
                }
            }
        }
        return MachineDataInfoRuntime.staticMD;
    }
    
    public static MachineDataInfo getRuntime() {
        if (!MachineDataInfoRuntime.initialized) {
            synchronized (MachineDataInfo.class) {
                if (!MachineDataInfoRuntime.initialized) {
                    throw new InternalError("Not set");
                }
            }
        }
        return MachineDataInfoRuntime.runtimeMD;
    }
    
    public static MachineDataInfo.StaticConfig guessStaticMachineDataInfo(final Platform.OSType osType, final Platform.CPUType cpuType) {
        if (cpuType.is32Bit) {
            if (Platform.CPUFamily.ARM == cpuType.family || Platform.CPUType.MIPS_32 == cpuType) {
                return MachineDataInfo.StaticConfig.ARM_MIPS_32;
            }
            if (Platform.OSType.WINDOWS == osType) {
                return MachineDataInfo.StaticConfig.X86_32_WINDOWS;
            }
            if (Platform.OSType.MACOS == osType) {
                return MachineDataInfo.StaticConfig.X86_32_MACOS;
            }
            if (Platform.OSType.SUNOS == osType && Platform.CPUType.SPARC_32 == cpuType) {
                return MachineDataInfo.StaticConfig.SPARC_32_SUNOS;
            }
            if (Platform.CPUType.PPC == cpuType) {
                return MachineDataInfo.StaticConfig.PPC_32_UNIX;
            }
            return MachineDataInfo.StaticConfig.X86_32_UNIX;
        }
        else {
            if (osType == Platform.OSType.WINDOWS) {
                return MachineDataInfo.StaticConfig.X86_64_WINDOWS;
            }
            return MachineDataInfo.StaticConfig.LP64_UNIX;
        }
    }
    
    private static MachineDataInfo getRuntimeImpl() {
        try {
            Platform.initSingleton();
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            return null;
        }
        final int pointerSizeInBytesImpl = getPointerSizeInBytesImpl();
        switch (pointerSizeInBytesImpl) {
            case 4:
            case 8: {
                final long pageSizeInBytesImpl = getPageSizeInBytesImpl();
                if (2147483647L < pageSizeInBytesImpl) {
                    throw new InternalError("PageSize exceeds integer value: " + pageSizeInBytesImpl);
                }
                return new MachineDataInfo(true, getSizeOfIntImpl(), getSizeOfLongImpl(), getSizeOfFloatImpl(), getSizeOfDoubleImpl(), getSizeOfLongDoubleImpl(), pointerSizeInBytesImpl, (int)pageSizeInBytesImpl, getAlignmentInt8Impl(), getAlignmentInt16Impl(), getAlignmentInt32Impl(), getAlignmentInt64Impl(), getAlignmentIntImpl(), getAlignmentLongImpl(), getAlignmentFloatImpl(), getAlignmentDoubleImpl(), getAlignmentLongDoubleImpl(), getAlignmentPointerImpl());
            }
            default: {
                throw new RuntimeException("Unsupported pointer size " + pointerSizeInBytesImpl + "bytes, please implement.");
            }
        }
    }
    
    private static native int getPointerSizeInBytesImpl();
    
    private static native long getPageSizeInBytesImpl();
    
    private static native int getAlignmentInt8Impl();
    
    private static native int getAlignmentInt16Impl();
    
    private static native int getAlignmentInt32Impl();
    
    private static native int getAlignmentInt64Impl();
    
    private static native int getAlignmentIntImpl();
    
    private static native int getAlignmentLongImpl();
    
    private static native int getAlignmentPointerImpl();
    
    private static native int getAlignmentFloatImpl();
    
    private static native int getAlignmentDoubleImpl();
    
    private static native int getAlignmentLongDoubleImpl();
    
    private static native int getSizeOfIntImpl();
    
    private static native int getSizeOfLongImpl();
    
    private static native int getSizeOfPointerImpl();
    
    private static native int getSizeOfFloatImpl();
    
    private static native int getSizeOfDoubleImpl();
    
    private static native int getSizeOfLongDoubleImpl();
    
    static {
        MachineDataInfoRuntime.initialized = false;
        MachineDataInfoRuntime.runtimeMD = null;
        MachineDataInfoRuntime.staticMD = null;
    }
}
