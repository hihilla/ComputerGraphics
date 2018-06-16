// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import jogamp.common.util.SyncedBitfield;
import jogamp.common.util.Int32ArrayBitfield;
import jogamp.common.util.Int32Bitfield;

public interface Bitfield
{
    public static final int UNSIGNED_INT_MAX_VALUE = -1;
    
    int size();
    
    void clearField(final boolean p0);
    
    int get32(final int p0, final int p1) throws IndexOutOfBoundsException;
    
    void put32(final int p0, final int p1, final int p2) throws IndexOutOfBoundsException;
    
    int copy32(final int p0, final int p1, final int p2) throws IndexOutOfBoundsException;
    
    boolean get(final int p0) throws IndexOutOfBoundsException;
    
    boolean put(final int p0, final boolean p1) throws IndexOutOfBoundsException;
    
    void set(final int p0) throws IndexOutOfBoundsException;
    
    void clear(final int p0) throws IndexOutOfBoundsException;
    
    boolean copy(final int p0, final int p1) throws IndexOutOfBoundsException;
    
    int bitCount();
    
    public static class Util
    {
        public static int getBitMask(final int n) {
            if (32 > n) {
                return (1 << n) - 1;
            }
            if (32 == n) {
                return -1;
            }
            throw new IndexOutOfBoundsException("n <= 32 expected, is " + n);
        }
        
        public static final int bitCount(int n) {
            n -= (n >>> 1 & 0x55555555);
            n = (n & 0x33333333) + (n >>> 2 & 0x33333333);
            n = (n + (n >>> 4) & 0xF0F0F0F);
            n += n >>> 8;
            n += n >>> 16;
            return n & 0x3F;
        }
    }
    
    public static class Factory
    {
        public static Bitfield create(final int n) {
            if (32 >= n) {
                return new Int32Bitfield();
            }
            return new Int32ArrayBitfield(n);
        }
        
        public static Bitfield synchronize(final Bitfield bitfield) {
            return new SyncedBitfield(bitfield);
        }
    }
}
