// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.UpstreamSurfaceHookMutableSize;

public class GDIDummyUpstreamSurfaceHook extends UpstreamSurfaceHookMutableSize
{
    public GDIDummyUpstreamSurfaceHook(final int n, final int n2) {
        super(n, n2);
    }
    
    @Override
    public final void create(final ProxySurface proxySurface) {
        final GDISurface gdiSurface = (GDISurface)proxySurface;
        if (0L == gdiSurface.getWindowHandle()) {
            final long createDummyWindow = GDIUtil.CreateDummyWindow(0, 0, 64, 64);
            if (0L == createDummyWindow) {
                throw new NativeWindowException("Error windowHandle 0, werr: " + GDI.GetLastError());
            }
            gdiSurface.setWindowHandle(createDummyWindow);
            gdiSurface.addUpstreamOptionBits(64);
        }
        proxySurface.addUpstreamOptionBits(256);
    }
    
    @Override
    public final void destroy(final ProxySurface proxySurface) {
        final GDISurface gdiSurface = (GDISurface)proxySurface;
        if (gdiSurface.containsUpstreamOptionBits(64)) {
            if (0L == gdiSurface.getWindowHandle()) {
                throw new InternalError("Owns upstream surface, but no GDI window: " + gdiSurface);
            }
            GDI.ShowWindow(gdiSurface.getWindowHandle(), 0);
            GDIUtil.DestroyDummyWindow(gdiSurface.getWindowHandle());
            gdiSurface.setWindowHandle(0L);
            gdiSurface.clearUpstreamOptionBits(64);
        }
    }
}
