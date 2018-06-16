// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import jogamp.opengl.GLDynamicLookupHelper;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import java.nio.IntBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.opengl.egl.EGL;
import java.util.Map;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import com.jogamp.opengl.egl.EGLExt;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import jogamp.opengl.GLContextImpl;

public class EGLContext extends GLContextImpl
{
    private EGLExtProcAddressTable eglExtProcAddressTable;
    private EGLExtImpl eglExtImpl;
    static final int CTX_PROFILE_COMPAT = 2;
    static final int CTX_PROFILE_CORE = 4;
    static final int CTX_PROFILE_ES = 8;
    private static final int ctx_attribs_idx_major = 0;
    private static final int[] ctx_attribs_rom;
    
    public static String getGLProfile(final int n, final int n2, final int n3) throws GLException {
        return GLContext.getGLProfile(n, n2, n3);
    }
    
    EGLContext(final GLDrawableImpl glDrawableImpl, final GLContext glContext) {
        super(glDrawableImpl, glContext);
    }
    
    @Override
    protected void resetStates(final boolean b) {
        this.eglExtProcAddressTable = null;
        this.eglExtImpl = null;
        super.resetStates(b);
    }
    
    @Override
    public Object getPlatformGLExtensions() {
        return this.getEGLExt();
    }
    
    public final EGLExt getEGLExt() {
        return this.eglExtImpl;
    }
    
