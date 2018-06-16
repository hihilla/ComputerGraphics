// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import com.jogamp.opengl.GLException;
import jogamp.nativewindow.windows.GDI;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.opengl.GLDrawableImpl;

public abstract class WindowsWGLDrawable extends GLDrawableImpl
{
    private static final boolean PROFILING;
    private static final int PROFILING_TICKS = 200;
    private int profilingSwapBuffersTicks;
    private long profilingSwapBuffersTime;
    
    public WindowsWGLDrawable(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface, final boolean b) {
        super(glDrawableFactory, nativeSurface, b);
    }
    
    @Override
    protected void setRealizedImpl() {
        if (this.realized) {
            final NativeSurface nativeSurface = this.getNativeSurface();
            final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration = (WindowsWGLGraphicsConfiguration)nativeSurface.getGraphicsConfiguration();
            windowsWGLGraphicsConfiguration.updateGraphicsConfiguration(this.getFactory(), nativeSurface, null);
            if (WindowsWGLDrawable.DEBUG) {
                System.err.println(GLDrawableImpl.getThreadName() + ": WindowsWGLDrawable.setRealized(true): " + windowsWGLGraphicsConfiguration);
            }
        }
    }
    
    @Override
    protected final void swapBuffersImpl(final boolean b) {
        if (b) {
            long currentTimeMillis;
            if (WindowsWGLDrawable.PROFILING) {
                currentTimeMillis = System.currentTimeMillis();
            }
            else {
                currentTimeMillis = 0L;
            }
            if (!WGLUtil.SwapBuffers(this.getHandle()) && GDI.GetLastError() != 0) {
                throw new GLException("Error swapping buffers");
            }
            if (WindowsWGLDrawable.PROFILING) {
                this.profilingSwapBuffersTime += System.currentTimeMillis() - currentTimeMillis;
                if (++this.profilingSwapBuffersTicks == 200) {
                    System.err.println("SwapBuffers calls: " + this.profilingSwapBuffersTime + " ms / " + 200 + "  calls (" + this.profilingSwapBuffersTime / 200.0f + " ms/call)");
                    this.profilingSwapBuffersTime = 0L;
                    this.profilingSwapBuffersTicks = 0;
                }
            }
        }
    }
    
    static {
        Debug.initSingleton();
        PROFILING = PropertyAccess.isPropertyDefined("jogl.debug.GLDrawable.profiling", true);
    }
}
