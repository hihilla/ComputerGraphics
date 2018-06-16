// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLException;
import com.jogamp.common.util.VersionUtil;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.FBObject;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLFBODrawable;

public class GLFBODrawableImpl extends GLDrawableImpl implements GLFBODrawable
{
    protected static final boolean DEBUG;
    protected static final boolean DEBUG_SWAP;
    private final GLDrawableImpl parent;
    private GLCapabilitiesImmutable origParentChosenCaps;
    private boolean initialized;
    private int maxSamples;
    private int fboModeBits;
    private int texUnit;
    private int samples;
    private boolean fboResetQuirk;
    private FBObject[] fbos;
    private int fboIBack;
    private int fboIFront;
    private int pendingFBOReset;
    private boolean fboBound;
    private boolean fboSwapped;
    private static volatile boolean resetQuirkInfoDumped;
    private static final int bufferCount = 2;
    private SwapBufferContext swapBufferContext;
    private static final String illegalBufferName = "Only GL_FRONT and GL_BACK buffer are allowed, passed ";
    
    protected GLFBODrawableImpl(final GLDrawableFactoryImpl glDrawableFactoryImpl, final GLDrawableImpl parent, final NativeSurface nativeSurface, final GLCapabilitiesImmutable glCapabilitiesImmutable, final int texUnit) {
        super(glDrawableFactoryImpl, nativeSurface, glCapabilitiesImmutable, false);
        this.pendingFBOReset = -1;
        this.initialized = false;
        this.fboModeBits = 1;
        this.parent = parent;
        this.origParentChosenCaps = this.getChosenGLCapabilities();
        this.texUnit = texUnit;
        this.samples = glCapabilitiesImmutable.getNumSamples();
        this.fboResetQuirk = false;
        this.swapBufferContext = null;
    }
    
    private final void setupFBO(final GL gl, final int n, final int n2, final int n3, final int n4, final boolean b, final int n5, final int n6, final boolean b2, final boolean b3, final boolean b4) {
        final FBObject fbObject = new FBObject();
        this.fbos[n] = fbObject;
        final boolean b5 = n5 > 0;
        final boolean b6 = n6 > 0;
        fbObject.init(gl, n2, n3, n4);
        if (fbObject.getNumSamples() != n4) {
            throw new InternalError("Sample number mismatch: " + n4 + ", fbos[" + n + "] " + fbObject);
        }
        if (n4 > 0 || !b2) {
            fbObject.attachColorbuffer(gl, 0, b);
        }
        else {
            fbObject.attachTexture2D(gl, 0, b);
        }
        if (b6) {
            if (b5) {
                fbObject.attachRenderbuffer(gl, FBObject.Attachment.Type.DEPTH_STENCIL, n5);
            }
            else {
                fbObject.attachRenderbuffer(gl, FBObject.Attachment.Type.STENCIL, n6);
            }
        }
        else if (b5) {
            fbObject.attachRenderbuffer(gl, FBObject.Attachment.Type.DEPTH, n5);
        }
        if (n4 > 0) {
            final FBObject samplingSink = new FBObject();
            samplingSink.init(gl, n2, n3, 0);
            if (!b2) {
                samplingSink.attachColorbuffer(gl, 0, b);
            }
            else {
                samplingSink.attachTexture2D(gl, 0, b);
            }
            if (b6) {
                if (b5) {
                    samplingSink.attachRenderbuffer(gl, FBObject.Attachment.Type.DEPTH_STENCIL, n5);
                }
                else {
                    samplingSink.attachRenderbuffer(gl, FBObject.Attachment.Type.STENCIL, n6);
                }
            }
            else if (b5) {
                samplingSink.attachRenderbuffer(gl, FBObject.Attachment.Type.DEPTH, n5);
            }
            fbObject.setSamplingSink(samplingSink);
            fbObject.resetSamplingSink(gl);
        }
        fbObject.bind(gl);
        if (b3) {
            gl.glViewport(0, 0, n2, n3);
            gl.glScissor(0, 0, n2, n3);
        }
        if (b5) {
            gl.glClear(16640);
        }
        else {
            gl.glClear(16384);
        }
        if (b4) {
            fbObject.unbind(gl);
        }
        else {
            fbObject.markUnbound();
        }
    }
    
