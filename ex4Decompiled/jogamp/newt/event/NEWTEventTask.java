// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.event;

import com.jogamp.newt.event.NEWTEvent;

public class NEWTEventTask
{
    private final NEWTEvent event;
    private final Object notifyObject;
    private RuntimeException exception;
    private volatile boolean dispatched;
    
    public NEWTEventTask(final NEWTEvent event, final Object notifyObject) {
        this.event = event;
        this.notifyObject = notifyObject;
        this.exception = null;
        this.dispatched = false;
    }
    
    public final NEWTEvent get() {
        return this.event;
    }
    
    public final void setException(final RuntimeException exception) {
        this.exception = exception;
    }
    
    public final RuntimeException getException() {
        return this.exception;
    }
    
    public final boolean isCallerWaiting() {
        return null != this.notifyObject;
    }
    
    public final boolean isDispatched() {
        return this.dispatched;
    }
    
    public final void setDispatched() {
        this.dispatched = true;
    }
    
    public void notifyCaller() {
        this.setDispatched();
        if (null != this.notifyObject) {
            synchronized (this.notifyObject) {
                this.notifyObject.notifyAll();
            }
        }
    }
}
