// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.awt;

import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import java.awt.image.BufferedImage;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.TextureIO;

public class AWTTextureIO extends TextureIO
{
    public static TextureData newTextureData(final GLProfile glProfile, final BufferedImage bufferedImage, final boolean b) {
        return newTextureDataImpl(glProfile, bufferedImage, 0, 0, b);
    }
    
    public static TextureData newTextureData(final GLProfile glProfile, final BufferedImage bufferedImage, final int n, final int n2, final boolean b) throws IllegalArgumentException {
        if (n == 0 || n2 == 0) {
            throw new IllegalArgumentException("internalFormat and pixelFormat must be non-zero");
        }
        return newTextureDataImpl(glProfile, bufferedImage, n, n2, b);
    }
    
    public static Texture newTexture(final GLProfile glProfile, final BufferedImage bufferedImage, final boolean b) throws GLException {
        final TextureData textureData = newTextureData(glProfile, bufferedImage, b);
        final Texture texture = TextureIO.newTexture(textureData);
        textureData.flush();
        return texture;
    }
    
    private static TextureData newTextureDataImpl(final GLProfile glProfile, final BufferedImage bufferedImage, final int n, final int n2, final boolean b) {
        return new AWTTextureData(glProfile, n, n2, b, bufferedImage);
    }
}
