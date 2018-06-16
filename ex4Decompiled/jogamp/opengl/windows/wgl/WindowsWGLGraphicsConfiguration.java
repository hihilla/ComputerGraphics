// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLRendererQuirks;
import jogamp.opengl.GLGraphicsConfigurationUtil;
import java.util.ArrayList;
import java.util.List;
import java.nio.FloatBuffer;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import jogamp.nativewindow.windows.PIXELFORMATDESCRIPTOR;
import jogamp.nativewindow.windows.GDIUtil;
import jogamp.nativewindow.windows.GDI;
import com.jogamp.nativewindow.DefaultGraphicsConfiguration;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;

public class WindowsWGLGraphicsConfiguration extends MutableGraphicsConfiguration implements Cloneable
{
    protected static final int MAX_PFORMATS = 256;
    protected static final int MAX_ATTRIBS = 256;
    private final GLCapabilitiesChooser chooser;
    private boolean isDetermined;
    private boolean isExternal;
    
    WindowsWGLGraphicsConfiguration(final AbstractGraphicsScreen abstractGraphicsScreen, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser chooser) {
        super(abstractGraphicsScreen, glCapabilitiesImmutable, glCapabilitiesImmutable2);
        this.isDetermined = false;
        this.isExternal = false;
        this.chooser = chooser;
        this.isDetermined = false;
    }
    
    WindowsWGLGraphicsConfiguration(final AbstractGraphicsScreen abstractGraphicsScreen, final WGLGLCapabilities capsPFD, final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        super(abstractGraphicsScreen, capsPFD, glCapabilitiesImmutable);
        this.isDetermined = false;
        this.isExternal = false;
        this.setCapsPFD(capsPFD);
        this.chooser = null;
    }
    
