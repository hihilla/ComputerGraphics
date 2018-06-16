// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.x11;

import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.common.util.Bitfield;
import jogamp.nativewindow.x11.X11Capabilities;
import jogamp.nativewindow.x11.X11Lib;
import jogamp.nativewindow.x11.XRenderPictFormat;
import jogamp.nativewindow.x11.XRenderDirectFormat;
import jogamp.nativewindow.x11.XVisualInfo;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;

public class X11GraphicsConfiguration extends MutableGraphicsConfiguration implements Cloneable
{
    private XVisualInfo info;
    
    protected static XRenderDirectFormat XVisual2XRenderMask(final long n, final long n2) {
        return XVisual2XRenderMask(n, n2, XRenderPictFormat.create());
    }
    
    protected static XRenderDirectFormat XVisual2XRenderMask(final long n, final long n2, final XRenderPictFormat xRenderPictFormat) {
        if (!X11Lib.XRenderFindVisualFormat(n, n2, xRenderPictFormat)) {
            return null;
        }
        return xRenderPictFormat.getDirect();
    }
    
    public static X11Capabilities XVisualInfo2X11Capabilities(final X11GraphicsDevice x11GraphicsDevice, final XVisualInfo xVisualInfo) {
        final long handle = x11GraphicsDevice.getHandle();
        final X11Capabilities x11Capabilities = new X11Capabilities(xVisualInfo);
        final XRenderDirectFormat xRenderDirectFormat = (null != xVisualInfo) ? XVisual2XRenderMask(handle, xVisualInfo.getVisual()) : null;
        final short transparentAlphaValue = (short)((null != xRenderDirectFormat) ? xRenderDirectFormat.getAlphaMask() : 0);
        if (0 < transparentAlphaValue) {
            x11Capabilities.setBackgroundOpaque(false);
            x11Capabilities.setTransparentRedValue(xRenderDirectFormat.getRedMask());
            x11Capabilities.setTransparentGreenValue(xRenderDirectFormat.getGreenMask());
            x11Capabilities.setTransparentBlueValue(xRenderDirectFormat.getBlueMask());
            x11Capabilities.setTransparentAlphaValue(transparentAlphaValue);
        }
        else {
            x11Capabilities.setBackgroundOpaque(true);
        }
        x11Capabilities.setRedBits(Bitfield.Util.bitCount((int)xVisualInfo.getRed_mask()));
        x11Capabilities.setGreenBits(Bitfield.Util.bitCount((int)xVisualInfo.getGreen_mask()));
        x11Capabilities.setBlueBits(Bitfield.Util.bitCount((int)xVisualInfo.getBlue_mask()));
        x11Capabilities.setAlphaBits(Bitfield.Util.bitCount(transparentAlphaValue));
        return x11Capabilities;
    }
    
    public X11GraphicsConfiguration(final X11GraphicsScreen x11GraphicsScreen, final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final XVisualInfo info) {
        super(x11GraphicsScreen, capabilitiesImmutable, capabilitiesImmutable2);
        this.info = info;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    public final XVisualInfo getXVisualInfo() {
        return this.info;
    }
    
    protected final void setXVisualInfo(final XVisualInfo info) {
        this.info = info;
    }
    
    public final int getXVisualID() {
        return (null != this.info) ? ((int)this.info.getVisualid()) : 0;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.getScreen() + ", visualID 0x" + Long.toHexString(this.getXVisualID()) + ",\n\tchosen    " + this.capabilitiesChosen + ",\n\trequested " + this.capabilitiesRequested + "]";
    }
}
