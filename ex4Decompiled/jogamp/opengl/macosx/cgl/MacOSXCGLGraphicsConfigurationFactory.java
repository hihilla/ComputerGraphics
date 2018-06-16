// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import jogamp.opengl.GLGraphicsConfigurationUtil;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.macosx.MacOSXGraphicsDevice;
import jogamp.opengl.GLGraphicsConfigurationFactory;

public class MacOSXCGLGraphicsConfigurationFactory extends GLGraphicsConfigurationFactory
{
    static void registerFactory() {
        GraphicsConfigurationFactory.registerFactory(MacOSXGraphicsDevice.class, GLCapabilitiesImmutable.class, new MacOSXCGLGraphicsConfigurationFactory());
    }
    
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) {
        if (abstractGraphicsScreen == null) {
            throw new IllegalArgumentException("AbstractGraphicsScreen is null");
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
        return chooseGraphicsConfigurationStatic((GLCapabilitiesImmutable)capabilitiesImmutable, (GLCapabilitiesImmutable)capabilitiesImmutable2, (GLCapabilitiesChooser)capabilitiesChooser, abstractGraphicsScreen, false);
    }
    
    static MacOSXCGLGraphicsConfiguration chooseGraphicsConfigurationStatic(GLCapabilitiesImmutable fixGLCapabilities, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser glCapabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final boolean b) {
        if (abstractGraphicsScreen == null) {
            throw new IllegalArgumentException("AbstractGraphicsScreen is null");
        }
        fixGLCapabilities = GLGraphicsConfigurationUtil.fixGLCapabilities(fixGLCapabilities, GLDrawableFactory.getDesktopFactory(), abstractGraphicsScreen.getDevice());
        return new MacOSXCGLGraphicsConfiguration(abstractGraphicsScreen, fixGLCapabilities, glCapabilitiesImmutable);
    }
}
