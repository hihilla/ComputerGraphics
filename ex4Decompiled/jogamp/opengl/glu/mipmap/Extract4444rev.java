// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class Extract4444rev implements Extract
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
        array[0] = (n & 0xF) / 15.0f;
        array[1] = ((n & 0xF0) >> 4) / 15.0f;
        array[2] = ((n & 0xF00) >> 8) / 15.0f;
        array[3] = ((n & 0xF000) >> 12) / 15.0f;
    }
    
    @Override
    public void shove(final float[] array, final int n, final ByteBuffer byteBuffer) {
        assert 0.0f <= array[0] && array[0] <= 1.0f;
        assert 0.0f <= array[1] && array[1] <= 1.0f;
        assert 0.0f <= array[2] && array[2] <= 1.0f;
        assert 0.0f <= array[3] && array[3] <= 1.0f;
        byteBuffer.asShortBuffer().put(n, (short)(((int)(array[0] * 15.0f + 0.5f) & 0xF) | ((int)(array[1] * 15.0f + 0.5f) << 4 & 0xF0) | ((int)(array[2] * 15.0f + 0.5f) << 8 & 0xF00) | ((int)(array[3] * 15.0f + 0.5f) << 12 & 0xF000)));
    }
}
