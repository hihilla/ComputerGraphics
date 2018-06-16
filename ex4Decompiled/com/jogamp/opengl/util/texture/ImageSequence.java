// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture;

import com.jogamp.opengl.GLException;
import java.io.IOException;
import java.net.URLConnection;
import com.jogamp.opengl.GLProfile;
import com.jogamp.common.util.IOUtil;
import com.jogamp.opengl.GL;
import java.util.ArrayList;
import java.util.List;

public class ImageSequence implements TextureSequence
{
    private final int textureUnit;
    private final boolean useBuildInTexLookup;
    private final List<TextureFrame> frames;
    private final int[] texMinMagFilter;
    private final int[] texWrapST;
    private volatile int frameIdx;
    private volatile boolean manualStepping;
    private int textureFragmentShaderHashCode;
    private String textureLookupFunctionName;
    
    public ImageSequence(final int textureUnit, final boolean useBuildInTexLookup) {
        this.frames = new ArrayList<TextureFrame>();
        this.texMinMagFilter = new int[] { 9728, 9728 };
        this.texWrapST = new int[] { 33071, 33071 };
        this.frameIdx = 0;
        this.manualStepping = false;
        this.textureFragmentShaderHashCode = 0;
        this.textureLookupFunctionName = "myTexture2D";
        this.textureUnit = textureUnit;
        this.useBuildInTexLookup = useBuildInTexLookup;
    }
    
    public void setParams(final int n, final int n2, final int n3, final int n4) {
        this.texMinMagFilter[0] = n2;
        this.texMinMagFilter[1] = n;
        this.texWrapST[0] = n3;
        this.texWrapST[1] = n4;
    }
    
    public final void addFrame(final GL gl, final Texture texture) {
        this.frames.add(new TextureFrame(texture));
        texture.bind(gl);
        gl.glTexParameteri(this.getTextureTarget(), 10241, this.texMinMagFilter[0]);
        gl.glTexParameteri(this.getTextureTarget(), 10240, this.texMinMagFilter[1]);
        gl.glTexParameteri(this.getTextureTarget(), 10242, this.texWrapST[0]);
        gl.glTexParameteri(this.getTextureTarget(), 10243, this.texWrapST[1]);
    }
    
    public final void addFrame(final GL gl, final Class<?> clazz, final String s, final String s2) throws IOException {
        final URLConnection resource = IOUtil.getResource(s, clazz.getClassLoader(), clazz);
        if (null != resource) {
            final TextureData textureData = TextureIO.newTextureData(GLProfile.getGL2ES2(), resource.getInputStream(), false, s2);
            final Texture texture = new Texture(this.getTextureTarget());
            texture.updateImage(gl, textureData);
            this.addFrame(gl, texture);
        }
    }
    
    public final int getFrameCount() {
        return this.frames.size();
    }
    
    public final int getCurrentIdx() {
        return this.frameIdx;
    }
    
    public final void setCurrentIdx(final int frameIdx) throws IndexOutOfBoundsException {
        if (0 > frameIdx || frameIdx >= this.frames.size()) {
            throw new IndexOutOfBoundsException("idx shall be within 0 <= " + frameIdx + " < " + this.frames.size());
        }
        this.frameIdx = frameIdx;
    }
    
    public final void setManualStepping(final boolean manualStepping) {
        this.manualStepping = manualStepping;
    }
    
    public final boolean getManualStepping() {
        return this.manualStepping;
    }
    
    public final TextureFrame getFrame(final int n) {
        return this.frames.get(n);
    }
    
    public void destroy(final GL gl) throws GLException {
        for (int i = this.frames.size() - 1; i >= 0; --i) {
            this.frames.get(i).getTexture().destroy(gl);
        }
        this.frames.clear();
    }
    
    @Override
    public int getTextureTarget() {
        return 3553;
    }
    
    @Override
    public int getTextureUnit() {
        return this.textureUnit;
    }
    
    @Override
    public int[] getTextureMinMagFilter() {
        return this.texMinMagFilter;
    }
    
    @Override
    public int[] getTextureWrapST() {
        return this.texWrapST;
    }
    
    @Override
    public boolean isTextureAvailable() {
        return this.frames.size() > 0;
    }
    
    @Override
    public TextureFrame getLastTexture() throws IllegalStateException {
        return this.frames.get(this.frameIdx);
    }
    
    @Override
    public TextureFrame getNextTexture(final GL gl) throws IllegalStateException {
        if (!this.manualStepping) {
            this.frameIdx = (this.frameIdx + 1) % this.frames.size();
        }
        return this.frames.get(this.frameIdx);
    }
    
    @Override
    public String getRequiredExtensionsShaderStub() throws IllegalStateException {
        return "// TextTextureSequence: No extensions required\n";
    }
    
    @Override
    public String getTextureSampler2DType() throws IllegalStateException {
        return "sampler2D";
    }
    
    @Override
    public String getTextureLookupFunctionName(final String textureLookupFunctionName) throws IllegalStateException {
        if (this.useBuildInTexLookup) {
            return "texture2D";
        }
        if (null != textureLookupFunctionName && textureLookupFunctionName.length() > 0) {
            this.textureLookupFunctionName = textureLookupFunctionName;
        }
        return this.textureLookupFunctionName;
    }
    
    @Override
    public String getTextureLookupFragmentShaderImpl() throws IllegalStateException {
        if (this.useBuildInTexLookup) {
            return "";
        }
        return "\nvec4 " + this.textureLookupFunctionName + "(in " + this.getTextureSampler2DType() + " image, in vec2 texCoord) {\n" + "  return texture2D(image, texCoord);\n" + "}\n\n";
    }
    
    @Override
    public int getTextureFragmentShaderHashCode() {
        if (!this.isTextureAvailable()) {
            return this.textureFragmentShaderHashCode = 0;
        }
        if (0 == this.textureFragmentShaderHashCode) {
            final int n = 31 + this.getTextureLookupFragmentShaderImpl().hashCode();
            this.textureFragmentShaderHashCode = (n << 5) - n + this.getTextureSampler2DType().hashCode();
        }
        return this.textureFragmentShaderHashCode;
    }
}
