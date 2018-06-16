// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

import java.io.IOException;
import java.util.Arrays;
import com.jogamp.common.util.Bitstream;
import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;

public class PixelFormatUtil
{
    private static boolean DEBUG;
    
    public static final int find(final PixelFormat.CType cType, final PixelFormat.CType[] array, final boolean b) {
        int n;
        for (n = array.length - 1; n >= 0 && array[n] != cType; --n) {}
        if (0 > n && b && 1 == array.length && array[0] == PixelFormat.CType.Y && (PixelFormat.CType.R == cType || PixelFormat.CType.G == cType || PixelFormat.CType.B == cType)) {
            return 0;
        }
        return n;
    }
    
    public static int getShiftedI32(final int n, final byte[] array, final int n2) {
        if (n <= 4) {
            int n3 = 0;
            for (int i = 0; i < n; ++i) {
                n3 |= (0xFF & array[n2 + i]) << 8 * i;
            }
            return n3;
        }
        throw new UnsupportedOperationException(n + " bytesPerPixel too big, i.e. > 4");
    }
    
    public static long getShiftedI64(final int n, final byte[] array, final int n2) {
        if (n <= 8) {
            long n3 = 0L;
            for (int i = 0; i < n; ++i) {
                n3 |= (0xFF & array[n2 + i]) << 8 * i;
            }
            return n3;
        }
        throw new UnsupportedOperationException(n + " bytesPerPixel too big, i.e. > 8");
    }
    
    public static int getShiftedI32(final int n, final ByteBuffer byteBuffer, final boolean b) {
        if (n <= 4) {
            int n2 = 0;
            if (b) {
                final int position = byteBuffer.position();
                for (int i = 0; i < n; ++i) {
                    n2 |= (0xFF & byteBuffer.get(position + i)) << 8 * i;
                }
            }
            else {
                for (int j = 0; j < n; ++j) {
                    n2 |= (0xFF & byteBuffer.get()) << 8 * j;
                }
            }
            return n2;
        }
        throw new UnsupportedOperationException(n + " bytesPerPixel too big, i.e. > 4");
    }
    
    public static long getShiftedI64(final int n, final ByteBuffer byteBuffer, final boolean b) {
        if (n <= 8) {
            long n2 = 0L;
            if (b) {
                final int position = byteBuffer.position();
                for (int i = 0; i < n; ++i) {
                    n2 |= (0xFF & byteBuffer.get(position + i)) << 8 * i;
                }
            }
            else {
                for (int j = 0; j < n; ++j) {
                    n2 |= (0xFF & byteBuffer.get()) << 8 * j;
                }
            }
            return n2;
        }
        throw new UnsupportedOperationException(n + " bytesPerPixel too big, i.e. > 8");
    }
    
    public static PixelFormat getReversed(final PixelFormat pixelFormat) {
        switch (pixelFormat) {
            case RGB565: {
                return PixelFormat.BGR565;
            }
            case BGR565: {
                return PixelFormat.RGB565;
            }
            case RGBA5551: {
                return PixelFormat.ABGR1555;
            }
            case ABGR1555: {
                return PixelFormat.RGBA5551;
            }
            case RGB888: {
                return PixelFormat.BGR888;
            }
            case BGR888: {
                return PixelFormat.RGB888;
            }
            case RGBA8888: {
                return PixelFormat.ABGR8888;
            }
            case ABGR8888: {
                return PixelFormat.RGBA8888;
            }
            case ARGB8888: {
                return PixelFormat.BGRA8888;
            }
            case BGRA8888: {
                return PixelFormat.ABGR8888;
            }
            default: {
                return pixelFormat;
            }
        }
    }
    
