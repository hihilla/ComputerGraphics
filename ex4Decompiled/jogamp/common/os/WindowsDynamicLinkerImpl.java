// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os;

public final class WindowsDynamicLinkerImpl extends DynamicLinkerImpl
{
    private static final int symbolArgAlignment = 4;
    private static final int symbolMaxArguments = 12;
    
    private static native int FreeLibrary(final long p0);
    
    private static native int GetLastError();
    
    private static native long GetProcAddressA(final long p0, final String p1);
    
    private static native long LoadLibraryW(final String p0);
    
    @Override
    protected final long openLibraryLocalImpl(final String s) throws SecurityException {
        return LoadLibraryW(s);
    }
    
    @Override
    protected final long openLibraryGlobalImpl(final String s) throws SecurityException {
        return LoadLibraryW(s);
    }
    
    @Override
    protected final long lookupSymbolGlobalImpl(final String s) throws SecurityException {
        if (WindowsDynamicLinkerImpl.DEBUG_LOOKUP) {
            System.err.println("lookupSymbolGlobal: Not supported on Windows");
        }
        return 0L;
    }
    
    @Override
    protected final long lookupSymbolLocalImpl(final long n, final String s) throws IllegalArgumentException {
        long n2 = GetProcAddressA(n, s);
        if (0L == n2) {
            for (int n3 = 0; 0L == n2 && n3 <= 12; n2 = GetProcAddressA(n, s + "@" + n3 * 4), ++n3) {}
        }
        return n2;
    }
    
    @Override
    protected final void closeLibraryImpl(final long n) throws IllegalArgumentException {
        FreeLibrary(n);
    }
    
    @Override
    public final String getLastError() {
        final int getLastError = GetLastError();
        return "Last error: 0x" + Integer.toHexString(getLastError) + " (" + getLastError + ")";
    }
}
