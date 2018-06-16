// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

public final class Binary16
{
    public static final char NEGATIVE_INFINITY;
    public static final char POSITIVE_INFINITY;
    public static final char POSITIVE_ZERO;
    public static final char NEGATIVE_ZERO;
    public static final int BIAS;
    private static final int MASK_SIGN;
    private static final int MASK_EXPONENT;
    private static final int MASK_SIGNIFICAND;
    
    public static char exampleNaN() {
        return (char)(packSetExponentUnbiasedUnchecked(16) | packSetSignificandUnchecked(1));
    }
    
    public static boolean isInfinite(final char c) {
        return unpackGetExponentUnbiased(c) == 16 && unpackGetSignificand(c) == 0;
    }
    
    public static boolean isNaN(final char c) {
        final int unpackGetExponentUnbiased = unpackGetExponentUnbiased(c);
        final int unpackGetSignificand = unpackGetSignificand(c);
        return unpackGetExponentUnbiased == 16 && unpackGetSignificand > 0;
    }
    
    public static char packDouble(final double n) {
        if (Double.isNaN(n)) {
            return exampleNaN();
        }
        if (n == Double.POSITIVE_INFINITY) {
            return Binary16.POSITIVE_INFINITY;
        }
        if (n == Double.NEGATIVE_INFINITY) {
            return Binary16.NEGATIVE_INFINITY;
        }
        if (Double.doubleToLongBits(n) == Binary64.NEGATIVE_ZERO_BITS) {
            return Binary16.NEGATIVE_ZERO;
        }
        if (n == 0.0) {
            return Binary16.POSITIVE_ZERO;
        }
        return (char)(packSetSignUnchecked((int)Binary64.unpackGetSign(n)) | packSetExponentUnbiasedUnchecked((int)(Binary64.unpackGetExponentUnbiased(n) & 0x1FL)) | packSetSignificandUnchecked((int)((Binary64.unpackGetSignificand(n) & 0xFFC0000000000L) >> 42)));
    }
    
    public static char packFloat(final float n) {
        if (Float.isNaN(n)) {
            return exampleNaN();
        }
        if (n == Float.POSITIVE_INFINITY) {
            return Binary16.POSITIVE_INFINITY;
        }
        if (n == Float.NEGATIVE_INFINITY) {
            return Binary16.NEGATIVE_INFINITY;
        }
        if (Float.floatToIntBits(n) == Binary32.NEGATIVE_ZERO_BITS) {
            return Binary16.NEGATIVE_ZERO;
        }
        if (n == 0.0) {
            return Binary16.POSITIVE_ZERO;
        }
        return (char)(packSetSignUnchecked(Binary32.unpackGetSign(n)) | packSetExponentUnbiasedUnchecked((int)(Binary32.unpackGetExponentUnbiased(n) & 0x1FL)) | packSetSignificandUnchecked((int)((Binary32.unpackGetSignificand(n) & 0x7FE000L) >> 13)));
    }
    
    public static char packSetExponentUnbiasedUnchecked(final int n) {
        return (char)(n + Binary16.BIAS << 10 & Binary16.MASK_EXPONENT);
    }
    
    public static char packSetSignificandUnchecked(final int n) {
        return (char)(n & Binary16.MASK_SIGNIFICAND);
    }
    
    public static char packSetSignUnchecked(final int n) {
        return (char)(n << 15 & Binary16.MASK_SIGN);
    }
    
    public static String toRawBinaryString(final char c) {
        final StringBuilder sb = new StringBuilder();
        int n = c;
        for (int i = 0; i < 16; ++i) {
            if ((n & 0x1) == 0x1) {
                sb.insert(0, "1");
            }
            else {
                sb.insert(0, "0");
            }
            n >>= 1;
        }
        return sb.toString();
    }
    
    public static double unpackDouble(final char c) {
        if (isNaN(c)) {
            return Double.NaN;
        }
        if (c == Binary16.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        if (c == Binary16.NEGATIVE_INFINITY) {
            return Double.NEGATIVE_INFINITY;
        }
        if (c == Binary16.NEGATIVE_ZERO) {
            return -0.0;
        }
        if (c == Binary16.POSITIVE_ZERO) {
            return 0.0;
        }
        return Double.longBitsToDouble(unpackGetSign(c) << 63 | unpackGetExponentUnbiased(c) + Binary64.BIAS << 52 | unpackGetSignificand(c) << 42);
    }
    
    public static float unpackFloat(final char c) {
        if (isNaN(c)) {
            return Float.NaN;
        }
        if (c == Binary16.POSITIVE_INFINITY) {
            return Float.POSITIVE_INFINITY;
        }
        if (c == Binary16.NEGATIVE_INFINITY) {
            return Float.NEGATIVE_INFINITY;
        }
        if (c == Binary16.NEGATIVE_ZERO) {
            return -0.0f;
        }
        if (c == Binary16.POSITIVE_ZERO) {
            return 0.0f;
        }
        return Float.intBitsToFloat(unpackGetSign(c) << 31 | unpackGetExponentUnbiased(c) + Binary32.BIAS << 23 | unpackGetSignificand(c) << 13);
    }
    
    public static int unpackGetExponentUnbiased(final char c) {
        return ((c & Binary16.MASK_EXPONENT) >> 10) - Binary16.BIAS;
    }
    
    public static int unpackGetSign(final char c) {
        return (c & Binary16.MASK_SIGN) >> 15;
    }
    
    public static int unpackGetSignificand(final char c) {
        return c & Binary16.MASK_SIGNIFICAND;
    }
    
    private Binary16() {
        throw new AssertionError((Object)"Unreachable code, report this bug!");
    }
    
    static {
        NEGATIVE_INFINITY = '\ufc00';
        POSITIVE_INFINITY = '\u7c00';
        POSITIVE_ZERO = '\0';
        NEGATIVE_ZERO = '\u8000';
        BIAS = 15;
        MASK_SIGN = 32768;
        MASK_EXPONENT = 31744;
        MASK_SIGNIFICAND = 1023;
    }
}
