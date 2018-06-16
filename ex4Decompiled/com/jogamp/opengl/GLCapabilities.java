// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.Capabilities;

public class GLCapabilities extends Capabilities implements Cloneable, GLCapabilitiesImmutable
{
    private GLProfile glProfile;
    private boolean isPBuffer;
    private boolean isFBO;
    private boolean doubleBuffered;
    private boolean stereo;
    private boolean hardwareAccelerated;
    private int depthBits;
    private int stencilBits;
    private int accumRedBits;
    private int accumGreenBits;
    private int accumBlueBits;
    private int accumAlphaBits;
    private String sampleExtension;
    private boolean sampleBuffers;
    private int numSamples;
    
    public GLCapabilities(final GLProfile glProfile) throws GLException {
        this.glProfile = null;
        this.isPBuffer = false;
        this.isFBO = false;
        this.doubleBuffered = true;
        this.stereo = false;
        this.hardwareAccelerated = true;
        this.depthBits = 16;
        this.stencilBits = 0;
        this.accumRedBits = 0;
        this.accumGreenBits = 0;
        this.accumBlueBits = 0;
        this.accumAlphaBits = 0;
        this.sampleExtension = "default";
        this.sampleBuffers = false;
        this.numSamples = 2;
        this.glProfile = ((null != glProfile) ? glProfile : GLProfile.getDefault(GLProfile.getDefaultDevice()));
    }
    
    @Override
    public Object cloneMutable() {
        return this.clone();
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (RuntimeException ex) {
            throw new GLException(ex);
        }
    }
    
    public GLCapabilities copyFrom(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        super.copyFrom(glCapabilitiesImmutable);
        this.glProfile = glCapabilitiesImmutable.getGLProfile();
        this.isPBuffer = glCapabilitiesImmutable.isPBuffer();
        this.isFBO = glCapabilitiesImmutable.isFBO();
        this.doubleBuffered = glCapabilitiesImmutable.getDoubleBuffered();
        this.stereo = glCapabilitiesImmutable.getStereo();
        this.hardwareAccelerated = glCapabilitiesImmutable.getHardwareAccelerated();
        this.depthBits = glCapabilitiesImmutable.getDepthBits();
        this.stencilBits = glCapabilitiesImmutable.getStencilBits();
        this.accumRedBits = glCapabilitiesImmutable.getAccumRedBits();
        this.accumGreenBits = glCapabilitiesImmutable.getAccumGreenBits();
        this.accumBlueBits = glCapabilitiesImmutable.getAccumBlueBits();
        this.accumAlphaBits = glCapabilitiesImmutable.getAccumAlphaBits();
        this.sampleBuffers = glCapabilitiesImmutable.getSampleBuffers();
        this.numSamples = glCapabilitiesImmutable.getNumSamples();
        this.sampleExtension = glCapabilitiesImmutable.getSampleExtension();
        return this;
    }
    
