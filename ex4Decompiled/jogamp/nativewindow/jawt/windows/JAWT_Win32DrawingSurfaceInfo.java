// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt.windows;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;
import jogamp.nativewindow.jawt.JAWT_PlatformInfo;

public class JAWT_Win32DrawingSurfaceInfo implements JAWT_PlatformInfo
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] JAWT_Win32DrawingSurfaceInfo_size;
    private static final int[] handle_offset;
    private static final int[] hdc_offset;
    
    public static int size() {
        return JAWT_Win32DrawingSurfaceInfo.JAWT_Win32DrawingSurfaceInfo_size[JAWT_Win32DrawingSurfaceInfo.mdIdx];
    }
    
    public static JAWT_Win32DrawingSurfaceInfo create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static JAWT_Win32DrawingSurfaceInfo create(final ByteBuffer byteBuffer) {
        return new JAWT_Win32DrawingSurfaceInfo(byteBuffer);
    }
    
    JAWT_Win32DrawingSurfaceInfo(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[JAWT_Win32DrawingSurfaceInfo.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public JAWT_Win32DrawingSurfaceInfo setHandle(final long n) {
        this.accessor.setLongAt(JAWT_Win32DrawingSurfaceInfo.handle_offset[JAWT_Win32DrawingSurfaceInfo.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getHandle() {
        return this.accessor.getLongAt(JAWT_Win32DrawingSurfaceInfo.handle_offset[JAWT_Win32DrawingSurfaceInfo.mdIdx], this.md.pointerSizeInBytes());
    }
    
    public JAWT_Win32DrawingSurfaceInfo setHdc(final long n) {
        this.accessor.setLongAt(JAWT_Win32DrawingSurfaceInfo.hdc_offset[JAWT_Win32DrawingSurfaceInfo.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getHdc() {
        return this.accessor.getLongAt(JAWT_Win32DrawingSurfaceInfo.hdc_offset[JAWT_Win32DrawingSurfaceInfo.mdIdx], this.md.pointerSizeInBytes());
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        JAWT_Win32DrawingSurfaceInfo_size = new int[] { 12, 12, 12, 12, 12, 12, 24, 24 };
        handle_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        hdc_offset = new int[] { 4, 4, 4, 4, 4, 4, 8, 8 };
    }
}
