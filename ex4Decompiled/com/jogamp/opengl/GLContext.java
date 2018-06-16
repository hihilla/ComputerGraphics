// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import java.util.Iterator;
import java.util.Set;
import com.jogamp.common.os.Platform;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import jogamp.opengl.GLContextImpl;
import java.nio.IntBuffer;
import java.util.List;
import jogamp.opengl.GLContextShareSet;
import com.jogamp.common.util.locks.LockFactory;
import java.util.IdentityHashMap;
import com.jogamp.common.util.VersionNumberString;
import com.jogamp.common.util.locks.RecursiveLock;
import java.util.HashMap;
import com.jogamp.common.util.VersionNumber;

public abstract class GLContext
{
    public static final boolean DEBUG;
    public static final boolean TRACE_SWITCH;
    public static final boolean DEBUG_TRACE_SWITCH;
    public static final boolean PROFILE_ALIASING;
    protected static final boolean FORCE_NO_FBO_SUPPORT;
    protected static final boolean FORCE_MIN_FBO_SUPPORT;
    protected static final boolean FORCE_NO_COLOR_RENDERBUFFER;
    public static final boolean DEBUG_GL;
    public static final boolean TRACE_GL;
    public static final int CONTEXT_NOT_CURRENT = 0;
    public static final int CONTEXT_CURRENT = 1;
    public static final int CONTEXT_CURRENT_NEW = 2;
    public static final VersionNumber Version1_0;
    public static final VersionNumber Version1_10;
    public static final VersionNumber Version1_20;
    public static final VersionNumber Version1_30;
    public static final VersionNumber Version1_40;
    public static final VersionNumber Version1_50;
    public static final VersionNumber Version1_1;
    public static final VersionNumber Version1_2;
    public static final VersionNumber Version1_4;
    public static final VersionNumber Version1_5;
    public static final VersionNumber Version3_0;
    public static final VersionNumber Version3_1;
    public static final VersionNumber Version3_2;
    public static final VersionNumber Version4_3;
    protected static final VersionNumber Version8_0;
    private static final String S_EMPTY = "";
    protected static final int CTX_IMPL_FULL_MASK = 65535;
    protected static final int CTX_IMPL_CACHE_MASK = 1023;
    protected static final int CTX_IS_ARB_CREATED = 1;
    protected static final int CTX_PROFILE_COMPAT = 2;
    protected static final int CTX_PROFILE_CORE = 4;
    protected static final int CTX_PROFILE_ES = 8;
    protected static final int CTX_OPTION_FORWARD = 16;
    public static final int CTX_OPTION_DEBUG = 32;
    protected static final int CTX_IMPL_ACCEL_SOFT = 64;
    protected static final int CTX_IMPL_ES2_COMPAT = 1024;
    protected static final int CTX_IMPL_ES3_COMPAT = 2048;
    protected static final int CTX_IMPL_ES31_COMPAT = 4096;
    protected static final int CTX_IMPL_ES32_COMPAT = 8192;
    protected static final int CTX_IMPL_FBO = 16384;
    protected static final int CTX_IMPL_FP32_COMPAT_API = 32768;
    private static final ThreadLocal<GLContext> currentContext;
    private final HashMap<String, Object> attachedObjects;
    protected final RecursiveLock lock;
    protected volatile long contextHandle;
    protected VersionNumber ctxVersion;
    protected int ctxOptions;
    protected String ctxVersionString;
    protected VersionNumberString ctxVendorVersion;
    protected VersionNumber ctxGLSLVersion;
    protected GLRendererQuirks glRendererQuirks;
    protected boolean drawableRetargeted;
    protected int currentSwapGroup;
    public static final int[][] GL_VERSIONS;
    public static final int[][] ES_VERSIONS;
    protected static final IdentityHashMap<String, Integer> deviceVersionAvailable;
    private static final IdentityHashMap<String, String> deviceVersionsAvailableSet;
    
    protected GLContext() {
        this.attachedObjects = new HashMap<String, Object>();
        this.lock = LockFactory.createRecursiveLock();
        this.currentSwapGroup = -1;
        this.resetStates(true);
    }
    
    protected void resetStates(final boolean b) {
        if (GLContext.DEBUG) {
            System.err.println(getThreadName() + ": GLContext.resetStates(isInit " + b + ")");
        }
        this.ctxVersion = VersionNumberString.zeroVersion;
        this.ctxVendorVersion = VersionNumberString.zeroVersion;
        this.ctxOptions = 0;
        this.ctxVersionString = null;
        this.ctxGLSLVersion = VersionNumber.zeroVersion;
        this.attachedObjects.clear();
        this.contextHandle = 0L;
        this.glRendererQuirks = null;
        this.drawableRetargeted = false;
    }
    
    public final boolean isShared() {
        return GLContextShareSet.isShared(this);
    }
    
    public final GLContext getSharedMaster() {
        return GLContextShareSet.getSharedMaster(this);
    }
    
