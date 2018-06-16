// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.Buffer;

public interface GLES3 extends GLES2, GL4ES3
{
    void glVertexAttribPointer(final int p0, final int p1, final int p2, final boolean p3, final int p4, final Buffer p5);
    
    void glDrawElementsInstanced(final int p0, final int p1, final int p2, final Buffer p3, final int p4);
    
    void glDrawRangeElements(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glVertexAttribIPointer(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
}
