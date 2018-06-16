// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event.awt;

import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.ComponentEvent;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.NEWTEvent;
import jogamp.newt.awt.event.AWTNewtEventFactory;
import java.awt.event.FocusEvent;
import java.awt.Component;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.NEWTEventListener;
import com.jogamp.nativewindow.NativeSurfaceHolder;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;
import java.awt.event.ComponentListener;

public class AWTWindowAdapter extends AWTAdapter implements ComponentListener, WindowListener, FocusListener
{
    WindowClosingListener windowClosingListener;
    
    public AWTWindowAdapter(final com.jogamp.newt.event.WindowListener windowListener, final NativeSurfaceHolder nativeSurfaceHolder) {
        super(windowListener, nativeSurfaceHolder);
    }
    
    public AWTWindowAdapter(final com.jogamp.newt.event.WindowListener windowListener, final Window window) {
        super(windowListener, window);
    }
    
    public AWTWindowAdapter(final Window window) {
        super(window);
    }
    
    public AWTWindowAdapter() {
    }
    
    @Override
    public synchronized AWTAdapter addTo(final Component component) {
        final java.awt.Window window = getWindow(component);
        component.addComponentListener(this);
        component.addFocusListener(this);
        if (null != window && null == this.windowClosingListener) {
            window.addWindowListener(this.windowClosingListener = new WindowClosingListener());
        }
        if (component instanceof java.awt.Window) {
            ((java.awt.Window)component).addWindowListener(this);
        }
        return this;
    }
    
    public synchronized AWTAdapter removeWindowClosingFrom(final Component component) {
        final java.awt.Window window = getWindow(component);
        if (null != window && null != this.windowClosingListener) {
            window.removeWindowListener(this.windowClosingListener);
        }
        return this;
    }
    
    @Override
    public synchronized AWTAdapter removeFrom(final Component component) {
        component.removeFocusListener(this);
        component.removeComponentListener(this);
        this.removeWindowClosingFrom(component);
        if (component instanceof java.awt.Window) {
            ((java.awt.Window)component).removeWindowListener(this);
        }
        return this;
    }
    
    static java.awt.Window getWindow(Component parent) {
        while (null != parent && !(parent instanceof java.awt.Window)) {
            parent = parent.getParent();
        }
        return (java.awt.Window)parent;
    }
    
