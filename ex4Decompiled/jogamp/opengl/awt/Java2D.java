// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.awt;

import java.awt.HeadlessException;
import com.jogamp.common.os.Platform;
import jogamp.common.os.PlatformPropsImpl;
import jogamp.opengl.Debug;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLProfile;
import java.security.AccessController;
import java.lang.reflect.Field;
import java.security.PrivilegedAction;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import java.lang.reflect.Method;

public class Java2D
{
    private static boolean DEBUG;
    private static boolean isHeadless;
    private static boolean isOGLPipelineActive;
    private static boolean isOGLPipelineResourceCompatible;
    private static Method invokeWithOGLContextCurrentMethod;
    private static Method isQueueFlusherThreadMethod;
    private static Method getOGLViewportMethod;
    private static Method getOGLScissorBoxMethod;
    private static Method getOGLSurfaceIdentifierMethod;
    private static Method getOGLTextureTypeMethod;
    private static boolean fbObjectSupportInitialized;
    private static Method invokeWithOGLSharedContextCurrentMethod;
    private static Method getOGLSurfaceTypeMethod;
    public static final int UNDEFINED;
    public static final int WINDOW;
    public static final int PBUFFER;
    public static final int TEXTURE;
    public static final int FLIP_BACKBUFFER;
    public static final int FBOBJECT;
    private static boolean initializedJ2DFBOShareContext;
    private static GLContext j2dFBOShareContext;
    private static Method createOGLContextOnSurfaceMethod;
    private static Method makeOGLContextCurrentOnSurfaceMethod;
    private static Method destroyOGLContextMethod;
    
    public static boolean isOGLPipelineActive() {
        return Java2D.isOGLPipelineActive;
    }
    
    public static boolean isOGLPipelineResourceCompatible() {
        return Java2D.isOGLPipelineResourceCompatible;
    }
    
    public static boolean isFBOEnabled() {
        return Java2D.fbObjectSupportInitialized;
    }
    
