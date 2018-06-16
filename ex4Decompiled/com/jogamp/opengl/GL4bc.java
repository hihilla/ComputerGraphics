// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.Buffer;

public interface GL4bc extends GL3bc, GL4
{
    void glDrawArraysIndirect(final int p0, final Buffer p1);
    
    void glDrawElementsIndirect(final int p0, final int p1, final Buffer p2);
    
    void glMultiDrawArraysIndirect(final int p0, final Buffer p1, final int p2, final int p3);
    
    void glDrawElementsInstancedBaseInstance(final int p0, final int p1, final int p2, final Buffer p3, final int p4, final int p5);
    
    void glDrawElementsInstancedBaseVertexBaseInstance(final int p0, final int p1, final int p2, final Buffer p3, final int p4, final int p5, final int p6);
    
    void glVertexAttribLPointer(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
}
