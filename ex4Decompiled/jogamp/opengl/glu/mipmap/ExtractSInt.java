// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public class ExtractSInt implements ExtractPrimitive
{
    @Override
    public double extract(final boolean b, final ByteBuffer byteBuffer) {
        int n;
        if (b) {
            n = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
        }
        else {
            n = byteBuffer.getInt();
        }
        assert n <= Integer.MAX_VALUE;
        return n;
    }
    
    @Override
    public void shove(final double n, final int n2, final ByteBuffer byteBuffer) {
        assert 0.0 <= n && n < 2.147483647E9;
        final IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.position(n2);
        intBuffer.put((int)n);
    }
}
