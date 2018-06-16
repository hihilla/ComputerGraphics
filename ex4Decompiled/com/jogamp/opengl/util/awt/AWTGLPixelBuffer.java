// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.awt;

import java.util.Iterator;
import com.jogamp.common.util.IntObjectHashMap;
import java.nio.IntBuffer;
import java.awt.image.DataBufferInt;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GL;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.awt.Point;
import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.nio.Buffer;
import com.jogamp.nativewindow.util.PixelFormat;
import java.awt.image.BufferedImage;
import com.jogamp.opengl.util.GLPixelBuffer;

public class AWTGLPixelBuffer extends GLPixelBuffer
{
    private static final GLPixelAttributes awtPixelAttributesIntBGRA;
    private static final GLPixelAttributes awtPixelAttributesIntRGBA;
    public final BufferedImage image;
    private final PixelFormat.Composition hostPixelComp;
    private final int awtFormat;
    
    public AWTGLPixelBuffer(final PixelFormat.Composition hostPixelComp, final GLPixelAttributes glPixelAttributes, final boolean b, final int awtFormat, final int n, final int n2, final int n3, final BufferedImage image, final Buffer buffer, final boolean b2) {
        super(glPixelAttributes, b, n, n2, n3, buffer, b2);
        this.image = image;
        this.hostPixelComp = hostPixelComp;
        this.awtFormat = awtFormat;
    }
    
    public final PixelFormat.Composition getHostPixelComp() {
        return this.hostPixelComp;
    }
    
    public final int getAWTFormat() {
        return this.awtFormat;
    }
    
    @Override
    public void dispose() {
        this.image.flush();
        super.dispose();
    }
    
    public BufferedImage getAlignedImage(final int n, final int n2) throws IllegalArgumentException {
        if (n * n2 > this.image.getWidth() * this.image.getHeight()) {
            throw new IllegalArgumentException("Requested size exceeds image size: " + n + "x" + n2 + " > " + this.image.getWidth() + "x" + this.image.getHeight());
        }
        if (n == this.image.getWidth() && n2 == this.image.getHeight()) {
            return this.image;
        }
        final ColorModel colorModel = this.image.getColorModel();
        final WritableRaster raster = this.image.getRaster();
        final DataBuffer dataBuffer = raster.getDataBuffer();
        return new BufferedImage(colorModel, Raster.createWritableRaster(new SinglePixelPackedSampleModel(dataBuffer.getDataType(), n, n2, n, ((SinglePixelPackedSampleModel)raster.getSampleModel()).getBitMasks()), dataBuffer, null), colorModel.isAlphaPremultiplied(), null);
    }
    
    public final boolean isDataBufferSource(final BufferedImage bufferedImage) {
        return bufferedImage.getRaster().getDataBuffer() == this.image.getRaster().getDataBuffer();
    }
    
    @Override
    public StringBuilder toString(StringBuilder string) {
        string = super.toString(string);
        string.append(", allowRowStride ").append(this.allowRowStride).append(", image [").append(this.image.getWidth()).append("x").append(this.image.getHeight()).append(", ").append(this.image.toString()).append("]");
        return string;
    }
    
    @Override
    public String toString() {
        return "AWTGLPixelBuffer[" + this.toString(null).toString() + "]";
    }
    
    static {
        awtPixelAttributesIntBGRA = new GLPixelAttributes(32993, 5121);
        awtPixelAttributesIntRGBA = new GLPixelAttributes(6408, 5121);
    }
    
    public static class AWTGLPixelBufferProvider implements GLPixelBufferProvider
    {
        private final boolean allowRowStride;
        
        public AWTGLPixelBufferProvider(final boolean allowRowStride) {
            this.allowRowStride = allowRowStride;
        }
        
        @Override
        public boolean getAllowRowStride() {
            return this.allowRowStride;
        }
        
        @Override
        public GLPixelAttributes getAttributes(final GL gl, final int n, final boolean b) {
            return gl.isGLES() ? AWTGLPixelBuffer.awtPixelAttributesIntRGBA : AWTGLPixelBuffer.awtPixelAttributesIntBGRA;
        }
        
        public GLPixelAttributes getAttributes(final GLProfile glProfile, final int n) {
            return glProfile.isGLES() ? AWTGLPixelBuffer.awtPixelAttributesIntRGBA : AWTGLPixelBuffer.awtPixelAttributesIntBGRA;
        }
        
        @Override
        public PixelFormat.Composition getHostPixelComp(final GLProfile glProfile, final int n) {
            return this.getAWTPixelFormat(glProfile, n).comp;
        }
        
        public int getAWTFormat(final GLProfile glProfile, final int n) {
            if (4 == n) {
                return glProfile.isGLES() ? 4 : 2;
            }
            return glProfile.isGLES() ? 4 : 1;
        }
        