    public final List<GLContext> getCreatedShares() {
        return GLContextShareSet.getCreatedShares(this);
    }
    
    public final List<GLContext> getDestroyedShares() {
        return GLContextShareSet.getDestroyedShares(this);
    }
    
    public final GLRendererQuirks getRendererQuirks() {
        return this.glRendererQuirks;
    }
    
    public final boolean hasRendererQuirk(final int n) throws IllegalArgumentException {
        return null != this.glRendererQuirks && this.glRendererQuirks.exist(n);
    }
    
    public abstract GLDrawable setGLDrawable(final GLDrawable p0, final boolean p1);
    
    public abstract GLDrawable getGLDrawable();
    
    public abstract boolean isGLReadDrawableAvailable();
    
    public abstract GLDrawable setGLReadDrawable(final GLDrawable p0);
    
    public abstract GLDrawable getGLReadDrawable();
    
    public abstract int makeCurrent() throws GLException;
    
    public abstract void release() throws GLException;
    
    public abstract void copy(final GLContext p0, final int p1) throws GLException;
    
    public static GL getCurrentGL() throws GLException {
        final GLContext current = getCurrent();
        if (null == current) {
            throw new GLException(getThreadName() + ": No OpenGL context current on this thread");
        }
        return current.getGL();
    }
    
    public static GLContext getCurrent() {
        return GLContext.currentContext.get();
    }
    
    public final boolean isCurrent() {
        return getCurrent() == this;
    }
    
    public final void validateCurrent() throws GLException {
        if (getCurrent() != this) {
            throw new GLException(getThreadName() + ": This context is not current. Current context: " + getCurrent() + ", this context " + this);
        }
    }
    
    public static final String makeCurrentResultToString(final int n) {
        switch (n) {
            case 0: {
                return "CONTEXT_NOT_CURRENT";
            }
            case 1: {
                return "CONTEXT_CURRENT";
            }
            case 2: {
                return "CONTEXT_CURRENT_NEW";
            }
            default: {
                return "INVALID_VALUE";
            }
        }
    }
    
    protected static void setCurrent(final GLContext glContext) {
        if (GLContext.TRACE_SWITCH) {
            if (null == glContext) {
                System.err.println(getThreadName() + ": GLContext.ContextSwitch: - setCurrent() - NULL");
            }
            else {
                System.err.println(getThreadName() + ": GLContext.ContextSwitch: - setCurrent() - obj " + toHexString(glContext.hashCode()) + ", ctx " + toHexString(glContext.getHandle()));
            }
        }
        GLContext.currentContext.set(glContext);
    }
    
    public abstract void destroy();
    
    public abstract GL getRootGL();
    
    public abstract GL getGL();
    
    public abstract GL setGL(final GL p0);
    
    public final long getHandle() {
        return this.contextHandle;
    }
    
    public final boolean isCreated() {
        return 0L != this.contextHandle;
    }
    
    public final Object getAttachedObject(final String s) {
        return this.attachedObjects.get(s);
    }
    
    public final Object attachObject(final String s, final Object o) {
        return this.attachedObjects.put(s, o);
    }
    
    public final Object detachObject(final String s) {
        return this.attachedObjects.remove(s);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        this.append(sb);
        sb.append("] ");
        return sb.toString();
    }
    
    public final StringBuilder append(final StringBuilder sb) {
        sb.append("Version ").append(this.getGLVersion()).append(" [GL ").append(this.getGLVersionNumber()).append(", vendor ").append(this.getGLVendorVersionNumber());
        sb.append("], options 0x");
        sb.append(Integer.toHexString(this.ctxOptions));
        sb.append(", this ");
        sb.append(toHexString(this.hashCode()));
        sb.append(", handle ");
        sb.append(toHexString(this.contextHandle));
        sb.append(", isShared " + this.isShared() + ", ");
        sb.append(this.getGL());
        sb.append(",\n\t quirks: ");
        if (null != this.glRendererQuirks) {
            this.glRendererQuirks.toString(sb);
        }
        else {
            sb.append("n/a");
        }
        if (this.getGLDrawable() != this.getGLReadDrawable()) {
            sb.append(",\n\tRead Drawable : ");
            sb.append(this.getGLReadDrawable());
            sb.append(",\n\tWrite Drawable: ");
            sb.append(this.getGLDrawable());
        }
        else {
            sb.append(",\n\tDrawable: ");
            sb.append(this.getGLDrawable());
        }
        return sb;
    }
    
    public abstract boolean isFunctionAvailable(final String p0);
    
    public abstract boolean isExtensionAvailable(final String p0);
    
    public abstract int getPlatformExtensionCount();
    
    public abstract String getPlatformExtensionsString();
    
    public abstract int getGLExtensionCount();
    
    public abstract String getGLExtensionsString();
    
    public abstract int getContextCreationFlags();
    
    public abstract void setContextCreationFlags(final int p0);
    
    public final String getGLVersion() {
        return this.ctxVersionString;
    }
    
    public final VersionNumber getGLVersionNumber() {
        return this.ctxVersion;
    }
    
