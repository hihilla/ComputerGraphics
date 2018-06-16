// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.swt;

import com.jogamp.common.util.InterruptSource;
import jogamp.newt.Debug;
import com.jogamp.common.util.TaskBase;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.common.util.RunnableTask;
import com.jogamp.common.ExceptionUtils;
import jogamp.newt.DisplayImpl;
import org.eclipse.swt.widgets.Display;
import com.jogamp.newt.util.EDTUtil;

public class SWTEDTUtil implements EDTUtil
{
    public static final boolean DEBUG;
    private final Object edtLock;
    private final ThreadGroup threadGroup;
    private final String name;
    private final Runnable dispatchMessages;
    private final Display swtDisplay;
    private NEDT nedt;
    private int start_iter;
    private static long pollPeriod;
    
    public SWTEDTUtil(final com.jogamp.newt.Display display, final Display swtDisplay) {
        this.edtLock = new Object();
        this.nedt = null;
        this.start_iter = 0;
        this.threadGroup = Thread.currentThread().getThreadGroup();
        this.name = Thread.currentThread().getName() + "-SWTDisplay-" + display.getFQName() + "-EDT-";
        this.dispatchMessages = new Runnable() {
            @Override
            public void run() {
                ((DisplayImpl)display).dispatchMessages();
            }
        };
        this.swtDisplay = swtDisplay;
        (this.nedt = new NEDT(this.threadGroup, this.name)).setDaemon(true);
    }
    
    public final Display getDisplay() {
        return this.swtDisplay;
    }
    
    @Override
    public long getPollPeriod() {
        return SWTEDTUtil.pollPeriod;
    }
    
    @Override
    public void setPollPeriod(final long pollPeriod) {
        SWTEDTUtil.pollPeriod = pollPeriod;
    }
    
    @Override
    public final void start() throws IllegalStateException {
        final boolean disposed = this.swtDisplay.isDisposed();
        synchronized (this.edtLock) {
            if (this.nedt.isRunning()) {
                final Thread currentThread = Thread.currentThread();
                Thread thread;
                String name;
                if (!disposed) {
                    thread = this.swtDisplay.getThread();
                    name = thread.getName();
                }
                else {
                    thread = null;
                    name = null;
                }
                throw new IllegalStateException("EDT still running and not subject to stop. Curr " + currentThread.getName() + ", NEDT " + this.nedt.getName() + ", isRunning " + this.nedt.isRunning + ", shouldStop " + this.nedt.shouldStop + ", SWT-EDT " + name + ", on SWT-EDT " + (thread == currentThread));
            }
            if (SWTEDTUtil.DEBUG) {
                System.err.println(Thread.currentThread() + ": SWT-EDT reset - edt: " + this.nedt + ", swtDisposed (skipping) " + disposed);
            }
            if (!disposed) {
                if (this.nedt.getState() != Thread.State.NEW) {
                    (this.nedt = new NEDT(this.threadGroup, this.name)).setDaemon(true);
                }
                this.startImpl();
            }
        }
        if (!disposed && !this.nedt.isRunning()) {
            throw new RuntimeException("EDT could not be started: " + this.nedt);
        }
    }
    
    private final void startImpl() {
        if (this.nedt.isAlive()) {
            throw new RuntimeException("SWT-EDT Thread.isAlive(): true, isRunning: " + this.nedt.isRunning + ", shouldStop " + this.nedt.shouldStop + ", edt: " + this.nedt);
        }
        ++this.start_iter;
        this.nedt.setName(this.name + this.start_iter);
        if (SWTEDTUtil.DEBUG) {
            System.err.println(Thread.currentThread() + ": SWT-EDT START - edt: " + this.nedt);
        }
        this.nedt.start();
    }
    
    @Override
    public boolean isCurrentThreadEDT() {
        return !this.swtDisplay.isDisposed() && this.swtDisplay.getThread() == Thread.currentThread();
    }
    
    @Override
    public final boolean isCurrentThreadNEDT() {
        return this.nedt == Thread.currentThread();
    }
    
    @Override
    public final boolean isCurrentThreadEDTorNEDT() {
        final Thread currentThread = Thread.currentThread();
        return (!this.swtDisplay.isDisposed() && currentThread == this.swtDisplay.getThread()) || currentThread == this.nedt;
    }
    
