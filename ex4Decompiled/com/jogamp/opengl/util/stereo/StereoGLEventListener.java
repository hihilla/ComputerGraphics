// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.CustomGLEventListener;

public interface StereoGLEventListener extends CustomGLEventListener
{
    void reshapeForEye(final GLAutoDrawable p0, final int p1, final int p2, final int p3, final int p4, final EyeParameter p5, final ViewerPose p6);
}
