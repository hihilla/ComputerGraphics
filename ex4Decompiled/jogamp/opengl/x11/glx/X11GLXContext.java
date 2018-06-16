// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import java.util.HashMap;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import java.nio.IntBuffer;
import jogamp.opengl.GLDynamicLookupHelper;
import com.jogamp.common.ExceptionUtils;
import jogamp.nativewindow.x11.X11Lib;
import jogamp.nativewindow.x11.X11Util;
import com.jogamp.common.nio.Buffers;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import com.jogamp.opengl.GLContext;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.common.util.VersionNumber;
import java.util.Map;
import jogamp.opengl.GLContextImpl;

public class X11GLXContext extends GLContextImpl
{
    private static final Map<String, String> extensionNameMap;
    private GLXExt _glXExt;
    private GLXExtProcAddressTable glXExtProcAddressTable;
    private int hasSwapInterval;
    private int hasSwapGroupNV;
    protected boolean isDirect;
    protected volatile VersionNumber glXServerVersion;
    protected volatile boolean isGLXVersionGreaterEqualOneThree;
    private static final int ctx_arb_attribs_idx_major = 0;
    private static final int ctx_arb_attribs_idx_minor = 2;
    private static final int ctx_arb_attribs_idx_flags = 6;
    private static final int ctx_arb_attribs_idx_profile = 8;
    private static final int[] ctx_arb_attribs_rom;
    
    X11GLXContext(final GLDrawableImpl glDrawableImpl, final GLContext glContext) {
        super(glDrawableImpl, glContext);
        this.hasSwapInterval = 0;
        this.hasSwapGroupNV = 0;
    }
    
    @Override
    protected void resetStates(final boolean b) {
        this.glXExtProcAddressTable = null;
        this.hasSwapInterval = 0;
        this.hasSwapGroupNV = 0;
        this.isDirect = false;
        this.glXServerVersion = null;
        this.isGLXVersionGreaterEqualOneThree = false;
        super.resetStates(b);
    }
    
    @Override
    public final ProcAddressTable getPlatformExtProcAddressTable() {
        return this.getGLXExtProcAddressTable();
    }
    
    public final GLXExtProcAddressTable getGLXExtProcAddressTable() {
        return this.glXExtProcAddressTable;
    }
    
    @Override
    public Object getPlatformGLExtensions() {
        return this.getGLXExt();
    }
    
    public GLXExt getGLXExt() {
        if (this._glXExt == null) {
            this._glXExt = new GLXExtImpl(this);
        }
        return this._glXExt;
    }
    
    @Override
    protected Map<String, String> getFunctionNameMap() {
        return null;
    }
    
    @Override
    protected Map<String, String> getExtensionNameMap() {
        return X11GLXContext.extensionNameMap;
    }
    
    protected final boolean isGLXVersionGreaterEqualOneThree() {
        if (null != this.glXServerVersion) {
            return this.isGLXVersionGreaterEqualOneThree;
        }
        this.glXServerVersion = ((X11GLXDrawableFactory)this.drawable.getFactoryImpl()).getGLXVersionNumber(this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice());
        return this.isGLXVersionGreaterEqualOneThree = (null != this.glXServerVersion && this.glXServerVersion.compareTo(X11GLXDrawableFactory.versionOneThree) >= 0);
    }
    
    protected final void forceGLXVersionOneOne() {
        this.glXServerVersion = X11GLXDrawableFactory.versionOneOne;
        this.isGLXVersionGreaterEqualOneThree = false;
        if (X11GLXContext.DEBUG) {
            System.err.println("X11GLXContext.forceGLXVersionNumber: " + this.glXServerVersion);
        }
    }
    
    @Override
    public final boolean isGLReadDrawableAvailable() {
        return this.isGLXVersionGreaterEqualOneThree();
    }
    
