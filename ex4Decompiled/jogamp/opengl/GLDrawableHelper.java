// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.util.PropertyAccess;
import java.util.List;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.opengl.GLRunnable;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLFBODrawable;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GLException;
import java.lang.ref.WeakReference;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLAnimatorControl;
import java.util.HashSet;
import com.jogamp.opengl.GLEventListener;
import java.util.ArrayList;

public class GLDrawableHelper
{
    private static final boolean PERF_STATS;
    protected static final boolean DEBUG;
    private static final boolean DEBUG_SETCLEAR;
    private final Object listenersLock;
    private final ArrayList<GLEventListener> listeners;
    private final HashSet<GLEventListener> listenersToBeInit;
    private final Object glRunnablesLock;
    private ArrayList<GLRunnableTask> glRunnables;
    private volatile int glRunnableCount;
    private boolean autoSwapBufferMode;
    private volatile Thread exclusiveContextThread;
    private volatile int exclusiveContextSwitch;
    private GLAnimatorControl animatorCtrl;
    private static Runnable nop;
    private GLContext sharedContext;
    private GLAutoDrawable sharedAutoDrawable;
    private static final int MAX_RELEASE_ITER = 512;
    private static final ThreadLocal<WeakReference<Runnable>> perThreadInitAction;
    
    public GLDrawableHelper() {
        this.listenersLock = new Object();
        this.listeners = new ArrayList<GLEventListener>();
        this.listenersToBeInit = new HashSet<GLEventListener>();
        this.glRunnablesLock = new Object();
        this.glRunnables = new ArrayList<GLRunnableTask>();
        this.glRunnableCount = 0;
        this.reset();
    }
    
    public final void reset() {
        synchronized (this.listenersLock) {
            this.listeners.clear();
            this.listenersToBeInit.clear();
        }
        this.autoSwapBufferMode = true;
        this.exclusiveContextThread = null;
        this.exclusiveContextSwitch = 0;
        synchronized (this.glRunnablesLock) {
            this.glRunnableCount = 0;
            this.glRunnables.clear();
        }
        this.animatorCtrl = null;
        this.sharedContext = null;
        this.sharedAutoDrawable = null;
    }
    
    public final void setSharedContext(final GLContext glContext, final GLContext sharedContext) throws IllegalStateException {
        if (null == sharedContext) {
            throw new IllegalStateException("Null shared GLContext");
        }
        if (glContext == sharedContext) {
            throw new IllegalStateException("Shared GLContext same as local");
        }
        if (null != this.sharedContext) {
            throw new IllegalStateException("Shared GLContext already set");
        }
        if (null != this.sharedAutoDrawable) {
            throw new IllegalStateException("Shared GLAutoDrawable already set");
        }
        this.sharedContext = sharedContext;
    }
    
    public final void setSharedAutoDrawable(final GLAutoDrawable glAutoDrawable, final GLAutoDrawable sharedAutoDrawable) throws IllegalStateException {
        if (null == sharedAutoDrawable) {
            throw new IllegalStateException("Null shared GLAutoDrawable");
        }
        if (glAutoDrawable == sharedAutoDrawable) {
            throw new IllegalStateException("Shared GLAutoDrawable same as this");
        }
        if (null != this.sharedContext) {
            throw new IllegalStateException("Shared GLContext already set");
        }
        if (null != this.sharedAutoDrawable) {
            throw new IllegalStateException("Shared GLAutoDrawable already set");
        }
        this.sharedAutoDrawable = sharedAutoDrawable;
    }
    
