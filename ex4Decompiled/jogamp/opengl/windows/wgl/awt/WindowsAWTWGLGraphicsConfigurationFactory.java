// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl.awt;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.common.util.ArrayHashSet;
import jogamp.nativewindow.jawt.windows.Win32SunJDKReflection;
import jogamp.opengl.windows.wgl.WindowsWGLDrawableFactory;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLException;
import jogamp.opengl.windows.wgl.WindowsWGLGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.DefaultGraphicsScreen;
import com.jogamp.nativewindow.windows.WindowsGraphicsDevice;
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

public class WindowsAWTWGLGraphicsConfigurationFactory extends GLGraphicsConfigurationFactory
{
    public static void registerFactory() {
        GraphicsConfigurationFactory.registerFactory(AWTGraphicsDevice.class, GLCapabilitiesImmutable.class, new WindowsAWTWGLGraphicsConfigurationFactory());
    }
    
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, AbstractGraphicsScreen default1, final int n) {
        if (default1 != null && !(default1 instanceof AWTGraphicsScreen)) {
            throw new IllegalArgumentException("This GraphicsConfigurationFactory accepts only AWTGraphicsScreen objects");
        }
        if (null == default1) {
            default1 = AWTGraphicsScreen.createDefault();
            if (WindowsAWTWGLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("WindowsAWTWGLGraphicsConfigurationFactory: creating default device: " + default1);
            }
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
        if (WindowsAWTWGLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("WindowsAWTWGLGraphicsConfigurationFactory: got " + default1);
        }
        final WindowsGraphicsDevice windowsGraphicsDevice = new WindowsGraphicsDevice(0);
        final DefaultGraphicsScreen defaultGraphicsScreen = new DefaultGraphicsScreen(windowsGraphicsDevice, awtGraphicsScreen.getIndex());
        final WindowsWGLGraphicsConfiguration windowsWGLGraphicsConfiguration = (WindowsWGLGraphicsConfiguration)GraphicsConfigurationFactory.getFactory(windowsGraphicsDevice, capabilitiesImmutable).chooseGraphicsConfiguration(capabilitiesImmutable, capabilitiesImmutable2, capabilitiesChooser, defaultGraphicsScreen, n);
        if (windowsWGLGraphicsConfiguration == null) {
            throw new GLException("Unable to choose a GraphicsConfiguration: " + capabilitiesImmutable + ",\n\t" + capabilitiesChooser + "\n\t" + defaultGraphicsScreen);
        }
        final GLDrawableFactory factory = GLDrawableFactory.getFactory(((GLCapabilitiesImmutable)capabilitiesImmutable).getGLProfile());
        GraphicsConfiguration graphicsConfiguration = null;
        if (factory instanceof WindowsWGLDrawableFactory) {
            try {
                windowsWGLGraphicsConfiguration.preselectGraphicsConfiguration(factory, null);
                if (1 <= windowsWGLGraphicsConfiguration.getPixelFormatID()) {
                    graphicsConfiguration = Win32SunJDKReflection.graphicsConfigurationGet(graphicsDevice, windowsWGLGraphicsConfiguration.getPixelFormatID());
                    if (WindowsAWTWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("WindowsAWTWGLGraphicsConfigurationFactory: Found new AWT PFD ID " + windowsWGLGraphicsConfiguration.getPixelFormatID() + " -> " + windowsWGLGraphicsConfiguration);
                    }
                }
            }
            catch (GLException ex) {
                if (WindowsAWTWGLGraphicsConfigurationFactory.DEBUG) {
                    ex.printStackTrace();
                }
            }
            if (null == graphicsConfiguration) {
                final GraphicsConfiguration[] configurations = graphicsDevice.getConfigurations();
                final int[] array = new int[configurations.length];
                final ArrayHashSet<Integer> set = new ArrayHashSet<Integer>(false, 16, 0.75f);
                for (int i = 0; i < configurations.length; ++i) {
                    array[i] = Win32SunJDKReflection.graphicsConfigurationGetPixelFormatID(configurations[i]);
                    set.add(array[i]);
                    if (WindowsAWTWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("AWT pfd[" + i + "] " + array[i]);
                    }
                }
                if (WindowsAWTWGLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("WindowsAWTWGLGraphicsConfigurationFactory: PFD IDs: " + array.length + ", unique: " + set.size());
                }
                windowsWGLGraphicsConfiguration.preselectGraphicsConfiguration(factory, array);
                final int index = set.indexOf(windowsWGLGraphicsConfiguration.getPixelFormatID());
                if (0 > index) {
                    graphicsConfiguration = configurations[index];
                    if (WindowsAWTWGLGraphicsConfigurationFactory.DEBUG) {
                        System.err.println("WindowsAWTWGLGraphicsConfigurationFactory: Found matching AWT PFD ID " + windowsWGLGraphicsConfiguration.getPixelFormatID() + " -> " + windowsWGLGraphicsConfiguration);
                    }
                }
            }
        }
        else {
            graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
        }
        if (null == graphicsConfiguration) {
            throw new GLException("Unable to determine GraphicsConfiguration: " + windowsWGLGraphicsConfiguration);
        }
        return new AWTGraphicsConfiguration(awtGraphicsScreen, windowsWGLGraphicsConfiguration.getChosenCapabilities(), windowsWGLGraphicsConfiguration.getRequestedCapabilities(), graphicsConfiguration, windowsWGLGraphicsConfiguration);
    }
}
