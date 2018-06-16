// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.newt.Screen;
import com.jogamp.newt.event.MonitorEvent;
import com.jogamp.common.util.locks.LockFactory;
import java.util.HashMap;
import com.jogamp.newt.event.MonitorModeListener;
import java.util.ArrayList;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.common.util.ArrayHashSet;
import com.jogamp.common.util.locks.RecursiveLock;

public class ScreenMonitorState
{
    private static boolean DEBUG;
    private final RecursiveLock lock;
    private final ArrayHashSet<MonitorDevice> allMonitors;
    private final ArrayHashSet<MonitorMode> allMonitorModes;
    private final MonitorDevice primaryMonitor;
    private final ArrayList<MonitorModeListener> listener;
    private static HashMap<String, ScreenMonitorState> screenFQN2ScreenMonitorState;
    private static RecursiveLock screen2ScreenMonitorState;
    
    protected static void mapScreenMonitorState(final String s, final ScreenMonitorState screenMonitorState) {
        ScreenMonitorState.screen2ScreenMonitorState.lock();
        try {
            final ScreenMonitorState screenMonitorState2 = ScreenMonitorState.screenFQN2ScreenMonitorState.get(s);
            if (null != screenMonitorState2) {
                throw new RuntimeException("ScreenMonitorState " + screenMonitorState2 + " already mapped to " + s);
            }
            ScreenMonitorState.screenFQN2ScreenMonitorState.put(s, screenMonitorState);
            if (ScreenMonitorState.DEBUG) {
                System.err.println("ScreenMonitorState.map " + s + " -> " + screenMonitorState);
            }
        }
        finally {
            ScreenMonitorState.screen2ScreenMonitorState.unlock();
        }
    }
    
    protected static void unmapScreenMonitorState(final String s) {
        ScreenMonitorState.screen2ScreenMonitorState.lock();
        try {
            unmapScreenMonitorStateUnlocked(s);
        }
        finally {
            ScreenMonitorState.screen2ScreenMonitorState.unlock();
        }
    }
    
    protected static void unmapScreenMonitorStateUnlocked(final String s) {
        final ScreenMonitorState screenMonitorState = ScreenMonitorState.screenFQN2ScreenMonitorState.remove(s);
        if (ScreenMonitorState.DEBUG) {
            System.err.println("ScreenMonitorState.unmap " + s + " -> " + screenMonitorState);
        }
    }
    
    protected static ScreenMonitorState getScreenMonitorState(final String s) {
        ScreenMonitorState.screen2ScreenMonitorState.lock();
        try {
            return getScreenMonitorStateUnlocked(s);
        }
        finally {
            ScreenMonitorState.screen2ScreenMonitorState.unlock();
        }
    }
    
    protected static ScreenMonitorState getScreenMonitorStateUnlocked(final String s) {
        return ScreenMonitorState.screenFQN2ScreenMonitorState.get(s);
    }
    
    protected static void lockScreenMonitorState() {
        ScreenMonitorState.screen2ScreenMonitorState.lock();
    }
    
    protected static void unlockScreenMonitorState() {
        ScreenMonitorState.screen2ScreenMonitorState.unlock();
    }
    
    public ScreenMonitorState(final ArrayHashSet<MonitorDevice> allMonitors, final ArrayHashSet<MonitorMode> allMonitorModes, final MonitorDevice primaryMonitor) {
        this.lock = LockFactory.createRecursiveLock();
        this.listener = new ArrayList<MonitorModeListener>();
        this.allMonitors = allMonitors;
        this.allMonitorModes = allMonitorModes;
        this.primaryMonitor = primaryMonitor;
    }
    
    protected ArrayHashSet<MonitorDevice> getMonitorDevices() {
        return this.allMonitors;
    }
    
    protected MonitorDevice getPrimaryMonitorDevice() {
        return this.primaryMonitor;
    }
    
    protected ArrayHashSet<MonitorMode> getMonitorModes() {
        return this.allMonitorModes;
    }
    
    protected final int addListener(final MonitorModeListener monitorModeListener) {
        this.lock();
        try {
            this.listener.add(monitorModeListener);
            if (ScreenMonitorState.DEBUG) {
                System.err.println("ScreenMonitorState.addListener (size: " + this.listener.size() + "): " + monitorModeListener);
            }
            return this.listener.size();
        }
        finally {
            this.unlock();
        }
    }
    
    protected final int removeListener(final MonitorModeListener monitorModeListener) {
        this.lock();
        try {
            if (!this.listener.remove(monitorModeListener)) {
                throw new RuntimeException("MonitorModeListener " + monitorModeListener + " not contained");
            }
            if (ScreenMonitorState.DEBUG) {
                System.err.println("ScreenMonitorState.removeListener (size: " + this.listener.size() + "): " + monitorModeListener);
            }
            return this.listener.size();
        }
        finally {
            this.unlock();
        }
    }
    
    protected final MonitorDevice getMonitor(final MonitorDevice monitorDevice) {
        return this.allMonitors.get(monitorDevice);
    }
    
    protected final void validateMonitor(final MonitorDevice monitorDevice) {
        if (null == this.allMonitors.get(monitorDevice)) {
            throw new InternalError("Monitor unknown: " + monitorDevice);
        }
    }
    
    protected final void fireMonitorModeChangeNotify(final MonitorDevice monitorDevice, final MonitorMode monitorMode) {
        this.lock();
        try {
            this.validateMonitor(monitorDevice);
            final MonitorEvent monitorEvent = new MonitorEvent((short)600, monitorDevice, System.currentTimeMillis(), monitorMode);
            for (int i = 0; i < this.listener.size(); ++i) {
                this.listener.get(i).monitorModeChangeNotify(monitorEvent);
            }
        }
        finally {
            this.unlock();
        }
    }
    
    protected void fireMonitorModeChanged(final MonitorDevice monitorDevice, final MonitorMode monitorMode, final boolean b) {
        this.lock();
        try {
            this.validateMonitor(monitorDevice);
            final MonitorEvent monitorEvent = new MonitorEvent((short)601, monitorDevice, System.currentTimeMillis(), monitorMode);
            for (int i = 0; i < this.listener.size(); ++i) {
                this.listener.get(i).monitorModeChanged(monitorEvent, b);
            }
        }
        finally {
            this.unlock();
        }
    }
    
    protected final void lock() throws RuntimeException {
        this.lock.lock();
    }
    
    protected final void unlock() throws RuntimeException {
        this.lock.unlock();
    }
    
    static {
        ScreenMonitorState.DEBUG = Screen.DEBUG;
        ScreenMonitorState.screenFQN2ScreenMonitorState = new HashMap<String, ScreenMonitorState>();
        ScreenMonitorState.screen2ScreenMonitorState = LockFactory.createRecursiveLock();
    }
}
