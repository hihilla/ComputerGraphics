// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.GLDebugMessage;
import java.nio.IntBuffer;
import com.jogamp.gluegen.runtime.opengl.GLNameResolver;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import jogamp.nativewindow.x11.X11Util;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import java.security.AccessController;
import com.jogamp.common.os.DynamicLookupHelper;
import java.security.PrivilegedAction;
import java.lang.reflect.Method;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.common.util.VersionNumberString;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.common.os.Platform;
import java.util.Iterator;
import java.util.Map;
import java.util.IdentityHashMap;
import com.jogamp.common.util.VersionNumber;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLDebugListener;
import com.jogamp.opengl.GLPipelineFactory;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLDrawable;
import java.util.HashMap;
import com.jogamp.opengl.GL;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import com.jogamp.opengl.GLContext;

public abstract class GLContextImpl extends GLContext
{
    private String contextFQN;
    private int additionalCtxCreationFlags;
    protected ExtensionAvailabilityCache extensionAvailability;
    private ProcAddressTable glProcAddressTable;
    private String glVendor;
    private String glRenderer;
    private String glRendererLowerCase;
    private String glVersion;
    private boolean glGetPtrInit;
    private long glGetStringPtr;
    private long glGetIntegervPtr;
    private final GLBufferObjectTracker bufferObjectTracker;
    private final GLBufferStateTracker bufferStateTracker;
    private final GLStateTracker glStateTracker;
    private GLDebugMessageHandler glDebugHandler;
    private final int[] boundFBOTarget;
    private int defaultVAO;
    protected GLDrawableImpl drawable;
    protected GLDrawableImpl drawableRead;
    private boolean isSurfaceless;
    private boolean pixelDataEvaluated;
    private int pixelDataFormat;
    private int pixelDataType;
    private int currentSwapInterval;
    protected GL gl;
    protected static final Object mappedContextTypeObjectLock;
    protected static final HashMap<String, ExtensionAvailabilityCache> mappedExtensionAvailabilityCache;
    protected static final HashMap<String, ProcAddressTable> mappedGLProcAddress;
    protected static final HashMap<String, ProcAddressTable> mappedGLXProcAddress;
    private Throwable lastCtxReleaseStack;
    private static MappedGLVersionListener mapGLVersionListener;
    
    public static void shutdownImpl() {
        GLContextImpl.mappedExtensionAvailabilityCache.clear();
        GLContextImpl.mappedGLProcAddress.clear();
        GLContextImpl.mappedGLXProcAddress.clear();
    }
    
    public GLContextImpl(final GLDrawableImpl glDrawableImpl, final GLContext glContext) {
        this.glGetPtrInit = false;
        this.glGetStringPtr = 0L;
        this.glGetIntegervPtr = 0L;
        this.glStateTracker = new GLStateTracker();
        this.glDebugHandler = null;
        this.boundFBOTarget = new int[] { 0, 0 };
        this.defaultVAO = 0;
        this.isSurfaceless = false;
        this.lastCtxReleaseStack = null;
        if (null == glDrawableImpl) {
            throw new IllegalArgumentException("Null drawable");
        }
        this.bufferStateTracker = new GLBufferStateTracker();
        if (null != glContext) {
            GLContextShareSet.registerSharing(this, glContext);
            this.bufferObjectTracker = ((GLContextImpl)glContext).getBufferObjectTracker();
            if (null == this.bufferObjectTracker) {
                throw new InternalError("shared-master context hash null GLBufferObjectTracker: " + GLContext.toHexString(glContext.hashCode()));
            }
        }
        else {
            this.bufferObjectTracker = new GLBufferObjectTracker();
        }
        this.drawable = glDrawableImpl;
        this.drawableRead = glDrawableImpl;
        this.glDebugHandler = new GLDebugMessageHandler(this);
    }
    
    private final void clearStates() {
        if (!GLContextShareSet.hasCreatedSharedLeft(this)) {
            this.bufferObjectTracker.clear();
        }
        this.bufferStateTracker.clear();
        this.glStateTracker.setEnabled(false);
        this.glStateTracker.clearStates();
    }
    
    @Override
    protected void resetStates(final boolean b) {
        if (!b) {
            this.clearStates();
        }
        this.extensionAvailability = null;
        this.glProcAddressTable = null;
        this.gl = null;
        this.contextFQN = null;
        this.additionalCtxCreationFlags = 0;
        this.glVendor = "";
        this.glRenderer = this.glVendor;
        this.glRendererLowerCase = this.glRenderer;
        this.glVersion = this.glVendor;
        this.glGetPtrInit = false;
        this.glGetStringPtr = 0L;
        this.glGetIntegervPtr = 0L;
        if (!b && null != this.boundFBOTarget) {
            this.boundFBOTarget[0] = 0;
            this.boundFBOTarget[1] = 0;
        }
        this.isSurfaceless = false;
        this.pixelDataEvaluated = false;
        this.currentSwapInterval = 0;
        super.resetStates(b);
    }
    
    @Override
    public final GLDrawable setGLReadDrawable(final GLDrawable glDrawable) {
        if (!this.isGLReadDrawableAvailable()) {
            throw new GLException("Setting read drawable feature not available");
        }
        final Thread currentThread = Thread.currentThread();
        if (this.lock.isLockedByOtherThread()) {
            throw new GLException("GLContext current by other thread " + this.lock.getOwner().getName() + ", operation not allowed on this thread " + currentThread.getName());
        }
        final boolean owner = this.lock.isOwner(currentThread);
        if (owner && this.lock.getHoldCount() > 1) {
            throw new GLException("GLContext is recursively locked - unsupported for setGLDrawable(..)");
        }
        if (owner) {
            this.release(false);
        }
        final GLDrawableImpl drawableRead = this.drawableRead;
        this.drawableRead = (GLDrawableImpl)((null != glDrawable) ? glDrawable : this.drawable);
        if (owner) {
            this.makeCurrent();
        }
        return drawableRead;
    }
    
    @Override
    public final GLDrawable getGLReadDrawable() {
        return this.drawableRead;
    }
    
    @Override
    public final GLDrawable setGLDrawable(final GLDrawable glDrawable, final boolean b) {
        final Thread currentThread = Thread.currentThread();
        if (this.lock.isLockedByOtherThread()) {
            throw new GLException("GLContext current by other thread " + this.lock.getOwner().getName() + ", operation not allowed on this thread " + currentThread.getName());
        }
        final boolean owner = this.lock.isOwner(currentThread);
        if (owner && this.lock.getHoldCount() > 1) {
            throw new GLException("GLContext is recursively locked - unsupported for setGLDrawable(..)");
        }
        if (this.drawable == glDrawable && (b || this.drawableRead == glDrawable)) {
            return this.drawable;
        }
        final GLDrawableImpl drawable = this.drawable;
        final GLDrawableImpl drawableRead = this.drawableRead;
        if (this.isCreated() && null != drawable && drawable.isRealized()) {
            if (!owner) {
                this.makeCurrent();
            }
            this.gl.glFinish();
            this.associateDrawable(false);
            if (!owner) {
                this.release(false);
            }
        }
        if (owner) {
            this.release(false);
        }
        if (!b || this.drawableRead == this.drawable) {
            this.drawableRead = (GLDrawableImpl)glDrawable;
        }
        this.drawableRetargeted |= (null != this.drawable && glDrawable != this.drawable);
        this.drawable = (GLDrawableImpl)glDrawable;
        if (this.isCreated() && null != this.drawable && this.drawable.isRealized()) {
            int current = 0;
            Throwable t = null;
            try {
                current = this.makeCurrent(true);
            }
            catch (Throwable t2) {
                t = t2;
            }
            finally {
                if (current == 0) {
                    this.drawableRead = drawableRead;
                    this.drawable = drawable;
                    if (this.drawable.isRealized()) {
                        this.makeCurrent(true);
                    }
                    if (!owner) {
                        this.release(false);
                    }
                    final String string = "Error: makeCurrent() failed with new drawable " + glDrawable;
                    if (null != t) {
                        throw new GLException(string, t);
                    }
                    throw new GLException(string);
                }
            }
            if (!owner) {
                this.release(false);
            }
        }
        return drawable;
    }
    
    @Override
    public final GLDrawable getGLDrawable() {
        return this.drawable;
    }
    
    public final GLDrawableImpl getDrawableImpl() {
        return this.drawable;
    }
    
    @Override
    public final GL getRootGL() {
        GL gl = this.gl;
        for (GL gl2 = gl.getDownstreamGL(); null != gl2; gl2 = gl.getDownstreamGL()) {
            gl = gl2;
        }
        return gl;
    }
    
    @Override
    public final GL getGL() {
        return this.gl;
    }
    
    @Override
    public GL setGL(final GL gl) {
        if (GLContextImpl.DEBUG) {
            System.err.println("Info: setGL (OpenGL " + this.getGLVersion() + "): " + GLContext.getThreadName() + ", " + ((null != this.gl) ? (this.gl.getClass().getSimpleName() + ", " + this.gl.toString()) : "<null>") + " -> " + ((null != gl) ? (gl.getClass().getSimpleName() + ", " + gl.toString()) : "<null>"));
            ExceptionUtils.dumpStack(System.err);
        }
        return this.gl = gl;
    }
    
    @Override
    public final int getDefaultVAO() {
        return this.defaultVAO;
    }
    
    protected void drawableUpdatedNotify() throws GLException {
    }
    
    public abstract Object getPlatformGLExtensions();
    
    @Override
    public void release() throws GLException {
        this.release(false);
    }
    
    private String getTraceSwitchMsg() {
        return "obj " + GLContext.toHexString(this.hashCode()) + ", ctx " + GLContext.toHexString(this.contextHandle) + ", isShared " + GLContextShareSet.isShared(this) + ", surf " + (null != this.drawable) + " " + GLContext.toHexString((null != this.drawable) ? this.drawable.getHandle() : 0L) + ", " + this.lock;
    }
    
    private void release(final boolean b) throws GLException {
        if (GLContextImpl.TRACE_SWITCH) {
            System.err.println(GLContext.getThreadName() + ": GLContext.ContextSwitch[release.0, inDestruction: " + b + "]: " + this.getTraceSwitchMsg());
        }
        if (!this.lock.isOwner(Thread.currentThread())) {
            final String string = GLContext.getThreadName() + ": Context not current on thread, inDestruction: " + b + ", " + this.getTraceSwitchMsg();
            if (GLContextImpl.DEBUG_TRACE_SWITCH) {
                System.err.println(string);
                if (null != this.lastCtxReleaseStack) {
                    System.err.print("Last release call: ");
                    this.lastCtxReleaseStack.printStackTrace();
                }
                else {
                    System.err.println("Last release call: NONE");
                }
            }
            throw new GLException(string);
        }
        Throwable t = null;
        final boolean b2 = (b || this.lock.getHoldCount() == 1) && 0L != this.contextHandle;
        try {
            if (b2) {
                if (!b) {
                    try {
                        this.contextMadeCurrent(false);
                    }
                    catch (Throwable t2) {
                        t = t2;
                    }
                }
                this.releaseImpl();
            }
        }
        finally {
            if (b2) {
                GLContext.setCurrent(null);
            }
            this.lock.unlock();
            this.drawable.unlockSurface();
            if (GLContextImpl.DEBUG_TRACE_SWITCH) {
                final String string2 = GLContext.getThreadName() + ": GLContext.ContextSwitch[release.X]: " + (b2 ? "switch" : "keep  ") + " - " + this.getTraceSwitchMsg();
                this.lastCtxReleaseStack = new Throwable(string2);
                if (GLContextImpl.TRACE_SWITCH) {
                    System.err.println(string2);
                }
            }
        }
        if (null != t) {
            throw new GLException("GLContext.release(false) during GLDrawableImpl.contextMadeCurrent(this, false)", t);
        }
    }
    
