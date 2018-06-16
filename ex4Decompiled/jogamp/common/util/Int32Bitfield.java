// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util;

import com.jogamp.common.util.Bitfield;

public class Int32Bitfield implements Bitfield
{
    private static final int UNIT_SIZE = 32;
    private int storage;
    
    public Int32Bitfield() {
        this.storage = 0;
    }
    
    @Override
    public int size() {
        return 32;
    }
    
    @Override
    public final void clearField(final boolean b) {
        if (b) {
            this.storage = -1;
        }
        else {
            this.storage = 0;
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
        check(32 - n2 + 1, n);
        final int n3 = 32 - n;
        if (32 == n3) {
            return Util.getBitMask(n2) & this.storage;
        }
        return (1 << Math.min(n2, n3)) - 1 & this.storage >>> n;
    }
    
    @Override
    public final void put32(final int n, final int n2, final int n3) throws IndexOutOfBoundsException {
        if (0 > n2 || n2 > 32) {
            throw new IndexOutOfBoundsException("length should be within [0..32], but is " + n2);
        }
        check(32 - n2 + 1, n);
        final int n4 = 32 - n;
        if (32 == n4) {
            final int bitMask = Util.getBitMask(n2);
            this.storage = ((~bitMask & this.storage) | (bitMask & n3));
        }
        else {
            final int n5 = (1 << Math.min(n2, n4)) - 1;
            this.storage = ((~(n5 << n) & this.storage) | (n5 & n3) << n);
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
        check(32, n);
        return 0x0 != (this.storage & 1 << n);
    }
    
    @Override
    public final boolean put(final int n, final boolean b) throws IndexOutOfBoundsException {
        check(32, n);
        final int n2 = 1 << n;
        final boolean b2 = 0x0 != (this.storage & n2);
        if (b2 != b) {
            if (b) {
                this.storage |= n2;
            }
            else {
                this.storage &= ~n2;
            }
        }
        return b2;
    }
    
    @Override
    public final void set(final int n) throws IndexOutOfBoundsException {
        check(32, n);
        this.storage |= 1 << n;
    }
    
    @Override
    public final void clear(final int n) throws IndexOutOfBoundsException {
        check(32, n);
        this.storage &= ~(1 << n);
    }
    
    @Override
    public final boolean copy(final int n, final int n2) throws IndexOutOfBoundsException {
        check(32, n);
        check(32, n2);
        final boolean b = 0x0 != (this.storage & 1 << n);
        final int n3 = 1 << n2;
        if (b) {
            this.storage |= n3;
        }
        else {
            this.storage &= ~n3;
        }
        return b;
    }
    
    @Override
    public int bitCount() {
        return Util.bitCount(this.storage);
    }
}
