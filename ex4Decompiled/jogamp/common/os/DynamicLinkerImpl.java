// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os;

import com.jogamp.common.util.SecurityUtil;
import com.jogamp.common.util.LongObjectHashMap;
import com.jogamp.common.os.DynamicLinker;

abstract class DynamicLinkerImpl implements DynamicLinker
{
    private final Object secSync;
    private boolean allLinkPermissionGranted;
    private static final LongObjectHashMap libHandle2Name;
    
    DynamicLinkerImpl() {
        this.secSync = new Object();
        this.allLinkPermissionGranted = false;
    }
    
    @Override
    public final void claimAllLinkPermission() throws SecurityException {
        synchronized (this.secSync) {
            this.allLinkPermissionGranted = true;
        }
    }
    
    @Override
    public final void releaseAllLinkPermission() throws SecurityException {
        synchronized (this.secSync) {
            this.allLinkPermissionGranted = false;
        }
    }
    
    private final void checkLinkPermission(final String s) throws SecurityException {
        synchronized (this.secSync) {
            if (!this.allLinkPermissionGranted) {
                SecurityUtil.checkLinkPermission(s);
            }
        }
    }
    
    private final void checkLinkPermission(final long n) throws SecurityException {
        synchronized (this.secSync) {
            if (!this.allLinkPermissionGranted) {
                final LibRef libRef = this.getLibRef(n);
                if (null == libRef) {
                    throw new IllegalArgumentException("Library handle 0x" + Long.toHexString(n) + " unknown.");
                }
                SecurityUtil.checkLinkPermission(libRef.getName());
            }
        }
    }
    
    private final void checkAllLinkPermission() throws SecurityException {
        synchronized (this.secSync) {
            if (!this.allLinkPermissionGranted) {
                SecurityUtil.checkAllLinkPermission();
            }
        }
    }
    
    @Override
    public final long openLibraryGlobal(final String s, final boolean b) throws SecurityException {
        this.checkLinkPermission(s);
        final long openLibraryGlobalImpl = this.openLibraryGlobalImpl(s);
        if (0L != openLibraryGlobalImpl) {
            final LibRef incrLibRefCount = this.incrLibRefCount(openLibraryGlobalImpl, s);
            if (DynamicLinkerImpl.DEBUG || b) {
                System.err.println("DynamicLinkerImpl.openLibraryGlobal \"" + s + "\": 0x" + Long.toHexString(openLibraryGlobalImpl) + " -> " + incrLibRefCount + ")");
            }
        }
        else if (DynamicLinkerImpl.DEBUG || b) {
            System.err.println("DynamicLinkerImpl.openLibraryGlobal \"" + s + "\" failed, error: " + this.getLastError());
        }
        return openLibraryGlobalImpl;
    }
    
    protected abstract long openLibraryGlobalImpl(final String p0) throws SecurityException;
    
    @Override
    public final long openLibraryLocal(final String s, final boolean b) throws SecurityException {
        this.checkLinkPermission(s);
        final long openLibraryLocalImpl = this.openLibraryLocalImpl(s);
        if (0L != openLibraryLocalImpl) {
            final LibRef incrLibRefCount = this.incrLibRefCount(openLibraryLocalImpl, s);
            if (DynamicLinkerImpl.DEBUG || b) {
                System.err.println("DynamicLinkerImpl.openLibraryLocal \"" + s + "\": 0x" + Long.toHexString(openLibraryLocalImpl) + " -> " + incrLibRefCount + ")");
            }
        }
        else if (DynamicLinkerImpl.DEBUG || b) {
            System.err.println("DynamicLinkerImpl.openLibraryLocal \"" + s + "\" failed, error: " + this.getLastError());
        }
        return openLibraryLocalImpl;
    }
    
    protected abstract long openLibraryLocalImpl(final String p0) throws SecurityException;
    
