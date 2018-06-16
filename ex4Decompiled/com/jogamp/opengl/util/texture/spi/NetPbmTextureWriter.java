// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import java.nio.channels.FileChannel;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.io.IOException;
import com.jogamp.common.util.IOUtil;
import com.jogamp.opengl.util.texture.TextureData;
import java.io.File;
import com.jogamp.opengl.GLException;

public class NetPbmTextureWriter implements TextureWriter
{
    int magic;
    public static final String PPM = "ppm";
    public static final String PAM = "pam";
    
    public NetPbmTextureWriter() {
        this(0);
    }
    
    public NetPbmTextureWriter(final int magic) {
        switch (magic) {
            case 0:
            case 6:
            case 7: {
                this.magic = magic;
            }
            default: {
                throw new GLException("Unsupported magic: " + magic + ", should be 0 (auto), 6 (PPM) or 7 (PAM)");
            }
        }
    }
    
    public int getMagic() {
        return this.magic;
    }
    
    public String getSuffix() {
        return (this.magic == 6) ? "ppm" : "pam";
    }
    
    @Override
    public boolean write(final File file, final TextureData textureData) throws IOException {
        final int magic = this.magic;
        if (0 == this.magic) {
            if ("ppm".equals(IOUtil.getFileSuffix(file))) {
                this.magic = 6;
            }
            else {
                if (!"pam".equals(IOUtil.getFileSuffix(file))) {
                    return false;
                }
                this.magic = 7;
            }
        }
        boolean writeImpl;
        try {
            writeImpl = this.writeImpl(file, textureData);
        }
        finally {
            this.magic = magic;
        }
        return writeImpl;
    }
    
    private boolean writeImpl(final File file, final TextureData textureData) throws IOException {
        int pixelFormat = textureData.getPixelFormat();
        final int pixelType = textureData.getPixelType();
        if ((pixelFormat != 6407 && pixelFormat != 6408 && pixelFormat != 32992 && pixelFormat != 32993) || (pixelType != 5120 && pixelType != 5121)) {
            throw new IOException("NetPbmTextureWriter writer doesn't support this pixel format / type (only GL_RGB/A + bytes)");
        }
        ByteBuffer byteBuffer = (ByteBuffer)textureData.getBuffer();
        if (null == byteBuffer) {
            byteBuffer = (ByteBuffer)textureData.getMipmapData()[0];
        }
        byteBuffer.rewind();
        final int n = (pixelFormat == 6408 || pixelFormat == 32993) ? 4 : 3;
        if (pixelFormat == 32992 || pixelFormat == 32993) {
            for (int i = 0; i < byteBuffer.remaining(); i += n) {
                final byte value = byteBuffer.get(i + 0);
                byteBuffer.put(i + 0, byteBuffer.get(i + 2));
                byteBuffer.put(i + 2, value);
            }
            pixelFormat = ((4 == n) ? 6408 : 6407);
            textureData.setPixelFormat(pixelFormat);
        }
        if (this.magic == 6 && n == 4) {
            throw new IOException("NetPbmTextureWriter magic 6 (PPM) doesn't RGBA pixel format, use magic 7 (PAM)");
        }
        final FileOutputStream fileOutputStream = IOUtil.getFileOutputStream(file, true);
        final StringBuilder sb = new StringBuilder();
        sb.append("P");
        sb.append(this.magic);
        sb.append("\n");
        if (7 == this.magic) {
            sb.append("WIDTH ");
        }
        sb.append(textureData.getWidth());
        if (7 == this.magic) {
            sb.append("\nHEIGHT ");
        }
        else {
            sb.append(" ");
        }
        sb.append(textureData.getHeight());
        if (7 == this.magic) {
            sb.append("\nDEPTH ");
            sb.append(n);
            sb.append("\nMAXVAL 255\nTUPLTYPE ");
            if (pixelFormat == 6408) {
                sb.append("RGB_ALPHA");
            }
            else {
                sb.append("RGB");
            }
            sb.append("\nENDHDR\n");
        }
        else {
            sb.append("\n255\n");
        }
        fileOutputStream.write(sb.toString().getBytes());
        final FileChannel channel = fileOutputStream.getChannel();
        channel.write(byteBuffer);
        channel.force(true);
        channel.close();
        fileOutputStream.close();
        byteBuffer.rewind();
        return true;
    }
}
