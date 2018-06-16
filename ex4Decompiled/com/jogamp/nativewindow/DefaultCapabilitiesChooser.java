// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import com.jogamp.common.util.PropertyAccess;
import jogamp.nativewindow.Debug;
import java.util.List;

public class DefaultCapabilitiesChooser implements CapabilitiesChooser
{
    private static final boolean DEBUG;
    private static final int NO_SCORE = -9999999;
    private static final int COLOR_MISMATCH_PENALTY_SCALE = 36;
    
    @Override
    public int chooseCapabilities(final CapabilitiesImmutable capabilitiesImmutable, final List<? extends CapabilitiesImmutable> list, final int n) {
        if (DefaultCapabilitiesChooser.DEBUG) {
            System.err.println("Desired: " + capabilitiesImmutable);
            for (int i = 0; i < list.size(); ++i) {
                System.err.println("Available " + i + ": " + list.get(i));
            }
            System.err.println("Window system's recommended choice: " + n);
        }
        final int size = list.size();
        if (n >= 0 && n < size && null != list.get(n)) {
            if (DefaultCapabilitiesChooser.DEBUG) {
                System.err.println("Choosing window system's recommended choice of " + n);
                System.err.println(list.get(n));
            }
            return n;
        }
        final int[] array = new int[size];
        for (int j = 0; j < size; ++j) {
            array[j] = -9999999;
        }
        for (int k = 0; k < size; ++k) {
            final CapabilitiesImmutable capabilitiesImmutable2 = (CapabilitiesImmutable)list.get(k);
            if (capabilitiesImmutable2 != null) {
                if (!capabilitiesImmutable.isOnscreen() || capabilitiesImmutable2.isOnscreen()) {
                    array[k] = 0 + 36 * (capabilitiesImmutable2.getRedBits() + capabilitiesImmutable2.getGreenBits() + capabilitiesImmutable2.getBlueBits() + capabilitiesImmutable2.getAlphaBits() - (capabilitiesImmutable.getRedBits() + capabilitiesImmutable.getGreenBits() + capabilitiesImmutable.getBlueBits() + capabilitiesImmutable.getAlphaBits()));
                }
            }
        }
        if (DefaultCapabilitiesChooser.DEBUG) {
            System.err.print("Scores: [");
            for (int l = 0; l < size; ++l) {
                if (l > 0) {
                    System.err.print(",");
                }
                System.err.print(" " + array[l]);
            }
            System.err.println(" ]");
        }
        int n2 = -9999999;
        int n3 = -1;
        for (int n4 = 0; n4 < size; ++n4) {
            final int n5 = array[n4];
            if (n5 != -9999999) {
                if (n2 == -9999999 || (Math.abs(n5) < Math.abs(n2) && (sign(n2) < 0 || sign(n5) > 0))) {
                    n2 = n5;
                    n3 = n4;
                }
            }
        }
        if (n3 < 0) {
            throw new NativeWindowException("Unable to select one of the provided Capabilities");
        }
        if (DefaultCapabilitiesChooser.DEBUG) {
            System.err.println("Chosen index: " + n3);
            System.err.println("Chosen capabilities:");
            System.err.println(list.get(n3));
        }
        return n3;
    }
    
    private static int sign(final int n) {
        if (n < 0) {
            return -1;
        }
        return 1;
    }
    
    static {
        Debug.initSingleton();
        DEBUG = PropertyAccess.isPropertyDefined("nativewindow.debug.CapabilitiesChooser", true);
    }
}