    @Override
    public synchronized void focusGained(final FocusEvent focusEvent) {
        if (!this.isSetup) {
            return;
        }
        final WindowEvent windowEvent = AWTNewtEventFactory.createWindowEvent(focusEvent, this.nsHolder);
        if (AWTWindowAdapter.DEBUG_IMPLEMENTATION) {
            System.err.println("AWT: focusGained: " + focusEvent + " -> " + windowEvent);
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, windowEvent)) {
            ((com.jogamp.newt.event.WindowListener)this.newtListener).windowGainedFocus(windowEvent);
        }
    }
    
    @Override
    public synchronized void focusLost(final FocusEvent focusEvent) {
        if (!this.isSetup) {
            return;
        }
        final WindowEvent windowEvent = AWTNewtEventFactory.createWindowEvent(focusEvent, this.nsHolder);
        if (AWTWindowAdapter.DEBUG_IMPLEMENTATION) {
            System.err.println("AWT: focusLost: " + focusEvent + " -> " + windowEvent);
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, windowEvent)) {
            ((com.jogamp.newt.event.WindowListener)this.newtListener).windowLostFocus(windowEvent);
        }
    }
    
    @Override
    public synchronized void componentResized(final ComponentEvent componentEvent) {
        if (!this.isSetup) {
            return;
        }
        final WindowEvent windowEvent = AWTNewtEventFactory.createWindowEvent(componentEvent, this.nsHolder);
        if (AWTWindowAdapter.DEBUG_IMPLEMENTATION) {
            final Component component = componentEvent.getComponent();
            final Dimension size = component.getSize();
            Insets insets;
            Dimension dimension;
            if (component instanceof Container) {
                insets = ((Container)component).getInsets();
                dimension = new Dimension(size.width - insets.left - insets.right, size.height - insets.top - insets.bottom);
            }
            else {
                insets = null;
                dimension = size;
            }
            System.err.println("AWT: componentResized: " + size + " ( " + insets + ", " + dimension + " ), " + componentEvent + " -> " + windowEvent);
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, windowEvent)) {
            ((com.jogamp.newt.event.WindowListener)this.newtListener).windowResized(windowEvent);
        }
    }
    
    @Override
    public synchronized void componentMoved(final ComponentEvent componentEvent) {
        if (!this.isSetup) {
            return;
        }
        final WindowEvent windowEvent = AWTNewtEventFactory.createWindowEvent(componentEvent, this.nsHolder);
        if (AWTWindowAdapter.DEBUG_IMPLEMENTATION) {
            System.err.println("AWT: componentMoved: " + componentEvent + " -> " + windowEvent);
        }
        if (EventProcRes.DISPATCH == this.processEvent(false, windowEvent)) {
            ((com.jogamp.newt.event.WindowListener)this.newtListener).windowMoved(windowEvent);
        }
    }
    
    @Override
    public synchronized void componentShown(final ComponentEvent componentEvent) {
        if (!this.isSetup) {
            return;
        }
        final Component component = componentEvent.getComponent();
        if (AWTWindowAdapter.DEBUG_IMPLEMENTATION) {
            System.err.println("AWT: componentShown: " + component);
        }
    }
    
    @Override
    public synchronized void componentHidden(final ComponentEvent componentEvent) {
        if (!this.isSetup) {
            return;
        }
        final Component component = componentEvent.getComponent();
        if (AWTWindowAdapter.DEBUG_IMPLEMENTATION) {
            System.err.println("AWT: componentHidden: " + component);
        }
    }
    
    @Override
    public synchronized void windowActivated(final java.awt.event.WindowEvent windowEvent) {
        if (!this.isSetup) {
            return;
        }
        final WindowEvent windowEvent2 = AWTNewtEventFactory.createWindowEvent(windowEvent, this.nsHolder);
        if (EventProcRes.DISPATCH == this.processEvent(false, windowEvent2)) {
            ((com.jogamp.newt.event.WindowListener)this.newtListener).windowGainedFocus(windowEvent2);
        }
    }
    
    @Override
    public synchronized void windowClosed(final java.awt.event.WindowEvent windowEvent) {
    }
    
    @Override
    public synchronized void windowClosing(final java.awt.event.WindowEvent windowEvent) {
    }
    
    @Override
    public synchronized void windowDeactivated(final java.awt.event.WindowEvent windowEvent) {
        if (!this.isSetup) {
            return;
        }
        final WindowEvent windowEvent2 = AWTNewtEventFactory.createWindowEvent(windowEvent, this.nsHolder);
        if (EventProcRes.DISPATCH == this.processEvent(false, windowEvent2)) {
            ((com.jogamp.newt.event.WindowListener)this.newtListener).windowLostFocus(windowEvent2);
        }
    }
    
    @Override
    public synchronized void windowDeiconified(final java.awt.event.WindowEvent windowEvent) {
    }
    
    @Override
    public synchronized void windowIconified(final java.awt.event.WindowEvent windowEvent) {
    }
    
    @Override
    public synchronized void windowOpened(final java.awt.event.WindowEvent windowEvent) {
    }
    
    class WindowClosingListener implements WindowListener
    {
        @Override
        public void windowClosing(final java.awt.event.WindowEvent windowEvent) {
            synchronized (AWTWindowAdapter.this) {
                if (!AWTWindowAdapter.this.isSetup) {
                    return;
                }
                final WindowEvent windowEvent2 = AWTNewtEventFactory.createWindowEvent(windowEvent, AWTWindowAdapter.this.nsHolder);
                if (EventProcRes.DISPATCH == AWTWindowAdapter.this.processEvent(true, windowEvent2)) {
                    ((com.jogamp.newt.event.WindowListener)AWTWindowAdapter.this.newtListener).windowDestroyNotify(windowEvent2);
                }
            }
        }
        
        @Override
        public void windowClosed(final java.awt.event.WindowEvent windowEvent) {
            synchronized (AWTWindowAdapter.this) {
                if (!AWTWindowAdapter.this.isSetup) {
                    return;
                }
                final WindowEvent windowEvent2 = AWTNewtEventFactory.createWindowEvent(windowEvent, AWTWindowAdapter.this.nsHolder);
                if (EventProcRes.DISPATCH == AWTWindowAdapter.this.processEvent(true, windowEvent2)) {
                    ((com.jogamp.newt.event.WindowListener)AWTWindowAdapter.this.newtListener).windowDestroyed(windowEvent2);
                }
            }
        }
        
        @Override
        public void windowActivated(final java.awt.event.WindowEvent windowEvent) {
        }
        
        @Override
        public void windowDeactivated(final java.awt.event.WindowEvent windowEvent) {
        }
        
        @Override
        public void windowDeiconified(final java.awt.event.WindowEvent windowEvent) {
        }
        
        @Override
        public void windowIconified(final java.awt.event.WindowEvent windowEvent) {
        }
        
        @Override
        public void windowOpened(final java.awt.event.WindowEvent windowEvent) {
        }
    }
}
