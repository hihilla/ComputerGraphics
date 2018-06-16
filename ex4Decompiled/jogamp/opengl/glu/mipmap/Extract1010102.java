// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class Extract1010102 implements Extract
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
        array[0] = ((n & 0xFFFFFFFFFFC00000L) >> 22) / 1023.0f;
        array[1] = ((n & 0x3FF000L) >> 12) / 1023.0f;
        array[2] = ((n & 0xFFCL) >> 2) / 1023.0f;
        array[3] = (n & 0x3L) / 3.0f;
    }
    
    @Override
    public void shove(final float[] array, final int n, final ByteBuffer byteBuffer) {
        assert 0.0f <= array[0] && array[0] <= 1.0f;
        assert 0.0f <= array[1] && array[1] <= 1.0f;
        assert 0.0f <= array[2] && array[2] <= 1.0f;
        assert 0.0f <= array[3] && array[3] <= 1.0f;
        byteBuffer.asIntBuffer().put(n, ((int)(array[0] * 1023.0f + 0.5f) << 22 & 0xFFC00000) | ((int)(array[1] * 1023.0f + 0.5f) << 12 & 0x3FF000) | ((int)(array[2] * 1023.0f + 0.5f) << 2 & 0xFFC) | ((int)(array[3] * 3.0f + 0.5f) & 0x3));
    }
}
