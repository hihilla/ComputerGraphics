// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.awt;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import jogamp.nativewindow.awt.AWTMisc;
import java.awt.event.WindowListener;
import java.awt.Window;
import java.awt.Component;
import com.jogamp.nativewindow.WindowClosingProtocol;

public class AWTWindowClosingProtocol implements WindowClosingProtocol
{
    private final Component comp;
    private Window listenTo;
    private final Runnable closingOperationClose;
    private final Runnable closingOperationNOP;
    private final Object closingListenerLock;
    private WindowClosingMode defaultCloseOperation;
    private boolean defaultCloseOperationSetByUser;
    WindowListener windowClosingAdapter;
    
    public AWTWindowClosingProtocol(final Component comp, final Runnable closingOperationClose, final Runnable closingOperationNOP) {
        this.closingListenerLock = new Object();
        this.defaultCloseOperation = WindowClosingMode.DISPOSE_ON_CLOSE;
        this.defaultCloseOperationSetByUser = false;
        this.windowClosingAdapter = new WindowClosingAdapter();
        this.comp = comp;
        this.listenTo = null;
        this.closingOperationClose = closingOperationClose;
        this.closingOperationNOP = closingOperationNOP;
    }
    
    public final boolean addClosingListener() throws IllegalStateException {
        synchronized (this.closingListenerLock) {
            if (null != this.listenTo) {
                throw new IllegalStateException("WindowClosingListener already set");
            }
            this.listenTo = AWTMisc.getWindow(this.comp);
            if (null != this.listenTo) {
                this.listenTo.addWindowListener(this.windowClosingAdapter);
                return true;
            }
        }
        return false;
    }
    
    public final boolean removeClosingListener() {
        synchronized (this.closingListenerLock) {
            if (null != this.listenTo) {
                this.listenTo.removeWindowListener(this.windowClosingAdapter);
                this.listenTo = null;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public final WindowClosingMode getDefaultCloseOperation() {
        synchronized (this.closingListenerLock) {
            if (this.defaultCloseOperationSetByUser) {
                return this.defaultCloseOperation;
            }
        }
        return AWTMisc.getNWClosingOperation(this.comp);
    }
    
    @Override
    public final WindowClosingMode setDefaultCloseOperation(final WindowClosingMode defaultCloseOperation) {
        synchronized (this.closingListenerLock) {
            final WindowClosingMode defaultCloseOperation2 = this.defaultCloseOperation;
            this.defaultCloseOperation = defaultCloseOperation;
            this.defaultCloseOperationSetByUser = true;
            return defaultCloseOperation2;
        }
    }
    
    class WindowClosingAdapter extends WindowAdapter
    {
        @Override
        public void windowClosing(final WindowEvent windowEvent) {
            if (WindowClosingMode.DISPOSE_ON_CLOSE == AWTWindowClosingProtocol.this.getDefaultCloseOperation()) {
                AWTWindowClosingProtocol.this.closingOperationClose.run();
            }
            else if (null != AWTWindowClosingProtocol.this.closingOperationNOP) {
                AWTWindowClosingProtocol.this.closingOperationNOP.run();
            }
        }
    }
}
