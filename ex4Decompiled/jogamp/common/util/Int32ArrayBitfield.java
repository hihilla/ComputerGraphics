// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util;

import com.jogamp.common.util.Bitfield;

public class Int32ArrayBitfield implements Bitfield
{
    private static final int UNIT_SHIFT = 5;
    private final int[] storage;
    private final int bitSize;
    
    public Int32ArrayBitfield(final int n) {
        final int max = Math.max(1, n + 31 >>> 5);
        this.storage = new int[max];
        this.bitSize = max << 5;
    }
    
    @Override
    public int size() {
        return this.bitSize;
    }
    
    @Override
    public final void clearField(final boolean b) {
        int n;
        if (b) {
            n = -1;
        }
        else {
            n = 0;
        }
        for (int i = this.storage.length - 1; i >= 0; --i) {
            this.storage[i] = n;
        }
    }
    
    private static final void check(final int n, final int n2) throws IndexOutOfBoundsException {
        if (0 > n2 || n2 >= n) {
            throw new IndexOutOfBoundsException("Bitnum should be within [0.." + (n - 1) + "], but is " + n2);
        }
    }
    
    @Override
    public final int get32(final int n, final int n2) throws IndexOutOfBoundsException {
        if (0 > n2 || n2 > 32) {
            throw new IndexOutOfBoundsException("length should be within [0..32], but is " + n2);
        }
        check(this.bitSize - n2 + 1, n);
        final int n3 = n >>> 5;
        final int n4 = 32 - (n - (n3 << 5));
        if (32 == n4) {
            return Util.getBitMask(n2) & this.storage[n3];
        }
        final int min = Math.min(n2, n4);
        final int n5 = (1 << min) - 1 & this.storage[n3] >>> n;
        final int n6 = n2 - min;
        if (n6 > 0) {
            return n5 | ((1 << n6) - 1 & this.storage[n3 + 1]) << min;
        }
        return n5;
    }
    
    @Override
    public final void put32(final int n, final int n2, final int n3) throws IndexOutOfBoundsException {
        if (0 > n2 || n2 > 32) {
            throw new IndexOutOfBoundsException("length should be within [0..32], but is " + n2);
        }
        check(this.bitSize - n2 + 1, n);
        final int n4 = n >>> 5;
        final int n5 = 32 - (n - (n4 << 5));
        if (32 == n5) {
            final int bitMask = Util.getBitMask(n2);
            this.storage[n4] = ((~bitMask & this.storage[n4]) | (bitMask & n3));
        }
        else {
            final int min = Math.min(n2, n5);
            final int n6 = (1 << min) - 1;
            this.storage[n4] = ((~(n6 << n) & this.storage[n4]) | (n6 & n3) << n);
            final int n7 = n2 - min;
            if (n7 > 0) {
                final int n8 = (1 << n7) - 1;
                this.storage[n4 + 1] = ((~n8 & this.storage[n4 + 1]) | (n8 & n3 >>> min));
            }
        }
    }
    
    @Override
    public final int copy32(final int n, final int n2, final int n3) throws IndexOutOfBoundsException {
        final int get32 = this.get32(n, n3);
        this.put32(n2, n3, get32);
        return get32;
    }
    
    @Override
    public final boolean get(final int n) throws IndexOutOfBoundsException {
        check(this.bitSize, n);
        final int n2 = n >>> 5;
        return 0x0 != (this.storage[n2] & 1 << n - (n2 << 5));
    }
    
    @Override
    public final boolean put(final int n, final boolean b) throws IndexOutOfBoundsException {
        check(this.bitSize, n);
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
    
    @Override
    public final void set(final int n) throws IndexOutOfBoundsException {
        check(this.bitSize, n);
        final int n2 = n >>> 5;
        final int n3 = 1 << n - (n2 << 5);
        final int[] storage = this.storage;
        final int n4 = n2;
        storage[n4] |= n3;
    }
    
    @Override
    public final void clear(final int n) throws IndexOutOfBoundsException {
        check(this.bitSize, n);
        final int n2 = n >>> 5;
        final int n3 = 1 << n - (n2 << 5);
        final int[] storage = this.storage;
        final int n4 = n2;
        storage[n4] &= ~n3;
    }
    
    @Override
    public final boolean copy(final int n, final int n2) throws IndexOutOfBoundsException {
        check(this.bitSize, n);
        check(this.bitSize, n2);
        final int n3 = n >>> 5;
        final boolean b = 0x0 != (this.storage[n3] & 1 << n - (n3 << 5));
        final int n4 = n2 >>> 5;
        final int n5 = 1 << n2 - (n4 << 5);
        if (b) {
            final int[] storage = this.storage;
            final int n6 = n4;
            storage[n6] |= n5;
        }
        else {
            final int[] storage2 = this.storage;
            final int n7 = n4;
            storage2[n7] &= ~n5;
        }
        return b;
    }
    
    @Override
    public int bitCount() {
        int n = 0;
        for (int i = this.storage.length - 1; i >= 0; --i) {
            n += Util.bitCount(this.storage[i]);
        }
        return n;
    }
}
