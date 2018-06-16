// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.x11;

import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.MonitorDevice;
import jogamp.newt.MonitorModeProps;
import java.util.Iterator;
import com.jogamp.common.util.IntLongHashMap;
import com.jogamp.common.util.VersionNumber;

class RandR13 implements RandR
{
    private static final boolean DEBUG;
    private final VersionNumber version;
    long sessionScreenResources;
    IntLongHashMap crtInfoHandleMap;
    
    RandR13(final VersionNumber version) {
        this.sessionScreenResources = 0L;
        this.crtInfoHandleMap = null;
        this.version = version;
    }
    
    @Override
    public final VersionNumber getVersion() {
        return this.version;
    }
    
    @Override
    public String toString() {
        return "RandR13[version " + this.version + "]";
    }
    
    @Override
    public void dumpInfo(final long n, final int n2) {
        final long screenResources0 = getScreenResources0(n, n2);
        if (0L == screenResources0) {
            return;
        }
        try {
            dumpInfo0(n, n2, screenResources0);
        }
        finally {
            freeScreenResources0(screenResources0);
        }
    }
    
    @Override
    public boolean beginInitialQuery(final long n, final ScreenDriver screenDriver) {
        this.sessionScreenResources = getScreenResources0(n, screenDriver.getIndex());
        if (0L != this.sessionScreenResources) {
            (this.crtInfoHandleMap = new IntLongHashMap()).setKeyNotFoundValue(0L);
            return true;
        }
        return false;
    }
    
    @Override
    public void endInitialQuery(final long n, final ScreenDriver screenDriver) {
        if (null != this.crtInfoHandleMap) {
            final Iterator<IntLongHashMap.Entry> iterator = this.crtInfoHandleMap.iterator();
            while (iterator.hasNext()) {
                freeMonitorInfoHandle0(iterator.next().value);
            }
            this.crtInfoHandleMap.clear();
            this.crtInfoHandleMap = null;
        }
        if (0L != this.sessionScreenResources) {
            freeScreenResources0(this.sessionScreenResources);
            this.sessionScreenResources = 0L;
        }
    }
    
    private final long getScreenResourceHandle(final long n, final int n2) {
        if (0L != this.sessionScreenResources) {
            return this.sessionScreenResources;
        }
        return getScreenResources0(n, n2);
    }
    
    private final void releaseScreenResourceHandle(final long n) {
        if (0L == this.sessionScreenResources) {
            freeScreenResources0(n);
        }
    }
    
    private final long getMonitorInfoHandle(final long n, final int n2, final long n3, final int n4) {
        if (null != this.crtInfoHandleMap) {
            long n5 = this.crtInfoHandleMap.get(n4);
            if (0L == n5) {
                n5 = getMonitorInfoHandle0(n, n2, n3, n4);
                this.crtInfoHandleMap.put(n4, n5);
            }
            return n5;
        }
        return getMonitorInfoHandle0(n, n2, n3, n4);
    }
    
    private final void releaseMonitorInfoHandle(final long n) {
        if (null == this.crtInfoHandleMap) {
            freeMonitorInfoHandle0(n);
        }
    }
    
    @Override
    public int[] getMonitorDeviceIds(final long n, final ScreenDriver screenDriver) {
        final long screenResourceHandle = this.getScreenResourceHandle(n, screenDriver.getIndex());
        try {
            return getMonitorDeviceIds0(screenResourceHandle);
        }
        finally {
            this.releaseScreenResourceHandle(screenResourceHandle);
        }
    }
    
    @Override
    public int[] getAvailableRotations(final long n, final ScreenDriver screenDriver, final int n2) {
        final int index = screenDriver.getIndex();
        final long screenResourceHandle = this.getScreenResourceHandle(n, index);
        try {
            final long monitorInfoHandle = this.getMonitorInfoHandle(n, index, screenResourceHandle, n2);
            try {
                final int[] availableRotations0 = getAvailableRotations0(monitorInfoHandle);
                if (null == availableRotations0 || 0 == availableRotations0.length) {
                    return null;
                }
                return availableRotations0;
            }
            finally {
                this.releaseMonitorInfoHandle(monitorInfoHandle);
            }
        }
        finally {
            this.releaseScreenResourceHandle(screenResourceHandle);
        }
    }
    
    @Override
    public int[] getMonitorModeProps(final long n, final ScreenDriver screenDriver, final int n2) {
        final long screenResourceHandle = this.getScreenResourceHandle(n, screenDriver.getIndex());
        try {
            return getMonitorMode0(screenResourceHandle, n2);
        }
        finally {
            this.releaseScreenResourceHandle(screenResourceHandle);
        }
    }
    
