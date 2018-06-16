// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public interface AbstractGraphicsScreen extends Cloneable
{
    Object clone();
    
    AbstractGraphicsDevice getDevice();
    
    int getIndex();
}
