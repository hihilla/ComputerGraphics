// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.awt;

import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.util.Hashtable;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferUShort;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferFloat;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferByte;
import java.awt.image.SampleModel;
import com.jogamp.opengl.GLException;
import java.awt.image.ComponentSampleModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.nio.Buffer;
import com.jogamp.opengl.util.GLPixelBuffer;
import com.jogamp.opengl.GLProfile;
import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import com.jogamp.opengl.util.texture.TextureData;

public class AWTTextureData extends TextureData
{
    private BufferedImage imageForLazyCustomConversion;
    private boolean expectingEXTABGR;
    private boolean expectingGL12;
    private static final ColorModel rgbaColorModel;
    private static final ColorModel rgbColorModel;
    
    public AWTTextureData(final GLProfile glProfile, final int internalFormat, final int n, final boolean mipmap, final BufferedImage bufferedImage) {
        super(glProfile);
        if (internalFormat == 0) {
            this.internalFormat = (bufferedImage.getColorModel().hasAlpha() ? 6408 : 6407);
        }
        else {
            this.internalFormat = internalFormat;
        }
        this.createFromImage(glProfile, bufferedImage);
        this.mipmap = mipmap;
        if (this.buffer != null) {
            this.estimatedMemorySize = TextureData.estimatedMemorySize(this.buffer);
        }
        else if (this.imageForLazyCustomConversion != null) {
            this.estimatedMemorySize = TextureData.estimatedMemorySize(this.wrapImageDataBuffer(this.imageForLazyCustomConversion));
        }
    }
    
    private void validatePixelAttributes() {
        if (this.imageForLazyCustomConversion != null && (!this.expectingEXTABGR || !this.haveEXTABGR) && (!this.expectingGL12 || !this.haveGL12)) {
            this.revertPixelAttributes();
        }
    }
    
    @Override
    public GLPixelBuffer.GLPixelAttributes getPixelAttributes() {
        this.validatePixelAttributes();
        return super.getPixelAttributes();
    }
    
    @Override
    public int getPixelFormat() {
        this.validatePixelAttributes();
        return super.getPixelFormat();
    }
    
    @Override
    public int getPixelType() {
        this.validatePixelAttributes();
        return super.getPixelType();
    }
    
    @Override
    public Buffer getBuffer() {
        if (this.imageForLazyCustomConversion != null && (!this.expectingEXTABGR || !this.haveEXTABGR) && (!this.expectingGL12 || !this.haveGL12)) {
            this.revertPixelAttributes();
            this.createFromCustom(this.imageForLazyCustomConversion);
        }
        return this.buffer;
    }
    