    public boolean isSharedGLContextPending(final GLContext[] array) {
        GLContext glContext;
        boolean b;
        if (null != this.sharedAutoDrawable) {
            glContext = this.sharedAutoDrawable.getContext();
            b = (null == glContext || !glContext.isCreated() || !this.sharedAutoDrawable.areAllGLEventListenerInitialized());
        }
        else {
            glContext = this.sharedContext;
            b = (null != glContext && !glContext.isCreated());
        }
        array[0] = glContext;
        return b;
    }
    
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GLAnimatorControl: " + this.animatorCtrl + ", ");
        synchronized (this.listenersLock) {
            sb.append("GLEventListeners num " + this.listeners.size() + " [");
            for (int i = 0; i < this.listeners.size(); ++i) {
                final GLEventListener value = this.listeners.get(i);
                sb.append(value);
                sb.append("[init ");
                sb.append(!this.listenersToBeInit.contains(value));
                sb.append("], ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static final void forceNativeRelease(final GLContext glContext) {
        int n = 0;
        do {
            glContext.release();
            ++n;
            if (GLDrawableHelper.DEBUG) {
                System.err.println("GLDrawableHelper.forceNativeRelease() #" + n + " -- currentThread " + Thread.currentThread() + " -> " + GLContext.getCurrent());
            }
        } while (512 > n && glContext.isCurrent());
        if (glContext.isCurrent()) {
            throw new GLException("Context still current after 512 releases: " + glContext);
        }
    }
    
    public static final void switchContext(final GLDrawable glDrawable, final GLContext glContext, final boolean b, final GLContext glContext2, final int contextCreationFlags) {
        if (null != glContext) {
            if (b) {
                glContext.destroy();
            }
            else {
                glContext.setGLDrawable(null, true);
            }
        }
        if (null != glContext2) {
            glContext2.setContextCreationFlags(contextCreationFlags);
            glContext2.setGLDrawable(glDrawable, true);
        }
    }
    
    public static final GLDrawableImpl recreateGLDrawable(GLDrawableImpl glDrawableImpl, final GLContext glContext) {
        if (!glDrawableImpl.isRealized()) {
            return glDrawableImpl;
        }
        final GLContext current = GLContext.getCurrent();
        final GLDrawableFactory factory = glDrawableImpl.getFactory();
        final NativeSurface nativeSurface = glDrawableImpl.getNativeSurface();
        final ProxySurface proxySurface = (nativeSurface instanceof ProxySurface) ? ((ProxySurface)nativeSurface) : null;
        if (null != glContext) {
            if (current != glContext) {
                glContext.makeCurrent();
            }
            glContext.setGLDrawable(null, true);
        }
        if (null != proxySurface) {
            proxySurface.enableUpstreamSurfaceHookLifecycle(false);
        }
        try {
            glDrawableImpl.setRealized(false);
            glDrawableImpl = (GLDrawableImpl)factory.createGLDrawable(nativeSurface);
            glDrawableImpl.setRealized(true);
        }
        finally {
            if (null != proxySurface) {
                proxySurface.enableUpstreamSurfaceHookLifecycle(true);
            }
        }
        if (null != glContext) {
            glContext.setGLDrawable(glDrawableImpl, true);
        }
        if (null != current) {
            current.makeCurrent();
        }
        return glDrawableImpl;
    }
    
    public static final GLDrawableImpl resizeOffscreenDrawable(GLDrawableImpl recreateGLDrawable, final GLContext glContext, int n, int n2) throws NativeWindowException, GLException {
        final NativeSurface nativeSurface = recreateGLDrawable.getNativeSurface();
        if (1 >= nativeSurface.lockSurface()) {
            throw new NativeWindowException("Could not lock surface of drawable: " + recreateGLDrawable);
        }
        boolean b = true;
        try {
            if (!recreateGLDrawable.isRealized()) {
                return recreateGLDrawable;
            }
            if (recreateGLDrawable.getChosenGLCapabilities().isOnscreen()) {
                throw new NativeWindowException("Drawable is not offscreen: " + recreateGLDrawable);
            }
            if (GLDrawableHelper.DEBUG && (0 >= n || 0 >= n2)) {
                System.err.println("WARNING: Odd size detected: " + n + "x" + n2 + ", using safe size 1x1. Drawable " + recreateGLDrawable);
                ExceptionUtils.dumpStack(System.err);
            }
            if (0 >= n) {
                n = 1;
                b = false;
            }
            if (0 >= n2) {
                n2 = 1;
                b = false;
            }
            if (nativeSurface instanceof ProxySurface) {
                final UpstreamSurfaceHook upstreamSurfaceHook = ((ProxySurface)nativeSurface).getUpstreamSurfaceHook();
                if (upstreamSurfaceHook instanceof UpstreamSurfaceHook.MutableSize) {
                    ((UpstreamSurfaceHook.MutableSize)upstreamSurfaceHook).setSurfaceSize(n, n2);
                }
                else if (GLDrawableHelper.DEBUG) {
                    System.err.println("GLDrawableHelper.resizeOffscreenDrawable: Drawable's offscreen ProxySurface n.a. UpstreamSurfaceHook.MutableSize, but " + ((UpstreamSurfaceHook.MutableSize)upstreamSurfaceHook).getClass().getName() + ": " + upstreamSurfaceHook);
                }
            }
            else if (GLDrawableHelper.DEBUG) {
                System.err.println("GLDrawableHelper.resizeOffscreenDrawable: Drawable's offscreen surface n.a. ProxySurface, but " + ((ProxySurface)nativeSurface).getClass().getName() + ": " + nativeSurface);
            }
            if (recreateGLDrawable instanceof GLFBODrawable) {
                if (null != glContext && glContext.isCreated()) {
                    ((GLFBODrawable)recreateGLDrawable).resetSize(glContext.getGL());
                }
            }
            else {
                recreateGLDrawable = recreateGLDrawable(recreateGLDrawable, glContext);
            }
        }
        finally {
            nativeSurface.unlockSurface();
        }
        if (b && (recreateGLDrawable.getSurfaceWidth() != n || recreateGLDrawable.getSurfaceHeight() != n2)) {
            throw new InternalError("Incomplete resize operation: expected " + n + "x" + n2 + ", has: " + recreateGLDrawable);
        }
        return recreateGLDrawable;
    }
    
    public final void addGLEventListener(final GLEventListener glEventListener) {
        this.addGLEventListener(-1, glEventListener);
    }
    
    public final void addGLEventListener(int size, final GLEventListener glEventListener) {
        synchronized (this.listenersLock) {
            if (0 > size) {
                size = this.listeners.size();
            }
            this.listenersToBeInit.add(glEventListener);
            this.listeners.add(size, glEventListener);
        }
    }
    
    public final GLEventListener removeGLEventListener(final GLEventListener glEventListener) {
        synchronized (this.listenersLock) {
            this.listenersToBeInit.remove(glEventListener);
            return this.listeners.remove(glEventListener) ? glEventListener : null;
        }
    }
    
    public final GLEventListener removeGLEventListener(int n) throws IndexOutOfBoundsException {
        synchronized (this.listenersLock) {
            if (0 > n) {
                n = this.listeners.size() - 1;
            }
            final GLEventListener glEventListener = this.listeners.remove(n);
            this.listenersToBeInit.remove(glEventListener);
            return glEventListener;
        }
    }
    
    public final int getGLEventListenerCount() {
        synchronized (this.listenersLock) {
            return this.listeners.size();
        }
    }
    
    public final GLEventListener getGLEventListener(int n) throws IndexOutOfBoundsException {
        synchronized (this.listenersLock) {
            if (0 > n) {
                n = this.listeners.size() - 1;
            }
            return this.listeners.get(n);
        }
    }
    
    public final boolean areAllGLEventListenerInitialized() {
        synchronized (this.listenersLock) {
            return 0 == this.listenersToBeInit.size();
        }
    }
    
    public final boolean getGLEventListenerInitState(final GLEventListener glEventListener) {
        synchronized (this.listenersLock) {
            return !this.listenersToBeInit.contains(glEventListener);
        }
    }
    
    public final void setGLEventListenerInitState(final GLEventListener glEventListener, final boolean b) {
        synchronized (this.listenersLock) {
            if (b) {
                this.listenersToBeInit.remove(glEventListener);
            }
            else {
                this.listenersToBeInit.add(glEventListener);
            }
        }
    }
    
    public final GLEventListener disposeGLEventListener(final GLAutoDrawable glAutoDrawable, final GLEventListener glEventListener, final boolean b) {
        synchronized (this.listenersLock) {
            if (b) {
                if (this.listeners.remove(glEventListener)) {
                    if (!this.listenersToBeInit.remove(glEventListener)) {
                        glEventListener.dispose(glAutoDrawable);
                    }
                    return glEventListener;
                }
            }
            else if (this.listeners.contains(glEventListener) && !this.listenersToBeInit.contains(glEventListener)) {
                glEventListener.dispose(glAutoDrawable);
                this.listenersToBeInit.add(glEventListener);
                return glEventListener;
            }
        }
        return null;
    }
    
    public final int disposeAllGLEventListener(final GLAutoDrawable glAutoDrawable, final boolean b) throws GLException {
        Throwable t = null;
        int n = 0;
        synchronized (this.listenersLock) {
            if (b) {
                for (int size = this.listeners.size(); 0 < size && 0 < this.listeners.size(); --size) {
                    final GLEventListener glEventListener = this.listeners.remove(0);
                    if (!this.listenersToBeInit.remove(glEventListener)) {
                        try {
                            glEventListener.dispose(glAutoDrawable);
                        }
                        catch (Throwable t2) {
                            if (null == t) {
                                t = t2;
                            }
                            else {
                                ExceptionUtils.dumpThrowable("subsequent", t2);
                            }
                        }
                        ++n;
                    }
                }
            }
            else {
                for (int i = 0; i < this.listeners.size(); ++i) {
                    final GLEventListener glEventListener2 = this.listeners.get(i);
                    if (!this.listenersToBeInit.contains(glEventListener2)) {
                        try {
                            glEventListener2.dispose(glAutoDrawable);
                        }
                        catch (Throwable t3) {
                            if (null == t) {
                                t = t3;
                            }
                            else {
                                ExceptionUtils.dumpThrowable("subsequent", t3);
                            }
                        }
                        this.listenersToBeInit.add(glEventListener2);
                        ++n;
                    }
                }
            }
        }
        if (null != t) {
            this.flushGLRunnables();
            throw GLException.newGLException(t);
        }
        return n;
    }
    
    public final GLEventListener disposeGLEventListener(final GLAutoDrawable glAutoDrawable, final GLDrawable glDrawable, final GLContext glContext, final GLEventListener glEventListener, final boolean b) {
        synchronized (this.listenersLock) {
            if (this.listenersToBeInit.contains(glEventListener)) {
                if (b) {
                    this.listenersToBeInit.remove(glEventListener);
                    return this.listeners.remove(glEventListener) ? glEventListener : null;
                }
                return null;
            }
        }
        final boolean b2 = this.isAnimatorAnimatingOnOtherThread() && this.animatorCtrl.pause();
        final GLEventListener[] array = { null };
        this.invokeGL(glDrawable, glContext, new Runnable() {
            @Override
            public void run() {
                array[0] = GLDrawableHelper.this.disposeGLEventListener(glAutoDrawable, glEventListener, b);
            }
        }, GLDrawableHelper.nop);
        if (b2) {
            this.animatorCtrl.resume();
        }
        return array[0];
    }
    
    public final void disposeAllGLEventListener(final GLAutoDrawable glAutoDrawable, final GLDrawable glDrawable, final GLContext glContext, final boolean b) {
        final boolean b2 = this.isAnimatorAnimatingOnOtherThread() && this.animatorCtrl.pause();
        this.invokeGL(glDrawable, glContext, new Runnable() {
            @Override
            public void run() {
                GLDrawableHelper.this.disposeAllGLEventListener(glAutoDrawable, b);
            }
        }, GLDrawableHelper.nop);
        if (b2) {
            this.animatorCtrl.resume();
        }
    }
    
    private final void init(final GLEventListener glEventListener, final GLAutoDrawable glAutoDrawable, final boolean b) {
        glEventListener.init(glAutoDrawable);
        if (b) {
            glEventListener.reshape(glAutoDrawable, 0, 0, glAutoDrawable.getSurfaceWidth(), glAutoDrawable.getSurfaceHeight());
        }
    }
    
    public final void init(final GLAutoDrawable glAutoDrawable, final boolean b) {
        this.setViewportAndClear(glAutoDrawable, 0, 0, glAutoDrawable.getSurfaceWidth(), glAutoDrawable.getSurfaceHeight());
        synchronized (this.listenersLock) {
            final ArrayList<GLEventListener> listeners = this.listeners;
            final int size = listeners.size();
            if (size > 0) {
                for (int i = 0; i < size; ++i) {
                    final GLEventListener glEventListener = listeners.get(i);
                    this.listenersToBeInit.remove(glEventListener);
                    this.init(glEventListener, glAutoDrawable, b);
                }
            }
        }
    }
    
    public final void display(final GLAutoDrawable glAutoDrawable) {
        this.displayImpl(glAutoDrawable);
        if (this.glRunnableCount > 0 && !this.execGLRunnables(glAutoDrawable)) {
            this.displayImpl(glAutoDrawable);
        }
    }
    
    private final void displayImpl(final GLAutoDrawable glAutoDrawable) {
        synchronized (this.listenersLock) {
            final ArrayList<GLEventListener> listeners = this.listeners;
            for (int size = listeners.size(), i = 0; i < size; ++i) {
                final GLEventListener glEventListener = listeners.get(i);
                if (this.listenersToBeInit.remove(glEventListener)) {
                    this.init(glEventListener, glAutoDrawable, true);
                }
                glEventListener.display(glAutoDrawable);
            }
        }
    }
    
    public final void runForAllGLEventListener(final GLAutoDrawable glAutoDrawable, final GLEventListenerAction glEventListenerAction) {
        synchronized (this.listenersLock) {
            final ArrayList<GLEventListener> listeners = this.listeners;
            for (int size = listeners.size(), i = 0; i < size; ++i) {
                final GLEventListener glEventListener = listeners.get(i);
                if (this.listenersToBeInit.remove(glEventListener)) {
                    this.init(glEventListener, glAutoDrawable, true);
                }
                glEventListenerAction.run(glAutoDrawable, glEventListener);
            }
        }
    }
    
    private final void setViewportAndClear(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
        final GL gl = glAutoDrawable.getGL();
        if (GLDrawableHelper.DEBUG_SETCLEAR) {
            final int glGetError = gl.glGetError();
            if (glGetError != 0) {
                System.err.println("Info: GLDrawableHelper.reshape: pre-exisiting GL error 0x" + Integer.toHexString(glGetError));
                ExceptionUtils.dumpStack(System.err);
            }
        }
        gl.glViewport(n, n2, n3, n4);
        gl.glClear(16384);
    }
    
    public final void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
        this.setViewportAndClear(glAutoDrawable, n, n2, n3, n4);
        synchronized (this.listenersLock) {
            for (int i = 0; i < this.listeners.size(); ++i) {
                final GLEventListener glEventListener = this.listeners.get(i);
                if (this.listenersToBeInit.remove(glEventListener)) {
                    glEventListener.init(glAutoDrawable);
                }
                glEventListener.reshape(glAutoDrawable, n, n2, n3, n4);
            }
        }
    }
    
    private final boolean execGLRunnables(final GLAutoDrawable glAutoDrawable) {
        final ArrayList<GLRunnableTask> glRunnables;
        synchronized (this.glRunnablesLock) {
            if (this.glRunnables.size() <= 0) {
                return true;
            }
            this.glRunnableCount = 0;
            glRunnables = this.glRunnables;
            this.glRunnables = new ArrayList<GLRunnableTask>();
        }
        boolean b = true;
        for (int i = 0; i < glRunnables.size(); ++i) {
            b = (glRunnables.get(i).run(glAutoDrawable) && b);
        }
        return b;
    }
    
    public final void flushGLRunnables() {
        synchronized (this.glRunnablesLock) {
            this.glRunnableCount = 0;
            while (this.glRunnables.size() > 0) {
                this.glRunnables.remove(0).flush();
            }
        }
    }
    
    public final void setAnimator(final GLAnimatorControl animatorCtrl) throws GLException {
        synchronized (this.glRunnablesLock) {
            if (this.animatorCtrl != animatorCtrl && null != animatorCtrl && null != this.animatorCtrl) {
                throw new GLException("Trying to register GLAnimatorControl " + animatorCtrl + ", where " + this.animatorCtrl + " is already registered. Unregister first.");
            }
            this.animatorCtrl = animatorCtrl;
        }
    }
    
    public final GLAnimatorControl getAnimator() {
        synchronized (this.glRunnablesLock) {
            return this.animatorCtrl;
        }
    }
    
    public final boolean isAnimatorStartedOnOtherThread() {
        return null != this.animatorCtrl && (this.animatorCtrl.isStarted() && this.animatorCtrl.getThread() != Thread.currentThread());
    }
    
    public final boolean isAnimatorStarted() {
        return null != this.animatorCtrl && this.animatorCtrl.isStarted();
    }
    
    public final boolean isAnimatorAnimatingOnOtherThread() {
        return null != this.animatorCtrl && (this.animatorCtrl.isAnimating() && this.animatorCtrl.getThread() != Thread.currentThread());
    }
    
    public final boolean isAnimatorAnimating() {
        return null != this.animatorCtrl && this.animatorCtrl.isAnimating();
    }
    
    public static final boolean isLockedByOtherThread(final GLAutoDrawable glAutoDrawable) {
        final Thread currentThread = Thread.currentThread();
        final Thread owner = glAutoDrawable.getUpstreamLock().getOwner();
        if (null != owner && currentThread != owner) {
            return true;
        }
        final NativeSurface nativeSurface = glAutoDrawable.getNativeSurface();
        final Thread thread = (null != nativeSurface) ? nativeSurface.getSurfaceLockOwner() : null;
        return null != thread && currentThread != thread;
    }
    
    public static final boolean isLockedByThisThread(final GLAutoDrawable glAutoDrawable) {
        final Thread currentThread = Thread.currentThread();
        if (currentThread == glAutoDrawable.getUpstreamLock().getOwner()) {
            return true;
        }
        final NativeSurface nativeSurface = glAutoDrawable.getNativeSurface();
        return currentThread == ((null != nativeSurface) ? nativeSurface.getSurfaceLockOwner() : null);
    }
    
    public final boolean invoke(final GLAutoDrawable glAutoDrawable, boolean b, final GLRunnable glRunnable) throws IllegalStateException {
        if (null == glRunnable || null == glAutoDrawable || (b && (!glAutoDrawable.isRealized() || null == glAutoDrawable.getContext()))) {
            return false;
        }
        final Object o = new Object();
        synchronized (o) {
            int animatorAnimatingOnOtherThread;
            final GLRunnableTask glRunnableTask;
            synchronized (this.glRunnablesLock) {
                final boolean threadGLCapable = glAutoDrawable.isThreadGLCapable();
                animatorAnimatingOnOtherThread = (this.isAnimatorAnimatingOnOtherThread() ? 1 : 0);
                if (animatorAnimatingOnOtherThread != 0) {
                    if (b && isLockedByThisThread(glAutoDrawable)) {
                        if (!threadGLCapable) {
                            throw new IllegalStateException("Deferred, wait, isLocked on current and not GL-Thread: thread " + Thread.currentThread());
                        }
                        animatorAnimatingOnOtherThread = 0;
                        b = false;
                    }
                }
                else {
                    if (!threadGLCapable && isLockedByThisThread(glAutoDrawable)) {
                        throw new IllegalStateException("Not deferred, isLocked on current and not GL-Thread: thread " + Thread.currentThread());
                    }
                    b = false;
                }
                glRunnableTask = new GLRunnableTask(glRunnable, b ? o : null, b);
                ++this.glRunnableCount;
                this.glRunnables.add(glRunnableTask);
            }
            if (animatorAnimatingOnOtherThread == 0) {
                glAutoDrawable.display();
            }
            else if (b) {
                try {
                    while (glRunnableTask.isInQueue()) {
                        o.wait();
                    }
                }
                catch (InterruptedException ex) {
                    throw new InterruptedRuntimeException(ex);
                }
                final Throwable throwable = glRunnableTask.getThrowable();
                if (null != throwable) {
                    throw new RuntimeException(throwable);
                }
            }
        }
        return true;
    }
    
    public final boolean invoke(final GLAutoDrawable glAutoDrawable, boolean b, final List<GLRunnable> list) throws IllegalStateException {
        if (null == list || list.size() == 0 || null == glAutoDrawable || (b && (!glAutoDrawable.isRealized() || null == glAutoDrawable.getContext()))) {
            return false;
        }
        final int size = list.size();
        final Object o = new Object();
        synchronized (o) {
            int animatorAnimatingOnOtherThread;
            final GLRunnableTask glRunnableTask;
            synchronized (this.glRunnablesLock) {
                final boolean threadGLCapable = glAutoDrawable.isThreadGLCapable();
                animatorAnimatingOnOtherThread = (this.isAnimatorAnimatingOnOtherThread() ? 1 : 0);
                if (animatorAnimatingOnOtherThread != 0) {
                    if (b && isLockedByThisThread(glAutoDrawable)) {
                        if (!threadGLCapable) {
                            throw new IllegalStateException("Deferred, wait, isLocked on current and not GL-Thread: thread " + Thread.currentThread());
                        }
                        animatorAnimatingOnOtherThread = 0;
                        b = false;
                    }
                }
                else {
                    if (!threadGLCapable && isLockedByThisThread(glAutoDrawable)) {
                        throw new IllegalStateException("Not deferred, isLocked on current and not GL-Thread: thread " + Thread.currentThread());
                    }
                    b = false;
                }
                for (int i = 0; i < size - 1; ++i) {
                    ++this.glRunnableCount;
                    this.glRunnables.add(new GLRunnableTask(list.get(i), null, false));
                }
                glRunnableTask = new GLRunnableTask(list.get(size - 1), b ? o : null, b);
                ++this.glRunnableCount;
                this.glRunnables.add(glRunnableTask);
            }
            if (animatorAnimatingOnOtherThread == 0) {
                glAutoDrawable.display();
            }
            else if (b) {
                try {
                    while (glRunnableTask.isInQueue()) {
                        o.wait();
                    }
                }
                catch (InterruptedException ex) {
                    throw new InterruptedRuntimeException(ex);
                }
                final Throwable throwable = glRunnableTask.getThrowable();
                if (null != throwable) {
                    throw new RuntimeException(throwable);
                }
            }
        }
        return true;
    }
    
    public final void enqueue(final GLRunnable glRunnable) {
        if (null == glRunnable) {
            return;
        }
        synchronized (this.glRunnablesLock) {
            ++this.glRunnableCount;
            this.glRunnables.add(new GLRunnableTask(glRunnable, null, false));
        }
    }
    
    public final void setAutoSwapBufferMode(final boolean autoSwapBufferMode) {
        this.autoSwapBufferMode = autoSwapBufferMode;
    }
    
    public final boolean getAutoSwapBufferMode() {
        return this.autoSwapBufferMode;
    }
    
    private final String getExclusiveContextSwitchString() {
        return (0 == this.exclusiveContextSwitch) ? "nop" : ((0 > this.exclusiveContextSwitch) ? "released" : "claimed");
    }
    
    public final Thread setExclusiveContextThread(final Thread exclusiveContextThread, final GLContext glContext) throws GLException {
        if (GLDrawableHelper.DEBUG) {
            System.err.println("GLDrawableHelper.setExclusiveContextThread(): START switch " + this.getExclusiveContextSwitchString() + ", thread " + this.exclusiveContextThread + " -> " + exclusiveContextThread + " -- currentThread " + Thread.currentThread());
        }
        final Thread exclusiveContextThread2 = this.exclusiveContextThread;
        if (this.exclusiveContextThread == exclusiveContextThread) {
            this.exclusiveContextSwitch = 0;
        }
        else if (null == exclusiveContextThread) {
            this.exclusiveContextSwitch = -1;
        }
        else {
            this.exclusiveContextSwitch = 1;
            if (null != this.exclusiveContextThread) {
                throw new GLException("Release current exclusive Context Thread " + this.exclusiveContextThread + " first");
            }
            if (null != glContext && glContext.isCurrent()) {
                try {
                    forceNativeRelease(glContext);
                }
                catch (Throwable t) {
                    this.flushGLRunnables();
                    throw GLException.newGLException(t);
                }
            }
            this.exclusiveContextThread = exclusiveContextThread;
        }
        if (GLDrawableHelper.DEBUG) {
            System.err.println("GLDrawableHelper.setExclusiveContextThread(): END switch " + this.getExclusiveContextSwitchString() + ", thread " + this.exclusiveContextThread + " -- currentThread " + Thread.currentThread());
        }
        return exclusiveContextThread2;
    }
    
    public final Thread getExclusiveContextThread() {
        return this.exclusiveContextThread;
    }
    
    public final void runOutsideOfExclusiveContextThread(final GLContext glContext, final Runnable runnable) {
        final Thread setExclusiveContextThread = this.setExclusiveContextThread(null, glContext);
        try {
            runnable.run();
        }
        finally {
            this.setExclusiveContextThread(setExclusiveContextThread, glContext);
        }
    }
    
    private static final Runnable getLastInitAction() {
        final WeakReference<Runnable> weakReference = GLDrawableHelper.perThreadInitAction.get();
        if (null != weakReference) {
            final Runnable runnable = weakReference.get();
            if (null == runnable) {
                GLDrawableHelper.perThreadInitAction.set(null);
            }
            return runnable;
        }
        return null;
    }
    
    private static final void setLastInitAction(final Runnable runnable) {
        GLDrawableHelper.perThreadInitAction.set(new WeakReference<Runnable>(runnable));
    }
    
    public final void invokeGL(final GLDrawable glDrawable, final GLContext glContext, final Runnable runnable, final Runnable runnable2) {
        if (null == glContext) {
            if (GLDrawableHelper.DEBUG) {
                ExceptionUtils.dumpThrowable("informal", new GLException("Info: GLDrawableHelper " + this + ".invokeGL(): NULL GLContext"));
            }
            return;
        }
        if (GLDrawableHelper.PERF_STATS) {
            this.invokeGLImplStats(glDrawable, glContext, runnable, runnable2);
        }
        else {
            this.invokeGLImpl(glDrawable, glContext, runnable, runnable2);
        }
    }
    
    public final void disposeGL(final GLAutoDrawable glAutoDrawable, final GLContext glContext, final boolean b) throws GLException {
        GLContext current = GLContext.getCurrent();
        Runnable lastInitAction = null;
        if (current != null) {
            if (current == glContext) {
                current = null;
            }
            else {
                lastInitAction = getLastInitAction();
                current.release();
            }
        }
        GLException ex = null;
        Throwable t = null;
        try {
            final int current2 = glContext.makeCurrent();
            if (current2 != 0) {
                if (2 == current2) {
                    throw new GLException(getThreadName() + " GLDrawableHelper " + this + ".invokeGL(): Dispose case (no init action given): Native context was not created (new ctx): " + glContext);
                }
                if (this.listeners.size() > 0 && null != glAutoDrawable) {
                    try {
                        this.disposeAllGLEventListener(glAutoDrawable, false);
                    }
                    catch (GLException ex2) {
                        ex = ex2;
                    }
                }
            }
        }
        finally {
            try {
                if (b) {
                    glContext.destroy();
                }
                else {
                    forceNativeRelease(glContext);
                }
            }
            catch (Throwable t2) {
                t = t2;
            }
            this.flushGLRunnables();
            if (current != null) {
                final int current3 = current.makeCurrent();
                if (null != lastInitAction && current3 == 2) {
                    lastInitAction.run();
                }
            }
            if (null != ex) {
                if (null != t) {
                    ExceptionUtils.dumpThrowable("subsequent", t);
                }
                throw ex;
            }
            if (null != t) {
                throw GLException.newGLException(t);
            }
        }
    }
    
    private final void invokeGLImpl(final GLDrawable glDrawable, final GLContext glContext, final Runnable runnable, final Runnable lastInitAction) {
        final Thread currentThread = Thread.currentThread();
        Throwable t = null;
        Throwable t2 = null;
        int n;
        boolean b;
        if (null != this.exclusiveContextThread) {
            if (currentThread != this.exclusiveContextThread) {
                return;
            }
            n = ((0 > this.exclusiveContextSwitch) ? 1 : 0);
            b = (n == 0);
            this.exclusiveContextSwitch = 0;
        }
        else {
            n = 0;
            b = false;
        }
        int current = 0;
        GLContext current2 = GLContext.getCurrent();
        Runnable lastInitAction2 = null;
        if (current2 != null) {
            if (current2 == glContext) {
                current = 1;
                current2 = null;
            }
            else {
                lastInitAction2 = getLastInitAction();
                current2.release();
            }
        }
        try {
            int n2;
            if (current == 0) {
                current = glContext.makeCurrent();
                n2 = (b ? 0 : 1);
            }
            else {
                n2 = n;
            }
            if (current != 0) {
                try {
                    setLastInitAction(lastInitAction);
                    if (2 == current) {
                        if (GLDrawableHelper.DEBUG) {
                            System.err.println("GLDrawableHelper " + this + ".invokeGL(): Running initAction");
                        }
                        lastInitAction.run();
                    }
                    runnable.run();
                    if (this.autoSwapBufferMode) {
                        glDrawable.swapBuffers();
                    }
                }
                catch (Throwable t3) {
                    t = t3;
                    if (n != 0) {
                        this.exclusiveContextThread = null;
                        if (GLDrawableHelper.DEBUG) {
                            System.err.println("GLDrawableHelper.invokeGL() - Release ExclusiveContextThread -- currentThread " + Thread.currentThread());
                        }
                    }
                    if (n2 != 0) {
                        try {
                            glContext.release();
                        }
                        catch (Throwable t4) {
                            t2 = t4;
                        }
                    }
                }
                finally {
                    if (n != 0) {
                        this.exclusiveContextThread = null;
                        if (GLDrawableHelper.DEBUG) {
                            System.err.println("GLDrawableHelper.invokeGL() - Release ExclusiveContextThread -- currentThread " + Thread.currentThread());
                        }
                    }
                    if (n2 != 0) {
                        try {
                            glContext.release();
                        }
                        catch (Throwable t5) {
                            t2 = t5;
                        }
                    }
                }
            }
        }
        finally {
            if (current2 != null) {
                final int current3 = current2.makeCurrent();
                if (null != lastInitAction2 && current3 == 2) {
                    lastInitAction2.run();
                }
            }
            if (null != t) {
                this.flushGLRunnables();
                if (null != t2) {
                    ExceptionUtils.dumpThrowable("subsequent", t2);
                }
                throw GLException.newGLException(t);
            }
            if (null != t2) {
                this.flushGLRunnables();
                throw GLException.newGLException(t2);
            }
        }
    }
    
    private final void invokeGLImplStats(final GLDrawable glDrawable, final GLContext glContext, final Runnable runnable, final Runnable lastInitAction) {
        final Thread currentThread = Thread.currentThread();
        Throwable t = null;
        Throwable t2 = null;
        int n;
        boolean b;
        if (null != this.exclusiveContextThread) {
            if (currentThread != this.exclusiveContextThread) {
                return;
            }
            n = ((0 > this.exclusiveContextSwitch) ? 1 : 0);
            b = (n == 0);
        }
        else {
            n = 0;
            b = false;
        }
        int current = 0;
        GLContext current2 = GLContext.getCurrent();
        Runnable lastInitAction2 = null;
        if (current2 != null) {
            if (current2 == glContext) {
                current = 1;
                current2 = null;
            }
            else {
                lastInitAction2 = getLastInitAction();
                current2.release();
            }
        }
        final long currentTimeMillis = System.currentTimeMillis();
        long n2 = 0L;
        long currentTimeMillis2 = 0L;
        long currentTimeMillis3 = 0L;
        long currentTimeMillis4 = 0L;
        boolean b2 = false;
        boolean b3 = false;
        long n4 = 0L;
        try {
            int n3;
            if (current == 0) {
                current = glContext.makeCurrent();
                n3 = (b ? 0 : 1);
                b2 = true;
            }
            else {
                n3 = n;
            }
            if (current != 0) {
                try {
                    setLastInitAction(lastInitAction);
                    if (2 == current) {
                        if (GLDrawableHelper.DEBUG) {
                            System.err.println("GLDrawableHelper " + this + ".invokeGL(): Running initAction");
                        }
                        lastInitAction.run();
                    }
                    currentTimeMillis2 = System.currentTimeMillis();
                    n2 = currentTimeMillis2 - currentTimeMillis;
                    runnable.run();
                    currentTimeMillis3 = System.currentTimeMillis();
                    currentTimeMillis2 = currentTimeMillis3 - currentTimeMillis2;
                    if (this.autoSwapBufferMode) {
                        glDrawable.swapBuffers();
                        currentTimeMillis4 = System.currentTimeMillis();
                        currentTimeMillis3 = currentTimeMillis4 - currentTimeMillis3;
                    }
                }
                catch (Throwable t3) {
                    t = t3;
                    if (n != 0) {
                        this.exclusiveContextSwitch = 0;
                        this.exclusiveContextThread = null;
                        if (GLDrawableHelper.DEBUG) {
                            System.err.println("GLDrawableHelper.invokeGL() - Release ExclusiveContextThread -- currentThread " + Thread.currentThread());
                        }
                    }
                    if (n3 != 0) {
                        try {
                            glContext.release();
                            b3 = true;
                        }
                        catch (Throwable t4) {
                            t2 = t4;
                        }
                    }
                }
                finally {
                    if (n != 0) {
                        this.exclusiveContextSwitch = 0;
                        this.exclusiveContextThread = null;
                        if (GLDrawableHelper.DEBUG) {
                            System.err.println("GLDrawableHelper.invokeGL() - Release ExclusiveContextThread -- currentThread " + Thread.currentThread());
                        }
                    }
                    if (n3 != 0) {
                        try {
                            glContext.release();
                            b3 = true;
                        }
                        catch (Throwable t5) {
                            t2 = t5;
                        }
                    }
                }
            }
        }
        finally {
            n4 = System.currentTimeMillis() - currentTimeMillis4;
            if (current2 != null) {
                final int current3 = current2.makeCurrent();
                if (null != lastInitAction2 && current3 == 2) {
                    lastInitAction2.run();
                }
            }
            if (null != t) {
                this.flushGLRunnables();
                if (null != t2) {
                    ExceptionUtils.dumpThrowable("subsequent", t2);
                }
                throw GLException.newGLException(t);
            }
            if (null != t2) {
                this.flushGLRunnables();
                throw GLException.newGLException(t2);
            }
        }
        final long n5 = System.currentTimeMillis() - currentTimeMillis;
        System.err.println("td0 " + n5 + "ms, fps " + 1.0 / (n5 / 1000.0) + ", td-makeCurrent: " + n2 + "ms, td-render " + currentTimeMillis2 + "ms, td-swap " + currentTimeMillis3 + "ms, td-release " + n4 + "ms, ctx claimed: " + b2 + ", ctx release: " + b3 + ", ctx destroyed " + false);
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    static {
        Debug.initSingleton();
        PERF_STATS = PropertyAccess.isPropertyDefined("jogl.debug.GLDrawable.PerfStats", true);
        DEBUG = GLDrawableImpl.DEBUG;
        DEBUG_SETCLEAR = (GLContext.DEBUG_GL || GLDrawableHelper.DEBUG);
        GLDrawableHelper.nop = new Runnable() {
            @Override
            public void run() {
            }
        };
        perThreadInitAction = new ThreadLocal<WeakReference<Runnable>>();
    }
    
    public interface GLEventListenerAction
    {
        void run(final GLAutoDrawable p0, final GLEventListener p1);
    }
}
