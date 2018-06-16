// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.awt;

import java.awt.GraphicsEnvironment;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.opengl.GLException;
import java.lang.reflect.Method;

public class AWTUtil
{
    private static boolean headlessMode;
    private static Method isOGLPipelineActive;
    private static Method isQueueFlusherThread;
    private static boolean j2dOk;
    private static boolean lockedToolkit;
    
    public static synchronized void lockToolkit() throws GLException {
        if (AWTUtil.lockedToolkit) {
            throw new GLException("Toolkit already locked");
        }
        AWTUtil.lockedToolkit = true;
        if (AWTUtil.headlessMode) {
            return;
        }
        if (AWTUtil.j2dOk) {
            try {
                if (!(boolean)AWTUtil.isOGLPipelineActive.invoke(null, (Object[])null) || !(boolean)AWTUtil.isQueueFlusherThread.invoke(null, (Object[])null)) {
                    NativeWindowFactory.getAWTToolkitLock().lock();
                }
            }
            catch (Exception ex) {
                AWTUtil.j2dOk = false;
            }
        }
        if (!AWTUtil.j2dOk) {
            NativeWindowFactory.getAWTToolkitLock().lock();
        }
    }
    
    public static synchronized void unlockToolkit() {
        if (AWTUtil.lockedToolkit) {
            AWTUtil.lockedToolkit = false;
            if (AWTUtil.headlessMode) {
                return;
            }
            if (AWTUtil.j2dOk) {
                try {
                    if (!(boolean)AWTUtil.isOGLPipelineActive.invoke(null, (Object[])null) || !(boolean)AWTUtil.isQueueFlusherThread.invoke(null, (Object[])null)) {
                        NativeWindowFactory.getAWTToolkitLock().unlock();
                    }
                }
                catch (Exception ex) {
                    AWTUtil.j2dOk = false;
                }
            }
            if (!AWTUtil.j2dOk) {
                NativeWindowFactory.getAWTToolkitLock().unlock();
            }
        }
    }
    
    static {
        AWTUtil.isOGLPipelineActive = null;
        AWTUtil.isQueueFlusherThread = null;
        AWTUtil.j2dOk = false;
        AWTUtil.lockedToolkit = false;
        if (!(AWTUtil.headlessMode = GraphicsEnvironment.isHeadless())) {
            try {
                final Class<?> forName = Class.forName("jogamp.opengl.awt.Java2D");
                AWTUtil.isOGLPipelineActive = forName.getMethod("isOGLPipelineActive", (Class<?>[])null);
                AWTUtil.isQueueFlusherThread = forName.getMethod("isQueueFlusherThread", (Class<?>[])null);
                AWTUtil.j2dOk = true;
            }
            catch (Exception ex) {}
        }
    }
}
