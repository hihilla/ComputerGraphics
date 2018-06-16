// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL2;

public interface IRenderable
{
    public static final int TOGGLE_LIGHT_SPHERES = 0;
    
    void render(final GL2 p0);
    
    void init(final GL2 p0);
    
    void control(final int p0, final Object p1);
    
    boolean isAnimated();
    
    void setCamera(final GL2 p0);
    
    default void destroy(final GL2 gl) {
    }
}
