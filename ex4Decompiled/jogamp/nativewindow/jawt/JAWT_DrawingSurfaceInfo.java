// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt;

import jogamp.common.os.MachineDataInfoRuntime;
import com.jogamp.common.os.Platform;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import java.lang.reflect.Method;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class JAWT_DrawingSurfaceInfo
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] JAWT_DrawingSurfaceInfo_size;
    private static final int[] ds_offset;
    private static final int[] bounds_offset;
    private static final int[] bounds_size;
    private static final int[] clipSize_offset;
    private static final int[] clip_offset;
    private static Method platformInfoFactoryMethod;
    
    public static int size() {
        return JAWT_DrawingSurfaceInfo.JAWT_DrawingSurfaceInfo_size[JAWT_DrawingSurfaceInfo.mdIdx];
    }
    
    public static JAWT_DrawingSurfaceInfo create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static JAWT_DrawingSurfaceInfo create(final ByteBuffer byteBuffer) {
        return new JAWT_DrawingSurfaceInfo(byteBuffer);
    }
    
    JAWT_DrawingSurfaceInfo(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[JAWT_DrawingSurfaceInfo.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public JAWT_Rectangle getBounds() {
        return JAWT_Rectangle.create(this.accessor.slice(JAWT_DrawingSurfaceInfo.bounds_offset[JAWT_DrawingSurfaceInfo.mdIdx], JAWT_DrawingSurfaceInfo.bounds_size[JAWT_DrawingSurfaceInfo.mdIdx]));
    }
    
    public JAWT_DrawingSurfaceInfo setClipSize(final int n) {
        this.accessor.setIntAt(JAWT_DrawingSurfaceInfo.clipSize_offset[JAWT_DrawingSurfaceInfo.mdIdx], n);
        return this;
    }
    
    public int getClipSize() {
        return this.accessor.getIntAt(JAWT_DrawingSurfaceInfo.clipSize_offset[JAWT_DrawingSurfaceInfo.mdIdx]);
    }
    
    public JAWT_PlatformInfo platformInfo(final JAWT jawt) {
        return newPlatformInfo(jawt, this.platformInfo0(this.getBuffer()));
    }
    
    private native ByteBuffer platformInfo0(final Buffer p0);
    
    private static JAWT_PlatformInfo newPlatformInfo(final JAWT jawt, final ByteBuffer byteBuffer) {
        if (JAWT_DrawingSurfaceInfo.platformInfoFactoryMethod == null) {
            try {
                Class<?> clazz;
                if (Platform.OS_TYPE == Platform.OSType.WINDOWS) {
                    clazz = Class.forName("jogamp.nativewindow.jawt.windows.JAWT_Win32DrawingSurfaceInfo");
                }
                else if (Platform.OS_TYPE == Platform.OSType.MACOS) {
                    if (0x0 != (jawt.getCachedVersion() & Integer.MIN_VALUE)) {
                        clazz = Class.forName("jogamp.nativewindow.jawt.macosx.JAWT_SurfaceLayers");
                    }
                    else {
                        clazz = Class.forName("jogamp.nativewindow.jawt.macosx.JAWT_MacOSXDrawingSurfaceInfo");
                    }
                }
                else {
                    clazz = Class.forName("jogamp.nativewindow.jawt.x11.JAWT_X11DrawingSurfaceInfo");
                }
                JAWT_DrawingSurfaceInfo.platformInfoFactoryMethod = clazz.getMethod("create", ByteBuffer.class);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            return (JAWT_PlatformInfo)JAWT_DrawingSurfaceInfo.platformInfoFactoryMethod.invoke(null, byteBuffer);
        }
        catch (Exception ex2) {
            throw new RuntimeException(ex2);
        }
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        JAWT_DrawingSurfaceInfo_size = new int[] { 32, 32, 32, 32, 32, 32, 48, 48 };
        ds_offset = new int[] { 4, 4, 4, 4, 4, 4, 8, 8 };
        bounds_offset = new int[] { 8, 8, 8, 8, 8, 8, 16, 16 };
        bounds_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        clipSize_offset = new int[] { 24, 24, 24, 24, 24, 24, 32, 32 };
        clip_offset = new int[] { 28, 28, 28, 28, 28, 28, 40, 40 };
    }
}