    public final VersionNumberString getGLVendorVersionNumber() {
        return this.ctxVendorVersion;
    }
    
    public final boolean isGLCompatibilityProfile() {
        return 0x0 != (0x2 & this.ctxOptions);
    }
    
    public final boolean isGLCoreProfile() {
        return 0x0 != (0x4 & this.ctxOptions);
    }
    
    public final boolean isGLESProfile() {
        return 0x0 != (0x8 & this.ctxOptions);
    }
    
    public final boolean isGLForwardCompatible() {
        return 0x0 != (0x10 & this.ctxOptions);
    }
    
    public final boolean isGLDebugEnabled() {
        return 0x0 != (0x20 & this.ctxOptions);
    }
    
    public final boolean isCreatedWithARBMethod() {
        return 0x0 != (0x1 & this.ctxOptions);
    }
    
    public final VersionNumber getGLSLVersionNumber() {
        return this.ctxGLSLVersion;
    }
    
    public final String getGLSLVersionString() {
        if (this.ctxGLSLVersion.isZero()) {
            return "";
        }
        final int minor = this.ctxGLSLVersion.getMinor();
        String s;
        if (this.isGLES()) {
            s = ((this.ctxGLSLVersion.compareTo(GLContext.Version3_0) >= 0) ? " es" : "");
        }
        else if (this.isGLCoreProfile()) {
            s = ((this.ctxGLSLVersion.compareTo(GLContext.Version1_50) >= 0) ? " core" : "");
        }
        else {
            if (!this.isGLCompatibilityProfile()) {
                throw new InternalError("Neither ES, Core nor Compat: " + this);
            }
            s = ((this.ctxGLSLVersion.compareTo(GLContext.Version1_50) >= 0) ? " compatibility" : "");
        }
        return "#version " + this.ctxGLSLVersion.getMajor() + ((minor < 10) ? ("0" + minor) : minor) + s + "\n";
    }
    
    protected static final VersionNumber getStaticGLSLVersionNumber(final int n, final int n2, final int n3) {
        if (0x0 != (0x8 & n3)) {
            if (3 == n) {
                return GLContext.Version3_0;
            }
            if (2 == n) {
                return GLContext.Version1_0;
            }
        }
        else {
            if (1 == n) {
                return GLContext.Version1_10;
            }
            if (2 == n) {
                switch (n2) {
                    case 0: {
                        return GLContext.Version1_10;
                    }
                    default: {
                        return GLContext.Version1_20;
                    }
                }
            }
            else if (3 == n && 2 >= n2) {
                switch (n2) {
                    case 0: {
                        return GLContext.Version1_30;
                    }
                    case 1: {
                        return GLContext.Version1_40;
                    }
                    default: {
                        return GLContext.Version1_50;
                    }
                }
            }
        }
        return new VersionNumber(n, n2 * 10, 0);
    }
    
    public final boolean isGLES2Compatible() {
        return 0x0 != (this.ctxOptions & 0xC00);
    }
    
    public final boolean isGLES3Compatible() {
        return 0x0 != (this.ctxOptions & 0x800);
    }
    
    public final boolean isGLES31Compatible() {
        return 0x0 != (this.ctxOptions & 0x1000);
    }
    
    public final boolean isGLES32Compatible() {
        return 0x0 != (this.ctxOptions & 0x2000);
    }
    
    public final boolean isHardwareRasterizer() {
        return 0x0 == (this.ctxOptions & 0x40);
    }
    
    public final boolean hasGLSL() {
        return this.isGLES3() || this.isGLES2() || this.isGL3() || (this.isGL2() && this.ctxVersion.getMajor() > 1);
    }
    
    public final boolean hasBasicFBOSupport() {
        return 0x0 != (this.ctxOptions & 0x4000);
    }
    
    public final boolean hasFullFBOSupport() {
        return this.hasBasicFBOSupport() && !this.hasRendererQuirk(11) && (this.isGL3ES3() || this.isExtensionAvailable("GL_ARB_framebuffer_object") || (this.isExtensionAvailable("GL_EXT_framebuffer_object") && this.isExtensionAvailable("GL_EXT_framebuffer_multisample") && this.isExtensionAvailable("GL_EXT_framebuffer_blit") && this.isExtensionAvailable("GL_EXT_packed_depth_stencil")));
    }
    
    public final boolean hasFP32CompatAPI() {
        return 0x0 != (this.ctxOptions & 0x8000);
    }
    