    public static int convertToInt32(final PixelFormat pixelFormat, final byte b, final byte b2, final byte b3, final byte b4) {
        switch (pixelFormat) {
            case LUMINANCE: {
                final byte b5 = (byte)(((0xFF & b) + (0xFF & b2) + (0xFF & b3)) / 3 * b4);
                return 0xFF000000 | (0xFF & b5) << 16 | (0xFF & b5) << 8 | (0xFF & b5);
            }
            case RGB888: {
                return 0xFF000000 | (0xFF & b3) << 16 | (0xFF & b2) << 8 | (0xFF & b);
            }
            case BGR888: {
                return 0xFF000000 | (0xFF & b) << 16 | (0xFF & b2) << 8 | (0xFF & b3);
            }
            case RGBA8888: {
                return (0xFF & b4) << 24 | (0xFF & b3) << 16 | (0xFF & b2) << 8 | (0xFF & b);
            }
            case ABGR8888: {
                return (0xFF & b) << 24 | (0xFF & b2) << 16 | (0xFF & b3) << 8 | (0xFF & b4);
            }
            case ARGB8888: {
                return (0xFF & b3) << 24 | (0xFF & b2) << 16 | (0xFF & b) << 8 | (0xFF & b4);
            }
            case BGRA8888: {
                return (0xFF & b4) << 24 | (0xFF & b) << 16 | (0xFF & b2) << 8 | (0xFF & b3);
            }
            default: {
                throw new InternalError("Unhandled format " + pixelFormat);
            }
        }
    }
    
    public static int convertToInt32(final PixelFormat pixelFormat, final PixelFormat pixelFormat2, final ByteBuffer byteBuffer, int n) {
        byte b3 = 0;
        byte b2 = 0;
        byte b = 0;
        byte b4 = 0;
        switch (pixelFormat2) {
            case LUMINANCE: {
                b = (b2 = (b3 = byteBuffer.get(n++)));
                b4 = -1;
                break;
            }
            case RGB888: {
                b = byteBuffer.get(n++);
                b3 = byteBuffer.get(n++);
                b2 = byteBuffer.get(n++);
                b4 = -1;
                break;
            }
            case BGR888: {
                b2 = byteBuffer.get(n++);
                b3 = byteBuffer.get(n++);
                b = byteBuffer.get(n++);
                b4 = -1;
                break;
            }
            case RGBA8888: {
                b = byteBuffer.get(n++);
                b3 = byteBuffer.get(n++);
                b2 = byteBuffer.get(n++);
                b4 = byteBuffer.get(n++);
                break;
            }
            case ABGR8888: {
                b4 = byteBuffer.get(n++);
                b2 = byteBuffer.get(n++);
                b3 = byteBuffer.get(n++);
                b = byteBuffer.get(n++);
                break;
            }
            case ARGB8888: {
                b4 = byteBuffer.get(n++);
                b = byteBuffer.get(n++);
                b3 = byteBuffer.get(n++);
                b2 = byteBuffer.get(n++);
                break;
            }
            case BGRA8888: {
                b2 = byteBuffer.get(n++);
                b3 = byteBuffer.get(n++);
                b = byteBuffer.get(n++);
                b4 = byteBuffer.get(n++);
                break;
            }
            default: {
                throw new InternalError("Unhandled format " + pixelFormat2);
            }
        }
        return convertToInt32(pixelFormat, b, b3, b2, b4);
    }
    
    public static int convertToInt32(final PixelFormat pixelFormat, final PixelFormat pixelFormat2, final int n) {
        byte b3 = 0;
        byte b2 = 0;
        byte b = 0;
        byte b4 = 0;
        switch (pixelFormat2) {
            case LUMINANCE: {
                b = (b2 = (b3 = (byte)n));
                b4 = -1;
                break;
            }
            case RGB888: {
                b = (byte)n;
                b3 = (byte)(n >>> 8);
                b2 = (byte)(n >>> 16);
                b4 = -1;
                break;
            }
            case BGR888: {
                b2 = (byte)n;
                b3 = (byte)(n >>> 8);
                b = (byte)(n >>> 16);
                b4 = -1;
                break;
            }
            case RGBA8888: {
                b = (byte)n;
                b3 = (byte)(n >>> 8);
                b2 = (byte)(n >>> 16);
                b4 = (byte)(n >>> 24);
                break;
            }
            case ABGR8888: {
                b4 = (byte)n;
                b2 = (byte)(n >>> 8);
                b3 = (byte)(n >>> 16);
                b = (byte)(n >>> 24);
                break;
            }
            case ARGB8888: {
                b4 = (byte)n;
                b = (byte)(n >>> 8);
                b3 = (byte)(n >>> 16);
                b2 = (byte)(n >>> 24);
                break;
            }
            case BGRA8888: {
                b2 = (byte)n;
                b3 = (byte)(n >>> 8);
                b = (byte)(n >>> 16);
                b4 = (byte)(n >>> 24);
                break;
            }
            default: {
                throw new InternalError("Unhandled format " + pixelFormat2);
            }
        }
        return convertToInt32(pixelFormat, b, b3, b2, b4);
    }
    
