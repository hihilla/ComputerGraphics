// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.x11;

import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import jogamp.newt.MonitorModeProps;
import com.jogamp.common.util.VersionNumber;

class RandR11 implements RandR
{
    private static final boolean DEBUG;
    private static final int SINGLE_CRT_ID = 1;
    private final VersionNumber version;
    private int widthMM;
    private int heightMM;
    private int modeCount;
    private int resolutionCount;
    private int[][] nrates;
    private int[] idx_rate;
    private int[] idx_res;
    volatile int set_screen_idx;
    volatile int set_mode_idx;
    volatile int set_freq;
    volatile int set_rot;
    volatile boolean set_done;
    
    RandR11(final VersionNumber version) {
        this.widthMM = 0;
        this.heightMM = 0;
        this.modeCount = 0;
        this.resolutionCount = 0;
        this.nrates = null;
        this.idx_rate = null;
        this.idx_res = null;
        this.version = version;
    }
    
    @Override
    public final VersionNumber getVersion() {
        return this.version;
    }
    
    @Override
    public String toString() {
        return "RandR11[version " + this.version + "]";
    }
    
    @Override
    public void dumpInfo(final long n, final int n2) {
    }
    
    @Override
    public boolean beginInitialQuery(final long n, final ScreenDriver screenDriver) {
        final int index = screenDriver.getIndex();
        this.resolutionCount = getNumScreenResolutions0(n, index);
        if (0 == this.resolutionCount) {
            this.endInitialQuery(n, screenDriver);
            return false;
        }
        this.nrates = new int[this.resolutionCount][];
        for (int i = 0; i < this.resolutionCount; ++i) {
            this.nrates[i] = getScreenRates0(n, index, i);
            if (null == this.nrates[i] || 0 == this.nrates[i].length) {
                this.endInitialQuery(n, screenDriver);
                return false;
            }
        }
        for (int j = 0; j < this.resolutionCount; ++j) {
            this.modeCount += this.nrates[j].length;
        }
        this.idx_rate = new int[this.modeCount];
        this.idx_res = new int[this.modeCount];
        int n2 = 0;
        for (int k = 0; k < this.resolutionCount; ++k) {
            for (int l = 0; l < this.nrates[k].length; ++l) {
                this.idx_rate[n2] = l;
                this.idx_res[n2] = k;
                ++n2;
            }
        }
        return true;
    }
    
    @Override
    public void endInitialQuery(final long n, final ScreenDriver screenDriver) {
        this.idx_rate = null;
        this.idx_res = null;
        this.nrates = null;
    }
    
    @Override
    public int[] getMonitorDeviceIds(final long n, final ScreenDriver screenDriver) {
        return new int[] { 1 };
    }
    
    @Override
    public int[] getAvailableRotations(final long n, final ScreenDriver screenDriver, final int n2) {
        if (1 != n2) {
            return null;
        }
        final int[] availableScreenRotations0 = getAvailableScreenRotations0(n, screenDriver.getIndex());
        if (null == availableScreenRotations0 || 0 == availableScreenRotations0.length) {
            return null;
        }
        return availableScreenRotations0;
    }
    
    @Override
    public int[] getMonitorModeProps(final long n, final ScreenDriver screenDriver, final int n2) {
        if (n2 >= this.modeCount) {
            return null;
        }
        final int index = screenDriver.getIndex();
        final int n3 = this.idx_res[n2];
        final int n4 = this.idx_rate[n2];
        final int[] screenResolution0 = getScreenResolution0(n, index, n3);
        if (null == screenResolution0 || 0 == screenResolution0.length) {
            return null;
        }
        if (0 >= screenResolution0[0] || 0 >= screenResolution0[1]) {
            throw new InternalError("invalid resolution: " + screenResolution0[0] + "x" + screenResolution0[1] + " for res idx " + n3 + "/" + this.resolutionCount);
        }
        if (screenResolution0[2] > this.widthMM) {
            this.widthMM = screenResolution0[2];
        }
        if (screenResolution0[3] > this.heightMM) {
            this.heightMM = screenResolution0[3];
        }
        int n5 = this.nrates[n3][n4];
        if (0 >= n5) {
            n5 = 60;
            if (RandR11.DEBUG) {
                System.err.println("Invalid rate: " + n5 + " at index " + n4 + "/" + this.nrates.length + ", using default: " + 60);
            }
        }
        final int[] array = new int[8];
        int n6 = 0;
        array[n6++] = 8;
        array[n6++] = screenResolution0[0];
        array[n6++] = screenResolution0[1];
        array[n6++] = 32;
        array[n6++] = n5 * 100;
        array[n6++] = 0;
        array[n6++] = n3;
        array[n6++] = -1;
        if (8 != n6) {
            throw new InternalError("XX");
        }
        return array;
    }
    