    protected abstract void releaseImpl() throws GLException;
    
    @Override
    public final void destroy() {
        if (GLContextImpl.DEBUG_TRACE_SWITCH) {
            System.err.println(GLContext.getThreadName() + ": GLContextImpl.destroy.0: " + this.getTraceSwitchMsg());
        }
        if (0L != this.contextHandle) {
            if (null == this.drawable) {
                throw new GLException("GLContext created but drawable is null: " + this.toString());
            }
            if (1 >= this.drawable.lockSurface()) {
                throw new GLException("Surface not ready to lock: " + this.drawable);
            }
            Throwable t = null;
            try {
                if (!this.drawable.isRealized()) {
                    throw new GLException("GLContext created but drawable not realized: " + this.toString());
                }
                this.lock.lock();
                if (GLContextImpl.DEBUG_TRACE_SWITCH && this.lock.getHoldCount() > 2) {
                    System.err.println(GLContext.getThreadName() + ": GLContextImpl.destroy: Lock was hold more than once - makeCurrent/release imbalance: " + this.getTraceSwitchMsg());
                    ExceptionUtils.dumpStack(System.err);
                }
                try {
                    if (this.lock.getHoldCount() == 1 && 0 == this.makeCurrent()) {
                        throw new GLException("GLContext.makeCurrent() failed: " + this.toString());
                    }
                    try {
                        this.associateDrawable(false);
                    }
                    catch (Throwable t2) {
                        t = t2;
                    }
                    if (0 != this.defaultVAO) {
                        final int[] array = { this.defaultVAO };
                        final GL2ES3 gl2ES3 = this.gl.getRootGL().getGL2ES3();
                        gl2ES3.glBindVertexArray(0);
                        gl2ES3.glDeleteVertexArrays(1, array, 0);
                        this.defaultVAO = 0;
                    }
                    this.glDebugHandler.enable(false);
                    if (this.lock.getHoldCount() > 1) {
                        this.release(true);
                    }
                    this.destroyImpl();
                    this.contextHandle = 0L;
                    this.glDebugHandler = null;
                    if (GLContextShareSet.contextDestroyed(this) && !GLContextShareSet.hasCreatedSharedLeft(this)) {
                        GLContextShareSet.unregisterSharing(this);
                    }
                    this.resetStates(false);
                }
                finally {
                    this.lock.unlock();
                    if (GLContextImpl.DEBUG_TRACE_SWITCH) {
                        System.err.println(GLContext.getThreadName() + ": GLContextImpl.destroy.X: " + this.getTraceSwitchMsg());
                    }
                }
            }
            finally {
                this.drawable.unlockSurface();
            }
            if (null != t) {
                throw new GLException("Exception @ destroy's associateDrawable(false)", t);
            }
        }
        else {
            this.resetStates(false);
        }
    }
    
    protected abstract void destroyImpl() throws GLException;
    
    @Override
    public final void copy(final GLContext glContext, final int n) throws GLException {
        if (glContext.getHandle() == 0L) {
            throw new GLException("Source OpenGL context has not been created");
        }
        if (this.getHandle() == 0L) {
            throw new GLException("Destination OpenGL context has not been created");
        }
        if (1 >= this.drawable.lockSurface()) {
            throw new GLException("Surface not ready to lock");
        }
        try {
            this.copyImpl(glContext, n);
        }
        finally {
            this.drawable.unlockSurface();
        }
    }
    
    protected abstract void copyImpl(final GLContext p0, final int p1) throws GLException;
    
    protected final boolean isSurfaceless() {
        return this.isSurfaceless;
    }
    
    @Override
    public final int makeCurrent() throws GLException {
        return this.makeCurrent(false);
    }
    
    protected final int makeCurrent(boolean b) throws GLException {
        final boolean b2 = null != this.drawable;
        if (GLContextImpl.TRACE_SWITCH) {
            System.err.println(GLContext.getThreadName() + ": GLContext.ContextSwitch[makeCurrent.0]: " + this.getTraceSwitchMsg());
        }
        if (!b2) {
            if (GLContextImpl.DEBUG_TRACE_SWITCH) {
                System.err.println(GLContext.getThreadName() + ": GLContext.ContextSwitch[makeCurrent.X0]: NULL Drawable - CONTEXT_NOT_CURRENT - " + this.getTraceSwitchMsg());
            }
            return 0;
        }
        final int lockSurface = this.drawable.lockSurface();
        if (1 >= lockSurface) {
            if (GLContextImpl.DEBUG_TRACE_SWITCH) {
                System.err.println(GLContext.getThreadName() + ": GLContext.ContextSwitch[makeCurrent.X1]: Surface Not Ready - CONTEXT_NOT_CURRENT - " + this.getTraceSwitchMsg());
            }
            return 0;
        }
        int n = 1;
        int currentWithinLock = 0;
        try {
            if (this.drawable.isRealized()) {
                this.lock.lock();
                try {
                    if (0L == this.drawable.getHandle() && !this.isSurfaceless) {
                        if (GLContextImpl.DEBUG) {
                            System.err.println(GLContext.getThreadName() + ": GLContext.makeCurrent: Surfaceless evaluate");
                        }
                        if (this.hasRendererQuirk(22)) {
                            throw new GLException(String.format("Surfaceless not supported due to quirk %s: %s", GLRendererQuirks.toString(22), this.toString()));
                        }
                        final NativeSurface nativeSurface = this.drawable.getNativeSurface();
                        if (!(nativeSurface instanceof ProxySurface) || !((ProxySurface)nativeSurface).containsUpstreamOptionBits(512)) {
                            throw new GLException(String.format("non-surfaceless drawable has zero-handle: %s", this.drawable.toString()));
                        }
                    }
                    final GLContext current = GLContext.getCurrent();
                    if (current != null) {
                        if (current == this) {
                            this.drawableUpdatedNotify();
                            n = 0;
                            if (GLContextImpl.TRACE_SWITCH) {
                                System.err.println(GLContext.getThreadName() + ": GLContext.ContextSwitch[makeCurrent.X2]: KEEP - CONTEXT_CURRENT - " + this.getTraceSwitchMsg());
                            }
                            return 1;
                        }
                        current.release();
                    }
                    currentWithinLock = this.makeCurrentWithinLock(lockSurface);
                    n = ((currentWithinLock != 0) ? 0 : 1);
                }
                catch (RuntimeException ex) {
                    n = 1;
                    throw ex;
                }
                finally {
                    if (n != 0) {
                        if (GLContextImpl.DEBUG_TRACE_SWITCH) {
                            System.err.println(GLContext.getThreadName() + ": GLContext.ContextSwitch[makeCurrent.1]: Context lock.unlock() due to error, res " + GLContext.makeCurrentResultToString(currentWithinLock) + ", " + this.lock);
                        }
                        this.lock.unlock();
                    }
                }
            }
        }
        catch (RuntimeException ex2) {
            n = 1;
            throw ex2;
        }
        finally {
            if (n != 0) {
                this.drawable.unlockSurface();
            }
        }
        if (currentWithinLock != 0) {
            if (0L == this.drawable.getHandle() && !this.isSurfaceless) {
                if (this.hasRendererQuirk(22)) {
                    throw new GLException(String.format("Surfaceless not supported due to quirk %s: %s", GLRendererQuirks.toString(22), this.toString()));
                }
                if (GLContextImpl.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext.makeCurrent: Surfaceless OK - validated");
                }
                this.isSurfaceless = true;
            }
            GLContext.setCurrent(this);
            if (2 == currentWithinLock) {
                this.glDebugHandler.init(this.isGLDebugEnabled());
                if (GLContextImpl.DEBUG_GL) {
                    this.setGL(GLPipelineFactory.create("com.jogamp.opengl.Debug", null, this.gl, null));
                    if (this.glDebugHandler.isEnabled()) {
                        this.glDebugHandler.addListener(new GLDebugMessageHandler.StdErrGLDebugListener(true));
                    }
                }
                if (GLContextImpl.TRACE_GL) {
                    this.setGL(GLPipelineFactory.create("com.jogamp.opengl.Trace", null, this.gl, new Object[] { System.err }));
                }
                b = true;
            }
            if (b) {
                this.associateDrawable(true);
            }
            this.contextMadeCurrent(true);
        }
        if (GLContextImpl.TRACE_SWITCH) {
            System.err.println(GLContext.getThreadName() + ": GLContext.ContextSwitch[makeCurrent.X3]: SWITCH - " + GLContext.makeCurrentResultToString(currentWithinLock) + " - stateTracker.on " + this.glStateTracker.isEnabled() + " - " + this.getTraceSwitchMsg());
        }
        return currentWithinLock;
    }
    
    private final GLContextImpl getOtherSharedMaster() {
        final GLContextImpl glContextImpl = (GLContextImpl)GLContextShareSet.getSharedMaster(this);
        return (this != glContextImpl) ? glContextImpl : null;
    }
    
