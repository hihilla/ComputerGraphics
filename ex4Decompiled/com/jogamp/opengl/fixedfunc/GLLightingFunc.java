// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.fixedfunc;

import java.nio.FloatBuffer;

public interface GLLightingFunc
{
    public static final int GL_LIGHT0 = 16384;
    public static final int GL_LIGHT1 = 16385;
    public static final int GL_LIGHT2 = 16386;
    public static final int GL_LIGHT3 = 16387;
    public static final int GL_LIGHT4 = 16388;
    public static final int GL_LIGHT5 = 16389;
    public static final int GL_LIGHT6 = 16390;
    public static final int GL_LIGHT7 = 16391;
    public static final int GL_LIGHTING = 2896;
    public static final int GL_AMBIENT = 4608;
    public static final int GL_DIFFUSE = 4609;
    public static final int GL_SPECULAR = 4610;
    public static final int GL_POSITION = 4611;
    public static final int GL_SPOT_DIRECTION = 4612;
    public static final int GL_SPOT_EXPONENT = 4613;
    public static final int GL_SPOT_CUTOFF = 4614;
    public static final int GL_CONSTANT_ATTENUATION = 4615;
    public static final int GL_LINEAR_ATTENUATION = 4616;
    public static final int GL_QUADRATIC_ATTENUATION = 4617;
    public static final int GL_EMISSION = 5632;
    public static final int GL_SHININESS = 5633;
    public static final int GL_AMBIENT_AND_DIFFUSE = 5634;
    public static final int GL_COLOR_MATERIAL = 2903;
    public static final int GL_NORMALIZE = 2977;
    public static final int GL_FLAT = 7424;
    public static final int GL_SMOOTH = 7425;
    
    void glLightfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glLightfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glMaterialf(final int p0, final int p1, final float p2);
    
    void glMaterialfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glMaterialfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glColor4f(final float p0, final float p1, final float p2, final float p3);
    
    void glShadeModel(final int p0);
}
