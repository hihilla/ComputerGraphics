// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import com.jogamp.nativewindow.AbstractGraphicsScreen;
import jogamp.nativewindow.x11.XVisualInfo;
import com.jogamp.nativewindow.MutableSurface;
import com.jogamp.opengl.GLException;
import jogamp.nativewindow.x11.X11Lib;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class X11PixmapGLXDrawable extends X11GLXDrawable
{
    private long pixmap;
    
    protected X11PixmapGLXDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        super(glDrawableFactory, nativeSurface, false);
    }
    
    @Override
    protected void setRealizedImpl() {
        if (this.realized) {
            this.createPixmap();
        }
        else {
            this.destroyPixmap();
        }
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new X11GLXContext(this, glContext);
    }
    
    private void createPixmap() {
        final NativeSurface nativeSurface = this.getNativeSurface();
        final X11GLXGraphicsConfiguration x11GLXGraphicsConfiguration = (X11GLXGraphicsConfiguration)nativeSurface.getGraphicsConfiguration();
        final XVisualInfo xVisualInfo = x11GLXGraphicsConfiguration.getXVisualInfo();
        final int depth = xVisualInfo.getDepth();
        final AbstractGraphicsScreen screen = x11GLXGraphicsConfiguration.getScreen();
        final long handle = screen.getDevice().getHandle();
        this.pixmap = X11Lib.XCreatePixmap(handle, X11Lib.RootWindow(handle, screen.getIndex()), this.surface.getSurfaceWidth(), this.surface.getSurfaceHeight(), depth);
        if (this.pixmap == 0L) {
            throw new GLException("XCreatePixmap failed");
        }
        final long glXCreateGLXPixmap = GLX.glXCreateGLXPixmap(handle, xVisualInfo, this.pixmap);
        if (glXCreateGLXPixmap == 0L) {
            X11Lib.XFreePixmap(handle, this.pixmap);
            this.pixmap = 0L;
            throw new GLException("glXCreateGLXPixmap failed");
        }
        ((MutableSurface)nativeSurface).setSurfaceHandle(glXCreateGLXPixmap);
        if (X11PixmapGLXDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": Created pixmap " + GLDrawableImpl.toHexString(this.pixmap) + ", GLXPixmap " + GLDrawableImpl.toHexString(glXCreateGLXPixmap) + ", display " + GLDrawableImpl.toHexString(handle));
        }
    }
    
    protected void destroyPixmap() {
        if (this.pixmap == 0L) {
            return;
        }
        final NativeSurface nativeSurface = this.getNativeSurface();
        final long displayHandle = nativeSurface.getDisplayHandle();
        final long surfaceHandle = nativeSurface.getSurfaceHandle();
        if (X11PixmapGLXDrawable.DEBUG) {
            System.err.println(GLDrawableImpl.getThreadName() + ": Destroying pixmap " + GLDrawableImpl.toHexString(this.pixmap) + ", GLXPixmap " + GLDrawableImpl.toHexString(surfaceHandle) + ", display " + GLDrawableImpl.toHexString(displayHandle));
        }
        if (X11PixmapGLXDrawable.DEBUG) {
            final long glXGetCurrentContext = GLX.glXGetCurrentContext();
            if (glXGetCurrentContext != 0L) {
                System.err.println("WARNING: found context " + GLDrawableImpl.toHexString(glXGetCurrentContext) + " current during pixmap destruction");
            }
        }
        GLX.glXMakeCurrent(displayHandle, 0L, 0L);
        GLX.glXDestroyGLXPixmap(displayHandle, surfaceHandle);
        X11Lib.XFreePixmap(displayHandle, this.pixmap);
        this.pixmap = 0L;
        ((MutableSurface)nativeSurface).setSurfaceHandle(0L);
    }
}
