// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import jogamp.nativewindow.windows.PIXELFORMATDESCRIPTOR;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLCapabilities;
import jogamp.nativewindow.windows.GDIUtil;
import jogamp.nativewindow.windows.GDI;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.NativeSurface;
import java.util.ArrayList;
import jogamp.opengl.GLContextImpl;
import jogamp.opengl.GLDrawableImpl;
import java.util.Comparator;
import java.util.Collections;
import java.util.Collection;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.windows.WindowsGraphicsDevice;
import com.jogamp.nativewindow.VisualIDHolder;
import jogamp.opengl.GLGraphicsConfigurationFactory;

public class WindowsWGLGraphicsConfigurationFactory extends GLGraphicsConfigurationFactory
{
    static VisualIDHolder.VIDComparator PfdIDComparator;
    
    static void registerFactory() {
        GraphicsConfigurationFactory.registerFactory(WindowsGraphicsDevice.class, GLCapabilitiesImmutable.class, new WindowsWGLGraphicsConfigurationFactory());
    }
    
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) {
        if (!(capabilitiesImmutable instanceof GLCapabilitiesImmutable)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilities objects - chosen");
        }
        if (!(capabilitiesImmutable2 instanceof GLCapabilitiesImmutable)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilities objects - requested");
        }
        if (capabilitiesChooser != null && !(capabilitiesChooser instanceof GLCapabilitiesChooser)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilitiesChooser objects");
        }
        return chooseGraphicsConfigurationStatic((GLCapabilitiesImmutable)capabilitiesImmutable, (GLCapabilitiesImmutable)capabilitiesImmutable2, (GLCapabilitiesChooser)capabilitiesChooser, abstractGraphicsScreen);
    }
    
    static WindowsWGLGraphicsConfiguration createDefaultGraphicsConfiguration(final GLCapabilitiesImmutable glCapabilitiesImmutable, final AbstractGraphicsScreen abstractGraphicsScreen) {
        return chooseGraphicsConfigurationStatic(glCapabilitiesImmutable, glCapabilitiesImmutable, null, abstractGraphicsScreen);
    }
    
    static WindowsWGLGraphicsConfiguration chooseGraphicsConfigurationStatic(GLCapabilitiesImmutable fixGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, AbstractGraphicsScreen default1) {
        if (null == default1) {
            default1 = DefaultGraphicsScreen.createDefault(NativeWindowFactory.TYPE_WINDOWS);
        }
        fixGLCapabilities = GLGraphicsConfigurationUtil.fixGLCapabilities(fixGLCapabilities, GLDrawableFactory.getDesktopFactory(), default1.getDevice());
        return new WindowsWGLGraphicsConfiguration(default1, fixGLCapabilities, glCapabilitiesImmutable, glCapabilitiesChooser);
    }
    
    protected static List<GLCapabilitiesImmutable> getAvailableCapabilities(final WindowsWGLDrawableFactory windowsWGLDrawableFactory, final AbstractGraphicsDevice abstractGraphicsDevice) {
        final WindowsWGLDrawableFactory.SharedResource orCreateSharedResourceImpl = windowsWGLDrawableFactory.getOrCreateSharedResourceImpl(abstractGraphicsDevice);
        if (null == orCreateSharedResourceImpl) {
            throw new GLException("Shared resource for device n/a: " + abstractGraphicsDevice);
        }
        final GLDrawableImpl drawable = orCreateSharedResourceImpl.getDrawable();
        final GLProfile default1 = GLProfile.getDefault(abstractGraphicsDevice);
        Object availableGLCapabilitiesARB = null;
        GLContextImpl context;
        if (windowsWGLDrawableFactory.hasRendererQuirk(abstractGraphicsDevice, null, 9)) {
            context = orCreateSharedResourceImpl.getContext();
            if (0 == context.makeCurrent()) {
                throw new GLException("Could not make Shared Context current: " + abstractGraphicsDevice);
            }
        }
        else {
            context = null;
            drawable.lockSurface();
        }
        try {
            final long handle = drawable.getHandle();
            if (0L == handle) {
                throw new GLException("Error: HDC is null");
            }
            if (orCreateSharedResourceImpl.hasARBPixelFormat()) {
                availableGLCapabilitiesARB = getAvailableGLCapabilitiesARB(orCreateSharedResourceImpl, orCreateSharedResourceImpl.getDevice(), default1, handle);
            }
            final boolean b = null != availableGLCapabilitiesARB && !((List)availableGLCapabilitiesARB).isEmpty();
            final List<GLCapabilitiesImmutable> availableGLCapabilitiesGDI = getAvailableGLCapabilitiesGDI(abstractGraphicsDevice, default1, handle, b);
            if (!b) {
                availableGLCapabilitiesARB = availableGLCapabilitiesGDI;
            }
            else {
                ((List<GLCapabilitiesImmutable>)availableGLCapabilitiesARB).addAll(availableGLCapabilitiesGDI);
            }
        }
        finally {
            if (null != context) {
                context.release();
            }
            else {
                drawable.unlockSurface();
            }
        }
        if (null != availableGLCapabilitiesARB && ((List)availableGLCapabilitiesARB).size() > 1) {
            Collections.sort((List<Object>)availableGLCapabilitiesARB, (Comparator<? super Object>)WindowsWGLGraphicsConfigurationFactory.PfdIDComparator);
        }
        return (List<GLCapabilitiesImmutable>)availableGLCapabilitiesARB;
    }
    
    private static List<GLCapabilitiesImmutable> getAvailableGLCapabilitiesARB(final WindowsWGLDrawableFactory.SharedResource sharedResource, final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n) {
        return WindowsWGLGraphicsConfiguration.wglARBPFIDs2GLCapabilities(sharedResource, abstractGraphicsDevice, glProfile, n, WindowsWGLGraphicsConfiguration.wglAllARBPFDIDs(WindowsWGLGraphicsConfiguration.wglARBPFDIDCount((WindowsWGLContext)sharedResource.getContext(), n)), 13, false);
    }
    
    private static List<GLCapabilitiesImmutable> getAvailableGLCapabilitiesGDI(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n, final boolean b) {
        final int[] wglAllGDIPFIDs = WindowsWGLGraphicsConfiguration.wglAllGDIPFIDs(n);
        final int length = wglAllGDIPFIDs.length;
        final ArrayList list = new ArrayList<WGLGLCapabilities>(length);
        for (int i = 0; i < length; ++i) {
            final WGLGLCapabilities pfd2GLCapabilities = WindowsWGLGraphicsConfiguration.PFD2GLCapabilities(abstractGraphicsDevice, glProfile, n, wglAllGDIPFIDs[i], b ? 2 : 15);
            if (null != pfd2GLCapabilities) {
                list.add(pfd2GLCapabilities);
            }
        }
        return (List<GLCapabilitiesImmutable>)list;
    }
    
    static void updateGraphicsConfiguration(final CapabilitiesChooser capabilitiesChooser, final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface, final int[] array) {
        if (capabilitiesChooser != null && !(capabilitiesChooser instanceof GLCapabilitiesChooser)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilitiesChooser objects");
        }
        if (glDrawableFactory == null) {
            throw new IllegalArgumentException("GLDrawableFactory is null");
        }
        if (nativeSurface == null) {
            throw new IllegalArgumentException("NativeSurface is null");
        }
        if (1 >= nativeSurface.lockSurface()) {
            throw new GLException("Surface not ready (lockSurface)");
        }
        try {
            final long surfaceHandle = nativeSurface.getSurfaceHandle();
            if (0L == surfaceHandle) {
                if (!(nativeSurface instanceof ProxySurface) || !((ProxySurface)nativeSurface).containsUpstreamOptionBits(512)) {
                    throw new GLException(String.format("non-surfaceless drawable has zero-handle (HDC): %s", nativeSurface.toString()));
                }
            }
            else {
                final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration = (WindowsWGLGraphicsConfiguration)nativeSurface.getGraphicsConfiguration();
                if (!windowsWGLGraphicsConfiguration.isExternal()) {
                    if (!windowsWGLGraphicsConfiguration.isDetermined()) {
                        updateGraphicsConfiguration(windowsWGLGraphicsConfiguration, capabilitiesChooser, glDrawableFactory, surfaceHandle, false, array);
                    }
                    else {
                        boolean b = false;
                        final int getPixelFormat;
                        if (1 > (getPixelFormat = WGLUtil.GetPixelFormat(surfaceHandle))) {
                            if (!WGLUtil.SetPixelFormat(surfaceHandle, windowsWGLGraphicsConfiguration.getPixelFormatID(), windowsWGLGraphicsConfiguration.getPixelFormat())) {
                                throw new GLException("Unable to set pixel format " + windowsWGLGraphicsConfiguration.getPixelFormatID() + " for device context " + GraphicsConfigurationFactory.toHexString(surfaceHandle) + ": error code " + GDI.GetLastError());
                            }
                            b = true;
                        }
                        if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                            System.err.println("setPixelFormat (post): hdc " + GraphicsConfigurationFactory.toHexString(surfaceHandle) + ", " + getPixelFormat + " -> " + windowsWGLGraphicsConfiguration.getPixelFormatID() + ", set: " + b);
                        }
                    }
                }
            }
        }
        finally {
            nativeSurface.unlockSurface();
        }
    }
    
    static void preselectGraphicsConfiguration(final CapabilitiesChooser capabilitiesChooser, final GLDrawableFactory glDrawableFactory, final AbstractGraphicsDevice abstractGraphicsDevice, final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration, final int[] array) {
        if (capabilitiesChooser != null && !(capabilitiesChooser instanceof GLCapabilitiesChooser)) {
            throw new IllegalArgumentException("This NativeWindowFactory accepts only GLCapabilitiesChooser objects");
        }
        if (glDrawableFactory == null) {
            throw new IllegalArgumentException("GLDrawableFactory is null");
        }
        if (windowsWGLGraphicsConfiguration == null) {
            throw new IllegalArgumentException("WindowsWGLGraphicsConfiguration is null");
        }
        if (!(glDrawableFactory instanceof WindowsWGLDrawableFactory)) {
            throw new GLException("GLDrawableFactory is not a WindowsWGLDrawableFactory, but: " + glDrawableFactory.getClass().getSimpleName());
        }
        final WindowsWGLDrawableFactory windowsWGLDrawableFactory = (WindowsWGLDrawableFactory)glDrawableFactory;
        final WindowsWGLDrawable orCreateSharedDrawable = windowsWGLDrawableFactory.getOrCreateSharedDrawable(abstractGraphicsDevice);
        if (null == orCreateSharedDrawable) {
            throw new IllegalArgumentException("Shared Drawable is null");
        }
        if (1 >= orCreateSharedDrawable.lockSurface()) {
            throw new GLException("Shared Surface not ready (lockSurface): " + abstractGraphicsDevice + " -> " + orCreateSharedDrawable);
        }
        try {
            final long handle = orCreateSharedDrawable.getHandle();
            if (0L == handle) {
                throw new GLException("Error: HDC is null");
            }
            updateGraphicsConfiguration(windowsWGLGraphicsConfiguration, capabilitiesChooser, windowsWGLDrawableFactory, handle, true, array);
        }
        finally {
            orCreateSharedDrawable.unlockSurface();
        }
    }
    
    private static void updateGraphicsConfiguration(final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration, final CapabilitiesChooser capabilitiesChooser, final GLDrawableFactory glDrawableFactory, final long n, final boolean b, final int[] array) {
        if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
            if (b) {
                System.err.println("updateGraphicsConfiguration(using shared): hdc " + GraphicsConfigurationFactory.toHexString(n));
            }
            else {
                System.err.println("updateGraphicsConfiguration(using target): hdc " + GraphicsConfigurationFactory.toHexString(n));
            }
            System.err.println("user chosen caps " + windowsWGLGraphicsConfiguration.getChosenCapabilities());
        }
        final AbstractGraphicsDevice device = windowsWGLGraphicsConfiguration.getScreen().getDevice();
        final WindowsWGLDrawableFactory.SharedResource orCreateSharedResourceImpl = ((WindowsWGLDrawableFactory)glDrawableFactory).getOrCreateSharedResourceImpl(device);
        GLContextImpl context;
        if (glDrawableFactory.hasRendererQuirk(device, null, 9)) {
            context = orCreateSharedResourceImpl.getContext();
            if (0 == context.makeCurrent()) {
                throw new GLException("Could not make Shared Context current: " + device);
            }
        }
        else {
            context = null;
        }
        try {
            final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)windowsWGLGraphicsConfiguration.getChosenCapabilities();
            boolean updateGraphicsConfigurationARB = false;
            if (glCapabilitiesImmutable.getHardwareAccelerated() && !glCapabilitiesImmutable.isBitmap()) {
                updateGraphicsConfigurationARB = updateGraphicsConfigurationARB((WindowsWGLDrawableFactory)glDrawableFactory, windowsWGLGraphicsConfiguration, capabilitiesChooser, n, b, array);
            }
            if (!updateGraphicsConfigurationARB) {
                updateGraphicsConfigurationGDI(windowsWGLGraphicsConfiguration, capabilitiesChooser, n, b, array);
            }
        }
        finally {
            if (null != context) {
                context.release();
            }
        }
    }
    
    private static boolean updateGraphicsConfigurationARB(final WindowsWGLDrawableFactory windowsWGLDrawableFactory, final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration, final CapabilitiesChooser capabilitiesChooser, final long n, final boolean b, int[] array) {
        final AbstractGraphicsDevice device = windowsWGLGraphicsConfiguration.getScreen().getDevice();
        final WindowsWGLDrawableFactory.SharedResource orCreateSharedResourceImpl = windowsWGLDrawableFactory.getOrCreateSharedResourceImpl(device);
        if (null == orCreateSharedResourceImpl) {
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("updateGraphicsConfigurationARB: SharedResource is null: " + device);
            }
            return false;
        }
        if (!orCreateSharedResourceImpl.hasARBPixelFormat()) {
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("updateGraphicsConfigurationARB: WGL_ARB_pixel_format not available");
            }
            return false;
        }
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)windowsWGLGraphicsConfiguration.getChosenCapabilities();
        final boolean b2 = glCapabilitiesImmutable.isBackgroundOpaque() && GDIUtil.DwmIsCompositionEnabled();
        final int n2 = GLGraphicsConfigurationUtil.getExclusiveWinAttributeBits(glCapabilitiesImmutable) & 0xFFFFFFFD;
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final int wglARBPFDIDCount = WindowsWGLGraphicsConfiguration.wglARBPFDIDCount((WindowsWGLContext)orCreateSharedResourceImpl.getContext(), n);
        if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("updateGraphicsConfigurationARB: hdc " + GraphicsConfigurationFactory.toHexString(n) + ", pfdIDCount(hdc) " + wglARBPFDIDCount + ", capsChosen " + glCapabilitiesImmutable + ", " + GLGraphicsConfigurationUtil.winAttributeBits2String(null, n2).toString());
            System.err.println("\tisOpaque " + b2 + " (translucency requested: " + !glCapabilitiesImmutable.isBackgroundOpaque() + ", compositioning enabled: " + GDIUtil.DwmIsCompositionEnabled() + ")");
            System.err.println("\textHDC " + b + ", chooser " + (null != capabilitiesChooser) + ", pformatsNum " + ((null != array) ? array.length : -1));
        }
        if (0 >= wglARBPFDIDCount) {
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("updateGraphicsConfigurationARB: failed due to 0 pfdIDs for hdc " + GraphicsConfigurationFactory.toHexString(n) + " - hdc incompatible w/ ARB ext.");
            }
            return false;
        }
        boolean b3 = false;
        final int n3 = b ? -1 : WGLUtil.GetPixelFormat(n);
        WGLGLCapabilities capsPFD;
        if (1 <= n3) {
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("updateGraphicsConfigurationARB: Pixel format already chosen for HDC: " + GraphicsConfigurationFactory.toHexString(n) + ", pixelformat " + n3);
            }
            b3 = true;
            capsPFD = (WGLGLCapabilities)GLGraphicsConfigurationUtil.fixOpaqueGLCapabilities(WindowsWGLGraphicsConfiguration.wglARBPFID2GLCapabilities(orCreateSharedResourceImpl, device, glProfile, n, n3, n2), b2);
        }
        else {
            int n4 = -1;
            if (null == array) {
                final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(512);
                final FloatBuffer directFloatBuffer = Buffers.newDirectFloatBuffer(1);
                array = WindowsWGLGraphicsConfiguration.wglChoosePixelFormatARB(orCreateSharedResourceImpl, device, glCapabilitiesImmutable, n, directIntBuffer, 8231, directFloatBuffer);
                if (null == array) {
                    array = WindowsWGLGraphicsConfiguration.wglChoosePixelFormatARB(orCreateSharedResourceImpl, device, glCapabilitiesImmutable, n, directIntBuffer, 8230, directFloatBuffer);
                }
                if (null == array) {
                    array = WindowsWGLGraphicsConfiguration.wglChoosePixelFormatARB(orCreateSharedResourceImpl, device, glCapabilitiesImmutable, n, directIntBuffer, -1, directFloatBuffer);
                }
                if (null != array) {
                    n4 = 0;
                }
                else {
                    if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("updateGraphicsConfigurationARB: wglChoosePixelFormatARB failed with: " + glCapabilitiesImmutable);
                    }
                    array = WindowsWGLGraphicsConfiguration.wglAllARBPFDIDs(wglARBPFDIDCount);
                    if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("updateGraphicsConfigurationARB: NumFormats (wglAllARBPFIDs) " + ((null != array) ? array.length : 0));
                    }
                }
                if (null == array) {
                    if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("updateGraphicsConfigurationARB: failed, return false");
                        ExceptionUtils.dumpStack(System.err);
                    }
                    return false;
                }
            }
            final boolean b4 = 0 <= n4 && null == capabilitiesChooser && glCapabilitiesImmutable.isBackgroundOpaque();
            final List<GLCapabilitiesImmutable> wglARBPFIDs2GLCapabilities = WindowsWGLGraphicsConfiguration.wglARBPFIDs2GLCapabilities(orCreateSharedResourceImpl, device, glProfile, n, array, n2, b4);
            if (null == wglARBPFIDs2GLCapabilities || 0 == wglARBPFIDs2GLCapabilities.size()) {
                if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("updateGraphicsConfigurationARB: wglARBPFIDs2GLCapabilities failed with " + array.length + " pfd ids");
                    ExceptionUtils.dumpStack(System.err);
                }
                return false;
            }
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("updateGraphicsConfigurationARB: " + array.length + " pfd ids, skipCapsChooser " + b4 + ", " + GLGraphicsConfigurationUtil.winAttributeBits2String(null, n2).toString() + ", " + wglARBPFIDs2GLCapabilities.size() + " glcaps");
                if (0 <= n4) {
                    System.err.println("updateGraphicsConfigurationARB: Used wglChoosePixelFormatARB to recommend pixel format " + array[n4] + ", idx " + n4 + ", " + wglARBPFIDs2GLCapabilities.get(n4));
                }
            }
            int chooseCapabilities;
            if (b4) {
                chooseCapabilities = n4;
            }
            else {
                chooseCapabilities = GLGraphicsConfigurationFactory.chooseCapabilities(capabilitiesChooser, glCapabilitiesImmutable, wglARBPFIDs2GLCapabilities, n4);
            }
            if (0 > chooseCapabilities) {
                if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                    ExceptionUtils.dumpStack(System.err);
                }
                return false;
            }
            final WGLGLCapabilities wglglCapabilities = wglARBPFIDs2GLCapabilities.get(chooseCapabilities);
            if (null == wglglCapabilities) {
                throw new GLException("Null Capabilities with  chosen pfdID: native recommended " + (n4 + 1) + " chosen idx " + chooseCapabilities + ", skipCapsChooser " + b4);
            }
            capsPFD = (WGLGLCapabilities)GLGraphicsConfigurationUtil.fixOpaqueGLCapabilities(wglglCapabilities, b2);
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("chosen pfdID (ARB): native recommended " + (n4 + 1) + " chosen " + capsPFD + ", skipCapsChooser " + b4);
            }
        }
        if (!b && !b3) {
            windowsWGLGraphicsConfiguration.setPixelFormat(n, capsPFD);
        }
        else {
            windowsWGLGraphicsConfiguration.setCapsPFD(capsPFD);
        }
        return true;
    }
    
    private static boolean updateGraphicsConfigurationGDI(final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration, final CapabilitiesChooser capabilitiesChooser, final long n, final boolean b, int[] wglAllGDIPFIDs) {
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)windowsWGLGraphicsConfiguration.getChosenCapabilities();
        if (!glCapabilitiesImmutable.isOnscreen() && glCapabilitiesImmutable.isPBuffer()) {
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("updateGraphicsConfigurationGDI: no pbuffer supported on GDI: " + glCapabilitiesImmutable);
            }
            return false;
        }
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final int exclusiveWinAttributeBits = GLGraphicsConfigurationUtil.getExclusiveWinAttributeBits(glCapabilitiesImmutable);
        if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("updateGraphicsConfigurationGDI: hdc " + GraphicsConfigurationFactory.toHexString(n) + ", capsChosen " + glCapabilitiesImmutable + ", " + GLGraphicsConfigurationUtil.winAttributeBits2String(null, exclusiveWinAttributeBits).toString());
            System.err.println("\textHDC " + b + ", chooser " + (null != capabilitiesChooser) + ", pformatsNum " + ((null != wglAllGDIPFIDs) ? wglAllGDIPFIDs.length : -1));
        }
        final AbstractGraphicsDevice device = windowsWGLGraphicsConfiguration.getScreen().getDevice();
        boolean b2 = false;
        final int n2 = b ? -1 : WGLUtil.GetPixelFormat(n);
        WGLGLCapabilities pfd2GLCapabilities;
        if (1 <= n2) {
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("updateGraphicsConfigurationGDI: NOTE: pixel format already chosen for HDC: " + GraphicsConfigurationFactory.toHexString(n) + ", pixelformat " + n2);
            }
            b2 = true;
            pfd2GLCapabilities = WindowsWGLGraphicsConfiguration.PFD2GLCapabilities(device, glProfile, n, n2, exclusiveWinAttributeBits);
            if (null == pfd2GLCapabilities) {
                throw new GLException("Could not map PFD2GLCaps w/ already chosen pfdID " + n2);
            }
        }
        else {
            final boolean b3 = null != wglAllGDIPFIDs;
            if (!b3) {
                wglAllGDIPFIDs = WindowsWGLGraphicsConfiguration.wglAllGDIPFIDs(n);
            }
            final ArrayList<WGLGLCapabilities> list = new ArrayList<WGLGLCapabilities>();
            final PIXELFORMATDESCRIPTOR glCapabilities2PFD = WindowsWGLGraphicsConfiguration.GLCapabilities2PFD(glCapabilitiesImmutable, WindowsWGLGraphicsConfiguration.createPixelFormatDescriptor());
            int choosePixelFormat = WGLUtil.ChoosePixelFormat(n, glCapabilities2PFD);
            int n3 = -1;
            boolean b5;
            if (1 <= choosePixelFormat) {
                final boolean b4 = null == capabilitiesChooser && glCapabilitiesImmutable.isBackgroundOpaque();
                int n4;
                for (n4 = wglAllGDIPFIDs.length - 1; 0 <= n4 && choosePixelFormat != wglAllGDIPFIDs[n4]; --n4) {}
                if (0 <= n4) {
                    if (b4) {
                        final WGLGLCapabilities pfd2GLCapabilities2 = WindowsWGLGraphicsConfiguration.PFD2GLCapabilities(device, glProfile, n, choosePixelFormat, exclusiveWinAttributeBits);
                        if (null != pfd2GLCapabilities2) {
                            list.add(pfd2GLCapabilities2);
                            n3 = 0;
                            b5 = true;
                        }
                        else {
                            b5 = false;
                        }
                    }
                    else {
                        b5 = false;
                    }
                    if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("Chosen PFDID " + choosePixelFormat + " (idx " + n4 + ") -> recommendedIndex " + n3 + ", skipCapsChooser " + b5);
                    }
                }
                else {
                    if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("Chosen PFDID " + choosePixelFormat + " (idx " + n4 + "), but not found in available caps (use given pfdIDs " + b3 + ", reqPFDCaps " + WindowsWGLGraphicsConfiguration.PFD2GLCapabilitiesNoCheck(device, glProfile, glCapabilities2PFD, choosePixelFormat) + ", chosenCaps: " + WindowsWGLGraphicsConfiguration.PFD2GLCapabilities(device, glProfile, n, choosePixelFormat, exclusiveWinAttributeBits));
                    }
                    choosePixelFormat = 0;
                    b5 = false;
                }
            }
            else {
                b5 = false;
            }
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("updateGraphicsConfigurationGDI: ChoosePixelFormat(HDC " + GraphicsConfigurationFactory.toHexString(n) + ") = pfdID " + choosePixelFormat + ", skipCapsChooser " + b5 + ", idx " + n3 + " (LastError: " + GDI.GetLastError() + ")");
            }
            if (!b5) {
                for (int i = 0; i < wglAllGDIPFIDs.length; ++i) {
                    final WGLGLCapabilities pfd2GLCapabilities3 = WindowsWGLGraphicsConfiguration.PFD2GLCapabilities(device, glProfile, n, wglAllGDIPFIDs[i], exclusiveWinAttributeBits);
                    if (null != pfd2GLCapabilities3) {
                        list.add(pfd2GLCapabilities3);
                        if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                            System.err.println("updateGraphicsConfigurationGDI: availableCaps[" + i + " -> " + (list.size() - 1) + "]: " + pfd2GLCapabilities3);
                        }
                    }
                    else if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("updateGraphicsConfigurationGDI: availableCaps[" + i + " -> skip]: pfdID " + wglAllGDIPFIDs[i] + ", " + WindowsWGLGraphicsConfiguration.PFD2GLCapabilitiesNoCheck(device, glProfile, n, wglAllGDIPFIDs[i]));
                    }
                }
                if (1 <= choosePixelFormat && 0 > n3) {
                    for (n3 = list.size() - 1; 0 <= n3 && choosePixelFormat != ((WGLGLCapabilities)list.get(n3)).getPFDID(); --n3) {}
                }
            }
            int chooseCapabilities;
            if (b5) {
                chooseCapabilities = n3;
            }
            else {
                chooseCapabilities = GLGraphicsConfigurationFactory.chooseCapabilities(capabilitiesChooser, glCapabilitiesImmutable, list, n3);
            }
            if (0 > chooseCapabilities) {
                if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("updateGraphicsConfigurationGDI: failed, return false");
                    ExceptionUtils.dumpStack(System.err);
                }
                return false;
            }
            pfd2GLCapabilities = (WGLGLCapabilities)list.get(chooseCapabilities);
            if (WindowsWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("chosen pfdID (GDI): recommendedIndex " + n3 + " -> chosenIndex " + chooseCapabilities + ", skipCapsChooser " + b5 + ", caps " + pfd2GLCapabilities + " (" + WGLGLCapabilities.PFD2String(pfd2GLCapabilities.getPFD(), pfd2GLCapabilities.getPFDID()) + ")");
            }
        }
        if (!b && !b2) {
            windowsWGLGraphicsConfiguration.setPixelFormat(n, pfd2GLCapabilities);
        }
        else {
            windowsWGLGraphicsConfiguration.setCapsPFD(pfd2GLCapabilities);
        }
        return true;
    }
    
    static {
        WindowsWGLGraphicsConfigurationFactory.PfdIDComparator = new VisualIDHolder.VIDComparator(VisualIDHolder.VIDType.WIN32_PFD);
    }
}
