// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.opengl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import com.jogamp.opengl.GLAnimatorControl;
import jogamp.opengl.GLDrawableHelper;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.common.GlueGenVersion;
import com.jogamp.common.util.VersionUtil;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.SurfaceUpdatedListener;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.newt.event.GestureHandler;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.NEWTEvent;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.nativewindow.util.Point;
import java.util.List;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.nativewindow.util.InsetsImmutable;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.Display;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.newt.Screen;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.newt.NewtFactory;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.event.WindowAdapter;
import jogamp.opengl.GLContextImpl;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLDrawableFactory;
import jogamp.newt.WindowImpl;
import com.jogamp.opengl.FPSCounter;
import com.jogamp.newt.event.NEWTEventConsumer;
import com.jogamp.newt.Window;
import com.jogamp.opengl.GLAutoDrawable;
import jogamp.opengl.GLAutoDrawableBase;

public class GLWindow extends GLAutoDrawableBase implements GLAutoDrawable, Window, NEWTEventConsumer, FPSCounter
{
    private final WindowImpl window;
    private GLDrawableFactory factory;
    
    protected GLWindow(final Window window) {
        super(null, null, false);
        (this.window = (WindowImpl)window).setWindowDestroyNotifyAction(new Runnable() {
            @Override
            public void run() {
                GLWindow.this.defaultWindowDestroyNotifyOp();
            }
        });
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowRepaint(final WindowUpdateEvent windowUpdateEvent) {
                GLWindow.this.defaultWindowRepaintOp();
            }
            
            @Override
            public void windowResized(final WindowEvent windowEvent) {
                GLWindow.this.defaultWindowResizedOp(GLWindow.this.getSurfaceWidth(), GLWindow.this.getSurfaceHeight());
            }
        });
        this.window.setLifecycleHook(new GLLifecycleHook());
    }
    
    @Override
    public final Object getUpstreamWidget() {
        return this.window;
    }
    
    @Override
    public final RecursiveLock getUpstreamLock() {
        return this.window.getLock();
    }
    
    public static GLWindow create(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        return new GLWindow(NewtFactory.createWindow(glCapabilitiesImmutable));
    }
    
    public static GLWindow create(final Screen screen, final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        return new GLWindow(NewtFactory.createWindow(screen, glCapabilitiesImmutable));
    }
    
    public static GLWindow create(final Window window) {
        return new GLWindow(window);
    }
    
    public static GLWindow create(final NativeWindow nativeWindow, final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        return new GLWindow(NewtFactory.createWindow(nativeWindow, glCapabilitiesImmutable));
    }
    
    @Override
    public WindowClosingProtocol.WindowClosingMode getDefaultCloseOperation() {
        return this.window.getDefaultCloseOperation();
    }
    
    @Override
    public WindowClosingProtocol.WindowClosingMode setDefaultCloseOperation(final WindowClosingProtocol.WindowClosingMode defaultCloseOperation) {
        return this.window.setDefaultCloseOperation(defaultCloseOperation);
    }
    
    @Override
    public final int getStatePublicBitCount() {
        return this.window.getStatePublicBitCount();
    }
    
    @Override
    public final int getStatePublicBitmask() {
        return this.window.getStatePublicBitmask();
    }
    
    @Override
    public final int getStateMask() {
        return this.window.getStateMask();
    }
    
    @Override
    public final String getStateMaskString() {
        return this.window.getStateMaskString();
    }
    
    @Override
    public final int getSupportedStateMask() {
        return this.window.getSupportedStateMask();
    }
    
    @Override
    public final String getSupportedStateMaskString() {
        return this.window.getSupportedStateMaskString();
    }
    
    @Override
    public CapabilitiesChooser setCapabilitiesChooser(final CapabilitiesChooser capabilitiesChooser) {
        return this.window.setCapabilitiesChooser(capabilitiesChooser);
    }
    
    @Override
    public final CapabilitiesImmutable getChosenCapabilities() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getChosenGLCapabilities() : this.window.getChosenCapabilities();
    }
    
    @Override
    public final CapabilitiesImmutable getRequestedCapabilities() {
        return this.window.getRequestedCapabilities();
    }
    
    @Override
    public final Window getDelegatedWindow() {
        return this.window.getDelegatedWindow();
    }
    
    @Override
    public final NativeWindow getParent() {
        return this.window.getParent();
    }
    
    @Override
    public final Screen getScreen() {
        return this.window.getScreen();
    }
    
    @Override
    public final MonitorDevice getMainMonitor() {
        return this.window.getMainMonitor();
    }
    
    @Override
    public final void setTitle(final String title) {
        this.window.setTitle(title);
    }
    
    @Override
    public final String getTitle() {
        return this.window.getTitle();
    }
    
    @Override
    public final boolean isPointerVisible() {
        return this.window.isPointerVisible();
    }
    
    @Override
    public final void setPointerVisible(final boolean pointerVisible) {
        this.window.setPointerVisible(pointerVisible);
    }
    
    @Override
    public final Display.PointerIcon getPointerIcon() {
        return this.window.getPointerIcon();
    }
    
    @Override
    public final void setPointerIcon(final Display.PointerIcon pointerIcon) {
        this.window.setPointerIcon(pointerIcon);
    }
    
    @Override
    public final boolean isPointerConfined() {
        return this.window.isPointerConfined();
    }
    
    @Override
    public final void confinePointer(final boolean b) {
        this.window.confinePointer(b);
    }
    
    @Override
    public final void setUndecorated(final boolean undecorated) {
        this.window.setUndecorated(undecorated);
    }
    
    @Override
    public final void warpPointer(final int n, final int n2) {
        this.window.warpPointer(n, n2);
    }
    
    @Override
    public final boolean isUndecorated() {
        return this.window.isUndecorated();
    }
    
    @Override
    public final void setAlwaysOnTop(final boolean alwaysOnTop) {
        this.window.setAlwaysOnTop(alwaysOnTop);
    }
    
    @Override
    public final boolean isAlwaysOnTop() {
        return this.window.isAlwaysOnTop();
    }
    
    @Override
    public final void setAlwaysOnBottom(final boolean alwaysOnBottom) {
        this.window.setAlwaysOnBottom(alwaysOnBottom);
    }
    
    @Override
    public final boolean isAlwaysOnBottom() {
        return this.window.isAlwaysOnBottom();
    }
    
    @Override
    public final void setResizable(final boolean resizable) {
        this.window.setResizable(resizable);
    }
    
    @Override
    public final boolean isResizable() {
        return this.window.isResizable();
    }
    
    @Override
    public final void setSticky(final boolean sticky) {
        this.window.setSticky(sticky);
    }
    
    @Override
    public final boolean isSticky() {
        return this.window.isSticky();
    }
    
    @Override
    public final void setMaximized(final boolean b, final boolean b2) {
        this.window.setMaximized(b, b2);
    }
    
    @Override
    public final boolean isMaximizedVert() {
        return this.window.isMaximizedVert();
    }
    
    @Override
    public final boolean isMaximizedHorz() {
        return this.window.isMaximizedHorz();
    }
    
    @Override
    public final void setFocusAction(final FocusRunnable focusAction) {
        this.window.setFocusAction(focusAction);
    }
    
    @Override
    public void setKeyboardFocusHandler(final KeyListener keyboardFocusHandler) {
        this.window.setKeyboardFocusHandler(keyboardFocusHandler);
    }
    
    @Override
    public final void requestFocus() {
        this.window.requestFocus();
    }
    
    @Override
    public final void requestFocus(final boolean b) {
        this.window.requestFocus(b);
    }
    
    @Override
    public boolean hasFocus() {
        return this.window.hasFocus();
    }
    
    @Override
    public final InsetsImmutable getInsets() {
        return this.window.getInsets();
    }
    
    @Override
    public final int getX() {
        return this.window.getX();
    }
    
    @Override
    public final int getY() {
        return this.window.getY();
    }
    
    @Override
    public final int getWidth() {
        return this.window.getWidth();
    }
    
    @Override
    public final int getHeight() {
        return this.window.getHeight();
    }
    
    @Override
    public final Rectangle getBounds() {
        return this.window.getBounds();
    }
    
    @Override
    public final int getSurfaceWidth() {
        return this.window.getSurfaceWidth();
    }
    
    @Override
    public final int getSurfaceHeight() {
        return this.window.getSurfaceHeight();
    }
    
    @Override
    public final int[] convertToWindowUnits(final int[] array) {
        return this.window.convertToWindowUnits(array);
    }
    
    @Override
    public final int[] convertToPixelUnits(final int[] array) {
        return this.window.convertToPixelUnits(array);
    }
    
    @Override
    public final boolean setSurfaceScale(final float[] surfaceScale) {
        return this.window.setSurfaceScale(surfaceScale);
    }
    
    @Override
    public final float[] getRequestedSurfaceScale(final float[] array) {
        return this.window.getRequestedSurfaceScale(array);
    }
    
    @Override
    public final float[] getCurrentSurfaceScale(final float[] array) {
        return this.window.getCurrentSurfaceScale(array);
    }
    
    @Override
    public final float[] getMinimumSurfaceScale(final float[] array) {
        return this.window.getMinimumSurfaceScale(array);
    }
    
    @Override
    public final float[] getMaximumSurfaceScale(final float[] array) {
        return this.window.getMaximumSurfaceScale(array);
    }
    
    @Override
    public final float[] getPixelsPerMM(final float[] array) {
        return this.window.getPixelsPerMM(array);
    }
    
    @Override
    public final void setPosition(final int n, final int n2) {
        this.window.setPosition(n, n2);
    }
    
    @Override
    public void setTopLevelPosition(final int n, final int n2) {
        this.window.setTopLevelPosition(n, n2);
    }
    
    @Override
    public final boolean setFullscreen(final boolean fullscreen) {
        return this.window.setFullscreen(fullscreen);
    }
    
    @Override
    public boolean setFullscreen(final List<MonitorDevice> fullscreen) {
        return this.window.setFullscreen(fullscreen);
    }
    
    @Override
    public final boolean isFullscreen() {
        return this.window.isFullscreen();
    }
    
    @Override
    public final boolean isVisible() {
        return this.window.isVisible();
    }
    
    @Override
    public final String toString() {
        return "NEWT-GLWindow[ \n\tHelper: " + this.helper + ", \n\tDrawable: " + this.drawable + ", \n\tContext: " + this.context + ", \n\tWindow: " + this.window + "]";
    }
    
    @Override
    public final ReparentOperation reparentWindow(final NativeWindow nativeWindow, final int n, final int n2, final int n3) {
        return this.window.reparentWindow(nativeWindow, n, n2, n3);
    }
    
    @Override
    public final boolean isChildWindow() {
        return this.window.isChildWindow();
    }
    
    @Override
    public final boolean removeChild(final NativeWindow nativeWindow) {
        return this.window.removeChild(nativeWindow);
    }
    
    @Override
    public final boolean addChild(final NativeWindow nativeWindow) {
        return this.window.addChild(nativeWindow);
    }
    
    @Override
    public final void destroy() {
        this.window.destroy();
    }
    
    @Override
    public void setWindowDestroyNotifyAction(final Runnable windowDestroyNotifyAction) {
        this.window.setWindowDestroyNotifyAction(windowDestroyNotifyAction);
    }
    
    @Override
    public final void setVisible(final boolean visible) {
        this.window.setVisible(visible);
    }
    
    @Override
    public void setVisible(final boolean b, final boolean b2) {
        this.window.setVisible(b, b2);
    }
    
    @Override
    public final void setSize(final int n, final int n2) {
        this.window.setSize(n, n2);
    }
    
    @Override
    public final void setSurfaceSize(final int n, final int n2) {
        this.window.setSurfaceSize(n, n2);
    }
    
    @Override
    public void setTopLevelSize(final int n, final int n2) {
        this.window.setTopLevelSize(n, n2);
    }
    
    @Override
    public final boolean isNativeValid() {
        return this.window.isNativeValid();
    }
    
    @Override
    public Point getLocationOnScreen(final Point point) {
        return this.window.getLocationOnScreen(point);
    }
    
    @Override
    public void display() {
        if (!this.isNativeValid() || !this.isVisible()) {
            return;
        }
        if (this.sendDestroy || (this.window.hasDeviceChanged() && GLAutoDrawable.SCREEN_CHANGE_ACTION_ENABLED)) {
            this.sendDestroy = false;
            this.destroy();
            return;
        }
        final RecursiveLock lock = this.window.getLock();
        lock.lock();
        boolean b;
        try {
            if (null != this.context) {
                this.helper.invokeGL(this.drawable, this.context, this.defaultDisplayAction, this.defaultInitAction);
                b = true;
            }
            else {
                b = false;
            }
        }
        finally {
            lock.unlock();
        }
        if (!b && 0 < this.getSurfaceWidth() && 0 < this.getSurfaceHeight()) {
            this.setVisible(true);
        }
    }
    
    @Override
    public final boolean isGLStatePreservationSupported() {
        return true;
    }
    
    @Override
    public final GLDrawableFactory getFactory() {
        return this.factory;
    }
    
    @Override
    public final void swapBuffers() throws GLException {
        this.defaultSwapBuffers();
    }
    
    @Override
    public boolean consumeEvent(final NEWTEvent newtEvent) {
        return this.window.consumeEvent(newtEvent);
    }
    
    @Override
    public final void windowRepaint(final int n, final int n2, final int n3, final int n4) {
        this.window.windowRepaint(n, n2, n3, n4);
    }
    
    @Override
    public final void enqueueEvent(final boolean b, final NEWTEvent newtEvent) {
        this.window.enqueueEvent(b, newtEvent);
    }
    
    @Override
    public final void runOnEDTIfAvail(final boolean b, final Runnable runnable) {
        this.window.runOnEDTIfAvail(b, runnable);
    }
    
    @Override
    public void sendWindowEvent(final int n) {
        this.window.sendWindowEvent(n);
    }
    
    @Override
    public final WindowListener getWindowListener(final int n) {
        return this.window.getWindowListener(n);
    }
    
    @Override
    public final WindowListener[] getWindowListeners() {
        return this.window.getWindowListeners();
    }
    
    @Override
    public final void removeWindowListener(final WindowListener windowListener) {
        this.window.removeWindowListener(windowListener);
    }
    
    @Override
    public final void addWindowListener(final WindowListener windowListener) {
        this.window.addWindowListener(windowListener);
    }
    
    @Override
    public final void addWindowListener(final int n, final WindowListener windowListener) throws IndexOutOfBoundsException {
        this.window.addWindowListener(n, windowListener);
    }
    
    @Override
    public final void setKeyboardVisible(final boolean keyboardVisible) {
        this.window.setKeyboardVisible(keyboardVisible);
    }
    
    @Override
    public final boolean isKeyboardVisible() {
        return this.window.isKeyboardVisible();
    }
    
    @Override
    public final void addKeyListener(final KeyListener keyListener) {
        this.window.addKeyListener(keyListener);
    }
    
    @Override
    public final void addKeyListener(final int n, final KeyListener keyListener) {
        this.window.addKeyListener(n, keyListener);
    }
    
    @Override
    public final void removeKeyListener(final KeyListener keyListener) {
        this.window.removeKeyListener(keyListener);
    }
    
    @Override
    public final KeyListener getKeyListener(final int n) {
        return this.window.getKeyListener(n);
    }
    
    @Override
    public final KeyListener[] getKeyListeners() {
        return this.window.getKeyListeners();
    }
    
    @Override
    public final void addMouseListener(final MouseListener mouseListener) {
        this.window.addMouseListener(mouseListener);
    }
    
    @Override
    public final void addMouseListener(final int n, final MouseListener mouseListener) {
        this.window.addMouseListener(n, mouseListener);
    }
    
    @Override
    public final void removeMouseListener(final MouseListener mouseListener) {
        this.window.removeMouseListener(mouseListener);
    }
    
    @Override
    public final MouseListener getMouseListener(final int n) {
        return this.window.getMouseListener(n);
    }
    
    @Override
    public final MouseListener[] getMouseListeners() {
        return this.window.getMouseListeners();
    }
    
    @Override
    public void setDefaultGesturesEnabled(final boolean defaultGesturesEnabled) {
        this.window.setDefaultGesturesEnabled(defaultGesturesEnabled);
    }
    
    @Override
    public boolean areDefaultGesturesEnabled() {
        return this.window.areDefaultGesturesEnabled();
    }
    
    @Override
    public final void addGestureHandler(final GestureHandler gestureHandler) {
        this.window.addGestureHandler(gestureHandler);
    }
    
    @Override
    public final void addGestureHandler(final int n, final GestureHandler gestureHandler) {
        this.window.addGestureHandler(n, gestureHandler);
    }
    
    @Override
    public final void removeGestureHandler(final GestureHandler gestureHandler) {
        this.window.removeGestureHandler(gestureHandler);
    }
    
    @Override
    public final void addGestureListener(final GestureHandler.GestureListener gestureListener) {
        this.window.addGestureListener(-1, gestureListener);
    }
    
    @Override
    public final void addGestureListener(final int n, final GestureHandler.GestureListener gestureListener) {
        this.window.addGestureListener(n, gestureListener);
    }
    
    @Override
    public final void removeGestureListener(final GestureHandler.GestureListener gestureListener) {
        this.window.removeGestureListener(gestureListener);
    }
    
    @Override
    public final int lockSurface() throws NativeWindowException, RuntimeException {
        return this.window.lockSurface();
    }
    
    @Override
    public final void unlockSurface() {
        this.window.unlockSurface();
    }
    
    @Override
    public final boolean isSurfaceLockedByOtherThread() {
        return this.window.isSurfaceLockedByOtherThread();
    }
    
    @Override
    public final Thread getSurfaceLockOwner() {
        return this.window.getSurfaceLockOwner();
    }
    
    @Override
    public final boolean surfaceSwap() {
        return this.window.surfaceSwap();
    }
    
    @Override
    public final void removeSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.window.removeSurfaceUpdatedListener(surfaceUpdatedListener);
    }
    
    @Override
    public final void addSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.window.addSurfaceUpdatedListener(surfaceUpdatedListener);
    }
    
    @Override
    public final void addSurfaceUpdatedListener(final int n, final SurfaceUpdatedListener surfaceUpdatedListener) throws IndexOutOfBoundsException {
        this.window.addSurfaceUpdatedListener(n, surfaceUpdatedListener);
    }
    
    @Override
    public final void surfaceUpdated(final Object o, final NativeSurface nativeSurface, final long n) {
        this.window.surfaceUpdated(o, nativeSurface, n);
    }
    
    @Override
    public final long getWindowHandle() {
        return this.window.getWindowHandle();
    }
    
    @Override
    public final long getSurfaceHandle() {
        return this.window.getSurfaceHandle();
    }
    
    @Override
    public final AbstractGraphicsConfiguration getGraphicsConfiguration() {
        return this.window.getGraphicsConfiguration();
    }
    
    @Override
    public final long getDisplayHandle() {
        return this.window.getDisplayHandle();
    }
    
    @Override
    public final int getScreenIndex() {
        return this.window.getScreenIndex();
    }
    
    public static void main(final String[] array) {
        boolean b = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        if (null != array) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i].equals("-es2")) {
                    b = true;
                }
                else if (array[i].equals("-es3")) {
                    b2 = true;
                }
                else if (array[i].equals("-gl3")) {
                    b3 = true;
                }
                else if (array[i].equals("-gl4es3")) {
                    b4 = true;
                }
            }
        }
        final boolean b5 = b;
        final boolean b6 = b2;
        final boolean b7 = b3;
        final boolean b8 = b4;
        System.err.println("forceES2    " + b5);
        System.err.println("forceES3    " + b6);
        System.err.println("forceGL3    " + b7);
        System.err.println("forceGL4ES3 " + b8);
        System.err.println(VersionUtil.getPlatformInfo());
        System.err.println(GlueGenVersion.getInstance());
        System.err.println(JoglVersion.getInstance());
        System.err.println(JoglVersion.getDefaultOpenGLInfo(null, null, true).toString());
        GLProfile glProfile;
        if (b8) {
            glProfile = GLProfile.get("GL4ES3");
        }
        else if (b7) {
            glProfile = GLProfile.get("GL3");
        }
        else if (b6) {
            glProfile = GLProfile.get("GLES3");
        }
        else if (b5) {
            glProfile = GLProfile.get("GLES2");
        }
        else {
            glProfile = GLProfile.getDefault();
        }
        final GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        System.err.println("Requesting: " + glCapabilities);
        final GLWindow create = create(glCapabilities);
        create.setSize(128, 128);
        create.addGLEventListener(new GLEventListener() {
            @Override
            public void init(final GLAutoDrawable glAutoDrawable) {
                final MonitorDevice mainMonitor = create.getMainMonitor();
                System.err.println("Main Monitor: " + mainMonitor);
                final float[] pixelsPerMM = mainMonitor.getPixelsPerMM(new float[2]);
                System.err.println("    pixel/mm [" + pixelsPerMM[0] + ", " + pixelsPerMM[1] + "]");
                System.err.println("    pixel/in [" + pixelsPerMM[0] * 25.4f + ", " + pixelsPerMM[1] * 25.4f + "]");
                final GL gl = glAutoDrawable.getGL();
                System.err.println(JoglVersion.getGLInfo(gl, null));
                System.err.println("Requested: " + glAutoDrawable.getNativeSurface().getGraphicsConfiguration().getRequestedCapabilities());
                System.err.println("Chosen   : " + glAutoDrawable.getChosenGLCapabilities());
                System.err.println("GL impl. class " + gl.getClass().getName());
                if (gl.isGL4ES3()) {
                    System.err.println("GL4ES3 retrieved, impl. class " + gl.getGL4ES3().getClass().getName());
                }
                if (gl.isGL3()) {
                    System.err.println("GL3 retrieved, impl. class " + gl.getGL3().getClass().getName());
                }
                if (gl.isGLES3()) {
                    System.err.println("GLES3 retrieved, impl. class " + gl.getGLES3().getClass().getName());
                }
                if (gl.isGLES2()) {
                    System.err.println("GLES2 retrieved, impl. class " + gl.getGLES2().getClass().getName());
                }
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
        });
        create.setVisible(true);
        create.destroy();
    }
    
    protected class GLLifecycleHook implements WindowImpl.LifecycleHook
    {
        private GLAnimatorControl savedAnimator;
        
        protected GLLifecycleHook() {
            this.savedAnimator = null;
        }
        
        @Override
        public void preserveGLStateAtDestroy(final boolean b) {
            GLWindow.this.preserveGLStateAtDestroy(b);
        }
        
        @Override
        public synchronized void destroyActionPreLock() {
        }
        
        @Override
        public synchronized void destroyActionInLock() {
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("GLWindow.destroy() " + WindowImpl.getThreadName() + ", start");
            }
            GLWindow.this.destroyImplInLock();
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("GLWindow.destroy() " + WindowImpl.getThreadName() + ", fin");
            }
        }
        
        @Override
        public synchronized void resetCounter() {
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("GLWindow.resetCounter() " + WindowImpl.getThreadName());
            }
            GLWindow.this.resetFPSCounter();
            final GLAnimatorControl animator = GLWindow.this.getAnimator();
            if (null != animator) {
                animator.resetFPSCounter();
            }
        }
        
        @Override
        public synchronized void setVisibleActionPost(final boolean b, final boolean b2) {
            long nanoTime;
            if (Window.DEBUG_IMPLEMENTATION) {
                nanoTime = System.nanoTime();
                System.err.println("GLWindow.setVisibleActionPost(" + b + ", " + b2 + ") " + WindowImpl.getThreadName() + ", start");
            }
            else {
                nanoTime = 0L;
            }
            if (null == GLWindow.this.drawable && b && 0L != GLWindow.this.window.getWindowHandle() && 0 < GLWindow.this.getSurfaceWidth() * GLWindow.this.getSurfaceHeight()) {
                if (null != GLWindow.this.context) {
                    throw new InternalError("GLWindow.LifecycleHook.setVisiblePost: " + WindowImpl.getThreadName() + " - Null drawable, but valid context - " + GLWindow.this);
                }
                final GLContext[] array = { null };
                if (!GLWindow.this.helper.isSharedGLContextPending(array)) {
                    final NativeSurface wrappedSurface = GLWindow.this.window.getWrappedSurface();
                    final WindowImpl windowImpl = (WindowImpl)((null != wrappedSurface) ? wrappedSurface : GLWindow.this.window);
                    final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)windowImpl.getGraphicsConfiguration().getChosenCapabilities();
                    if (null == GLWindow.this.factory) {
                        GLWindow.this.factory = GLDrawableFactory.getFactory(glCapabilitiesImmutable.getGLProfile());
                    }
                    GLWindow.this.drawable = (GLDrawableImpl)GLWindow.this.factory.createGLDrawable(windowImpl);
                    GLWindow.this.drawable.setRealized(true);
                    if (!GLWindow.this.restoreGLEventListenerState()) {
                        GLWindow.this.context = (GLContextImpl)GLWindow.this.drawable.createContext(array[0]);
                        GLWindow.this.context.setContextCreationFlags(GLWindow.this.additionalCtxCreationFlags);
                    }
                }
            }
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("GLWindow.setVisibleActionPost(" + b + ", " + b2 + ") " + WindowImpl.getThreadName() + ", fin: dt " + (System.nanoTime() - nanoTime) / 1000000.0 + "ms");
            }
        }
        
        @Override
        public synchronized boolean pauseRenderingAction() {
            this.savedAnimator = GLWindow.this.getAnimator();
            return null != this.savedAnimator && this.savedAnimator.pause();
        }
        
        @Override
        public synchronized void resumeRenderingAction() {
            if (null != this.savedAnimator && this.savedAnimator.isPaused()) {
                this.savedAnimator.resume();
            }
        }
        
        @Override
        public void shutdownRenderingAction() {
            final GLAnimatorControl animator = GLWindow.this.getAnimator();
            if (null != animator && animator.isAnimating()) {
                final Thread thread = animator.getThread();
                if (thread == Thread.currentThread()) {
                    animator.stop();
                }
                else {
                    AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                        @Override
                        public Object run() {
                            if (animator.isAnimating() && null != thread) {
                                try {
                                    thread.stop();
                                }
                                catch (Throwable t) {
                                    if (GLAutoDrawableBase.DEBUG) {
                                        System.err.println("Caught " + t.getClass().getName() + ": " + t.getMessage());
                                        t.printStackTrace();
                                    }
                                }
                            }
                            return null;
                        }
                    });
                }
            }
        }
    }
}
