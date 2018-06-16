// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.security.AccessController;
import com.jogamp.common.JogampRuntimeException;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.opengl.GLProfile;
import com.jogamp.common.util.PropertyAccess;
import java.security.PrivilegedAction;
import java.lang.reflect.InvocationTargetException;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.Threading;

public class ThreadingImpl
{
    protected static final boolean DEBUG;
    private static boolean singleThreaded;
    private static Threading.Mode mode;
    private static boolean hasAWT;
    private static boolean _isX11;
    private static final ToolkitThreadingPlugin threadingPlugin;
    
    public static boolean isX11() {
        return ThreadingImpl._isX11;
    }
    
    public static Threading.Mode getMode() {
        return ThreadingImpl.mode;
    }
    
    public static final void disableSingleThreading() {
        if (Threading.Mode.MT != ThreadingImpl.mode) {
            ThreadingImpl.singleThreaded = false;
            if (Debug.verbose()) {
                System.err.println("Application forced disabling of single-threading of com.jogamp.opengl implementation");
            }
        }
    }
    
    public static final boolean isSingleThreaded() {
        return ThreadingImpl.singleThreaded;
    }
    
    public static final boolean isOpenGLThread() throws GLException {
        if (Threading.Mode.MT == ThreadingImpl.mode || !ThreadingImpl.singleThreaded) {
            return true;
        }
        if (null != ThreadingImpl.threadingPlugin) {
            return ThreadingImpl.threadingPlugin.isOpenGLThread();
        }
        switch (ThreadingImpl.mode) {
            case ST_AWT: {
                throw new InternalError();
            }
            case ST_WORKER: {
                return GLWorkerThread.isWorkerThread();
            }
            default: {
                throw new InternalError("Illegal single-threading mode " + ThreadingImpl.mode);
            }
        }
    }
    
    public static final boolean isToolkitThread() throws GLException {
        return null != ThreadingImpl.threadingPlugin && ThreadingImpl.threadingPlugin.isToolkitThread();
    }
    
    public static final void invokeOnOpenGLThread(final boolean b, final Runnable runnable) throws GLException {
        if (null != ThreadingImpl.threadingPlugin) {
            ThreadingImpl.threadingPlugin.invokeOnOpenGLThread(b, runnable);
            return;
        }
        switch (ThreadingImpl.mode) {
            case ST_WORKER: {
                invokeOnWorkerThread(b, runnable);
                break;
            }
            case MT: {
                runnable.run();
                break;
            }
            default: {
                throw new InternalError("Illegal single-threading mode " + ThreadingImpl.mode);
            }
        }
    }
    
    public static final void invokeOnWorkerThread(final boolean b, final Runnable runnable) throws GLException {
        GLWorkerThread.start();
        try {
            GLWorkerThread.invoke(b, runnable);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (InterruptedException ex2) {
            throw new GLException(ex2);
        }
    }
    
    static {
        DEBUG = Debug.debug("Threading");
        threadingPlugin = AccessController.doPrivileged((PrivilegedAction<ToolkitThreadingPlugin>)new PrivilegedAction<ToolkitThreadingPlugin>() {
            @Override
            public ToolkitThreadingPlugin run() {
                final String property = PropertyAccess.getProperty("jogl.1thread", true);
                final String s = (null != property) ? property.toLowerCase() : null;
                final ClassLoader classLoader = ThreadingImpl.class.getClassLoader();
                ThreadingImpl.hasAWT = GLProfile.isAWTAvailable();
                ThreadingImpl._isX11 = (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(false));
                if (s != null) {
                    if (s.equals("true") || s.equals("auto")) {
                        ThreadingImpl.mode = (ThreadingImpl.hasAWT ? Threading.Mode.ST_AWT : Threading.Mode.MT);
                    }
                    else if (s.equals("worker")) {
                        ThreadingImpl.mode = Threading.Mode.ST_WORKER;
                    }
                    else if (ThreadingImpl.hasAWT && s.equals("awt")) {
                        ThreadingImpl.mode = Threading.Mode.ST_AWT;
                    }
                    else {
                        if (!s.equals("false")) {
                            throw new RuntimeException("Unsupported value for property jogl.1thread: " + s + ", should be [true/auto, worker, awt or false]");
                        }
                        ThreadingImpl.mode = Threading.Mode.MT;
                    }
                }
                else {
                    ThreadingImpl.mode = (ThreadingImpl.hasAWT ? Threading.Mode.ST_AWT : Threading.Mode.MT);
                }
                ThreadingImpl.singleThreaded = (Threading.Mode.MT != ThreadingImpl.mode);
                Object o = null;
                if (ThreadingImpl.hasAWT) {
                    Throwable t = null;
                    try {
                        o = ReflectionUtil.createInstance("jogamp.opengl.awt.AWTThreadingPlugin", classLoader);
                    }
                    catch (JogampRuntimeException ex) {
                        t = ex;
                    }
                    if (Threading.Mode.ST_AWT == ThreadingImpl.mode && null == o) {
                        throw new GLException("Mode is AWT, but class 'jogamp.opengl.awt.AWTThreadingPlugin' is not available", t);
                    }
                }
                if (ThreadingImpl.DEBUG) {
                    System.err.println("Threading: jogl.1thread " + s + ", singleThreaded " + ThreadingImpl.singleThreaded + ", hasAWT " + ThreadingImpl.hasAWT + ", mode " + ThreadingImpl.mode + ", plugin " + o);
                }
                return (ToolkitThreadingPlugin)o;
            }
        });
    }
}
