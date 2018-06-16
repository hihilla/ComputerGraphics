// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class Extract2101010rev implements Extract
{
    @Override
    public void extract(final boolean b, final ByteBuffer byteBuffer, final float[] array) {
        long n;
        if (b) {
            n = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
        }
        else {
            n = (-1 & byteBuffer.getInt());
        }
        array[0] = (n & 0x3FFL) / 1023.0f;
        array[1] = ((n & 0xFFC00L) >> 10) / 1023.0f;
        array[2] = ((n & 0x3FF00000L) >> 20) / 1023.0f;
        array[3] = ((n & 0xFFFFFFFFC0000000L) >> 30) / 3.0f;
    }
    
    @Override
    public void shove(final float[] array, final int n, final ByteBuffer byteBuffer) {
        assert 0.0f <= array[0] && array[0] <= 1.0f;
        assert 0.0f <= array[1] && array[1] <= 1.0f;
        assert 0.0f <= array[2] && array[2] <= 1.0f;
        assert 0.0f <= array[3] && array[3] <= 1.0f;
        byteBuffer.asIntBuffer().put(n, ((int)(array[0] * 1023.0f + 0.5f) & 0x3FF) | ((int)(array[1] * 1023.0f + 0.5f) << 10 & 0xFFC00) | ((int)(array[2] * 1023.0f + 0.5f) << 20 & 0x3FF00000) | ((int)(array[3] * 3.0f + 0.5f) << 30 & 0xC0000000));
    }
}
