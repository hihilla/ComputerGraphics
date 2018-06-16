// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.DefaultGLCapabilitiesChooser;
import com.jogamp.nativewindow.NativeWindowException;
import java.util.List;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;

public abstract class GLGraphicsConfigurationFactory extends GraphicsConfigurationFactory
{
    protected static int chooseCapabilities(CapabilitiesChooser capabilitiesChooser, final CapabilitiesImmutable capabilitiesImmutable, final List<? extends CapabilitiesImmutable> list, final int n) {
        if (null == capabilitiesImmutable) {
            throw new NativeWindowException("Null requested capabilities");
        }
        if (0 == list.size()) {
            if (GLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("Empty available capabilities");
            }
            return -1;
        }
        if (null == capabilitiesChooser && 0 <= n && capabilitiesImmutable.isBackgroundOpaque()) {
            if (GLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("chooseCapabilities: Using recommendedIndex (opaque): idx " + n);
            }
            return n;
        }
        if (null == capabilitiesChooser) {
            capabilitiesChooser = new DefaultGLCapabilitiesChooser();
        }
        try {
            final int chooseCapabilities = capabilitiesChooser.chooseCapabilities(capabilitiesImmutable, list, n);
            if (0 <= chooseCapabilities) {
                if (GLGraphicsConfigurationFactory.DEBUG) {
                    System.err.println("chooseCapabilities: Chosen idx " + chooseCapabilities);
                }
                return chooseCapabilities;
            }
        }
        catch (NativeWindowException ex) {
            if (GLGraphicsConfigurationFactory.DEBUG) {
                ex.printStackTrace();
            }
        }
        int n2;
        for (n2 = 0; n2 < list.size() && list.get(n2) == null; ++n2) {}
        if (n2 == list.size()) {
            if (GLGraphicsConfigurationFactory.DEBUG) {
                System.err.println("chooseCapabilities: Failed .. nothing available, bail out");
            }
            return -1;
        }
        if (GLGraphicsConfigurationFactory.DEBUG) {
            System.err.println("chooseCapabilities: Fall back to 1st available idx " + n2);
        }
        return n2;
    }
}
