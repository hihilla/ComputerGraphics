// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.util.List;
import com.jogamp.nativewindow.CapabilitiesImmutable;

public class GenericGLCapabilitiesChooser extends DefaultGLCapabilitiesChooser
{
    @Override
    public int chooseCapabilities(final CapabilitiesImmutable capabilitiesImmutable, final List<? extends CapabilitiesImmutable> list, final int n) {
        return super.chooseCapabilities(capabilitiesImmutable, list, -1);
    }
}
