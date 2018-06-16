// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.util;

import java.nio.ByteBuffer;

public interface PixelRectangle
{
    int hashCode();
    
    PixelFormat getPixelformat();
    
    DimensionImmutable getSize();
    
    int getStride();
    
    boolean isGLOriented();
    
    ByteBuffer getPixels();
    
    String toString();
    
    public static class GenericPixelRect implements PixelRectangle
    {
        protected final PixelFormat pixelformat;
        protected final DimensionImmutable size;
        protected final int strideInBytes;
        protected final boolean isGLOriented;
        protected final ByteBuffer pixels;
        private int hashCode;
        private volatile boolean hashCodeComputed;
        
        public GenericPixelRect(final PixelFormat pixelformat, final DimensionImmutable size, int strideInBytes, final boolean isGLOriented, final ByteBuffer pixels) throws IllegalArgumentException, IndexOutOfBoundsException {
            this.hashCode = 0;
            this.hashCodeComputed = false;
            if (0 != strideInBytes) {
                if (strideInBytes < pixelformat.comp.bytesPerPixel() * size.getWidth()) {
                    throw new IllegalArgumentException("Invalid stride " + strideInBytes + ", must be greater than bytesPerPixel " + pixelformat.comp.bytesPerPixel() + " * width " + size.getWidth());
                }
            }
            else {
                strideInBytes = pixelformat.comp.bytesPerPixel() * size.getWidth();
            }
            final int n = strideInBytes * size.getHeight();
            if (pixels.limit() < n) {
                throw new IndexOutOfBoundsException("Dest buffer has insufficient bytes left, needs " + n + ": " + pixels);
            }
            this.pixelformat = pixelformat;
            this.size = size;
            this.strideInBytes = strideInBytes;
            this.isGLOriented = isGLOriented;
            this.pixels = pixels;
        }
        
        public GenericPixelRect(final PixelRectangle pixelRectangle) throws IllegalArgumentException, IndexOutOfBoundsException {
            this(pixelRectangle.getPixelformat(), pixelRectangle.getSize(), pixelRectangle.getStride(), pixelRectangle.isGLOriented(), pixelRectangle.getPixels());
        }
        
        @Override
        public int hashCode() {
            if (!this.hashCodeComputed) {
                synchronized (this) {
                    if (!this.hashCodeComputed) {
                        final int hashCode = this.pixelformat.comp.hashCode();
                        final int n = (hashCode << 5) - hashCode + this.size.hashCode();
                        final int n2 = (n << 5) - n + this.strideInBytes;
                        final int n3 = (n2 << 5) - n2 + (this.isGLOriented ? 1 : 0);
                        this.hashCode = (n3 << 5) - n3 + this.pixels.hashCode();
                        this.hashCodeComputed = true;
                    }
                }
            }
            return this.hashCode;
        }
        
        @Override
        public PixelFormat getPixelformat() {
            return this.pixelformat;
        }
        
        @Override
        public DimensionImmutable getSize() {
            return this.size;
        }
        
        @Override
        public int getStride() {
            return this.strideInBytes;
        }
        
        @Override
        public boolean isGLOriented() {
            return this.isGLOriented;
        }
        
        @Override
        public ByteBuffer getPixels() {
            return this.pixels;
        }
        
        @Override
        public final String toString() {
            return "PixelRect[obj 0x" + Integer.toHexString(super.hashCode()) + ", " + this.pixelformat + ", " + this.size + ", stride " + this.strideInBytes + ", isGLOrient " + this.isGLOriented + ", pixels " + this.pixels + "]";
        }
    }
}
