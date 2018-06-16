// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.nativewindow.NativeWindowFactory;
import jogamp.nativewindow.macosx.OSXUtil;
import jogamp.opengl.GLFBODrawableImpl;
import com.jogamp.nativewindow.OffscreenLayerSurface;
import com.jogamp.common.util.VersionNumber;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.opengl.GL;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import jogamp.opengl.GLDynamicLookupHelper;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.common.os.Platform;
import java.util.Map;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import jogamp.opengl.GLContextImpl;

public class MacOSXCGLContext extends GLContextImpl
{
    static final boolean isTigerOrLater;
    static final boolean isLionOrLater;
    static final boolean isMavericksOrLater;
    private static final String shaderBasename = "texture01_xxx";
    private boolean haveSetOpenGLMode;
    private MacOSXCGLDrawable.GLBackendType openGLMode;
    protected GLBackendImpl impl;
    private CGLExt _cglExt;
    private CGLExtProcAddressTable cglExtProcAddressTable;
    private long updateHandle;
    private int lastWidth;
    private int lastHeight;
    
    static boolean isGLProfileSupported(final int n, final int n2, final int n3) {
        if (0x0 != (0x8 & n)) {
            return false;
        }
        final boolean b = 0x0 != (0x2 & n);
        final boolean b2 = 0x0 != (0x4 & n);
        if (3 < n2 || (3 == n2 && 1 <= n3)) {
            return !b && b2 && MacOSXCGLContext.isLionOrLater && (3 >= n2 || MacOSXCGLContext.isMavericksOrLater);
        }
        return n2 < 3;
    }
    
    static int GLProfile2CGLOGLProfileValue(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final int n2, final int n3) {
        if (!isGLProfileSupported(n, n2, n3)) {
            throw new GLException("OpenGL profile not supported: " + GLContext.getGLVersion(n2, n3, n, "@GLProfile2CGLOGLProfileVersion"));
        }
        final boolean b = 0x0 != (0x4 & n);
        if (n2 == 4 && b) {
            if (GLRendererQuirks.existStickyDeviceQuirk(abstractGraphicsDevice, 13)) {
                return 12800;
            }
            return 16640;
        }
        else {
            if (n2 == 3 && n3 >= 1 && b) {
                return 12800;
            }
            return 4096;
        }
    }
    
    private static ShaderProgram createCALayerShader(final GL3ES3 gl3ES3) {
        final ShaderProgram shaderProgram = new ShaderProgram();
        final ShaderCode create = ShaderCode.create(gl3ES3, 35633, MacOSXCGLContext.class, "../../shader", "../../shader/bin", "texture01_xxx", true);
        final ShaderCode create2 = ShaderCode.create(gl3ES3, 35632, MacOSXCGLContext.class, "../../shader", "../../shader/bin", "texture01_xxx", true);
        create.defaultShaderCustomization(gl3ES3, true, true);
        create2.defaultShaderCustomization(gl3ES3, true, true);
        shaderProgram.add(create);
        shaderProgram.add(create2);
        if (!shaderProgram.link(gl3ES3, System.err)) {
            throw new GLException("Couldn't link program: " + shaderProgram);
        }
        shaderProgram.useProgram(gl3ES3, true);
        final PMVMatrix pmvMatrix = new PMVMatrix();
        pmvMatrix.glMatrixMode(5889);
        pmvMatrix.glLoadIdentity();
        pmvMatrix.glMatrixMode(5888);
        pmvMatrix.glLoadIdentity();
        final GLUniformData glUniformData = new GLUniformData("mgl_PMVMatrix", 4, 4, pmvMatrix.glGetPMvMatrixf());
        glUniformData.setLocation(gl3ES3, shaderProgram.program());
        gl3ES3.glUniform(glUniformData);
        shaderProgram.useProgram(gl3ES3, false);
        return shaderProgram;
    }
    
    protected MacOSXCGLContext(final GLDrawableImpl glDrawableImpl, final GLContext glContext) {
        super(glDrawableImpl, glContext);
        this.haveSetOpenGLMode = false;
        this.openGLMode = MacOSXCGLDrawable.GLBackendType.NSOPENGL;
        this.updateHandle = 0L;
        this.initOpenGLImpl(this.getOpenGLMode());
    }
    
    @Override
    protected void resetStates(final boolean b) {
        this.cglExtProcAddressTable = null;
        super.resetStates(b);
    }
    
    @Override
    public Object getPlatformGLExtensions() {
        return this.getCGLExt();
    }
    
    protected boolean isNSContext() {
        return (null != this.impl) ? this.impl.isNSContext() : (this.openGLMode == MacOSXCGLDrawable.GLBackendType.NSOPENGL);
    }
    
    public CGLExt getCGLExt() {
        if (this._cglExt == null) {
            this._cglExt = new CGLExtImpl(this);
        }
        return this._cglExt;
    }
    
    @Override
    public final ProcAddressTable getPlatformExtProcAddressTable() {
        return this.getCGLExtProcAddressTable();
    }
    
    public final CGLExtProcAddressTable getCGLExtProcAddressTable() {
        return this.cglExtProcAddressTable;
    }
    
    @Override
    protected Map<String, String> getFunctionNameMap() {
        return null;
    }
    
    @Override
    protected Map<String, String> getExtensionNameMap() {
        return null;
    }
    
