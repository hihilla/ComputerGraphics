// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.opengl.egl.EGL;
import com.jogamp.common.ExceptionUtils;
import jogamp.nativewindow.ProxySurfaceImpl;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.UpstreamSurfaceHookMutableSize;

public class EGLDummyUpstreamSurfaceHook extends UpstreamSurfaceHookMutableSize
{
    public EGLDummyUpstreamSurfaceHook(final int n, final int n2) {
        super(n, n2);
    }
    
    @Override
    public final void create(final ProxySurface proxySurface) {
        final EGLGraphicsDevice eglGraphicsDevice = (EGLGraphicsDevice)proxySurface.getGraphicsConfiguration().getScreen().getDevice();
        eglGraphicsDevice.lock();
        try {
            if (0L == eglGraphicsDevice.getHandle()) {
                eglGraphicsDevice.open();
                proxySurface.addUpstreamOptionBits(128);
            }
            if (0L == proxySurface.getSurfaceHandle()) {
                proxySurface.setSurfaceHandle(EGLDrawableFactory.createPBufferSurfaceImpl((EGLGraphicsConfiguration)proxySurface.getGraphicsConfiguration(), 64, 64, false));
                proxySurface.addUpstreamOptionBits(64);
            }
            proxySurface.addUpstreamOptionBits(256);
        }
        finally {
            eglGraphicsDevice.unlock();
        }
    }
    
    @Override
    public final void destroy(final ProxySurface proxySurface) {
        if (proxySurface.containsUpstreamOptionBits(64)) {
            final EGLGraphicsDevice eglGraphicsDevice = (EGLGraphicsDevice)proxySurface.getGraphicsConfiguration().getScreen().getDevice();
            if (0L == proxySurface.getSurfaceHandle()) {
                throw new InternalError("Owns upstream surface, but no EGL surface: " + proxySurface);
            }
            eglGraphicsDevice.lock();
            try {
                if (EGLDrawable.DEBUG) {
                    System.err.println(EGLSurface.getThreadName() + ": EGLDummyUpstreamSurfaceHook: EGL.eglDestroySurface: 0x" + Long.toHexString(proxySurface.getSurfaceHandle()));
                    ProxySurfaceImpl.dumpHierarchy(System.err, proxySurface);
                    ExceptionUtils.dumpStack(System.err);
                }
                EGL.eglDestroySurface(eglGraphicsDevice.getHandle(), proxySurface.getSurfaceHandle());
                proxySurface.setSurfaceHandle(0L);
                proxySurface.clearUpstreamOptionBits(64);
            }
            finally {
                eglGraphicsDevice.unlock();
            }
        }
    }
}
