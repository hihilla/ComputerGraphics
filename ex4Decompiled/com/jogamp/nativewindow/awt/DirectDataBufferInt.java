// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.awt;

import java.awt.image.BufferedImage;
import com.jogamp.common.nio.Buffers;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.DirectColorModel;
import java.awt.color.ColorSpace;
import java.util.Hashtable;
import java.awt.Point;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.awt.image.DataBuffer;

public final class DirectDataBufferInt extends DataBuffer
{
    private final ByteBuffer dataBytes;
    private final IntBuffer dataInts;
    private final ByteBuffer[] bankdataBytes;
    private final IntBuffer[] bankdataInts;
    
    public static BufferedImageInt createBufferedImage(final int n, final int n2, final int n3, Point point, final Hashtable<?, ?> hashtable) {
        final ColorSpace instance = ColorSpace.getInstance(1000);
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        boolean b = false;
        switch (n3) {
            case 2: {
                n4 = 32;
                n5 = 16711680;
                n6 = 65280;
                n7 = 255;
                n8 = -16777216;
                b = false;
                break;
            }
            case 3: {
                n4 = 32;
                n5 = 16711680;
                n6 = 65280;
                n7 = 255;
                n8 = -16777216;
                b = true;
                break;
            }
            case 1: {
                n4 = 24;
                n5 = 16711680;
                n6 = 65280;
                n7 = 255;
                n8 = 0;
                b = false;
                break;
            }
            case 4: {
                n4 = 24;
                n5 = 255;
                n6 = 65280;
                n7 = 16711680;
                n8 = 0;
                b = false;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported imageType, must be [INT_ARGB, INT_ARGB_PRE, INT_RGB, INT_BGR], has " + n3);
            }
        }
        final DirectColorModel directColorModel = new DirectColorModel(instance, n4, n5, n6, n7, n8, b, 3);
        int[] array;
        if (n8 != 0) {
            array = new int[] { 0, 0, 0, n8 };
        }
        else {
            array = new int[3];
        }
        array[0] = n5;
        array[1] = n6;
        array[2] = n7;
        final DirectDataBufferInt directDataBufferInt = new DirectDataBufferInt(n * n2);
        if (null == point) {
            point = new Point(0, 0);
        }
        return new BufferedImageInt(n3, directColorModel, directDataBufferInt, new DirectWritableRaster(new SinglePixelPackedSampleModel(directDataBufferInt.getDataType(), n, n2, n, array), directDataBufferInt, point), hashtable);
    }
    
    public DirectDataBufferInt(final int n) {
        super(3, n);
        this.dataBytes = Buffers.newDirectByteBuffer(n * 4);
        this.dataInts = this.dataBytes.asIntBuffer();
        this.bankdataBytes = new ByteBuffer[1];
        this.bankdataInts = new IntBuffer[1];
        this.bankdataBytes[0] = this.dataBytes;
        this.bankdataInts[0] = this.dataInts;
    }
    
    public DirectDataBufferInt(final int n, final int n2) {
        super(3, n, n2);
        this.bankdataBytes = new ByteBuffer[n2];
        this.bankdataInts = new IntBuffer[n2];
        for (int i = 0; i < n2; ++i) {
            this.bankdataBytes[i] = Buffers.newDirectByteBuffer(n * 4);
            this.bankdataInts[i] = this.bankdataBytes[i].asIntBuffer();
        }
        this.dataBytes = this.bankdataBytes[0];
        this.dataInts = this.bankdataInts[0];
    }
    
    public DirectDataBufferInt(final ByteBuffer byteBuffer, final int n) {
        super(3, n);
        this.dataBytes = Buffers.nativeOrder(byteBuffer);
        this.dataInts = this.dataBytes.asIntBuffer();
        this.bankdataBytes = new ByteBuffer[1];
        this.bankdataInts = new IntBuffer[1];
        this.bankdataBytes[0] = this.dataBytes;
        this.bankdataInts[0] = this.dataInts;
    }
    
    public IntBuffer getData() {
        return this.dataInts;
    }
    
    public ByteBuffer getDataBytes() {
        return this.dataBytes;
    }
    
    public IntBuffer getData(final int n) {
        return this.bankdataInts[n];
    }
    
    public ByteBuffer getDataBytes(final int n) {
        return this.bankdataBytes[n];
    }
    
    @Override
    public int getElem(final int n) {
        return this.dataInts.get(n + this.offset);
    }
    
    @Override
    public int getElem(final int n, final int n2) {
        return this.bankdataInts[n].get(n2 + this.offsets[n]);
    }
    
    @Override
    public void setElem(final int n, final int n2) {
        this.dataInts.put(n + this.offset, n2);
    }
    
    @Override
    public void setElem(final int n, final int n2, final int n3) {
        this.bankdataInts[n].put(n2 + this.offsets[n], n3);
    }
    
    public static class DirectWritableRaster extends WritableRaster
    {
        protected DirectWritableRaster(final SampleModel sampleModel, final DirectDataBufferInt directDataBufferInt, final Point point) {
            super(sampleModel, directDataBufferInt, point);
        }
    }
    
    public static class BufferedImageInt extends BufferedImage
    {
        final int customImageType;
        final DirectDataBufferInt dataBuffer;
        
        public BufferedImageInt(final int customImageType, final ColorModel colorModel, final DirectDataBufferInt dataBuffer, final WritableRaster writableRaster, final Hashtable<?, ?> hashtable) {
            super(colorModel, writableRaster, false, hashtable);
            this.customImageType = customImageType;
            this.dataBuffer = dataBuffer;
        }
        
        public int getCustomType() {
            return this.customImageType;
        }
        
        public DirectDataBufferInt getDataBuffer() {
            return this.dataBuffer;
        }
        
        @Override
        public String toString() {
            return "BufferedImageInt@" + Integer.toHexString(this.hashCode()) + ": custom/internal type = " + this.customImageType + "/" + this.getType() + " " + this.getColorModel() + " " + this.getRaster();
        }
    }
}
