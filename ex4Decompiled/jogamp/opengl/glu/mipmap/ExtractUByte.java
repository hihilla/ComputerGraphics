// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class ExtractUByte implements ExtractPrimitive
{
    @Override
    public double extract(final boolean b, final ByteBuffer byteBuffer) {
        final int n = 0xFF & byteBuffer.get();
        assert n <= 255;
        return n;
    }
    
    @Override
    public void shove(final double n, final int n2, final ByteBuffer byteBuffer) {
        assert 0.0 <= n && n < 256.0;
        byteBuffer.position(n2);
        byteBuffer.put((byte)n);
    }
}