    @Override
    public boolean isRunning() {
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
    
    private final boolean invokeImpl(boolean b, final Runnable runnable, boolean b2) {
        final Object o = new Object();
        synchronized (o) {
            TaskBase taskBase;
            synchronized (this.edtLock) {
                if (this.nedt.shouldStop) {
                    if (SWTEDTUtil.DEBUG) {
                        System.err.println(Thread.currentThread() + ": Warning: SWT-EDT about (1) to stop, won't enqueue new task: " + this.nedt + ", isRunning " + this.nedt.isRunning + ", shouldStop " + this.nedt.shouldStop);
                        ExceptionUtils.dumpStack(System.err);
                    }
                    return false;
                }
                if (this.swtDisplay.isDisposed()) {
                    b2 = true;
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
                    if (!this.nedt.isRunning && !this.swtDisplay.isDisposed()) {
                        if (null != runnable) {
                            if (b2) {
                                System.err.println(Thread.currentThread() + ": Warning: SWT-EDT is about (3) to stop and stopped already, dropping task. NEDT " + this.nedt);
                            }
                            else {
                                System.err.println(Thread.currentThread() + ": Warning: SWT-EDT is not running, dropping task. NEDT " + this.nedt);
                            }
                            if (SWTEDTUtil.DEBUG) {
                                ExceptionUtils.dumpStack(System.err);
                            }
                        }
                        return false;
                    }
                    if (b2) {
                        if (this.nedt.isRunning) {
                            if (SWTEDTUtil.DEBUG) {
                                System.err.println(Thread.currentThread() + ": SWT-EDT signal STOP (on edt: " + this.isCurrentThreadEDT() + ") - " + this.nedt + ", isRunning " + this.nedt.isRunning + ", shouldStop " + this.nedt.shouldStop);
                            }
                            synchronized (this.nedt.sync) {
                                this.nedt.shouldStop = true;
                                this.nedt.sync.notifyAll();
                            }
                        }
                        if (this.swtDisplay.isDisposed()) {
                            System.err.println(Thread.currentThread() + ": Warning: SWT-EDT is about (3) to stop and stopped already, dropping task. " + this.nedt);
                            if (SWTEDTUtil.DEBUG) {
                                ExceptionUtils.dumpStack(System.err);
                            }
                            return false;
                        }
                    }
                    if (null != runnable) {
                        taskBase = new RunnableTask(runnable, b ? o : null, true, b ? null : System.err);
                        this.swtDisplay.asyncExec((Runnable)taskBase);
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
        final Thread currentThread = Thread.currentThread();
        if (!nedt.isRunning || nedt == currentThread || this.swtDisplay.isDisposed() || this.swtDisplay.getThread() == currentThread) {
            return false;
        }
        try {
            this.swtDisplay.syncExec((Runnable)new Runnable() {
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
            final Thread currentThread = Thread.currentThread();
            final boolean b = (this.swtDisplay.isDisposed() ? null : this.swtDisplay.getThread()) == currentThread;
            if (this.nedt.isRunning && this.nedt != currentThread && !b) {
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
        SWTEDTUtil.pollPeriod = 10L;
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
            if (SWTEDTUtil.DEBUG) {
                System.err.println(this.getName() + ": SWT-EDT run() START " + this.getName());
            }
            Object o = null;
            try {
                do {
                    if (!this.shouldStop) {
                        if (!SWTEDTUtil.this.swtDisplay.isDisposed()) {
                            SWTEDTUtil.this.swtDisplay.syncExec(SWTEDTUtil.this.dispatchMessages);
                        }
                        else {
                            SWTEDTUtil.this.dispatchMessages.run();
                        }
                    }
                    synchronized (this.sync) {
                        if (this.shouldStop) {
                            continue;
                        }
                        try {
                            this.sync.wait(SWTEDTUtil.pollPeriod);
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
                    o = new RuntimeException("Within SWT-EDT", t);
                }
            }
            finally {
                if (SWTEDTUtil.DEBUG) {
                    System.err.println(this.getName() + ": SWT-EDT run() END " + this.getName() + ", " + o);
                }
                synchronized (SWTEDTUtil.this.edtLock) {
                    this.isRunning = false;
                    SWTEDTUtil.this.edtLock.notifyAll();
                }
                if (SWTEDTUtil.DEBUG) {
                    System.err.println(this.getName() + ": SWT-EDT run() EXIT " + this.getName() + ", exception: " + o);
                }
                if (null != o) {
                    throw o;
                }
            }
        }
    }
}
