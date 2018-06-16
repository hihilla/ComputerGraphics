// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.common.util.locks.Lock;
import jogamp.common.util.locks.LockDebugUtil;
import java.util.ArrayList;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.common.util.RunnableTask;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.newt.util.EDTUtil;

public class DefaultEDTUtil implements EDTUtil
{
    public static final boolean DEBUG;
    private static final Object TASK_ATTACHMENT_STOP;
    private static final Object TASK_ATTACHMENT_TEST_ERROR;
    private final Object edtLock;
    private ThreadGroup threadGroup;
    private final String name;
    private final Runnable dispatchMessages;
    private NEDT edt;
    private int start_iter;
    private static long pollPeriod;
    private static Runnable nullTask;
    
    public DefaultEDTUtil(final ThreadGroup threadGroup, final String s, final Runnable dispatchMessages) {
        this.edtLock = new Object();
        this.edt = null;
        this.start_iter = 0;
        this.threadGroup = threadGroup;
        this.name = Thread.currentThread().getName() + "-" + s + "-EDT-";
        this.dispatchMessages = dispatchMessages;
        (this.edt = new NEDT(this.threadGroup, this.name)).setDaemon(true);
    }
    
    @Override
    public final long getPollPeriod() {
        return DefaultEDTUtil.pollPeriod;
    }
    
    @Override
    public final void setPollPeriod(final long pollPeriod) {
        DefaultEDTUtil.pollPeriod = pollPeriod;
    }
    
    @Override
    public final void start() throws IllegalStateException {
        synchronized (this.edtLock) {
            if (this.edt.isRunning()) {
                throw new IllegalStateException("EDT still running and not subject to stop. Curr " + Thread.currentThread().getName() + ", EDT " + this.edt.getName() + ", isRunning " + this.edt.isRunning + ", shouldStop " + this.edt.shouldStop);
            }
            if (DefaultEDTUtil.DEBUG) {
                if (this.edt.tasks.size() > 0) {
                    System.err.println(Thread.currentThread() + ": Default-EDT reset, remaining tasks: " + this.edt.tasks.size() + " - " + this.edt);
                }
                System.err.println(Thread.currentThread() + ": Default-EDT reset - edt: " + this.edt);
            }
            if (this.edt.getState() != Thread.State.NEW) {
                if (null != this.threadGroup && this.threadGroup.isDestroyed()) {
                    this.threadGroup = Thread.currentThread().getThreadGroup();
                }
                (this.edt = new NEDT(this.threadGroup, this.name)).setDaemon(true);
            }
            this.startImpl();
        }
        if (!this.edt.isRunning()) {
            throw new RuntimeException("EDT could not be started: " + this.edt);
        }
    }
    
    private final void startImpl() {
        if (this.edt.isAlive()) {
            throw new RuntimeException("Default-EDT Thread.isAlive(): true, isRunning: " + this.edt.isRunning + ", shouldStop " + this.edt.shouldStop + ", edt: " + this.edt + ", tasks: " + this.edt.tasks.size());
        }
        ++this.start_iter;
        this.edt.setName(this.name + this.start_iter);
        if (DefaultEDTUtil.DEBUG) {
            System.err.println(Thread.currentThread() + ": Default-EDT START - edt: " + this.edt);
        }
        this.edt.start();
    }
    
    @Override
    public final boolean isCurrentThreadEDT() {
        return this.edt == Thread.currentThread();
    }
    
    @Override
    public final boolean isCurrentThreadNEDT() {
        return this.edt == Thread.currentThread();
    }
    
    @Override
    public final boolean isCurrentThreadEDTorNEDT() {
        return this.edt == Thread.currentThread();
    }
    
    @Override
    public final boolean isRunning() {
        return this.edt.isRunning();
    }
    
    @Override
    public final boolean invokeStop(final boolean b, final Runnable runnable) {
        if (DefaultEDTUtil.DEBUG) {
            System.err.println(Thread.currentThread() + ": Default-EDT.invokeStop wait " + b);
            ExceptionUtils.dumpStack(System.err);
        }
        return this.invokeImpl(b, runnable, true, false);
    }
    
    public final boolean invokeAndWaitError(final Runnable runnable) {
        if (DefaultEDTUtil.DEBUG) {
            System.err.println(Thread.currentThread() + ": Default-EDT.invokeAndWaitError");
            ExceptionUtils.dumpStack(System.err);
        }
        return this.invokeImpl(true, runnable, false, true);
    }
    