    public final int getMaxRenderbufferSamples() {
        if (this.hasFullFBOSupport()) {
            final GL gl = this.getGL();
            final int[] array = { 0 };
            try {
                gl.glGetIntegerv(36183, array, 0);
                final int glGetError = gl.glGetError();
                if (glGetError == 0) {
                    return array[0];
                }
                if (GLContext.DEBUG) {
                    System.err.println("GLContext.getMaxRenderbufferSamples: GL_MAX_SAMPLES query GL Error 0x" + Integer.toHexString(glGetError));
                }
            }
            catch (GLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
    
    public boolean isNPOTTextureAvailable() {
        return this.isGL3() || this.isGLES2Compatible() || this.isExtensionAvailable("GL_ARB_texture_non_power_of_two");
    }
    
    public boolean isTextureFormatBGRA8888Available() {
        return this.isGL2GL3() || this.isExtensionAvailable("GL_EXT_texture_format_BGRA8888") || this.isExtensionAvailable("GL_IMG_texture_format_BGRA8888");
    }
    
    public final boolean isGL4bc() {
        return 0x0 != (this.ctxOptions & 0x2) && this.ctxVersion.getMajor() >= 4;
    }
    
    public final boolean isGL4() {
        return 0x0 != (this.ctxOptions & 0x6) && this.ctxVersion.getMajor() >= 4;
    }
    
    public final boolean isGL4core() {
        return 0x0 != (this.ctxOptions & 0x4) && this.ctxVersion.getMajor() >= 4;
    }
    
    public final boolean isGL3bc() {
        return 0x0 != (this.ctxOptions & 0x2) && this.ctxVersion.compareTo(GLContext.Version3_1) >= 0;
    }
    
    public final boolean isGL3() {
        return 0x0 != (this.ctxOptions & 0x6) && this.ctxVersion.compareTo(GLContext.Version3_1) >= 0;
    }
    
    public final boolean isGL3core() {
        return 0x0 != (this.ctxOptions & 0x4) && this.ctxVersion.compareTo(GLContext.Version3_1) >= 0;
    }
    
    public final boolean isGLcore() {
        return (0x0 != (this.ctxOptions & 0x8) && this.ctxVersion.getMajor() >= 2) || (0x0 != (this.ctxOptions & 0x4) && this.ctxVersion.compareTo(GLContext.Version3_1) >= 0);
    }
    
    public final boolean isCPUDataSourcingAvail() {
        return this.isGL2ES1() || this.isGLES2();
    }
    
    public final boolean hasNoDefaultVAO() {
        return 0x0 != (this.ctxOptions & 0x1) && 0x0 != (this.ctxOptions & 0x4) && this.ctxVersion.compareTo(GLContext.Version3_1) >= 0;
    }
    
    public abstract int getDefaultVAO();
    
    public final boolean isGL2() {
        return 0x0 != (this.ctxOptions & 0x2) && this.ctxVersion.getMajor() >= 1;
    }
    
    public final boolean isGL2GL3() {
        return this.isGL2() || this.isGL3();
    }
    
    public final boolean isGLES1() {
        return 0x0 != (this.ctxOptions & 0x8) && this.ctxVersion.getMajor() == 1;
    }
    
    public final boolean isGLES2() {
        if (0x0 != (this.ctxOptions & 0x8)) {
            final int major = this.ctxVersion.getMajor();
            return 2 == major || 3 == major;
        }
        return false;
    }
    
    public final boolean isGLES3() {
        return 0x0 != (this.ctxOptions & 0x8) && this.ctxVersion.getMajor() == 3;
    }
    
    public final boolean isGLES() {
        return 0x0 != (0x8 & this.ctxOptions);
    }
    
    public final boolean isGL2ES1() {
        return this.isGLES1() || this.isGL2();
    }
    
    public final boolean isGL2ES2() {
        return this.isGLES2() || this.isGL2GL3();
    }
    
    public final boolean isGL2ES3() {
        return this.isGL3ES3() || this.isGL2GL3();
    }
    
    public final boolean isGL3ES3() {
        return this.isGL4ES3() || this.isGL3();
    }
    
    public final boolean isGL4ES3() {
        return this.isGLES3Compatible();
    }
    
    public boolean setSwapInterval(final int n) throws GLException {
        throw new InternalError("Implemented in GLContextImpl");
    }
    
    protected boolean setSwapIntervalImpl(final int n) {
        throw new InternalError("Implemented in GLContextImpl");
    }
    
    public int getSwapInterval() {
        throw new InternalError("Implemented in GLContextImpl");
    }
    
    protected void setDefaultSwapInterval() {
        throw new InternalError("Implemented in GLContextImpl");
    }
    
    public final boolean queryMaxSwapGroups(final int[] array, final int n, final int[] array2, final int n2) {
        this.validateCurrent();
        return this.queryMaxSwapGroupsImpl(array, n, array2, n2);
    }
    
    protected boolean queryMaxSwapGroupsImpl(final int[] array, final int n, final int[] array2, final int n2) {
        return false;
    }
    
    public final boolean joinSwapGroup(final int n) {
        this.validateCurrent();
        return this.joinSwapGroupImpl(n);
    }
    
    protected boolean joinSwapGroupImpl(final int n) {
        return false;
    }
    
    public int getSwapGroup() {
        return this.currentSwapGroup;
    }
    
    public final boolean bindSwapBarrier(final int n, final int n2) {
        this.validateCurrent();
        return this.bindSwapBarrierImpl(n, n2);
    }
    
    protected boolean bindSwapBarrierImpl(final int n, final int n2) {
        return false;
    }
    
    public abstract int getBoundFramebuffer(final int p0);
    
    public abstract int getDefaultDrawFramebuffer();
    
    public abstract int getDefaultReadFramebuffer();
    
    public abstract int getDefaultReadBuffer();
    
    public abstract int getDefaultPixelDataType();
    
    public abstract int getDefaultPixelDataFormat();
    
    public abstract String getGLDebugMessageExtension();
    
    public abstract boolean isGLDebugSynchronous();
    
    public abstract void setGLDebugSynchronous(final boolean p0);
    
    public abstract boolean isGLDebugMessageEnabled();
    
    public abstract void enableGLDebugMessage(final boolean p0) throws GLException;
    
    public abstract void addGLDebugListener(final GLDebugListener p0);
    
    public abstract void removeGLDebugListener(final GLDebugListener p0);
    
    public abstract void glDebugMessageControl(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4, final boolean p5);
    
    public abstract void glDebugMessageControl(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5, final boolean p6);
    
    public abstract void glDebugMessageInsert(final int p0, final int p1, final int p2, final int p3, final String p4);
    
    public static final int getMaxMajor(final int n) {
        return (0x0 != (0x8 & n)) ? (GLContext.ES_VERSIONS.length - 1) : (GLContext.GL_VERSIONS.length - 1);
    }
    
    public static final int getMaxMinor(final int n, final int n2) {
        if (1 > n2) {
            return -1;
        }
        if (0x0 != (0x8 & n)) {
            if (n2 >= GLContext.ES_VERSIONS.length) {
                return -1;
            }
            return GLContext.ES_VERSIONS[n2].length - 1;
        }
        else {
            if (n2 >= GLContext.GL_VERSIONS.length) {
                return -1;
            }
            return GLContext.GL_VERSIONS[n2].length - 1;
        }
    }
    
    public static final boolean isValidGLVersion(final int n, final int n2, final int n3) {
        if (1 > n2 || 0 > n3) {
            return false;
        }
        if (0x0 != (0x8 & n)) {
            if (n2 >= GLContext.ES_VERSIONS.length + 1) {
                return false;
            }
        }
        else if (n2 >= GLContext.GL_VERSIONS.length + 1) {
            return false;
        }
        return true;
    }
    
    public static final boolean clipGLVersion(final int n, final int[] array, final int[] array2) {
        final int n2 = array[0];
        final int n3 = array2[0];
        if (0x0 != (0x8 & n)) {
            if (n2 >= GLContext.ES_VERSIONS.length) {
                array[0] = GLContext.ES_VERSIONS.length - 1;
                array2[0] = GLContext.ES_VERSIONS[array[0]].length - 1;
                return true;
            }
            if (n3 >= GLContext.ES_VERSIONS[n2].length) {
                array2[0] = GLContext.ES_VERSIONS[n2].length - 1;
                return true;
            }
        }
        else {
            if (n2 >= GLContext.GL_VERSIONS.length) {
                array[0] = GLContext.GL_VERSIONS.length - 1;
                array2[0] = GLContext.GL_VERSIONS[array[0]].length - 1;
                return true;
            }
            if (n3 >= GLContext.GL_VERSIONS[n2].length) {
                array2[0] = GLContext.GL_VERSIONS[n2].length - 1;
                return true;
            }
        }
        return false;
    }
    
    public static final boolean decrementGLVersion(final int n, final int[] array, final int[] array2) {
        if (!clipGLVersion(n, array, array2)) {
            int n2 = array[0];
            int n3 = array2[0] - 1;
            if (n3 < 0) {
                if (0x0 != (0x8 & n)) {
                    if (n2 >= 3) {
                        --n2;
                    }
                    else {
                        n2 = 0;
                    }
                    n3 = GLContext.ES_VERSIONS[n2].length - 1;
                }
                else {
                    --n2;
                    n3 = GLContext.GL_VERSIONS[n2].length - 1;
                }
            }
            if (!isValidGLVersion(n, n2, n3)) {
                return false;
            }
            array[0] = n2;
            array2[0] = n3;
        }
        return true;
    }
    
    protected static int composeBits(final int n, final int n2, final int n3) {
        return (n & 0xFF) << 24 | (n2 & 0xFF) << 16 | (n3 & 0xFFFF);
    }
    
    protected static VersionNumber decomposeBits(final int n, final int[] array) {
        final int n2 = (n & 0xFF000000) >>> 24;
        final int n3 = (n & 0xFF0000) >>> 16;
        array[0] = (n & 0xFFFF);
        return new VersionNumber(n2, n3, 0);
    }
    
    private static void validateProfileBits(final int n, final String s) {
        int n2 = 0;
        if (0x0 != (0x2 & n)) {
            ++n2;
        }
        if (0x0 != (0x4 & n)) {
            ++n2;
        }
        if (0x0 != (0x8 & n)) {
            ++n2;
        }
        if (n2 == 0) {
            throw new GLException("Internal Error: " + s + ": 1 != num-profiles: " + n2);
        }
    }
    
    protected static void shutdown() {
        GLContext.deviceVersionAvailable.clear();
        GLContext.deviceVersionsAvailableSet.clear();
        GLContextImpl.shutdownImpl();
    }
    
    protected static boolean getAvailableGLVersionsSet(final AbstractGraphicsDevice abstractGraphicsDevice) {
        synchronized (GLContext.deviceVersionsAvailableSet) {
            return GLContext.deviceVersionsAvailableSet.containsKey(abstractGraphicsDevice.getUniqueID());
        }
    }
    
    protected static void setAvailableGLVersionsSet(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b) {
        synchronized (GLContext.deviceVersionsAvailableSet) {
            final String uniqueID = abstractGraphicsDevice.getUniqueID();
            if (b) {
                GLContext.deviceVersionsAvailableSet.put(uniqueID, uniqueID);
            }
            else {
                GLContext.deviceVersionsAvailableSet.remove(uniqueID);
            }
            if (GLContext.DEBUG) {
                System.err.println(getThreadName() + ": createContextARB-MapGLVersions SET " + uniqueID);
                System.err.println(dumpAvailableGLVersions(null).toString());
            }
        }
    }
    
    protected static String getDeviceVersionAvailableKey(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2) {
        return (abstractGraphicsDevice.getUniqueID() + "-" + toHexString(composeBits(n, n2, 0))).intern();
    }
    
    protected static Integer mapAvailableGLVersion(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final int n3, final int n4, int n5) {
        validateProfileBits(n2, "profile");
        validateProfileBits(n5, "resCtp");
        if (GLContext.FORCE_NO_FBO_SUPPORT) {
            n5 &= 0xFFFFBFFF;
        }
        if (GLContext.DEBUG) {
            System.err.println(getThreadName() + ": createContextARB-MapGLVersions MAP " + abstractGraphicsDevice + ": " + n + " (" + getGLProfile(new StringBuilder(), n2).toString() + ") -> " + getGLVersion(n3, n4, n5, null));
        }
        final String deviceVersionAvailableKey = getDeviceVersionAvailableKey(abstractGraphicsDevice, n, n2);
        final Integer value = composeBits(n3, n4, n5);
        synchronized (GLContext.deviceVersionAvailable) {
            return GLContext.deviceVersionAvailable.put(deviceVersionAvailableKey, value);
        }
    }
    
    protected static StringBuilder dumpAvailableGLVersions(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        synchronized (GLContext.deviceVersionAvailable) {
            final Set<String> keySet = GLContext.deviceVersionAvailable.keySet();
            int n = 0;
            final Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                if (n != 0) {
                    sb.append(Platform.getNewline());
                }
                final String s = iterator.next();
                sb.append("MapGLVersions ").append(s).append(": ");
                final Integer n2 = GLContext.deviceVersionAvailable.get(s);
                if (null != n2) {
                    final int[] array = { 0 };
                    getGLVersion(sb, decomposeBits(n2, array), array[0], null);
                }
                else {
                    sb.append("n/a");
                }
                n = 1;
            }
        }
        return sb;
    }
    
    protected static Integer getAvailableGLVersion(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2) {
        final String deviceVersionAvailableKey = getDeviceVersionAvailableKey(abstractGraphicsDevice, n, n2);
        final Integer n3;
        synchronized (GLContext.deviceVersionAvailable) {
            n3 = GLContext.deviceVersionAvailable.get(deviceVersionAvailableKey);
        }
        return n3;
    }
    
    protected static boolean getAvailableGLVersion(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final int[] array, final int[] array2, final int[] array3) {
        final Integer availableGLVersion = getAvailableGLVersion(abstractGraphicsDevice, n, n2);
        if (null == availableGLVersion) {
            return false;
        }
        final int intValue = availableGLVersion;
        if (null != array) {
            array[0] = (intValue & 0xFF000000) >>> 24;
        }
        if (null != array2) {
            array2[0] = (intValue & 0xFF0000) >>> 16;
        }
        if (null != array3) {
            array3[0] = (intValue & 0xFFFF);
        }
        return true;
    }
    
    protected static String getGLProfile(final int n, final int n2, final int n3) throws GLException {
        if (0x0 == (0x2 & n3)) {
            if (0x0 != (0x4 & n3)) {
                if (n >= 4) {
                    return "GL4";
                }
                if (n == 3 && n2 >= 1) {
                    return "GL3";
                }
            }
            else if (0x0 != (0x8 & n3)) {
                if (n == 3) {
                    return "GLES3";
                }
                if (n == 2) {
                    return "GLES2";
                }
                if (n == 1) {
                    return "GLES1";
                }
            }
            throw new GLException("Unhandled OpenGL version/profile: " + getGLVersion(n, n2, n3, null));
        }
        if (n >= 4) {
            return "GL4bc";
        }
        if (n == 3 && n2 >= 1) {
            return "GL3bc";
        }
        return "GL2";
    }
    
    protected static final void getRequestMajorAndCompat(final GLProfile glProfile, final int[] array) {
        final GLProfile impl = glProfile.getImpl();
        if (impl.isGL4()) {
            array[0] = 4;
        }
        else if (impl.isGL3() || impl.isGLES3()) {
            array[0] = 3;
        }
        else if (impl.isGLES1()) {
            array[0] = 1;
        }
        else {
            array[0] = 2;
        }
        if (impl.isGLES()) {
            array[1] = 8;
        }
        else if (impl.isGL2()) {
            array[1] = 2;
        }
        else {
            array[1] = 4;
        }
    }
    
    protected static final int getAvailableContextProperties(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile) {
        final int[] array = { 0, 0 };
        getRequestMajorAndCompat(glProfile, array);
        final int[] array2 = { 0 };
        final int[] array3 = { 0 };
        final int[] array4 = { 0 };
        if (getAvailableGLVersion(abstractGraphicsDevice, array[0], array[1], array2, array3, array4)) {
            return array4[0];
        }
        return 0;
    }
    
    protected static GLProfile getAvailableGLProfile(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2) throws GLException {
        final String availableGLProfileName = getAvailableGLProfileName(abstractGraphicsDevice, n, n2);
        return (null != availableGLProfileName) ? GLProfile.get(abstractGraphicsDevice, availableGLProfileName) : null;
    }
    
    static String getAvailableGLProfileName(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2) throws GLException {
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        final int[] array3 = { 0 };
        if (getAvailableGLVersion(abstractGraphicsDevice, n, n2, array, array2, array3)) {
            return getGLProfile(array[0], array2[0], array3[0]);
        }
        return null;
    }
    
    protected static String getAvailableGLVersionAsString(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2) {
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        final int[] array3 = { 0 };
        if (getAvailableGLVersion(abstractGraphicsDevice, n, n2, array, array2, array3)) {
            return getGLVersion(array[0], array2[0], array3[0], null);
        }
        return null;
    }
    
    public static final boolean isFBOAvailable(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile) {
        return 0x0 != (0x4000 & getAvailableContextProperties(abstractGraphicsDevice, glProfile));
    }
    
    public static final int isHardwareRasterizer(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile) {
        final int availableContextProperties = getAvailableContextProperties(abstractGraphicsDevice, glProfile);
        int n;
        if (availableContextProperties == 0) {
            n = -1;
        }
        else if (0x0 == (0x40 & availableContextProperties)) {
            n = 1;
        }
        else {
            n = 0;
        }
        return n;
    }
    
    protected static boolean isGLVersionAvailable(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final boolean[] array) {
        final Integer availableGLVersion = getAvailableGLVersion(abstractGraphicsDevice, n, n2);
        if (null == availableGLVersion) {
            return false;
        }
        array[0] = (0x0 == (availableGLVersion & 0x40));
        return true;
    }
    
    public static boolean isGLES1Available(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array) {
        return isGLVersionAvailable(abstractGraphicsDevice, 1, 8, array);
    }
    
    public static boolean isGLES2Available(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array) {
        return isGLVersionAvailable(abstractGraphicsDevice, 2, 8, array);
    }
    
    public static boolean isGLES3Available(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array) {
        return isGLVersionAvailable(abstractGraphicsDevice, 3, 8, array);
    }
    
    private static final int getGL3ctp(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        final int[] array3 = { 0 };
        boolean b = getAvailableGLVersion(abstractGraphicsDevice, 3, 8, array, array2, array3);
        if (!b) {
            b = getAvailableGLVersion(abstractGraphicsDevice, 3, 4, array, array2, array3);
        }
        if (!b) {
            getAvailableGLVersion(abstractGraphicsDevice, 3, 2, array, array2, array3);
        }
        return array3[0];
    }
    
    public static final boolean isGLES3CompatibleAvailable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return 0x0 != (getGL3ctp(abstractGraphicsDevice) & 0x800);
    }
    
    public static final boolean isGLES31CompatibleAvailable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return 0x0 != (getGL3ctp(abstractGraphicsDevice) & 0x1000);
    }
    
