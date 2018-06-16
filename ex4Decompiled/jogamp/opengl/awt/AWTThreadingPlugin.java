// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.awt;

import com.jogamp.opengl.Threading;
import com.jogamp.common.util.awt.AWTEDTExecutor;
import java.awt.Graphics;
import jogamp.opengl.GLWorkerThread;
import jogamp.opengl.ThreadingImpl;
import com.jogamp.opengl.GLException;
import java.awt.EventQueue;
import jogamp.opengl.ToolkitThreadingPlugin;

public class AWTThreadingPlugin implements ToolkitThreadingPlugin
{
    @Override
    public final boolean isToolkitThread() throws GLException {
        return EventQueue.isDispatchThread();
    }
    
    @Override
    public final boolean isOpenGLThread() throws GLException {
        switch (ThreadingImpl.getMode()) {
            case ST_AWT: {
                if (Java2D.isOGLPipelineActive() && !ThreadingImpl.isX11()) {
                    return Java2D.isQueueFlusherThread();
                }
                return EventQueue.isDispatchThread();
            }
            case ST_WORKER: {
                if (Java2D.isOGLPipelineActive()) {
                    return Java2D.isQueueFlusherThread() || (ThreadingImpl.isX11() && GLWorkerThread.isWorkerThread());
                }
                return GLWorkerThread.isWorkerThread();
            }
            default: {
                throw new InternalError("Illegal single-threading mode " + ThreadingImpl.getMode());
            }
        }
    }
    
    @Override
    public final void invokeOnOpenGLThread(final boolean b, final Runnable runnable) throws GLException {
        switch (ThreadingImpl.getMode()) {
            case ST_AWT: {
                if (b && Java2D.isOGLPipelineActive() && !ThreadingImpl.isX11()) {
                    Java2D.invokeWithOGLContextCurrent(null, runnable);
                    break;
                }
                AWTEDTExecutor.singleton.invoke(b, runnable);
                break;
            }
            case ST_WORKER: {
                ThreadingImpl.invokeOnWorkerThread(b, runnable);
                break;
            }
            case MT: {
                runnable.run();
                break;
            }
            default: {
                throw new InternalError("Illegal single-threading mode " + ThreadingImpl.getMode());
            }
        }
    }
}
