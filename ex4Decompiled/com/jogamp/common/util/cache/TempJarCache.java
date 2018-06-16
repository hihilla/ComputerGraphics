// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.cache;

import jogamp.common.Debug;
import java.security.cert.Certificate;
import com.jogamp.common.util.SecurityUtil;
import com.jogamp.common.os.NativeLibrary;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.jar.JarFile;
import com.jogamp.common.util.JarUtil;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import com.jogamp.common.net.Uri;
import java.util.Map;

public class TempJarCache
{
    private static final boolean DEBUG;
    private static Map<String, String> nativeLibMap;
    private static Map<Uri, LoadState> nativeLibJars;
    private static Map<Uri, LoadState> classFileJars;
    private static Map<Uri, LoadState> resourceFileJars;
    private static TempFileCache tmpFileCache;
    private static volatile boolean staticInitError;
    private static volatile boolean isInit;
    
    private static boolean testLoadState(final LoadState loadState, final LoadState loadState2) {
        if (null == loadState) {
            return null == loadState2;
        }
        return loadState.compliesWith(loadState2);
    }
    
    public static boolean initSingleton() {
        if (!TempJarCache.isInit) {
            synchronized (TempJarCache.class) {
                if (!TempJarCache.isInit) {
                    if (!(TempJarCache.staticInitError = !TempFileCache.initSingleton())) {
                        TempJarCache.tmpFileCache = new TempFileCache();
                        TempJarCache.staticInitError = !TempJarCache.tmpFileCache.isValid();
                    }
                    if (!TempJarCache.staticInitError) {
                        TempJarCache.nativeLibMap = new HashMap<String, String>();
                        TempJarCache.nativeLibJars = new HashMap<Uri, LoadState>();
                        TempJarCache.classFileJars = new HashMap<Uri, LoadState>();
                        TempJarCache.resourceFileJars = new HashMap<Uri, LoadState>();
                    }
                    if (TempJarCache.DEBUG) {
                        final File file = (null != TempJarCache.tmpFileCache) ? TempJarCache.tmpFileCache.getTempDir() : null;
                        System.err.println("TempJarCache.initSingleton(): ok " + !TempJarCache.staticInitError + ", " + ((null != file) ? file.getAbsolutePath() : null));
                    }
                    TempJarCache.isInit = true;
                }
            }
        }
        return !TempJarCache.staticInitError;
    }
    
