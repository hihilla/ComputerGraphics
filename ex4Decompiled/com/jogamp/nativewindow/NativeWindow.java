// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.nativewindow.util.InsetsImmutable;

public interface NativeWindow extends NativeSurface, NativeSurfaceHolder
{
    NativeSurface getNativeSurface();
    
    void destroy();
    
    NativeWindow getParent();
    
    long getWindowHandle();
    
    InsetsImmutable getInsets();
    
    int getX();
    
    int getY();
    
    int getWidth();
    
    int getHeight();
    
    Point getLocationOnScreen(final Point p0);
    
    boolean hasFocus();
}
