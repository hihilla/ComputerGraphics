// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.nativewindow.DefaultGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;

public class DefaultGraphicsConfigurationFactoryImpl extends GraphicsConfigurationFactory
{
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) {
        return new DefaultGraphicsConfiguration(abstractGraphicsScreen, capabilitiesImmutable, capabilitiesImmutable2);
    }
}
