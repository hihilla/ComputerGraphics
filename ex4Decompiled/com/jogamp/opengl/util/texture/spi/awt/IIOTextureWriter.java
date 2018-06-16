// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi.awt;

import java.awt.Graphics;
import java.io.IOException;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.awt.image.ImageObserver;
import java.awt.Image;
import com.jogamp.common.util.IOUtil;
import com.jogamp.opengl.util.awt.ImageUtil;
import java.nio.ByteBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.BufferedImage;
import com.jogamp.opengl.util.texture.TextureData;
import java.io.File;
import com.jogamp.opengl.util.texture.spi.TextureWriter;

public class IIOTextureWriter implements TextureWriter
{
    @Override
    public boolean write(final File file, final TextureData textureData) throws IOException {
        final int pixelFormat = textureData.getPixelFormat();
        final int pixelType = textureData.getPixelType();
        if ((pixelFormat == 6407 || pixelFormat == 6408) && (pixelType == 5120 || pixelType == 5121)) {
            BufferedImage bufferedImage = new BufferedImage(textureData.getWidth(), textureData.getHeight(), (pixelFormat == 6407) ? 5 : 6);
            final byte[] data = ((DataBufferByte)bufferedImage.getRaster().getDataBuffer()).getData();
            ByteBuffer byteBuffer = (ByteBuffer)textureData.getBuffer();
            if (byteBuffer == null) {
                byteBuffer = (ByteBuffer)textureData.getMipmapData()[0];
            }
            byteBuffer.rewind();
            byteBuffer.get(data);
            byteBuffer.rewind();
            if (pixelFormat == 6407) {
                for (int i = 0; i < data.length; i += 3) {
                    final byte b = data[i + 0];
                    data[i + 0] = data[i + 2];
                    data[i + 2] = b;
                }
            }
            else {
                for (int j = 0; j < data.length; j += 4) {
                    final byte b2 = data[j + 0];
                    final byte b3 = data[j + 1];
                    final byte b4 = data[j + 2];
                    data[j + 0] = data[j + 3];
                    data[j + 1] = b4;
                    data[j + 2] = b3;
                    data[j + 3] = b2;
                }
            }
            ImageUtil.flipImageVertically(bufferedImage);
            if ("jpg".equals(IOUtil.getFileSuffix(file)) && bufferedImage.getType() == 6) {
                final BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 5);
                final Graphics graphics = bufferedImage2.getGraphics();
                graphics.drawImage(bufferedImage, 0, 0, null);
                graphics.dispose();
                bufferedImage = bufferedImage2;
            }
            return ImageIO.write(bufferedImage, IOUtil.getFileSuffix(file), file);
        }
        throw new IOException("ImageIO writer doesn't support this pixel format / type (only GL_RGB/A + bytes)");
    }
}