    private final boolean glXMakeContextCurrent(final long n, final long n2, final long n3, final long n4) {
        boolean b = false;
        try {
            if (this.isGLXVersionGreaterEqualOneThree()) {
                b = GLX.glXMakeContextCurrent(n, n2, n3, n4);
            }
            else {
                if (n2 != n3) {
                    throw new InternalError("Given readDrawable but no driver support");
                }
                b = GLX.glXMakeCurrent(n, n2, n4);
            }
        }
        catch (RuntimeException ex) {
            if (X11GLXContext.DEBUG_TRACE_SWITCH) {
                System.err.println(GLContext.getThreadName() + ": Warning: X11GLXContext.glXMakeContextCurrent failed: " + ex + ", with " + "dpy " + GLContext.toHexString(n) + ", write " + GLContext.toHexString(n2) + ", read " + GLContext.toHexString(n3) + ", ctx " + GLContext.toHexString(n4));
                ex.printStackTrace();
            }
        }
        return b;
    }
    
    private final boolean glXReleaseContext(final long n) {
        boolean b = false;
        try {
            if (this.isGLXVersionGreaterEqualOneThree()) {
                b = GLX.glXMakeContextCurrent(n, 0L, 0L, 0L);
            }
            else {
                b = GLX.glXMakeCurrent(n, 0L, 0L);
            }
        }
        catch (RuntimeException ex) {
            if (X11GLXContext.DEBUG_TRACE_SWITCH) {
                System.err.println(GLContext.getThreadName() + ": Warning: X11GLXContext.glXReleaseContext failed: " + ex + ", with " + "dpy " + GLContext.toHexString(n));
                ex.printStackTrace();
            }
        }
        return b;
    }
    
    @Override
    protected void destroyContextARBImpl(final long n) {
        final long displayHandle = this.drawable.getNativeSurface().getDisplayHandle();
        this.glXReleaseContext(displayHandle);
        GLX.glXDestroyContext(displayHandle, n);
    }
    
