// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class GLXFBConfigSGIX
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] GLXFBConfigSGIX_size;
    
    public static int size() {
        return GLXFBConfigSGIX.GLXFBConfigSGIX_size[GLXFBConfigSGIX.mdIdx];
    }
    
    public static GLXFBConfigSGIX create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static GLXFBConfigSGIX create(final ByteBuffer byteBuffer) {
        return new GLXFBConfigSGIX(byteBuffer);
    }
    
    GLXFBConfigSGIX(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[GLXFBConfigSGIX.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        GLXFBConfigSGIX_size = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    }
}
