// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.opengl.GLException;
import jogamp.nativewindow.windows.GDI;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLContextShareSet;
import com.jogamp.opengl.GLContext;
import jogamp.opengl.GLDrawableImpl;

public class WindowsExternalWGLContext extends WindowsWGLContext
{
    private WindowsExternalWGLContext(final Drawable drawable, final long contextHandle, final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration) {
        super(drawable, null);
        this.contextHandle = contextHandle;
        if (WindowsExternalWGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": Created external OpenGL context " + GLContext.toHexString(contextHandle) + " for " + this);
        }
        GLContextShareSet.contextCreated(this);
        if (!this.setGLFunctionAvailability(false, 0, 0, 2, false, false)) {
            throw new InternalError("setGLFunctionAvailability !strictMatch failed");
        }
        this.getGLStateTracker().setEnabled(false);
    }
    
    protected static WindowsExternalWGLContext create(final GLDrawableFactory glDrawableFactory, final GLProfile glProfile) {
        if (WindowsExternalWGLContext.DEBUG) {
            System.err.println("WindowsExternalWGLContext 0: werr: " + GDI.GetLastError());
        }
        final long wglGetCurrentContext = WGL.wglGetCurrentContext();
        if (0L == wglGetCurrentContext) {
            throw new GLException("Error: attempted to make an external GLContext without a context current, werr " + GDI.GetLastError());
        }
        final long wglGetCurrentDC = WGL.wglGetCurrentDC();
        if (0L == wglGetCurrentDC) {
            throw new GLException("Error: attempted to make an external GLDrawable without a drawable current, werr " + GDI.GetLastError());
        }
        final AbstractGraphicsScreen default1 = DefaultGraphicsScreen.createDefault(NativeWindowFactory.TYPE_WINDOWS);
        final int getPixelFormat = WGLUtil.GetPixelFormat(wglGetCurrentDC);
        WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration;
        if (getPixelFormat == 0) {
            final int getLastError = GDI.GetLastError();
            windowsWGLGraphicsConfiguration = WindowsWGLGraphicsConfigurationFactory.createDefaultGraphicsConfiguration(new GLCapabilities(GLProfile.getDefault()), default1);
            windowsWGLGraphicsConfiguration.markExternal();
            if (WindowsExternalWGLContext.DEBUG) {
                System.err.println("WindowsExternalWGLContext invalid hdc/pfd werr " + getLastError + ", using default cfg: " + windowsWGLGraphicsConfiguration);
            }
        }
        else {
            windowsWGLGraphicsConfiguration = WindowsWGLGraphicsConfiguration.createFromExternal(glDrawableFactory, wglGetCurrentDC, getPixelFormat, glProfile, default1, true);
            if (WindowsExternalWGLContext.DEBUG) {
                System.err.println("WindowsExternalWGLContext valid hdc/pfd, retrieved cfg: " + windowsWGLGraphicsConfiguration);
            }
        }
        return new WindowsExternalWGLContext(new Drawable(glDrawableFactory, new WrappedSurface(windowsWGLGraphicsConfiguration, wglGetCurrentDC, 64, 64, true)), wglGetCurrentContext, windowsWGLGraphicsConfiguration);
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
    
    static class Drawable extends WindowsWGLDrawable
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
