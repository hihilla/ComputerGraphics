// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import java.nio.IntBuffer;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLContextShareSet;
import com.jogamp.opengl.GLContext;
import jogamp.opengl.GLDrawableImpl;

public class X11ExternalGLXContext extends X11GLXContext
{
    private X11ExternalGLXContext(final Drawable drawable, final long contextHandle) {
        super(drawable, null);
        this.contextHandle = contextHandle;
        GLContextShareSet.contextCreated(this);
        if (!this.setGLFunctionAvailability(false, 0, 0, 2, false, false)) {
            throw new InternalError("setGLFunctionAvailability !strictMatch failed");
        }
        this.getGLStateTracker().setEnabled(false);
    }
    
    protected static X11ExternalGLXContext create(final GLDrawableFactory glDrawableFactory, final GLProfile glProfile) {
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
            throw new GLException("Error: attempted to make an external GLDrawable without a drawable/context current");
        }
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        GLX.glXQueryDrawable(glXGetCurrentDisplay, glXGetCurrentDrawable, 32797, directIntBuffer);
        final int value = directIntBuffer.get(0);
        GLX.glXQueryDrawable(glXGetCurrentDisplay, glXGetCurrentDrawable, 32798, directIntBuffer);
        final int value2 = directIntBuffer.get(0);
        GLX.glXQueryContext(glXGetCurrentDisplay, glXGetCurrentContext, 32780, directIntBuffer);
        final X11GraphicsScreen x11GraphicsScreen = (X11GraphicsScreen)X11GraphicsScreen.createScreenDevice(glXGetCurrentDisplay, directIntBuffer.get(0), false);
        GLX.glXQueryContext(glXGetCurrentDisplay, glXGetCurrentContext, 32787, directIntBuffer);
        X11GLXGraphicsConfiguration x11GLXGraphicsConfiguration;
        if (0 == directIntBuffer.get(0) || !X11GLXGraphicsConfiguration.GLXFBConfigIDValid(glXGetCurrentDisplay, x11GraphicsScreen.getIndex(), directIntBuffer.get(0))) {
            final GLCapabilities glCapabilities = new GLCapabilities(GLProfile.getDefault());
            x11GLXGraphicsConfiguration = X11GLXGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilities, glCapabilities, null, x11GraphicsScreen, 0);
            if (X11ExternalGLXContext.DEBUG) {
                System.err.println("X11ExternalGLXContext invalid FBCONFIG_ID " + directIntBuffer.get(0) + ", using default cfg: " + x11GLXGraphicsConfiguration);
            }
        }
        else {
            x11GLXGraphicsConfiguration = X11GLXGraphicsConfiguration.create(glProfile, x11GraphicsScreen, directIntBuffer.get(0));
        }
        return new X11ExternalGLXContext(new Drawable(glDrawableFactory, new WrappedSurface(x11GLXGraphicsConfiguration, glXGetCurrentDrawable, value, value2, true)), glXGetCurrentContext);
    }
    
    @Override
    protected boolean createImpl(final long n) throws GLException {
        return true;
    }
    
    @Override
    protected void makeCurrentImpl() throws GLException {
    }
    
    @Override
    protected void releaseImpl() throws GLException {
    }
    
    @Override
    protected void destroyImpl() throws GLException {
    }
    
    static class Drawable extends X11GLXDrawable
    {
        Drawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
            super(glDrawableFactory, nativeSurface, true);
        }
        
        @Override
        public GLContext createContext(final GLContext glContext) {
            throw new GLException("Should not call this");
        }
        
        @Override
        public int getSurfaceWidth() {
            throw new GLException("Should not call this");
        }
        
        @Override
        public int getSurfaceHeight() {
            throw new GLException("Should not call this");
        }
        
        public void setSize(final int n, final int n2) {
            throw new GLException("Should not call this");
        }
    }
}
