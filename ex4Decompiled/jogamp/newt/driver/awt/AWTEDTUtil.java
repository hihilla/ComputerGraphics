// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.awt;

import com.jogamp.common.util.InterruptSource;
import jogamp.newt.Debug;
import com.jogamp.common.util.TaskBase;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.common.util.awt.AWTEDTExecutor;
import com.jogamp.common.util.RunnableTask;
import com.jogamp.common.ExceptionUtils;
import java.awt.EventQueue;
import com.jogamp.newt.util.EDTUtil;

public class AWTEDTUtil implements EDTUtil
{
    public static final boolean DEBUG;
    private final Object edtLock;
    private final ThreadGroup threadGroup;
    private final String name;
    private final Runnable dispatchMessages;
    private NEDT nedt;
    private int start_iter;
    private static long pollPeriod;
    
    public AWTEDTUtil(final ThreadGroup threadGroup, final String s, final Runnable dispatchMessages) {
        this.edtLock = new Object();
        this.nedt = null;
        this.start_iter = 0;
        this.threadGroup = threadGroup;
        this.name = Thread.currentThread().getName() + "-" + s + "-EDT-";
        this.dispatchMessages = dispatchMessages;
        (this.nedt = new NEDT(this.threadGroup, this.name)).setDaemon(true);
    }
    
    @Override
    public final long getPollPeriod() {
        return AWTEDTUtil.pollPeriod;
    }
    
    @Override
    public final void setPollPeriod(final long pollPeriod) {
        AWTEDTUtil.pollPeriod = pollPeriod;
    }
    
    @Override
    public final void start() throws IllegalStateException {
        synchronized (this.edtLock) {
            if (this.nedt.isRunning()) {
                throw new IllegalStateException("EDT still running and not subject to stop. Curr " + Thread.currentThread().getName() + ", NEDT " + this.nedt.getName() + ", isRunning " + this.nedt.isRunning + ", shouldStop " + this.nedt.shouldStop + ", on AWT-EDT " + EventQueue.isDispatchThread());
            }
            if (AWTEDTUtil.DEBUG) {
                System.err.println(Thread.currentThread() + ": AWT-EDT reset - edt: " + this.nedt);
            }
            if (this.nedt.getState() != Thread.State.NEW) {
                (this.nedt = new NEDT(this.threadGroup, this.name)).setDaemon(true);
            }
            this.startImpl();
        }
        if (!this.nedt.isRunning()) {
            throw new RuntimeException("EDT could not be started: " + this.nedt);
        }
    }
    
    private final void startImpl() {
        if (this.nedt.isAlive()) {
            throw new RuntimeException("AWT-EDT Thread.isAlive(): true, isRunning: " + this.nedt.isRunning + ", shouldStop " + this.nedt.shouldStop + ", edt: " + this.nedt);
        }
        ++this.start_iter;
        this.nedt.setName(this.name + this.start_iter);
        if (AWTEDTUtil.DEBUG) {
            System.err.println(Thread.currentThread() + ": AWT-EDT START - edt: " + this.nedt);
        }
        this.nedt.start();
    }
    
    @Override
    public final boolean isCurrentThreadEDT() {
        return EventQueue.isDispatchThread();
    }
    
    @Override
    public final boolean isCurrentThreadNEDT() {
        return this.nedt == Thread.currentThread();
    }
    
    @Override
    public final boolean isCurrentThreadEDTorNEDT() {
        return EventQueue.isDispatchThread() || this.nedt == Thread.currentThread();
    }
    
    @Override
    public final boolean isRunning() {
        return this.nedt.isRunning();
    }
    
    @Override
    public final boolean invokeStop(final boolean b, final Runnable runnable) {
        return this.invokeImpl(b, runnable, true);
    }
    
    @Override
    public final boolean invoke(final boolean b, final Runnable runnable) {
        return this.invokeImpl(b, runnable, false);
    }
    
