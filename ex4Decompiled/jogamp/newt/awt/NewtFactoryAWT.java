// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.awt;

import jogamp.newt.Debug;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.nativewindow.util.Point;
import jogamp.nativewindow.awt.AWTMisc;
import jogamp.nativewindow.jawt.JAWTUtil;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import com.jogamp.newt.Screen;
import java.awt.GraphicsDevice;
import jogamp.nativewindow.x11.X11Lib;
import jogamp.nativewindow.jawt.x11.X11SunJDKReflection;
import com.jogamp.newt.Display;
import java.awt.GraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import java.awt.Component;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.awt.JAWTWindow;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.newt.NewtFactory;

public class NewtFactoryAWT extends NewtFactory
{
    public static final boolean DEBUG_IMPLEMENTATION;
    
    public static JAWTWindow getNativeWindow(final Object o, final CapabilitiesImmutable capabilitiesImmutable) {
        if (null == o) {
            throw new NativeWindowException("Null AWT Component");
        }
        if (!(o instanceof Component)) {
            throw new NativeWindowException("AWT Component not a java.awt.Component");
        }
        return getNativeWindow((Component)o, capabilitiesImmutable);
    }
    
    public static JAWTWindow getNativeWindow(final Component component, final CapabilitiesImmutable capabilitiesImmutable) {
        return getNativeWindow(component, AWTGraphicsConfiguration.create(component, null, capabilitiesImmutable));
    }
    
    public static JAWTWindow getNativeWindow(final Component component, final AWTGraphicsConfiguration awtGraphicsConfiguration) {
        final NativeWindow nativeWindow = NativeWindowFactory.getNativeWindow(component, awtGraphicsConfiguration);
        if (!(nativeWindow instanceof JAWTWindow)) {
            throw new NativeWindowException("Not an AWT NativeWindow: " + nativeWindow);
        }
        if (NewtFactoryAWT.DEBUG_IMPLEMENTATION) {
            System.err.println("NewtFactoryAWT.getNativeWindow: " + component + " -> " + nativeWindow);
        }
        return (JAWTWindow)nativeWindow;
    }
    
    public static void destroyNativeWindow(final JAWTWindow jawtWindow) {
        final AbstractGraphicsConfiguration graphicsConfiguration = jawtWindow.getGraphicsConfiguration();
        jawtWindow.destroy();
        graphicsConfiguration.getScreen().getDevice().close();
    }
    
    private static GraphicsConfiguration checkComponentValid(final Component component) throws IllegalArgumentException {
        if (!component.isDisplayable()) {
            throw new IllegalArgumentException("Given AWT-Component is not displayable: " + component);
        }
        final GraphicsConfiguration graphicsConfiguration = component.getGraphicsConfiguration();
        if (null == graphicsConfiguration) {
            throw new IllegalArgumentException("Given AWT-Component has no GraphicsConfiguration set: " + component);
        }
        return graphicsConfiguration;
    }
    
    public static Display createDisplay(final Component component, final boolean b) throws IllegalArgumentException {
        final GraphicsDevice device = checkComponentValid(component).getDevice();
        String xDisplayString;
        if (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(true)) {
            final long graphicsDeviceGetDisplay = X11SunJDKReflection.graphicsDeviceGetDisplay(device);
            if (0L == graphicsDeviceGetDisplay) {
                xDisplayString = null;
            }
            else {
                xDisplayString = X11Lib.XDisplayString(graphicsDeviceGetDisplay);
            }
        }
        else {
            xDisplayString = null;
        }
        return NewtFactory.createDisplay(xDisplayString, b);
    }
    
    public static Screen createScreen(final Component component, final boolean b) throws IllegalArgumentException {
        return NewtFactory.createScreen(createDisplay(component, b), AWTGraphicsScreen.findScreenIndex(component.getGraphicsConfiguration().getDevice()));
    }
    
    public static MonitorDevice getMonitorDevice(final Screen screen, final Component component) throws IllegalArgumentException {
        final GraphicsConfiguration checkComponentValid = checkComponentValid(component);
        final String nativeWindowType = NativeWindowFactory.getNativeWindowType(true);
        MonitorDevice monitorDevice = null;
        screen.addReference();
        try {
            if (NativeWindowFactory.TYPE_MACOSX == nativeWindowType) {
                monitorDevice = screen.getMonitor(JAWTUtil.getMonitorDisplayID(checkComponentValid.getDevice()));
            }
            if (null == monitorDevice) {
                final Point locationOnScreenSafe = AWTMisc.getLocationOnScreenSafe(null, component, false);
                monitorDevice = screen.getMainMonitor(new Rectangle(locationOnScreenSafe.getX(), locationOnScreenSafe.getY(), component.getWidth(), component.getHeight()));
            }
        }
        finally {
            screen.removeReference();
        }
        return monitorDevice;
    }
    
    static {
        DEBUG_IMPLEMENTATION = Debug.debug("Window");
    }
}