    public static PixelRectangle convert(final PixelRectangle pixelRectangle, final PixelFormat pixelFormat, final int n, final boolean b, final boolean b2) {
        final int width = pixelRectangle.getSize().getWidth();
        final int height = pixelRectangle.getSize().getHeight();
        final int bytesPerPixel = pixelFormat.comp.bytesPerPixel();
        int n2;
        if (0 != n) {
            n2 = n;
        }
        else {
            n2 = bytesPerPixel * width;
        }
        final int n3 = n2 * height;
        final ByteBuffer byteBuffer = b2 ? Buffers.newDirectByteBuffer(n3) : ByteBuffer.allocate(n3).order(pixelRectangle.getPixels().order());
        convert(pixelRectangle, byteBuffer, pixelFormat, b, n2);
        return new PixelRectangle.GenericPixelRect(pixelFormat, pixelRectangle.getSize(), n2, b, byteBuffer);
    }
    
    public static void convert(final PixelRectangle pixelRectangle, final ByteBuffer byteBuffer, final PixelFormat pixelFormat, final boolean b, final int n) throws IllegalStateException {
        convert(pixelRectangle.getSize().getWidth(), pixelRectangle.getSize().getHeight(), pixelRectangle.getPixels(), pixelRectangle.getPixelformat(), pixelRectangle.isGLOriented(), pixelRectangle.getStride(), byteBuffer, pixelFormat, b, n);
    }
    
