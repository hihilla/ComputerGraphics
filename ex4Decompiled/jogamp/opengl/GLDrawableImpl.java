// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.GL;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLDrawable;

public abstract class GLDrawableImpl implements GLDrawable
{
    protected static final boolean DEBUG;
    protected final GLDrawableFactory factory;
    protected final NativeSurface surface;
    protected final GLCapabilitiesImmutable requestedCapabilities;
    protected volatile boolean realized;
    
    protected GLDrawableImpl(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface, final boolean b) {
        this(glDrawableFactory, nativeSurface, (GLCapabilitiesImmutable)nativeSurface.getGraphicsConfiguration().getRequestedCapabilities(), b);
    }
    
    protected GLDrawableImpl(final GLDrawableFactory factory, final NativeSurface surface, final GLCapabilitiesImmutable requestedCapabilities, final boolean realized) {
        this.factory = factory;
        this.surface = surface;
        this.realized = realized;
        this.requestedCapabilities = requestedCapabilities;
    }
    
    public final GLDrawableFactoryImpl getFactoryImpl() {
        return (GLDrawableFactoryImpl)this.getFactory();
    }
    
    @Override
    public final void swapBuffers() throws GLException {
        if (!this.realized) {
            return;
        }
        if (1 == this.lockSurface()) {
            return;
        }
        try {
            if (this.realized) {
                if (((GLCapabilitiesImmutable)this.surface.getGraphicsConfiguration().getChosenCapabilities()).getDoubleBuffered()) {
                    if (!this.surface.surfaceSwap()) {
                        this.swapBuffersImpl(true);
                    }
                }
                else {
                    final GLContext current = GLContext.getCurrent();
                    if (null != current && current.getGLDrawable() == this) {
                        current.getGL().glFlush();
                    }
                    this.swapBuffersImpl(false);
                }
            }
        }
        finally {
            this.unlockSurface();
        }
        this.surface.surfaceUpdated(this, this.surface, System.currentTimeMillis());
    }
    
    protected abstract void swapBuffersImpl(final boolean p0);
    
    public static final String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    @Override
    public final GLProfile getGLProfile() {
        return this.requestedCapabilities.getGLProfile();
    }
    
    @Override
    public final GLCapabilitiesImmutable getChosenGLCapabilities() {
        return (GLCapabilitiesImmutable)this.surface.getGraphicsConfiguration().getChosenCapabilities();
    }
    
    @Override
    public final GLCapabilitiesImmutable getRequestedGLCapabilities() {
        return this.requestedCapabilities;
    }
    
    @Override
    public NativeSurface getNativeSurface() {
        return this.surface;
    }
    
    protected void destroyHandle() {
    }
    
    protected void createHandle() {
    }
    
    @Override
    public long getHandle() {
        return this.surface.getSurfaceHandle();
    }
    
    @Override
    public final GLDrawableFactory getFactory() {
        return this.factory;
    }
    
    @Override
    public final void setRealized(final boolean realized) {
        if (this.realized != realized) {
            final boolean b = this.surface instanceof ProxySurface;
            if (GLDrawableImpl.DEBUG) {
                System.err.println(getThreadName() + ": setRealized: drawable " + this.getClass().getSimpleName() + ", surface " + this.surface.getClass().getSimpleName() + ", isProxySurface " + b + ": " + this.realized + " -> " + realized);
                ExceptionUtils.dumpStack(System.err);
            }
            final AbstractGraphicsDevice device = this.surface.getGraphicsConfiguration().getScreen().getDevice();
            if (realized) {
                if (b) {
                    ((ProxySurface)this.surface).createNotify();
                }
                if (1 >= this.surface.lockSurface()) {
                    throw new GLException("GLDrawableImpl.setRealized(true): Surface not ready (lockSurface)");
                }
            }
            else {
                device.lock();
            }
            try {
                if (this.realized != realized) {
                    this.realized = realized;
                    if (realized) {
                        this.setRealizedImpl();
                        this.createHandle();
                    }
                    else {
                        this.destroyHandle();
                        this.setRealizedImpl();
                    }
                }
            }
            finally {
                if (realized) {
                    this.surface.unlockSurface();
                }
                else {
                    device.unlock();
                    if (b) {
                        ((ProxySurface)this.surface).destroyNotify();
                    }
                }
            }
        }
        else if (GLDrawableImpl.DEBUG) {
            System.err.println(getThreadName() + ": setRealized: " + this.getClass().getName() + " " + this.realized + " == " + realized);
        }
    }
    
    protected abstract void setRealizedImpl();
    
    protected void associateContext(final GLContext glContext, final boolean b) {
    }
    
    protected void contextMadeCurrent(final GLContext glContext, final boolean b) {
    }
    
    protected int getDefaultDrawFramebuffer() {
        return 0;
    }
    
    protected int getDefaultReadFramebuffer() {
        return 0;
    }
    
    protected int getDefaultReadBuffer(final GL gl, final boolean b) {
        if (gl.isGLES() || b || this.getChosenGLCapabilities().getDoubleBuffered()) {
            return 1029;
        }
        return 1028;
    }
    
    @Override
    public final boolean isRealized() {
        return this.realized;
    }
    
    @Override
    public int getSurfaceWidth() {
        return this.surface.getSurfaceWidth();
    }
    
    @Override
    public int getSurfaceHeight() {
        return this.surface.getSurfaceHeight();
    }
    
    @Override
    public boolean isGLOriented() {
        return true;
    }
    
    public final int lockSurface() throws GLException {
        final int lockSurface = this.surface.lockSurface();
        if (2 == lockSurface && this.realized) {
            final long handle = this.getHandle();
            this.destroyHandle();
            this.createHandle();
            final long handle2 = this.getHandle();
            if (GLDrawableImpl.DEBUG && handle != handle2) {
                System.err.println(getThreadName() + ": Drawable handle changed: " + toHexString(handle) + " -> " + toHexString(handle2));
            }
        }
        return lockSurface;
    }
    
    public final void unlockSurface() {
        this.surface.unlockSurface();
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[Realized " + this.isRealized() + ",\n\tFactory   " + this.getFactory() + ",\n\tHandle    " + toHexString(this.getHandle()) + ",\n\tSurface   " + this.getNativeSurface() + "]";
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    static {
        DEBUG = GLDrawableFactoryImpl.DEBUG;
    }
}
