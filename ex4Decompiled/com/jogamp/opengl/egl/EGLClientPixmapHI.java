// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.egl;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class EGLClientPixmapHI
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] EGLClientPixmapHI_size;
    private static final int[] iWidth_offset;
    private static final int[] iHeight_offset;
    private static final int[] iStride_offset;
    
    public static int size() {
        return EGLClientPixmapHI.EGLClientPixmapHI_size[EGLClientPixmapHI.mdIdx];
    }
    
    public static EGLClientPixmapHI create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static EGLClientPixmapHI create(final ByteBuffer byteBuffer) {
        return new EGLClientPixmapHI(byteBuffer);
    }
    
    EGLClientPixmapHI(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[EGLClientPixmapHI.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public EGLClientPixmapHI setIWidth(final int n) {
        this.accessor.setIntAt(EGLClientPixmapHI.iWidth_offset[EGLClientPixmapHI.mdIdx], n);
        return this;
    }
    
    public int getIWidth() {
        return this.accessor.getIntAt(EGLClientPixmapHI.iWidth_offset[EGLClientPixmapHI.mdIdx]);
    }
    
    public EGLClientPixmapHI setIHeight(final int n) {
        this.accessor.setIntAt(EGLClientPixmapHI.iHeight_offset[EGLClientPixmapHI.mdIdx], n);
        return this;
    }
    
    public int getIHeight() {
        return this.accessor.getIntAt(EGLClientPixmapHI.iHeight_offset[EGLClientPixmapHI.mdIdx]);
    }
    
    public EGLClientPixmapHI setIStride(final int n) {
        this.accessor.setIntAt(EGLClientPixmapHI.iStride_offset[EGLClientPixmapHI.mdIdx], n);
        return this;
    }
    
    public int getIStride() {
        return this.accessor.getIntAt(EGLClientPixmapHI.iStride_offset[EGLClientPixmapHI.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        EGLClientPixmapHI_size = new int[] { 16, 16, 16, 16, 16, 16, 24, 24 };
        iWidth_offset = new int[] { 4, 4, 4, 4, 4, 4, 8, 8 };
        iHeight_offset = new int[] { 8, 8, 8, 8, 8, 8, 12, 12 };
        iStride_offset = new int[] { 12, 12, 12, 12, 12, 12, 16, 16 };
    }
}
