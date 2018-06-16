// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt.macosx;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;
import jogamp.nativewindow.jawt.JAWT_PlatformInfo;

public class JAWT_MacOSXDrawingSurfaceInfo implements JAWT_PlatformInfo
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] JAWT_MacOSXDrawingSurfaceInfo_size;
    private static final int[] cocoaViewRef_offset;
    
    public static int size() {
        return JAWT_MacOSXDrawingSurfaceInfo.JAWT_MacOSXDrawingSurfaceInfo_size[JAWT_MacOSXDrawingSurfaceInfo.mdIdx];
    }
    
    public static JAWT_MacOSXDrawingSurfaceInfo create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static JAWT_MacOSXDrawingSurfaceInfo create(final ByteBuffer byteBuffer) {
        return new JAWT_MacOSXDrawingSurfaceInfo(byteBuffer);
    }
    
    JAWT_MacOSXDrawingSurfaceInfo(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[JAWT_MacOSXDrawingSurfaceInfo.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public JAWT_MacOSXDrawingSurfaceInfo setCocoaViewRef(final long n) {
        this.accessor.setLongAt(JAWT_MacOSXDrawingSurfaceInfo.cocoaViewRef_offset[JAWT_MacOSXDrawingSurfaceInfo.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getCocoaViewRef() {
        return this.accessor.getLongAt(JAWT_MacOSXDrawingSurfaceInfo.cocoaViewRef_offset[JAWT_MacOSXDrawingSurfaceInfo.mdIdx], this.md.pointerSizeInBytes());
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        JAWT_MacOSXDrawingSurfaceInfo_size = new int[] { 4, 4, 4, 4, 4, 4, 8, 8 };
        cocoaViewRef_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    }
}
