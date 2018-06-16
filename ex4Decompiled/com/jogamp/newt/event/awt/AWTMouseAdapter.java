// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event.awt;

import java.awt.event.MouseWheelEvent;
import com.jogamp.newt.event.NEWTEvent;
import jogamp.newt.awt.event.AWTNewtEventFactory;
import java.awt.event.MouseEvent;
import java.awt.Component;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.NEWTEventListener;
import com.jogamp.nativewindow.NativeSurfaceHolder;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

public class AWTMouseAdapter extends AWTAdapter implements MouseListener, MouseMotionListener, MouseWheelListener
{
    public AWTMouseAdapter(final com.jogamp.newt.event.MouseListener mouseListener, final NativeSurfaceHolder nativeSurfaceHolder) {
        super(mouseListener, nativeSurfaceHolder);
    }
    
    public AWTMouseAdapter(final com.jogamp.newt.event.MouseListener mouseListener, final Window window) {
        super(mouseListener, window);
    }
    
    public AWTMouseAdapter(final Window window) {
        super(window);
    }
    
    public AWTMouseAdapter() {
    }
    
    @Override
    public synchronized AWTAdapter addTo(final Component component) {
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
        component.addMouseWheelListener(this);
        return this;
    }
    
    @Override
    public synchronized AWTAdapter removeFrom(final Component component) {
        component.removeMouseListener(this);
        component.removeMouseMotionListener(this);
        component.removeMouseWheelListener(this);
        return this;
    }
    
    @Override
    public synchronized void mouseClicked(final MouseEvent mouseEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.MouseEvent mouseEvent2 = AWTNewtEventFactory.createMouseEvent(mouseEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            mouseEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, mouseEvent2)) {
            ((com.jogamp.newt.event.MouseListener)this.newtListener).mouseClicked(mouseEvent2);
        }
    }
    
    @Override
    public synchronized void mouseEntered(final MouseEvent mouseEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.MouseEvent mouseEvent2 = AWTNewtEventFactory.createMouseEvent(mouseEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            mouseEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, mouseEvent2)) {
            ((com.jogamp.newt.event.MouseListener)this.newtListener).mouseEntered(mouseEvent2);
        }
    }
    
    @Override
    public synchronized void mouseExited(final MouseEvent mouseEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.MouseEvent mouseEvent2 = AWTNewtEventFactory.createMouseEvent(mouseEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            mouseEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, mouseEvent2)) {
            ((com.jogamp.newt.event.MouseListener)this.newtListener).mouseExited(mouseEvent2);
        }
    }
    
    @Override
    public synchronized void mousePressed(final MouseEvent mouseEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.MouseEvent mouseEvent2 = AWTNewtEventFactory.createMouseEvent(mouseEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            mouseEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, mouseEvent2)) {
            ((com.jogamp.newt.event.MouseListener)this.newtListener).mousePressed(mouseEvent2);
        }
    }
    
    @Override
    public synchronized void mouseReleased(final MouseEvent mouseEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.MouseEvent mouseEvent2 = AWTNewtEventFactory.createMouseEvent(mouseEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            mouseEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, mouseEvent2)) {
            ((com.jogamp.newt.event.MouseListener)this.newtListener).mouseReleased(mouseEvent2);
        }
    }
    
    @Override
    public synchronized void mouseDragged(final MouseEvent mouseEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.MouseEvent mouseEvent2 = AWTNewtEventFactory.createMouseEvent(mouseEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            mouseEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, mouseEvent2)) {
            ((com.jogamp.newt.event.MouseListener)this.newtListener).mouseDragged(mouseEvent2);
        }
    }
    
    @Override
    public synchronized void mouseMoved(final MouseEvent mouseEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.MouseEvent mouseEvent2 = AWTNewtEventFactory.createMouseEvent(mouseEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            mouseEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, mouseEvent2)) {
            ((com.jogamp.newt.event.MouseListener)this.newtListener).mouseMoved(mouseEvent2);
        }
    }
    
    @Override
    public synchronized void mouseWheelMoved(final MouseWheelEvent mouseWheelEvent) {
        if (!this.isSetup) {
            return;
        }
        final com.jogamp.newt.event.MouseEvent mouseEvent = AWTNewtEventFactory.createMouseEvent(mouseWheelEvent, this.nsHolder);
        if (this.consumeAWTEvent) {
            mouseWheelEvent.consume();
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, mouseEvent)) {
            ((com.jogamp.newt.event.MouseListener)this.newtListener).mouseWheelMoved(mouseEvent);
        }
    }
}
