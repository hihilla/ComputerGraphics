// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class Extract8888rev implements Extract
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
        array[0] = (n & 0xFFL) / 255.0f;
        array[1] = ((n & 0xFF00L) >> 8) / 255.0f;
        array[2] = ((n & 0xFF0000L) >> 16) / 255.0f;
        array[3] = ((n & 0xFFFFFFFFFF000000L) >> 24) / 255.0f;
    }
    
    @Override
    public void shove(final float[] array, final int n, final ByteBuffer byteBuffer) {
        assert 0.0f <= array[0] && array[0] <= 1.0f;
        assert 0.0f <= array[1] && array[1] <= 1.0f;
        assert 0.0f <= array[2] && array[2] <= 1.0f;
        assert 0.0f <= array[3] && array[3] <= 1.0f;
        byteBuffer.asIntBuffer().put(n, ((int)(array[0] * 255.0f + 0.5f) & 0xFF) | ((int)(array[1] * 255.0f + 0.5f) << 8 & 0xFF00) | ((int)(array[2] * 255.0f + 0.5f) << 16 & 0xFF0000) | ((int)(array[3] * 255.0f + 0.5f) << 24 & 0xFF000000));
    }
}
