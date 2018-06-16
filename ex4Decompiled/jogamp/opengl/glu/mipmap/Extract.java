// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;

public interface Extract
{
    void extract(final boolean p0, final ByteBuffer p1, final float[] p2);
    
    void shove(final float[] p0, final int p1, final ByteBuffer p2);
}
