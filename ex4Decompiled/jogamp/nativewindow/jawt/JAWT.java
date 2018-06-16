// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt;

import jogamp.common.os.MachineDataInfoRuntime;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class JAWT
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] JAWT_size;
    private static final int[] version_offset;
    private int jawt_version_cached;
    
    public static int size() {
        return JAWT.JAWT_size[JAWT.mdIdx];
    }
    
    public static JAWT create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static JAWT create(final ByteBuffer byteBuffer) {
        return new JAWT(byteBuffer);
    }
    
    JAWT(final ByteBuffer byteBuffer) {
        this.jawt_version_cached = 0;
        this.md = MachineDataInfo.StaticConfig.values()[JAWT.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public JAWT setVersion(final int n) {
        this.accessor.setIntAt(JAWT.version_offset[JAWT.mdIdx], n);
        return this;
    }
    
    public int getVersion() {
        return this.accessor.getIntAt(JAWT.version_offset[JAWT.mdIdx]);
    }
    
    public JAWT_DrawingSurface GetDrawingSurface(final Object o) {
        final ByteBuffer getDrawingSurface0 = this.GetDrawingSurface0(this.getBuffer(), o);
        if (getDrawingSurface0 == null) {
            return null;
        }
        return JAWT_DrawingSurface.create(Buffers.nativeOrder(getDrawingSurface0));
    }
    
    private native ByteBuffer GetDrawingSurface0(final ByteBuffer p0, final Object p1);
    
    public void FreeDrawingSurface(final JAWT_DrawingSurface jawt_DrawingSurface) {
        this.FreeDrawingSurface0(this.getBuffer(), (jawt_DrawingSurface == null) ? null : jawt_DrawingSurface.getBuffer());
    }
    
    private native void FreeDrawingSurface0(final ByteBuffer p0, final ByteBuffer p1);
    
    public void Lock() {
        this.Lock0(this.getBuffer());
    }
    
    private native void Lock0(final ByteBuffer p0);
    
    public void Unlock() {
        this.Unlock0(this.getBuffer());
    }
    
    private native void Unlock0(final ByteBuffer p0);
    
    public final int getCachedVersion() {
        return this.jawt_version_cached;
    }
    
    protected static boolean getJAWT(final JAWT jawt, final int n) {
        JAWTUtil.initSingleton();
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                jawt.setVersion(n);
                if (JAWTFactory.JAWT_GetAWT(jawt)) {
                    jawt.jawt_version_cached = jawt.getVersion();
                    return new Boolean(true);
                }
                jawt.jawt_version_cached = 0;
                return new Boolean(false);
            }
        });
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        JAWT_size = new int[] { 24, 24, 24, 24, 24, 24, 48, 48 };
        version_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    }
}