    @Override
    public int[] getMonitorDeviceProps(final long n, final ScreenDriver screenDriver, final MonitorModeProps.Cache cache, final int n2) {
        if (1 != n2) {
            return null;
        }
        final int[] currentMonitorModeProps = this.getCurrentMonitorModeProps(n, screenDriver, n2);
        if (null == currentMonitorModeProps) {
            return null;
        }
        final MonitorMode streamInMonitorMode = MonitorModeProps.streamInMonitorMode(null, cache, currentMonitorModeProps, 0);
        final int size = cache.monitorModes.size();
        final int[] array = new int[16 + size];
        int n3 = 0;
        array[n3++] = array.length;
        array[n3++] = 1;
        array[n3++] = 0;
        array[n3++] = ((0 == n2) ? 1 : 0);
        array[n3++] = this.widthMM;
        array[n3++] = this.heightMM;
        array[n3++] = 0;
        array[n3++] = 0;
        array[n3++] = streamInMonitorMode.getRotatedWidth();
        array[n3++] = streamInMonitorMode.getRotatedHeight();
        array[n3++] = 0;
        array[n3++] = 0;
        array[n3++] = streamInMonitorMode.getRotatedWidth();
        array[n3++] = streamInMonitorMode.getRotatedHeight();
        array[n3++] = streamInMonitorMode.getId();
        array[n3++] = streamInMonitorMode.getRotation();
        for (int i = 0; i < size; ++i) {
            array[n3++] = cache.monitorModes.get(i).getId();
        }
        return array;
    }
    
    @Override
    public int[] getMonitorDeviceViewport(final long n, final ScreenDriver screenDriver, final int n2) {
        if (1 != n2) {
            return null;
        }
        final int index = screenDriver.getIndex();
        final long screenConfiguration0 = getScreenConfiguration0(n, index);
        if (0L == screenConfiguration0) {
            return null;
        }
        int[] screenResolution0;
        try {
            final int numScreenResolutions0 = getNumScreenResolutions0(n, index);
            if (numScreenResolutions0 == 0) {
                return null;
            }
            final int currentScreenResolutionIndex0 = getCurrentScreenResolutionIndex0(screenConfiguration0);
            if (0 > currentScreenResolutionIndex0) {
                return null;
            }
            if (currentScreenResolutionIndex0 >= numScreenResolutions0) {
                throw new RuntimeException("Invalid resolution index: ! " + currentScreenResolutionIndex0 + " < " + numScreenResolutions0);
            }
            screenResolution0 = getScreenResolution0(n, index, currentScreenResolutionIndex0);
            if (null == screenResolution0 || 0 == screenResolution0.length) {
                return null;
            }
            if (0 >= screenResolution0[0] || 0 >= screenResolution0[1]) {
                throw new InternalError("invalid resolution: " + screenResolution0[0] + "x" + screenResolution0[1] + " for res idx " + currentScreenResolutionIndex0 + "/" + numScreenResolutions0);
            }
        }
        finally {
            freeScreenConfiguration0(screenConfiguration0);
        }
        final int[] array = new int[4];
        int n3 = 0;
        array[n3++] = 0;
        array[n3++] = 0;
        array[n3++] = screenResolution0[0];
        array[n3++] = screenResolution0[1];
        return array;
    }
    
