// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt;

import java.security.AccessController;
import java.util.Arrays;
import com.jogamp.common.util.PropertyAccess;
import java.security.PrivilegedAction;
import jogamp.newt.Debug;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindow;
import jogamp.newt.WindowImpl;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import jogamp.newt.ScreenImpl;
import jogamp.newt.DisplayImpl;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.common.util.IOUtil;

public class NewtFactory
{
    public static final boolean DEBUG_IMPLEMENTATION;
    public static final String DRIVER_DEFAULT_ROOT_PACKAGE = "jogamp.newt.driver";
    private static IOUtil.ClassResources defaultWindowIcons;
    private static String sysPaths;
    private static boolean useEDT;
    
    public static IOUtil.ClassResources getWindowIcons() {
        return NewtFactory.defaultWindowIcons;
    }
    
    public static void setWindowIcons(final IOUtil.ClassResources defaultWindowIcons) {
        NewtFactory.defaultWindowIcons = defaultWindowIcons;
    }
    
    public static Class<?> getCustomClass(final String s, final String s2) {
        Class<?> forName = null;
        if (s != null && s2 != null) {
            String s3;
            if (s.startsWith(".")) {
                s3 = "jogamp.newt.driver" + s + "." + s2;
            }
            else {
                s3 = s + "." + s2;
            }
            try {
                forName = Class.forName(s3);
            }
            catch (Throwable t) {
                if (NewtFactory.DEBUG_IMPLEMENTATION) {
                    System.err.println("Warning: Failed to find class <" + s3 + ">: " + t.getMessage());
                    t.printStackTrace();
                }
            }
        }
        return forName;
    }
    
    public static synchronized void setUseEDT(final boolean useEDT) {
        NewtFactory.useEDT = useEDT;
    }
    
    public static boolean useEDT() {
        return NewtFactory.useEDT;
    }
    
    public static Display createDisplay(final String s) {
        return createDisplay(s, true);
    }
    
    public static Display createDisplay(final String s, final boolean b) {
        return DisplayImpl.create(NativeWindowFactory.getNativeWindowType(true), s, 0L, b);
    }
    
    public static Display createDisplay(final String s, final String s2) {
        return createDisplay(s, s2, true);
    }
    
    public static Display createDisplay(final String s, final String s2, final boolean b) {
        return DisplayImpl.create(s, s2, 0L, b);
    }
    
    public static Screen createScreen(final Display display, final int n) {
        return ScreenImpl.create(display, n);
    }
    
    public static Window createWindow(final CapabilitiesImmutable capabilitiesImmutable) {
        return createWindowImpl(NativeWindowFactory.getNativeWindowType(true), capabilitiesImmutable);
    }
    
    public static Window createWindow(final Screen screen, final CapabilitiesImmutable capabilitiesImmutable) {
        return WindowImpl.create(null, 0L, screen, capabilitiesImmutable);
    }
    
    public static Window createWindow(final NativeWindow nativeWindow, final CapabilitiesImmutable capabilitiesImmutable) {
        final String nativeWindowType = NativeWindowFactory.getNativeWindowType(true);
        if (null == nativeWindow) {
            return createWindowImpl(nativeWindowType, capabilitiesImmutable);
        }
        Window window = null;
        Screen screen;
        if (nativeWindow instanceof Window) {
            window = (Window)nativeWindow;
            screen = window.getScreen();
        }
        else {
            final AbstractGraphicsConfiguration graphicsConfiguration = nativeWindow.getGraphicsConfiguration();
            if (null != graphicsConfiguration) {
                final AbstractGraphicsScreen screen2 = graphicsConfiguration.getScreen();
                screen = createScreen(createDisplay(nativeWindowType, screen2.getDevice().getHandle(), true), screen2.getIndex());
            }
            else {
                screen = createScreen(createDisplay(nativeWindowType, null, true), 0);
            }
        }
        final WindowImpl create = WindowImpl.create(nativeWindow, 0L, screen, capabilitiesImmutable);
        create.setSize(nativeWindow.getWidth(), nativeWindow.getHeight());
        if (null != window) {
            window.addChild(create);
            create.setVisible(window.isVisible());
        }
        return create;
    }
    
    private static Window createWindowImpl(final String s, final CapabilitiesImmutable capabilitiesImmutable) {
        return WindowImpl.create(null, 0L, createScreen(createDisplay(s, null, true), 0), capabilitiesImmutable);
    }
    
    public static Window createWindow(final String s, final int n, final long n2, final CapabilitiesImmutable capabilitiesImmutable) {
        return WindowImpl.create(null, n2, createScreen(createDisplay(NativeWindowFactory.getNativeWindowType(true), s, true), n), capabilitiesImmutable);
    }
    
    public static Window createWindow(final Object[] array, final Screen screen, final CapabilitiesImmutable capabilitiesImmutable) {
        return WindowImpl.create(array, screen, capabilitiesImmutable);
    }
    
    public static Display createDisplay(final String s, final long n, final boolean b) {
        return DisplayImpl.create(s, null, n, b);
    }
    
    public static boolean isScreenCompatible(final NativeWindow nativeWindow, final Screen screen) {
        final AbstractGraphicsScreen screen2 = nativeWindow.getGraphicsConfiguration().getScreen();
        final AbstractGraphicsDevice device = screen2.getDevice();
        final DisplayImpl displayImpl = (DisplayImpl)screen.getDisplay();
        return displayImpl.validateDisplayName(null, device.getHandle()).equals(displayImpl.getName()) && screen2.getIndex() == screen.getIndex();
    }
    
    public static Screen createCompatibleScreen(final NativeWindow nativeWindow) {
        return createCompatibleScreen(nativeWindow, null);
    }
    
    public static Screen createCompatibleScreen(final NativeWindow nativeWindow, final Screen screen) {
        final AbstractGraphicsScreen screen2 = nativeWindow.getGraphicsConfiguration().getScreen();
        final AbstractGraphicsDevice device = screen2.getDevice();
        if (null != screen) {
            final DisplayImpl displayImpl = (DisplayImpl)screen.getDisplay();
            final String validateDisplayName = displayImpl.validateDisplayName(null, device.getHandle());
            final String name = displayImpl.getName();
            final boolean equals = validateDisplayName.equals(name);
            final boolean b = screen2.getIndex() == screen.getIndex();
            if (NewtFactory.DEBUG_IMPLEMENTATION) {
                System.err.println("NewtFactory.createCompatibleScreen: Display: " + validateDisplayName + " =? " + name + " : " + equals + "; Screen: " + screen2.getIndex() + " =? " + screen.getIndex() + " : " + b);
            }
            if (equals && b) {
                return screen;
            }
        }
        return createScreen(createDisplay(NativeWindowFactory.getNativeWindowType(true), device.getHandle(), true), screen2.getIndex());
    }
    
    static {
        DEBUG_IMPLEMENTATION = Debug.debug("Window");
        NewtFactory.sysPaths = "newt/data/jogamp-16x16.png newt/data/jogamp-32x32.png";
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                NativeWindowFactory.initSingleton();
                final String[] split = PropertyAccess.getProperty("newt.window.icons", true, NewtFactory.sysPaths).split("[\\s,]");
                if (split.length < 2) {
                    throw new IllegalArgumentException("Property 'newt.window.icons' did not specify at least two PNG icons, but " + Arrays.toString(split));
                }
                NewtFactory.defaultWindowIcons = new IOUtil.ClassResources(split, NewtFactory.class.getClassLoader(), null);
                return null;
            }
        });
        NewtFactory.useEDT = true;
    }
}
