// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class JAWT_DrawingSurface
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] JAWT_DrawingSurface_size;
    private static final int[] env_offset;
    
    public static int size() {
        return JAWT_DrawingSurface.JAWT_DrawingSurface_size[JAWT_DrawingSurface.mdIdx];
    }
    
    public static JAWT_DrawingSurface create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static JAWT_DrawingSurface create(final ByteBuffer byteBuffer) {
        return new JAWT_DrawingSurface(byteBuffer);
    }
    
    JAWT_DrawingSurface(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[JAWT_DrawingSurface.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public int Lock() {
        return this.Lock0(this.getBuffer());
    }
    
    private native int Lock0(final ByteBuffer p0);
    
    public JAWT_DrawingSurfaceInfo GetDrawingSurfaceInfo() {
        final ByteBuffer getDrawingSurfaceInfo0 = this.GetDrawingSurfaceInfo0(this.getBuffer());
        if (getDrawingSurfaceInfo0 == null) {
            return null;
        }
        return JAWT_DrawingSurfaceInfo.create(Buffers.nativeOrder(getDrawingSurfaceInfo0));
    }
    
    private native ByteBuffer GetDrawingSurfaceInfo0(final ByteBuffer p0);
    
    public void FreeDrawingSurfaceInfo(final JAWT_DrawingSurfaceInfo jawt_DrawingSurfaceInfo) {
        this.FreeDrawingSurfaceInfo0(this.getBuffer(), (jawt_DrawingSurfaceInfo == null) ? null : jawt_DrawingSurfaceInfo.getBuffer());
    }
    
    private native void FreeDrawingSurfaceInfo0(final ByteBuffer p0, final ByteBuffer p1);
    
    public void Unlock() {
        this.Unlock0(this.getBuffer());
    }
    
    private native void Unlock0(final ByteBuffer p0);
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        JAWT_DrawingSurface_size = new int[] { 24, 24, 24, 24, 24, 24, 48, 48 };
        env_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    }
}
