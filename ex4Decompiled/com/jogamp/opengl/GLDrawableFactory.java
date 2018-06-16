// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import jogamp.opengl.Debug;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.nativewindow.NativeWindowFactory;
import java.util.ArrayList;

public abstract class GLDrawableFactory
{
    protected static final boolean DEBUG;
    private static volatile boolean isInit;
    private static GLDrawableFactory eglFactory;
    private static GLDrawableFactory nativeOSFactory;
    private static ArrayList<GLDrawableFactory> glDrawableFactories;
    
    public static final void initSingleton() {
        if (!GLDrawableFactory.isInit) {
            synchronized (GLDrawableFactory.class) {
                if (!GLDrawableFactory.isInit) {
                    GLDrawableFactory.isInit = true;
                    initSingletonImpl();
                }
            }
        }
    }
    
    private static final void initSingletonImpl() {
        NativeWindowFactory.initSingleton();
        NativeWindowFactory.addCustomShutdownHook(false, new Runnable() {
            @Override
            public void run() {
                shutdown0();
            }
        });
        final String nativeWindowType = NativeWindowFactory.getNativeWindowType(true);
        GLDrawableFactory glDrawableFactory = null;
        String property = PropertyAccess.getProperty("jogl.gldrawablefactory.class.name", true);
        final ClassLoader classLoader = GLDrawableFactory.class.getClassLoader();
        if (null == property) {
            if (nativeWindowType == NativeWindowFactory.TYPE_X11) {
                property = "jogamp.opengl.x11.glx.X11GLXDrawableFactory";
            }
            else if (nativeWindowType == NativeWindowFactory.TYPE_WINDOWS) {
                property = "jogamp.opengl.windows.wgl.WindowsWGLDrawableFactory";
            }
            else if (nativeWindowType == NativeWindowFactory.TYPE_MACOSX) {
                property = "jogamp.opengl.macosx.cgl.MacOSXCGLDrawableFactory";
            }
            else if (GLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                System.err.println("GLDrawableFactory.static - No native Windowing Factory for: " + nativeWindowType + "; May use EGLDrawableFactory, if available.");
            }
        }
        if (!GLProfile.disableOpenGLDesktop) {
            if (null != property) {
                if (GLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                    System.err.println("GLDrawableFactory.static - Native OS Factory for: " + nativeWindowType + ": " + property);
                }
                try {
                    glDrawableFactory = (GLDrawableFactory)ReflectionUtil.createInstance(property, classLoader);
                }
                catch (Exception ex) {
                    if (GLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                        System.err.println("Info: GLDrawableFactory.static - Native Platform: " + nativeWindowType + " - not available: " + property);
                        ex.printStackTrace();
                    }
                }
                if (null != glDrawableFactory && glDrawableFactory.isComplete()) {
                    GLDrawableFactory.nativeOSFactory = glDrawableFactory;
                }
                glDrawableFactory = null;
            }
            else if (GLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                System.err.println("Info: GLDrawableFactory.static - Desktop GLDrawableFactory unspecified!");
            }
        }
        else if (GLDrawableFactory.DEBUG || GLProfile.DEBUG) {
            System.err.println("Info: GLDrawableFactory.static - Desktop GLDrawableFactory - disabled!");
        }
        if (!GLProfile.disableOpenGLES) {
            try {
                glDrawableFactory = (GLDrawableFactory)ReflectionUtil.createInstance("jogamp.opengl.egl.EGLDrawableFactory", classLoader);
            }
            catch (Exception ex2) {
                if (GLDrawableFactory.DEBUG || GLProfile.DEBUG) {
                    System.err.println("Info: GLDrawableFactory.static - EGLDrawableFactory - not available");
                    ex2.printStackTrace();
                }
            }
            if (null != glDrawableFactory && glDrawableFactory.isComplete()) {
                GLDrawableFactory.eglFactory = glDrawableFactory;
            }
        }
        else if (GLDrawableFactory.DEBUG || GLProfile.DEBUG) {
            System.err.println("Info: GLDrawableFactory.static - EGLDrawableFactory - disabled!");
        }
    }
    
