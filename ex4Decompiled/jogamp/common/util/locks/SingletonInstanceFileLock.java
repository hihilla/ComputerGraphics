// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util.locks;

import java.io.IOException;
import com.jogamp.common.util.InterruptSource;
import java.nio.channels.FileLock;
import java.io.RandomAccessFile;
import java.io.File;
import com.jogamp.common.util.locks.SingletonInstance;

public class SingletonInstanceFileLock extends SingletonInstance
{
    static final String temp_file_path;
    private final File file;
    private RandomAccessFile randomAccessFile;
    private FileLock fileLock;
    
    public static String getCanonicalTempPath() {
        return SingletonInstanceFileLock.temp_file_path;
    }
    
    public static String getCanonicalTempLockFilePath(final String s) {
        return getCanonicalTempPath() + File.separator + s;
    }
    
    public SingletonInstanceFileLock(final long n, final String s) {
        super(n);
        this.randomAccessFile = null;
        this.fileLock = null;
        this.file = new File(getCanonicalTempLockFilePath(s));
        this.setupFileCleanup();
    }
    
    public SingletonInstanceFileLock(final long n, final File file) {
        super(n);
        this.randomAccessFile = null;
        this.fileLock = null;
        this.file = file;
        this.setupFileCleanup();
    }
    
    @Override
    public final String getName() {
        return this.file.getPath();
    }
    
    private void setupFileCleanup() {
        this.file.deleteOnExit();
        Runtime.getRuntime().addShutdownHook(new InterruptSource.Thread() {
            @Override
            public void run() {
                if (SingletonInstanceFileLock.this.isLocked()) {
                    System.err.println(SingletonInstanceFileLock.this.infoPrefix() + " XXX " + SingletonInstanceFileLock.this.getName() + " - Unlock @ JVM Shutdown");
                }
                SingletonInstanceFileLock.this.unlock();
            }
        });
    }
    
    @Override
    protected boolean tryLockImpl() {
        try {
            this.randomAccessFile = new RandomAccessFile(this.file, "rw");
            this.fileLock = this.randomAccessFile.getChannel().tryLock();
            if (this.fileLock != null) {
                return true;
            }
        }
        catch (Exception ex) {
            System.err.println(this.infoPrefix() + " III " + this.getName() + " - Unable to create and/or lock file");
            ex.printStackTrace();
        }
        return false;
    }
    
    @Override
    protected boolean unlockImpl() {
        try {
            if (null != this.fileLock) {
                this.fileLock.release();
                this.fileLock = null;
            }
            if (null != this.randomAccessFile) {
                this.randomAccessFile.close();
                this.randomAccessFile = null;
            }
            if (null != this.file) {
                this.file.delete();
            }
            return true;
        }
        catch (Exception ex) {
            System.err.println(this.infoPrefix() + " EEE " + this.getName() + " - Unable to remove lock file");
            ex.printStackTrace();
        }
        finally {
            this.fileLock = null;
            this.randomAccessFile = null;
        }
        return false;
    }
    
    static {
        String substring = null;
        try {
            final File tempFile = File.createTempFile("TEST", "tst");
            final String canonicalPath = tempFile.getCanonicalPath();
            tempFile.delete();
            substring = canonicalPath.substring(0, canonicalPath.lastIndexOf(File.separator));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        temp_file_path = substring;
    }
}
