// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt.x11;

import jogamp.nativewindow.x11.X11Lib;
import com.jogamp.nativewindow.util.Point;
import java.awt.GraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.nativewindow.jawt.JAWTUtil;
import jogamp.nativewindow.jawt.JAWT;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.jawt.JAWT_DrawingSurfaceInfo;
import jogamp.nativewindow.jawt.JAWT_DrawingSurface;
import com.jogamp.nativewindow.awt.JAWTWindow;

public class X11JAWTWindow extends JAWTWindow
{
    private JAWT_DrawingSurface ds;
    private boolean dsLocked;
    private JAWT_DrawingSurfaceInfo dsi;
    private JAWT_X11DrawingSurfaceInfo x11dsi;
    
    public X11JAWTWindow(final Object o, final AbstractGraphicsConfiguration abstractGraphicsConfiguration) {
        super(o, abstractGraphicsConfiguration);
    }
    
    @Override
    protected void invalidateNative() {
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
        this.x11dsi = (JAWT_X11DrawingSurfaceInfo)this.dsi.platformInfo(this.getJAWT());
        if (this.x11dsi == null) {
            this.unlockSurfaceImpl();
            return 1;
        }
        this.drawable = this.x11dsi.getDrawable();
        if (this.drawable == 0L) {
            this.unlockSurfaceImpl();
            return 1;
        }
        return n;
    }
    
    @Override
    protected void unlockSurfaceImpl() throws NativeWindowException {
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
        this.x11dsi = null;
    }
    
    @Override
    protected Point getLocationOnScreenNativeImpl(final int n, final int n2) {
        return X11Lib.GetRelativeLocation(this.getDisplayHandle(), this.getScreenIndex(), this.getWindowHandle(), 0L, n, n2);
    }
}
