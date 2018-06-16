// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.x11;

import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import jogamp.nativewindow.x11.X11Util;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.nativewindow.Capabilities;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.NewtFactory;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.Display;
import com.jogamp.common.util.ArrayHashMap;
import com.jogamp.newt.event.MouseEvent;
import jogamp.newt.WindowImpl;
import jogamp.newt.driver.KeyTracker;
import jogamp.newt.driver.MouseTracker;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowListener;

public class X11UnderlayTracker implements WindowListener, KeyListener, MouseListener, MouseTracker, KeyTracker
{
    private static final X11UnderlayTracker tracker;
    private volatile WindowImpl focusedWindow;
    private volatile MouseEvent lastMouse;
    private static volatile ArrayHashMap<WindowImpl, WindowImpl> underlayWindowMap;
    private static volatile ArrayHashMap<WindowImpl, WindowImpl> overlayWindowMap;
    private final Display display;
    private final Screen screen;
    
    public static X11UnderlayTracker getSingleton() {
        return X11UnderlayTracker.tracker;
    }
    
    private X11UnderlayTracker() {
        this.focusedWindow = null;
        this.display = NewtFactory.createDisplay(NativeWindowFactory.TYPE_X11, null, false);
        this.screen = NewtFactory.createScreen(this.display, 0);
    }
    
