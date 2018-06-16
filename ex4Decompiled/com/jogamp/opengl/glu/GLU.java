// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.glu;

import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.glsl.ShaderState;
import jogamp.opengl.glu.GLUquadricImpl;
import jogamp.opengl.glu.tessellator.GLUtessellatorImpl;
import com.jogamp.common.util.ReflectionUtil;
import jogamp.opengl.glu.registry.Registry;
import jogamp.opengl.glu.error.Error;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import jogamp.opengl.ProjectFloat;

public class GLU
{
    private static final Class gl2Class;
    private static final Class gl2es1Class;
    protected static boolean availableGLUtessellatorImpl;
    protected static boolean checkedGLUtessellatorImpl;
    public static final int GLU_FALSE = 0;
    public static final int GLU_TRUE = 1;
    public static final int GLU_VERSION = 100800;
    public static final int GLU_EXTENSIONS = 100801;
    public static final String versionString = "1.3";
    public static final String extensionString = "GLU_EXT_nurbs_tessellator GLU_EXT_object_space_tess ";
    public static final int GLU_INVALID_ENUM = 100900;
    public static final int GLU_INVALID_VALUE = 100901;
    public static final int GLU_OUT_OF_MEMORY = 100902;
    public static final int GLU_INVALID_OPERATION = 100904;
    public static final int GLU_POINT = 100010;
    public static final int GLU_LINE = 100011;
    public static final int GLU_FILL = 100012;
    public static final int GLU_SILHOUETTE = 100013;
    public static final int GLU_SMOOTH = 100000;
    public static final int GLU_FLAT = 100001;
    public static final int GLU_NONE = 100002;
    public static final int GLU_OUTSIDE = 100020;
    public static final int GLU_INSIDE = 100021;
    public static final int GLU_ERROR = 100103;
    public static final int GLU_TESS_BEGIN = 100100;
    public static final int GLU_BEGIN = 100100;
    public static final int GLU_TESS_VERTEX = 100101;
    public static final int GLU_VERTEX = 100101;
    public static final int GLU_TESS_END = 100102;
    public static final int GLU_END = 100102;
    public static final int GLU_TESS_ERROR = 100103;
    public static final int GLU_TESS_EDGE_FLAG = 100104;
    public static final int GLU_EDGE_FLAG = 100104;
    public static final int GLU_TESS_COMBINE = 100105;
    public static final int GLU_TESS_BEGIN_DATA = 100106;
    public static final int GLU_TESS_VERTEX_DATA = 100107;
    public static final int GLU_TESS_END_DATA = 100108;
    public static final int GLU_TESS_ERROR_DATA = 100109;
    public static final int GLU_TESS_EDGE_FLAG_DATA = 100110;
    public static final int GLU_TESS_COMBINE_DATA = 100111;
    public static final int GLU_CW = 100120;
    public static final int GLU_CCW = 100121;
    public static final int GLU_INTERIOR = 100122;
    public static final int GLU_EXTERIOR = 100123;
    public static final int GLU_UNKNOWN = 100124;
    public static final int GLU_TESS_WINDING_RULE = 100140;
    public static final int GLU_TESS_BOUNDARY_ONLY = 100141;
    public static final int GLU_TESS_TOLERANCE = 100142;
    public static final int GLU_TESS_AVOID_DEGENERATE_TRIANGLES = 100149;
    public static final int GLU_TESS_ERROR1 = 100151;
    public static final int GLU_TESS_ERROR2 = 100152;
    public static final int GLU_TESS_ERROR3 = 100153;
    public static final int GLU_TESS_ERROR4 = 100154;
    public static final int GLU_TESS_ERROR5 = 100155;
    public static final int GLU_TESS_ERROR6 = 100156;
    public static final int GLU_TESS_ERROR7 = 100157;
    public static final int GLU_TESS_ERROR8 = 100158;
    public static final int GLU_TESS_MISSING_BEGIN_POLYGON = 100151;
    public static final int GLU_TESS_MISSING_BEGIN_CONTOUR = 100152;
    public static final int GLU_TESS_MISSING_END_POLYGON = 100153;
    public static final int GLU_TESS_MISSING_END_CONTOUR = 100154;
    public static final int GLU_TESS_COORD_TOO_LARGE = 100155;
    public static final int GLU_TESS_NEED_COMBINE_CALLBACK = 100156;
    public static final int GLU_TESS_WINDING_ODD = 100130;
    public static final int GLU_TESS_WINDING_NONZERO = 100131;
    public static final int GLU_TESS_WINDING_POSITIVE = 100132;
    public static final int GLU_TESS_WINDING_NEGATIVE = 100133;
    public static final int GLU_TESS_WINDING_ABS_GEQ_TWO = 100134;
    public static final double GLU_TESS_MAX_COORD = 1.0E150;
    protected static boolean availableGLUquadricImpl;
    protected static boolean checkedGLUquadricImpl;
    protected static volatile Object syncObject;
    private final ProjectFloat project;
    