    private final int makeCurrentWithinLock(final int n) throws GLException {
        if (this.isCreated()) {
            this.makeCurrentImpl();
            return 1;
        }
        if (0 >= this.drawable.getSurfaceWidth() || 0 >= this.drawable.getSurfaceHeight()) {
            if (GLContextImpl.DEBUG_TRACE_SWITCH) {
                System.err.println(GLContext.getThreadName() + ": Create GL context REJECTED (zero surface size) for " + this.getClass().getName() + " - " + this.getTraceSwitchMsg());
                System.err.println(this.drawable.toString());
            }
            return 0;
        }
        if (GLContextImpl.DEBUG_GL) {
            this.additionalCtxCreationFlags |= 0x20;
        }
        final GLContextImpl otherSharedMaster = this.getOtherSharedMaster();
        if (null != otherSharedMaster && 1 >= otherSharedMaster.drawable.lockSurface()) {
            throw new GLException("GLContextShareSet could not lock sharedMaster surface: " + otherSharedMaster.drawable);
        }
        boolean b;
        try {
            if (null != otherSharedMaster) {
                final long handle = otherSharedMaster.getHandle();
                if (0L == handle) {
                    throw new GLException("GLContextShareSet returned an invalid sharedMaster context: " + otherSharedMaster);
                }
                b = this.createImpl(handle);
            }
            else {
                b = this.createImpl(0L);
            }
            if (b && this.hasNoDefaultVAO()) {
                final int[] array = { 0 };
                final GL2ES3 gl2ES3 = this.gl.getRootGL().getGL2ES3();
                gl2ES3.glGenVertexArrays(1, array, 0);
                gl2ES3.glBindVertexArray(this.defaultVAO = array[0]);
            }
        }
        finally {
            if (null != otherSharedMaster) {
                otherSharedMaster.drawable.unlockSurface();
            }
        }
        if (GLContextImpl.DEBUG_TRACE_SWITCH) {
            System.err.println(GLContext.getThreadName() + ": Create GL context " + (b ? "OK" : "FAILED") + ": For " + this.getClass().getName() + " - " + this.getGLVersion() + " - " + this.getTraceSwitchMsg());
        }
        if (!b) {
            return 0;
        }
        final AbstractGraphicsDevice device = this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice();
        if (!GLContext.getAvailableGLVersionsSet(device) && 0x0 == (this.ctxOptions & 0x8)) {
            int major;
            if (this.ctxVersion.compareTo(GLContextImpl.Version3_0) <= 0) {
                major = 2;
            }
            else {
                major = this.ctxVersion.getMajor();
            }
            int n2;
            boolean b2;
            if (0x0 != (this.ctxOptions & 0x4)) {
                n2 = 4;
                b2 = false;
            }
            else {
                n2 = 2;
                b2 = true;
            }
            final MappedGLVersion mapAvailableGLVersion = mapAvailableGLVersion(device, major, n2, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
            if (b2) {
                mapAvailableGLVersion(device, major, 4, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                if (major >= 4) {
                    mapAvailableGLVersion(device, 3, n2, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                    mapAvailableGLVersion(device, 3, 4, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                }
                if (major >= 3) {
                    mapAvailableGLVersion(device, 2, n2, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                }
            }
            else if (major >= 4) {
                mapAvailableGLVersion(device, 3, n2, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
            }
            GLContext.setAvailableGLVersionsSet(device, true);
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextOLD-MapGLVersions HAVE: " + mapAvailableGLVersion);
            }
        }
        GLContextShareSet.contextCreated(this);
        return 2;
    }
    
    protected abstract void makeCurrentImpl() throws GLException;
    
    protected void associateDrawable(final boolean b) {
        this.drawable.associateContext(this, b);
    }
    
    protected void contextMadeCurrent(final boolean b) {
        this.drawable.contextMadeCurrent(this, b);
    }
    
    protected abstract boolean createImpl(final long p0) throws GLException;
    
    protected abstract long createContextARBImpl(final long p0, final boolean p1, final int p2, final int p3, final int p4);
    
    protected abstract void destroyContextARBImpl(final long p0);
    
    protected final boolean isCreateContextARBAvail(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return !GLProfile.disableOpenGLARBContext && !GLRendererQuirks.existStickyDeviceQuirk(abstractGraphicsDevice, 21);
    }
    
    protected final String getCreateContextARBAvailStr(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return "disabled " + GLProfile.disableOpenGLARBContext + ", quirk " + GLRendererQuirks.existStickyDeviceQuirk(abstractGraphicsDevice, 21);
    }
    
    protected final long createContextARB(final long n, final boolean b) {
        final AbstractGraphicsConfiguration graphicsConfiguration = this.drawable.getNativeSurface().getGraphicsConfiguration();
        final AbstractGraphicsDevice device = graphicsConfiguration.getScreen().getDevice();
        final GLProfile glProfile = ((GLCapabilitiesImmutable)graphicsConfiguration.getChosenCapabilities()).getGLProfile();
        if (GLContextImpl.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions is SET (" + device.getConnection() + "): " + GLContext.getAvailableGLVersionsSet(device));
        }
        if (!GLContext.getAvailableGLVersionsSet(device) && !this.mapGLVersions(device)) {
            return 0L;
        }
        final int[] array = { 0, 0 };
        GLContext.getRequestMajorAndCompat(glProfile, array);
        if (GLContextImpl.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions Requested " + glProfile + " -> " + GLContext.getGLVersion(array[0], 0, array[1], null));
        }
        final int[] array2 = { 0 };
        final int[] array3 = { 0 };
        final int[] array4 = { 0 };
        long contextARBImpl = 0L;
        if (GLContext.getAvailableGLVersion(device, array[0], array[1], array2, array3, array4)) {
            final int[] array5 = array4;
            final int n2 = 0;
            array5[n2] |= this.additionalCtxCreationFlags;
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions Mapped " + GLContext.getGLVersion(array2[0], array3[0], array4[0], null));
            }
            contextARBImpl = this.createContextARBImpl(n, b, array4[0], array2[0], array3[0]);
            if (0L != contextARBImpl && !this.setGLFunctionAvailability(true, array2[0], array3[0], array4[0], false, false)) {
                throw new InternalError("setGLFunctionAvailability !strictMatch failed");
            }
        }
        return contextARBImpl;
    }
    
    protected static synchronized void setMappedGLVersionListener(final MappedGLVersionListener mapGLVersionListener) {
        GLContextImpl.mapGLVersionListener = mapGLVersionListener;
    }
    
    protected static MappedGLVersion mapAvailableGLVersion(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final VersionNumber versionNumber, final int n3, final GLRendererQuirks glRendererQuirks) {
        final Integer mapAvailableGLVersion = GLContext.mapAvailableGLVersion(abstractGraphicsDevice, n, n2, versionNumber.getMajor(), versionNumber.getMinor(), n3);
        final int[] array = { 0 };
        final MappedGLVersion mappedGLVersion = new MappedGLVersion(abstractGraphicsDevice, n, n2, versionNumber, n3, glRendererQuirks, (null != mapAvailableGLVersion) ? GLContext.decomposeBits(mapAvailableGLVersion, array) : null, array[0]);
        if (null != GLContextImpl.mapGLVersionListener) {
            GLContextImpl.mapGLVersionListener.glVersionMapped(mappedGLVersion);
        }
        return mappedGLVersion;
    }
    
    protected static void remapAvailableGLVersions(final AbstractGraphicsDevice abstractGraphicsDevice, final AbstractGraphicsDevice abstractGraphicsDevice2) {
        if (abstractGraphicsDevice == abstractGraphicsDevice2 || abstractGraphicsDevice.getUniqueID() == abstractGraphicsDevice2.getUniqueID()) {
            return;
        }
        synchronized (GLContextImpl.deviceVersionAvailable) {
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions REMAP " + abstractGraphicsDevice + " -> " + abstractGraphicsDevice2);
            }
            final IdentityHashMap<String, Integer> identityHashMap = new IdentityHashMap<String, Integer>();
            for (final String s : GLContextImpl.deviceVersionAvailable.keySet()) {
                final Integer n = GLContextImpl.deviceVersionAvailable.get(s);
                if (null != n) {
                    if (GLContextImpl.DEBUG) {
                        final int[] array = { 0 };
                        System.err.println(" MapGLVersions REMAP OLD " + s + " -> " + GLContext.getGLVersion(new StringBuilder(), GLContext.decomposeBits(n, array), array[0], null).toString());
                    }
                    identityHashMap.put(s, n);
                    final int lastIndex = s.lastIndexOf(45);
                    if (0 >= lastIndex) {
                        throw new InternalError("device-separator '-' at " + lastIndex + " of " + s);
                    }
                    if (!abstractGraphicsDevice.getUniqueID().equals(s.substring(0, lastIndex))) {
                        continue;
                    }
                    final String intern = (abstractGraphicsDevice2.getUniqueID() + s.substring(lastIndex)).intern();
                    if (GLContextImpl.DEBUG) {
                        System.err.println(" MapGLVersions REMAP NEW " + intern + " -> (ditto)");
                    }
                    identityHashMap.put(intern, n);
                }
            }
            GLContextImpl.deviceVersionAvailable.clear();
            GLContextImpl.deviceVersionAvailable.putAll(identityHashMap);
            GLContext.setAvailableGLVersionsSet(abstractGraphicsDevice2, true);
        }
    }
    
    private final boolean mapGLVersions(final AbstractGraphicsDevice abstractGraphicsDevice) {
        synchronized (GLContext.deviceVersionAvailable) {
            final boolean hasOpenGLESSupport = this.drawable.getFactory().hasOpenGLESSupport();
            final boolean hasOpenGLDesktopSupport = this.drawable.getFactory().hasOpenGLDesktopSupport();
            final boolean hasMajorMinorCreateContextARB = this.drawable.getFactoryImpl().hasMajorMinorCreateContextARB();
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions START (GLDesktop " + hasOpenGLDesktopSupport + ", GLES " + hasOpenGLESSupport + ", minorVersion " + hasMajorMinorCreateContextARB + ") on " + abstractGraphicsDevice);
            }
            final long n = GLContextImpl.DEBUG ? System.nanoTime() : 0L;
            boolean b = false;
            final boolean b2 = false;
            boolean b3 = false;
            boolean b4 = false;
            boolean contextARBMapVersionsAvailable = false;
            boolean b5 = false;
            final boolean b6 = false;
            boolean b7 = false;
            final boolean b8 = false;
            if (hasOpenGLESSupport && !GLProfile.disableOpenGLES) {
                if (!b6) {
                    final boolean contextARBMapVersionsAvailable2 = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 3, 8, hasMajorMinorCreateContextARB);
                    b |= contextARBMapVersionsAvailable2;
                    if (contextARBMapVersionsAvailable2) {
                        if (0x0 == (0x40 & this.ctxOptions)) {
                            mapAvailableGLVersion(abstractGraphicsDevice, 2, 8, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                            if (GLContextImpl.PROFILE_ALIASING) {
                                b7 = true;
                            }
                        }
                        this.resetStates(false);
                    }
                }
                if (!b7) {
                    final boolean contextARBMapVersionsAvailable3 = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 2, 8, hasMajorMinorCreateContextARB);
                    b |= contextARBMapVersionsAvailable3;
                    if (contextARBMapVersionsAvailable3) {
                        if (this.ctxVersion.getMajor() >= 3 && this.hasRendererQuirk(15)) {
                            mapAvailableGLVersion(abstractGraphicsDevice, 3, 8, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                        }
                        this.resetStates(false);
                    }
                }
                if (!b8) {
                    final boolean contextARBMapVersionsAvailable4 = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 1, 8, hasMajorMinorCreateContextARB);
                    b |= contextARBMapVersionsAvailable4;
                    if (contextARBMapVersionsAvailable4) {
                        this.resetStates(false);
                    }
                }
            }
            if (Platform.OSType.MACOS == Platform.getOSType() && Platform.getOSVersionNumber().compareTo(PlatformPropsImpl.OSXVersion.Mavericks) >= 0 && hasOpenGLDesktopSupport && !GLProfile.disableOpenGLDesktop && !GLProfile.disableOpenGLCore && !contextARBMapVersionsAvailable && !b5) {
                b5 = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 3, 4, hasMajorMinorCreateContextARB);
                b |= b5;
                if (b5) {
                    if (0x0 == (0x40 & this.ctxOptions) && this.ctxVersion.getMajor() >= 4) {
                        mapAvailableGLVersion(abstractGraphicsDevice, 4, 4, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                        contextARBMapVersionsAvailable = true;
                        if (GLContextImpl.DEBUG) {
                            System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions: Quirk Triggerd: " + GLRendererQuirks.toString(13) + ": cause: OS " + Platform.getOSType() + ", OS Version " + Platform.getOSVersionNumber());
                        }
                    }
                    this.resetStates(false);
                }
            }
            if (hasOpenGLDesktopSupport && !GLProfile.disableOpenGLDesktop && !GLProfile.disableOpenGLCore) {
                if (!contextARBMapVersionsAvailable) {
                    contextARBMapVersionsAvailable = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 4, 4, hasMajorMinorCreateContextARB);
                    b |= contextARBMapVersionsAvailable;
                    if (contextARBMapVersionsAvailable) {
                        if (0x0 == (0x40 & this.ctxOptions)) {
                            mapAvailableGLVersion(abstractGraphicsDevice, 3, 4, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                            if (GLContextImpl.PROFILE_ALIASING) {
                                b5 = true;
                            }
                        }
                        this.resetStates(false);
                    }
                }
                if (!b5) {
                    b5 = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 3, 4, hasMajorMinorCreateContextARB);
                    b |= b5;
                    if (b5) {
                        this.resetStates(false);
                    }
                }
            }
            if (hasOpenGLDesktopSupport && !GLProfile.disableOpenGLDesktop) {
                if (!b2) {
                    final boolean contextARBMapVersionsAvailable5 = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 4, 2, hasMajorMinorCreateContextARB);
                    b |= contextARBMapVersionsAvailable5;
                    if (contextARBMapVersionsAvailable5) {
                        if (!contextARBMapVersionsAvailable) {
                            mapAvailableGLVersion(abstractGraphicsDevice, 4, 4, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                        }
                        if (!b5) {
                            mapAvailableGLVersion(abstractGraphicsDevice, 3, 4, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                            b5 = true;
                        }
                        if (0x0 == (0x40 & this.ctxOptions)) {
                            mapAvailableGLVersion(abstractGraphicsDevice, 3, 2, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                            mapAvailableGLVersion(abstractGraphicsDevice, 2, 2, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                            if (GLContextImpl.PROFILE_ALIASING) {
                                b3 = true;
                                b4 = true;
                            }
                        }
                        this.resetStates(false);
                    }
                }
                if (!b3) {
                    final boolean contextARBMapVersionsAvailable6 = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 3, 2, hasMajorMinorCreateContextARB);
                    b |= contextARBMapVersionsAvailable6;
                    if (contextARBMapVersionsAvailable6) {
                        if (!b5) {
                            mapAvailableGLVersion(abstractGraphicsDevice, 3, 4, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                        }
                        if (0x0 == (0x40 & this.ctxOptions)) {
                            mapAvailableGLVersion(abstractGraphicsDevice, 2, 2, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
                            if (GLContextImpl.PROFILE_ALIASING) {
                                b4 = true;
                            }
                        }
                        this.resetStates(false);
                    }
                }
                if (!b4) {
                    final boolean contextARBMapVersionsAvailable7 = this.createContextARBMapVersionsAvailable(abstractGraphicsDevice, 2, 2, hasMajorMinorCreateContextARB);
                    b |= contextARBMapVersionsAvailable7;
                    if (contextARBMapVersionsAvailable7) {
                        this.resetStates(false);
                    }
                }
            }
            if (b) {
                GLContext.setAvailableGLVersionsSet(abstractGraphicsDevice, true);
            }
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions END (success " + b + ") on " + abstractGraphicsDevice + ", profileAliasing: " + GLContextImpl.PROFILE_ALIASING + ", total " + (System.nanoTime() - n) / 1000000.0 + "ms");
                if (b) {
                    System.err.println(GLContext.dumpAvailableGLVersions(null).toString());
                }
            }
            return b;
        }
    }
    
    private final boolean createContextARBMapVersionsAvailable(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final boolean b) {
        final int n3 = 0x1 | n2;
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        int n4;
        int n5;
        int n6;
        int n7;
        if (b) {
            if (8 == n2) {
                n4 = n;
                n5 = GLContext.getMaxMinor(n3, n4);
                n6 = n;
                n7 = 0;
            }
            else if (4 == n) {
                n4 = 4;
                n5 = GLContext.getMaxMinor(n3, n4);
                n6 = 4;
                n7 = 0;
            }
            else if (3 == n) {
                n4 = 3;
                n5 = GLContext.getMaxMinor(n3, n4);
                n6 = 3;
                n7 = 1;
            }
            else {
                n4 = 3;
                n5 = 0;
                n6 = 2;
                n7 = 0;
            }
        }
        else if (8 == n2) {
            n4 = n;
            n5 = 0;
            n6 = n;
            n7 = 0;
        }
        else if (4 == n) {
            n4 = 4;
            n5 = 0;
            n6 = 4;
            n7 = 0;
        }
        else if (3 == n) {
            n4 = 3;
            n5 = 1;
            n6 = 3;
            n7 = 1;
        }
        else {
            n4 = 2;
            n5 = 0;
            n6 = 2;
            n7 = 0;
        }
        long n8 = this.createContextARBVersions(0L, true, n3, n4, n5, n6, n7, array, array2);
        if (0L == n8 && 4 == n2 && !GLContextImpl.PROFILE_ALIASING) {
            final int n9 = (n3 & 0xFFFFFFFB) | 0x10;
            n8 = this.createContextARBVersions(0L, true, n9, n4, n5, n6, n7, array, array2);
            if (0L == n8) {
                n8 = this.createContextARBVersions(0L, true, (n9 & 0xFFFFFFFB & 0xFFFFFFEF) | 0x2, n4, n5, n6, n7, array, array2);
            }
        }
        boolean b2;
        if (0L != n8) {
            final MappedGLVersion mapAvailableGLVersion = mapAvailableGLVersion(abstractGraphicsDevice, n, n2, this.ctxVersion, this.ctxOptions, this.glRendererQuirks);
            this.destroyContextARBImpl(n8);
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions HAVE " + mapAvailableGLVersion.toString(new StringBuilder(), n6, n7, n4, n5).toString());
            }
            b2 = true;
        }
        else {
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARB-MapGLVersions NOPE " + abstractGraphicsDevice + ", " + n + " (" + GLContext.getGLProfile(new StringBuilder(), n2).toString() + ") [" + n4 + "." + n5 + " .. " + n6 + "." + n7 + "]");
            }
            b2 = false;
        }
        return b2;
    }
    
    private final long createContextARBVersions(final long n, final boolean b, final int n2, final int n3, final int n4, final int n5, final int n6, final int[] array, final int[] array2) {
        array[0] = n3;
        array2[0] = n4;
        int n7 = 0;
        long contextARBImpl;
        do {
            if (GLContextImpl.DEBUG) {
                ++n7;
                System.err.println(GLContext.getThreadName() + ": createContextARBVersions." + n7 + ": share " + n + ", direct " + b + ", version " + array[0] + "." + array2[0] + " [" + n3 + "." + n4 + " .. " + n5 + "." + n6 + "]");
            }
            contextARBImpl = this.createContextARBImpl(n, b, n2, array[0], array2[0]);
            if (0L != contextARBImpl) {
                if (this.setGLFunctionAvailability(true, array[0], array2[0], n2, true, true)) {
                    break;
                }
                this.destroyContextARBImpl(contextARBImpl);
                contextARBImpl = 0L;
            }
        } while ((array[0] > n5 || (array[0] == n5 && array2[0] > n6)) && GLContext.decrementGLVersion(n2, array, array2));
        if (GLContextImpl.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": createContextARBVersions.X: ctx " + GLContext.toHexString(contextARBImpl) + ", share " + n + ", direct " + b + ", version " + array[0] + "." + array2[0] + " [" + n3 + "." + n4 + " .. " + n5 + "." + n6 + "]");
        }
        return contextARBImpl;
    }
    
