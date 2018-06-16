// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class DWM_BLURBEHIND
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] DWM_BLURBEHIND_size;
    private static final int[] dwFlags_offset;
    private static final int[] fEnable_offset;
    private static final int[] hRgnBlur_offset;
    private static final int[] fTransitionOnMaximized_offset;
    
    public static int size() {
        return DWM_BLURBEHIND.DWM_BLURBEHIND_size[DWM_BLURBEHIND.mdIdx];
    }
    
    public static DWM_BLURBEHIND create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static DWM_BLURBEHIND create(final ByteBuffer byteBuffer) {
        return new DWM_BLURBEHIND(byteBuffer);
    }
    
    DWM_BLURBEHIND(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[DWM_BLURBEHIND.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public DWM_BLURBEHIND setDwFlags(final int n) {
        this.accessor.setIntAt(DWM_BLURBEHIND.dwFlags_offset[DWM_BLURBEHIND.mdIdx], n);
        return this;
    }
    
    public int getDwFlags() {
        return this.accessor.getIntAt(DWM_BLURBEHIND.dwFlags_offset[DWM_BLURBEHIND.mdIdx]);
    }
    
    public DWM_BLURBEHIND setFEnable(final int n) {
        this.accessor.setIntAt(DWM_BLURBEHIND.fEnable_offset[DWM_BLURBEHIND.mdIdx], n);
        return this;
    }
    
    public int getFEnable() {
        return this.accessor.getIntAt(DWM_BLURBEHIND.fEnable_offset[DWM_BLURBEHIND.mdIdx]);
    }
    
    public DWM_BLURBEHIND setHRgnBlur(final long n) {
        this.accessor.setLongAt(DWM_BLURBEHIND.hRgnBlur_offset[DWM_BLURBEHIND.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getHRgnBlur() {
        return this.accessor.getLongAt(DWM_BLURBEHIND.hRgnBlur_offset[DWM_BLURBEHIND.mdIdx], this.md.pointerSizeInBytes());
    }
    
    public DWM_BLURBEHIND setFTransitionOnMaximized(final int n) {
        this.accessor.setIntAt(DWM_BLURBEHIND.fTransitionOnMaximized_offset[DWM_BLURBEHIND.mdIdx], n);
        return this;
    }
    
    public int getFTransitionOnMaximized() {
        return this.accessor.getIntAt(DWM_BLURBEHIND.fTransitionOnMaximized_offset[DWM_BLURBEHIND.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        DWM_BLURBEHIND_size = new int[] { 16, 16, 16, 16, 16, 16, 24, 24 };
        dwFlags_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        fEnable_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        hRgnBlur_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        fTransitionOnMaximized_offset = new int[] { 12, 12, 12, 12, 12, 12, 16, 16 };
    }
}
