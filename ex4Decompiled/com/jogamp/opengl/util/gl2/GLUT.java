// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.gl2;

import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.glu.gl2.GLUgl2;

public class GLUT
{
    public static final int STROKE_ROMAN = 0;
    public static final int STROKE_MONO_ROMAN = 1;
    public static final int BITMAP_9_BY_15 = 2;
    public static final int BITMAP_8_BY_13 = 3;
    public static final int BITMAP_TIMES_ROMAN_10 = 4;
    public static final int BITMAP_TIMES_ROMAN_24 = 5;
    public static final int BITMAP_HELVETICA_10 = 6;
    public static final int BITMAP_HELVETICA_12 = 7;
    public static final int BITMAP_HELVETICA_18 = 8;
    private final GLUgl2 glu;
    private GLUquadric quadObj;
    private static float[][] boxVertices;
    private static final float[][] boxNormals;
    private static final int[][] boxFaces;
    private float[][] dodec;
    private static final float[][] odata;
    private static final int[][] ondex;
    private static final float X = 0.5257311f;
    private static final float Z = 0.8506508f;
    private static final float[][] idata;
    private static final int[][] index;
    private static final double[][] rdod_r;
    private static final int[][] rdod_v;
    private static final double[][] rdod_n;
    private static final float T = 1.7320508f;
    private static final float[][] tdata;
    private static final int[][] tndex;
    private static final int[][] teapotPatchData;
    private static final float[][] teapotCPData;
    private static final float[] teapotTex;
    private static final BitmapFontRec[] bitmapFonts;
    private static final StrokeFontRec[] strokeFonts;
    
    public GLUT() {
        this.glu = new GLUgl2();
    }
    
    public void glutWireSphere(final double n, final int n2, final int n3) {
        this.quadObjInit(this.glu);
        this.glu.gluQuadricDrawStyle(this.quadObj, 100011);
        this.glu.gluQuadricNormals(this.quadObj, 100000);
        this.glu.gluSphere(this.quadObj, n, n2, n3);
    }
    
    public void glutSolidSphere(final double n, final int n2, final int n3) {
        this.quadObjInit(this.glu);
        this.glu.gluQuadricDrawStyle(this.quadObj, 100012);
        this.glu.gluQuadricNormals(this.quadObj, 100000);
        this.glu.gluSphere(this.quadObj, n, n2, n3);
    }
    
    public void glutWireCone(final double n, final double n2, final int n3, final int n4) {
        this.quadObjInit(this.glu);
        this.glu.gluQuadricDrawStyle(this.quadObj, 100011);
        this.glu.gluQuadricNormals(this.quadObj, 100000);
        this.glu.gluCylinder(this.quadObj, n, 0.0, n2, n3, n4);
    }
    
    public void glutSolidCone(final double n, final double n2, final int n3, final int n4) {
        this.quadObjInit(this.glu);
        this.glu.gluQuadricDrawStyle(this.quadObj, 100012);
        this.glu.gluQuadricNormals(this.quadObj, 100000);
        this.glu.gluCylinder(this.quadObj, n, 0.0, n2, n3, n4);
    }
    
    public void glutWireCylinder(final double n, final double n2, final int n3, final int n4) {
        this.quadObjInit(this.glu);
        this.glu.gluQuadricDrawStyle(this.quadObj, 100011);
        this.glu.gluQuadricNormals(this.quadObj, 100000);
        this.glu.gluCylinder(this.quadObj, n, n, n2, n3, n4);
    }
    
    public void glutSolidCylinder(final double n, final double n2, final int n3, final int n4) {
        final GL2 currentGL2 = GLUgl2.getCurrentGL2();
        final double[] array = new double[n3];
        final double[] array2 = new double[n3];
        final double n5 = 6.283185307179586 / n3;
        for (int i = 0; i < n3; ++i) {
            final double n6 = i * n5;
            array[i] = Math.cos(n6) * n;
            array2[i] = Math.sin(n6) * n;
        }
        currentGL2.glBegin(6);
        currentGL2.glNormal3d(0.0, 0.0, -1.0);
        currentGL2.glVertex3d(0.0, 0.0, 0.0);
        for (int j = 0; j < n3; ++j) {
            currentGL2.glVertex3d(array[j], array2[j], 0.0);
        }
        currentGL2.glVertex3d(array[0], array2[0], 0.0);
        currentGL2.glEnd();
        currentGL2.glBegin(6);
        currentGL2.glNormal3d(0.0, 0.0, 1.0);
        currentGL2.glVertex3d(0.0, 0.0, n2);
        for (int k = 0; k < n3; ++k) {
            currentGL2.glVertex3d(array[k], array2[k], n2);
        }
        currentGL2.glVertex3d(array[0], array2[0], n2);
        currentGL2.glEnd();
        this.quadObjInit(this.glu);
        this.glu.gluQuadricDrawStyle(this.quadObj, 100012);
        this.glu.gluQuadricNormals(this.quadObj, 100000);
        this.glu.gluCylinder(this.quadObj, n, n, n2, n3, n4);
    }
    
    public void glutWireCube(final float n) {
        this.drawBox(GLUgl2.getCurrentGL2(), n, 2);
    }
    
    public void glutSolidCube(final float n) {
        this.drawBox(GLUgl2.getCurrentGL2(), n, 7);
    }
    
    public void glutWireTorus(final double n, final double n2, final int n3, final int n4) {
        final GL2 currentGL2 = GLUgl2.getCurrentGL2();
        currentGL2.glPushAttrib(8);
        currentGL2.glPolygonMode(1032, 6913);
        doughnut(currentGL2, n, n2, n3, n4);
        currentGL2.glPopAttrib();
    }
    
    public void glutSolidTorus(final double n, final double n2, final int n3, final int n4) {
        doughnut(GLUgl2.getCurrentGL2(), n, n2, n3, n4);
    }
    
    public void glutWireDodecahedron() {
        this.dodecahedron(GLUgl2.getCurrentGL2(), 2);
    }
    
    public void glutSolidDodecahedron() {
        this.dodecahedron(GLUgl2.getCurrentGL2(), 6);
    }
    
    public void glutWireOctahedron() {
        octahedron(GLUgl2.getCurrentGL2(), 2);
    }
    
