// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.newt.Screen;
import com.jogamp.common.util.ArrayHashSet;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.MonitorMode;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.newt.MonitorDevice;

public class MonitorDeviceImpl extends MonitorDevice
{
    public MonitorDeviceImpl(final ScreenImpl screenImpl, final int n, final boolean b, final boolean b2, final DimensionImmutable dimensionImmutable, final MonitorMode monitorMode, final float[] array, final Rectangle rectangle, final Rectangle rectangle2, final ArrayHashSet<MonitorMode> set) {
        super(screenImpl, n, b, b2, dimensionImmutable, monitorMode, array, rectangle, rectangle2, set);
    }
    
    @Override
    public final MonitorMode queryCurrentMode() throws IllegalStateException {
        final ScreenImpl screenImpl = (ScreenImpl)this.screen;
        if (!screenImpl.isNativeValid()) {
            throw new IllegalStateException("Screen is not created natively: " + screenImpl);
        }
        final ScreenMonitorState screenMonitorStatus = screenImpl.getScreenMonitorStatus(true);
        screenMonitorStatus.lock();
        try {
            final MonitorMode queryCurrentMonitorModeIntern = screenImpl.queryCurrentMonitorModeIntern(this);
            if (null == queryCurrentMonitorModeIntern) {
                throw new InternalError("getCurrentMonitorModeIntern() == null");
            }
            MonitorMode monitorMode = this.supportedModes.get(queryCurrentMonitorModeIntern);
            if (null == monitorMode) {
                monitorMode = this.supportedModes.getOrAdd(screenMonitorStatus.getMonitorModes().getOrAdd(queryCurrentMonitorModeIntern));
                if (Screen.DEBUG) {
                    System.err.println("Adding new mode: " + queryCurrentMonitorModeIntern + " -> " + monitorMode);
                }
            }
            if (this.getCurrentMode().hashCode() != monitorMode.hashCode()) {
                this.setCurrentModeValue(monitorMode, this.isPrimary);
                screenMonitorStatus.fireMonitorModeChanged(this, monitorMode, true);
            }
            return monitorMode;
        }
        finally {
            screenMonitorStatus.unlock();
        }
    }
    
    @Override
    public final boolean setCurrentMode(final MonitorMode monitorMode) throws IllegalStateException {
        final ScreenImpl screenImpl = (ScreenImpl)this.screen;
        if (!screenImpl.isNativeValid()) {
            throw new IllegalStateException("Screen is not created natively: " + screenImpl);
        }
        if (Screen.DEBUG) {
            System.err.println("Screen.setCurrentMode.0: " + this + " -> " + monitorMode);
        }
        final ScreenMonitorState screenMonitorStatus = screenImpl.getScreenMonitorStatus(true);
        screenMonitorStatus.lock();
        try {
            final MonitorMode queryCurrentMode = this.queryCurrentMode();
            final MonitorMode monitorMode2 = this.supportedModes.get(monitorMode);
            if (null == monitorMode2) {
                throw new IllegalArgumentException("Given mode not in set of modes. Current mode " + monitorMode + ", " + this);
            }
            if (monitorMode2.equals(queryCurrentMode)) {
                if (Screen.DEBUG) {
                    System.err.println("Screen.setCurrentMode: 0.0 is-current (skip) " + monitorMode2 + " == " + queryCurrentMode);
                }
                return true;
            }
            long currentTimeMillis;
            if (Screen.DEBUG) {
                currentTimeMillis = System.currentTimeMillis();
            }
            else {
                currentTimeMillis = 0L;
            }
            screenMonitorStatus.fireMonitorModeChangeNotify(this, monitorMode2);
            if (Screen.DEBUG) {
                System.err.println("Screen.setCurrentMode (" + (System.currentTimeMillis() - currentTimeMillis) + "ms): fireModeChangeNotify() " + monitorMode2);
            }
            boolean setCurrentMonitorModeImpl = screenImpl.setCurrentMonitorModeImpl(this, monitorMode2);
            if (setCurrentMonitorModeImpl) {
                if (Screen.DEBUG) {
                    System.err.println("Screen.setCurrentMode (" + (System.currentTimeMillis() - currentTimeMillis) + "ms): setCurrentModeImpl() " + monitorMode2 + ", success(1): " + setCurrentMonitorModeImpl);
                }
            }
            else {
                final MonitorMode queryCurrentMode2 = this.queryCurrentMode();
                setCurrentMonitorModeImpl = (queryCurrentMode2.hashCode() == monitorMode2.hashCode());
                if (Screen.DEBUG) {
                    System.err.println("Screen.setCurrentMode.2: queried " + queryCurrentMode2);
                    System.err.println("Screen.setCurrentMode (" + (System.currentTimeMillis() - currentTimeMillis) + "ms): setCurrentModeImpl() " + monitorMode2 + ", success(2): " + setCurrentMonitorModeImpl);
                }
            }
            if (setCurrentMonitorModeImpl) {
                this.setCurrentModeValue(monitorMode2, this.isPrimary);
                this.modeChanged = !this.isOriginalMode();
            }
            screenMonitorStatus.fireMonitorModeChanged(this, monitorMode2, setCurrentMonitorModeImpl);
            if (Screen.DEBUG) {
                System.err.println("Screen.setCurrentMode (" + (System.currentTimeMillis() - currentTimeMillis) + "ms): X.X: success " + setCurrentMonitorModeImpl + ": " + this);
            }
            return setCurrentMonitorModeImpl;
        }
        finally {
            screenMonitorStatus.unlock();
        }
    }
    
    private final void setCurrentModeValue(final MonitorMode currentMode, final boolean isPrimary) {
        this.currentMode = currentMode;
        this.isPrimary = isPrimary;
    }
    
    final Rectangle getMutuableViewportPU() {
        return this.viewportPU;
    }
    
    final Rectangle getMutuableViewportWU() {
        return this.viewportWU;
    }
    
    final ArrayHashSet<MonitorMode> getSupportedModesImpl() {
        return this.supportedModes;
    }
    
    final void setIsClone(final boolean isClone) {
        this.isClone = isClone;
    }
    
    final void setIsPrimary(final boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
}
