// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import java.io.PrintStream;
import java.util.List;
import com.jogamp.opengl.GLRunnable;
import com.jogamp.common.util.RunnableTask;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GLEventListenerState;
import com.jogamp.opengl.GLSharedContextSetter;
import com.jogamp.opengl.FPSCounter;
import com.jogamp.opengl.GLStateKeeper;
import com.jogamp.opengl.GLAutoDrawable;

public abstract class GLAutoDrawableBase implements GLAutoDrawable, GLStateKeeper, FPSCounter, GLSharedContextSetter
{
    public static final boolean DEBUG;
    protected final GLDrawableHelper helper;
    protected final FPSCounterImpl fpsCounter;
    protected volatile GLDrawableImpl drawable;
    protected volatile GLContextImpl context;
    protected boolean preserveGLELSAtDestroy;
    protected GLEventListenerState glels;
    protected Listener glStateKeeperListener;
    protected final boolean ownsDevice;
    protected int additionalCtxCreationFlags;
    protected volatile boolean sendReshape;
    protected volatile boolean sendDestroy;
    protected final Runnable defaultInitAction;
    protected final Runnable defaultDisplayAction;
    
    public GLAutoDrawableBase(final GLDrawableImpl drawable, final GLContextImpl context, final boolean ownsDevice) {
        this.helper = new GLDrawableHelper();
        this.fpsCounter = new FPSCounterImpl();
        this.additionalCtxCreationFlags = 0;
        this.sendReshape = false;
        this.sendDestroy = false;
        this.defaultInitAction = new Runnable() {
            @Override
            public final void run() {
                GLAutoDrawableBase.this.helper.init(GLAutoDrawableBase.this, !GLAutoDrawableBase.this.sendReshape);
                GLAutoDrawableBase.this.resetFPSCounter();
            }
        };
        this.defaultDisplayAction = new Runnable() {
            @Override
            public final void run() {
                if (GLAutoDrawableBase.this.sendReshape) {
                    GLAutoDrawableBase.this.helper.reshape(GLAutoDrawableBase.this, 0, 0, GLAutoDrawableBase.this.getSurfaceWidth(), GLAutoDrawableBase.this.getSurfaceHeight());
                    GLAutoDrawableBase.this.sendReshape = false;
                }
                GLAutoDrawableBase.this.helper.display(GLAutoDrawableBase.this);
                GLAutoDrawableBase.this.fpsCounter.tickFPS();
            }
        };
        this.drawable = drawable;
        this.context = context;
        this.preserveGLELSAtDestroy = false;
        this.glels = null;
        this.glStateKeeperListener = null;
        this.ownsDevice = ownsDevice;
        if (null != context && null != drawable) {
            context.setGLDrawable(drawable, false);
        }
        this.resetFPSCounter();
    }
    
    @Override
    public final void setSharedContext(final GLContext glContext) throws IllegalStateException {
        this.helper.setSharedContext(this.context, glContext);
    }
    
    @Override
    public final void setSharedAutoDrawable(final GLAutoDrawable glAutoDrawable) throws IllegalStateException {
        this.helper.setSharedAutoDrawable(this, glAutoDrawable);
    }
    
    @Override
    public final Listener setGLStateKeeperListener(final Listener glStateKeeperListener) {
        final Listener glStateKeeperListener2 = this.glStateKeeperListener;
        this.glStateKeeperListener = glStateKeeperListener;
        return glStateKeeperListener2;
    }
    
    @Override
    public final boolean preserveGLStateAtDestroy(final boolean preserveGLELSAtDestroy) {
        final boolean glStatePreservationSupported = this.isGLStatePreservationSupported();
        if (glStatePreservationSupported) {
            if (GLAutoDrawableBase.DEBUG) {
                System.err.println("GLAutoDrawableBase.setPreserveGLStateAtDestroy: (" + getThreadName() + "): " + this.preserveGLELSAtDestroy + " -> " + preserveGLELSAtDestroy + " - surfaceHandle 0x" + Long.toHexString((null != this.getNativeSurface()) ? this.getNativeSurface().getSurfaceHandle() : 0L));
            }
            this.preserveGLELSAtDestroy = preserveGLELSAtDestroy;
        }
        return glStatePreservationSupported;
    }
    
