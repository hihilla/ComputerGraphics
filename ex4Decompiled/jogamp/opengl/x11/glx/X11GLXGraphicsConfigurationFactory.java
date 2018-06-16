// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.DefaultGLCapabilitiesChooser;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLCapabilities;
import jogamp.nativewindow.x11.X11Lib;
import jogamp.nativewindow.x11.XVisualInfo;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import com.jogamp.common.nio.Buffers;
import java.util.Comparator;
import java.util.Collections;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.VisualIDHolder;
import jogamp.opengl.GLGraphicsConfigurationFactory;

public class X11GLXGraphicsConfigurationFactory extends GLGraphicsConfigurationFactory
{
    static VisualIDHolder.VIDComparator XVisualIDComparator;
    static GraphicsConfigurationFactory fallbackX11GraphicsConfigurationFactory;
    
    static void registerFactory() {
        final X11GLXGraphicsConfigurationFactory x11GLXGraphicsConfigurationFactory = new X11GLXGraphicsConfigurationFactory();
        final GraphicsConfigurationFactory registerFactory = GraphicsConfigurationFactory.registerFactory(X11GraphicsDevice.class, GLCapabilitiesImmutable.class, x11GLXGraphicsConfigurationFactory);
        if (registerFactory == x11GLXGraphicsConfigurationFactory) {
            throw new InternalError("GraphicsConfigurationFactory lifecycle impl. error");
        }
        if (null != registerFactory) {
            X11GLXGraphicsConfigurationFactory.fallbackX11GraphicsConfigurationFactory = registerFactory;
        }
        else {
            X11GLXGraphicsConfigurationFactory.fallbackX11GraphicsConfigurationFactory = GraphicsConfigurationFactory.getFactory(X11GraphicsDevice.class, CapabilitiesImmutable.class);
            if (null == X11GLXGraphicsConfigurationFactory.fallbackX11GraphicsConfigurationFactory) {
                throw new InternalError("Missing fallback GraphicsConfigurationFactory");
            }
        }
    }
    
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) {
        if (!(abstractGraphicsScreen instanceof X11GraphicsScreen)) {
            throw new IllegalArgumentException("Only X11GraphicsScreen are allowed here");
        }
        if (!(capabilitiesImmutable instanceof GLCapabilitiesImmutable)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilities objects - chosen");
        }
        if (!(capabilitiesImmutable2 instanceof GLCapabilitiesImmutable)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilities objects - requested");
        }
        if (capabilitiesChooser != null && !(capabilitiesChooser instanceof GLCapabilitiesChooser)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilitiesChooser objects");
        }
        if (GLXUtil.isGLXAvailableOnServer((X11GraphicsDevice)abstractGraphicsScreen.getDevice())) {
            return chooseGraphicsConfigurationStatic((GLCapabilitiesImmutable)capabilitiesImmutable, (GLCapabilitiesImmutable)capabilitiesImmutable2, (GLCapabilitiesChooser)capabilitiesChooser, (X11GraphicsScreen)abstractGraphicsScreen, n);
        }
        if (null != X11GLXGraphicsConfigurationFactory.fallbackX11GraphicsConfigurationFactory) {
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("No GLX available, fallback to " + X11GLXGraphicsConfigurationFactory.fallbackX11GraphicsConfigurationFactory.getClass().getSimpleName() + " for: " + abstractGraphicsScreen);
            }
            return X11GLXGraphicsConfigurationFactory.fallbackX11GraphicsConfigurationFactory.chooseGraphicsConfiguration(capabilitiesImmutable, capabilitiesImmutable2, capabilitiesChooser, abstractGraphicsScreen, 0);
        }
        throw new InternalError("No GLX and no fallback GraphicsConfigurationFactory available for: " + abstractGraphicsScreen);
    }
    
    protected static List<GLCapabilitiesImmutable> getAvailableCapabilities(final X11GLXDrawableFactory x11GLXDrawableFactory, final AbstractGraphicsDevice abstractGraphicsDevice) {
        final X11GLXDrawableFactory.SharedResource orCreateSharedResourceImpl = x11GLXDrawableFactory.getOrCreateSharedResourceImpl(abstractGraphicsDevice);
        if (null == orCreateSharedResourceImpl) {
            throw new GLException("Shared resource for device n/a: " + abstractGraphicsDevice);
        }
        final X11GraphicsScreen x11GraphicsScreen = (X11GraphicsScreen)orCreateSharedResourceImpl.getScreen();
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        final boolean glxMultisampleAvailable = orCreateSharedResourceImpl.isGLXMultisampleAvailable();
        final GLProfile default1 = GLProfile.getDefault(abstractGraphicsDevice);
        Object o = null;
        x11GraphicsDevice.lock();
        try {
            if (orCreateSharedResourceImpl.isGLXVersionGreaterEqualOneThree()) {
                o = getAvailableGLCapabilitiesFBConfig(x11GraphicsScreen, default1, glxMultisampleAvailable);
            }
            if (null == o || ((List)o).isEmpty()) {
                o = getAvailableGLCapabilitiesXVisual(x11GraphicsScreen, default1, glxMultisampleAvailable);
            }
        }
        finally {
            x11GraphicsDevice.unlock();
        }
        if (null != o && ((List)o).size() > 1) {
            Collections.sort((List<Object>)o, (Comparator<? super Object>)X11GLXGraphicsConfigurationFactory.XVisualIDComparator);
        }
        return (List<GLCapabilitiesImmutable>)o;
    }
    
    static List<GLCapabilitiesImmutable> getAvailableGLCapabilitiesFBConfig(final X11GraphicsScreen x11GraphicsScreen, final GLProfile glProfile, final boolean b) {
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        final long handle = x11GraphicsDevice.getHandle();
        final int index = x11GraphicsScreen.getIndex();
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        directIntBuffer.put(0, -1);
        final ArrayList<X11GLCapabilities> list = (ArrayList<X11GLCapabilities>)new ArrayList<GLCapabilitiesImmutable>();
        final PointerBuffer glXChooseFBConfig = GLX.glXChooseFBConfig(handle, index, null, directIntBuffer);
        if (glXChooseFBConfig == null || glXChooseFBConfig.limit() <= 0) {
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.getAvailableGLCapabilitiesFBConfig: Failed glXChooseFBConfig (" + x11GraphicsScreen + "): " + glXChooseFBConfig + ", " + directIntBuffer.get(0));
            }
            return null;
        }
        for (int i = 0; i < glXChooseFBConfig.limit(); ++i) {
            final X11GLCapabilities glxfbConfig2GLCapabilities = X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities(x11GraphicsDevice, glProfile, glXChooseFBConfig.get(i), 15, b);
            if (null != glxfbConfig2GLCapabilities) {
                list.add(glxfbConfig2GLCapabilities);
            }
            else if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.getAvailableGLCapabilitiesFBConfig: FBConfig invalid (2): (" + x11GraphicsScreen + "): fbcfg: " + GraphicsConfigurationFactory.toHexString(glXChooseFBConfig.get(i)));
            }
        }
        return (List<GLCapabilitiesImmutable>)list;
    }
    
    static List<GLCapabilitiesImmutable> getAvailableGLCapabilitiesXVisual(final X11GraphicsScreen x11GraphicsScreen, final GLProfile glProfile, final boolean b) {
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        final long handle = x11GraphicsDevice.getHandle();
        final int index = x11GraphicsScreen.getIndex();
        final int[] array = { 0 };
        final XVisualInfo create = XVisualInfo.create();
        create.setScreen(index);
        final XVisualInfo[] xGetVisualInfo = X11Lib.XGetVisualInfo(handle, 2L, create, array, 0);
        if (xGetVisualInfo == null || xGetVisualInfo.length < 1) {
            throw new GLException("Error while enumerating available XVisualInfos");
        }
        final ArrayList<GLCapabilitiesImmutable> list = new ArrayList<GLCapabilitiesImmutable>();
        for (int i = 0; i < xGetVisualInfo.length; ++i) {
            final X11GLCapabilities xVisualInfo2GLCapabilities = X11GLXGraphicsConfiguration.XVisualInfo2GLCapabilities(x11GraphicsDevice, glProfile, xGetVisualInfo[i], 15, b);
            if (null != xVisualInfo2GLCapabilities) {
                list.add(xVisualInfo2GLCapabilities);
            }
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.getAvailableGLCapabilitiesXVisual: XVisual invalid: (" + x11GraphicsScreen + "): fbcfg: " + GraphicsConfigurationFactory.toHexString(xGetVisualInfo[i].getVisualid()));
            }
        }
        return list;
    }
    
    static X11GLXGraphicsConfiguration chooseGraphicsConfigurationStatic(GLCapabilitiesImmutable fixGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final X11GraphicsScreen x11GraphicsScreen, final int n) {
        if (x11GraphicsScreen == null) {
            throw new IllegalArgumentException("AbstractGraphicsScreen is null");
        }
        if (fixGLCapabilities == null) {
            fixGLCapabilities = new GLCapabilities(null);
        }
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        final X11GLXDrawableFactory x11GLXDrawableFactory = (X11GLXDrawableFactory)GLDrawableFactory.getDesktopFactory();
        fixGLCapabilities = GLGraphicsConfigurationUtil.fixGLCapabilities(fixGLCapabilities, x11GLXDrawableFactory, x11GraphicsDevice);
        final boolean b = !fixGLCapabilities.isOnscreen() && fixGLCapabilities.isPBuffer();
        X11GLXGraphicsConfiguration x11GLXGraphicsConfiguration = null;
        x11GraphicsDevice.lock();
        try {
            if (x11GLXDrawableFactory.isGLXVersionGreaterEqualOneThree(x11GraphicsDevice)) {
                x11GLXGraphicsConfiguration = chooseGraphicsConfigurationFBConfig(fixGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, x11GraphicsScreen, n);
            }
            if (null == x11GLXGraphicsConfiguration) {
                if (b) {
                    throw new GLException("Error: Couldn't create X11GLXGraphicsConfiguration based on FBConfig for visualID " + GraphicsConfigurationFactory.toHexString(n) + ", " + fixGLCapabilities);
                }
                x11GLXGraphicsConfiguration = chooseGraphicsConfigurationXVisual(fixGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, x11GraphicsScreen, n);
            }
        }
        finally {
            x11GraphicsDevice.unlock();
        }
        if (null == x11GLXGraphicsConfiguration) {
            throw new GLException("Error: Couldn't create X11GLXGraphicsConfiguration based on FBConfig and XVisual for visualID " + GraphicsConfigurationFactory.toHexString(n) + ", " + x11GraphicsScreen + ", " + fixGLCapabilities);
        }
        if (X11GLXGraphicsConfigurationFactory.DEBUG) {
            System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationStatic(visualID " + GraphicsConfigurationFactory.toHexString(n) + ", " + x11GraphicsScreen + "," + fixGLCapabilities + "): " + x11GLXGraphicsConfiguration);
        }
        return x11GLXGraphicsConfiguration;
    }
    
    static X11GLXGraphicsConfiguration fetchGraphicsConfigurationFBConfig(final X11GraphicsScreen x11GraphicsScreen, final int n, final GLProfile glProfile) {
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        final long handle = x11GraphicsDevice.getHandle();
        final long glXFBConfigID2FBConfig = X11GLXGraphicsConfiguration.glXFBConfigID2FBConfig(handle, x11GraphicsScreen.getIndex(), n);
        if (0L == glXFBConfigID2FBConfig || !X11GLXGraphicsConfiguration.GLXFBConfigValid(handle, glXFBConfigID2FBConfig)) {
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationFBConfig: Failed.0 - GLX FBConfig invalid: (" + x11GraphicsScreen + "," + GraphicsConfigurationFactory.toHexString(n) + "): fbcfg: " + GraphicsConfigurationFactory.toHexString(glXFBConfigID2FBConfig));
            }
            return null;
        }
        final X11GLCapabilities glxfbConfig2GLCapabilities = X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities(x11GraphicsDevice, glProfile, glXFBConfigID2FBConfig, 15, ((X11GLXDrawableFactory)GLDrawableFactory.getDesktopFactory()).isGLXMultisampleAvailable(x11GraphicsDevice));
        if (null == glxfbConfig2GLCapabilities) {
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationFBConfig: Failed.1 - GLX FBConfig invalid: (" + x11GraphicsScreen + "," + GraphicsConfigurationFactory.toHexString(n) + "): fbcfg: " + GraphicsConfigurationFactory.toHexString(glXFBConfigID2FBConfig));
            }
            return null;
        }
        return new X11GLXGraphicsConfiguration(x11GraphicsScreen, glxfbConfig2GLCapabilities, glxfbConfig2GLCapabilities, new DefaultGLCapabilitiesChooser());
    }
    
    private static X11GLXGraphicsConfiguration chooseGraphicsConfigurationFBConfig(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser glCapabilitiesChooser, final X11GraphicsScreen x11GraphicsScreen, final int n) {
        int n2 = -1;
        PointerBuffer glXChooseFBConfig = null;
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        final long handle = x11GraphicsDevice.getHandle();
        final int index = x11GraphicsScreen.getIndex();
        final boolean glxMultisampleAvailable = ((X11GLXDrawableFactory)GLDrawableFactory.getDesktopFactory()).isGLXMultisampleAvailable(x11GraphicsDevice);
        final IntBuffer glCapabilities2AttribList = X11GLXGraphicsConfiguration.GLCapabilities2AttribList(glCapabilitiesImmutable, true, glxMultisampleAvailable, handle, index);
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        directIntBuffer.put(0, -1);
        final int exclusiveWinAttributeBits = GLGraphicsConfigurationUtil.getExclusiveWinAttributeBits(glCapabilitiesImmutable);
        boolean b;
        if (0 == n) {
            glXChooseFBConfig = GLX.glXChooseFBConfig(handle, index, glCapabilities2AttribList, directIntBuffer);
            b = (glXChooseFBConfig != null && glXChooseFBConfig.limit() > 0);
        }
        else {
            b = false;
        }
        final boolean b2 = b && glCapabilitiesImmutable.isBackgroundOpaque();
        final boolean b3 = null == glCapabilitiesChooser && b2;
        Object o;
        if (b) {
            o = X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities(x11GraphicsDevice, glProfile, glXChooseFBConfig, exclusiveWinAttributeBits, glxMultisampleAvailable, b3);
            if (((List)o).size() > 0) {
                n2 = (b2 ? 0 : -1);
                if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("glXChooseFBConfig recommended fbcfg " + GraphicsConfigurationFactory.toHexString(glXChooseFBConfig.get(0)) + ", idx " + n2);
                    System.err.println("useRecommendedIndex " + b2 + ", skipCapsChooser " + b3);
                    System.err.println("user  caps " + glCapabilitiesImmutable);
                    System.err.println("fbcfg caps " + glXChooseFBConfig.limit() + ", availCaps " + ((List<GLCapabilitiesImmutable>)o).get(0));
                }
            }
            else if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("glXChooseFBConfig no caps for recommended fbcfg " + GraphicsConfigurationFactory.toHexString(glXChooseFBConfig.get(0)));
                System.err.println("useRecommendedIndex " + b2 + ", skipCapsChooser " + b3);
                System.err.println("user  caps " + glCapabilitiesImmutable);
            }
        }
        else {
            o = new ArrayList<GLCapabilitiesImmutable>();
        }
        if (0 == ((List)o).size()) {
            n2 = -1;
            final PointerBuffer glXChooseFBConfig2 = GLX.glXChooseFBConfig(handle, index, null, directIntBuffer);
            if (glXChooseFBConfig2 == null || glXChooseFBConfig2.limit() <= 0) {
                if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationFBConfig: Failed glXChooseFBConfig (" + x11GraphicsScreen + "," + glCapabilitiesImmutable + "): " + glXChooseFBConfig2 + ", " + directIntBuffer.get(0));
                }
                return null;
            }
            o = X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities(x11GraphicsDevice, glProfile, glXChooseFBConfig2, exclusiveWinAttributeBits, glxMultisampleAvailable, false);
        }
        if (X11GLXGraphicsConfigurationFactory.DEBUG) {
            System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationFBConfig: got configs: " + ((List)o).size());
            for (int i = 0; i < ((List)o).size(); ++i) {
                System.err.println(i + ": " + ((List<X11GLCapabilities>)o).get(i));
            }
        }
        if (0 != n) {
            int j = 0;
            while (j < ((List)o).size()) {
                if (((List<X11GLCapabilities>)o).get(j).getVisualID(VisualIDHolder.VIDType.X11_XVISUAL) != n) {
                    ((List<X11GLCapabilities>)o).remove(j);
                }
                else {
                    ++j;
                }
            }
            if (0 == ((List)o).size()) {
                if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationFBConfig: post filter visualID " + GraphicsConfigurationFactory.toHexString(n) + " no config found, failed - return null");
                }
                return null;
            }
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationFBConfig: post filter visualID " + GraphicsConfigurationFactory.toHexString(n) + " got configs: " + ((List)o).size());
            }
        }
        int chooseCapabilities;
        if (b3 && 0 <= n2) {
            chooseCapabilities = n2;
        }
        else {
            chooseCapabilities = GLGraphicsConfigurationFactory.chooseCapabilities(glCapabilitiesChooser, glCapabilitiesImmutable, (List<? extends CapabilitiesImmutable>)o, n2);
        }
        if (0 > chooseCapabilities) {
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationFBConfig: failed, return null");
                ExceptionUtils.dumpStack(System.err);
            }
            return null;
        }
        return new X11GLXGraphicsConfiguration(x11GraphicsScreen, ((List<X11GLCapabilities>)o).get(chooseCapabilities), glCapabilitiesImmutable2, glCapabilitiesChooser);
    }
    
    private static X11GLXGraphicsConfiguration chooseGraphicsConfigurationXVisual(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, GLCapabilitiesChooser glCapabilitiesChooser, final X11GraphicsScreen x11GraphicsScreen, final int n) {
        if (glCapabilitiesChooser == null) {
            glCapabilitiesChooser = new DefaultGLCapabilitiesChooser();
        }
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final int exclusiveWinAttributeBits = GLGraphicsConfigurationUtil.getExclusiveWinAttributeBits(glCapabilitiesImmutable.isOnscreen(), glCapabilitiesImmutable.isFBO(), false, glCapabilitiesImmutable.isBitmap());
        final ArrayList<X11GLCapabilities> list = new ArrayList<X11GLCapabilities>();
        int n2 = -1;
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        final long handle = x11GraphicsDevice.getHandle();
        final int index = x11GraphicsScreen.getIndex();
        final boolean glxMultisampleAvailable = ((X11GLXDrawableFactory)GLDrawableFactory.getDesktopFactory()).isGLXMultisampleAvailable(x11GraphicsDevice);
        final IntBuffer glCapabilities2AttribList = X11GLXGraphicsConfiguration.GLCapabilities2AttribList(glCapabilitiesImmutable, false, glxMultisampleAvailable, handle, index);
        XVisualInfo glXChooseVisual = null;
        if (0 == n) {
            glXChooseVisual = GLX.glXChooseVisual(handle, index, glCapabilities2AttribList);
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.print("glXChooseVisual recommended ");
                if (glXChooseVisual == null) {
                    System.err.println("null visual");
                }
                else {
                    System.err.println("visual id " + GraphicsConfigurationFactory.toHexString(glXChooseVisual.getVisualid()));
                }
            }
        }
        final int[] array = { 0 };
        final XVisualInfo create = XVisualInfo.create();
        create.setScreen(index);
        final XVisualInfo[] xGetVisualInfo = X11Lib.XGetVisualInfo(handle, 2L, create, array, 0);
        if (xGetVisualInfo == null || xGetVisualInfo.length < 1) {
            throw new GLException("Error while enumerating available XVisualInfos");
        }
        for (int i = 0; i < xGetVisualInfo.length; ++i) {
            final X11GLCapabilities xVisualInfo2GLCapabilities = X11GLXGraphicsConfiguration.XVisualInfo2GLCapabilities(x11GraphicsDevice, glProfile, xGetVisualInfo[i], exclusiveWinAttributeBits, glxMultisampleAvailable);
            if (null != xVisualInfo2GLCapabilities) {
                list.add(xVisualInfo2GLCapabilities);
                if (glCapabilitiesImmutable.isBackgroundOpaque() && glXChooseVisual != null && glXChooseVisual.getVisualid() == xGetVisualInfo[i].getVisualid()) {
                    n2 = list.size() - 1;
                }
            }
            else if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationXVisual: XVisual invalid: (" + x11GraphicsScreen + "): fbcfg: " + GraphicsConfigurationFactory.toHexString(xGetVisualInfo[i].getVisualid()));
            }
        }
        if (X11GLXGraphicsConfigurationFactory.DEBUG) {
            System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationXVisual: got configs: " + list.size());
            for (int j = 0; j < list.size(); ++j) {
                System.err.println(j + ": " + list.get(j));
            }
        }
        if (0 != n) {
            int k = 0;
            while (k < list.size()) {
                if (((X11GLCapabilities)list.get(k)).getVisualID(VisualIDHolder.VIDType.X11_XVISUAL) != n) {
                    list.remove(k);
                }
                else {
                    ++k;
                }
            }
            if (0 == list.size()) {
                if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationXVisual: post filter visualID " + GraphicsConfigurationFactory.toHexString(n) + " no config found, failed - return null");
                }
                return null;
            }
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationXVisual: post filter visualID " + GraphicsConfigurationFactory.toHexString(n) + " got configs: " + list.size());
            }
        }
        final int chooseCapabilities = GLGraphicsConfigurationFactory.chooseCapabilities(glCapabilitiesChooser, glCapabilitiesImmutable, list, n2);
        if (0 > chooseCapabilities) {
            if (X11GLXGraphicsConfigurationFactory.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.chooseGraphicsConfigurationXVisual: failed, return null");
                ExceptionUtils.dumpStack(System.err);
            }
            return null;
        }
        return new X11GLXGraphicsConfiguration(x11GraphicsScreen, (X11GLCapabilities)list.get(chooseCapabilities), glCapabilitiesImmutable2, glCapabilitiesChooser);
    }
    
    static {
        X11GLXGraphicsConfigurationFactory.XVisualIDComparator = new VisualIDHolder.VIDComparator(VisualIDHolder.VIDType.X11_XVISUAL);
        X11GLXGraphicsConfigurationFactory.fallbackX11GraphicsConfigurationFactory = null;
    }
}
