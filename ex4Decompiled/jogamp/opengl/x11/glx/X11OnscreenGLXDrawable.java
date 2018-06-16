// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class X11OnscreenGLXDrawable extends X11GLXDrawable
{
    public static final boolean USE_GLXWINDOW = false;
    long glXWindow;
    boolean useGLXWindow;
    
    protected X11OnscreenGLXDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface, final boolean b) {
        super(glDrawableFactory, nativeSurface, b);
        this.glXWindow = 0L;
        this.useGLXWindow = false;
        if (b) {
            this.createHandle();
        }
    }
    
    @Override
    public long getHandle() {
        return super.getHandle();
    }
    
    @Override
    protected void destroyHandle() {
    }
    
    @Override
    protected final void createHandle() {
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new X11GLXContext(this, glContext);
    }
}