    @Override
    public int[] getMonitorDeviceProps(final long n, final ScreenDriver screenDriver, final MonitorModeProps.Cache cache, final int n2) {
        final int index = screenDriver.getIndex();
        final long screenResourceHandle = this.getScreenResourceHandle(n, index);
        try {
            final long monitorInfoHandle = this.getMonitorInfoHandle(n, index, screenResourceHandle, n2);
            try {
                return getMonitorDevice0(n, screenResourceHandle, monitorInfoHandle, n2);
            }
            finally {
                this.releaseMonitorInfoHandle(monitorInfoHandle);
            }
        }
        finally {
            this.releaseScreenResourceHandle(screenResourceHandle);
        }
    }
    
    @Override
    public int[] getMonitorDeviceViewport(final long n, final ScreenDriver screenDriver, final int n2) {
        final int index = screenDriver.getIndex();
        final long screenResourceHandle = this.getScreenResourceHandle(n, index);
        try {
            final long monitorInfoHandle = this.getMonitorInfoHandle(n, index, screenResourceHandle, n2);
            try {
                return getMonitorViewport0(monitorInfoHandle);
            }
            finally {
                this.releaseMonitorInfoHandle(monitorInfoHandle);
            }
        }
        finally {
            this.releaseScreenResourceHandle(screenResourceHandle);
        }
    }
    
    @Override
    public int[] getCurrentMonitorModeProps(final long n, final ScreenDriver screenDriver, final int n2) {
        final int index = screenDriver.getIndex();
        final long screenResourceHandle = this.getScreenResourceHandle(n, index);
        try {
            final long monitorInfoHandle = this.getMonitorInfoHandle(n, index, screenResourceHandle, n2);
            try {
                return getMonitorCurrentMode0(screenResourceHandle, monitorInfoHandle);
            }
            finally {
                this.releaseMonitorInfoHandle(monitorInfoHandle);
            }
        }
        finally {
            this.releaseScreenResourceHandle(screenResourceHandle);
        }
    }
    
    @Override
    public boolean setCurrentMonitorModeStart(final long n, final ScreenDriver screenDriver, final MonitorDevice monitorDevice, final MonitorMode monitorMode) {
        final int index = screenDriver.getIndex();
        final long screenResourceHandle = this.getScreenResourceHandle(n, index);
        boolean setMonitorMode0;
        try {
            final long monitorInfoHandle = this.getMonitorInfoHandle(n, index, screenResourceHandle, monitorDevice.getId());
            try {
                setMonitorMode0 = setMonitorMode0(n, index, screenResourceHandle, monitorInfoHandle, monitorDevice.getId(), monitorMode.getId(), monitorMode.getRotation(), -1, -1);
            }
            finally {
                this.releaseMonitorInfoHandle(monitorInfoHandle);
            }
        }
        finally {
            this.releaseScreenResourceHandle(screenResourceHandle);
        }
        return setMonitorMode0;
    }
    
    @Override
    public void sendRRScreenChangeNotify(final long n, final long n2) {
        sendRRScreenChangeNotify0(n, n2);
    }
    
    @Override
    public boolean setCurrentMonitorModeWait(final ScreenDriver screenDriver) {
        return true;
    }
    
    @Override
    public final void updateScreenViewport(final long n, final ScreenDriver screenDriver, final RectangleImmutable rectangleImmutable) {
        final int index = screenDriver.getIndex();
        final long screenResourceHandle = this.getScreenResourceHandle(n, index);
        try {
            setScreenViewport0(n, index, screenResourceHandle, rectangleImmutable.getX(), rectangleImmutable.getY(), rectangleImmutable.getWidth(), rectangleImmutable.getHeight());
        }
        finally {
            this.releaseScreenResourceHandle(screenResourceHandle);
        }
    }
    
    private static native long getScreenResources0(final long p0, final int p1);
    
    private static native void freeScreenResources0(final long p0);
    
    private static native void dumpInfo0(final long p0, final int p1, final long p2);
    
    private static native int[] getMonitorDeviceIds0(final long p0);
    
    private static native long getMonitorInfoHandle0(final long p0, final int p1, final long p2, final int p3);
    
    private static native void freeMonitorInfoHandle0(final long p0);
    
    private static native int[] getAvailableRotations0(final long p0);
    
    private static native int[] getMonitorViewport0(final long p0);
    
    private static native int[] getMonitorMode0(final long p0, final int p1);
    
    private static native int[] getMonitorCurrentMode0(final long p0, final long p1);
    
    private static native int[] getMonitorDevice0(final long p0, final long p1, final long p2, final int p3);
    
    private static native boolean setMonitorMode0(final long p0, final int p1, final long p2, final long p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    private static native boolean setScreenViewport0(final long p0, final int p1, final long p2, final int p3, final int p4, final int p5, final int p6);
    
    private static native void sendRRScreenChangeNotify0(final long p0, final long p1);
    
    static {
        DEBUG = ScreenDriver.DEBUG;
    }
}
