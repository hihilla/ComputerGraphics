// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

import com.jogamp.common.type.WriteCloneable;

public interface InsetsImmutable extends WriteCloneable
{
    int getLeftWidth();
    
    int getRightWidth();
    
    int getTotalWidth();
    
    int getTopHeight();
    
    int getBottomHeight();
    
    int getTotalHeight();
    
    boolean equals(final Object p0);
    
    int hashCode();
}