    public static final boolean isGLES32CompatibleAvailable(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return 0x0 != (getGL3ctp(abstractGraphicsDevice) & 0x2000);
    }
    
    public static boolean isGL4bcAvailable(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array) {
        return isGLVersionAvailable(abstractGraphicsDevice, 4, 2, array);
    }
    
    public static boolean isGL4Available(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array) {
        return isGLVersionAvailable(abstractGraphicsDevice, 4, 4, array);
    }
    
    public static boolean isGL3bcAvailable(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array) {
        return isGLVersionAvailable(abstractGraphicsDevice, 3, 2, array);
    }
    
    public static boolean isGL3Available(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array) {
        return isGLVersionAvailable(abstractGraphicsDevice, 3, 4, array);
    }
    
    public static boolean isGL2Available(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean[] array) {
        return isGLVersionAvailable(abstractGraphicsDevice, 2, 2, array);
    }
    
    protected static StringBuilder getGLProfile(final StringBuilder sb, final int n) {
        appendString(sb, "compat[", appendString(sb, "debug", appendString(sb, "arb", appendString(sb, "forward", appendString(sb, "Core profile", appendString(sb, "Compat profile", appendString(sb, "ES profile", false, 0x0 != (0x8 & n)), 0x0 != (0x2 & n)), 0x0 != (0x4 & n)), 0x0 != (0x10 & n)), 0x0 != (0x1 & n)), 0x0 != (0x20 & n)), true);
        appendString(sb, "FP32", appendString(sb, "ES32", appendString(sb, "ES31", appendString(sb, "ES3", appendString(sb, "ES2", false, 0x0 != (0x400 & n)), 0x0 != (0x800 & n)), 0x0 != (0x1000 & n)), 0x0 != (0x2000 & n)), 0x0 != (0x8000 & n));
        final boolean appendString = appendString(sb, "FBO", appendString(sb, "]", false, true), 0x0 != (0x4000 & n));
        if (0x0 != (0x40 & n)) {
            appendString(sb, "software", appendString, true);
        }
        else {
            appendString(sb, "hardware", appendString, true);
        }
        return sb;
    }
    
