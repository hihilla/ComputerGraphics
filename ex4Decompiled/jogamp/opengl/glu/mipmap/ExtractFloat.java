// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class ExtractFloat implements ExtractPrimitive
{
    @Override
    public double extract(final boolean b, final ByteBuffer byteBuffer) {
        float n;
        if (b) {
            n = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
        }
        else {
            n = byteBuffer.getInt();
        }
        assert n <= 1.0f;
        return n;
    }
    
    @Override
    public void shove(final double n, final int n2, final ByteBuffer byteBuffer) {
        assert 0.0 <= n && n < 1.0;
        byteBuffer.asFloatBuffer().put(n2, (float)n);
    }
}
