// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

public class ValueConv
{
    public static final byte float_to_byte(final float n, final boolean b) {
        if (b) {
            return (byte)(n * ((n > 0.0f) ? 127.0f : 128.0f));
        }
        return (byte)(n * 255.0f);
    }
    
    public static final short float_to_short(final float n, final boolean b) {
        if (b) {
            return (short)(n * ((n > 0.0f) ? 32767.0f : 32768.0f));
        }
        return (short)(n * 65535.0f);
    }
    
    public static final int float_to_int(final float n, final boolean b) {
        if (b) {
            return (int)(n * ((n > 0.0f) ? 2.147483647E9 : 2.147483648E9));
        }
        return (int)(n * 4.294967295E9);
    }
    
    public static final byte double_to_byte(final double n, final boolean b) {
        if (b) {
            return (byte)(n * ((n > 0.0) ? 127.0 : 128.0));
        }
        return (byte)(n * 255.0);
    }
    
    public static final short double_to_short(final double n, final boolean b) {
        if (b) {
            return (short)(n * ((n > 0.0) ? 32767.0 : 32768.0));
        }
        return (short)(n * 65535.0);
    }
    
    public static final int double_to_int(final double n, final boolean b) {
        if (b) {
            return (int)(n * ((n > 0.0) ? 2.147483647E9 : 2.147483648E9));
        }
        return (int)(n * 4.294967295E9);
    }
    
    public static final float byte_to_float(final byte b, final boolean b2) {
        if (b2) {
            return (b & 0xFF) / ((b > 0) ? 127.0f : -128.0f);
        }
        return (b & 0xFF) / 255.0f;
    }
    
    public static final double byte_to_double(final byte b, final boolean b2) {
        if (b2) {
            return (b & 0xFF) / ((b > 0) ? 127.0 : -128.0);
        }
        return (b & 0xFF) / 255.0;
    }
    
    public static final float short_to_float(final short n, final boolean b) {
        if (b) {
            return (n & 0xFFFF) / ((n > 0) ? 32767.0f : -32768.0f);
        }
        return (n & 0xFFFF) / 65535.0f;
    }
    
    public static final double short_to_double(final short n, final boolean b) {
        if (b) {
            return (n & 0xFFFF) / ((n > 0) ? 32767.0 : -32768.0);
        }
        return (n & 0xFFFF) / 65535.0;
    }
    
    public static final float int_to_float(final int n, final boolean b) {
        if (b) {
            return (float)(n / ((n > 0) ? 2.147483647E9 : 2.147483648E9));
        }
        return (float)((n & 0xFFFFFFFFL) / 4.294967295E9);
    }
    
    public static final double int_to_double(final int n, final boolean b) {
        if (b) {
            return n / ((n > 0) ? 2.147483647E9 : 2.147483648E9);
        }
        return (n & 0xFFFFFFFFL) / 4.294967295E9;
    }
    
    public static final short byte_to_short(final byte b, final boolean b2, final boolean b3) {
        return float_to_short(byte_to_float(b, b2), b3);
    }
    
    public static final int byte_to_int(final byte b, final boolean b2, final boolean b3) {
        return float_to_int(byte_to_float(b, b2), b3);
    }
    
    public static final byte short_to_byte(final short n, final boolean b, final boolean b2) {
        return float_to_byte(short_to_float(n, b), b2);
    }
    
    public static final int short_to_int(final short n, final boolean b, final boolean b2) {
        return float_to_int(short_to_float(n, b), b2);
    }
    
    public static final byte int_to_byte(final int n, final boolean b, final boolean b2) {
        return float_to_byte(int_to_float(n, b), b2);
    }
    
    public static final short int_to_short(final int n, final boolean b, final boolean b2) {
        return float_to_short(int_to_float(n, b), b2);
    }
}
