// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl.awt;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.opengl.GLException;
import jogamp.opengl.macosx.cgl.MacOSXCGLGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.macosx.MacOSXGraphicsDevice;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import jogamp.opengl.GLGraphicsConfigurationFactory;

public class MacOSXAWTCGLGraphicsConfigurationFactory extends GLGraphicsConfigurationFactory
{
    public static void registerFactory() {
        GraphicsConfigurationFactory.registerFactory(AWTGraphicsDevice.class, GLCapabilitiesImmutable.class, new MacOSXAWTCGLGraphicsConfigurationFactory());
    }
    
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, AbstractGraphicsScreen default1, final int n) {
        if (default1 != null && !(default1 instanceof AWTGraphicsScreen)) {
            throw new IllegalArgumentException("This GraphicsConfigurationFactory accepts only AWTGraphicsScreen objects");
        }
        if (null == default1) {
            default1 = AWTGraphicsScreen.createDefault();
        }
        final AWTGraphicsScreen awtGraphicsScreen = (AWTGraphicsScreen)default1;
        final GraphicsDevice graphicsDevice = ((AWTGraphicsDevice)awtGraphicsScreen.getDevice()).getGraphicsDevice();
        if (!(capabilitiesImmutable instanceof GLCapabilitiesImmutable)) {
            throw new IllegalArgumentException("This GraphicsConfigurationFactory accepts only GLCapabilities objects - chosen");
        }
        if (!(capabilitiesImmutable2 instanceof GLCapabilitiesImmutable)) {
            throw new IllegalArgumentException("This GraphicsConfigurationFactory accepts only GLCapabilities objects - requested");
        }
        if (capabilitiesChooser != null && !(capabilitiesChooser instanceof GLCapabilitiesChooser)) {
            throw new IllegalArgumentException("This GraphicsConfigurationFactory accepts only GLCapabilitiesChooser objects");
        }
        if (MacOSXAWTCGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("MacOSXAWTCGLGraphicsConfigurationFactory: got " + default1);
        }
        final MacOSXGraphicsDevice macOSXGraphicsDevice = new MacOSXGraphicsDevice(0);
        final DefaultGraphicsScreen defaultGraphicsScreen = new DefaultGraphicsScreen(macOSXGraphicsDevice, awtGraphicsScreen.getIndex());
        if (MacOSXAWTCGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("MacOSXAWTCGLGraphicsConfigurationFactory: made " + defaultGraphicsScreen);
        }
        final GraphicsConfiguration defaultConfiguration = graphicsDevice.getDefaultConfiguration();
        final MacOSXCGLGraphicsConfiguration macOSXCGLGraphicsConfiguration = (MacOSXCGLGraphicsConfiguration)GraphicsConfigurationFactory.getFactory(macOSXGraphicsDevice, capabilitiesImmutable).chooseGraphicsConfiguration(capabilitiesImmutable, capabilitiesImmutable2, capabilitiesChooser, defaultGraphicsScreen, n);
        if (macOSXCGLGraphicsConfiguration == null) {
            throw new GLException("Unable to choose a GraphicsConfiguration: " + capabilitiesImmutable + ",\n\t" + capabilitiesChooser + "\n\t" + defaultGraphicsScreen);
        }
        return new AWTGraphicsConfiguration(awtGraphicsScreen, macOSXCGLGraphicsConfiguration.getChosenCapabilities(), macOSXCGLGraphicsConfiguration.getRequestedCapabilities(), defaultConfiguration, macOSXCGLGraphicsConfiguration);
    }
}
