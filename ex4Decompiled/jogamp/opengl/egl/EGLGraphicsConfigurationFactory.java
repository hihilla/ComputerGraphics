// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import java.io.PrintStream;
import com.jogamp.opengl.GLRendererQuirks;
import java.util.ArrayList;
import com.jogamp.opengl.GLContext;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLCapabilities;
import java.nio.IntBuffer;
import java.util.Comparator;
import java.util.Collections;
import com.jogamp.opengl.GLProfile;
import com.jogamp.common.nio.PointerBuffer;
import com.jogamp.opengl.egl.EGL;
import com.jogamp.common.nio.Buffers;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.VisualIDHolder;
import jogamp.opengl.GLGraphicsConfigurationFactory;

public class EGLGraphicsConfigurationFactory extends GLGraphicsConfigurationFactory
{
    static VisualIDHolder.VIDComparator EglCfgIDComparator;
    static GraphicsConfigurationFactory nativeGraphicsConfigurationFactory;
    static GraphicsConfigurationFactory kdeglGraphicsConfigurationFactory;
    static GraphicsConfigurationFactory fallbackGraphicsConfigurationFactory;
    
    static void registerFactory() {
        final EGLGraphicsConfigurationFactory eglGraphicsConfigurationFactory = new EGLGraphicsConfigurationFactory();
        if (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(false)) {
            EGLGraphicsConfigurationFactory.nativeGraphicsConfigurationFactory = GraphicsConfigurationFactory.registerFactory(X11GraphicsDevice.class, GLCapabilitiesImmutable.class, eglGraphicsConfigurationFactory);
            if (null != EGLGraphicsConfigurationFactory.nativeGraphicsConfigurationFactory) {
                EGLGraphicsConfigurationFactory.fallbackGraphicsConfigurationFactory = EGLGraphicsConfigurationFactory.nativeGraphicsConfigurationFactory;
            }
            else {
                EGLGraphicsConfigurationFactory.fallbackGraphicsConfigurationFactory = GraphicsConfigurationFactory.getFactory(X11GraphicsDevice.class, CapabilitiesImmutable.class);
            }
        }
        EGLGraphicsConfigurationFactory.kdeglGraphicsConfigurationFactory = GraphicsConfigurationFactory.registerFactory(EGLGraphicsDevice.class, GLCapabilitiesImmutable.class, eglGraphicsConfigurationFactory);
    }
    
