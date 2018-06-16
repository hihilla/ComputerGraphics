// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11;

import java.util.ArrayList;
import jogamp.nativewindow.Debug;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.nativewindow.NWJNILibLoader;
import java.util.HashMap;
import java.util.List;
import com.jogamp.common.util.LongObjectHashMap;
import jogamp.nativewindow.ToolkitProperties;

public class X11Util implements ToolkitProperties
{
    public static final boolean DEBUG;
    public static final boolean ATI_HAS_XCLOSEDISPLAY_BUG;
    public static final boolean HAS_XCLOSEDISPLAY_BUG;
    public static final boolean ATI_HAS_MULTITHREADING_BUG;
    public static final boolean XSYNC_ENABLED;
    public static final boolean XERROR_STACKDUMP;
    private static final boolean TRACE_DISPLAY_LIFECYCLE;
    private static String nullDisplayName;
    private static volatile boolean isInit;
    private static boolean markAllDisplaysUnclosable;
    private static boolean hasThreadingIssues;
    private static final Object setX11ErrorHandlerLock;
    private static final String X11_EXTENSION_ATIFGLRXDRI = "ATIFGLRXDRI";
    private static final String X11_EXTENSION_ATIFGLEXTENSION = "ATIFGLEXTENSION";
    private static Object globalLock;
    private static LongObjectHashMap openDisplayMap;
    private static List<NamedDisplay> openDisplayList;
    private static List<NamedDisplay> reusableDisplayList;
    private static List<NamedDisplay> pendingDisplayList;
    private static final HashMap<String, Boolean> displayXineramaEnabledMap;
    static volatile boolean XineramaFetched;
    static long XineramaLibHandle;
    static long XineramaQueryFunc;
    
    public static void initSingleton() {
        if (!X11Util.isInit) {
            synchronized (X11Util.class) {
                if (!X11Util.isInit) {
                    X11Util.isInit = true;
                    if (X11Util.DEBUG) {
                        System.out.println("X11Util.initSingleton()");
                    }
                    if (!NWJNILibLoader.loadNativeWindow("x11")) {
                        throw new NativeWindowException("NativeWindow X11 native library load error.");
                    }
                    final boolean initialize0 = initialize0(X11Util.XERROR_STACKDUMP);
                    final long xOpenDisplay = X11Lib.XOpenDisplay(PropertyAccess.getProperty("nativewindow.x11.display.default", true));
                    boolean queryExtension;
                    boolean queryExtension2;
                    if (0L != xOpenDisplay) {
                        if (X11Util.XSYNC_ENABLED) {
                            X11Lib.XSynchronize(xOpenDisplay, true);
                        }
                        try {
                            X11Util.nullDisplayName = X11Lib.XDisplayString(xOpenDisplay);
                            queryExtension = X11Lib.QueryExtension(xOpenDisplay, "ATIFGLRXDRI");
                            queryExtension2 = X11Lib.QueryExtension(xOpenDisplay, "ATIFGLEXTENSION");
                        }
                        finally {
                            X11Lib.XCloseDisplay(xOpenDisplay);
                        }
                    }
                    else {
                        X11Util.nullDisplayName = "nil";
                        queryExtension = false;
                        queryExtension2 = false;
                    }
                    final boolean b = queryExtension || queryExtension2;
                    X11Util.hasThreadingIssues = (X11Util.ATI_HAS_MULTITHREADING_BUG && b);
                    if (!X11Util.markAllDisplaysUnclosable) {
                        X11Util.markAllDisplaysUnclosable = ((X11Util.ATI_HAS_XCLOSEDISPLAY_BUG && b) || X11Util.HAS_XCLOSEDISPLAY_BUG);
                    }
                    if (X11Util.DEBUG) {
                        System.err.println("X11Util.initSingleton(): OK " + initialize0 + "]" + ",\n\t X11 Display(NULL) <" + X11Util.nullDisplayName + ">" + ",\n\t XSynchronize Enabled: " + X11Util.XSYNC_ENABLED + ",\n\t X11_EXTENSION_ATIFGLRXDRI " + queryExtension + ",\n\t X11_EXTENSION_ATIFGLEXTENSION " + queryExtension2 + ",\n\t requiresToolkitLock " + requiresToolkitLock() + ",\n\t hasThreadingIssues " + hasThreadingIssues() + ",\n\t markAllDisplaysUnclosable " + getMarkAllDisplaysUnclosable());
                    }
                }
            }
        }
    }
    
