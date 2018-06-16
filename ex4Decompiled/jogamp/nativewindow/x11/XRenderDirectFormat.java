// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class XRenderDirectFormat
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] XRenderDirectFormat_size;
    private static final int[] red_offset;
    private static final int[] redMask_offset;
    private static final int[] green_offset;
    private static final int[] greenMask_offset;
    private static final int[] blue_offset;
    private static final int[] blueMask_offset;
    private static final int[] alpha_offset;
    private static final int[] alphaMask_offset;
    
    public static int size() {
        return XRenderDirectFormat.XRenderDirectFormat_size[XRenderDirectFormat.mdIdx];
    }
    
    public static XRenderDirectFormat create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static XRenderDirectFormat create(final ByteBuffer byteBuffer) {
        return new XRenderDirectFormat(byteBuffer);
    }
    
    XRenderDirectFormat(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[XRenderDirectFormat.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public XRenderDirectFormat setRed(final short n) {
        this.accessor.setShortAt(XRenderDirectFormat.red_offset[XRenderDirectFormat.mdIdx], n);
        return this;
    }
    
    public short getRed() {
        return this.accessor.getShortAt(XRenderDirectFormat.red_offset[XRenderDirectFormat.mdIdx]);
    }
    
    public XRenderDirectFormat setRedMask(final short n) {
        this.accessor.setShortAt(XRenderDirectFormat.redMask_offset[XRenderDirectFormat.mdIdx], n);
        return this;
    }
    
    public short getRedMask() {
        return this.accessor.getShortAt(XRenderDirectFormat.redMask_offset[XRenderDirectFormat.mdIdx]);
    }
    
    public XRenderDirectFormat setGreen(final short n) {
        this.accessor.setShortAt(XRenderDirectFormat.green_offset[XRenderDirectFormat.mdIdx], n);
        return this;
    }
    
    public short getGreen() {
        return this.accessor.getShortAt(XRenderDirectFormat.green_offset[XRenderDirectFormat.mdIdx]);
    }
    
    public XRenderDirectFormat setGreenMask(final short n) {
        this.accessor.setShortAt(XRenderDirectFormat.greenMask_offset[XRenderDirectFormat.mdIdx], n);
        return this;
    }
    
    public short getGreenMask() {
        return this.accessor.getShortAt(XRenderDirectFormat.greenMask_offset[XRenderDirectFormat.mdIdx]);
    }
    
    public XRenderDirectFormat setBlue(final short n) {
        this.accessor.setShortAt(XRenderDirectFormat.blue_offset[XRenderDirectFormat.mdIdx], n);
        return this;
    }
    
    public short getBlue() {
        return this.accessor.getShortAt(XRenderDirectFormat.blue_offset[XRenderDirectFormat.mdIdx]);
    }
    
    public XRenderDirectFormat setBlueMask(final short n) {
        this.accessor.setShortAt(XRenderDirectFormat.blueMask_offset[XRenderDirectFormat.mdIdx], n);
        return this;
    }
    
    public short getBlueMask() {
        return this.accessor.getShortAt(XRenderDirectFormat.blueMask_offset[XRenderDirectFormat.mdIdx]);
    }
    
    public XRenderDirectFormat setAlpha(final short n) {
        this.accessor.setShortAt(XRenderDirectFormat.alpha_offset[XRenderDirectFormat.mdIdx], n);
        return this;
    }
    
    public short getAlpha() {
        return this.accessor.getShortAt(XRenderDirectFormat.alpha_offset[XRenderDirectFormat.mdIdx]);
    }
    
    public XRenderDirectFormat setAlphaMask(final short n) {
        this.accessor.setShortAt(XRenderDirectFormat.alphaMask_offset[XRenderDirectFormat.mdIdx], n);
        return this;
    }
    
    public short getAlphaMask() {
        return this.accessor.getShortAt(XRenderDirectFormat.alphaMask_offset[XRenderDirectFormat.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        XRenderDirectFormat_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        red_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        redMask_offset = new int[] { 2, 2, 2, 2, 2, 2, 2, 2 };
        green_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        greenMask_offset = new int[] { 6, 6, 6, 6, 6, 6, 6, 6 };
        blue_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        blueMask_offset = new int[] { 10, 10, 10, 10, 10, 10, 10, 10 };
        alpha_offset = new int[] { 12, 12, 12, 12, 12, 12, 12, 12 };
        alphaMask_offset = new int[] { 14, 14, 14, 14, 14, 14, 14, 14 };
    }
}
