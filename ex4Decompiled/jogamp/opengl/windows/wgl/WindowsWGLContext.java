// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import java.util.HashMap;
import java.nio.IntBuffer;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.opengl.GLException;
import jogamp.opengl.GLDynamicLookupHelper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import jogamp.nativewindow.windows.GDI;
import com.jogamp.opengl.GLContext;
import jogamp.opengl.GLDrawableImpl;
import java.util.Map;
import jogamp.opengl.GLContextImpl;

public class WindowsWGLContext extends GLContextImpl
{
    static final Map<String, String> extensionNameMap;
    private boolean wglGetExtensionsStringEXTInitialized;
    private boolean wglGetExtensionsStringEXTAvailable;
    private boolean wglGLReadDrawableAvailableSet;
    private boolean wglGLReadDrawableAvailable;
    private WGLExt _wglExt;
    private WGLExtProcAddressTable wglExtProcAddressTable;
    private int hasSwapInterval;
    private int hasSwapGroupNV;
    
    WindowsWGLContext(final GLDrawableImpl glDrawableImpl, final GLContext glContext) {
        super(glDrawableImpl, glContext);
        this.hasSwapInterval = 0;
        this.hasSwapGroupNV = 0;
    }
    
    @Override
    protected void resetStates(final boolean b) {
        this.wglGetExtensionsStringEXTInitialized = false;
        this.wglGetExtensionsStringEXTAvailable = false;
        this.wglGLReadDrawableAvailableSet = false;
        this.wglGLReadDrawableAvailable = false;
        this.wglExtProcAddressTable = null;
        this.hasSwapInterval = 0;
        this.hasSwapGroupNV = 0;
        super.resetStates(b);
    }
    
    @Override
    public Object getPlatformGLExtensions() {
        return this.getWGLExt();
    }
    
    final WGLExt getWGLExt() {
        if (null == this.getWGLExtProcAddressTable()) {
            throw new InternalError("Null WGLExtProcAddressTable");
        }
        if (this._wglExt == null) {
            this._wglExt = new WGLExtImpl(this);
        }
        return this._wglExt;
    }
    
    @Override
    public final boolean isGLReadDrawableAvailable() {
        if (!this.wglGLReadDrawableAvailableSet && null != this.getWGLExtProcAddressTable()) {
            switch (((WindowsWGLDrawableFactory)this.drawable.getFactoryImpl()).isReadDrawableAvailable(this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice())) {
                case 1: {
                    this.wglGLReadDrawableAvailable = true;
                    this.wglGLReadDrawableAvailableSet = true;
                    break;
                }
                case 0: {
                    this.wglGLReadDrawableAvailable = false;
                    this.wglGLReadDrawableAvailableSet = true;
                    break;
                }
            }
        }
        return this.wglGLReadDrawableAvailable;
    }
    
    private final boolean wglMakeContextCurrent(final long n, final long n2, final long n3) {
        boolean b;
        if (this.wglGLReadDrawableAvailable) {
            b = this.getWGLExt().wglMakeContextCurrent(n, n2, n3);
        }
        else {
            if (n != n2) {
                throw new InternalError("Given readDrawable but no driver support");
            }
            b = WGL.wglMakeCurrent(n, n3);
        }
        if (WindowsWGLContext.DEBUG && !b) {
            new Throwable("Info: wglMakeContextCurrent NOK: draw " + GLContext.toHexString(n) + ", read " + GLContext.toHexString(n2) + ", ctx " + GLContext.toHexString(n3) + ", werr " + GDI.GetLastError()).printStackTrace();
        }
        return b;
    }
    
    private final boolean wglReleaseContext() {
        boolean b;
        if (this.wglGLReadDrawableAvailable) {
            b = this.getWGLExt().wglMakeContextCurrent(0L, 0L, 0L);
        }
        else {
            b = WGL.wglMakeCurrent(0L, 0L);
        }
        if (!b) {
            final int getLastError = GDI.GetLastError();
            final boolean b2 = getLastError == 0;
            if (WindowsWGLContext.DEBUG) {
                new Throwable("Info: wglReleaseContext NOK: werr " + getLastError + " -> ok " + b2).printStackTrace();
            }
            return b2;
        }
        return b;
    }
    
    @Override
    public final ProcAddressTable getPlatformExtProcAddressTable() {
        return this.getWGLExtProcAddressTable();
    }
    
    public final WGLExtProcAddressTable getWGLExtProcAddressTable() {
        return this.wglExtProcAddressTable;
    }
    
    @Override
    protected Map<String, String> getFunctionNameMap() {
        return null;
    }
    
