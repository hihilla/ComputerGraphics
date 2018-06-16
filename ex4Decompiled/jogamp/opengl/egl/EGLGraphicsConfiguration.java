// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.DefaultGraphicsConfiguration;
import java.nio.IntBuffer;
import com.jogamp.opengl.egl.EGL;
import com.jogamp.common.nio.PointerBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.DefaultGLCapabilitiesChooser;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;

public class EGLGraphicsConfiguration extends MutableGraphicsConfiguration implements Cloneable
{
    private static final String dbgCfgFailIntro = "Info: EGLConfig could not retrieve ";
    private static final String dbgCfgFailForConfig = " for config ";
    private static final String dbgCfgFailError = ", error ";
    private final GLCapabilitiesChooser chooser;
    
    public final long getNativeConfig() {
        return ((EGLGLCapabilities)this.capabilitiesChosen).getEGLConfig();
    }
    
    public final int getNativeConfigID() {
        return ((EGLGLCapabilities)this.capabilitiesChosen).getEGLConfigID();
    }
    
    EGLGraphicsConfiguration(final AbstractGraphicsScreen abstractGraphicsScreen, final EGLGLCapabilities eglglCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser chooser) {
        super(abstractGraphicsScreen, eglglCapabilities, glCapabilitiesImmutable);
        this.chooser = chooser;
    }
    
    public static EGLGraphicsConfiguration create(final GLCapabilitiesImmutable glCapabilitiesImmutable, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) {
        final AbstractGraphicsDevice device = abstractGraphicsScreen.getDevice();
        if (null == device || !(device instanceof EGLGraphicsDevice)) {
            throw new GLException("GraphicsDevice must be a valid EGLGraphicsDevice");
        }
        final long handle = device.getHandle();
        if (handle == 0L) {
            throw new GLException("Invalid EGL display: " + device);
        }
        final long eglConfigId2EGLConfig = EGLConfigId2EGLConfig(handle, n);
        if (0L < eglConfigId2EGLConfig) {
            return new EGLGraphicsConfiguration(abstractGraphicsScreen, EGLConfig2Capabilities(GLRendererQuirks.getStickyDeviceQuirks(GLDrawableFactory.getEGLFactory().getDefaultDevice()), (EGLGraphicsDevice)device, glCapabilitiesImmutable.getGLProfile(), eglConfigId2EGLConfig, GLGraphicsConfigurationUtil.getExclusiveWinAttributeBits(glCapabilitiesImmutable), false), glCapabilitiesImmutable, new DefaultGLCapabilitiesChooser());
        }
        return null;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    void updateGraphicsConfiguration() {
        final CapabilitiesImmutable chosenCapabilities = this.getChosenCapabilities();
        final EGLGraphicsConfiguration eglGraphicsConfiguration = (EGLGraphicsConfiguration)GraphicsConfigurationFactory.getFactory(this.getScreen().getDevice(), chosenCapabilities).chooseGraphicsConfiguration(chosenCapabilities, this.getRequestedCapabilities(), this.chooser, this.getScreen(), 0);
        if (null != eglGraphicsConfiguration) {
            this.setChosenCapabilities(eglGraphicsConfiguration.getChosenCapabilities());
            if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("updateGraphicsConfiguration(1): " + this);
            }
        }
    }
    
