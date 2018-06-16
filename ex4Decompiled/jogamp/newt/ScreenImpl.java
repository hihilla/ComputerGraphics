// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import com.jogamp.newt.util.MonitorModeUtil;
import com.jogamp.newt.event.MonitorEvent;
import java.util.List;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import java.util.ArrayList;
import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.newt.event.MonitorModeListener;
import com.jogamp.newt.Screen;

public abstract class ScreenImpl extends Screen implements MonitorModeListener
{
    protected static final boolean DEBUG_TEST_SCREENMODE_DISABLED;
    public static final int default_sm_bpp = 32;
    public static final int default_sm_widthmm = 519;
    public static final int default_sm_heightmm = 324;
    public static final int default_sm_rate = 60;
    public static final int default_sm_rotation = 0;
    protected DisplayImpl display;
    protected int screen_idx;
    protected String fqname;
    protected int hashCode;
    protected AbstractGraphicsScreen aScreen;
    protected int refCount;
    protected Rectangle virtViewportPU;
    protected Rectangle virtViewportWU;
    protected static Dimension usrSize;
    protected static volatile boolean usrSizeQueried;
    private final ArrayList<MonitorModeListener> refMonitorModeListener;
    private long tCreated;
    
    public ScreenImpl() {
        this.virtViewportPU = new Rectangle(0, 0, 0, 0);
        this.virtViewportWU = new Rectangle(0, 0, 0, 0);
        this.refMonitorModeListener = new ArrayList<MonitorModeListener>();
    }
    
    static void initSingleton() {
    }
    
    private static Class<?> getScreenClass(final String s) throws ClassNotFoundException {
        final Class<?> customClass = NewtFactory.getCustomClass(s, "ScreenDriver");
        if (null == customClass) {
            throw new ClassNotFoundException("Failed to find NEWT Screen Class <" + s + ".ScreenDriver>");
        }
        return customClass;
    }
    
