// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.util.applet;

import jogamp.newt.Debug;
import com.jogamp.nativewindow.util.InsetsImmutable;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLPipelineFactory;
import java.io.PrintStream;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.newt.opengl.util.NEWTDemoListener;
import com.jogamp.newt.event.MouseListener;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.opengl.util.Animator;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.newt.event.KeyListener;

public class JOGLNewtAppletBase implements KeyListener, GLEventListener
{
    public static final boolean DEBUG;
    String glEventListenerClazzName;
    int glSwapInterval;
    boolean noDefaultKeyListener;
    boolean glClosable;
    boolean glDebug;
    boolean glTrace;
    GLEventListener glEventListener;
    GLWindow glWindow;
    Animator glAnimator;
    boolean isValid;
    NativeWindow parentWin;
    private final WindowListener reparentHomeListener;
    
    public JOGLNewtAppletBase(final String glEventListenerClazzName, final int glSwapInterval, final boolean noDefaultKeyListener, final boolean glClosable, final boolean glDebug, final boolean glTrace) {
        this.glEventListener = null;
        this.glWindow = null;
        this.glAnimator = null;
        this.isValid = false;
        this.reparentHomeListener = new WindowAdapter() {
            @Override
            public void windowDestroyNotify(final WindowEvent windowEvent) {
                if (JOGLNewtAppletBase.this.isValid() && WindowClosingProtocol.WindowClosingMode.DO_NOTHING_ON_CLOSE == JOGLNewtAppletBase.this.glWindow.getDefaultCloseOperation() && null == JOGLNewtAppletBase.this.glWindow.getParent() && null != JOGLNewtAppletBase.this.parentWin && 0L != JOGLNewtAppletBase.this.parentWin.getWindowHandle()) {
                    new InterruptSource.Thread(null, new Runnable() {
                        @Override
                        public void run() {
                            if (JOGLNewtAppletBase.this.glWindow.isNativeValid() && null != JOGLNewtAppletBase.this.parentWin && 0L != JOGLNewtAppletBase.this.parentWin.getWindowHandle()) {
                                JOGLNewtAppletBase.this.glWindow.reparentWindow(JOGLNewtAppletBase.this.parentWin, -1, -1, 2);
                            }
                        }
                    }).start();
                }
            }
        };
        this.glEventListenerClazzName = glEventListenerClazzName;
        this.glSwapInterval = glSwapInterval;
        this.noDefaultKeyListener = noDefaultKeyListener;
        this.glClosable = glClosable;
        this.glDebug = glDebug;
        this.glTrace = glTrace;
    }
    
    public GLEventListener getGLEventListener() {
        return this.glEventListener;
    }
    
    public GLWindow getGLWindow() {
        return this.glWindow;
    }
    
    public Animator getGLAnimator() {
        return this.glAnimator;
    }
    
    public boolean isValid() {
        return this.isValid;
    }
    