    public boolean isFunctionAvailable(final String s) {
        return true;
    }
    
    public static final GLU createGLU() throws GLException {
        return createGLU(getCurrentGL());
    }
    
    public static final GLU createGLU(final GL gl) throws GLException {
        try {
            Class<GLU> clazz;
            if (gl.isGL2() && null != GLU.gl2Class) {
                clazz = (Class<GLU>)GLU.gl2Class;
            }
            else if (gl.isGL2ES1() && null != GLU.gl2es1Class) {
                clazz = (Class<GLU>)GLU.gl2es1Class;
            }
            else {
                clazz = GLU.class;
            }
            return clazz.newInstance();
        }
        catch (Exception ex) {
            throw new GLException(ex);
        }
    }
    
    public GLU() {
        this.project = new ProjectFloat();
    }
    
    public static final GL getCurrentGL() throws GLException {
        final GLContext current = GLContext.getCurrent();
        if (current == null) {
            throw new GLException("No OpenGL context current on this thread");
        }
        return current.getGL();
    }
    
    public final String gluErrorString(final int n) {
        return Error.gluErrorString(n);
    }
    
    public final boolean gluCheckExtension(final String s, final String s2) {
        return Registry.gluCheckExtension(s, s2);
    }
    
    public final String gluGetString(final int n) {
        return Registry.gluGetString(n);
    }
    
    protected static final void validateGLUtessellatorImpl() {
        if (!GLU.checkedGLUtessellatorImpl) {
            GLU.availableGLUtessellatorImpl = ReflectionUtil.isClassAvailable("jogamp.opengl.glu.tessellator.GLUtessellatorImpl", GLU.class.getClassLoader());
            GLU.checkedGLUtessellatorImpl = true;
        }
        if (!GLU.availableGLUtessellatorImpl) {
            throw new GLException("GLUtessellator not available (GLUtessellatorImpl)");
        }
    }
    
    public static final GLUtessellator gluNewTess() {
        validateGLUtessellatorImpl();
        return GLUtessellatorImpl.gluNewTess();
    }
    