    static WindowsWGLGraphicsConfiguration createFromExternal(final GLDrawableFactory glDrawableFactory, final long n, final int n2, GLProfile default1, final AbstractGraphicsScreen abstractGraphicsScreen, final boolean b) {
        if (glDrawableFactory == null) {
            throw new GLException("Null factory");
        }
        if (n == 0L) {
            throw new GLException("Null HDC");
        }
        if (n2 <= 0) {
            throw new GLException("Invalid pixelformat id " + n2);
        }
        if (null == default1) {
            default1 = GLProfile.getDefault(abstractGraphicsScreen.getDevice());
        }
        final WindowsWGLDrawableFactory windowsWGLDrawableFactory = (WindowsWGLDrawableFactory)glDrawableFactory;
        final AbstractGraphicsDevice device = abstractGraphicsScreen.getDevice();
        final WindowsWGLDrawableFactory.SharedResource orCreateSharedResourceImpl = windowsWGLDrawableFactory.getOrCreateSharedResourceImpl(device);
        final boolean b2 = null != orCreateSharedResourceImpl && orCreateSharedResourceImpl.hasARBPixelFormat();
        WGLGLCapabilities wglglCapabilities;
        if (b2) {
            wglglCapabilities = wglARBPFID2GLCapabilities(orCreateSharedResourceImpl, device, default1, n, n2, 15);
        }
        else {
            wglglCapabilities = PFD2GLCapabilities(device, default1, n, n2, 15);
        }
        if (null == wglglCapabilities) {
            throw new GLException("Couldn't choose Capabilities by: HDC 0x" + Long.toHexString(n) + ", pfdID " + n2 + ", onscreen " + b + ", hasARB " + b2);
        }
        final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration = new WindowsWGLGraphicsConfiguration(abstractGraphicsScreen, wglglCapabilities, wglglCapabilities);
        windowsWGLGraphicsConfiguration.markExternal();
        return windowsWGLGraphicsConfiguration;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    public final void updateGraphicsConfiguration(final GLDrawableFactory glDrawableFactory, final NativeSurface nativeSurface, final int[] array) {
        WindowsWGLGraphicsConfigurationFactory.updateGraphicsConfiguration(this.chooser, glDrawableFactory, nativeSurface, array);
    }
    
    public final void preselectGraphicsConfiguration(final GLDrawableFactory glDrawableFactory, final int[] array) {
        WindowsWGLGraphicsConfigurationFactory.preselectGraphicsConfiguration(this.chooser, glDrawableFactory, this.getScreen().getDevice(), this, array);
    }
    
    final void setPixelFormat(final long n, final WGLGLCapabilities capsPFD) {
        if (0L == n) {
            throw new GLException("Error: HDC is null");
        }
        if (!WGLUtil.SetPixelFormat(n, capsPFD.getPFDID(), capsPFD.getPFD())) {
            throw new GLException("Unable to set pixel format " + capsPFD.getPFDID() + " of " + capsPFD + " for device context " + DefaultGraphicsConfiguration.toHexString(n) + ": error code " + GDI.GetLastError());
        }
        if (!capsPFD.isBackgroundOpaque()) {
            GDIUtil.DwmSetupTranslucency(GDI.WindowFromDC(n), true);
        }
        if (WindowsWGLGraphicsConfiguration.DEBUG) {
            System.err.println("setPixelFormat: hdc " + DefaultGraphicsConfiguration.toHexString(n) + ", " + capsPFD);
        }
        this.setCapsPFD(capsPFD);
    }
    
    final void setCapsPFD(final WGLGLCapabilities chosenCapabilities) {
        this.setChosenCapabilities(chosenCapabilities);
        this.isDetermined = true;
        if (WindowsWGLGraphicsConfiguration.DEBUG) {
            System.err.println("*** setCapsPFD: " + chosenCapabilities);
        }
    }
    
    public final boolean isExternal() {
        return this.isExternal;
    }
    
    final void markExternal() {
        this.isExternal = true;
    }
    
    public final boolean isDetermined() {
        return this.isDetermined;
    }
    
    public final PIXELFORMATDESCRIPTOR getPixelFormat() {
        return this.isDetermined ? ((WGLGLCapabilities)this.capabilitiesChosen).getPFD() : null;
    }
    
    public final int getPixelFormatID() {
        return this.isDetermined ? ((WGLGLCapabilities)this.capabilitiesChosen).getPFDID() : 0;
    }
    
    public final boolean isChoosenByARB() {
        return this.isDetermined && ((WGLGLCapabilities)this.capabilitiesChosen).isSetByARB();
    }
    
    static int fillAttribsForGeneralWGLARBQuery(final WindowsWGLDrawableFactory.SharedResource sharedResource, final IntBuffer intBuffer) {
        int n = 0;
        intBuffer.put(n++, 8193);
        if (sharedResource.hasARBPBuffer()) {
            intBuffer.put(n++, 8237);
        }
        intBuffer.put(n++, 8194);
        intBuffer.put(n++, 8195);
        intBuffer.put(n++, 8208);
        intBuffer.put(n++, 8226);
        intBuffer.put(n++, 8227);
        intBuffer.put(n++, 8209);
        intBuffer.put(n++, 8210);
        intBuffer.put(n++, 8211);
        intBuffer.put(n++, 8213);
        intBuffer.put(n++, 8215);
        intBuffer.put(n++, 8217);
        intBuffer.put(n++, 8219);
        intBuffer.put(n++, 8222);
        intBuffer.put(n++, 8223);
        intBuffer.put(n++, 8224);
        intBuffer.put(n++, 8225);
        if (sharedResource.hasARBMultisample()) {
            intBuffer.put(n++, 8257);
            intBuffer.put(n++, 8258);
        }
        return n;
    }
    
    static boolean wglARBPFIDValid(final WindowsWGLContext windowsWGLContext, final long n, final int n2) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(1);
        directIntBuffer2.put(0, 8212);
        return windowsWGLContext.getWGLExt().wglGetPixelFormatAttribivARB(n, n2, 0, 1, directIntBuffer2, directIntBuffer) || GDI.GetLastError() == 0;
    }
    
