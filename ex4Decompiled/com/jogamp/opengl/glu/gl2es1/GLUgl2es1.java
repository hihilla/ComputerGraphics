// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.glu.gl2es1;

import com.jogamp.opengl.GL;
import jogamp.opengl.glu.mipmap.Mipmap;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.glu.GLU;

public class GLUgl2es1 extends GLU
{
    protected static boolean availableMipmap;
    protected static boolean checkedMipmap;
    
    public static final GL2ES1 getCurrentGL2ES1() throws GLException {
        final GLContext current = GLContext.getCurrent();
        if (current == null) {
            throw new GLException("No OpenGL context current on this thread");
        }
        return current.getGL().getGL2ES1();
    }
    
    protected static final void validateMipmap() {
        if (!GLUgl2es1.checkedMipmap) {
            GLUgl2es1.availableMipmap = ReflectionUtil.isClassAvailable("jogamp.opengl.glu.mipmap.Mipmap", GLU.class.getClassLoader());
            GLUgl2es1.checkedMipmap = true;
        }
        if (!GLUgl2es1.availableMipmap) {
            throw new GLException("Mipmap not available");
        }
    }
    
    private final ByteBuffer copyToByteBuffer(final Buffer buffer) {
        if (buffer instanceof ByteBuffer) {
            if (buffer.position() == 0) {
                return (ByteBuffer)buffer;
            }
            return Buffers.copyByteBuffer((ByteBuffer)buffer);
        }
        else {
            if (buffer instanceof ShortBuffer) {
                return Buffers.copyShortBufferAsByteBuffer((ShortBuffer)buffer);
            }
            if (buffer instanceof IntBuffer) {
                return Buffers.copyIntBufferAsByteBuffer((IntBuffer)buffer);
            }
            if (buffer instanceof FloatBuffer) {
                return Buffers.copyFloatBufferAsByteBuffer((FloatBuffer)buffer);
            }
            throw new IllegalArgumentException("Unsupported buffer type (must be one of byte, short, int, or float)");
        }
    }
    
    @Override
    public final int gluScaleImage(final int n, final int n2, final int n3, final int n4, final Buffer buffer, final int n5, final int n6, final int n7, final Buffer buffer2) {
        validateMipmap();
        final ByteBuffer copyToByteBuffer = this.copyToByteBuffer(buffer);
        ByteBuffer byteBuffer;
        if (buffer2 instanceof ByteBuffer) {
            byteBuffer = (ByteBuffer)buffer2;
        }
        else if (buffer2 instanceof ShortBuffer) {
            byteBuffer = Buffers.newDirectByteBuffer(buffer2.remaining() * 2);
        }
        else if (buffer2 instanceof IntBuffer) {
            byteBuffer = Buffers.newDirectByteBuffer(buffer2.remaining() * 4);
        }
        else {
            if (!(buffer2 instanceof FloatBuffer)) {
                throw new IllegalArgumentException("Unsupported destination buffer type (must be byte, short, int, or float)");
            }
            byteBuffer = Buffers.newDirectByteBuffer(buffer2.remaining() * 4);
        }
        final int gluScaleImage = Mipmap.gluScaleImage(getCurrentGL2ES1(), n, n2, n3, n4, copyToByteBuffer, n5, n6, n7, byteBuffer);
        if (gluScaleImage == 0) {
            byteBuffer.rewind();
            if (byteBuffer != buffer2) {
                if (buffer2 instanceof ShortBuffer) {
                    ((ShortBuffer)buffer2).put(byteBuffer.asShortBuffer());
                }
                else if (buffer2 instanceof IntBuffer) {
                    ((IntBuffer)buffer2).put(byteBuffer.asIntBuffer());
                }
                else {
                    if (!(buffer2 instanceof FloatBuffer)) {
                        throw new RuntimeException("Should not reach here");
                    }
                    ((FloatBuffer)buffer2).put(byteBuffer.asFloatBuffer());
                }
            }
        }
        return gluScaleImage;
    }
    
    @Override
    public final int gluBuild1DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        validateMipmap();
        return Mipmap.gluBuild1DMipmapLevels(getCurrentGL2ES1(), n, n2, n3, n4, n5, n6, n7, n8, this.copyToByteBuffer(buffer));
    }
    
    @Override
    public final int gluBuild1DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer) {
        validateMipmap();
        return Mipmap.gluBuild1DMipmaps(getCurrentGL2ES1(), n, n2, n3, n4, n5, this.copyToByteBuffer(buffer));
    }
    
    @Override
    public final int gluBuild2DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Buffer buffer) {
        validateMipmap();
        return Mipmap.gluBuild2DMipmapLevels(getCurrentGL2ES1(), n, n2, n3, n4, n5, n6, n7, n8, n9, this.copyToByteBuffer(buffer));
    }
    
    @Override
    public final int gluBuild2DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        validateMipmap();
        return Mipmap.gluBuild2DMipmaps(getCurrentGL2ES1(), n, n2, n3, n4, n5, n6, this.copyToByteBuffer(buffer));
    }
    
    @Override
    public final int gluBuild3DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        validateMipmap();
        return Mipmap.gluBuild3DMipmapLevels(getCurrentGL2ES1(), n, n2, n3, n4, n5, n6, n7, n8, n9, n10, this.copyToByteBuffer(buffer));
    }
    
    @Override
    public final int gluBuild3DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        validateMipmap();
        return Mipmap.gluBuild3DMipmaps(getCurrentGL2ES1(), n, n2, n3, n4, n5, n6, n7, this.copyToByteBuffer(buffer));
    }
    
    static {
        GLUgl2es1.availableMipmap = false;
        GLUgl2es1.checkedMipmap = false;
    }
}