    public static boolean str2Bool(final String s, final boolean b) {
        if (null == s) {
            return b;
        }
        try {
            return Boolean.valueOf(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return b;
        }
    }
    
    public static int str2Int(final String s, final int n) {
        if (null == s) {
            return n;
        }
        try {
            return Integer.parseInt(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return n;
        }
    }
    
    public static GLEventListener createInstance(final String s) {
        GLEventListener instance;
        try {
            instance = AccessController.doPrivileged((PrivilegedAction<Class<Object>>)new PrivilegedAction<Class<?>>() {
                @Override
                public Class<?> run() {
                    final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                    Class<?> forName = null;
                    try {
                        forName = Class.forName(s, false, contextClassLoader);
                    }
                    catch (Throwable t) {
                        t.printStackTrace();
                    }
                    return forName;
                }
            }).newInstance();
        }
        catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException("Error while instantiating demo: " + s);
        }
        if (null == instance) {
            throw new RuntimeException("Null GLEventListener: " + s);
        }
        if (!(instance instanceof GLEventListener)) {
            throw new RuntimeException("Not a GLEventListener: " + s);
        }
        return instance;
    }
    
    public static boolean setField(final Object o, final String s, final Object o2) {
        try {
            final Field field = o.getClass().getField(s);
            if (field.getType().isInstance(o2)) {
                field.set(o, o2);
                return true;
            }
            System.out.println(o.getClass() + " '" + s + "' field not assignable with " + o2.getClass() + ", it's a: " + field.getType());
        }
        catch (NoSuchFieldException ex) {
            System.out.println(o.getClass() + " has no '" + s + "' field");
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }
    
    public void init(final GLWindow glWindow) {
        this.init(Thread.currentThread().getThreadGroup(), glWindow);
    }
    
    public void init(final ThreadGroup threadGroup, final GLWindow glWindow) {
        this.isValid = false;
        this.glWindow = glWindow;
        this.glEventListener = createInstance(this.glEventListenerClazzName);
        if (null == this.glEventListener) {
            return;
        }
        try {
            if (!setField(this.glEventListener, "window", glWindow)) {
                setField(this.glEventListener, "glWindow", glWindow);
            }
            glWindow.addGLEventListener(this);
            glWindow.addGLEventListener(this.glEventListener);
            if (this.glEventListener instanceof WindowListener) {
                glWindow.addWindowListener((WindowListener)this.glEventListener);
            }
            if (this.glEventListener instanceof MouseListener) {
                glWindow.addMouseListener((MouseListener)this.glEventListener);
            }
            if (this.glEventListener instanceof KeyListener) {
                glWindow.addKeyListener((KeyListener)this.glEventListener);
            }
            glWindow.addWindowListener(this.reparentHomeListener);
            if (!this.noDefaultKeyListener) {
                glWindow.addKeyListener(this);
                final NEWTDemoListener newtDemoListener = new NEWTDemoListener(glWindow);
                glWindow.addKeyListener(newtDemoListener);
                glWindow.addMouseListener(newtDemoListener);
            }
            glWindow.setUpdateFPSFrames(300, System.err);
            (this.glAnimator = new Animator()).setModeBits(false, 1);
            this.glAnimator.setThreadGroup(threadGroup);
            this.glAnimator.add(glWindow);
            this.glAnimator.setUpdateFPSFrames(300, null);
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
        this.isValid = true;
    }
    
    public void start() {
        if (this.isValid) {
            this.glWindow.setVisible(true);
            this.glWindow.sendWindowEvent(100);
            this.glAnimator.start();
            this.parentWin = this.glWindow.getParent();
        }
    }
    
    public void stop() {
        if (null != this.glAnimator) {
            this.glAnimator.stop();
            this.glWindow.setVisible(false);
        }
    }
    
    public void destroy() {
        this.isValid = false;
        if (null != this.glAnimator) {
            this.glAnimator.stop();
            this.glAnimator.remove(this.glWindow);
            this.glAnimator = null;
        }
        if (null != this.glWindow) {
            this.glWindow.destroy();
            this.glWindow = null;
        }
    }
    
    @Override
    public void init(final GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        if (this.glDebug) {
            try {
                gl = gl.getContext().setGL(GLPipelineFactory.create("com.jogamp.opengl.Debug", null, gl, null));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.glTrace) {
            try {
                gl = gl.getContext().setGL(GLPipelineFactory.create("com.jogamp.opengl.Trace", null, gl, new Object[] { System.err }));
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        gl.setSwapInterval(this.glSwapInterval);
    }
    
    @Override
    public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
    }
    
    @Override
    public void display(final GLAutoDrawable glAutoDrawable) {
    }
    
    @Override
    public void dispose(final GLAutoDrawable glAutoDrawable) {
    }
    
    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        if (!keyEvent.isPrintableKey() || keyEvent.isAutoRepeat()) {
            return;
        }
        if (keyEvent.getKeyChar() == 'r' && 0 == keyEvent.getModifiers() && null != this.parentWin) {
            keyEvent.setConsumed(true);
            this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                @Override
                public void run() {
                    if (null == JOGLNewtAppletBase.this.glWindow.getParent()) {
                        JOGLNewtAppletBase.this.glWindow.reparentWindow(JOGLNewtAppletBase.this.parentWin, -1, -1, 0);
                    }
                    else {
                        final InsetsImmutable insets = JOGLNewtAppletBase.this.glWindow.getInsets();
                        int leftWidth;
                        int topHeight;
                        if (0 >= insets.getTopHeight()) {
                            leftWidth = 32;
                            topHeight = 32;
                        }
                        else {
                            leftWidth = insets.getLeftWidth();
                            topHeight = insets.getTopHeight();
                        }
                        JOGLNewtAppletBase.this.glWindow.reparentWindow(null, leftWidth, topHeight, 0);
                        JOGLNewtAppletBase.this.glWindow.setDefaultCloseOperation(JOGLNewtAppletBase.this.glClosable ? WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE : WindowClosingProtocol.WindowClosingMode.DO_NOTHING_ON_CLOSE);
                    }
                }
            });
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent keyEvent) {
    }
    
    static {
        DEBUG = Debug.debug("Applet");
    }
}