    @Override
    protected long createContextARBImpl(final long n, final boolean b, final int n2, final int n3, final int n4) {
        if (!isGLProfileSupported(n2, n3, n4)) {
            if (MacOSXCGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARBImpl: Not supported " + GLContext.getGLVersion(n3, n4, n2, "@creation on OSX " + Platform.getOSVersionNumber()));
            }
            return 0L;
        }
        long create = this.impl.create(n, n2, n3, n4);
        if (0L != create) {
            if (!this.impl.makeCurrent(create)) {
                if (MacOSXCGLContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": createContextARB couldn't make current " + GLContext.getGLVersion(n3, n4, n2, "@creation"));
                }
                this.impl.release(create);
                this.impl.destroy(create);
                create = 0L;
            }
            else if (MacOSXCGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARBImpl: OK " + GLContext.getGLVersion(n3, n4, n2, "@creation") + ", share " + n + ", direct " + b + " on OSX " + Platform.getOSVersionNumber());
            }
        }
        else if (MacOSXCGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": createContextARBImpl: NO " + GLContext.getGLVersion(n3, n4, n2, "@creation on OSX " + Platform.getOSVersionNumber()));
        }
        return create;
    }
    
    @Override
    protected void destroyContextARBImpl(final long n) {
        this.impl.release(n);
        this.impl.destroy(n);
    }
    
    @Override
    public final boolean isGLReadDrawableAvailable() {
        return false;
    }
    
    @Override
    protected boolean createImpl(final long n) throws GLException {
        final MacOSXCGLGraphicsConfiguration macOSXCGLGraphicsConfiguration = (MacOSXCGLGraphicsConfiguration)this.drawable.getNativeSurface().getGraphicsConfiguration();
        final AbstractGraphicsDevice device = macOSXCGLGraphicsConfiguration.getScreen().getDevice();
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)macOSXCGLGraphicsConfiguration.getChosenCapabilities();
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final boolean createContextARBAvail = this.isCreateContextARBAvail(device);
        if (MacOSXCGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": MacOSXCGLContext.createImpl: START " + glCapabilitiesImmutable + ", share " + GLContext.toHexString(n));
            System.err.println(GLContext.getThreadName() + ": Use ARB[avail[" + this.getCreateContextARBAvailStr(device) + "] -> " + createContextARBAvail + "]]");
        }
        if (glProfile.isGLES() || (glProfile.isGL3() && !MacOSXCGLContext.isLionOrLater) || (glProfile.isGL4() && !MacOSXCGLContext.isMavericksOrLater)) {
            throw new GLException("OpenGL profile not supported on MacOSX " + Platform.getOSVersionNumber() + ": " + glProfile);
        }
        if (0L != n && MacOSXCGLDrawable.GLBackendType.NSOPENGL != this.getOpenGLMode()) {
            throw new GLException("Context sharing only supported in mode " + MacOSXCGLDrawable.GLBackendType.NSOPENGL + ": " + this);
        }
        this.contextHandle = this.createContextARB(n, true);
        return 0L != this.contextHandle;
    }
    
    @Override
    protected void makeCurrentImpl() throws GLException {
        if (!this.impl.makeCurrent(this.contextHandle)) {
            throw new GLException("Error making Context current: " + this);
        }
        this.drawableUpdatedNotify();
    }
    
    @Override
    protected void releaseImpl() throws GLException {
        if (!this.impl.release(this.contextHandle)) {
            throw new GLException("Error releasing OpenGL Context: " + this);
        }
    }
    
    @Override
    protected void destroyImpl() throws GLException {
        this.releaseUpdateHandle();
        if (!this.impl.destroy(this.contextHandle)) {
            throw new GLException("Error destroying OpenGL Context: " + this);
        }
    }
    
    private final long getUpdateHandle() {
        if (0L == this.updateHandle) {
            this.lastWidth = -1;
            this.lastHeight = -1;
            if (this.isCreated() && this.drawable.getChosenGLCapabilities().isOnscreen() && this.isNSContext()) {
                final NativeSurface nativeSurface = this.drawable.getNativeSurface();
                if (!(nativeSurface instanceof ProxySurface) || !((ProxySurface)nativeSurface).containsUpstreamOptionBits(256)) {
                    this.updateHandle = CGL.updateContextRegister(this.contextHandle, this.drawable.getHandle());
                    if (0L == this.updateHandle) {
                        throw new InternalError("XXX2");
                    }
                }
            }
        }
        return this.updateHandle;
    }
    
    private final void releaseUpdateHandle() {
        if (0L != this.updateHandle) {
            CGL.updateContextUnregister(this.updateHandle);
            this.updateHandle = 0L;
        }
    }
    
    @Override
    protected void drawableUpdatedNotify() throws GLException {
        if (this.drawable.getChosenGLCapabilities().isOnscreen()) {
            final long updateHandle = this.getUpdateHandle();
            final int surfaceWidth = this.drawable.getSurfaceWidth();
            final int surfaceHeight = this.drawable.getSurfaceHeight();
            if ((0L != updateHandle && CGL.updateContextNeedsUpdate(updateHandle)) || surfaceWidth != this.lastWidth || surfaceHeight != this.lastHeight) {
                this.lastWidth = surfaceWidth;
                this.lastHeight = surfaceHeight;
                if (this.contextHandle == 0L) {
                    throw new GLException("Context not created");
                }
                CGL.updateContext(this.contextHandle);
            }
        }
    }
    
    @Override
    protected void associateDrawable(final boolean b) {
        if (b) {
            super.associateDrawable(true);
            this.impl.associateDrawable(true);
            this.getUpdateHandle();
        }
        else {
            this.releaseUpdateHandle();
            this.impl.associateDrawable(false);
            super.associateDrawable(false);
        }
    }
    
    void detachPBuffer() {
        this.impl.detachPBuffer();
    }
    
    @Override
    protected void copyImpl(final GLContext glContext, final int n) throws GLException {
        if (this.isNSContext() != ((MacOSXCGLContext)glContext).isNSContext()) {
            throw new GLException("Source/Destination OpenGL Context type mismatch: source " + glContext + ", dest: " + this);
        }
        if (!this.impl.copyImpl(glContext.getHandle(), n)) {
            throw new GLException("Error copying OpenGL Context: source " + glContext + ", dest: " + this);
        }
    }
    
    protected void swapBuffers() {
        if (!this.impl.swapBuffers()) {
            throw new GLException("Error swapping buffers: " + this);
        }
    }
    
    @Override
    protected final Integer setSwapIntervalImpl2(final int n) {
        if (!this.impl.isUsingCALayer() && !this.drawable.getChosenGLCapabilities().isOnscreen()) {
            return null;
        }
        int abs;
        if (0 > n) {
            abs = Math.abs(n);
        }
        else {
            abs = n;
        }
        if (this.impl.setSwapInterval(abs)) {
            return abs;
        }
        return null;
    }
    
    @Override
    protected final void updateGLXProcAddressTable(final String s, final GLDynamicLookupHelper glDynamicLookupHelper) {
        if (null == glDynamicLookupHelper) {
            throw new GLException("No GLDynamicLookupHelper for " + this);
        }
        final String string = "MacOSX-" + this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice().getUniqueID();
        if (MacOSXCGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": Initializing CGL extension address table: " + string);
        }
        ProcAddressTable procAddressTable = null;
        synchronized (MacOSXCGLContext.mappedContextTypeObjectLock) {
            procAddressTable = MacOSXCGLContext.mappedGLXProcAddress.get(string);
        }
        if (null != procAddressTable) {
            this.cglExtProcAddressTable = (CGLExtProcAddressTable)procAddressTable;
            if (MacOSXCGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext CGL ProcAddressTable reusing key(" + string + ") -> " + GLContext.toHexString(procAddressTable.hashCode()));
            }
        }
        else {
            this.cglExtProcAddressTable = new CGLExtProcAddressTable(new GLProcAddressResolver());
            this.resetProcAddressTable(this.getCGLExtProcAddressTable(), glDynamicLookupHelper);
            synchronized (MacOSXCGLContext.mappedContextTypeObjectLock) {
                MacOSXCGLContext.mappedGLXProcAddress.put(string, this.getCGLExtProcAddressTable());
                if (MacOSXCGLContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext CGL ProcAddressTable mapping key(" + string + ") -> " + GLContext.toHexString(this.getCGLExtProcAddressTable().hashCode()));
                }
            }
        }
    }
    
    @Override
    protected final StringBuilder getPlatformExtensionsStringImpl() {
        return new StringBuilder();
    }
    
    public void setOpenGLMode(final MacOSXCGLDrawable.GLBackendType glBackendType) {
        if (glBackendType == this.openGLMode) {
            return;
        }
        if (this.haveSetOpenGLMode) {
            throw new GLException("Can't switch between using NSOpenGLPixelBuffer and CGLPBufferObj more than once");
        }
        this.destroyImpl();
        ((MacOSXCGLDrawable)this.drawable).setOpenGLMode(glBackendType);
        if (MacOSXCGLContext.DEBUG) {
            System.err.println("MacOSXCGLContext: Switching context mode " + this.openGLMode + " -> " + glBackendType);
        }
        this.initOpenGLImpl(glBackendType);
        this.openGLMode = glBackendType;
        this.haveSetOpenGLMode = true;
    }
    
    public final MacOSXCGLDrawable.GLBackendType getOpenGLMode() {
        return this.openGLMode;
    }
    
    protected void initOpenGLImpl(final MacOSXCGLDrawable.GLBackendType glBackendType) {
        switch (glBackendType) {
            case NSOPENGL: {
                this.impl = new NSOpenGLImpl();
                break;
            }
            case CGL: {
                this.impl = new CGLImpl();
                break;
            }
            default: {
                throw new InternalError("Illegal implementation mode " + glBackendType);
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        super.append(sb);
        sb.append(", mode ");
        sb.append(this.openGLMode);
        sb.append("] ");
        return sb.toString();
    }
    
    static {
        final VersionNumber osVersionNumber = Platform.getOSVersionNumber();
        isTigerOrLater = (osVersionNumber.compareTo(PlatformPropsImpl.OSXVersion.Tiger) >= 0);
        isLionOrLater = (osVersionNumber.compareTo(PlatformPropsImpl.OSXVersion.Lion) >= 0);
        isMavericksOrLater = (osVersionNumber.compareTo(PlatformPropsImpl.OSXVersion.Mavericks) >= 0);
    }
    
    class NSOpenGLImpl implements GLBackendImpl
    {
        private OffscreenLayerSurface backingLayerHost;
        private long pixelFormat;
        private int screenVSyncTimeout;
        private volatile int vsyncTimeout;
        private int lastWidth;
        private int lastHeight;
        private boolean needsSetContextPBuffer;
        private ShaderProgram gl3ShaderProgram;
        AttachGLLayerCmd attachGLLayerCmd;
        private int skipSync;
        
        NSOpenGLImpl() {
            this.backingLayerHost = null;
            this.pixelFormat = 0L;
            this.screenVSyncTimeout = 16666;
            this.vsyncTimeout = 17666;
            this.lastWidth = 0;
            this.lastHeight = 0;
            this.needsSetContextPBuffer = false;
            this.gl3ShaderProgram = null;
            this.attachGLLayerCmd = null;
            this.skipSync = 0;
        }
        
        @Override
        public boolean isNSContext() {
            return true;
        }
        
        @Override
        public boolean isUsingCALayer() {
            return null != this.backingLayerHost;
        }
        
        private long getNSViewHandle(final boolean[] array, final boolean[] array2, final boolean[] array3) {
            long getNSView;
            if (MacOSXCGLContext.this.drawable instanceof GLFBODrawableImpl) {
                getNSView = 0L;
                array[0] = false;
                array2[0] = true;
                array3[0] = false;
                if (GLContext.DEBUG) {
                    System.err.println("NS viewHandle.1: GLFBODrawableImpl drawable: isFBO " + array2[0] + ", isPBuffer " + array[0] + ", isSurfaceless " + array3[0] + ", " + MacOSXCGLContext.this.drawable.getClass().getName() + ",\n\t" + MacOSXCGLContext.this.drawable);
                }
            }
            else {
                final long handle = MacOSXCGLContext.this.drawable.getHandle();
                final boolean nsView = OSXUtil.isNSView(handle);
                final boolean nsWindow = OSXUtil.isNSWindow(handle);
                array[0] = CGL.isNSOpenGLPixelBuffer(handle);
                array3[0] = (array2[0] = false);
                if (nsView) {
                    getNSView = handle;
                }
                else if (nsWindow) {
                    getNSView = OSXUtil.GetNSView(handle);
                }
                else if (array[0]) {
                    getNSView = 0L;
                }
                else {
                    if (!MacOSXCGLContext.this.isSurfaceless()) {
                        throw new GLException("Drawable's handle neither NSView, NSWindow nor PBuffer: drawableHandle " + GLContext.toHexString(handle) + ", isNSView " + nsView + ", isNSWindow " + nsWindow + ", isFBO " + array2[0] + ", isPBuffer " + array[0] + ", " + MacOSXCGLContext.this.drawable.getClass().getName() + ",\n\t" + MacOSXCGLContext.this.drawable);
                    }
                    array3[0] = true;
                    getNSView = 0L;
                }
                if (GLContext.DEBUG) {
                    System.err.println("NS viewHandle.2: drawableHandle " + GLContext.toHexString(handle) + " -> nsViewHandle " + GLContext.toHexString(getNSView) + ": isNSView " + nsView + ", isNSWindow " + nsWindow + ", isFBO " + array2[0] + ", isPBuffer " + array[0] + ", isSurfaceless " + array3[0] + ", " + MacOSXCGLContext.this.drawable.getClass().getName() + ",\n\t" + MacOSXCGLContext.this.drawable);
                }
            }
            this.needsSetContextPBuffer = array[0];
            return getNSView;
        }
        
        @Override
        public long create(final long n, final int n2, final int n3, final int n4) {
            final NativeSurface nativeSurface = MacOSXCGLContext.this.drawable.getNativeSurface();
            final MacOSXCGLGraphicsConfiguration macOSXCGLGraphicsConfiguration = (MacOSXCGLGraphicsConfiguration)nativeSurface.getGraphicsConfiguration();
            final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)macOSXCGLGraphicsConfiguration.getChosenCapabilities();
            final boolean[] array = { false };
            final boolean[] array2 = { false };
            final boolean[] array3 = { false };
            final long nsViewHandle = this.getNSViewHandle(array, array2, array3);
            final boolean pBuffer = array[0];
            final boolean b = array2[0];
            final boolean b2 = array3[0];
            final OffscreenLayerSurface offscreenLayerSurface = NativeWindowFactory.getOffscreenLayerSurface(nativeSurface, true);
            boolean containsUpstreamOptionBits = null != offscreenLayerSurface;
            if (!containsUpstreamOptionBits && nativeSurface instanceof ProxySurface) {
                containsUpstreamOptionBits = ((ProxySurface)nativeSurface).containsUpstreamOptionBits(256);
            }
            GLCapabilitiesImmutable glCapabilitiesImmutable2;
            if (b) {
                glCapabilitiesImmutable2 = new GLCapabilities(glCapabilitiesImmutable.getGLProfile());
            }
            else {
                glCapabilitiesImmutable2 = glCapabilitiesImmutable;
            }
            this.pixelFormat = MacOSXCGLGraphicsConfiguration.GLCapabilities2NSPixelFormat(macOSXCGLGraphicsConfiguration.getScreen().getDevice(), glCapabilitiesImmutable2, n2, n3, n4);
            if (this.pixelFormat == 0L) {
                if (GLContext.DEBUG) {
                    System.err.println("Unable to allocate pixel format with requested GLCapabilities: " + glCapabilitiesImmutable);
                }
                return 0L;
            }
            GLCapabilitiesImmutable fixOpaqueGLCapabilities;
            if (b) {
                fixOpaqueGLCapabilities = glCapabilitiesImmutable;
            }
            else {
                final GLCapabilities nsPixelFormat2GLCapabilities = MacOSXCGLGraphicsConfiguration.NSPixelFormat2GLCapabilities(glCapabilitiesImmutable.getGLProfile(), this.pixelFormat);
                if (!nsPixelFormat2GLCapabilities.isPBuffer() && pBuffer) {
                    throw new InternalError("handle is PBuffer, fixedCaps not: " + MacOSXCGLContext.this.drawable);
                }
                nsPixelFormat2GLCapabilities.setPBuffer(pBuffer);
                nsPixelFormat2GLCapabilities.setBitmap(false);
                nsPixelFormat2GLCapabilities.setOnscreen(!b && !pBuffer && !b2);
                fixOpaqueGLCapabilities = GLGraphicsConfigurationUtil.fixOpaqueGLCapabilities(nsPixelFormat2GLCapabilities, glCapabilitiesImmutable.isBackgroundOpaque());
            }
            final int getScreenRefreshRate = OSXUtil.GetScreenRefreshRate(MacOSXCGLContext.this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getIndex());
            if (0 < getScreenRefreshRate) {
                this.screenVSyncTimeout = 1000000 / getScreenRefreshRate;
            }
            if (GLContext.DEBUG) {
                System.err.println("NS create OSX>=lion " + MacOSXCGLContext.isLionOrLater + ", OSX>=mavericks " + MacOSXCGLContext.isMavericksOrLater);
                System.err.println("NS create incompleteView: " + containsUpstreamOptionBits);
                System.err.println("NS create backingLayerHost: " + offscreenLayerSurface);
                System.err.println("NS create share: " + n);
                System.err.println("NS create drawable type: " + MacOSXCGLContext.this.drawable.getClass().getName());
                System.err.println("NS create drawable handle: isPBuffer " + pBuffer + ", isFBO " + b + ", isSurfaceless " + b2);
                System.err.println("NS create pixelFormat: " + GLContext.toHexString(this.pixelFormat));
                System.err.println("NS create chosenCaps: " + glCapabilitiesImmutable);
                System.err.println("NS create fixedCaps: " + fixOpaqueGLCapabilities);
                System.err.println("NS create drawable native-handle: " + GLContext.toHexString(MacOSXCGLContext.this.drawable.getHandle()));
                System.err.println("NS create drawable NSView-handle: " + GLContext.toHexString(nsViewHandle));
                System.err.println("NS create screen refresh-rate: " + getScreenRefreshRate + " hz, " + this.screenVSyncTimeout + " micros");
            }
            macOSXCGLGraphicsConfiguration.setChosenCapabilities(fixOpaqueGLCapabilities);
            final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
            final long context = CGL.createContext(n, nsViewHandle, containsUpstreamOptionBits, this.pixelFormat, glCapabilitiesImmutable.isBackgroundOpaque(), directIntBuffer);
            if (0L == context) {
                if (GLContext.DEBUG) {
                    System.err.println("NS create failed: viewNotReady: " + (1 == directIntBuffer.get(0)));
                }
                return 0L;
            }
            if (glCapabilitiesImmutable.isOnscreen() && !glCapabilitiesImmutable.isBackgroundOpaque()) {
                CGL.setContextOpacity(context, 0);
            }
            return context;
        }
        
        @Override
        public boolean destroy(final long n) {
            if (0L != this.pixelFormat) {
                CGL.deletePixelFormat(this.pixelFormat);
                this.pixelFormat = 0L;
            }
            return CGL.deleteContext(n, true);
        }
        
        @Override
        public void associateDrawable(final boolean b) {
            this.backingLayerHost = NativeWindowFactory.getOffscreenLayerSurface(MacOSXCGLContext.this.drawable.getNativeSurface(), true);
            if (GLContext.DEBUG) {
                System.err.println("MaxOSXCGLContext.NSOpenGLImpl.associateDrawable: " + b + ", ctx " + GLContext.toHexString(MacOSXCGLContext.this.contextHandle) + ", hasBackingLayerHost " + (null != this.backingLayerHost) + ", attachGLLayerCmd " + this.attachGLLayerCmd);
            }
            if (b) {
                if (null != this.backingLayerHost) {
                    final GLCapabilitiesImmutable chosenGLCapabilities = MacOSXCGLContext.this.drawable.getChosenGLCapabilities();
                    final long handle = MacOSXCGLContext.this.getHandle();
                    final long handle2 = MacOSXCGLContext.this.drawable.getHandle();
                    int name;
                    long n;
                    if (MacOSXCGLContext.this.drawable instanceof GLFBODrawableImpl) {
                        final GLFBODrawableImpl glfboDrawableImpl = (GLFBODrawableImpl)MacOSXCGLContext.this.drawable;
                        name = glfboDrawableImpl.getColorbuffer(1028).getName();
                        n = 0L;
                        glfboDrawableImpl.setSwapBufferContext(new GLFBODrawableImpl.SwapBufferContext() {
                            @Override
                            public void swapBuffers(final boolean b) {
                                NSOpenGLImpl.this.swapBuffers();
                            }
                        });
                    }
                    else {
                        if (!CGL.isNSOpenGLPixelBuffer(handle2)) {
                            throw new GLException("BackingLayerHost w/ unknown handle (!FBO, !PBuffer): " + MacOSXCGLContext.this.drawable);
                        }
                        name = 0;
                        n = handle2;
                        if (0L != handle2) {
                            CGL.setContextPBuffer(handle, n);
                            this.needsSetContextPBuffer = false;
                        }
                    }
                    this.lastWidth = MacOSXCGLContext.this.drawable.getSurfaceWidth();
                    this.lastHeight = MacOSXCGLContext.this.drawable.getSurfaceHeight();
                    if (0 >= this.lastWidth || 0 >= this.lastHeight || !MacOSXCGLContext.this.drawable.isRealized()) {
                        throw new GLException("Drawable not realized yet or invalid texture size, texSize " + this.lastWidth + "x" + this.lastHeight + ", " + MacOSXCGLContext.this.drawable);
                    }
                    int program;
                    if (MacOSXCGLContext.this.isGL3core()) {
                        if (null == this.gl3ShaderProgram) {
                            this.gl3ShaderProgram = createCALayerShader(MacOSXCGLContext.this.gl.getGL3ES3());
                        }
                        program = this.gl3ShaderProgram.program();
                    }
                    else {
                        program = 0;
                    }
                    final int[] convertToWindowUnits = MacOSXCGLContext.this.drawable.getNativeSurface().convertToWindowUnits(new int[] { this.lastWidth, this.lastHeight });
                    this.attachGLLayerCmd = new AttachGLLayerCmd(this.backingLayerHost, handle, program, this.pixelFormat, n, name, chosenGLCapabilities.isBackgroundOpaque(), this.lastWidth, this.lastHeight, convertToWindowUnits[0], convertToWindowUnits[1]);
                    if (GLContext.DEBUG) {
                        System.err.println("MaxOSXCGLContext.NSOpenGLImpl.associateDrawable(true): " + this.attachGLLayerCmd);
                    }
                    OSXUtil.RunOnMainThread(false, false, this.attachGLLayerCmd);
                }
                else {
                    this.lastWidth = MacOSXCGLContext.this.drawable.getSurfaceWidth();
                    this.lastHeight = MacOSXCGLContext.this.drawable.getSurfaceHeight();
                    CGL.setContextView(MacOSXCGLContext.this.contextHandle, this.getNSViewHandle(new boolean[] { false }, new boolean[] { false }, new boolean[] { false }));
                }
            }
            else {
                if (null != this.backingLayerHost) {
                    final AttachGLLayerCmd attachGLLayerCmd = this.attachGLLayerCmd;
                    this.attachGLLayerCmd = null;
                    if (null == attachGLLayerCmd) {
                        throw new GLException("Null attachGLLayerCmd: " + MacOSXCGLContext.this.drawable);
                    }
                    if (0L != attachGLLayerCmd.pbuffer) {
                        CGL.setContextPBuffer(MacOSXCGLContext.this.contextHandle, 0L);
                    }
                    synchronized (attachGLLayerCmd) {
                        if (!attachGLLayerCmd.valid) {
                            attachGLLayerCmd.valid = true;
                        }
                        else {
                            final DetachGLLayerCmd detachGLLayerCmd = new DetachGLLayerCmd(attachGLLayerCmd);
                            if (GLContext.DEBUG) {
                                System.err.println("MaxOSXCGLContext.NSOpenGLImpl.associateDrawable(false): " + detachGLLayerCmd);
                            }
                            OSXUtil.RunOnMainThread(false, true, detachGLLayerCmd);
                            if (null != this.gl3ShaderProgram) {
                                this.gl3ShaderProgram.destroy(MacOSXCGLContext.this.gl.getGL3());
                                this.gl3ShaderProgram = null;
                            }
                        }
                    }
                }
                CGL.clearDrawable(MacOSXCGLContext.this.contextHandle);
                this.backingLayerHost = null;
            }
        }
        
        private final void validatePBufferConfig(final long n) {
            final long handle = MacOSXCGLContext.this.drawable.getHandle();
            if (this.needsSetContextPBuffer && 0L != handle && CGL.isNSOpenGLPixelBuffer(handle)) {
                this.needsSetContextPBuffer = false;
                CGL.setContextPBuffer(n, handle);
                if (GLContext.DEBUG) {
                    System.err.println("NS.validateDrawableConfig bind pbuffer " + GLContext.toHexString(handle) + " -> ctx " + GLContext.toHexString(n));
                }
            }
        }
        
        private final boolean validateDrawableSizeConfig(final long n) {
            final int surfaceWidth = MacOSXCGLContext.this.drawable.getSurfaceWidth();
            final int surfaceHeight = MacOSXCGLContext.this.drawable.getSurfaceHeight();
            if (this.lastWidth != surfaceWidth || this.lastHeight != surfaceHeight) {
                this.lastWidth = MacOSXCGLContext.this.drawable.getSurfaceWidth();
                this.lastHeight = MacOSXCGLContext.this.drawable.getSurfaceHeight();
                if (GLContext.DEBUG) {
                    System.err.println("NS.validateDrawableConfig size changed");
                }
                return true;
            }
            return false;
        }
        
        @Override
        public boolean copyImpl(final long n, final int n2) {
            CGL.copyContext(MacOSXCGLContext.this.contextHandle, n, n2);
            return true;
        }
        
        @Override
        public boolean makeCurrent(final long n) {
            final long cglContext = CGL.getCGLContext(n);
            if (0L == cglContext) {
                throw new InternalError("Null CGLContext for: " + this);
            }
            final int cglLockContext = CGL.CGLLockContext(cglContext);
            if (cglLockContext == 0) {
                this.validatePBufferConfig(n);
                return CGL.makeCurrentContext(n);
            }
            if (GLContext.DEBUG) {
                System.err.println("NSGL: Could not lock context: err 0x" + Integer.toHexString(cglLockContext) + ": " + this);
            }
            return false;
        }
        
        @Override
        public boolean release(final long n) {
            try {
                if (MacOSXCGLContext.this.hasRendererQuirk(7) && null != MacOSXCGLContext.this.getGLProcAddressTable()) {
                    MacOSXCGLContext.this.gl.glFlush();
                }
            }
            catch (GLException ex) {
                if (GLContext.DEBUG) {
                    System.err.println("MacOSXCGLContext.NSOpenGLImpl.release: INFO: glFlush() caught exception:");
                    ex.printStackTrace();
                }
            }
            final boolean clearCurrentContext = CGL.clearCurrentContext(n);
            final long cglContext = CGL.getCGLContext(n);
            if (0L == cglContext) {
                throw new InternalError("Null CGLContext for: " + this);
            }
            final int cglUnlockContext = CGL.CGLUnlockContext(cglContext);
            if (GLContext.DEBUG && cglUnlockContext) {
                System.err.println("CGL: Could not unlock context: err 0x" + Integer.toHexString(cglUnlockContext) + ": " + this);
            }
            return clearCurrentContext && cglUnlockContext == 0;
        }
        
        @Override
        public boolean detachPBuffer() {
            return this.needsSetContextPBuffer = true;
        }
        
        @Override
        public boolean setSwapInterval(final int n) {
            final AttachGLLayerCmd attachGLLayerCmd = this.attachGLLayerCmd;
            if (null != attachGLLayerCmd) {
                synchronized (attachGLLayerCmd) {
                    if (attachGLLayerCmd.valid && 0L != attachGLLayerCmd.nsOpenGLLayer) {
                        this.setSwapIntervalImpl(attachGLLayerCmd.nsOpenGLLayer, n);
                        return true;
                    }
                }
            }
            this.setSwapIntervalImpl(0L, n);
            return true;
        }
        
        private void setSwapIntervalImpl(final long n, final int n2) {
            if (0L != n) {
                CGL.setNSOpenGLLayerSwapInterval(n, n2);
                if (0 < n2) {
                    this.vsyncTimeout = n2 * this.screenVSyncTimeout + 1000;
                }
                else {
                    this.vsyncTimeout = 1 * this.screenVSyncTimeout + 1000;
                }
                if (GLContext.DEBUG) {
                    System.err.println("NS setSwapInterval: " + n2 + " -> " + this.vsyncTimeout + " micros");
                }
            }
            if (GLContext.DEBUG) {
                System.err.println("CGL setSwapInterval: " + n2);
            }
            CGL.setSwapInterval(MacOSXCGLContext.this.contextHandle, n2);
        }
        
        @Override
        public boolean swapBuffers() {
            final AttachGLLayerCmd attachGLLayerCmd = this.attachGLLayerCmd;
            if (null != attachGLLayerCmd) {
                synchronized (attachGLLayerCmd) {
                    if (attachGLLayerCmd.valid && 0L != attachGLLayerCmd.nsOpenGLLayer) {
                        if (this.validateDrawableSizeConfig(MacOSXCGLContext.this.contextHandle)) {
                            this.skipSync = 10;
                        }
                        final boolean b = MacOSXCGLContext.this.drawable instanceof GLFBODrawableImpl;
                        int name;
                        boolean b2;
                        if (b) {
                            name = ((GLFBODrawableImpl)MacOSXCGLContext.this.drawable).getColorbuffer(1028).getName();
                            b2 = (name != 0);
                        }
                        else {
                            name = 0;
                            b2 = (0L != MacOSXCGLContext.this.drawable.getHandle());
                        }
                        boolean flushBuffer;
                        if (b2) {
                            flushBuffer = CGL.flushBuffer(MacOSXCGLContext.this.contextHandle);
                            if (flushBuffer) {
                                if (0 == this.skipSync) {
                                    MacOSXCGLContext.this.gl.glFinish();
                                    CGL.waitUntilNSOpenGLLayerIsReady(attachGLLayerCmd.nsOpenGLLayer, this.vsyncTimeout);
                                }
                                else {
                                    --this.skipSync;
                                }
                                if (b) {
                                    CGL.setNSOpenGLLayerNeedsDisplayFBO(attachGLLayerCmd.nsOpenGLLayer, name);
                                }
                                else {
                                    CGL.setNSOpenGLLayerNeedsDisplayPBuffer(attachGLLayerCmd.nsOpenGLLayer, MacOSXCGLContext.this.drawable.getHandle());
                                }
                            }
                        }
                        else {
                            flushBuffer = true;
                        }
                        return flushBuffer;
                    }
                }
            }
            return CGL.flushBuffer(MacOSXCGLContext.this.contextHandle);
        }
        
        class AttachGLLayerCmd implements Runnable
        {
            final OffscreenLayerSurface ols;
            final long ctx;
            final int shaderProgram;
            final long pfmt;
            final long pbuffer;
            final int texID;
            final boolean isOpaque;
            final int texWidth;
            final int texHeight;
            final int winWidth;
            final int winHeight;
            long nsOpenGLLayer;
            boolean valid;
            
            AttachGLLayerCmd(final OffscreenLayerSurface ols, final long ctx, final int shaderProgram, final long pfmt, final long pbuffer, final int texID, final boolean isOpaque, final int texWidth, final int texHeight, final int winWidth, final int winHeight) {
                this.ols = ols;
                this.ctx = ctx;
                this.shaderProgram = shaderProgram;
                this.pfmt = pfmt;
                this.pbuffer = pbuffer;
                this.texID = texID;
                this.isOpaque = isOpaque;
                this.texWidth = texWidth;
                this.texHeight = texHeight;
                this.winWidth = winWidth;
                this.winHeight = winHeight;
                this.valid = false;
                this.nsOpenGLLayer = 0L;
            }
            
            public final String contentToString() {
                return "valid " + this.valid + ", size tex[" + this.texWidth + "x" + this.texHeight + "], win[" + this.winWidth + "x" + this.winHeight + "], ctx " + GLContext.toHexString(this.ctx) + ", opaque " + this.isOpaque + ", texID " + this.texID + ", pbuffer " + GLContext.toHexString(this.pbuffer) + ", nsOpenGLLayer " + GLContext.toHexString(this.nsOpenGLLayer);
            }
            
            @Override
            public final String toString() {
                return "AttachGLLayerCmd[" + this.contentToString() + "]";
            }
            
            @Override
            public void run() {
                synchronized (this) {
                    if (!this.valid) {
                        try {
                            final int n = NSOpenGLImpl.this.screenVSyncTimeout / 2000;
                            final RecursiveLock lock = this.ols.getLock();
                            if (lock.tryLock(n)) {
                                try {
                                    if (MacOSXCGLContext.this.lock.tryLock(n)) {
                                        try {
                                            this.nsOpenGLLayer = CGL.createNSOpenGLLayer(this.ctx, this.shaderProgram, this.pfmt, this.pbuffer, this.texID, this.isOpaque, this.texWidth, this.texHeight, this.winWidth, this.winHeight);
                                            this.ols.attachSurfaceLayer(this.nsOpenGLLayer);
                                            final int swapInterval = MacOSXCGLContext.this.getSwapInterval();
                                            NSOpenGLImpl.this.setSwapIntervalImpl(this.nsOpenGLLayer, (0 <= swapInterval) ? swapInterval : true);
                                            this.valid = true;
                                            if (GLContext.DEBUG) {
                                                System.err.println("NSOpenGLLayer.Attach: OK, layer " + GLContext.toHexString(this.nsOpenGLLayer) + " w/ pbuffer " + GLContext.toHexString(this.pbuffer) + ", texID " + this.texID + ", texSize " + NSOpenGLImpl.this.lastWidth + "x" + NSOpenGLImpl.this.lastHeight + ", drawableHandle " + GLContext.toHexString(MacOSXCGLContext.this.drawable.getHandle()) + " - " + GLContext.getThreadName());
                                            }
                                        }
                                        finally {
                                            MacOSXCGLContext.this.lock.unlock();
                                        }
                                    }
                                }
                                finally {
                                    lock.unlock();
                                }
                            }
                        }
                        catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        if (!this.valid) {
                            if (GLContext.DEBUG) {
                                System.err.println("NSOpenGLLayer.Attach: Re-Queue, drawableHandle " + GLContext.toHexString(MacOSXCGLContext.this.drawable.getHandle()) + " - " + GLContext.getThreadName());
                            }
                            OSXUtil.RunLater(true, this, 1);
                        }
                    }
                }
            }
        }
        
        class DetachGLLayerCmd implements Runnable
        {
            final AttachGLLayerCmd cmd;
            
            DetachGLLayerCmd(final AttachGLLayerCmd cmd) {
                this.cmd = cmd;
            }
            
            @Override
            public final String toString() {
                return "DetachGLLayerCmd[" + this.cmd.contentToString() + "]";
            }
            
            @Override
            public void run() {
                synchronized (this.cmd) {
                    if (this.cmd.valid) {
                        try {
                            final OffscreenLayerSurface ols = this.cmd.ols;
                            if (0L != ols.getAttachedSurfaceLayer()) {
                                ols.detachSurfaceLayer();
                            }
                        }
                        catch (Throwable t) {
                            System.err.println("Caught exception on thread " + GLContext.getThreadName());
                            t.printStackTrace();
                        }
                        CGL.releaseNSOpenGLLayer(this.cmd.nsOpenGLLayer);
                        if (GLContext.DEBUG) {
                            System.err.println("NSOpenGLLayer.Detach: OK, layer " + GLContext.toHexString(this.cmd.nsOpenGLLayer) + " - " + GLContext.getThreadName());
                        }
                        this.cmd.nsOpenGLLayer = 0L;
                        this.cmd.valid = false;
                    }
                    else if (GLContext.DEBUG) {
                        System.err.println("NSOpenGLLayer.Detach: Skipped " + GLContext.toHexString(this.cmd.nsOpenGLLayer) + " - " + GLContext.getThreadName());
                    }
                }
            }
        }
    }
    
    class CGLImpl implements GLBackendImpl
    {
        @Override
        public boolean isNSContext() {
            return false;
        }
        
        @Override
        public boolean isUsingCALayer() {
            return false;
        }
        
        @Override
        public long create(final long n, final int n2, final int n3, final int n4) {
            long value = 0L;
            final MacOSXCGLGraphicsConfiguration macOSXCGLGraphicsConfiguration = (MacOSXCGLGraphicsConfiguration)MacOSXCGLContext.this.drawable.getNativeSurface().getGraphicsConfiguration();
            final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)macOSXCGLGraphicsConfiguration.getChosenCapabilities();
            final long glCapabilities2CGLPixelFormat = MacOSXCGLGraphicsConfiguration.GLCapabilities2CGLPixelFormat(macOSXCGLGraphicsConfiguration.getScreen().getDevice(), glCapabilitiesImmutable, n2, n3, n4);
            if (glCapabilities2CGLPixelFormat == 0L) {
                throw new GLException("Unable to allocate pixel format with requested GLCapabilities");
            }
            try {
                final PointerBuffer allocateDirect = PointerBuffer.allocateDirect(1);
                if (GLContext.DEBUG) {
                    System.err.println("Share context for CGL-based pbuffer context is " + GLContext.toHexString(n));
                }
                final int cglCreateContext = CGL.CGLCreateContext(glCapabilities2CGLPixelFormat, n, allocateDirect);
                if (cglCreateContext != 0) {
                    throw new GLException("Error code " + cglCreateContext + " while creating context");
                }
                value = allocateDirect.get(0);
                if (0L != value) {
                    final GLCapabilities fixOpaqueGLCapabilities = GLGraphicsConfigurationUtil.fixOpaqueGLCapabilities(MacOSXCGLGraphicsConfiguration.CGLPixelFormat2GLCapabilities(glCapabilities2CGLPixelFormat), glCapabilitiesImmutable.isBackgroundOpaque());
                    fixOpaqueGLCapabilities.setFBO(false);
                    fixOpaqueGLCapabilities.setPBuffer(fixOpaqueGLCapabilities.isPBuffer() && !glCapabilitiesImmutable.isOnscreen());
                    fixOpaqueGLCapabilities.setBitmap(false);
                    fixOpaqueGLCapabilities.setOnscreen(!fixOpaqueGLCapabilities.isPBuffer());
                    macOSXCGLGraphicsConfiguration.setChosenCapabilities(fixOpaqueGLCapabilities);
                    if (GLContext.DEBUG) {
                        System.err.println("CGL create fixedCaps: " + fixOpaqueGLCapabilities);
                    }
                    if (fixOpaqueGLCapabilities.isPBuffer()) {
                        final int cglSetPBuffer = CGL.CGLSetPBuffer(value, MacOSXCGLContext.this.drawable.getHandle(), 0, 0, 0);
                        if (cglSetPBuffer != 0) {
                            throw new GLException("Error code " + cglSetPBuffer + " while attaching context to pbuffer");
                        }
                    }
                }
            }
            finally {
                CGL.CGLDestroyPixelFormat(glCapabilities2CGLPixelFormat);
            }
            return value;
        }
        
        @Override
        public boolean destroy(final long n) {
            return CGL.CGLDestroyContext(n) == 0;
        }
        
        @Override
        public void associateDrawable(final boolean b) {
        }
        
        @Override
        public boolean copyImpl(final long n, final int n2) {
            CGL.CGLCopyContext(n, MacOSXCGLContext.this.contextHandle, n2);
            return true;
        }
        
        @Override
        public boolean makeCurrent(final long n) {
            final int cglLockContext = CGL.CGLLockContext(n);
            if (cglLockContext == 0) {
                final int cglSetCurrentContext = CGL.CGLSetCurrentContext(n);
                if (cglSetCurrentContext == 0) {
                    return true;
                }
                if (GLContext.DEBUG) {
                    System.err.println("CGL: Could not make context current: err 0x" + Integer.toHexString(cglSetCurrentContext) + ": " + this);
                }
            }
            else if (GLContext.DEBUG) {
                System.err.println("CGL: Could not lock context: err 0x" + Integer.toHexString(cglLockContext) + ": " + this);
            }
            return false;
        }
        
        @Override
        public boolean release(final long n) {
            try {
                if (MacOSXCGLContext.this.hasRendererQuirk(7) && null != MacOSXCGLContext.this.getGLProcAddressTable()) {
                    MacOSXCGLContext.this.gl.glFlush();
                }
            }
            catch (GLException ex) {
                if (GLContext.DEBUG) {
                    System.err.println("MacOSXCGLContext.CGLImpl.release: INFO: glFlush() caught exception:");
                    ex.printStackTrace();
                }
            }
            final int cglSetCurrentContext = CGL.CGLSetCurrentContext(0L);
            if (GLContext.DEBUG && cglSetCurrentContext) {
                System.err.println("CGL: Could not release current context: err 0x" + Integer.toHexString(cglSetCurrentContext) + ": " + this);
            }
            final int cglUnlockContext = CGL.CGLUnlockContext(n);
            if (GLContext.DEBUG && cglUnlockContext) {
                System.err.println("CGL: Could not unlock context: err 0x" + Integer.toHexString(cglUnlockContext) + ": " + this);
            }
            return cglSetCurrentContext == 0 && cglUnlockContext == 0;
        }
        
        @Override
        public boolean detachPBuffer() {
            return true;
        }
        
        @Override
        public boolean setSwapInterval(final int n) {
            final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
            directIntBuffer.put(0, n);
            CGL.CGLSetParameter(MacOSXCGLContext.this.contextHandle, 222, directIntBuffer);
            return true;
        }
        
        @Override
        public boolean swapBuffers() {
            return 0 == CGL.CGLFlushDrawable(MacOSXCGLContext.this.contextHandle);
        }
    }
    
    protected interface GLBackendImpl
    {
        boolean isNSContext();
        
        boolean isUsingCALayer();
        
        long create(final long p0, final int p1, final int p2, final int p3);
        
        boolean destroy(final long p0);
        
        void associateDrawable(final boolean p0);
        
        boolean copyImpl(final long p0, final int p1);
        
        boolean makeCurrent(final long p0);
        
        boolean release(final long p0);
        
        boolean detachPBuffer();
        
        boolean setSwapInterval(final int p0);
        
        boolean swapBuffers();
    }
}
