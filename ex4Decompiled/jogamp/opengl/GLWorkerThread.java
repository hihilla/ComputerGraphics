// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLContext;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.common.util.InterruptSource;
import java.util.List;

public class GLWorkerThread
{
    private static volatile boolean started;
    private static volatile Thread thread;
    private static Object lock;
    private static volatile boolean shouldTerminate;
    private static volatile Throwable exception;
    private static volatile Runnable work;
    private static List<Runnable> queue;
    
    public static void start() {
        if (!GLWorkerThread.started) {
            synchronized (GLWorkerThread.class) {
                if (GLWorkerThread.started) {
                    throw new RuntimeException(getThreadName() + ": Should not start GLWorkerThread twice");
                }
                GLWorkerThread.lock = new Object();
                final WorkerRunnable workerRunnable = new WorkerRunnable();
                (GLWorkerThread.thread = new InterruptSource.Thread(null, workerRunnable, "JOGL-GLWorkerThread-")).setDaemon(true);
                GLWorkerThread.started = true;
                synchronized (GLWorkerThread.lock) {
                    GLWorkerThread.thread.start();
                    try {
                        while (!workerRunnable.isRunning) {
                            GLWorkerThread.lock.wait();
                        }
                    }
                    catch (InterruptedException ex) {
                        throw new InterruptedRuntimeException(ex);
                    }
                }
            }
        }
    }
    
    public static void invoke(final boolean b, final Runnable runnable) throws InvocationTargetException, InterruptedException {
        if (b) {
            invokeAndWait(runnable);
        }
        else {
            invokeLater(runnable);
        }
    }
    
    public static void invokeAndWait(final Runnable work) throws InvocationTargetException, InterruptedException {
        if (!GLWorkerThread.started) {
            throw new RuntimeException(getThreadName() + ": May not invokeAndWait on worker thread without starting it first");
        }
        final Object lock = GLWorkerThread.lock;
        if (lock == null) {
            return;
        }
        synchronized (lock) {
            if (GLWorkerThread.thread == null) {
                return;
            }
            GLWorkerThread.work = work;
            lock.notifyAll();
            while (null != GLWorkerThread.work) {
                lock.wait();
            }
            if (GLWorkerThread.exception != null) {
                final Throwable exception = GLWorkerThread.exception;
                GLWorkerThread.exception = null;
                throw new InvocationTargetException(exception);
            }
        }
    }
    
    public static void invokeLater(final Runnable runnable) {
        if (!GLWorkerThread.started) {
            throw new RuntimeException(getThreadName() + ": May not invokeLater on worker thread without starting it first");
        }
        final Object lock = GLWorkerThread.lock;
        if (lock == null) {
            return;
        }
        synchronized (lock) {
            if (GLWorkerThread.thread == null) {
                return;
            }
            GLWorkerThread.queue.add(runnable);
            lock.notifyAll();
        }
    }
    
    public static boolean isStarted() {
        return GLWorkerThread.started;
    }
    
    public static boolean isWorkerThread() {
        return Thread.currentThread() == GLWorkerThread.thread;
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    static {
        GLWorkerThread.queue = new ArrayList<Runnable>();
    }
    
    static class WorkerRunnable implements Runnable
    {
        volatile boolean isRunning;
        
        WorkerRunnable() {
            this.isRunning = false;
        }
        
        @Override
        public void run() {
            synchronized (GLWorkerThread.lock) {
                this.isRunning = true;
                GLWorkerThread.lock.notifyAll();
            }
            while (!GLWorkerThread.shouldTerminate) {
                synchronized (GLWorkerThread.lock) {
                    while (!GLWorkerThread.shouldTerminate && GLWorkerThread.work == null && GLWorkerThread.queue.isEmpty()) {
                        try {
                            GLWorkerThread.lock.wait(1000L);
                        }
                        catch (InterruptedException ex) {
                            throw new InterruptedRuntimeException(ex);
                        }
                        if (GLContext.getCurrent() != null) {
                            break;
                        }
                    }
                    if (GLWorkerThread.shouldTerminate) {
                        GLWorkerThread.lock.notifyAll();
                        GLWorkerThread.thread = null;
                        GLWorkerThread.lock = null;
                        return;
                    }
                    if (GLWorkerThread.work != null) {
                        try {
                            GLWorkerThread.work.run();
                        }
                        catch (Throwable t) {
                            GLWorkerThread.exception = t;
                        }
                        finally {
                            GLWorkerThread.work = null;
                            GLWorkerThread.lock.notifyAll();
                        }
                    }
                    while (!GLWorkerThread.queue.isEmpty()) {
                        try {
                            GLWorkerThread.queue.remove(0).run();
                        }
                        catch (Throwable t2) {
                            ExceptionUtils.dumpThrowable("suppressed", t2);
                        }
                    }
                    final GLContext current = GLContext.getCurrent();
                    if (current == null || !(current instanceof GLContextImpl)) {
                        continue;
                    }
                    final GLContextImpl glContextImpl = (GLContextImpl)current;
                    if (!glContextImpl.hasWaiters()) {
                        continue;
                    }
                    glContextImpl.release();
                }
            }
            this.isRunning = false;
        }
    }
}
