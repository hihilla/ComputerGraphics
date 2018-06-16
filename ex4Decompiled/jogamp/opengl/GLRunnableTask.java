// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLRunnable;

public class GLRunnableTask implements GLRunnable
{
    GLRunnable runnable;
    Object notifyObject;
    boolean catchExceptions;
    volatile boolean isExecuted;
    volatile boolean isFlushed;
    Throwable runnableException;
    
    public GLRunnableTask(final GLRunnable runnable, final Object notifyObject, final boolean catchExceptions) {
        this.runnable = runnable;
        this.notifyObject = notifyObject;
        this.catchExceptions = catchExceptions;
        this.isExecuted = false;
        this.isFlushed = false;
    }
    
    @Override
    public boolean run(final GLAutoDrawable glAutoDrawable) {
        boolean b = true;
        if (null == this.notifyObject) {
            try {
                b = this.runnable.run(glAutoDrawable);
            }
            catch (Throwable runnableException) {
                this.runnableException = runnableException;
                if (!this.catchExceptions) {
                    throw new RuntimeException(this.runnableException);
                }
                ExceptionUtils.dumpThrowable("", this.runnableException);
            }
            finally {
                this.isExecuted = true;
            }
        }
        else {
            synchronized (this.notifyObject) {
                try {
                    b = this.runnable.run(glAutoDrawable);
                }
                catch (Throwable runnableException2) {
                    this.runnableException = runnableException2;
                    if (!this.catchExceptions) {
                        throw new RuntimeException(this.runnableException);
                    }
                    ExceptionUtils.dumpThrowable("", this.runnableException);
                }
                finally {
                    this.isExecuted = true;
                    this.notifyObject.notifyAll();
                }
            }
        }
        return b;
    }
    
    public void flush() {
        if (!this.isExecuted() && null != this.notifyObject) {
            synchronized (this.notifyObject) {
                this.isFlushed = true;
                this.notifyObject.notifyAll();
            }
        }
    }
    
    public boolean isInQueue() {
        return !this.isExecuted && !this.isFlushed;
    }
    
    public boolean isExecuted() {
        return this.isExecuted;
    }
    
    public boolean isFlushed() {
        return this.isFlushed;
    }
    
    public Throwable getThrowable() {
        return this.runnableException;
    }
}
