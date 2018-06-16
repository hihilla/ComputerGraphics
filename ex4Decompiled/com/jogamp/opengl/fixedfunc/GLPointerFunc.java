// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.fixedfunc;

import java.nio.Buffer;
import com.jogamp.opengl.GLArrayData;

public interface GLPointerFunc
{
    public static final int GL_VERTEX_ARRAY = 32884;
    public static final int GL_NORMAL_ARRAY = 32885;
    public static final int GL_COLOR_ARRAY = 32886;
    public static final int GL_TEXTURE_COORD_ARRAY = 32888;
    
    void glEnableClientState(final int p0);
    
    void glDisableClientState(final int p0);
    
    void glVertexPointer(final GLArrayData p0);
    
    void glVertexPointer(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glVertexPointer(final int p0, final int p1, final int p2, final long p3);
    
    void glColorPointer(final GLArrayData p0);
    
    void glColorPointer(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glColorPointer(final int p0, final int p1, final int p2, final long p3);
    
    void glColor4f(final float p0, final float p1, final float p2, final float p3);
    
    void glNormalPointer(final GLArrayData p0);
    
    void glNormalPointer(final int p0, final int p1, final Buffer p2);
    
    void glNormalPointer(final int p0, final int p1, final long p2);
    
    void glTexCoordPointer(final GLArrayData p0);
    
    void glTexCoordPointer(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glTexCoordPointer(final int p0, final int p1, final int p2, final long p3);
}
