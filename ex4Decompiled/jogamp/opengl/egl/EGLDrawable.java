// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.opengl.egl.EGL;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.ProxySurface;
import jogamp.nativewindow.ProxySurfaceImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLDrawableImpl;

public class EGLDrawable extends GLDrawableImpl
{
    static boolean DEBUG;
    
    protected EGLDrawable(final EGLDrawableFactory eglDrawableFactory, final EGLSurface eglSurface) throws GLException {
        super(eglDrawableFactory, eglSurface, false);
    }
    
    @Override
    public final GLContext createContext(final GLContext glContext) {
        return new EGLContext(this, glContext);
    }
    
    @Override
    protected final void createHandle() {
        final EGLSurface eglSurface = (EGLSurface)this.surface;
        if (EGLDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": createHandle of " + eglSurface);
            ProxySurfaceImpl.dumpHierarchy(System.err, eglSurface);
        }
        if (eglSurface.containsUpstreamOptionBits(64)) {
            if (0L != eglSurface.getSurfaceHandle()) {
                throw new InternalError("Set surface but claimed to be invalid: " + eglSurface);
            }
            if (!eglSurface.containsUpstreamOptionBits(512)) {
                eglSurface.setEGLSurfaceHandle();
            }
        }
        else if (0L == eglSurface.getSurfaceHandle()) {
            throw new InternalError("Nil surface but claimed to be valid: " + eglSurface);
        }
    }
    
    @Override
    protected void destroyHandle() {
        final EGLSurface eglSurface = (EGLSurface)this.surface;
        final long surfaceHandle = eglSurface.getSurfaceHandle();
        if (EGLDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": EGLDrawable: destroyHandle of " + GLDrawableImpl.toHexString(surfaceHandle));
            ProxySurfaceImpl.dumpHierarchy(System.err, eglSurface);
            System.err.println(GLDrawableImpl.getThreadName() + ": EGLSurface         : " + eglSurface);
            ExceptionUtils.dumpStack(System.err);
        }
        if (!eglSurface.containsUpstreamOptionBits(512) && 0L == surfaceHandle) {
            throw new InternalError("Nil surface but claimed to be valid: " + eglSurface);
        }
        final EGLGraphicsDevice eglGraphicsDevice = (EGLGraphicsDevice)eglSurface.getGraphicsConfiguration().getScreen().getDevice();
        if (eglSurface.containsUpstreamOptionBits(64) && 0L != surfaceHandle) {
            EGL.eglDestroySurface(eglGraphicsDevice.getHandle(), surfaceHandle);
            eglSurface.setSurfaceHandle(0L);
        }
    }
    
    @Override
    protected final void setRealizedImpl() {
        if (EGLDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": EGLDrawable.setRealized(" + this.realized + "): NOP - " + this.surface);
        }
    }
    
    @Override
    protected final void swapBuffersImpl(final boolean b) {
        if (b && !EGL.eglSwapBuffers(((EGLGraphicsDevice)this.surface.getGraphicsConfiguration().getScreen().getDevice()).getHandle(), this.surface.getSurfaceHandle())) {
            throw new GLException("Error swapping buffers, eglError " + GLDrawableImpl.toHexString(EGL.eglGetError()) + ", " + this);
        }
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[realized " + this.isRealized() + ",\n\tfactory    " + this.getFactory() + ",\n\tsurface    " + this.getNativeSurface() + ",\n\teglSurface " + GLDrawableImpl.toHexString(this.surface.getSurfaceHandle()) + ",\n\teglConfig  " + this.surface.getGraphicsConfiguration() + ",\n\trequested  " + this.getRequestedGLCapabilities() + ",\n\tchosen     " + this.getChosenGLCapabilities() + "]";
    }
    
    static {
        EGLDrawable.DEBUG = GLDrawableImpl.DEBUG;
    }
}
