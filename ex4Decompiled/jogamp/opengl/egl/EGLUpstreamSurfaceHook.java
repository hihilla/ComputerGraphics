// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.opengl.egl.EGL;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.WrappedSurface;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.UpstreamSurfaceHook;

public class EGLUpstreamSurfaceHook implements UpstreamSurfaceHook.MutableSize
{
    private static final boolean DEBUG;
    private final NativeSurface upstreamSurface;
    private final UpstreamSurfaceHook.MutableSize upstreamSurfaceHookMutableSize;
    
    public EGLUpstreamSurfaceHook(final NativeSurface upstreamSurface) {
        this.upstreamSurface = upstreamSurface;
        if (this.upstreamSurface instanceof ProxySurface) {
            final UpstreamSurfaceHook upstreamSurfaceHook = ((ProxySurface)this.upstreamSurface).getUpstreamSurfaceHook();
            if (upstreamSurfaceHook instanceof UpstreamSurfaceHook.MutableSize) {
                this.upstreamSurfaceHookMutableSize = (UpstreamSurfaceHook.MutableSize)upstreamSurfaceHook;
            }
            else {
                this.upstreamSurfaceHookMutableSize = null;
            }
        }
        else {
            this.upstreamSurfaceHookMutableSize = null;
        }
    }
    
    public EGLUpstreamSurfaceHook(final EGLGraphicsConfiguration eglGraphicsConfiguration, final long n, final UpstreamSurfaceHook upstreamSurfaceHook, final boolean b) {
        this(new WrappedSurface(eglGraphicsConfiguration, n, upstreamSurfaceHook, b));
    }
    
    static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    @Override
    public final NativeSurface getUpstreamSurface() {
        return this.upstreamSurface;
    }
    
    @Override
    public final void setSurfaceSize(final int n, final int n2) {
        if (null != this.upstreamSurfaceHookMutableSize) {
            this.upstreamSurfaceHookMutableSize.setSurfaceSize(n, n2);
        }
    }
    
    @Override
    public final void create(final ProxySurface proxySurface) {
        String string;
        if (EGLUpstreamSurfaceHook.DEBUG) {
            string = getThreadName() + ": EGLUpstreamSurfaceHook.create( up " + this.upstreamSurface.getClass().getSimpleName() + " -> this " + proxySurface.getClass().getSimpleName() + " ): ";
            System.err.println(string + this);
        }
        else {
            string = null;
        }
        if (this.upstreamSurface instanceof ProxySurface) {
            ((ProxySurface)this.upstreamSurface).createNotify();
        }
        if (1 >= this.upstreamSurface.lockSurface()) {
            throw new GLException("Could not lock: " + this.upstreamSurface);
        }
        try {
            this.evalUpstreamSurface(string, proxySurface);
        }
        finally {
            this.upstreamSurface.unlockSurface();
        }
    }
    
