// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public class ExtractUInt implements ExtractPrimitive
{
    @Override
    public double extract(final boolean b, final ByteBuffer byteBuffer) {
        long n;
        if (b) {
            n = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
        }
        else {
            n = (-1 & byteBuffer.getInt());
        }
        assert n <= -1L;
        return n;
    }
    
    @Override
    public void shove(final double n, final int n2, final ByteBuffer byteBuffer) {
        assert 0.0 <= n && n < -1.0;
        final IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.position(n2);
        intBuffer.put((int)n);
    }
}