    protected static void shutdown() {
        if (GLDrawableFactory.isInit) {
            synchronized (GLDrawableFactory.class) {
                if (GLDrawableFactory.isInit) {
                    GLDrawableFactory.isInit = false;
                    shutdown0();
                }
            }
        }
    }
    
    private static void shutdown0() {
        synchronized (GLDrawableFactory.glDrawableFactories) {
            final int size = GLDrawableFactory.glDrawableFactories.size();
            if (GLDrawableFactory.DEBUG) {
                System.err.println("GLDrawableFactory.shutdownAll " + size + " instances, on thread " + getThreadName());
            }
            for (int i = 0; i < size; ++i) {
                final GLDrawableFactory glDrawableFactory = GLDrawableFactory.glDrawableFactories.get(i);
                if (GLDrawableFactory.DEBUG) {
                    System.err.println("GLDrawableFactory.shutdownAll[" + (i + 1) + "/" + size + "]:  " + glDrawableFactory.getClass().getName());
                }
                try {
                    glDrawableFactory.resetAllDisplayGammaNoSync();
                    glDrawableFactory.shutdownImpl();
                }
                catch (Throwable t) {
                    System.err.println("GLDrawableFactory.shutdownImpl: Caught " + t.getClass().getName() + " during factory shutdown #" + (i + 1) + "/" + size + " " + glDrawableFactory.getClass().getName());
                    if (GLDrawableFactory.DEBUG) {
                        t.printStackTrace();
                    }
                }
            }
            GLDrawableFactory.glDrawableFactories.clear();
            GLDrawableFactory.nativeOSFactory = null;
            GLDrawableFactory.eglFactory = null;
        }
        GLContext.shutdown();
        if (GLDrawableFactory.DEBUG) {
            System.err.println("GLDrawableFactory.shutdownAll.X on thread " + getThreadName());
        }
    }
    