    @Override
    protected long createContextARBImpl(final long n, final boolean b, final int n2, final int n3, final int n4) {
        if (X11GLXContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": X11GLXContext.createContextARBImpl: " + GLContext.getGLVersion(n3, n4, n2, "@creation") + ", handle " + GLContext.toHexString(this.drawable.getHandle()) + ", share " + GLContext.toHexString(n) + ", direct " + b);
        }
        final boolean b2 = 0x0 == (0x8 & n2);
        final boolean b3 = 0x0 != (0x2 & n2);
        final boolean b4 = 0x0 != (0x10 & n2);
        final boolean b5 = 0x0 != (0x20 & n2);
        if (!b2) {
            if (X11GLXContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": X11GLXContext.createContextARBImpl: GL ES not avail " + GLContext.getGLVersion(n3, n4, n2, "@creation"));
            }
            return 0L;
        }
        final GLDynamicLookupHelper glDynamicLookupHelper = this.getGLDynamicLookupHelper(n3, n2);
        if (null == glDynamicLookupHelper) {
            if (X11GLXContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + " - X11GLXContext.createContextARBImpl: Null GLDynamicLookupHelper");
            }
            return 0L;
        }
        this.updateGLXProcAddressTable(null, glDynamicLookupHelper);
        final GLXExt glxExt = this.getGLXExt();
        if (X11GLXContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": X11GLXContext.createContextARBImpl: " + ", glXCreateContextAttribsARB: " + GLContext.toHexString(this.glXExtProcAddressTable._addressof_glXCreateContextAttribsARB));
        }
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(X11GLXContext.ctx_arb_attribs_rom);
        directIntBuffer.put(1, n3);
        directIntBuffer.put(3, n4);
        if (n3 > 3 || (n3 == 3 && n4 >= 2)) {
            directIntBuffer.put(8, 37158);
            if (b3) {
                directIntBuffer.put(9, 2);
            }
            else {
                directIntBuffer.put(9, 1);
            }
        }
        if (n3 >= 3) {
            int value = directIntBuffer.get(7);
            if (!b3 && b4) {
                value |= 0x2;
            }
            if (b5) {
                value |= 0x1;
            }
            directIntBuffer.put(7, value);
        }
        final X11GLXGraphicsConfiguration x11GLXGraphicsConfiguration = (X11GLXGraphicsConfiguration)this.drawable.getNativeSurface().getGraphicsConfiguration();
        final long handle = x11GLXGraphicsConfiguration.getScreen().getDevice().getHandle();
        long glXCreateContextAttribsARB = 0L;
        try {
            X11Util.setX11ErrorHandler(true, !X11GLXContext.DEBUG);
            X11Lib.XSync(handle, false);
            glXCreateContextAttribsARB = glxExt.glXCreateContextAttribsARB(handle, x11GLXGraphicsConfiguration.getFBConfig(), n, b, directIntBuffer);
        }
        catch (RuntimeException ex) {
            if (X11GLXContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": Info: X11GLXContext.createContextARBImpl glXCreateContextAttribsARB failed with " + GLContext.getGLVersion(n3, n4, n2, "@creation"));
                ExceptionUtils.dumpThrowable("", ex);
            }
        }
        if (0L != glXCreateContextAttribsARB) {
            if (!this.glXMakeContextCurrent(handle, this.drawable.getHandle(), this.drawableRead.getHandle(), glXCreateContextAttribsARB)) {
                if (X11GLXContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": X11GLXContext.createContextARBImpl couldn't make current " + GLContext.getGLVersion(n3, n4, n2, "@creation"));
                }
                this.glXReleaseContext(handle);
                GLX.glXDestroyContext(handle, glXCreateContextAttribsARB);
                glXCreateContextAttribsARB = 0L;
            }
            else if (X11GLXContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARBImpl: OK " + GLContext.getGLVersion(n3, n4, n2, "@creation") + ", share " + n + ", direct " + b);
            }
        }
        else if (X11GLXContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": createContextARBImpl: NO " + GLContext.getGLVersion(n3, n4, n2, "@creation"));
        }
        return glXCreateContextAttribsARB;
    }
    
    @Override
    protected boolean createImpl(final long n) throws GLException {
        boolean glXIsDirect = true;
        this.isDirect = false;
        final X11GLXDrawableFactory x11GLXDrawableFactory = (X11GLXDrawableFactory)this.drawable.getFactoryImpl();
        final X11GLXGraphicsConfiguration x11GLXGraphicsConfiguration = (X11GLXGraphicsConfiguration)this.drawable.getNativeSurface().getGraphicsConfiguration();
        final AbstractGraphicsDevice device = x11GLXGraphicsConfiguration.getScreen().getDevice();
        final X11GLXContext x11GLXContext = (X11GLXContext)x11GLXDrawableFactory.getOrCreateSharedContext(device);
        final long handle = device.getHandle();
        if (0L != n) {
            glXIsDirect = GLX.glXIsDirect(handle, n);
        }
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)x11GLXGraphicsConfiguration.getChosenCapabilities();
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final boolean b = this.isCreateContextARBAvail(device) && x11GLXGraphicsConfiguration.hasFBConfig();
        final boolean b2 = null != x11GLXContext && x11GLXContext.isCreatedWithARBMethod();
        if (X11GLXContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": X11GLXContext.createImpl: START " + glCapabilitiesImmutable + ", share " + GLContext.toHexString(n));
            System.err.println(GLContext.getThreadName() + ": Use ARB[avail[" + this.getCreateContextARBAvailStr(device) + "], fbCfg " + x11GLXGraphicsConfiguration.hasFBConfig() + " -> " + b + "], shared " + b2 + "]");
        }
        if (glProfile.isGLES()) {
            throw new GLException(GLContext.getThreadName() + ": Unable to create OpenGL ES context on desktopDevice " + device + ", config " + x11GLXGraphicsConfiguration + ", " + glProfile + ", shareWith " + GLContext.toHexString(n));
        }
        if (x11GLXGraphicsConfiguration.hasFBConfig()) {
            boolean b3 = false;
            if (b && b2) {
                this.contextHandle = this.createContextARB(n, glXIsDirect);
                b3 = true;
                if (X11GLXContext.DEBUG && 0L != this.contextHandle) {
                    System.err.println(GLContext.getThreadName() + ": createImpl: OK (ARB, using sharedContext) share " + GLContext.toHexString(n));
                }
            }
            long glXCreateNewContext;
            if (0L == this.contextHandle) {
                glXCreateNewContext = GLX.glXCreateNewContext(handle, x11GLXGraphicsConfiguration.getFBConfig(), 32788, n, glXIsDirect);
                if (0L == glXCreateNewContext) {
                    throw new GLException(GLContext.getThreadName() + ": Unable to create temp OpenGL context(1)");
                }
                if (!this.glXMakeContextCurrent(handle, this.drawable.getHandle(), this.drawableRead.getHandle(), glXCreateNewContext)) {
                    throw new GLException(GLContext.getThreadName() + ": Error making temp context(1) current: display " + GLContext.toHexString(handle) + ", context " + GLContext.toHexString(glXCreateNewContext) + ", drawable " + this.drawable);
                }
                if (!this.setGLFunctionAvailability(true, 0, 0, 2, false, null == x11GLXContext)) {
                    this.glXReleaseContext(handle);
                    GLX.glXDestroyContext(handle, glXCreateNewContext);
                    throw new GLException("setGLFunctionAvailability !strictMatch failed.2");
                }
                this.glXReleaseContext(handle);
                if (b && !b3) {
                    final boolean functionAvailable = this.isFunctionAvailable("glXCreateContextAttribsARB");
                    final boolean extensionAvailable = this.isExtensionAvailable("GLX_ARB_create_context");
                    if (functionAvailable && extensionAvailable) {
                        this.contextHandle = this.createContextARB(n, glXIsDirect);
                        b3 = true;
                        if (X11GLXContext.DEBUG) {
                            if (0L != this.contextHandle) {
                                System.err.println(GLContext.getThreadName() + ": createImpl: OK (ARB, initial) share " + GLContext.toHexString(n));
                            }
                            else {
                                System.err.println(GLContext.getThreadName() + ": createImpl: NOT OK (ARB, initial) - creation failed - share " + GLContext.toHexString(n));
                            }
                        }
                    }
                    else if (X11GLXContext.DEBUG) {
                        System.err.println(GLContext.getThreadName() + ": createImpl: NOT OK (ARB, initial) - extension not available - share " + GLContext.toHexString(n) + ", isProcCreateContextAttribsARBAvailable " + functionAvailable + ", isExtGLXARBCreateContextAvailable " + extensionAvailable);
                    }
                }
            }
            else {
                glXCreateNewContext = 0L;
            }
            if (0L != this.contextHandle) {
                if (0L != glXCreateNewContext) {
                    this.glXReleaseContext(handle);
                    GLX.glXDestroyContext(handle, glXCreateNewContext);
                    if (!this.glXMakeContextCurrent(handle, this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
                        throw new GLException(GLContext.getThreadName() + ": Cannot make previous verified context current");
                    }
                }
            }
            else {
                if (glProfile.isGL3() && b3) {
                    this.glXReleaseContext(handle);
                    GLX.glXDestroyContext(handle, glXCreateNewContext);
                    throw new GLException(GLContext.getThreadName() + ": createImpl ARB n/a but required, profile > GL2 requested (OpenGL >= 3.1). Requested: " + glProfile + ", current: " + this.getGLVersion());
                }
                if (X11GLXContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": createImpl ARB not used[avail " + b + ", tried " + b3 + "], fall back to !ARB context " + this.getGLVersion());
                }
                this.contextHandle = glXCreateNewContext;
                if (!this.glXMakeContextCurrent(handle, this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
                    this.glXReleaseContext(handle);
                    GLX.glXDestroyContext(handle, glXCreateNewContext);
                    throw new GLException(GLContext.getThreadName() + ": Error making context(1) current: display " + GLContext.toHexString(handle) + ", context " + GLContext.toHexString(this.contextHandle) + ", drawable " + this.drawable);
                }
                if (X11GLXContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": createImpl: OK (old-2) share " + GLContext.toHexString(n));
                }
            }
            this.isDirect = GLX.glXIsDirect(handle, this.contextHandle);
            if (X11GLXContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createImpl: OK direct " + this.isDirect + "/" + glXIsDirect);
            }
            return true;
        }
        this.forceGLXVersionOneOne();
        if (glProfile.isGL3()) {
            throw new GLException(GLContext.getThreadName() + ": Unable to create OpenGL >= 3.1 context w/o FBConfig");
        }
        this.contextHandle = GLX.glXCreateContext(handle, x11GLXGraphicsConfiguration.getXVisualInfo(), n, glXIsDirect);
        if (0L == this.contextHandle) {
            throw new GLException(GLContext.getThreadName() + ": Unable to create context(0)");
        }
        if (!this.glXMakeContextCurrent(handle, this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
            throw new GLException(GLContext.getThreadName() + ": Error making temp context(0) current: display " + GLContext.toHexString(handle) + ", context " + GLContext.toHexString(this.contextHandle) + ", drawable " + this.drawable);
        }
        if (!this.setGLFunctionAvailability(true, 0, 0, 2, false, null == x11GLXContext)) {
            this.glXReleaseContext(handle);
            GLX.glXDestroyContext(handle, this.contextHandle);
            this.contextHandle = 0L;
            throw new GLException("setGLFunctionAvailability !strictMatch failed.1");
        }
        this.isDirect = GLX.glXIsDirect(handle, this.contextHandle);
        if (X11GLXContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": createImpl: OK (old-1) share " + GLContext.toHexString(n) + ", direct " + this.isDirect + "/" + glXIsDirect);
        }
        return true;
    }
    
    @Override
    protected void makeCurrentImpl() throws GLException {
        final long displayHandle = this.drawable.getNativeSurface().getDisplayHandle();
        if (!this.glXMakeContextCurrent(displayHandle, this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
            throw new GLException("Error making context " + GLContext.toHexString(this.contextHandle) + " current on Thread " + GLContext.getThreadName() + " with display " + GLContext.toHexString(displayHandle) + ", drawableWrite " + GLContext.toHexString(this.drawable.getHandle()) + ", drawableRead " + GLContext.toHexString(this.drawableRead.getHandle()) + " - " + this);
        }
    }
    
    @Override
    protected void releaseImpl() throws GLException {
        if (!this.glXReleaseContext(this.drawable.getNativeSurface().getDisplayHandle())) {
            throw new GLException(GLContext.getThreadName() + ": Error freeing OpenGL context");
        }
    }
    
    @Override
    protected void destroyImpl() throws GLException {
        this.destroyContextARBImpl(this.contextHandle);
    }
    
    @Override
    protected void copyImpl(final GLContext glContext, final int n) throws GLException {
        final long handle = this.getHandle();
        final long handle2 = glContext.getHandle();
        final long displayHandle = this.drawable.getNativeSurface().getDisplayHandle();
        if (0L == displayHandle) {
            throw new GLException(GLContext.getThreadName() + ": Connection to X display not yet set up");
        }
        GLX.glXCopyContext(displayHandle, handle2, handle, n);
    }
    
    @Override
    protected final void updateGLXProcAddressTable(final String s, final GLDynamicLookupHelper glDynamicLookupHelper) {
        if (null == glDynamicLookupHelper) {
            throw new GLException("No GLDynamicLookupHelper for " + this);
        }
        final String string = "GLX-" + this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice().getUniqueID();
        if (X11GLXContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": Initializing GLX extension address table: " + string);
        }
        ProcAddressTable procAddressTable = null;
        synchronized (X11GLXContext.mappedContextTypeObjectLock) {
            procAddressTable = X11GLXContext.mappedGLXProcAddress.get(string);
        }
        if (null != procAddressTable) {
            this.glXExtProcAddressTable = (GLXExtProcAddressTable)procAddressTable;
            if (X11GLXContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext GLX ProcAddressTable reusing key(" + string + ") -> " + GLContext.toHexString(procAddressTable.hashCode()));
            }
        }
        else {
            this.glXExtProcAddressTable = new GLXExtProcAddressTable(new GLProcAddressResolver());
            this.resetProcAddressTable(this.getGLXExtProcAddressTable(), glDynamicLookupHelper);
            synchronized (X11GLXContext.mappedContextTypeObjectLock) {
                X11GLXContext.mappedGLXProcAddress.put(string, this.getGLXExtProcAddressTable());
                if (X11GLXContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext GLX ProcAddressTable mapping key(" + string + ") -> " + GLContext.toHexString(this.getGLXExtProcAddressTable().hashCode()));
                }
            }
        }
    }
    
    @Override
    protected final StringBuilder getPlatformExtensionsStringImpl() {
        final NativeSurface nativeSurface = this.drawable.getNativeSurface();
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)nativeSurface.getGraphicsConfiguration().getScreen().getDevice();
        final StringBuilder sb = new StringBuilder();
        x11GraphicsDevice.lock();
        try {
            if (X11GLXContext.DEBUG) {
                System.err.println("GLX Version client " + GLXUtil.getClientVersionNumber() + ", server: " + GLXUtil.getGLXServerVersionNumber(x11GraphicsDevice));
            }
            if (((X11GLXDrawableFactory)this.drawable.getFactoryImpl()).isGLXVersionGreaterEqualOneOne(x11GraphicsDevice)) {
                final String glXGetClientString = GLX.glXGetClientString(x11GraphicsDevice.getHandle(), 3);
                if (X11GLXContext.DEBUG) {
                    System.err.println("GLX extensions (glXGetClientString): " + glXGetClientString);
                }
                sb.append(glXGetClientString).append(" ");
                final String glXQueryExtensionsString = GLX.glXQueryExtensionsString(x11GraphicsDevice.getHandle(), nativeSurface.getScreenIndex());
                if (X11GLXContext.DEBUG) {
                    System.err.println("GLX extensions (glXQueryExtensionsString): " + glXQueryExtensionsString);
                }
                sb.append(glXQueryExtensionsString).append(" ");
                final String glXQueryServerString = GLX.glXQueryServerString(x11GraphicsDevice.getHandle(), nativeSurface.getScreenIndex(), 3);
                if (X11GLXContext.DEBUG) {
                    System.err.println("GLX extensions (glXQueryServerString): " + glXQueryServerString);
                }
                sb.append(glXQueryServerString).append(" ");
            }
        }
        finally {
            x11GraphicsDevice.unlock();
        }
        return sb;
    }
    
    @Override
    protected final Integer setSwapIntervalImpl2(final int n) {
        if (!this.drawable.getChosenGLCapabilities().isOnscreen()) {
            return null;
        }
        final long displayHandle = this.drawable.getNativeSurface().getDisplayHandle();
        if (0 == this.hasSwapInterval) {
            try {
                if (this.isExtensionAvailable("GLX_EXT_swap_control")) {
                    this.hasSwapInterval = 1;
                    if (this.isExtensionAvailable("GLX_EXT_swap_control_tear")) {
                        try {
                            final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
                            GLX.glXQueryDrawable(displayHandle, this.drawable.getHandle(), 8435, directIntBuffer);
                            if (1 == directIntBuffer.get(0)) {
                                this.hasSwapInterval = 2;
                                if (X11GLXContext.DEBUG) {
                                    System.err.println("X11GLXContext.setSwapInterval.2 using: GLX_EXT_swap_control_tear, GLX_EXT_swap_control_tear");
                                }
                            }
                            else if (X11GLXContext.DEBUG) {
                                System.err.println("X11GLXContext.setSwapInterval.2 n/a: GLX_EXT_swap_control_tear, query: " + directIntBuffer.get(0));
                            }
                        }
                        catch (Throwable t) {
                            if (X11GLXContext.DEBUG) {
                                ExceptionUtils.dumpThrowable("", t);
                            }
                        }
                    }
                    if (X11GLXContext.DEBUG && 1 == this.hasSwapInterval) {
                        System.err.println("X11GLXContext.setSwapInterval.1 using: GLX_EXT_swap_control");
                    }
                }
                else if (this.isExtensionAvailable("GLX_SGI_swap_control")) {
                    this.hasSwapInterval = 3;
                    if (X11GLXContext.DEBUG) {
                        System.err.println("X11GLXContext.setSwapInterval.3 using: GLX_SGI_swap_control");
                    }
                }
                else {
                    this.hasSwapInterval = -1;
                    if (X11GLXContext.DEBUG) {
                        System.err.println("X11GLXContext.setSwapInterval.0 N/A");
                    }
                }
            }
            catch (Throwable t2) {
                this.hasSwapInterval = -1;
                if (X11GLXContext.DEBUG) {
                    ExceptionUtils.dumpThrowable("", t2);
                }
            }
        }
        if (3 == this.hasSwapInterval) {
            int abs;
            if (0 > n) {
                abs = Math.abs(n);
            }
            else {
                abs = n;
            }
            try {
                if (0 == this.getGLXExt().glXSwapIntervalSGI(abs)) {
                    return abs;
                }
            }
            catch (Throwable t3) {
                this.hasSwapInterval = -1;
                if (X11GLXContext.DEBUG) {
                    ExceptionUtils.dumpThrowable("", t3);
                }
            }
        }
        else if (0 < this.hasSwapInterval) {
            int abs2;
            if (1 == this.hasSwapInterval && 0 > n) {
                abs2 = Math.abs(n);
            }
            else {
                abs2 = n;
            }
            try {
                GLX.glXSwapIntervalEXT(displayHandle, this.drawable.getHandle(), abs2);
                return abs2;
            }
            catch (Throwable t4) {
                this.hasSwapInterval = -1;
                if (X11GLXContext.DEBUG) {
                    ExceptionUtils.dumpThrowable("", t4);
                }
            }
        }
        return null;
    }
    
    private final int initSwapGroupImpl(final GLXExt glxExt) {
        if (0 == this.hasSwapGroupNV) {
            try {
                this.hasSwapGroupNV = (glxExt.isExtensionAvailable("GLX_NV_swap_group") ? 1 : -1);
            }
            catch (Throwable t) {
                this.hasSwapGroupNV = 1;
            }
            if (X11GLXContext.DEBUG) {
                System.err.println("initSwapGroupImpl: GLX_NV_swap_group: " + this.hasSwapGroupNV);
            }
        }
        return this.hasSwapGroupNV;
    }
    
    @Override
    protected final boolean queryMaxSwapGroupsImpl(final int[] array, final int n, final int[] array2, final int n2) {
        boolean b = false;
        final GLXExt glxExt = this.getGLXExt();
        if (this.initSwapGroupImpl(glxExt) > 0) {
            final NativeSurface nativeSurface = this.drawable.getNativeSurface();
            try {
                final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(array.length - n);
                final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(array2.length - n2);
                if (glxExt.glXQueryMaxSwapGroupsNV(nativeSurface.getDisplayHandle(), nativeSurface.getScreenIndex(), directIntBuffer, directIntBuffer2)) {
                    directIntBuffer.get(array, n, directIntBuffer.remaining());
                    directIntBuffer2.get(array, n, directIntBuffer2.remaining());
                    b = true;
                }
            }
            catch (Throwable t) {
                this.hasSwapGroupNV = -1;
            }
        }
        return b;
    }
    
    @Override
    protected final boolean joinSwapGroupImpl(final int currentSwapGroup) {
        boolean b = false;
        final GLXExt glxExt = this.getGLXExt();
        if (this.initSwapGroupImpl(glxExt) > 0) {
            try {
                if (glxExt.glXJoinSwapGroupNV(this.drawable.getNativeSurface().getDisplayHandle(), this.drawable.getHandle(), currentSwapGroup)) {
                    this.currentSwapGroup = currentSwapGroup;
                    b = true;
                }
            }
            catch (Throwable t) {
                this.hasSwapGroupNV = -1;
            }
        }
        return b;
    }
    
    @Override
    protected final boolean bindSwapBarrierImpl(final int n, final int n2) {
        boolean b = false;
        final GLXExt glxExt = this.getGLXExt();
        if (this.initSwapGroupImpl(glxExt) > 0) {
            try {
                if (glxExt.glXBindSwapBarrierNV(this.drawable.getNativeSurface().getDisplayHandle(), n, n2)) {
                    b = true;
                }
            }
            catch (Throwable t) {
                this.hasSwapGroupNV = -1;
            }
        }
        return b;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        super.append(sb);
        sb.append(", direct ");
        sb.append(this.isDirect);
        sb.append("] ");
        return sb.toString();
    }
    
    static {
        (extensionNameMap = new HashMap<String, String>()).put("GL_ARB_pbuffer", "GLX_SGIX_pbuffer");
        X11GLXContext.extensionNameMap.put("GL_ARB_pixel_format", "GLX_SGIX_pbuffer");
        ctx_arb_attribs_rom = new int[] { 8337, 0, 8338, 0, 32785, 32788, 8340, 0, 0, 0, 0 };
    }
}
