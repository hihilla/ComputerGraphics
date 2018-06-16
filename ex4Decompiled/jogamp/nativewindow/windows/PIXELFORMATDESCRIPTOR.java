// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class PIXELFORMATDESCRIPTOR
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] PIXELFORMATDESCRIPTOR_size;
    private static final int[] nSize_offset;
    private static final int[] nVersion_offset;
    private static final int[] dwFlags_offset;
    private static final int[] iPixelType_offset;
    private static final int[] cColorBits_offset;
    private static final int[] cRedBits_offset;
    private static final int[] cRedShift_offset;
    private static final int[] cGreenBits_offset;
    private static final int[] cGreenShift_offset;
    private static final int[] cBlueBits_offset;
    private static final int[] cBlueShift_offset;
    private static final int[] cAlphaBits_offset;
    private static final int[] cAlphaShift_offset;
    private static final int[] cAccumBits_offset;
    private static final int[] cAccumRedBits_offset;
    private static final int[] cAccumGreenBits_offset;
    private static final int[] cAccumBlueBits_offset;
    private static final int[] cAccumAlphaBits_offset;
    private static final int[] cDepthBits_offset;
    private static final int[] cStencilBits_offset;
    private static final int[] cAuxBuffers_offset;
    private static final int[] iLayerType_offset;
    private static final int[] bReserved_offset;
    private static final int[] dwLayerMask_offset;
    private static final int[] dwVisibleMask_offset;
    private static final int[] dwDamageMask_offset;
    
    public static int size() {
        return PIXELFORMATDESCRIPTOR.PIXELFORMATDESCRIPTOR_size[PIXELFORMATDESCRIPTOR.mdIdx];
    }
    
    public static PIXELFORMATDESCRIPTOR create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static PIXELFORMATDESCRIPTOR create(final ByteBuffer byteBuffer) {
        return new PIXELFORMATDESCRIPTOR(byteBuffer);
    }
    
    PIXELFORMATDESCRIPTOR(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[PIXELFORMATDESCRIPTOR.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public PIXELFORMATDESCRIPTOR setNSize(final short n) {
        this.accessor.setShortAt(PIXELFORMATDESCRIPTOR.nSize_offset[PIXELFORMATDESCRIPTOR.mdIdx], n);
        return this;
    }
    
    public short getNSize() {
        return this.accessor.getShortAt(PIXELFORMATDESCRIPTOR.nSize_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setNVersion(final short n) {
        this.accessor.setShortAt(PIXELFORMATDESCRIPTOR.nVersion_offset[PIXELFORMATDESCRIPTOR.mdIdx], n);
        return this;
    }
    
    public short getNVersion() {
        return this.accessor.getShortAt(PIXELFORMATDESCRIPTOR.nVersion_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setDwFlags(final int n) {
        this.accessor.setIntAt(PIXELFORMATDESCRIPTOR.dwFlags_offset[PIXELFORMATDESCRIPTOR.mdIdx], n);
        return this;
    }
    
    public int getDwFlags() {
        return this.accessor.getIntAt(PIXELFORMATDESCRIPTOR.dwFlags_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setIPixelType(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.iPixelType_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getIPixelType() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.iPixelType_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCColorBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cColorBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCColorBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cColorBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCRedBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cRedBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCRedBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cRedBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCRedShift(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cRedShift_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCRedShift() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cRedShift_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCGreenBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cGreenBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCGreenBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cGreenBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCGreenShift(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cGreenShift_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCGreenShift() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cGreenShift_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCBlueBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cBlueBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCBlueBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cBlueBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCBlueShift(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cBlueShift_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCBlueShift() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cBlueShift_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCAlphaBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cAlphaBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCAlphaBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cAlphaBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCAlphaShift(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cAlphaShift_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCAlphaShift() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cAlphaShift_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCAccumBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cAccumBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCAccumBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cAccumBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCAccumRedBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cAccumRedBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCAccumRedBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cAccumRedBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCAccumGreenBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cAccumGreenBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCAccumGreenBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cAccumGreenBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCAccumBlueBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cAccumBlueBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCAccumBlueBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cAccumBlueBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCAccumAlphaBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cAccumAlphaBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCAccumAlphaBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cAccumAlphaBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCDepthBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cDepthBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCDepthBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cDepthBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCStencilBits(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cStencilBits_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCStencilBits() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cStencilBits_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setCAuxBuffers(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.cAuxBuffers_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getCAuxBuffers() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.cAuxBuffers_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setILayerType(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.iLayerType_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getILayerType() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.iLayerType_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setBReserved(final byte b) {
        this.accessor.setByteAt(PIXELFORMATDESCRIPTOR.bReserved_offset[PIXELFORMATDESCRIPTOR.mdIdx], b);
        return this;
    }
    
    public byte getBReserved() {
        return this.accessor.getByteAt(PIXELFORMATDESCRIPTOR.bReserved_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setDwLayerMask(final int n) {
        this.accessor.setIntAt(PIXELFORMATDESCRIPTOR.dwLayerMask_offset[PIXELFORMATDESCRIPTOR.mdIdx], n);
        return this;
    }
    
    public int getDwLayerMask() {
        return this.accessor.getIntAt(PIXELFORMATDESCRIPTOR.dwLayerMask_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setDwVisibleMask(final int n) {
        this.accessor.setIntAt(PIXELFORMATDESCRIPTOR.dwVisibleMask_offset[PIXELFORMATDESCRIPTOR.mdIdx], n);
        return this;
    }
    
    public int getDwVisibleMask() {
        return this.accessor.getIntAt(PIXELFORMATDESCRIPTOR.dwVisibleMask_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    public PIXELFORMATDESCRIPTOR setDwDamageMask(final int n) {
        this.accessor.setIntAt(PIXELFORMATDESCRIPTOR.dwDamageMask_offset[PIXELFORMATDESCRIPTOR.mdIdx], n);
        return this;
    }
    
    public int getDwDamageMask() {
        return this.accessor.getIntAt(PIXELFORMATDESCRIPTOR.dwDamageMask_offset[PIXELFORMATDESCRIPTOR.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        PIXELFORMATDESCRIPTOR_size = new int[] { 40, 40, 40, 40, 40, 40, 40, 40 };
        nSize_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        nVersion_offset = new int[] { 2, 2, 2, 2, 2, 2, 2, 2 };
        dwFlags_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        iPixelType_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        cColorBits_offset = new int[] { 9, 9, 9, 9, 9, 9, 9, 9 };
        cRedBits_offset = new int[] { 10, 10, 10, 10, 10, 10, 10, 10 };
        cRedShift_offset = new int[] { 11, 11, 11, 11, 11, 11, 11, 11 };
        cGreenBits_offset = new int[] { 12, 12, 12, 12, 12, 12, 12, 12 };
        cGreenShift_offset = new int[] { 13, 13, 13, 13, 13, 13, 13, 13 };
        cBlueBits_offset = new int[] { 14, 14, 14, 14, 14, 14, 14, 14 };
        cBlueShift_offset = new int[] { 15, 15, 15, 15, 15, 15, 15, 15 };
        cAlphaBits_offset = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        cAlphaShift_offset = new int[] { 17, 17, 17, 17, 17, 17, 17, 17 };
        cAccumBits_offset = new int[] { 18, 18, 18, 18, 18, 18, 18, 18 };
        cAccumRedBits_offset = new int[] { 19, 19, 19, 19, 19, 19, 19, 19 };
        cAccumGreenBits_offset = new int[] { 20, 20, 20, 20, 20, 20, 20, 20 };
        cAccumBlueBits_offset = new int[] { 21, 21, 21, 21, 21, 21, 21, 21 };
        cAccumAlphaBits_offset = new int[] { 22, 22, 22, 22, 22, 22, 22, 22 };
        cDepthBits_offset = new int[] { 23, 23, 23, 23, 23, 23, 23, 23 };
        cStencilBits_offset = new int[] { 24, 24, 24, 24, 24, 24, 24, 24 };
        cAuxBuffers_offset = new int[] { 25, 25, 25, 25, 25, 25, 25, 25 };
        iLayerType_offset = new int[] { 26, 26, 26, 26, 26, 26, 26, 26 };
        bReserved_offset = new int[] { 27, 27, 27, 27, 27, 27, 27, 27 };
        dwLayerMask_offset = new int[] { 28, 28, 28, 28, 28, 28, 28, 28 };
        dwVisibleMask_offset = new int[] { 32, 32, 32, 32, 32, 32, 32, 32 };
        dwDamageMask_offset = new int[] { 36, 36, 36, 36, 36, 36, 36, 36 };
    }
}