    @Override
    public final boolean invoke(final boolean b, final Runnable runnable) {
        return this.invokeImpl(b, runnable, false, false);
    }
    
    private final boolean invokeImpl(boolean b, Runnable nullTask, final boolean b2, final boolean b3) {
        final Object o = new Object();
        synchronized (o) {
            RunnableTask runnableTask;
            synchronized (this.edtLock) {
                if (this.edt.shouldStop) {
                    System.err.println(Thread.currentThread() + ": Warning: Default-EDT about (1) to stop, won't enqueue new task: " + this.edt);
                    if (DefaultEDTUtil.DEBUG) {
                        ExceptionUtils.dumpStack(System.err);
                    }
                    return false;
                }
                if (this.isCurrentThreadEDT()) {
                    if (null != nullTask) {
                        nullTask.run();
                    }
                    b = false;
                    runnableTask = null;
                    if (b2) {
                        this.edt.shouldStop = true;
                        if (this.edt.tasks.size() > 0) {
                            System.err.println(Thread.currentThread() + ": Warning: Default-EDT about (2) to stop, task executed. Remaining tasks: " + this.edt.tasks.size() + " - " + this.edt);
                            if (DefaultEDTUtil.DEBUG) {
                                ExceptionUtils.dumpStack(System.err);
                            }
                        }
                    }
                }
                else {
                    if (!this.edt.isRunning) {
                        if (null != nullTask) {
                            if (b2) {
                                System.err.println(Thread.currentThread() + ": Warning: Default-EDT is about (3) to stop and stopped already, dropping task. Remaining tasks: " + this.edt.tasks.size() + " - " + this.edt);
                            }
                            else {
                                System.err.println(Thread.currentThread() + ": Warning: Default-EDT is not running, dropping task. NEDT " + this.edt);
                            }
                            if (DefaultEDTUtil.DEBUG) {
                                ExceptionUtils.dumpStack(System.err);
                            }
                        }
                        return false;
                    }
                    if (b2 && null == nullTask) {
                        nullTask = DefaultEDTUtil.nullTask;
                    }
                    if (null != nullTask) {
                        synchronized (this.edt.tasks) {
                            runnableTask = new RunnableTask(nullTask, b ? o : null, true, b ? null : System.err);
                            if (b2) {
                                runnableTask.setAttachment(DefaultEDTUtil.TASK_ATTACHMENT_STOP);
                            }
                            else if (b3) {
                                runnableTask.setAttachment(DefaultEDTUtil.TASK_ATTACHMENT_TEST_ERROR);
                            }
                            this.edt.tasks.add(runnableTask);
                            this.edt.tasks.notifyAll();
                        }
                    }
                    else {
                        b = false;
                        runnableTask = null;
                    }
                }
            }
            if (b) {
                try {
                    while (runnableTask.isInQueue()) {
                        o.wait();
                    }
                }
                catch (InterruptedException ex) {
                    throw new InterruptedRuntimeException(ex);
                }
                final Throwable throwable = runnableTask.getThrowable();
                if (null != throwable) {
                    if (throwable instanceof NativeWindowException) {
                        throw (NativeWindowException)throwable;
                    }
                    throw new RuntimeException(throwable);
                }
            }
            if (DefaultEDTUtil.DEBUG && b2) {
                System.err.println(Thread.currentThread() + ": Default-EDT signal STOP X edt: " + this.edt);
            }
            return true;
        }
    }
    
    @Override
    public final boolean waitUntilIdle() {
        final NEDT edt;
        synchronized (this.edtLock) {
            edt = this.edt;
        }
        if (!edt.isRunning || edt == Thread.currentThread()) {
            return false;
        }
        synchronized (edt.tasks) {
            try {
                while (edt.isRunning && edt.tasks.size() > 0) {
                    edt.tasks.notifyAll();
                    edt.tasks.wait();
                }
            }
            catch (InterruptedException ex) {
                throw new InterruptedRuntimeException(ex);
            }
            return true;
        }
    }
    
