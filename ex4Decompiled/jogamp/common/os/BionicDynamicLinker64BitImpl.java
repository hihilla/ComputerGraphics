// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os;

public final class BionicDynamicLinker64BitImpl extends UnixDynamicLinkerImpl
{
    private static final int RTLD_LAZY = 1;
    private static final int RTLD_LOCAL = 0;
    private static final int RTLD_GLOBAL = 256;
    private static final long RTLD_DEFAULT = 0L;
    
    @Override
    protected final long openLibraryLocalImpl(final String s) throws SecurityException {
        return UnixDynamicLinkerImpl.dlopen(s, 1);
    }
    
    @Override
    protected final long openLibraryGlobalImpl(final String s) throws SecurityException {
        return UnixDynamicLinkerImpl.dlopen(s, 257);
    }
    
    @Override
    protected final long lookupSymbolGlobalImpl(final String s) throws SecurityException {
        return UnixDynamicLinkerImpl.dlsym(0L, s);
    }
}