    public static void shutdown() {
        if (X11Util.isInit) {
            synchronized (X11Util.class) {
                if (X11Util.isInit) {
                    final boolean jvmShuttingDown = NativeWindowFactory.isJVMShuttingDown();
                    if (X11Util.DEBUG || ((X11Util.openDisplayMap.size() > 0 || X11Util.reusableDisplayList.size() > 0 || X11Util.pendingDisplayList.size() > 0) && (X11Util.reusableDisplayList.size() != X11Util.pendingDisplayList.size() || !X11Util.markAllDisplaysUnclosable))) {
                        System.err.println("X11Util.Display: Shutdown (JVM shutdown: " + jvmShuttingDown + ", open (no close attempt): " + X11Util.openDisplayMap.size() + "/" + X11Util.openDisplayList.size() + ", reusable (open, marked uncloseable): " + X11Util.reusableDisplayList.size() + ", pending (open in creation order): " + X11Util.pendingDisplayList.size() + ")");
                        if (X11Util.DEBUG) {
                            ExceptionUtils.dumpStack(System.err);
                        }
                        if (X11Util.openDisplayList.size() > 0) {
                            dumpOpenDisplayConnections();
                        }
                        if (X11Util.DEBUG && (X11Util.reusableDisplayList.size() > 0 || X11Util.pendingDisplayList.size() > 0)) {
                            dumpPendingDisplayConnections();
                        }
                    }
                    if (jvmShuttingDown) {
                        synchronized (X11Util.globalLock) {
                            X11Util.isInit = false;
                            closePendingDisplayConnections();
                            X11Util.openDisplayList.clear();
                            X11Util.reusableDisplayList.clear();
                            X11Util.pendingDisplayList.clear();
                            X11Util.openDisplayMap.clear();
                            X11Util.displayXineramaEnabledMap.clear();
                            shutdown0();
                        }
                    }
                }
            }
        }
    }
    
    public static final boolean requiresToolkitLock() {
        return true;
    }
    
    public static final boolean hasThreadingIssues() {
        return X11Util.hasThreadingIssues;
    }
    
    public static void setX11ErrorHandler(final boolean b, final boolean b2) {
        synchronized (X11Util.setX11ErrorHandlerLock) {
            setX11ErrorHandler0(b, b2);
        }
    }
    
    public static String getNullDisplayName() {
        return X11Util.nullDisplayName;
    }
    
    public static void markAllDisplaysUnclosable() {
        synchronized (X11Util.globalLock) {
            X11Util.markAllDisplaysUnclosable = true;
            for (int i = 0; i < X11Util.openDisplayList.size(); ++i) {
                X11Util.openDisplayList.get(i).setUncloseable(true);
            }
            for (int j = 0; j < X11Util.reusableDisplayList.size(); ++j) {
                X11Util.reusableDisplayList.get(j).setUncloseable(true);
            }
            for (int k = 0; k < X11Util.pendingDisplayList.size(); ++k) {
                X11Util.pendingDisplayList.get(k).setUncloseable(true);
            }
        }
    }
    
    public static boolean getMarkAllDisplaysUnclosable() {
        return X11Util.markAllDisplaysUnclosable;
    }
    
    private static int closePendingDisplayConnections() {
        int n = 0;
        synchronized (X11Util.globalLock) {
            if (getMarkAllDisplaysUnclosable()) {
                for (int i = 0; i < X11Util.pendingDisplayList.size(); ++i) {
                    final NamedDisplay namedDisplay = X11Util.pendingDisplayList.get(i);
                    if (X11Util.DEBUG) {
                        System.err.println("X11Util.closePendingDisplayConnections(): Closing [" + i + "]: " + namedDisplay + " - closeAttempted " + !X11Util.openDisplayMap.containsKey(namedDisplay.getHandle()));
                    }
                    XCloseDisplay(namedDisplay.getHandle());
                    ++n;
                }
                if (X11Util.DEBUG) {
                    System.err.println("X11Util.closePendingDisplayConnections(): Closed " + n + " pending display connections");
                }
            }
        }
        return n;
    }
    
    public static int getOpenDisplayConnectionNumber() {
        synchronized (X11Util.globalLock) {
            return X11Util.openDisplayList.size();
        }
    }
    
