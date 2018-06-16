// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

public final class Binary32
{
    static final int NEGATIVE_ZERO_BITS;
    static final int MASK_SIGN;
    static final int MASK_EXPONENT;
    static final int MASK_SIGNIFICAND;
    static final int BIAS;
    
    public static int unpackGetExponentUnbiased(final float n) {
        return ((Float.floatToRawIntBits(n) & Binary32.MASK_EXPONENT) >> 23) - Binary32.BIAS;
    }
    
    public static int unpackGetSign(final float n) {
        return (Float.floatToRawIntBits(n) & Binary32.MASK_SIGN) >> 31 & 0x1;
    }
    
    public static int unpackGetSignificand(final float n) {
        return Float.floatToRawIntBits(n) & Binary32.MASK_SIGNIFICAND;
    }
    
    static {
        NEGATIVE_ZERO_BITS = Integer.MIN_VALUE;
        MASK_SIGN = Integer.MIN_VALUE;
        MASK_EXPONENT = 2146435072;
        MASK_SIGNIFICAND = 8388607;
        BIAS = 127;
    }
}
