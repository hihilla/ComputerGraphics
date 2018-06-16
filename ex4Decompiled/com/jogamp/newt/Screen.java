// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt;

import jogamp.newt.Debug;
import java.util.Collection;
import com.jogamp.newt.event.MonitorModeListener;
import com.jogamp.nativewindow.util.Rectangle;
import java.util.List;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.NativeWindowException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class Screen
{
    public static final int SCREEN_MODE_CHANGE_TIMEOUT = 10000;
    public static final boolean DEBUG;
    protected static final ArrayList<WeakReference<Screen>> screenList;
    protected static int screensActive;
    
    @Override
    public abstract int hashCode();
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof Screen && ((Screen)o).getFQName().equals(this.getFQName()));
    }
    
    public abstract void createNative() throws NativeWindowException;
    
    public abstract void destroy();
    
    public abstract boolean isNativeValid();
    
    public abstract int getReferenceCount();
    
    public abstract int addReference() throws NativeWindowException;
    
    public abstract int removeReference();
    
    public abstract AbstractGraphicsScreen getGraphicsScreen();
    
    public abstract int getIndex();
    
    public abstract int getX();
    
    public abstract int getY();
    
    public abstract int getWidth();
    
    public abstract int getHeight();
    
    public abstract RectangleImmutable getViewport();
    
    public abstract RectangleImmutable getViewportInWindowUnits();
    
    public abstract Display getDisplay();
    
    public abstract String getFQName();
    
    public abstract List<MonitorMode> getMonitorModes();
    
    public abstract List<MonitorDevice> getMonitorDevices();
    
    public abstract MonitorDevice getPrimaryMonitor();
    
    public final MonitorDevice getMainMonitor(final RectangleImmutable rectangleImmutable) {
        MonitorDevice monitorDevice = null;
        float n = Float.MIN_VALUE;
        final List<MonitorDevice> monitorDevices = this.getMonitorDevices();
        for (int size = monitorDevices.size(), i = 0; i < size; ++i) {
            final MonitorDevice monitorDevice2 = monitorDevices.get(i);
            if (!monitorDevice2.isClone()) {
                final float coverage = monitorDevice2.getViewportInWindowUnits().coverage(rectangleImmutable);
                if (coverage > n) {
                    n = coverage;
                    monitorDevice = monitorDevice2;
                }
            }
        }
        if (n > 0.0f && null != monitorDevice) {
            return monitorDevice;
        }
        return monitorDevices.get(0);
    }
    
    public final MonitorDevice getMonitor(final int n) {
        final List<MonitorDevice> monitorDevices = this.getMonitorDevices();
        for (int size = monitorDevices.size(), i = 0; i < size; ++i) {
            final MonitorDevice monitorDevice = monitorDevices.get(i);
            if (monitorDevice.getId() == n) {
                return monitorDevice;
            }
        }
        return null;
    }
    
    public final void unionOfMonitorViewports(final Rectangle rectangle, final Rectangle rectangle2) {
        MonitorDevice.unionOfViewports(rectangle, rectangle2, this.getMonitorDevices());
    }
    
    public abstract void addMonitorModeListener(final MonitorModeListener p0);
    
    public abstract void removeMonitorModeListener(final MonitorModeListener p0);
    
    public static Screen getFirstScreenOf(final Display display, final int n, final int n2) {
        return getScreenOfImpl(display, n, n2, 1);
    }
    
    public static Screen getLastScreenOf(final Display display, final int n, final int n2) {
        return getScreenOfImpl(display, n, n2, -1);
    }
    
    private static Screen getScreenOfImpl(final Display display, final int n, final int n2, final int n3) {
        synchronized (Screen.screenList) {
            int n4 = (n2 >= 0) ? n2 : (Screen.screenList.size() - 1);
            while (true) {
                if (n3 > 0) {
                    if (n4 >= Screen.screenList.size()) {
                        break;
                    }
                }
                else if (n4 < 0) {
                    break;
                }
                final Screen screen = Screen.screenList.get(n4).get();
                if (null == screen) {
                    Screen.screenList.remove(n4);
                    if (n3 >= 0) {
                        continue;
                    }
                    n4 += n3;
                }
                else {
                    if (screen.getDisplay().equals(display) && screen.getIndex() == n) {
                        return screen;
                    }
                    n4 += n3;
                }
            }
        }
        return null;
    }
    
    protected static void addScreen2List(final Screen screen) {
        synchronized (Screen.screenList) {
            int i = 0;
            while (i < Screen.screenList.size()) {
                if (null == Screen.screenList.get(i).get()) {
                    Screen.screenList.remove(i);
                }
                else {
                    ++i;
                }
            }
            Screen.screenList.add(new WeakReference<Screen>(screen));
        }
    }
    
    public static Collection<Screen> getAllScreens() {
        final ArrayList<Object> list;
        synchronized (Screen.screenList) {
            list = (ArrayList<Object>)new ArrayList<Screen>();
            int i = 0;
            while (i < Screen.screenList.size()) {
                if (null == Screen.screenList.get(i).get()) {
                    Screen.screenList.remove(i);
                }
                else {
                    list.add(Screen.screenList.get(i).get());
                    ++i;
                }
            }
        }
        return (Collection<Screen>)list;
    }
    
    public static int getActiveScreenNumber() {
        synchronized (Screen.screenList) {
            return Screen.screensActive;
        }
    }
    
    static {
        DEBUG = Debug.debug("Screen");
        screenList = new ArrayList<WeakReference<Screen>>();
        Screen.screensActive = 0;
    }
}
