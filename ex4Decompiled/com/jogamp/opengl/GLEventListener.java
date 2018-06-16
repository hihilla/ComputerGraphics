// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.util.EventListener;

public interface GLEventListener extends EventListener
{
    void init(final GLAutoDrawable p0);
    
    void dispose(final GLAutoDrawable p0);
    
    void display(final GLAutoDrawable p0);
    
    void reshape(final GLAutoDrawable p0, final int p1, final int p2, final int p3, final int p4);
}
