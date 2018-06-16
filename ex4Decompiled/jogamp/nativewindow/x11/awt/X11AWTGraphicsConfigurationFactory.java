// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11.awt;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import com.jogamp.nativewindow.VisualIDHolder;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.x11.X11GraphicsScreen;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import com.jogamp.nativewindow.NativeWindowFactory;
import jogamp.nativewindow.x11.X11Lib;
import jogamp.nativewindow.x11.X11Util;
import jogamp.nativewindow.jawt.x11.X11SunJDKReflection;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;

public class X11AWTGraphicsConfigurationFactory extends GraphicsConfigurationFactory
{
    public static void registerFactory() {
        GraphicsConfigurationFactory.registerFactory(AWTGraphicsDevice.class, CapabilitiesImmutable.class, new X11AWTGraphicsConfigurationFactory());
    }
    
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, AbstractGraphicsScreen default1, final int n) {
        if (default1 != null && !(default1 instanceof AWTGraphicsScreen)) {
            throw new IllegalArgumentException("This GraphicsConfigurationFactory accepts only AWTGraphicsScreen objects");
        }
        if (null == default1) {
            default1 = AWTGraphicsScreen.createDefault();
        }
        return chooseGraphicsConfigurationStatic(capabilitiesImmutable, capabilitiesImmutable2, capabilitiesChooser, (AWTGraphicsScreen)default1, n);
    }
    
    public static AWTGraphicsConfiguration chooseGraphicsConfigurationStatic(CapabilitiesImmutable setupCapabilitiesRGBABits, final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesChooser capabilitiesChooser, final AWTGraphicsScreen awtGraphicsScreen, final int n) {
        if (X11AWTGraphicsConfigurationFactory.DEBUG) {
            System.err.println("X11AWTGraphicsConfigurationFactory: got " + awtGraphicsScreen);
        }
        final GraphicsDevice graphicsDevice = ((AWTGraphicsDevice)awtGraphicsScreen.getDevice()).getGraphicsDevice();
        final long graphicsDeviceGetDisplay = X11SunJDKReflection.graphicsDeviceGetDisplay(graphicsDevice);
        long n2;
        boolean b;
        if (0L == graphicsDeviceGetDisplay) {
            n2 = X11Util.openDisplay(null);
            b = true;
            if (X11AWTGraphicsConfigurationFactory.DEBUG) {
                System.err.println(GraphicsConfigurationFactory.getThreadName() + " - X11AWTGraphicsConfigurationFactory: Null AWT dpy, create local X11 display: " + GraphicsConfigurationFactory.toHexString(n2));
            }
        }
        else {
            final String xDisplayString = X11Lib.XDisplayString(graphicsDeviceGetDisplay);
            n2 = X11Util.openDisplay(xDisplayString);
            b = true;
            if (X11AWTGraphicsConfigurationFactory.DEBUG) {
                System.err.println(GraphicsConfigurationFactory.getThreadName() + " - X11AWTGraphicsConfigurationFactory: AWT dpy " + xDisplayString + " / " + GraphicsConfigurationFactory.toHexString(graphicsDeviceGetDisplay) + ", create X11 display " + GraphicsConfigurationFactory.toHexString(n2));
            }
        }
        final X11GraphicsDevice x11GraphicsDevice = new X11GraphicsDevice(n2, 0, NativeWindowFactory.getDefaultToolkitLock(NativeWindowFactory.TYPE_AWT), b);
        final X11GraphicsScreen x11GraphicsScreen = new X11GraphicsScreen(x11GraphicsDevice, awtGraphicsScreen.getIndex());
        if (X11AWTGraphicsConfigurationFactory.DEBUG) {
            System.err.println("X11AWTGraphicsConfigurationFactory: made " + x11GraphicsScreen);
        }
        final GraphicsConfigurationFactory factory = GraphicsConfigurationFactory.getFactory(x11GraphicsDevice, setupCapabilitiesRGBABits);
        final AbstractGraphicsConfiguration chooseGraphicsConfiguration = factory.chooseGraphicsConfiguration(setupCapabilitiesRGBABits, capabilitiesImmutable, capabilitiesChooser, x11GraphicsScreen, n);
        if (chooseGraphicsConfiguration == null) {
            throw new NativeWindowException("Unable to choose a GraphicsConfiguration (1): " + setupCapabilitiesRGBABits + ",\n\t" + capabilitiesChooser + "\n\t" + x11GraphicsScreen);
        }
        if (X11AWTGraphicsConfigurationFactory.DEBUG) {
            System.err.println("X11AWTGraphicsConfigurationFactory: chosen config: " + chooseGraphicsConfiguration);
        }
        final GraphicsConfiguration[] configurations = graphicsDevice.getConfigurations();
        final int visualID = chooseGraphicsConfiguration.getVisualID(VisualIDHolder.VIDType.NATIVE);
        if (visualID != 0) {
            for (int i = 0; i < configurations.length; ++i) {
                final GraphicsConfiguration graphicsConfiguration = configurations[i];
                if (graphicsConfiguration != null && X11SunJDKReflection.graphicsConfigurationGetVisualID(graphicsConfiguration) == visualID) {
                    if (X11AWTGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("Found matching AWT visual: 0x" + Integer.toHexString(visualID) + " -> " + chooseGraphicsConfiguration);
                    }
                    return new AWTGraphicsConfiguration(awtGraphicsScreen, chooseGraphicsConfiguration.getChosenCapabilities(), chooseGraphicsConfiguration.getRequestedCapabilities(), graphicsConfiguration, chooseGraphicsConfiguration);
                }
            }
        }
        setupCapabilitiesRGBABits = AWTGraphicsConfiguration.setupCapabilitiesRGBABits(setupCapabilitiesRGBABits, graphicsDevice.getDefaultConfiguration());
        final AbstractGraphicsConfiguration chooseGraphicsConfiguration2 = factory.chooseGraphicsConfiguration(setupCapabilitiesRGBABits, capabilitiesImmutable, capabilitiesChooser, x11GraphicsScreen, n);
        if (chooseGraphicsConfiguration2 == null) {
            throw new NativeWindowException("Unable to choose a GraphicsConfiguration (2): " + setupCapabilitiesRGBABits + ",\n\t" + capabilitiesChooser + "\n\t" + x11GraphicsScreen);
        }
        final int visualID2 = chooseGraphicsConfiguration2.getVisualID(VisualIDHolder.VIDType.NATIVE);
        if (visualID2 != 0) {
            for (int j = 0; j < configurations.length; ++j) {
                final GraphicsConfiguration graphicsConfiguration2 = configurations[j];
                if (X11SunJDKReflection.graphicsConfigurationGetVisualID(graphicsConfiguration2) == visualID2) {
                    if (X11AWTGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("Found matching default AWT visual: 0x" + Integer.toHexString(visualID2) + " -> " + chooseGraphicsConfiguration2);
                    }
                    return new AWTGraphicsConfiguration(awtGraphicsScreen, chooseGraphicsConfiguration2.getChosenCapabilities(), chooseGraphicsConfiguration2.getRequestedCapabilities(), graphicsConfiguration2, chooseGraphicsConfiguration2);
                }
            }
        }
        if (X11AWTGraphicsConfigurationFactory.DEBUG) {
            System.err.println("Using default configuration");
        }
        return new AWTGraphicsConfiguration(awtGraphicsScreen, chooseGraphicsConfiguration2.getChosenCapabilities(), chooseGraphicsConfiguration2.getRequestedCapabilities(), graphicsDevice.getDefaultConfiguration(), chooseGraphicsConfiguration2);
    }
}
