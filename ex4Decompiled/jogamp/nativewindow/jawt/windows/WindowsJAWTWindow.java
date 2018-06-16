// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt.windows;

import jogamp.nativewindow.windows.GDIUtil;
import com.jogamp.nativewindow.util.Point;
import java.awt.GraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.nativewindow.jawt.JAWTUtil;
import jogamp.nativewindow.jawt.JAWT;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.jawt.JAWT_DrawingSurfaceInfo;
import jogamp.nativewindow.jawt.JAWT_DrawingSurface;
import com.jogamp.nativewindow.awt.JAWTWindow;

public class WindowsJAWTWindow extends JAWTWindow
{
    private JAWT_DrawingSurface ds;
    private boolean dsLocked;
    private JAWT_DrawingSurfaceInfo dsi;
    private JAWT_Win32DrawingSurfaceInfo win32dsi;
    protected long windowHandle;
    
    public WindowsJAWTWindow(final Object o, final AbstractGraphicsConfiguration abstractGraphicsConfiguration) {
        super(o, abstractGraphicsConfiguration);
    }
    
    @Override
    protected void invalidateNative() {
        this.windowHandle = 0L;
    }
    
    @Override
    protected JAWT fetchJAWTImpl() throws NativeWindowException {
        return JAWTUtil.getJAWT(false);
    }
    
    @Override
    protected int lockSurfaceImpl(final GraphicsConfiguration graphicsConfiguration) throws NativeWindowException {
        int n = 3;
        this.ds = this.getJAWT().GetDrawingSurface(this.component);
        if (this.ds == null) {
            this.unlockSurfaceImpl();
            return 1;
        }
        final int lock = this.ds.Lock();
        if (!(this.dsLocked = (0x0 == (lock & 0x1)))) {
            this.unlockSurfaceImpl();
            throw new NativeWindowException("Unable to lock surface");
        }
        if ((lock & 0x8) != 0x0) {
            n = 2;
        }
        this.dsi = this.ds.GetDrawingSurfaceInfo();
        if (this.dsi == null) {
            this.unlockSurfaceImpl();
            return 1;
        }
        this.updateLockedData(this.dsi.getBounds(), graphicsConfiguration);
        this.win32dsi = (JAWT_Win32DrawingSurfaceInfo)this.dsi.platformInfo(this.getJAWT());
        if (this.win32dsi == null) {
            this.unlockSurfaceImpl();
            return 1;
        }
        this.windowHandle = this.win32dsi.getHandle();
        this.drawable = this.win32dsi.getHdc();
        if (this.windowHandle == 0L || this.drawable == 0L) {
            this.unlockSurfaceImpl();
            return 1;
        }
        return n;
    }
    
    @Override
    protected void unlockSurfaceImpl() throws NativeWindowException {
        this.drawable = 0L;
        if (null != this.ds) {
            if (null != this.dsi) {
                this.ds.FreeDrawingSurfaceInfo(this.dsi);
            }
            if (this.dsLocked) {
                this.ds.Unlock();
            }
            this.getJAWT().FreeDrawingSurface(this.ds);
        }
        this.ds = null;
        this.dsi = null;
        this.win32dsi = null;
    }
    
    @Override
    public long getWindowHandle() {
        return this.windowHandle;
    }
    
    @Override
    protected Point getLocationOnScreenNativeImpl(final int n, final int n2) {
        return GDIUtil.GetRelativeLocation(this.getWindowHandle(), 0L, n, n2);
    }
}