    private static boolean isInitializedImpl() {
        if (!TempJarCache.isInit) {
            synchronized (TempJarCache.class) {
                if (!TempJarCache.isInit) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isInitialized() {
        return isInitializedImpl() && !TempJarCache.staticInitError;
    }
    
    static void checkInitialized() {
        if (!isInitializedImpl()) {
            throw new RuntimeException("initSingleton() has to be called first.");
        }
        if (TempJarCache.staticInitError) {
            throw new RuntimeException("initSingleton() failed.");
        }
    }
    
    public static TempFileCache getTempFileCache() {
        checkInitialized();
        return TempJarCache.tmpFileCache;
    }
    
    public static synchronized boolean checkNativeLibs(final Uri uri, final LoadState loadState) throws IOException {
        checkInitialized();
        if (null == uri) {
            throw new IllegalArgumentException("jarUri is null");
        }
        return testLoadState(TempJarCache.nativeLibJars.get(uri), loadState);
    }
    
    public static synchronized boolean checkClasses(final Uri uri, final LoadState loadState) throws IOException {
        checkInitialized();
        if (null == uri) {
            throw new IllegalArgumentException("jarUri is null");
        }
        return testLoadState(TempJarCache.classFileJars.get(uri), loadState);
    }
    
    public static synchronized boolean checkResources(final Uri uri, final LoadState loadState) throws IOException {
        checkInitialized();
        if (null == uri) {
            throw new IllegalArgumentException("jarUri is null");
        }
        return testLoadState(TempJarCache.resourceFileJars.get(uri), loadState);
    }
    
    public static final synchronized boolean addNativeLibs(final Class<?> clazz, final Uri uri, final String s) throws IOException, SecurityException, IllegalArgumentException, URISyntaxException {
        checkInitialized();
        final LoadState loadState = TempJarCache.nativeLibJars.get(uri);
        if (!testLoadState(loadState, LoadState.LOOKED_UP)) {
            TempJarCache.nativeLibJars.put(uri, LoadState.LOOKED_UP);
            final JarFile jarFile = JarUtil.getJarFile(uri);
            if (TempJarCache.DEBUG) {
                System.err.println("TempJarCache: addNativeLibs: " + uri + ": nativeJar " + jarFile.getName() + " (NEW)");
            }
            validateCertificates(clazz, jarFile);
            final int extract = JarUtil.extract(TempJarCache.tmpFileCache.getTempDir(), TempJarCache.nativeLibMap, jarFile, s, true, false, false);
            TempJarCache.nativeLibJars.put(uri, LoadState.LOADED);
            return extract > 0;
        }
        if (testLoadState(loadState, LoadState.LOADED)) {
            if (TempJarCache.DEBUG) {
                System.err.println("TempJarCache: addNativeLibs: " + uri + ": nativeJar " + uri + " (REUSE)");
            }
            return true;
        }
        throw new IOException("TempJarCache: addNativeLibs: " + uri + ", previous load attempt failed");
    }
    
    public static final synchronized void addClasses(final Class<?> clazz, final Uri uri) throws IOException, SecurityException, IllegalArgumentException, URISyntaxException {
        checkInitialized();
        final LoadState loadState = TempJarCache.classFileJars.get(uri);
        if (!testLoadState(loadState, LoadState.LOOKED_UP)) {
            TempJarCache.classFileJars.put(uri, LoadState.LOOKED_UP);
            final JarFile jarFile = JarUtil.getJarFile(uri);
            if (TempJarCache.DEBUG) {
                System.err.println("TempJarCache: addClasses: " + uri + ": nativeJar " + jarFile.getName());
            }
            validateCertificates(clazz, jarFile);
            JarUtil.extract(TempJarCache.tmpFileCache.getTempDir(), null, jarFile, null, false, true, false);
            TempJarCache.classFileJars.put(uri, LoadState.LOADED);
        }
        else if (!testLoadState(loadState, LoadState.LOADED)) {
            throw new IOException("TempJarCache: addClasses: " + uri + ", previous load attempt failed");
        }
    }
    
    public static final synchronized void addResources(final Class<?> clazz, final URI uri) throws IOException, SecurityException, IllegalArgumentException, URISyntaxException {
        addResources(clazz, Uri.valueOf(uri));
    }
    
    public static final synchronized void addResources(final Class<?> clazz, final Uri uri) throws IOException, SecurityException, IllegalArgumentException, URISyntaxException {
        checkInitialized();
        final LoadState loadState = TempJarCache.resourceFileJars.get(uri);
        if (!testLoadState(loadState, LoadState.LOOKED_UP)) {
            TempJarCache.resourceFileJars.put(uri, LoadState.LOOKED_UP);
            final JarFile jarFile = JarUtil.getJarFile(uri);
            if (TempJarCache.DEBUG) {
                System.err.println("TempJarCache: addResources: " + uri + ": nativeJar " + jarFile.getName());
            }
            validateCertificates(clazz, jarFile);
            JarUtil.extract(TempJarCache.tmpFileCache.getTempDir(), null, jarFile, null, false, false, true);
            TempJarCache.resourceFileJars.put(uri, LoadState.LOADED);
        }
        else if (!testLoadState(loadState, LoadState.LOADED)) {
            throw new IOException("TempJarCache: addResources: " + uri + ", previous load attempt failed");
        }
    }
    
    public static final synchronized void addAll(final Class<?> clazz, final Uri uri) throws IOException, SecurityException, IllegalArgumentException, URISyntaxException {
        checkInitialized();
        if (null == uri) {
            throw new IllegalArgumentException("jarUri is null");
        }
        final LoadState loadState = TempJarCache.nativeLibJars.get(uri);
        final LoadState loadState2 = TempJarCache.classFileJars.get(uri);
        final LoadState loadState3 = TempJarCache.resourceFileJars.get(uri);
        if (!testLoadState(loadState, LoadState.LOOKED_UP) || !testLoadState(loadState2, LoadState.LOOKED_UP) || !testLoadState(loadState3, LoadState.LOOKED_UP)) {
            final boolean b = !testLoadState(loadState, LoadState.LOADED);
            final boolean b2 = !testLoadState(loadState2, LoadState.LOADED);
            final boolean b3 = !testLoadState(loadState3, LoadState.LOOKED_UP);
            if (b) {
                TempJarCache.nativeLibJars.put(uri, LoadState.LOOKED_UP);
            }
            if (b2) {
                TempJarCache.classFileJars.put(uri, LoadState.LOOKED_UP);
            }
            if (b3) {
                TempJarCache.resourceFileJars.put(uri, LoadState.LOOKED_UP);
            }
            final JarFile jarFile = JarUtil.getJarFile(uri);
            if (TempJarCache.DEBUG) {
                System.err.println("TempJarCache: addAll: " + uri + ": nativeJar " + jarFile.getName());
            }
            validateCertificates(clazz, jarFile);
            JarUtil.extract(TempJarCache.tmpFileCache.getTempDir(), TempJarCache.nativeLibMap, jarFile, null, b, b2, b3);
            if (b) {
                TempJarCache.nativeLibJars.put(uri, LoadState.LOADED);
            }
            if (b2) {
                TempJarCache.classFileJars.put(uri, LoadState.LOADED);
            }
            if (b3) {
                TempJarCache.resourceFileJars.put(uri, LoadState.LOADED);
            }
        }
        else if (!testLoadState(loadState, LoadState.LOADED) || !testLoadState(loadState2, LoadState.LOADED) || !testLoadState(loadState3, LoadState.LOADED)) {
            throw new IOException("TempJarCache: addAll: " + uri + ", previous load attempt failed");
        }
    }
    
    public static final synchronized String findLibrary(final String s) {
        checkInitialized();
        String absolutePath = TempJarCache.nativeLibMap.get(s);
        if (null == absolutePath && null != NativeLibrary.isValidNativeLibraryName(s, false)) {
            final File file = new File(TempJarCache.tmpFileCache.getTempDir(), s);
            if (file.exists()) {
                absolutePath = file.getAbsolutePath();
            }
        }
        return absolutePath;
    }
    
    public static final synchronized String findResource(final String s) {
        checkInitialized();
        final File file = new File(TempJarCache.tmpFileCache.getTempDir(), s);
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }
    
    public static final synchronized URI getResource(final String s) throws URISyntaxException {
        return getResourceUri(s).toURI();
    }
    
    public static final synchronized Uri getResourceUri(final String s) throws URISyntaxException {
        checkInitialized();
        final File file = new File(TempJarCache.tmpFileCache.getTempDir(), s);
        if (file.exists()) {
            return Uri.valueOf(file);
        }
        return null;
    }
    
    private static void validateCertificates(final Class<?> clazz, final JarFile jarFile) throws IOException, SecurityException {
        if (null == clazz) {
            throw new IllegalArgumentException("certClass is null");
        }
        final Certificate[] certs = SecurityUtil.getCerts(clazz);
        if (null != certs) {
            JarUtil.validateCertificates(certs, jarFile);
            if (TempJarCache.DEBUG) {
                System.err.println("TempJarCache: validateCertificates: OK - Matching rootCerts in given class " + clazz.getName() + ", nativeJar " + jarFile.getName());
            }
        }
        else if (TempJarCache.DEBUG) {
            System.err.println("TempJarCache: validateCertificates: OK - No rootCerts in given class " + clazz.getName() + ", nativeJar " + jarFile.getName());
        }
    }
    
    static {
        DEBUG = Debug.debug("TempJarCache");
        TempJarCache.staticInitError = false;
        TempJarCache.isInit = false;
    }
    
    public enum LoadState
    {
        LOOKED_UP, 
        LOADED;
        
        public boolean compliesWith(final LoadState loadState) {
            return null != loadState && this.compareTo(loadState) >= 0;
        }
    }
}
