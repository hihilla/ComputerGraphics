// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class Extract565 implements Extract
{
    @Override
    public void extract(final boolean b, final ByteBuffer byteBuffer, final float[] array) {
        int n;
        if (b) {
            n = (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
        }
        else {
            n = (0xFFFF & byteBuffer.getShort());
        }
        array[0] = ((n & 0xF800) >> 11) / 31.0f;
        array[1] = ((n & 0x7E0) >> 5) / 63.0f;
        array[2] = (n & 0x1F) / 31.0f;
    }
    
    @Override
    public void shove(final float[] array, final int n, final ByteBuffer byteBuffer) {
        assert 0.0f <= array[0] && array[0] <= 1.0f;
        assert 0.0f <= array[1] && array[1] <= 1.0f;
        assert 0.0f <= array[2] && array[2] <= 1.0f;
        byteBuffer.asShortBuffer().put(n, (short)(((int)(array[0] * 31.0f + 0.5f) << 11 & 0xF800) | ((int)(array[1] * 63.0f + 0.5f) << 5 & 0x7E0) | ((int)(array[2] * 31.0f + 0.5f) & 0x1F)));
    }
}
