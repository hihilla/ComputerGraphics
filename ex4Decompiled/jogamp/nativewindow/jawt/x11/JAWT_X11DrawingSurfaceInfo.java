// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt.x11;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;
import jogamp.nativewindow.jawt.JAWT_PlatformInfo;

public class JAWT_X11DrawingSurfaceInfo implements JAWT_PlatformInfo
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] JAWT_X11DrawingSurfaceInfo_size;
    private static final int[] drawable_offset;
    private static final int[] display_offset;
    private static final int[] visualID_offset;
    private static final int[] colormapID_offset;
    private static final int[] depth_offset;
    
    public static int size() {
        return JAWT_X11DrawingSurfaceInfo.JAWT_X11DrawingSurfaceInfo_size[JAWT_X11DrawingSurfaceInfo.mdIdx];
    }
    
    public static JAWT_X11DrawingSurfaceInfo create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static JAWT_X11DrawingSurfaceInfo create(final ByteBuffer byteBuffer) {
        return new JAWT_X11DrawingSurfaceInfo(byteBuffer);
    }
    
    JAWT_X11DrawingSurfaceInfo(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[JAWT_X11DrawingSurfaceInfo.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public JAWT_X11DrawingSurfaceInfo setDrawable(final long n) {
        this.accessor.setLongAt(JAWT_X11DrawingSurfaceInfo.drawable_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getDrawable() {
        return this.accessor.getLongAt(JAWT_X11DrawingSurfaceInfo.drawable_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], this.md.pointerSizeInBytes());
    }
    
    public JAWT_X11DrawingSurfaceInfo setDisplay(final long n) {
        this.accessor.setLongAt(JAWT_X11DrawingSurfaceInfo.display_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getDisplay() {
        return this.accessor.getLongAt(JAWT_X11DrawingSurfaceInfo.display_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], this.md.pointerSizeInBytes());
    }
    
    public JAWT_X11DrawingSurfaceInfo setVisualID(final long n) {
        this.accessor.setLongAt(JAWT_X11DrawingSurfaceInfo.visualID_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getVisualID() {
        return this.accessor.getLongAt(JAWT_X11DrawingSurfaceInfo.visualID_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], this.md.longSizeInBytes());
    }
    
    public JAWT_X11DrawingSurfaceInfo setColormapID(final long n) {
        this.accessor.setLongAt(JAWT_X11DrawingSurfaceInfo.colormapID_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getColormapID() {
        return this.accessor.getLongAt(JAWT_X11DrawingSurfaceInfo.colormapID_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], this.md.pointerSizeInBytes());
    }
    
    public JAWT_X11DrawingSurfaceInfo setDepth(final int n) {
        this.accessor.setIntAt(JAWT_X11DrawingSurfaceInfo.depth_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], n, this.md.intSizeInBytes());
        return this;
    }
    
    public int getDepth() {
        return this.accessor.getIntAt(JAWT_X11DrawingSurfaceInfo.depth_offset[JAWT_X11DrawingSurfaceInfo.mdIdx], this.md.intSizeInBytes());
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        JAWT_X11DrawingSurfaceInfo_size = new int[] { 24, 24, 24, 24, 24, 24, 48, 48 };
        drawable_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        display_offset = new int[] { 4, 4, 4, 4, 4, 4, 8, 8 };
        visualID_offset = new int[] { 8, 8, 8, 8, 8, 8, 16, 16 };
        colormapID_offset = new int[] { 12, 12, 12, 12, 12, 12, 24, 24 };
        depth_offset = new int[] { 16, 16, 16, 16, 16, 16, 32, 32 };
    }
}
