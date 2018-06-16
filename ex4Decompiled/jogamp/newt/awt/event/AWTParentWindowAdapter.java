// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.awt.event;

import java.awt.event.HierarchyEvent;
import java.awt.event.WindowEvent;
import jogamp.newt.driver.DriverUpdatePosition;
import java.awt.event.ComponentEvent;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.Component;
import com.jogamp.newt.event.awt.AWTAdapter;
import com.jogamp.newt.Window;
import com.jogamp.nativewindow.NativeWindow;
import java.awt.event.HierarchyListener;
import com.jogamp.newt.event.awt.AWTWindowAdapter;

public class AWTParentWindowAdapter extends AWTWindowAdapter implements HierarchyListener
{
    NativeWindow downstreamParent;
    
    public AWTParentWindowAdapter(final NativeWindow downstreamParent, final Window window) {
        super(window);
        this.downstreamParent = downstreamParent;
    }
    
    public AWTParentWindowAdapter() {
    }
    
    public AWTParentWindowAdapter setDownstream(final NativeWindow downstreamParent, final Window downstream) {
        this.setDownstream(downstream);
        this.downstreamParent = downstreamParent;
        return this;
    }
    
    @Override
    public synchronized AWTAdapter clear() {
        super.clear();
        this.downstreamParent = null;
        return this;
    }
    
    @Override
    public synchronized AWTAdapter addTo(final Component component) {
        component.addHierarchyListener(this);
        return super.addTo(component);
    }
    
    @Override
    public synchronized AWTAdapter removeFrom(final Component component) {
        component.removeHierarchyListener(this);
        return super.removeFrom(component);
    }
    
    @Override
    public synchronized void focusGained(final FocusEvent focusEvent) {
        if (!this.isSetup) {
            return;
        }
        final Window newtWindow = this.getNewtWindow();
        if (null != newtWindow) {
            final boolean b = newtWindow.isNativeValid() && newtWindow.getGraphicsConfiguration().getChosenCapabilities().isOnscreen();
            final boolean b2 = this.downstreamParent == newtWindow.getParent();
            final boolean fullscreen = newtWindow.isFullscreen();
            if (AWTParentWindowAdapter.DEBUG_IMPLEMENTATION) {
                System.err.println("AWT: focusGained: onscreen " + b + ", " + focusEvent + ", isParent: " + b2 + ", isFS " + fullscreen);
            }
            if (b2) {
                if (b && !fullscreen) {
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                }
                newtWindow.requestFocus(false);
            }
        }
    }
    
    @Override
    public synchronized void focusLost(final FocusEvent focusEvent) {
        if (!this.isSetup) {
            return;
        }
        if (AWTParentWindowAdapter.DEBUG_IMPLEMENTATION) {
            System.err.println("AWT: focusLost: " + focusEvent);
        }
    }
    
    @Override
    public synchronized void componentResized(final ComponentEvent componentEvent) {
        if (!this.isSetup) {
            return;
        }
        final Component component = componentEvent.getComponent();
        if (AWTParentWindowAdapter.DEBUG_IMPLEMENTATION) {
            System.err.println("AWT: componentResized: " + component);
        }
        final Window newtWindow = this.getNewtWindow();
        if (null != newtWindow) {
            newtWindow.runOnEDTIfAvail(false, new Runnable() {
                @Override
                public void run() {
                    final int width = component.getWidth();
                    final int height = component.getHeight();
                    if (0 < width && 0 < height) {
                        if (newtWindow.getWidth() != width || newtWindow.getHeight() != height) {
                            newtWindow.setSize(width, height);
                            final boolean showing = component.isShowing();
                            if (showing != newtWindow.isVisible()) {
                                newtWindow.setVisible(showing);
                            }
                        }
                    }
                    else if (newtWindow.isVisible()) {
                        newtWindow.setVisible(false);
                    }
                }
            });
        }
    }
    
    @Override
    public synchronized void componentMoved(final ComponentEvent componentEvent) {
        if (!this.isSetup) {
            return;
        }
        if (AWTParentWindowAdapter.DEBUG_IMPLEMENTATION) {
            System.err.println("AWT: componentMoved: " + componentEvent);
        }
        final Window newtWindow = this.getNewtWindow();
        if (null != newtWindow && newtWindow.getDelegatedWindow() instanceof DriverUpdatePosition) {
            ((DriverUpdatePosition)newtWindow.getDelegatedWindow()).updatePosition(0, 0);
        }
    }
    
    @Override
    public synchronized void windowActivated(final WindowEvent windowEvent) {
    }
    
    @Override
    public synchronized void windowDeactivated(final WindowEvent windowEvent) {
    }
    
    @Override
    public synchronized void hierarchyChanged(final HierarchyEvent hierarchyEvent) {
        if (!this.isSetup) {
            return;
        }
        final Window newtWindow = this.getNewtWindow();
        if (null != newtWindow && null == this.getNewtEventListener()) {
            final long changeFlags = hierarchyEvent.getChangeFlags();
            final Component component = hierarchyEvent.getComponent();
            if (0x0L != (0x4L & changeFlags)) {
                final boolean showing = component.isShowing();
                if (AWTParentWindowAdapter.DEBUG_IMPLEMENTATION) {
                    System.err.println("AWT: hierarchyChanged SHOWING_CHANGED: showing " + showing + ", comp " + component + ", changed " + hierarchyEvent.getChanged());
                }
                newtWindow.runOnEDTIfAvail(false, new Runnable() {
                    @Override
                    public void run() {
                        if (newtWindow.isVisible() != showing) {
                            newtWindow.setVisible(showing);
                        }
                    }
                });
            }
            if (AWTParentWindowAdapter.DEBUG_IMPLEMENTATION && 0x0L != (0x2L & changeFlags)) {
                System.err.println("AWT: hierarchyChanged DISPLAYABILITY_CHANGED: " + hierarchyEvent.getChanged());
            }
        }
    }
}
