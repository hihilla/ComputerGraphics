// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

public class FixedPoint
{
    public static final int toFixed(int n) {
        if (n < -32768) {
            n = -32768;
        }
        if (n > 32767) {
            n = 32767;
        }
        return n * 65536;
    }
    
    public static final int toFixed(float n) {
        if (n < -32768.0f) {
            n = -32768.0f;
        }
        if (n > 32767.0f) {
            n = 32767.0f;
        }
        return (int)(n * 65536.0f);
    }
    
    public static final float toFloat(final int n) {
        return n / 65536.0f;
    }
    
    public static final int mult(final int n, final int n2) {
        return (int)(n * n2 / 65536L);
    }
    
    public static final int div(final int n, final int n2) {
        return (n << 16) / n2;
    }
}
