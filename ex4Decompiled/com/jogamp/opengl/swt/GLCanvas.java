// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.swt;

import jogamp.opengl.Debug;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.common.GlueGenVersion;
import com.jogamp.common.util.VersionUtil;
import org.eclipse.swt.widgets.Display;
import java.util.List;
import com.jogamp.opengl.GLRunnable;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import jogamp.nativewindow.x11.X11Util;
import org.eclipse.swt.widgets.Control;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.nativewindow.swt.SWTAccessor;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.common.util.locks.LockFactory;
import org.eclipse.swt.widgets.Composite;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import jogamp.opengl.GLContextImpl;
import jogamp.opengl.GLDrawableImpl;
import org.eclipse.swt.graphics.Rectangle;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import jogamp.opengl.GLDrawableHelper;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.opengl.GLSharedContextSetter;
import com.jogamp.opengl.GLAutoDrawable;
import org.eclipse.swt.widgets.Canvas;

public class GLCanvas extends Canvas implements GLAutoDrawable, GLSharedContextSetter
{
    private static final boolean DEBUG;
    private final RecursiveLock lock;
    private final GLDrawableHelper helper;
    private final GLCapabilitiesImmutable capsRequested;
    private final GLCapabilitiesChooser capsChooser;
    private volatile Rectangle clientArea;
    private volatile GLDrawableImpl drawable;
    private volatile GLContextImpl context;
    private final boolean useX11GTK;
    private volatile long gdkWindow;
    private volatile long x11Window;
    private final AbstractGraphicsScreen screen;
    private int additionalCtxCreationFlags;
    private volatile boolean sendReshape;
    private final Runnable initAction;
    private final Runnable displayAction;
    private final Runnable makeCurrentAndDisplayOnGLAction;
    private final Runnable swapBuffersOnGLAction;
    private final Runnable disposeOnEDTGLAction;
    private final UpstreamSurfaceHook swtCanvasUpStreamHook;
    private boolean isValidAndVisibleOnEDTActionResult;
    private final Runnable isValidAndVisibleOnEDTAction;
    
    private static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    private static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    private static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    public static GLCanvas create(final Composite composite, final int n, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser) {
        final GLCanvas[] array = { null };
        composite.getDisplay().syncExec((Runnable)new Runnable() {
            @Override
            public void run() {
                array[0] = new GLCanvas(composite, n, glCapabilitiesImmutable, glCapabilitiesChooser);
            }
        });
        return array[0];
    }
    
