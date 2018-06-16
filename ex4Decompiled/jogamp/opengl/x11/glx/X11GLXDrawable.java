// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLDrawableImpl;

public abstract class X11GLXDrawable extends GLDrawableImpl
{
    protected X11GLXDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface, final boolean b) {
        super(glDrawableFactory, nativeSurface, b);
    }
    
    @Override
    protected void setRealizedImpl() {
        if (this.realized) {
            final X11GLXGraphicsConfiguration x11GLXGraphicsConfiguration = (X11GLXGraphicsConfiguration)this.getNativeSurface().getGraphicsConfiguration();
            x11GLXGraphicsConfiguration.updateGraphicsConfiguration();
            if (X11GLXDrawable.DEBUG) {
                System.err.println(GLDrawableImpl.getThreadName() + ": X11GLXDrawable.setRealized(true): " + x11GLXGraphicsConfiguration);
            }
        }
    }
    
    @Override
    protected final void swapBuffersImpl(final boolean b) {
        if (b) {
            GLX.glXSwapBuffers(this.getNativeSurface().getDisplayHandle(), this.getHandle());
        }
    }
}
