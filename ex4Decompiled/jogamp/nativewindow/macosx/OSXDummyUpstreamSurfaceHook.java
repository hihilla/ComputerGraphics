// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.macosx;

import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.UpstreamSurfaceHookMutableSize;

public class OSXDummyUpstreamSurfaceHook extends UpstreamSurfaceHookMutableSize
{
    long nsWindow;
    
    public OSXDummyUpstreamSurfaceHook(final int n, final int n2) {
        super(n, n2);
        this.nsWindow = 0L;
    }
    
    @Override
    public final void create(final ProxySurface proxySurface) {
        if (0L == this.nsWindow && 0L == proxySurface.getSurfaceHandle()) {
            this.nsWindow = OSXUtil.CreateNSWindow(0, 0, 64, 64);
            if (0L == this.nsWindow) {
                throw new NativeWindowException("Error NS window 0");
            }
            final long getNSView = OSXUtil.GetNSView(this.nsWindow);
            if (0L == getNSView) {
                throw new NativeWindowException("Error NS view 0");
            }
            proxySurface.setSurfaceHandle(getNSView);
            proxySurface.addUpstreamOptionBits(64);
        }
        proxySurface.addUpstreamOptionBits(256);
    }
    
    @Override
    public final void destroy(final ProxySurface proxySurface) {
        if (proxySurface.containsUpstreamOptionBits(64)) {
            if (0L == this.nsWindow || 0L == proxySurface.getSurfaceHandle()) {
                throw new InternalError("Owns upstream surface, but no OSX view/window: " + proxySurface + ", nsWindow 0x" + Long.toHexString(this.nsWindow));
            }
            OSXUtil.DestroyNSWindow(this.nsWindow);
            proxySurface.setSurfaceHandle(this.nsWindow = 0L);
            proxySurface.clearUpstreamOptionBits(64);
        }
    }
}
