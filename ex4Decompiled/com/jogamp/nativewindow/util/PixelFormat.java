// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

import com.jogamp.common.util.Bitfield;
import java.util.Arrays;

public enum PixelFormat
{
    LUMINANCE(new CType[] { CType.Y }, 1, 8, 8), 
    RGB565(new CType[] { CType.R, CType.G, CType.B }, new int[] { 31, 63, 31 }, new int[] { 0, 5, 11 }, 16), 
    BGR565(new CType[] { CType.B, CType.G, CType.R }, new int[] { 31, 63, 31 }, new int[] { 0, 5, 11 }, 16), 
    RGBA5551(new CType[] { CType.R, CType.G, CType.B, CType.A }, new int[] { 31, 31, 31, 1 }, new int[] { 0, 5, 10, 15 }, 16), 
    ABGR1555(new CType[] { CType.A, CType.B, CType.G, CType.R }, new int[] { 1, 31, 31, 31 }, new int[] { 0, 1, 6, 11 }, 16), 
    RGB888(new CType[] { CType.R, CType.G, CType.B }, 3, 8, 24), 
    BGR888(new CType[] { CType.B, CType.G, CType.R }, 3, 8, 24), 
    RGBx8888(new CType[] { CType.R, CType.G, CType.B }, 3, 8, 32), 
    BGRx8888(new CType[] { CType.B, CType.G, CType.R }, 3, 8, 32), 
    RGBA8888(new CType[] { CType.R, CType.G, CType.B, CType.A }, 4, 8, 32), 
    ABGR8888(new CType[] { CType.A, CType.B, CType.G, CType.R }, 4, 8, 32), 
    ARGB8888(new CType[] { CType.A, CType.R, CType.G, CType.B }, 4, 8, 32), 
    BGRA8888(new CType[] { CType.B, CType.G, CType.R, CType.A }, 4, 8, 32);
    
    public final Composition comp;
    
    private PixelFormat(final CType[] array, final int n2, final int n3, final int n4) {
        this.comp = new PackedComposition(array, n2, n3, n4);
    }
    
    private PixelFormat(final CType[] array, final int[] array2, final int[] array3, final int n2) {
        this.comp = new PackedComposition(array, array2, array3, n2);
    }
    
    public static PixelFormat valueOf(final Composition composition) {
        final PixelFormat[] values = values();
        for (int i = values.length - 1; i >= 0; --i) {
            final PixelFormat pixelFormat = values[i];
            if (composition.hashCode() == pixelFormat.comp.hashCode() && composition.equals(pixelFormat.comp)) {
                return pixelFormat;
            }
        }
        return null;
    }
    
