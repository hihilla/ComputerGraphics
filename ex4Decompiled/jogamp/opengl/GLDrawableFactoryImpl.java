// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.util.Set;
import java.util.Iterator;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.opengl.GLFBODrawable;
import com.jogamp.nativewindow.UpstreamSurfaceHookMutableSize;
import com.jogamp.opengl.GLAutoDrawableDelegate;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.OffscreenLayerSurface;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.DelegatedUpstreamSurfaceHookWithSurfaceSize;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.nativewindow.MutableSurface;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.opengl.GLContext;
import java.util.HashMap;
import java.nio.Buffer;
import java.util.Map;
import com.jogamp.opengl.GLDrawableFactory;

public abstract class GLDrawableFactoryImpl extends GLDrawableFactory
{
    protected static final boolean DEBUG;
    private final Map<DeviceScreenID, Buffer> screen2OrigGammaRamp;
    
    protected GLDrawableFactoryImpl() {
        this.screen2OrigGammaRamp = new HashMap<DeviceScreenID, Buffer>();
    }
    
    protected final boolean probeSurfacelessCtx(final GLContext glContext, final boolean b) {
        final GLDrawable glDrawable = glContext.getGLDrawable();
        final AbstractGraphicsDevice device = glDrawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice();
        final boolean hasRendererQuirk = glContext.hasRendererQuirk(22);
        boolean b2 = false;
        if (!hasRendererQuirk) {
            GLDrawable onscreenDrawableImpl = null;
            try {
                final GLCapabilitiesImmutable requestedGLCapabilities = glDrawable.getRequestedGLCapabilities();
                onscreenDrawableImpl = this.createOnscreenDrawableImpl(this.createSurfacelessImpl(device, true, requestedGLCapabilities, requestedGLCapabilities, null, 64, 64));
                onscreenDrawableImpl.setRealized(true);
                glContext.setGLDrawable(onscreenDrawableImpl, false);
                b2 = true;
                if (b) {
                    glContext.setGLDrawable(glDrawable, false);
                }
            }
            catch (Throwable t) {
                if (GLDrawableFactoryImpl.DEBUG || GLContext.DEBUG) {
                    ExceptionUtils.dumpThrowable("", t);
                }
            }
            finally {
                if (null != onscreenDrawableImpl && b) {
                    onscreenDrawableImpl.setRealized(false);
                }
            }
        }
        if (!hasRendererQuirk && !b2) {
            this.setNoSurfacelessCtxQuirkImpl(device, glContext);
        }
        return b2;
    }
    
    protected final void setNoSurfacelessCtxQuirk(final GLContext glContext) {
        if (!glContext.hasRendererQuirk(22)) {
            this.setNoSurfacelessCtxQuirkImpl(glContext.getGLDrawable().getNativeSurface().getGraphicsConfiguration().getScreen().getDevice(), glContext);
        }
    }
    
    private final void setNoSurfacelessCtxQuirkImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final GLContext glContext) {
        if (GLDrawableFactoryImpl.DEBUG || GLContext.DEBUG) {
            System.err.println("Quirk: " + GLRendererQuirks.toString(22) + " -> " + abstractGraphicsDevice + ": cause: probe");
        }
        final GLRendererQuirks rendererQuirks = glContext.getRendererQuirks();
        if (null != rendererQuirks) {
            rendererQuirks.addQuirk(22);
        }
        GLRendererQuirks.addStickyDeviceQuirk(abstractGraphicsDevice, 22);
    }
    
    protected final SharedResourceRunner.Resource getOrCreateSharedResource(AbstractGraphicsDevice validateDevice) {
        try {
            validateDevice = this.validateDevice(validateDevice);
            if (null != validateDevice) {
                return this.getOrCreateSharedResourceImpl(validateDevice);
            }
        }
        catch (GLException ex) {
            if (GLDrawableFactoryImpl.DEBUG) {
                System.err.println("Caught exception on thread " + GLDrawableFactory.getThreadName());
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    protected abstract SharedResourceRunner.Resource getOrCreateSharedResourceImpl(final AbstractGraphicsDevice p0);
    
    public final GLContext getOrCreateSharedContext(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final SharedResourceRunner.Resource orCreateSharedResource = this.getOrCreateSharedResource(abstractGraphicsDevice);
        if (null != orCreateSharedResource) {
            return orCreateSharedResource.getContext();
        }
        return null;
    }
    
    @Override
    protected final boolean createSharedResourceImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final SharedResourceRunner.Resource orCreateSharedResource = this.getOrCreateSharedResource(abstractGraphicsDevice);
        return null != orCreateSharedResource && orCreateSharedResource.isAvailable();
    }
    
    @Override
    public final GLRendererQuirks getRendererQuirks(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile) {
        final SharedResourceRunner.Resource orCreateSharedResource = this.getOrCreateSharedResource(abstractGraphicsDevice);
        if (null != orCreateSharedResource) {
            return orCreateSharedResource.getRendererQuirks(glProfile);
        }
        return null;
    }
    
    public abstract boolean hasMajorMinorCreateContextARB();
    
    protected final AbstractGraphicsDevice getOrCreateSharedDevice(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final SharedResourceRunner.Resource orCreateSharedResource = this.getOrCreateSharedResource(abstractGraphicsDevice);
        if (null != orCreateSharedResource) {
            return orCreateSharedResource.getDevice();
        }
        return null;
    }
    
    public abstract GLDynamicLookupHelper getGLDynamicLookupHelper(final int p0, final int p1);
    
    @Override
    public final GLDrawable createGLDrawable(final NativeSurface nativeSurface) {
        if (nativeSurface == null) {
            throw new IllegalArgumentException("Null target");
        }
        final MutableGraphicsConfiguration mutableGraphicsConfiguration = (MutableGraphicsConfiguration)nativeSurface.getGraphicsConfiguration();
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)mutableGraphicsConfiguration.getChosenCapabilities();
        final AbstractGraphicsDevice device = mutableGraphicsConfiguration.getScreen().getDevice();
        final boolean fboAvailable = GLContext.isFBOAvailable(device, glCapabilitiesImmutable.getGLProfile());
        Object o = null;
        device.lock();
        try {
            final OffscreenLayerSurface offscreenLayerSurface = NativeWindowFactory.getOffscreenLayerSurface(nativeSurface, true);
            if (null != offscreenLayerSurface) {
                final GLCapabilitiesImmutable fixOffscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOffscreenGLCapabilities(glCapabilitiesImmutable, this, device);
                if (!fixOffscreenGLCapabilities.isFBO() && !fixOffscreenGLCapabilities.isPBuffer()) {
                    throw new GLException("Neither FBO nor Pbuffer is available for " + fixOffscreenGLCapabilities + ", " + nativeSurface);
                }
                mutableGraphicsConfiguration.setChosenCapabilities(fixOffscreenGLCapabilities);
                offscreenLayerSurface.setChosenCapabilities(fixOffscreenGLCapabilities);
                if (GLDrawableFactoryImpl.DEBUG) {
                    System.err.println("GLDrawableFactoryImpl.createGLDrawable -> OnscreenDrawable -> Offscreen-Layer");
                    System.err.println("chosenCaps:    " + glCapabilitiesImmutable);
                    System.err.println("chosenCapsMod: " + fixOffscreenGLCapabilities);
                    System.err.println("OffscreenLayerSurface: **** " + offscreenLayerSurface);
                    System.err.println("Target: **** " + nativeSurface);
                    ExceptionUtils.dumpStack(System.err);
                }
                if (!(nativeSurface instanceof MutableSurface)) {
                    throw new IllegalArgumentException("Passed NativeSurface must implement SurfaceChangeable for offscreen layered surface: " + nativeSurface);
                }
                if (fixOffscreenGLCapabilities.isFBO()) {
                    o = this.createFBODrawableImpl(nativeSurface, fixOffscreenGLCapabilities, 0);
                }
                else {
                    o = this.createOffscreenDrawableImpl(nativeSurface);
                }
            }
            else if (glCapabilitiesImmutable.isOnscreen()) {
                mutableGraphicsConfiguration.setChosenCapabilities(GLGraphicsConfigurationUtil.fixOnscreenGLCapabilities(glCapabilitiesImmutable));
                if (GLDrawableFactoryImpl.DEBUG) {
                    System.err.println("GLDrawableFactoryImpl.createGLDrawable -> OnscreenDrawable: " + nativeSurface);
                }
                o = this.createOnscreenDrawableImpl(nativeSurface);
            }
            else {
                if (GLDrawableFactoryImpl.DEBUG) {
                    System.err.println("GLDrawableFactoryImpl.createGLDrawable -> OffScreenDrawable, FBO chosen / avail, PBuffer: " + glCapabilitiesImmutable.isFBO() + " / " + fboAvailable + ", " + glCapabilitiesImmutable.isPBuffer() + ": " + nativeSurface);
                }
                if (!(nativeSurface instanceof MutableSurface)) {
                    throw new IllegalArgumentException("Passed NativeSurface must implement MutableSurface for offscreen: " + nativeSurface);
                }
                if (glCapabilitiesImmutable.isFBO() && fboAvailable) {
                    final ProxySurface dummySurfaceImpl = this.createDummySurfaceImpl(device, false, new GLCapabilities(glCapabilitiesImmutable.getGLProfile()), (GLCapabilitiesImmutable)mutableGraphicsConfiguration.getRequestedCapabilities(), null, 64, 64);
                    dummySurfaceImpl.setUpstreamSurfaceHook(new DelegatedUpstreamSurfaceHookWithSurfaceSize(dummySurfaceImpl.getUpstreamSurfaceHook(), nativeSurface));
                    o = this.createFBODrawableImpl(dummySurfaceImpl, glCapabilitiesImmutable, 0);
                }
                else {
                    o = this.createOffscreenDrawableImpl(nativeSurface);
                }
            }
        }
        finally {
            device.unlock();
        }
        if (GLDrawableFactoryImpl.DEBUG) {
            System.err.println("GLDrawableFactoryImpl.createGLDrawable: " + o);
        }
        return (GLDrawable)o;
    }
    
    protected abstract GLDrawableImpl createOnscreenDrawableImpl(final NativeSurface p0);
    
    @Override
    public abstract boolean canCreateGLPbuffer(final AbstractGraphicsDevice p0, final GLProfile p1);
    
    @Override
    public final boolean canCreateFBO(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile) {
        final AbstractGraphicsDevice orCreateSharedDevice = this.getOrCreateSharedDevice(abstractGraphicsDevice);
        if (null == orCreateSharedDevice) {
            throw new GLException("No shared device for requested: " + abstractGraphicsDevice);
        }
        return GLContext.isFBOAvailable(orCreateSharedDevice, glProfile);
    }
    
    @Override
    public final GLOffscreenAutoDrawable createOffscreenAutoDrawable(final AbstractGraphicsDevice abstractGraphicsDevice, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        final GLDrawable offscreenDrawable = this.createOffscreenDrawable(abstractGraphicsDevice, glCapabilitiesImmutable, glCapabilitiesChooser, n, n2);
        try {
            offscreenDrawable.setRealized(true);
        }
        catch (GLException ex) {
            try {
                offscreenDrawable.setRealized(false);
            }
            catch (GLException ex2) {}
            throw ex;
        }
        if (offscreenDrawable instanceof GLFBODrawableImpl) {
            return new GLOffscreenAutoDrawableImpl.FBOImpl((GLFBODrawableImpl)offscreenDrawable, null, null, null);
        }
        return new GLOffscreenAutoDrawableImpl(offscreenDrawable, null, null, null);
    }
    
    @Override
    public final GLAutoDrawable createDummyAutoDrawable(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser) {
        final GLDrawable dummyDrawable = this.createDummyDrawable(abstractGraphicsDevice, b, glCapabilitiesImmutable, glCapabilitiesChooser);
        try {
            dummyDrawable.setRealized(true);
        }
        catch (GLException ex) {
            try {
                dummyDrawable.setRealized(false);
            }
            catch (GLException ex2) {}
            throw ex;
        }
        return new GLAutoDrawableDelegate(dummyDrawable, null, null, true, null) {};
    }
    
    @Override
    public final GLDrawable createOffscreenDrawable(final AbstractGraphicsDevice abstractGraphicsDevice, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        if (n <= 0 || n2 <= 0) {
            throw new GLException("initial size must be positive (were (" + n + " x " + n2 + "))");
        }
        final SharedResourceRunner.Resource orCreateSharedResource = this.getOrCreateSharedResource(abstractGraphicsDevice);
        if (null == orCreateSharedResource) {
            throw new GLException("No shared device for requested: " + abstractGraphicsDevice);
        }
        final AbstractGraphicsDevice device = orCreateSharedResource.getDevice();
        final GLCapabilitiesImmutable fixOffscreenGLCapabilities = GLGraphicsConfigurationUtil.fixOffscreenGLCapabilities(glCapabilitiesImmutable, this, device);
        if (fixOffscreenGLCapabilities.isFBO()) {
            final GLProfile glProfile = fixOffscreenGLCapabilities.getGLProfile();
            final GLCapabilities glCapabilities = new GLCapabilities(glProfile);
            final GLRendererQuirks rendererQuirks = orCreateSharedResource.getRendererQuirks(glProfile);
            ProxySurface proxySurface;
            if (null != rendererQuirks && !rendererQuirks.exist(22)) {
                proxySurface = this.createSurfacelessImpl(device, true, glCapabilities, glCapabilitiesImmutable, null, n, n2);
            }
            else {
                proxySurface = this.createDummySurfaceImpl(device, true, glCapabilities, glCapabilitiesImmutable, null, n, n2);
            }
            return new GLFBODrawableImpl.ResizeableImpl(this, this.createOnscreenDrawableImpl(proxySurface), proxySurface, fixOffscreenGLCapabilities, 0);
        }
        return this.createOffscreenDrawableImpl(this.createMutableSurfaceImpl(device, true, fixOffscreenGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, new UpstreamSurfaceHookMutableSize(n, n2)));
    }
    
    @Override
    public final GLDrawable createDummyDrawable(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser) {
        final AbstractGraphicsDevice abstractGraphicsDevice2 = b ? this.getOrCreateSharedDevice(abstractGraphicsDevice) : abstractGraphicsDevice;
        if (null == abstractGraphicsDevice2) {
            throw new GLException("No shared device for requested: " + abstractGraphicsDevice + ", createNewDevice " + b);
        }
        if (!b) {
            abstractGraphicsDevice2.lock();
        }
        try {
            return this.createOnscreenDrawableImpl(this.createDummySurfaceImpl(abstractGraphicsDevice2, b, glCapabilitiesImmutable, glCapabilitiesImmutable, glCapabilitiesChooser, 64, 64));
        }
        finally {
            if (!b) {
                abstractGraphicsDevice2.unlock();
            }
        }
    }
    
    protected final GLFBODrawable createFBODrawableImpl(final NativeSurface nativeSurface, final GLCapabilitiesImmutable glCapabilitiesImmutable, final int n) {
        return new GLFBODrawableImpl(this, this.createOnscreenDrawableImpl(nativeSurface), nativeSurface, glCapabilitiesImmutable, n);
    }
    
    protected abstract GLDrawableImpl createOffscreenDrawableImpl(final NativeSurface p0);
    
    protected abstract ProxySurface createMutableSurfaceImpl(final AbstractGraphicsDevice p0, final boolean p1, final GLCapabilitiesImmutable p2, final GLCapabilitiesImmutable p3, final GLCapabilitiesChooser p4, final UpstreamSurfaceHook p5);
    
    public final ProxySurface createDummySurface(final AbstractGraphicsDevice abstractGraphicsDevice, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final int n, final int n2) {
        final AbstractGraphicsDevice orCreateSharedDevice = this.getOrCreateSharedDevice(abstractGraphicsDevice);
        if (null == orCreateSharedDevice) {
            throw new GLException("No shared device for requested: " + abstractGraphicsDevice);
        }
        return this.createDummySurfaceImpl(orCreateSharedDevice, true, glCapabilitiesImmutable, glCapabilitiesImmutable, glCapabilitiesChooser, n, n2);
    }
    
    public abstract ProxySurface createDummySurfaceImpl(final AbstractGraphicsDevice p0, final boolean p1, final GLCapabilitiesImmutable p2, final GLCapabilitiesImmutable p3, final GLCapabilitiesChooser p4, final int p5, final int p6);
    
    public abstract ProxySurface createSurfacelessImpl(final AbstractGraphicsDevice p0, final boolean p1, final GLCapabilitiesImmutable p2, final GLCapabilitiesImmutable p3, final GLCapabilitiesChooser p4, final int p5, final int p6);
    
    @Override
    public ProxySurface createProxySurface(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final long n2, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final UpstreamSurfaceHook upstreamSurfaceHook) {
        final AbstractGraphicsDevice orCreateSharedDevice = this.getOrCreateSharedDevice(abstractGraphicsDevice);
        if (null == orCreateSharedDevice) {
            throw new GLException("No shared device for requested: " + abstractGraphicsDevice);
        }
        if (0L == n2) {
            throw new IllegalArgumentException("Null windowHandle");
        }
        orCreateSharedDevice.lock();
        try {
            return this.createProxySurfaceImpl(orCreateSharedDevice, n, n2, glCapabilitiesImmutable, glCapabilitiesChooser, upstreamSurfaceHook);
        }
        finally {
            orCreateSharedDevice.unlock();
        }
    }
    
    protected abstract ProxySurface createProxySurfaceImpl(final AbstractGraphicsDevice p0, final int p1, final long p2, final GLCapabilitiesImmutable p3, final GLCapabilitiesChooser p4, final UpstreamSurfaceHook p5);
    
    protected abstract GLContext createExternalGLContextImpl();
    
    @Override
    public GLContext createExternalGLContext() {
        return this.createExternalGLContextImpl();
    }
    
    protected abstract GLDrawable createExternalGLDrawableImpl();
    
    @Override
    public GLDrawable createExternalGLDrawable() {
        return this.createExternalGLDrawableImpl();
    }
    
    public static GLDrawableFactoryImpl getFactoryImpl(final GLProfile glProfile) {
        return (GLDrawableFactoryImpl)GLDrawableFactory.getFactory(glProfile);
    }
    
    @Override
    public final synchronized boolean setDisplayGamma(final NativeSurface nativeSurface, final float n, final float n2, final float n3) throws IllegalArgumentException {
        if (n2 < -1.0f || n2 > 1.0f) {
            throw new IllegalArgumentException("Brightness must be between -1.0 and 1.0");
        }
        if (n3 < 0.0f) {
            throw new IllegalArgumentException("Contrast must be greater than 0.0");
        }
        if (1 >= nativeSurface.lockSurface()) {
            return false;
        }
        try {
            final int gammaRampLength = this.getGammaRampLength(nativeSurface);
            if (gammaRampLength == 0) {
                return false;
            }
            final float[] array = new float[gammaRampLength];
            for (int i = 0; i < gammaRampLength; ++i) {
                float n4 = ((float)Math.pow(i / (gammaRampLength - 1), n) + n2 - 0.5f) * n3 + 0.5f;
                if (n4 > 1.0f) {
                    n4 = 1.0f;
                }
                else if (n4 < 0.0f) {
                    n4 = 0.0f;
                }
                array[i] = n4;
            }
            final AbstractGraphicsScreen screen = nativeSurface.getGraphicsConfiguration().getScreen();
            final DeviceScreenID deviceScreenID = new DeviceScreenID(screen.getDevice().getConnection(), screen.getIndex());
            if (null == this.screen2OrigGammaRamp.get(deviceScreenID)) {
                this.screen2OrigGammaRamp.put(deviceScreenID, this.getGammaRamp(nativeSurface));
                if (GLDrawableFactoryImpl.DEBUG) {
                    System.err.println("DisplayGamma: Stored: " + deviceScreenID);
                    this.dumpGammaStore();
                }
            }
            return this.setGammaRamp(nativeSurface, array);
        }
        finally {
            nativeSurface.unlockSurface();
        }
    }
    
    @Override
    public final synchronized void resetDisplayGamma(final NativeSurface nativeSurface) {
        if (1 >= nativeSurface.lockSurface()) {
            return;
        }
        try {
            final AbstractGraphicsScreen screen = nativeSurface.getGraphicsConfiguration().getScreen();
            final Buffer buffer = this.screen2OrigGammaRamp.remove(new DeviceScreenID(screen.getDevice().getConnection(), screen.getIndex()));
            if (null != buffer) {
                this.resetGammaRamp(nativeSurface, buffer);
            }
        }
        finally {
            nativeSurface.unlockSurface();
        }
    }
    
    @Override
    public final synchronized void resetAllDisplayGamma() {
        this.resetAllDisplayGammaNoSync();
    }
    
    @Override
    protected final void resetAllDisplayGammaNoSync() {
        if (GLDrawableFactoryImpl.DEBUG) {
            System.err.println("DisplayGamma: Reset");
            this.dumpGammaStore();
        }
        for (final DeviceScreenID deviceScreenID : this.screen2OrigGammaRamp.keySet()) {
            final Buffer buffer = this.screen2OrigGammaRamp.remove(deviceScreenID);
            if (null != buffer) {
                this.resetGammaRamp(deviceScreenID, buffer);
            }
        }
    }
    
    private void dumpGammaStore() {
        final Set<DeviceScreenID> keySet = this.screen2OrigGammaRamp.keySet();
        int n = 0;
        for (final DeviceScreenID deviceScreenID : keySet) {
            System.err.printf("%4d/%4d: %s -> %s%n", n, keySet.size(), deviceScreenID, this.screen2OrigGammaRamp.get(deviceScreenID));
            ++n;
        }
    }
    
    protected int getGammaRampLength(final NativeSurface nativeSurface) {
        return 0;
    }
    
    protected boolean setGammaRamp(final NativeSurface nativeSurface, final float[] array) {
        return false;
    }
    
    protected Buffer getGammaRamp(final NativeSurface nativeSurface) {
        return null;
    }
    
    protected void resetGammaRamp(final NativeSurface nativeSurface, final Buffer buffer) {
    }
    
    protected void resetGammaRamp(final DeviceScreenID deviceScreenID, final Buffer buffer) {
    }
    
    static {
        DEBUG = GLDrawableFactory.DEBUG;
    }
    
    public final class DeviceScreenID
    {
        public final String deviceConnection;
        public final int screenIdx;
        
        DeviceScreenID(final String deviceConnection, final int screenIdx) {
            this.deviceConnection = deviceConnection;
            this.screenIdx = screenIdx;
        }
        
        @Override
        public int hashCode() {
            final int n = 31 + this.deviceConnection.hashCode();
            return (n << 5) - n + this.screenIdx;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof DeviceScreenID) {
                final DeviceScreenID deviceScreenID = (DeviceScreenID)o;
                return this.deviceConnection.equals(deviceScreenID.deviceConnection) && this.screenIdx == deviceScreenID.screenIdx;
            }
            return false;
        }
        
        @Override
        public String toString() {
            return "DeviceScreenID[devCon " + this.deviceConnection + ", screenIdx " + this.screenIdx + ", hash 0x" + Integer.toHexString(this.hashCode()) + "]";
        }
    }
}