    public static boolean isQueueFlusherThread() {
        checkActive();
        try {
            return (boolean)Java2D.isQueueFlusherThreadMethod.invoke(null, (Object[])null);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static void invokeWithOGLContextCurrent(final Graphics graphics, final Runnable runnable) throws GLException {
        checkActive();
        try {
            initFBOShareContext(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
            AWTUtil.lockToolkit();
            try {
                Java2D.invokeWithOGLContextCurrentMethod.invoke(null, graphics, runnable);
            }
            finally {
                AWTUtil.unlockToolkit();
            }
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static boolean invokeWithOGLSharedContextCurrent(final GraphicsConfiguration graphicsConfiguration, final Runnable runnable) throws GLException {
        checkCompatible();
        try {
            AWTUtil.lockToolkit();
            try {
                return (boolean)Java2D.invokeWithOGLSharedContextCurrentMethod.invoke(null, graphicsConfiguration, runnable);
            }
            finally {
                AWTUtil.unlockToolkit();
            }
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static Rectangle getOGLViewport(final Graphics graphics, final int n, final int n2) {
        checkCompatible();
        try {
            return (Rectangle)Java2D.getOGLViewportMethod.invoke(null, graphics, n, n2);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static Rectangle getOGLScissorBox(final Graphics graphics) {
        checkCompatible();
        try {
            return (Rectangle)Java2D.getOGLScissorBoxMethod.invoke(null, graphics);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static Object getOGLSurfaceIdentifier(final Graphics graphics) {
        checkCompatible();
        try {
            return Java2D.getOGLSurfaceIdentifierMethod.invoke(null, graphics);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static int getOGLSurfaceType(final Graphics graphics) {
        checkCompatible();
        try {
            if (!Java2D.fbObjectSupportInitialized) {
                return 0;
            }
            return (int)Java2D.getOGLSurfaceTypeMethod.invoke(null, graphics);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static int getOGLTextureType(final Graphics graphics) {
        checkCompatible();
        if (Java2D.getOGLTextureTypeMethod == null) {
            return 3553;
        }
        try {
            return (int)Java2D.getOGLTextureTypeMethod.invoke(null, graphics);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static GLContext filterShareContext(final GLContext glContext) {
        if (Java2D.isHeadless) {
            return glContext;
        }
        initFBOShareContext(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
        if (Java2D.j2dFBOShareContext != null) {
            return Java2D.j2dFBOShareContext;
        }
        return glContext;
    }
    
    public static GLContext getShareContext(final GraphicsDevice graphicsDevice) {
        initFBOShareContext(graphicsDevice);
        return Java2D.j2dFBOShareContext;
    }
    
    public static long createOGLContextOnSurface(final Graphics graphics, final long n) {
        checkCompatible();
        try {
            return (long)Java2D.createOGLContextOnSurfaceMethod.invoke(null, graphics, n);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static boolean makeOGLContextCurrentOnSurface(final Graphics graphics, final long n) {
        checkCompatible();
        try {
            return (boolean)Java2D.makeOGLContextCurrentOnSurfaceMethod.invoke(null, graphics, n);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    public static void destroyOGLContext(final long n) {
        checkCompatible();
        try {
            Java2D.destroyOGLContextMethod.invoke(null, n);
        }
        catch (InvocationTargetException ex) {
            throw new GLException(ex.getTargetException());
        }
        catch (Exception ex2) {
            throw (InternalError)new InternalError().initCause(ex2);
        }
    }
    
    private static void checkActive() {
        if (!isOGLPipelineActive()) {
            throw new GLException("Java2D OpenGL pipeline not active");
        }
    }
    
    private static void checkCompatible() {
        if (!isOGLPipelineResourceCompatible()) {
            throw new GLException("Java2D OpenGL pipeline not resource compatible");
        }
    }
    
    private static int getOGLUtilitiesIntField(final String s) {
        final Integer n = AccessController.doPrivileged((PrivilegedAction<Integer>)new PrivilegedAction<Integer>() {
            @Override
            public Integer run() {
                try {
                    final Field field = Class.forName("sun.java2d.opengl.OGLUtilities").getField(s);
                    field.setAccessible(true);
                    return (Integer)field.get(null);
                }
                catch (Exception ex) {
                    if (Java2D.DEBUG) {
                        ex.printStackTrace();
                    }
                    return null;
                }
            }
        });
        if (n == null) {
            return 0;
        }
        if (Java2D.DEBUG) {
            System.err.println("OGLUtilities." + s + " = " + (int)n);
        }
        return n;
    }
    
    private static void initFBOShareContext(final GraphicsDevice graphicsDevice) {
        if (isOGLPipelineResourceCompatible() && isFBOEnabled() && !Java2D.initializedJ2DFBOShareContext) {
            Java2D.initializedJ2DFBOShareContext = true;
            if (Java2D.DEBUG) {
                System.err.println("Starting initialization of J2D FBO share context");
            }
            invokeWithOGLSharedContextCurrent(graphicsDevice.getDefaultConfiguration(), new Runnable() {
                @Override
                public void run() {
                    Java2D.j2dFBOShareContext = GLDrawableFactory.getFactory(GLProfile.getDefault(GLProfile.getDefaultDevice())).createExternalGLContext();
                }
            });
            if (Java2D.DEBUG) {
                System.err.println("Ending initialization of J2D FBO share context");
            }
        }
    }
    
    static {
        Java2D.DEBUG = Debug.debug("Java2D");
        UNDEFINED = getOGLUtilitiesIntField("UNDEFINED");
        WINDOW = getOGLUtilitiesIntField("WINDOW");
        PBUFFER = getOGLUtilitiesIntField("PBUFFER");
        TEXTURE = getOGLUtilitiesIntField("TEXTURE");
        FLIP_BACKBUFFER = getOGLUtilitiesIntField("FLIP_BACKBUFFER");
        FBOBJECT = getOGLUtilitiesIntField("FBOBJECT");
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                if (Java2D.DEBUG) {
                    System.err.println("Checking for Java2D/OpenGL support");
                }
                Throwable t = null;
                try {
                    Java2D.isHeadless = true;
                    final boolean b = PlatformPropsImpl.OS_TYPE == Platform.OSType.MACOS;
                    boolean booleanValue = true;
                    final String property = System.getProperty("sun.java2d.opengl");
                    if (null != property) {
                        booleanValue = Boolean.valueOf(property);
                    }
                    final boolean b2 = !booleanValue;
                    String name;
                    if (!b2 && !b) {
                        name = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getClass().getName();
                    }
                    else {
                        if (Java2D.DEBUG) {
                            System.err.println("Java2D support disabled: by Property " + b2 + ", by OS " + b);
                        }
                        name = "nil";
                    }
                    Java2D.isHeadless = false;
                    if (Java2D.DEBUG) {
                        System.err.println("Java2D support: default GraphicsConfiguration = " + name);
                    }
                    Java2D.isOGLPipelineActive = name.startsWith("sun.java2d.opengl");
                    Java2D.isOGLPipelineResourceCompatible = Java2D.isOGLPipelineActive;
                    if (Java2D.isOGLPipelineActive) {
                        try {
                            final Class<?> forName = Class.forName("sun.java2d.opengl.OGLUtilities");
                            Java2D.invokeWithOGLContextCurrentMethod = forName.getDeclaredMethod("invokeWithOGLContextCurrent", Graphics.class, Runnable.class);
                            Java2D.invokeWithOGLContextCurrentMethod.setAccessible(true);
                            Java2D.isQueueFlusherThreadMethod = forName.getDeclaredMethod("isQueueFlusherThread", (Class<?>[])new Class[0]);
                            Java2D.isQueueFlusherThreadMethod.setAccessible(true);
                            if (Java2D.isOGLPipelineResourceCompatible) {
                                Java2D.getOGLViewportMethod = forName.getDeclaredMethod("getOGLViewport", Graphics.class, Integer.TYPE, Integer.TYPE);
                                Java2D.getOGLViewportMethod.setAccessible(true);
                                Java2D.getOGLScissorBoxMethod = forName.getDeclaredMethod("getOGLScissorBox", Graphics.class);
                                Java2D.getOGLScissorBoxMethod.setAccessible(true);
                                Java2D.getOGLSurfaceIdentifierMethod = forName.getDeclaredMethod("getOGLSurfaceIdentifier", Graphics.class);
                                Java2D.getOGLSurfaceIdentifierMethod.setAccessible(true);
                                Java2D.fbObjectSupportInitialized = true;
                                try {
                                    Java2D.invokeWithOGLSharedContextCurrentMethod = forName.getDeclaredMethod("invokeWithOGLSharedContextCurrent", GraphicsConfiguration.class, Runnable.class);
                                    Java2D.invokeWithOGLSharedContextCurrentMethod.setAccessible(true);
                                    Java2D.getOGLSurfaceTypeMethod = forName.getDeclaredMethod("getOGLSurfaceType", Graphics.class);
                                    Java2D.getOGLSurfaceTypeMethod.setAccessible(true);
                                }
                                catch (Exception ex) {
                                    Java2D.fbObjectSupportInitialized = false;
                                    if (Java2D.DEBUG) {
                                        ex.printStackTrace();
                                        System.err.println("Info: Disabling Java2D/JOGL FBO support");
                                    }
                                }
                                try {
                                    Java2D.getOGLTextureTypeMethod = forName.getDeclaredMethod("getOGLTextureType", Graphics.class);
                                    Java2D.getOGLTextureTypeMethod.setAccessible(true);
                                }
                                catch (Exception ex2) {
                                    if (Java2D.DEBUG) {
                                        ex2.printStackTrace();
                                        System.err.println("Info: GL_ARB_texture_rectangle FBO support disabled");
                                    }
                                }
                                Class<?> forName2 = null;
                                try {
                                    forName2 = Class.forName("sun.java2d.opengl.CGLSurfaceData");
                                }
                                catch (Exception ex3) {
                                    if (Java2D.DEBUG) {
                                        ex3.printStackTrace();
                                        System.err.println("Info: Unable to find class sun.java2d.opengl.CGLSurfaceData for OS X");
                                    }
                                }
                                if (forName2 != null) {
                                    Java2D.fbObjectSupportInitialized = false;
                                    Java2D.createOGLContextOnSurfaceMethod = forName2.getDeclaredMethod("createOGLContextOnSurface", Graphics.class, Long.TYPE);
                                    Java2D.createOGLContextOnSurfaceMethod.setAccessible(true);
                                    Java2D.makeOGLContextCurrentOnSurfaceMethod = forName2.getDeclaredMethod("makeOGLContextCurrentOnSurface", Graphics.class, Long.TYPE);
                                    Java2D.makeOGLContextCurrentOnSurfaceMethod.setAccessible(true);
                                    Java2D.destroyOGLContextMethod = forName2.getDeclaredMethod("destroyOGLContext", Long.TYPE);
                                    Java2D.destroyOGLContextMethod.setAccessible(true);
                                }
                            }
                        }
                        catch (Exception ex4) {
                            t = ex4;
                            if (Java2D.DEBUG) {
                                System.err.println("Info: Disabling Java2D/JOGL integration");
                            }
                            Java2D.isOGLPipelineActive = false;
                            Java2D.isOGLPipelineResourceCompatible = false;
                        }
                    }
                }
                catch (HeadlessException ex5) {}
                catch (Error error) {
                    t = error;
                }
                if (Java2D.DEBUG) {
                    if (null != t) {
                        t.printStackTrace();
                    }
                    System.err.println("JOGL/Java2D OGL Pipeline active " + Java2D.isOGLPipelineActive + ", resourceCompatible " + Java2D.isOGLPipelineResourceCompatible);
                }
                return null;
            }
        });
    }
}
