// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLException;
import com.jogamp.common.nio.Buffers;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GL;
import java.io.IOException;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.Texture;

public class GLReadBufferUtil
{
    protected final GLPixelBuffer.GLPixelBufferProvider pixelBufferProvider;
    protected final Texture readTexture;
    protected final GLPixelStorageModes psm;
    protected boolean hasAlpha;
    protected GLPixelBuffer readPixelBuffer;
    protected TextureData readTextureData;
    
    public GLReadBufferUtil(final boolean b, final boolean b2) {
        this(GLPixelBuffer.defaultProviderNoRowStride, b, b2);
    }
    
    public GLReadBufferUtil(final GLPixelBuffer.GLPixelBufferProvider pixelBufferProvider, final boolean hasAlpha, final boolean b) {
        this.readPixelBuffer = null;
        this.readTextureData = null;
        this.pixelBufferProvider = pixelBufferProvider;
        this.readTexture = (b ? new Texture(3553) : null);
        this.psm = new GLPixelStorageModes();
        this.hasAlpha = hasAlpha;
    }
    
    public GLPixelBuffer.GLPixelBufferProvider getPixelBufferProvider() {
        return this.pixelBufferProvider;
    }
    
    public boolean isValid() {
        return null != this.readTextureData && null != this.readPixelBuffer && this.readPixelBuffer.isValid();
    }
    
    public boolean hasAlpha() {
        return this.hasAlpha;
    }
    
    public GLPixelStorageModes getGLPixelStorageModes() {
        return this.psm;
    }
    
    public GLPixelBuffer getPixelBuffer() {
        return this.readPixelBuffer;
    }
    
    public void rewindPixelBuffer() {
        if (null != this.readPixelBuffer) {
            this.readPixelBuffer.rewind();
        }
    }
    
    public TextureData getTextureData() {
        return this.readTextureData;
    }
    
    public Texture getTexture() {
        return this.readTexture;
    }
    
    public void write(final File file) {
        try {
            TextureIO.write(this.readTextureData, file);
            this.rewindPixelBuffer();
        }
        catch (IOException ex) {
            throw new RuntimeException("can not write to file: " + file.getAbsolutePath(), ex);
        }
    }
    
    public boolean readPixels(final GL gl, final boolean b) {
        return this.readPixels(gl, 0, 0, 0, 0, b);
    }
    
    public boolean readPixels(final GL gl, final int n, final int n2, final int n3, final int n4, final boolean b) {
        final GLDrawable glReadDrawable = gl.getContext().getGLReadDrawable();
        int surfaceWidth;
        if (0 >= n3 || glReadDrawable.getSurfaceWidth() < n3) {
            surfaceWidth = glReadDrawable.getSurfaceWidth();
        }
        else {
            surfaceWidth = n3;
        }
        int surfaceHeight;
        if (0 >= n4 || glReadDrawable.getSurfaceHeight() < n4) {
            surfaceHeight = glReadDrawable.getSurfaceHeight();
        }
        else {
            surfaceHeight = n4;
        }
        return this.readPixelsImpl(glReadDrawable, gl, n, n2, surfaceWidth, surfaceHeight, b);
    }
    
    protected boolean readPixelsImpl(final GLDrawable glDrawable, final GL gl, final int n, final int n2, final int width, final int height, final boolean b) {
        final int glGetError = gl.glGetError();
        if (glGetError != 0) {
            System.err.println("Info: GLReadBufferUtil.readPixels: pre-exisiting GL error 0x" + Integer.toHexString(glGetError));
        }
        final int n3 = this.hasAlpha ? 4 : 3;
        final PixelFormat.Composition hostPixelComp = this.pixelBufferProvider.getHostPixelComp(gl.getGLProfile(), n3);
        final GLPixelBuffer.GLPixelAttributes attributes = this.pixelBufferProvider.getAttributes(gl, n3, true);
        final int componentCount = attributes.pfmt.comp.componentCount();
        this.hasAlpha = (0 <= attributes.pfmt.comp.find(PixelFormat.CType.A));
        final int n4 = (4 == componentCount) ? 4 : 1;
        final int internalFormat = (4 == componentCount) ? 6408 : 6407;
        boolean b2;
        if (glDrawable.isGLOriented()) {
            b2 = b;
        }
        else {
            b2 = !b;
        }
        final int sizeof = GLBuffers.sizeof(gl, new int[1], attributes.pfmt.comp.bytesPerPixel(), width, height, 1, true);
        boolean b3 = false;
        Label_0395: {
            if (null == this.readPixelBuffer || this.readPixelBuffer.requiresNewBuffer(gl, width, height, sizeof)) {
                this.readPixelBuffer = this.pixelBufferProvider.allocate(gl, hostPixelComp, attributes, true, width, height, 1, sizeof);
                Buffers.rangeCheckBytes(this.readPixelBuffer.buffer, sizeof);
                try {
                    this.readTextureData = new TextureData(gl.getGLProfile(), internalFormat, width, height, 0, attributes, false, false, b2, this.readPixelBuffer.buffer, null);
                    b3 = true;
                    break Label_0395;
                }
                catch (Exception ex) {
                    this.readTextureData = null;
                    this.readPixelBuffer = null;
                    throw new RuntimeException("can not fetch offscreen texture", ex);
                }
            }
            this.readTextureData.setInternalFormat(internalFormat);
            this.readTextureData.setWidth(width);
            this.readTextureData.setHeight(height);
            this.readTextureData.setPixelAttributes(attributes);
        }
        boolean b4 = null != this.readPixelBuffer && this.readPixelBuffer.isValid();
        if (b4) {
            this.psm.setPackAlignment(gl, n4);
            if (gl.isGL2ES3()) {
                final GL2ES3 gl2ES3 = gl.getGL2ES3();
                this.psm.setPackRowLength(gl2ES3, width);
                gl2ES3.glReadBuffer(gl2ES3.getDefaultReadBuffer());
            }
            this.readPixelBuffer.clear();
            try {
                gl.glReadPixels(n, n2, width, height, attributes.format, attributes.type, this.readPixelBuffer.buffer);
            }
            catch (GLException ex2) {
                b4 = false;
                ex2.printStackTrace();
            }
            this.readPixelBuffer.position(sizeof);
            this.readPixelBuffer.flip();
            final int glGetError2 = gl.glGetError();
            if (glGetError2 != 0) {
                System.err.println("GLReadBufferUtil.readPixels: readPixels error 0x" + Integer.toHexString(glGetError2) + " " + width + "x" + height + ", " + attributes + ", " + this.readPixelBuffer + ", sz " + sizeof);
                b4 = false;
            }
            if (b4 && null != this.readTexture) {
                if (b3) {
                    this.readTexture.updateImage(gl, this.readTextureData);
                }
                else {
                    this.readTexture.updateSubImage(gl, this.readTextureData, 0, n, n2, 0, 0, width, height);
                }
                this.readPixelBuffer.rewind();
            }
            this.psm.restore(gl);
        }
        return b4;
    }
    
    public void dispose(final GL gl) {
        if (null != this.readTexture) {
            this.readTexture.destroy(gl);
            this.readTextureData = null;
        }
        if (null != this.readPixelBuffer) {
            this.readPixelBuffer.dispose();
            this.readPixelBuffer = null;
        }
    }
}
