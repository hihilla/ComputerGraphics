// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class JAWT_Rectangle
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] JAWT_Rectangle_size;
    private static final int[] x_offset;
    private static final int[] y_offset;
    private static final int[] width_offset;
    private static final int[] height_offset;
    
    public static int size() {
        return JAWT_Rectangle.JAWT_Rectangle_size[JAWT_Rectangle.mdIdx];
    }
    
    public static JAWT_Rectangle create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static JAWT_Rectangle create(final ByteBuffer byteBuffer) {
        return new JAWT_Rectangle(byteBuffer);
    }
    
    JAWT_Rectangle(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[JAWT_Rectangle.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public JAWT_Rectangle setX(final int n) {
        this.accessor.setIntAt(JAWT_Rectangle.x_offset[JAWT_Rectangle.mdIdx], n);
        return this;
    }
    
    public int getX() {
        return this.accessor.getIntAt(JAWT_Rectangle.x_offset[JAWT_Rectangle.mdIdx]);
    }
    
    public JAWT_Rectangle setY(final int n) {
        this.accessor.setIntAt(JAWT_Rectangle.y_offset[JAWT_Rectangle.mdIdx], n);
        return this;
    }
    
    public int getY() {
        return this.accessor.getIntAt(JAWT_Rectangle.y_offset[JAWT_Rectangle.mdIdx]);
    }
    
    public JAWT_Rectangle setWidth(final int n) {
        this.accessor.setIntAt(JAWT_Rectangle.width_offset[JAWT_Rectangle.mdIdx], n);
        return this;
    }
    
    public int getWidth() {
        return this.accessor.getIntAt(JAWT_Rectangle.width_offset[JAWT_Rectangle.mdIdx]);
    }
    
    public JAWT_Rectangle setHeight(final int n) {
        this.accessor.setIntAt(JAWT_Rectangle.height_offset[JAWT_Rectangle.mdIdx], n);
        return this;
    }
    
    public int getHeight() {
        return this.accessor.getIntAt(JAWT_Rectangle.height_offset[JAWT_Rectangle.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        JAWT_Rectangle_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        x_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        y_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        width_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        height_offset = new int[] { 12, 12, 12, 12, 12, 12, 12, 12 };
    }
}