    private final boolean invokeImpl(boolean b, final Runnable runnable, final boolean b2) {
        final Object o = new Object();
        synchronized (o) {
            TaskBase taskBase;
            synchronized (this.edtLock) {
                if (this.nedt.shouldStop) {
                    System.err.println(Thread.currentThread() + ": Warning: AWT-EDT about (1) to stop, won't enqueue new task: " + this.nedt);
                    if (AWTEDTUtil.DEBUG) {
                        ExceptionUtils.dumpStack(System.err);
                    }
                    return false;
                }
                if (this.isCurrentThreadEDT()) {
                    if (null != runnable) {
                        runnable.run();
                    }
                    b = false;
                    taskBase = null;
                    if (b2) {
                        this.nedt.shouldStop = true;
                    }
                }
                else {
                    if (!this.nedt.isRunning) {
                        if (null != runnable) {
                            if (b2) {
                                System.err.println(Thread.currentThread() + ": Warning: AWT-EDT is about (3) to stop and stopped already, dropping task. NEDT " + this.nedt);
                            }
                            else {
                                System.err.println(Thread.currentThread() + ": Warning: AWT-EDT is not running, dropping task. NEDT " + this.nedt);
                            }
                            if (AWTEDTUtil.DEBUG) {
                                ExceptionUtils.dumpStack(System.err);
                            }
                        }
                        return false;
                    }
                    if (b2) {
                        if (AWTEDTUtil.DEBUG) {
                            System.err.println(Thread.currentThread() + ": AWT-EDT signal STOP (on edt: " + this.isCurrentThreadEDT() + ") - " + this.nedt + ", isRunning " + this.nedt.isRunning + ", shouldStop " + this.nedt.shouldStop);
                        }
                        synchronized (this.nedt.sync) {
                            this.nedt.shouldStop = true;
                            this.nedt.sync.notifyAll();
                        }
                    }
                    if (null != runnable) {
                        taskBase = new RunnableTask(runnable, b ? o : null, true, b ? null : System.err);
                        AWTEDTExecutor.singleton.invoke(false, taskBase);
                    }
                    else {
                        b = false;
                        taskBase = null;
                    }
                }
            }
            if (b) {
                try {
                    while (taskBase.isInQueue()) {
                        o.wait();
                    }
                }
                catch (InterruptedException ex) {
                    throw new InterruptedRuntimeException(ex);
                }
                final Throwable throwable = taskBase.getThrowable();
                if (null != throwable) {
                    if (throwable instanceof NativeWindowException) {
                        throw (NativeWindowException)throwable;
                    }
                    throw new RuntimeException(throwable);
                }
            }
            return true;
        }
    }
    
    @Override
    public final boolean waitUntilIdle() {
        final NEDT nedt;
        synchronized (this.edtLock) {
            nedt = this.nedt;
        }
        if (!nedt.isRunning || nedt == Thread.currentThread() || EventQueue.isDispatchThread()) {
            return false;
        }
        try {
            AWTEDTExecutor.singleton.invoke(true, new Runnable() {
                @Override
                public void run() {
                }
            });
        }
        catch (Exception ex) {}
        return true;
    }
    
    @Override
    public final boolean waitUntilStopped() {
        synchronized (this.edtLock) {
            if (this.nedt.isRunning && this.nedt != Thread.currentThread() && !EventQueue.isDispatchThread()) {
                try {
                    while (this.nedt.isRunning) {
                        this.edtLock.wait();
                    }
                }
                catch (InterruptedException ex) {
                    throw new InterruptedRuntimeException(ex);
                }
                return true;
            }
            return false;
        }
    }
    
    static {
        DEBUG = Debug.debug("EDT");
        AWTEDTUtil.pollPeriod = 10L;
    }
    
    class NEDT extends InterruptSource.Thread
    {
        volatile boolean shouldStop;
        volatile boolean isRunning;
        Object sync;
        
        public NEDT(final ThreadGroup threadGroup, final String s) {
            super(threadGroup, null, s);
            this.shouldStop = false;
            this.isRunning = false;
            this.sync = new Object();
        }
        
        public final boolean isRunning() {
            return this.isRunning && !this.shouldStop;
        }
        
        @Override
        public final void start() throws IllegalThreadStateException {
            this.isRunning = true;
            super.start();
        }
        
        @Override
        public final void run() {
            if (AWTEDTUtil.DEBUG) {
                System.err.println(this.getName() + ": AWT-EDT run() START " + this.getName());
            }
            Object o = null;
            try {
                do {
                    if (!this.shouldStop) {
                        AWTEDTExecutor.singleton.invoke(true, AWTEDTUtil.this.dispatchMessages);
                    }
                    synchronized (this.sync) {
                        if (this.shouldStop) {
                            continue;
                        }
                        try {
                            this.sync.wait(AWTEDTUtil.pollPeriod);
                        }
                        catch (InterruptedException ex) {
                            throw new InterruptedRuntimeException(ex);
                        }
                    }
                } while (!this.shouldStop);
            }
            catch (Throwable t) {
                this.shouldStop = true;
                if (t instanceof RuntimeException) {
                    o = t;
                }
                else {
                    o = new RuntimeException("Within AWT-EDT", t);
                }
            }
            finally {
                if (AWTEDTUtil.DEBUG) {
                    System.err.println(this.getName() + ": AWT-EDT run() END " + this.getName() + ", " + o);
                }
                synchronized (AWTEDTUtil.this.edtLock) {
                    this.isRunning = false;
                    AWTEDTUtil.this.edtLock.notifyAll();
                }
                if (AWTEDTUtil.DEBUG) {
                    System.err.println(this.getName() + ": AWT-EDT run() EXIT " + this.getName() + ", exception: " + o);
                }
                if (null != o) {
                    throw o;
                }
            }
        }
    }
}
