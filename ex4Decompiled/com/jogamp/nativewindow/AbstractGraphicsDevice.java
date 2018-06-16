// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import jogamp.nativewindow.Debug;

public interface AbstractGraphicsDevice extends Cloneable
{
    public static final boolean DEBUG = Debug.debug("GraphicsDevice");
    public static final String DEFAULT_CONNECTION = "decon";
    public static final String EXTERNAL_CONNECTION = "excon";
    public static final int DEFAULT_UNIT = 0;
    
    Object clone();
    
    String getType();
    
    String getConnection();
    
    int getUnitID();
    
    String getUniqueID();
    
    long getHandle();
    
    void lock();
    
    void unlock();
    
    void validateLocked() throws RuntimeException;
    
    boolean open();
    
    boolean close();
    
    boolean isHandleOwner();
    
    void clearHandleOwner();
}
