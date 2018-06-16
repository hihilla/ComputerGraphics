// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class RGBQUAD
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] RGBQUAD_size;
    private static final int[] rgbBlue_offset;
    private static final int[] rgbGreen_offset;
    private static final int[] rgbRed_offset;
    private static final int[] rgbReserved_offset;
    
    public static int size() {
        return RGBQUAD.RGBQUAD_size[RGBQUAD.mdIdx];
    }
    
    public static RGBQUAD create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static RGBQUAD create(final ByteBuffer byteBuffer) {
        return new RGBQUAD(byteBuffer);
    }
    
    RGBQUAD(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[RGBQUAD.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public RGBQUAD setRgbBlue(final byte b) {
        this.accessor.setByteAt(RGBQUAD.rgbBlue_offset[RGBQUAD.mdIdx], b);
        return this;
    }
    
    public byte getRgbBlue() {
        return this.accessor.getByteAt(RGBQUAD.rgbBlue_offset[RGBQUAD.mdIdx]);
    }
    
    public RGBQUAD setRgbGreen(final byte b) {
        this.accessor.setByteAt(RGBQUAD.rgbGreen_offset[RGBQUAD.mdIdx], b);
        return this;
    }
    
    public byte getRgbGreen() {
        return this.accessor.getByteAt(RGBQUAD.rgbGreen_offset[RGBQUAD.mdIdx]);
    }
    
    public RGBQUAD setRgbRed(final byte b) {
        this.accessor.setByteAt(RGBQUAD.rgbRed_offset[RGBQUAD.mdIdx], b);
        return this;
    }
    
    public byte getRgbRed() {
        return this.accessor.getByteAt(RGBQUAD.rgbRed_offset[RGBQUAD.mdIdx]);
    }
    
    public RGBQUAD setRgbReserved(final byte b) {
        this.accessor.setByteAt(RGBQUAD.rgbReserved_offset[RGBQUAD.mdIdx], b);
        return this;
    }
    
    public byte getRgbReserved() {
        return this.accessor.getByteAt(RGBQUAD.rgbReserved_offset[RGBQUAD.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        RGBQUAD_size = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        rgbBlue_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        rgbGreen_offset = new int[] { 1, 1, 1, 1, 1, 1, 1, 1 };
        rgbRed_offset = new int[] { 2, 2, 2, 2, 2, 2, 2, 2 };
        rgbReserved_offset = new int[] { 3, 3, 3, 3, 3, 3, 3, 3 };
    }
}
