// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.nio;

import java.nio.ByteOrder;
import java.nio.ByteBuffer;

public class StructAccessor
{
    private final ByteBuffer bb;
    
    public StructAccessor(final ByteBuffer byteBuffer) {
        this.bb = byteBuffer.order(ByteOrder.nativeOrder());
    }
    
    public final ByteBuffer getBuffer() {
        return this.bb;
    }
    
    public final ByteBuffer slice(final int n, final int n2) {
        this.bb.position(n);
        this.bb.limit(n + n2);
        final ByteBuffer order = this.bb.slice().order(this.bb.order());
        this.bb.position(0);
        this.bb.limit(this.bb.capacity());
        return order;
    }
    
    public final byte getByteAt(final int n) {
        return this.bb.get(n);
    }
    
    public final void setByteAt(final int n, final byte b) {
        this.bb.put(n, b);
    }
    
    public final boolean getBooleanAt(final int n) {
        return 0 != this.bb.get(n);
    }
    
    public final void setBooleanAt(final int n, final boolean b) {
        this.bb.put(n, (byte)(b ? 1 : 0));
    }
    
    public final char getCharAt(final int n) {
        return this.bb.getChar(n);
    }
    
    public final void setCharAt(final int n, final char c) {
        this.bb.putChar(n, c);
    }
    
    public final short getShortAt(final int n) {
        return this.bb.getShort(n);
    }
    
    public final void setShortAt(final int n, final short n2) {
        this.bb.putShort(n, n2);
    }
    
    public final int getIntAt(final int n) {
        return this.bb.getInt(n);
    }
    
    public final void setIntAt(final int n, final int n2) {
        this.bb.putInt(n, n2);
    }
    
    public final int getIntAt(final int n, final int n2) {
        switch (n2) {
            case 2: {
                return this.bb.getShort(n) & 0xFFFF;
            }
            case 4: {
                return this.bb.getInt(n);
            }
            case 8: {
                return (int)(this.bb.getLong(n) & 0xFFFFFFFFL);
            }
            default: {
                throw new InternalError("invalid nativeSizeInBytes " + n2);
            }
        }
    }
    
    public final void setIntAt(final int n, final int n2, final int n3) {
        switch (n3) {
            case 2: {
                this.bb.putShort(n, (short)(n2 & 0xFFFF));
                break;
            }
            case 4: {
                this.bb.putInt(n, n2);
                break;
            }
            case 8: {
                this.bb.putLong(n, n2 & 0xFFFFFFFFL);
                break;
            }
            default: {
                throw new InternalError("invalid nativeSizeInBytes " + n3);
            }
        }
    }
    
    public final float getFloatAt(final int n) {
        return this.bb.getFloat(n);
    }
    
    public final void setFloatAt(final int n, final float n2) {
        this.bb.putFloat(n, n2);
    }
    
    public final double getDoubleAt(final int n) {
        return this.bb.getDouble(n);
    }
    
    public final void setDoubleAt(final int n, final double n2) {
        this.bb.putDouble(n, n2);
    }
    
    public final long getLongAt(final int n) {
        return this.bb.getLong(n);
    }
    
    public final void setLongAt(final int n, final long n2) {
        this.bb.putLong(n, n2);
    }
    
    public final long getLongAt(final int n, final int n2) {
        switch (n2) {
            case 4: {
                return this.bb.getInt(n) & 0xFFFFFFFFL;
            }
            case 8: {
                return this.bb.getLong(n);
            }
            default: {
                throw new InternalError("invalid nativeSizeInBytes " + n2);
            }
        }
    }
    
    public final void setLongAt(final int n, final long n2, final int n3) {
        switch (n3) {
            case 4: {
                this.bb.putInt(n, (int)(n2 & 0xFFFFFFFFL));
                break;
            }
            case 8: {
                this.bb.putLong(n, n2);
                break;
            }
            default: {
                throw new InternalError("invalid nativeSizeInBytes " + n3);
            }
        }
    }
    
    public final void setBytesAt(int n, final byte[] array) {
        for (int i = 0; i < array.length; ++i) {
            this.bb.put(n++, array[i]);
        }
    }
    
    public final byte[] getBytesAt(int n, final byte[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.bb.get(n++);
        }
        return array;
    }
    
    public final void setBooleansAt(int n, final boolean[] array) {
        for (int i = 0; i < array.length; ++i) {
            this.bb.put(n++, (byte)(array[i] ? 1 : 0));
        }
    }
    
    public final boolean[] getBooleansAt(int n, final boolean[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = (0 != this.bb.get(n++));
        }
        return array;
    }
    
    public final void setCharsAt(int n, final char[] array) {
        for (int i = 0; i < array.length; ++i, n += 2) {
            this.bb.putChar(n, array[i]);
        }
    }
    
    public final char[] getCharsAt(int n, final char[] array) {
        for (int i = 0; i < array.length; ++i, n += 2) {
            array[i] = this.bb.getChar(n);
        }
        return array;
    }
    
    public final void setShortsAt(int n, final short[] array) {
        for (int i = 0; i < array.length; ++i, n += 2) {
            this.bb.putShort(n, array[i]);
        }
    }
    
    public final short[] getShortsAt(int n, final short[] array) {
        for (int i = 0; i < array.length; ++i, n += 2) {
            array[i] = this.bb.getShort(n);
        }
        return array;
    }
    
    public final void setIntsAt(int n, final int[] array) {
        for (int i = 0; i < array.length; ++i, n += 4) {
            this.bb.putInt(n, array[i]);
        }
    }
    
    public final int[] getIntsAt(int n, final int[] array) {
        for (int i = 0; i < array.length; ++i, n += 4) {
            array[i] = this.bb.getInt(n);
        }
        return array;
    }
    
    public final void setFloatsAt(int n, final float[] array) {
        for (int i = 0; i < array.length; ++i, n += 4) {
            this.bb.putFloat(n, array[i]);
        }
    }
    
    public final float[] getFloatsAt(int n, final float[] array) {
        for (int i = 0; i < array.length; ++i, n += 4) {
            array[i] = this.bb.getFloat(n);
        }
        return array;
    }
    
    public final void setDoublesAt(int n, final double[] array) {
        for (int i = 0; i < array.length; ++i, n += 8) {
            this.bb.putDouble(n, array[i]);
        }
    }
    
    public final double[] getDoublesAt(int n, final double[] array) {
        for (int i = 0; i < array.length; ++i, n += 8) {
            array[i] = this.bb.getDouble(n);
        }
        return array;
    }
    
    public final void setLongsAt(int n, final long[] array) {
        for (int i = 0; i < array.length; ++i, n += 8) {
            this.bb.putLong(n, array[i]);
        }
    }
    
    public final long[] getLongsAt(int n, final long[] array) {
        for (int i = 0; i < array.length; ++i, n += 8) {
            array[i] = this.bb.getLong(n);
        }
        return array;
    }
}