    public static Screen create(final Display display, int validateScreenIndex) {
        try {
            if (!ScreenImpl.usrSizeQueried) {
                synchronized (Screen.class) {
                    if (!ScreenImpl.usrSizeQueried) {
                        ScreenImpl.usrSizeQueried = true;
                        final int intProperty = PropertyAccess.getIntProperty("newt.ws.swidth", true, 0);
                        final int intProperty2 = PropertyAccess.getIntProperty("newt.ws.sheight", true, 0);
                        if (intProperty > 0 && intProperty2 > 0) {
                            ScreenImpl.usrSize = new Dimension(intProperty, intProperty2);
                            System.err.println("User screen size " + ScreenImpl.usrSize);
                        }
                    }
                }
            }
            synchronized (ScreenImpl.screenList) {
                final ScreenImpl screenImpl = (ScreenImpl)getScreenClass(display.getType()).newInstance();
                screenImpl.display = (DisplayImpl)display;
                validateScreenIndex = screenImpl.validateScreenIndex(validateScreenIndex);
                final Screen lastScreen = Screen.getLastScreenOf(display, validateScreenIndex, -1);
                if (null != lastScreen) {
                    if (ScreenImpl.DEBUG) {
                        System.err.println("Screen.create() REUSE: " + lastScreen + " " + Display.getThreadName());
                    }
                    return lastScreen;
                }
                screenImpl.screen_idx = validateScreenIndex;
                screenImpl.fqname = display.getFQName() + "-s" + validateScreenIndex;
                screenImpl.hashCode = screenImpl.fqname.hashCode();
                screenImpl.instantiationFinished();
                Screen.addScreen2List(screenImpl);
                if (ScreenImpl.DEBUG) {
                    System.err.println("Screen.create() NEW: " + screenImpl + " " + Display.getThreadName());
                }
                return screenImpl;
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    protected void instantiationFinished() {
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final ScreenImpl screenImpl = (ScreenImpl)o;
        return (this.display == screenImpl.display || (this.display != null && this.display.equals(screenImpl.display))) && this.screen_idx == screenImpl.screen_idx;
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    
    @Override
    public final synchronized void createNative() throws NativeWindowException {
        if (null == this.aScreen) {
            if (ScreenImpl.DEBUG) {
                this.tCreated = System.nanoTime();
                System.err.println("Screen.createNative() START (" + Display.getThreadName() + ", " + this + ")");
            }
            else {
                this.tCreated = 0L;
            }
            this.display.addReference();
            this.createNativeImpl();
            if (null == this.aScreen) {
                throw new NativeWindowException("Screen.createNative() failed to instanciate an AbstractGraphicsScreen");
            }
            this.initMonitorState();
            synchronized (ScreenImpl.screenList) {
                ++ScreenImpl.screensActive;
                if (ScreenImpl.DEBUG) {
                    System.err.println("Screen.createNative() END (" + Display.getThreadName() + ", " + this + "), active " + ScreenImpl.screensActive + ", total " + (System.nanoTime() - this.tCreated) / 1000000.0 + "ms");
                }
            }
            ScreenMonitorState.getScreenMonitorState(this.getFQName()).addListener(this);
        }
    }
    
    @Override
    public final synchronized void destroy() {
        synchronized (ScreenImpl.screenList) {
            if (0 < ScreenImpl.screensActive) {
                --ScreenImpl.screensActive;
            }
            if (ScreenImpl.DEBUG) {
                System.err.println("Screen.destroy() (" + Display.getThreadName() + "): active " + ScreenImpl.screensActive);
            }
        }
        if (null != this.aScreen) {
            this.releaseMonitorState();
            this.closeNativeImpl();
            this.aScreen = null;
        }
        this.refCount = 0;
        this.display.removeReference();
    }
    
    @Override
    public final synchronized int addReference() throws NativeWindowException {
        if (ScreenImpl.DEBUG) {
            System.err.println("Screen.addReference() (" + Display.getThreadName() + "): " + this.refCount + " -> " + (this.refCount + 1));
        }
        if (0 == this.refCount) {
            this.createNative();
        }
        else if (null == this.aScreen) {
            throw new NativeWindowException("Screen.addReference() (refCount " + this.refCount + ") null AbstractGraphicsScreen");
        }
        return ++this.refCount;
    }
    
    @Override
    public final synchronized int removeReference() {
        if (ScreenImpl.DEBUG) {
            System.err.println("Screen.removeReference() (" + Display.getThreadName() + "): " + this.refCount + " -> " + (this.refCount - 1));
        }
        --this.refCount;
        if (0 >= this.refCount) {
            this.destroy();
            this.refCount = 0;
        }
        return this.refCount;
    }
    
    @Override
    public final synchronized int getReferenceCount() {
        return this.refCount;
    }
    
    protected abstract void createNativeImpl();
    
    protected abstract void closeNativeImpl();
    
    protected abstract int validateScreenIndex(final int p0);
    
    protected void calcVirtualScreenOriginAndSize(final Rectangle rectangle, final Rectangle rectangle2) {
        this.unionOfMonitorViewports(rectangle, rectangle2);
    }
    
    @Override
    public final String getFQName() {
        return this.fqname;
    }
    
    protected void updateVirtualScreenOriginAndSize() {
        if (null != ScreenImpl.usrSize) {
            this.virtViewportPU.set(0, 0, ScreenImpl.usrSize.getWidth(), ScreenImpl.usrSize.getHeight());
            this.virtViewportWU.set(0, 0, ScreenImpl.usrSize.getWidth(), ScreenImpl.usrSize.getHeight());
            if (ScreenImpl.DEBUG) {
                System.err.println("Update user virtual screen viewport @ " + Thread.currentThread().getName() + ": " + this.virtViewportPU);
            }
        }
        else {
            this.calcVirtualScreenOriginAndSize(this.virtViewportPU, this.virtViewportWU);
            if (ScreenImpl.DEBUG) {
                System.err.println("Updated virtual screen viewport @ " + Thread.currentThread().getName() + ": " + this.virtViewportPU + " [pixel], " + this.virtViewportWU + " [window]");
            }
        }
    }
    
    @Override
    public final Display getDisplay() {
        return this.display;
    }
    
    @Override
    public final int getIndex() {
        return this.screen_idx;
    }
    
    @Override
    public final AbstractGraphicsScreen getGraphicsScreen() {
        return this.aScreen;
    }
    
    @Override
    public final synchronized boolean isNativeValid() {
        return null != this.aScreen;
    }
    
    @Override
    public final int getX() {
        return this.virtViewportPU.getX();
    }
    
    @Override
    public final int getY() {
        return this.virtViewportPU.getY();
    }
    
    @Override
    public final int getWidth() {
        return this.virtViewportPU.getWidth();
    }
    
    @Override
    public final int getHeight() {
        return this.virtViewportPU.getHeight();
    }
    
    @Override
    public final RectangleImmutable getViewport() {
        return this.virtViewportPU;
    }
    
    @Override
    public final RectangleImmutable getViewportInWindowUnits() {
        return this.virtViewportWU;
    }
    
    @Override
    public String toString() {
        return "NEWT-Screen[" + this.getFQName() + ", idx " + this.screen_idx + ", refCount " + this.refCount + ", vsize " + this.virtViewportPU + " [pixels], " + this.virtViewportWU + " [window], " + this.aScreen + ", " + this.display + ", monitors: " + this.getMonitorDevices() + "]";
    }
    
    protected abstract void collectNativeMonitorModesAndDevicesImpl(final MonitorModeProps.Cache p0);
    
    protected boolean updateNativeMonitorDeviceViewportImpl(final MonitorDevice monitorDevice, final float[] array, final Rectangle rectangle, final Rectangle rectangle2) {
        return false;
    }
    
    protected abstract MonitorMode queryCurrentMonitorModeImpl(final MonitorDevice p0);
    
    protected abstract boolean setCurrentMonitorModeImpl(final MonitorDevice p0, final MonitorMode p1);
    
    @Override
    public final List<MonitorMode> getMonitorModes() {
        final ScreenMonitorState screenMonitorStatus = this.getScreenMonitorStatus(false);
        return (null != screenMonitorStatus) ? screenMonitorStatus.getMonitorModes().getData() : null;
    }
    
    @Override
    public final List<MonitorDevice> getMonitorDevices() {
        final ScreenMonitorState screenMonitorStatus = this.getScreenMonitorStatus(false);
        return (null != screenMonitorStatus) ? screenMonitorStatus.getMonitorDevices().getData() : null;
    }
    
    @Override
    public MonitorDevice getPrimaryMonitor() {
        final ScreenMonitorState screenMonitorStatus = this.getScreenMonitorStatus(false);
        return (null != screenMonitorStatus) ? screenMonitorStatus.getPrimaryMonitorDevice() : null;
    }
    
    final ScreenMonitorState getScreenMonitorStatus(final boolean b) {
        final String fqName = this.getFQName();
        final ScreenMonitorState screenMonitorState = ScreenMonitorState.getScreenMonitorState(fqName);
        if (null == screenMonitorState & b) {
            throw new InternalError("ScreenMonitorStatus.getMonitorModeStatus(" + fqName + ") == null");
        }
        return screenMonitorState;
    }
    
    @Override
    public void monitorModeChangeNotify(final MonitorEvent monitorEvent) {
        if (ScreenImpl.DEBUG) {
            System.err.println("monitorModeChangeNotify @ " + Thread.currentThread().getName() + ": " + monitorEvent);
        }
        for (int i = 0; i < this.refMonitorModeListener.size(); ++i) {
            this.refMonitorModeListener.get(i).monitorModeChangeNotify(monitorEvent);
        }
    }
    
    private void updateNativeMonitorDevicesViewport() {
        final List<MonitorDevice> monitorDevices = this.getMonitorDevices();
        for (int i = monitorDevices.size() - 1; i >= 0; --i) {
            final MonitorDeviceImpl monitorDeviceImpl = monitorDevices.get(i);
            final float[] pixelScale = monitorDeviceImpl.getPixelScale(new float[2]);
            final boolean updateNativeMonitorDeviceViewportImpl = this.updateNativeMonitorDeviceViewportImpl(monitorDeviceImpl, pixelScale, monitorDeviceImpl.getMutuableViewportPU(), monitorDeviceImpl.getMutuableViewportWU());
            if (ScreenImpl.DEBUG) {
                System.err.println("Screen.updateMonitorViewport[" + Display.toHexString(monitorDeviceImpl.getId()) + "] @  " + Thread.currentThread().getName() + ": updated: " + updateNativeMonitorDeviceViewportImpl + ", PU " + monitorDeviceImpl.getViewport() + ", WU " + monitorDeviceImpl.getViewportInWindowUnits() + ", pixelScale [" + pixelScale[0] + ", " + pixelScale[1] + "]");
            }
        }
    }
    
    @Override
    public void monitorModeChanged(final MonitorEvent monitorEvent, final boolean b) {
        if (b) {
            this.updateNativeMonitorDevicesViewport();
            this.updateVirtualScreenOriginAndSize();
        }
        if (ScreenImpl.DEBUG) {
            System.err.println("monitorModeChangeNotify @ " + Thread.currentThread().getName() + ": success " + b + ", " + monitorEvent);
        }
        for (int i = 0; i < this.refMonitorModeListener.size(); ++i) {
            this.refMonitorModeListener.get(i).monitorModeChanged(monitorEvent, b);
        }
    }
    
    @Override
    public final synchronized void addMonitorModeListener(final MonitorModeListener monitorModeListener) {
        this.refMonitorModeListener.add(monitorModeListener);
    }
    
    @Override
    public final synchronized void removeMonitorModeListener(final MonitorModeListener monitorModeListener) {
        this.refMonitorModeListener.remove(monitorModeListener);
    }
    
    private final MonitorMode getVirtualMonitorMode(final MonitorModeProps.Cache cache, final int n) {
        final int[] array = new int[8];
        int n2 = 0;
        array[n2++] = 8;
        array[n2++] = this.getWidth();
        array[n2++] = this.getHeight();
        array[n2++] = 32;
        array[n2++] = 6000;
        array[n2++] = 0;
        array[n2++] = n;
        array[n2++] = 0;
        if (8 != n2) {
            throw new InternalError("XX");
        }
        return MonitorModeProps.streamInMonitorMode(null, cache, array, 0);
    }
    
    private final MonitorDevice getVirtualMonitorDevice(final MonitorModeProps.Cache cache, final int n, final MonitorMode monitorMode) {
        final int[] array = new int[17];
        int n2 = 0;
        array[n2++] = 17;
        array[n2++] = n;
        array[n2++] = 0;
        array[n2++] = ((0 == n) ? 1 : 0);
        array[n2++] = 519;
        array[n2++] = 324;
        array[n2++] = 0;
        array[n2++] = 0;
        array[n2++] = monitorMode.getRotatedWidth();
        array[n2++] = monitorMode.getRotatedHeight();
        array[n2++] = 0;
        array[n2++] = 0;
        array[n2++] = monitorMode.getRotatedWidth();
        array[n2++] = monitorMode.getRotatedHeight();
        array[n2++] = monitorMode.getId();
        array[n2++] = monitorMode.getRotation();
        array[n2++] = monitorMode.getId();
        if (17 != n2) {
            throw new InternalError("XX");
        }
        return MonitorModeProps.streamInMonitorDevice(cache, this, null, array, 0, null);
    }
    
    protected final MonitorMode queryCurrentMonitorModeIntern(final MonitorDevice monitorDevice) {
        MonitorMode monitorMode;
        if (ScreenImpl.DEBUG_TEST_SCREENMODE_DISABLED) {
            monitorMode = null;
        }
        else {
            monitorMode = this.queryCurrentMonitorModeImpl(monitorDevice);
        }
        if (null == monitorMode) {
            if (0 >= this.getWidth() || 0 >= this.getHeight()) {
                this.updateVirtualScreenOriginAndSize();
            }
            monitorMode = this.getVirtualMonitorMode(null, monitorDevice.getCurrentMode().getId());
        }
        return monitorMode;
    }
    
    private final ScreenMonitorState initMonitorState() {
        long nanoTime;
        if (ScreenImpl.DEBUG) {
            nanoTime = System.nanoTime();
            System.err.println("Screen.initMonitorState() START (" + Display.getThreadName() + ", " + this + ")");
        }
        else {
            nanoTime = 0L;
        }
        boolean b = false;
        ScreenMonitorState.lockScreenMonitorState();
        ScreenMonitorState screenMonitorState;
        try {
            screenMonitorState = ScreenMonitorState.getScreenMonitorState(this.getFQName());
            if (null == screenMonitorState) {
                final MonitorModeProps.Cache cache = new MonitorModeProps.Cache();
                if (0 >= this.collectNativeMonitorModes(cache)) {
                    this.updateVirtualScreenOriginAndSize();
                    b = true;
                    final MonitorMode virtualMonitorMode = this.getVirtualMonitorMode(cache, 0);
                    cache.monitorModes.getOrAdd(virtualMonitorMode);
                    final MonitorDevice virtualMonitorDevice = this.getVirtualMonitorDevice(cache, 0, virtualMonitorMode);
                    ((MonitorDeviceImpl)virtualMonitorDevice).setIsPrimary(true);
                    cache.monitorDevices.getOrAdd(virtualMonitorDevice);
                    cache.setPrimary(virtualMonitorDevice);
                }
                if (null == cache.getPrimary()) {
                    final MonitorDevice primary = cache.monitorDevices.get(0);
                    ((MonitorDeviceImpl)primary).setIsPrimary(true);
                    cache.setPrimary(primary);
                    if (ScreenImpl.DEBUG) {
                        System.err.println("WARNING: Fallback primary: " + primary);
                    }
                }
                MonitorModeUtil.sort(cache.monitorModes.getData(), false);
                final Iterator<MonitorDevice> iterator = cache.monitorDevices.iterator();
                while (iterator.hasNext()) {
                    MonitorModeUtil.sort(iterator.next().getSupportedModes(), false);
                }
                if (ScreenImpl.DEBUG) {
                    int n = 0;
                    final Iterator<MonitorMode> iterator2 = cache.monitorModes.iterator();
                    while (iterator2.hasNext()) {
                        System.err.println("All[" + n + "]: " + iterator2.next());
                        ++n;
                    }
                    int n2 = 0;
                    for (final MonitorDevice monitorDevice : cache.monitorDevices) {
                        System.err.println("[" + n2 + "]: " + monitorDevice);
                        int n3 = 0;
                        final Iterator<MonitorMode> iterator4 = monitorDevice.getSupportedModes().iterator();
                        while (iterator4.hasNext()) {
                            System.err.println("[" + n2 + "][" + n3 + "]: " + iterator4.next());
                            ++n3;
                        }
                        ++n2;
                    }
                    System.err.println("Primary: " + cache.getPrimary());
                }
                screenMonitorState = new ScreenMonitorState(cache.monitorDevices, cache.monitorModes, cache.getPrimary());
                ScreenMonitorState.mapScreenMonitorState(this.getFQName(), screenMonitorState);
            }
        }
        finally {
            ScreenMonitorState.unlockScreenMonitorState();
        }
        if (ScreenImpl.DEBUG) {
            System.err.println("Screen.initMonitorState() END dt " + (System.nanoTime() - nanoTime) / 1000000.0 + "ms");
        }
        if (!b) {
            this.updateVirtualScreenOriginAndSize();
        }
        return screenMonitorState;
    }
    
    private final int collectNativeMonitorModes(final MonitorModeProps.Cache cache) {
        if (!ScreenImpl.DEBUG_TEST_SCREENMODE_DISABLED) {
            this.collectNativeMonitorModesAndDevicesImpl(cache);
            MonitorModeProps.identifyMonitorDevices(cache);
        }
        for (int i = cache.monitorModes.size() - 1; i >= 0; --i) {
            final MonitorMode monitorMode = cache.monitorModes.get(i);
            if (16 > monitorMode.getSurfaceSize().getBitsPerPixel()) {
                boolean equals = false;
                for (int n = cache.monitorDevices.size() - 1; !equals && n >= 0; equals = cache.monitorDevices.get(n).getCurrentMode().equals(monitorMode), --n) {}
                if (!equals) {
                    cache.monitorModes.remove(i);
                    for (int j = cache.monitorDevices.size() - 1; j >= 0; --j) {
                        ((MonitorDeviceImpl)cache.monitorDevices.get(j)).getSupportedModesImpl().remove(monitorMode);
                    }
                }
            }
        }
        if (ScreenImpl.DEBUG) {
            System.err.println("ScreenImpl.collectNativeMonitorModes: MonitorDevice number : " + cache.monitorDevices.size());
            System.err.println("ScreenImpl.collectNativeMonitorModes: MonitorMode number   : " + cache.monitorModes.size());
            System.err.println("ScreenImpl.collectNativeMonitorModes: SizeAndRate number   : " + cache.sizeAndRates.size());
            System.err.println("ScreenImpl.collectNativeMonitorModes: SurfaceSize number   : " + cache.surfaceSizes.size());
            System.err.println("ScreenImpl.collectNativeMonitorModes: Resolution number    : " + cache.resolutions.size());
        }
        return cache.monitorDevices.size();
    }
    
    private final void releaseMonitorState() {
        ScreenMonitorState.lockScreenMonitorState();
        try {
            final ScreenMonitorState screenMonitorState = ScreenMonitorState.getScreenMonitorState(this.getFQName());
            if (null != screenMonitorState) {
                screenMonitorState.lock();
                try {
                    if (0 == screenMonitorState.removeListener(this)) {
                        final ArrayList<MonitorDevice> data = screenMonitorState.getMonitorDevices().getData();
                        for (int i = 0; i < data.size(); ++i) {
                            final MonitorDevice monitorDevice = data.get(i);
                            if (monitorDevice.isModeChangedByUs()) {
                                System.err.println("Screen.destroy(): Reset " + monitorDevice);
                                try {
                                    monitorDevice.setCurrentMode(monitorDevice.getOriginalMode());
                                }
                                catch (Throwable t) {
                                    t.printStackTrace();
                                }
                            }
                        }
                        ScreenMonitorState.unmapScreenMonitorState(this.getFQName());
                    }
                }
                finally {
                    screenMonitorState.unlock();
                }
            }
        }
        finally {
            ScreenMonitorState.unlockScreenMonitorState();
        }
    }
    
    private final void shutdown() {
        final ScreenMonitorState screenMonitorStateUnlocked = ScreenMonitorState.getScreenMonitorStateUnlocked(this.getFQName());
        if (null != screenMonitorStateUnlocked) {
            final ArrayList<MonitorDevice> data = screenMonitorStateUnlocked.getMonitorDevices().getData();
            for (int i = 0; i < data.size(); ++i) {
                final MonitorDevice monitorDevice = data.get(i);
                if (monitorDevice.isModeChangedByUs()) {
                    System.err.println("Screen.shutdown(): Reset " + monitorDevice);
                    try {
                        monitorDevice.setCurrentMode(monitorDevice.getOriginalMode());
                    }
                    catch (Throwable t) {}
                }
            }
            ScreenMonitorState.unmapScreenMonitorStateUnlocked(this.getFQName());
        }
    }
    
    static final void shutdownAll() {
        final int size = ScreenImpl.screenList.size();
        if (ScreenImpl.DEBUG) {
            System.err.println("Screen.shutdownAll " + size + " instances, on thread " + Display.getThreadName());
        }
        for (int n = 0; n < size && ScreenImpl.screenList.size() > 0; ++n) {
            final ScreenImpl screenImpl = ScreenImpl.screenList.remove(0).get();
            if (ScreenImpl.DEBUG) {
                System.err.println("Screen.shutdownAll[" + (n + 1) + "/" + size + "]: " + screenImpl + ", GCed " + (null == screenImpl));
            }
            if (null != screenImpl) {
                screenImpl.shutdown();
            }
        }
    }
    
    static {
        Debug.initSingleton();
        DEBUG_TEST_SCREENMODE_DISABLED = PropertyAccess.isPropertyDefined("newt.test.Screen.disableScreenMode", true);
        DisplayImpl.initSingleton();
        ScreenImpl.usrSize = null;
        ScreenImpl.usrSizeQueried = false;
    }
}