    @Override
    protected Map<String, String> getExtensionNameMap() {
        return WindowsWGLContext.extensionNameMap;
    }
    
    @Override
    protected void destroyContextARBImpl(final long n) {
        WGL.wglMakeCurrent(0L, 0L);
        WGL.wglDeleteContext(n);
    }
    
    @Override
    protected long createContextARBImpl(final long n, final boolean b, final int n2, final int n3, final int n4) {
        if (WindowsWGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + " - WindowWGLContext.createContextARBImpl: " + GLContext.getGLVersion(n3, n4, n2, "@creation") + ", handle " + GLContext.toHexString(this.drawable.getHandle()) + ", share " + GLContext.toHexString(n) + ", direct " + b);
        }
        final boolean b2 = 0x0 == (0x8 & n2);
        final boolean b3 = 0x0 != (0x2 & n2);
        final boolean b4 = 0x0 != (0x10 & n2);
        final boolean b5 = 0x0 != (0x20 & n2);
        if (!b2) {
            if (WindowsWGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": WindowWGLContext.createContextARBImpl: GL ES not avail " + GLContext.getGLVersion(n3, n4, n2, "@creation"));
            }
            return 0L;
        }
        if (null == this.getWGLExtProcAddressTable()) {
            final GLDynamicLookupHelper glDynamicLookupHelper = this.getGLDynamicLookupHelper(n3, n2);
            if (null == glDynamicLookupHelper) {
                if (WindowsWGLContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + " - WindowWGLContext.createContextARBImpl: Null GLDynamicLookupHelper");
                }
                return 0L;
            }
            this.updateGLXProcAddressTable(null, glDynamicLookupHelper);
        }
        final WGLExt wglExt = this.getWGLExt();
        if (WindowsWGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + " - WindowWGLContext.createContextARBImpl: " + ", wglCreateContextAttribsARB: " + GLContext.toHexString(this.wglExtProcAddressTable._addressof_wglCreateContextAttribsARB));
        }
        long wglCreateContextAttribsARB = 0L;
        final int[] array = { 8337, n3, 8338, n4, 8340, 0, 0, 0, 0 };
        if (n3 > 3 || (n3 == 3 && n4 >= 2)) {
            array[6] = 37158;
            if (b3) {
                array[7] = 2;
            }
            else {
                array[7] = 1;
            }
        }
        if (n3 >= 3) {
            if (!b3 && b4) {
                final int[] array2 = array;
                final int n5 = 5;
                array2[n5] |= 0x2;
            }
            if (b5) {
                final int[] array3 = array;
                final int n6 = 5;
                array3[n6] |= 0x1;
            }
        }
        try {
            wglCreateContextAttribsARB = wglExt.wglCreateContextAttribsARB(this.drawable.getHandle(), n, Buffers.newDirectIntBuffer(array));
        }
        catch (RuntimeException ex) {
            if (WindowsWGLContext.DEBUG) {
                new Throwable("Info: WindowWGLContext.createContextARBImpl wglCreateContextAttribsARB failed with " + GLContext.getGLVersion(n3, n4, n2, "@creation"), ex).printStackTrace();
            }
        }
        if (0L != wglCreateContextAttribsARB) {
            if (!this.wglMakeContextCurrent(this.drawable.getHandle(), this.drawableRead.getHandle(), wglCreateContextAttribsARB)) {
                if (WindowsWGLContext.DEBUG) {
                    System.err.println("WindowsWGLContext.createContextARB couldn't make current " + GLContext.getGLVersion(n3, n4, n2, "@creation"));
                }
                WGL.wglMakeCurrent(0L, 0L);
                WGL.wglDeleteContext(wglCreateContextAttribsARB);
                wglCreateContextAttribsARB = 0L;
            }
            else if (WindowsWGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createContextARBImpl: OK " + GLContext.getGLVersion(n3, n4, n2, "@creation") + ", share " + n + ", direct " + b);
            }
        }
        else if (WindowsWGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": createContextARBImpl: NO " + GLContext.getGLVersion(n3, n4, n2, "@creation"));
        }
        return wglCreateContextAttribsARB;
    }
    
    @Override
    protected boolean createImpl(long n) throws GLException {
        final AbstractGraphicsConfiguration graphicsConfiguration = this.drawable.getNativeSurface().getGraphicsConfiguration();
        final AbstractGraphicsDevice device = graphicsConfiguration.getScreen().getDevice();
        final WindowsWGLContext windowsWGLContext = (WindowsWGLContext)((WindowsWGLDrawableFactory)this.drawable.getFactoryImpl()).getOrCreateSharedContext(device);
        final GLCapabilitiesImmutable chosenGLCapabilities = this.drawable.getChosenGLCapabilities();
        this.isGLReadDrawableAvailable();
        final boolean b = this.isCreateContextARBAvail(device) && !chosenGLCapabilities.isBitmap();
        final boolean b2 = null != windowsWGLContext && windowsWGLContext.isCreatedWithARBMethod();
        if (WindowsWGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": WindowsWGLContext.createImpl: START " + chosenGLCapabilities + ", share " + GLContext.toHexString(n));
            System.err.println(GLContext.getThreadName() + ": Use ARB[avail[" + this.getCreateContextARBAvailStr(device) + "], bitmap " + chosenGLCapabilities.isBitmap() + " -> " + b + "], shared " + b2 + "]");
        }
        final GLProfile glProfile = chosenGLCapabilities.getGLProfile();
        if (glProfile.isGLES()) {
            throw new GLException(GLContext.getThreadName() + ": Unable to create OpenGL ES context on desktopDevice " + device + ", config " + graphicsConfiguration + ", " + glProfile + ", shareWith " + GLContext.toHexString(n));
        }
        boolean b3 = false;
        if (b && b2) {
            if (windowsWGLContext.getRendererQuirks().exist(10)) {
                if (0 == windowsWGLContext.makeCurrent()) {
                    throw new GLException("Could not make Shared Context current: " + windowsWGLContext);
                }
                this.contextHandle = this.createContextARB(n, true);
                windowsWGLContext.release();
                if (!this.wglMakeContextCurrent(this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
                    throw new GLException("Cannot make previous verified context current: 0x" + GLContext.toHexString(this.contextHandle) + ", werr: " + GDI.GetLastError());
                }
            }
            else {
                this.contextHandle = this.createContextARB(n, true);
            }
            b3 = true;
            if (WindowsWGLContext.DEBUG && 0L != this.contextHandle) {
                System.err.println(GLContext.getThreadName() + ": createImpl: OK (ARB, using sharedContext) share " + GLContext.toHexString(n));
            }
        }
        long wglCreateContext;
        if (0L == this.contextHandle) {
            wglCreateContext = WGL.wglCreateContext(this.drawable.getHandle());
            if (0L == wglCreateContext) {
                throw new GLException("Unable to create temp OpenGL context for device context " + GLContext.toHexString(this.drawable.getHandle()));
            }
            if (!WGL.wglMakeCurrent(this.drawable.getHandle(), wglCreateContext)) {
                throw new GLException("Error making temp context current: 0x" + GLContext.toHexString(wglCreateContext) + ", werr: " + GDI.GetLastError());
            }
            if (!this.setGLFunctionAvailability(true, 0, 0, 2, false, null == windowsWGLContext)) {
                WGL.wglMakeCurrent(0L, 0L);
                WGL.wglDeleteContext(wglCreateContext);
                throw new GLException("setGLFunctionAvailability !strictMatch failed");
            }
            WGL.wglMakeCurrent(0L, 0L);
            if (b && !b3) {
                final boolean functionAvailable = this.isFunctionAvailable("wglCreateContextAttribsARB");
                final boolean extensionAvailable = this.isExtensionAvailable("WGL_ARB_create_context");
                if (functionAvailable && extensionAvailable) {
                    this.contextHandle = this.createContextARB(n, true);
                    b3 = true;
                    if (WindowsWGLContext.DEBUG) {
                        if (0L != this.contextHandle) {
                            System.err.println(GLContext.getThreadName() + ": createImpl: OK (ARB, initial) share " + GLContext.toHexString(n));
                        }
                        else {
                            System.err.println(GLContext.getThreadName() + ": createImpl: NOT OK (ARB, initial) - creation failed - share " + GLContext.toHexString(n));
                        }
                    }
                }
                else if (WindowsWGLContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": createImpl: NOT OK (ARB, initial) - extension not available - share " + GLContext.toHexString(n) + ", isProcCreateContextAttribsARBAvailable " + functionAvailable + ", isExtGLXARBCreateContextAvailable " + extensionAvailable);
                }
            }
        }
        else {
            wglCreateContext = 0L;
        }
        if (0L != this.contextHandle) {
            n = 0L;
            if (0L != wglCreateContext) {
                WGL.wglMakeCurrent(0L, 0L);
                WGL.wglDeleteContext(wglCreateContext);
                if (!this.wglMakeContextCurrent(this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
                    throw new GLException("Cannot make previous verified context current: 0x" + GLContext.toHexString(this.contextHandle) + ", werr: " + GDI.GetLastError());
                }
            }
        }
        else {
            if (chosenGLCapabilities.getGLProfile().isGL3() && b3) {
                WGL.wglMakeCurrent(0L, 0L);
                WGL.wglDeleteContext(wglCreateContext);
                throw new GLException(GLContext.getThreadName() + ": createImpl ctx !ARB but ARB is used, profile > GL2 requested (OpenGL >= 3.1). Requested: " + chosenGLCapabilities.getGLProfile() + ", current: " + this.getGLVersion());
            }
            if (WindowsWGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createImpl ARB not used[avail " + b + ", tried " + b3 + "], fall back to !ARB context " + this.getGLVersion());
            }
            this.contextHandle = wglCreateContext;
            if (!this.wglMakeContextCurrent(this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
                WGL.wglMakeCurrent(0L, 0L);
                WGL.wglDeleteContext(this.contextHandle);
                throw new GLException("Error making old context current: 0x" + GLContext.toHexString(this.contextHandle) + ", werr: " + GDI.GetLastError());
            }
            if (0L != n && !WGL.wglShareLists(n, this.contextHandle)) {
                throw new GLException("wglShareLists(" + GLContext.toHexString(n) + ", " + GLContext.toHexString(this.contextHandle) + ") failed: werr " + GDI.GetLastError());
            }
            if (WindowsWGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": createImpl: OK (old) share " + GLContext.toHexString(n));
            }
        }
        return true;
    }
    
    @Override
    protected void makeCurrentImpl() throws GLException {
        if (!this.wglMakeContextCurrent(this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
            throw new GLException("Error making context " + GLContext.toHexString(this.contextHandle) + " current on Thread " + GLContext.getThreadName() + ", drawableWrite " + GLContext.toHexString(this.drawable.getHandle()) + ", drawableRead " + GLContext.toHexString(this.drawableRead.getHandle()) + ", werr: " + GDI.GetLastError() + ", " + this);
        }
    }
    
    @Override
    protected void releaseImpl() throws GLException {
        if (!this.wglReleaseContext()) {
            throw new GLException("Error freeing OpenGL context, werr: " + GDI.GetLastError());
        }
    }
    
    @Override
    protected void destroyImpl() throws GLException {
        WGL.wglMakeCurrent(0L, 0L);
        if (!WGL.wglDeleteContext(this.contextHandle)) {
            throw new GLException("Unable to delete OpenGL context");
        }
    }
    
    @Override
    protected void copyImpl(final GLContext glContext, final int n) throws GLException {
        if (!WGL.wglCopyContext(glContext.getHandle(), this.getHandle(), n)) {
            throw new GLException("wglCopyContext failed");
        }
    }
    
    @Override
    protected final void updateGLXProcAddressTable(final String s, final GLDynamicLookupHelper glDynamicLookupHelper) {
        if (null == glDynamicLookupHelper) {
            throw new GLException("No GLDynamicLookupHelper for " + this);
        }
        final String string = "WGL-" + this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice().getUniqueID();
        if (WindowsWGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": Initializing WGL extension address table: " + string);
        }
        this.wglGetExtensionsStringEXTInitialized = false;
        this.wglGetExtensionsStringEXTAvailable = false;
        this.wglGLReadDrawableAvailableSet = false;
        this.wglGLReadDrawableAvailable = false;
        ProcAddressTable procAddressTable = null;
        synchronized (WindowsWGLContext.mappedContextTypeObjectLock) {
            procAddressTable = WindowsWGLContext.mappedGLXProcAddress.get(string);
        }
        if (null != procAddressTable) {
            this.wglExtProcAddressTable = (WGLExtProcAddressTable)procAddressTable;
            if (WindowsWGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext WGL ProcAddressTable reusing key(" + string + ") -> " + GLContext.toHexString(procAddressTable.hashCode()));
            }
        }
        else {
            this.resetProcAddressTable(this.wglExtProcAddressTable = new WGLExtProcAddressTable(new GLProcAddressResolver()), glDynamicLookupHelper);
            synchronized (WindowsWGLContext.mappedContextTypeObjectLock) {
                WindowsWGLContext.mappedGLXProcAddress.put(string, this.getWGLExtProcAddressTable());
                if (WindowsWGLContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext WGL ProcAddressTable mapping key(" + string + ") -> " + GLContext.toHexString(this.getWGLExtProcAddressTable().hashCode()));
                }
            }
        }
    }
    
    @Override
    protected final StringBuilder getPlatformExtensionsStringImpl() {
        final StringBuilder sb = new StringBuilder();
        if (!this.wglGetExtensionsStringEXTInitialized) {
            this.wglGetExtensionsStringEXTAvailable = (WGL.wglGetProcAddress("wglGetExtensionsStringEXT") != 0L);
            this.wglGetExtensionsStringEXTInitialized = true;
        }
        if (this.wglGetExtensionsStringEXTAvailable) {
            sb.append(this.getWGLExt().wglGetExtensionsStringEXT());
        }
        return sb;
    }
    
    @Override
    protected final Integer setSwapIntervalImpl2(final int n) {
        if (!this.drawable.getChosenGLCapabilities().isOnscreen()) {
            return null;
        }
        if (0 == this.hasSwapInterval) {
            try {
                if (this.isExtensionAvailable("WGL_EXT_swap_control")) {
                    this.hasSwapInterval = 1;
                    if (this.isExtensionAvailable("WGL_EXT_swap_control_tear")) {
                        this.hasSwapInterval = 2;
                        if (WindowsWGLContext.DEBUG) {
                            System.err.println("WGLContext.setSwapInterval.2 using: WGL_EXT_swap_control_tear, WGL_EXT_swap_control_tear");
                        }
                    }
                    else {
                        this.hasSwapInterval = 1;
                        if (WindowsWGLContext.DEBUG) {
                            System.err.println("WGLContext.setSwapInterval.1 using: WGL_EXT_swap_control");
                        }
                    }
                }
                else {
                    this.hasSwapInterval = -1;
                    if (WindowsWGLContext.DEBUG) {
                        System.err.println("WGLContext.setSwapInterval.0 N/A");
                    }
                }
            }
            catch (Throwable t) {
                this.hasSwapInterval = -1;
                if (WindowsWGLContext.DEBUG) {
                    ExceptionUtils.dumpThrowable("", t);
                }
            }
        }
        if (0 < this.hasSwapInterval) {
            int abs;
            if (1 == this.hasSwapInterval && 0 > n) {
                abs = Math.abs(n);
            }
            else {
                abs = n;
            }
            try {
                if (this.getWGLExt().wglSwapIntervalEXT(abs)) {
                    return abs;
                }
            }
            catch (Throwable t2) {
                this.hasSwapInterval = -1;
                if (WindowsWGLContext.DEBUG) {
                    ExceptionUtils.dumpThrowable("", t2);
                }
            }
        }
        return null;
    }
    
    private final int initSwapGroupImpl(final WGLExt wglExt) {
        if (0 == this.hasSwapGroupNV) {
            try {
                this.hasSwapGroupNV = (wglExt.isExtensionAvailable("WGL_NV_swap_group") ? 1 : -1);
            }
            catch (Throwable t) {
                this.hasSwapGroupNV = 1;
            }
            if (WindowsWGLContext.DEBUG) {
                System.err.println("initSwapGroupImpl: hasSwapGroupNV: " + this.hasSwapGroupNV);
            }
        }
        return this.hasSwapGroupNV;
    }
    
    @Override
    protected final boolean queryMaxSwapGroupsImpl(final int[] array, final int n, final int[] array2, final int n2) {
        boolean b = false;
        final WGLExt wglExt = this.getWGLExt();
        if (this.initSwapGroupImpl(wglExt) > 0) {
            final NativeSurface nativeSurface = this.drawable.getNativeSurface();
            try {
                final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(array.length - n);
                final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(array2.length - n2);
                if (wglExt.wglQueryMaxSwapGroupsNV(nativeSurface.getDisplayHandle(), directIntBuffer, directIntBuffer2)) {
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
        final WGLExt wglExt = this.getWGLExt();
        if (this.initSwapGroupImpl(wglExt) > 0) {
            try {
                if (wglExt.wglJoinSwapGroupNV(this.drawable.getHandle(), currentSwapGroup)) {
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
        final WGLExt wglExt = this.getWGLExt();
        if (this.initSwapGroupImpl(wglExt) > 0) {
            try {
                if (wglExt.wglBindSwapBarrierNV(n, n2)) {
                    b = true;
                }
            }
            catch (Throwable t) {
                this.hasSwapGroupNV = -1;
            }
        }
        return b;
    }
    
    static {
        (extensionNameMap = new HashMap<String, String>()).put("GL_ARB_pbuffer", "WGL_ARB_pbuffer");
        WindowsWGLContext.extensionNameMap.put("GL_ARB_pixel_format", "WGL_ARB_pixel_format");
    }
}