    public GLCanvas(final Composite composite, final int n, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser capsChooser) {
        super(composite, n | 0x40000);
        this.lock = LockFactory.createRecursiveLock();
        this.helper = new GLDrawableHelper();
        this.additionalCtxCreationFlags = 0;
        this.initAction = new Runnable() {
            @Override
            public void run() {
                GLCanvas.this.helper.init(GLCanvas.this, !GLCanvas.this.sendReshape);
            }
        };
        this.displayAction = new Runnable() {
            @Override
            public void run() {
                if (GLCanvas.this.sendReshape) {
                    GLCanvas.this.helper.reshape(GLCanvas.this, 0, 0, GLCanvas.this.clientArea.width, GLCanvas.this.clientArea.height);
                    GLCanvas.this.sendReshape = false;
                }
                GLCanvas.this.helper.display(GLCanvas.this);
            }
        };
        this.makeCurrentAndDisplayOnGLAction = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$300 = GLCanvas.this.lock;
                access$300.lock();
                try {
                    if (!GLCanvas.this.isDisposed()) {
                        GLCanvas.this.helper.invokeGL(GLCanvas.this.drawable, GLCanvas.this.context, GLCanvas.this.displayAction, GLCanvas.this.initAction);
                    }
                }
                finally {
                    access$300.unlock();
                }
            }
        };
        this.swapBuffersOnGLAction = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$300 = GLCanvas.this.lock;
                access$300.lock();
                try {
                    if (null != GLCanvas.this.drawable && GLCanvas.this.drawable.isRealized() && !GLCanvas.this.isDisposed()) {
                        GLCanvas.this.drawable.swapBuffers();
                    }
                }
                finally {
                    access$300.unlock();
                }
            }
        };
        this.disposeOnEDTGLAction = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$300 = GLCanvas.this.lock;
                access$300.lock();
                try {
                    final GLAnimatorControl animator = GLCanvas.this.getAnimator();
                    final boolean b = null != animator && animator.pause();
                    GLException ex = null;
                    if (null != GLCanvas.this.context) {
                        if (GLCanvas.this.context.isCreated()) {
                            try {
                                if (!GLCanvas.this.isDisposed()) {
                                    GLCanvas.this.helper.disposeGL(GLCanvas.this, GLCanvas.this.context, true);
                                }
                                else {
                                    GLCanvas.this.context.destroy();
                                }
                            }
                            catch (GLException ex2) {
                                ex = ex2;
                            }
                        }
                        GLCanvas.this.context = null;
                    }
                    Throwable t = null;
                    if (null != GLCanvas.this.drawable) {
                        try {
                            GLCanvas.this.drawable.setRealized(false);
                        }
                        catch (Throwable t2) {
                            t = t2;
                        }
                        GLCanvas.this.drawable = null;
                    }
                    Throwable t3 = null;
                    try {
                        if (0L != GLCanvas.this.x11Window) {
                            SWTAccessor.destroyX11Window(GLCanvas.this.screen.getDevice(), GLCanvas.this.x11Window);
                            GLCanvas.this.x11Window = 0L;
                        }
                        else if (0L != GLCanvas.this.gdkWindow) {
                            SWTAccessor.destroyGDKWindow(GLCanvas.this.gdkWindow);
                            GLCanvas.this.gdkWindow = 0L;
                        }
                        GLCanvas.this.screen.getDevice().close();
                    }
                    catch (Throwable t4) {
                        t3 = t4;
                    }
                    if (b) {
                        animator.resume();
                    }
                    if (null != ex) {
                        throw ex;
                    }
                    if (null != t) {
                        throw GLException.newGLException(t);
                    }
                    if (null != t3) {
                        throw GLException.newGLException(t3);
                    }
                }
                finally {
                    access$300.unlock();
                }
            }
        };
        this.swtCanvasUpStreamHook = new UpstreamSurfaceHook() {
            @Override
            public final void create(final ProxySurface proxySurface) {
            }
            
            @Override
            public final void destroy(final ProxySurface proxySurface) {
            }
            
            @Override
            public final int getSurfaceWidth(final ProxySurface proxySurface) {
                return GLCanvas.this.clientArea.width;
            }
            
            @Override
            public final int getSurfaceHeight(final ProxySurface proxySurface) {
                return GLCanvas.this.clientArea.height;
            }
            
            @Override
            public String toString() {
                return "SWTCanvasUpstreamSurfaceHook[upstream: " + GLCanvas.this.toString() + ", " + GLCanvas.this.clientArea.width + "x" + GLCanvas.this.clientArea.height + "]";
            }
            
            @Override
            public final NativeSurface getUpstreamSurface() {
                return null;
            }
        };
        this.isValidAndVisibleOnEDTAction = new Runnable() {
            @Override
            public void run() {
                GLCanvas.this.isValidAndVisibleOnEDTActionResult = (!GLCanvas.this.isDisposed() && GLCanvas.this.isVisible());
            }
        };
        GLProfile.initSingleton();
        SWTAccessor.setRealized((Control)this, true);
        this.clientArea = this.getClientArea();
        final AbstractGraphicsDevice device = SWTAccessor.getDevice((Control)this);
        this.useX11GTK = SWTAccessor.useX11GTK();
        if (this.useX11GTK) {
            final long openDisplay = X11Util.openDisplay(device.getConnection());
            if (0L == openDisplay) {
                throw new RuntimeException("Error creating display(EDT): " + device.getConnection());
            }
            this.screen = SWTAccessor.getScreen(new X11GraphicsDevice(openDisplay, 0, true), -1);
        }
        else {
            this.screen = SWTAccessor.getScreen(device, -1);
        }
        if (null == glCapabilitiesImmutable) {
            this.capsRequested = new GLCapabilities(GLProfile.getDefault(this.screen.getDevice()));
        }
        else {
            this.capsRequested = (GLCapabilitiesImmutable)glCapabilitiesImmutable.cloneMutable();
        }
        this.capsChooser = capsChooser;
        this.gdkWindow = 0L;
        this.x11Window = 0L;
        this.drawable = null;
        this.context = null;
        final Listener listener = (Listener)new Listener() {
            public void handleEvent(final Event event) {
                switch (event.type) {
                    case 9: {
                        GLCanvas.this.displayIfNoAnimatorNoCheck();
                        break;
                    }
                    case 11: {
                        GLCanvas.this.updateSizeCheck();
                        break;
                    }
                    case 12: {
                        GLCanvas.this.dispose();
                        break;
                    }
                }
            }
        };
        this.addListener(11, (Listener)listener);
        this.addListener(9, (Listener)listener);
        this.addListener(12, (Listener)listener);
    }
    
    public final void setSharedContext(final GLContext glContext) throws IllegalStateException {
        this.helper.setSharedContext(this.context, glContext);
    }
    
    public final void setSharedAutoDrawable(final GLAutoDrawable glAutoDrawable) throws IllegalStateException {
        this.helper.setSharedAutoDrawable(this, glAutoDrawable);
    }
    
    protected final void updateSizeCheck() {
        final Rectangle clientArea = this.clientArea;
        final Rectangle clientArea2 = this.getClientArea();
        if (clientArea2 != null && (clientArea2.width != clientArea.width || clientArea2.height != clientArea.height)) {
            this.clientArea = clientArea2;
            final GLDrawableImpl drawable = this.drawable;
            final boolean b = null != drawable && drawable.isRealized();
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": GLCanvas.sizeChanged: (" + Thread.currentThread().getName() + "): " + clientArea2.x + "/" + clientArea2.y + " " + clientArea2.width + "x" + clientArea2.height + " - drawableHandle " + toHexString(b ? drawable.getHandle() : 0L));
            }
            if (b && !drawable.getChosenGLCapabilities().isOnscreen()) {
                final RecursiveLock lock = this.lock;
                lock.lock();
                try {
                    final GLDrawableImpl resizeOffscreenDrawable = GLDrawableHelper.resizeOffscreenDrawable(drawable, this.context, clientArea2.width, clientArea2.height);
                    if (drawable != resizeOffscreenDrawable) {
                        this.drawable = resizeOffscreenDrawable;
                    }
                }
                finally {
                    lock.unlock();
                }
            }
            if (0L != this.x11Window) {
                SWTAccessor.resizeX11Window(this.screen.getDevice(), this.clientArea, this.x11Window);
            }
            else if (0L != this.gdkWindow) {
                SWTAccessor.resizeGDKWindow(this.clientArea, this.gdkWindow);
            }
            this.sendReshape = true;
        }
    }
    
    private final boolean isValidAndVisibleOnEDT() {
        synchronized (this.isValidAndVisibleOnEDTAction) {
            this.runOnEDTIfAvail(true, this.isValidAndVisibleOnEDTAction);
            return this.isValidAndVisibleOnEDTActionResult;
        }
    }
    
    protected final boolean validateDrawableAndContextWithCheck() {
        return this.isValidAndVisibleOnEDT() && this.validateDrawableAndContextPostCheck();
    }
    
    private final boolean isDrawableAndContextValid() {
        return null != this.drawable && null != this.context;
    }
    
    private final boolean validateDrawableAndContextPostCheck() {
        final RecursiveLock lock = this.lock;
        lock.lock();
        boolean b;
        try {
            if (null == this.drawable) {
                this.createDrawableImpl();
            }
            final GLDrawableImpl drawable = this.drawable;
            if (null != drawable) {
                b = (null != this.context || this.createContextImpl(drawable));
                if (b) {
                    this.sendReshape = true;
                }
            }
            else {
                if (GLCanvas.DEBUG) {
                    System.err.println(getThreadName() + ": SWT.GLCanvas.validate " + toHexString(this.hashCode()) + ": null drawable");
                }
                b = false;
            }
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": SWT.GLCanvas.validate.X  " + toHexString(this.hashCode()) + ": " + b + ", drawable-realized " + this.drawable.isRealized() + ", has context " + (null != this.context));
            }
        }
        finally {
            lock.unlock();
        }
        return b;
    }
    
    private final void createDrawableImpl() {
        final Rectangle clientArea = this.clientArea;
        if (0 >= clientArea.width || 0 >= clientArea.height) {
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": SWT.GLCanvas.validate.X " + toHexString(this.hashCode()) + ": drawable could not be created: size < 0x0");
            }
            return;
        }
        final AbstractGraphicsDevice device = this.screen.getDevice();
        device.open();
        long n;
        if (this.useX11GTK) {
            final GraphicsConfigurationFactory factory = GraphicsConfigurationFactory.getFactory(device, this.capsRequested);
            final AbstractGraphicsConfiguration chooseGraphicsConfiguration = factory.chooseGraphicsConfiguration(this.capsRequested, this.capsRequested, this.capsChooser, this.screen, 0);
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": SWT.GLCanvas.X11 " + toHexString(this.hashCode()) + ": factory: " + factory + ", chosen config: " + chooseGraphicsConfiguration);
            }
            if (null == chooseGraphicsConfiguration) {
                throw new NativeWindowException("Error choosing GraphicsConfiguration creating window: " + this);
            }
            final int visualID = chooseGraphicsConfiguration.getVisualID(VisualIDHolder.VIDType.NATIVE);
            if (visualID == 0) {
                throw new GLException("Could not choose valid visualID: " + toHexString(visualID) + ", " + this);
            }
            this.x11Window = SWTAccessor.createCompatibleX11ChildWindow(this.screen, (Control)this, visualID, this.clientArea.width, this.clientArea.height);
            n = this.x11Window;
        }
        else {
            n = SWTAccessor.getWindowHandle((Control)this);
        }
        final GLDrawableFactory factory2 = GLDrawableFactory.getFactory(this.capsRequested.getGLProfile());
        final GLDrawableImpl drawable = (GLDrawableImpl)factory2.createGLDrawable(factory2.createProxySurface(device, this.screen.getIndex(), n, this.capsRequested, this.capsChooser, this.swtCanvasUpStreamHook));
        drawable.setRealized(true);
        if (!drawable.isRealized()) {
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": SWT.GLCanvas.validate.X " + toHexString(this.hashCode()) + ": Drawable could not be realized: " + drawable);
            }
        }
        else {
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": SWT.GLCanvas.validate " + toHexString(this.hashCode()) + ": Drawable created and realized");
            }
            this.drawable = drawable;
        }
    }
    
    private boolean createContextImpl(final GLDrawable glDrawable) {
        final GLContext[] array = { null };
        if (!this.helper.isSharedGLContextPending(array)) {
            (this.context = (GLContextImpl)glDrawable.createContext(array[0])).setContextCreationFlags(this.additionalCtxCreationFlags);
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": SWT.GLCanvas.validate " + toHexString(this.hashCode()) + ": Context created: has shared " + (null != array[0]));
            }
            return true;
        }
        if (GLCanvas.DEBUG) {
            System.err.println(getThreadName() + ": SWT.GLCanvas.validate " + toHexString(this.hashCode()) + ": Context !created: pending share");
        }
        return false;
    }
    
    public void update() {
    }
    
    public void dispose() {
        this.runInGLThread(this.disposeOnEDTGLAction);
        super.dispose();
    }
    
    private final void displayIfNoAnimatorNoCheck() {
        if (!this.helper.isAnimatorAnimatingOnOtherThread() && (this.isDrawableAndContextValid() || this.validateDrawableAndContextPostCheck())) {
            this.runInGLThread(this.makeCurrentAndDisplayOnGLAction);
        }
    }
    
    public void display() {
        if (this.isDrawableAndContextValid() || this.validateDrawableAndContextWithCheck()) {
            this.runInGLThread(this.makeCurrentAndDisplayOnGLAction);
        }
    }
    
    public final Object getUpstreamWidget() {
        return this;
    }
    
    public final RecursiveLock getUpstreamLock() {
        return this.lock;
    }
    
    public int getSurfaceWidth() {
        return this.clientArea.width;
    }
    
    public int getSurfaceHeight() {
        return this.clientArea.height;
    }
    
    public boolean isGLOriented() {
        final GLDrawableImpl drawable = this.drawable;
        return null == drawable || drawable.isGLOriented();
    }
    
    public void addGLEventListener(final GLEventListener glEventListener) {
        this.helper.addGLEventListener(glEventListener);
    }
    
    public void addGLEventListener(final int n, final GLEventListener glEventListener) throws IndexOutOfBoundsException {
        this.helper.addGLEventListener(n, glEventListener);
    }
    
    public int getGLEventListenerCount() {
        return this.helper.getGLEventListenerCount();
    }
    
    public GLEventListener getGLEventListener(final int n) throws IndexOutOfBoundsException {
        return this.helper.getGLEventListener(n);
    }
    
    public boolean areAllGLEventListenerInitialized() {
        return this.helper.areAllGLEventListenerInitialized();
    }
    
    public boolean getGLEventListenerInitState(final GLEventListener glEventListener) {
        return this.helper.getGLEventListenerInitState(glEventListener);
    }
    
    public void setGLEventListenerInitState(final GLEventListener glEventListener, final boolean b) {
        this.helper.setGLEventListenerInitState(glEventListener, b);
    }
    
    public GLEventListener disposeGLEventListener(final GLEventListener glEventListener, final boolean b) {
        final DisposeGLEventListenerAction disposeGLEventListenerAction = new DisposeGLEventListenerAction(glEventListener, b);
        this.runInGLThread(disposeGLEventListenerAction);
        return disposeGLEventListenerAction.listener;
    }
    
    public GLEventListener removeGLEventListener(final GLEventListener glEventListener) {
        return this.helper.removeGLEventListener(glEventListener);
    }
    
    public void destroy() {
        this.dispose();
    }
    
    public GLAnimatorControl getAnimator() {
        return this.helper.getAnimator();
    }
    
    public final Thread setExclusiveContextThread(final Thread thread) throws GLException {
        return this.helper.setExclusiveContextThread(thread, this.context);
    }
    
    public final Thread getExclusiveContextThread() {
        return this.helper.getExclusiveContextThread();
    }
    
    public boolean getAutoSwapBufferMode() {
        return this.helper.getAutoSwapBufferMode();
    }
    
    public final GLDrawable getDelegatedDrawable() {
        return this.drawable;
    }
    
    public GLContext getContext() {
        return this.context;
    }
    
    public int getContextCreationFlags() {
        return this.additionalCtxCreationFlags;
    }
    
    public GL getGL() {
        final GLContextImpl context = this.context;
        return (null == context) ? null : context.getGL();
    }
    
    public boolean invoke(final boolean b, final GLRunnable glRunnable) throws IllegalStateException {
        return this.helper.invoke(this, b, glRunnable);
    }
    
    public boolean invoke(final boolean b, final List<GLRunnable> list) throws IllegalStateException {
        return this.helper.invoke(this, b, list);
    }
    
    public void flushGLRunnables() {
        this.helper.flushGLRunnables();
    }
    
    public void setAnimator(final GLAnimatorControl animator) throws GLException {
        this.helper.setAnimator(animator);
    }
    
    public void setAutoSwapBufferMode(final boolean autoSwapBufferMode) {
        this.helper.setAutoSwapBufferMode(autoSwapBufferMode);
    }
    
    public GLContext setContext(final GLContext glContext, final boolean b) {
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            final GLContextImpl context = this.context;
            GLDrawableHelper.switchContext(this.drawable, context, b, glContext, this.additionalCtxCreationFlags);
            this.context = (GLContextImpl)glContext;
            return context;
        }
        finally {
            lock.unlock();
        }
    }
    
    public void setContextCreationFlags(final int additionalCtxCreationFlags) {
        this.additionalCtxCreationFlags = additionalCtxCreationFlags;
        final GLContextImpl context = this.context;
        if (null != context) {
            context.setContextCreationFlags(this.additionalCtxCreationFlags);
        }
    }
    
    public GL setGL(final GL gl) {
        final GLContextImpl context = this.context;
        if (null != context) {
            context.setGL(gl);
            return gl;
        }
        return null;
    }
    
    public GLContext createContext(final GLContext glContext) {
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            if (this.drawable != null) {
                final GLContext context = this.drawable.createContext(glContext);
                context.setContextCreationFlags(this.additionalCtxCreationFlags);
                return context;
            }
            return null;
        }
        finally {
            lock.unlock();
        }
    }
    
    public GLCapabilitiesImmutable getChosenGLCapabilities() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getChosenGLCapabilities() : null;
    }
    
    public GLCapabilitiesImmutable getRequestedGLCapabilities() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getRequestedGLCapabilities() : null;
    }
    
    public GLDrawableFactory getFactory() {
        final GLDrawableImpl drawable = this.drawable;
        return (drawable != null) ? drawable.getFactory() : null;
    }
    
    public GLProfile getGLProfile() {
        return this.capsRequested.getGLProfile();
    }
    
    public long getHandle() {
        final GLDrawableImpl drawable = this.drawable;
        return (drawable != null) ? drawable.getHandle() : 0L;
    }
    
    public NativeSurface getNativeSurface() {
        final GLDrawableImpl drawable = this.drawable;
        return (drawable != null) ? drawable.getNativeSurface() : null;
    }
    
    public boolean isRealized() {
        final GLDrawableImpl drawable = this.drawable;
        return drawable != null && drawable.isRealized();
    }
    
    public void setRealized(final boolean b) {
    }
    
    public void swapBuffers() throws GLException {
        this.runInGLThread(this.swapBuffersOnGLAction);
    }
    
    public final boolean isThreadGLCapable() {
        return true;
    }
    
    private void runInGLThread(final Runnable runnable) {
        runnable.run();
    }
    
    private void runOnEDTIfAvail(final boolean b, final Runnable runnable) {
        final Display display = this.isDisposed() ? null : this.getDisplay();
        if (null == display || display.isDisposed() || display.getThread() == Thread.currentThread()) {
            runnable.run();
        }
        else if (b) {
            display.syncExec(runnable);
        }
        else {
            display.asyncExec(runnable);
        }
    }
    
    public String toString() {
        final GLDrawableImpl drawable = this.drawable;
        return "SWT-GLCanvas[Realized " + this.isRealized() + ",\n\t" + ((null != drawable) ? drawable.getClass().getName() : "null-drawable") + ",\n\tFactory   " + this.getFactory() + ",\n\thandle    " + toHexString(this.getHandle()) + ",\n\tDrawable size " + ((null != drawable) ? drawable.getSurfaceWidth() : -1) + "x" + ((null != drawable) ? drawable.getSurfaceHeight() : -1) + ",\n\tSWT size " + this.getSurfaceWidth() + "x" + this.getSurfaceHeight() + "]";
    }
    
    public static void main(final String[] array) {
        System.err.println(VersionUtil.getPlatformInfo());
        System.err.println(GlueGenVersion.getInstance());
        System.err.println(JoglVersion.getInstance());
        System.err.println(JoglVersion.getDefaultOpenGLInfo(null, null, true).toString());
        final GLCapabilities glCapabilities = new GLCapabilities(GLProfile.getDefault(GLProfile.getDefaultDevice()));
        final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setSize(128, 128);
        shell.setLayout((Layout)new FillLayout());
        final GLCanvas glCanvas = new GLCanvas((Composite)shell, 0, glCapabilities, null);
        glCanvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(final GLAutoDrawable glAutoDrawable) {
                System.err.println(JoglVersion.getGLInfo(glAutoDrawable.getGL(), null));
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
        shell.open();
        glCanvas.display();
        display.dispose();
    }
    
    static {
        DEBUG = Debug.debug("GLCanvas");
    }
    
    private class DisposeGLEventListenerAction implements Runnable
    {
        private GLEventListener listener;
        private final boolean remove;
        
        private DisposeGLEventListenerAction(final GLEventListener listener, final boolean remove) {
            this.listener = listener;
            this.remove = remove;
        }
        
        @Override
        public void run() {
            final RecursiveLock access$300 = GLCanvas.this.lock;
            access$300.lock();
            try {
                if (!GLCanvas.this.isDisposed()) {
                    this.listener = GLCanvas.this.helper.disposeGLEventListener(GLCanvas.this, GLCanvas.this.drawable, GLCanvas.this.context, this.listener, this.remove);
                }
            }
            finally {
                access$300.unlock();
            }
        }
    }
}
