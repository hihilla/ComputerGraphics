// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os;

abstract class UnixDynamicLinkerImpl extends DynamicLinkerImpl
{
    protected static native int dlclose(final long p0);
    
    protected static native String dlerror();
    
    protected static native long dlopen(final String p0, final int p1);
    
    protected static native long dlsym(final long p0, final String p1);
    
    @Override
    protected final long lookupSymbolLocalImpl(final long n, final String s) throws SecurityException {
        return dlsym(n, s);
    }
    
    @Override
    protected final void closeLibraryImpl(final long n) throws SecurityException {
        dlclose(n);
    }
    
    @Override
    public final String getLastError() {
        return dlerror();
    }
}
