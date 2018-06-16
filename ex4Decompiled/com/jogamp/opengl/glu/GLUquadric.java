// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.glu;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.ImmModeSink;

public interface GLUquadric
{
    void enableImmModeSink(final boolean p0);
    
    boolean isImmModeSinkEnabled();
    
    void setImmMode(final boolean p0);
    
    boolean getImmMode();
    
    ImmModeSink replaceImmModeSink();
    
    void resetImmModeSink(final GL p0);
}
