// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

public class HashUtil
{
    public static int getAddrHash32_EqualDist(final long n) {
        final int n2 = 31 + (int)n;
        return (n2 << 5) - n2 + (int)(n >>> 32);
    }
    
    public static int getAddrSizeHash32_EqualDist(final long n, final long n2) {
        final int n3 = 31 + (int)n;
        final int n4 = (n3 << 5) - n3 + (int)(n >>> 32);
        final int n5 = (n4 << 5) - n4 + (int)n2;
        return (n5 << 5) - n5 + (int)(n2 >>> 32);
    }
    
    public static long getHash64(final long n, final long n2) {
        final long n3 = 31L + n;
        return (n3 << 5) - n3 + n2;
    }
}
