// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.os;

import jogamp.common.os.PlatformPropsImpl;

public class MachineDataInfo
{
    private static final int[] size_arm_mips_32;
    private static final int[] size_x86_32_unix;
    private static final int[] size_x86_32_macos;
    private static final int[] size_ppc_32_unix;
    private static final int[] size_sparc_32_sunos;
    private static final int[] size_x86_32_windows;
    private static final int[] size_lp64_unix;
    private static final int[] size_x86_64_windows;
    private static final int[] align_arm_mips_32;
    private static final int[] align_x86_32_unix;
    private static final int[] align_x86_32_macos;
    private static final int[] align_ppc_32_unix;
    private static final int[] align_sparc_32_sunos;
    private static final int[] align_x86_32_windows;
    private static final int[] align_lp64_unix;
    private static final int[] align_x86_64_windows;
    private final boolean runtimeValidated;
    private final int int8SizeInBytes = 1;
    private final int int16SizeInBytes = 2;
    private final int int32SizeInBytes = 4;
    private final int int64SizeInBytes = 8;
    private final int intSizeInBytes;
    private final int longSizeInBytes;
    private final int floatSizeInBytes;
    private final int doubleSizeInBytes;
    private final int ldoubleSizeInBytes;
    private final int pointerSizeInBytes;
    private final int pageSizeInBytes;
    private final int int8AlignmentInBytes;
    private final int int16AlignmentInBytes;
    private final int int32AlignmentInBytes;
    private final int int64AlignmentInBytes;
    private final int intAlignmentInBytes;
    private final int longAlignmentInBytes;
    private final int floatAlignmentInBytes;
    private final int doubleAlignmentInBytes;
    private final int ldoubleAlignmentInBytes;
    private final int pointerAlignmentInBytes;
    
    public MachineDataInfo(final boolean runtimeValidated, final int intSizeInBytes, final int longSizeInBytes, final int floatSizeInBytes, final int doubleSizeInBytes, final int ldoubleSizeInBytes, final int pointerSizeInBytes, final int pageSizeInBytes, final int int8AlignmentInBytes, final int int16AlignmentInBytes, final int int32AlignmentInBytes, final int int64AlignmentInBytes, final int intAlignmentInBytes, final int longAlignmentInBytes, final int floatAlignmentInBytes, final int doubleAlignmentInBytes, final int ldoubleAlignmentInBytes, final int pointerAlignmentInBytes) {
        this.runtimeValidated = runtimeValidated;
        this.intSizeInBytes = intSizeInBytes;
        this.longSizeInBytes = longSizeInBytes;
        this.floatSizeInBytes = floatSizeInBytes;
        this.doubleSizeInBytes = doubleSizeInBytes;
        this.ldoubleSizeInBytes = ldoubleSizeInBytes;
        this.pointerSizeInBytes = pointerSizeInBytes;
        this.pageSizeInBytes = pageSizeInBytes;
        this.int8AlignmentInBytes = int8AlignmentInBytes;
        this.int16AlignmentInBytes = int16AlignmentInBytes;
        this.int32AlignmentInBytes = int32AlignmentInBytes;
        this.int64AlignmentInBytes = int64AlignmentInBytes;
        this.intAlignmentInBytes = intAlignmentInBytes;
        this.longAlignmentInBytes = longAlignmentInBytes;
        this.floatAlignmentInBytes = floatAlignmentInBytes;
        this.doubleAlignmentInBytes = doubleAlignmentInBytes;
        this.ldoubleAlignmentInBytes = ldoubleAlignmentInBytes;
        this.pointerAlignmentInBytes = pointerAlignmentInBytes;
    }
    
    public final boolean isRuntimeValidated() {
        return this.runtimeValidated;
    }
    
    public final int intSizeInBytes() {
        return this.intSizeInBytes;
    }
    
    public final int longSizeInBytes() {
        return this.longSizeInBytes;
    }
    
    public final int int8SizeInBytes() {
        return 1;
    }
    
    public final int int16SizeInBytes() {
        return 2;
    }
    
    public final int int32SizeInBytes() {
        return 4;
    }
    
    public final int int64SizeInBytes() {
        return 8;
    }
    
    public final int floatSizeInBytes() {
        return this.floatSizeInBytes;
    }
    
    public final int doubleSizeInBytes() {
        return this.doubleSizeInBytes;
    }
    
    public final int ldoubleSizeInBytes() {
        return this.ldoubleSizeInBytes;
    }
    
    public final int pointerSizeInBytes() {
        return this.pointerSizeInBytes;
    }
    
    public final int pageSizeInBytes() {
        return this.pageSizeInBytes;
    }
    
    public final int intAlignmentInBytes() {
        return this.intAlignmentInBytes;
    }
    
