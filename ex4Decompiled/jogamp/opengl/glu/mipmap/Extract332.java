// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class Extract332 implements Extract
{
    @Override
    public void extract(final boolean b, final ByteBuffer byteBuffer, final float[] array) {
        final byte value = byteBuffer.get();
        array[0] = ((value & 0xE0) >> 5) / 7.0f;
        array[1] = ((value & 0x1C) >> 2) / 7.0f;
        array[2] = (value & 0x3) / 3.0f;
    }
    
    @Override
    public void shove(final float[] array, final int n, final ByteBuffer byteBuffer) {
        assert 0.0f <= array[0] && array[0] <= 1.0f;
        assert 0.0f <= array[1] && array[1] <= 1.0f;
        assert 0.0f <= array[2] && array[2] <= 1.0f;
        byteBuffer.put(n, (byte)((byte)((byte)((int)(array[0] * 7.0f + 0.5f) << 5 & 0xE0) | (byte)((int)(array[1] * 7.0f + 0.5f) << 2 & 0x1C)) | (byte)((int)(array[2] * 3.0f + 0.5f) & 0x3)));
    }
}