        public PixelFormat getAWTPixelFormat(final GLProfile glProfile, final int n) {
            if (4 == n) {
                return glProfile.isGLES() ? PixelFormat.RGBx8888 : PixelFormat.BGRA8888;
            }
            return glProfile.isGLES() ? PixelFormat.RGBx8888 : PixelFormat.BGRx8888;
        }
        
        @Override
        public AWTGLPixelBuffer allocate(final GL gl, final PixelFormat.Composition composition, final GLPixelAttributes glPixelAttributes, final boolean b, final int n, final int n2, final int n3, final int n4) {
            if (null == composition) {
                throw new IllegalArgumentException("Null hostPixComp");
            }
            final int awtFormat = this.getAWTFormat(gl.getGLProfile(), composition.componentCount());
            final BufferedImage bufferedImage = new BufferedImage(n, n2, awtFormat);
            return new AWTGLPixelBuffer(composition, glPixelAttributes, b, awtFormat, n, n2, n3, bufferedImage, IntBuffer.wrap(((DataBufferInt)bufferedImage.getRaster().getDataBuffer()).getData()), this.allowRowStride);
        }
    }
    
    public static class SingleAWTGLPixelBufferProvider extends AWTGLPixelBufferProvider implements SingletonGLPixelBufferProvider
    {
        private final IntObjectHashMap bufferMap;
        
        private static int getHashCode(final PixelFormat.Composition composition, final GLPixelAttributes glPixelAttributes, final boolean b) {
            final int hashCode = composition.hashCode();
            return (hashCode << 5) - hashCode + glPixelAttributes.hashCode();
        }
        
        public SingleAWTGLPixelBufferProvider(final boolean b) {
            super(b);
            this.bufferMap = new IntObjectHashMap(8);
        }
        
        @Override
        public AWTGLPixelBuffer allocate(final GL gl, PixelFormat.Composition comp, final GLPixelAttributes glPixelAttributes, final boolean b, final int n, final int n2, final int n3, final int n4) {
            if (null == comp) {
                comp = glPixelAttributes.pfmt.comp;
            }
            final int hashCode = getHashCode(comp, glPixelAttributes, b);
            AWTGLPixelBuffer allocateImpl = (AWTGLPixelBuffer)this.bufferMap.get(hashCode);
            if (null == allocateImpl || allocateImpl.requiresNewBuffer(gl, n, n2, n4)) {
                if (null != allocateImpl) {
                    allocateImpl.dispose();
                }
                allocateImpl = this.allocateImpl(comp, glPixelAttributes, b, this.getAWTFormat(gl.getGLProfile(), comp.componentCount()), n, n2, n3, n4);
                this.bufferMap.put(hashCode, allocateImpl);
            }
            return allocateImpl;
        }
        
        private AWTGLPixelBuffer allocateImpl(final PixelFormat.Composition composition, final GLPixelAttributes glPixelAttributes, final boolean b, final int n, final int n2, final int n3, final int n4, final int n5) {
            final BufferedImage bufferedImage = new BufferedImage(n2, n3, n);
            return new AWTGLPixelBuffer(composition, glPixelAttributes, b, n, n2, n3, n4, bufferedImage, IntBuffer.wrap(((DataBufferInt)bufferedImage.getRaster().getDataBuffer()).getData()), this.getAllowRowStride());
        }
        
        @Override
        public AWTGLPixelBuffer getSingleBuffer(final PixelFormat.Composition composition, final GLPixelAttributes glPixelAttributes, final boolean b) {
            return (AWTGLPixelBuffer)this.bufferMap.get(getHashCode(composition, glPixelAttributes, b));
        }
        
        @Override
        public AWTGLPixelBuffer initSingleton(final GLProfile glProfile, final int n, final boolean b, final int n2, final int n3, final int n4) {
            final GLPixelAttributes attributes = this.getAttributes(glProfile, n);
            final PixelFormat awtPixelFormat = this.getAWTPixelFormat(glProfile, n);
            final int awtFormat = this.getAWTFormat(glProfile, n);
            final int hashCode = getHashCode(awtPixelFormat.comp, attributes, b);
            if (null != this.bufferMap.get(hashCode)) {
                return null;
            }
            final AWTGLPixelBuffer allocateImpl = this.allocateImpl(awtPixelFormat.comp, attributes, b, awtFormat, n2, n3, n4, 0);
            this.bufferMap.put(hashCode, allocateImpl);
            return allocateImpl;
        }
        
        @Override
        public void dispose() {
            final Iterator<IntObjectHashMap.Entry> iterator = this.bufferMap.iterator();
            while (iterator.hasNext()) {
                ((AWTGLPixelBuffer)iterator.next().value).dispose();
            }
            this.bufferMap.clear();
        }
    }
}
