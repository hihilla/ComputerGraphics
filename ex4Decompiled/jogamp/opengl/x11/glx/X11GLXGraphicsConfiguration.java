// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import jogamp.nativewindow.x11.X11Lib;
import jogamp.nativewindow.x11.XRenderDirectFormat;
import jogamp.nativewindow.x11.XVisualInfo;
import com.jogamp.opengl.GLCapabilities;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import java.util.ArrayList;
import java.util.List;
import com.jogamp.common.nio.PointerBuffer;
import jogamp.nativewindow.x11.XRenderPictFormat;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import com.jogamp.opengl.DefaultGLCapabilitiesChooser;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.nativewindow.DefaultGraphicsConfiguration;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.x11.X11GraphicsConfiguration;

public class X11GLXGraphicsConfiguration extends X11GraphicsConfiguration implements Cloneable
{
    public static final int MAX_ATTRIBS = 128;
    private final GLCapabilitiesChooser chooser;
    
    X11GLXGraphicsConfiguration(final X11GraphicsScreen x11GraphicsScreen, final X11GLCapabilities x11GLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser chooser) {
        super(x11GraphicsScreen, x11GLCapabilities, glCapabilitiesImmutable, x11GLCapabilities.getXVisualInfo());
        this.chooser = chooser;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    public final long getFBConfig() {
        return ((X11GLCapabilities)this.capabilitiesChosen).getFBConfig();
    }
    
    public final int getFBConfigID() {
        return ((X11GLCapabilities)this.capabilitiesChosen).getFBConfigID();
    }
    
    public final boolean hasFBConfig() {
        return ((X11GLCapabilities)this.capabilitiesChosen).hasFBConfig();
    }
    
    void updateGraphicsConfiguration() {
        final CapabilitiesImmutable chosenCapabilities = this.getChosenCapabilities();
        if (!(chosenCapabilities instanceof X11GLCapabilities) || 0 == chosenCapabilities.getVisualID(VisualIDHolder.VIDType.X11_XVISUAL)) {
            final X11GLXGraphicsConfiguration x11GLXGraphicsConfiguration = (X11GLXGraphicsConfiguration)GraphicsConfigurationFactory.getFactory(this.getScreen().getDevice(), chosenCapabilities).chooseGraphicsConfiguration(chosenCapabilities, this.getRequestedCapabilities(), this.chooser, this.getScreen(), 0);
            if (null == x11GLXGraphicsConfiguration) {
                throw new GLException("No native VisualID pre-chosen and update failed: " + this);
            }
            this.setXVisualInfo(x11GLXGraphicsConfiguration.getXVisualInfo());
            this.setChosenCapabilities(x11GLXGraphicsConfiguration.getChosenCapabilities());
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.updateGraphicsConfiguration updated:" + this);
            }
        }
        else if (X11GLXGraphicsConfiguration.DEBUG) {
            System.err.println("X11GLXGraphicsConfiguration.updateGraphicsConfiguration kept:" + this);
        }
    }
    
    static X11GLXGraphicsConfiguration create(GLProfile default1, final X11GraphicsScreen x11GraphicsScreen, final int n) {
        final X11GraphicsDevice x11GraphicsDevice = (X11GraphicsDevice)x11GraphicsScreen.getDevice();
        final long handle = x11GraphicsDevice.getHandle();
        if (0L == handle) {
            throw new GLException("Display null of " + x11GraphicsScreen);
        }
        final long glXFBConfigID2FBConfig = glXFBConfigID2FBConfig(handle, x11GraphicsScreen.getIndex(), n);
        if (0L == glXFBConfigID2FBConfig) {
            throw new GLException("FBConfig null of " + DefaultGraphicsConfiguration.toHexString(n));
        }
        if (null == default1) {
            default1 = GLProfile.getDefault(x11GraphicsScreen.getDevice());
        }
        final X11GLCapabilities glxfbConfig2GLCapabilities = GLXFBConfig2GLCapabilities(x11GraphicsDevice, default1, glXFBConfigID2FBConfig, 15, ((X11GLXDrawableFactory)GLDrawableFactory.getDesktopFactory()).isGLXMultisampleAvailable(x11GraphicsDevice));
        if (null == glxfbConfig2GLCapabilities) {
            throw new GLException("GLCapabilities null of " + DefaultGraphicsConfiguration.toHexString(glXFBConfigID2FBConfig));
        }
        return new X11GLXGraphicsConfiguration(x11GraphicsScreen, glxfbConfig2GLCapabilities, glxfbConfig2GLCapabilities, new DefaultGLCapabilitiesChooser());
    }
    