    private final void initialize(final boolean initialized, final GL gl) {
        if (!this.initialized && !initialized) {
            if (GLFBODrawableImpl.DEBUG) {
                System.err.println("GLFBODrawableImpl.initialize(): WARNING - Already unrealized!");
                ExceptionUtils.dumpStack(System.err);
            }
            return;
        }
        if (this.initialized == initialized) {
            throw new IllegalStateException("initialize already in state " + initialized + ": " + this);
        }
        if (initialized) {
            final GLCapabilities glCapabilities = (GLCapabilities)this.getChosenGLCapabilities();
            this.maxSamples = gl.getMaxRenderbufferSamples();
            final int samples = (this.samples <= this.maxSamples) ? this.samples : this.maxSamples;
            if (GLFBODrawableImpl.DEBUG) {
                System.err.println("GLFBODrawableImpl.initialize(): samples " + this.samples + " -> " + samples + "/" + this.maxSamples);
            }
            this.samples = samples;
            int n;
            if (this.samples > 0) {
                n = 1;
            }
            else if (glCapabilities.getDoubleBuffered()) {
                n = 2;
            }
            else {
                n = 1;
            }
            this.fbos = new FBObject[n];
            this.fboIBack = 0;
            this.fboIFront = this.fbos.length - 1;
            if (0x0 == (0x1 & this.fboModeBits) && gl.getContext().hasRendererQuirk(18)) {
                this.fboModeBits |= 0x1;
            }
            final boolean b = 0x0 != (0x1 & this.fboModeBits);
            final boolean b2 = glCapabilities.getAlphaBits() > 0;
            final int surfaceWidth = this.getSurfaceWidth();
            final int surfaceHeight = this.getSurfaceHeight();
            for (int i = 0; i < n; ++i) {
                this.setupFBO(gl, i, surfaceWidth, surfaceHeight, this.samples, b2, glCapabilities.getDepthBits(), glCapabilities.getStencilBits(), b, i == 0 && 0L == this.parent.getHandle(), n - 1 == i);
            }
            this.fbos[0].formatToGLCapabilities(glCapabilities);
            glCapabilities.setDoubleBuffered(glCapabilities.getDoubleBuffered() || this.samples > 0);
        }
        else {
            for (int j = 0; j < this.fbos.length; ++j) {
                this.fbos[j].destroy(gl);
            }
            this.fbos = null;
        }
        this.fboBound = false;
        this.fboSwapped = false;
        this.pendingFBOReset = -1;
        this.initialized = initialized;
        if (GLFBODrawableImpl.DEBUG) {
            System.err.println("GLFBODrawableImpl.initialize(" + initialized + "): " + this);
            ExceptionUtils.dumpStack(System.err);
        }
    }
    
    public final void setSwapBufferContext(final SwapBufferContext swapBufferContext) {
        this.swapBufferContext = swapBufferContext;
    }
    
    private final void reset(final GL gl, final int n, final int n2, final int n3, final int n4, final boolean b, final int n5, final int n6) {
        if (!this.fboResetQuirk) {
            try {
                this.fbos[n].reset(gl, n2, n3, n4);
                if (this.fbos[n].getNumSamples() != n4) {
                    throw new InternalError("Sample number mismatch: " + n4 + ", fbos[" + n + "] " + this.fbos[n]);
                }
                return;
            }
            catch (GLException ex) {
                this.fboResetQuirk = true;
                if (GLFBODrawableImpl.DEBUG && !GLFBODrawableImpl.resetQuirkInfoDumped) {
                    GLFBODrawableImpl.resetQuirkInfoDumped = true;
                    System.err.println("GLFBODrawable: FBO Reset failed: " + ex.getMessage());
                    System.err.println("GLFBODrawable: Enabling FBOResetQuirk, due to GL driver bug.");
                    final JoglVersion instance = JoglVersion.getInstance();
                    System.err.println(VersionUtil.getPlatformInfo());
                    System.err.println(instance.toString());
                    System.err.println(JoglVersion.getGLInfo(gl, null));
                    ex.printStackTrace();
                }
            }
        }
        this.fbos[n].destroy(gl);
        this.setupFBO(gl, n, n2, n3, n4, b, n5, n6, 0x0 != (0x1 & this.fboModeBits), false, true);
    }
    
