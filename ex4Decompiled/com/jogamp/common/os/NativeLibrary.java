// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.os;

import com.jogamp.common.util.cache.TempJarCache;
import java.util.StringTokenizer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.io.File;
import java.util.ArrayList;
import java.net.URISyntaxException;
import com.jogamp.common.util.IOUtil;
import com.jogamp.common.ExceptionUtils;
import java.util.Iterator;
import jogamp.common.os.DynamicLinkerImpl;
import java.util.List;
import jogamp.common.os.PosixDynamicLinkerImpl;
import jogamp.common.os.BionicDynamicLinker64BitImpl;
import jogamp.common.os.BionicDynamicLinker32bitImpl;
import jogamp.common.os.MacOSXDynamicLinkerImpl;
import jogamp.common.os.WindowsDynamicLinkerImpl;
import jogamp.common.os.PlatformPropsImpl;
import java.lang.reflect.Method;

public final class NativeLibrary implements DynamicLookupHelper
{
    private static final String[] prefixes;
    private static final String[] suffixes;
    private static final boolean isOSX;
    private final DynamicLinker dynLink;
    private long libraryHandle;
    private final String libraryPath;
    private final boolean global;
    private static boolean initializedFindLibraryMethod;
    private static Method findLibraryMethod;
    
    private NativeLibrary(final DynamicLinker dynLink, final long libraryHandle, final String libraryPath, final boolean global) {
        this.dynLink = dynLink;
        this.libraryHandle = libraryHandle;
        this.libraryPath = libraryPath;
        this.global = global;
        if (NativeLibrary.DEBUG) {
            System.err.println("NativeLibrary.open(): Successfully loaded: " + this);
        }
    }
    
    @Override
    public final String toString() {
        return "NativeLibrary[" + this.dynLink.getClass().getSimpleName() + ", " + this.libraryPath + ", 0x" + Long.toHexString(this.libraryHandle) + ", global " + this.global + "]";
    }
    
    public static final NativeLibrary open(final String s, final ClassLoader classLoader) throws SecurityException {
        return open(s, s, s, true, classLoader, true);
    }
    
    public static final NativeLibrary open(final String s, final ClassLoader classLoader, final boolean b) throws SecurityException {
        return open(s, s, s, true, classLoader, b);
    }
    
    public static final NativeLibrary open(final String s, final String s2, final String s3, final boolean b, final ClassLoader classLoader) throws SecurityException {
        return open(s, s2, s3, b, classLoader, true);
    }
    