    @Override
    public int hashCode() {
        final int hashCode = super.hashCode();
        final int n = (hashCode << 5) - hashCode + this.glProfile.hashCode();
        final int n2 = (n << 5) - n + (this.hardwareAccelerated ? 1 : 0);
        final int n3 = (n2 << 5) - n2 + (this.stereo ? 1 : 0);
        final int n4 = (n3 << 5) - n3 + (this.isFBO ? 1 : 0);
        final int n5 = (n4 << 5) - n4 + (this.isPBuffer ? 1 : 0);
        final int n6 = (n5 << 5) - n5 + (this.sampleBuffers ? 1 : 0);
        final int n7 = (n6 << 5) - n6 + this.getNumSamples();
        final int n8 = (n7 << 5) - n7 + this.sampleExtension.hashCode();
        final int n9 = (n8 << 5) - n8 + this.depthBits;
        final int n10 = (n9 << 5) - n9 + this.stencilBits;
        final int n11 = (n10 << 5) - n10 + this.accumRedBits;
        final int n12 = (n11 << 5) - n11 + this.accumGreenBits;
        final int n13 = (n12 << 5) - n12 + this.accumBlueBits;
        return (n13 << 5) - n13 + this.accumAlphaBits;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GLCapabilitiesImmutable)) {
            return false;
        }
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)o;
        boolean b = super.equals(o) && glCapabilitiesImmutable.getGLProfile() == this.glProfile && glCapabilitiesImmutable.isPBuffer() == this.isPBuffer && glCapabilitiesImmutable.isFBO() == this.isFBO && glCapabilitiesImmutable.getDoubleBuffered() == this.doubleBuffered && glCapabilitiesImmutable.getStereo() == this.stereo && glCapabilitiesImmutable.getHardwareAccelerated() == this.hardwareAccelerated && glCapabilitiesImmutable.getDepthBits() == this.depthBits && glCapabilitiesImmutable.getStencilBits() == this.stencilBits && glCapabilitiesImmutable.getAccumRedBits() == this.accumRedBits && glCapabilitiesImmutable.getAccumGreenBits() == this.accumGreenBits && glCapabilitiesImmutable.getAccumBlueBits() == this.accumBlueBits && glCapabilitiesImmutable.getAccumAlphaBits() == this.accumAlphaBits && glCapabilitiesImmutable.getSampleBuffers() == this.sampleBuffers;
        if (b && this.sampleBuffers) {
            b = (glCapabilitiesImmutable.getNumSamples() == this.getNumSamples() && glCapabilitiesImmutable.getSampleExtension().equals(this.sampleExtension));
        }
        return b;
    }
    
    @Override
    public int compareTo(final CapabilitiesImmutable capabilitiesImmutable) {
        if (!(capabilitiesImmutable instanceof GLCapabilitiesImmutable)) {
            throw new ClassCastException("Not a GLCapabilitiesImmutable object, but " + ((null != capabilitiesImmutable) ? capabilitiesImmutable.getClass() : null));
        }
        final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)capabilitiesImmutable;
        if (this.hardwareAccelerated && !glCapabilitiesImmutable.getHardwareAccelerated()) {
            return 1;
        }
        if (!this.hardwareAccelerated && glCapabilitiesImmutable.getHardwareAccelerated()) {
            return -1;
        }
        if (this.stereo && !glCapabilitiesImmutable.getStereo()) {
            return 1;
        }
        if (!this.stereo && glCapabilitiesImmutable.getStereo()) {
            return -1;
        }
        if (this.doubleBuffered && !glCapabilitiesImmutable.getDoubleBuffered()) {
            return 1;
        }
        if (!this.doubleBuffered && glCapabilitiesImmutable.getDoubleBuffered()) {
            return -1;
        }
        final int numSamples = this.getNumSamples();
        final int numSamples2 = glCapabilitiesImmutable.getNumSamples();
        if (numSamples > numSamples2) {
            return 1;
        }
        if (numSamples < numSamples2) {
            return -1;
        }
        if (this.stencilBits > glCapabilitiesImmutable.getStencilBits()) {
            return 1;
        }
        if (this.stencilBits < glCapabilitiesImmutable.getStencilBits()) {
            return -1;
        }
        final int compareTo = super.compareTo((CapabilitiesImmutable)glCapabilitiesImmutable);
        if (compareTo != 0) {
            return compareTo;
        }
        if (this.depthBits > glCapabilitiesImmutable.getDepthBits()) {
            return 1;
        }
        if (this.depthBits < glCapabilitiesImmutable.getDepthBits()) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public final GLProfile getGLProfile() {
        return this.glProfile;
    }
    
    public void setGLProfile(final GLProfile glProfile) {
        this.glProfile = glProfile;
    }
    
    @Override
    public final boolean isPBuffer() {
        return this.isPBuffer;
    }
    
    public void setPBuffer(final boolean isPBuffer) {
        if (isPBuffer) {
            this.setOnscreen(false);
        }
        this.isPBuffer = isPBuffer;
    }
    
    @Override
    public final boolean isFBO() {
        return this.isFBO;
    }
    
    public void setFBO(final boolean isFBO) {
        if (isFBO) {
            this.setOnscreen(false);
        }
        this.isFBO = isFBO;
    }
    
    @Override
    public final boolean getDoubleBuffered() {
        return this.doubleBuffered;
    }
    
    public void setDoubleBuffered(final boolean doubleBuffered) {
        this.doubleBuffered = doubleBuffered;
    }
    
    @Override
    public final boolean getStereo() {
        return this.stereo;
    }
    
    public void setStereo(final boolean stereo) {
        this.stereo = stereo;
    }
    
    @Override
    public final boolean getHardwareAccelerated() {
        return this.hardwareAccelerated;
    }
    
    public void setHardwareAccelerated(final boolean hardwareAccelerated) {
        this.hardwareAccelerated = hardwareAccelerated;
    }
    
    @Override
    public final int getDepthBits() {
        return this.depthBits;
    }
    
    public void setDepthBits(final int depthBits) {
        this.depthBits = depthBits;
    }
    
    @Override
    public final int getStencilBits() {
        return this.stencilBits;
    }
    
    public void setStencilBits(final int stencilBits) {
        this.stencilBits = stencilBits;
    }
    
    @Override
    public final int getAccumRedBits() {
        return this.accumRedBits;
    }
    
    public void setAccumRedBits(final int accumRedBits) {
        this.accumRedBits = accumRedBits;
    }
    
    @Override
    public final int getAccumGreenBits() {
        return this.accumGreenBits;
    }
    
    public void setAccumGreenBits(final int accumGreenBits) {
        this.accumGreenBits = accumGreenBits;
    }
    
    @Override
    public final int getAccumBlueBits() {
        return this.accumBlueBits;
    }
    
    public void setAccumBlueBits(final int accumBlueBits) {
        this.accumBlueBits = accumBlueBits;
    }
    
    @Override
    public final int getAccumAlphaBits() {
        return this.accumAlphaBits;
    }
    
    public void setAccumAlphaBits(final int accumAlphaBits) {
        this.accumAlphaBits = accumAlphaBits;
    }
    
    public void setSampleExtension(final String sampleExtension) {
        this.sampleExtension = sampleExtension;
    }
    
    @Override
    public final String getSampleExtension() {
        return this.sampleExtension;
    }
    
    public void setSampleBuffers(final boolean sampleBuffers) {
        this.sampleBuffers = sampleBuffers;
        if (this.sampleBuffers && this.getAlphaBits() == 0) {
            this.setAlphaBits(1);
        }
    }
    
    @Override
    public final boolean getSampleBuffers() {
        return this.sampleBuffers;
    }
    
    public void setNumSamples(final int numSamples) {
        this.numSamples = numSamples;
    }
    
    @Override
    public final int getNumSamples() {
        return this.sampleBuffers ? this.numSamples : 0;
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final int n = this.sampleBuffers ? this.numSamples : 0;
        super.toString(sb, false);
        sb.append(", accum-rgba ").append(this.accumRedBits).append("/").append(this.accumGreenBits).append("/").append(this.accumBlueBits).append("/").append(this.accumAlphaBits);
        sb.append(", dp/st/ms ").append(this.depthBits).append("/").append(this.stencilBits).append("/").append(n);
        if (n > 0) {
            sb.append(", sample-ext ").append(this.sampleExtension);
        }
        if (this.doubleBuffered) {
            sb.append(", dbl");
        }
        else {
            sb.append(", one");
        }
        if (this.stereo) {
            sb.append(", stereo");
        }
        else {
            sb.append(", mono  ");
        }
        if (this.hardwareAccelerated) {
            sb.append(", hw, ");
        }
        else {
            sb.append(", sw, ");
        }
        sb.append(this.glProfile);
        if (this.isOnscreen()) {
            sb.append(", on-scr[");
        }
        else {
            sb.append(", offscr[");
        }
        int n2 = 0;
        if (this.isFBO()) {
            sb.append("fbo");
            n2 = 1;
        }
        if (this.isPBuffer()) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("pbuffer");
            n2 = 1;
        }
        if (this.isBitmap()) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("bitmap");
            n2 = 1;
        }
        if (n2 == 0) {
            if (this.isOnscreen()) {
                sb.append(".");
            }
            else {
                sb.append("auto-cfg");
            }
        }
        sb.append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GLCaps[");
        this.toString(sb);
        sb.append("]");
        return sb.toString();
    }
}
