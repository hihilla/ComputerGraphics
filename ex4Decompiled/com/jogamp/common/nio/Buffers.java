// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import com.jogamp.common.util.ValueConv;
import java.nio.ByteOrder;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.ShortBuffer;
import java.nio.LongBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.DoubleBuffer;
import java.nio.ByteBuffer;

public class Buffers
{
    public static final int SIZEOF_BYTE = 1;
    public static final int SIZEOF_SHORT = 2;
    public static final int SIZEOF_CHAR = 2;
    public static final int SIZEOF_INT = 4;
    public static final int SIZEOF_FLOAT = 4;
    public static final int SIZEOF_LONG = 8;
    public static final int SIZEOF_DOUBLE = 8;
    
    public static ByteBuffer newDirectByteBuffer(final int n) {
        return nativeOrder(ByteBuffer.allocateDirect(n));
    }
    
    public static ByteBuffer newDirectByteBuffer(final byte[] array, final int n, final int n2) {
        return (ByteBuffer)newDirectByteBuffer(n2).put(array, n, n2).rewind();
    }
    
    public static ByteBuffer newDirectByteBuffer(final byte[] array, final int n) {
        return newDirectByteBuffer(array, n, array.length - n);
    }
    
    public static ByteBuffer newDirectByteBuffer(final byte[] array) {
        return newDirectByteBuffer(array, 0);
    }
    
    public static DoubleBuffer newDirectDoubleBuffer(final int n) {
        return newDirectByteBuffer(n * 8).asDoubleBuffer();
    }
    
    public static DoubleBuffer newDirectDoubleBuffer(final double[] array, final int n, final int n2) {
        return (DoubleBuffer)newDirectDoubleBuffer(n2).put(array, n, n2).rewind();
    }
    
    public static DoubleBuffer newDirectDoubleBuffer(final double[] array, final int n) {
        return newDirectDoubleBuffer(array, n, array.length - n);
    }
    
    public static DoubleBuffer newDirectDoubleBuffer(final double[] array) {
        return newDirectDoubleBuffer(array, 0);
    }
    
    public static FloatBuffer newDirectFloatBuffer(final int n) {
        return newDirectByteBuffer(n * 4).asFloatBuffer();
    }
    
    public static FloatBuffer newDirectFloatBuffer(final float[] array, final int n, final int n2) {
        return (FloatBuffer)newDirectFloatBuffer(n2).put(array, n, n2).rewind();
    }
    
    public static FloatBuffer newDirectFloatBuffer(final float[] array, final int n) {
        return newDirectFloatBuffer(array, n, array.length - n);
    }
    
    public static FloatBuffer newDirectFloatBuffer(final float[] array) {
        return newDirectFloatBuffer(array, 0);
    }
    
    public static IntBuffer newDirectIntBuffer(final int n) {
        return newDirectByteBuffer(n * 4).asIntBuffer();
    }
    
    public static IntBuffer newDirectIntBuffer(final int[] array, final int n, final int n2) {
        return (IntBuffer)newDirectIntBuffer(n2).put(array, n, n2).rewind();
    }
    
    public static IntBuffer newDirectIntBuffer(final int[] array, final int n) {
        return newDirectIntBuffer(array, n, array.length - n);
    }
    
    public static IntBuffer newDirectIntBuffer(final int[] array) {
        return newDirectIntBuffer(array, 0);
    }
    
    public static LongBuffer newDirectLongBuffer(final int n) {
        return newDirectByteBuffer(n * 8).asLongBuffer();
    }
    
    public static LongBuffer newDirectLongBuffer(final long[] array, final int n, final int n2) {
        return (LongBuffer)newDirectLongBuffer(n2).put(array, n, n2).rewind();
    }
    
    public static LongBuffer newDirectLongBuffer(final long[] array, final int n) {
        return newDirectLongBuffer(array, n, array.length - n);
    }
    
    public static LongBuffer newDirectLongBuffer(final long[] array) {
        return newDirectLongBuffer(array, 0);
    }
    
    public static ShortBuffer newDirectShortBuffer(final int n) {
        return newDirectByteBuffer(n * 2).asShortBuffer();
    }
    
    public static ShortBuffer newDirectShortBuffer(final short[] array, final int n, final int n2) {
        return (ShortBuffer)newDirectShortBuffer(n2).put(array, n, n2).rewind();
    }
    