    public static final NativeLibrary open(final String s, final String s2, final String s3, final boolean b, final ClassLoader classLoader, final boolean b2) throws SecurityException {
        final List<String> enumerateLibraryPaths = enumerateLibraryPaths(s, s2, s3, b, classLoader);
        Platform.initSingleton();
        DynamicLinkerImpl dynamicLinkerImpl = null;
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS: {
                dynamicLinkerImpl = new WindowsDynamicLinkerImpl();
                break;
            }
            case MACOS: {
                dynamicLinkerImpl = new MacOSXDynamicLinkerImpl();
                break;
            }
            case ANDROID: {
                if (PlatformPropsImpl.CPU_ARCH.is32Bit) {
                    dynamicLinkerImpl = new BionicDynamicLinker32bitImpl();
                    break;
                }
                dynamicLinkerImpl = new BionicDynamicLinker64BitImpl();
                break;
            }
            default: {
                dynamicLinkerImpl = new PosixDynamicLinkerImpl();
                break;
            }
        }
        for (final String s4 : enumerateLibraryPaths) {
            if (NativeLibrary.DEBUG) {
                System.err.println("NativeLibrary.open(global " + b2 + "): Trying to load " + s4);
            }
            Throwable t = null;
            long n;
            try {
                if (b2) {
                    n = dynamicLinkerImpl.openLibraryGlobal(s4, NativeLibrary.DEBUG);
                }
                else {
                    n = dynamicLinkerImpl.openLibraryLocal(s4, NativeLibrary.DEBUG);
                }
            }
            catch (Throwable t2) {
                t = t2;
                n = 0L;
            }
            if (0L != n) {
                return new NativeLibrary(dynamicLinkerImpl, n, s4, b2);
            }
            if (!NativeLibrary.DEBUG) {
                continue;
            }
            if (null != t) {
                System.err.println("NativeLibrary.open: Caught " + t.getClass().getSimpleName() + ": " + t.getMessage());
            }
            String lastError;
            try {
                lastError = dynamicLinkerImpl.getLastError();
            }
            catch (Throwable t3) {
                lastError = null;
            }
            System.err.println("NativeLibrary.open: Last error " + lastError);
            if (null == t) {
                continue;
            }
            t.printStackTrace();
        }
        if (NativeLibrary.DEBUG) {
            System.err.println("NativeLibrary.open(global " + b2 + "): Did not succeed in loading (" + s + ", " + s2 + ", " + s3 + ")");
        }
        return null;
    }
    
    @Override
    public final void claimAllLinkPermission() throws SecurityException {
        this.dynLink.claimAllLinkPermission();
    }
    
    @Override
    public final void releaseAllLinkPermission() throws SecurityException {
        this.dynLink.releaseAllLinkPermission();
    }
    
    @Override
    public final long dynamicLookupFunction(final String s) throws SecurityException {
        if (0L == this.libraryHandle) {
            throw new RuntimeException("Library is not open");
        }
        return this.dynLink.lookupSymbol(this.libraryHandle, s);
    }
    
    @Override
    public final boolean isFunctionAvailable(final String s) throws SecurityException {
        if (0L == this.libraryHandle) {
            throw new RuntimeException("Library is not open");
        }
        return 0L != this.dynLink.lookupSymbol(this.libraryHandle, s);
    }
    
    public final long dynamicLookupFunctionGlobal(final String s) throws SecurityException {
        return this.dynLink.lookupSymbolGlobal(s);
    }
    
    final DynamicLinker getDynamicLinker() {
        return this.dynLink;
    }
    
    public final long getLibraryHandle() {
        return this.libraryHandle;
    }
    
    public final String getLibraryPath() {
        return this.libraryPath;
    }
    
    public final void close() throws SecurityException {
        if (NativeLibrary.DEBUG) {
            System.err.println("NativeLibrary.close(): closing " + this);
        }
        if (0L == this.libraryHandle) {
            throw new RuntimeException("Library already closed");
        }
        final long libraryHandle = this.libraryHandle;
        this.libraryHandle = 0L;
        this.dynLink.closeLibrary(libraryHandle, NativeLibrary.DEBUG);
        if (NativeLibrary.DEBUG) {
            System.err.println("NativeLibrary.close(): Successfully closed " + this);
            ExceptionUtils.dumpStack(System.err);
        }
    }
    
    public static final String isValidNativeLibraryName(final String s, final boolean b) {
        String basename;
        try {
            basename = IOUtil.getBasename(s);
        }
        catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
        final String s2 = b ? basename : basename.toLowerCase();
        int n = -1;
        for (int n2 = 0; n2 < NativeLibrary.prefixes.length && 0 > n; ++n2) {
            if (s2.startsWith(NativeLibrary.prefixes[n2])) {
                n = n2;
            }
        }
        if (0 <= n) {
            for (int i = 0; i < NativeLibrary.suffixes.length; ++i) {
                if (s2.endsWith(NativeLibrary.suffixes[i])) {
                    return basename.substring(NativeLibrary.prefixes[n].length(), basename.length() - NativeLibrary.suffixes[i].length());
                }
            }
        }
        return null;
    }
    
    public static final List<String> enumerateLibraryPaths(final String s, final String s2, final String s3, final ClassLoader classLoader) {
        return enumerateLibraryPaths(s, s2, s3, false, false, classLoader);
    }
    
    public static final List<String> enumerateLibraryPaths(final String s, final String s2, final String s3, final boolean b, final ClassLoader classLoader) {
        return enumerateLibraryPaths(s, s2, s3, true, b, classLoader);
    }
    
    private static final List<String> enumerateLibraryPaths(final String s, final String s2, final String s3, final boolean b, final boolean b2, final ClassLoader classLoader) {
        final ArrayList<String> list = new ArrayList<String>();
        final String selectName = selectName(s, s2, s3);
        if (selectName == null) {
            return list;
        }
        if (new File(selectName).isAbsolute()) {
            list.add(selectName);
            return list;
        }
        final String[] buildNames = buildNames(selectName);
        if (b && b2) {
            for (int i = 0; i < buildNames.length; ++i) {
                list.add(buildNames[i]);
            }
            if (NativeLibrary.isOSX) {
                addPaths("/Library/Frameworks/" + selectName + ".Framework", buildNames, list);
                addPaths("/System/Library/Frameworks/" + selectName + ".Framework", buildNames, list);
            }
        }
        final String library = findLibrary(selectName, classLoader);
        if (library != null) {
            list.add(library);
        }
        final String[] array = AccessController.doPrivileged((PrivilegedAction<String[]>)new PrivilegedAction<String[]>() {
            @Override
            public String[] run() {
                int n = 0;
                final String property = System.getProperty("java.library.path");
                if (null != property) {
                    ++n;
                }
                String property2;
                if (b) {
                    property2 = System.getProperty("sun.boot.library.path");
                    if (null != property2) {
                        ++n;
                    }
                }
                else {
                    property2 = null;
                }
                final String[] array = new String[n];
                int n2 = 0;
                if (null != property2 && b2) {
                    array[n2++] = property2;
                }
                if (null != property) {
                    array[n2++] = property;
                }
                if (null != property2 && !b2) {
                    array[n2++] = property2;
                }
                return array;
            }
        });
        if (null != array) {
            for (int j = 0; j < array.length; ++j) {
                final StringTokenizer stringTokenizer = new StringTokenizer(array[j], File.pathSeparator);
                while (stringTokenizer.hasMoreTokens()) {
                    addPaths(stringTokenizer.nextToken(), buildNames, list);
                }
            }
        }
        final String s4 = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getProperty("user.dir");
            }
        });
        addPaths(s4, buildNames, list);
        addPaths(s4 + File.separator + "natives" + File.separator + PlatformPropsImpl.os_and_arch + File.separator, buildNames, list);
        if (b && !b2) {
            for (int k = 0; k < buildNames.length; ++k) {
                list.add(buildNames[k]);
            }
            if (NativeLibrary.isOSX) {
                addPaths("/Library/Frameworks/" + selectName + ".Framework", buildNames, list);
                addPaths("/System/Library/Frameworks/" + selectName + ".Framework", buildNames, list);
            }
        }
        return list;
    }
    
    private static final String selectName(final String s, final String s2, final String s3) {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS: {
                return s;
            }
            case MACOS: {
                return s3;
            }
            default: {
                return s2;
            }
        }
    }
    
    private static final String[] buildNames(final String s) {
        String lowerCase;
        try {
            lowerCase = IOUtil.getBasename(s).toLowerCase();
        }
        catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
        int n = -1;
        for (int n2 = 0; n2 < NativeLibrary.prefixes.length && 0 > n; ++n2) {
            if (lowerCase.startsWith(NativeLibrary.prefixes[n2])) {
                n = n2;
            }
        }
        if (0 <= n) {
            for (int i = 0; i < NativeLibrary.suffixes.length; ++i) {
                if (lowerCase.endsWith(NativeLibrary.suffixes[i])) {
                    return new String[] { s };
                }
            }
            int index = -1;
            for (int n3 = 0; n3 < NativeLibrary.suffixes.length && 0 > index; index = lowerCase.indexOf(NativeLibrary.suffixes[n3]), ++n3) {}
            boolean b = true;
            if (index >= 0) {
                for (int j = index + NativeLibrary.suffixes[0].length(); j < s.length(); ++j) {
                    final char char1 = s.charAt(j);
                    if (char1 != '.' && (char1 < '0' || char1 > '9')) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    return new String[] { s };
                }
            }
        }
        final String[] array = new String[NativeLibrary.prefixes.length * NativeLibrary.suffixes.length + (NativeLibrary.isOSX ? 1 : 0)];
        int n4 = 0;
        for (int k = 0; k < NativeLibrary.prefixes.length; ++k) {
            for (int l = 0; l < NativeLibrary.suffixes.length; ++l) {
                array[n4++] = NativeLibrary.prefixes[k] + s + NativeLibrary.suffixes[l];
            }
        }
        if (NativeLibrary.isOSX) {
            array[n4++] = s;
        }
        return array;
    }
    
    private static final void addPaths(final String s, final String[] array, final List<String> list) {
        for (int i = 0; i < array.length; ++i) {
            list.add(s + File.separator + array[i]);
        }
    }
    
    private static final String findLibraryImpl(final String s, final ClassLoader classLoader) {
        if (classLoader == null) {
            return null;
        }
        if (!NativeLibrary.initializedFindLibraryMethod) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        NativeLibrary.findLibraryMethod = ClassLoader.class.getDeclaredMethod("findLibrary", String.class);
                        NativeLibrary.findLibraryMethod.setAccessible(true);
                    }
                    catch (Exception ex) {}
                    NativeLibrary.initializedFindLibraryMethod = true;
                    return null;
                }
            });
        }
        if (NativeLibrary.findLibraryMethod != null) {
            try {
                return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
                    @Override
                    public String run() {
                        try {
                            return (String)NativeLibrary.findLibraryMethod.invoke(classLoader, s);
                        }
                        catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
            catch (Exception ex) {
                if (NativeLibrary.DEBUG) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static final String findLibrary(final String s, final ClassLoader classLoader) {
        String s2 = null;
        if (TempJarCache.isInitialized()) {
            s2 = TempJarCache.findLibrary(s);
            if (NativeLibrary.DEBUG) {
                System.err.println("NativeLibrary.findLibrary(<" + s + ">) (TempJarCache): " + s2);
            }
        }
        if (null == s2) {
            s2 = findLibraryImpl(s, classLoader);
            if (NativeLibrary.DEBUG) {
                System.err.println("NativeLibrary.findLibrary(<" + s + ">, " + classLoader + ") (CL): " + s2);
            }
        }
        return s2;
    }
    
    static {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS: {
                prefixes = new String[] { "" };
                suffixes = new String[] { ".dll" };
                isOSX = false;
                break;
            }
            case MACOS: {
                prefixes = new String[] { "lib" };
                suffixes = new String[] { ".dylib", ".jnilib" };
                isOSX = true;
                break;
            }
            default: {
                prefixes = new String[] { "lib" };
                suffixes = new String[] { ".so" };
                isOSX = false;
                break;
            }
        }
        NativeLibrary.initializedFindLibraryMethod = false;
        NativeLibrary.findLibraryMethod = null;
    }
}