    @Override
    public int[] getCurrentMonitorModeProps(final long n, final ScreenDriver screenDriver, final int n2) {
        if (1 != n2) {
            return null;
        }
        final int index = screenDriver.getIndex();
        final long screenConfiguration0 = getScreenConfiguration0(n, index);
        if (0L == screenConfiguration0) {
            return null;
        }
        int currentScreenResolutionIndex0;
        int[] screenResolution0;
        int currentScreenRate0;
        int currentScreenRotation0;
        try {
            final int numScreenResolutions0 = getNumScreenResolutions0(n, index);
            if (numScreenResolutions0 == 0) {
                return null;
            }
            currentScreenResolutionIndex0 = getCurrentScreenResolutionIndex0(screenConfiguration0);
            if (0 > currentScreenResolutionIndex0) {
                return null;
            }
            if (currentScreenResolutionIndex0 >= numScreenResolutions0) {
                throw new RuntimeException("Invalid resolution index: ! " + currentScreenResolutionIndex0 + " < " + numScreenResolutions0);
            }
            screenResolution0 = getScreenResolution0(n, index, currentScreenResolutionIndex0);
            if (null == screenResolution0 || 0 == screenResolution0.length) {
                return null;
            }
            if (0 >= screenResolution0[0] || 0 >= screenResolution0[1]) {
                throw new InternalError("invalid resolution: " + screenResolution0[0] + "x" + screenResolution0[1] + " for res idx " + currentScreenResolutionIndex0 + "/" + numScreenResolutions0);
            }
            currentScreenRate0 = getCurrentScreenRate0(screenConfiguration0);
            if (0 > currentScreenRate0) {
                return null;
            }
            currentScreenRotation0 = getCurrentScreenRotation0(screenConfiguration0);
            if (0 > currentScreenRotation0) {
                return null;
            }
        }
        finally {
            freeScreenConfiguration0(screenConfiguration0);
        }
        final int[] array = new int[8];
        int n3 = 0;
        array[n3++] = 8;
        array[n3++] = screenResolution0[0];
        array[n3++] = screenResolution0[1];
        array[n3++] = 32;
        array[n3++] = currentScreenRate0 * 100;
        array[n3++] = 0;
        array[n3++] = currentScreenResolutionIndex0;
        array[n3++] = currentScreenRotation0;
        if (8 != n3) {
            throw new InternalError("XX");
        }
        return array;
    }
    
    @Override
    public boolean setCurrentMonitorModeStart(final long n, final ScreenDriver screenDriver, final MonitorDevice monitorDevice, final MonitorMode monitorMode) {
        final int index = screenDriver.getIndex();
        final long screenConfiguration0 = getScreenConfiguration0(n, index);
        if (0L == screenConfiguration0) {
            return false;
        }
        boolean setCurrentScreenModeStart0 = false;
        try {
            final int id = monitorMode.getId();
            if (0 > id || id >= this.resolutionCount) {
                throw new RuntimeException("Invalid resolution index: ! 0 < " + id + " < " + this.resolutionCount + ", " + monitorDevice + ", " + monitorMode);
            }
            final int set_freq = (int)monitorMode.getRefreshRate();
            final int rotation = monitorMode.getRotation();
            this.set_screen_idx = index;
            this.set_mode_idx = id;
            this.set_freq = set_freq;
            this.set_rot = rotation;
            this.set_done = false;
            setCurrentScreenModeStart0 = setCurrentScreenModeStart0(n, index, screenConfiguration0, id, set_freq, rotation);
            if (!setCurrentScreenModeStart0) {
                this.clearSetModeState();
            }
        }
        finally {
            freeScreenConfiguration0(screenConfiguration0);
        }
        return setCurrentScreenModeStart0;
    }
    
    @Override
    public boolean setCurrentMonitorModeWait(final ScreenDriver screenDriver) {
        final long currentTimeMillis = System.currentTimeMillis();
        boolean set_done = false;
        while (!set_done && System.currentTimeMillis() - currentTimeMillis < 10000L) {
            set_done = this.set_done;
            if (!set_done) {
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException ex) {}
            }
        }
        this.clearSetModeState();
        return set_done;
    }
    
    @Override
    public void sendRRScreenChangeNotify(final long n, final long n2) {
        this.set_done = sendRRScreenChangeNotify0(n, this.set_screen_idx, n2, this.set_mode_idx, this.set_freq, this.set_rot);
    }
    
    private void clearSetModeState() {
        this.set_screen_idx = -1;
        this.set_mode_idx = -1;
        this.set_freq = 0;
        this.set_rot = 0;
        this.set_done = false;
    }
    
    @Override
    public final void updateScreenViewport(final long n, final ScreenDriver screenDriver, final RectangleImmutable rectangleImmutable) {
    }
    
    private static native int[] getAvailableScreenRotations0(final long p0, final int p1);
    
    private static native int getNumScreenResolutions0(final long p0, final int p1);
    
    private static native int[] getScreenResolution0(final long p0, final int p1, final int p2);
    
    private static native int[] getScreenRates0(final long p0, final int p1, final int p2);
    
    private static native long getScreenConfiguration0(final long p0, final int p1);
    
    private static native void freeScreenConfiguration0(final long p0);
    
    private static native int getCurrentScreenResolutionIndex0(final long p0);
    
    private static native int getCurrentScreenRate0(final long p0);
    
    private static native int getCurrentScreenRotation0(final long p0);
    
    private static native boolean setCurrentScreenModeStart0(final long p0, final int p1, final long p2, final int p3, final int p4, final int p5);
    
    private static native boolean sendRRScreenChangeNotify0(final long p0, final int p1, final long p2, final int p3, final int p4, final int p5);
    
    static {
        DEBUG = ScreenDriver.DEBUG;
    }
}