    @Override
    public final long lookupSymbolGlobal(final String s) throws SecurityException {
        this.checkAllLinkPermission();
        final long lookupSymbolGlobalImpl = this.lookupSymbolGlobalImpl(s);
        if (DynamicLinkerImpl.DEBUG_LOOKUP) {
            System.err.println("DynamicLinkerImpl.lookupSymbolGlobal(" + s + ") -> 0x" + Long.toHexString(lookupSymbolGlobalImpl));
        }
        return lookupSymbolGlobalImpl;
    }
    
    protected abstract long lookupSymbolGlobalImpl(final String p0) throws SecurityException;
    
    @Override
    public final long lookupSymbol(final long n, final String s) throws SecurityException, IllegalArgumentException {
        this.checkLinkPermission(n);
        final long lookupSymbolLocalImpl = this.lookupSymbolLocalImpl(n, s);
        if (DynamicLinkerImpl.DEBUG_LOOKUP) {
            System.err.println("DynamicLinkerImpl.lookupSymbol(0x" + Long.toHexString(n) + ", " + s + ") -> 0x" + Long.toHexString(lookupSymbolLocalImpl));
        }
        return lookupSymbolLocalImpl;
    }
    
    protected abstract long lookupSymbolLocalImpl(final long p0, final String p1) throws SecurityException;
    
    @Override
    public final void closeLibrary(final long n, final boolean b) throws SecurityException, IllegalArgumentException {
        final LibRef decrLibRefCount = this.decrLibRefCount(n);
        if (null == decrLibRefCount) {
            throw new IllegalArgumentException("Library handle 0x" + Long.toHexString(n) + " unknown.");
        }
        this.checkLinkPermission(decrLibRefCount.getName());
        if (DynamicLinkerImpl.DEBUG || b) {
            System.err.println("DynamicLinkerImpl.closeLibrary(0x" + Long.toHexString(n) + " -> " + decrLibRefCount + ")");
        }
        this.closeLibraryImpl(n);
    }
    
    protected abstract void closeLibraryImpl(final long p0) throws SecurityException;
    
    private final LibRef getLibRef(final long n) {
        synchronized (DynamicLinkerImpl.libHandle2Name) {
            return (LibRef)DynamicLinkerImpl.libHandle2Name.get(n);
        }
    }
    
    private final LibRef incrLibRefCount(final long n, final String s) {
        synchronized (DynamicLinkerImpl.libHandle2Name) {
            LibRef libRef = this.getLibRef(n);
            if (null == libRef) {
                libRef = new LibRef(s);
                DynamicLinkerImpl.libHandle2Name.put(n, libRef);
            }
            else {
                libRef.incrRefCount();
            }
            if (DynamicLinkerImpl.DEBUG) {
                System.err.println("DynamicLinkerImpl.incrLibRefCount 0x" + Long.toHexString(n) + " -> " + libRef + ", libs loaded " + DynamicLinkerImpl.libHandle2Name.size());
            }
            return libRef;
        }
    }
    
    private final LibRef decrLibRefCount(final long n) {
        synchronized (DynamicLinkerImpl.libHandle2Name) {
            final LibRef libRef = this.getLibRef(n);
            if (null != libRef && 0 == libRef.decrRefCount()) {
                DynamicLinkerImpl.libHandle2Name.remove(n);
            }
            if (DynamicLinkerImpl.DEBUG) {
                System.err.println("DynamicLinkerImpl.decrLibRefCount 0x" + Long.toHexString(n) + " -> " + libRef + ", libs loaded " + DynamicLinkerImpl.libHandle2Name.size());
            }
            return libRef;
        }
    }
    
    static {
        libHandle2Name = new LongObjectHashMap(16);
    }
    
    static final class LibRef
    {
        private final String name;
        private int refCount;
        
        LibRef(final String name) {
            this.name = name;
            this.refCount = 1;
        }
        
        final int incrRefCount() {
            return ++this.refCount;
        }
        
        final int decrRefCount() {
            return --this.refCount;
        }
        
        final int getRefCount() {
            return this.refCount;
        }
        
        final String getName() {
            return this.name;
        }
        
        @Override
        public final String toString() {
            return "LibRef[" + this.name + ", refCount " + this.refCount + "]";
        }
    }
}
