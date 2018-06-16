// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.NativeWindowException;
import java.util.List;
import com.jogamp.nativewindow.CapabilitiesImmutable;

public class DefaultGLCapabilitiesChooser implements GLCapabilitiesChooser
{
    private static final boolean DEBUG;
    private static final int NO_SCORE = -9999999;
    private static final int DOUBLE_BUFFER_MISMATCH_PENALTY = 1000;
    private static final int OPAQUE_MISMATCH_PENALTY = 750;
    private static final int STENCIL_MISMATCH_PENALTY = 500;
    private static final int MULTISAMPLE_MISMATCH_PENALTY = 500;
    private static final int MULTISAMPLE_EXTENSION_MISMATCH_PENALTY = 250;
    private static final int COLOR_MISMATCH_PENALTY_SCALE = 36;
    private static final int DEPTH_MISMATCH_PENALTY_SCALE = 6;
    private static final int ACCUM_MISMATCH_PENALTY_SCALE = 1;
    private static final int STENCIL_MISMATCH_PENALTY_SCALE = 3;
    private static final int MULTISAMPLE_MISMATCH_PENALTY_SCALE = 3;
    
    @Override
    public int chooseCapabilities(final CapabilitiesImmutable capabilitiesImmutable, final List<? extends CapabilitiesImmutable> list, final int n) {
        if (null == capabilitiesImmutable) {
            throw new NativeWindowException("Null desired capabilities");
        }
        if (0 == list.size()) {
            throw new NativeWindowException("Empty available capabilities");
        }
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)capabilitiesImmutable;
        final int size = list.size();
        if (DefaultGLCapabilitiesChooser.DEBUG) {
            ExceptionUtils.dumpStack(System.err);
            System.err.println("Desired: " + glCapabilitiesImmutable);
            System.err.println("Available: " + size);
            for (int i = 0; i < list.size(); ++i) {
                System.err.println(i + ": " + list.get(i));
            }
            System.err.println("Window system's recommended choice: " + n);
        }
        if (n >= 0 && n < size && null != list.get(n)) {
            if (DefaultGLCapabilitiesChooser.DEBUG) {
                System.err.println("Choosing window system's recommended choice of " + n);
                System.err.println(list.get(n));
            }
            return n;
        }
        final int[] array = new int[size];
        for (int j = 0; j < array.length; ++j) {
            array[j] = -9999999;
        }
        final int numSamples = glCapabilitiesImmutable.getNumSamples();
        for (int k = 0; k < size; ++k) {
            final GLCapabilitiesImmutable glCapabilitiesImmutable2 = (GLCapabilitiesImmutable)list.get(k);
            if (glCapabilitiesImmutable2 != null) {
                if (!glCapabilitiesImmutable.isOnscreen() || glCapabilitiesImmutable2.isOnscreen()) {
                    if (!glCapabilitiesImmutable.isOnscreen()) {
                        if (glCapabilitiesImmutable.isPBuffer() && !glCapabilitiesImmutable2.isPBuffer()) {
                            continue;
                        }
                        if (glCapabilitiesImmutable.isBitmap() && !glCapabilitiesImmutable2.isBitmap()) {
                            continue;
                        }
                    }
                    if (glCapabilitiesImmutable.getStereo() == glCapabilitiesImmutable2.getStereo()) {
                        final int numSamples2 = glCapabilitiesImmutable2.getNumSamples();
                        final int n2 = 0 + 36 * (glCapabilitiesImmutable2.getRedBits() + glCapabilitiesImmutable2.getGreenBits() + glCapabilitiesImmutable2.getBlueBits() + glCapabilitiesImmutable2.getAlphaBits() - (glCapabilitiesImmutable.getRedBits() + glCapabilitiesImmutable.getGreenBits() + glCapabilitiesImmutable.getBlueBits() + glCapabilitiesImmutable.getAlphaBits()));
                        final int n3 = n2 + 6 * sign(n2) * Math.abs(glCapabilitiesImmutable2.getDepthBits() - glCapabilitiesImmutable.getDepthBits());
                        final int n4 = n3 + 1 * sign(n3) * Math.abs(glCapabilitiesImmutable2.getAccumRedBits() + glCapabilitiesImmutable2.getAccumGreenBits() + glCapabilitiesImmutable2.getAccumBlueBits() + glCapabilitiesImmutable2.getAccumAlphaBits() - (glCapabilitiesImmutable.getAccumRedBits() + glCapabilitiesImmutable.getAccumGreenBits() + glCapabilitiesImmutable.getAccumBlueBits() + glCapabilitiesImmutable.getAccumAlphaBits()));
                        final int n5 = n4 + 3 * sign(n4) * (glCapabilitiesImmutable2.getStencilBits() - glCapabilitiesImmutable.getStencilBits());
                        int n6 = n5 + 3 * sign(n5) * (numSamples2 - numSamples);
                        if (glCapabilitiesImmutable2.getDoubleBuffered() != glCapabilitiesImmutable.getDoubleBuffered()) {
                            n6 += sign(n6) * 1000;
                        }
                        if (glCapabilitiesImmutable2.isBackgroundOpaque() != glCapabilitiesImmutable.isBackgroundOpaque()) {
                            n6 += sign(n6) * 750;
                        }
                        if (glCapabilitiesImmutable.getStencilBits() > 0 && glCapabilitiesImmutable2.getStencilBits() == 0) {
                            n6 += sign(n6) * 500;
                        }
                        if (numSamples > 0) {
                            if (numSamples2 == 0) {
                                n6 += sign(n6) * 500;
                            }
                            if (!glCapabilitiesImmutable.getSampleExtension().equals(glCapabilitiesImmutable2.getSampleExtension())) {
                                n6 += sign(n6) * 250;
                            }
                        }
                        array[k] = n6;
                    }
                }
            }
        }
        int n7 = 0;
        int n8 = 0;
        for (int l = 0; l < size; ++l) {
            final int n9 = array[l];
            if (n9 != -9999999) {
                if (((GLCapabilitiesImmutable)list.get(l)).getHardwareAccelerated()) {
                    final int abs = Math.abs(n9);
                    if (n7 == 0 || abs > n8) {
                        n7 = 1;
                        n8 = abs;
                    }
                }
            }
        }
        if (n7 != 0) {
            for (int n10 = 0; n10 < size; ++n10) {
                int n11 = array[n10];
                if (n11 != -9999999) {
                    if (!((GLCapabilitiesImmutable)list.get(n10)).getHardwareAccelerated()) {
                        if (n11 <= 0) {
                            n11 -= n8;
                        }
                        else if (n11 > 0) {
                            n11 += n8;
                        }
                        array[n10] = n11;
                    }
                }
            }
        }
        if (DefaultGLCapabilitiesChooser.DEBUG) {
            System.err.print("Scores: [");
            for (int n12 = 0; n12 < size; ++n12) {
                if (n12 > 0) {
                    System.err.print(",");
                }
                System.err.print(" " + n12 + ": " + array[n12]);
            }
            System.err.println(" ]");
        }
        int n13 = -9999999;
        int n14 = -1;
        for (int n15 = 0; n15 < size; ++n15) {
            final int n16 = array[n15];
            if (n16 != -9999999) {
                if (n13 == -9999999 || (Math.abs(n16) < Math.abs(n13) && (sign(n13) < 0 || sign(n16) > 0))) {
                    n13 = n16;
                    n14 = n15;
                }
            }
        }
        if (n14 < 0) {
            throw new NativeWindowException("Unable to select one of the provided GLCapabilities");
        }
        if (DefaultGLCapabilitiesChooser.DEBUG) {
            System.err.println("Chosen index: " + n14);
            System.err.println("Chosen capabilities:");
            System.err.println(list.get(n14));
        }
        return n14;
    }
    
    private static int sign(final int n) {
        if (n < 0) {
            return -1;
        }
        return 1;
    }
    
    static {
        Debug.initSingleton();
        DEBUG = PropertyAccess.isPropertyDefined("jogl.debug.CapabilitiesChooser", true);
    }
}
