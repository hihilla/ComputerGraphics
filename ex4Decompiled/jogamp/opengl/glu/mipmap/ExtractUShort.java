// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ShortBuffer;
import java.nio.ByteBuffer;

public class ExtractUShort implements ExtractPrimitive
{
    @Override
    public double extract(final boolean b, final ByteBuffer byteBuffer) {
        int n;
        if (b) {
            n = (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
        }
        else {
            n = (0xFFFF & byteBuffer.getShort());
        }
        assert n <= 65535;
        return n;
    }
    
    @Override
    public void shove(final double n, final int n2, final ByteBuffer byteBuffer) {
        assert 0.0 <= n && n < 65536.0;
        final ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.position(n2);
        shortBuffer.put((short)n);
    }
}