    public static void dumpOpenDisplayConnections() {
        synchronized (X11Util.globalLock) {
            System.err.println("X11Util: Open X11 Display Connections: " + X11Util.openDisplayList.size());
            for (int i = 0; i < X11Util.openDisplayList.size(); ++i) {
                final NamedDisplay namedDisplay = X11Util.openDisplayList.get(i);
                System.err.println("X11Util: Open[" + i + "]: " + namedDisplay);
                if (null != namedDisplay) {
                    final Throwable creationStack = namedDisplay.getCreationStack();
                    if (null != creationStack) {
                        creationStack.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static int getReusableDisplayConnectionNumber() {
        synchronized (X11Util.globalLock) {
            return X11Util.reusableDisplayList.size();
        }
    }
    
    public static int getPendingDisplayConnectionNumber() {
        synchronized (X11Util.globalLock) {
            return X11Util.pendingDisplayList.size();
        }
    }
    
    public static void dumpPendingDisplayConnections() {
        synchronized (X11Util.globalLock) {
            System.err.println("X11Util: Reusable X11 Display Connections: " + X11Util.reusableDisplayList.size());
            for (int i = 0; i < X11Util.reusableDisplayList.size(); ++i) {
                final NamedDisplay namedDisplay = X11Util.reusableDisplayList.get(i);
                System.err.println("X11Util: Reusable[" + i + "]: " + namedDisplay);
                if (null != namedDisplay) {
                    final Throwable creationStack = namedDisplay.getCreationStack();
                    if (null != creationStack) {
                        creationStack.printStackTrace();
                    }
                }
            }
            System.err.println("X11Util: Pending X11 Display Connections (creation order): " + X11Util.pendingDisplayList.size());
            for (int j = 0; j < X11Util.pendingDisplayList.size(); ++j) {
                final NamedDisplay namedDisplay2 = X11Util.pendingDisplayList.get(j);
                System.err.println("X11Util: Pending[" + j + "]: " + namedDisplay2);
                if (null != namedDisplay2) {
                    final Throwable creationStack2 = namedDisplay2.getCreationStack();
                    if (null != creationStack2) {
                        creationStack2.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static boolean markDisplayUncloseable(final long n) {
        final NamedDisplay namedDisplay;
        synchronized (X11Util.globalLock) {
            namedDisplay = (NamedDisplay)X11Util.openDisplayMap.get(n);
        }
        if (null != namedDisplay) {
            namedDisplay.setUncloseable(true);
            return true;
        }
        return false;
    }
    
    public static long openDisplay(String validateDisplayName) {
        long n = 0L;
        NamedDisplay namedDisplay = null;
        validateDisplayName = validateDisplayName(validateDisplayName);
        boolean b = false;
        synchronized (X11Util.globalLock) {
            for (int i = 0; i < X11Util.reusableDisplayList.size(); ++i) {
                if (X11Util.reusableDisplayList.get(i).getName().equals(validateDisplayName)) {
                    namedDisplay = X11Util.reusableDisplayList.remove(i);
                    n = namedDisplay.getHandle();
                    b = true;
                    break;
                }
            }
            if (0L == n) {
                n = XOpenDisplay(validateDisplayName);
                if (0L == n) {
                    throw new NativeWindowException("X11Util.Display: Unable to create a display(" + validateDisplayName + ") connection. Thread " + Thread.currentThread().getName());
                }
                namedDisplay = new NamedDisplay(validateDisplayName, n);
                X11Util.pendingDisplayList.add(namedDisplay);
            }
            namedDisplay.addRef();
            X11Util.openDisplayMap.put(n, namedDisplay);
            X11Util.openDisplayList.add(namedDisplay);
            if (X11Util.markAllDisplaysUnclosable) {
                namedDisplay.setUncloseable(true);
            }
        }
        if (X11Util.DEBUG) {
            System.err.println("X11Util.Display: openDisplay [reuse " + b + "] " + namedDisplay + ". Thread " + Thread.currentThread().getName());
        }
        return namedDisplay.getHandle();
    }
    
    public static void closeDisplay(final long n) {
        synchronized (X11Util.globalLock) {
            final NamedDisplay namedDisplay = (NamedDisplay)X11Util.openDisplayMap.remove(n);
            if (null == namedDisplay) {
                dumpPendingDisplayConnections();
                throw new RuntimeException("X11Util.Display: Display(0x" + Long.toHexString(n) + ") with given handle is not mapped. Thread " + Thread.currentThread().getName());
            }
            if (namedDisplay.getHandle() != n) {
                dumpPendingDisplayConnections();
                throw new RuntimeException("X11Util.Display: Display(0x" + Long.toHexString(n) + ") Mapping error: " + namedDisplay + ". Thread " + Thread.currentThread().getName());
            }
            namedDisplay.removeRef();
            if (!X11Util.openDisplayList.remove(namedDisplay)) {
                throw new RuntimeException("Internal: " + namedDisplay);
            }
            if (X11Util.markAllDisplaysUnclosable) {
                namedDisplay.setUncloseable(true);
            }
            if (!namedDisplay.isUncloseable()) {
                XCloseDisplay(namedDisplay.getHandle());
                X11Util.pendingDisplayList.remove(namedDisplay);
            }
            else {
                X11Lib.XSync(namedDisplay.getHandle(), true);
                X11Util.reusableDisplayList.add(namedDisplay);
            }
            if (X11Util.DEBUG) {
                System.err.println("X11Util.Display: Closed (real: " + !namedDisplay.isUncloseable() + ") " + namedDisplay + ". Thread " + Thread.currentThread().getName());
            }
        }
    }
    
    public static NamedDisplay getNamedDisplay(final long n) {
        synchronized (X11Util.globalLock) {
            return (NamedDisplay)X11Util.openDisplayMap.get(n);
        }
    }
    
    public static String validateDisplayName(final String s) {
        return (null == s || "decon".equals(s)) ? getNullDisplayName() : s;
    }
    
    public static String validateDisplayName(String xDisplayString, final long n) {
        if ((null == xDisplayString || "decon".equals(xDisplayString)) && 0L != n) {
            xDisplayString = X11Lib.XDisplayString(n);
        }
        return validateDisplayName(xDisplayString);
    }
    
    public static long XOpenDisplay(final String s) {
        final long xOpenDisplay = X11Lib.XOpenDisplay(s);
        if (X11Util.XSYNC_ENABLED && 0L != xOpenDisplay) {
            X11Lib.XSynchronize(xOpenDisplay, true);
        }
        if (X11Util.TRACE_DISPLAY_LIFECYCLE) {
            System.err.println(Thread.currentThread() + " - X11Util.XOpenDisplay(" + s + ") 0x" + Long.toHexString(xOpenDisplay));
        }
        return xOpenDisplay;
    }
    
    public static int XCloseDisplay(final long n) {
        if (X11Util.TRACE_DISPLAY_LIFECYCLE) {
            System.err.println(Thread.currentThread() + " - X11Util.XCloseDisplay() 0x" + Long.toHexString(n));
        }
        int xCloseDisplay = -1;
        try {
            xCloseDisplay = X11Lib.XCloseDisplay(n);
        }
        catch (Exception ex) {
            System.err.println("X11Util: Caught exception:");
            ex.printStackTrace();
        }
        return xCloseDisplay;
    }
    
    public static boolean XineramaIsEnabled(final X11GraphicsDevice x11GraphicsDevice) {
        if (null == x11GraphicsDevice) {
            throw new IllegalArgumentException("X11 Display device is NULL");
        }
        x11GraphicsDevice.lock();
        try {
            return XineramaIsEnabled(x11GraphicsDevice.getHandle());
        }
        finally {
            x11GraphicsDevice.unlock();
        }
    }
    
    public static boolean XineramaIsEnabled(final long n) {
        if (0L == n) {
            throw new IllegalArgumentException("X11 Display handle is NULL");
        }
        final String xDisplayString = X11Lib.XDisplayString(n);
        synchronized (X11Util.displayXineramaEnabledMap) {
            final Boolean b = X11Util.displayXineramaEnabledMap.get(xDisplayString);
            if (null != b) {
                return b;
            }
        }
        if (!X11Util.XineramaFetched) {
            synchronized (X11Util.class) {
                if (!X11Util.XineramaFetched) {
                    X11Util.XineramaLibHandle = X11Lib.XineramaGetLibHandle();
                    if (0L != X11Util.XineramaLibHandle) {
                        X11Util.XineramaQueryFunc = X11Lib.XineramaGetQueryFunc(X11Util.XineramaLibHandle);
                    }
                    X11Util.XineramaFetched = true;
                }
            }
        }
        boolean xineramaIsEnabled;
        if (0L != X11Util.XineramaQueryFunc) {
            xineramaIsEnabled = X11Lib.XineramaIsEnabled(X11Util.XineramaQueryFunc, n);
        }
        else {
            if (X11Util.DEBUG) {
                System.err.println("XineramaIsEnabled: Couldn't bind to Xinerama - lib 0x" + Long.toHexString(X11Util.XineramaLibHandle) + "query 0x" + Long.toHexString(X11Util.XineramaQueryFunc));
            }
            xineramaIsEnabled = false;
        }
        synchronized (X11Util.displayXineramaEnabledMap) {
            if (X11Util.DEBUG) {
                System.err.println("XineramaIsEnabled Cache: Display " + xDisplayString + " (0x" + Long.toHexString(n) + ") -> " + xineramaIsEnabled);
            }
            X11Util.displayXineramaEnabledMap.put(xDisplayString, xineramaIsEnabled);
        }
        return xineramaIsEnabled;
    }
    
    private static final String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
    
    private static final void dumpStack() {
        ExceptionUtils.dumpStack(System.err);
    }
    
    private static native boolean initialize0(final boolean p0);
    
    private static native void shutdown0();
    
    private static native void setX11ErrorHandler0(final boolean p0, final boolean p1);
    
    static {
        DEBUG = Debug.debug("X11Util");
        ATI_HAS_XCLOSEDISPLAY_BUG = !PropertyAccess.isPropertyDefined("nativewindow.debug.X11Util.ATI_HAS_NO_XCLOSEDISPLAY_BUG", true);
        HAS_XCLOSEDISPLAY_BUG = PropertyAccess.isPropertyDefined("nativewindow.debug.X11Util.HAS_XCLOSEDISPLAY_BUG", true);
        ATI_HAS_MULTITHREADING_BUG = !PropertyAccess.isPropertyDefined("nativewindow.debug.X11Util.ATI_HAS_NO_MULTITHREADING_BUG", true);
        XSYNC_ENABLED = PropertyAccess.isPropertyDefined("nativewindow.debug.X11Util.XSync", true);
        XERROR_STACKDUMP = (X11Util.DEBUG || PropertyAccess.isPropertyDefined("nativewindow.debug.X11Util.XErrorStackDump", true));
        TRACE_DISPLAY_LIFECYCLE = PropertyAccess.isPropertyDefined("nativewindow.debug.X11Util.TraceDisplayLifecycle", true);
        X11Util.nullDisplayName = null;
        X11Util.isInit = false;
        X11Util.markAllDisplaysUnclosable = false;
        X11Util.hasThreadingIssues = false;
        setX11ErrorHandlerLock = new Object();
        X11Util.globalLock = new Object();
        X11Util.openDisplayMap = new LongObjectHashMap();
        X11Util.openDisplayList = new ArrayList<NamedDisplay>();
        X11Util.reusableDisplayList = new ArrayList<NamedDisplay>();
        X11Util.pendingDisplayList = new ArrayList<NamedDisplay>();
        displayXineramaEnabledMap = new HashMap<String, Boolean>();
        X11Util.XineramaFetched = false;
        X11Util.XineramaLibHandle = 0L;
        X11Util.XineramaQueryFunc = 0L;
    }
    
    public static class NamedDisplay
    {
        final String name;
        final long handle;
        final int hash32;
        int refCount;
        boolean unCloseable;
        Throwable creationStack;
        
        protected NamedDisplay(final String name, final long handle) {
            this.name = name;
            this.handle = handle;
            this.refCount = 0;
            this.unCloseable = false;
            final int n = 31 + (int)handle;
            this.hash32 = (n << 5) - n + (int)(handle >>> 32);
            if (X11Util.DEBUG) {
                this.creationStack = new Throwable("NamedDisplay Created at:");
            }
            else {
                this.creationStack = null;
            }
        }
        
        @Override
        public final int hashCode() {
            return this.hash32;
        }
        
        @Override
        public final boolean equals(final Object o) {
            return this == o || (o instanceof NamedDisplay && this.handle == ((NamedDisplay)o).handle);
        }
        
        public final void addRef() {
            ++this.refCount;
        }
        
        public final void removeRef() {
            --this.refCount;
        }
        
        public final String getName() {
            return this.name;
        }
        
        public final long getHandle() {
            return this.handle;
        }
        
        public final int getRefCount() {
            return this.refCount;
        }
        
        public final void setUncloseable(final boolean unCloseable) {
            this.unCloseable = unCloseable;
        }
        
        public final boolean isUncloseable() {
            return this.unCloseable;
        }
        
        public final Throwable getCreationStack() {
            return this.creationStack;
        }
        
        @Override
        public String toString() {
            return "NamedX11Display[" + this.name + ", 0x" + Long.toHexString(this.handle) + ", refCount " + this.refCount + ", unCloseable " + this.unCloseable + "]";
        }
    }
}
