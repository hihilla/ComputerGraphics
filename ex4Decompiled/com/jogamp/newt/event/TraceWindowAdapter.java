// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

public class TraceWindowAdapter implements WindowListener
{
    WindowListener downstream;
    
    public TraceWindowAdapter() {
        this.downstream = null;
    }
    
    public TraceWindowAdapter(final WindowListener downstream) {
        this.downstream = downstream;
    }
    
    @Override
    public void windowResized(final WindowEvent windowEvent) {
        System.err.println(windowEvent);
        if (null != this.downstream) {
            this.downstream.windowResized(windowEvent);
        }
    }
    
    @Override
    public void windowMoved(final WindowEvent windowEvent) {
        System.err.println(windowEvent);
        if (null != this.downstream) {
            this.downstream.windowMoved(windowEvent);
        }
    }
    
    @Override
    public void windowDestroyNotify(final WindowEvent windowEvent) {
        System.err.println(windowEvent);
        if (null != this.downstream) {
            this.downstream.windowDestroyNotify(windowEvent);
        }
    }
    
    @Override
    public void windowDestroyed(final WindowEvent windowEvent) {
        System.err.println(windowEvent);
        if (null != this.downstream) {
            this.downstream.windowDestroyed(windowEvent);
        }
    }
    
    @Override
    public void windowGainedFocus(final WindowEvent windowEvent) {
        System.err.println(windowEvent);
        if (null != this.downstream) {
            this.downstream.windowGainedFocus(windowEvent);
        }
    }
    
    @Override
    public void windowLostFocus(final WindowEvent windowEvent) {
        System.err.println(windowEvent);
        if (null != this.downstream) {
            this.downstream.windowLostFocus(windowEvent);
        }
    }
    
    @Override
    public void windowRepaint(final WindowUpdateEvent windowUpdateEvent) {
        System.err.println(windowUpdateEvent);
        if (null != this.downstream) {
            this.downstream.windowRepaint(windowUpdateEvent);
        }
    }
}
