// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class XRenderPictFormat
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] XRenderPictFormat_size;
    private static final int[] id_offset;
    private static final int[] type_offset;
    private static final int[] depth_offset;
    private static final int[] direct_offset;
    private static final int[] direct_size;
    private static final int[] colormap_offset;
    
    public static int size() {
        return XRenderPictFormat.XRenderPictFormat_size[XRenderPictFormat.mdIdx];
    }
    
    public static XRenderPictFormat create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static XRenderPictFormat create(final ByteBuffer byteBuffer) {
        return new XRenderPictFormat(byteBuffer);
    }
    
    XRenderPictFormat(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[XRenderPictFormat.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public XRenderPictFormat setId(final long n) {
        this.accessor.setLongAt(XRenderPictFormat.id_offset[XRenderPictFormat.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getId() {
        return this.accessor.getLongAt(XRenderPictFormat.id_offset[XRenderPictFormat.mdIdx], this.md.pointerSizeInBytes());
    }
    
    public XRenderPictFormat setType(final int n) {
        this.accessor.setIntAt(XRenderPictFormat.type_offset[XRenderPictFormat.mdIdx], n, this.md.intSizeInBytes());
        return this;
    }
    
    public int getType() {
        return this.accessor.getIntAt(XRenderPictFormat.type_offset[XRenderPictFormat.mdIdx], this.md.intSizeInBytes());
    }
    
    public XRenderPictFormat setDepth(final int n) {
        this.accessor.setIntAt(XRenderPictFormat.depth_offset[XRenderPictFormat.mdIdx], n, this.md.intSizeInBytes());
        return this;
    }
    
    public int getDepth() {
        return this.accessor.getIntAt(XRenderPictFormat.depth_offset[XRenderPictFormat.mdIdx], this.md.intSizeInBytes());
    }
    
    public XRenderDirectFormat getDirect() {
        return XRenderDirectFormat.create(this.accessor.slice(XRenderPictFormat.direct_offset[XRenderPictFormat.mdIdx], XRenderPictFormat.direct_size[XRenderPictFormat.mdIdx]));
    }
    
    public XRenderPictFormat setColormap(final long n) {
        this.accessor.setLongAt(XRenderPictFormat.colormap_offset[XRenderPictFormat.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getColormap() {
        return this.accessor.getLongAt(XRenderPictFormat.colormap_offset[XRenderPictFormat.mdIdx], this.md.pointerSizeInBytes());
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        XRenderPictFormat_size = new int[] { 32, 32, 32, 32, 32, 32, 40, 40 };
        id_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        type_offset = new int[] { 4, 4, 4, 4, 4, 4, 8, 8 };
        depth_offset = new int[] { 8, 8, 8, 8, 8, 8, 12, 12 };
        direct_offset = new int[] { 12, 12, 12, 12, 12, 12, 16, 16 };
        direct_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        colormap_offset = new int[] { 28, 28, 28, 28, 28, 28, 32, 32 };
    }
}
