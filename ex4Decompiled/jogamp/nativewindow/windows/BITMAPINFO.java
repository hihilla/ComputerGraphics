// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class BITMAPINFO
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] BITMAPINFO_size;
    private static final int[] bmiHeader_offset;
    private static final int[] bmiHeader_size;
    private static final int[] bmiColors_offset;
    private static final int[] bmiColors_size;
    
    public static int size() {
        return BITMAPINFO.BITMAPINFO_size[BITMAPINFO.mdIdx];
    }
    
    public static BITMAPINFO create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static BITMAPINFO create(final ByteBuffer byteBuffer) {
        return new BITMAPINFO(byteBuffer);
    }
    
    BITMAPINFO(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[BITMAPINFO.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public BITMAPINFOHEADER getBmiHeader() {
        return BITMAPINFOHEADER.create(this.accessor.slice(BITMAPINFO.bmiHeader_offset[BITMAPINFO.mdIdx], BITMAPINFO.bmiHeader_size[BITMAPINFO.mdIdx]));
    }
    
    public BITMAPINFO setBmiColors(final RGBQUAD rgbquad) {
        final int size = RGBQUAD.size();
        final ByteBuffer buffer = this.getBuffer();
        if (size > BITMAPINFO.bmiColors_size[BITMAPINFO.mdIdx]) {
            throw new IndexOutOfBoundsException("elemSize " + size + " > size " + BITMAPINFO.bmiColors_size[BITMAPINFO.mdIdx]);
        }
        int n = BITMAPINFO.bmiColors_offset[BITMAPINFO.mdIdx];
        final int n2 = n + size;
        if (n2 > buffer.limit()) {
            throw new IndexOutOfBoundsException("bLimes " + n2 + " > buffer.limit " + buffer.limit() + ", elemOff " + n + ", elemSize " + size);
        }
        final ByteBuffer buffer2 = rgbquad.getBuffer();
        for (int i = 0; i < size; ++i) {
            if (n >= n2) {
                throw new IndexOutOfBoundsException("elem-byte[0][" + i + "]: bOffset " + n + " >= bLimes " + n2 + ", elemSize " + size);
            }
            buffer.put(n++, buffer2.get(i));
        }
        return this;
    }
    
    public RGBQUAD getBmiColors() {
        return RGBQUAD.create(this.accessor.slice(BITMAPINFO.bmiColors_offset[BITMAPINFO.mdIdx], RGBQUAD.size()));
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        BITMAPINFO_size = new int[] { 44, 44, 44, 44, 44, 44, 44, 44 };
        bmiHeader_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        bmiHeader_size = new int[] { 40, 40, 40, 40, 40, 40, 40, 40 };
        bmiColors_offset = new int[] { 40, 40, 40, 40, 40, 40, 40, 40 };
        bmiColors_size = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
    }
}
