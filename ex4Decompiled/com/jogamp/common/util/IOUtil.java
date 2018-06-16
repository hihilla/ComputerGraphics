// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import jogamp.common.Debug;
import java.io.PrintStream;
import java.io.Closeable;
import java.security.Permission;
import java.io.FilePermission;
import jogamp.common.os.AndroidUtils;
import java.io.FileWriter;
import java.io.SyncFailedException;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.common.net.Uri;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.net.AssetURLContext;
import java.net.URL;
import java.net.URISyntaxException;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;
import java.io.Reader;
import com.jogamp.common.os.Platform;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URLConnection;
import java.lang.reflect.Constructor;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

public class IOUtil
{
    public static final boolean DEBUG;
    private static final boolean DEBUG_EXE;
    private static final boolean DEBUG_EXE_NOSTREAM;
    private static final boolean DEBUG_EXE_EXISTING_FILE;
    private static final String java_io_tmpdir_propkey = "java.io.tmpdir";
    private static final String user_home_propkey = "user.home";
    private static final String XDG_CACHE_HOME_envkey = "XDG_CACHE_HOME";
    public static final String tmpSubDir = "jogamp";
    private static final Pattern patternSingleBS;
    public static final Pattern patternSpaceEnc;
    private static final Object exeTestLock;
    private static WeakReference<byte[]> exeTestCodeRef;
    private static File tempRootExec;
    private static File tempRootNoexec;
    private static volatile boolean tempRootSet;
    
    private static final Constructor<?> getFOSCtor() {
        Constructor<?> constructor;
        Throwable t;
        try {
            constructor = ReflectionUtil.getConstructor("java.io.FileOutputStream", new Class[] { File.class }, true, IOUtil.class.getClassLoader());
            t = null;
        }
        catch (Throwable t2) {
            constructor = null;
            t = t2;
        }
        if (IOUtil.DEBUG) {
            System.err.println("IOUtil: java.io.FileOutputStream available: " + (null != constructor));
            if (null != t) {
                t.printStackTrace();
            }
        }
        return constructor;
    }
    
    public static int copyURLConn2File(final URLConnection urlConnection, final File file) throws IOException {
        urlConnection.connect();
        int copyStream2File = 0;
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
        try {
            copyStream2File = copyStream2File(bufferedInputStream, file, urlConnection.getContentLength());
        }
        finally {
            bufferedInputStream.close();
        }
        return copyStream2File;
    }
    
    public static int copyStream2File(final InputStream inputStream, final File file, int copyStream2Stream) throws IOException {
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        try {
            copyStream2Stream = copyStream2Stream(inputStream, bufferedOutputStream, copyStream2Stream);
        }
        finally {
            bufferedOutputStream.close();
        }
        return copyStream2Stream;
    }
    
    public static int copyStream2Stream(final InputStream inputStream, final OutputStream outputStream, final int n) throws IOException {
        return copyStream2Stream(Platform.getMachineDataInfo().pageSizeInBytes(), inputStream, outputStream, n);
    }
    
    public static int copyStream2Stream(final int n, final InputStream inputStream, final OutputStream outputStream, final int n2) throws IOException {
        final byte[] array = new byte[n];
        int n3 = 0;
        int read;
        while ((read = inputStream.read(array)) != -1) {
            outputStream.write(array, 0, read);
            n3 += read;
        }
        return n3;
    }
    
    public static StringBuilder appendCharStream(final StringBuilder sb, final Reader reader) throws IOException {
        final char[] array = new char[1024];
        int read;
        while (0 < (read = reader.read(array))) {
            sb.append(array, 0, read);
        }
        return sb;
    }
    
