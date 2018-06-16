// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.opengl.GLException;
import jogamp.nativewindow.windows.GDI;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class WindowsExternalWGLDrawable extends WindowsWGLDrawable
{
    private WindowsExternalWGLDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        super(glDrawableFactory, nativeSurface, true);
    }
    
    protected static WindowsExternalWGLDrawable create(final GLDrawableFactory glDrawableFactory, final GLProfile glProfile) {
        final long wglGetCurrentDC = WGL.wglGetCurrentDC();
        if (0L == wglGetCurrentDC) {
            throw new GLException("Error: attempted to make an external GLDrawable without a drawable current, werr " + GDI.GetLastError());
        }
        final int getPixelFormat = WGLUtil.GetPixelFormat(wglGetCurrentDC);
        if (getPixelFormat == 0) {
            throw new GLException("Error: attempted to make an external GLContext without a valid pixelformat, werr " + GDI.GetLastError());
        }
        return new WindowsExternalWGLDrawable(glDrawableFactory, new WrappedSurface(WindowsWGLGraphicsConfiguration.createFromExternal(glDrawableFactory, wglGetCurrentDC, getPixelFormat, glProfile, DefaultGraphicsScreen.createDefault(NativeWindowFactory.TYPE_WINDOWS), true), wglGetCurrentDC, 64, 64, true));
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new WindowsWGLContext(this, glContext);
    }
    
    public void setSize(final int n, final int n2) {
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
}