    static int wglARBPFDIDCount(final WindowsWGLContext windowsWGLContext, final long n) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(1);
        directIntBuffer2.put(0, 8192);
        if (!windowsWGLContext.getWGLExt().wglGetPixelFormatAttribivARB(n, 1, 0, 1, directIntBuffer2, directIntBuffer)) {
            if (WindowsWGLGraphicsConfiguration.DEBUG) {
                System.err.println("GetPixelFormatAttribivARB: Failed - HDC 0x" + Long.toHexString(n) + ", value " + directIntBuffer.get(0) + ", LastError: " + GDI.GetLastError());
                ExceptionUtils.dumpStack(System.err);
            }
            return 0;
        }
        final int value = directIntBuffer.get(0);
        if (value == 0 && WindowsWGLGraphicsConfiguration.DEBUG) {
            System.err.println("GetPixelFormatAttribivARB: No formats - HDC 0x" + Long.toHexString(n) + ", LastError: " + GDI.GetLastError());
            ExceptionUtils.dumpStack(System.err);
        }
        return value;
    }
    
    static int[] wglAllARBPFDIDs(final int n) {
        final int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = 1 + i;
        }
        return array;
    }
    
    static WGLGLCapabilities wglARBPFID2GLCapabilities(final WindowsWGLDrawableFactory.SharedResource sharedResource, final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n, final int n2, final int n3) {
        if (!sharedResource.hasARBPixelFormat()) {
            return null;
        }
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(512);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(512);
        final int fillAttribsForGeneralWGLARBQuery = fillAttribsForGeneralWGLARBQuery(sharedResource, directIntBuffer);
        if (!((WindowsWGLContext)sharedResource.getContext()).getWGLExt().wglGetPixelFormatAttribivARB(n, n2, 0, fillAttribsForGeneralWGLARBQuery, directIntBuffer, directIntBuffer2)) {
            throw new GLException("wglARBPFID2GLCapabilities: Error getting pixel format attributes for pixel format " + n2 + " of device context " + DefaultGraphicsConfiguration.toHexString(n) + ", werr " + GDI.GetLastError());
        }
        return AttribList2GLCapabilities(abstractGraphicsDevice, glProfile, n, n2, directIntBuffer, fillAttribsForGeneralWGLARBQuery, directIntBuffer2, n3);
    }
    
    static WGLGLCapabilities wglARBPFID2GLCapabilitiesNoCheck(final WindowsWGLDrawableFactory.SharedResource sharedResource, final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n, final int n2, final int n3) {
        if (!sharedResource.hasARBPixelFormat()) {
            return null;
        }
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(512);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(512);
        final int fillAttribsForGeneralWGLARBQuery = fillAttribsForGeneralWGLARBQuery(sharedResource, directIntBuffer);
        if (!((WindowsWGLContext)sharedResource.getContext()).getWGLExt().wglGetPixelFormatAttribivARB(n, n2, 0, fillAttribsForGeneralWGLARBQuery, directIntBuffer, directIntBuffer2)) {
            throw new GLException("wglARBPFID2GLCapabilities: Error getting pixel format attributes for pixel format " + n2 + " of device context " + DefaultGraphicsConfiguration.toHexString(n) + ", werr " + GDI.GetLastError());
        }
        return AttribList2GLCapabilitiesNoCheck(abstractGraphicsDevice, glProfile, n, n2, directIntBuffer, fillAttribsForGeneralWGLARBQuery, directIntBuffer2, n3);
    }
    
    static int[] wglChoosePixelFormatARB(final WindowsWGLDrawableFactory.SharedResource sharedResource, final AbstractGraphicsDevice abstractGraphicsDevice, final GLCapabilitiesImmutable glCapabilitiesImmutable, final long n, final IntBuffer intBuffer, final int n2, final FloatBuffer floatBuffer) {
        if (!GLCapabilities2AttribList(sharedResource, glCapabilitiesImmutable, intBuffer, n2, null)) {
            if (WindowsWGLGraphicsConfiguration.DEBUG) {
                System.err.println("wglChoosePixelFormatARB: GLCapabilities2AttribList failed: " + GDI.GetLastError());
                ExceptionUtils.dumpStack(System.err);
            }
            return null;
        }
        final WGLExt wglExt = ((WindowsWGLContext)sharedResource.getContext()).getWGLExt();
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(256);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(1);
        if (!wglExt.wglChoosePixelFormatARB(n, intBuffer, floatBuffer, 256, directIntBuffer, directIntBuffer2)) {
            if (WindowsWGLGraphicsConfiguration.DEBUG) {
                System.err.println("wglChoosePixelFormatARB: wglChoosePixelFormatARB failed: " + GDI.GetLastError());
                ExceptionUtils.dumpStack(System.err);
            }
            return null;
        }
        final int min = Math.min(directIntBuffer2.get(0), 256);
        int[] array;
        if (0 < min) {
            array = new int[min];
            directIntBuffer.get(array, 0, min);
        }
        else {
            array = null;
        }
        if (WindowsWGLGraphicsConfiguration.DEBUG) {
            System.err.println("wglChoosePixelFormatARB: NumFormats (wglChoosePixelFormatARB) accelMode 0x" + Integer.toHexString(n2) + ": " + min);
            for (int i = 0; i < min; ++i) {
                System.err.println("pixel format " + array[i] + " (index " + i + "): " + wglARBPFID2GLCapabilities(sharedResource, abstractGraphicsDevice, glCapabilitiesImmutable.getGLProfile(), n, array[i], 15));
            }
        }
        return array;
    }
    
    static List<GLCapabilitiesImmutable> wglARBPFIDs2GLCapabilities(final WindowsWGLDrawableFactory.SharedResource sharedResource, final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n, final int[] array, final int n2, final boolean b) {
        if (!sharedResource.hasARBPixelFormat()) {
            return null;
        }
        final int length = array.length;
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(512);
        final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(512);
        final int fillAttribsForGeneralWGLARBQuery = fillAttribsForGeneralWGLARBQuery(sharedResource, directIntBuffer);
        final ArrayList<WGLGLCapabilities> list = (ArrayList<WGLGLCapabilities>)new ArrayList<GLCapabilitiesImmutable>();
        for (int i = 0; i < length; ++i) {
            if (array[i] >= 1 && ((WindowsWGLContext)sharedResource.getContext()).getWGLExt().wglGetPixelFormatAttribivARB(n, array[i], 0, fillAttribsForGeneralWGLARBQuery, directIntBuffer, directIntBuffer2)) {
                final WGLGLCapabilities attribList2GLCapabilities = AttribList2GLCapabilities(abstractGraphicsDevice, glProfile, n, array[i], directIntBuffer, fillAttribsForGeneralWGLARBQuery, directIntBuffer2, n2);
                if (null != attribList2GLCapabilities) {
                    list.add(attribList2GLCapabilities);
                    if (WindowsWGLGraphicsConfiguration.DEBUG) {
                        System.err.println("wglARBPFIDs2GLCapabilities: bucket[" + i + " -> " + (list.size() - 1) + "]: " + attribList2GLCapabilities);
                    }
                    if (b) {
                        break;
                    }
                }
                else if (WindowsWGLGraphicsConfiguration.DEBUG) {
                    System.err.println("wglARBPFIDs2GLCapabilities: bucket[" + i + " -> skip]: pfdID " + array[i] + ", " + AttribList2GLCapabilitiesNoCheck(abstractGraphicsDevice, glProfile, n, array[i], directIntBuffer, fillAttribsForGeneralWGLARBQuery, directIntBuffer2, 15) + ", winattr " + GLGraphicsConfigurationUtil.winAttributeBits2String(null, n2).toString());
                }
            }
            else if (WindowsWGLGraphicsConfiguration.DEBUG) {
                if (1 > array[i]) {
                    System.err.println("wglARBPFIDs2GLCapabilities: Invalid pfdID " + i + "/" + length + ": " + array[i]);
                }
                else {
                    System.err.println("wglARBPFIDs2GLCapabilities: Cannot get pixel format attributes for pixel format " + i + "/" + length + ": " + array[i] + ", hdc " + DefaultGraphicsConfiguration.toHexString(n));
                }
            }
        }
        return (List<GLCapabilitiesImmutable>)list;
    }
    
    static boolean GLCapabilities2AttribList(final WindowsWGLDrawableFactory.SharedResource sharedResource, final GLCapabilitiesImmutable glCapabilitiesImmutable, final IntBuffer intBuffer, final int n, final int[] array) throws GLException {
        if (!sharedResource.hasARBPixelFormat()) {
            return false;
        }
        int n2 = 0;
        intBuffer.put(n2++, 8208);
        intBuffer.put(n2++, 1);
        if (n > 0) {
            intBuffer.put(n2++, 8195);
            intBuffer.put(n2++, n);
        }
        final boolean b = glCapabilitiesImmutable.isPBuffer() && sharedResource.hasARBPBuffer();
        int n3;
        if (glCapabilitiesImmutable.isOnscreen()) {
            n3 = 8193;
        }
        else if (glCapabilitiesImmutable.isFBO()) {
            n3 = 8193;
        }
        else if (b) {
            n3 = 8237;
        }
        else {
            if (!glCapabilitiesImmutable.isBitmap()) {
                throw new GLException("no surface type set in caps: " + glCapabilitiesImmutable);
            }
            n3 = 8194;
        }
        intBuffer.put(n2++, n3);
        intBuffer.put(n2++, 1);
        intBuffer.put(n2++, 8209);
        if (glCapabilitiesImmutable.getDoubleBuffered()) {
            intBuffer.put(n2++, 1);
        }
        else {
            intBuffer.put(n2++, 0);
        }
        intBuffer.put(n2++, 8210);
        if (glCapabilitiesImmutable.getStereo()) {
            intBuffer.put(n2++, 1);
        }
        else {
            intBuffer.put(n2++, 0);
        }
        intBuffer.put(n2++, 8213);
        intBuffer.put(n2++, glCapabilitiesImmutable.getRedBits());
        intBuffer.put(n2++, 8215);
        intBuffer.put(n2++, glCapabilitiesImmutable.getGreenBits());
        intBuffer.put(n2++, 8217);
        intBuffer.put(n2++, glCapabilitiesImmutable.getBlueBits());
        if (glCapabilitiesImmutable.getAlphaBits() > 0) {
            intBuffer.put(n2++, 8219);
            intBuffer.put(n2++, glCapabilitiesImmutable.getAlphaBits());
        }
        if (glCapabilitiesImmutable.getStencilBits() > 0) {
            intBuffer.put(n2++, 8227);
            intBuffer.put(n2++, glCapabilitiesImmutable.getStencilBits());
        }
        intBuffer.put(n2++, 8226);
        intBuffer.put(n2++, glCapabilitiesImmutable.getDepthBits());
        if (glCapabilitiesImmutable.getAccumRedBits() > 0 || glCapabilitiesImmutable.getAccumGreenBits() > 0 || glCapabilitiesImmutable.getAccumBlueBits() > 0 || glCapabilitiesImmutable.getAccumAlphaBits() > 0) {
            final GLRendererQuirks rendererQuirks = sharedResource.getRendererQuirks(null);
            if (!b || null == rendererQuirks || !rendererQuirks.exist(19)) {
                intBuffer.put(n2++, 8221);
                intBuffer.put(n2++, glCapabilitiesImmutable.getAccumRedBits() + glCapabilitiesImmutable.getAccumGreenBits() + glCapabilitiesImmutable.getAccumBlueBits() + glCapabilitiesImmutable.getAccumAlphaBits());
                intBuffer.put(n2++, 8222);
                intBuffer.put(n2++, glCapabilitiesImmutable.getAccumRedBits());
                intBuffer.put(n2++, 8223);
                intBuffer.put(n2++, glCapabilitiesImmutable.getAccumGreenBits());
                intBuffer.put(n2++, 8224);
                intBuffer.put(n2++, glCapabilitiesImmutable.getAccumBlueBits());
                intBuffer.put(n2++, 8225);
                intBuffer.put(n2++, glCapabilitiesImmutable.getAccumAlphaBits());
            }
        }
        if (glCapabilitiesImmutable.getSampleBuffers() && sharedResource.hasARBMultisample()) {
            intBuffer.put(n2++, 8257);
            intBuffer.put(n2++, 1);
            intBuffer.put(n2++, 8258);
            intBuffer.put(n2++, glCapabilitiesImmutable.getNumSamples());
        }
        intBuffer.put(n2++, 8211);
        intBuffer.put(n2++, 8235);
        intBuffer.put(n2++, 0);
        return true;
    }
    
    static int AttribList2DrawableTypeBits(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            switch (intBuffer.get(i)) {
                case 8193: {
                    if (intBuffer2.get(i) == 1) {
                        n2 |= 0x9;
                        break;
                    }
                    break;
                }
                case 8194: {
                    if (intBuffer2.get(i) == 1) {
                        n2 |= 0x2;
                        break;
                    }
                    break;
                }
                case 8237: {
                    if (intBuffer2.get(i) == 1) {
                        n2 |= 0x4;
                        break;
                    }
                    break;
                }
            }
        }
        return n2;
    }
    
    static WGLGLCapabilities AttribList2GLCapabilities(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n, final int n2, final IntBuffer intBuffer, final int n3, final IntBuffer intBuffer2, final int n4) {
        int n5 = n4 & AttribList2DrawableTypeBits(intBuffer, n3, intBuffer2);
        if (n5 == 0) {
            return null;
        }
        final PIXELFORMATDESCRIPTOR pixelFormatDescriptor = createPixelFormatDescriptor();
        if (WGLUtil.DescribePixelFormat(n, n2, PIXELFORMATDESCRIPTOR.size(), pixelFormatDescriptor) == 0) {
            n5 &= 0xFFFFFFF4;
            if (n5 == 0) {
                return null;
            }
        }
        final WGLGLCapabilities wglglCapabilities = new WGLGLCapabilities(pixelFormatDescriptor, n2, glProfile);
        wglglCapabilities.setValuesByARB(intBuffer, n3, intBuffer2);
        return (WGLGLCapabilities)GLGraphicsConfigurationUtil.fixWinAttribBitsAndHwAccel(abstractGraphicsDevice, n5, wglglCapabilities);
    }
    
    static WGLGLCapabilities AttribList2GLCapabilitiesNoCheck(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n, final int n2, final IntBuffer intBuffer, final int n3, final IntBuffer intBuffer2, final int n4) {
        final int n5 = n4 & AttribList2DrawableTypeBits(intBuffer, n3, intBuffer2);
        if (n5 == 0) {
            return null;
        }
        final PIXELFORMATDESCRIPTOR pixelFormatDescriptor = createPixelFormatDescriptor();
        WGLUtil.DescribePixelFormat(n, n2, PIXELFORMATDESCRIPTOR.size(), pixelFormatDescriptor);
        final WGLGLCapabilities wglglCapabilities = new WGLGLCapabilities(pixelFormatDescriptor, n2, glProfile);
        wglglCapabilities.setValuesByARB(intBuffer, n3, intBuffer2);
        return (WGLGLCapabilities)GLGraphicsConfigurationUtil.fixWinAttribBitsAndHwAccel(abstractGraphicsDevice, n5, wglglCapabilities);
    }
    
    static int[] wglAllGDIPFIDs(final long n) {
        final int describePixelFormat = WGLUtil.DescribePixelFormat(n, 1, 0, null);
        if (describePixelFormat == 0) {
            throw new GLException("DescribePixelFormat: No formats - HDC 0x" + Long.toHexString(n) + ", LastError: " + GDI.GetLastError());
        }
        final int[] array = new int[describePixelFormat];
        for (int i = 0; i < describePixelFormat; ++i) {
            array[i] = 1 + i;
        }
        return array;
    }
    
    static int PFD2DrawableTypeBits(final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        int n = 0;
        final int dwFlags = pixelformatdescriptor.getDwFlags();
        if (0x0 != (0x4 & dwFlags)) {
            n |= 0x9;
        }
        if (0x0 != (0x8 & dwFlags)) {
            n |= 0x2;
        }
        return n;
    }
    
    static WGLGLCapabilities PFD2GLCapabilities(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n, final int n2, final int n3) {
        final PIXELFORMATDESCRIPTOR pixelFormatDescriptor = createPixelFormatDescriptor(n, n2);
        if (null == pixelFormatDescriptor) {
            return null;
        }
        if ((pixelFormatDescriptor.getDwFlags() & 0x20) == 0x0) {
            return null;
        }
        final int n4 = n3 & PFD2DrawableTypeBits(pixelFormatDescriptor);
        if (n4 == 0) {
            if (WindowsWGLGraphicsConfiguration.DEBUG) {
                System.err.println("Drop [drawableType mismatch]: " + WGLGLCapabilities.PFD2String(pixelFormatDescriptor, n2));
            }
            return null;
        }
        if (2 == n4 && (pixelFormatDescriptor.getCColorBits() != 24 || 0 < pixelFormatDescriptor.getCAlphaBits())) {
            if (WindowsWGLGraphicsConfiguration.DEBUG) {
                System.err.println("Drop [color bits excl BITMAP]: " + WGLGLCapabilities.PFD2String(pixelFormatDescriptor, n2));
            }
            return null;
        }
        final WGLGLCapabilities wglglCapabilities = new WGLGLCapabilities(pixelFormatDescriptor, n2, glProfile);
        wglglCapabilities.setValuesByGDI();
        return (WGLGLCapabilities)GLGraphicsConfigurationUtil.fixWinAttribBitsAndHwAccel(abstractGraphicsDevice, n4, wglglCapabilities);
    }
    
    static WGLGLCapabilities PFD2GLCapabilitiesNoCheck(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final long n, final int n2) {
        return PFD2GLCapabilitiesNoCheck(abstractGraphicsDevice, glProfile, createPixelFormatDescriptor(n, n2), n2);
    }
    
    static WGLGLCapabilities PFD2GLCapabilitiesNoCheck(final AbstractGraphicsDevice abstractGraphicsDevice, final GLProfile glProfile, final PIXELFORMATDESCRIPTOR pixelformatdescriptor, final int n) {
        if (null == pixelformatdescriptor) {
            return null;
        }
        final WGLGLCapabilities wglglCapabilities = new WGLGLCapabilities(pixelformatdescriptor, n, glProfile);
        wglglCapabilities.setValuesByGDI();
        return (WGLGLCapabilities)GLGraphicsConfigurationUtil.fixWinAttribBitsAndHwAccel(abstractGraphicsDevice, PFD2DrawableTypeBits(pixelformatdescriptor), wglglCapabilities);
    }
    
    static PIXELFORMATDESCRIPTOR GLCapabilities2PFD(final GLCapabilitiesImmutable glCapabilitiesImmutable, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        final int n = glCapabilitiesImmutable.getRedBits() + glCapabilitiesImmutable.getGreenBits() + glCapabilitiesImmutable.getBlueBits();
        if (n < 15) {
            throw new GLException("Bit depths < 15 (i.e., non-true-color) not supported");
        }
        final int n2 = 4128;
        int dwFlags;
        if (glCapabilitiesImmutable.isOnscreen()) {
            dwFlags = (n2 | 0x4);
        }
        else if (glCapabilitiesImmutable.isFBO()) {
            dwFlags = (n2 | 0x4);
        }
        else if (glCapabilitiesImmutable.isPBuffer()) {
            dwFlags = (n2 | 0x8);
        }
        else {
            if (!glCapabilitiesImmutable.isBitmap()) {
                throw new GLException("no surface type set in caps: " + glCapabilitiesImmutable);
            }
            dwFlags = (n2 | 0x8);
        }
        if (glCapabilitiesImmutable.getDoubleBuffered()) {
            if (glCapabilitiesImmutable.isBitmap() || glCapabilitiesImmutable.isPBuffer()) {
                dwFlags |= 0x40000000;
            }
            else {
                dwFlags |= 0x1;
            }
        }
        if (glCapabilitiesImmutable.getStereo()) {
            dwFlags |= 0x2;
        }
        pixelformatdescriptor.setDwFlags(dwFlags);
        pixelformatdescriptor.setIPixelType((byte)0);
        pixelformatdescriptor.setCColorBits((byte)n);
        pixelformatdescriptor.setCRedBits((byte)glCapabilitiesImmutable.getRedBits());
        pixelformatdescriptor.setCGreenBits((byte)glCapabilitiesImmutable.getGreenBits());
        pixelformatdescriptor.setCBlueBits((byte)glCapabilitiesImmutable.getBlueBits());
        pixelformatdescriptor.setCAlphaBits((byte)glCapabilitiesImmutable.getAlphaBits());
        pixelformatdescriptor.setCAccumBits((byte)(glCapabilitiesImmutable.getAccumRedBits() + glCapabilitiesImmutable.getAccumGreenBits() + glCapabilitiesImmutable.getAccumBlueBits()));
        pixelformatdescriptor.setCAccumRedBits((byte)glCapabilitiesImmutable.getAccumRedBits());
        pixelformatdescriptor.setCAccumGreenBits((byte)glCapabilitiesImmutable.getAccumGreenBits());
        pixelformatdescriptor.setCAccumBlueBits((byte)glCapabilitiesImmutable.getAccumBlueBits());
        pixelformatdescriptor.setCAccumAlphaBits((byte)glCapabilitiesImmutable.getAccumAlphaBits());
        pixelformatdescriptor.setCDepthBits((byte)glCapabilitiesImmutable.getDepthBits());
        pixelformatdescriptor.setCStencilBits((byte)glCapabilitiesImmutable.getStencilBits());
        pixelformatdescriptor.setILayerType((byte)0);
        return pixelformatdescriptor;
    }
    
    static PIXELFORMATDESCRIPTOR createPixelFormatDescriptor(final long n, final int n2) {
        final PIXELFORMATDESCRIPTOR create = PIXELFORMATDESCRIPTOR.create();
        create.setNSize((short)PIXELFORMATDESCRIPTOR.size());
        create.setNVersion((short)1);
        if (0L != n && 1 <= n2 && WGLUtil.DescribePixelFormat(n, n2, PIXELFORMATDESCRIPTOR.size(), create) == 0) {
            if (WindowsWGLGraphicsConfiguration.DEBUG) {
                System.err.println("Info: Non displayable pixel format " + n2 + " of device context: error code " + GDI.GetLastError());
            }
            return null;
        }
        return create;
    }
    
    static PIXELFORMATDESCRIPTOR createPixelFormatDescriptor() {
        return createPixelFormatDescriptor(0L, 0);
    }
    
    @Override
    public String toString() {
        return "WindowsWGLGraphicsConfiguration[" + this.getScreen() + ", pfdID " + this.getPixelFormatID() + ", ARB-Choosen " + this.isChoosenByARB() + ",\n\trequested " + this.getRequestedCapabilities() + ",\n\tchosen    " + this.getChosenCapabilities() + "]";
    }
}