    public static byte[] copyStream2ByteArray(InputStream inputStream) throws IOException {
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        int n = 0;
        int n2 = inputStream.available();
        byte[] array = new byte[n2];
        int read;
        do {
            if (n + n2 > array.length) {
                final byte[] array2 = new byte[n + n2];
                System.arraycopy(array, 0, array2, 0, n);
                array = array2;
            }
            read = inputStream.read(array, n, n2);
            if (read >= 0) {
                n += read;
            }
            n2 = inputStream.available();
        } while (n2 > 0 && read >= 0);
        if (n != array.length) {
            final byte[] array3 = new byte[n];
            System.arraycopy(array, 0, array3, 0, n);
            array = array3;
        }
        return array;
    }
    
    public static ByteBuffer copyStream2ByteBuffer(final InputStream inputStream) throws IOException {
        return copyStream2ByteBuffer(inputStream, -1);
    }
    
    public static ByteBuffer copyStream2ByteBuffer(InputStream inputStream, int n) throws IOException {
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        int n2 = inputStream.available();
        if (n < n2) {
            n = n2;
        }
        final MachineDataInfo machineDataInfo = Platform.getMachineDataInfo();
        ByteBuffer directByteBuffer = Buffers.newDirectByteBuffer(machineDataInfo.pageAlignedSize(n));
        final byte[] array = new byte[machineDataInfo.pageSizeInBytes()];
        int n3 = Math.min(machineDataInfo.pageSizeInBytes(), n2);
        int i;
        do {
            if (n2 > directByteBuffer.remaining()) {
                final ByteBuffer directByteBuffer2 = Buffers.newDirectByteBuffer(machineDataInfo.pageAlignedSize(directByteBuffer.position() + n2));
                directByteBuffer2.put(directByteBuffer);
                directByteBuffer = directByteBuffer2;
            }
            i = inputStream.read(array, 0, n3);
            if (i > 0) {
                directByteBuffer.put(array, 0, i);
            }
            n2 = inputStream.available();
            n3 = Math.min(machineDataInfo.pageSizeInBytes(), n2);
        } while (i > 0);
        directByteBuffer.flip();
        return directByteBuffer;
    }
    
    public static String slashify(final String s, final boolean b, final boolean b2) throws URISyntaxException {
        String s2 = IOUtil.patternSingleBS.matcher(s).replaceAll("/");
        if (b && !s2.startsWith("/")) {
            s2 = "/" + s2;
        }
        if (b2 && !s2.endsWith("/")) {
            s2 += "/";
        }
        return cleanPathString(s2);
    }
    
    public static String getFileSuffix(final File file) {
        return getFileSuffix(file.getName());
    }
    
    public static String getFileSuffix(final String s) {
        final int lastIndex = s.lastIndexOf(46);
        if (lastIndex < 0) {
            return null;
        }
        return toLowerCase(s.substring(lastIndex + 1));
    }
    
