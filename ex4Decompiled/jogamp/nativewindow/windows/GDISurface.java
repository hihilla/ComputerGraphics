// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.ProxySurfaceImpl;

public class GDISurface extends ProxySurfaceImpl
{
    private long windowHandle;
    private long surfaceHandle;
    
    public GDISurface(final AbstractGraphicsConfiguration abstractGraphicsConfiguration, final long windowHandle, final UpstreamSurfaceHook upstreamSurfaceHook, final boolean b) {
        super(abstractGraphicsConfiguration, upstreamSurfaceHook, b);
        this.windowHandle = windowHandle;
        this.surfaceHandle = 0L;
    }
    
    @Override
    protected void invalidateImpl() {
        if (0L != this.surfaceHandle) {
            throw new NativeWindowException("didn't release surface Handle: " + this);
        }
        this.windowHandle = 0L;
    }
    
    @Override
    public final void setSurfaceHandle(final long windowHandle) {
        this.windowHandle = windowHandle;
    }
    
    public final void setWindowHandle(final long windowHandle) {
        this.windowHandle = windowHandle;
    }
    
    public final long getWindowHandle() {
        return this.windowHandle;
    }
    
    @Override
    protected final int lockSurfaceImpl() {
        if (0L == this.windowHandle) {
            throw new NativeWindowException("null window handle: " + this);
        }
        if (0L != this.surfaceHandle) {
            throw new InternalError("surface not released");
        }
        this.surfaceHandle = GDI.GetDC(this.windowHandle);
        return (0L != this.surfaceHandle) ? 3 : 1;
    }
    
    @Override
    protected final void unlockSurfaceImpl() {
        if (0L != this.surfaceHandle) {
            if (0 == GDI.ReleaseDC(this.windowHandle, this.surfaceHandle)) {
                throw new NativeWindowException("DC not released: " + this + ", isWindow " + GDI.IsWindow(this.windowHandle) + ", werr " + GDI.GetLastError() + ", thread: " + Thread.currentThread().getName());
            }
            this.surfaceHandle = 0L;
        }
    }
    
    @Override
    public final long getSurfaceHandle() {
        return this.surfaceHandle;
    }
    
    @Override
    public final int[] convertToWindowUnits(final int[] array) {
        return array;
    }
    
    @Override
    public final int[] convertToPixelUnits(final int[] array) {
        return array;
    }
}