    protected GLDrawableFactory() {
        synchronized (GLDrawableFactory.glDrawableFactories) {
            GLDrawableFactory.glDrawableFactories.add(this);
        }
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    protected abstract boolean isComplete();
    
    protected void enterThreadCriticalZone() {
    }
    
    protected void leaveThreadCriticalZone() {
    }
    
    protected abstract void shutdownImpl();
    
    public abstract boolean setDisplayGamma(final NativeSurface p0, final float p1, final float p2, final float p3) throws IllegalArgumentException;
    
    public abstract void resetDisplayGamma(final NativeSurface p0);
    
    public abstract void resetAllDisplayGamma();
    
    protected abstract void resetAllDisplayGammaNoSync();
    
    public abstract AbstractGraphicsDevice getDefaultDevice();
    
    public abstract boolean getIsDeviceCompatible(final AbstractGraphicsDevice p0);
    
    protected final AbstractGraphicsDevice validateDevice(AbstractGraphicsDevice defaultDevice) {
        if (null == defaultDevice) {
            defaultDevice = this.getDefaultDevice();
            if (null == defaultDevice) {
                throw new InternalError("no default device available");
            }
            if (GLProfile.DEBUG) {
                System.err.println("Info: " + this.getClass().getSimpleName() + ".validateDevice: using default device : " + defaultDevice);
            }
        }
        if (!this.getIsDeviceCompatible(defaultDevice)) {
            if (GLProfile.DEBUG) {
                System.err.println("Info: " + this.getClass().getSimpleName() + ".validateDevice: device not compatible : " + defaultDevice);
            }
            return null;
        }
        return defaultDevice;
    }
    
    protected abstract Thread getSharedResourceThread();
    
    protected final boolean createSharedResource(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return this.createSharedResourceImpl(abstractGraphicsDevice);
    }
    
    protected abstract boolean createSharedResourceImpl(final AbstractGraphicsDevice p0);
    
    public final boolean hasRendererQuirk(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final int n) {
        final GLRendererQuirks rendererQuirks = this.getRendererQuirks(abstractGraphicsDevice, glProfile);
        return null != rendererQuirks && rendererQuirks.exist(n);
    }
    
    public abstract GLRendererQuirks getRendererQuirks(final AbstractGraphicsDevice p0, final GLProfile p1);
    
    public abstract boolean hasOpenGLDesktopSupport();
    
    public abstract boolean hasOpenGLESSupport();
    
    public static GLDrawableFactory getDesktopFactory() {
        GLProfile.initSingleton();
        return GLDrawableFactory.nativeOSFactory;
    }
    
    public static GLDrawableFactory getEGLFactory() {
        GLProfile.initSingleton();
        return GLDrawableFactory.eglFactory;
    }
    
    public static GLDrawableFactory getFactory(final GLProfile glProfile) throws GLException {
        return getFactoryImpl(glProfile.getImplName());
    }
    
    protected static GLDrawableFactory getFactoryImpl(final String s) throws GLException {
        if (GLProfile.usesNativeGLES(s)) {
            if (null != GLDrawableFactory.eglFactory) {
                return GLDrawableFactory.eglFactory;
            }
        }
        else if (null != GLDrawableFactory.nativeOSFactory) {
            return GLDrawableFactory.nativeOSFactory;
        }
        throw new GLException("No GLDrawableFactory available for profile: " + s);
    }
    
    protected static GLDrawableFactory getFactoryImpl(final AbstractGraphicsDevice abstractGraphicsDevice) throws GLException {
        if (null != GLDrawableFactory.nativeOSFactory && GLDrawableFactory.nativeOSFactory.getIsDeviceCompatible(abstractGraphicsDevice)) {
            return GLDrawableFactory.nativeOSFactory;
        }
        if (null != GLDrawableFactory.eglFactory && GLDrawableFactory.eglFactory.getIsDeviceCompatible(abstractGraphicsDevice)) {
            return GLDrawableFactory.eglFactory;
        }
        throw new GLException("No native platform GLDrawableFactory, nor EGLDrawableFactory available: " + abstractGraphicsDevice);
    }
    
    public final List<GLCapabilitiesImmutable> getAvailableCapabilities(AbstractGraphicsDevice validateDevice) {
        validateDevice = this.validateDevice(validateDevice);
        if (null != validateDevice) {
            return this.getAvailableCapabilitiesImpl(validateDevice);
        }
        return null;
    }
    
    protected abstract List<GLCapabilitiesImmutable> getAvailableCapabilitiesImpl(final AbstractGraphicsDevice p0);
    
    public abstract GLDrawable createGLDrawable(final NativeSurface p0) throws IllegalArgumentException, GLException;
    
    public abstract GLOffscreenAutoDrawable createOffscreenAutoDrawable(final AbstractGraphicsDevice p0, final GLCapabilitiesImmutable p1, final GLCapabilitiesChooser p2, final int p3, final int p4) throws GLException;
    
    public abstract GLAutoDrawable createDummyAutoDrawable(final AbstractGraphicsDevice p0, final boolean p1, final GLCapabilitiesImmutable p2, final GLCapabilitiesChooser p3);
    
    public abstract GLDrawable createOffscreenDrawable(final AbstractGraphicsDevice p0, final GLCapabilitiesImmutable p1, final GLCapabilitiesChooser p2, final int p3, final int p4) throws GLException;
    
    public abstract GLDrawable createDummyDrawable(final AbstractGraphicsDevice p0, final boolean p1, final GLCapabilitiesImmutable p2, final GLCapabilitiesChooser p3);
    
    public abstract ProxySurface createProxySurface(final AbstractGraphicsDevice p0, final int p1, final long p2, final GLCapabilitiesImmutable p3, final GLCapabilitiesChooser p4, final UpstreamSurfaceHook p5);
    
    public abstract boolean canCreateFBO(final AbstractGraphicsDevice p0, final GLProfile p1);
    
    public abstract boolean canCreateGLPbuffer(final AbstractGraphicsDevice p0, final GLProfile p1);
    
    public abstract GLContext createExternalGLContext() throws GLException;
    
    public abstract boolean canCreateExternalGLDrawable(final AbstractGraphicsDevice p0);
    
    public abstract GLDrawable createExternalGLDrawable() throws GLException;
    
    static {
        DEBUG = Debug.debug("GLDrawable");
        GLDrawableFactory.isInit = false;
        GLDrawableFactory.glDrawableFactories = new ArrayList<GLDrawableFactory>();
    }
}
