// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLProfile;
import java.nio.Buffer;
import com.jogamp.opengl.util.GLPixelBuffer;

public class TextureData
{
    protected int width;
    protected int height;
    private int border;
    protected GLPixelBuffer.GLPixelAttributes pixelAttributes;
    protected int internalFormat;
    protected boolean mipmap;
    private boolean dataIsCompressed;
    protected boolean mustFlipVertically;
    protected Buffer buffer;
    private Buffer[] mipmapData;
    private Flusher flusher;
    protected int rowLength;
    protected int alignment;
    protected int estimatedMemorySize;
    protected boolean haveEXTABGR;
    protected boolean haveGL12;
    protected GLProfile glProfile;
    protected ColorSpace pixelCS;
    ImageType srcImageType;
    
    public TextureData(final GLProfile glProfile, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final boolean b2, final boolean b3, final Buffer buffer, final Flusher flusher) throws IllegalArgumentException {
        this(glProfile, n, n2, n3, n4, new GLPixelBuffer.GLPixelAttributes(n5, n6), b, b2, b3, buffer, flusher);
    }
    
    public TextureData(final GLProfile glProfile, final int internalFormat, final int width, final int height, final int border, final GLPixelBuffer.GLPixelAttributes pixelAttributes, final boolean mipmap, final boolean dataIsCompressed, final boolean mustFlipVertically, final Buffer buffer, final Flusher flusher) throws IllegalArgumentException {
        this.pixelCS = ColorSpace.RGB;
        if (mipmap && dataIsCompressed) {
            throw new IllegalArgumentException("Can not generate mipmaps for compressed textures");
        }
        this.glProfile = glProfile;
        this.width = width;
        this.height = height;
        this.border = border;
        this.pixelAttributes = pixelAttributes;
        this.internalFormat = internalFormat;
        this.mipmap = mipmap;
        this.dataIsCompressed = dataIsCompressed;
        this.mustFlipVertically = mustFlipVertically;
        this.buffer = buffer;
        this.flusher = flusher;
        this.alignment = 1;
        this.estimatedMemorySize = estimatedMemorySize(buffer);
    }
    
    public TextureData(final GLProfile glProfile, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final boolean b2, final Buffer[] array, final Flusher flusher) throws IllegalArgumentException {
        this(glProfile, n, n2, n3, n4, new GLPixelBuffer.GLPixelAttributes(n5, n6), b, b2, array, flusher);
    }
    
    public TextureData(final GLProfile glProfile, final int internalFormat, final int width, final int height, final int border, final GLPixelBuffer.GLPixelAttributes pixelAttributes, final boolean dataIsCompressed, final boolean mustFlipVertically, final Buffer[] array, final Flusher flusher) throws IllegalArgumentException {
        this.pixelCS = ColorSpace.RGB;
        this.glProfile = glProfile;
        this.width = width;
        this.height = height;
        this.border = border;
        this.pixelAttributes = pixelAttributes;
        this.internalFormat = internalFormat;
        this.dataIsCompressed = dataIsCompressed;
        this.mustFlipVertically = mustFlipVertically;
        this.mipmapData = array.clone();
        this.flusher = flusher;
        this.alignment = 1;
        for (int i = 0; i < array.length; ++i) {
            this.estimatedMemorySize += estimatedMemorySize(array[i]);
        }
    }
    
    public ColorSpace getColorSpace() {
        return this.pixelCS;
    }
    
    public void setColorSpace(final ColorSpace pixelCS) {
        this.pixelCS = pixelCS;
    }
    
    protected TextureData(final GLProfile glProfile) {
        this.pixelCS = ColorSpace.RGB;
        this.glProfile = glProfile;
        this.pixelAttributes = GLPixelBuffer.GLPixelAttributes.UNDEF;
    }
    
    public final ImageType getSourceImageType() {
        return this.srcImageType;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getBorder() {
        return this.border;
    }
    
    public GLPixelBuffer.GLPixelAttributes getPixelAttributes() {
        return this.pixelAttributes;
    }
    
    public int getPixelFormat() {
        return this.pixelAttributes.format;
    }
    
    public int getPixelType() {
        return this.pixelAttributes.type;
    }
    
    public int getInternalFormat() {
        return this.internalFormat;
    }
    
    public boolean getMipmap() {
        return this.mipmap;
    }
    
    public boolean isDataCompressed() {
        return this.dataIsCompressed;
    }
    
    public boolean getMustFlipVertically() {
        return this.mustFlipVertically;
    }
    
    public Buffer getBuffer() {
        return this.buffer;
    }
    
    public Buffer[] getMipmapData() {
        return this.mipmapData;
    }
    
    public int getAlignment() {
        return this.alignment;
    }
    
    public int getRowLength() {
        return this.rowLength;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public void setBorder(final int border) {
        this.border = border;
    }
    
    public void setPixelAttributes(final GLPixelBuffer.GLPixelAttributes pixelAttributes) {
        this.pixelAttributes = pixelAttributes;
    }
    
    public void setPixelFormat(final int n) {
        if (this.pixelAttributes.format != n) {
            this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(n, this.pixelAttributes.type);
        }
    }
    
    public void setPixelType(final int n) {
        if (this.pixelAttributes.type != n) {
            this.pixelAttributes = new GLPixelBuffer.GLPixelAttributes(this.pixelAttributes.format, n);
        }
    }
    
    public void setInternalFormat(final int internalFormat) {
        this.internalFormat = internalFormat;
    }
    
    public void setMipmap(final boolean mipmap) {
        this.mipmap = mipmap;
    }
    
    public void setIsDataCompressed(final boolean dataIsCompressed) {
        this.dataIsCompressed = dataIsCompressed;
    }
    
    public void setMustFlipVertically(final boolean mustFlipVertically) {
        this.mustFlipVertically = mustFlipVertically;
    }
    
    public void setBuffer(final Buffer buffer) {
        this.buffer = buffer;
        this.estimatedMemorySize = estimatedMemorySize(buffer);
    }
    
    public void setAlignment(final int alignment) {
        this.alignment = alignment;
    }
    
    public void setRowLength(final int rowLength) {
        this.rowLength = rowLength;
    }
    
    public void setHaveEXTABGR(final boolean haveEXTABGR) {
        this.haveEXTABGR = haveEXTABGR;
    }
    
    public void setHaveGL12(final boolean haveGL12) {
        this.haveGL12 = haveGL12;
    }
    
    public GLProfile getGLProfile() {
        return this.glProfile;
    }
    
    public int getEstimatedMemorySize() {
        return this.estimatedMemorySize;
    }
    
    public void flush() {
        if (this.flusher != null) {
            this.flusher.flush();
            this.flusher = null;
        }
    }
    
    public void destroy() {
        this.flush();
    }
    
    @Override
    public String toString() {
        return "TextureData[" + this.width + "x" + this.height + ", y-flip " + this.mustFlipVertically + ", internFormat 0x" + Integer.toHexString(this.internalFormat) + ", " + this.pixelAttributes + ", border " + this.border + ", estSize " + this.estimatedMemorySize + ", alignment " + this.alignment + ", rowlen " + this.rowLength + ((null != this.srcImageType) ? (", " + this.srcImageType) : "");
    }
    
    protected static int estimatedMemorySize(final Buffer buffer) {
        if (buffer == null) {
            return 0;
        }
        return buffer.capacity() * Buffers.sizeOfBufferElem(buffer);
    }
    
    public enum ColorSpace
    {
        RGB, 
        YCbCr, 
        YCCK, 
        CMYK;
    }
    
    public interface Flusher
    {
        void flush();
    }
}
