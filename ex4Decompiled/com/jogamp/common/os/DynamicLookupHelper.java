// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.os;

import jogamp.common.Debug;

public interface DynamicLookupHelper
{
    public static final boolean DEBUG = Debug.debug("NativeLibrary");
    public static final boolean DEBUG_LOOKUP = Debug.debug("NativeLibrary.Lookup");
    
    void claimAllLinkPermission() throws SecurityException;
    
    void releaseAllLinkPermission() throws SecurityException;
    
    long dynamicLookupFunction(final String p0) throws SecurityException;
    
    boolean isFunctionAvailable(final String p0) throws SecurityException;
}
