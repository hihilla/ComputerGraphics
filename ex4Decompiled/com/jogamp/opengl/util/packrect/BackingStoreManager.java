// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.packrect;

public interface BackingStoreManager
{
    Object allocateBackingStore(final int p0, final int p1);
    
    void deleteBackingStore(final Object p0);
    
    boolean canCompact();
    
    boolean preExpand(final Rect p0, final int p1);
    
    boolean additionFailed(final Rect p0, final int p1);
    
    void beginMovement(final Object p0, final Object p1);
    
    void move(final Object p0, final Rect p1, final Object p2, final Rect p3);
    
    void endMovement(final Object p0, final Object p1);
}