    @Override
    public boolean isGLStatePreservationSupported() {
        return false;
    }
    
    @Override
    public final GLEventListenerState getPreservedGLState() {
        return this.glels;
    }
    
    @Override
    public final GLEventListenerState clearPreservedGLState() {
        final GLEventListenerState glels = this.glels;
        this.glels = null;
        return glels;
    }
    
    protected final boolean preserveGLEventListenerState() throws IllegalStateException {
        if (null != this.glels) {
            throw new IllegalStateException("GLEventListenerState already pulled");
        }
        if (null != this.context && this.context.isCreated()) {
            if (null != this.glStateKeeperListener) {
                this.glStateKeeperListener.glStatePreserveNotify(this);
            }
            this.glels = GLEventListenerState.moveFrom(this);
            return null != this.glels;
        }
        return false;
    }
    
    protected final boolean restoreGLEventListenerState() {
        if (null != this.glels) {
            this.glels.moveTo(this);
            this.glels = null;
            if (null != this.glStateKeeperListener) {
                this.glStateKeeperListener.glStateRestored(this);
            }
            return true;
        }
        return false;
    }
    
    protected final void defaultWindowRepaintOp() {
        final GLDrawableImpl drawable = this.drawable;
        if (null != drawable && drawable.isRealized() && !drawable.getNativeSurface().isSurfaceLockedByOtherThread() && !this.helper.isAnimatorAnimatingOnOtherThread()) {
            this.display();
        }
    }
    
    protected final void defaultWindowResizedOp(final int n, final int n2) throws NativeWindowException, GLException {
        GLDrawableImpl drawable = this.drawable;
        if (null != drawable) {
            if (GLAutoDrawableBase.DEBUG) {
                System.err.println("GLAutoDrawableBase.sizeChanged: (" + getThreadName() + "): " + n + "x" + n2 + " - surfaceHandle 0x" + Long.toHexString((null != this.getNativeSurface()) ? this.getNativeSurface().getSurfaceHandle() : 0L));
            }
            if (!drawable.getChosenGLCapabilities().isOnscreen()) {
                final RecursiveLock upstreamLock = this.getUpstreamLock();
                upstreamLock.lock();
                try {
                    final GLDrawableImpl resizeOffscreenDrawable = GLDrawableHelper.resizeOffscreenDrawable(drawable, this.context, n, n2);
                    if (drawable != resizeOffscreenDrawable) {
                        drawable = resizeOffscreenDrawable;
                        this.drawable = resizeOffscreenDrawable;
                    }
                }
                finally {
                    upstreamLock.unlock();
                }
            }
            this.sendReshape = true;
            if (drawable.isRealized() && !drawable.getNativeSurface().isSurfaceLockedByOtherThread() && !this.helper.isAnimatorAnimatingOnOtherThread()) {
                this.display();
            }
        }
    }
    
    protected final void defaultWindowDestroyNotifyOp() {
        final NativeSurface nativeSurface = this.getNativeSurface();
        if (!(nativeSurface instanceof WindowClosingProtocol) || WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE == ((WindowClosingProtocol)nativeSurface).getDefaultCloseOperation()) {
            try {
                this.destroyAvoidAwareOfLocking();
            }
            catch (Throwable t) {
                ExceptionUtils.dumpThrowable("ignored", t);
            }
        }
    }
    