    protected static StringBuilder getGLVersion(final StringBuilder sb, final VersionNumber versionNumber, final int n, final String s) {
        return getGLVersion(sb, versionNumber.getMajor(), versionNumber.getMinor(), n, s);
    }
    
    protected static StringBuilder getGLVersion(final StringBuilder sb, final int n, final int n2, final int n3, final String s) {
        sb.append(n);
        sb.append(".");
        sb.append(n2);
        sb.append(" (");
        getGLProfile(sb, n3);
        sb.append(")");
        if (null != s) {
            sb.append(" - ");
            sb.append(s);
        }
        return sb;
    }
    
    protected static String getGLVersion(final int n, final int n2, final int n3, final String s) {
        return getGLVersion(new StringBuilder(), n, n2, n3, s).toString();
    }
    
    protected static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    protected static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    private static boolean appendString(final StringBuilder sb, final String s, boolean b, final boolean b2) {
        if (b2) {
            if (b) {
                sb.append(", ");
            }
            sb.append(s);
            b = true;
        }
        return b;
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    static {
        DEBUG = Debug.debug("GLContext");
        TRACE_SWITCH = PropertyAccess.isPropertyDefined("jogl.debug.GLContext.TraceSwitch", true);
        DEBUG_TRACE_SWITCH = (GLContext.DEBUG || GLContext.TRACE_SWITCH);
        PROFILE_ALIASING = !PropertyAccess.isPropertyDefined("jogl.debug.GLContext.NoProfileAliasing", true);
        FORCE_NO_FBO_SUPPORT = PropertyAccess.isPropertyDefined("jogl.fbo.force.none", true);
        FORCE_MIN_FBO_SUPPORT = PropertyAccess.isPropertyDefined("jogl.fbo.force.min", true);
        FORCE_NO_COLOR_RENDERBUFFER = PropertyAccess.isPropertyDefined("jogl.fbo.force.nocolorrenderbuffer", true);
        DEBUG_GL = PropertyAccess.isPropertyDefined("jogl.debug.DebugGL", true);
        TRACE_GL = PropertyAccess.isPropertyDefined("jogl.debug.TraceGL", true);
        Version1_0 = new VersionNumber(1, 0, 0);
        Version1_10 = new VersionNumber(1, 10, 0);
        Version1_20 = new VersionNumber(1, 20, 0);
        Version1_30 = new VersionNumber(1, 30, 0);
        Version1_40 = new VersionNumber(1, 40, 0);
        Version1_50 = new VersionNumber(1, 50, 0);
        Version1_1 = new VersionNumber(1, 1, 0);
        Version1_2 = new VersionNumber(1, 2, 0);
        Version1_4 = new VersionNumber(1, 4, 0);
        Version1_5 = new VersionNumber(1, 5, 0);
        Version3_0 = new VersionNumber(3, 0, 0);
        Version3_1 = new VersionNumber(3, 1, 0);
        Version3_2 = new VersionNumber(3, 2, 0);
        Version4_3 = new VersionNumber(4, 3, 0);
        Version8_0 = new VersionNumber(8, 0, 0);
        currentContext = new ThreadLocal<GLContext>();
        GL_VERSIONS = new int[][] { { -1 }, { 0, 1, 2, 3, 4, 5 }, { 0, 1 }, { 0, 1, 2, 3 }, { 0, 1, 2, 3, 4, 5 } };
        ES_VERSIONS = new int[][] { { -1 }, { 0, 1 }, { 0 }, { 0, 1, 2 } };
        deviceVersionAvailable = new IdentityHashMap<String, Integer>();
        deviceVersionsAvailableSet = new IdentityHashMap<String, String>();
    }
}
