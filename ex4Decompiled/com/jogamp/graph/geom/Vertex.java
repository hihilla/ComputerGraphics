// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.geom;

import com.jogamp.opengl.math.Vert3fImmutable;

public interface Vertex extends Vert3fImmutable, Cloneable
{
    void setCoord(final float p0, final float p1, final float p2);
    
    void setCoord(final float[] p0, final int p1, final int p2);
    
    void setX(final float p0);
    
    void setY(final float p0);
    
    void setZ(final float p0);
    
    boolean isOnCurve();
    
    void setOnCurve(final boolean p0);
    
    int getId();
    
    void setId(final int p0);
    
    float[] getTexCoord();
    
    void setTexCoord(final float p0, final float p1, final float p2);
    
    void setTexCoord(final float[] p0, final int p1, final int p2);
    
    boolean equals(final Object p0);
    
    Vertex clone();
    
    public interface Factory<T extends Vertex>
    {
        T create();
        
        T create(final Vertex p0);
        
        T create(final int p0, final boolean p1, final float[] p2);
        
        T create(final float p0, final float p1, final float p2, final boolean p3);
        
        T create(final float[] p0, final int p1, final int p2, final boolean p3);
    }
}
