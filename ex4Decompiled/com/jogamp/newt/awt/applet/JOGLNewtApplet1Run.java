// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.awt.applet;

import jogamp.nativewindow.jawt.JAWTUtil;
import java.util.Arrays;
import java.awt.Point;
import java.awt.EventQueue;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.newt.Window;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Button;
import java.awt.Container;
import com.jogamp.common.util.awt.AWTEDTExecutor;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.newt.util.applet.JOGLNewtAppletBase;
import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.opengl.GLWindow;
import java.applet.Applet;

public class JOGLNewtApplet1Run extends Applet
{
    public static final boolean DEBUG;
    GLWindow glWindow;
    NewtCanvasAWT newtCanvasAWT;
    JOGLNewtAppletBase base;
    int glXd;
    int glYd;
    int glWidth;
    int glHeight;
    
    public JOGLNewtApplet1Run() {
        this.glWindow = null;
        this.newtCanvasAWT = null;
        this.base = null;
        this.glXd = Integer.MAX_VALUE;
        this.glYd = Integer.MAX_VALUE;
        this.glWidth = Integer.MAX_VALUE;
        this.glHeight = Integer.MAX_VALUE;
    }
    
    @Override
    public void init() {
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run.init() START - " + currentThreadName());
        }
        String parameter = null;
        String parameter2 = null;
        int str2Int = 1;
        boolean str2Bool = false;
        boolean str2Bool2 = false;
        boolean str2Bool3 = false;
        boolean str2Bool4 = false;
        boolean str2Bool5 = false;
        boolean str2Bool6 = true;
        int str2Int2 = 0;
        int str2Int3 = 0;
        boolean str2Bool7 = false;
        boolean str2Bool8 = false;
        try {
            parameter = this.getParameter("gl_event_listener_class");
            parameter2 = this.getParameter("gl_profile");
            str2Int = JOGLNewtAppletBase.str2Int(this.getParameter("gl_swap_interval"), str2Int);
            str2Bool = JOGLNewtAppletBase.str2Bool(this.getParameter("gl_debug"), str2Bool);
            str2Bool2 = JOGLNewtAppletBase.str2Bool(this.getParameter("gl_trace"), str2Bool2);
            str2Bool3 = JOGLNewtAppletBase.str2Bool(this.getParameter("gl_undecorated"), str2Bool3);
            str2Bool4 = JOGLNewtAppletBase.str2Bool(this.getParameter("gl_alwaysontop"), str2Bool4);
            str2Bool5 = JOGLNewtAppletBase.str2Bool(this.getParameter("gl_closeable"), str2Bool5);
            str2Bool6 = JOGLNewtAppletBase.str2Bool(this.getParameter("gl_opaque"), str2Bool6);
            str2Int2 = JOGLNewtAppletBase.str2Int(this.getParameter("gl_alpha"), str2Int2);
            str2Int3 = JOGLNewtAppletBase.str2Int(this.getParameter("gl_multisamplebuffer"), str2Int3);
            this.glXd = JOGLNewtAppletBase.str2Int(this.getParameter("gl_dx"), this.glXd);
            this.glYd = JOGLNewtAppletBase.str2Int(this.getParameter("gl_dy"), this.glYd);
            this.glWidth = JOGLNewtAppletBase.str2Int(this.getParameter("gl_width"), this.glWidth);
            this.glHeight = JOGLNewtAppletBase.str2Int(this.getParameter("gl_height"), this.glHeight);
            str2Bool7 = JOGLNewtAppletBase.str2Bool(this.getParameter("gl_nodefaultkeyListener"), str2Bool7);
            str2Bool8 = JOGLNewtAppletBase.str2Bool(this.getParameter("appletDebugTestBorder"), str2Bool8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (null == parameter) {
            throw new RuntimeException("No applet parameter 'gl_event_listener_class'");
        }
        final boolean b = Integer.MAX_VALUE > this.glXd && Integer.MAX_VALUE > this.glYd && Integer.MAX_VALUE > this.glWidth && Integer.MAX_VALUE > this.glHeight;
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run Configuration:");
            System.err.println("glStandalone: " + b);
            if (b) {
                System.err.println("pos-size: " + this.glXd + "/" + this.glYd + " " + this.glWidth + "x" + this.glHeight);
            }
            System.err.println("glEventListenerClazzName: " + parameter);
            System.err.println("glProfileName: " + parameter2);
            System.err.println("glSwapInterval: " + str2Int);
            System.err.println("glDebug: " + str2Bool);
            System.err.println("glTrace: " + str2Bool2);
            System.err.println("glUndecorated: " + str2Bool3);
            System.err.println("glAlwaysOnTop: " + str2Bool4);
            System.err.println("glCloseable: " + str2Bool5);
            System.err.println("glOpaque: " + str2Bool6);
            System.err.println("glAlphaBits: " + str2Int2);
            System.err.println("glNumMultisampleBuffer: " + str2Int3);
            System.err.println("glNoDefaultKeyListener: " + str2Bool7);
        }
        this.base = new JOGLNewtAppletBase(parameter, str2Int, str2Bool7, str2Bool5, str2Bool, str2Bool2);
        try {
            final GLCapabilities glCapabilities = new GLCapabilities(GLProfile.get(parameter2));
            glCapabilities.setAlphaBits(str2Int2);
            if (0 < str2Int3) {
                glCapabilities.setSampleBuffers(true);
                glCapabilities.setNumSamples(str2Int3);
            }
            glCapabilities.setBackgroundOpaque(str2Bool6);
            (this.glWindow = GLWindow.create(glCapabilities)).setUpdateFPSFrames(300, System.err);
            this.glWindow.setUndecorated(str2Bool3);
            this.glWindow.setAlwaysOnTop(str2Bool4);
            this.glWindow.setDefaultCloseOperation(str2Bool5 ? WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE : WindowClosingProtocol.WindowClosingMode.DO_NOTHING_ON_CLOSE);
            this.setLayout(new BorderLayout());
            if (str2Bool8) {
                AWTEDTExecutor.singleton.invoke(true, new Runnable() {
                    final /* synthetic */ Container val$container;
                    
                    @Override
                    public void run() {
                        this.val$container.add(new Button("North"), "North");
                        this.val$container.add(new Button("South"), "South");
                        this.val$container.add(new Button("East"), "East");
                        this.val$container.add(new Button("West"), "West");
                    }
                });
            }
            this.base.init(this.glWindow);
            if (this.base.isValid()) {
                final GLEventListener glEventListener = this.base.getGLEventListener();
                if (glEventListener instanceof MouseListener) {
                    this.addMouseListener((MouseListener)glEventListener);
                }
                if (glEventListener instanceof MouseMotionListener) {
                    this.addMouseMotionListener((MouseMotionListener)glEventListener);
                }
                if (glEventListener instanceof KeyListener) {
                    this.addKeyListener((KeyListener)glEventListener);
                }
            }
            if (!b) {
                AWTEDTExecutor.singleton.invoke(true, new Runnable() {
                    final /* synthetic */ Container val$container;
                    
                    @Override
                    public void run() {
                        (JOGLNewtApplet1Run.this.newtCanvasAWT = new NewtCanvasAWT(JOGLNewtApplet1Run.this.glWindow)).setSkipJAWTDestroy(true);
                        this.val$container.add(JOGLNewtApplet1Run.this.newtCanvasAWT, "Center");
                        this.val$container.validate();
                    }
                });
            }
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run.init() END - " + currentThreadName());
        }
    }
    