    private static String toLowerCase(final String s) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase();
    }
    
    public static FileOutputStream getFileOutputStream(final File file, final boolean b) throws IOException {
        final Constructor<?> fosCtor = getFOSCtor();
        if (null == fosCtor) {
            throw new IOException("Cannot open file (" + file + ") for writing, FileOutputStream feature not available.");
        }
        if (file.exists() && !b) {
            throw new IOException("File already exists (" + file + ") and overwrite=false");
        }
        try {
            return (FileOutputStream)fosCtor.newInstance(file);
        }
        catch (Exception ex) {
            throw new IOException("error opening " + file + " for write. ", ex);
        }
    }
    
    public static String getClassFileName(final String s) {
        return s.replace('.', '/') + ".class";
    }
    
    public static URL getClassURL(final String s, final ClassLoader classLoader) throws IOException {
        final URL resource = classLoader.getResource(getClassFileName(s));
        if (null == resource) {
            throw new IOException("Cannot not find: " + s);
        }
        return resource;
    }
    
    public static String getBasename(String s) throws URISyntaxException {
        s = slashify(s, false, false);
        final int lastIndex = s.lastIndexOf(47);
        if (lastIndex >= 0) {
            s = s.substring(lastIndex + 1);
        }
        return s;
    }
    
    public static String getDirname(String s) throws URISyntaxException {
        s = slashify(s, false, false);
        final int lastIndex = s.lastIndexOf(47);
        if (lastIndex >= 0) {
            s = s.substring(0, lastIndex + 1);
        }
        return s;
    }
    
    public static URLConnection getResource(final Class<?> clazz, final String s) {
        return getResource(s, (null != clazz) ? clazz.getClassLoader() : IOUtil.class.getClassLoader(), clazz);
    }
    
    public static URLConnection getResource(final String s, final ClassLoader classLoader, final Class<?> clazz) {
        if (null == s) {
            return null;
        }
        URLConnection urlConnection = null;
        if (null != clazz) {
            final String replace = clazz.getName().replace('.', '/');
            final int lastIndex = replace.lastIndexOf(47);
            if (lastIndex >= 0) {
                final String substring = replace.substring(0, lastIndex + 1);
                urlConnection = getResource(substring + s, classLoader);
                if (IOUtil.DEBUG) {
                    System.err.println("IOUtil: found <" + s + "> within class package <" + substring + "> of given class <" + clazz.getName() + ">: " + (null != urlConnection));
                }
            }
        }
        else if (IOUtil.DEBUG) {
            System.err.println("IOUtil: null context, skip rel. lookup");
        }
        if (null == urlConnection) {
            urlConnection = getResource(s, classLoader);
            if (IOUtil.DEBUG) {
                System.err.println("IOUtil: found <" + s + "> by classloader: " + (null != urlConnection));
            }
        }
        return urlConnection;
    }
    
    public static URLConnection getResource(final String s, final ClassLoader classLoader) {
        if (null == s) {
            return null;
        }
        if (IOUtil.DEBUG) {
            System.err.println("IOUtil: locating <" + s + ">, has cl: " + (null != classLoader));
        }
        if (s.startsWith("asset:")) {
            try {
                return AssetURLContext.createURL(s, classLoader).openConnection();
            }
            catch (IOException ex) {
                if (IOUtil.DEBUG) {
                    ExceptionUtils.dumpThrowable("IOUtil", ex);
                }
                return null;
            }
        }
        try {
            return AssetURLContext.resolve(s, classLoader);
        }
        catch (IOException ex2) {
            if (IOUtil.DEBUG) {
                ExceptionUtils.dumpThrowable("IOUtil", ex2);
            }
            return null;
        }
    }
    
    public static String getRelativeOf(final File file, final String s) throws URISyntaxException {
        if (null == s) {
            return null;
        }
        if (file != null) {
            return slashify(new File(file, s).getPath(), false, false);
        }
        return null;
    }
    
    public static String getParentOf(final String s) throws URISyntaxException {
        final int n = (null != s) ? s.length() : 0;
        if (n == 0) {
            throw new IllegalArgumentException("path is empty <" + s + ">");
        }
        final int lastIndex = s.lastIndexOf("/");
        if (lastIndex < 0) {
            throw new URISyntaxException(s, "path contains no '/': <" + s + ">");
        }
        if (lastIndex == 0) {
            throw new URISyntaxException(s, "path has no parents: <" + s + ">");
        }
        if (lastIndex < n - 1) {
            return s.substring(0, lastIndex + 1);
        }
        final int n2 = s.lastIndexOf("!") + 1;
        final int lastIndex2 = s.lastIndexOf("/", lastIndex - 1);
        if (lastIndex2 >= n2) {
            return s.substring(0, lastIndex2 + 1);
        }
        if (s.substring(n2, lastIndex).equals("..")) {
            throw new URISyntaxException(s, "parent is unresolved: <" + s + ">");
        }
        return s.substring(0, n2);
    }
    
    public static String cleanPathString(String s) throws URISyntaxException {
        int lastIndex = s.length() - 1;
        while (lastIndex >= 1 && (lastIndex = s.lastIndexOf("./", lastIndex)) >= 0) {
            if (0 < lastIndex && s.charAt(lastIndex - 1) == '.') {
                lastIndex -= 2;
            }
            else {
                s = s.substring(0, lastIndex) + s.substring(lastIndex + 2);
                --lastIndex;
            }
        }
        int index = 0;
        while ((index = s.indexOf("../", index)) >= 0) {
            if (index == 0) {
                index += 3;
            }
            else {
                s = getParentOf(s.substring(0, index)) + s.substring(index + 3);
                index = 0;
            }
        }
        return s;
    }
    
    public static String getUriFilePathOrASCII(final Uri uri) {
        if (uri.isFileScheme()) {
            return uri.toFile().getPath();
        }
        return uri.toASCIIString().get();
    }
    
    public static URLConnection openURL(final URL url) {
        return openURL(url, ".");
    }
    
    public static URLConnection openURL(final URL url, final String s) {
        if (null != url) {
            try {
                final URLConnection openConnection = url.openConnection();
                openConnection.connect();
                if (IOUtil.DEBUG) {
                    System.err.println("IOUtil: urlExists(" + url + ") [" + s + "] - true");
                }
                return openConnection;
            }
            catch (IOException ex) {
                if (IOUtil.DEBUG) {
                    ExceptionUtils.dumpThrowable("IOUtil: urlExists(" + url + ") [" + s + "] - false -", ex);
                }
                return null;
            }
        }
        if (IOUtil.DEBUG) {
            System.err.println("IOUtil: no url - urlExists(null) [" + s + "]");
        }
        return null;
    }
    
    private static String getExeTestFileSuffix() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS: {
                if (Platform.CPUFamily.X86 == PlatformPropsImpl.CPU_ARCH.family) {
                    return ".exe";
                }
                return ".bat";
            }
            default: {
                return ".sh";
            }
        }
    }
    
    private static String getExeTestShellCode() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS: {
                return "echo off" + PlatformPropsImpl.NEWLINE;
            }
            default: {
                return null;
            }
        }
    }
    
    private static String[] getExeTestCommandArgs(final String s) {
        switch (PlatformPropsImpl.OS_TYPE) {
            default: {
                return new String[] { s };
            }
        }
    }
    
    private static final byte[] readCode(final String s) throws IOException {
        final InputStream inputStream = getResource(s, IOUtil.class.getClassLoader(), IOUtil.class).getInputStream();
        byte[] inflateFromStream = null;
        try {
            inflateFromStream = CustomCompress.inflateFromStream(inputStream);
        }
        finally {
            inputStream.close();
        }
        return inflateFromStream;
    }
    
    private static void fillExeTestFile(final File file) throws IOException {
        if (Platform.OSType.WINDOWS == PlatformPropsImpl.OS_TYPE && Platform.CPUFamily.X86 == PlatformPropsImpl.CPU_ARCH.family) {
            byte[] code;
            synchronized (IOUtil.exeTestLock) {
                final byte[] array;
                if (null == IOUtil.exeTestCodeRef || null == (array = IOUtil.exeTestCodeRef.get())) {
                    String s;
                    if (Platform.CPUType.X86_64 == PlatformPropsImpl.CPU_ARCH) {
                        s = "bin/exe-windows-x86_64.defl";
                    }
                    else {
                        s = "bin/exe-windows-i386.defl";
                    }
                    code = readCode(s);
                    IOUtil.exeTestCodeRef = new WeakReference<byte[]>(code);
                }
                else {
                    code = array;
                }
            }
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(code, 0, code.length);
                try {
                    fileOutputStream.getFD().sync();
                }
                catch (SyncFailedException ex) {
                    ExceptionUtils.dumpThrowable("", ex);
                }
            }
            finally {
                fileOutputStream.close();
            }
        }
        else {
            final String exeTestShellCode = getExeTestShellCode();
            if (isStringSet(exeTestShellCode)) {
                final FileWriter fileWriter = new FileWriter(file);
                try {
                    fileWriter.write(exeTestShellCode);
                    try {
                        fileWriter.flush();
                    }
                    catch (IOException ex2) {
                        ExceptionUtils.dumpThrowable("", ex2);
                    }
                }
                finally {
                    fileWriter.close();
                }
            }
        }
    }
    
    private static boolean getOSHasNoexecFS() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case OPENKODE: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    private static boolean getOSHasFreeDesktopXDG() {
        switch (PlatformPropsImpl.OS_TYPE) {
            case WINDOWS:
            case OPENKODE:
            case ANDROID:
            case MACOS: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public static boolean testFile(final File file, final boolean b, final boolean b2) {
        if (!file.exists()) {
            if (IOUtil.DEBUG) {
                System.err.println("IOUtil.testFile: <" + file.getAbsolutePath() + ">: does not exist");
            }
            return false;
        }
        if (b && !file.isDirectory()) {
            if (IOUtil.DEBUG) {
                System.err.println("IOUtil.testFile: <" + file.getAbsolutePath() + ">: is not a directory");
            }
            return false;
        }
        if (b2 && !file.canWrite()) {
            if (IOUtil.DEBUG) {
                System.err.println("IOUtil.testFile: <" + file.getAbsolutePath() + ">: is not writable");
            }
            return false;
        }
        return true;
    }
    
    public static boolean testDirExec(final File file) throws SecurityException {
        final boolean b = IOUtil.DEBUG_EXE || IOUtil.DEBUG;
        if (!testFile(file, true, true)) {
            if (b) {
                System.err.println("IOUtil.testDirExec: <" + file.getAbsolutePath() + ">: Not writeable dir");
            }
            return false;
        }
        if (!getOSHasNoexecFS()) {
            if (b) {
                System.err.println("IOUtil.testDirExec: <" + file.getAbsolutePath() + ">: Always executable");
            }
            return true;
        }
        final long n = b ? System.currentTimeMillis() : 0L;
        File tempFile;
        boolean b2;
        try {
            File file2;
            if (IOUtil.DEBUG_EXE_EXISTING_FILE) {
                final StringBuilder sb;
                file2 = new File(file, sb.append("jogamp_exe_tst").append(getExeTestFileSuffix()).toString());
                sb = new StringBuilder();
            }
            else {
                file2 = null;
            }
            final File file3 = file2;
            if (null != file3 && file3.exists()) {
                tempFile = file3;
                b2 = true;
            }
            else {
                tempFile = File.createTempFile("jogamp_exe_tst", getExeTestFileSuffix(), file);
                b2 = false;
            }
        }
        catch (SecurityException ex) {
            throw ex;
        }
        catch (IOException ex2) {
            if (b) {
                ex2.printStackTrace();
            }
            return false;
        }
        final long n2 = b ? System.currentTimeMillis() : 0L;
        int n3 = -1;
        int exitValue = -1;
        long n4;
        if (b2 || tempFile.setExecutable(true, true)) {
            Process exec = null;
            try {
                if (!b2) {
                    fillExeTestFile(tempFile);
                }
                n4 = (b ? System.currentTimeMillis() : 0L);
                exec = Runtime.getRuntime().exec(getExeTestCommandArgs(tempFile.getCanonicalPath()), null, null);
                if (IOUtil.DEBUG_EXE && !IOUtil.DEBUG_EXE_NOSTREAM) {
                    final StreamMonitor streamMonitor = new StreamMonitor(new InputStream[] { exec.getInputStream(), exec.getErrorStream() }, System.err, "Exe-Tst: ");
                }
                exec.waitFor();
                exitValue = exec.exitValue();
                n3 = 0;
            }
            catch (SecurityException ex3) {
                throw ex3;
            }
            catch (Throwable t) {
                n4 = (b ? System.currentTimeMillis() : 0L);
                n3 = -2;
                if (b) {
                    System.err.println("IOUtil.testDirExec: <" + tempFile.getAbsolutePath() + ">: Caught " + t.getClass().getSimpleName() + ": " + t.getMessage());
                    t.printStackTrace();
                }
            }
            finally {
                if (null != exec) {
                    try {
                        exec.destroy();
                    }
                    catch (Throwable t2) {
                        ExceptionUtils.dumpThrowable("", t2);
                    }
                }
            }
        }
        else {
            n4 = (b ? System.currentTimeMillis() : 0L);
        }
        final boolean b3 = n3 == 0;
        if (!IOUtil.DEBUG_EXE && !b2) {
            tempFile.delete();
        }
        if (b) {
            final long currentTimeMillis = System.currentTimeMillis();
            System.err.println("IOUtil.testDirExec(): test-exe <" + tempFile.getAbsolutePath() + ">, existingFile " + b2 + ", returned " + exitValue);
            System.err.println("IOUtil.testDirExec(): abs-path <" + file.getAbsolutePath() + ">: res " + n3 + " -> " + b3);
            System.err.println("IOUtil.testDirExec(): total " + (currentTimeMillis - n) + "ms, create " + (n2 - n) + "ms, fill " + (n4 - n2) + "ms, execute " + (currentTimeMillis - n4) + "ms");
        }
        return b3;
    }
    
    private static File testDirImpl(final File file, final boolean b, final boolean b2, final String s) throws SecurityException {
        if (b && !file.exists()) {
            file.mkdirs();
        }
        File file2;
        if (b2) {
            file2 = (testDirExec(file) ? file : null);
        }
        else {
            file2 = (testFile(file, true, true) ? file : null);
        }
        if (IOUtil.DEBUG) {
            System.err.println("IOUtil.testDirImpl(" + s + "): <" + file.getAbsolutePath() + ">, create " + b + ", exec " + b2 + ": " + (null != file2));
        }
        return file2;
    }
    
    public static File testDir(final File file, final boolean b, final boolean b2) throws SecurityException {
        return testDirImpl(file, b, b2, "testDir");
    }
    
    private static boolean isStringSet(final String s) {
        return null != s && 0 < s.length();
    }
    
    private static File getSubTempDir(final File file, final String s, final boolean b, final String s2) throws SecurityException {
        File testDirImpl = null;
        if (null != testDirImpl(file, true, b, s2)) {
            for (int n = 0; null == testDirImpl && n <= 9999; testDirImpl = testDirImpl(new File(file, s + String.format("_%04d", n)), true, b, s2), ++n) {}
        }
        return testDirImpl;
    }
    
    private static File getFile(final String s) {
        if (isStringSet(s)) {
            return new File(s);
        }
        return null;
    }
    
    public static File getTempDir(final boolean b) throws SecurityException, IOException {
        if (!IOUtil.tempRootSet) {
            synchronized (IOUtil.class) {
                if (!IOUtil.tempRootSet) {
                    IOUtil.tempRootSet = true;
                    final File tempRoot = AndroidUtils.getTempRoot();
                    if (null != tempRoot) {
                        IOUtil.tempRootNoexec = getSubTempDir(tempRoot, "jogamp", false, "Android.ctxTemp");
                        return IOUtil.tempRootExec = IOUtil.tempRootNoexec;
                    }
                    final File file = getFile(PropertyAccess.getProperty("java.io.tmpdir", false));
                    if (IOUtil.DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): tempX1 <" + file + ">, used " + (null != file));
                    }
                    String s = System.getenv("TMPDIR");
                    if (!isStringSet(s)) {
                        s = System.getenv("TEMP");
                    }
                    final File file2 = getFile(s);
                    File file3;
                    if (null != file2 && !file2.equals(file)) {
                        file3 = file2;
                    }
                    else {
                        file3 = null;
                    }
                    if (IOUtil.DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): tempX3 <" + file2 + ">, used " + (null != file3));
                    }
                    final File file4 = getFile(PropertyAccess.getProperty("user.home", false));
                    if (IOUtil.DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): tempX4 <" + file4 + ">, used " + (null != file4));
                    }
                    String s2;
                    if (getOSHasFreeDesktopXDG()) {
                        s2 = System.getenv("XDG_CACHE_HOME");
                        if (!isStringSet(s2) && null != file4) {
                            s2 = file4.getAbsolutePath() + File.separator + ".cache";
                        }
                    }
                    else {
                        s2 = null;
                    }
                    final File file5 = getFile(s2);
                    File file6;
                    if (null != file5 && !file5.equals(file)) {
                        file6 = file5;
                    }
                    else {
                        file6 = null;
                    }
                    if (IOUtil.DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): tempX2 <" + file5 + ">, used " + (null != file6));
                    }
                    if (null == IOUtil.tempRootExec && null != file) {
                        if (Platform.OSType.MACOS == PlatformPropsImpl.OS_TYPE) {
                            IOUtil.tempRootExec = getSubTempDir(file, "jogamp", false, "tempX1");
                        }
                        else {
                            IOUtil.tempRootExec = getSubTempDir(file, "jogamp", true, "tempX1");
                        }
                    }
                    if (null == IOUtil.tempRootExec && null != file6) {
                        IOUtil.tempRootExec = getSubTempDir(file6, "jogamp", true, "tempX2");
                    }
                    if (null == IOUtil.tempRootExec && null != file3) {
                        IOUtil.tempRootExec = getSubTempDir(file3, "jogamp", true, "tempX3");
                    }
                    if (null == IOUtil.tempRootExec && null != file4) {
                        IOUtil.tempRootExec = getSubTempDir(file4, ".jogamp", true, "tempX4");
                    }
                    if (null != IOUtil.tempRootExec) {
                        IOUtil.tempRootNoexec = IOUtil.tempRootExec;
                    }
                    else {
                        if (null == IOUtil.tempRootNoexec && null != file) {
                            IOUtil.tempRootNoexec = getSubTempDir(file, "jogamp", false, "temp01");
                        }
                        if (null == IOUtil.tempRootNoexec && null != file6) {
                            IOUtil.tempRootNoexec = getSubTempDir(file6, "jogamp", false, "temp02");
                        }
                        if (null == IOUtil.tempRootNoexec && null != file3) {
                            IOUtil.tempRootNoexec = getSubTempDir(file3, "jogamp", false, "temp03");
                        }
                        if (null == IOUtil.tempRootNoexec && null != file4) {
                            IOUtil.tempRootNoexec = getSubTempDir(file4, ".jogamp", false, "temp04");
                        }
                    }
                    if (IOUtil.DEBUG) {
                        System.err.println("IOUtil.getTempRoot(): temp dirs: exec: " + ((null != IOUtil.tempRootExec) ? IOUtil.tempRootExec.getAbsolutePath() : null) + ", noexec: " + ((null != IOUtil.tempRootNoexec) ? IOUtil.tempRootNoexec.getAbsolutePath() : null));
                    }
                }
            }
        }
        final File file7 = b ? IOUtil.tempRootExec : IOUtil.tempRootNoexec;
        if (null == file7) {
            throw new IOException("Could not determine a temporary " + (b ? "executable " : "") + "directory");
        }
        SecurityUtil.checkPermission(new FilePermission(file7.getAbsolutePath(), "read,write,delete"));
        return file7;
    }
    
    public static File createTempFile(final String s, final String s2, final boolean b) throws IllegalArgumentException, IOException, SecurityException {
        return File.createTempFile(s, s2, getTempDir(b));
    }
    
    public static void close(final Closeable closeable, final boolean b) throws RuntimeException {
        if (null != closeable) {
            try {
                closeable.close();
            }
            catch (IOException ex) {
                if (b) {
                    throw new RuntimeException(ex);
                }
                if (IOUtil.DEBUG) {
                    System.err.println("Caught Exception: ");
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public static IOException close(final Closeable closeable, final IOException[] array, final PrintStream printStream) {
        try {
            closeable.close();
        }
        catch (IOException ex) {
            if (null != array[0]) {
                if (null != printStream) {
                    printStream.println("Caught " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                    ex.printStackTrace(printStream);
                }
                return ex;
            }
            array[0] = ex;
        }
        return null;
    }
    
    static {
        Debug.initSingleton();
        DEBUG = Debug.debug("IOUtil");
        DEBUG_EXE = PropertyAccess.isPropertyDefined("jogamp.debug.IOUtil.Exe", true);
        DEBUG_EXE_NOSTREAM = PropertyAccess.isPropertyDefined("jogamp.debug.IOUtil.Exe.NoStream", true);
        DEBUG_EXE_EXISTING_FILE = false;
        patternSingleBS = Pattern.compile("\\\\{1}");
        patternSpaceEnc = Pattern.compile("%20");
        exeTestLock = new Object();
        IOUtil.exeTestCodeRef = null;
        IOUtil.tempRootExec = null;
        IOUtil.tempRootNoexec = null;
        IOUtil.tempRootSet = false;
    }
    
    public static class ClassResources
    {
        public final ClassLoader classLoader;
        public final Class<?> contextCL;
        public final String[] resourcePaths;
        
        public final int resourceCount() {
            return this.resourcePaths.length;
        }
        
        public ClassResources(final Class<?> clazz, final String[] array) {
            this(array, clazz.getClassLoader(), clazz);
        }
        
        public ClassResources(final String[] resourcePaths, final ClassLoader classLoader, final Class<?> contextCL) {
            for (int i = resourcePaths.length - 1; i >= 0; --i) {
                if (null == resourcePaths[i]) {
                    throw new IllegalArgumentException("resourcePath[" + i + "] is null");
                }
            }
            this.classLoader = classLoader;
            this.contextCL = contextCL;
            this.resourcePaths = resourcePaths;
        }
        
        public URLConnection resolve(final int n) throws ArrayIndexOutOfBoundsException {
            return IOUtil.getResource(this.resourcePaths[n], this.classLoader, this.contextCL);
        }
    }
    
    public static class StreamMonitor implements Runnable
    {
        private final InputStream[] istreams;
        private final boolean[] eos;
        private final PrintStream ostream;
        private final String prefix;
        
        public StreamMonitor(final InputStream[] istreams, final PrintStream ostream, final String prefix) {
            this.istreams = istreams;
            this.eos = new boolean[istreams.length];
            this.ostream = ostream;
            this.prefix = prefix;
            final InterruptSource.Thread thread = new InterruptSource.Thread(null, this, "StreamMonitor-" + Thread.currentThread().getName());
            thread.setDaemon(true);
            thread.start();
        }
        
        @Override
        public void run() {
            final byte[] array = new byte[4096];
            try {
                final int length = this.istreams.length;
                int i = 0;
                do {
                    for (int j = 0; j < this.istreams.length; ++j) {
                        if (!this.eos[j]) {
                            final int read = this.istreams[j].read(array);
                            if (read > 0) {
                                if (null != this.ostream) {
                                    if (null != this.prefix) {
                                        this.ostream.write(this.prefix.getBytes());
                                    }
                                    this.ostream.write(array, 0, read);
                                }
                            }
                            else {
                                ++i;
                                this.eos[j] = true;
                            }
                        }
                    }
                    if (null != this.ostream) {
                        this.ostream.flush();
                    }
                } while (i < length);
            }
            catch (IOException ex) {}
            finally {
                if (null != this.ostream) {
                    this.ostream.flush();
                }
            }
        }
    }
}
