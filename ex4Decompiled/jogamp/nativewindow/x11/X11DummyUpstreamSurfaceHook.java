// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11;

import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.nativewindow.x11.X11GraphicsConfiguration;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.UpstreamSurfaceHookMutableSize;

public class X11DummyUpstreamSurfaceHook extends UpstreamSurfaceHookMutableSize
{
    public X11DummyUpstreamSurfaceHook(final int n, final int n2) {
        super(n, n2);
    }
    
    @Override
    public final void create(final ProxySurface proxySurface) {
        final X11GraphicsConfiguration x11GraphicsConfiguration = (X11GraphicsConfiguration)proxySurface.getGraphicsConfiguration();
        final X11GraphicsScreen x11GraphicsScreen = (X11GraphicsScreen)x11GraphicsConfiguration.getScreen();
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        x11GraphicsDevice.lock();
        try {
            if (0L == x11GraphicsDevice.getHandle()) {
                x11GraphicsDevice.open();
                proxySurface.addUpstreamOptionBits(128);
            }
            if (0L == proxySurface.getSurfaceHandle()) {
                final long createWindow = X11Lib.CreateWindow(0L, x11GraphicsDevice.getHandle(), x11GraphicsScreen.getIndex(), x11GraphicsConfiguration.getXVisualID(), 64, 64, false, false);
                if (0L == createWindow) {
                    throw new NativeWindowException("Creating dummy window failed w/ " + x11GraphicsConfiguration);
                }
                proxySurface.setSurfaceHandle(createWindow);
                proxySurface.addUpstreamOptionBits(64);
            }
            proxySurface.addUpstreamOptionBits(256);
        }
        finally {
            x11GraphicsDevice.unlock();
        }
    }
    
    @Override
    public final void destroy(final ProxySurface proxySurface) {
        if (proxySurface.containsUpstreamOptionBits(64)) {
            final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)proxySurface.getGraphicsConfiguration().getScreen().getDevice();
            if (0L == proxySurface.getSurfaceHandle()) {
                throw new InternalError("Owns upstream surface, but no X11 window: " + proxySurface);
            }
            x11GraphicsDevice.lock();
            try {
                X11Lib.DestroyWindow(x11GraphicsDevice.getHandle(), proxySurface.getSurfaceHandle());
                proxySurface.setSurfaceHandle(0L);
                proxySurface.clearUpstreamOptionBits(64);
            }
            finally {
                x11GraphicsDevice.unlock();
            }
        }
    }
}
