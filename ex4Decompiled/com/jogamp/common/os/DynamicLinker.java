// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.os;

public interface DynamicLinker
{
    public static final boolean DEBUG = NativeLibrary.DEBUG;
    public static final boolean DEBUG_LOOKUP = NativeLibrary.DEBUG_LOOKUP;
    
    void claimAllLinkPermission() throws SecurityException;
    
    void releaseAllLinkPermission() throws SecurityException;
    
    long openLibraryGlobal(final String p0, final boolean p1) throws SecurityException;
    
    long openLibraryLocal(final String p0, final boolean p1) throws SecurityException;
    
    long lookupSymbolGlobal(final String p0) throws SecurityException;
    
    long lookupSymbol(final long p0, final String p1) throws SecurityException, IllegalArgumentException;
    
    void closeLibrary(final long p0, final boolean p1) throws SecurityException, IllegalArgumentException;
    
    String getLastError();
}
