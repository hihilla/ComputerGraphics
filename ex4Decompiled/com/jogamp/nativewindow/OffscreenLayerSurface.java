// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import com.jogamp.nativewindow.util.PointImmutable;
import com.jogamp.nativewindow.util.PixelRectangle;
import com.jogamp.common.util.locks.RecursiveLock;

public interface OffscreenLayerSurface
{
    void attachSurfaceLayer(final long p0) throws NativeWindowException;
    
    void detachSurfaceLayer() throws NativeWindowException;
    
    long getAttachedSurfaceLayer();
    
    boolean isSurfaceLayerAttached();
    
    void setChosenCapabilities(final CapabilitiesImmutable p0);
    
    RecursiveLock getLock();
    
    boolean setCursor(final PixelRectangle p0, final PointImmutable p1);
    
    boolean hideCursor();
}