    public void glutSolidOctahedron() {
        octahedron(GLUgl2.getCurrentGL2(), 4);
    }
    
    public void glutWireIcosahedron() {
        icosahedron(GLUgl2.getCurrentGL2(), 2);
    }
    
    public void glutSolidIcosahedron() {
        icosahedron(GLUgl2.getCurrentGL2(), 4);
    }
    
    public void glutWireTetrahedron() {
        tetrahedron(GLUgl2.getCurrentGL2(), 2);
    }
    
    public void glutSolidTetrahedron() {
        tetrahedron(GLUgl2.getCurrentGL2(), 4);
    }
    
    public void glutSolidTeapot(final double n) {
        this.glutSolidTeapot(n, true);
    }
    
    public void glutSolidTeapot(final double n, final boolean b) {
        teapot(GLUgl2.getCurrentGL2(), 14, n, 6914, b);
    }
    
    public void glutWireTeapot(final double n) {
        this.glutWireTeapot(n, true);
    }
    
    public void glutWireTeapot(final double n, final boolean b) {
        teapot(GLUgl2.getCurrentGL2(), 10, n, 6913, b);
    }
    
    public void glutBitmapCharacter(final int n, final char c) {
        final GL2 currentGL2 = GLUgl2.getCurrentGL2();
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        final int[] array3 = { 0 };
        final int[] array4 = { 0 };
        final int[] array5 = { 0 };
        final int[] array6 = { 0 };
        beginBitmap(currentGL2, array, array2, array3, array4, array5, array6);
        bitmapCharacterImpl(currentGL2, n, c);
        endBitmap(currentGL2, array, array2, array3, array4, array5, array6);
    }
    
    public void glutBitmapString(final int n, final String s) {
        final GL2 currentGL2 = GLUgl2.getCurrentGL2();
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        final int[] array3 = { 0 };
        final int[] array4 = { 0 };
        final int[] array5 = { 0 };
        final int[] array6 = { 0 };
        beginBitmap(currentGL2, array, array2, array3, array4, array5, array6);
        for (int length = s.length(), i = 0; i < length; ++i) {
            bitmapCharacterImpl(currentGL2, n, s.charAt(i));
        }
        endBitmap(currentGL2, array, array2, array3, array4, array5, array6);
    }
    
    public int glutBitmapWidth(final int n, final char c) {
        final BitmapFontRec bitmapFont = getBitmapFont(n);
        final char c2 = (char)(c & '\uffff');
        if (c2 < bitmapFont.first || c2 >= bitmapFont.first + bitmapFont.num_chars) {
            return 0;
        }
        final BitmapCharRec bitmapCharRec = bitmapFont.ch[c2 - bitmapFont.first];
        if (bitmapCharRec != null) {
            return (int)bitmapCharRec.advance;
        }
        return 0;
    }
    
    public void glutStrokeCharacter(final int n, final char c) {
        final GL2 currentGL2 = GLUgl2.getCurrentGL2();
        final StrokeFontRec strokeFont = getStrokeFont(n);
        final char c2 = (char)(c & '\uffff');
        if (c2 < '\0' || c2 >= strokeFont.num_chars) {
            return;
        }
        final StrokeCharRec strokeCharRec = strokeFont.ch[c2];
        if (strokeCharRec != null) {
            for (int i = 0; i < strokeCharRec.num_strokes; ++i) {
                final StrokeRec strokeRec = strokeCharRec.stroke[i];
                currentGL2.glBegin(3);
                for (int j = 0; j < strokeRec.num_coords; ++j) {
                    final CoordRec coordRec = strokeRec.coord[j];
                    currentGL2.glVertex2f(coordRec.x, coordRec.y);
                }
                currentGL2.glEnd();
            }
            currentGL2.glTranslatef(strokeCharRec.right, 0.0f, 0.0f);
        }
    }
    
