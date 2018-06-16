// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture;

import com.jogamp.opengl.util.TimeFrameI;
import com.jogamp.opengl.GL;

public interface TextureSequence
{
    public static final String samplerExternalOES = "samplerExternalOES";
    public static final String sampler2D = "sampler2D";
    
    int getTextureTarget();
    
    int getTextureUnit();
    
    int[] getTextureMinMagFilter();
    
    int[] getTextureWrapST();
    
    boolean isTextureAvailable();
    
    TextureFrame getLastTexture() throws IllegalStateException;
    
    TextureFrame getNextTexture(final GL p0) throws IllegalStateException;
    
    String getRequiredExtensionsShaderStub() throws IllegalStateException;
    
    String getTextureSampler2DType() throws IllegalStateException;
    
    String getTextureLookupFunctionName(final String p0) throws IllegalStateException;
    
    String getTextureLookupFragmentShaderImpl() throws IllegalStateException;
    
    int getTextureFragmentShaderHashCode();
    
    public static class TextureFrame extends TimeFrameI
    {
        protected final Texture texture;
        
        public TextureFrame(final Texture texture, final int n, final int n2) {
            super(n, n2);
            this.texture = texture;
        }
        
        public TextureFrame(final Texture texture) {
            this.texture = texture;
        }
        
        public final Texture getTexture() {
            return this.texture;
        }
        
        @Override
        public String toString() {
            return "TextureFrame[pts " + this.pts + " ms, l " + this.duration + " ms, texID " + ((null != this.texture) ? this.texture.getTextureObject() : 0) + "]";
        }
    }
    
    public interface TexSeqEventListener<T extends TextureSequence>
    {
        void newFrameAvailable(final T p0, final TextureFrame p1, final long p2);
    }
}
