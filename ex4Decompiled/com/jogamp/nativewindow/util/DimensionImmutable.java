// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

import com.jogamp.common.type.WriteCloneable;

public interface DimensionImmutable extends WriteCloneable, Comparable<DimensionImmutable>
{
    int getHeight();
    
    int getWidth();
    
    int compareTo(final DimensionImmutable p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