    public static long EGLConfigId2EGLConfig(final long n, final int n2) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(new int[] { 12328, n2, 12344 });
        final PointerBuffer allocateDirect = PointerBuffer.allocateDirect(1);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(1);
        if (!EGL.eglChooseConfig(n, directIntBuffer, allocateDirect, 1, directIntBuffer2)) {
            return 0L;
        }
        if (directIntBuffer2.get(0) == 0) {
            return 0L;
        }
        return allocateDirect.get(0);
    }
    
    public static boolean isEGLConfigValid(final long n, final long n2) {
        if (0L == n2) {
            return false;
        }
        if (!EGL.eglGetConfigAttrib(n, n2, 12328, Buffers.newDirectIntBuffer(1))) {
            final int eglGetError = EGL.eglGetError();
            if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: EGLConfig could not retrieve EGL_CONFIG_ID for config " + DefaultGraphicsConfiguration.toHexString(n2) + ", error " + DefaultGraphicsConfiguration.toHexString(eglGetError));
            }
            return false;
        }
        return true;
    }
    
    static int EGLConfigDrawableTypeBits(final EGLGraphicsDevice eglGraphicsDevice, final long n) {
        int n2 = 0;
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        if (!EGL.eglGetConfigAttrib(eglGraphicsDevice.getHandle(), n, 12339, directIntBuffer)) {
            throw new GLException("Could not determine EGL_SURFACE_TYPE");
        }
        final int value = directIntBuffer.get(0);
        if (0x0 != (value & 0x4)) {
            n2 |= 0x1;
        }
        if (0x0 != (value & 0x2)) {
            n2 |= 0x2;
        }
        if (0x0 != (value & 0x1)) {
            n2 |= 0xC;
        }
        return n2;
    }
    
    public static EGLGLCapabilities EGLConfig2Capabilities(final GLRendererQuirks glRendererQuirks, final EGLGraphicsDevice eglGraphicsDevice, GLProfile compatible, final long n, final int n2, final boolean b) {
        final long handle = eglGraphicsDevice.getHandle();
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(new int[] { 12328, 12352, 12334, 12327, 12324, 12323, 12322, 12321, 12326, 12325, 12340, 12343, 12342, 12341, 12337, 12512, 12513 });
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(directIntBuffer.remaining());
        EGL.eglGetConfigAttributes(handle, n, directIntBuffer, directIntBuffer2);
        if (12328 != directIntBuffer.get(0)) {
            if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: EGLConfig could not retrieve ConfigID for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
            }
            return null;
        }
        final int value = directIntBuffer2.get(0);
        if (12352 != directIntBuffer.get(1)) {
            if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: EGLConfig could not retrieve EGL_RENDERABLE_TYPE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
            }
            return null;
        }
        final int value2 = directIntBuffer2.get(1);
        int n3;
        if (glRendererQuirks.exist(15) && 0x0 != (0x4 & value2)) {
            n3 = (value2 | 0x40);
        }
        else {
            n3 = value2;
        }
        int value3;
        if (12334 == directIntBuffer.get(2)) {
            value3 = directIntBuffer2.get(2);
        }
        else {
            if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: EGLConfig could not retrieve EGL_NATIVE_VISUAL_ID for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
            }
            value3 = 0;
        }
        EGLGLCapabilities eglglCapabilities;
        try {
            if (null == compatible) {
                compatible = EGLGLCapabilities.getCompatible(eglGraphicsDevice, n3);
            }
            if (!EGLGLCapabilities.isCompatible(compatible, n3)) {
                if (EGLGraphicsConfiguration.DEBUG) {
                    System.err.println("config " + DefaultGraphicsConfiguration.toHexString(n) + ": Requested GLProfile " + compatible + " with quirks " + glRendererQuirks + " not compatible with EGL-RenderableType[" + (Object)EGLGLCapabilities.renderableTypeToString(null, n3) + "]");
                }
                return null;
            }
            eglglCapabilities = new EGLGLCapabilities(n, value, value3, compatible, n3);
        }
        catch (GLException ex) {
            if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("config " + DefaultGraphicsConfiguration.toHexString(n) + ": " + ex);
            }
            return null;
        }
        if (12327 == directIntBuffer.get(3)) {
            if (12368 == directIntBuffer2.get(3)) {
                eglglCapabilities.setHardwareAccelerated(false);
            }
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_CONFIG_CAVEAT for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (12324 == directIntBuffer.get(4)) {
            eglglCapabilities.setRedBits(directIntBuffer2.get(4));
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_RED_SIZE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (12323 == directIntBuffer.get(5)) {
            eglglCapabilities.setGreenBits(directIntBuffer2.get(5));
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_GREEN_SIZE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (12322 == directIntBuffer.get(6)) {
            eglglCapabilities.setBlueBits(directIntBuffer2.get(6));
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_BLUE_SIZE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (12321 == directIntBuffer.get(7)) {
            eglglCapabilities.setAlphaBits(directIntBuffer2.get(7));
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_ALPHA_SIZE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (12326 == directIntBuffer.get(8)) {
            eglglCapabilities.setStencilBits(directIntBuffer2.get(8));
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_STENCIL_SIZE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (12325 == directIntBuffer.get(9)) {
            eglglCapabilities.setDepthBits(directIntBuffer2.get(9));
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_DEPTH_SIZE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (b) {
            eglglCapabilities.setBackgroundOpaque(false);
        }
        else if (12340 == directIntBuffer.get(10)) {
            eglglCapabilities.setBackgroundOpaque(directIntBuffer2.get(10) != 12370);
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_TRANSPARENT_TYPE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (!eglglCapabilities.isBackgroundOpaque()) {
            if (12343 == directIntBuffer.get(11)) {
                final int value4 = directIntBuffer2.get(11);
                eglglCapabilities.setTransparentRedValue((-1 == value4) ? -1 : value4);
            }
            else if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: EGLConfig could not retrieve EGL_TRANSPARENT_RED_VALUE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
            }
            if (12342 == directIntBuffer.get(12)) {
                final int value5 = directIntBuffer2.get(12);
                eglglCapabilities.setTransparentGreenValue((-1 == value5) ? -1 : value5);
            }
            else if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: EGLConfig could not retrieve EGL_TRANSPARENT_GREEN_VALUE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
            }
            if (12341 == directIntBuffer.get(13)) {
                final int value6 = directIntBuffer2.get(13);
                eglglCapabilities.setTransparentBlueValue((-1 == value6) ? -1 : value6);
            }
            else if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: EGLConfig could not retrieve EGL_TRANSPARENT_BLUE_VALUE for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
            }
        }
        if (12337 == directIntBuffer.get(14)) {
            final int value7 = directIntBuffer2.get(14);
            eglglCapabilities.setSampleBuffers(value7 > 0);
            eglglCapabilities.setNumSamples(value7);
        }
        else if (EGLGraphicsConfiguration.DEBUG) {
            System.err.println("Info: EGLConfig could not retrieve EGL_SAMPLES for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
        }
        if (!eglglCapabilities.getSampleBuffers() && 12512 == directIntBuffer.get(15)) {
            if (directIntBuffer2.get(15) > 0 && 12513 == directIntBuffer.get(16)) {
                eglglCapabilities.setSampleExtension("NV_coverage_sample");
                eglglCapabilities.setSampleBuffers(true);
                eglglCapabilities.setNumSamples(directIntBuffer2.get(16));
            }
            else if (EGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: EGLConfig could not retrieve EGL_COVERAGE_SAMPLES_NV for config " + DefaultGraphicsConfiguration.toHexString(n) + ", error " + DefaultGraphicsConfiguration.toHexString(EGL.eglGetError()));
            }
        }
        final int n4 = n2 & EGLConfigDrawableTypeBits(eglGraphicsDevice, n);
        if (n4 == 0) {
            return null;
        }
        return (EGLGLCapabilities)GLGraphicsConfigurationUtil.fixWinAttribBitsAndHwAccel(eglGraphicsDevice, n4, eglglCapabilities);
    }
    
    public static IntBuffer GLCapabilities2AttribList(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(32);
        int n = 0;
        directIntBuffer.put(n++, 12339);
        int n2;
        if (glCapabilitiesImmutable.isOnscreen()) {
            n2 = 4;
        }
        else if (glCapabilitiesImmutable.isFBO()) {
            n2 = 1;
        }
        else if (glCapabilitiesImmutable.isPBuffer()) {
            n2 = 1;
        }
        else {
            if (!glCapabilitiesImmutable.isBitmap()) {
                throw new GLException("no surface type set in caps: " + glCapabilitiesImmutable);
            }
            n2 = 2;
        }
        directIntBuffer.put(n++, n2);
        directIntBuffer.put(n++, 12324);
        directIntBuffer.put(n++, glCapabilitiesImmutable.getRedBits());
        directIntBuffer.put(n++, 12323);
        directIntBuffer.put(n++, glCapabilitiesImmutable.getGreenBits());
        directIntBuffer.put(n++, 12322);
        directIntBuffer.put(n++, glCapabilitiesImmutable.getBlueBits());
        if (glCapabilitiesImmutable.getAlphaBits() > 0) {
            directIntBuffer.put(n++, 12321);
            directIntBuffer.put(n++, glCapabilitiesImmutable.getAlphaBits());
        }
        if (glCapabilitiesImmutable.getStencilBits() > 0) {
            directIntBuffer.put(n++, 12326);
            directIntBuffer.put(n++, glCapabilitiesImmutable.getStencilBits());
        }
        directIntBuffer.put(n++, 12325);
        directIntBuffer.put(n++, glCapabilitiesImmutable.getDepthBits());
        if (glCapabilitiesImmutable.getSampleBuffers()) {
            if (glCapabilitiesImmutable.getSampleExtension().equals("NV_coverage_sample")) {
                directIntBuffer.put(n++, 12512);
                directIntBuffer.put(n++, 1);
                directIntBuffer.put(n++, 12513);
                directIntBuffer.put(n++, glCapabilitiesImmutable.getNumSamples());
            }
            else {
                directIntBuffer.put(n++, 12338);
                directIntBuffer.put(n++, 1);
                directIntBuffer.put(n++, 12337);
                directIntBuffer.put(n++, glCapabilitiesImmutable.getNumSamples());
            }
        }
        directIntBuffer.put(n++, 12340);
        directIntBuffer.put(n++, glCapabilitiesImmutable.isBackgroundOpaque() ? 12344 : 12340);
        if (!glCapabilitiesImmutable.isBackgroundOpaque()) {
            directIntBuffer.put(n++, 12343);
            directIntBuffer.put(n++, (glCapabilitiesImmutable.getTransparentRedValue() >= 0) ? glCapabilitiesImmutable.getTransparentRedValue() : -1);
            directIntBuffer.put(n++, 12342);
            directIntBuffer.put(n++, (glCapabilitiesImmutable.getTransparentGreenValue() >= 0) ? glCapabilitiesImmutable.getTransparentGreenValue() : -1);
            directIntBuffer.put(n++, 12341);
            directIntBuffer.put(n++, (glCapabilitiesImmutable.getTransparentBlueValue() >= 0) ? glCapabilitiesImmutable.getTransparentBlueValue() : -1);
        }
        directIntBuffer.put(n++, 12352);
        if (glCapabilitiesImmutable.getGLProfile().usesNativeGLES1()) {
            directIntBuffer.put(n++, 1);
        }
        else if (glCapabilitiesImmutable.getGLProfile().usesNativeGLES2()) {
            directIntBuffer.put(n++, 4);
        }
        else if (glCapabilitiesImmutable.getGLProfile().usesNativeGLES3()) {
            if (GLRendererQuirks.existStickyDeviceQuirk(GLDrawableFactory.getEGLFactory().getDefaultDevice(), 15)) {
                directIntBuffer.put(n++, 4);
            }
            else {
                directIntBuffer.put(n++, 64);
            }
        }
        else {
            directIntBuffer.put(n++, 8);
        }
        directIntBuffer.put(n++, 12344);
        return directIntBuffer;
    }
    
    public static IntBuffer CreatePBufferSurfaceAttribList(final int n, final int n2, final int n3) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(16);
        int n4 = 0;
        directIntBuffer.put(n4++, 12375);
        directIntBuffer.put(n4++, n);
        directIntBuffer.put(n4++, 12374);
        directIntBuffer.put(n4++, n2);
        directIntBuffer.put(n4++, 12416);
        directIntBuffer.put(n4++, n3);
        directIntBuffer.put(n4++, 12417);
        directIntBuffer.put(n4++, (12380 == n3) ? 12380 : 12383);
        directIntBuffer.put(n4++, 12344);
        return directIntBuffer;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.getScreen() + ",\n\teglConfigHandle " + DefaultGraphicsConfiguration.toHexString(this.getNativeConfig()) + ", eglConfigID " + DefaultGraphicsConfiguration.toHexString(this.getNativeConfigID()) + ",\n\trequested " + this.getRequestedCapabilities() + ",\n\tchosen    " + this.getChosenCapabilities() + "]";
    }
}
