// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import com.jogamp.common.nio.PointerBuffer;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.common.nio.Buffers;
import java.util.ArrayList;
import com.jogamp.opengl.GLException;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import java.nio.IntBuffer;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;

public class MacOSXCGLGraphicsConfiguration extends MutableGraphicsConfiguration implements Cloneable
{
    static final IntBuffer cglInternalAttributeToken;
    
    MacOSXCGLGraphicsConfiguration(final AbstractGraphicsScreen abstractGraphicsScreen, final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2) {
        super(abstractGraphicsScreen, glCapabilitiesImmutable, glCapabilitiesImmutable2);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    protected static List<GLCapabilitiesImmutable> getAvailableCapabilities(final MacOSXCGLDrawableFactory macOSXCGLDrawableFactory, final AbstractGraphicsDevice abstractGraphicsDevice) {
        if (null == macOSXCGLDrawableFactory.getOrCreateSharedResourceImpl(abstractGraphicsDevice)) {
            throw new GLException("Shared resource for device n/a: " + abstractGraphicsDevice);
        }
        return new ArrayList<GLCapabilitiesImmutable>(0);
    }
    
    static IntBuffer GLCapabilities2NSAttribList(final AbstractGraphicsDevice abstractGraphicsDevice, final IntBuffer intBuffer, final GLCapabilitiesImmutable glCapabilitiesImmutable, final int n, final int n2, final int n3) {
        final int remaining = intBuffer.remaining();
        final int position = intBuffer.position();
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(remaining);
        for (int i = 0; i < remaining; ++i) {
            switch (intBuffer.get(i + position)) {
                case 99: {
                    directIntBuffer.put(i, MacOSXCGLContext.GLProfile2CGLOGLProfileValue(abstractGraphicsDevice, n, n2, n3));
                    break;
                }
                case 72: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getHardwareAccelerated() ? 1 : 0);
                    break;
                }
                case 58: {
                    directIntBuffer.put(i, 0);
                    break;
                }
                case 90: {
                    directIntBuffer.put(i, (!glCapabilitiesImmutable.isOnscreen() && glCapabilitiesImmutable.isPBuffer()) ? 1 : 0);
                    break;
                }
                case 5: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getDoubleBuffered() ? 1 : 0);
                    break;
                }
                case 6: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getStereo() ? 1 : 0);
                    break;
                }
                case 8: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getRedBits() + glCapabilitiesImmutable.getGreenBits() + glCapabilitiesImmutable.getBlueBits());
                    break;
                }
                case 11: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getAlphaBits());
                    break;
                }
                case 12: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getDepthBits());
                    break;
                }
                case 14: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getAccumRedBits() + glCapabilitiesImmutable.getAccumGreenBits() + glCapabilitiesImmutable.getAccumBlueBits() + glCapabilitiesImmutable.getAccumAlphaBits());
                    break;
                }
                case 13: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getStencilBits());
                    break;
                }
                case 55: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getSampleBuffers() ? 1 : 0);
                    break;
                }
                case 56: {
                    directIntBuffer.put(i, glCapabilitiesImmutable.getNumSamples());
                    break;
                }
            }
        }
        return directIntBuffer;
    }
    
    static long GLCapabilities2NSPixelFormat(final AbstractGraphicsDevice abstractGraphicsDevice, final GLCapabilitiesImmutable glCapabilitiesImmutable, final int n, final int n2, final int n3) {
        final IntBuffer duplicate = MacOSXCGLGraphicsConfiguration.cglInternalAttributeToken.duplicate();
        if (!MacOSXCGLContext.isLionOrLater) {
            duplicate.position(1);
        }
        return CGL.createPixelFormat(duplicate, duplicate.remaining(), GLCapabilities2NSAttribList(abstractGraphicsDevice, duplicate, glCapabilitiesImmutable, n, n2, n3));
    }
    
    static GLCapabilities NSPixelFormat2GLCapabilities(final GLProfile glProfile, final long n) {
        return PixelFormat2GLCapabilities(glProfile, n, true);
    }
    
    static long GLCapabilities2CGLPixelFormat(final AbstractGraphicsDevice abstractGraphicsDevice, final GLCapabilitiesImmutable glCapabilitiesImmutable, final int n, final int n2, final int n3) {
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(256);
        int n4 = 0;
        if (MacOSXCGLContext.isLionOrLater) {
            directIntBuffer.put(n4++, 99);
            directIntBuffer.put(n4++, MacOSXCGLContext.GLProfile2CGLOGLProfileValue(abstractGraphicsDevice, n, n2, n3));
        }
        if (glCapabilitiesImmutable.getDoubleBuffered()) {
            directIntBuffer.put(n4++, 5);
        }
        if (glCapabilitiesImmutable.getStereo()) {
            directIntBuffer.put(n4++, 6);
        }
        directIntBuffer.put(n4++, 8);
        directIntBuffer.put(n4++, glCapabilitiesImmutable.getRedBits() + glCapabilitiesImmutable.getGreenBits() + glCapabilitiesImmutable.getBlueBits());
        directIntBuffer.put(n4++, 11);
        directIntBuffer.put(n4++, glCapabilitiesImmutable.getAlphaBits());
        directIntBuffer.put(n4++, 12);
        directIntBuffer.put(n4++, glCapabilitiesImmutable.getDepthBits());
        directIntBuffer.put(n4++, 13);
        directIntBuffer.put(n4++, glCapabilitiesImmutable.getStencilBits());
        directIntBuffer.put(n4++, 14);
        directIntBuffer.put(n4++, glCapabilitiesImmutable.getAccumRedBits() + glCapabilitiesImmutable.getAccumGreenBits() + glCapabilitiesImmutable.getAccumBlueBits() + glCapabilitiesImmutable.getAccumAlphaBits());
        if (glCapabilitiesImmutable.getSampleBuffers()) {
            directIntBuffer.put(n4++, 55);
            directIntBuffer.put(n4++, 1);
            directIntBuffer.put(n4++, 56);
            directIntBuffer.put(n4++, glCapabilitiesImmutable.getNumSamples());
        }
        final PointerBuffer allocateDirect = PointerBuffer.allocateDirect(1);
        final int cglChoosePixelFormat = CGL.CGLChoosePixelFormat(directIntBuffer, allocateDirect, Buffers.newDirectIntBuffer(1));
        if (cglChoosePixelFormat != 0) {
            throw new GLException("Error code " + cglChoosePixelFormat + " while choosing pixel format");
        }
        return allocateDirect.get(0);
    }
    
    static GLCapabilities CGLPixelFormat2GLCapabilities(final long n) {
        return PixelFormat2GLCapabilities(null, n, false);
    }
    
    private static GLCapabilities PixelFormat2GLCapabilities(GLProfile glProfile, final long n, final boolean b) {
        final IntBuffer duplicate = MacOSXCGLGraphicsConfiguration.cglInternalAttributeToken.duplicate();
        int n2;
        if (!MacOSXCGLContext.isLionOrLater) {
            n2 = 1;
        }
        else {
            n2 = 0;
        }
        duplicate.position(n2);
        final int remaining = duplicate.remaining();
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(remaining);
        if (b) {
            CGL.queryPixelFormat(n, duplicate, remaining, directIntBuffer);
        }
        else {
            CGL.CGLQueryPixelFormat(n, duplicate, remaining, directIntBuffer);
        }
        if (null == glProfile && MacOSXCGLContext.isLionOrLater) {
            for (int i = 0; i < remaining; ++i) {
                final int value = directIntBuffer.get(i);
                if (99 == duplicate.get(i + n2)) {
                    switch (value) {
                        case 16640: {
                            glProfile = GLProfile.get("GL4");
                            break;
                        }
                        case 12800: {
                            glProfile = GLProfile.get("GL3");
                            break;
                        }
                        case 4096: {
                            glProfile = GLProfile.get("GL2");
                            break;
                        }
                        default: {
                            throw new RuntimeException("Unhandled OSX OpenGL Profile: 0x" + Integer.toHexString(value));
                        }
                    }
                }
            }
        }
        if (null == glProfile) {
            glProfile = GLProfile.get("GL2");
        }
        final GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        int alphaBits = 0;
        for (int j = 0; j < remaining; ++j) {
            final int value2 = duplicate.get(j + n2);
            final int value3 = directIntBuffer.get(j);
            switch (value2) {
                case 73: {
                    glCapabilities.setHardwareAccelerated(value3 != 0);
                }
                case 90: {
                    glCapabilities.setPBuffer(value3 != 0);
                    break;
                }
                case 5: {
                    glCapabilities.setDoubleBuffered(value3 != 0);
                    break;
                }
                case 6: {
                    glCapabilities.setStereo(value3 != 0);
                    break;
                }
                case 8: {
                    final int blueBits = ((32 == value3) ? 24 : value3) / 3;
                    glCapabilities.setRedBits(blueBits);
                    glCapabilities.setGreenBits(blueBits);
                    glCapabilities.setBlueBits(blueBits);
                    break;
                }
                case 11: {
                    alphaBits = value3;
                    break;
                }
                case 12: {
                    glCapabilities.setDepthBits(value3);
                    break;
                }
                case 14: {
                    final int n3 = value3 / 4;
                    glCapabilities.setAccumRedBits(n3);
                    glCapabilities.setAccumGreenBits(n3);
                    glCapabilities.setAccumBlueBits(n3);
                    glCapabilities.setAccumAlphaBits(n3);
                    break;
                }
                case 13: {
                    glCapabilities.setStencilBits(value3);
                    break;
                }
                case 55: {
                    glCapabilities.setSampleBuffers(value3 != 0);
                    break;
                }
                case 56: {
                    glCapabilities.setNumSamples(value3);
                    break;
                }
            }
        }
        glCapabilities.setAlphaBits(alphaBits);
        return glCapabilities;
    }
    
    static {
        cglInternalAttributeToken = Buffers.newDirectIntBuffer(new int[] { 99, 73, 72, 58, 90, 5, 6, 8, 11, 12, 14, 13, 55, 56 });
    }
}