    private final void setContextVersion(final int n, final int n2, final int ctxOptions, final VersionNumberString ctxVendorVersion, final boolean b) {
        if (0 == ctxOptions) {
            throw new GLException("Invalid GL Version " + n + "." + n2 + ", ctp " + GLContext.toHexString(ctxOptions));
        }
        this.ctxVersion = new VersionNumber(n, n2, 0);
        this.ctxVersionString = GLContext.getGLVersion(n, n2, ctxOptions, this.glVersion);
        this.ctxVendorVersion = ctxVendorVersion;
        this.ctxOptions = ctxOptions;
        if (b) {
            this.ctxGLSLVersion = VersionNumber.zeroVersion;
            if (this.hasGLSL()) {
                final String s = this.isGLES() ? null : this.gl.glGetString(35724);
                if (null != s) {
                    this.ctxGLSLVersion = new VersionNumber(s);
                    if (this.ctxGLSLVersion.getMajor() < 1) {
                        this.ctxGLSLVersion = VersionNumber.zeroVersion;
                    }
                }
                if (this.ctxGLSLVersion.isZero()) {
                    this.ctxGLSLVersion = GLContext.getStaticGLSLVersionNumber(n, n2, this.ctxOptions);
                }
            }
        }
    }
    
    private final boolean verifyInstance(final GLProfile glProfile, final String s, final Object o) {
        return ReflectionUtil.instanceOf(o, glProfile.getGLImplBaseClassName() + s);
    }
    
    private final Object createInstance(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final int n3, final boolean b, final Object[] array) {
        return ReflectionUtil.createInstance(GLProfile.get(abstractGraphicsDevice, GLContext.getGLProfile(n, n2, n3)).getGLCtor(b), array);
    }
    
    private final boolean verifyInstance(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final int n3, final String s, final Object o) {
        return ReflectionUtil.instanceOf(o, GLProfile.get(abstractGraphicsDevice, GLContext.getGLProfile(n, n2, n3)).getGLImplBaseClassName() + s);
    }
    
    private final GL createGL(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final int n3) {
        final GLProfile value = GLProfile.get(abstractGraphicsDevice, GLContext.getGLProfile(n, n2, n3));
        return (GL)ReflectionUtil.createInstance(value.getGLCtor(true), value, this);
    }
    
