// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event.awt;

import com.jogamp.newt.event.NEWTEvent;
import jogamp.newt.awt.event.AWTNewtEventFactory;
import java.awt.event.KeyEvent;
import java.awt.Component;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.NEWTEventListener;
import com.jogamp.nativewindow.NativeSurfaceHolder;
import java.awt.event.KeyListener;

public class AWTKeyAdapter extends AWTAdapter implements KeyListener
{
    public AWTKeyAdapter(final com.jogamp.newt.event.KeyListener keyListener, final NativeSurfaceHolder nativeSurfaceHolder) {
        super(keyListener, nativeSurfaceHolder);
    }
    
    public AWTKeyAdapter(final com.jogamp.newt.event.KeyListener keyListener, final Window window) {
        super(keyListener, window);
    }
    
    public AWTKeyAdapter(final Window window) {
        super(window);
    }
    
    public AWTKeyAdapter() {
    }
    
    @Override
    public synchronized AWTAdapter addTo(final Component component) {
        component.addKeyListener(this);
        return this;
    }
    
    @Override
    public synchronized AWTAdapter removeFrom(final Component component) {
        component.removeKeyListener(this);
        return this;
    }
    
    @Override
    public synchronized void keyPressed(final KeyEvent keyEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.KeyEvent keyEvent2 = AWTNewtEventFactory.createKeyEvent((short)300, keyEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            keyEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, keyEvent2)) {
            ((com.jogamp.newt.event.KeyListener)this.newtListener).keyPressed(keyEvent2);
        }
    }
    
    @Override
    public synchronized void keyReleased(final KeyEvent keyEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.KeyEvent keyEvent2 = AWTNewtEventFactory.createKeyEvent((short)301, keyEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            keyEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, keyEvent2)) {
            ((com.jogamp.newt.event.KeyListener)this.newtListener).keyReleased(keyEvent2);
        }
    }
    
    @Override
    public synchronized void keyTyped(final KeyEvent keyEvent) {
        if (!this.isSetup) {
            return;
        }
        if (this.consumeAWTEvent) {
            keyEvent.consume();
        }
    }
}
