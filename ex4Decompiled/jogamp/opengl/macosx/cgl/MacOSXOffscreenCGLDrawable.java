// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;

public class MacOSXOffscreenCGLDrawable extends MacOSXPbufferCGLDrawable
{
    public MacOSXOffscreenCGLDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface) {
        super(glDrawableFactory, nativeSurface);
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        return new MacOSXCGLContext(this, glContext);
    }
}