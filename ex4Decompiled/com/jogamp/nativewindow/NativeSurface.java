// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public interface NativeSurface extends SurfaceUpdatedListener
{
    public static final int LOCK_SURFACE_UNLOCKED = 0;
    public static final int LOCK_SURFACE_NOT_READY = 1;
    public static final int LOCK_SURFACE_CHANGED = 2;
    public static final int LOCK_SUCCESS = 3;
    
    int lockSurface() throws NativeWindowException, RuntimeException;
    
    void unlockSurface();
    
    boolean isSurfaceLockedByOtherThread();
    
    Thread getSurfaceLockOwner();
    
    boolean surfaceSwap();
    
    void addSurfaceUpdatedListener(final SurfaceUpdatedListener p0);
    
    void addSurfaceUpdatedListener(final int p0, final SurfaceUpdatedListener p1) throws IndexOutOfBoundsException;
    
    void removeSurfaceUpdatedListener(final SurfaceUpdatedListener p0);
    
    long getSurfaceHandle();
    
    int getSurfaceWidth();
    
    int getSurfaceHeight();
    
    int[] convertToWindowUnits(final int[] p0);
    
    int[] convertToPixelUnits(final int[] p0);
    
    AbstractGraphicsConfiguration getGraphicsConfiguration();
    
    long getDisplayHandle();
    
    int getScreenIndex();
}