    static IntBuffer GLCapabilities2AttribList(final GLCapabilitiesImmutable glCapabilitiesImmutable, final boolean b, final boolean b2, final long n, final int n2) {
        if (glCapabilitiesImmutable.getRedBits() + glCapabilitiesImmutable.getGreenBits() + glCapabilitiesImmutable.getBlueBits() < 15) {
            throw new GLException("Bit depths < 15 (i.e., non-true-color) not supported");
        }
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(128);
        int n3 = 0;
        if (b) {
            directIntBuffer.put(n3++, 32784);
            int n4;
            if (glCapabilitiesImmutable.isOnscreen()) {
                n4 = 1;
            }
            else if (glCapabilitiesImmutable.isFBO()) {
                n4 = 1;
            }
            else if (glCapabilitiesImmutable.isPBuffer()) {
                n4 = 4;
            }
            else {
                if (!glCapabilitiesImmutable.isBitmap()) {
                    throw new GLException("no surface type set in caps: " + glCapabilitiesImmutable);
                }
                n4 = 2;
            }
            directIntBuffer.put(n3++, n4);
            directIntBuffer.put(n3++, 32785);
            directIntBuffer.put(n3++, 1);
        }
        else {
            directIntBuffer.put(n3++, 4);
        }
        if (b) {
            directIntBuffer.put(n3++, 5);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getDoubleBuffered() ? 1 : 0);
            directIntBuffer.put(n3++, 6);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getStereo() ? 1 : 0);
            directIntBuffer.put(n3++, 35);
            directIntBuffer.put(n3++, 32768);
        }
        else {
            if (glCapabilitiesImmutable.getDoubleBuffered()) {
                directIntBuffer.put(n3++, 5);
            }
            if (glCapabilitiesImmutable.getStereo()) {
                directIntBuffer.put(n3++, 6);
            }
        }
        directIntBuffer.put(n3++, 8);
        directIntBuffer.put(n3++, glCapabilitiesImmutable.getRedBits());
        directIntBuffer.put(n3++, 9);
        directIntBuffer.put(n3++, glCapabilitiesImmutable.getGreenBits());
        directIntBuffer.put(n3++, 10);
        directIntBuffer.put(n3++, glCapabilitiesImmutable.getBlueBits());
        if (glCapabilitiesImmutable.getAlphaBits() > 0) {
            directIntBuffer.put(n3++, 11);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getAlphaBits());
        }
        if (glCapabilitiesImmutable.getStencilBits() > 0) {
            directIntBuffer.put(n3++, 13);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getStencilBits());
        }
        directIntBuffer.put(n3++, 12);
        directIntBuffer.put(n3++, glCapabilitiesImmutable.getDepthBits());
        if (glCapabilitiesImmutable.getAccumRedBits() > 0 || glCapabilitiesImmutable.getAccumGreenBits() > 0 || glCapabilitiesImmutable.getAccumBlueBits() > 0 || glCapabilitiesImmutable.getAccumAlphaBits() > 0) {
            directIntBuffer.put(n3++, 14);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getAccumRedBits());
            directIntBuffer.put(n3++, 15);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getAccumGreenBits());
            directIntBuffer.put(n3++, 16);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getAccumBlueBits());
            directIntBuffer.put(n3++, 17);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getAccumAlphaBits());
        }
        if (b2 && glCapabilitiesImmutable.getSampleBuffers()) {
            directIntBuffer.put(n3++, 100000);
            directIntBuffer.put(n3++, 1);
            directIntBuffer.put(n3++, 100001);
            directIntBuffer.put(n3++, glCapabilitiesImmutable.getNumSamples());
        }
        directIntBuffer.put(n3++, 0);
        return directIntBuffer;
    }
    
    static boolean GLXFBConfigIDValid(final long n, final int n2, final int n3) {
        final long glXFBConfigID2FBConfig = glXFBConfigID2FBConfig(n, n2, n3);
        return 0L != glXFBConfigID2FBConfig && GLXFBConfigValid(n, glXFBConfigID2FBConfig);
    }
    
    static boolean GLXFBConfigValid(final long n, final long n2) {
        return 2 != GLX.glXGetFBConfigAttrib(n, n2, 32785, Buffers.newDirectIntBuffer(1));
    }
    
    static int FBCfgDrawableTypeBits(final X11GraphicsDevice x11GraphicsDevice, final long n) {
        int n2 = 0;
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        if (!glXGetFBConfig(x11GraphicsDevice.getHandle(), n, 32784, directIntBuffer)) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.FBCfgDrawableTypeBits: FBConfig invalid: fbcfg: " + DefaultGraphicsConfiguration.toHexString(n));
            }
            return 0;
        }
        final int value = directIntBuffer.get(0);
        if (0x0 != (value & 0x1)) {
            n2 |= 0x9;
        }
        if (0x0 != (value & 0x2)) {
            n2 |= 0x2;
        }
        if (0x0 != (value & 0x4)) {
            n2 |= 0x4;
        }
        return n2;
    }
    
    static X11GLCapabilities GLXFBConfig2GLCapabilities(final X11GraphicsDevice x11GraphicsDevice, final GLProfile glProfile, final long n, final int n2, final boolean b) {
        return GLXFBConfig2GLCapabilities(x11GraphicsDevice, glProfile, n, n2, b, Buffers.newDirectIntBuffer(1), XRenderPictFormat.create());
    }
    
    static List<GLCapabilitiesImmutable> GLXFBConfig2GLCapabilities(final X11GraphicsDevice x11GraphicsDevice, final GLProfile glProfile, final PointerBuffer pointerBuffer, final int n, final boolean b, final boolean b2) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        final XRenderPictFormat create = XRenderPictFormat.create();
        final ArrayList<X11GLCapabilities> list = (ArrayList<X11GLCapabilities>)new ArrayList<GLCapabilitiesImmutable>();
        for (int i = 0; i < pointerBuffer.limit(); ++i) {
            final X11GLCapabilities glxfbConfig2GLCapabilities = GLXFBConfig2GLCapabilities(x11GraphicsDevice, glProfile, pointerBuffer.get(i), n, b, directIntBuffer, create);
            if (null != glxfbConfig2GLCapabilities) {
                list.add(glxfbConfig2GLCapabilities);
                if (b2) {
                    break;
                }
            }
        }
        return (List<GLCapabilitiesImmutable>)list;
    }
    
    static X11GLCapabilities GLXFBConfig2GLCapabilities(final X11GraphicsDevice x11GraphicsDevice, final GLProfile glProfile, final long n, final int n2, final boolean b, final IntBuffer intBuffer, final XRenderPictFormat xRenderPictFormat) {
        final long handle = x11GraphicsDevice.getHandle();
        final int fbCfgDrawableTypeBits = FBCfgDrawableTypeBits(x11GraphicsDevice, n);
        int n3 = n2 & fbCfgDrawableTypeBits;
        if (fbCfgDrawableTypeBits == 0 || n3 == 0) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities: zero drawablebits: allDrawableTypeBits: " + DefaultGraphicsConfiguration.toHexString(fbCfgDrawableTypeBits) + ", drawableTypeBits " + DefaultGraphicsConfiguration.toHexString(n3));
            }
            return null;
        }
        final int glXFBConfig2FBConfigID = glXFBConfig2FBConfigID(handle, n);
        if (glXFBConfig2FBConfigID == 0) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities: FBConfig invalid (0): fbcfg: " + DefaultGraphicsConfiguration.toHexString(n));
            }
            return null;
        }
        final XVisualInfo glXGetVisualFromFBConfig = GLX.glXGetVisualFromFBConfig(handle, n);
        if (null == glXGetVisualFromFBConfig) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities: Null XVisualInfo for FBConfigID 0x" + Integer.toHexString(glXFBConfig2FBConfigID));
            }
            n3 &= 0xFFFFFFF6;
        }
        if (n3 == 0) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities: zero drawablebits: winattrmask: " + DefaultGraphicsConfiguration.toHexString(n2) + ", offscreen " + (null == glXGetVisualFromFBConfig));
            }
            return null;
        }
        if (2 == GLX.glXGetFBConfigAttrib(handle, n, 32785, intBuffer)) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities: FBConfig invalid (1): fbcfg: " + DefaultGraphicsConfiguration.toHexString(n));
            }
            return null;
        }
        if (0x0 == (0x1 & intBuffer.get(0))) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("X11GLXGraphicsConfiguration.GLXFBConfig2GLCapabilities: FBConfig not RGBA (2): fbcfg: " + DefaultGraphicsConfiguration.toHexString(n));
            }
            return null;
        }
        final X11GLCapabilities x11GLCapabilities = new X11GLCapabilities(glXGetVisualFromFBConfig, n, glXFBConfig2FBConfigID, glProfile);
        final XRenderDirectFormat xRenderDirectFormat = (null != glXGetVisualFromFBConfig) ? X11GraphicsConfiguration.XVisual2XRenderMask(handle, glXGetVisualFromFBConfig.getVisual(), xRenderPictFormat) : null;
        final int[] array = { 100000, 100001, 5, 6, 32, 8, 9, 10, 11, 14, 15, 16, 17, 12, 13 };
        final int n4 = b ? 0 : 2;
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(array);
        directIntBuffer.position(n4);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(directIntBuffer.remaining());
        final int glXGetFBConfigAttributes = GLX.glXGetFBConfigAttributes(handle, n, directIntBuffer, directIntBuffer2);
        if (glXGetFBConfigAttributes != 0) {
            throw new GLException("glXGetFBConfig(" + DefaultGraphicsConfiguration.toHexString(directIntBuffer.get(n4 + directIntBuffer2.get(0))) + ") failed: error code " + glXGetFBConfigErrorCode(glXGetFBConfigAttributes));
        }
        int n5 = 0;
        if (b) {
            x11GLCapabilities.setSampleBuffers(directIntBuffer2.get(n5++) != 0);
            x11GLCapabilities.setNumSamples(directIntBuffer2.get(n5++));
        }
        final short transparentAlphaValue = (short)((null != xRenderDirectFormat) ? xRenderDirectFormat.getAlphaMask() : 0);
        x11GLCapabilities.setBackgroundOpaque(0 >= transparentAlphaValue);
        if (!x11GLCapabilities.isBackgroundOpaque()) {
            x11GLCapabilities.setTransparentRedValue(xRenderDirectFormat.getRedMask());
            x11GLCapabilities.setTransparentGreenValue(xRenderDirectFormat.getGreenMask());
            x11GLCapabilities.setTransparentBlueValue(xRenderDirectFormat.getBlueMask());
            x11GLCapabilities.setTransparentAlphaValue(transparentAlphaValue);
        }
        x11GLCapabilities.setDoubleBuffered(directIntBuffer2.get(n5++) != 0);
        x11GLCapabilities.setStereo(directIntBuffer2.get(n5++) != 0);
        x11GLCapabilities.setHardwareAccelerated(directIntBuffer2.get(n5++) != 32769);
        x11GLCapabilities.setRedBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setGreenBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setBlueBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setAlphaBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setAccumRedBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setAccumGreenBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setAccumBlueBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setAccumAlphaBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setDepthBits(directIntBuffer2.get(n5++));
        x11GLCapabilities.setStencilBits(directIntBuffer2.get(n5++));
        return (X11GLCapabilities)GLGraphicsConfigurationUtil.fixWinAttribBitsAndHwAccel(x11GraphicsDevice, n3, x11GLCapabilities);
    }
    
    private static String glXGetFBConfigErrorCode(final int n) {
        switch (n) {
            case 3: {
                return "GLX_NO_EXTENSION";
            }
            case 2: {
                return "GLX_BAD_ATTRIBUTE";
            }
            default: {
                return "Unknown error code " + n;
            }
        }
    }
    
    static boolean glXGetFBConfig(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (n == 0L) {
            throw new GLException("No display connection");
        }
        final boolean b = 2 != GLX.glXGetFBConfigAttrib(n, n2, n3, intBuffer);
        if (!b && X11GLXGraphicsConfiguration.DEBUG) {
            System.err.println("X11GLXGraphicsConfiguration.glXGetFBConfig: FBConfig invalid: fbcfg: " + DefaultGraphicsConfiguration.toHexString(n2));
        }
        return b;
    }
    
    static int glXFBConfig2FBConfigID(final long n, final long n2) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        if (glXGetFBConfig(n, n2, 32787, directIntBuffer)) {
            return directIntBuffer.get(0);
        }
        return 0;
    }
    
    static long glXFBConfigID2FBConfig(final long n, final int n2, final int n3) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(new int[] { 32787, n3, 0 });
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(1);
        directIntBuffer2.put(0, -1);
        final PointerBuffer glXChooseFBConfig = GLX.glXChooseFBConfig(n, n2, directIntBuffer, directIntBuffer2);
        if (glXChooseFBConfig == null || glXChooseFBConfig.limit() < 1) {
            return 0L;
        }
        return glXChooseFBConfig.get(0);
    }
    
    static XVisualInfo XVisualID2XVisualInfo(final long n, final long visualid) {
        final int[] array = { 0 };
        final XVisualInfo create = XVisualInfo.create();
        create.setVisualid(visualid);
        final XVisualInfo[] xGetVisualInfo = X11Lib.XGetVisualInfo(n, 1L, create, array, 0);
        if (xGetVisualInfo == null || xGetVisualInfo.length == 0) {
            return null;
        }
        final XVisualInfo create2 = XVisualInfo.create(xGetVisualInfo[0]);
        if (X11GLXGraphicsConfiguration.DEBUG) {
            System.err.println("Fetched XVisualInfo for visual ID " + DefaultGraphicsConfiguration.toHexString(visualid));
            System.err.println("Resulting XVisualInfo: visualid = " + DefaultGraphicsConfiguration.toHexString(create2.getVisualid()));
        }
        return create2;
    }
    
    static X11GLCapabilities XVisualInfo2GLCapabilities(final X11GraphicsDevice x11GraphicsDevice, final GLProfile glProfile, final XVisualInfo xVisualInfo, final int n, final boolean b) {
        final int n2 = n & 0xB;
        if (n2 == 0) {
            return null;
        }
        final long handle = x11GraphicsDevice.getHandle();
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        if (glXGetConfig(handle, xVisualInfo, 1, directIntBuffer) == 0) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("Visual (" + DefaultGraphicsConfiguration.toHexString(xVisualInfo.getVisualid()) + ") does not support OpenGL");
            }
            return null;
        }
        if (glXGetConfig(handle, xVisualInfo, 4, directIntBuffer) == 0) {
            if (X11GLXGraphicsConfiguration.DEBUG) {
                System.err.println("Visual (" + DefaultGraphicsConfiguration.toHexString(xVisualInfo.getVisualid()) + ") does not support RGBA");
            }
            return null;
        }
        final X11GLCapabilities x11GLCapabilities = new X11GLCapabilities(xVisualInfo, glProfile);
        x11GLCapabilities.setDoubleBuffered(glXGetConfig(handle, xVisualInfo, 5, directIntBuffer) != 0);
        x11GLCapabilities.setStereo(glXGetConfig(handle, xVisualInfo, 6, directIntBuffer) != 0);
        if (b) {
            x11GLCapabilities.setSampleBuffers(glXGetConfig(handle, xVisualInfo, 100000, directIntBuffer) != 0);
            x11GLCapabilities.setNumSamples(glXGetConfig(handle, xVisualInfo, 100001, directIntBuffer));
        }
        final XRenderDirectFormat xRenderDirectFormat = (null != xVisualInfo) ? X11GraphicsConfiguration.XVisual2XRenderMask(handle, xVisualInfo.getVisual()) : null;
        final short transparentAlphaValue = (short)((null != xRenderDirectFormat) ? xRenderDirectFormat.getAlphaMask() : 0);
        x11GLCapabilities.setBackgroundOpaque(0 >= transparentAlphaValue);
        if (!x11GLCapabilities.isBackgroundOpaque()) {
            x11GLCapabilities.setTransparentRedValue(xRenderDirectFormat.getRedMask());
            x11GLCapabilities.setTransparentGreenValue(xRenderDirectFormat.getGreenMask());
            x11GLCapabilities.setTransparentBlueValue(xRenderDirectFormat.getBlueMask());
            x11GLCapabilities.setTransparentAlphaValue(transparentAlphaValue);
        }
        x11GLCapabilities.setHardwareAccelerated(true);
        x11GLCapabilities.setDepthBits(glXGetConfig(handle, xVisualInfo, 12, directIntBuffer));
        x11GLCapabilities.setStencilBits(glXGetConfig(handle, xVisualInfo, 13, directIntBuffer));
        x11GLCapabilities.setRedBits(glXGetConfig(handle, xVisualInfo, 8, directIntBuffer));
        x11GLCapabilities.setGreenBits(glXGetConfig(handle, xVisualInfo, 9, directIntBuffer));
        x11GLCapabilities.setBlueBits(glXGetConfig(handle, xVisualInfo, 10, directIntBuffer));
        x11GLCapabilities.setAlphaBits(glXGetConfig(handle, xVisualInfo, 11, directIntBuffer));
        x11GLCapabilities.setAccumRedBits(glXGetConfig(handle, xVisualInfo, 14, directIntBuffer));
        x11GLCapabilities.setAccumGreenBits(glXGetConfig(handle, xVisualInfo, 15, directIntBuffer));
        x11GLCapabilities.setAccumBlueBits(glXGetConfig(handle, xVisualInfo, 16, directIntBuffer));
        x11GLCapabilities.setAccumAlphaBits(glXGetConfig(handle, xVisualInfo, 17, directIntBuffer));
        return (X11GLCapabilities)GLGraphicsConfigurationUtil.fixWinAttribBitsAndHwAccel(x11GraphicsDevice, n2, x11GLCapabilities);
    }
    
    private static String glXGetConfigErrorCode(final int n) {
        switch (n) {
            case 3: {
                return "GLX_NO_EXTENSION";
            }
            case 1: {
                return "GLX_BAD_SCREEN";
            }
            case 2: {
                return "GLX_BAD_ATTRIBUTE";
            }
            case 4: {
                return "GLX_BAD_VISUAL";
            }
            default: {
                return "Unknown error code " + n;
            }
        }
    }
    
    static int glXGetConfig(final long n, final XVisualInfo xVisualInfo, final int n2, final IntBuffer intBuffer) {
        if (n == 0L) {
            throw new GLException("No display connection");
        }
        final int glXGetConfig = GLX.glXGetConfig(n, xVisualInfo, n2, intBuffer);
        if (glXGetConfig != 0) {
            throw new GLException("glXGetConfig(" + DefaultGraphicsConfiguration.toHexString(n2) + ") failed: error code " + glXGetConfigErrorCode(glXGetConfig));
        }
        return intBuffer.get(intBuffer.position());
    }
    
    @Override
    public String toString() {
        return "X11GLXGraphicsConfiguration[" + this.getScreen() + ", visualID " + DefaultGraphicsConfiguration.toHexString(this.getXVisualID()) + ", fbConfigID " + DefaultGraphicsConfiguration.toHexString(this.getFBConfigID()) + ",\n\trequested " + this.getRequestedCapabilities() + ",\n\tchosen    " + this.getChosenCapabilities() + "]";
    }
}