    private final void evalUpstreamSurface(final String s, final ProxySurface proxySurface) {
        int validEGLSurfaceHandle = 1;
        final AbstractGraphicsConfiguration graphicsConfiguration = proxySurface.getGraphicsConfiguration();
        final AbstractGraphicsDevice abstractGraphicsDevice = (null != graphicsConfiguration) ? graphicsConfiguration.getScreen().getDevice() : null;
        if (EGLUpstreamSurfaceHook.DEBUG) {
            System.err.println(s + "SurfaceDevice: " + ((EGLGraphicsDevice)abstractGraphicsDevice).getClass().getSimpleName() + ", hash 0x" + Integer.toHexString(abstractGraphicsDevice.hashCode()) + ", " + abstractGraphicsDevice);
            System.err.println(s + "SurfaceConfig: " + ((EGLGraphicsConfiguration)graphicsConfiguration).getClass().getSimpleName() + ", hash 0x" + Integer.toHexString(graphicsConfiguration.hashCode()) + ", " + graphicsConfiguration);
        }
        final AbstractGraphicsConfiguration graphicsConfiguration2 = this.upstreamSurface.getGraphicsConfiguration();
        final AbstractGraphicsDevice device = graphicsConfiguration2.getScreen().getDevice();
        if (EGLUpstreamSurfaceHook.DEBUG) {
            System.err.println(s + "UpstreamDevice: " + ((EGLGraphicsDevice)device).getClass().getSimpleName() + ", hash 0x" + Integer.toHexString(device.hashCode()) + ", " + device);
            System.err.println(s + "UpstreamConfig: " + ((EGLGraphicsConfiguration)graphicsConfiguration2).getClass().getSimpleName() + ", hash 0x" + Integer.toHexString(graphicsConfiguration2.hashCode()) + ", " + graphicsConfiguration2);
        }
        EGLGraphicsDevice eglCreateEGLGraphicsDevice;
        EGLGraphicsConfiguration eglGraphicsConfiguration;
        if (abstractGraphicsDevice instanceof EGLGraphicsDevice) {
            eglCreateEGLGraphicsDevice = (EGLGraphicsDevice)abstractGraphicsDevice;
            eglGraphicsConfiguration = (EGLGraphicsConfiguration)graphicsConfiguration;
            if (EGLUpstreamSurfaceHook.DEBUG) {
                System.err.println(s + "Reusing this eglDevice: " + eglCreateEGLGraphicsDevice + ", using this config " + eglGraphicsConfiguration.getClass().getSimpleName() + " " + eglGraphicsConfiguration);
            }
            if (0L == eglCreateEGLGraphicsDevice.getHandle()) {
                eglCreateEGLGraphicsDevice.open();
                validEGLSurfaceHandle = 0;
                proxySurface.addUpstreamOptionBits(128);
            }
        }
        else if (device instanceof EGLGraphicsDevice) {
            eglCreateEGLGraphicsDevice = (EGLGraphicsDevice)device;
            eglGraphicsConfiguration = (EGLGraphicsConfiguration)graphicsConfiguration2;
            if (EGLUpstreamSurfaceHook.DEBUG) {
                System.err.println(s + "Reusing upstream eglDevice: " + eglCreateEGLGraphicsDevice + ", using upstream config " + eglGraphicsConfiguration.getClass().getSimpleName() + " " + eglGraphicsConfiguration);
            }
            if (0L == eglCreateEGLGraphicsDevice.getHandle()) {
                eglCreateEGLGraphicsDevice.open();
                validEGLSurfaceHandle = 0;
                proxySurface.addUpstreamOptionBits(128);
            }
        }
        else {
            eglCreateEGLGraphicsDevice = EGLDisplayUtil.eglCreateEGLGraphicsDevice(this.upstreamSurface);
            eglCreateEGLGraphicsDevice.open();
            eglGraphicsConfiguration = (EGLGraphicsConfiguration)graphicsConfiguration2;
            validEGLSurfaceHandle = 0;
            proxySurface.addUpstreamOptionBits(128);
        }
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)eglGraphicsConfiguration.getRequestedCapabilities();
        EGLGraphicsConfiguration chooseGraphicsConfigurationStatic;
        if (eglGraphicsConfiguration instanceof EGLGraphicsConfiguration) {
            final EGLGLCapabilities eglglCapabilities = (EGLGLCapabilities)eglGraphicsConfiguration.getChosenCapabilities();
            if (validEGLSurfaceHandle == 0 || !EGLGraphicsConfiguration.isEGLConfigValid(eglCreateEGLGraphicsDevice.getHandle(), eglglCapabilities.getEGLConfig())) {
                eglglCapabilities.setEGLConfig(EGLGraphicsConfiguration.EGLConfigId2EGLConfig(eglCreateEGLGraphicsDevice.getHandle(), eglglCapabilities.getEGLConfigID()));
                if (0L == eglglCapabilities.getEGLConfig()) {
                    throw new GLException("Refreshing native EGLConfig handle failed with error " + EGLContext.toHexString(EGL.eglGetError()) + ": " + eglCreateEGLGraphicsDevice + ", " + eglglCapabilities + " of " + eglGraphicsConfiguration);
                }
                chooseGraphicsConfigurationStatic = new EGLGraphicsConfiguration(new DefaultGraphicsScreen(eglCreateEGLGraphicsDevice, eglGraphicsConfiguration.getScreen().getIndex()), eglglCapabilities, glCapabilitiesImmutable, null);
                if (EGLUpstreamSurfaceHook.DEBUG) {
                    System.err.println(s + "Refreshing eglConfig: " + chooseGraphicsConfigurationStatic);
                }
                validEGLSurfaceHandle = 0;
            }
            else {
                chooseGraphicsConfigurationStatic = eglGraphicsConfiguration;
                if (EGLUpstreamSurfaceHook.DEBUG) {
                    System.err.println(s + "Reusing eglConfig: " + chooseGraphicsConfigurationStatic);
                }
            }
        }
        else {
            final DefaultGraphicsScreen defaultGraphicsScreen = new DefaultGraphicsScreen(eglCreateEGLGraphicsDevice, eglGraphicsConfiguration.getScreen().getIndex());
            chooseGraphicsConfigurationStatic = EGLGraphicsConfigurationFactory.chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable, null, defaultGraphicsScreen, eglGraphicsConfiguration.getVisualID(VisualIDHolder.VIDType.NATIVE), false);
            if (null == chooseGraphicsConfigurationStatic) {
                throw new GLException("Couldn't create EGLGraphicsConfiguration from " + defaultGraphicsScreen);
            }
            if (EGLUpstreamSurfaceHook.DEBUG) {
                System.err.println(s + "Chosen eglConfig: " + chooseGraphicsConfigurationStatic);
            }
            validEGLSurfaceHandle = 0;
        }
        proxySurface.setGraphicsConfiguration(chooseGraphicsConfigurationStatic);
        if (validEGLSurfaceHandle != 0) {
            validEGLSurfaceHandle = (EGLSurface.isValidEGLSurfaceHandle(eglCreateEGLGraphicsDevice.getHandle(), this.upstreamSurface.getSurfaceHandle()) ? 1 : 0);
        }
        if (validEGLSurfaceHandle != 0) {
            proxySurface.setSurfaceHandle(this.upstreamSurface.getSurfaceHandle());
            proxySurface.clearUpstreamOptionBits(64);
            if (EGLUpstreamSurfaceHook.DEBUG) {
                System.err.println(s + "Fin: Already valid EGL surface - use as-is: " + this.upstreamSurface);
            }
        }
        else {
            proxySurface.setSurfaceHandle(0L);
            proxySurface.addUpstreamOptionBits(64);
            if (EGLUpstreamSurfaceHook.DEBUG) {
                System.err.println(s + "Fin: EGL surface n/a - TBD: " + this.upstreamSurface);
            }
        }
    }
    
    @Override
    public final void destroy(final ProxySurface proxySurface) {
        if (EGLUpstreamSurfaceHook.DEBUG) {
            System.err.println(getThreadName() + ": EGLUpstreamSurfaceHook.destroy(" + proxySurface.getClass().getSimpleName() + "): " + this);
        }
        proxySurface.clearUpstreamOptionBits(64);
        if (this.upstreamSurface instanceof ProxySurface) {
            ((ProxySurface)this.upstreamSurface).destroyNotify();
        }
    }
    
    @Override
    public final int getSurfaceWidth(final ProxySurface proxySurface) {
        return this.upstreamSurface.getSurfaceWidth();
    }
    
    @Override
    public final int getSurfaceHeight(final ProxySurface proxySurface) {
        return this.upstreamSurface.getSurfaceHeight();
    }
    
    @Override
    public String toString() {
        String string;
        int surfaceWidth;
        int surfaceHeight;
        if (null != this.upstreamSurface) {
            string = this.upstreamSurface.getClass().getName() + ": 0x" + Long.toHexString(this.upstreamSurface.getSurfaceHandle());
            surfaceWidth = this.upstreamSurface.getSurfaceWidth();
            surfaceHeight = this.upstreamSurface.getSurfaceHeight();
        }
        else {
            string = "nil";
            surfaceWidth = -1;
            surfaceHeight = -1;
        }
        return "EGLUpstreamSurfaceHook[ " + surfaceWidth + "x" + surfaceHeight + ", " + string + "]";
    }
    
    static {
        DEBUG = EGLDrawableFactory.DEBUG;
    }
}