    static void unregisterFactory() {
        if (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(false)) {
            GraphicsConfigurationFactory.registerFactory(X11GraphicsDevice.class, GLCapabilitiesImmutable.class, EGLGraphicsConfigurationFactory.nativeGraphicsConfigurationFactory);
        }
        EGLGraphicsConfigurationFactory.nativeGraphicsConfigurationFactory = null;
        EGLGraphicsConfigurationFactory.fallbackGraphicsConfigurationFactory = null;
        GraphicsConfigurationFactory.registerFactory(EGLGraphicsDevice.class, GLCapabilitiesImmutable.class, EGLGraphicsConfigurationFactory.kdeglGraphicsConfigurationFactory);
        EGLGraphicsConfigurationFactory.kdeglGraphicsConfigurationFactory = null;
    }
    
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) {
        if (abstractGraphicsScreen == null) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only AbstractGraphicsDevice objects");
        }
        if (!(capabilitiesImmutable instanceof GLCapabilitiesImmutable)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilities objects - chosen");
        }
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)capabilitiesImmutable;
        if (!(capabilitiesImmutable2 instanceof GLCapabilitiesImmutable)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilities objects - requested");
        }
        if (capabilitiesChooser != null && !(capabilitiesChooser instanceof GLCapabilitiesChooser)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilitiesChooser objects");
        }
        final AbstractGraphicsDevice device = abstractGraphicsScreen.getDevice();
        if (null == device) {
            throw new GLException("Null AbstractGraphicsDevice");
        }
        AbstractGraphicsConfiguration abstractGraphicsConfiguration = null;
        if (device instanceof EGLGraphicsDevice) {
            abstractGraphicsConfiguration = chooseGraphicsConfigurationStatic((GLCapabilitiesImmutable)capabilitiesImmutable, (GLCapabilitiesImmutable)capabilitiesImmutable2, (GLCapabilitiesChooser)capabilitiesChooser, abstractGraphicsScreen, n, false);
        }
        else {
            if (null == EGLGraphicsConfigurationFactory.fallbackGraphicsConfigurationFactory) {
                throw new InternalError("Native fallback GraphicsConfigurationFactory is null, but call issued for device: " + device + " of type " + device.getClass().getSimpleName());
            }
            if (glCapabilitiesImmutable.getGLProfile().usesNativeGLES()) {
                if (EGLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("EGLGraphicsConfigurationFactory.choose..: Handle native device " + device.getClass().getSimpleName());
                }
                abstractGraphicsConfiguration = chooseGraphicsConfigurationStatic((GLCapabilitiesImmutable)capabilitiesImmutable, (GLCapabilitiesImmutable)capabilitiesImmutable2, (GLCapabilitiesChooser)capabilitiesChooser, abstractGraphicsScreen, n, false);
                if (null == abstractGraphicsConfiguration || 0 == abstractGraphicsConfiguration.getVisualID(VisualIDHolder.VIDType.NATIVE)) {
                    abstractGraphicsConfiguration = null;
                    if (EGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("EGLGraphicsConfigurationFactory.choose..: No native visual ID, fallback ..");
                    }
                }
            }
            if (null == abstractGraphicsConfiguration) {
                if (EGLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("EGLGraphicsConfigurationFactory.choose..: Delegate to " + EGLGraphicsConfigurationFactory.fallbackGraphicsConfigurationFactory.getClass().getSimpleName());
                }
                abstractGraphicsConfiguration = EGLGraphicsConfigurationFactory.fallbackGraphicsConfigurationFactory.chooseGraphicsConfiguration(capabilitiesImmutable, capabilitiesImmutable2, capabilitiesChooser, abstractGraphicsScreen, n);
            }
        }
        return abstractGraphicsConfiguration;
    }
    
    protected static List<GLCapabilitiesImmutable> getAvailableCapabilities(final EGLDrawableFactory eglDrawableFactory, final AbstractGraphicsDevice abstractGraphicsDevice) {
        final EGLDrawableFactory.SharedResource orCreateSharedResourceImpl = eglDrawableFactory.getOrCreateSharedResourceImpl(abstractGraphicsDevice);
        if (null == orCreateSharedResourceImpl) {
            throw new GLException("Shared resource for device n/a: " + abstractGraphicsDevice);
        }
        final EGLGraphicsDevice device = orCreateSharedResourceImpl.getDevice();
        final long handle = device.getHandle();
        if (0L == handle) {
            throw new GLException("null eglDisplay");
        }
        Object eglConfigs2GLCaps = null;
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        if (!EGL.eglGetConfigs(handle, null, 0, directIntBuffer)) {
            throw new GLException("Graphics configuration get maxConfigs (eglGetConfigs) call failed, error " + GraphicsConfigurationFactory.toHexString(EGL.eglGetError()));
        }
        if (0 == directIntBuffer.get(0)) {
            throw new GLException("Graphics configuration get maxConfigs (eglGetConfigs) no configs");
        }
        final PointerBuffer allocateDirect = PointerBuffer.allocateDirect(directIntBuffer.get(0));
        if (!EGL.eglGetConfigs(handle, allocateDirect, allocateDirect.capacity(), directIntBuffer)) {
            throw new GLException("Graphics configuration get all configs (eglGetConfigs) call failed, error " + GraphicsConfigurationFactory.toHexString(EGL.eglGetError()));
        }
        if (directIntBuffer.get(0) > 0) {
            eglConfigs2GLCaps = eglConfigs2GLCaps(device, null, allocateDirect, directIntBuffer.get(0), 15, false, false);
            if (null != eglConfigs2GLCaps && ((List)eglConfigs2GLCaps).size() > 1) {
                Collections.sort((List<Object>)eglConfigs2GLCaps, (Comparator<? super Object>)EGLGraphicsConfigurationFactory.EglCfgIDComparator);
            }
        }
        return (List<GLCapabilitiesImmutable>)eglConfigs2GLCaps;
    }
    
    public static EGLGraphicsConfiguration chooseGraphicsConfigurationStatic(GLCapabilitiesImmutable fixGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n, final boolean b) {
        if (fixGLCapabilities == null) {
            fixGLCapabilities = new GLCapabilities(null);
        }
        if (null == abstractGraphicsScreen) {
            throw new GLException("Null AbstractGraphicsScreen");
        }
        final AbstractGraphicsDevice device = abstractGraphicsScreen.getDevice();
        if (null == device) {
            throw new GLException("Null AbstractGraphicsDevice");
        }
        EGLGraphicsDevice eglCreateEGLGraphicsDevice;
        boolean b2;
        if (device instanceof EGLGraphicsDevice) {
            eglCreateEGLGraphicsDevice = (EGLGraphicsDevice)device;
            if (eglCreateEGLGraphicsDevice.getHandle() == 0L) {
                throw new GLException("Invalid EGL display: " + eglCreateEGLGraphicsDevice);
            }
            b2 = false;
        }
        else {
            eglCreateEGLGraphicsDevice = EGLDisplayUtil.eglCreateEGLGraphicsDevice(device);
            eglCreateEGLGraphicsDevice.open();
            b2 = true;
        }
        final GLProfile glProfile = fixGLCapabilities.getGLProfile();
        fixGLCapabilities = GLGraphicsConfigurationUtil.fixGLCapabilities(fixGLCapabilities, GLDrawableFactory.getEGLFactory(), device);
        EGLGraphicsConfiguration eglGraphicsConfiguration = eglChooseConfig(eglCreateEGLGraphicsDevice, fixGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, abstractGraphicsScreen, n, b);
        if (null == eglGraphicsConfiguration) {
            if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("eglChooseConfig failed with given capabilities " + fixGLCapabilities);
            }
            final GLCapabilities glCapabilities = new GLCapabilities(glProfile);
            glCapabilities.setSampleBuffers(true);
            glCapabilities.setNumSamples(4);
            glCapabilities.setRedBits(8);
            glCapabilities.setGreenBits(8);
            glCapabilities.setBlueBits(8);
            glCapabilities.setDepthBits(16);
            if (!fixGLCapabilities.isOnscreen()) {
                glCapabilities.setOnscreen(false);
                glCapabilities.setPBuffer(fixGLCapabilities.isPBuffer());
                glCapabilities.setFBO(fixGLCapabilities.isFBO());
            }
            if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("trying fixed caps (1): " + glCapabilities);
            }
            eglGraphicsConfiguration = eglChooseConfig(eglCreateEGLGraphicsDevice, glCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser, abstractGraphicsScreen, n, false);
        }
        if (null == eglGraphicsConfiguration) {
            final GLCapabilities glCapabilities2 = new GLCapabilities(glProfile);
            glCapabilities2.setRedBits(5);
            glCapabilities2.setGreenBits(6);
            glCapabilities2.setBlueBits(5);
            glCapabilities2.setDepthBits(16);
            if (!fixGLCapabilities.isOnscreen()) {
                glCapabilities2.setOnscreen(false);
                glCapabilities2.setPBuffer(fixGLCapabilities.isPBuffer());
                glCapabilities2.setFBO(fixGLCapabilities.isFBO());
            }
            if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("trying fixed caps (2): " + glCapabilities2);
            }
            eglGraphicsConfiguration = eglChooseConfig(eglCreateEGLGraphicsDevice, glCapabilities2, glCapabilitiesImmutable, glCapabilitiesChooser, abstractGraphicsScreen, n, false);
        }
        if (null == eglGraphicsConfiguration) {
            final GLCapabilities glCapabilities3 = new GLCapabilities(glProfile);
            glCapabilities3.setSampleBuffers(true);
            glCapabilities3.setNumSamples(4);
            glCapabilities3.setRedBits(5);
            glCapabilities3.setGreenBits(6);
            glCapabilities3.setBlueBits(5);
            glCapabilities3.setDepthBits(16);
            if (!fixGLCapabilities.isOnscreen()) {
                glCapabilities3.setOnscreen(false);
                glCapabilities3.setPBuffer(fixGLCapabilities.isPBuffer());
                glCapabilities3.setFBO(fixGLCapabilities.isFBO());
            }
            if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("trying fixed caps (3): " + glCapabilities3);
            }
            eglGraphicsConfiguration = eglChooseConfig(eglCreateEGLGraphicsDevice, glCapabilities3, glCapabilitiesImmutable, glCapabilitiesChooser, abstractGraphicsScreen, n, false);
        }
        if (null == eglGraphicsConfiguration) {
            throw new GLException("Graphics configuration failed [direct caps, eglGetConfig/chooser and fixed-caps(1-3)]");
        }
        if (b2) {
            ((EGLGLCapabilities)eglGraphicsConfiguration.getChosenCapabilities()).setEGLConfig(0L);
            eglCreateEGLGraphicsDevice.close();
        }
        return eglGraphicsConfiguration;
    }
    
    static EGLGraphicsConfiguration eglChooseConfig(final EGLGraphicsDevice eglGraphicsDevice, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser glCapabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n, final boolean b) {
        final long handle = eglGraphicsDevice.getHandle();
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final int exclusiveWinAttributeBits = GLGraphicsConfigurationUtil.getExclusiveWinAttributeBits(glCapabilitiesImmutable);
        List<? extends CapabilitiesImmutable> list = null;
        int n2 = -1;
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        if (!EGL.eglGetConfigs(handle, null, 0, directIntBuffer)) {
            throw new GLException("EGLGraphicsConfiguration.eglChooseConfig: Get maxConfigs (eglGetConfigs) call failed, error " + GraphicsConfigurationFactory.toHexString(EGL.eglGetError()));
        }
        if (0 == directIntBuffer.get(0)) {
            throw new GLException("EGLGraphicsConfiguration.eglChooseConfig: Get maxConfigs (eglGetConfigs) no configs");
        }
        final int value = directIntBuffer.get(0);
        if (EGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("EGLGraphicsConfiguration.eglChooseConfig: eglChooseConfig eglDisplay " + GraphicsConfigurationFactory.toHexString(handle) + ", nativeVisualID " + GraphicsConfigurationFactory.toHexString(n) + ", capsChosen " + glCapabilitiesImmutable + ", winbits " + GLGraphicsConfigurationUtil.winAttributeBits2String(null, exclusiveWinAttributeBits).toString() + ", fboAvail " + GLContext.isFBOAvailable(eglGraphicsDevice, glProfile) + ", device " + eglGraphicsDevice + ", " + eglGraphicsDevice.getUniqueID() + ", numEGLConfigs " + value);
        }
        final IntBuffer glCapabilities2AttribList = EGLGraphicsConfiguration.GLCapabilities2AttribList(glCapabilitiesImmutable);
        final PointerBuffer allocateDirect = PointerBuffer.allocateDirect(directIntBuffer.get(0));
        boolean b2;
        if (0 == n) {
            if (!EGL.eglChooseConfig(handle, glCapabilities2AttribList, allocateDirect, allocateDirect.capacity(), directIntBuffer)) {
                if (EGLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #1 eglChooseConfig: false");
                }
                directIntBuffer.put(0, 0);
                b2 = false;
            }
            else {
                b2 = (directIntBuffer.get(0) > 0);
            }
        }
        else {
            if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("EGLGraphicsConfiguration.eglChooseConfig: Skipped due to given visualID: " + GraphicsConfigurationFactory.toHexString(n));
            }
            b2 = false;
        }
        final boolean b3 = b2 && !b && glCapabilitiesImmutable.isBackgroundOpaque();
        final boolean b4 = null == glCapabilitiesChooser && b3;
        if (b2) {
            list = eglConfigs2GLCaps(eglGraphicsDevice, glProfile, allocateDirect, directIntBuffer.get(0), exclusiveWinAttributeBits, b, b4);
            if (list.size() > 0) {
                final long value2 = allocateDirect.get(0);
                n2 = 0;
                if (EGLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #1 eglChooseConfig: recommended fbcfg " + GraphicsConfigurationFactory.toHexString(value2) + ", idx " + n2);
                    System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #1 useRecommendedIndex " + b3 + ", skipCapsChooser " + b4);
                    System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #1 fbcfg caps " + list.get(n2));
                }
            }
            else if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #1 eglChooseConfig: no caps for recommended fbcfg " + GraphicsConfigurationFactory.toHexString(allocateDirect.get(0)));
                System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #1 useRecommendedIndex " + b3 + ", skipCapsChooser " + b4);
            }
        }
        else if (EGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #1 eglChooseConfig: no configs");
            System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #1 useRecommendedIndex " + b3 + ", skipCapsChooser " + b4);
        }
        if (null == list || 0 == list.size()) {
            n2 = -1;
            if (!EGL.eglGetConfigs(handle, allocateDirect, allocateDirect.capacity(), directIntBuffer)) {
                throw new GLException("EGLGraphicsConfiguration.eglChooseConfig: #2 Get all configs (eglGetConfigs) call failed, error " + GraphicsConfigurationFactory.toHexString(EGL.eglGetError()));
            }
            if (directIntBuffer.get(0) > 0) {
                list = eglConfigs2GLCaps(eglGraphicsDevice, glProfile, allocateDirect, directIntBuffer.get(0), exclusiveWinAttributeBits, b, false);
            }
        }
        if (null == list || 0 == list.size()) {
            if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #2 Graphics configuration 1st choice and 2nd choice failed - no configs");
                printCaps("AllCaps", eglConfigs2GLCaps(eglGraphicsDevice, glProfile, allocateDirect, directIntBuffer.get(0), 15, b, false), System.err);
            }
            return null;
        }
        if (EGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("EGLGraphicsConfiguration.eglChooseConfig: got configs: " + list.size());
            for (int i = 0; i < list.size(); ++i) {
                System.err.println(i + ": " + list.get(i));
            }
        }
        if (0 != n) {
            final ArrayList<CapabilitiesImmutable> list2 = new ArrayList<CapabilitiesImmutable>();
            int j = 0;
            while (j < list.size()) {
                final GLCapabilitiesImmutable glCapabilitiesImmutable3 = (GLCapabilitiesImmutable)list.get(j);
                if (glCapabilitiesImmutable3.getVisualID(VisualIDHolder.VIDType.NATIVE) != n) {
                    if (EGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("Remove[" + j + "] (mismatch VisualID): " + glCapabilitiesImmutable3);
                    }
                    list2.add((CapabilitiesImmutable)list.remove(j));
                }
                else if (0 == glCapabilitiesImmutable3.getDepthBits() && 0 < glCapabilitiesImmutable.getDepthBits()) {
                    if (EGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("Remove[" + j + "] (mismatch depth-bits): " + glCapabilitiesImmutable3);
                    }
                    list2.add((CapabilitiesImmutable)list.remove(j));
                }
                else {
                    ++j;
                }
            }
            if (0 == list.size()) {
                list = list2;
                if (EGLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("EGLGraphicsConfiguration.eglChooseConfig: post filter nativeVisualID " + GraphicsConfigurationFactory.toHexString(n) + " no config found, revert to all");
                }
            }
            else if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("EGLGraphicsConfiguration.eglChooseConfig: post filter nativeVisualID " + GraphicsConfigurationFactory.toHexString(n) + " got configs: " + list.size());
                for (int k = 0; k < list.size(); ++k) {
                    System.err.println(k + ": " + list.get(k));
                }
            }
        }
        int chooseCapabilities;
        if (b4 && 0 <= n2) {
            chooseCapabilities = n2;
        }
        else {
            chooseCapabilities = GLGraphicsConfigurationFactory.chooseCapabilities(glCapabilitiesChooser, glCapabilitiesImmutable, list, n2);
        }
        if (0 > chooseCapabilities) {
            if (EGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("EGLGraphicsConfiguration.eglChooseConfig: #2 chooseCapabilities failed");
            }
            return null;
        }
        final EGLGLCapabilities eglglCapabilities = (EGLGLCapabilities)list.get(chooseCapabilities);
        final EGLGraphicsConfiguration eglGraphicsConfiguration = new EGLGraphicsConfiguration(abstractGraphicsScreen, eglglCapabilities, glCapabilitiesImmutable2, glCapabilitiesChooser);
        if (EGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("EGLGraphicsConfiguration.eglChooseConfig: X chosen :" + chooseCapabilities + ", eglConfig: " + GraphicsConfigurationFactory.toHexString(eglglCapabilities.getEGLConfig()) + ": " + eglGraphicsConfiguration);
        }
        return eglGraphicsConfiguration;
    }
    
    static List<GLCapabilitiesImmutable> eglConfigs2GLCaps(final EGLGraphicsDevice eglGraphicsDevice, final GLProfile glProfile, final PointerBuffer pointerBuffer, final int n, final int n2, final boolean b, final boolean b2) {
        final GLRendererQuirks stickyDeviceQuirks = GLRendererQuirks.getStickyDeviceQuirks(GLDrawableFactory.getEGLFactory().getDefaultDevice());
        final ArrayList<EGLGLCapabilities> list = (ArrayList<EGLGLCapabilities>)new ArrayList<GLCapabilitiesImmutable>(n);
        for (int i = 0; i < n; ++i) {
            final EGLGLCapabilities eglConfig2Capabilities = EGLGraphicsConfiguration.EGLConfig2Capabilities(stickyDeviceQuirks, eglGraphicsDevice, glProfile, pointerBuffer.get(i), n2, b);
            if (null != eglConfig2Capabilities) {
                list.add(eglConfig2Capabilities);
                if (b2) {
                    break;
                }
            }
        }
        return (List<GLCapabilitiesImmutable>)list;
    }
    
    static void printCaps(final String s, final List<GLCapabilitiesImmutable> list, final PrintStream printStream) {
        for (int i = 0; i < list.size(); ++i) {
            printStream.println(s + "[" + i + "] " + list.get(i));
        }
    }
    
    static {
        EGLGraphicsConfigurationFactory.EglCfgIDComparator = new VisualIDHolder.VIDComparator(VisualIDHolder.VIDType.EGL_CONFIG);
        EGLGraphicsConfigurationFactory.nativeGraphicsConfigurationFactory = null;
        EGLGraphicsConfigurationFactory.kdeglGraphicsConfigurationFactory = null;
        EGLGraphicsConfigurationFactory.fallbackGraphicsConfigurationFactory = null;
    }
}
