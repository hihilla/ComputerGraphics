// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import jogamp.common.Debug;
import java.security.cert.Certificate;
import com.jogamp.common.os.Platform;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.util.zip.ZipEntry;
import java.io.File;
import java.util.Enumeration;
import com.jogamp.common.os.NativeLibrary;
import java.util.jar.JarEntry;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import java.net.JarURLConnection;
import java.util.jar.JarFile;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URL;
import com.jogamp.common.net.Uri;

public class JarUtil
{
    private static final boolean DEBUG;
    private static final int BUFFER_SIZE = 4096;
    private static Resolver resolver;
    
    public static void setResolver(final Resolver resolver) throws IllegalArgumentException, IllegalStateException, SecurityException {
        if (resolver == null) {
            throw new IllegalArgumentException("Null Resolver passed");
        }
        if (JarUtil.resolver != null) {
            throw new IllegalStateException("Resolver already set!");
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSetFactory();
        }
        JarUtil.resolver = resolver;
    }
    
    public static boolean hasJarUri(final String s, final ClassLoader classLoader) {
        try {
            return null != getJarUri(s, classLoader);
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public static Uri getJarUri(final String s, final ClassLoader classLoader) throws IllegalArgumentException, IOException, URISyntaxException {
        if (null == s || null == classLoader) {
            throw new IllegalArgumentException("null arguments: clazzBinName " + s + ", cl " + classLoader);
        }
        final URL classURL = IOUtil.getClassURL(s, classLoader);
        final String protocol = classURL.getProtocol();
        Uri uri;
        if (null != JarUtil.resolver && !protocol.equals("jar") && !protocol.equals("file") && !protocol.equals("http") && !protocol.equals("https")) {
            final URL resolve = JarUtil.resolver.resolve(classURL);
            uri = Uri.valueOf(resolve);
            if (JarUtil.DEBUG) {
                System.err.println("getJarUri Resolver: " + classURL + "\n\t-> " + resolve + "\n\t-> " + uri);
            }
        }
        else {
            uri = Uri.valueOf(classURL);
            if (JarUtil.DEBUG) {
                System.err.println("getJarUri Default " + classURL + "\n\t-> " + uri);
            }
        }
        if (!uri.isJarScheme()) {
            throw new IllegalArgumentException("Uri is not using scheme jar: <" + uri + ">");
        }
        if (JarUtil.DEBUG) {
            System.err.println("getJarUri res: " + s + " -> " + classURL + " -> " + uri);
        }
        return uri;
    }
    
    public static Uri.Encoded getJarBasename(final Uri uri) throws IllegalArgumentException {
        if (null == uri) {
            throw new IllegalArgumentException("Uri is null");
        }
        if (!uri.isJarScheme()) {
            throw new IllegalArgumentException("Uri is not using scheme jar: <" + uri + ">");
        }
        final Uri.Encoded schemeSpecificPart = uri.schemeSpecificPart;
        final int lastIndex = schemeSpecificPart.lastIndexOf(33);
        if (0 > lastIndex) {
            throw new IllegalArgumentException("Uri does not contain jar uri terminator '!', in <" + uri + ">");
        }
        final Uri.Encoded substring = schemeSpecificPart.substring(0, lastIndex);
        int n = substring.lastIndexOf(47);
        if (0 > n) {
            n = substring.lastIndexOf(58);
            if (0 > n) {
                throw new IllegalArgumentException("Uri does not contain protocol terminator ':', in <" + uri + ">");
            }
        }
        final Uri.Encoded substring2 = substring.substring(n + 1);
        if (0 >= substring2.lastIndexOf(".jar")) {
            throw new IllegalArgumentException("No Jar name in <" + uri + ">");
        }
        if (JarUtil.DEBUG) {
            System.err.println("getJarName res: " + (Object)substring2);
        }
        return substring2;
    }
    
    public static Uri.Encoded getJarBasename(final String s, final ClassLoader classLoader) throws IllegalArgumentException, IOException, URISyntaxException {
        return getJarBasename(getJarUri(s, classLoader));
    }
    
    public static Uri.Encoded getJarEntry(final Uri uri) {
        if (null == uri) {
            throw new IllegalArgumentException("Uri is null");
        }
        if (!uri.isJarScheme()) {
            throw new IllegalArgumentException("Uri is not a using scheme jar: <" + uri + ">");
        }
        final Uri.Encoded schemeSpecificPart = uri.schemeSpecificPart;
        final int lastIndex = schemeSpecificPart.lastIndexOf(33);
        if (0 <= lastIndex) {
            final Uri.Encoded substring = schemeSpecificPart.substring(lastIndex + 1);
            if (JarUtil.DEBUG) {
                System.err.println("getJarEntry res: " + uri + " -> " + (Object)schemeSpecificPart + " -> " + lastIndex + " -> " + (Object)substring);
            }
            return substring;
        }
        throw new IllegalArgumentException("JAR Uri does not contain jar uri terminator '!', uri <" + uri + ">");
    }
    
    public static Uri getJarFileUri(final String s, final ClassLoader classLoader) throws IllegalArgumentException, IOException, URISyntaxException {
        if (null == s || null == classLoader) {
            throw new IllegalArgumentException("null arguments: clazzBinName " + s + ", cl " + classLoader);
        }
        final Uri cast = Uri.cast("jar:" + getJarUri(s, classLoader).getContainedUri().toString() + "!/");
        if (JarUtil.DEBUG) {
            System.err.println("getJarFileUri res: " + cast);
        }
        return cast;
    }
    
    public static Uri getJarFileUri(final Uri uri, final Uri.Encoded encoded) throws IllegalArgumentException, URISyntaxException {
        if (null == uri || null == encoded) {
            throw new IllegalArgumentException("null arguments: baseUri " + uri + ", jarFileName " + (Object)encoded);
        }
        return Uri.cast("jar:" + uri.toString() + (Object)encoded + "!/");
    }
    
    public static Uri getJarFileUri(final Uri uri) throws IllegalArgumentException, URISyntaxException {
        if (null == uri) {
            throw new IllegalArgumentException("jarSubUri is null");
        }
        return Uri.cast("jar:" + uri.toString() + "!/");
    }
    
    public static Uri getJarFileUri(final Uri.Encoded encoded) throws IllegalArgumentException, URISyntaxException {
        if (null == encoded) {
            throw new IllegalArgumentException("jarSubUriS is null");
        }
        return Uri.cast("jar:" + (Object)encoded + "!/");
    }
    
    public static Uri getJarEntryUri(final Uri uri, final Uri.Encoded encoded) throws IllegalArgumentException, URISyntaxException {
        if (null == encoded) {
            throw new IllegalArgumentException("jarEntry is null");
        }
        return Uri.cast(uri.toString() + (Object)encoded);
    }
    
    public static JarFile getJarFile(final String s, final ClassLoader classLoader) throws IOException, IllegalArgumentException, URISyntaxException {
        return getJarFile(getJarFileUri(s, classLoader));
    }
    
    public static JarFile getJarFile(final Uri uri) throws IOException, IllegalArgumentException, URISyntaxException {
        if (null == uri) {
            throw new IllegalArgumentException("null jarFileUri");
        }
        if (JarUtil.DEBUG) {
            System.err.println("getJarFile.0: " + uri.toString());
        }
        final URL url = uri.toURL();
        if (JarUtil.DEBUG) {
            System.err.println("getJarFile.1: " + url.toString());
        }
        if (url.openConnection() instanceof JarURLConnection) {
            final JarFile jarFile = ((JarURLConnection)url.openConnection()).getJarFile();
            if (JarUtil.DEBUG) {
                System.err.println("getJarFile res: " + jarFile.getName());
            }
            return jarFile;
        }
        if (JarUtil.DEBUG) {
            System.err.println("getJarFile res: NULL");
        }
        return null;
    }
    
    public static URI getRelativeOf(final Class<?> clazz, final String s, final String s2) throws IllegalArgumentException, IOException, URISyntaxException {
        return getRelativeOf(clazz, Uri.Encoded.cast(s), Uri.Encoded.cast(s2)).toURI();
    }
    
    public static Uri getRelativeOf(final Class<?> clazz, final Uri.Encoded encoded, final Uri.Encoded encoded2) throws IllegalArgumentException, IOException, URISyntaxException {
        final Uri jarUri = getJarUri(clazz.getName(), clazz.getClassLoader());
        if (JarUtil.DEBUG) {
            System.err.println("JarUtil.getRelativeOf: (classFromJavaJar " + clazz + ", classJarUri " + jarUri + ", cutOffInclSubDir " + (Object)encoded + ", relResPath " + (Object)encoded2 + "): ");
        }
        final Uri containedUri = jarUri.getContainedUri();
        if (null == containedUri) {
            throw new IllegalArgumentException("JarSubUri is null of: " + jarUri);
        }
        final Uri.Encoded encoded3 = containedUri.getDirectory().getEncoded();
        if (JarUtil.DEBUG) {
            System.err.println("JarUtil.getRelativeOf: uri " + containedUri.toString() + " -> " + (Object)encoded3);
        }
        Uri.Encoded encoded4;
        if (encoded3.endsWith(encoded.get())) {
            encoded4 = encoded3.concat(encoded2);
        }
        else {
            encoded4 = encoded3.concat(encoded).concat(encoded2);
        }
        if (JarUtil.DEBUG) {
            System.err.println("JarUtil.getRelativeOf: ...  -> " + (Object)encoded4);
        }
        final Uri jarFileUri = getJarFileUri(encoded4);
        if (JarUtil.DEBUG) {
            System.err.println("JarUtil.getRelativeOf: fin " + jarFileUri);
        }
        return jarFileUri;
    }
    
    public static Map<String, String> getNativeLibNames(final JarFile jarFile) {
        if (JarUtil.DEBUG) {
            System.err.println("JarUtil: getNativeLibNames: " + jarFile);
        }
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final String name = entries.nextElement().getName();
            final String validNativeLibraryName = NativeLibrary.isValidNativeLibraryName(name, false);
            if (null != validNativeLibraryName) {
                hashMap.put(validNativeLibraryName, name);
            }
        }
        return hashMap;
    }
    
    public static final int extract(final File file, final Map<String, String> map, final JarFile jarFile, final String s, final boolean b, final boolean b2, final boolean b3) throws IOException {
        if (JarUtil.DEBUG) {
            System.err.println("JarUtil: extract: " + jarFile.getName() + " -> " + file + ", extractNativeLibraries " + b + " (" + s + ")" + ", extractClassFiles " + b2 + ", extractOtherFiles " + b3);
        }
        int n = 0;
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry jarEntry = entries.nextElement();
            final String name = jarEntry.getName();
            final String validNativeLibraryName = NativeLibrary.isValidNativeLibraryName(name, false);
            final boolean b4 = null != validNativeLibraryName;
            if (b4) {
                if (!b) {
                    if (JarUtil.DEBUG) {
                        System.err.println("JarUtil: JarEntry : " + name + " native-lib skipped, skip all native libs");
                        continue;
                    }
                    continue;
                }
                else if (null != s) {
                    String slashify;
                    String dirname;
                    try {
                        slashify = IOUtil.slashify(s, false, true);
                        dirname = IOUtil.getDirname(name);
                    }
                    catch (URISyntaxException ex) {
                        throw new IOException(ex);
                    }
                    if (!slashify.equals(dirname)) {
                        if (JarUtil.DEBUG) {
                            System.err.println("JarUtil: JarEntry : " + name + " native-lib skipped, not in path: " + slashify);
                            continue;
                        }
                        continue;
                    }
                }
            }
            final boolean endsWith = name.endsWith(".class");
            if (endsWith && !b2) {
                if (!JarUtil.DEBUG) {
                    continue;
                }
                System.err.println("JarUtil: JarEntry : " + name + " class-file skipped");
            }
            else if (!b4 && !endsWith && !b3) {
                if (!JarUtil.DEBUG) {
                    continue;
                }
                System.err.println("JarUtil: JarEntry : " + name + " other-file skipped");
            }
            else {
                final boolean endsWith2 = name.endsWith("/");
                final boolean b5 = name.indexOf(47) == -1 && name.indexOf(File.separatorChar) == -1;
                if (JarUtil.DEBUG) {
                    System.err.println("JarUtil: JarEntry : isNativeLib " + b4 + ", isClassFile " + endsWith + ", isDir " + endsWith2 + ", isRootEntry " + b5);
                }
                final File file2 = new File(file, name);
                if (endsWith2) {
                    if (JarUtil.DEBUG) {
                        System.err.println("JarUtil: MKDIR: " + name + " -> " + file2);
                    }
                    file2.mkdirs();
                }
                else {
                    final File file3 = new File(file2.getParent());
                    if (!file3.exists()) {
                        if (JarUtil.DEBUG) {
                            System.err.println("JarUtil: MKDIR (parent): " + name + " -> " + file3);
                        }
                        file3.mkdirs();
                    }
                    final BufferedInputStream bufferedInputStream = new BufferedInputStream(jarFile.getInputStream(jarEntry));
                    final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                    int copyStream2Stream = -1;
                    try {
                        copyStream2Stream = IOUtil.copyStream2Stream(4096, bufferedInputStream, bufferedOutputStream, -1);
                    }
                    finally {
                        bufferedInputStream.close();
                        bufferedOutputStream.close();
                    }
                    boolean b6 = false;
                    if (copyStream2Stream > 0) {
                        ++n;
                        if (b4 && (b5 || !map.containsKey(validNativeLibraryName))) {
                            map.put(validNativeLibraryName, file2.getAbsolutePath());
                            b6 = true;
                            fixNativeLibAttribs(file2);
                        }
                    }
                    if (!JarUtil.DEBUG) {
                        continue;
                    }
                    System.err.println("JarUtil: EXTRACT[" + n + "]: [" + validNativeLibraryName + " -> ] " + name + " -> " + file2 + ": " + copyStream2Stream + " bytes, addedAsNativeLib: " + b6);
                }
            }
        }
        return n;
    }
    
