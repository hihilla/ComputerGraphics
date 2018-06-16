// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class Capabilities implements CapabilitiesImmutable, Cloneable
{
    protected static final String na_str = "----";
    private int redBits;
    private int greenBits;
    private int blueBits;
    private int alphaBits;
    private boolean backgroundOpaque;
    private int transparentValueRed;
    private int transparentValueGreen;
    private int transparentValueBlue;
    private int transparentValueAlpha;
    private boolean onscreen;
    private boolean isBitmap;
    protected static final String ESEP = "/";
    protected static final String CSEP = ", ";
    
    public Capabilities() {
        this.redBits = 8;
        this.greenBits = 8;
        this.blueBits = 8;
        this.alphaBits = 0;
        this.backgroundOpaque = true;
        this.transparentValueRed = 0;
        this.transparentValueGreen = 0;
        this.transparentValueBlue = 0;
        this.transparentValueAlpha = 0;
        this.onscreen = true;
        this.isBitmap = false;
    }
    
    @Override
    public Object cloneMutable() {
        return this.clone();
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new NativeWindowException(ex);
        }
    }
    
    public Capabilities copyFrom(final CapabilitiesImmutable capabilitiesImmutable) {
        this.redBits = capabilitiesImmutable.getRedBits();
        this.greenBits = capabilitiesImmutable.getGreenBits();
        this.blueBits = capabilitiesImmutable.getBlueBits();
        this.alphaBits = capabilitiesImmutable.getAlphaBits();
        this.backgroundOpaque = capabilitiesImmutable.isBackgroundOpaque();
        this.onscreen = capabilitiesImmutable.isOnscreen();
        this.isBitmap = capabilitiesImmutable.isBitmap();
        this.transparentValueRed = capabilitiesImmutable.getTransparentRedValue();
        this.transparentValueGreen = capabilitiesImmutable.getTransparentGreenValue();
        this.transparentValueBlue = capabilitiesImmutable.getTransparentBlueValue();
        this.transparentValueAlpha = capabilitiesImmutable.getTransparentAlphaValue();
        return this;
    }
    
    @Override
    public int hashCode() {
        final int n = 31 + this.redBits;
        final int n2 = (n << 5) - n + (this.onscreen ? 1 : 0);
        final int n3 = (n2 << 5) - n2 + (this.isBitmap ? 1 : 0);
        final int n4 = (n3 << 5) - n3 + this.greenBits;
        final int n5 = (n4 << 5) - n4 + this.blueBits;
        final int n6 = (n5 << 5) - n5 + this.alphaBits;
        final int n7 = (n6 << 5) - n6 + (this.backgroundOpaque ? 1 : 0);
        final int n8 = (n7 << 5) - n7 + this.transparentValueRed;
        final int n9 = (n8 << 5) - n8 + this.transparentValueGreen;
        final int n10 = (n9 << 5) - n9 + this.transparentValueBlue;
        return (n10 << 5) - n10 + this.transparentValueAlpha;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CapabilitiesImmutable)) {
            return false;
        }
        final CapabilitiesImmutable capabilitiesImmutable = (CapabilitiesImmutable)o;
        boolean b = capabilitiesImmutable.getRedBits() == this.redBits && capabilitiesImmutable.getGreenBits() == this.greenBits && capabilitiesImmutable.getBlueBits() == this.blueBits && capabilitiesImmutable.getAlphaBits() == this.alphaBits && capabilitiesImmutable.isBackgroundOpaque() == this.backgroundOpaque && capabilitiesImmutable.isOnscreen() == this.onscreen && capabilitiesImmutable.isBitmap() == this.isBitmap;
        if (b && !this.backgroundOpaque) {
            b = (capabilitiesImmutable.getTransparentRedValue() == this.transparentValueRed && capabilitiesImmutable.getTransparentGreenValue() == this.transparentValueGreen && capabilitiesImmutable.getTransparentBlueValue() == this.transparentValueBlue && capabilitiesImmutable.getTransparentAlphaValue() == this.transparentValueAlpha);
        }
        return b;
    }
    
    @Override
    public int compareTo(final CapabilitiesImmutable capabilitiesImmutable) {
        final int n = this.redBits * this.greenBits * this.blueBits * (this.alphaBits + 1);
        final int n2 = capabilitiesImmutable.getRedBits() * capabilitiesImmutable.getGreenBits() * capabilitiesImmutable.getBlueBits() * (capabilitiesImmutable.getAlphaBits() + 1);
        if (n > n2) {
            return 1;
        }
        if (n < n2) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public int getVisualID(final VisualIDHolder.VIDType vidType) throws NativeWindowException {
        switch (vidType) {
            case INTRINSIC:
            case NATIVE: {
                return 0;
            }
            default: {
                throw new NativeWindowException("Invalid type <" + vidType + ">");
            }
        }
    }
    
    @Override
    public final int getRedBits() {
        return this.redBits;
    }
    
    public void setRedBits(final int redBits) {
        this.redBits = redBits;
    }
    
    @Override
    public final int getGreenBits() {
        return this.greenBits;
    }
    
    public void setGreenBits(final int greenBits) {
        this.greenBits = greenBits;
    }
    
    @Override
    public final int getBlueBits() {
        return this.blueBits;
    }
    
    public void setBlueBits(final int blueBits) {
        this.blueBits = blueBits;
    }
    
    @Override
    public final int getAlphaBits() {
        return this.alphaBits;
    }
    
    public void setAlphaBits(final int alphaBits) {
        this.alphaBits = alphaBits;
    }
    
    public void setBackgroundOpaque(final boolean backgroundOpaque) {
        this.backgroundOpaque = backgroundOpaque;
        if (!backgroundOpaque && this.getAlphaBits() == 0) {
            this.setAlphaBits(1);
        }
    }
    
    @Override
    public final boolean isBackgroundOpaque() {
        return this.backgroundOpaque;
    }
    
    public void setOnscreen(final boolean onscreen) {
        this.onscreen = onscreen;
    }
    
    @Override
    public final boolean isOnscreen() {
        return this.onscreen;
    }
    
    public void setBitmap(final boolean isBitmap) {
        if (isBitmap) {
            this.setOnscreen(false);
        }
        this.isBitmap = isBitmap;
    }
    
    @Override
    public boolean isBitmap() {
        return this.isBitmap;
    }
    
    @Override
    public final int getTransparentRedValue() {
        return this.transparentValueRed;
    }
    
    @Override
    public final int getTransparentGreenValue() {
        return this.transparentValueGreen;
    }
    
    @Override
    public final int getTransparentBlueValue() {
        return this.transparentValueBlue;
    }
    
    @Override
    public final int getTransparentAlphaValue() {
        return this.transparentValueAlpha;
    }
    
    public void setTransparentRedValue(final int transparentValueRed) {
        this.transparentValueRed = transparentValueRed;
    }
    
    public void setTransparentGreenValue(final int transparentValueGreen) {
        this.transparentValueGreen = transparentValueGreen;
    }
    
    public void setTransparentBlueValue(final int transparentValueBlue) {
        this.transparentValueBlue = transparentValueBlue;
    }
    
    public void setTransparentAlphaValue(final int transparentValueAlpha) {
        this.transparentValueAlpha = transparentValueAlpha;
    }
    
    @Override
    public StringBuilder toString(final StringBuilder sb) {
        return this.toString(sb, true);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Caps[");
        this.toString(sb);
        sb.append("]");
        return sb.toString();
    }
    
    protected StringBuilder onoffScreenToString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        if (this.onscreen) {
            sb.append("on-scr");
        }
        else {
            sb.append("offscr[");
        }
        if (this.isBitmap) {
            sb.append("bitmap");
        }
        else if (this.onscreen) {
            sb.append(".");
        }
        else {
            sb.append("auto-cfg");
        }
        sb.append("]");
        return sb;
    }
    
    protected StringBuilder toString(StringBuilder sb, final boolean b) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("rgba ").append(this.redBits).append("/").append(this.greenBits).append("/").append(this.blueBits).append("/").append(this.alphaBits);
        if (this.backgroundOpaque) {
            sb.append(", opaque");
        }
        else {
            sb.append(", trans-rgba 0x").append(this.toHexString(this.transparentValueRed)).append("/").append(this.toHexString(this.transparentValueGreen)).append("/").append(this.toHexString(this.transparentValueBlue)).append("/").append(this.toHexString(this.transparentValueAlpha));
        }
        if (b) {
            sb.append(", ");
            this.onoffScreenToString(sb);
        }
        return sb;
    }
    
    protected final String toHexString(final int n) {
        return Integer.toHexString(n);
    }
}