    private static String currentThreadName() {
        return "[" + Thread.currentThread().getName() + ", isAWT-EDT " + EventQueue.isDispatchThread() + "]";
    }
    
    @Override
    public void start() {
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run.start() START (isVisible " + this.isVisible() + ", isDisplayable " + this.isDisplayable() + ") - " + currentThreadName());
        }
        final Point[] array = { null };
        AWTEDTExecutor.singleton.invoke(true, new Runnable() {
            @Override
            public void run() {
                JOGLNewtApplet1Run.this.setVisible(true);
                array[0] = JOGLNewtApplet1Run.this.getLocationOnScreen();
                if (null != JOGLNewtApplet1Run.this.newtCanvasAWT) {
                    JOGLNewtApplet1Run.this.newtCanvasAWT.setFocusable(true);
                    JOGLNewtApplet1Run.this.newtCanvasAWT.requestFocus();
                }
            }
        });
        if (null == this.newtCanvasAWT) {
            this.glWindow.requestFocus();
            this.glWindow.setSize(this.glWidth, this.glHeight);
            this.glWindow.setPosition(array[0].x + this.glXd, array[0].y + this.glYd);
        }
        if (JOGLNewtApplet1Run.DEBUG) {
            Container parent;
            for (parent = this; null != parent.getParent(); parent = parent.getParent()) {}
            System.err.println("JOGLNewtApplet1Run start:");
            System.err.println("TopComponent: " + parent.getLocation() + " rel, " + parent.getLocationOnScreen() + " screen, visible " + parent.isVisible() + ", " + parent);
            System.err.println("Applet Pos: " + this.getLocation() + " rel, " + Arrays.toString(array) + " screen, visible " + this.isVisible() + ", " + this);
            if (null != this.newtCanvasAWT) {
                System.err.println("NewtCanvasAWT Pos: " + this.newtCanvasAWT.getLocation() + " rel, " + this.newtCanvasAWT.getLocationOnScreen() + " screen, visible " + this.newtCanvasAWT.isVisible() + ", " + this.newtCanvasAWT);
            }
            System.err.println("GLWindow Pos: " + this.glWindow.getX() + "/" + this.glWindow.getY() + " rel, " + this.glWindow.getLocationOnScreen(null) + " screen");
            System.err.println("GLWindow: " + this.glWindow);
        }
        this.base.start();
        if (null != this.newtCanvasAWT && this.newtCanvasAWT.isOffscreenLayerSurfaceEnabled() && 0x0 != (0x2 & JAWTUtil.getOSXCALayerQuirks())) {
            AWTEDTExecutor.singleton.invoke(true, new Runnable() {
                @Override
                public void run() {
                    final int width = JOGLNewtApplet1Run.this.newtCanvasAWT.getWidth();
                    final int height = JOGLNewtApplet1Run.this.newtCanvasAWT.getHeight();
                    JOGLNewtApplet1Run.this.newtCanvasAWT.setSize(width + 1, height + 1);
                    JOGLNewtApplet1Run.this.newtCanvasAWT.setSize(width, height);
                }
            });
        }
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run.start() END - " + currentThreadName());
        }
    }
    
    @Override
    public void stop() {
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run.stop() START - " + currentThreadName());
        }
        this.base.stop();
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run.stop() END - " + currentThreadName());
        }
    }
    
    @Override
    public void destroy() {
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run.destroy() START - " + currentThreadName());
        }
        AWTEDTExecutor.singleton.invoke(true, new Runnable() {
            @Override
            public void run() {
                JOGLNewtApplet1Run.this.glWindow.setVisible(false);
                if (null != JOGLNewtApplet1Run.this.newtCanvasAWT) {
                    JOGLNewtApplet1Run.this.newtCanvasAWT.setSkipJAWTDestroy(false);
                    JOGLNewtApplet1Run.this.remove(JOGLNewtApplet1Run.this.newtCanvasAWT);
                    JOGLNewtApplet1Run.this.newtCanvasAWT.destroy();
                }
            }
        });
        this.base.destroy();
        this.base = null;
        this.glWindow = null;
        this.newtCanvasAWT = null;
        if (JOGLNewtApplet1Run.DEBUG) {
            System.err.println("JOGLNewtApplet1Run.destroy() END - " + currentThreadName());
        }
    }
    
    static {
        DEBUG = JOGLNewtAppletBase.DEBUG;
    }
}
