// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.common.nio.Buffers;
import com.jogamp.nativewindow.NativeWindow;
import java.nio.IntBuffer;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.egl.EGL;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.GenericUpstreamSurfacelessHook;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.ProxySurface;
import jogamp.nativewindow.ProxySurfaceImpl;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.NativeSurface;
import jogamp.nativewindow.WrappedSurface;

public class EGLSurface extends WrappedSurface
{
    static boolean DEBUG;
    
    public static EGLSurface get(final NativeSurface nativeSurface) {
        if (nativeSurface instanceof EGLSurface) {
            return (EGLSurface)nativeSurface;
        }
        return new EGLSurface(nativeSurface);
    }
    
    private EGLSurface(final NativeSurface nativeSurface) {
        super(nativeSurface.getGraphicsConfiguration(), 0L, new EGLUpstreamSurfaceHook(nativeSurface), false);
        if (EGLDrawableFactory.DEBUG) {
            System.err.println("EGLSurface.ctor().1: " + this);
            ProxySurfaceImpl.dumpHierarchy(System.err, this);
        }
    }
    
    public static EGLSurface createWrapped(final EGLGraphicsConfiguration eglGraphicsConfiguration, final long n, final UpstreamSurfaceHook upstreamSurfaceHook, final boolean b) {
        return new EGLSurface(eglGraphicsConfiguration, n, upstreamSurfaceHook, b);
    }
    
    private EGLSurface(final EGLGraphicsConfiguration eglGraphicsConfiguration, final long n, final UpstreamSurfaceHook upstreamSurfaceHook, final boolean b) {
        super(eglGraphicsConfiguration, 0L, new EGLUpstreamSurfaceHook(eglGraphicsConfiguration, n, upstreamSurfaceHook, b), false);
        if (EGLDrawableFactory.DEBUG) {
            System.err.println("EGLSurface.ctor().2: " + this);
            ProxySurfaceImpl.dumpHierarchy(System.err, this);
        }
    }
    
    public static EGLSurface createSurfaceless(final EGLGraphicsConfiguration eglGraphicsConfiguration, final GenericUpstreamSurfacelessHook genericUpstreamSurfacelessHook, final boolean b) {
        return new EGLSurface(eglGraphicsConfiguration, genericUpstreamSurfacelessHook, b);
    }
    
    private EGLSurface(final EGLGraphicsConfiguration eglGraphicsConfiguration, final GenericUpstreamSurfacelessHook genericUpstreamSurfacelessHook, final boolean b) {
        super(eglGraphicsConfiguration, 0L, genericUpstreamSurfacelessHook, b);
        if (EGLDrawableFactory.DEBUG) {
            System.err.println("EGLSurface.ctor().3: " + this);
            ProxySurfaceImpl.dumpHierarchy(System.err, this);
        }
    }
    
    public void setEGLSurfaceHandle() throws GLException {
        this.setSurfaceHandle(this.createEGLSurfaceHandle());
    }
    
    private long createEGLSurfaceHandle() throws GLException {
        final EGLGraphicsConfiguration eglGraphicsConfiguration = (EGLGraphicsConfiguration)this.getGraphicsConfiguration();
        final NativeSurface upstreamSurface = this.getUpstreamSurface();
        final boolean pBuffer = ((GLCapabilitiesImmutable)eglGraphicsConfiguration.getChosenCapabilities()).isPBuffer();
        long n = this.createEGLSurfaceHandle(pBuffer, true, eglGraphicsConfiguration, upstreamSurface);
        if (EGLSurface.DEBUG) {
            System.err.println(getThreadName() + ": EGLSurface: EGL.eglCreateSurface.0: 0x" + Long.toHexString(n));
            ProxySurfaceImpl.dumpHierarchy(System.err, this);
        }
        if (0L == n) {
            final int eglGetError = EGL.eglGetError();
            if (12299 != eglGetError || pBuffer) {
                throw new GLException("Creation of window surface w/ surface handle failed (1): " + eglGraphicsConfiguration + ", " + this + ", error " + GLDrawableImpl.toHexString(eglGetError));
            }
            if (!hasUniqueNativeWindowHandle(upstreamSurface)) {
                throw new GLException("Creation of window surface w/ surface handle failed (2): " + eglGraphicsConfiguration + ", " + this + ", error " + GLDrawableImpl.toHexString(eglGetError));
            }
            n = this.createEGLSurfaceHandle(pBuffer, false, eglGraphicsConfiguration, upstreamSurface);
            if (EGLSurface.DEBUG) {
                System.err.println(getThreadName() + ": Info: Creation of window surface w/ surface handle failed: " + eglGraphicsConfiguration + ", error " + GLDrawableImpl.toHexString(eglGetError) + ", retry w/ windowHandle");
                System.err.println(getThreadName() + ": EGLSurface: EGL.eglCreateSurface.1: 0x" + Long.toHexString(n));
            }
            if (0L == n) {
                throw new GLException("Creation of window surface w/ window handle failed: " + eglGraphicsConfiguration + ", " + this + ", error " + GLDrawableImpl.toHexString(EGL.eglGetError()));
            }
        }
        if (EGLSurface.DEBUG) {
            System.err.println(getThreadName() + ": createEGLSurface handle " + GLDrawableImpl.toHexString(n));
        }
        return n;
    }
    
    private long createEGLSurfaceHandle(final boolean b, final boolean b2, final EGLGraphicsConfiguration eglGraphicsConfiguration, final NativeSurface nativeSurface) {
        if (b) {
            return EGLDrawableFactory.createPBufferSurfaceImpl(eglGraphicsConfiguration, this.getSurfaceWidth(), this.getSurfaceHeight(), false);
        }
        if (b2) {
            return EGL.eglCreateWindowSurface(eglGraphicsConfiguration.getScreen().getDevice().getHandle(), eglGraphicsConfiguration.getNativeConfig(), nativeSurface.getSurfaceHandle(), null);
        }
        return EGL.eglCreateWindowSurface(eglGraphicsConfiguration.getScreen().getDevice().getHandle(), eglGraphicsConfiguration.getNativeConfig(), ((NativeWindow)nativeSurface).getWindowHandle(), null);
    }
    
    private static boolean hasUniqueNativeWindowHandle(final NativeSurface nativeSurface) {
        return nativeSurface instanceof NativeWindow && ((NativeWindow)nativeSurface).getWindowHandle() != nativeSurface.getSurfaceHandle();
    }
    
    static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    public static boolean isValidEGLSurfaceHandle(final long n, final long n2) {
        if (0L == n2) {
            return false;
        }
        final boolean eglQuerySurface = EGL.eglQuerySurface(n, n2, 12328, Buffers.newDirectIntBuffer(1));
        if (!eglQuerySurface) {
            final int eglGetError = EGL.eglGetError();
            if (EGLSurface.DEBUG) {
                System.err.println(getThreadName() + ": EGLSurface.isValidEGLSurfaceHandle eglQuerySuface failed: error " + GLDrawableImpl.toHexString(eglGetError) + ", " + GLDrawableImpl.toHexString(n2));
            }
        }
        return eglQuerySurface;
    }
    
    static {
        EGLSurface.DEBUG = (EGLDrawable.DEBUG || ProxySurface.DEBUG);
    }
}