    public static void convert(final int n, final int n2, final ByteBuffer byteBuffer, final PixelFormat pixelFormat, final boolean b, int n3, final ByteBuffer byteBuffer2, final PixelFormat pixelFormat2, final boolean b2, int n4) throws IllegalStateException, IllegalArgumentException {
        final PixelFormat.Composition comp = pixelFormat.comp;
        final PixelFormat.Composition comp2 = pixelFormat2.comp;
        final int bytesPerPixel = comp.bytesPerPixel();
        final int bytesPerPixel2 = comp2.bytesPerPixel();
        if (0 != n3) {
            if (n3 < bytesPerPixel * n) {
                throw new IllegalArgumentException(String.format("Invalid %s stride %d, must be greater than bytesPerPixel %d * width %d", "source", n3, bytesPerPixel, n));
            }
        }
        else {
            n3 = bytesPerPixel * n;
        }
        if (0 != n4) {
            if (n4 < bytesPerPixel2 * n) {
                throw new IllegalArgumentException(String.format("Invalid %s stride %d, must be greater than bytesPerPixel %d * width %d", "destination", n4, bytesPerPixel2, n));
            }
        }
        else {
            n4 = bytesPerPixel2 * n;
        }
        final int bitStride = comp2.bitStride();
        final boolean b3 = b != b2;
        final boolean b4 = comp.equals(comp2) && 0 == bitStride % 8;
        if (PixelFormatUtil.DEBUG) {
            System.err.println("XXX: size " + n + "x" + n2 + ", fast_copy " + b4);
            System.err.println("XXX: SRC fmt " + pixelFormat + ", " + comp + ", stride " + n3 + ", isGLOrient " + b);
            System.err.println("XXX: DST fmt " + pixelFormat2 + ", " + comp2 + ", stride " + n4 + ", isGLOrient " + b2);
        }
        if (b4) {
            for (int i = 0; i < n2; ++i) {
                int n5 = b3 ? ((n2 - 1 - i) * n3) : (i * n3);
                int n6 = n4 * i;
                for (int j = 0; j < n; ++j) {
                    byteBuffer2.put(n6 + 0, byteBuffer.get(n5 + 0));
                    if (2 <= bytesPerPixel2) {
                        byteBuffer2.put(n6 + 1, byteBuffer.get(n5 + 1));
                        if (3 <= bytesPerPixel2) {
                            byteBuffer2.put(n6 + 2, byteBuffer.get(n5 + 2));
                            if (4 <= bytesPerPixel2) {
                                byteBuffer2.put(n6 + 3, byteBuffer.get(n5 + 3));
                            }
                        }
                    }
                    n5 += bytesPerPixel;
                    n6 += bytesPerPixel2;
                }
            }
        }
        else {
            final ComponentMap componentMap = new ComponentMap(pixelFormat.comp, pixelFormat2.comp);
            final Bitstream<ByteBuffer> bitstream = new Bitstream<ByteBuffer>(new Bitstream.ByteBufferStream(byteBuffer), false);
            bitstream.setThrowIOExceptionOnEOF(true);
            final Bitstream bitstream2 = new Bitstream<ByteBuffer>((Bitstream.ByteStream<T>)new Bitstream.ByteBufferStream(byteBuffer2), true);
            bitstream2.setThrowIOExceptionOnEOF(true);
            if (PixelFormatUtil.DEBUG) {
                System.err.println("XXX: cmap.dst2src " + Arrays.toString(componentMap.dst2src));
                System.err.println("XXX: cmap.src2dst " + Arrays.toString(componentMap.src2dst));
                System.err.println("XXX: cmap.srcRGBA " + Arrays.toString(componentMap.srcRGBA));
                System.err.println("XXX: srcBitStream " + bitstream);
                System.err.println("XXX: dstBitStream " + bitstream2);
            }
            try {
                for (int k = 0; k < n2; ++k) {
                    bitstream.position(b3 ? ((n2 - 1 - k) * n3 * 8) : (k * n3 * 8));
                    for (int l = 0; l < n; ++l) {
                        convert(componentMap, comp2, bitstream2, comp, bitstream);
                    }
                    bitstream2.skip(n4 * 8 - bitStride * n);
                }
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (PixelFormatUtil.DEBUG) {
                System.err.println("XXX: srcBitStream " + bitstream);
                System.err.println("XXX: dstBitStream " + bitstream2);
            }
        }
    }
    
    public static void convert(final ComponentMap componentMap, final PixelFormat.Composition composition, final Bitstream<ByteBuffer> bitstream, final PixelFormat.Composition composition2, final Bitstream<ByteBuffer> bitstream2) throws IllegalStateException, IOException {
        final int componentCount = composition2.componentCount();
        final int componentCount2 = composition.componentCount();
        final int[] array = new int[componentCount];
        final int[] array2 = new int[componentCount2];
        final int[] componentBitCount = composition2.componentBitCount();
        final int[] componentBitMask = composition2.componentBitMask();
        final int[] componentBitCount2 = composition.componentBitCount();
        for (int i = 0; i < componentCount; ++i) {
            array[i] = (bitstream2.readBits31(componentBitCount[i]) & componentBitMask[i]);
        }
        bitstream2.skip(composition2.bitStride() - composition2.bitsPerPixel());
        for (int j = 0; j < componentCount2; ++j) {
            array2[j] = composition.defaultValue(j, false);
        }
        if (componentCount2 && PixelFormat.CType.Y == composition.componentOrder()[0] && componentMap.hasSrcRGB) {
            final int n = array[componentMap.srcRGBA[0]];
            final int n2 = array[componentMap.srcRGBA[1]];
            final int n3 = array[componentMap.srcRGBA[2]];
            final float float1 = composition2.toFloat(n, componentMap.srcRGBA[0], false);
            final float float2 = composition2.toFloat(n2, componentMap.srcRGBA[1], false);
            final float float3 = composition2.toFloat(n3, componentMap.srcRGBA[2], false);
            final int n4 = 1;
            final float n5 = 1.0f;
            final float n6 = (float1 + float2 + float3) * n5 / 3.0f;
            final int fromFloat = composition.fromFloat(n6, 0, false);
            bitstream.writeBits31(componentBitCount2[0], fromFloat);
            bitstream.skip(composition.bitStride() - composition.bitsPerPixel());
            if (PixelFormatUtil.DEBUG && bitstream2.position() <= 32L) {
                System.err.printf("convert: rgb[a] -> Y: rgb 0x%02X 0x%02X 0x%02X 0x%02X -> %f %f %f %f -> %f -> dstC 0 0x%08X (%d bits: %s)%n", n, n2, n3, n4, float1, float2, float3, n5, n6, fromFloat, componentBitCount2[0], Bitstream.toBinString(true, fromFloat, componentBitCount2[0]));
            }
            return;
        }
        for (int k = 0; k < componentCount2; ++k) {
            final int n7;
            if (0 <= (n7 = componentMap.dst2src[k])) {
                final float float4 = composition2.toFloat(array[n7], n7, false);
                final int fromFloat2 = composition.fromFloat(float4, k, false);
                bitstream.writeBits31(componentBitCount2[k], fromFloat2);
                if (PixelFormatUtil.DEBUG && bitstream2.position() <= 32L) {
                    System.err.printf("convert: srcC %d: 0x%08X -> %f -> dstC %d 0x%08X (%d bits: %s)%n", n7, array[n7], float4, k, fromFloat2, componentBitCount2[k], Bitstream.toBinString(true, fromFloat2, componentBitCount2[k]));
                }
            }
            else {
                bitstream.writeBits31(componentBitCount2[k], array2[k]);
                if (PixelFormatUtil.DEBUG && bitstream2.position() <= 32L) {
                    System.err.printf("convert: srcC %d: undef -> dstC %d 0x%08X (%d bits: %s)%n", n7, k, array2[k], componentBitCount2[k], Bitstream.toBinString(true, array2[k], componentBitCount2[k]));
                }
            }
        }
        bitstream.skip(composition.bitStride() - composition.bitsPerPixel());
    }
    
    static {
        PixelFormatUtil.DEBUG = false;
    }
    
    public static class ComponentMap
    {
        final int[] dst2src;
        final int[] src2dst;
        final int[] srcRGBA;
        final boolean hasSrcRGB;
        
        public ComponentMap(final PixelFormat.Composition composition, final PixelFormat.Composition composition2) {
            final int componentCount = composition.componentCount();
            final int componentCount2 = composition2.componentCount();
            final PixelFormat.CType[] componentOrder = composition.componentOrder();
            final PixelFormat.CType[] componentOrder2 = composition2.componentOrder();
            this.dst2src = new int[componentCount2];
            for (int i = 0; i < componentCount2; ++i) {
                this.dst2src[i] = PixelFormatUtil.find(componentOrder2[i], componentOrder, true);
            }
            this.src2dst = new int[componentCount];
            for (int j = 0; j < componentCount; ++j) {
                this.src2dst[j] = PixelFormatUtil.find(componentOrder[j], componentOrder2, true);
            }
            (this.srcRGBA = new int[4])[0] = PixelFormatUtil.find(PixelFormat.CType.R, componentOrder, false);
            this.srcRGBA[1] = PixelFormatUtil.find(PixelFormat.CType.G, componentOrder, false);
            this.srcRGBA[2] = PixelFormatUtil.find(PixelFormat.CType.B, componentOrder, false);
            this.srcRGBA[3] = PixelFormatUtil.find(PixelFormat.CType.A, componentOrder, false);
            this.hasSrcRGB = (0 <= this.srcRGBA[0] && 0 <= this.srcRGBA[1] && 0 <= this.srcRGBA[2]);
        }
    }
}
