// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import java.util.ArrayList;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;
import java.lang.ref.WeakReference;
import java.util.List;
import jogamp.opengl.GLDrawableImpl;

public abstract class MacOSXCGLDrawable extends GLDrawableImpl
{
    List<WeakReference<MacOSXCGLContext>> createdContexts;
    private boolean haveSetOpenGLMode;
    private GLBackendType openGLMode;
    
    public MacOSXCGLDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface, final boolean b) {
        super(glDrawableFactory, nativeSurface, b);
        this.createdContexts = new ArrayList<WeakReference<MacOSXCGLContext>>();
        this.haveSetOpenGLMode = false;
        this.openGLMode = GLBackendType.NSOPENGL;
        this.initOpenGLImpl(this.getOpenGLMode());
    }
    
    @Override
    protected void setRealizedImpl() {
    }
    
    @Override
    protected void associateContext(final GLContext glContext, final boolean b) {
        synchronized (this.createdContexts) {
            if (b) {
                this.createdContexts.add(new WeakReference<MacOSXCGLContext>((MacOSXCGLContext)glContext));
            }
            else {
                int i = 0;
                while (i < this.createdContexts.size()) {
                    final MacOSXCGLContext macOSXCGLContext = this.createdContexts.get(i).get();
                    if (macOSXCGLContext == null || macOSXCGLContext == glContext) {
                        this.createdContexts.remove(i);
                    }
                    else {
                        ++i;
                    }
                }
            }
        }
    }
    
    @Override
    protected final void swapBuffersImpl(final boolean b) {
        if (b) {
            synchronized (this.createdContexts) {
                int i = 0;
                while (i < this.createdContexts.size()) {
                    final MacOSXCGLContext macOSXCGLContext = this.createdContexts.get(i).get();
                    if (macOSXCGLContext != null) {
                        macOSXCGLContext.swapBuffers();
                        ++i;
                    }
                    else {
                        this.createdContexts.remove(i);
                    }
                }
            }
        }
    }
    
    public void setOpenGLMode(final GLBackendType openGLMode) {
        if (openGLMode == this.openGLMode) {
            return;
        }
        if (this.haveSetOpenGLMode) {
            throw new GLException("Can't switch between using NSOpenGLPixelBuffer and CGLPBufferObj more than once");
        }
        this.setRealized(false);
        if (MacOSXCGLDrawable.DEBUG) {
            System.err.println("MacOSXCGLDrawable: Switching context mode " + this.openGLMode + " -> " + openGLMode);
        }
        this.initOpenGLImpl(openGLMode);
        this.openGLMode = openGLMode;
        this.haveSetOpenGLMode = true;
    }
    
    public final GLBackendType getOpenGLMode() {
        return this.openGLMode;
    }
    
    protected void initOpenGLImpl(final GLBackendType glBackendType) {
    }
    
    public enum GLBackendType
    {
        NSOPENGL(0), 
        CGL(1);
        
        public final int id;
        
        private GLBackendType(final int id) {
            this.id = id;
        }
    }
}
