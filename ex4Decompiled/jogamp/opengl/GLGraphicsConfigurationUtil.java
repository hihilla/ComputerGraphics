// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLCapabilitiesImmutable;

public class GLGraphicsConfigurationUtil
{
    public static final String NV_coverage_sample = "NV_coverage_sample";
    public static final int WINDOW_BIT = 1;
    public static final int BITMAP_BIT = 2;
    public static final int PBUFFER_BIT = 4;
    public static final int FBO_BIT = 8;
    public static final int ALL_BITS = 15;
    
    public static final StringBuilder winAttributeBits2String(StringBuilder sb, final int n) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        int n2 = 0;
        if (0x0 != (0x1 & n)) {
            sb.append("WINDOW");
            n2 = 1;
        }
        if (0x0 != (0x2 & n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("BITMAP");
            n2 = 1;
        }
        if (0x0 != (0x4 & n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("PBUFFER");
            n2 = 1;
        }
        if (0x0 != (0x8 & n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("FBO");
        }
        return sb;
    }
    
    public static final int getExclusiveWinAttributeBits(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        int n;
        if (b) {
            n = 1;
        }
        else if (b2) {
            n = 8;
        }
        else if (b3) {
            n = 4;
        }
        else {
            if (!b4) {
                throw new InternalError("Empty bitmask");
            }
            n = 2;
        }
        return n;
    }
    
    public static final int getExclusiveWinAttributeBits(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        return getExclusiveWinAttributeBits(glCapabilitiesImmutable.isOnscreen(), glCapabilitiesImmutable.isFBO(), glCapabilitiesImmutable.isPBuffer(), glCapabilitiesImmutable.isBitmap());
    }
    
    public static final GLCapabilities fixWinAttribBitsAndHwAccel(final AbstractGraphicsDevice abstractGraphicsDevice, final int n, final GLCapabilities glCapabilities) {
        glCapabilities.setBitmap(0x0 != (0x2 & n));
        glCapabilities.setPBuffer(0x0 != (0x4 & n));
        glCapabilities.setFBO(0x0 != (0x8 & n));
        glCapabilities.setOnscreen(0x0 != (0x1 & n));
        if (0 == GLContext.isHardwareRasterizer(abstractGraphicsDevice, glCapabilities.getGLProfile()) && glCapabilities.getHardwareAccelerated()) {
            glCapabilities.setHardwareAccelerated(false);
        }
        return glCapabilities;
    }
    
    public static GLCapabilitiesImmutable fixGLCapabilities(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLDrawableFactory glDrawableFactory, final AbstractGraphicsDevice abstractGraphicsDevice) {
        if (!glCapabilitiesImmutable.isOnscreen()) {
            return fixOffscreenGLCapabilities(glCapabilitiesImmutable, glDrawableFactory, abstractGraphicsDevice);
        }
        return glCapabilitiesImmutable;
    }
    
    public static GLCapabilitiesImmutable fixOnscreenGLCapabilities(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        if (!glCapabilitiesImmutable.isOnscreen() || glCapabilitiesImmutable.isFBO() || glCapabilitiesImmutable.isPBuffer() || glCapabilitiesImmutable.isBitmap()) {
            final GLCapabilities glCapabilities = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
            glCapabilities.setBitmap(false);
            glCapabilities.setPBuffer(false);
            glCapabilities.setFBO(false);
            glCapabilities.setOnscreen(true);
            return glCapabilities;
        }
        return glCapabilitiesImmutable;
    }
    
    public static GLCapabilitiesImmutable fixOffscreenBitOnly(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        if (glCapabilitiesImmutable.isOnscreen()) {
            final GLCapabilities glCapabilities = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
            glCapabilities.setOnscreen(false);
            return glCapabilities;
        }
        return glCapabilitiesImmutable;
    }
    
    public static GLCapabilitiesImmutable fixOffscreenGLCapabilities(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLDrawableFactory glDrawableFactory, AbstractGraphicsDevice defaultDevice) {
        if (null == defaultDevice) {
            defaultDevice = glDrawableFactory.getDefaultDevice();
        }
        final GLProfile glProfile = glCapabilitiesImmutable.getGLProfile();
        final boolean fboAvailable = GLContext.isFBOAvailable(defaultDevice, glProfile);
        final boolean canCreateGLPbuffer = glDrawableFactory.canCreateGLPbuffer(defaultDevice, glProfile);
        final GLRendererQuirks rendererQuirks = glDrawableFactory.getRendererQuirks(defaultDevice, glProfile);
        boolean b;
        boolean b2;
        if (null != rendererQuirks) {
            b = !rendererQuirks.exist(3);
            b2 = ((!glCapabilitiesImmutable.getDoubleBuffered() || !glCapabilitiesImmutable.isPBuffer() || !rendererQuirks.exist(0)) && (!glCapabilitiesImmutable.isBitmap() || !rendererQuirks.exist(1)));
        }
        else {
            b = true;
            b2 = true;
        }
        final boolean b3 = (!fboAvailable || !glCapabilitiesImmutable.isFBO()) && (!canCreateGLPbuffer || !glCapabilitiesImmutable.isPBuffer()) && (!b || !glCapabilitiesImmutable.isBitmap());
        final boolean fbo = fboAvailable && (b3 || glCapabilitiesImmutable.isFBO());
        final boolean pBuffer = !fbo && canCreateGLPbuffer && (b3 || glCapabilitiesImmutable.isPBuffer());
        final boolean bitmap = !fbo && !pBuffer && b && (b3 || glCapabilitiesImmutable.isBitmap());
        if (glCapabilitiesImmutable.isOnscreen() || fbo != glCapabilitiesImmutable.isFBO() || pBuffer != glCapabilitiesImmutable.isPBuffer() || bitmap != glCapabilitiesImmutable.isBitmap() || (!b2 && glCapabilitiesImmutable.getDoubleBuffered())) {
            final GLCapabilities glCapabilities = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
            glCapabilities.setOnscreen(false);
            glCapabilities.setFBO(fbo);
            glCapabilities.setPBuffer(pBuffer);
            glCapabilities.setBitmap(bitmap);
            if (!b2) {
                glCapabilities.setDoubleBuffered(false);
            }
            return glCapabilities;
        }
        return glCapabilitiesImmutable;
    }
    
    public static GLCapabilitiesImmutable fixGLPBufferGLCapabilities(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        if (glCapabilitiesImmutable.isOnscreen() || !glCapabilitiesImmutable.isPBuffer() || glCapabilitiesImmutable.isFBO()) {
            final GLCapabilities glCapabilities = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
            glCapabilities.setOnscreen(false);
            glCapabilities.setFBO(false);
            glCapabilities.setPBuffer(true);
            glCapabilities.setBitmap(false);
            return glCapabilities;
        }
        return glCapabilitiesImmutable;
    }
    
    public static GLCapabilities fixOpaqueGLCapabilities(final GLCapabilities glCapabilities, final boolean backgroundOpaque) {
        if (glCapabilities.isBackgroundOpaque() != backgroundOpaque) {
            final int alphaBits = glCapabilities.getAlphaBits();
            glCapabilities.setBackgroundOpaque(backgroundOpaque);
            glCapabilities.setAlphaBits(alphaBits);
        }
        return glCapabilities;
    }
    
    public static GLCapabilitiesImmutable fixDoubleBufferedGLCapabilities(final GLCapabilitiesImmutable glCapabilitiesImmutable, final boolean doubleBuffered) {
        if (glCapabilitiesImmutable.getDoubleBuffered() != doubleBuffered) {
            final GLCapabilities glCapabilities = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
            glCapabilities.setDoubleBuffered(doubleBuffered);
            return glCapabilities;
        }
        return glCapabilitiesImmutable;
    }
    
    public static GLCapabilitiesImmutable clipRGBAGLCapabilities(final GLCapabilitiesImmutable glCapabilitiesImmutable, final boolean b, final boolean b2) {
        final int redBits = glCapabilitiesImmutable.getRedBits();
        final int greenBits = glCapabilitiesImmutable.getGreenBits();
        final int blueBits = glCapabilitiesImmutable.getBlueBits();
        final int alphaBits = glCapabilitiesImmutable.getAlphaBits();
        final int clipColor = clipColor(redBits, b);
        final int clipColor2 = clipColor(greenBits, b);
        final int clipColor3 = clipColor(blueBits, b);
        final int alphaBits2 = (b2 && 0 < alphaBits) ? clipColor : false;
        if (redBits != clipColor || greenBits != clipColor2 || blueBits != clipColor3 || alphaBits != alphaBits2) {
            final GLCapabilities glCapabilities = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
            glCapabilities.setRedBits(clipColor);
            glCapabilities.setGreenBits(clipColor2);
            glCapabilities.setBlueBits(clipColor3);
            glCapabilities.setAlphaBits(alphaBits2);
            return glCapabilities;
        }
        return glCapabilitiesImmutable;
    }
    
    public static int clipColor(final int n, final boolean b) {
        int n2;
        if (5 < n || !b) {
            n2 = 8;
        }
        else {
            n2 = 5;
        }
        return n2;
    }
    
    public static GLCapabilitiesImmutable fixGLProfile(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLProfile glProfile) {
        if (glCapabilitiesImmutable.getGLProfile() != glProfile) {
            final GLCapabilities glCapabilities = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
            glCapabilities.setGLProfile(glProfile);
            return glCapabilities;
        }
        return glCapabilitiesImmutable;
    }
}