    private void finalizeInit(final GL gl) {
        Method method = null;
        try {
            method = ReflectionUtil.getMethod(gl.getClass(), "finalizeInit", (Class<?>[])new Class[0]);
        }
        catch (Throwable t) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Caught " + t.getClass().getName() + ": " + t.getMessage());
                t.printStackTrace();
            }
        }
        if (null != method) {
            ReflectionUtil.callMethod(gl, method, new Object[0]);
            return;
        }
        throw new InternalError("Missing 'void finalizeInit(ProcAddressTable)' in " + gl.getClass().getName());
    }
    
    public final ProcAddressTable getGLProcAddressTable() {
        return this.glProcAddressTable;
    }
    
    public abstract ProcAddressTable getPlatformExtProcAddressTable();
    
    protected final String mapToRealGLFunctionName(final String s) {
        final Map<String, String> functionNameMap = this.getFunctionNameMap();
        if (null != functionNameMap) {
            final String s2 = functionNameMap.get(s);
            if (s2 != null) {
                return s2;
            }
        }
        return s;
    }
    
    protected abstract Map<String, String> getFunctionNameMap();
    
    protected final String mapToRealGLExtensionName(final String s) {
        final Map<String, String> extensionNameMap = this.getExtensionNameMap();
        if (null != extensionNameMap) {
            final String s2 = extensionNameMap.get(s);
            if (s2 != null) {
                return s2;
            }
        }
        return s;
    }
    
    protected abstract Map<String, String> getExtensionNameMap();
    
    public final GLDynamicLookupHelper getGLDynamicLookupHelper() {
        return this.drawable.getFactoryImpl().getGLDynamicLookupHelper(this.ctxVersion.getMajor(), this.ctxOptions);
    }
    
    public final GLDynamicLookupHelper getGLDynamicLookupHelper(final int n, final int n2) {
        return this.drawable.getFactoryImpl().getGLDynamicLookupHelper(n, n2);
    }
    
    protected final void resetProcAddressTable(final ProcAddressTable procAddressTable, final GLDynamicLookupHelper glDynamicLookupHelper) {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                procAddressTable.reset(glDynamicLookupHelper);
                return null;
            }
        });
    }
    
    protected abstract void updateGLXProcAddressTable(final String p0, final GLDynamicLookupHelper p1);
    
    private final boolean initGLRendererAndGLVersionStrings(final int n, final int n2) {
        if (!this.glGetPtrInit) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    final GLDynamicLookupHelper glDynamicLookupHelper = GLContextImpl.this.getGLDynamicLookupHelper(n, n2);
                    if (null != glDynamicLookupHelper) {
                        glDynamicLookupHelper.claimAllLinkPermission();
                        try {
                            GLContextImpl.this.glGetStringPtr = glDynamicLookupHelper.dynamicLookupFunction("glGetString");
                            GLContextImpl.this.glGetIntegervPtr = glDynamicLookupHelper.dynamicLookupFunction("glGetIntegerv");
                        }
                        finally {
                            glDynamicLookupHelper.releaseAllLinkPermission();
                        }
                    }
                    return null;
                }
            });
            this.glGetPtrInit = true;
        }
        if (0L == this.glGetStringPtr || 0L == this.glGetIntegervPtr) {
            System.err.println("Error: Could not lookup: glGetString " + GLContext.toHexString(this.glGetStringPtr) + ", glGetIntegerv " + GLContext.toHexString(this.glGetIntegervPtr));
            if (GLContextImpl.DEBUG) {
                ExceptionUtils.dumpStack(System.err);
            }
            return false;
        }
        final String glGetStringInt = glGetStringInt(7936, this.glGetStringPtr);
        if (null == glGetStringInt) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Warning: GL_VENDOR is NULL.");
                ExceptionUtils.dumpStack(System.err);
            }
            return false;
        }
        this.glVendor = glGetStringInt;
        final String glGetStringInt2 = glGetStringInt(7937, this.glGetStringPtr);
        if (null == glGetStringInt2) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Warning: GL_RENDERER is NULL.");
                ExceptionUtils.dumpStack(System.err);
            }
            return false;
        }
        this.glRenderer = glGetStringInt2;
        this.glRendererLowerCase = this.glRenderer.toLowerCase();
        final String glGetStringInt3 = glGetStringInt(7938, this.glGetStringPtr);
        if (null == glGetStringInt3) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Warning: GL_VERSION is NULL.");
                ExceptionUtils.dumpStack(System.err);
            }
            return false;
        }
        this.glVersion = glGetStringInt3;
        return true;
    }
    
    private final void getGLIntVersion(final int[] array, final int[] array2) {
        array2[array[0] = 0] = 0;
        if (0L == this.glGetIntegervPtr) {
            throw new InternalError("Not initialized: glGetString " + GLContext.toHexString(this.glGetStringPtr) + ", glGetIntegerv " + GLContext.toHexString(this.glGetIntegervPtr));
        }
        glGetIntegervInt(33307, array, 0, this.glGetIntegervPtr);
        glGetIntegervInt(33308, array2, 0, this.glGetIntegervPtr);
    }
    
    private static final VersionNumber getGLVersionNumber(final int n, final String s) {
        if (null != s) {
            final GLVersionNumber create = GLVersionNumber.create(s);
            if (create.isValid()) {
                final int[] array = { create.getMajor() };
                final int[] array2 = { create.getMinor() };
                if (GLContext.isValidGLVersion(n, array[0], array2[0])) {
                    return new VersionNumber(array[0], array2[0], 0);
                }
            }
        }
        return null;
    }
    
    protected final int getCtxOptions() {
        return this.ctxOptions;
    }
    
    protected final boolean setGLFunctionAvailability(final boolean b, int n, int n2, int n3, final boolean b2, final boolean b3) throws GLException {
        if (null != this.gl && null != this.glProcAddressTable && !b) {
            return true;
        }
        if (0 < n && !GLContext.isValidGLVersion(n3, n, n2)) {
            throw new GLException("Invalid GL Version Request " + GLContext.getGLVersion(n, n2, n3, null));
        }
        final AbstractGraphicsDevice device = this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice();
        final int n4 = n3;
        final VersionNumber versionNumber = new VersionNumber(n, n2, 0);
        if (!this.initGLRendererAndGLVersionStrings(n, n3)) {
            final String string = "Intialization of GL renderer strings failed. " + device + " - " + GLContext.getGLVersion(n, n2, n3, null);
            if (b2) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Warning: setGLFunctionAvailability: " + string);
                }
                return false;
            }
            throw new GLException(string);
        }
        else {
            final VersionNumber glVersionNumber = getGLVersionNumber(n3, this.glVersion);
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail: Given " + device + " - " + GLContext.getGLVersion(n, n2, n3, this.glVersion) + ", Number(Str) " + glVersionNumber);
            }
            final boolean b4 = 0x0 != (0x8 & n3);
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail: Pre version verification - expected " + GLContext.getGLVersion(n, n2, n3, null) + ", strictMatch " + b2 + ", glVersionsMapping " + b3);
            }
            boolean b5;
            if (n >= 3 || glVersionNumber.compareTo(GLContextImpl.Version3_0) >= 0) {
                final int[] array = { 0 };
                final int[] array2 = { 0 };
                this.getGLIntVersion(array, array2);
                final VersionNumber versionNumber2 = new VersionNumber(array[0], array2[0], 0);
                if (GLContextImpl.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail: Version verification (Int): String " + this.glVersion + ", Number(Int) " + versionNumber2);
                }
                if (GLContext.isValidGLVersion(n3, versionNumber2.getMajor(), versionNumber2.getMinor())) {
                    final int major = versionNumber2.getMajor();
                    if (b2 && (((b4 || n >= 3) && versionNumber2.compareTo(versionNumber) < 0) || (b4 && ((2 == n && (2 > major || major > 3)) || ((1 == n || 3 <= n) && n != major))))) {
                        if (GLContextImpl.DEBUG) {
                            System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail.X: FAIL, GL version mismatch (Int): " + GLContext.getGLVersion(n, n2, n3, null) + " -> " + this.glVersion + ", " + versionNumber2);
                        }
                        return false;
                    }
                    n = versionNumber2.getMajor();
                    n2 = versionNumber2.getMinor();
                    b5 = true;
                }
                else {
                    b5 = false;
                }
            }
            else {
                b5 = false;
            }
            boolean b6;
            if (b5) {
                b6 = true;
            }
            else {
                if (GLContextImpl.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail: Version verification (String): String " + this.glVersion + ", Number(Str) " + glVersionNumber);
                }
                if (null != glVersionNumber) {
                    final int major2 = glVersionNumber.getMajor();
                    if (b2 && (((b4 || n >= 3) && glVersionNumber.compareTo(versionNumber) < 0) || (b4 && ((2 == n && (2 > major2 || major2 > 3)) || ((1 == n || 3 <= n) && n != major2))))) {
                        if (GLContextImpl.DEBUG) {
                            System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail.X: FAIL, GL version mismatch (String): " + GLContext.getGLVersion(n, n2, n3, null) + " -> " + this.glVersion + ", " + glVersionNumber);
                        }
                        return false;
                    }
                    if (b2 && !b5 && n >= 3) {
                        if (GLContextImpl.DEBUG) {
                            System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail.X: FAIL, GL3/ES3 version Int failed, String: " + GLContext.getGLVersion(n, n2, n3, null) + " -> " + this.glVersion + ", " + glVersionNumber);
                        }
                        return false;
                    }
                    n = glVersionNumber.getMajor();
                    n2 = glVersionNumber.getMinor();
                    b6 = true;
                }
                else {
                    b6 = false;
                }
            }
            if (b2 && !b6) {
                if (GLContextImpl.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail.X: FAIL, No GL version validation possible: " + GLContext.getGLVersion(n, n2, n3, null) + " -> " + this.glVersion);
                }
                return false;
            }
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail: Post version verification req " + GLContext.getGLVersion(versionNumber.getMajor(), versionNumber.getMinor(), n4, null) + " -> has " + GLContext.getGLVersion(n, n2, n3, null) + ", strictMatch " + b2 + ", versionValidated " + b6 + ", versionGL3IntOK " + b5);
            }
            if (n < 2) {
                n3 &= 0xFFFFC3FF;
            }
            if (!this.isCurrentContextHardwareRasterizer()) {
                n3 |= 0x40;
            }
            final VersionNumberString vendorVersion = GLVersionNumber.createVendorVersion(this.glVersion);
            this.setRendererQuirks(device, this.getDrawableImpl().getFactoryImpl(), versionNumber.getMajor(), versionNumber.getMinor(), n4, n, n2, n3, vendorVersion, b3);
            if (b2 && this.glRendererQuirks.exist(6)) {
                if (GLContextImpl.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail.X: FAIL, GL is not compliant: " + GLContext.getGLVersion(n, n2, n3, this.glVersion) + ", " + this.glRenderer);
                }
                return false;
            }
            this.contextFQN = getContextFQN(device, n, n2, n3);
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail.0 validated FQN: " + this.contextFQN + " - " + GLContext.getGLVersion(n, n2, n3, this.glVersion));
            }
            final GLDynamicLookupHelper glDynamicLookupHelper = this.getGLDynamicLookupHelper(n, n3);
            if (null == glDynamicLookupHelper) {
                if (GLContextImpl.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail.X: FAIL, No GLDynamicLookupHelper for request: " + GLContext.getGLVersion(n, n2, n3, null));
                }
                return false;
            }
            this.updateGLXProcAddressTable(this.contextFQN, glDynamicLookupHelper);
            Object glProfile = this.drawable.getGLProfile();
            ProcAddressTable glProcAddressTable = null;
            synchronized (GLContextImpl.mappedContextTypeObjectLock) {
                glProcAddressTable = GLContextImpl.mappedGLProcAddress.get(this.contextFQN);
                if (null != glProcAddressTable) {
                    if (!this.verifyInstance(device, n, n2, n3, "ProcAddressTable", glProcAddressTable)) {
                        throw new GLException("GLContext GL ProcAddressTable mapped key(" + this.contextFQN + " - " + GLContext.getGLVersion(n, n2, n3, null) + ") -> " + GLContext.toHexString(glProcAddressTable.hashCode()) + " not matching " + glProcAddressTable.getClass().getName());
                    }
                    if (!b3 && !this.verifyInstance((GLProfile)glProfile, "ProcAddressTable", glProcAddressTable)) {
                        throw new GLException("GLContext GL ProcAddressTable mapped key(" + this.contextFQN + " - " + GLContext.getGLVersion(n, n2, n3, null) + ") -> " + GLContext.toHexString(glProcAddressTable.hashCode()) + ": " + glProcAddressTable.getClass().getName() + " not matching " + ((GLProfile)glProfile).getGLImplBaseClassName() + "/" + glProfile);
                    }
                }
            }
            if (null != glProcAddressTable) {
                this.glProcAddressTable = glProcAddressTable;
                if (GLContextImpl.DEBUG) {
                    if (b3) {
                        System.err.println(GLContext.getThreadName() + ": GLContext GL ProcAddressTable reusing key(" + this.contextFQN + " - " + GLContext.getGLVersion(n, n2, n3, null) + ") -> " + GLContext.toHexString(glProcAddressTable.hashCode()) + ": " + glProcAddressTable.getClass().getName());
                    }
                    else {
                        System.err.println(GLContext.getThreadName() + ": GLContext GL ProcAddressTable reusing key(" + this.contextFQN + " - " + GLContext.getGLVersion(n, n2, n3, null) + ") -> " + GLContext.toHexString(glProcAddressTable.hashCode()) + ": " + glProcAddressTable.getClass().getName() + " -> " + ((GLProfile)glProfile).getGLImplBaseClassName());
                    }
                }
            }
            else {
                this.resetProcAddressTable(this.glProcAddressTable = (ProcAddressTable)this.createInstance(device, n, n2, n3, false, new Object[] { new GLProcAddressResolver() }), glDynamicLookupHelper);
                synchronized (GLContextImpl.mappedContextTypeObjectLock) {
                    GLContextImpl.mappedGLProcAddress.put(this.contextFQN, this.glProcAddressTable);
                    if (GLContextImpl.DEBUG) {
                        if (b3) {
                            System.err.println(GLContext.getThreadName() + ": GLContext GL ProcAddressTable mapping key(" + this.contextFQN + " - " + GLContext.getGLVersion(n, n2, n3, null) + ") -> " + GLContext.toHexString(this.glProcAddressTable.hashCode()) + ": " + this.glProcAddressTable.getClass().getName());
                        }
                        else {
                            System.err.println(GLContext.getThreadName() + ": GLContext GL ProcAddressTable mapping key(" + this.contextFQN + " - " + GLContext.getGLVersion(n, n2, n3, null) + ") -> " + GLContext.toHexString(this.glProcAddressTable.hashCode()) + ": " + this.glProcAddressTable.getClass().getName() + " -> " + ((GLProfile)glProfile).getGLImplBaseClassName());
                        }
                    }
                }
            }
            if (null == this.gl || !this.verifyInstance(device, n, n2, n3, "Impl", this.gl)) {
                this.setGL(this.createGL(device, n, n2, n3));
            }
            if (!b3 && !this.verifyInstance((GLProfile)glProfile, "Impl", this.gl)) {
                throw new GLException("GLContext GL Object mismatch: " + GLContext.getGLVersion(n, n2, n3, null) + ") -> " + ": " + this.gl.getClass().getName() + " not matching " + ((GLProfile)glProfile).getGLImplBaseClassName() + "/" + glProfile);
            }
            synchronized (GLContextImpl.mappedContextTypeObjectLock) {
                glProfile = GLContextImpl.mappedExtensionAvailabilityCache.get(this.contextFQN);
            }
            if (null != glProfile) {
                this.extensionAvailability = (ExtensionAvailabilityCache)glProfile;
                if (GLContextImpl.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext GL ExtensionAvailabilityCache reusing key(" + this.contextFQN + ") -> " + GLContext.toHexString(glProfile.hashCode()) + " - entries: " + ((ExtensionAvailabilityCache)glProfile).getTotalExtensionCount());
                }
            }
            else {
                this.extensionAvailability = new ExtensionAvailabilityCache();
                this.setContextVersion(n, n2, n3, vendorVersion, false);
                this.extensionAvailability.reset(this);
                synchronized (GLContextImpl.mappedContextTypeObjectLock) {
                    GLContextImpl.mappedExtensionAvailabilityCache.put(this.contextFQN, this.extensionAvailability);
                    if (GLContextImpl.DEBUG) {
                        System.err.println(GLContext.getThreadName() + ": GLContext GL ExtensionAvailabilityCache mapping key(" + this.contextFQN + ") -> " + GLContext.toHexString(this.extensionAvailability.hashCode()) + " - entries: " + this.extensionAvailability.getTotalExtensionCount());
                    }
                }
            }
            if (b4) {
                if (n >= 3) {
                    n3 |= 0xC00;
                    n3 |= 0x4000;
                    if (n2 >= 2) {
                        n3 |= 0x3000;
                    }
                    else if (n2 >= 1) {
                        n3 |= 0x1000;
                    }
                }
                else if (n >= 2) {
                    n3 |= 0x400;
                    n3 |= 0x4000;
                }
            }
            else if (n > 4 || (n == 4 && n2 >= 5) || n > 3 || (n == 3 && n2 >= 1)) {
                if (this.isExtensionAvailable("GL_ARB_ES3_2_compatibility")) {
                    n3 |= 0x3000;
                }
                else if (this.isExtensionAvailable("GL_ARB_ES3_1_compatibility")) {
                    n3 |= 0x1000;
                }
                n3 |= 0xC00;
                n3 |= 0x4000;
            }
            else if (n > 4 || (n == 4 && n2 >= 3) || ((n > 3 || (n == 3 && n2 >= 1)) && this.isExtensionAvailable("GL_ARB_ES3_compatibility"))) {
                n3 |= 0xC00;
                n3 |= 0x4000;
            }
            else if (this.isExtensionAvailable("GL_ARB_ES2_compatibility")) {
                n3 |= 0x400;
                n3 |= 0x4000;
            }
            else if (hasFBOImpl(n, n3, this.extensionAvailability)) {
                n3 |= 0x4000;
            }
            if ((b4 && n == 1) || this.isExtensionAvailable("GL_OES_single_precision")) {
                n3 |= 0x8000;
            }
            if (GLContextImpl.FORCE_NO_FBO_SUPPORT) {
                n3 &= 0xFFFFBFFF;
            }
            this.setContextVersion(n, n2, n3, vendorVersion, true);
            this.finalizeInit(this.gl);
            this.setDefaultSwapInterval();
            final int glGetError = this.gl.glGetError();
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext.setGLFuncAvail.X: OK " + this.contextFQN + " - " + GLContext.getGLVersion(this.ctxVersion.getMajor(), this.ctxVersion.getMinor(), this.ctxOptions, null) + " - glErr " + GLContext.toHexString(glGetError));
            }
            return true;
        }
    }
    
    private static final void addStickyQuirkAlways(final AbstractGraphicsDevice abstractGraphicsDevice, final GLRendererQuirks glRendererQuirks, final int n, final boolean b) {
        glRendererQuirks.addQuirk(n);
        if (b) {
            GLRendererQuirks.addStickyDeviceQuirk(abstractGraphicsDevice, n);
        }
        else {
            synchronized (GLContextImpl.class) {
                GLRendererQuirks.addStickyDeviceQuirk(abstractGraphicsDevice, n);
            }
        }
    }
    
    private static final void addStickyQuirkAtMapping(final AbstractGraphicsDevice abstractGraphicsDevice, final GLRendererQuirks glRendererQuirks, final int n, final boolean b) {
        glRendererQuirks.addQuirk(n);
        if (b) {
            GLRendererQuirks.addStickyDeviceQuirk(abstractGraphicsDevice, n);
        }
    }
    
    private final void setRendererQuirks(final AbstractGraphicsDevice abstractGraphicsDevice, final GLDrawableFactoryImpl glDrawableFactoryImpl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final VersionNumberString versionNumberString, final boolean b) {
        final boolean b2 = 0x0 == (n6 & 0x40);
        final boolean b3 = 0x0 != (n6 & 0x2);
        final boolean b4 = 0x0 != (n6 & 0x8);
        final boolean b5 = NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(true);
        final boolean b6 = Platform.getOSType() == Platform.OSType.WINDOWS;
        final boolean b7 = this.glRenderer.contains("Mesa ") || this.glRenderer.contains("Gallium ");
        boolean b8;
        boolean b9;
        boolean startsWith;
        if (!b7) {
            b8 = (this.glVendor.contains("ATI Technologies") || this.glRenderer.startsWith("ATI "));
            b9 = (this.glVendor.contains("NVIDIA Corporation") || this.glRenderer.contains("NVIDIA "));
            startsWith = this.glVendor.startsWith("Intel");
        }
        else {
            b8 = false;
            b9 = false;
            startsWith = false;
        }
        final GLRendererQuirks glRendererQuirks = new GLRendererQuirks();
        if (b4 && 2 == n && 2 < n4) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(15) + ": cause: ES req " + n + " and 2 < " + n4);
            }
            addStickyQuirkAlways(abstractGraphicsDevice, glRendererQuirks, 15, b);
        }
        if (GLProfile.disableSurfacelessContext) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(22) + ": cause: disabled");
            }
            addStickyQuirkAlways(abstractGraphicsDevice, glRendererQuirks, 22, b);
        }
        if (GLProfile.disableOpenGLARBContext) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(21) + ": cause: disabled");
            }
            addStickyQuirkAlways(abstractGraphicsDevice, glRendererQuirks, 21, b);
        }
        if (Platform.getOSType() == Platform.OSType.MACOS) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(3) + ": cause: OS " + Platform.getOSType());
            }
            glRendererQuirks.addQuirk(3);
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(20) + ": cause: OS " + Platform.getOSType());
            }
            glRendererQuirks.addQuirk(20);
            if (Platform.getOSVersionNumber().compareTo(PlatformPropsImpl.OSXVersion.Mavericks) >= 0 && 3 == n && 4 == n4) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(13) + ": cause: OS " + Platform.getOSType() + ", OS Version " + Platform.getOSVersionNumber() + ", req " + n + "." + n2);
                }
                addStickyQuirkAtMapping(abstractGraphicsDevice, glRendererQuirks, 13, b);
            }
            if (b9) {
                if (Platform.getOSVersionNumber().compareTo(new VersionNumber(10, 7, 3)) < 0) {
                    if (GLContextImpl.DEBUG) {
                        System.err.println("Quirk: " + GLRendererQuirks.toString(7) + ": cause: OS " + Platform.getOSType() + ", OS Version " + Platform.getOSVersionNumber() + ", Renderer " + this.glRenderer);
                    }
                    glRendererQuirks.addQuirk(7);
                }
                if (Platform.getOSVersionNumber().compareTo(PlatformPropsImpl.OSXVersion.Lion) < 0) {
                    if (GLContextImpl.DEBUG) {
                        System.err.println("Quirk: " + GLRendererQuirks.toString(12) + ": cause: OS " + Platform.getOSType() + ", OS Version " + Platform.getOSVersionNumber() + ", Renderer " + this.glRenderer);
                    }
                    glRendererQuirks.addQuirk(12);
                }
            }
        }
        else if (b6) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(1) + ": cause: OS " + Platform.getOSType());
            }
            glRendererQuirks.addQuirk(1);
            if (b8) {
                final VersionNumber versionNumber = new VersionNumber(5, 1, 0);
                if (versionNumberString.compareTo(new VersionNumber(12, 102, 3)) < 0) {
                    if (GLContextImpl.DEBUG) {
                        System.err.println("Quirk: " + GLRendererQuirks.toString(10) + ": cause: OS " + Platform.getOSType() + ", [Vendor " + this.glVendor + " or Renderer " + this.glRenderer + "], driverVersion " + versionNumberString);
                    }
                    glRendererQuirks.addQuirk(10);
                }
                if (Platform.getOSVersionNumber().compareTo(versionNumber) <= 0) {
                    if (GLContextImpl.DEBUG) {
                        System.err.println("Quirk: " + GLRendererQuirks.toString(9) + ": cause: OS-Version " + Platform.getOSType() + " " + Platform.getOSVersionNumber() + ", [Vendor " + this.glVendor + " or Renderer " + this.glRenderer + "]");
                    }
                    glRendererQuirks.addQuirk(9);
                }
                if (versionNumberString.compareTo(VersionNumberString.zeroVersion) == 0 && new VersionNumber(this.glVersion).getSub() <= 8787 && this.glRenderer.equals("ATI Radeon 3100 Graphics")) {
                    if (GLContextImpl.DEBUG) {
                        System.err.println("Quirk: " + GLRendererQuirks.toString(21) + ": cause: OS " + Platform.getOSType() + ", [Vendor " + this.glVendor + ", Renderer " + this.glRenderer + " and Version " + this.glVersion + "]");
                    }
                    addStickyQuirkAtMapping(abstractGraphicsDevice, glRendererQuirks, 21, b);
                }
            }
            else if (startsWith && this.glRenderer.equals("Intel Bear Lake B")) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(19) + ": cause: OS " + Platform.getOSType() + ", [Vendor " + this.glVendor + " and Renderer " + this.glRenderer + "]");
                }
                glRendererQuirks.addQuirk(19);
            }
        }
        else if (Platform.OSType.ANDROID == Platform.getOSType()) {
            if (this.glRenderer.contains("PowerVR")) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(2) + ": cause: OS " + Platform.getOSType() + ", Renderer " + this.glRenderer);
                }
                glRendererQuirks.addQuirk(2);
            }
            if (this.glRenderer.contains("Immersion.16")) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(14) + ": cause: OS " + Platform.getOSType() + ", Renderer " + this.glRenderer);
                }
                glRendererQuirks.addQuirk(14);
            }
        }
        if (b5) {
            if (this.glRenderer.contains("Mesa ")) {
                if (this.glRenderer.contains("X11") && versionNumberString.compareTo(GLContextImpl.Version8_0) < 0) {
                    if (GLContextImpl.DEBUG) {
                        System.err.println("Quirk: " + GLRendererQuirks.toString(8) + ": cause: X11 Renderer=" + this.glRenderer + ", Version=[vendor " + versionNumberString + ", GL " + this.glVersion + "]");
                    }
                    glRendererQuirks.addQuirk(8);
                }
            }
            else if (b8) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(8) + ": cause: X11 Renderer=" + this.glRenderer);
                }
                glRendererQuirks.addQuirk(8);
            }
            else if (X11Util.getMarkAllDisplaysUnclosable()) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(8) + ": cause: X11Util Downstream");
                }
                glRendererQuirks.addQuirk(8);
            }
            if (b9 && !b4 && !(abstractGraphicsDevice instanceof EGLGraphicsDevice)) {
                if (GLContextImpl.DEBUG) {
                    System.err.print("Quirk: " + GLRendererQuirks.toString(22) + ": cause: !ES, !EGL, Vendor " + this.glVendor + ", X11 Renderer " + this.glRenderer + ", Version=[vendor " + versionNumberString + ", GL " + this.glVersion + "]");
                }
                addStickyQuirkAtMapping(abstractGraphicsDevice, glRendererQuirks, 22, b);
            }
        }
        if (b7) {
            final VersionNumber versionNumber2 = new VersionNumber(8, 0, 0);
            final VersionNumber versionNumber3 = new VersionNumber(9, 2, 1);
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(4) + ": cause: Renderer " + this.glRenderer);
            }
            glRendererQuirks.addQuirk(4);
            if (b2) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(0) + ": cause: Renderer " + this.glRenderer);
                }
                glRendererQuirks.addQuirk(0);
            }
            else if (versionNumberString.compareTo(versionNumber2) < 0) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(18) + ": cause: Renderer " + this.glRenderer + " / Mesa-Version " + versionNumberString);
                }
                glRendererQuirks.addQuirk(18);
            }
            if (b3 && (n4 > 3 || (n4 == 3 && n5 >= 1))) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(6) + ": cause: Renderer " + this.glRenderer);
                }
                glRendererQuirks.addQuirk(6);
            }
            if (this.glRenderer.contains("Intel(R)") && versionNumberString.compareTo(versionNumber3) >= 0 && b5) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(14) + ": cause: X11 / Renderer " + this.glRenderer + " / Mesa-Version " + versionNumberString);
                }
                glRendererQuirks.addQuirk(14);
            }
            if (this.glVendor.contains("nouveau")) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(17) + ": cause: X11 / Renderer " + this.glRenderer + " / Vendor " + this.glVendor);
                }
                addStickyQuirkAtMapping(abstractGraphicsDevice, glRendererQuirks, 17, b);
            }
            if (b6 && this.glRenderer.contains("SVGA3D") && versionNumberString.compareTo(versionNumber2) < 0) {
                if (GLContextImpl.DEBUG) {
                    System.err.println("Quirk: " + GLRendererQuirks.toString(11) + ": cause: OS " + Platform.getOSType() + " / Renderer " + this.glRenderer + " / Mesa-Version " + versionNumberString);
                }
                glRendererQuirks.addQuirk(11);
            }
        }
        if (GLContextImpl.FORCE_NO_COLOR_RENDERBUFFER) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(18) + ": cause: property");
            }
            glRendererQuirks.addQuirk(18);
        }
        if (GLContextImpl.FORCE_MIN_FBO_SUPPORT || glRendererQuirks.exist(18)) {
            if (GLContextImpl.DEBUG) {
                System.err.println("Quirk: " + GLRendererQuirks.toString(11) + ": cause: " + (GLContextImpl.FORCE_MIN_FBO_SUPPORT ? "property, " : "") + (glRendererQuirks.exist(18) ? "BuggyColorRenderbuffer" : ""));
            }
            glRendererQuirks.addQuirk(11);
        }
        if (GLContextImpl.DEBUG) {
            System.err.println("Quirks local.0: " + glRendererQuirks);
        }
        GLRendererQuirks.pushStickyDeviceQuirks(abstractGraphicsDevice, glRendererQuirks);
        final AbstractGraphicsDevice defaultDevice = glDrawableFactoryImpl.getDefaultDevice();
        if (!GLRendererQuirks.areSameStickyDevice(defaultDevice, abstractGraphicsDevice)) {
            GLRendererQuirks.pushStickyDeviceQuirks(defaultDevice, glRendererQuirks);
        }
        if (b4) {
            final AbstractGraphicsDevice defaultDevice2 = GLDrawableFactory.getEGLFactory().getDefaultDevice();
            if (!GLRendererQuirks.areSameStickyDevice(defaultDevice2, abstractGraphicsDevice) && !GLRendererQuirks.areSameStickyDevice(defaultDevice2, defaultDevice)) {
                GLRendererQuirks.pushStickyDeviceQuirks(defaultDevice2, glRendererQuirks);
            }
        }
        this.glRendererQuirks = glRendererQuirks;
        if (GLContextImpl.DEBUG) {
            System.err.println("Quirks local.X: " + this.glRendererQuirks);
            System.err.println("Quirks sticky on " + abstractGraphicsDevice + ": " + GLRendererQuirks.getStickyDeviceQuirks(abstractGraphicsDevice));
        }
    }
    
    private static final boolean hasFBOImpl(final int n, final int n2, final ExtensionAvailabilityCache extensionAvailabilityCache) {
        return (0x0 != (n2 & 0x8) && n >= 2) || n >= 3 || (null != extensionAvailabilityCache && (extensionAvailabilityCache.isExtensionAvailable("GL_ARB_ES2_compatibility") || extensionAvailabilityCache.isExtensionAvailable("GL_ARB_framebuffer_object") || extensionAvailabilityCache.isExtensionAvailable("GL_EXT_framebuffer_object") || extensionAvailabilityCache.isExtensionAvailable("GL_OES_framebuffer_object")));
    }
    
    private final void removeCachedVersion(final int n, final int n2, int n3) {
        if (!this.isCurrentContextHardwareRasterizer()) {
            n3 |= 0x40;
        }
        this.contextFQN = getContextFQN(this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice(), n, n2, n3);
        if (GLContextImpl.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": RM Context FQN: " + this.contextFQN + " - " + GLContext.getGLVersion(n, n2, n3, null));
        }
        synchronized (GLContextImpl.mappedContextTypeObjectLock) {
            final ProcAddressTable procAddressTable = GLContextImpl.mappedGLProcAddress.remove(this.contextFQN);
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": RM GLContext GL ProcAddressTable mapping key(" + this.contextFQN + ") -> " + GLContext.toHexString((null != procAddressTable) ? procAddressTable.hashCode() : 0));
            }
        }
        synchronized (GLContextImpl.mappedContextTypeObjectLock) {
            final ExtensionAvailabilityCache extensionAvailabilityCache = GLContextImpl.mappedExtensionAvailabilityCache.remove(this.contextFQN);
            if (GLContextImpl.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": RM GLContext GL ExtensionAvailabilityCache mapping key(" + this.contextFQN + ") -> " + GLContext.toHexString((null != extensionAvailabilityCache) ? extensionAvailabilityCache.hashCode() : 0));
            }
        }
    }
    
    private final boolean isCurrentContextHardwareRasterizer() {
        return this.drawable.getChosenGLCapabilities().getHardwareAccelerated() && !this.glRendererLowerCase.contains("software") && !this.glRendererLowerCase.contains("mesa x11") && !this.glRendererLowerCase.contains("softpipe") && !this.glRendererLowerCase.contains("llvmpipe");
    }
    
    protected abstract StringBuilder getPlatformExtensionsStringImpl();
    
    @Override
    public final boolean isFunctionAvailable(final String s) {
        if (null != this.glProcAddressTable) {
            try {
                if (this.glProcAddressTable.isFunctionAvailable(s)) {
                    return true;
                }
            }
            catch (Exception ex) {}
        }
        final ProcAddressTable platformExtProcAddressTable = this.getPlatformExtProcAddressTable();
        if (null != platformExtProcAddressTable) {
            try {
                if (platformExtProcAddressTable.isFunctionAvailable(s)) {
                    return true;
                }
            }
            catch (Exception ex2) {}
        }
        final GLDynamicLookupHelper glDynamicLookupHelper = this.getGLDynamicLookupHelper(this.ctxVersion.getMajor(), this.ctxOptions);
        if (null == glDynamicLookupHelper) {
            throw new GLException("No GLDynamicLookupHelper for " + this);
        }
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            final /* synthetic */ String val$tmpBase = GLNameResolver.normalizeVEN(GLNameResolver.normalizeARB(s, true), true);
            
            @Override
            public Boolean run() {
                boolean functionAvailable = false;
                glDynamicLookupHelper.claimAllLinkPermission();
                try {
                    for (int funcNamePermutationNumber = GLNameResolver.getFuncNamePermutationNumber(this.val$tmpBase), n = 0; !functionAvailable && n < funcNamePermutationNumber; ++n) {
                        final String funcNamePermutation = GLNameResolver.getFuncNamePermutation(this.val$tmpBase, n);
                        try {
                            functionAvailable = glDynamicLookupHelper.isFunctionAvailable(funcNamePermutation);
                        }
                        catch (Exception ex) {}
                    }
                }
                finally {
                    glDynamicLookupHelper.releaseAllLinkPermission();
                }
                return functionAvailable;
            }
        });
    }
    
    @Override
    public final boolean isExtensionAvailable(final String s) {
        return null != this.extensionAvailability && this.extensionAvailability.isExtensionAvailable(this.mapToRealGLExtensionName(s));
    }
    
    @Override
    public final int getPlatformExtensionCount() {
        return (null != this.extensionAvailability) ? this.extensionAvailability.getPlatformExtensionCount() : 0;
    }
    
    @Override
    public final String getPlatformExtensionsString() {
        if (null != this.extensionAvailability) {
            return this.extensionAvailability.getPlatformExtensionsString();
        }
        return null;
    }
    
    @Override
    public final int getGLExtensionCount() {
        return (null != this.extensionAvailability) ? this.extensionAvailability.getGLExtensionCount() : 0;
    }
    
    @Override
    public final String getGLExtensionsString() {
        if (null != this.extensionAvailability) {
            return this.extensionAvailability.getGLExtensionsString();
        }
        return null;
    }
    
    public final boolean isExtensionCacheInitialized() {
        return null != this.extensionAvailability && this.extensionAvailability.isInitialized();
    }
    
    protected static String getContextFQN(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, int n3) {
        n3 &= 0x3FF;
        return abstractGraphicsDevice.getUniqueID() + "-" + GLContext.toHexString(GLContext.composeBits(n, n2, n3));
    }
    
    protected final String getContextFQN() {
        return this.contextFQN;
    }
    
    @Override
    public int getDefaultPixelDataType() {
        this.evalPixelDataType();
        return this.pixelDataType;
    }
    
    @Override
    public int getDefaultPixelDataFormat() {
        this.evalPixelDataType();
        return this.pixelDataFormat;
    }
    
    private final void evalPixelDataType() {
        if (!this.pixelDataEvaluated) {
            int n = 0;
            if (this.isGLES2Compatible() || this.isExtensionAvailable("GL_OES_read_format")) {
                final int[] array = { 0, 0 };
                this.gl.glGetIntegerv(35739, array, 0);
                this.gl.glGetIntegerv(35738, array, 1);
                this.pixelDataFormat = array[0];
                this.pixelDataType = array[1];
                n = ((0 != this.pixelDataFormat && 0 != this.pixelDataType) ? 1 : 0);
            }
            if (n == 0) {
                this.pixelDataFormat = 6408;
                this.pixelDataType = 5121;
            }
            this.pixelDataEvaluated = true;
        }
    }
    
    @Override
    public final boolean setSwapInterval(final int swapIntervalNC) throws GLException {
        this.validateCurrent();
        return this.setSwapIntervalNC(swapIntervalNC);
    }
    
    protected final boolean setSwapIntervalNC(final int swapIntervalImpl2) throws GLException {
        if (!this.drawableRetargeted || !this.hasRendererQuirk(4)) {
            final Integer setSwapIntervalImpl2 = this.setSwapIntervalImpl2(swapIntervalImpl2);
            if (null != setSwapIntervalImpl2) {
                this.currentSwapInterval = setSwapIntervalImpl2;
                return true;
            }
        }
        return false;
    }
    
    protected abstract Integer setSwapIntervalImpl2(final int p0);
    
    @Override
    public final int getSwapInterval() {
        return this.currentSwapInterval;
    }
    
    @Override
    protected final void setDefaultSwapInterval() {
        this.currentSwapInterval = 0;
        this.setSwapIntervalNC(1);
    }
    
    public final GLBufferObjectTracker getBufferObjectTracker() {
        return this.bufferObjectTracker;
    }
    
    public final GLBufferStateTracker getBufferStateTracker() {
        return this.bufferStateTracker;
    }
    
    public final GLStateTracker getGLStateTracker() {
        return this.glStateTracker;
    }
    
    public final boolean isOwner(final Thread thread) {
        return this.lock.isOwner(thread);
    }
    
    public final boolean hasWaiters() {
        return this.lock.getQueueLength() > 0;
    }
    
    public final int getLockCount() {
        return this.lock.getHoldCount();
    }
    
    public final void setBoundFramebuffer(final int n, final int n2) {
        if (0 > n2) {
            return;
        }
        switch (n) {
            case 36009:
            case 36160: {
                this.boundFBOTarget[0] = n2;
                break;
            }
            case 36008: {
                this.boundFBOTarget[1] = n2;
                break;
            }
        }
    }
    
    @Override
    public final int getBoundFramebuffer(final int n) {
        switch (n) {
            case 36009:
            case 36160: {
                return this.boundFBOTarget[0];
            }
            case 36008: {
                return this.boundFBOTarget[1];
            }
            default: {
                throw new InternalError("Invalid FBO target name: " + GLContext.toHexString(n));
            }
        }
    }
    
    @Override
    public final int getDefaultDrawFramebuffer() {
        return this.drawable.getDefaultDrawFramebuffer();
    }
    
    @Override
    public final int getDefaultReadFramebuffer() {
        return this.drawable.getDefaultReadFramebuffer();
    }
    
    @Override
    public final int getDefaultReadBuffer() {
        return this.drawable.getDefaultReadBuffer(this.gl, this.drawableRead != this.drawable);
    }
    
    @Override
    public final String getGLDebugMessageExtension() {
        return this.glDebugHandler.getExtension();
    }
    
    @Override
    public final boolean isGLDebugMessageEnabled() {
        return this.glDebugHandler.isEnabled();
    }
    
    @Override
    public final int getContextCreationFlags() {
        return this.additionalCtxCreationFlags;
    }
    
    @Override
    public final void setContextCreationFlags(final int n) {
        if (!this.isCreated()) {
            this.additionalCtxCreationFlags = (n & 0x20);
        }
    }
    
    @Override
    public final boolean isGLDebugSynchronous() {
        return this.glDebugHandler.isSynchronous();
    }
    
    @Override
    public final void setGLDebugSynchronous(final boolean synchronous) {
        this.glDebugHandler.setSynchronous(synchronous);
    }
    
    @Override
    public final void enableGLDebugMessage(final boolean b) throws GLException {
        if (!this.isCreated()) {
            if (b) {
                this.additionalCtxCreationFlags |= 0x20;
            }
            else {
                this.additionalCtxCreationFlags &= 0xFFFFFFDF;
            }
        }
        else if (0x0 != (this.additionalCtxCreationFlags & 0x20) && null != this.getGLDebugMessageExtension()) {
            this.glDebugHandler.enable(b);
        }
    }
    
    @Override
    public final void addGLDebugListener(final GLDebugListener glDebugListener) {
        this.glDebugHandler.addListener(glDebugListener);
    }
    
    @Override
    public final void removeGLDebugListener(final GLDebugListener glDebugListener) {
        this.glDebugHandler.removeListener(glDebugListener);
    }
    
    @Override
    public final void glDebugMessageControl(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final boolean b) {
        if (this.glDebugHandler.isExtensionKHRARB()) {
            this.gl.getGL2ES2().glDebugMessageControl(n, n2, n3, n4, intBuffer, b);
        }
        else if (this.glDebugHandler.isExtensionAMD()) {
            this.gl.getGL2GL3().glDebugMessageEnableAMD(GLDebugMessage.translateARB2AMDCategory(n, n2), n3, n4, intBuffer, b);
        }
    }
    
    @Override
    public final void glDebugMessageControl(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final boolean b) {
        if (this.glDebugHandler.isExtensionKHRARB()) {
            this.gl.getGL2ES2().glDebugMessageControl(n, n2, n3, n4, array, n5, b);
        }
        else if (this.glDebugHandler.isExtensionAMD()) {
            this.gl.getGL2GL3().glDebugMessageEnableAMD(GLDebugMessage.translateARB2AMDCategory(n, n2), n3, n4, array, n5, b);
        }
    }
    
    @Override
    public final void glDebugMessageInsert(final int n, final int n2, final int n3, final int n4, final String s) {
        final int n5 = (null != s) ? s.length() : 0;
        if (this.glDebugHandler.isExtensionKHRARB()) {
            this.gl.getGL2ES2().glDebugMessageInsert(n, n2, n3, n4, n5, s);
        }
        else if (this.glDebugHandler.isExtensionAMD()) {
            this.gl.getGL2GL3().glDebugMessageInsertAMD(GLDebugMessage.translateARB2AMDCategory(n, n2), n4, n3, n5, s);
        }
    }
    
    private static native String glGetStringInt(final int p0, final long p1);
    
    private static native void glGetIntegervInt(final int p0, final int[] p1, final int p2, final long p3);
    
    static {
        mappedContextTypeObjectLock = new Object();
        mappedExtensionAvailabilityCache = new HashMap<String, ExtensionAvailabilityCache>();
        mappedGLProcAddress = new HashMap<String, ProcAddressTable>();
        mappedGLXProcAddress = new HashMap<String, ProcAddressTable>();
        GLContextImpl.mapGLVersionListener = null;
    }
    
    public static class MappedGLVersion
    {
        public final AbstractGraphicsDevice device;
        public final int reqMajorVersion;
        public final int reqProfile;
        public final VersionNumber ctxVersion;
        public final int ctxOptions;
        public final GLRendererQuirks quirks;
        public final VersionNumber preCtxVersion;
        public final int preCtxOptions;
        
        public MappedGLVersion(final AbstractGraphicsDevice device, final int reqMajorVersion, final int reqProfile, final VersionNumber ctxVersion, final int ctxOptions, final GLRendererQuirks quirks, final VersionNumber preCtxVersion, final int preCtxOptions) {
            this.device = device;
            this.reqMajorVersion = reqMajorVersion;
            this.reqProfile = reqProfile;
            this.ctxVersion = ctxVersion;
            this.ctxOptions = ctxOptions;
            this.quirks = quirks;
            this.preCtxVersion = preCtxVersion;
            this.preCtxOptions = preCtxOptions;
        }
        
        @Override
        public final String toString() {
            return this.toString(new StringBuilder(), -1, -1, -1, -1).toString();
        }
        
        public final StringBuilder toString(final StringBuilder sb, final int n, final int n2, final int n3, final int n4) {
            sb.append(this.device.toString()).append(" ").append(this.reqMajorVersion).append(" (");
            GLContext.getGLProfile(sb, this.reqProfile).append(")");
            if (n >= 0 && n2 >= 0 && n3 >= 0 && n4 >= 0) {
                sb.append("[").append(n).append(".").append(n2).append(" .. ").append(n3).append(".").append(n4).append("]");
            }
            sb.append(": [");
            if (null != this.preCtxVersion) {
                GLContext.getGLVersion(sb, this.preCtxVersion, this.preCtxOptions, null);
            }
            else {
                sb.append("None");
            }
            sb.append("] -> [");
            GLContext.getGLVersion(sb, this.ctxVersion, this.ctxOptions, null).append("]");
            return sb;
        }
    }
    
    public interface MappedGLVersionListener
    {
        void glVersionMapped(final MappedGLVersion p0);
    }
}