    public final int longAlignmentInBytes() {
        return this.longAlignmentInBytes;
    }
    
    public final int int8AlignmentInBytes() {
        return this.int8AlignmentInBytes;
    }
    
    public final int int16AlignmentInBytes() {
        return this.int16AlignmentInBytes;
    }
    
    public final int int32AlignmentInBytes() {
        return this.int32AlignmentInBytes;
    }
    
    public final int int64AlignmentInBytes() {
        return this.int64AlignmentInBytes;
    }
    
    public final int floatAlignmentInBytes() {
        return this.floatAlignmentInBytes;
    }
    
    public final int doubleAlignmentInBytes() {
        return this.doubleAlignmentInBytes;
    }
    
    public final int ldoubleAlignmentInBytes() {
        return this.ldoubleAlignmentInBytes;
    }
    
    public final int pointerAlignmentInBytes() {
        return this.pointerAlignmentInBytes;
    }
    
    public int pageCount(final int n) {
        return (n + (this.pageSizeInBytes - 1)) / this.pageSizeInBytes;
    }
    
    public int pageAlignedSize(final int n) {
        return this.pageCount(n) * this.pageSizeInBytes;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineDataInfo)) {
            return false;
        }
        final MachineDataInfo machineDataInfo = (MachineDataInfo)o;
        return this.pageSizeInBytes == machineDataInfo.pageSizeInBytes && this.compatible(machineDataInfo);
    }
    
    public final boolean compatible(final MachineDataInfo machineDataInfo) {
        return this.intSizeInBytes == machineDataInfo.intSizeInBytes && this.longSizeInBytes == machineDataInfo.longSizeInBytes && this.floatSizeInBytes == machineDataInfo.floatSizeInBytes && this.doubleSizeInBytes == machineDataInfo.doubleSizeInBytes && this.ldoubleSizeInBytes == machineDataInfo.ldoubleSizeInBytes && this.pointerSizeInBytes == machineDataInfo.pointerSizeInBytes && this.int8AlignmentInBytes == machineDataInfo.int8AlignmentInBytes && this.int16AlignmentInBytes == machineDataInfo.int16AlignmentInBytes && this.int32AlignmentInBytes == machineDataInfo.int32AlignmentInBytes && this.int64AlignmentInBytes == machineDataInfo.int64AlignmentInBytes && this.intAlignmentInBytes == machineDataInfo.intAlignmentInBytes && this.longAlignmentInBytes == machineDataInfo.longAlignmentInBytes && this.floatAlignmentInBytes == machineDataInfo.floatAlignmentInBytes && this.doubleAlignmentInBytes == machineDataInfo.doubleAlignmentInBytes && this.ldoubleAlignmentInBytes == machineDataInfo.ldoubleAlignmentInBytes && this.pointerAlignmentInBytes == machineDataInfo.pointerAlignmentInBytes;
    }
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("MachineDataInfo: runtimeValidated ").append(this.isRuntimeValidated()).append(", 32Bit ").append(4 == this.pointerAlignmentInBytes).append(", primitive size / alignment:").append(PlatformPropsImpl.NEWLINE);
        sb.append("  int8    ").append(1).append(" / ").append(this.int8AlignmentInBytes);
        sb.append(", int16   ").append(2).append(" / ").append(this.int16AlignmentInBytes).append(Platform.getNewline());
        sb.append("  int     ").append(this.intSizeInBytes).append(" / ").append(this.intAlignmentInBytes);
        sb.append(", long    ").append(this.longSizeInBytes).append(" / ").append(this.longAlignmentInBytes).append(Platform.getNewline());
        sb.append("  int32   ").append(4).append(" / ").append(this.int32AlignmentInBytes);
        sb.append(", int64   ").append(8).append(" / ").append(this.int64AlignmentInBytes).append(Platform.getNewline());
        sb.append("  float   ").append(this.floatSizeInBytes).append(" / ").append(this.floatAlignmentInBytes);
        sb.append(", double  ").append(this.doubleSizeInBytes).append(" / ").append(this.doubleAlignmentInBytes);
        sb.append(", ldouble ").append(this.ldoubleSizeInBytes).append(" / ").append(this.ldoubleAlignmentInBytes).append(Platform.getNewline());
        sb.append("  pointer ").append(this.pointerSizeInBytes).append(" / ").append(this.pointerAlignmentInBytes);
        sb.append(", page    ").append(this.pageSizeInBytes);
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    static {
        size_arm_mips_32 = new int[] { 4, 4, 4, 8, 8, 4, 4096 };
        size_x86_32_unix = new int[] { 4, 4, 4, 8, 12, 4, 4096 };
        size_x86_32_macos = new int[] { 4, 4, 4, 8, 16, 4, 4096 };
        size_ppc_32_unix = new int[] { 4, 4, 4, 8, 16, 4, 4096 };
        size_sparc_32_sunos = new int[] { 4, 4, 4, 8, 16, 4, 8192 };
        size_x86_32_windows = new int[] { 4, 4, 4, 8, 12, 4, 4096 };
        size_lp64_unix = new int[] { 4, 8, 4, 8, 16, 8, 4096 };
        size_x86_64_windows = new int[] { 4, 4, 4, 8, 16, 8, 4096 };
        align_arm_mips_32 = new int[] { 1, 2, 4, 8, 4, 4, 4, 8, 8, 4 };
        align_x86_32_unix = new int[] { 1, 2, 4, 4, 4, 4, 4, 4, 4, 4 };
        align_x86_32_macos = new int[] { 1, 2, 4, 4, 4, 4, 4, 4, 16, 4 };
        align_ppc_32_unix = new int[] { 1, 2, 4, 8, 4, 4, 4, 8, 16, 4 };
        align_sparc_32_sunos = new int[] { 1, 2, 4, 8, 4, 4, 4, 8, 8, 4 };
        align_x86_32_windows = new int[] { 1, 2, 4, 8, 4, 4, 4, 8, 4, 4 };
        align_lp64_unix = new int[] { 1, 2, 4, 8, 4, 8, 4, 8, 16, 8 };
        align_x86_64_windows = new int[] { 1, 2, 4, 8, 4, 4, 4, 8, 16, 8 };
    }
    
    public enum StaticConfig
    {
        ARM_MIPS_32(MachineDataInfo.size_arm_mips_32, MachineDataInfo.align_arm_mips_32), 
        X86_32_UNIX(MachineDataInfo.size_x86_32_unix, MachineDataInfo.align_x86_32_unix), 
        X86_32_MACOS(MachineDataInfo.size_x86_32_macos, MachineDataInfo.align_x86_32_macos), 
        PPC_32_UNIX(MachineDataInfo.size_ppc_32_unix, MachineDataInfo.align_ppc_32_unix), 
        SPARC_32_SUNOS(MachineDataInfo.size_sparc_32_sunos, MachineDataInfo.align_sparc_32_sunos), 
        X86_32_WINDOWS(MachineDataInfo.size_x86_32_windows, MachineDataInfo.align_x86_32_windows), 
        LP64_UNIX(MachineDataInfo.size_lp64_unix, MachineDataInfo.align_lp64_unix), 
        X86_64_WINDOWS(MachineDataInfo.size_x86_64_windows, MachineDataInfo.align_x86_64_windows);
        
        public final MachineDataInfo md;
        
        private StaticConfig(final int[] array, final int[] array2) {
            int n2 = 0;
            int n3 = 0;
            this.md = new MachineDataInfo(false, array[n2++], array[n2++], array[n2++], array[n2++], array[n2++], array[n2++], array[n2++], array2[n3++], array2[n3++], array2[n3++], array2[n3++], array2[n3++], array2[n3++], array2[n3++], array2[n3++], array2[n3++], array2[n3++]);
        }
        
        public final StringBuilder toString(StringBuilder sb) {
            if (null == sb) {
                sb = new StringBuilder();
            }
            sb.append("MachineDataInfoStatic: ").append(this.name()).append("(").append(this.ordinal()).append("): ");
            this.md.toString(sb);
            return sb;
        }
        
        public final String toShortString() {
            return this.name() + "(" + this.ordinal() + ")";
        }
        
        @Override
        public String toString() {
            return this.toString(null).toString();
        }
        
        public static final void validateUniqueMachineDataInfo() {
            final StaticConfig[] values = values();
            for (int i = values.length - 1; i >= 0; --i) {
                final StaticConfig staticConfig = values[i];
                for (int j = values.length - 1; j >= 0; --j) {
                    if (i != j) {
                        final StaticConfig staticConfig2 = values[j];
                        if (staticConfig.md.compatible(staticConfig2.md)) {
                            final String string = "Duplicate/Compatible MachineDataInfo in StaticConfigs: Elements [" + i + ": " + staticConfig.toShortString() + "] and [" + j + ": " + staticConfig2.toShortString() + "]";
                            System.err.println(string);
                            System.err.println(staticConfig);
                            System.err.println(staticConfig2);
                            throw new InternalError(string);
                        }
                    }
                }
            }
        }
        
        public static final StaticConfig findCompatible(final MachineDataInfo machineDataInfo) {
            final StaticConfig[] values = values();
            for (int i = values.length - 1; i >= 0; --i) {
                final StaticConfig staticConfig = values[i];
                if (staticConfig.md.compatible(machineDataInfo)) {
                    return staticConfig;
                }
            }
            return null;
        }
    }
}
