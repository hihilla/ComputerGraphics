// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.glu.gl2;

import java.security.AccessController;
import java.security.PrivilegedAction;
import jogamp.opengl.GLDynamicLookupHelper;
import com.jogamp.common.os.DynamicLookupHelper;
import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import jogamp.opengl.GLContextImpl;
import jogamp.opengl.glu.gl2.nurbs.GLUgl2nurbsImpl;
import com.jogamp.opengl.glu.GLUnurbs;
import com.jogamp.opengl.GL;
import jogamp.opengl.glu.mipmap.Mipmap;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.DoubleBuffer;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.common.nio.Buffers;
import java.nio.Buffer;
import jogamp.opengl.gl2.ProjectDouble;
import com.jogamp.opengl.glu.GLU;

public class GLUgl2 extends GLU
{
    private static boolean useJavaMipmapCode;
    private final ProjectDouble project;
    private static GLUgl2ProcAddressTable gluProcAddressTable;
    
    private int gluBuild1DMipmapLevelsC(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_gluBuild1DMipmapLevels = getGLUProcAddressTable()._addressof_gluBuild1DMipmapLevels;
        if (addressof_gluBuild1DMipmapLevels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "gluBuild1DMipmapLevels"));
        }
        return this.dispatch_gluBuild1DMipmapLevelsC1(n, n2, n3, n4, n5, n6, n7, n8, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_gluBuild1DMipmapLevels);
    }
    
    private native int dispatch_gluBuild1DMipmapLevelsC1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    private int gluBuild1DMipmapsC(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_gluBuild1DMipmaps = getGLUProcAddressTable()._addressof_gluBuild1DMipmaps;
        if (addressof_gluBuild1DMipmaps == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "gluBuild1DMipmaps"));
        }
        return this.dispatch_gluBuild1DMipmapsC1(n, n2, n3, n4, n5, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_gluBuild1DMipmaps);
    }
    
    private native int dispatch_gluBuild1DMipmapsC1(final int p0, final int p1, final int p2, final int p3, final int p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    private int gluBuild2DMipmapLevelsC(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_gluBuild2DMipmapLevels = getGLUProcAddressTable()._addressof_gluBuild2DMipmapLevels;
        if (addressof_gluBuild2DMipmapLevels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "gluBuild2DMipmapLevels"));
        }
        return this.dispatch_gluBuild2DMipmapLevelsC1(n, n2, n3, n4, n5, n6, n7, n8, n9, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_gluBuild2DMipmapLevels);
    }
    
    private native int dispatch_gluBuild2DMipmapLevelsC1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Object p9, final int p10, final boolean p11, final long p12);
    
    private int gluBuild2DMipmapsC(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_gluBuild2DMipmaps = getGLUProcAddressTable()._addressof_gluBuild2DMipmaps;
        if (addressof_gluBuild2DMipmaps == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "gluBuild2DMipmaps"));
        }
        return this.dispatch_gluBuild2DMipmapsC1(n, n2, n3, n4, n5, n6, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_gluBuild2DMipmaps);
    }
    
    private native int dispatch_gluBuild2DMipmapsC1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Object p6, final int p7, final boolean p8, final long p9);
    
    private int gluBuild3DMipmapLevelsC(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_gluBuild3DMipmapLevels = getGLUProcAddressTable()._addressof_gluBuild3DMipmapLevels;
        if (addressof_gluBuild3DMipmapLevels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "gluBuild3DMipmapLevels"));
        }
        return this.dispatch_gluBuild3DMipmapLevelsC1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_gluBuild3DMipmapLevels);
    }
    
    private native int dispatch_gluBuild3DMipmapLevelsC1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Object p10, final int p11, final boolean p12, final long p13);
    
    private int gluBuild3DMipmapsC(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_gluBuild3DMipmaps = getGLUProcAddressTable()._addressof_gluBuild3DMipmaps;
        if (addressof_gluBuild3DMipmaps == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "gluBuild3DMipmaps"));
        }
        return this.dispatch_gluBuild3DMipmapsC1(n, n2, n3, n4, n5, n6, n7, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_gluBuild3DMipmaps);
    }
    
    private native int dispatch_gluBuild3DMipmapsC1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Object p7, final int p8, final boolean p9, final long p10);
    
    private int gluScaleImageC(final int n, final int n2, final int n3, final int n4, final Buffer buffer, final int n5, final int n6, final int n7, final Buffer buffer2) {
        final boolean direct = Buffers.isDirect(buffer);
        final boolean direct2 = Buffers.isDirect(buffer2);
        final long addressof_gluScaleImage = getGLUProcAddressTable()._addressof_gluScaleImage;
        if (addressof_gluScaleImage == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "gluScaleImage"));
        }
        return this.dispatch_gluScaleImageC1(n, n2, n3, n4, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n5, n6, n7, direct2 ? buffer2 : Buffers.getArray(buffer2), direct2 ? Buffers.getDirectBufferByteOffset(buffer2) : Buffers.getIndirectBufferByteOffset(buffer2), direct2, addressof_gluScaleImage);
    }
    
    private native int dispatch_gluScaleImageC1(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final boolean p6, final int p7, final int p8, final int p9, final Object p10, final int p11, final boolean p12, final long p13);
    
    public GLUgl2() {
        this.project = new ProjectDouble();
    }
    
    public static final GL2 getCurrentGL2() throws GLException {
        final GLContext current = GLContext.getCurrent();
        if (current == null) {
            throw new GLException("No OpenGL context current on this thread");
        }
        return current.getGL().getGL2();
    }
    
    @Override
    public final boolean isFunctionAvailable(final String s) {
        return GLUgl2.useJavaMipmapCode || GLUgl2.gluProcAddressTable.getAddressFor(s) != 0L;
    }
    
    @Override
    public final void gluOrtho2D(final float n, final float n2, final float n3, final float n4) {
        this.project.gluOrtho2D(getCurrentGL2(), n, n2, n3, n4);
    }
    
    @Override
    public final void gluOrtho2D(final double n, final double n2, final double n3, final double n4) {
        this.project.gluOrtho2D(getCurrentGL2(), n, n2, n3, n4);
    }
    
    @Override
    public final void gluPerspective(final float n, final float n2, final float n3, final float n4) {
        this.project.gluPerspective(getCurrentGL2(), n, n2, n3, n4);
    }
    
    @Override
    public final void gluPerspective(final double n, final double n2, final double n3, final double n4) {
        this.project.gluPerspective(getCurrentGL2(), n, n2, n3, n4);
    }
    
    @Override
    public final void gluLookAt(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        this.project.gluLookAt(getCurrentGL2(), n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    @Override
    public final void gluLookAt(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9) {
        this.project.gluLookAt(getCurrentGL2(), n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    @Override
    public final boolean gluProject(final double n, final double n2, final double n3, final double[] array, final int n4, final double[] array2, final int n5, final int[] array3, final int n6, final double[] array4, final int n7) {
        return this.project.gluProject(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
    }
    
    public final boolean gluProject(final double n, final double n2, final double n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer3) {
        return this.project.gluProject(n, n2, n3, doubleBuffer, doubleBuffer2, intBuffer, doubleBuffer3);
    }
    
    @Override
    public final boolean gluUnProject(final double n, final double n2, final double n3, final double[] array, final int n4, final double[] array2, final int n5, final int[] array3, final int n6, final double[] array4, final int n7) {
        return this.project.gluUnProject(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
    }
    
    public final boolean gluUnProject(final double n, final double n2, final double n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer3) {
        return this.project.gluUnProject(n, n2, n3, doubleBuffer, doubleBuffer2, intBuffer, doubleBuffer3);
    }
    
    @Override
    public final boolean gluUnProject4(final double n, final double n2, final double n3, final double n4, final double[] array, final int n5, final double[] array2, final int n6, final int[] array3, final int n7, final double n8, final double n9, final double[] array4, final int n10) {
        return this.project.gluUnProject4(n, n2, n3, n4, array, n5, array2, n6, array3, n7, n8, n9, array4, n10);
    }
    
    public final boolean gluUnProject4(final double n, final double n2, final double n3, final double n4, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final IntBuffer intBuffer, final double n5, final double n6, final DoubleBuffer doubleBuffer3) {
        return this.project.gluUnProject4(n, n2, n3, n4, doubleBuffer, doubleBuffer2, intBuffer, n5, n6, doubleBuffer3);
    }
    
    @Override
    public final void gluPickMatrix(final double n, final double n2, final double n3, final double n4, final int[] array, final int n5) {
        this.project.gluPickMatrix(getCurrentGL2(), n, n2, n3, n4, array, n5);
    }
    
    @Override
    public final void gluPickMatrix(final double n, final double n2, final double n3, final double n4, final IntBuffer intBuffer) {
        this.project.gluPickMatrix(getCurrentGL2(), n, n2, n3, n4, intBuffer);
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
    
    private final int gluScaleImageJava(final int n, final int n2, final int n3, final int n4, final Buffer buffer, final int n5, final int n6, final int n7, final Buffer buffer2) {
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
        final int gluScaleImage = Mipmap.gluScaleImage(getCurrentGL2(), n, n2, n3, n4, copyToByteBuffer, n5, n6, n7, byteBuffer);
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
    
    private final int gluBuild1DMipmapLevelsJava(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        return Mipmap.gluBuild1DMipmapLevels(getCurrentGL2(), n, n2, n3, n4, n5, n6, n7, n8, this.copyToByteBuffer(buffer));
    }
    
    private final int gluBuild1DMipmapsJava(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer) {
        return Mipmap.gluBuild1DMipmaps(getCurrentGL2(), n, n2, n3, n4, n5, this.copyToByteBuffer(buffer));
    }
    
    private final int gluBuild2DMipmapLevelsJava(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Buffer buffer) {
        return Mipmap.gluBuild2DMipmapLevels(getCurrentGL2(), n, n2, n3, n4, n5, n6, n7, n8, n9, this.copyToByteBuffer(buffer));
    }
    
    private final int gluBuild2DMipmapsJava(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        return Mipmap.gluBuild2DMipmaps(getCurrentGL2(), n, n2, n3, n4, n5, n6, this.copyToByteBuffer(buffer));
    }
    
    private final int gluBuild3DMipmapLevelsJava(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        return Mipmap.gluBuild3DMipmapLevels(getCurrentGL2(), n, n2, n3, n4, n5, n6, n7, n8, n9, n10, this.copyToByteBuffer(buffer));
    }
    
    private final int gluBuild3DMipmapsJava(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        return Mipmap.gluBuild3DMipmaps(getCurrentGL2(), n, n2, n3, n4, n5, n6, n7, this.copyToByteBuffer(buffer));
    }
    
    @Override
    public final int gluBuild1DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        if (GLUgl2.useJavaMipmapCode) {
            return this.gluBuild1DMipmapLevelsJava(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        }
        return this.gluBuild1DMipmapLevelsC(n, n2, n3, n4, n5, n6, n7, n8, buffer);
    }
    
    @Override
    public final int gluBuild1DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer) {
        if (GLUgl2.useJavaMipmapCode) {
            return this.gluBuild1DMipmapsJava(n, n2, n3, n4, n5, buffer);
        }
        return this.gluBuild1DMipmapsC(n, n2, n3, n4, n5, buffer);
    }
    
    @Override
    public final int gluBuild2DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Buffer buffer) {
        if (GLUgl2.useJavaMipmapCode) {
            return this.gluBuild2DMipmapLevelsJava(n, n2, n3, n4, n5, n6, n7, n8, n9, buffer);
        }
        return this.gluBuild2DMipmapLevelsC(n, n2, n3, n4, n5, n6, n7, n8, n9, buffer);
    }
    
    @Override
    public final int gluBuild2DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        if (GLUgl2.useJavaMipmapCode) {
            return this.gluBuild2DMipmapsJava(n, n2, n3, n4, n5, n6, buffer);
        }
        return this.gluBuild2DMipmapsC(n, n2, n3, n4, n5, n6, buffer);
    }
    
    @Override
    public final int gluBuild3DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        if (GLUgl2.useJavaMipmapCode) {
            return this.gluBuild3DMipmapLevelsJava(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        }
        return this.gluBuild3DMipmapLevelsC(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
    }
    
    @Override
    public final int gluBuild3DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        if (GLUgl2.useJavaMipmapCode) {
            return this.gluBuild3DMipmapsJava(n, n2, n3, n4, n5, n6, n7, buffer);
        }
        return this.gluBuild3DMipmapsC(n, n2, n3, n4, n5, n6, n7, buffer);
    }
    
    @Override
    public final int gluScaleImage(final int n, final int n2, final int n3, final int n4, final Buffer buffer, final int n5, final int n6, final int n7, final Buffer buffer2) {
        if (GLUgl2.useJavaMipmapCode) {
            return this.gluScaleImageJava(n, n2, n3, n4, buffer, n5, n6, n7, buffer2);
        }
        return this.gluScaleImageC(n, n2, n3, n4, buffer, n5, n6, n7, buffer2);
    }
    
    public final void gluNurbsProperty(final GLUnurbs glUnurbs, final int n, final float n2) {
    }
    
    public final GLUnurbs gluNewNurbsRenderer() {
        return new GLUgl2nurbsImpl();
    }
    
    public final void gluBeginCurve(final GLUnurbs glUnurbs) {
        ((GLUgl2nurbsImpl)glUnurbs).bgncurve();
    }
    
    public final void gluBeginSurface(final GLUnurbs glUnurbs) {
        ((GLUgl2nurbsImpl)glUnurbs).bgnsurface();
    }
    
    public final void gluEndSurface(final GLUnurbs glUnurbs) {
        ((GLUgl2nurbsImpl)glUnurbs).endsurface();
    }
    
    public final void gluNurbsSurface(final GLUnurbs glUnurbs, final int n, final float[] array, final int n2, final float[] array2, final int n3, final int n4, final float[] array3, final int n5, final int n6, final int n7) {
        ((GLUgl2nurbsImpl)glUnurbs).nurbssurface(n, array, n2, array2, n3, n4, array3, n5, n6, n7);
    }
    
    public final void gluNurbsCurve(final GLUnurbs glUnurbs, final int n, final float[] array, final int n2, final float[] array2, final int n3, final int n4) {
        ((GLUgl2nurbsImpl)glUnurbs).nurbscurve(n, array, n2, array2, n3, n4);
    }
    
    public final void gluEndCurve(final GLUnurbs glUnurbs) {
        ((GLUgl2nurbsImpl)glUnurbs).endcurve();
    }
    
    private static final GLUgl2ProcAddressTable getGLUProcAddressTable() {
        if (GLUgl2.gluProcAddressTable == null) {
            final GLContext current = GLContext.getCurrent();
            if (current == null) {
                throw new GLException("No OpenGL context current on this thread");
            }
            final GLDynamicLookupHelper glDynamicLookupHelper = ((GLContextImpl)current).getGLDynamicLookupHelper();
            glDynamicLookupHelper.loadGLULibrary();
            final GLUgl2ProcAddressTable gluProcAddressTable = new GLUgl2ProcAddressTable(new GLProcAddressResolver());
            gluProcAddressTable.reset(glDynamicLookupHelper);
            GLUgl2.gluProcAddressTable = gluProcAddressTable;
        }
        return GLUgl2.gluProcAddressTable;
    }
    
    static {
        GLUgl2.useJavaMipmapCode = true;
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            @Override
            public Object run() {
                final String property = System.getProperty("jogl.glu.nojava");
                if (property != null && !property.toLowerCase().equals("false")) {
                    GLUgl2.useJavaMipmapCode = false;
                }
                return null;
            }
        });
    }
}
