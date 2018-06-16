// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;

public interface GL3bc extends GL2, GL3
{
    void glVertexP2ui(final int p0, final int p1);
    
    void glVertexP2uiv(final int p0, final IntBuffer p1);
    
    void glVertexP2uiv(final int p0, final int[] p1, final int p2);
    
    void glVertexP3ui(final int p0, final int p1);
    
    void glVertexP3uiv(final int p0, final IntBuffer p1);
    
    void glVertexP3uiv(final int p0, final int[] p1, final int p2);
    
    void glVertexP4ui(final int p0, final int p1);
    
    void glVertexP4uiv(final int p0, final IntBuffer p1);
    
    void glVertexP4uiv(final int p0, final int[] p1, final int p2);
    
    void glTexCoordP1ui(final int p0, final int p1);
    
    void glTexCoordP1uiv(final int p0, final IntBuffer p1);
    
    void glTexCoordP1uiv(final int p0, final int[] p1, final int p2);
    
    void glTexCoordP2ui(final int p0, final int p1);
    
    void glTexCoordP2uiv(final int p0, final IntBuffer p1);
    
    void glTexCoordP2uiv(final int p0, final int[] p1, final int p2);
    
    void glTexCoordP3ui(final int p0, final int p1);
    
    void glTexCoordP3uiv(final int p0, final IntBuffer p1);
    
    void glTexCoordP3uiv(final int p0, final int[] p1, final int p2);
    
    void glTexCoordP4ui(final int p0, final int p1);
    
    void glTexCoordP4uiv(final int p0, final IntBuffer p1);
    
    void glTexCoordP4uiv(final int p0, final int[] p1, final int p2);
    
    void glMultiTexCoordP1ui(final int p0, final int p1, final int p2);
    
    void glMultiTexCoordP1uiv(final int p0, final int p1, final IntBuffer p2);
    
    void glMultiTexCoordP1uiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glMultiTexCoordP2ui(final int p0, final int p1, final int p2);
    
    void glMultiTexCoordP2uiv(final int p0, final int p1, final IntBuffer p2);
    
    void glMultiTexCoordP2uiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glMultiTexCoordP3ui(final int p0, final int p1, final int p2);
    
    void glMultiTexCoordP3uiv(final int p0, final int p1, final IntBuffer p2);
    
    void glMultiTexCoordP3uiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glMultiTexCoordP4ui(final int p0, final int p1, final int p2);
    
    void glMultiTexCoordP4uiv(final int p0, final int p1, final IntBuffer p2);
    
    void glMultiTexCoordP4uiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glNormalP3ui(final int p0, final int p1);
    
    void glNormalP3uiv(final int p0, final IntBuffer p1);
    
    void glNormalP3uiv(final int p0, final int[] p1, final int p2);
    
    void glColorP3ui(final int p0, final int p1);
    
    void glColorP3uiv(final int p0, final IntBuffer p1);
    
    void glColorP3uiv(final int p0, final int[] p1, final int p2);
    
    void glColorP4ui(final int p0, final int p1);
    
    void glColorP4uiv(final int p0, final IntBuffer p1);
    
    void glColorP4uiv(final int p0, final int[] p1, final int p2);
    
    void glSecondaryColorP3ui(final int p0, final int p1);
    
    void glSecondaryColorP3uiv(final int p0, final IntBuffer p1);
    
    void glSecondaryColorP3uiv(final int p0, final int[] p1, final int p2);
    
    void glDrawElementsBaseVertex(final int p0, final int p1, final int p2, final Buffer p3, final int p4);
    
    void glDrawElementsInstancedBaseVertex(final int p0, final int p1, final int p2, final Buffer p3, final int p4, final int p5);
    
    void glDrawRangeElementsBaseVertex(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5, final int p6);
}
