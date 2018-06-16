// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os;

public final class MacOSXDynamicLinkerImpl extends UnixDynamicLinkerImpl
{
    private static final long RTLD_DEFAULT = -2L;
    private static final int RTLD_LAZY = 1;
    private static final int RTLD_LOCAL = 4;
    private static final int RTLD_GLOBAL = 8;
    
    @Override
    protected final long openLibraryLocalImpl(final String s) throws SecurityException {
        return UnixDynamicLinkerImpl.dlopen(s, 5);
    }
    
    @Override
    protected final long openLibraryGlobalImpl(final String s) throws SecurityException {
        return UnixDynamicLinkerImpl.dlopen(s, 9);
    }
    
    @Override
    protected final long lookupSymbolGlobalImpl(final String s) throws SecurityException {
        return UnixDynamicLinkerImpl.dlsym(-2L, s);
    }
}
