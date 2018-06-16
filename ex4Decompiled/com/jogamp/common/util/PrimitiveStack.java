// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

public interface PrimitiveStack
{
    int capacity();
    
    int position();
    
    void position(final int p0) throws IndexOutOfBoundsException;
    
    int remaining();
    
    int getGrowSize();
    
    void setGrowSize(final int p0);
}
