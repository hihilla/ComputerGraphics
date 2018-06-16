// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public interface CustomGLEventListener extends GLEventListener
{
    public static final int DISPLAY_REPEAT = 1;
    public static final int DISPLAY_DONTCLEAR = 2;
    
    void display(final GLAutoDrawable p0, final int p1);
}
