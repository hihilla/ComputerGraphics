// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.x11;

import com.jogamp.common.util.PropertyAccess;
import jogamp.newt.Debug;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.newt.MonitorMode;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import java.util.Collection;
import java.util.ArrayList;
import com.jogamp.common.util.ArrayHashSet;
import jogamp.newt.MonitorModeProps;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import jogamp.nativewindow.x11.X11Util;
import jogamp.newt.DisplayImpl;
import com.jogamp.common.util.VersionNumber;
import jogamp.newt.ScreenImpl;

public class ScreenDriver extends ScreenImpl
{
    protected static final boolean DEBUG_TEST_RANDR13_DISABLED;
    private VersionNumber randrVersion;
    private RandR rAndR;
    private final DisplayImpl.DisplayRunnable<Boolean> xineramaEnabledQueryWithTemp;
    
    static void initSingleton() {
    }
    
    public ScreenDriver() {
        this.xineramaEnabledQueryWithTemp = new DisplayImpl.DisplayRunnable<Boolean>() {
            @Override
            public Boolean run(final long n) {
                return X11Util.XineramaIsEnabled(n);
            }
        };
    }
    
    @Override
    protected void createNativeImpl() {
        if (this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Long>)new DisplayImpl.DisplayRunnable<Long>() {
            @Override
            public Long run(final long n) {
                return GetScreen0(n, ScreenDriver.this.screen_idx);
            }
        }) == 0L) {
            throw new RuntimeException("Error creating screen: " + this.screen_idx);
        }
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)this.getDisplay().getGraphicsDevice();
        final long handle = x11GraphicsDevice.getHandle();
        this.aScreen = new X11GraphicsScreen(x11GraphicsDevice, this.screen_idx);
        final int[] randRVersion0 = getRandRVersion0(handle);
        this.randrVersion = new VersionNumber(randRVersion0[0], randRVersion0[1], 0);
        if (!ScreenDriver.DEBUG_TEST_RANDR13_DISABLED && this.randrVersion.compareTo(RandR.version130) >= 0) {
            this.rAndR = new RandR13(this.randrVersion);
        }
        else if (this.randrVersion.compareTo(RandR.version110) >= 0) {
            this.rAndR = new RandR11(this.randrVersion);
        }
        else {
            this.rAndR = null;
        }
        ((DisplayDriver)this.display).registerRandR(this.rAndR);
        if (ScreenDriver.DEBUG) {
            System.err.println("Using " + this.rAndR);
            this.rAndR.dumpInfo(handle, this.screen_idx);
        }
    }
    
    @Override
    protected void closeNativeImpl() {
    }
    
    @Override
    protected final void collectNativeMonitorModesAndDevicesImpl(final MonitorModeProps.Cache cache) {
        if (null == this.rAndR) {
            return;
        }
        final AbstractGraphicsDevice graphicsDevice = this.getDisplay().getGraphicsDevice();
        graphicsDevice.lock();
        try {
            if (this.rAndR.beginInitialQuery(graphicsDevice.getHandle(), this)) {
                try {
                    final int[] monitorDeviceIds = this.rAndR.getMonitorDeviceIds(graphicsDevice.getHandle(), this);
                    final int n = (null != monitorDeviceIds) ? monitorDeviceIds.length : 0;
                    final ArrayHashSet<Integer> set = new ArrayHashSet<Integer>(false, 16, 0.75f);
                    for (int i = 0; i < n; ++i) {
                        final int[] availableRotations = this.rAndR.getAvailableRotations(graphicsDevice.getHandle(), this, monitorDeviceIds[i]);
                        if (null != availableRotations) {
                            final ArrayList list = new ArrayList<Integer>(availableRotations.length);
                            for (int j = 0; j < availableRotations.length; ++j) {
                                list.add(availableRotations[j]);
                            }
                            set.addAll((Collection<? extends Integer>)list);
                        }
                    }
                    int n2 = 0;
                    int[] monitorModeProps;
                    do {
                        monitorModeProps = this.rAndR.getMonitorModeProps(graphicsDevice.getHandle(), this, n2++);
                        if (null != monitorModeProps) {
                            for (int k = 0; k < set.size(); ++k) {
                                monitorModeProps[7] = set.get(k);
                                MonitorModeProps.streamInMonitorMode(null, cache, monitorModeProps, 0);
                            }
                        }
                    } while (null != monitorModeProps);
                    if (cache.monitorModes.size() > 0) {
                        for (int l = 0; l < n; ++l) {
                            final int[] monitorDeviceProps = this.rAndR.getMonitorDeviceProps(graphicsDevice.getHandle(), this, cache, monitorDeviceIds[l]);
                            if (null != monitorDeviceProps && 17 <= monitorDeviceProps[0] && 17 <= monitorDeviceProps.length) {
                                MonitorModeProps.streamInMonitorDevice(cache, this, null, monitorDeviceProps, 0, null);
                            }
                        }
                    }
                }
                finally {
                    this.rAndR.endInitialQuery(graphicsDevice.getHandle(), this);
                }
            }
        }
        finally {
            graphicsDevice.unlock();
        }
    }
    
    @Override
    protected boolean updateNativeMonitorDeviceViewportImpl(final MonitorDevice monitorDevice, final float[] array, final Rectangle rectangle, final Rectangle rectangle2) {
        final AbstractGraphicsDevice graphicsDevice = this.getDisplay().getGraphicsDevice();
        graphicsDevice.lock();
        try {
            final int[] monitorDeviceViewport = this.rAndR.getMonitorDeviceViewport(graphicsDevice.getHandle(), this, monitorDevice.getId());
            if (null != monitorDeviceViewport) {
                rectangle.set(monitorDeviceViewport[0], monitorDeviceViewport[1], monitorDeviceViewport[2], monitorDeviceViewport[3]);
                rectangle2.set(monitorDeviceViewport[0], monitorDeviceViewport[1], monitorDeviceViewport[2], monitorDeviceViewport[3]);
                return true;
            }
            return false;
        }
        finally {
            graphicsDevice.unlock();
        }
    }
    
    @Override
    protected MonitorMode queryCurrentMonitorModeImpl(final MonitorDevice monitorDevice) {
        if (null == this.rAndR) {
            return null;
        }
        return this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<MonitorMode>)new DisplayImpl.DisplayRunnable<MonitorMode>() {
            @Override
            public MonitorMode run(final long n) {
                final int[] currentMonitorModeProps = ScreenDriver.this.rAndR.getCurrentMonitorModeProps(n, ScreenDriver.this, monitorDevice.getId());
                if (null != currentMonitorModeProps) {
                    return MonitorModeProps.streamInMonitorMode(null, null, currentMonitorModeProps, 0);
                }
                return null;
            }
        });
    }
    
    @Override
    protected boolean setCurrentMonitorModeImpl(final MonitorDevice monitorDevice, final MonitorMode monitorMode) {
        if (null == this.rAndR) {
            return false;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final boolean booleanValue = this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Boolean>)new DisplayImpl.DisplayRunnable<Boolean>() {
            @Override
            public Boolean run(final long n) {
                return ScreenDriver.this.rAndR.setCurrentMonitorModeStart(n, ScreenDriver.this, monitorDevice, monitorMode);
            }
        });
        final boolean b = booleanValue && this.rAndR.setCurrentMonitorModeWait(this);
        if (ScreenDriver.DEBUG || !b) {
            System.err.println("X11Screen.setCurrentMonitorModeImpl: " + (b ? " OK" : "NOK") + " (started " + booleanValue + "): t/TO " + (System.currentTimeMillis() - currentTimeMillis) + "/" + 10000 + "ms; " + monitorDevice.getCurrentMode() + " -> " + monitorMode);
        }
        return b;
    }
    
    @Override
    protected int validateScreenIndex(final int n) {
        final Boolean xineramaEnabled = ((DisplayDriver)this.getDisplay()).isXineramaEnabled();
        if (null != xineramaEnabled) {
            return xineramaEnabled ? 0 : n;
        }
        return this.runWithTempDisplayHandle(this.xineramaEnabledQueryWithTemp) ? 0 : n;
    }
    
    @Override
    protected void calcVirtualScreenOriginAndSize(final Rectangle rectangle, final Rectangle rectangle2) {
        final RectangleImmutable rectangleImmutable = ScreenDriver.DEBUG ? ((RectangleImmutable)this.getViewport().cloneMutable()) : null;
        this.runWithLockedDisplayDevice((DisplayImpl.DisplayRunnable<Object>)new DisplayImpl.DisplayRunnable<Object>() {
            @Override
            public Object run(final long n) {
                rectangle.set(0, 0, getWidth0(n, ScreenDriver.this.screen_idx), getHeight0(n, ScreenDriver.this.screen_idx));
                rectangle2.set(rectangle);
                return null;
            }
        });
        if (ScreenDriver.DEBUG) {
            System.err.println("X11Screen.calcVirtualScreenOriginAndSize: Querying X11: " + rectangleImmutable + " -> " + rectangle);
        }
    }
    
    private final <T> T runWithLockedDisplayDevice(final DisplayImpl.DisplayRunnable<T> displayRunnable) {
        return this.display.runWithLockedDisplayDevice(displayRunnable);
    }
    
    private final <T> T runWithTempDisplayHandle(final DisplayImpl.DisplayRunnable<T> displayRunnable) {
        final long openDisplay = X11Util.openDisplay(this.display.getName());
        if (0L == openDisplay) {
            throw new RuntimeException("null device");
        }
        T run;
        try {
            run = displayRunnable.run(openDisplay);
        }
        finally {
            X11Util.closeDisplay(openDisplay);
        }
        return run;
    }
    
    private static native long GetScreen0(final long p0, final int p1);
    
    private static native int getWidth0(final long p0, final int p1);
    
    private static native int getHeight0(final long p0, final int p1);
    
    private static native int[] getRandRVersion0(final long p0);
    
    static {
        Debug.initSingleton();
        DEBUG_TEST_RANDR13_DISABLED = PropertyAccess.isPropertyDefined("newt.test.Screen.disableRandR13", true);
        DisplayDriver.initSingleton();
    }
}
