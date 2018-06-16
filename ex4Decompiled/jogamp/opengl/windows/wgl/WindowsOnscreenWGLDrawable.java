// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class WindowsOnscreenWGLDrawable extends WindowsWGLDrawable
{
    protected WindowsOnscreenWGLDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        super(glDrawableFactory, nativeSurface, false);
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new WindowsWGLContext(this, glContext);
    }
}
