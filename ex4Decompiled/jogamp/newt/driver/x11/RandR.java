// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.x11;

import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.MonitorDevice;
import jogamp.newt.MonitorModeProps;
import com.jogamp.common.util.VersionNumber;

public interface RandR
{
    public static final VersionNumber version110 = new VersionNumber(1, 1, 0);
    public static final VersionNumber version130 = new VersionNumber(1, 3, 0);
    public static final VersionNumber version140 = new VersionNumber(1, 4, 0);
    
    VersionNumber getVersion();
    
    String toString();
    
    void dumpInfo(final long p0, final int p1);
    
    boolean beginInitialQuery(final long p0, final ScreenDriver p1);
    
    void endInitialQuery(final long p0, final ScreenDriver p1);
    
    int[] getMonitorDeviceIds(final long p0, final ScreenDriver p1);
    
    int[] getAvailableRotations(final long p0, final ScreenDriver p1, final int p2);
    
    int[] getMonitorModeProps(final long p0, final ScreenDriver p1, final int p2);
    
    int[] getMonitorDeviceProps(final long p0, final ScreenDriver p1, final MonitorModeProps.Cache p2, final int p3);
    
    int[] getMonitorDeviceViewport(final long p0, final ScreenDriver p1, final int p2);
    
    int[] getCurrentMonitorModeProps(final long p0, final ScreenDriver p1, final int p2);
    
    boolean setCurrentMonitorModeStart(final long p0, final ScreenDriver p1, final MonitorDevice p2, final MonitorMode p3);
    
    boolean setCurrentMonitorModeWait(final ScreenDriver p0);
    
    void sendRRScreenChangeNotify(final long p0, final long p1);
    
    void updateScreenViewport(final long p0, final ScreenDriver p1, final RectangleImmutable p2);
}