    private static final void fixNativeLibAttribs(final File file) {
        if (Platform.OSType.MACOS == Platform.getOSType()) {
            final String absolutePath = file.getAbsolutePath();
            try {
                fixNativeLibAttribs(absolutePath);
                if (JarUtil.DEBUG) {
                    System.err.println("JarUtil.fixNativeLibAttribs: " + absolutePath + " - OK");
                }
            }
            catch (Throwable t) {
                if (JarUtil.DEBUG) {
                    System.err.println("JarUtil.fixNativeLibAttribs: " + absolutePath + " - " + t.getClass().getSimpleName() + ": " + t.getMessage());
                }
            }
        }
    }
    
    private static native boolean fixNativeLibAttribs(final String p0);
    
    public static final void validateCertificates(final Certificate[] array, final JarFile jarFile) throws IOException, SecurityException {
        if (JarUtil.DEBUG) {
            System.err.println("JarUtil: validateCertificates: " + jarFile.getName());
        }
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Null certificates passed");
        }
        final byte[] array2 = new byte[1024];
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry jarEntry = entries.nextElement();
            if (!jarEntry.isDirectory() && !jarEntry.getName().startsWith("META-INF/")) {
                validateCertificate(array, jarFile, jarEntry, array2);
            }
        }
    }
    
    private static final void validateCertificate(final Certificate[] array, final JarFile jarFile, final JarEntry jarEntry, final byte[] array2) throws IOException, SecurityException {
        if (JarUtil.DEBUG) {
            System.err.println("JarUtil: validate JarEntry : " + jarEntry.getName());
        }
        final InputStream inputStream = jarFile.getInputStream(jarEntry);
        try {
            while (inputStream.read(array2) > 0) {}
        }
        finally {
            inputStream.close();
        }
        final Certificate[] certificates = jarEntry.getCertificates();
        if (certificates == null || certificates.length == 0) {
            throw new SecurityException("no certificate for " + jarEntry.getName() + " in " + jarFile.getName());
        }
        if (!SecurityUtil.equals(array, certificates)) {
            throw new SecurityException("certificates not equal for " + jarEntry.getName() + " in " + jarFile.getName());
        }
    }
    
    static {
        DEBUG = Debug.debug("JarUtil");
    }
    
    public interface Resolver
    {
        URL resolve(final URL p0);
    }
}
