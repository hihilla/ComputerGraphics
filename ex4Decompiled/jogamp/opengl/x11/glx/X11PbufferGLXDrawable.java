// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import java.nio.IntBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.MutableSurface;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class X11PbufferGLXDrawable extends X11GLXDrawable
{
    protected X11PbufferGLXDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
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
        return new X11GLXContext(this, glContext);
    }
    
    protected void destroyPbuffer() {
        final NativeSurface nativeSurface = this.getNativeSurface();
        if (nativeSurface.getSurfaceHandle() != 0L) {
            GLX.glXDestroyPbuffer(nativeSurface.getDisplayHandle(), nativeSurface.getSurfaceHandle());
        }
        ((MutableSurface)nativeSurface).setSurfaceHandle(0L);
        if (X11PbufferGLXDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": Destroyed pbuffer " + this);
        }
    }
    
    private void createPbuffer() {
        final MutableSurface mutableSurface = (MutableSurface)this.getNativeSurface();
        final X11GLXGraphicsConfiguration x11GLXGraphicsConfiguration = (X11GLXGraphicsConfiguration)mutableSurface.getGraphicsConfiguration();
        final long handle = x11GLXGraphicsConfiguration.getScreen().getDevice().getHandle();
        if (X11PbufferGLXDrawable.DEBUG) {
            System.out.println(GLDrawableImpl.getThreadName() + ": Pbuffer config: " + x11GLXGraphicsConfiguration);
        }
        if (handle == 0L) {
            throw new GLException("Null display");
        }
        int n = 0;
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(7);
        directIntBuffer.put(n++, 32833);
        directIntBuffer.put(n++, mutableSurface.getSurfaceWidth());
        directIntBuffer.put(n++, 32832);
        directIntBuffer.put(n++, mutableSurface.getSurfaceHeight());
        directIntBuffer.put(n++, 32796);
        directIntBuffer.put(n++, 0);
        directIntBuffer.put(n++, 0);
        final long glXCreatePbuffer = GLX.glXCreatePbuffer(handle, x11GLXGraphicsConfiguration.getFBConfig(), directIntBuffer);
        if (glXCreatePbuffer == 0L) {
            throw new GLException("pbuffer creation error: glXCreatePbuffer() failed");
        }
        mutableSurface.setSurfaceHandle(glXCreatePbuffer);
        if (X11PbufferGLXDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": Created pbuffer " + this);
        }
    }
}
