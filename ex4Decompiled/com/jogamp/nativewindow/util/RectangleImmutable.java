// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

import com.jogamp.common.type.WriteCloneable;

public interface RectangleImmutable extends WriteCloneable, Comparable<RectangleImmutable>
{
    int getHeight();
    
    int getWidth();
    
    int getX();
    
    int getY();
    
    RectangleImmutable union(final RectangleImmutable p0);
    
    RectangleImmutable union(final int p0, final int p1, final int p2, final int p3);
    
    RectangleImmutable intersection(final RectangleImmutable p0);
    
    RectangleImmutable intersection(final int p0, final int p1, final int p2, final int p3);
    
    float coverage(final RectangleImmutable p0);
    
    int compareTo(final RectangleImmutable p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
