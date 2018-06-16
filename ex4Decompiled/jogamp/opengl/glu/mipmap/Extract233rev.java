// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class Extract233rev implements Extract
{
    @Override
    public void extract(final boolean b, final ByteBuffer byteBuffer, final float[] array) {
        final byte value = byteBuffer.get();
        array[0] = (value & 0x7) / 7.0f;
        array[1] = ((value & 0x38) >> 3) / 7.0f;
        array[2] = ((value & 0xC0) >> 6) / 3.0f;
    }
    
    @Override
    public void shove(final float[] array, final int n, final ByteBuffer byteBuffer) {
        assert 0.0f <= array[0] && array[0] <= 1.0f;
        assert 0.0f <= array[1] && array[1] <= 1.0f;
        assert 0.0f <= array[2] && array[2] <= 1.0f;
        final byte b = (byte)((byte)((byte)((int)(array[0] * 7.0f + 0.5f) & 0x7) | (byte)((int)(array[1] * 7.0f + 0.5f) << 3 & 0x38)) | (byte)((int)(array[2] * 3.0f + 0.5f) << 6 & 0xC0));
        byteBuffer.position(n);
        byteBuffer.put(b);
    }
}
