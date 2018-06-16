// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import com.jogamp.common.nio.PointerBuffer;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.nativewindow.DefaultGraphicsConfiguration;
import java.lang.ref.WeakReference;
import com.jogamp.nativewindow.MutableSurface;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class MacOSXPbufferCGLDrawable extends MacOSXCGLDrawable
{
    protected GLBackendImpl impl;
    protected int pBufferTexTarget;
    protected int pBufferTexWidth;
    protected int pBufferTexHeight;
    
    public MacOSXPbufferCGLDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        super(glDrawableFactory, nativeSurface, false);
    }
    
    @Override
    protected void setRealizedImpl() {
        if (this.realized) {
            this.createPbuffer();
        }
        else {
            this.destroyPbuffer();
        }
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new MacOSXCGLContext(this, glContext);
    }
    
    protected int getTextureTarget() {
        return this.pBufferTexTarget;
    }
    
    protected int getTextureWidth() {
        return this.pBufferTexWidth;
    }
    
    protected int getTextureHeight() {
        return this.pBufferTexHeight;
    }
    
    protected void destroyPbuffer() {
        final MutableSurface mutableSurface = (MutableSurface)this.getNativeSurface();
        final long surfaceHandle = mutableSurface.getSurfaceHandle();
        if (0L != surfaceHandle) {
            synchronized (this.createdContexts) {
                int i = 0;
                while (i < this.createdContexts.size()) {
                    final MacOSXCGLContext macOSXCGLContext = this.createdContexts.get(i).get();
                    if (macOSXCGLContext != null) {
                        macOSXCGLContext.detachPBuffer();
                        ++i;
                    }
                    else {
                        this.createdContexts.remove(i);
                    }
                }
            }
            this.impl.destroy(surfaceHandle);
            mutableSurface.setSurfaceHandle(0L);
        }
    }
    
    private void createPbuffer() {
        final MutableSurface mutableSurface = (MutableSurface)this.getNativeSurface();
        final DefaultGraphicsConfiguration defaultGraphicsConfiguration = (DefaultGraphicsConfiguration)mutableSurface.getGraphicsConfiguration();
        final MacOSXCGLDrawableFactory.SharedResource orCreateSharedResourceImpl = ((MacOSXCGLDrawableFactory)this.factory).getOrCreateSharedResourceImpl(defaultGraphicsConfiguration.getScreen().getDevice());
        if (MacOSXPbufferCGLDrawable.DEBUG) {
            System.out.println(GLDrawableImpl.getThreadName() + ": Pbuffer config: " + defaultGraphicsConfiguration);
            if (null != orCreateSharedResourceImpl) {
                System.out.println("Pbuffer NPOT Texure  avail: " + orCreateSharedResourceImpl.isNPOTTextureAvailable());
                System.out.println("Pbuffer RECT Texture avail: " + orCreateSharedResourceImpl.isRECTTextureAvailable());
            }
            else {
                System.out.println("Pbuffer no sr, no RECT/NPOT Texture avail");
            }
        }
        this.pBufferTexTarget = 3553;
        if (null != orCreateSharedResourceImpl && orCreateSharedResourceImpl.isNPOTTextureAvailable()) {
            this.pBufferTexWidth = this.getSurfaceWidth();
            this.pBufferTexHeight = this.getSurfaceHeight();
        }
        else {
            this.pBufferTexWidth = GLBuffers.getNextPowerOf2(this.getSurfaceWidth());
            this.pBufferTexHeight = GLBuffers.getNextPowerOf2(this.getSurfaceHeight());
        }
        final long create = this.impl.create(this.pBufferTexTarget, 6408, this.getSurfaceWidth(), this.getSurfaceHeight());
        if (MacOSXPbufferCGLDrawable.DEBUG) {
            System.err.println("MacOSXPbufferCGLDrawable tex: target " + GLDrawableImpl.toHexString(this.pBufferTexTarget) + ", pbufferSize " + this.getSurfaceWidth() + "x" + this.getSurfaceHeight() + ", texSize " + this.pBufferTexWidth + "x" + this.pBufferTexHeight + ", internal-fmt " + GLDrawableImpl.toHexString(6408L));
            System.err.println("MacOSXPbufferCGLDrawable pBuffer: " + GLDrawableImpl.toHexString(create));
        }
        if (create == 0L) {
            throw new GLException("pbuffer creation error: CGL.createPBuffer() failed");
        }
        mutableSurface.setSurfaceHandle(create);
    }
    
    @Override
    public void setOpenGLMode(final GLBackendType openGLMode) {
        super.setOpenGLMode(openGLMode);
        this.createPbuffer();
    }
    
    @Override
    protected void initOpenGLImpl(final GLBackendType glBackendType) {
        switch (glBackendType) {
            case NSOPENGL: {
                this.impl = new NSOpenGLImpl();
                break;
            }
            case CGL: {
                this.impl = new CGLImpl();
                break;
            }
            default: {
                throw new InternalError("Illegal implementation mode " + glBackendType);
            }
        }
    }
    
    static class NSOpenGLImpl implements GLBackendImpl
    {
        @Override
        public long create(final int n, final int n2, final int n3, final int n4) {
            return CGL.createPBuffer(n, n2, n3, n4);
        }
        
        @Override
        public void destroy(final long n) {
            CGL.destroyPBuffer(n);
        }
    }
    
    static class CGLImpl implements GLBackendImpl
    {
        @Override
        public long create(final int n, final int n2, final int n3, final int n4) {
            final PointerBuffer allocateDirect = PointerBuffer.allocateDirect(1);
            final int cglCreatePBuffer = CGL.CGLCreatePBuffer(n3, n4, n, n2, 0, allocateDirect);
            if (cglCreatePBuffer != 0) {
                throw new GLException("Error creating CGL-based pbuffer: error code " + cglCreatePBuffer);
            }
            return allocateDirect.get(0);
        }
        
        @Override
        public void destroy(final long n) {
            final int cglDestroyPBuffer = CGL.CGLDestroyPBuffer(n);
            if (cglDestroyPBuffer != 0) {
                throw new GLException("Error destroying CGL-based pbuffer: error code " + cglDestroyPBuffer);
            }
        }
    }
    
    interface GLBackendImpl
    {
        long create(final int p0, final int p1, final int p2, final int p3);
        
        void destroy(final long p0);
    }
}
