// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11;

import jogamp.common.os.MachineDataInfoRuntime;
import com.jogamp.common.util.Bitfield;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class XVisualInfo
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] XVisualInfo_size;
    private static final int[] visual_offset;
    private static final int[] visualid_offset;
    private static final int[] screen_offset;
    private static final int[] depth_offset;
    private static final int[] c_class_offset;
    private static final int[] red_mask_offset;
    private static final int[] green_mask_offset;
    private static final int[] blue_mask_offset;
    private static final int[] colormap_size_offset;
    private static final int[] bits_per_rgb_offset;
    
    public static int size() {
        return XVisualInfo.XVisualInfo_size[XVisualInfo.mdIdx];
    }
    
    public static XVisualInfo create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static XVisualInfo create(final ByteBuffer byteBuffer) {
        return new XVisualInfo(byteBuffer);
    }
    
    XVisualInfo(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[XVisualInfo.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public XVisualInfo setVisual(final long n) {
        this.accessor.setLongAt(XVisualInfo.visual_offset[XVisualInfo.mdIdx], n, this.md.pointerSizeInBytes());
        return this;
    }
    
    public long getVisual() {
        return this.accessor.getLongAt(XVisualInfo.visual_offset[XVisualInfo.mdIdx], this.md.pointerSizeInBytes());
    }
    
    public XVisualInfo setVisualid(final long n) {
        this.accessor.setLongAt(XVisualInfo.visualid_offset[XVisualInfo.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getVisualid() {
        return this.accessor.getLongAt(XVisualInfo.visualid_offset[XVisualInfo.mdIdx], this.md.longSizeInBytes());
    }
    
    public XVisualInfo setScreen(final int n) {
        this.accessor.setIntAt(XVisualInfo.screen_offset[XVisualInfo.mdIdx], n, this.md.intSizeInBytes());
        return this;
    }
    
    public int getScreen() {
        return this.accessor.getIntAt(XVisualInfo.screen_offset[XVisualInfo.mdIdx], this.md.intSizeInBytes());
    }
    
    public XVisualInfo setDepth(final int n) {
        this.accessor.setIntAt(XVisualInfo.depth_offset[XVisualInfo.mdIdx], n, this.md.intSizeInBytes());
        return this;
    }
    
    public int getDepth() {
        return this.accessor.getIntAt(XVisualInfo.depth_offset[XVisualInfo.mdIdx], this.md.intSizeInBytes());
    }
    
    public XVisualInfo setC_class(final int n) {
        this.accessor.setIntAt(XVisualInfo.c_class_offset[XVisualInfo.mdIdx], n, this.md.intSizeInBytes());
        return this;
    }
    
    public int getC_class() {
        return this.accessor.getIntAt(XVisualInfo.c_class_offset[XVisualInfo.mdIdx], this.md.intSizeInBytes());
    }
    
    public XVisualInfo setRed_mask(final long n) {
        this.accessor.setLongAt(XVisualInfo.red_mask_offset[XVisualInfo.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getRed_mask() {
        return this.accessor.getLongAt(XVisualInfo.red_mask_offset[XVisualInfo.mdIdx], this.md.longSizeInBytes());
    }
    
    public XVisualInfo setGreen_mask(final long n) {
        this.accessor.setLongAt(XVisualInfo.green_mask_offset[XVisualInfo.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getGreen_mask() {
        return this.accessor.getLongAt(XVisualInfo.green_mask_offset[XVisualInfo.mdIdx], this.md.longSizeInBytes());
    }
    
    public XVisualInfo setBlue_mask(final long n) {
        this.accessor.setLongAt(XVisualInfo.blue_mask_offset[XVisualInfo.mdIdx], n, this.md.longSizeInBytes());
        return this;
    }
    
    public long getBlue_mask() {
        return this.accessor.getLongAt(XVisualInfo.blue_mask_offset[XVisualInfo.mdIdx], this.md.longSizeInBytes());
    }
    
    public XVisualInfo setColormap_size(final int n) {
        this.accessor.setIntAt(XVisualInfo.colormap_size_offset[XVisualInfo.mdIdx], n, this.md.intSizeInBytes());
        return this;
    }
    
    public int getColormap_size() {
        return this.accessor.getIntAt(XVisualInfo.colormap_size_offset[XVisualInfo.mdIdx], this.md.intSizeInBytes());
    }
    
    public XVisualInfo setBits_per_rgb(final int n) {
        this.accessor.setIntAt(XVisualInfo.bits_per_rgb_offset[XVisualInfo.mdIdx], n, this.md.intSizeInBytes());
        return this;
    }
    
    public int getBits_per_rgb() {
        return this.accessor.getIntAt(XVisualInfo.bits_per_rgb_offset[XVisualInfo.mdIdx], this.md.intSizeInBytes());
    }
    
    public static XVisualInfo create(final XVisualInfo xVisualInfo) {
        final XVisualInfo create = create(Buffers.newDirectByteBuffer(xVisualInfo.getBuffer().capacity()));
        create.getBuffer().put(xVisualInfo.getBuffer());
        create.getBuffer().rewind();
        xVisualInfo.getBuffer().rewind();
        return create;
    }
    
    @Override
    public String toString() {
        return "XVisualInfo[size " + size() + "/" + this.getBuffer().capacity() + ", visual 0x" + Long.toHexString(this.getVisual()) + ", visual-id  0x" + Long.toHexString(this.getVisualid()) + ", c-class " + this.getC_class() + ", cmap-size " + this.getColormap_size() + ", depth " + this.getDepth() + ", rgb[" + Bitfield.Util.bitCount((int)this.getRed_mask()) + ", " + Bitfield.Util.bitCount((int)this.getRed_mask()) + ", " + Bitfield.Util.bitCount((int)this.getRed_mask()) + " - " + this.getBits_per_rgb() + "]]";
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        XVisualInfo_size = new int[] { 40, 40, 40, 40, 40, 40, 64, 48 };
        visual_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        visualid_offset = new int[] { 4, 4, 4, 4, 4, 4, 8, 8 };
        screen_offset = new int[] { 8, 8, 8, 8, 8, 8, 16, 12 };
        depth_offset = new int[] { 12, 12, 12, 12, 12, 12, 20, 16 };
        c_class_offset = new int[] { 16, 16, 16, 16, 16, 16, 24, 20 };
        red_mask_offset = new int[] { 20, 20, 20, 20, 20, 20, 32, 24 };
        green_mask_offset = new int[] { 24, 24, 24, 24, 24, 24, 40, 28 };
        blue_mask_offset = new int[] { 28, 28, 28, 28, 28, 28, 48, 32 };
        colormap_size_offset = new int[] { 32, 32, 32, 32, 32, 32, 56, 36 };
        bits_per_rgb_offset = new int[] { 36, 36, 36, 36, 36, 36, 60, 40 };
    }
}