    private static String toHexString(final int[] array, final int[] array2, final int[] array3) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int length = array2.length, i = 0; i < length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]).append(": ").append("0x").append(Integer.toHexString(array2[i])).append(" << ").append(array3[i]);
        }
        return sb.append("]").toString();
    }
    
    public enum CType
    {
        R, 
        G, 
        B, 
        A, 
        Y, 
        U, 
        V;
    }
    
    public static class PackedComposition implements Composition
    {
        private final CType[] compOrder;
        private final int[] compMask;
        private final int[] compBitCount;
        private final int[] compBitShift;
        private final int bitsPerPixel;
        private final int bitStride;
        private final boolean uniform;
        private final int hashCode;
        
        @Override
        public final String toString() {
            return String.format("PackedComp[order %s, stride %d, bpp %d, uni %b, comp %d: %s]", Arrays.toString(this.compOrder), this.bitStride, this.bitsPerPixel, this.uniform, this.compMask.length, toHexString(this.compBitCount, this.compMask, this.compBitShift));
        }
        
        public PackedComposition(final CType[] compOrder, final int n, final int n2, final int bitStride) {
            this.compOrder = compOrder;
            this.compMask = new int[n];
            this.compBitShift = new int[n];
            this.compBitCount = new int[n];
            final int n3 = (1 << n2) - 1;
            for (int i = 0; i < n; ++i) {
                this.compMask[i] = n3;
                this.compBitShift[i] = n2 * i;
                this.compBitCount[i] = n2;
            }
            this.uniform = true;
            this.bitsPerPixel = n2 * n;
            this.bitStride = bitStride;
            if (this.bitStride < this.bitsPerPixel) {
                throw new IllegalArgumentException(String.format("bit-stride %d < bitsPerPixel %d", this.bitStride, this.bitsPerPixel));
            }
            this.hashCode = this.hashCodeImpl();
        }
        
        public PackedComposition(final CType[] compOrder, final int[] compMask, final int[] compBitShift, final int bitStride) {
            this.compOrder = compOrder;
            this.compMask = compMask;
            this.compBitShift = compBitShift;
            this.compBitCount = new int[compMask.length];
            int bitsPerPixel = 0;
            boolean uniform = true;
            for (int i = compMask.length - 1; i >= 0; --i) {
                final int n = compMask[i];
                final int bitCount = Bitfield.Util.bitCount(n);
                bitsPerPixel += bitCount;
                this.compBitCount[i] = bitCount;
                if (i > 0 && uniform) {
                    uniform = (compMask[i - 1] == n);
                }
            }
            this.uniform = uniform;
            this.bitsPerPixel = bitsPerPixel;
            this.bitStride = bitStride;
            if (this.bitStride < this.bitsPerPixel) {
                throw new IllegalArgumentException(String.format("bit-stride %d < bitsPerPixel %d", this.bitStride, this.bitsPerPixel));
            }
            this.hashCode = this.hashCodeImpl();
        }
        
        @Override
        public final boolean isUniform() {
            return this.uniform;
        }
        
        @Override
        public final boolean isInterleaved() {
            return true;
        }
        
        @Override
        public final int componentCount() {
            return this.compMask.length;
        }
        
        @Override
        public final int bitsPerPixel() {
            return this.bitsPerPixel;
        }
        
        @Override
        public final int bitStride() {
            return this.bitStride;
        }
        
        @Override
        public final int bytesPerPixel() {
            return (7 + this.bitStride) / 8;
        }
        
        @Override
        public final CType[] componentOrder() {
            return this.compOrder;
        }
        
        @Override
        public final int find(final CType cType) {
            return PixelFormatUtil.find(cType, this.compOrder, false);
        }
        
        @Override
        public final int[] componentBitMask() {
            return this.compMask;
        }
        
        @Override
        public final int[] componentBitCount() {
            return this.compBitCount;
        }
        
        @Override
        public final int[] componentBitShift() {
            return this.compBitShift;
        }
        
        @Override
        public final int decodeSingleI32(final int n, final int n2) {
            return n >>> this.compBitShift[n2] & this.compMask[n2];
        }
        
        @Override
        public final int decodeSingleI64(final long n, final int n2) {
            return (int)(0xFFFFFFFFL & n >>> this.compBitShift[n2]) & this.compMask[n2];
        }
        
        @Override
        public final int encodeSingleI32(final int n, final int n2) {
            return (n & this.compMask[n2]) << this.compBitShift[n2];
        }
        
        @Override
        public final long encodeSingleI64(final int n, final int n2) {
            return (0xFFFFFFFFL & (n & this.compMask[n2])) << this.compBitShift[n2];
        }
        
        @Override
        public final int encode3CompI32(final int n, final int n2, final int n3) {
            return (n & this.compMask[0]) << this.compBitShift[0] | (n2 & this.compMask[1]) << this.compBitShift[1] | (n3 & this.compMask[2]) << this.compBitShift[2];
        }
        
        @Override
        public final int encode4CompI32(final int n, final int n2, final int n3, final int n4) {
            return (n & this.compMask[0]) << this.compBitShift[0] | (n2 & this.compMask[1]) << this.compBitShift[1] | (n3 & this.compMask[2]) << this.compBitShift[2] | (n4 & this.compMask[3]) << this.compBitShift[3];
        }
        
        @Override
        public final int encodeSingleI8(final byte b, final int n) {
            return (b & this.compMask[n]) << this.compBitShift[n];
        }
        
        @Override
        public final int encode3CompI8(final byte b, final byte b2, final byte b3) {
            return (b & this.compMask[0]) << this.compBitShift[0] | (b2 & this.compMask[1]) << this.compBitShift[1] | (b3 & this.compMask[2]) << this.compBitShift[2];
        }
        
        @Override
        public final int encode4CompI8(final byte b, final byte b2, final byte b3, final byte b4) {
            return (b & this.compMask[0]) << this.compBitShift[0] | (b2 & this.compMask[1]) << this.compBitShift[1] | (b3 & this.compMask[2]) << this.compBitShift[2] | (b4 & this.compMask[3]) << this.compBitShift[3];
        }
        
        @Override
        public final float toFloat(final int n, final int n2, final boolean b) {
            if (b) {
                return (n >>> this.compBitShift[n2] & this.compMask[n2]) / this.compMask[n2];
            }
            return (n & this.compMask[n2]) / this.compMask[n2];
        }
        
        @Override
        public final int fromFloat(final float n, final int n2, final boolean b) {
            final int n3 = (int)(n * this.compMask[n2] + 0.5f);
            return b ? (n3 << this.compBitShift[n2]) : n3;
        }
        
        @Override
        public final int defaultValue(final int n, final boolean b) {
            final int n2 = (CType.A == this.compOrder[n] || CType.Y == this.compOrder[n]) ? this.compMask[n] : 0;
            return b ? (n2 << this.compBitShift[n]) : n2;
        }
        
        @Override
        public final int hashCode() {
            return this.hashCode;
        }
        
        private final int hashCodeImpl() {
            final int n = 31 + this.bitStride;
            final int n2 = (n << 5) - n + this.bitsPerPixel;
            int n3 = (n2 << 5) - n2 + this.compMask.length;
            for (int i = this.compOrder.length - 1; i >= 0; --i) {
                n3 = (n3 << 5) - n3 + this.compOrder[i].ordinal();
            }
            for (int j = this.compMask.length - 1; j >= 0; --j) {
                n3 = (n3 << 5) - n3 + this.compMask[j];
            }
            for (int k = this.compBitShift.length - 1; k >= 0; --k) {
                n3 = (n3 << 5) - n3 + this.compBitShift[k];
            }
            return n3;
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof PackedComposition) {
                final PackedComposition packedComposition = (PackedComposition)o;
                return this.bitStride == packedComposition.bitStride && this.bitsPerPixel == packedComposition.bitsPerPixel && Arrays.equals(this.compOrder, packedComposition.compOrder) && Arrays.equals(this.compMask, packedComposition.compMask) && Arrays.equals(this.compBitShift, packedComposition.compBitShift);
            }
            return false;
        }
    }
    
    public interface Composition
    {
        public static final int UNDEF = -1;
        
        boolean isUniform();
        
        boolean isInterleaved();
        
        int componentCount();
        
        int bitsPerPixel();
        
        int bitStride();
        
        int bytesPerPixel();
        
        CType[] componentOrder();
        
        int find(final CType p0);
        
        int[] componentBitMask();
        
        int[] componentBitCount();
        
        int[] componentBitShift();
        
        int decodeSingleI32(final int p0, final int p1);
        
        int decodeSingleI64(final long p0, final int p1);
        
        int encodeSingleI32(final int p0, final int p1);
        
        long encodeSingleI64(final int p0, final int p1);
        
        int encode3CompI32(final int p0, final int p1, final int p2);
        
        int encode4CompI32(final int p0, final int p1, final int p2, final int p3);
        
        int encodeSingleI8(final byte p0, final int p1);
        
        int encode3CompI8(final byte p0, final byte p1, final byte p2);
        
        int encode4CompI8(final byte p0, final byte p1, final byte p2, final byte p3);
        
        float toFloat(final int p0, final int p1, final boolean p2);
        
        int fromFloat(final float p0, final int p1, final boolean p2);
        
        int defaultValue(final int p0, final boolean p1);
        
        int hashCode();
        
        boolean equals(final Object p0);
        
        String toString();
    }
}