    public static ShortBuffer newDirectShortBuffer(final short[] array, final int n) {
        return newDirectShortBuffer(array, n, array.length - n);
    }
    
    public static ShortBuffer newDirectShortBuffer(final short[] array) {
        return newDirectShortBuffer(array, 0);
    }
    
    public static CharBuffer newDirectCharBuffer(final int n) {
        return newDirectByteBuffer(n * 2).asCharBuffer();
    }
    
    public static CharBuffer newDirectCharBuffer(final char[] array, final int n, final int n2) {
        return (CharBuffer)newDirectCharBuffer(n2).put(array, n, n2).rewind();
    }
    
    public static CharBuffer newDirectCharBuffer(final char[] array, final int n) {
        return newDirectCharBuffer(array, n, array.length - n);
    }
    
    public static CharBuffer newDirectCharBuffer(final char[] array) {
        return newDirectCharBuffer(array, 0);
    }
    
    public static <B extends Buffer> B slice(final B b) {
        if (b instanceof ByteBuffer) {
            final ByteBuffer byteBuffer = (ByteBuffer)b;
            return (B)byteBuffer.slice().order(byteBuffer.order());
        }
        if (b instanceof IntBuffer) {
            return (B)((IntBuffer)b).slice();
        }
        if (b instanceof ShortBuffer) {
            return (B)((ShortBuffer)b).slice();
        }
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).slice();
        }
        if (b instanceof DoubleBuffer) {
            return (B)((DoubleBuffer)b).slice();
        }
        if (b instanceof LongBuffer) {
            return (B)((LongBuffer)b).slice();
        }
        if (b instanceof CharBuffer) {
            return (B)((CharBuffer)b).slice();
        }
        throw new IllegalArgumentException("unexpected buffer type: " + b.getClass());
    }
    
    public static <B extends Buffer> B slice(final B b, final int n, final int n2) {
        final int position = b.position();
        final int limit = b.limit();
        Buffer slice = null;
        try {
            b.position(n).limit(n + n2);
            slice = slice(b);
        }
        finally {
            b.position(position).limit(limit);
        }
        return (B)slice;
    }
    
    public static final FloatBuffer slice2Float(final Buffer buffer, final int n, final int n2) {
        int position;
        int limit;
        if (null != buffer) {
            position = buffer.position();
            limit = buffer.limit();
        }
        else {
            position = 0;
            limit = 0;
        }
        FloatBuffer floatBuffer;
        try {
            if (buffer instanceof ByteBuffer) {
                final ByteBuffer byteBuffer = (ByteBuffer)buffer;
                byteBuffer.position(n * 4);
                byteBuffer.limit((n + n2) * 4);
                floatBuffer = byteBuffer.slice().order(byteBuffer.order()).asFloatBuffer();
            }
            else {
                if (!(buffer instanceof FloatBuffer)) {
                    throw new InternalError("Buffer not ByteBuffer, nor FloarBuffer, nor backing array given");
                }
                final FloatBuffer floatBuffer2 = (FloatBuffer)buffer;
                floatBuffer2.position(n);
                floatBuffer2.limit(n + n2);
                floatBuffer = floatBuffer2.slice();
            }
        }
        finally {
            if (null != buffer) {
                buffer.position(position).limit(limit);
            }
        }
        floatBuffer.mark();
        return floatBuffer;
    }
    
    public static final FloatBuffer slice2Float(final float[] array, final int n, final int n2) {
        return (FloatBuffer)FloatBuffer.wrap(array, n, n2).mark();
    }
    
    public static ByteBuffer nativeOrder(final ByteBuffer byteBuffer) {
        return byteBuffer.order(ByteOrder.nativeOrder());
    }
    
    public static int sizeOfBufferElem(final Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof ByteBuffer) {
            return 1;
        }
        if (o instanceof IntBuffer) {
            return 4;
        }
        if (o instanceof ShortBuffer) {
            return 2;
        }
        if (o instanceof FloatBuffer) {
            return 4;
        }
        if (o instanceof DoubleBuffer) {
            return 8;
        }
        if (o instanceof LongBuffer) {
            return 8;
        }
        if (o instanceof CharBuffer) {
            return 2;
        }
        if (o instanceof NativeBuffer) {
            return ((NativeBuffer)o).elementSize();
        }
        throw new RuntimeException("Unexpected buffer type " + o.getClass().getName());
    }
    
    public static int remainingElem(final Object o) throws IllegalArgumentException {
        if (o == null) {
            return 0;
        }
        if (o instanceof Buffer) {
            return ((Buffer)o).remaining();
        }
        if (o instanceof NativeBuffer) {
            return ((NativeBuffer)o).remaining();
        }
        throw new IllegalArgumentException("Unsupported anonymous buffer type: " + o.getClass().getCanonicalName());
    }
    
    public static int remainingBytes(final Object o) throws IllegalArgumentException {
        if (o == null) {
            return 0;
        }
        int n;
        if (o instanceof Buffer) {
            final int remaining = ((Buffer)o).remaining();
            if (o instanceof ByteBuffer) {
                n = remaining;
            }
            else if (o instanceof FloatBuffer) {
                n = remaining * 4;
            }
            else if (o instanceof IntBuffer) {
                n = remaining * 4;
            }
            else if (o instanceof ShortBuffer) {
                n = remaining * 2;
            }
            else if (o instanceof DoubleBuffer) {
                n = remaining * 8;
            }
            else if (o instanceof LongBuffer) {
                n = remaining * 8;
            }
            else {
                if (!(o instanceof CharBuffer)) {
                    throw new InternalError("Unsupported Buffer type: " + o.getClass().getCanonicalName());
                }
                n = remaining * 2;
            }
        }
        else {
            if (!(o instanceof NativeBuffer)) {
                throw new IllegalArgumentException("Unsupported anonymous buffer type: " + o.getClass().getCanonicalName());
            }
            final NativeBuffer nativeBuffer = (NativeBuffer)o;
            n = nativeBuffer.remaining() * nativeBuffer.elementSize();
        }
        return n;
    }
    
    public static boolean isDirect(final Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof Buffer) {
            return ((Buffer)o).isDirect();
        }
        if (o instanceof PointerBuffer) {
            return ((PointerBuffer)o).isDirect();
        }
        throw new IllegalArgumentException("Unexpected buffer type " + o.getClass().getName());
    }
    
    public static int getDirectBufferByteOffset(final Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Buffer) {
            final int position = ((Buffer)o).position();
            if (o instanceof ByteBuffer) {
                return position;
            }
            if (o instanceof FloatBuffer) {
                return position * 4;
            }
            if (o instanceof IntBuffer) {
                return position * 4;
            }
            if (o instanceof ShortBuffer) {
                return position * 2;
            }
            if (o instanceof DoubleBuffer) {
                return position * 8;
            }
            if (o instanceof LongBuffer) {
                return position * 8;
            }
            if (o instanceof CharBuffer) {
                return position * 2;
            }
        }
        else if (o instanceof NativeBuffer) {
            final NativeBuffer nativeBuffer = (NativeBuffer)o;
            return nativeBuffer.position() * nativeBuffer.elementSize();
        }
        throw new IllegalArgumentException("Disallowed array backing store type in buffer " + o.getClass().getName());
    }
    
    public static Object getArray(final Object o) throws UnsupportedOperationException, IllegalArgumentException {
        if (o == null) {
            return null;
        }
        if (o instanceof Buffer) {
            return ((Buffer)o).array();
        }
        if (o instanceof NativeBuffer) {
            return ((NativeBuffer)o).array();
        }
        throw new IllegalArgumentException("Disallowed array backing store type in buffer " + o.getClass().getName());
    }
    
    public static int getIndirectBufferByteOffset(final Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Buffer) {
            final int position = ((Buffer)o).position();
            if (o instanceof ByteBuffer) {
                return ((ByteBuffer)o).arrayOffset() + position;
            }
            if (o instanceof FloatBuffer) {
                return 4 * (((FloatBuffer)o).arrayOffset() + position);
            }
            if (o instanceof IntBuffer) {
                return 4 * (((IntBuffer)o).arrayOffset() + position);
            }
            if (o instanceof ShortBuffer) {
                return 2 * (((ShortBuffer)o).arrayOffset() + position);
            }
            if (o instanceof DoubleBuffer) {
                return 8 * (((DoubleBuffer)o).arrayOffset() + position);
            }
            if (o instanceof LongBuffer) {
                return 8 * (((LongBuffer)o).arrayOffset() + position);
            }
            if (o instanceof CharBuffer) {
                return 2 * (((CharBuffer)o).arrayOffset() + position);
            }
        }
        else if (o instanceof NativeBuffer) {
            final NativeBuffer nativeBuffer = (NativeBuffer)o;
            return nativeBuffer.elementSize() * (nativeBuffer.arrayOffset() + nativeBuffer.position());
        }
        throw new IllegalArgumentException("Unknown buffer type " + o.getClass().getName());
    }
    
    public static ByteBuffer copyByteBuffer(final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        final ByteBuffer directByteBuffer = newDirectByteBuffer(byteBuffer.remaining());
        directByteBuffer.put(byteBuffer);
        directByteBuffer.rewind();
        byteBuffer.position(position);
        return directByteBuffer;
    }
    
    public static FloatBuffer copyFloatBuffer(final FloatBuffer floatBuffer) {
        return copyFloatBufferAsByteBuffer(floatBuffer).asFloatBuffer();
    }
    
    public static IntBuffer copyIntBuffer(final IntBuffer intBuffer) {
        return copyIntBufferAsByteBuffer(intBuffer).asIntBuffer();
    }
    
    public static ShortBuffer copyShortBuffer(final ShortBuffer shortBuffer) {
        return copyShortBufferAsByteBuffer(shortBuffer).asShortBuffer();
    }
    
    public static ByteBuffer copyFloatBufferAsByteBuffer(final FloatBuffer floatBuffer) {
        final int position = floatBuffer.position();
        final ByteBuffer directByteBuffer = newDirectByteBuffer(floatBuffer.remaining() * 4);
        directByteBuffer.asFloatBuffer().put(floatBuffer);
        directByteBuffer.rewind();
        floatBuffer.position(position);
        return directByteBuffer;
    }
    
    public static ByteBuffer copyIntBufferAsByteBuffer(final IntBuffer intBuffer) {
        final int position = intBuffer.position();
        final ByteBuffer directByteBuffer = newDirectByteBuffer(intBuffer.remaining() * 4);
        directByteBuffer.asIntBuffer().put(intBuffer);
        directByteBuffer.rewind();
        intBuffer.position(position);
        return directByteBuffer;
    }
    
    public static ByteBuffer copyShortBufferAsByteBuffer(final ShortBuffer shortBuffer) {
        final int position = shortBuffer.position();
        final ByteBuffer directByteBuffer = newDirectByteBuffer(shortBuffer.remaining() * 2);
        directByteBuffer.asShortBuffer().put(shortBuffer);
        directByteBuffer.rewind();
        shortBuffer.position(position);
        return directByteBuffer;
    }
    
    public static float[] getFloatArray(final double[] array, final int n, float[] array2, int n2, int n3) {
        if (0 > n3) {
            n3 = array.length - n;
        }
        if (n3 > array.length - n) {
            throw new IllegalArgumentException("payload (" + n3 + ") greater than remaining source bytes [len " + array.length + ", offset " + n + "]");
        }
        if (null == array2) {
            array2 = new float[n3];
            n2 = 0;
        }
        if (n3 > array2.length - n2) {
            throw new IllegalArgumentException("payload (" + n3 + ") greater than remaining dest bytes [len " + array2.length + ", offset " + n2 + "]");
        }
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = (float)array[n + i];
        }
        return array2;
    }
    
    public static FloatBuffer getFloatBuffer(final DoubleBuffer doubleBuffer, FloatBuffer directFloatBuffer) {
        if (null == directFloatBuffer) {
            directFloatBuffer = newDirectFloatBuffer(doubleBuffer.remaining());
        }
        if (directFloatBuffer.remaining() < doubleBuffer.remaining()) {
            throw new IllegalArgumentException("payload (" + doubleBuffer.remaining() + ") is greater than remaining dest bytes: " + directFloatBuffer.remaining());
        }
        while (doubleBuffer.hasRemaining()) {
            directFloatBuffer.put((float)doubleBuffer.get());
        }
        return directFloatBuffer;
    }
    
    public static double[] getDoubleArray(final float[] array, final int n, double[] array2, int n2, int n3) {
        if (0 > n3) {
            n3 = array.length - n;
        }
        if (n3 > array.length - n) {
            throw new IllegalArgumentException("payload (" + n3 + ") greater than remaining source bytes [len " + array.length + ", offset " + n + "]");
        }
        if (null == array2) {
            array2 = new double[n3];
            n2 = 0;
        }
        if (n3 > array2.length - n2) {
            throw new IllegalArgumentException("payload (" + n3 + ") greater than remaining dest bytes [len " + array2.length + ", offset " + n2 + "]");
        }
        for (int i = 0; i < n3; ++i) {
            array2[n2 + i] = array[n + i];
        }
        return array2;
    }
    
    public static DoubleBuffer getDoubleBuffer(final FloatBuffer floatBuffer, DoubleBuffer directDoubleBuffer) {
        if (null == directDoubleBuffer) {
            directDoubleBuffer = newDirectDoubleBuffer(floatBuffer.remaining());
        }
        if (directDoubleBuffer.remaining() < floatBuffer.remaining()) {
            throw new IllegalArgumentException("payload (" + floatBuffer.remaining() + ") is greater than remaining dest bytes: " + directDoubleBuffer.remaining());
        }
        while (floatBuffer.hasRemaining()) {
            directDoubleBuffer.put(floatBuffer.get());
        }
        return directDoubleBuffer;
    }
    
    public static <B extends Buffer> B put(final B b, final Buffer buffer) {
        if (b instanceof ByteBuffer && buffer instanceof ByteBuffer) {
            return (B)((ByteBuffer)b).put((ByteBuffer)buffer);
        }
        if (b instanceof ShortBuffer && buffer instanceof ShortBuffer) {
            return (B)((ShortBuffer)b).put((ShortBuffer)buffer);
        }
        if (b instanceof IntBuffer && buffer instanceof IntBuffer) {
            return (B)((IntBuffer)b).put((IntBuffer)buffer);
        }
        if (b instanceof FloatBuffer && buffer instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put((FloatBuffer)buffer);
        }
        if (b instanceof LongBuffer && buffer instanceof LongBuffer) {
            return (B)((LongBuffer)b).put((LongBuffer)buffer);
        }
        if (b instanceof DoubleBuffer && buffer instanceof DoubleBuffer) {
            return (B)((DoubleBuffer)b).put((DoubleBuffer)buffer);
        }
        if (b instanceof CharBuffer && buffer instanceof CharBuffer) {
            return (B)((CharBuffer)b).put((CharBuffer)buffer);
        }
        throw new IllegalArgumentException("Incompatible Buffer classes: dest = " + b.getClass().getName() + ", src = " + buffer.getClass().getName());
    }
    
    public static <B extends Buffer> B putb(final B b, final byte b2) {
        if (b instanceof ByteBuffer) {
            return (B)((ByteBuffer)b).put(b2);
        }
        if (b instanceof ShortBuffer) {
            return (B)((ShortBuffer)b).put(b2);
        }
        if (b instanceof IntBuffer) {
            return (B)((IntBuffer)b).put(b2);
        }
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put(b2);
        }
        if (b instanceof LongBuffer) {
            return (B)((LongBuffer)b).put(b2);
        }
        if (b instanceof DoubleBuffer) {
            return (B)((DoubleBuffer)b).put(b2);
        }
        if (b instanceof CharBuffer) {
            return (B)((CharBuffer)b).put((char)b2);
        }
        throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + b);
    }
    
    public static <B extends Buffer> B puts(final B b, final short n) {
        if (b instanceof ShortBuffer) {
            return (B)((ShortBuffer)b).put(n);
        }
        if (b instanceof IntBuffer) {
            return (B)((IntBuffer)b).put(n);
        }
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put(n);
        }
        if (b instanceof LongBuffer) {
            return (B)((LongBuffer)b).put(n);
        }
        if (b instanceof DoubleBuffer) {
            return (B)((DoubleBuffer)b).put(n);
        }
        throw new IllegalArgumentException("Short doesn't match Buffer Class: " + b);
    }
    
    public static <B extends Buffer> B puti(final B b, final int n) {
        if (b instanceof IntBuffer) {
            return (B)((IntBuffer)b).put(n);
        }
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put(n);
        }
        if (b instanceof LongBuffer) {
            return (B)((LongBuffer)b).put(n);
        }
        if (b instanceof DoubleBuffer) {
            return (B)((DoubleBuffer)b).put(n);
        }
        throw new IllegalArgumentException("Integer doesn't match Buffer Class: " + b);
    }
    
    public static <B extends Buffer> B putf(final B b, final float n) {
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put(n);
        }
        if (b instanceof DoubleBuffer) {
            return (B)((DoubleBuffer)b).put(n);
        }
        throw new IllegalArgumentException("Float doesn't match Buffer Class: " + b);
    }
    
    public static <B extends Buffer> B putd(final B b, final double n) {
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put((float)n);
        }
        throw new IllegalArgumentException("Double doesn't match Buffer Class: " + b);
    }
    
    public static <B extends Buffer> B putNb(final B b, final boolean b2, final byte b3, final boolean b4) {
        if (b instanceof ByteBuffer) {
            return (B)((ByteBuffer)b).put(b3);
        }
        if (b instanceof ShortBuffer) {
            return (B)((ShortBuffer)b).put(ValueConv.byte_to_short(b3, b4, b2));
        }
        if (b instanceof IntBuffer) {
            return (B)((IntBuffer)b).put(ValueConv.byte_to_int(b3, b4, b2));
        }
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put(ValueConv.byte_to_float(b3, b4));
        }
        throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + b);
    }
    
    public static <B extends Buffer> B putNs(final B b, final boolean b2, final short n, final boolean b3) {
        if (b instanceof ByteBuffer) {
            return (B)((ByteBuffer)b).put(ValueConv.short_to_byte(n, b3, b2));
        }
        if (b instanceof ShortBuffer) {
            return (B)((ShortBuffer)b).put(n);
        }
        if (b instanceof IntBuffer) {
            return (B)((IntBuffer)b).put(ValueConv.short_to_int(n, b3, b2));
        }
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put(ValueConv.short_to_float(n, b3));
        }
        throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + b);
    }
    
    public static <B extends Buffer> B putNi(final B b, final boolean b2, final int n, final boolean b3) {
        if (b instanceof ByteBuffer) {
            return (B)((ByteBuffer)b).put(ValueConv.int_to_byte(n, b3, b2));
        }
        if (b instanceof ShortBuffer) {
            return (B)((ShortBuffer)b).put(ValueConv.int_to_short(n, b3, b2));
        }
        if (b instanceof IntBuffer) {
            return (B)((IntBuffer)b).put(n);
        }
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put(ValueConv.int_to_float(n, b3));
        }
        throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + b);
    }
    
    public static <B extends Buffer> B putNf(final B b, final boolean b2, final float n) {
        if (b instanceof ByteBuffer) {
            return (B)((ByteBuffer)b).put(ValueConv.float_to_byte(n, b2));
        }
        if (b instanceof ShortBuffer) {
            return (B)((ShortBuffer)b).put(ValueConv.float_to_short(n, b2));
        }
        if (b instanceof IntBuffer) {
            return (B)((IntBuffer)b).put(ValueConv.float_to_int(n, b2));
        }
        if (b instanceof FloatBuffer) {
            return (B)((FloatBuffer)b).put(n);
        }
        throw new IllegalArgumentException("Byte doesn't match Buffer Class: " + b);
    }
    
    public static void rangeCheck(final byte[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (array.length < n + n2) {
            throw new ArrayIndexOutOfBoundsException("Required " + n2 + " elements in array, only had " + (array.length - n));
        }
    }
    
    public static void rangeCheck(final char[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (array.length < n + n2) {
            throw new ArrayIndexOutOfBoundsException("Required " + n2 + " elements in array, only had " + (array.length - n));
        }
    }
    
    public static void rangeCheck(final short[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (array.length < n + n2) {
            throw new ArrayIndexOutOfBoundsException("Required " + n2 + " elements in array, only had " + (array.length - n));
        }
    }
    
    public static void rangeCheck(final int[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (array.length < n + n2) {
            throw new ArrayIndexOutOfBoundsException("Required " + n2 + " elements in array, only had " + (array.length - n));
        }
    }
    
    public static void rangeCheck(final long[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (array.length < n + n2) {
            throw new ArrayIndexOutOfBoundsException("Required " + n2 + " elements in array, only had " + (array.length - n));
        }
    }
    
    public static void rangeCheck(final float[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (array.length < n + n2) {
            throw new ArrayIndexOutOfBoundsException("Required " + n2 + " elements in array, only had " + (array.length - n));
        }
    }
    
    public static void rangeCheck(final double[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (array.length < n + n2) {
            throw new ArrayIndexOutOfBoundsException("Required " + n2 + " elements in array, only had " + (array.length - n));
        }
    }
    
    public static void rangeCheck(final Buffer buffer, final int n) {
        if (buffer == null) {
            return;
        }
        if (buffer.remaining() < n) {
            throw new IndexOutOfBoundsException("Required " + n + " remaining elements in buffer, only had " + buffer.remaining());
        }
    }
    
    public static void rangeCheckBytes(final Object o, final int n) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (o == null) {
            return;
        }
        final int remainingBytes = remainingBytes(o);
        if (remainingBytes < n) {
            throw new IndexOutOfBoundsException("Required " + n + " remaining bytes in buffer, only had " + remainingBytes);
        }
    }
    
    public static StringBuilder toString(StringBuilder sb, final String s, final Buffer buffer) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append(buffer.getClass().getSimpleName());
        sb.append("[pos ").append(buffer.position()).append(", lim ").append(buffer.limit()).append(", cap ").append(buffer.capacity());
        sb.append(", remaining ").append(buffer.remaining());
        sb.append("; array ").append(buffer.hasArray()).append(", direct ").append(buffer.isDirect());
        sb.append(", r/w ").append(!buffer.isReadOnly()).append(": ");
        if (buffer instanceof ByteBuffer) {
            final ByteBuffer byteBuffer = (ByteBuffer)buffer;
            for (int i = 0; i < byteBuffer.limit(); ++i) {
                if (0 < i) {
                    sb.append(", ");
                }
                if (null == s) {
                    sb.append(byteBuffer.get(i));
                }
                else {
                    sb.append(String.format(s, byteBuffer.get(i)));
                }
            }
        }
        else if (buffer instanceof FloatBuffer) {
            final FloatBuffer floatBuffer = (FloatBuffer)buffer;
            for (int j = 0; j < floatBuffer.limit(); ++j) {
                if (0 < j) {
                    sb.append(", ");
                }
                if (null == s) {
                    sb.append(floatBuffer.get(j));
                }
                else {
                    sb.append(String.format(s, floatBuffer.get(j)));
                }
            }
        }
        else if (buffer instanceof IntBuffer) {
            final IntBuffer intBuffer = (IntBuffer)buffer;
            for (int k = 0; k < intBuffer.limit(); ++k) {
                if (0 < k) {
                    sb.append(", ");
                }
                if (null == s) {
                    sb.append(intBuffer.get(k));
                }
                else {
                    sb.append(String.format(s, intBuffer.get(k)));
                }
            }
        }
        else if (buffer instanceof ShortBuffer) {
            final ShortBuffer shortBuffer = (ShortBuffer)buffer;
            for (int l = 0; l < shortBuffer.limit(); ++l) {
                if (0 < l) {
                    sb.append(", ");
                }
                if (null == s) {
                    sb.append(shortBuffer.get(l));
                }
                else {
                    sb.append(String.format(s, shortBuffer.get(l)));
                }
            }
        }
        else if (buffer instanceof DoubleBuffer) {
            final DoubleBuffer doubleBuffer = (DoubleBuffer)buffer;
            for (int n = 0; n < doubleBuffer.limit(); ++n) {
                if (0 < n) {
                    sb.append(", ");
                }
                if (null == s) {
                    sb.append(doubleBuffer.get(n));
                }
                else {
                    sb.append(String.format(s, doubleBuffer.get(n)));
                }
            }
        }
        else if (buffer instanceof LongBuffer) {
            final LongBuffer longBuffer = (LongBuffer)buffer;
            for (int n2 = 0; n2 < longBuffer.limit(); ++n2) {
                if (0 < n2) {
                    sb.append(", ");
                }
                if (null == s) {
                    sb.append(longBuffer.get(n2));
                }
                else {
                    sb.append(String.format(s, longBuffer.get(n2)));
                }
            }
        }
        else if (buffer instanceof CharBuffer) {
            final CharBuffer charBuffer = (CharBuffer)buffer;
            for (int n3 = 0; n3 < charBuffer.limit(); ++n3) {
                if (0 < n3) {
                    sb.append(", ");
                }
                if (null == s) {
                    sb.append(charBuffer.get(n3));
                }
                else {
                    sb.append(String.format(s, charBuffer.get(n3)));
                }
            }
        }
        sb.append("]");
        return sb;
    }
}