    private void createFromImage(final GLProfile glProfile, final BufferedImage bufferedImage) {
        this.pixelAttributes = GLPixelBuffer.GLPixelAttributes.UNDEF;
        this.mustFlipVertically = true;
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        final SampleModel sampleModel = bufferedImage.getRaster().getSampleModel();
        int rowLength;
        if (sampleModel instanceof SinglePixelPackedSampleModel) {
            rowLength = ((SinglePixelPackedSampleModel)sampleModel).getScanlineStride();
        }
        else if (sampleModel instanceof MultiPixelPackedSampleModel) {
            rowLength = ((MultiPixelPackedSampleModel)sampleModel).getScanlineStride();
        }
        else {
            if (!(sampleModel instanceof ComponentSampleModel)) {
                this.setupLazyCustomConversion(bufferedImage);
                return;
            }
            rowLength = ((ComponentSampleModel)sampleModel).getScanlineStride();
        }
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        if (glProfile.isGL2GL3()) {
            switch (bufferedImage.getType()) {
                case 1: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(32993, 33639);
                    this.rowLength = rowLength;
                    this.alignment = 4;
                    this.expectingGL12 = true;
                    this.setupLazyCustomConversion(bufferedImage);
                    break;
                }
                case 3: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(32993, 33639);
                    this.rowLength = rowLength;
                    this.alignment = 4;
                    this.expectingGL12 = true;
                    this.setupLazyCustomConversion(bufferedImage);
                    break;
                }
                case 4: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6408, 33639);
                    this.rowLength = rowLength;
                    this.alignment = 4;
                    this.expectingGL12 = true;
                    this.setupLazyCustomConversion(bufferedImage);
                    break;
                }
                case 5: {
                    if (rowLength % 3 == 0) {
                        this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(32992, 5121);
                        this.rowLength = rowLength / 3;
                        this.alignment = 1;
                        break;
                    }
                    this.setupLazyCustomConversion(bufferedImage);
                    return;
                }
                case 7: {
                    if (rowLength % 4 != 0 || glProfile.isGL2()) {}
                    this.setupLazyCustomConversion(bufferedImage);
                    return;
                }
                case 8: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6407, 33635);
                    this.rowLength = rowLength;
                    this.alignment = 2;
                    this.expectingGL12 = true;
                    this.setupLazyCustomConversion(bufferedImage);
                    break;
                }
                case 9: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(32993, 33638);
                    this.rowLength = rowLength;
                    this.alignment = 2;
                    this.expectingGL12 = true;
                    this.setupLazyCustomConversion(bufferedImage);
                    break;
                }
                case 10: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6409, 5121);
                    this.rowLength = rowLength;
                    this.alignment = 1;
                    break;
                }
                case 11: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6409, 5123);
                    this.rowLength = rowLength;
                    this.alignment = 2;
                    break;
                }
                default: {
                    final ColorModel colorModel = bufferedImage.getColorModel();
                    if (colorModel.equals(AWTTextureData.rgbColorModel)) {
                        this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6407, 5121);
                        this.rowLength = rowLength / 3;
                        this.alignment = 1;
                        break;
                    }
                    if (colorModel.equals(AWTTextureData.rgbaColorModel)) {
                        this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6408, 5121);
                        this.rowLength = rowLength / 4;
                        this.alignment = 4;
                        break;
                    }
                    this.setupLazyCustomConversion(bufferedImage);
                    return;
                }
            }
        }
        else {
            switch (bufferedImage.getType()) {
                case 1: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6407, 5121);
                    this.rowLength = rowLength;
                    this.alignment = 3;
                    this.expectingGL12 = true;
                    this.setupLazyCustomConversion(bufferedImage);
                    break;
                }
                case 3: {
                    throw new GLException("INT_ARGB_PRE n.a.");
                }
                case 4: {
                    throw new GLException("INT_BGR n.a.");
                }
                case 5: {
                    throw new GLException("INT_BGR n.a.");
                }
                case 7: {
                    throw new GLException("INT_BGR n.a.");
                }
                case 8: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6407, 33635);
                    this.rowLength = rowLength;
                    this.alignment = 2;
                    this.expectingGL12 = true;
                    this.setupLazyCustomConversion(bufferedImage);
                    break;
                }
                case 9: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6408, 32820);
                    this.rowLength = rowLength;
                    this.alignment = 2;
                    this.expectingGL12 = true;
                    this.setupLazyCustomConversion(bufferedImage);
                    break;
                }
                case 10: {
                    this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6409, 5121);
                    this.rowLength = rowLength;
                    this.alignment = 1;
                    break;
                }
                case 11: {
                    throw new GLException("USHORT_GRAY n.a.");
                }
                default: {
                    final ColorModel colorModel2 = bufferedImage.getColorModel();
                    if (colorModel2.equals(AWTTextureData.rgbColorModel)) {
                        this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6407, 5121);
                        this.rowLength = rowLength / 3;
                        this.alignment = 1;
                        break;
                    }
                    if (colorModel2.equals(AWTTextureData.rgbaColorModel)) {
                        this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(6408, 5121);
                        this.rowLength = rowLength / 4;
                        this.alignment = 4;
                        break;
                    }
                    this.setupLazyCustomConversion(bufferedImage);
                    return;
                }
            }
        }
        this.createNIOBufferFromImage(bufferedImage);
    }
    
    private void setupLazyCustomConversion(final BufferedImage imageForLazyCustomConversion) {
        this.imageForLazyCustomConversion = imageForLazyCustomConversion;
        final boolean hasAlpha = imageForLazyCustomConversion.getColorModel().hasAlpha();
        int format = this.pixelAttributes.format;
        int type = this.pixelAttributes.type;
        if (format == 0) {
            format = (hasAlpha ? 6408 : 6407);
        }
        this.alignment = 1;
        this.rowLength = this.width;
        final DataBuffer dataBuffer = imageForLazyCustomConversion.getRaster().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte || this.isPackedInt(imageForLazyCustomConversion)) {
            if (type == 0) {
                type = 5121;
            }
        }
        else {
            if (dataBuffer instanceof DataBufferDouble) {
                throw new RuntimeException("DataBufferDouble rasters not supported by OpenGL");
            }
            if (dataBuffer instanceof DataBufferFloat) {
                if (type == 0) {
                    type = 5126;
                }
            }
            else if (dataBuffer instanceof DataBufferInt) {
                if (type == 0) {
                    type = 5125;
                }
            }
            else if (dataBuffer instanceof DataBufferShort) {
                if (type == 0) {
                    type = 5122;
                }
            }
            else {
                if (!(dataBuffer instanceof DataBufferUShort)) {
                    throw new RuntimeException("Unexpected DataBuffer type?");
                }
                if (type == 0) {
                    type = 5123;
                }
            }
        }
        this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(format, type);
    }
    
    private void createFromCustom(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final boolean hasAlpha = bufferedImage.getColorModel().hasAlpha();
        int dataType = bufferedImage.getRaster().getDataBuffer().getDataType();
        if (this.isPackedInt(bufferedImage)) {
            dataType = 0;
        }
        ColorModel colorModel;
        if (dataType == 0) {
            colorModel = (hasAlpha ? AWTTextureData.rgbaColorModel : AWTTextureData.rgbColorModel);
        }
        else if (hasAlpha) {
            colorModel = new ComponentColorModel(java.awt.color.ColorSpace.getInstance(1000), null, true, true, 3, dataType);
        }
        else {
            colorModel = new ComponentColorModel(java.awt.color.ColorSpace.getInstance(1000), null, false, false, 1, dataType);
        }
        final BufferedImage bufferedImage2 = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(width, height), colorModel.isAlphaPremultiplied(), null);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        this.createNIOBufferFromImage(bufferedImage2);
    }
    
    private boolean isPackedInt(final BufferedImage bufferedImage) {
        final int type = bufferedImage.getType();
        return type == 1 || type == 4 || type == 2 || type == 3;
    }
    
    private void revertPixelAttributes() {
        this.pixelAttributes = GLPixelBuffer.GLPixelAttributes.UNDEF;
        this.setupLazyCustomConversion(this.imageForLazyCustomConversion);
    }
    
    private void createNIOBufferFromImage(final BufferedImage bufferedImage) {
        this.buffer = this.wrapImageDataBuffer(bufferedImage);
    }
    
    private Buffer wrapImageDataBuffer(final BufferedImage bufferedImage) {
        final DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte) {
            return ByteBuffer.wrap(((DataBufferByte)dataBuffer).getData());
        }
        if (dataBuffer instanceof DataBufferDouble) {
            throw new RuntimeException("DataBufferDouble rasters not supported by OpenGL");
        }
        if (dataBuffer instanceof DataBufferFloat) {
            return FloatBuffer.wrap(((DataBufferFloat)dataBuffer).getData());
        }
        if (dataBuffer instanceof DataBufferInt) {
            return IntBuffer.wrap(((DataBufferInt)dataBuffer).getData());
        }
        if (dataBuffer instanceof DataBufferShort) {
            return ShortBuffer.wrap(((DataBufferShort)dataBuffer).getData());
        }
        if (dataBuffer instanceof DataBufferUShort) {
            return ShortBuffer.wrap(((DataBufferUShort)dataBuffer).getData());
        }
        throw new RuntimeException("Unexpected DataBuffer type?");
    }
    
    static {
        rgbaColorModel = new ComponentColorModel(java.awt.color.ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, true, true, 3, 0);
        rgbColorModel = new ComponentColorModel(java.awt.color.ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 0 }, false, false, 1, 0);
    }
}