    public static final void gluDeleteTess(final GLUtessellator glUtessellator) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluDeleteTess();
    }
    
    public static final void gluTessProperty(final GLUtessellator glUtessellator, final int n, final double n2) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluTessProperty(n, n2);
    }
    
    public static final void gluGetTessProperty(final GLUtessellator glUtessellator, final int n, final double[] array, final int n2) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluGetTessProperty(n, array, n2);
    }
    
    public static final void gluTessNormal(final GLUtessellator glUtessellator, final double n, final double n2, final double n3) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluTessNormal(n, n2, n3);
    }
    
    public static final void gluTessCallback(final GLUtessellator glUtessellator, final int n, final GLUtessellatorCallback glUtessellatorCallback) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluTessCallback(n, glUtessellatorCallback);
    }
    
    public static final void gluTessVertex(final GLUtessellator glUtessellator, final double[] array, final int n, final Object o) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluTessVertex(array, n, o);
    }
    
    public static final void gluTessBeginPolygon(final GLUtessellator glUtessellator, final Object o) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluTessBeginPolygon(o);
    }
    
    public static final void gluTessBeginContour(final GLUtessellator glUtessellator) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluTessBeginContour();
    }
    
    public static final void gluTessEndContour(final GLUtessellator glUtessellator) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluTessEndContour();
    }
    
    public static final void gluTessEndPolygon(final GLUtessellator glUtessellator) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluTessEndPolygon();
    }
    
    public static final void gluBeginPolygon(final GLUtessellator glUtessellator) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluBeginPolygon();
    }
    
    public static final void gluNextContour(final GLUtessellator glUtessellator, final int n) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluNextContour(n);
    }
    
    public static final void gluEndPolygon(final GLUtessellator glUtessellator) {
        validateGLUtessellatorImpl();
        ((GLUtessellatorImpl)glUtessellator).gluEndPolygon();
    }
    
    protected static final void validateGLUquadricImpl() {
        if (!GLU.checkedGLUquadricImpl) {
            synchronized (GLU.syncObject) {
                if (!GLU.checkedGLUquadricImpl) {
                    GLU.availableGLUquadricImpl = ReflectionUtil.isClassAvailable("jogamp.opengl.glu.GLUquadricImpl", GLU.class.getClassLoader());
                    GLU.checkedGLUquadricImpl = true;
                }
            }
        }
        if (!GLU.availableGLUquadricImpl) {
            throw new GLException("GLUquadric not available (GLUquadricImpl)");
        }
    }
    
    public final void gluCylinder(final GLUquadric glUquadric, final double n, final double n2, final double n3, final int n4, final int n5) {
        validateGLUquadricImpl();
        ((GLUquadricImpl)glUquadric).drawCylinder(getCurrentGL(), (float)n, (float)n2, (float)n3, n4, n5);
    }
    
    public final void gluDeleteQuadric(final GLUquadric glUquadric) {
        validateGLUquadricImpl();
    }
    
    public final void gluDisk(final GLUquadric glUquadric, final double n, final double n2, final int n3, final int n4) {
        validateGLUquadricImpl();
        ((GLUquadricImpl)glUquadric).drawDisk(getCurrentGL(), (float)n, (float)n2, n3, n4);
    }
    
    public final GLUquadric gluNewQuadric() {
        return this.gluNewQuadric(false, null, 0);
    }
    
    public final GLUquadric gluNewQuadric(final boolean b, final ShaderState shaderState) {
        return this.gluNewQuadric(b, shaderState, 0);
    }
    
    public final GLUquadric gluNewQuadric(final boolean b, final int n) {
        return this.gluNewQuadric(b, null, n);
    }
    
    private final GLUquadric gluNewQuadric(final boolean b, final ShaderState shaderState, final int n) {
        final GL currentGL = getCurrentGL();
        if (b && !currentGL.isGL2ES2()) {
            throw new GLException("GLUquadric GLSL implementation not supported for profile: " + currentGL);
        }
        validateGLUquadricImpl();
        return new GLUquadricImpl(currentGL, b, shaderState, n);
    }
    
    public final void gluPartialDisk(final GLUquadric glUquadric, final double n, final double n2, final int n3, final int n4, final double n5, final double n6) {
        validateGLUquadricImpl();
        ((GLUquadricImpl)glUquadric).drawPartialDisk(getCurrentGL(), (float)n, (float)n2, n3, n4, (float)n5, (float)n6);
    }
    
    public final void gluQuadricDrawStyle(final GLUquadric glUquadric, final int drawStyle) {
        validateGLUquadricImpl();
        ((GLUquadricImpl)glUquadric).setDrawStyle(drawStyle);
    }
    
    public final void gluQuadricNormals(final GLUquadric glUquadric, final int normals) {
        validateGLUquadricImpl();
        ((GLUquadricImpl)glUquadric).setNormals(normals);
    }
    
    public final void gluQuadricOrientation(final GLUquadric glUquadric, final int orientation) {
        validateGLUquadricImpl();
        ((GLUquadricImpl)glUquadric).setOrientation(orientation);
    }
    
    public final void gluQuadricTexture(final GLUquadric glUquadric, final boolean textureFlag) {
        validateGLUquadricImpl();
        ((GLUquadricImpl)glUquadric).setTextureFlag(textureFlag);
    }
    
    public final void gluSphere(final GLUquadric glUquadric, final double n, final int n2, final int n3) {
        validateGLUquadricImpl();
        ((GLUquadricImpl)glUquadric).drawSphere(getCurrentGL(), (float)n, n2, n3);
    }
    
    public void gluOrtho2D(final float n, final float n2, final float n3, final float n4) {
        this.project.gluOrtho2D(getCurrentGL().getGL2ES1(), n, n2, n3, n4);
    }
    
    public void gluPerspective(final float n, final float n2, final float n3, final float n4) {
        this.project.gluPerspective(getCurrentGL().getGL2ES1(), n, n2, n3, n4);
    }
    
    public void gluLookAt(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        this.project.gluLookAt(getCurrentGL().getGL2ES1(), n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    public boolean gluProject(final float n, final float n2, final float n3, final float[] array, final int n4, final float[] array2, final int n5, final int[] array3, final int n6, final float[] array4, final int n7) {
        return this.project.gluProject(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
    }
    
    public boolean gluProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final FloatBuffer floatBuffer3) {
        return this.project.gluProject(n, n2, n3, floatBuffer, floatBuffer2, intBuffer, floatBuffer3);
    }
    
    public boolean gluUnProject(final float n, final float n2, final float n3, final float[] array, final int n4, final float[] array2, final int n5, final int[] array3, final int n6, final float[] array4, final int n7) {
        return this.project.gluUnProject(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
    }
    
    public boolean gluUnProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final FloatBuffer floatBuffer3) {
        return this.project.gluUnProject(n, n2, n3, floatBuffer, floatBuffer2, intBuffer, floatBuffer3);
    }
    
    public boolean gluUnProject4(final float n, final float n2, final float n3, final float n4, final float[] array, final int n5, final float[] array2, final int n6, final int[] array3, final int n7, final float n8, final float n9, final float[] array4, final int n10) {
        return this.project.gluUnProject4(n, n2, n3, n4, array, n5, array2, n6, array3, n7, n8, n9, array4, n10);
    }
    
    public boolean gluUnProject4(final float n, final float n2, final float n3, final float n4, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final float n5, final float n6, final FloatBuffer floatBuffer3) {
        return this.project.gluUnProject4(n, n2, n3, n4, floatBuffer, floatBuffer2, intBuffer, n5, n6, floatBuffer3);
    }
    
    public void gluPickMatrix(final float n, final float n2, final float n3, final float n4, final int[] array, final int n5) {
        this.project.gluPickMatrix(getCurrentGL().getGL2ES1(), n, n2, n3, n4, array, n5);
    }
    
    public void gluPickMatrix(final float n, final float n2, final float n3, final float n4, final IntBuffer intBuffer) {
        this.project.gluPickMatrix(getCurrentGL().getGL2ES1(), n, n2, n3, n4, intBuffer);
    }
    
    public void gluOrtho2D(final double n, final double n2, final double n3, final double n4) {
        this.project.gluOrtho2D(getCurrentGL().getGL2ES1(), (float)n, (float)n2, (float)n3, (float)n4);
    }
    
    public void gluPerspective(final double n, final double n2, final double n3, final double n4) {
        this.project.gluPerspective(getCurrentGL().getGL2ES1(), (float)n, (float)n2, (float)n3, (float)n4);
    }
    
    public void gluLookAt(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9) {
        this.project.gluLookAt(getCurrentGL().getGL2ES1(), (float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6, (float)n7, (float)n8, (float)n9);
    }
    
    public boolean gluProject(final double n, final double n2, final double n3, final double[] array, final int n4, final double[] array2, final int n5, final int[] array3, final int n6, final double[] array4, final int n7) {
        final float[] floatArray = Buffers.getFloatArray(array, n4, null, 0, -1);
        final float[] floatArray2 = Buffers.getFloatArray(array2, n5, null, 0, -1);
        final float[] floatArray3 = Buffers.getFloatArray(array4, n7, null, 0, -1);
        final boolean gluProject = this.project.gluProject((float)n, (float)n2, (float)n3, floatArray, 0, floatArray2, 0, array3, n6, floatArray3, 0);
        if (gluProject) {
            Buffers.getDoubleArray(floatArray, 0, array, n4, -1);
            Buffers.getDoubleArray(floatArray2, 0, array2, n5, -1);
            Buffers.getDoubleArray(floatArray3, 0, array4, n7, -1);
        }
        return gluProject;
    }
    
    public boolean gluUnProject(final double n, final double n2, final double n3, final double[] array, final int n4, final double[] array2, final int n5, final int[] array3, final int n6, final double[] array4, final int n7) {
        final float[] floatArray = Buffers.getFloatArray(array, n4, null, 0, -1);
        final float[] floatArray2 = Buffers.getFloatArray(array2, n5, null, 0, -1);
        final float[] floatArray3 = Buffers.getFloatArray(array4, n7, null, 0, -1);
        final boolean gluUnProject = this.project.gluUnProject((float)n, (float)n2, (float)n3, floatArray, 0, floatArray2, 0, array3, n6, floatArray3, 0);
        if (gluUnProject) {
            Buffers.getDoubleArray(floatArray, 0, array, n4, -1);
            Buffers.getDoubleArray(floatArray2, 0, array2, n5, -1);
            Buffers.getDoubleArray(floatArray3, 0, array4, n7, -1);
        }
        return gluUnProject;
    }
    
    public boolean gluUnProject4(final double n, final double n2, final double n3, final double n4, final double[] array, final int n5, final double[] array2, final int n6, final int[] array3, final int n7, final double n8, final double n9, final double[] array4, final int n10) {
        final float[] floatArray = Buffers.getFloatArray(array, n5, null, 0, -1);
        final float[] floatArray2 = Buffers.getFloatArray(array2, n6, null, 0, -1);
        final float[] floatArray3 = Buffers.getFloatArray(array4, n10, null, 0, -1);
        final boolean gluUnProject4 = this.project.gluUnProject4((float)n, (float)n2, (float)n3, (float)n4, floatArray, 0, floatArray2, 0, array3, n7, (float)n8, (float)n9, floatArray3, 0);
        if (gluUnProject4) {
            Buffers.getDoubleArray(floatArray, 0, array, n5, -1);
            Buffers.getDoubleArray(floatArray2, 0, array2, n6, -1);
            Buffers.getDoubleArray(floatArray3, 0, array4, n10, -1);
        }
        return gluUnProject4;
    }
    
    public void gluPickMatrix(final double n, final double n2, final double n3, final double n4, final int[] array, final int n5) {
        this.project.gluPickMatrix(getCurrentGL().getGL2ES1(), (float)n, (float)n2, (float)n3, (float)n4, array, n5);
    }
    
    public void gluPickMatrix(final double n, final double n2, final double n3, final double n4, final IntBuffer intBuffer) {
        this.project.gluPickMatrix(getCurrentGL().getGL2ES1(), (float)n, (float)n2, (float)n3, (float)n4, intBuffer);
    }
    
    public int gluScaleImage(final int n, final int n2, final int n3, final int n4, final Buffer buffer, final int n5, final int n6, final int n7, final Buffer buffer2) {
        throw new GLException("not implemented");
    }
    
    public int gluBuild1DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        throw new GLException("not implemented");
    }
    
    public int gluBuild1DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer) {
        throw new GLException("not implemented");
    }
    
    public int gluBuild2DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Buffer buffer) {
        throw new GLException("not implemented");
    }
    
    public int gluBuild2DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        throw new GLException("not implemented");
    }
    
    public int gluBuild3DMipmapLevels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        throw new GLException("not implemented");
    }
    
    public int gluBuild3DMipmaps(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        throw new GLException("not implemented");
    }
    
    static {
        Class<?> forName = null;
        Class<?> forName2 = null;
        try {
            final ClassLoader classLoader = GLU.class.getClassLoader();
            forName = Class.forName("com.jogamp.opengl.glu.gl2.GLUgl2", false, classLoader);
            forName2 = Class.forName("com.jogamp.opengl.glu.gl2es1.GLUgl2es1", false, classLoader);
        }
        catch (Throwable t) {}
        gl2Class = forName;
        gl2es1Class = forName2;
        GLU.availableGLUtessellatorImpl = false;
        GLU.checkedGLUtessellatorImpl = false;
        GLU.availableGLUquadricImpl = false;
        GLU.checkedGLUquadricImpl = false;
        GLU.syncObject = new Object();
    }
}