    @Override
    public final ProcAddressTable getPlatformExtProcAddressTable() {
        return this.eglExtProcAddressTable;
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
    public final boolean isGLReadDrawableAvailable() {
        return true;
    }
    
    @Override
    protected void makeCurrentImpl() throws GLException {
        final long displayHandle = this.drawable.getNativeSurface().getDisplayHandle();
        if (!EGL.eglMakeCurrent(displayHandle, this.drawable.getHandle(), this.drawableRead.getHandle(), this.contextHandle)) {
            throw new GLException("Error making context " + toHexString(this.contextHandle) + " current on Thread " + GLContext.getThreadName() + " with display " + toHexString(displayHandle) + ", drawableWrite " + toHexString(this.drawable.getHandle()) + ", drawableRead " + toHexString(this.drawableRead.getHandle()) + " - Error code " + toHexString(EGL.eglGetError()) + ", " + this);
        }
    }
    
    @Override
    protected void releaseImpl() throws GLException {
        if (!EGL.eglMakeCurrent(this.drawable.getNativeSurface().getDisplayHandle(), 0L, 0L, 0L)) {
            throw new GLException("Error freeing OpenGL context " + toHexString(this.contextHandle) + ": error code " + toHexString(EGL.eglGetError()));
        }
    }
    
    @Override
    protected void destroyImpl() throws GLException {
        this.destroyContextARBImpl(this.contextHandle);
    }
    
    @Override
    protected void destroyContextARBImpl(final long n) {
        if (!EGL.eglDestroyContext(this.drawable.getNativeSurface().getDisplayHandle(), n)) {
            final int eglGetError = EGL.eglGetError();
            if (12288 != eglGetError) {
                throw new GLException("Error destroying OpenGL context " + toHexString(n) + ": error code " + toHexString(eglGetError));
            }
        }
    }
    
    @Override
    protected long createContextARBImpl(final long n, final boolean b, final int n2, final int n3, final int n4) {
        final EGLGraphicsConfiguration eglGraphicsConfiguration = (EGLGraphicsConfiguration)this.drawable.getNativeSurface().getGraphicsConfiguration();
        final EGLGraphicsDevice eglGraphicsDevice = (EGLGraphicsDevice)eglGraphicsConfiguration.getScreen().getDevice();
        final long handle = eglGraphicsDevice.getHandle();
        final long nativeConfig = eglGraphicsConfiguration.getNativeConfig();
        final EGLDrawableFactory eglDrawableFactory = (EGLDrawableFactory)this.drawable.getFactoryImpl();
        final boolean hasOpenGLDesktopSupport = eglDrawableFactory.hasOpenGLDesktopSupport();
        final boolean hasDefaultDeviceKHRCreateContext = eglDrawableFactory.hasDefaultDeviceKHRCreateContext();
        final boolean b2 = 0x0 == (0x8 & n2);
        final boolean b3 = 0x0 != (0x2 & n2);
        final boolean b4 = 0x0 != (0x10 & n2);
        final boolean b5 = 0x0 != (0x20 & n2);
        if (EGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": EGLContext.createContextARBImpl: Start " + getGLVersion(n3, n4, n2, "@creation") + ", useKHRCreateContext " + hasDefaultDeviceKHRCreateContext + ", OpenGL API Support " + hasOpenGLDesktopSupport + ", device " + eglGraphicsDevice);
        }
        if (0L == handle) {
            throw new GLException("Error: attempted to create an OpenGL context without a display connection");
        }
        if (0L == nativeConfig) {
            throw new GLException("Error: attempted to create an OpenGL context without a graphics configuration");
        }
        if (b2 && !hasOpenGLDesktopSupport) {
            if (EGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": EGLContext.createContextARBImpl: DesktopGL not avail " + getGLVersion(n3, n4, n2, "@creation"));
            }
            return 0L;
        }
        try {
            if (hasOpenGLDesktopSupport && eglGraphicsDevice.getEGLVersion().compareTo(EGLContext.Version1_2) >= 0) {
                EGL.eglWaitClient();
            }
            if (!EGL.eglBindAPI(b2 ? 12450 : 12448)) {
                throw new GLException("Caught: eglBindAPI to " + (b2 ? "ES" : "GL") + " failed , error " + toHexString(EGL.eglGetError()) + " - " + getGLVersion(n3, n4, n2, "@creation"));
            }
        }
        catch (GLException ex) {
            if (EGLContext.DEBUG) {
                ExceptionUtils.dumpThrowable("", ex);
            }
        }
        int n5;
        if (n3 >= 3 && GLRendererQuirks.existStickyDeviceQuirk(GLDrawableFactory.getEGLFactory().getDefaultDevice(), 15)) {
            n5 = 2;
        }
        else {
            n5 = n3;
        }
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(EGLContext.ctx_attribs_rom);
        if (hasDefaultDeviceKHRCreateContext) {
            directIntBuffer.put(1, n5);
            int n6 = 2;
            if (n4 >= 0) {
                directIntBuffer.put(n6 + 0, 12539);
                directIntBuffer.put(n6 + 1, n4);
                n6 += 2;
            }
            if (b2 && (n5 > 3 || (n5 == 3 && n4 >= 2))) {
                directIntBuffer.put(n6 + 0, 12541);
                if (b3) {
                    directIntBuffer.put(n6 + 1, 2);
                }
                else {
                    directIntBuffer.put(n6 + 1, 1);
                }
                n6 += 2;
            }
            int n7 = 0;
            if (b2 && n5 >= 3 && !b3 && b4) {
                n7 |= 0x2;
            }
            if (b5) {
                n7 |= 0x1;
            }
            if (n7 != 0) {
                directIntBuffer.put(n6 + 0, 12540);
                directIntBuffer.put(n6 + 1, n7);
                n6 += 2;
            }
            if (EGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": EGLContext.createContextARBImpl: attrs.1: major " + n5 + ", flags " + toHexString(n7) + ", index " + n6);
            }
        }
        else {
            directIntBuffer.put(1, n5);
            if (EGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": EGLContext.createContextARBImpl: attrs.2: major " + n5);
            }
        }
        long eglCreateContext = 0L;
        try {
            eglCreateContext = EGL.eglCreateContext(handle, nativeConfig, n, directIntBuffer);
        }
        catch (RuntimeException ex2) {
            if (EGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": Info: EGLContext.createContextARBImpl glXCreateContextAttribsARB failed with " + getGLVersion(n3, n4, n2, "@creation"));
                ExceptionUtils.dumpThrowable("", ex2);
            }
        }
        if (0L != eglCreateContext) {
            if (!EGL.eglMakeCurrent(handle, this.drawable.getHandle(), this.drawableRead.getHandle(), eglCreateContext)) {
                if (EGLContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": EGLContext.createContextARBImpl couldn't make current " + getGLVersion(n3, n4, n2, "@creation") + " - error " + toHexString(EGL.eglGetError()));
                }
                EGL.eglMakeCurrent(handle, 0L, 0L, 0L);
                EGL.eglDestroyContext(handle, eglCreateContext);
                eglCreateContext = 0L;
            }
            else if (EGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": EGLContext.createContextARBImpl: OK " + getGLVersion(n3, n4, n2, "@creation") + ", share " + n + ", direct " + b);
            }
        }
        else if (EGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": EGLContext.createContextARBImpl: NO " + getGLVersion(n3, n4, n2, "@creation") + " - error " + toHexString(EGL.eglGetError()));
        }
        return eglCreateContext;
    }
    
    @Override
    protected boolean createImpl(final long n) throws GLException {
        final EGLGraphicsConfiguration eglGraphicsConfiguration = (EGLGraphicsConfiguration)this.drawable.getNativeSurface().getGraphicsConfiguration();
        final AbstractGraphicsDevice device = eglGraphicsConfiguration.getScreen().getDevice();
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)eglGraphicsConfiguration.getChosenCapabilities();
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final boolean createContextARBAvail = this.isCreateContextARBAvail(device);
        if (EGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": EGLContext.createImpl: START " + glCapabilitiesImmutable + ", share " + toHexString(n));
            System.err.println(GLContext.getThreadName() + ": Use ARB[avail[" + this.getCreateContextARBAvailStr(device) + "] -> " + createContextARBAvail + "]]");
        }
        if (createContextARBAvail) {
            this.contextHandle = this.createContextARB(n, true);
            if (EGLContext.DEBUG) {
                if (0L != this.contextHandle) {
                    System.err.println(GLContext.getThreadName() + ": createImpl: OK (ARB) on eglDevice " + device + ", eglConfig " + eglGraphicsConfiguration + ", " + glProfile + ", shareWith " + toHexString(n) + ", error " + toHexString(EGL.eglGetError()));
                }
                else {
                    System.err.println(GLContext.getThreadName() + ": createImpl: NOT OK (ARB) - creation failed on eglDevice " + device + ", eglConfig " + eglGraphicsConfiguration + ", " + glProfile + ", shareWith " + toHexString(n) + ", error " + toHexString(EGL.eglGetError()));
                }
            }
        }
        else {
            this.contextHandle = 0L;
        }
        if (0L == this.contextHandle) {
            if (!glProfile.isGLES()) {
                throw new GLException(GLContext.getThreadName() + ": Unable to create desktop OpenGL context(ARB n/a) on eglDevice " + device + ", eglConfig " + eglGraphicsConfiguration + ", " + glProfile + ", shareWith " + toHexString(n) + ", error " + toHexString(EGL.eglGetError()));
            }
            final int[] array = { 0, 0 };
            GLContext.getRequestMajorAndCompat(glProfile, array);
            final int[] array2 = array;
            final int n2 = 1;
            array2[n2] |= this.getContextCreationFlags();
            this.contextHandle = this.createContextARBImpl(n, true, array[1], array[0], 0);
            if (0L == this.contextHandle) {
                throw new GLException(GLContext.getThreadName() + ": Unable to create ES OpenGL context on eglDevice " + device + ", eglConfig " + eglGraphicsConfiguration + ", " + glProfile + ", shareWith " + toHexString(n) + ", error " + toHexString(EGL.eglGetError()));
            }
            if (!this.setGLFunctionAvailability(true, array[0], 0, array[1], false, false)) {
                EGL.eglMakeCurrent(this.drawable.getNativeSurface().getDisplayHandle(), 0L, 0L, 0L);
                EGL.eglDestroyContext(this.drawable.getNativeSurface().getDisplayHandle(), this.contextHandle);
                this.contextHandle = 0L;
                throw new GLException("setGLFunctionAvailability !strictMatch failed");
            }
        }
        if (EGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": createImpl: Created OpenGL context 0x" + Long.toHexString(this.contextHandle) + ",\n\twrite surface 0x" + Long.toHexString(this.drawable.getHandle()) + ",\n\tread  surface 0x" + Long.toHexString(this.drawableRead.getHandle()) + ",\n\t" + this + ",\n\tsharing with 0x" + Long.toHexString(n));
        }
        return true;
    }
    
    @Override
    protected final void updateGLXProcAddressTable(final String s, final GLDynamicLookupHelper glDynamicLookupHelper) {
        if (null == glDynamicLookupHelper) {
            throw new GLException("No GLDynamicLookupHelper for " + this);
        }
        final String string = "EGL-" + this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice().getUniqueID();
        if (EGLContext.DEBUG) {
            System.err.println(GLContext.getThreadName() + ": Initializing EGLextension address table: " + string);
        }
        ProcAddressTable procAddressTable = null;
        synchronized (EGLContext.mappedContextTypeObjectLock) {
            procAddressTable = EGLContext.mappedGLXProcAddress.get(string);
        }
        if (null != procAddressTable) {
            this.eglExtProcAddressTable = (EGLExtProcAddressTable)procAddressTable;
            if (EGLContext.DEBUG) {
                System.err.println(GLContext.getThreadName() + ": GLContext EGL ProcAddressTable reusing key(" + string + ") -> " + toHexString(procAddressTable.hashCode()));
            }
            if (null == this.eglExtImpl || this.eglExtImpl.getProcAdressTable() != this.eglExtProcAddressTable) {
                this.eglExtImpl = new EGLExtImpl(this, this.eglExtProcAddressTable);
            }
        }
        else {
            this.resetProcAddressTable(this.eglExtProcAddressTable = new EGLExtProcAddressTable(new GLProcAddressResolver()), glDynamicLookupHelper);
            synchronized (EGLContext.mappedContextTypeObjectLock) {
                EGLContext.mappedGLXProcAddress.put(string, this.eglExtProcAddressTable);
                if (EGLContext.DEBUG) {
                    System.err.println(GLContext.getThreadName() + ": GLContext EGL ProcAddressTable mapping key(" + string + ") -> " + toHexString(this.eglExtProcAddressTable.hashCode()));
                }
            }
            this.eglExtImpl = new EGLExtImpl(this, this.eglExtProcAddressTable);
        }
    }
    
    @Override
    protected final StringBuilder getPlatformExtensionsStringImpl() {
        return getPlatformExtensionsStringImpl((EGLGraphicsDevice)this.drawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice());
    }
    
    static final StringBuilder getPlatformExtensionsStringImpl(final EGLGraphicsDevice eglGraphicsDevice) {
        final StringBuilder sb = new StringBuilder();
        eglGraphicsDevice.lock();
        try {
            final long handle = eglGraphicsDevice.getHandle();
            if (EGLContext.DEBUG) {
                System.err.println("EGL PlatformExtensions: Device " + eglGraphicsDevice);
                EGLDrawableFactory.dumpEGLInfo("EGL PlatformExtensions: ", handle);
            }
            if (eglGraphicsDevice.getEGLVersion().compareTo(EGLContext.Version1_5) >= 0) {
                final String eglQueryString = EGL.eglQueryString(0L, 12373);
                if (EGLContext.DEBUG) {
                    System.err.println("EGL extensions (Client): " + eglQueryString);
                }
                sb.append(eglQueryString).append(" ");
            }
            if (0L != handle) {
                final String eglQueryString2 = EGL.eglQueryString(handle, 12373);
                if (EGLContext.DEBUG) {
                    System.err.println("EGL extensions (Server): " + eglQueryString2);
                }
                sb.append(eglQueryString2).append(" ");
            }
        }
        finally {
            eglGraphicsDevice.unlock();
        }
        return sb;
    }
    
    @Override
    protected final Integer setSwapIntervalImpl2(final int n) {
        if (!this.drawable.getChosenGLCapabilities().isOnscreen() || this.hasRendererQuirk(2)) {
            return null;
        }
        int abs;
        if (0 > n) {
            abs = Math.abs(n);
        }
        else {
            abs = n;
        }
        if (EGL.eglSwapInterval(this.drawable.getNativeSurface().getDisplayHandle(), abs)) {
            return abs;
        }
        return null;
    }
    
    static long eglGetProcAddress(final long n, final String s) {
        if (0L == n) {
            throw new GLException("Passed null pointer for method \"eglGetProcAddress\"");
        }
        return dispatch_eglGetProcAddress0(s, n);
    }
    
    private static native long dispatch_eglGetProcAddress0(final String p0, final long p1);
    
    static final boolean isGLES1(final int n, final int n2) {
        return 0x0 != (n2 & 0x8) && n == 1;
    }
    
    static final boolean isGLES2ES3(final int n, final int n2) {
        return 0x0 != (n2 & 0x8) && (2 == n || 3 == n);
    }
    
    static final boolean isGLDesktop(final int n) {
        return 0x0 != (n & 0x6);
    }
    
    protected static StringBuilder getGLProfile(final StringBuilder sb, final int n) {
        return GLContext.getGLProfile(sb, n);
    }
    
    int getContextOptions() {
        return this.ctxOptions;
    }
    
    protected static void remapAvailableGLVersions(final AbstractGraphicsDevice abstractGraphicsDevice, final AbstractGraphicsDevice abstractGraphicsDevice2) {
        GLContextImpl.remapAvailableGLVersions(abstractGraphicsDevice, abstractGraphicsDevice2);
    }
    
    protected static synchronized void setMappedGLVersionListener(final MappedGLVersionListener mappedGLVersionListener) {
        GLContextImpl.setMappedGLVersionListener(mappedGLVersionListener);
    }
    
    protected static String getGLVersion(final int n, final int n2, final int n3, final String s) {
        return GLContext.getGLVersion(n, n2, n3, s);
    }
    
    protected static String toHexString(final int n) {
        return GLContext.toHexString(n);
    }
    
    protected static String toHexString(final long n) {
        return GLContext.toHexString(n);
    }
    
    @Override
    protected void copyImpl(final GLContext glContext, final int n) throws GLException {
        throw new GLException("Not yet implemented");
    }
    
    static {
        ctx_attribs_rom = new int[] { 12440, 0, 12344, 12344, 12344, 12344, 12344, 12344, 12344, 12344, 12344 };
    }
}