    private final void reset(final GL gl, int n) throws GLException {
        if (!this.initialized) {
            return;
        }
        final GLContext current = GLContext.getCurrent();
        final GLContext context = gl.getContext();
        final boolean b = null != current && current != context;
        if (GLFBODrawableImpl.DEBUG) {
            System.err.println("GLFBODrawableImpl.reset(newSamples " + n + "): BEGIN - ctxSwitch " + b + ", " + this);
            ExceptionUtils.dumpStack(System.err);
        }
        Throwable t = null;
        Throwable t2 = null;
        context.makeCurrent();
        gl.glFinish();
        this.fboBound = false;
        this.fboSwapped = false;
        try {
            n = ((n <= this.maxSamples) ? n : this.maxSamples);
            if ((0 == this.samples && 0 < n) || (0 < this.samples && 0 == n)) {
                if (GLFBODrawableImpl.DEBUG) {
                    System.err.println("GLFBODrawableImpl.reset(): samples [on/off] reconfig: " + this.samples + " -> " + n + "/" + this.maxSamples);
                }
                this.initialize(false, gl);
                this.samples = n;
                this.initialize(true, gl);
            }
            else {
                if (GLFBODrawableImpl.DEBUG) {
                    System.err.println("GLFBODrawableImpl.reset(): simple reconfig: " + this.samples + " -> " + n + "/" + this.maxSamples);
                }
                final int surfaceWidth = this.getSurfaceWidth();
                final int surfaceHeight = this.getSurfaceHeight();
                this.samples = n;
                this.pendingFBOReset = ((1 < this.fbos.length) ? this.fboIFront : -1);
                final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)this.surface.getGraphicsConfiguration().getChosenCapabilities();
                for (int i = 0; i < this.fbos.length; ++i) {
                    if (this.pendingFBOReset != i) {
                        this.reset(gl, i, surfaceWidth, surfaceHeight, this.samples, glCapabilitiesImmutable.getAlphaBits() > 0, glCapabilitiesImmutable.getDepthBits(), glCapabilitiesImmutable.getStencilBits());
                    }
                }
                this.fbos[0].formatToGLCapabilities((GLCapabilities)this.surface.getGraphicsConfiguration().getChosenCapabilities());
            }
        }
        catch (Throwable t3) {
            t = t3;
            try {
                context.release();
                if (b) {
                    current.makeCurrent();
                }
            }
            catch (Throwable t4) {
                t2 = t4;
            }
        }
        finally {
            try {
                context.release();
                if (b) {
                    current.makeCurrent();
                }
            }
            catch (Throwable t5) {
                t2 = t5;
            }
        }
        if (null != t) {
            throw GLException.newGLException(t);
        }
        if (null != t2) {
            throw GLException.newGLException(t2);
        }
        if (GLFBODrawableImpl.DEBUG) {
            System.err.println("GLFBODrawableImpl.reset(newSamples " + n + "): END " + this);
        }
    }
    
    @Override
    public final GLContext createContext(final GLContext glContext) {
        final GLContext context = this.parent.createContext(glContext);
        context.setGLDrawable(this, false);
        return context;
    }
    
    @Override
    protected final int getDefaultDrawFramebuffer() {
        return this.initialized ? this.fbos[this.fboIBack].getWriteFramebuffer() : 0;
    }
    
    @Override
    protected final int getDefaultReadFramebuffer() {
        return this.initialized ? this.fbos[this.fboIFront].getReadFramebuffer() : 0;
    }
    
    @Override
    protected final int getDefaultReadBuffer(final GL gl, final boolean b) {
        return this.initialized ? this.fbos[this.fboIFront].getDefaultReadBuffer() : 36064;
    }
    
    @Override
    protected final void setRealizedImpl() {
        final MutableGraphicsConfiguration mutableGraphicsConfiguration = (MutableGraphicsConfiguration)this.surface.getGraphicsConfiguration();
        if (this.realized) {
            this.parent.setRealized(true);
            this.origParentChosenCaps = (GLCapabilitiesImmutable)mutableGraphicsConfiguration.getChosenCapabilities();
            final GLCapabilities chosenCapabilities = (GLCapabilities)this.origParentChosenCaps.cloneMutable();
            chosenCapabilities.copyFrom(this.getRequestedGLCapabilities());
            mutableGraphicsConfiguration.setChosenCapabilities(chosenCapabilities);
        }
        else {
            mutableGraphicsConfiguration.setChosenCapabilities(this.origParentChosenCaps);
            this.parent.setRealized(false);
        }
    }
    
    @Override
    protected void associateContext(final GLContext glContext, final boolean b) {
        this.initialize(b, glContext.getGL());
    }
    
    @Override
    protected final void contextMadeCurrent(final GLContext glContext, final boolean b) {
        final GL gl = glContext.getGL();
        if (b) {
            if (!this.initialized) {
                throw new GLException("Not initialized: " + this);
            }
            this.fbos[this.fboIBack].bind(gl);
            this.fboBound = true;
            this.fboSwapped = false;
        }
        else if (this.fboBound && !this.fboSwapped) {
            this.swapFBOImpl(glContext);
            this.swapFBOImplPost(glContext);
            this.fboBound = false;
            this.fboSwapped = true;
            if (GLFBODrawableImpl.DEBUG_SWAP) {
                System.err.println("Post FBO swap(@release): done");
            }
        }
    }
    
    @Override
    protected void swapBuffersImpl(final boolean b) {
        final GLContext current = GLContext.getCurrent();
        boolean b2;
        if (null != current && current.getGLDrawable() == this && this.fboBound) {
            this.swapFBOImpl(current);
            b2 = true;
            this.fboSwapped = true;
            if (GLFBODrawableImpl.DEBUG_SWAP) {
                System.err.println("Post FBO swap(@swap): done");
            }
        }
        else {
            b2 = false;
        }
        if (null != this.swapBufferContext) {
            this.swapBufferContext.swapBuffers(b);
        }
        if (b2) {
            this.swapFBOImplPost(current);
        }
    }
    
    private final void swapFBOImplPost(final GLContext glContext) {
        if (0 <= this.pendingFBOReset) {
            final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)this.surface.getGraphicsConfiguration().getChosenCapabilities();
            this.reset(glContext.getGL(), this.pendingFBOReset, this.getSurfaceWidth(), this.getSurfaceHeight(), this.samples, glCapabilitiesImmutable.getAlphaBits() > 0, glCapabilitiesImmutable.getDepthBits(), glCapabilitiesImmutable.getStencilBits());
            this.pendingFBOReset = -1;
        }
    }
    
    private final void swapFBOImpl(final GLContext glContext) {
        final GL gl = glContext.getGL();
        this.fbos[this.fboIBack].markUnbound();
        if (GLFBODrawableImpl.DEBUG) {
            final int n = (this.fboIFront + 1) % this.fbos.length;
            if (n != this.fboIBack) {
                throw new InternalError("XXX: " + n + "!=" + this.fboIBack);
            }
        }
        this.fboIFront = this.fboIBack;
        this.fboIBack = (this.fboIBack + 1) % this.fbos.length;
        final FBObject.Colorbuffer colorbuffer = (this.samples > 0) ? this.fbos[this.fboIFront].getSamplingSink() : this.fbos[this.fboIFront].getColorbuffer(0);
        if (null == colorbuffer) {
            throw new GLException("Front colorbuffer is null: samples " + this.samples + ", " + this);
        }
        FBObject.TextureAttachment textureAttachment;
        if (colorbuffer.isTextureAttachment()) {
            textureAttachment = colorbuffer.getTextureAttachment();
            gl.glActiveTexture(33984 + this.texUnit);
        }
        else {
            textureAttachment = null;
        }
        this.fbos[this.fboIFront].use(gl, textureAttachment);
        if (GLFBODrawableImpl.DEBUG_SWAP) {
            System.err.println("Post FBO swap(X): fboI back " + this.fboIBack + ", front " + this.fboIFront + ", num " + this.fbos.length);
        }
    }
    
    @Override
    public final boolean isInitialized() {
        return this.initialized;
    }
    
    @Override
    public final void setFBOMode(final int fboModeBits) throws IllegalStateException {
        if (this.isInitialized()) {
            throw new IllegalStateException("Already initialized: " + this);
        }
        this.fboModeBits = fboModeBits;
    }
    
    @Override
    public final int getFBOMode() {
        return this.fboModeBits;
    }
    
    @Override
    public final void resetSize(final GL gl) throws GLException {
        this.reset(gl, this.samples);
    }
    
    @Override
    public final int getTextureUnit() {
        return this.texUnit;
    }
    
    @Override
    public final void setTextureUnit(final int texUnit) {
        this.texUnit = texUnit;
    }
    
    @Override
    public final int getNumSamples() {
        return this.samples;
    }
    
    @Override
    public void setNumSamples(final GL gl, final int n) throws GLException {
        if (this.samples != n) {
            this.reset(gl, n);
        }
    }
    
    @Override
    public final int setNumBuffers(final int n) throws IllegalStateException, GLException {
        if (this.isInitialized()) {
            throw new IllegalStateException("Already initialized: " + this);
        }
        return 2;
    }
    
    @Override
    public final int getNumBuffers() {
        return 2;
    }
    
    @Override
    public FBObject getFBObject(final int n) throws IllegalArgumentException {
        if (!this.initialized) {
            return null;
        }
        FBObject samplingSinkFBO = null;
        switch (n) {
            case 1028: {
                if (this.samples > 0) {
                    samplingSinkFBO = this.fbos[0].getSamplingSinkFBO();
                    break;
                }
                samplingSinkFBO = this.fbos[this.fboIFront];
                break;
            }
            case 1029: {
                samplingSinkFBO = this.fbos[this.fboIBack];
                break;
            }
            default: {
                throw new IllegalArgumentException("Only GL_FRONT and GL_BACK buffer are allowed, passed " + GLDrawableImpl.toHexString(n));
            }
        }
        return samplingSinkFBO;
    }
    
    @Override
    public final FBObject.Colorbuffer getColorbuffer(final int n) throws IllegalArgumentException {
        if (!this.initialized) {
            return null;
        }
        FBObject.Colorbuffer colorbuffer = null;
        switch (n) {
            case 1028: {
                if (this.samples > 0) {
                    colorbuffer = this.fbos[0].getSamplingSink();
                    break;
                }
                colorbuffer = this.fbos[this.fboIFront].getColorbuffer(0);
                break;
            }
            case 1029: {
                if (this.samples > 0) {
                    throw new IllegalArgumentException("Cannot access GL_BACK buffer of MSAA FBO: " + this);
                }
                colorbuffer = this.fbos[this.fboIBack].getColorbuffer(0);
                break;
            }
            default: {
                throw new IllegalArgumentException("Only GL_FRONT and GL_BACK buffer are allowed, passed " + GLDrawableImpl.toHexString(n));
            }
        }
        return colorbuffer;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[Initialized " + this.initialized + ", realized " + this.isRealized() + ", texUnit " + this.texUnit + ", samples " + this.samples + ",\n\tFactory   " + this.getFactory() + ",\n\tHandle    " + GLDrawableImpl.toHexString(this.getHandle()) + ",\n\tCaps      " + this.surface.getGraphicsConfiguration().getChosenCapabilities() + ",\n\tfboI back " + this.fboIBack + ", front " + this.fboIFront + ", num " + (this.initialized ? this.fbos.length : 0) + ",\n\tFBO front read " + this.getDefaultReadFramebuffer() + ", " + this.getFBObject(1028) + ",\n\tFBO back  write " + this.getDefaultDrawFramebuffer() + ", " + this.getFBObject(1029) + ",\n\tSurface   " + this.surface + "]";
    }
    
    static {
        Debug.initSingleton();
        DEBUG = (GLDrawableImpl.DEBUG || Debug.debug("FBObject"));
        DEBUG_SWAP = PropertyAccess.isPropertyDefined("jogl.debug.FBObject.Swap", true);
        GLFBODrawableImpl.resetQuirkInfoDumped = false;
    }
    
    public static class ResizeableImpl extends GLFBODrawableImpl implements Resizeable
    {
        protected ResizeableImpl(final GLDrawableFactoryImpl glDrawableFactoryImpl, final GLDrawableImpl glDrawableImpl, final ProxySurface proxySurface, final GLCapabilitiesImmutable glCapabilitiesImmutable, final int n) {
            super(glDrawableFactoryImpl, glDrawableImpl, proxySurface, glCapabilitiesImmutable, n);
        }
        
        @Override
        public final void setSurfaceSize(final GLContext glContext, final int n, final int n2) throws NativeWindowException, GLException {
            if (ResizeableImpl.DEBUG) {
                System.err.println("GLFBODrawableImpl.ResizeableImpl setSize: (" + GLDrawableImpl.getThreadName() + "): " + n + "x" + n2 + " - surfaceHandle 0x" + Long.toHexString(this.getNativeSurface().getSurfaceHandle()));
            }
            if (1 >= this.lockSurface()) {
                throw new NativeWindowException("Could not lock surface: " + this);
            }
            try {
                final ProxySurface proxySurface = (ProxySurface)this.getNativeSurface();
                final UpstreamSurfaceHook upstreamSurfaceHook = proxySurface.getUpstreamSurfaceHook();
                if (!(upstreamSurfaceHook instanceof UpstreamSurfaceHook.MutableSize)) {
                    throw new InternalError("GLFBODrawableImpl.ResizableImpl's ProxySurface doesn't hold a UpstreamSurfaceHookMutableSize but " + ((UpstreamSurfaceHook.MutableSize)upstreamSurfaceHook).getClass().getName() + ", " + proxySurface + ", ush");
                }
                ((UpstreamSurfaceHook.MutableSize)upstreamSurfaceHook).setSurfaceSize(n, n2);
                if (null != glContext && glContext.isCreated()) {
                    this.resetSize(glContext.getGL());
                }
            }
            finally {
                this.unlockSurface();
            }
        }
    }
    
    public interface SwapBufferContext
    {
        void swapBuffers(final boolean p0);
    }
}
