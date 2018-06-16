// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

public final class Binary64
{
    static final long NEGATIVE_ZERO_BITS;
    static final long MASK_SIGN;
    static final long MASK_EXPONENT;
    static final long MASK_SIGNIFICAND;
    static final long BIAS;
    
    public static long unpackGetExponentUnbiased(final double n) {
        return ((Double.doubleToRawLongBits(n) & Binary64.MASK_EXPONENT) >> 52) - Binary64.BIAS;
    }
    
    public static long unpackGetSignificand(final double n) {
        return Double.doubleToRawLongBits(n) & Binary64.MASK_SIGNIFICAND;
    }
    
    public static long unpackGetSign(final double n) {
        return (Double.doubleToRawLongBits(n) & Binary64.MASK_SIGN) >> 63 & 0x1L;
    }
    
    static {
        NEGATIVE_ZERO_BITS = Long.MIN_VALUE;
        MASK_SIGN = Long.MIN_VALUE;
        MASK_EXPONENT = 9218868437227405312L;
        MASK_SIGNIFICAND = 4503599627370495L;
        BIAS = 1023L;
    }
}
