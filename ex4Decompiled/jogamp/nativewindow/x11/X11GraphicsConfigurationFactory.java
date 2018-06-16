// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11;

import com.jogamp.nativewindow.x11.X11GraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;

public class X11GraphicsConfigurationFactory extends GraphicsConfigurationFactory
{
    public static void registerFactory() {
        GraphicsConfigurationFactory.registerFactory(X11GraphicsDevice.class, CapabilitiesImmutable.class, new X11GraphicsConfigurationFactory());
    }
    
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) throws IllegalArgumentException, NativeWindowException {
        if (!(abstractGraphicsScreen instanceof X11GraphicsScreen)) {
            throw new NativeWindowException("Only valid X11GraphicsScreen are allowed");
        }
        XVisualInfo xVisualInfo;
        if (0 == n) {
            xVisualInfo = getXVisualInfo(abstractGraphicsScreen, capabilitiesImmutable);
        }
        else {
            xVisualInfo = getXVisualInfo(abstractGraphicsScreen, n);
        }
        final X11Capabilities xVisualInfo2X11Capabilities = X11GraphicsConfiguration.XVisualInfo2X11Capabilities((X11GraphicsDevice)abstractGraphicsScreen.getDevice(), xVisualInfo);
        final X11GraphicsConfiguration x11GraphicsConfiguration = new X11GraphicsConfiguration((X11GraphicsScreen)abstractGraphicsScreen, xVisualInfo2X11Capabilities, capabilitiesImmutable2, xVisualInfo2X11Capabilities.getXVisualInfo());
        if (X11GraphicsConfigurationFactory.DEBUG) {
            System.err.println("X11GraphicsConfigurationFactory.chooseGraphicsConfigurationImpl(visualID 0x" + Integer.toHexString(n) + ", " + xVisualInfo + ", " + abstractGraphicsScreen + "," + capabilitiesImmutable + "): " + x11GraphicsConfiguration);
        }
        return x11GraphicsConfiguration;
    }
    
    public static XVisualInfo getXVisualInfo(final AbstractGraphicsScreen abstractGraphicsScreen, final int n) {
        final XVisualInfo create = XVisualInfo.create();
        create.setVisualid(n);
        create.setScreen(abstractGraphicsScreen.getIndex());
        final int[] array = { -1 };
        final XVisualInfo[] xGetVisualInfo = X11Lib.XGetVisualInfo(abstractGraphicsScreen.getDevice().getHandle(), 3L, create, array, 0);
        if (xGetVisualInfo == null || array[0] < 1) {
            return null;
        }
        return XVisualInfo.create(xGetVisualInfo[0]);
    }
    
    public static XVisualInfo getXVisualInfo(final AbstractGraphicsScreen abstractGraphicsScreen, final CapabilitiesImmutable capabilitiesImmutable) {
        final XVisualInfo xVisualInfoImpl = getXVisualInfoImpl(abstractGraphicsScreen, capabilitiesImmutable, 4);
        if (null != xVisualInfoImpl) {
            return xVisualInfoImpl;
        }
        return getXVisualInfoImpl(abstractGraphicsScreen, capabilitiesImmutable, 5);
    }
    
    private static XVisualInfo getXVisualInfoImpl(final AbstractGraphicsScreen abstractGraphicsScreen, final CapabilitiesImmutable capabilitiesImmutable, final int c_class) {
        XVisualInfo create = null;
        final int[] array = { -1 };
        final XVisualInfo create2 = XVisualInfo.create();
        create2.setScreen(abstractGraphicsScreen.getIndex());
        create2.setC_class(c_class);
        final XVisualInfo[] xGetVisualInfo = X11Lib.XGetVisualInfo(abstractGraphicsScreen.getDevice().getHandle(), 2L, create2, array, 0);
        XVisualInfo xVisualInfo = null;
        final int n = capabilitiesImmutable.getRedBits() + capabilitiesImmutable.getGreenBits() + capabilitiesImmutable.getBlueBits() + capabilitiesImmutable.getAlphaBits();
        for (int n2 = 0; xGetVisualInfo != null && n2 < array[0]; ++n2) {
            if (xVisualInfo == null || xVisualInfo.getDepth() < xGetVisualInfo[n2].getDepth()) {
                xVisualInfo = xGetVisualInfo[n2];
                if (n <= xVisualInfo.getDepth()) {
                    break;
                }
            }
        }
        if (null != xVisualInfo && (n <= xVisualInfo.getDepth() || 24 == xVisualInfo.getDepth())) {
            create = XVisualInfo.create(xVisualInfo);
        }
        return create;
    }
}
