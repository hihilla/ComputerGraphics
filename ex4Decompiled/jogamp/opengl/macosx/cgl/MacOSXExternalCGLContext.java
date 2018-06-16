// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLContextShareSet;
import com.jogamp.opengl.GLContext;
import jogamp.opengl.GLDrawableImpl;

public class MacOSXExternalCGLContext extends MacOSXCGLContext
{
    private MacOSXExternalCGLContext(final Drawable drawable, final boolean b, final long contextHandle) {
        super(drawable, null);
        this.setOpenGLMode(b ? MacOSXCGLDrawable.GLBackendType.NSOPENGL : MacOSXCGLDrawable.GLBackendType.CGL);
        this.contextHandle = contextHandle;
        GLContextShareSet.contextCreated(this);
        if (!this.setGLFunctionAvailability(false, 0, 0, 2, false, false)) {
            throw new InternalError("setGLFunctionAvailability !strictMatch failed");
        }
        this.getGLStateTracker().setEnabled(false);
    }
    
    protected static MacOSXExternalCGLContext create(final GLDrawableFactory glDrawableFactory) {
        long nsView = 0L;
        long n = CGL.getCurrentContext();
        final boolean b = 0L != n;
        long n2;
        if (b) {
            final long cglContext = CGL.getCGLContext(n);
            if (cglContext == 0L) {
                throw new GLException("Error: NULL Context (CGL) of Context (NS) 0x" + Long.toHexString(n));
            }
            n2 = CGL.CGLGetPixelFormat(cglContext);
            nsView = CGL.getNSView(n);
            if (MacOSXExternalCGLContext.DEBUG) {
                System.err.println("MacOSXExternalCGLContext Create Context (NS) 0x" + Long.toHexString(n) + ", Context (CGL) 0x" + Long.toHexString(cglContext) + ", pixelFormat 0x" + Long.toHexString(n2) + ", drawable 0x" + Long.toHexString(nsView));
            }
        }
        else {
            n = CGL.CGLGetCurrentContext();
            if (n == 0L) {
                throw new GLException("Error: current Context (CGL) null, no Context (NS)");
            }
            n2 = CGL.CGLGetPixelFormat(n);
            if (MacOSXExternalCGLContext.DEBUG) {
                System.err.println("MacOSXExternalCGLContext Create Context (CGL) 0x" + Long.toHexString(n) + ", pixelFormat 0x" + Long.toHexString(n2));
            }
        }
        if (0L == n2) {
            throw new GLException("Error: current pixelformat of current Context 0x" + Long.toHexString(n) + " is null");
        }
        final GLCapabilities cglPixelFormat2GLCapabilities = MacOSXCGLGraphicsConfiguration.CGLPixelFormat2GLCapabilities(n2);
        if (MacOSXExternalCGLContext.DEBUG) {
            System.err.println("MacOSXExternalCGLContext Create " + cglPixelFormat2GLCapabilities);
        }
        final MacOSXCGLGraphicsConfiguration macOSXCGLGraphicsConfiguration = new MacOSXCGLGraphicsConfiguration(DefaultGraphicsScreen.createDefault(NativeWindowFactory.TYPE_MACOSX), cglPixelFormat2GLCapabilities, cglPixelFormat2GLCapabilities);
        if (0L == nsView) {
            nsView = 1L;
        }
        return new MacOSXExternalCGLContext(new Drawable(glDrawableFactory, new WrappedSurface(macOSXCGLGraphicsConfiguration, nsView, 64, 64, true)), b, n);
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
    
    static class Drawable extends MacOSXCGLDrawable
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
