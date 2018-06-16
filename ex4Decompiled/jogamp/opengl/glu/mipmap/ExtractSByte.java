// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public class ExtractSByte implements ExtractPrimitive
{
    @Override
    public double extract(final boolean b, final ByteBuffer byteBuffer) {
        return byteBuffer.get();
    }
    
    @Override
    public void shove(final double n, final int n2, final ByteBuffer byteBuffer) {
        byteBuffer.position(n2);
        byteBuffer.put((byte)n);
    }
}
