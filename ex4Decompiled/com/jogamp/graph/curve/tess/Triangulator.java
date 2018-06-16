// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.curve.tess;

import com.jogamp.graph.geom.Outline;
import com.jogamp.graph.geom.Triangle;
import java.util.List;

public interface Triangulator
{
    void addCurve(final List<Triangle> p0, final Outline p1, final float p2);
    
    void generate(final List<Triangle> p0);
    
    void reset();
    
    int getAddedVerticeCount();
}
