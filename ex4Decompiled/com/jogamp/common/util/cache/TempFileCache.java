// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util.cache;

import com.jogamp.common.util.IOUtil;
import jogamp.common.Debug;
import java.io.FilenameFilter;
import java.nio.channels.FileLock;
import com.jogamp.common.util.InterruptSource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class TempFileCache
{
    private static final boolean DEBUG;
    private static boolean staticInitError;
    private static final String tmpDirPrefix = "file_cache";
    private static final File tmpBaseDir;
    static final String tmpRootPropName = "jnlp.jogamp.tmp.cache.root";
    private static String tmpRootPropValue;
    private static File tmpRootDir;
    private boolean initError;
    private File individualTmpDir;
    
    public static boolean initSingleton() {
        return !TempFileCache.staticInitError;
    }
    
    private static void initTmpRoot() throws IOException {
        TempFileCache.tmpRootPropValue = System.getProperty("jnlp.jogamp.tmp.cache.root");
        if (TempFileCache.tmpRootPropValue != null) {
            if (TempFileCache.tmpRootPropValue.indexOf(47) >= 0 || TempFileCache.tmpRootPropValue.indexOf(File.separatorChar) >= 0) {
                throw new IOException("Illegal value of: jnlp.jogamp.tmp.cache.root");
            }
            if (TempFileCache.DEBUG) {
                System.err.println("TempFileCache: Trying existing value of: jnlp.jogamp.tmp.cache.root=" + TempFileCache.tmpRootPropValue);
            }
            TempFileCache.tmpRootDir = new File(TempFileCache.tmpBaseDir, TempFileCache.tmpRootPropValue);
            if (TempFileCache.DEBUG) {
                System.err.println("TempFileCache: Trying tmpRootDir = " + TempFileCache.tmpRootDir.getAbsolutePath());
            }
            if (TempFileCache.tmpRootDir.isDirectory()) {
                if (!TempFileCache.tmpRootDir.canWrite()) {
                    throw new IOException("Temp root directory is not writable: " + TempFileCache.tmpRootDir.getAbsolutePath());
                }
            }
            else {
                System.err.println("TempFileCache: None existing tmpRootDir = " + TempFileCache.tmpRootDir.getAbsolutePath() + ", assuming new path due to update");
                TempFileCache.tmpRootPropValue = null;
                TempFileCache.tmpRootDir = null;
                System.clearProperty("jnlp.jogamp.tmp.cache.root");
            }
        }
        if (TempFileCache.tmpRootPropValue == null) {
            final File tempFile = File.createTempFile("jln", ".tmp", TempFileCache.tmpBaseDir);
            if (TempFileCache.DEBUG) {
                System.err.println("TempFileCache: tmpFile = " + tempFile.getAbsolutePath());
            }
            final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            final FileLock lock = fileOutputStream.getChannel().lock();
            final String absolutePath = tempFile.getAbsolutePath();
            final String substring = absolutePath.substring(0, absolutePath.lastIndexOf(".tmp"));
            final File file = new File(substring + ".lck");
            if (TempFileCache.DEBUG) {
                System.err.println("TempFileCache: lckFile = " + file.getAbsolutePath());
            }
            file.createNewFile();
            final FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            final FileLock lock2 = fileOutputStream2.getChannel().lock();
            TempFileCache.tmpRootDir = new File(substring);
            if (TempFileCache.DEBUG) {
                System.err.println("TempFileCache: tmpRootDir = " + TempFileCache.tmpRootDir.getAbsolutePath());
            }
            if (!TempFileCache.tmpRootDir.mkdir()) {
                throw new IOException("Cannot create " + TempFileCache.tmpRootDir);
            }
            Runtime.getRuntime().addShutdownHook(new InterruptSource.Thread() {
                @Override
                public void run() {
                    try {
                        fileOutputStream.close();
                        lock.release();
                        fileOutputStream2.close();
                        lock2.release();
                    }
                    catch (IOException ex) {}
                }
            });
            System.setProperty("jnlp.jogamp.tmp.cache.root", TempFileCache.tmpRootPropValue = substring.substring(substring.lastIndexOf(File.separator) + 1));
            if (TempFileCache.DEBUG) {
                System.err.println("TempFileCache: Setting jnlp.jogamp.tmp.cache.root=" + TempFileCache.tmpRootPropValue);
            }
            final InterruptSource.Thread thread = new InterruptSource.Thread() {
                @Override
                public void run() {
                    deleteOldTempDirs();
                }
            };
            thread.setName("TempFileCache-Reaper");
            thread.start();
        }
    }
    
    private static void deleteOldTempDirs() {
        if (TempFileCache.DEBUG) {
            System.err.println("TempFileCache: *** Reaper: deleteOldTempDirs in " + TempFileCache.tmpBaseDir.getAbsolutePath());
        }
        final String[] list = TempFileCache.tmpBaseDir.list(new FilenameFilter() {
            final /* synthetic */ String val$ourLockFile = TempFileCache.tmpRootPropValue + ".lck";
            
            @Override
            public boolean accept(final File file, final String s) {
                return s.endsWith(".lck") && !s.equals(this.val$ourLockFile);
            }
        });
        if (list != null) {
            for (int i = 0; i < list.length; ++i) {
                final String s = list[i];
                final String substring = s.substring(0, s.lastIndexOf(".lck"));
                final String string = substring + ".tmp";
                final File file = new File(TempFileCache.tmpBaseDir, s);
                final File file2 = new File(TempFileCache.tmpBaseDir, string);
                final File file3 = new File(TempFileCache.tmpBaseDir, substring);
                if (file.exists() && file2.exists() && file3.isDirectory()) {
                    FileOutputStream fileOutputStream = null;
                    FileLock tryLock = null;
                    try {
                        fileOutputStream = new FileOutputStream(file2);
                        tryLock = fileOutputStream.getChannel().tryLock();
                    }
                    catch (Exception ex) {
                        if (TempFileCache.DEBUG) {
                            ex.printStackTrace();
                        }
                    }
                    if (tryLock != null) {
                        FileOutputStream fileOutputStream2 = null;
                        FileLock tryLock2 = null;
                        try {
                            fileOutputStream2 = new FileOutputStream(file);
                            tryLock2 = fileOutputStream2.getChannel().tryLock();
                        }
                        catch (Exception ex2) {
                            if (TempFileCache.DEBUG) {
                                ex2.printStackTrace();
                            }
                        }
                        if (tryLock2 != null) {
                            removeAll(file3);
                            try {
                                fileOutputStream2.close();
                            }
                            catch (IOException ex4) {}
                            file.delete();
                            try {
                                fileOutputStream.close();
                            }
                            catch (IOException ex5) {}
                            file2.delete();
                        }
                        else {
                            try {
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.close();
                                }
                                fileOutputStream.close();
                                tryLock.release();
                            }
                            catch (IOException ex3) {
                                if (TempFileCache.DEBUG) {
                                    ex3.printStackTrace();
                                }
                            }
                        }
                    }
                }
                else if (TempFileCache.DEBUG) {
                    System.err.println("TempFileCache: Skipping: " + file3.getAbsolutePath());
                }
            }
        }
    }
    
    private static void removeAll(final File file) {
        if (TempFileCache.DEBUG) {
            System.err.println("TempFileCache: removeAll(" + file + ")");
        }
        if (file.isDirectory()) {
            final File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (int i = 0; i < listFiles.length; ++i) {
                    removeAll(listFiles[i]);
                }
            }
        }
        file.delete();
    }
    
    public TempFileCache() {
        this.initError = false;
        if (TempFileCache.DEBUG) {
            System.err.println("TempFileCache: new TempFileCache() --------------------- (static ok: " + !TempFileCache.staticInitError + ")");
            System.err.println("TempFileCache: Thread: " + Thread.currentThread().getName() + ", CL 0x" + Integer.toHexString(TempFileCache.class.getClassLoader().hashCode()) + ", this 0x" + Integer.toHexString(this.hashCode()));
        }
        if (!TempFileCache.staticInitError) {
            try {
                this.createTmpDir();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                this.initError = true;
            }
        }
        if (TempFileCache.DEBUG) {
            System.err.println("TempFileCache: tempDir " + this.individualTmpDir + " (ok: " + !this.initError + ")");
            System.err.println("----------------------------------------------------------");
        }
    }
    
    public void destroy() {
        if (TempFileCache.DEBUG) {
            System.err.println("TempFileCache: destroy() --------------------- (static ok: " + !TempFileCache.staticInitError + ")");
            System.err.println("TempFileCache: Thread: " + Thread.currentThread().getName() + ", CL 0x" + Integer.toHexString(TempFileCache.class.getClassLoader().hashCode()) + ", this 0x" + Integer.toHexString(this.hashCode()));
        }
        if (!TempFileCache.staticInitError) {
            try {
                removeAll(this.individualTmpDir);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.individualTmpDir = null;
        if (TempFileCache.DEBUG) {
            System.err.println("TempFileCache: destroy() END");
        }
    }
    
    public boolean isValid() {
        return !TempFileCache.staticInitError && !this.initError;
    }
    
    public File getBaseDir() {
        return TempFileCache.tmpBaseDir;
    }
    
    public File getRootDir() {
        return TempFileCache.tmpRootDir;
    }
    
    public File getTempDir() {
        return this.individualTmpDir;
    }
    
    private void createTmpDir() throws IOException {
        final String absolutePath = File.createTempFile("jln", ".tmp", TempFileCache.tmpRootDir).getAbsolutePath();
        this.individualTmpDir = new File(absolutePath.substring(0, absolutePath.lastIndexOf(".tmp")));
        if (!this.individualTmpDir.mkdir()) {
            throw new IOException("Cannot create " + this.individualTmpDir);
        }
    }
    
    static {
        DEBUG = Debug.debug("TempFileCache");
        TempFileCache.staticInitError = false;
        synchronized (System.out) {
            File testDir = null;
            try {
                testDir = new File(IOUtil.getTempDir(true), "file_cache");
                testDir = IOUtil.testDir(testDir, true, false);
            }
            catch (Exception ex) {
                System.err.println("Warning: Caught Exception while retrieving executable temp base directory:");
                ex.printStackTrace();
                TempFileCache.staticInitError = true;
            }
            tmpBaseDir = testDir;
            if (TempFileCache.DEBUG) {
                final String s = (null != TempFileCache.tmpBaseDir) ? TempFileCache.tmpBaseDir.getAbsolutePath() : null;
                System.err.println("TempFileCache: Static Initialization ---------------------------------------------- OK: " + !TempFileCache.staticInitError);
                System.err.println("TempFileCache: Thread: " + Thread.currentThread().getName() + ", CL 0x" + Integer.toHexString(TempFileCache.class.getClassLoader().hashCode()) + ", tempBaseDir " + s);
            }
            if (!TempFileCache.staticInitError) {
                try {
                    initTmpRoot();
                }
                catch (Exception ex2) {
                    System.err.println("Warning: Caught Exception due to initializing TmpRoot:");
                    ex2.printStackTrace();
                    TempFileCache.staticInitError = true;
                }
            }
            if (TempFileCache.DEBUG) {
                System.err.println("------------------------------------------------------------------ OK: " + !TempFileCache.staticInitError);
            }
        }
    }
}
