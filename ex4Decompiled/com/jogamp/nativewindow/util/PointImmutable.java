// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

import com.jogamp.common.type.WriteCloneable;

public interface PointImmutable extends WriteCloneable, Comparable<PointImmutable>
{
    int getX();
    
    int getY();
    
    int compareTo(final PointImmutable p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
