// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event.awt;

import jogamp.newt.Debug;
import com.jogamp.newt.event.NEWTEvent;
import java.awt.Component;
import com.jogamp.nativewindow.NativeSurfaceHolder;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.NEWTEventListener;
import java.util.EventListener;

public abstract class AWTAdapter implements EventListener
{
    public static final boolean DEBUG_IMPLEMENTATION;
    NEWTEventListener newtListener;
    Window newtWindow;
    NativeSurfaceHolder nsHolder;
    boolean consumeAWTEvent;
    protected boolean isSetup;
    
    protected AWTAdapter(final NEWTEventListener newtListener, final NativeSurfaceHolder nsHolder) {
        if (null == newtListener) {
            throw new IllegalArgumentException("Argument newtListener is null");
        }
        if (null == nsHolder) {
            throw new IllegalArgumentException("Argument nwProxy is null");
        }
        this.newtListener = newtListener;
        this.newtWindow = null;
        this.nsHolder = nsHolder;
        this.consumeAWTEvent = false;
        this.isSetup = true;
    }
    
    protected AWTAdapter(final NEWTEventListener newtListener, final Window window) {
        if (null == newtListener) {
            throw new IllegalArgumentException("Argument newtListener is null");
        }
        if (null == window) {
            throw new IllegalArgumentException("Argument newtProxy is null");
        }
        this.newtListener = newtListener;
        this.newtWindow = window;
        this.nsHolder = window;
        this.consumeAWTEvent = false;
        this.isSetup = true;
    }
    
    protected AWTAdapter(final Window downstream) throws IllegalStateException {
        this();
        this.setDownstream(downstream);
    }
    
    public AWTAdapter() {
        this.clear();
        this.consumeAWTEvent = false;
    }
    
    public synchronized AWTAdapter setDownstream(final Window window) throws IllegalStateException {
        if (null == window) {
            throw new RuntimeException("Argument downstream is null");
        }
        this.newtListener = null;
        this.newtWindow = window;
        this.nsHolder = window;
        if (null == this.newtWindow.getScreen().getDisplay().getEDTUtil()) {
            throw new IllegalStateException("EDT not enabled");
        }
        this.isSetup = true;
        return this;
    }
    
    public synchronized AWTAdapter clear() {
        this.newtListener = null;
        this.newtWindow = null;
        this.nsHolder = null;
        this.isSetup = false;
        this.consumeAWTEvent = false;
        return this;
    }
    
    public final synchronized void setConsumeAWTEvent(final boolean consumeAWTEvent) {
        this.consumeAWTEvent = consumeAWTEvent;
    }
    
    public final synchronized NativeSurfaceHolder getNativeSurfaceHolder() {
        return this.nsHolder;
    }
    
    public final synchronized Window getNewtWindow() {
        return this.newtWindow;
    }
    
    public final synchronized NEWTEventListener getNewtEventListener() {
        return this.newtListener;
    }
    
    public abstract AWTAdapter addTo(final Component p0);
    
    public abstract AWTAdapter removeFrom(final Component p0);
    
    EventProcRes processEvent(final boolean b, final NEWTEvent newtEvent) {
        if (null != this.newtListener) {
            return EventProcRes.DISPATCH;
        }
        if (null != this.newtWindow) {
            this.newtWindow.enqueueEvent(b, newtEvent);
            return EventProcRes.ENQUEUED;
        }
        return EventProcRes.NOP;
    }
    
    static {
        DEBUG_IMPLEMENTATION = Debug.debug("Window");
    }
    
    enum EventProcRes
    {
        DISPATCH, 
        ENQUEUED, 
        NOP;
    }
}