    protected final void destroyAvoidAwareOfLocking() {
        final NativeSurface nativeSurface = this.getNativeSurface();
        final GLAnimatorControl animator = this.helper.getAnimator();
        if (this.helper.isAnimatorStartedOnOtherThread()) {
            final boolean pause = animator.pause();
            this.destroy();
            if (pause) {
                animator.resume();
            }
        }
        else if (null != nativeSurface && nativeSurface.isSurfaceLockedByOtherThread()) {
            this.sendDestroy = true;
        }
        else {
            this.destroy();
        }
    }
    
    protected final void defaultDestroy() {
        final RecursiveLock upstreamLock = this.getUpstreamLock();
        upstreamLock.lock();
        try {
            this.destroyImplInLock();
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    protected void destroyImplInLock() {
        if (this.preserveGLELSAtDestroy) {
            this.preserveGLStateAtDestroy(false);
            this.preserveGLEventListenerState();
        }
        GLException ex = null;
        if (null != this.context) {
            if (this.context.isCreated()) {
                try {
                    this.helper.disposeGL(this, this.context, true);
                }
                catch (GLException ex2) {
                    ex = ex2;
                }
            }
            this.context = null;
        }
        Throwable t = null;
        Throwable t2 = null;
        if (null != this.drawable) {
            final AbstractGraphicsDevice device = this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice();
            try {
                this.drawable.setRealized(false);
            }
            catch (Throwable t3) {
                t = t3;
            }
            this.drawable = null;
            try {
                if (this.ownsDevice) {
                    device.close();
                }
            }
            catch (Throwable t4) {
                t2 = t4;
            }
        }
        if (null != ex) {
            throw ex;
        }
        if (null != t) {
            throw GLException.newGLException(t);
        }
        if (null != t2) {
            throw GLException.newGLException(t2);
        }
    }
    
    public final void defaultSwapBuffers() throws GLException {
        final RecursiveLock upstreamLock = this.getUpstreamLock();
        upstreamLock.lock();
        try {
            if (null != this.drawable) {
                this.drawable.swapBuffers();
            }
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    protected final void defaultDisplay() {
        if (this.sendDestroy) {
            this.sendDestroy = false;
            this.destroy();
            return;
        }
        final RecursiveLock upstreamLock = this.getUpstreamLock();
        upstreamLock.lock();
        try {
            if (null == this.context) {
                boolean b = false;
                final GLDrawableImpl drawable = this.drawable;
                if (null != drawable && drawable.isRealized() && 0 < drawable.getSurfaceWidth() * drawable.getSurfaceHeight()) {
                    final GLContext[] array = { null };
                    if (!this.helper.isSharedGLContextPending(array) && !this.restoreGLEventListenerState()) {
                        (this.context = (GLContextImpl)drawable.createContext(array[0])).setContextCreationFlags(this.additionalCtxCreationFlags);
                        b = true;
                        this.helper.invokeGL(drawable, this.context, this.defaultDisplayAction, this.defaultInitAction);
                    }
                }
                if (GLAutoDrawableBase.DEBUG) {
                    System.err.println("GLAutoDrawableBase.defaultDisplay: contextCreated " + b);
                }
            }
            else {
                this.helper.invokeGL(this.drawable, this.context, this.defaultDisplayAction, this.defaultInitAction);
            }
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    protected final GLEventListener defaultDisposeGLEventListener(final GLEventListener glEventListener, final boolean b) {
        final RecursiveLock upstreamLock = this.getUpstreamLock();
        upstreamLock.lock();
        try {
            return this.helper.disposeGLEventListener(this, this.drawable, this.context, glEventListener, b);
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    @Override
    public final GLDrawable getDelegatedDrawable() {
        return this.drawable;
    }
    
    @Override
    public final GLContext getContext() {
        return this.context;
    }
    
    @Override
    public final GLContext setContext(final GLContext glContext, final boolean b) {
        final RecursiveLock upstreamLock = this.getUpstreamLock();
        upstreamLock.lock();
        try {
            final GLContextImpl context = this.context;
            GLDrawableHelper.switchContext(this.drawable, context, b, glContext, this.additionalCtxCreationFlags);
            this.context = (GLContextImpl)glContext;
            return context;
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    @Override
    public final GL getGL() {
        final GLContextImpl context = this.context;
        if (context == null) {
            return null;
        }
        return context.getGL();
    }
    
    @Override
    public final GL setGL(final GL gl) {
        final GLContextImpl context = this.context;
        if (context != null) {
            context.setGL(gl);
            return gl;
        }
        return null;
    }
    
    @Override
    public final void addGLEventListener(final GLEventListener glEventListener) {
        this.helper.addGLEventListener(glEventListener);
    }
    
    @Override
    public final void addGLEventListener(final int n, final GLEventListener glEventListener) throws IndexOutOfBoundsException {
        this.helper.addGLEventListener(n, glEventListener);
    }
    
    @Override
    public int getGLEventListenerCount() {
        return this.helper.getGLEventListenerCount();
    }
    
    @Override
    public GLEventListener getGLEventListener(final int n) throws IndexOutOfBoundsException {
        return this.helper.getGLEventListener(n);
    }
    
    @Override
    public boolean areAllGLEventListenerInitialized() {
        return this.helper.areAllGLEventListenerInitialized();
    }
    
    @Override
    public boolean getGLEventListenerInitState(final GLEventListener glEventListener) {
        return this.helper.getGLEventListenerInitState(glEventListener);
    }
    
    @Override
    public void setGLEventListenerInitState(final GLEventListener glEventListener, final boolean b) {
        this.helper.setGLEventListenerInitState(glEventListener, b);
    }
    
    @Override
    public GLEventListener disposeGLEventListener(final GLEventListener glEventListener, final boolean b) {
        return this.defaultDisposeGLEventListener(glEventListener, b);
    }
    
    @Override
    public final GLEventListener removeGLEventListener(final GLEventListener glEventListener) {
        return this.helper.removeGLEventListener(glEventListener);
    }
    
    @Override
    public final void setAnimator(final GLAnimatorControl animator) throws GLException {
        this.helper.setAnimator(animator);
    }
    
    @Override
    public final GLAnimatorControl getAnimator() {
        return this.helper.getAnimator();
    }
    
    @Override
    public final Thread setExclusiveContextThread(final Thread thread) throws GLException {
        return this.helper.setExclusiveContextThread(thread, this.context);
    }
    
    @Override
    public final Thread getExclusiveContextThread() {
        return this.helper.getExclusiveContextThread();
    }
    
    public final void invokeOnCurrentThread(final Runnable runnable) {
        this.helper.runOutsideOfExclusiveContextThread(this.context, runnable);
    }
    
    public final RunnableTask invokeOnNewThread(final ThreadGroup threadGroup, final boolean b, final Runnable runnable) {
        return RunnableTask.invokeOnNewThread(threadGroup, null, b, new Runnable() {
            @Override
            public final void run() {
                GLAutoDrawableBase.this.helper.runOutsideOfExclusiveContextThread(GLAutoDrawableBase.this.context, runnable);
            }
        });
    }
    
    @Override
    public final boolean invoke(final boolean b, final GLRunnable glRunnable) throws IllegalStateException {
        return this.helper.invoke(this, b, glRunnable);
    }
    
    @Override
    public boolean invoke(final boolean b, final List<GLRunnable> list) throws IllegalStateException {
        return this.helper.invoke(this, b, list);
    }
    
    @Override
    public void flushGLRunnables() {
        this.helper.flushGLRunnables();
    }
    
    @Override
    public final void setAutoSwapBufferMode(final boolean autoSwapBufferMode) {
        this.helper.setAutoSwapBufferMode(autoSwapBufferMode);
    }
    
    @Override
    public final boolean getAutoSwapBufferMode() {
        return this.helper.getAutoSwapBufferMode();
    }
    
    @Override
    public final void setContextCreationFlags(final int additionalCtxCreationFlags) {
        this.additionalCtxCreationFlags = additionalCtxCreationFlags;
        final GLContextImpl context = this.context;
        if (null != context) {
            context.setContextCreationFlags(this.additionalCtxCreationFlags);
        }
    }
    
    @Override
    public final int getContextCreationFlags() {
        return this.additionalCtxCreationFlags;
    }
    
    @Override
    public final boolean isThreadGLCapable() {
        return true;
    }
    
    @Override
    public final void setUpdateFPSFrames(final int n, final PrintStream printStream) {
        this.fpsCounter.setUpdateFPSFrames(n, printStream);
    }
    
    @Override
    public final void resetFPSCounter() {
        this.fpsCounter.resetFPSCounter();
    }
    
    @Override
    public final int getUpdateFPSFrames() {
        return this.fpsCounter.getUpdateFPSFrames();
    }
    
    @Override
    public final long getFPSStartTime() {
        return this.fpsCounter.getFPSStartTime();
    }
    
    @Override
    public final long getLastFPSUpdateTime() {
        return this.fpsCounter.getLastFPSUpdateTime();
    }
    
    @Override
    public final long getLastFPSPeriod() {
        return this.fpsCounter.getLastFPSPeriod();
    }
    
    @Override
    public final float getLastFPS() {
        return this.fpsCounter.getLastFPS();
    }
    
    @Override
    public final int getTotalFPSFrames() {
        return this.fpsCounter.getTotalFPSFrames();
    }
    
    @Override
    public final long getTotalFPSDuration() {
        return this.fpsCounter.getTotalFPSDuration();
    }
    
    @Override
    public final float getTotalFPS() {
        return this.fpsCounter.getTotalFPS();
    }
    
    @Override
    public final GLContext createContext(final GLContext glContext) {
        final RecursiveLock upstreamLock = this.getUpstreamLock();
        upstreamLock.lock();
        try {
            if (this.drawable != null) {
                final GLContext context = this.drawable.createContext(glContext);
                context.setContextCreationFlags(this.additionalCtxCreationFlags);
                return context;
            }
            return null;
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    @Override
    public final void setRealized(final boolean realized) {
        final RecursiveLock upstreamLock = this.getUpstreamLock();
        upstreamLock.lock();
        try {
            final GLDrawableImpl drawable = this.drawable;
            if (null == drawable || (realized && (0 >= drawable.getSurfaceWidth() || 0 >= drawable.getSurfaceHeight()))) {
                return;
            }
            drawable.setRealized(realized);
            if (realized && drawable.isRealized()) {
                this.sendReshape = true;
            }
        }
        finally {
            upstreamLock.unlock();
        }
    }
    
    @Override
    public final boolean isRealized() {
        final GLDrawableImpl drawable = this.drawable;
        return null != drawable && drawable.isRealized();
    }
    
    @Override
    public int getSurfaceWidth() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getSurfaceWidth() : 0;
    }
    
    @Override
    public int getSurfaceHeight() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getSurfaceHeight() : 0;
    }
    
    @Override
    public boolean isGLOriented() {
        final GLDrawableImpl drawable = this.drawable;
        return null == drawable || drawable.isGLOriented();
    }
    
    @Override
    public final GLCapabilitiesImmutable getChosenGLCapabilities() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getChosenGLCapabilities() : null;
    }
    
    @Override
    public final GLCapabilitiesImmutable getRequestedGLCapabilities() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getRequestedGLCapabilities() : null;
    }
    
    @Override
    public final GLProfile getGLProfile() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getGLProfile() : null;
    }
    
    @Override
    public final NativeSurface getNativeSurface() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getNativeSurface() : null;
    }
    
    @Override
    public final long getHandle() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getHandle() : 0L;
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[ \n\tHelper: " + this.helper + ", \n\tDrawable: " + this.drawable + ", \n\tContext: " + this.context + "]";
    }
    
    static {
        DEBUG = GLDrawableImpl.DEBUG;
    }
}
