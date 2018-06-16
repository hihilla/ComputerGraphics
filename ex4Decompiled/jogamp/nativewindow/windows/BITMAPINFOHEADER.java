// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class BITMAPINFOHEADER
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] BITMAPINFOHEADER_size;
    private static final int[] biSize_offset;
    private static final int[] biWidth_offset;
    private static final int[] biHeight_offset;
    private static final int[] biPlanes_offset;
    private static final int[] biBitCount_offset;
    private static final int[] biCompression_offset;
    private static final int[] biSizeImage_offset;
    private static final int[] biXPelsPerMeter_offset;
    private static final int[] biYPelsPerMeter_offset;
    private static final int[] biClrUsed_offset;
    private static final int[] biClrImportant_offset;
    
    public static int size() {
        return BITMAPINFOHEADER.BITMAPINFOHEADER_size[BITMAPINFOHEADER.mdIdx];
    }
    
    public static BITMAPINFOHEADER create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static BITMAPINFOHEADER create(final ByteBuffer byteBuffer) {
        return new BITMAPINFOHEADER(byteBuffer);
    }
    
    BITMAPINFOHEADER(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[BITMAPINFOHEADER.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public BITMAPINFOHEADER setBiSize(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biSize_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiSize() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biSize_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiWidth(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biWidth_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiWidth() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biWidth_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiHeight(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biHeight_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiHeight() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biHeight_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiPlanes(final short n) {
        this.accessor.setShortAt(BITMAPINFOHEADER.biPlanes_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public short getBiPlanes() {
        return this.accessor.getShortAt(BITMAPINFOHEADER.biPlanes_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiBitCount(final short n) {
        this.accessor.setShortAt(BITMAPINFOHEADER.biBitCount_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public short getBiBitCount() {
        return this.accessor.getShortAt(BITMAPINFOHEADER.biBitCount_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiCompression(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biCompression_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiCompression() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biCompression_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiSizeImage(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biSizeImage_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiSizeImage() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biSizeImage_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiXPelsPerMeter(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biXPelsPerMeter_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiXPelsPerMeter() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biXPelsPerMeter_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiYPelsPerMeter(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biYPelsPerMeter_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiYPelsPerMeter() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biYPelsPerMeter_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiClrUsed(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biClrUsed_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiClrUsed() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biClrUsed_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    public BITMAPINFOHEADER setBiClrImportant(final int n) {
        this.accessor.setIntAt(BITMAPINFOHEADER.biClrImportant_offset[BITMAPINFOHEADER.mdIdx], n);
        return this;
    }
    
    public int getBiClrImportant() {
        return this.accessor.getIntAt(BITMAPINFOHEADER.biClrImportant_offset[BITMAPINFOHEADER.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        BITMAPINFOHEADER_size = new int[] { 40, 40, 40, 40, 40, 40, 40, 40 };
        biSize_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        biWidth_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        biHeight_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        biPlanes_offset = new int[] { 12, 12, 12, 12, 12, 12, 12, 12 };
        biBitCount_offset = new int[] { 14, 14, 14, 14, 14, 14, 14, 14 };
        biCompression_offset = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        biSizeImage_offset = new int[] { 20, 20, 20, 20, 20, 20, 20, 20 };
        biXPelsPerMeter_offset = new int[] { 24, 24, 24, 24, 24, 24, 24, 24 };
        biYPelsPerMeter_offset = new int[] { 28, 28, 28, 28, 28, 28, 28, 28 };
        biClrUsed_offset = new int[] { 32, 32, 32, 32, 32, 32, 32, 32 };
        biClrImportant_offset = new int[] { 36, 36, 36, 36, 36, 36, 36, 36 };
    }
}