    public void glutStrokeString(final int n, final String s) {
        final GL2 currentGL2 = GLUgl2.getCurrentGL2();
        final StrokeFontRec strokeFont = getStrokeFont(n);
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char c = (char)(s.charAt(i) & '\uffff');
            if (c >= '\0') {
                if (c < strokeFont.num_chars) {
                    final StrokeCharRec strokeCharRec = strokeFont.ch[c];
                    if (strokeCharRec != null) {
                        for (int j = 0; j < strokeCharRec.num_strokes; ++j) {
                            final StrokeRec strokeRec = strokeCharRec.stroke[j];
                            currentGL2.glBegin(3);
                            for (int k = 0; k < strokeRec.num_coords; ++k) {
                                final CoordRec coordRec = strokeRec.coord[k];
                                currentGL2.glVertex2f(coordRec.x, coordRec.y);
                            }
                            currentGL2.glEnd();
                        }
                        currentGL2.glTranslatef(strokeCharRec.right, 0.0f, 0.0f);
                    }
                }
            }
        }
    }
    
    public int glutStrokeWidth(final int n, final char c) {
        return (int)this.glutStrokeWidthf(n, c);
    }
    
    public float glutStrokeWidthf(final int n, final char c) {
        final StrokeFontRec strokeFont = getStrokeFont(n);
        final char c2 = (char)(c & '\uffff');
        if (c2 < '\0' || c2 >= strokeFont.num_chars) {
            return 0.0f;
        }
        final StrokeCharRec strokeCharRec = strokeFont.ch[c2];
        if (strokeCharRec != null) {
            return strokeCharRec.right;
        }
        return 0.0f;
    }
    
    public int glutBitmapLength(final int n, final String s) {
        final BitmapFontRec bitmapFont = getBitmapFont(n);
        int n2 = 0;
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char c = (char)(s.charAt(i) & '\uffff');
            if (c >= bitmapFont.first && c < bitmapFont.first + bitmapFont.num_chars) {
                final BitmapCharRec bitmapCharRec = bitmapFont.ch[c - bitmapFont.first];
                if (bitmapCharRec != null) {
                    n2 += (int)bitmapCharRec.advance;
                }
            }
        }
        return n2;
    }
    
    public int glutStrokeLength(final int n, final String s) {
        return (int)this.glutStrokeLengthf(n, s);
    }
    
    public float glutStrokeLengthf(final int n, final String s) {
        final StrokeFontRec strokeFont = getStrokeFont(n);
        float n2 = 0.0f;
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 >= '\0' && char1 < strokeFont.num_chars) {
                final StrokeCharRec strokeCharRec = strokeFont.ch[char1];
                if (strokeCharRec != null) {
                    n2 += strokeCharRec.right;
                }
            }
        }
        return n2;
    }
    
    public void glutWireRhombicDodecahedron() {
        final GL2 currentGL2 = GLUgl2.getCurrentGL2();
        for (int i = 0; i < 12; ++i) {
            currentGL2.glBegin(2);
            currentGL2.glNormal3dv(GLUT.rdod_n[i], 0);
            currentGL2.glVertex3dv(GLUT.rdod_r[GLUT.rdod_v[i][0]], 0);
            currentGL2.glVertex3dv(GLUT.rdod_r[GLUT.rdod_v[i][1]], 0);
            currentGL2.glVertex3dv(GLUT.rdod_r[GLUT.rdod_v[i][2]], 0);
            currentGL2.glVertex3dv(GLUT.rdod_r[GLUT.rdod_v[i][3]], 0);
            currentGL2.glEnd();
        }
    }
    
    public void glutSolidRhombicDodecahedron() {
        final GL2 currentGL2 = GLUgl2.getCurrentGL2();
        currentGL2.glBegin(7);
        for (int i = 0; i < 12; ++i) {
            currentGL2.glNormal3dv(GLUT.rdod_n[i], 0);
            currentGL2.glVertex3dv(GLUT.rdod_r[GLUT.rdod_v[i][0]], 0);
            currentGL2.glVertex3dv(GLUT.rdod_r[GLUT.rdod_v[i][1]], 0);
            currentGL2.glVertex3dv(GLUT.rdod_r[GLUT.rdod_v[i][2]], 0);
            currentGL2.glVertex3dv(GLUT.rdod_r[GLUT.rdod_v[i][3]], 0);
        }
        currentGL2.glEnd();
    }
    
    private void quadObjInit(final GLUgl2 glUgl2) {
        if (this.quadObj == null) {
            this.quadObj = glUgl2.gluNewQuadric();
        }
        if (this.quadObj == null) {
            throw new GLException("Out of memory");
        }
    }
    
    private static void doughnut(final GL2 gl2, final double n, final double n2, final int n3, final int n4) {
        final float n5 = (float)(6.283185307179586 / n4);
        final float n6 = (float)(6.283185307179586 / n3);
        float n7 = 0.0f;
        float n8 = 1.0f;
        float n9 = 0.0f;
        for (int i = n4 - 1; i >= 0; --i) {
            final float n10 = n7 + n5;
            final float n11 = (float)Math.cos(n10);
            final float n12 = (float)Math.sin(n10);
            gl2.glBegin(8);
            float n13 = 0.0f;
            for (int j = n3; j >= 0; --j) {
                n13 += n6;
                final float n14 = (float)Math.cos(n13);
                final float n15 = (float)Math.sin(n13);
                final float n16 = (float)(n2 + n * n14);
                gl2.glNormal3f(n11 * n14, -n12 * n14, n15);
                gl2.glVertex3f(n11 * n16, -n12 * n16, (float)n * n15);
                gl2.glNormal3f(n8 * n14, -n9 * n14, n15);
                gl2.glVertex3f(n8 * n16, -n9 * n16, (float)n * n15);
            }
            gl2.glEnd();
            n7 = n10;
            n8 = n11;
            n9 = n12;
        }
    }
    
    private void drawBox(final GL2 gl2, final float n, final int n2) {
        if (GLUT.boxVertices == null) {
            final float[][] boxVertices = new float[8][];
            for (int i = 0; i < 8; ++i) {
                boxVertices[i] = new float[3];
            }
            final float[] array = boxVertices[0];
            final int n3 = 0;
            final float[] array2 = boxVertices[1];
            final int n4 = 0;
            final float[] array3 = boxVertices[2];
            final int n5 = 0;
            final float[] array4 = boxVertices[3];
            final int n6 = 0;
            final float n7 = -0.5f;
            array3[n5] = (array4[n6] = n7);
            array[n3] = (array2[n4] = n7);
            final float[] array5 = boxVertices[4];
            final int n8 = 0;
            final float[] array6 = boxVertices[5];
            final int n9 = 0;
            final float[] array7 = boxVertices[6];
            final int n10 = 0;
            final float[] array8 = boxVertices[7];
            final int n11 = 0;
            final float n12 = 0.5f;
            array7[n10] = (array8[n11] = n12);
            array5[n8] = (array6[n9] = n12);
            final float[] array9 = boxVertices[0];
            final int n13 = 1;
            final float[] array10 = boxVertices[1];
            final int n14 = 1;
            final float[] array11 = boxVertices[4];
            final int n15 = 1;
            final float[] array12 = boxVertices[5];
            final int n16 = 1;
            final float n17 = -0.5f;
            array11[n15] = (array12[n16] = n17);
            array9[n13] = (array10[n14] = n17);
            final float[] array13 = boxVertices[2];
            final int n18 = 1;
            final float[] array14 = boxVertices[3];
            final int n19 = 1;
            final float[] array15 = boxVertices[6];
            final int n20 = 1;
            final float[] array16 = boxVertices[7];
            final int n21 = 1;
            final float n22 = 0.5f;
            array15[n20] = (array16[n21] = n22);
            array13[n18] = (array14[n19] = n22);
            final float[] array17 = boxVertices[0];
            final int n23 = 2;
            final float[] array18 = boxVertices[3];
            final int n24 = 2;
            final float[] array19 = boxVertices[4];
            final int n25 = 2;
            final float[] array20 = boxVertices[7];
            final int n26 = 2;
            final float n27 = -0.5f;
            array19[n25] = (array20[n26] = n27);
            array17[n23] = (array18[n24] = n27);
            final float[] array21 = boxVertices[1];
            final int n28 = 2;
            final float[] array22 = boxVertices[2];
            final int n29 = 2;
            final float[] array23 = boxVertices[5];
            final int n30 = 2;
            final float[] array24 = boxVertices[6];
            final int n31 = 2;
            final float n32 = 0.5f;
            array23[n30] = (array24[n31] = n32);
            array21[n28] = (array22[n29] = n32);
            GLUT.boxVertices = boxVertices;
        }
        final float[][] boxVertices2 = GLUT.boxVertices;
        final float[][] boxNormals = GLUT.boxNormals;
        final int[][] boxFaces = GLUT.boxFaces;
        for (int j = 5; j >= 0; --j) {
            gl2.glBegin(n2);
            gl2.glNormal3fv(boxNormals[j], 0);
            final float[] array25 = boxVertices2[boxFaces[j][0]];
            gl2.glVertex3f(array25[0] * n, array25[1] * n, array25[2] * n);
            final float[] array26 = boxVertices2[boxFaces[j][1]];
            gl2.glVertex3f(array26[0] * n, array26[1] * n, array26[2] * n);
            final float[] array27 = boxVertices2[boxFaces[j][2]];
            gl2.glVertex3f(array27[0] * n, array27[1] * n, array27[2] * n);
            final float[] array28 = boxVertices2[boxFaces[j][3]];
            gl2.glVertex3f(array28[0] * n, array28[1] * n, array28[2] * n);
            gl2.glEnd();
        }
    }
    
    private void initDodecahedron() {
        this.dodec = new float[20][];
        for (int i = 0; i < this.dodec.length; ++i) {
            this.dodec[i] = new float[3];
        }
        final float n = (float)Math.sqrt(2.0 / (3.0 + Math.sqrt(5.0)));
        final float n2 = 1.0f + (float)Math.sqrt(6.0 / (3.0 + Math.sqrt(5.0)) - 2.0 + 2.0 * Math.sqrt(2.0 / (3.0 + Math.sqrt(5.0))));
        this.dodec[0][0] = -n;
        this.dodec[0][1] = 0.0f;
        this.dodec[0][2] = n2;
        this.dodec[1][0] = n;
        this.dodec[1][1] = 0.0f;
        this.dodec[1][2] = n2;
        this.dodec[2][0] = -1.0f;
        this.dodec[2][1] = -1.0f;
        this.dodec[2][2] = -1.0f;
        this.dodec[3][0] = -1.0f;
        this.dodec[3][1] = -1.0f;
        this.dodec[3][2] = 1.0f;
        this.dodec[4][0] = -1.0f;
        this.dodec[4][1] = 1.0f;
        this.dodec[4][2] = -1.0f;
        this.dodec[5][0] = -1.0f;
        this.dodec[5][1] = 1.0f;
        this.dodec[5][2] = 1.0f;
        this.dodec[6][0] = 1.0f;
        this.dodec[6][1] = -1.0f;
        this.dodec[6][2] = -1.0f;
        this.dodec[7][0] = 1.0f;
        this.dodec[7][1] = -1.0f;
        this.dodec[7][2] = 1.0f;
        this.dodec[8][0] = 1.0f;
        this.dodec[8][1] = 1.0f;
        this.dodec[8][2] = -1.0f;
        this.dodec[9][0] = 1.0f;
        this.dodec[9][1] = 1.0f;
        this.dodec[9][2] = 1.0f;
        this.dodec[10][0] = n2;
        this.dodec[10][1] = n;
        this.dodec[10][2] = 0.0f;
        this.dodec[11][0] = n2;
        this.dodec[11][1] = -n;
        this.dodec[11][2] = 0.0f;
        this.dodec[12][0] = -n2;
        this.dodec[12][1] = n;
        this.dodec[12][2] = 0.0f;
        this.dodec[13][0] = -n2;
        this.dodec[13][1] = -n;
        this.dodec[13][2] = 0.0f;
        this.dodec[14][0] = -n;
        this.dodec[14][1] = 0.0f;
        this.dodec[14][2] = -n2;
        this.dodec[15][0] = n;
        this.dodec[15][1] = 0.0f;
        this.dodec[15][2] = -n2;
        this.dodec[16][0] = 0.0f;
        this.dodec[16][1] = n2;
        this.dodec[16][2] = n;
        this.dodec[17][0] = 0.0f;
        this.dodec[17][1] = n2;
        this.dodec[17][2] = -n;
        this.dodec[18][0] = 0.0f;
        this.dodec[18][1] = -n2;
        this.dodec[18][2] = n;
        this.dodec[19][0] = 0.0f;
        this.dodec[19][1] = -n2;
        this.dodec[19][2] = -n;
    }
    
    private static void diff3(final float[] array, final float[] array2, final float[] array3) {
        array3[0] = array[0] - array2[0];
        array3[1] = array[1] - array2[1];
        array3[2] = array[2] - array2[2];
    }
    
    private static void crossprod(final float[] array, final float[] array2, final float[] array3) {
        final float[] array4 = { array[1] * array2[2] - array2[1] * array[2], array[2] * array2[0] - array2[2] * array[0], array[0] * array2[1] - array2[0] * array[1] };
        array3[0] = array4[0];
        array3[1] = array4[1];
        array3[2] = array4[2];
    }
    
    private static void normalize(final float[] array) {
        float n = (float)Math.sqrt(array[0] * array[0] + array[1] * array[1] + array[2] * array[2]);
        if (n == 0.0) {
            n = (array[0] = 1.0f);
        }
        final float n2 = 1.0f / n;
        final int n3 = 0;
        array[n3] *= n2;
        final int n4 = 1;
        array[n4] *= n2;
        final int n5 = 2;
        array[n5] *= n2;
    }
    
    private void pentagon(final GL2 gl2, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float[] array = new float[3];
        final float[] array2 = new float[3];
        final float[] array3 = new float[3];
        diff3(this.dodec[n], this.dodec[n2], array2);
        diff3(this.dodec[n2], this.dodec[n3], array3);
        crossprod(array2, array3, array);
        normalize(array);
        gl2.glBegin(n6);
        gl2.glNormal3fv(array, 0);
        gl2.glVertex3fv(this.dodec[n], 0);
        gl2.glVertex3fv(this.dodec[n2], 0);
        gl2.glVertex3fv(this.dodec[n3], 0);
        gl2.glVertex3fv(this.dodec[n4], 0);
        gl2.glVertex3fv(this.dodec[n5], 0);
        gl2.glEnd();
    }
    
    private void dodecahedron(final GL2 gl2, final int n) {
        if (this.dodec == null) {
            this.initDodecahedron();
        }
        this.pentagon(gl2, 0, 1, 9, 16, 5, n);
        this.pentagon(gl2, 1, 0, 3, 18, 7, n);
        this.pentagon(gl2, 1, 7, 11, 10, 9, n);
        this.pentagon(gl2, 11, 7, 18, 19, 6, n);
        this.pentagon(gl2, 8, 17, 16, 9, 10, n);
        this.pentagon(gl2, 2, 14, 15, 6, 19, n);
        this.pentagon(gl2, 2, 13, 12, 4, 14, n);
        this.pentagon(gl2, 2, 19, 18, 3, 13, n);
        this.pentagon(gl2, 3, 0, 5, 12, 13, n);
        this.pentagon(gl2, 6, 15, 8, 10, 11, n);
        this.pentagon(gl2, 4, 17, 8, 15, 14, n);
        this.pentagon(gl2, 4, 12, 5, 16, 17, n);
    }
    
    private static void recorditem(final GL2 gl2, final float[] array, final float[] array2, final float[] array3, final int n) {
        final float[] array4 = new float[3];
        final float[] array5 = new float[3];
        diff3(array, array2, array4);
        diff3(array2, array3, array5);
        crossprod(array4, array5, array5);
        normalize(array5);
        gl2.glBegin(n);
        gl2.glNormal3fv(array5, 0);
        gl2.glVertex3fv(array, 0);
        gl2.glVertex3fv(array2, 0);
        gl2.glVertex3fv(array3, 0);
        gl2.glEnd();
    }
    
    private static void subdivide(final GL2 gl2, final float[] array, final float[] array2, final float[] array3, final int n) {
        final float[] array4 = new float[3];
        final float[] array5 = new float[3];
        final float[] array6 = new float[3];
        for (int n2 = 1, i = 0; i < n2; ++i) {
            for (int n3 = 0; i + n3 < n2; ++n3) {
                final int n4 = n2 - i - n3;
                for (int j = 0; j < 3; ++j) {
                    array4[j] = (i * array[j] + n3 * array2[j] + n4 * array3[j]) / n2;
                    array5[j] = ((i + 1) * array[j] + n3 * array2[j] + (n4 - 1) * array3[j]) / n2;
                    array6[j] = (i * array[j] + (n3 + 1) * array2[j] + (n4 - 1) * array3[j]) / n2;
                }
                final float n5 = (float)Math.sqrt(array4[0] * array4[0] + array4[1] * array4[1] + array4[2] * array4[2]);
                final float[] array7 = array4;
                final int n6 = 0;
                array7[n6] /= n5;
                final float[] array8 = array4;
                final int n7 = 1;
                array8[n7] /= n5;
                final float[] array9 = array4;
                final int n8 = 2;
                array9[n8] /= n5;
                final float n9 = (float)Math.sqrt(array5[0] * array5[0] + array5[1] * array5[1] + array5[2] * array5[2]);
                final float[] array10 = array5;
                final int n10 = 0;
                array10[n10] /= n9;
                final float[] array11 = array5;
                final int n11 = 1;
                array11[n11] /= n9;
                final float[] array12 = array5;
                final int n12 = 2;
                array12[n12] /= n9;
                final float n13 = (float)Math.sqrt(array6[0] * array6[0] + array6[1] * array6[1] + array6[2] * array6[2]);
                final float[] array13 = array6;
                final int n14 = 0;
                array13[n14] /= n13;
                final float[] array14 = array6;
                final int n15 = 1;
                array14[n15] /= n13;
                final float[] array15 = array6;
                final int n16 = 2;
                array15[n16] /= n13;
                recorditem(gl2, array5, array4, array6, n);
            }
        }
    }
    
    private static void drawtriangle(final GL2 gl2, final int n, final float[][] array, final int[][] array2, final int n2) {
        subdivide(gl2, array[array2[n][0]], array[array2[n][1]], array[array2[n][2]], n2);
    }
    
    private static void octahedron(final GL2 gl2, final int n) {
        for (int i = 7; i >= 0; --i) {
            drawtriangle(gl2, i, GLUT.odata, GLUT.ondex, n);
        }
    }
    
    private static void icosahedron(final GL2 gl2, final int n) {
        for (int i = 19; i >= 0; --i) {
            drawtriangle(gl2, i, GLUT.idata, GLUT.index, n);
        }
    }
    
    private static final void tetrahedron(final GL2 gl2, final int n) {
        for (int i = 3; i >= 0; --i) {
            drawtriangle(gl2, i, GLUT.tdata, GLUT.tndex, n);
        }
    }
    
    private static void teapot(final GL2 gl2, final int n, final double n2, final int n3, final boolean b) {
        final float[] array = new float[48];
        final float[] array2 = new float[48];
        final float[] array3 = new float[48];
        final float[] array4 = new float[48];
        gl2.glPushAttrib(73736);
        gl2.glEnable(3456);
        gl2.glEnable(2977);
        gl2.glEnable(3511);
        gl2.glEnable(3508);
        gl2.glPushMatrix();
        if (!b) {
            gl2.glFrontFace(2304);
            gl2.glScaled(0.5 * n2, 0.5 * n2, 0.5 * n2);
        }
        else {
            gl2.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
            gl2.glScalef((float)(0.5 * n2), (float)(0.5 * n2), (float)(0.5 * n2));
            gl2.glTranslatef(0.0f, 0.0f, -1.5f);
        }
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    for (int l = 0; l < 3; ++l) {
                        array[(j * 4 + k) * 3 + l] = GLUT.teapotCPData[GLUT.teapotPatchData[i][j * 4 + k]][l];
                        array2[(j * 4 + k) * 3 + l] = GLUT.teapotCPData[GLUT.teapotPatchData[i][j * 4 + (3 - k)]][l];
                        if (l == 1) {
                            final float[] array5 = array2;
                            final int n4 = (j * 4 + k) * 3 + l;
                            array5[n4] *= (float)(-1.0);
                        }
                        if (i < 6) {
                            array3[(j * 4 + k) * 3 + l] = GLUT.teapotCPData[GLUT.teapotPatchData[i][j * 4 + (3 - k)]][l];
                            if (l == 0) {
                                final float[] array6 = array3;
                                final int n5 = (j * 4 + k) * 3 + l;
                                array6[n5] *= (float)(-1.0);
                            }
                            array4[(j * 4 + k) * 3 + l] = GLUT.teapotCPData[GLUT.teapotPatchData[i][j * 4 + k]][l];
                            if (l == 0) {
                                final float[] array7 = array4;
                                final int n6 = (j * 4 + k) * 3 + l;
                                array7[n6] *= (float)(-1.0);
                            }
                            if (l == 1) {
                                final float[] array8 = array4;
                                final int n7 = (j * 4 + k) * 3 + l;
                                array8[n7] *= (float)(-1.0);
                            }
                        }
                    }
                }
            }
            gl2.glMap2f(3508, 0.0f, 1.0f, 2, 2, 0.0f, 1.0f, 4, 2, GLUT.teapotTex, 0);
            gl2.glMap2f(3511, 0.0f, 1.0f, 3, 4, 0.0f, 1.0f, 12, 4, array, 0);
            gl2.glMapGrid2f(n, 0.0f, 1.0f, n, 0.0f, 1.0f);
            evaluateTeapotMesh(gl2, n, n3, i, !b);
            gl2.glMap2f(3511, 0.0f, 1.0f, 3, 4, 0.0f, 1.0f, 12, 4, array2, 0);
            evaluateTeapotMesh(gl2, n, n3, i, !b);
            if (i < 6) {
                gl2.glMap2f(3511, 0.0f, 1.0f, 3, 4, 0.0f, 1.0f, 12, 4, array3, 0);
                evaluateTeapotMesh(gl2, n, n3, i, !b);
                gl2.glMap2f(3511, 0.0f, 1.0f, 3, 4, 0.0f, 1.0f, 12, 4, array4, 0);
                evaluateTeapotMesh(gl2, n, n3, i, !b);
            }
        }
        gl2.glPopMatrix();
        gl2.glPopAttrib();
    }
    
    private static void evaluateTeapotMesh(final GL2 gl2, final int n, final int n2, final int n3, final boolean b) {
        if (b && (n3 == 5 || n3 == 3)) {
            gl2.glPolygonMode(1032, n2);
            for (int i = 0; i < n; ++i) {
                if (i == 0) {
                    gl2.glDisable(3456);
                    gl2.glNormal3f(0.0f, 0.0f, (n3 == 3) ? 1.0f : -1.0f);
                    gl2.glBegin(6);
                    gl2.glEvalCoord2f(0.0f, 0.0f);
                    for (int j = 0; j <= n; ++j) {
                        gl2.glEvalCoord2f(j / n, 1.0f / n / n);
                    }
                    gl2.glEnd();
                    gl2.glEnable(3456);
                }
                gl2.glBegin(8);
                for (int k = n; k >= 0; --k) {
                    gl2.glEvalCoord2f(k / n, (i + 1) / n);
                    gl2.glEvalCoord2f(k / n, Math.max(i, 1.0f / n) / n);
                }
                gl2.glEnd();
            }
        }
        else {
            gl2.glEvalMesh2(n2, 0, n, 0, n);
        }
    }
    
    private static void bitmapCharacterImpl(final GL2 gl2, final int n, final char c) {
        final BitmapFontRec bitmapFont = getBitmapFont(n);
        final char c2 = (char)(c & '\uffff');
        if (c2 < bitmapFont.first || c2 >= bitmapFont.first + bitmapFont.num_chars) {
            return;
        }
        final BitmapCharRec bitmapCharRec = bitmapFont.ch[c2 - bitmapFont.first];
        if (bitmapCharRec != null) {
            gl2.glBitmap(bitmapCharRec.width, bitmapCharRec.height, bitmapCharRec.xorig, bitmapCharRec.yorig, bitmapCharRec.advance, 0.0f, bitmapCharRec.bitmap, 0);
        }
    }
    
    private static BitmapFontRec getBitmapFont(final int n) {
        BitmapFontRec bitmapFontRec = GLUT.bitmapFonts[n];
        if (bitmapFontRec == null) {
            switch (n) {
                case 2: {
                    bitmapFontRec = GLUTBitmap9x15.glutBitmap9By15;
                    break;
                }
                case 3: {
                    bitmapFontRec = GLUTBitmap8x13.glutBitmap8By13;
                    break;
                }
                case 4: {
                    bitmapFontRec = GLUTBitmapTimesRoman10.glutBitmapTimesRoman10;
                    break;
                }
                case 5: {
                    bitmapFontRec = GLUTBitmapTimesRoman24.glutBitmapTimesRoman24;
                    break;
                }
                case 6: {
                    bitmapFontRec = GLUTBitmapHelvetica10.glutBitmapHelvetica10;
                    break;
                }
                case 7: {
                    bitmapFontRec = GLUTBitmapHelvetica12.glutBitmapHelvetica12;
                    break;
                }
                case 8: {
                    bitmapFontRec = GLUTBitmapHelvetica18.glutBitmapHelvetica18;
                    break;
                }
                default: {
                    throw new GLException("Unknown bitmap font number " + n);
                }
            }
            GLUT.bitmapFonts[n] = bitmapFontRec;
        }
        return bitmapFontRec;
    }
    
    private static StrokeFontRec getStrokeFont(final int n) {
        StrokeFontRec strokeFontRec = GLUT.strokeFonts[n];
        if (strokeFontRec == null) {
            switch (n) {
                case 0: {
                    strokeFontRec = GLUTStrokeRoman.glutStrokeRoman;
                    break;
                }
                case 1: {
                    strokeFontRec = GLUTStrokeMonoRoman.glutStrokeMonoRoman;
                    break;
                }
                default: {
                    throw new GLException("Unknown stroke font number " + n);
                }
            }
        }
        return strokeFontRec;
    }
    
    private static void beginBitmap(final GL2 gl2, final int[] array, final int[] array2, final int[] array3, final int[] array4, final int[] array5, final int[] array6) {
        gl2.glGetIntegerv(3312, array, 0);
        gl2.glGetIntegerv(3313, array2, 0);
        gl2.glGetIntegerv(3314, array3, 0);
        gl2.glGetIntegerv(3315, array4, 0);
        gl2.glGetIntegerv(3316, array5, 0);
        gl2.glGetIntegerv(3317, array6, 0);
        gl2.glPixelStorei(3312, 0);
        gl2.glPixelStorei(3313, 0);
        gl2.glPixelStorei(3314, 0);
        gl2.glPixelStorei(3315, 0);
        gl2.glPixelStorei(3316, 0);
        gl2.glPixelStorei(3317, 1);
    }
    
    private static void endBitmap(final GL2 gl2, final int[] array, final int[] array2, final int[] array3, final int[] array4, final int[] array5, final int[] array6) {
        gl2.glPixelStorei(3312, array[0]);
        gl2.glPixelStorei(3313, array2[0]);
        gl2.glPixelStorei(3314, array3[0]);
        gl2.glPixelStorei(3315, array4[0]);
        gl2.glPixelStorei(3316, array5[0]);
        gl2.glPixelStorei(3317, array6[0]);
    }
    
    static {
        boxNormals = new float[][] { { -1.0f, 0.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, { 1.0f, 0.0f, 0.0f }, { 0.0f, -1.0f, 0.0f }, { 0.0f, 0.0f, 1.0f }, { 0.0f, 0.0f, -1.0f } };
        boxFaces = new int[][] { { 0, 1, 2, 3 }, { 3, 2, 6, 7 }, { 7, 6, 5, 4 }, { 4, 5, 1, 0 }, { 5, 6, 2, 1 }, { 7, 4, 0, 3 } };
        odata = new float[][] { { 1.0f, 0.0f, 0.0f }, { -1.0f, 0.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, { 0.0f, -1.0f, 0.0f }, { 0.0f, 0.0f, 1.0f }, { 0.0f, 0.0f, -1.0f } };
        ondex = new int[][] { { 0, 4, 2 }, { 1, 2, 4 }, { 0, 3, 4 }, { 1, 4, 3 }, { 0, 2, 5 }, { 1, 5, 2 }, { 0, 5, 3 }, { 1, 3, 5 } };
        idata = new float[][] { { -0.5257311f, 0.0f, 0.8506508f }, { 0.5257311f, 0.0f, 0.8506508f }, { -0.5257311f, 0.0f, -0.8506508f }, { 0.5257311f, 0.0f, -0.8506508f }, { 0.0f, 0.8506508f, 0.5257311f }, { 0.0f, 0.8506508f, -0.5257311f }, { 0.0f, -0.8506508f, 0.5257311f }, { 0.0f, -0.8506508f, -0.5257311f }, { 0.8506508f, 0.5257311f, 0.0f }, { -0.8506508f, 0.5257311f, 0.0f }, { 0.8506508f, -0.5257311f, 0.0f }, { -0.8506508f, -0.5257311f, 0.0f } };
        index = new int[][] { { 0, 4, 1 }, { 0, 9, 4 }, { 9, 5, 4 }, { 4, 5, 8 }, { 4, 8, 1 }, { 8, 10, 1 }, { 8, 3, 10 }, { 5, 3, 8 }, { 5, 2, 3 }, { 2, 7, 3 }, { 7, 10, 3 }, { 7, 6, 10 }, { 7, 11, 6 }, { 11, 0, 6 }, { 0, 1, 6 }, { 6, 1, 10 }, { 9, 0, 11 }, { 9, 11, 2 }, { 9, 2, 5 }, { 7, 2, 11 } };
        rdod_r = new double[][] { { 0.0, 0.0, 1.0 }, { 0.707106781187, 0.0, 0.5 }, { 0.0, 0.707106781187, 0.5 }, { -0.707106781187, 0.0, 0.5 }, { 0.0, -0.707106781187, 0.5 }, { 0.707106781187, 0.707106781187, 0.0 }, { -0.707106781187, 0.707106781187, 0.0 }, { -0.707106781187, -0.707106781187, 0.0 }, { 0.707106781187, -0.707106781187, 0.0 }, { 0.707106781187, 0.0, -0.5 }, { 0.0, 0.707106781187, -0.5 }, { -0.707106781187, 0.0, -0.5 }, { 0.0, -0.707106781187, -0.5 }, { 0.0, 0.0, -1.0 } };
        rdod_v = new int[][] { { 0, 1, 5, 2 }, { 0, 2, 6, 3 }, { 0, 3, 7, 4 }, { 0, 4, 8, 1 }, { 5, 10, 6, 2 }, { 6, 11, 7, 3 }, { 7, 12, 8, 4 }, { 8, 9, 5, 1 }, { 5, 9, 13, 10 }, { 6, 10, 13, 11 }, { 7, 11, 13, 12 }, { 8, 12, 13, 9 } };
        rdod_n = new double[][] { { 0.353553390594, 0.353553390594, 0.5 }, { -0.353553390594, 0.353553390594, 0.5 }, { -0.353553390594, -0.353553390594, 0.5 }, { 0.353553390594, -0.353553390594, 0.5 }, { 0.0, 1.0, 0.0 }, { -1.0, 0.0, 0.0 }, { 0.0, -1.0, 0.0 }, { 1.0, 0.0, 0.0 }, { 0.353553390594, 0.353553390594, -0.5 }, { -0.353553390594, 0.353553390594, -0.5 }, { -0.353553390594, -0.353553390594, -0.5 }, { 0.353553390594, -0.353553390594, -0.5 } };
        tdata = new float[][] { { 1.7320508f, 1.7320508f, 1.7320508f }, { 1.7320508f, -1.7320508f, -1.7320508f }, { -1.7320508f, 1.7320508f, -1.7320508f }, { -1.7320508f, -1.7320508f, 1.7320508f } };
        tndex = new int[][] { { 0, 1, 3 }, { 2, 1, 0 }, { 3, 2, 0 }, { 1, 2, 3 } };
        teapotPatchData = new int[][] { { 102, 103, 104, 105, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }, { 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27 }, { 24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40 }, { 96, 96, 96, 96, 97, 98, 99, 100, 101, 101, 101, 101, 0, 1, 2, 3 }, { 0, 1, 2, 3, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117 }, { 118, 118, 118, 118, 124, 122, 119, 121, 123, 126, 125, 120, 40, 39, 38, 37 }, { 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56 }, { 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 28, 65, 66, 67 }, { 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83 }, { 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95 } };
        teapotCPData = new float[][] { { 0.2f, 0.0f, 2.7f }, { 0.2f, -0.112f, 2.7f }, { 0.112f, -0.2f, 2.7f }, { 0.0f, -0.2f, 2.7f }, { 1.3375f, 0.0f, 2.53125f }, { 1.3375f, -0.749f, 2.53125f }, { 0.749f, -1.3375f, 2.53125f }, { 0.0f, -1.3375f, 2.53125f }, { 1.4375f, 0.0f, 2.53125f }, { 1.4375f, -0.805f, 2.53125f }, { 0.805f, -1.4375f, 2.53125f }, { 0.0f, -1.4375f, 2.53125f }, { 1.5f, 0.0f, 2.4f }, { 1.5f, -0.84f, 2.4f }, { 0.84f, -1.5f, 2.4f }, { 0.0f, -1.5f, 2.4f }, { 1.75f, 0.0f, 1.875f }, { 1.75f, -0.98f, 1.875f }, { 0.98f, -1.75f, 1.875f }, { 0.0f, -1.75f, 1.875f }, { 2.0f, 0.0f, 1.35f }, { 2.0f, -1.12f, 1.35f }, { 1.12f, -2.0f, 1.35f }, { 0.0f, -2.0f, 1.35f }, { 2.0f, 0.0f, 0.9f }, { 2.0f, -1.12f, 0.9f }, { 1.12f, -2.0f, 0.9f }, { 0.0f, -2.0f, 0.9f }, { -2.0f, 0.0f, 0.9f }, { 2.0f, 0.0f, 0.45f }, { 2.0f, -1.12f, 0.45f }, { 1.12f, -2.0f, 0.45f }, { 0.0f, -2.0f, 0.45f }, { 1.5f, 0.0f, 0.225f }, { 1.5f, -0.84f, 0.225f }, { 0.84f, -1.5f, 0.225f }, { 0.0f, -1.5f, 0.225f }, { 1.5f, 0.0f, 0.15f }, { 1.5f, -0.84f, 0.15f }, { 0.84f, -1.5f, 0.15f }, { 0.0f, -1.5f, 0.15f }, { -1.6f, 0.0f, 2.025f }, { -1.6f, -0.3f, 2.025f }, { -1.5f, -0.3f, 2.25f }, { -1.5f, 0.0f, 2.25f }, { -2.3f, 0.0f, 2.025f }, { -2.3f, -0.3f, 2.025f }, { -2.5f, -0.3f, 2.25f }, { -2.5f, 0.0f, 2.25f }, { -2.7f, 0.0f, 2.025f }, { -2.7f, -0.3f, 2.025f }, { -3.0f, -0.3f, 2.25f }, { -3.0f, 0.0f, 2.25f }, { -2.7f, 0.0f, 1.8f }, { -2.7f, -0.3f, 1.8f }, { -3.0f, -0.3f, 1.8f }, { -3.0f, 0.0f, 1.8f }, { -2.7f, 0.0f, 1.575f }, { -2.7f, -0.3f, 1.575f }, { -3.0f, -0.3f, 1.35f }, { -3.0f, 0.0f, 1.35f }, { -2.5f, 0.0f, 1.125f }, { -2.5f, -0.3f, 1.125f }, { -2.65f, -0.3f, 0.9375f }, { -2.65f, 0.0f, 0.9375f }, { -2.0f, -0.3f, 0.9f }, { -1.9f, -0.3f, 0.6f }, { -1.9f, 0.0f, 0.6f }, { 1.7f, 0.0f, 1.425f }, { 1.7f, -0.66f, 1.425f }, { 1.7f, -0.66f, 0.6f }, { 1.7f, 0.0f, 0.6f }, { 2.6f, 0.0f, 1.425f }, { 2.6f, -0.66f, 1.425f }, { 3.1f, -0.66f, 0.825f }, { 3.1f, 0.0f, 0.825f }, { 2.3f, 0.0f, 2.1f }, { 2.3f, -0.25f, 2.1f }, { 2.4f, -0.25f, 2.025f }, { 2.4f, 0.0f, 2.025f }, { 2.7f, 0.0f, 2.4f }, { 2.7f, -0.25f, 2.4f }, { 3.3f, -0.25f, 2.4f }, { 3.3f, 0.0f, 2.4f }, { 2.8f, 0.0f, 2.475f }, { 2.8f, -0.25f, 2.475f }, { 3.525f, -0.25f, 2.49375f }, { 3.525f, 0.0f, 2.49375f }, { 2.9f, 0.0f, 2.475f }, { 2.9f, -0.15f, 2.475f }, { 3.45f, -0.15f, 2.5125f }, { 3.45f, 0.0f, 2.5125f }, { 2.8f, 0.0f, 2.4f }, { 2.8f, -0.15f, 2.4f }, { 3.2f, -0.15f, 2.4f }, { 3.2f, 0.0f, 2.4f }, { 0.0f, 0.0f, 3.15f }, { 0.8f, 0.0f, 3.15f }, { 0.8f, -0.45f, 3.15f }, { 0.45f, -0.8f, 3.15f }, { 0.0f, -0.8f, 3.15f }, { 0.0f, 0.0f, 2.85f }, { 1.4f, 0.0f, 2.4f }, { 1.4f, -0.784f, 2.4f }, { 0.784f, -1.4f, 2.4f }, { 0.0f, -1.4f, 2.4f }, { 0.4f, 0.0f, 2.55f }, { 0.4f, -0.224f, 2.55f }, { 0.224f, -0.4f, 2.55f }, { 0.0f, -0.4f, 2.55f }, { 1.3f, 0.0f, 2.55f }, { 1.3f, -0.728f, 2.55f }, { 0.728f, -1.3f, 2.55f }, { 0.0f, -1.3f, 2.55f }, { 1.3f, 0.0f, 2.4f }, { 1.3f, -0.728f, 2.4f }, { 0.728f, -1.3f, 2.4f }, { 0.0f, -1.3f, 2.4f }, { 0.0f, 0.0f, 0.0f }, { 1.425f, -0.798f, 0.0f }, { 1.5f, 0.0f, 0.075f }, { 1.425f, 0.0f, 0.0f }, { 0.798f, -1.425f, 0.0f }, { 0.0f, -1.5f, 0.075f }, { 0.0f, -1.425f, 0.0f }, { 1.5f, -0.84f, 0.075f }, { 0.84f, -1.5f, 0.075f } };
        teapotTex = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
        bitmapFonts = new BitmapFontRec[9];
        strokeFonts = new StrokeFontRec[9];
    }
}
