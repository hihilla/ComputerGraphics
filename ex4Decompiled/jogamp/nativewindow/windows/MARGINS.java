// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class MARGINS
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] MARGINS_size;
    private static final int[] cxLeftWidth_offset;
    private static final int[] cxRightWidth_offset;
    private static final int[] cyTopHeight_offset;
    private static final int[] cyBottomHeight_offset;
    
    public static int size() {
        return MARGINS.MARGINS_size[MARGINS.mdIdx];
    }
    
    public static MARGINS create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static MARGINS create(final ByteBuffer byteBuffer) {
        return new MARGINS(byteBuffer);
    }
    
    MARGINS(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[MARGINS.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public MARGINS setCxLeftWidth(final int n) {
        this.accessor.setIntAt(MARGINS.cxLeftWidth_offset[MARGINS.mdIdx], n);
        return this;
    }
    
    public int getCxLeftWidth() {
        return this.accessor.getIntAt(MARGINS.cxLeftWidth_offset[MARGINS.mdIdx]);
    }
    
    public MARGINS setCxRightWidth(final int n) {
        this.accessor.setIntAt(MARGINS.cxRightWidth_offset[MARGINS.mdIdx], n);
        return this;
    }
    
    public int getCxRightWidth() {
        return this.accessor.getIntAt(MARGINS.cxRightWidth_offset[MARGINS.mdIdx]);
    }
    
    public MARGINS setCyTopHeight(final int n) {
        this.accessor.setIntAt(MARGINS.cyTopHeight_offset[MARGINS.mdIdx], n);
        return this;
    }
    
    public int getCyTopHeight() {
        return this.accessor.getIntAt(MARGINS.cyTopHeight_offset[MARGINS.mdIdx]);
    }
    
    public MARGINS setCyBottomHeight(final int n) {
        this.accessor.setIntAt(MARGINS.cyBottomHeight_offset[MARGINS.mdIdx], n);
        return this;
    }
    
    public int getCyBottomHeight() {
        return this.accessor.getIntAt(MARGINS.cyBottomHeight_offset[MARGINS.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        MARGINS_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        cxLeftWidth_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        cxRightWidth_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        cyTopHeight_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        cyBottomHeight_offset = new int[] { 12, 12, 12, 12, 12, 12, 12, 12 };
    }
}
