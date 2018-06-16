// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class RECT
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] RECT_size;
    private static final int[] left_offset;
    private static final int[] top_offset;
    private static final int[] right_offset;
    private static final int[] bottom_offset;
    
    public static int size() {
        return RECT.RECT_size[RECT.mdIdx];
    }
    
    public static RECT create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static RECT create(final ByteBuffer byteBuffer) {
        return new RECT(byteBuffer);
    }
    
    RECT(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[RECT.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public RECT setLeft(final int n) {
        this.accessor.setIntAt(RECT.left_offset[RECT.mdIdx], n);
        return this;
    }
    
    public int getLeft() {
        return this.accessor.getIntAt(RECT.left_offset[RECT.mdIdx]);
    }
    
    public RECT setTop(final int n) {
        this.accessor.setIntAt(RECT.top_offset[RECT.mdIdx], n);
        return this;
    }
    
    public int getTop() {
        return this.accessor.getIntAt(RECT.top_offset[RECT.mdIdx]);
    }
    
    public RECT setRight(final int n) {
        this.accessor.setIntAt(RECT.right_offset[RECT.mdIdx], n);
        return this;
    }
    
    public int getRight() {
        return this.accessor.getIntAt(RECT.right_offset[RECT.mdIdx]);
    }
    
    public RECT setBottom(final int n) {
        this.accessor.setIntAt(RECT.bottom_offset[RECT.mdIdx], n);
        return this;
    }
    
    public int getBottom() {
        return this.accessor.getIntAt(RECT.bottom_offset[RECT.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        RECT_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        left_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        top_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        right_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        bottom_offset = new int[] { 12, 12, 12, 12, 12, 12, 12, 12 };
    }
}
