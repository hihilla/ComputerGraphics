// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.fixedfunc;

import java.nio.IntBuffer;
import java.nio.FloatBuffer;

public interface GLMatrixFunc
{
    public static final int GL_MATRIX_MODE = 2976;
    public static final int GL_MODELVIEW = 5888;
    public static final int GL_PROJECTION = 5889;
    public static final int GL_MODELVIEW_MATRIX = 2982;
    public static final int GL_PROJECTION_MATRIX = 2983;
    public static final int GL_TEXTURE_MATRIX = 2984;
    
    void glGetFloatv(final int p0, final FloatBuffer p1);
    
    void glGetFloatv(final int p0, final float[] p1, final int p2);
    
    void glGetIntegerv(final int p0, final IntBuffer p1);
    
    void glGetIntegerv(final int p0, final int[] p1, final int p2);
    
    void glMatrixMode(final int p0);
    
    void glPushMatrix();
    
    void glPopMatrix();
    
    void glLoadIdentity();
    
    void glLoadMatrixf(final FloatBuffer p0);
    
    void glLoadMatrixf(final float[] p0, final int p1);
    
    void glMultMatrixf(final FloatBuffer p0);
    
    void glMultMatrixf(final float[] p0, final int p1);
    
    void glTranslatef(final float p0, final float p1, final float p2);
    
    void glRotatef(final float p0, final float p1, final float p2, final float p3);
    
    void glScalef(final float p0, final float p1, final float p2);
    
    void glOrthof(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5);
    
    void glFrustumf(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5);
}
