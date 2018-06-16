// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

public class IntBitfield
{
    public static final int UNIT_SIZE = 32;
    private static final long UNIT_SHIFT_L = 5L;
    private static final int UNIT_SHIFT_I = 5;
    private final int[] storage;
    private final long bitsCountL;
    private final int bitsCountI;
    
    public IntBitfield(final long n) {
        final int n2 = (int)Math.max(1L, n + 31L >>> 5);
        this.storage = new int[n2];
        this.bitsCountL = n2 << 5;
        this.bitsCountI = ((this.bitsCountL > 2147483647L) ? Integer.MAX_VALUE : ((int)this.bitsCountL));
    }
    
    public IntBitfield(final int n) {
        final int max = Math.max(1, n + 31 >>> 5);
        this.storage = new int[max];
        this.bitsCountI = max << 5;
        this.bitsCountL = this.bitsCountI;
    }
    
    private final void check(final long n) {
        if (0L > n || n >= this.bitsCountL) {
            throw new ArrayIndexOutOfBoundsException("Bitnum should be within [0.." + (this.bitsCountL - 1L) + "], but is " + n);
        }
    }
    
    private final void check(final int n) {
        if (0 > n || n >= this.bitsCountI) {
            throw new ArrayIndexOutOfBoundsException("Bitnum should be within [0.." + (this.bitsCountI - 1) + "], but is " + n);
        }
    }
    
    public final long capacity() {
        return this.bitsCountL;
    }
    
    public final boolean get(final long n) {
        this.check(n);
        final int n2 = (int)(n >>> 5);
        return 0x0 != (this.storage[n2] & 1 << (int)(n - (n2 << 5)));
    }
    
    public final boolean get(final int n) {
        this.check(n);
        final int n2 = n >>> 5;
        return 0x0 != (this.storage[n2] & 1 << n - (n2 << 5));
    }
    
    public final boolean put(final long n, final boolean b) {
        this.check(n);
        final int n2 = (int)(n >>> 5);
        final int n3 = 1 << (int)(n - (n2 << 5));
        final boolean b2 = 0x0 != (this.storage[n2] & n3);
        if (b2 != b) {
            if (b) {
                final int[] storage = this.storage;
                final int n4 = n2;
                storage[n4] |= n3;
            }
            else {
                final int[] storage2 = this.storage;
                final int n5 = n2;
                storage2[n5] &= ~n3;
            }
        }
        return b2;
    }
    
    public final boolean put(final int n, final boolean b) {
        this.check(n);
        final int n2 = n >>> 5;
        final int n3 = 1 << n - (n2 << 5);
        final boolean b2 = 0x0 != (this.storage[n2] & n3);
        if (b2 != b) {
            if (b) {
                final int[] storage = this.storage;
                final int n4 = n2;
                storage[n4] |= n3;
            }
            else {
                final int[] storage2 = this.storage;
                final int n5 = n2;
                storage2[n5] &= ~n3;
            }
        }
        return b2;
    }
    
    public static final int getBitCount(final int n) {
        return Bitfield.Util.bitCount(n);
    }
    
    public long getBitCount() {
        long n = 0L;
        for (int i = this.storage.length - 1; i >= 0; --i) {
            n += Bitfield.Util.bitCount(this.storage[i]);
        }
        return n;
    }
}
