// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.awt;

import com.jogamp.opengl.GLAutoDrawable;

public interface AWTGLAutoDrawable extends GLAutoDrawable, ComponentEvents
{
    void setSize(final int p0, final int p1);
    
    void repaint();
}
