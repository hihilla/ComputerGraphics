// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import com.jogamp.common.nio.Buffers;
import jogamp.opengl.Debug;
import jogamp.opengl.util.jpeg.JPEGDecoder;
import java.io.IOException;
import com.jogamp.opengl.util.texture.TextureData;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class JPEGImage
{
    private static final boolean DEBUG;
    private final JPEGColorSink pixelStorage;
    private final int pixelWidth;
    private final int pixelHeight;
    private final int glFormat;
    private final int bytesPerPixel;
    private final boolean reversedChannels;
    private final ByteBuffer data;
    
    public static JPEGImage read(final InputStream inputStream, final TextureData.ColorSpace colorSpace) throws IOException {
        return new JPEGImage(inputStream, colorSpace);
    }
    
    public static JPEGImage read(final InputStream inputStream) throws IOException {
        return new JPEGImage(inputStream, TextureData.ColorSpace.RGB);
    }
    
    private JPEGImage(final InputStream inputStream, final TextureData.ColorSpace colorSpace) throws IOException {
        this.pixelStorage = new JPEGColorSink(colorSpace);
        final JPEGDecoder jpegDecoder = new JPEGDecoder();
        jpegDecoder.parse(inputStream);
        this.pixelWidth = jpegDecoder.getWidth();
        this.pixelHeight = jpegDecoder.getHeight();
        jpegDecoder.getPixel(this.pixelStorage, this.pixelWidth, this.pixelHeight);
        this.data = this.pixelStorage.data;
        this.bytesPerPixel = 3;
        this.glFormat = 6407;
        this.reversedChannels = false;
        if (JPEGImage.DEBUG) {
            System.err.println("JPEGImage: alpha false, bytesPerPixel " + this.bytesPerPixel + ", pixels " + this.pixelWidth + "x" + this.pixelHeight + ", glFormat 0x" + Integer.toHexString(this.glFormat));
            System.err.println("JPEGImage: " + jpegDecoder);
            System.err.println("JPEGImage: " + this.pixelStorage);
        }
        jpegDecoder.clear(null);
    }
    
    public TextureData.ColorSpace getColorSpace() {
        return this.pixelStorage.storageCS;
    }
    
    public int getComponentCount() {
        return this.pixelStorage.storageComponents;
    }
    
    public int getWidth() {
        return this.pixelWidth;
    }
    
    public int getHeight() {
        return this.pixelHeight;
    }
    
    public boolean getHasReversedChannels() {
        return this.reversedChannels;
    }
    
    public int getGLFormat() {
        return this.glFormat;
    }
    
    public int getGLType() {
        return 5121;
    }
    
    public int getBytesPerPixel() {
        return this.bytesPerPixel;
    }
    
    public ByteBuffer getData() {
        return this.data;
    }
    
    @Override
    public String toString() {
        return "JPEGImage[" + this.pixelWidth + "x" + this.pixelHeight + ", bytesPerPixel " + this.bytesPerPixel + ", reversedChannels " + this.reversedChannels + ", " + this.pixelStorage + ", " + this.data + "]";
    }
    
    static {
        DEBUG = Debug.debug("JPEGImage");
    }
    
    private static class JPEGColorSink implements JPEGDecoder.ColorSink
    {
        int width;
        int height;
        int sourceComponents;
        TextureData.ColorSpace sourceCS;
        int storageComponents;
        final TextureData.ColorSpace storageCS;
        ByteBuffer data;
        
        JPEGColorSink(final TextureData.ColorSpace storageCS) {
            this.width = 0;
            this.height = 0;
            this.sourceComponents = 0;
            this.sourceCS = TextureData.ColorSpace.YCbCr;
            this.data = null;
            this.storageCS = storageCS;
            switch (this.storageCS) {
                case RGB:
                case YCbCr: {
                    this.storageComponents = 3;
                }
                default: {
                    throw new IllegalArgumentException("Unsupported storage color-space: " + this.storageCS);
                }
            }
        }
        
        @Override
        public final TextureData.ColorSpace allocate(final int width, final int height, final TextureData.ColorSpace sourceCS, final int sourceComponents) throws RuntimeException {
            this.width = width;
            this.height = height;
            this.sourceComponents = sourceComponents;
            this.sourceCS = sourceCS;
            this.data = Buffers.newDirectByteBuffer(width * height * this.storageComponents);
            return this.storageCS;
        }
        
        @Override
        public final void storeRGB(final int n, final int n2, final byte b, final byte b2, final byte b3) {
            int n3 = ((this.height - n2 - 1) * this.width + n) * this.storageComponents;
            this.data.put(n3++, b);
            this.data.put(n3++, b2);
            this.data.put(n3++, b3);
        }
        
        @Override
        public final void store2(final int n, final int n2, final byte b, final byte b2) {
            throw new RuntimeException("not supported yet");
        }
        
        @Override
        public final void storeYCbCr(final int n, final int n2, final byte b, final byte b2, final byte b3) {
            int n3 = ((this.height - n2 - 1) * this.width + n) * this.storageComponents;
            this.data.put(n3++, b);
            this.data.put(n3++, b2);
            this.data.put(n3++, b3);
        }
        
        @Override
        public String toString() {
            return "JPEGPixels[" + this.width + "x" + this.height + ", sourceComp " + this.sourceComponents + ", sourceCS " + this.sourceCS + ", storageCS " + this.storageCS + ", storageComp " + this.storageComponents + "]";
        }
    }
}
