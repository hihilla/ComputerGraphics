// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import java.nio.IntBuffer;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class X11ExternalGLXDrawable extends X11GLXDrawable
{
    private X11ExternalGLXDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        super(glDrawableFactory, nativeSurface, true);
    }
    
    protected static X11ExternalGLXDrawable create(final GLDrawableFactory glDrawableFactory, final GLProfile glProfile) {
        final long glXGetCurrentContext = GLX.glXGetCurrentContext();
        if (glXGetCurrentContext == 0L) {
            throw new GLException("Error: current context null");
        }
        final long glXGetCurrentDisplay = GLX.glXGetCurrentDisplay();
        if (glXGetCurrentDisplay == 0L) {
            throw new GLException("Error: current display null");
        }
        final long glXGetCurrentDrawable = GLX.glXGetCurrentDrawable();
        if (glXGetCurrentDrawable == 0L) {
            throw new GLException("Error: attempted to make an external GLDrawable without a drawable current");
        }
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        GLX.glXQueryContext(glXGetCurrentDisplay, glXGetCurrentContext, 32780, directIntBuffer);
        final X11GraphicsScreen x11GraphicsScreen = (X11GraphicsScreen)X11GraphicsScreen.createScreenDevice(glXGetCurrentDisplay, directIntBuffer.get(0), false);
        GLX.glXQueryContext(glXGetCurrentDisplay, glXGetCurrentContext, 32787, directIntBuffer);
        final X11GLXGraphicsConfiguration create = X11GLXGraphicsConfiguration.create(glProfile, x11GraphicsScreen, directIntBuffer.get(0));
        GLX.glXQueryDrawable(glXGetCurrentDisplay, glXGetCurrentDrawable, 32797, directIntBuffer);
        final int value = directIntBuffer.get(0);
        GLX.glXQueryDrawable(glXGetCurrentDisplay, glXGetCurrentDrawable, 32798, directIntBuffer);
        final int value2 = directIntBuffer.get(0);
        GLX.glXQueryContext(glXGetCurrentDisplay, glXGetCurrentContext, 32785, directIntBuffer);
        if ((directIntBuffer.get(0) & 0x8014) == 0x0 && X11ExternalGLXDrawable.DEBUG) {
            System.err.println("X11ExternalGLXDrawable: WARNING: forcing GLX_RGBA_TYPE for newly created contexts (current 0x" + Integer.toHexString(directIntBuffer.get(0)) + ")");
        }
        return new X11ExternalGLXDrawable(glDrawableFactory, new WrappedSurface(create, glXGetCurrentDrawable, value, value2, true));
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new Context(this, glContext);
    }
    
    public void setSize(final int n, final int n2) {
        throw new GLException("Should not call this");
    }
    
    static class Context extends X11GLXContext
    {
        Context(final X11GLXDrawable x11GLXDrawable, final GLContext glContext) {
            super(x11GLXDrawable, glContext);
        }
    }
}