    @Override
    public void windowResized(final WindowEvent windowEvent) {
        final Object source = windowEvent.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            final WindowImpl windowImpl = (WindowImpl)source;
            final WindowImpl windowImpl2 = X11UnderlayTracker.underlayWindowMap.get(source);
            if (windowImpl2.getSurfaceWidth() != windowImpl.getSurfaceWidth() || windowImpl2.getSurfaceHeight() != windowImpl.getSurfaceHeight()) {
                windowImpl2.setSize(windowImpl.getSurfaceWidth(), windowImpl.getSurfaceHeight());
            }
        }
        else if (X11UnderlayTracker.overlayWindowMap.containsKey(source)) {
            final WindowImpl windowImpl3 = (WindowImpl)source;
            final WindowImpl windowImpl4 = X11UnderlayTracker.overlayWindowMap.get(source);
            if (windowImpl3.getSurfaceWidth() != windowImpl4.getSurfaceWidth() || windowImpl3.getSurfaceHeight() != windowImpl4.getSurfaceHeight()) {
                windowImpl4.setSize(windowImpl3.getSurfaceWidth(), windowImpl3.getSurfaceHeight());
            }
        }
    }
    
    @Override
    public void windowMoved(final WindowEvent windowEvent) {
        final Object source = windowEvent.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            final WindowImpl windowImpl = (WindowImpl)source;
            final WindowImpl windowImpl2 = X11UnderlayTracker.underlayWindowMap.get(source);
            final Point point = new Point();
            final Point point2 = new Point();
            windowImpl2.getLocationOnScreen(point);
            windowImpl.getLocationOnScreen(point2);
            if (point.getX() != point2.getX() || point.getY() != point2.getY()) {
                windowImpl2.setPosition(point2.getX(), point2.getY());
            }
        }
        else if (X11UnderlayTracker.overlayWindowMap.containsKey(source)) {
            final WindowImpl windowImpl3 = (WindowImpl)source;
            final WindowImpl windowImpl4 = X11UnderlayTracker.overlayWindowMap.get(source);
        }
    }
    
    @Override
    public void windowDestroyNotify(final WindowEvent windowEvent) {
        final Object source = windowEvent.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            final WindowImpl windowImpl = (WindowImpl)source;
            final WindowImpl windowImpl2 = X11UnderlayTracker.underlayWindowMap.get(source);
            X11UnderlayTracker.overlayWindowMap.remove(windowImpl2);
            X11UnderlayTracker.underlayWindowMap.remove(windowImpl);
            if (this.focusedWindow == windowImpl2) {
                this.focusedWindow = null;
            }
            windowImpl2.destroy();
        }
        else if (X11UnderlayTracker.overlayWindowMap.containsKey(source)) {
            final WindowImpl windowImpl3 = (WindowImpl)source;
            final WindowImpl windowImpl4 = X11UnderlayTracker.overlayWindowMap.get(source);
            X11UnderlayTracker.overlayWindowMap.remove(windowImpl3);
            X11UnderlayTracker.underlayWindowMap.remove(windowImpl4);
            if (this.focusedWindow == windowImpl3) {
                this.focusedWindow = null;
            }
            windowImpl4.destroy();
        }
    }
    
    @Override
    public void windowDestroyed(final WindowEvent windowEvent) {
    }
    
    @Override
    public void windowGainedFocus(final WindowEvent windowEvent) {
        final Object source = windowEvent.getSource();
        if (source instanceof WindowImpl) {
            if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
                this.focusedWindow = X11UnderlayTracker.underlayWindowMap.get(source);
            }
            else if (X11UnderlayTracker.overlayWindowMap.containsKey(source)) {
                this.focusedWindow = (WindowImpl)source;
            }
            else {
                final WindowImpl windowImpl = (WindowImpl)source;
                final Capabilities capabilities = new Capabilities();
                capabilities.setBackgroundOpaque(false);
                final WindowImpl create = WindowImpl.create(null, 0L, this.screen, capabilities);
                X11UnderlayTracker.underlayWindowMap.put(create, windowImpl);
                X11UnderlayTracker.overlayWindowMap.put(windowImpl, create);
                create.setAlwaysOnTop(true);
                create.setTitle(windowImpl.getTitle());
                if (windowImpl.isUndecorated()) {
                    create.setUndecorated(true);
                }
                create.addKeyListener(this);
                create.addMouseListener(this);
                create.addWindowListener(this);
                create.setSize(windowImpl.getSurfaceWidth(), windowImpl.getSurfaceHeight());
                create.setPosition(windowImpl.getX(), windowImpl.getY());
                create.setVisible(false, true);
                this.focusedWindow = (WindowImpl)source;
            }
        }
    }
    
    @Override
    public void windowLostFocus(final WindowEvent windowEvent) {
        final Object source = windowEvent.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            if (this.focusedWindow == X11UnderlayTracker.underlayWindowMap.get(source)) {
                this.focusedWindow = null;
            }
        }
        else if (this.focusedWindow == source) {
            this.focusedWindow = null;
        }
    }
    
    @Override
    public void windowRepaint(final WindowUpdateEvent windowUpdateEvent) {
    }
    
    public static void main(final String[] array) throws InterruptedException {
        final Capabilities capabilities = new Capabilities();
        capabilities.setBackgroundOpaque(false);
        final Window window = NewtFactory.createWindow(capabilities);
        window.setUndecorated(true);
        window.addWindowListener(getSingleton());
        window.setTitle("1");
        window.setVisible(true);
        final Window window2 = NewtFactory.createWindow(capabilities);
        window2.setUndecorated(false);
        window2.addWindowListener(getSingleton());
        window2.setTitle("2");
        window2.setVisible(true);
        Thread.sleep(25000L);
    }
    
    @Override
    public void mouseClicked(final MouseEvent lastMouse) {
        this.lastMouse = lastMouse;
        final Object source = lastMouse.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            X11UnderlayTracker.underlayWindowMap.get(source).sendMouseEvent((short)200, 0, lastMouse.getX(), lastMouse.getY(), (short)0, 0.0f);
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent lastMouse) {
        this.lastMouse = lastMouse;
        final Object source = lastMouse.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            X11UnderlayTracker.underlayWindowMap.get(source).sendMouseEvent((short)201, 0, lastMouse.getX(), lastMouse.getY(), (short)0, 0.0f);
        }
    }
    
    @Override
    public void mouseExited(final MouseEvent lastMouse) {
        this.lastMouse = lastMouse;
        final Object source = lastMouse.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            X11UnderlayTracker.underlayWindowMap.get(source).sendMouseEvent((short)202, 0, lastMouse.getX(), lastMouse.getY(), (short)0, 0.0f);
        }
    }
    
    @Override
    public void mousePressed(final MouseEvent lastMouse) {
        this.lastMouse = lastMouse;
        final Object source = lastMouse.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            X11UnderlayTracker.underlayWindowMap.get(source).sendMouseEvent((short)203, 0, lastMouse.getX(), lastMouse.getY(), (short)0, 0.0f);
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent lastMouse) {
        this.lastMouse = lastMouse;
        final Object source = lastMouse.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            X11UnderlayTracker.underlayWindowMap.get(source).sendMouseEvent((short)204, 0, lastMouse.getX(), lastMouse.getY(), (short)0, 0.0f);
        }
    }
    
    @Override
    public void mouseMoved(final MouseEvent lastMouse) {
        this.lastMouse = lastMouse;
        final Object source = lastMouse.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            X11UnderlayTracker.underlayWindowMap.get(source).sendMouseEvent((short)205, 0, lastMouse.getX(), lastMouse.getY(), (short)0, 0.0f);
        }
    }
    
    @Override
    public void mouseDragged(final MouseEvent lastMouse) {
        this.lastMouse = lastMouse;
        final Object source = lastMouse.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            X11UnderlayTracker.underlayWindowMap.get(source).sendMouseEvent((short)206, 0, lastMouse.getX(), lastMouse.getY(), (short)0, 0.0f);
        }
    }
    
    @Override
    public void mouseWheelMoved(final MouseEvent lastMouse) {
        this.lastMouse = lastMouse;
        final Object source = lastMouse.getSource();
        if (X11UnderlayTracker.underlayWindowMap.containsKey(source)) {
            X11UnderlayTracker.underlayWindowMap.get(source).sendMouseEvent((short)207, 0, lastMouse.getX(), lastMouse.getY(), (short)0, 0.0f);
        }
    }
    
    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        if (this.focusedWindow != null) {
            this.focusedWindow.sendKeyEvent(keyEvent.getEventType(), keyEvent.getModifiers(), keyEvent.getKeyCode(), keyEvent.getKeySymbol(), keyEvent.getKeyChar());
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent keyEvent) {
        if (this.focusedWindow != null) {
            this.focusedWindow.sendKeyEvent(keyEvent.getEventType(), keyEvent.getModifiers(), keyEvent.getKeyCode(), keyEvent.getKeySymbol(), keyEvent.getKeyChar());
        }
    }
    
    @Override
    public int getLastY() {
        if (this.lastMouse != null) {
            return this.lastMouse.getY();
        }
        return 0;
    }
    
    @Override
    public int getLastX() {
        if (this.lastMouse != null) {
            return this.lastMouse.getX();
        }
        return 0;
    }
    
    static {
        X11UnderlayTracker.underlayWindowMap = new ArrayHashMap<WindowImpl, WindowImpl>(false, 16, 0.75f);
        X11UnderlayTracker.overlayWindowMap = new ArrayHashMap<WindowImpl, WindowImpl>(false, 16, 0.75f);
        X11Util.initSingleton();
        GraphicsConfigurationFactory.initSingleton();
        try {
            ReflectionUtil.callStaticMethod("jogamp.nativewindow.x11.X11GraphicsConfigurationFactory", "registerFactory", null, null, GraphicsConfigurationFactory.class.getClassLoader());
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        tracker = new X11UnderlayTracker();
    }
}