    @Override
    public final boolean waitUntilStopped() {
        synchronized (this.edtLock) {
            if (this.edt.isRunning && this.edt != Thread.currentThread()) {
                try {
                    while (this.edt.isRunning) {
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
        TASK_ATTACHMENT_STOP = new Object();
        TASK_ATTACHMENT_TEST_ERROR = new Object();
        DefaultEDTUtil.pollPeriod = 10L;
        DefaultEDTUtil.nullTask = new Runnable() {
            @Override
            public void run() {
            }
        };
    }
    
    class NEDT extends InterruptSource.Thread
    {
        volatile boolean shouldStop;
        volatile boolean isRunning;
        final ArrayList<RunnableTask> tasks;
        
        public NEDT(final ThreadGroup threadGroup, final String s) {
            super(threadGroup, null, s);
            this.shouldStop = false;
            this.isRunning = false;
            this.tasks = new ArrayList<RunnableTask>();
        }
        
        public final boolean isRunning() {
            return this.isRunning && !this.shouldStop;
        }
        
        @Override
        public final void start() throws IllegalThreadStateException {
            this.isRunning = true;
            super.start();
        }
        
        private final void validateNoRecursiveLocksHold() {
            if (LockDebugUtil.getRecursiveLockTrace().size() > 0) {
                LockDebugUtil.dumpRecursiveLockTrace(System.err);
                throw new InternalError("XXX");
            }
        }
        
        @Override
        public final void run() {
            if (DefaultEDTUtil.DEBUG) {
                System.err.println(this.getName() + ": Default-EDT run() START " + this.getName());
            }
            if (Lock.DEBUG) {
                this.validateNoRecursiveLocksHold();
            }
            Throwable t = null;
            try {
                do {
                    if (!this.shouldStop) {
                        DefaultEDTUtil.this.dispatchMessages.run();
                    }
                    RunnableTask runnableTask = null;
                    synchronized (this.tasks) {
                        if (!this.shouldStop && this.tasks.size() == 0) {
                            try {
                                this.tasks.wait(DefaultEDTUtil.pollPeriod);
                            }
                            catch (InterruptedException ex) {
                                throw new InterruptedRuntimeException(ex);
                            }
                        }
                        if (this.tasks.size() > 0) {
                            runnableTask = this.tasks.remove(0);
                            this.tasks.notifyAll();
                            final Object attachment = runnableTask.getAttachment();
                            if (DefaultEDTUtil.TASK_ATTACHMENT_STOP == attachment) {
                                this.shouldStop = true;
                            }
                            else if (DefaultEDTUtil.TASK_ATTACHMENT_TEST_ERROR == attachment) {
                                this.tasks.add(0, runnableTask);
                                runnableTask = null;
                                throw new RuntimeException("TASK_ATTACHMENT_TEST_ERROR");
                            }
                        }
                    }
                    if (null != runnableTask) {
                        runnableTask.run();
                        if (Lock.DEBUG) {
                            this.validateNoRecursiveLocksHold();
                        }
                        if (runnableTask.hasWaiter() || null == runnableTask.getThrowable()) {
                            continue;
                        }
                        System.err.println("DefaultEDT.run(): Caught exception occured on thread " + java.lang.Thread.currentThread().getName() + ": " + runnableTask.toString());
                        runnableTask.getThrowable().printStackTrace();
                    }
                } while (!this.shouldStop);
            }
            catch (Throwable t2) {
                this.shouldStop = true;
                if (t2 instanceof RuntimeException) {
                    t = t2;
                }
                else {
                    t = new RuntimeException("Within Default-EDT", t2);
                }
            }
            finally {
                final String string = this.getName() + ": Default-EDT finished w/ " + this.tasks.size() + " left";
                if (DefaultEDTUtil.DEBUG) {
                    System.err.println(string + ", " + t);
                }
                synchronized (DefaultEDTUtil.this.edtLock) {
                    int n = 0;
                    while (this.tasks.size() > 0) {
                        final String string2 = string + ", task #" + n;
                        final Throwable t3 = (null != t) ? new Throwable(string2, t) : new Throwable(string2);
                        final RunnableTask runnableTask2 = this.tasks.remove(0);
                        if (null != runnableTask2) {
                            runnableTask2.flush(t3);
                            ++n;
                        }
                    }
                    this.isRunning = false;
                    DefaultEDTUtil.this.edtLock.notifyAll();
                }
                if (DefaultEDTUtil.DEBUG) {
                    System.err.println(string + " EXIT, exception: " + t);
                }
                if (null != t) {
                    throw t;
                }
            }
        }
    }
}
