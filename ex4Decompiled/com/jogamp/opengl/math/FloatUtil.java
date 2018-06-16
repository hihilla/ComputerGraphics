// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

import jogamp.opengl.Debug;
import com.jogamp.common.os.Platform;
import java.nio.FloatBuffer;
import com.jogamp.opengl.GLException;

public final class FloatUtil
{
    public static final boolean DEBUG;
    private static volatile boolean machEpsilonAvail;
    private static float machEpsilon;
    private static final boolean DEBUG_EPSILON = false;
    public static final float E = 2.7182817f;
    public static final float PI = 3.1415927f;
    public static final float TWO_PI = 6.2831855f;
    public static final float HALF_PI = 1.5707964f;
    public static final float QUARTER_PI = 0.7853982f;
    public static final float SQUARED_PI = 9.869605f;
    public static final float EPSILON = 1.1920929E-7f;
    public static final float INV_DEVIANCE = 1.0E-5f;
    
    public static float[] makeIdentity(final float[] array, final int n) {
        array[n + 0 + 0] = 1.0f;
        array[n + 2 + 0] = (array[n + 1 + 0] = 0.0f);
        array[n + 0 + 4] = (array[n + 3 + 0] = 0.0f);
        array[n + 1 + 4] = 1.0f;
        array[n + 3 + 4] = (array[n + 2 + 4] = 0.0f);
        array[n + 1 + 8] = (array[n + 0 + 8] = 0.0f);
        array[n + 2 + 8] = 1.0f;
        array[n + 0 + 12] = (array[n + 3 + 8] = 0.0f);
        array[n + 2 + 12] = (array[n + 1 + 12] = 0.0f);
        array[n + 3 + 12] = 1.0f;
        return array;
    }
    
    public static float[] makeIdentity(final float[] array) {
        array[0] = 1.0f;
        array[2] = (array[1] = 0.0f);
        array[4] = (array[3] = 0.0f);
        array[5] = 1.0f;
        array[7] = (array[6] = 0.0f);
        array[9] = (array[8] = 0.0f);
        array[10] = 1.0f;
        array[12] = (array[11] = 0.0f);
        array[14] = (array[13] = 0.0f);
        array[15] = 1.0f;
        return array;
    }
    
    public static float[] makeTranslation(final float[] array, final int n, final boolean b, final float n2, final float n3, final float n4) {
        if (b) {
            makeIdentity(array, n);
        }
        else {
            array[n + 1 + 4] = (array[n + 0 + 0] = 1.0f);
            array[n + 3 + 12] = (array[n + 2 + 8] = 1.0f);
        }
        array[n + 0 + 12] = n2;
        array[n + 1 + 12] = n3;
        array[n + 2 + 12] = n4;
        return array;
    }
    
    public static float[] makeTranslation(final float[] array, final boolean b, final float n, final float n2, final float n3) {
        if (b) {
            makeIdentity(array);
        }
        else {
            array[5] = (array[0] = 1.0f);
            array[15] = (array[10] = 1.0f);
        }
        array[12] = n;
        array[13] = n2;
        array[14] = n3;
        return array;
    }
    
    public static float[] makeScale(final float[] array, final int n, final boolean b, final float n2, final float n3, final float n4) {
        if (b) {
            makeIdentity(array, n);
        }
        else {
            array[n + 0 + 12] = 0.0f;
            array[n + 2 + 12] = (array[n + 1 + 12] = 0.0f);
            array[n + 3 + 12] = 1.0f;
        }
        array[n + 0 + 0] = n2;
        array[n + 1 + 4] = n3;
        array[n + 2 + 8] = n4;
        return array;
    }
    
    public static float[] makeScale(final float[] array, final boolean b, final float n, final float n2, final float n3) {
        if (b) {
            makeIdentity(array);
        }
        else {
            array[12] = 0.0f;
            array[14] = (array[13] = 0.0f);
            array[15] = 1.0f;
        }
        array[0] = n;
        array[5] = n2;
        array[10] = n3;
        return array;
    }
    
    public static float[] makeRotationAxis(final float[] array, final int n, final float n2, float n3, float n4, float n5, final float[] array2) {
        final float cos = cos(n2);
        final float n6 = 1.0f - cos;
        final float sin = sin(n2);
        array2[0] = n3;
        array2[1] = n4;
        array2[2] = n5;
        VectorUtil.normalizeVec3(array2);
        n3 = array2[0];
        n4 = array2[1];
        n5 = array2[2];
        final float n7 = n3 * n4;
        final float n8 = n3 * n5;
        final float n9 = n3 * sin;
        final float n10 = n4 * sin;
        final float n11 = n4 * n5;
        final float n12 = n5 * sin;
        array[0 + n] = n3 * n3 * n6 + cos;
        array[1 + n] = n7 * n6 + n12;
        array[2 + n] = n8 * n6 - n10;
        array[3 + n] = 0.0f;
        array[4 + n] = n7 * n6 - n12;
        array[5 + n] = n4 * n4 * n6 + cos;
        array[6 + n] = n11 * n6 + n9;
        array[7 + n] = 0.0f;
        array[8 + n] = n8 * n6 + n10;
        array[9 + n] = n11 * n6 - n9;
        array[10 + n] = n5 * n5 * n6 + cos;
        array[12 + n] = (array[11 + n] = 0.0f);
        array[14 + n] = (array[13 + n] = 0.0f);
        array[15 + n] = 1.0f;
        return array;
    }
    
    public static float[] makeRotationEuler(final float[] array, final int n, final float n2, final float n3, final float n4) {
        final float cos = cos(n3);
        final float sin = sin(n3);
        final float cos2 = cos(n4);
        final float sin2 = sin(n4);
        final float cos3 = cos(n2);
        final float sin3 = sin(n2);
        array[0 + n] = cos * cos2;
        array[1 + n] = sin2;
        array[2 + n] = -sin * cos2;
        array[3 + n] = 0.0f;
        array[4 + n] = sin * sin3 - cos * sin2 * cos3;
        array[5 + n] = cos2 * cos3;
        array[6 + n] = sin * sin2 * cos3 + cos * sin3;
        array[7 + n] = 0.0f;
        array[8 + n] = cos * sin2 * sin3 + sin * cos3;
        array[9 + n] = -cos2 * sin3;
        array[10 + n] = -sin * sin2 * sin3 + cos * cos3;
        array[12 + n] = (array[11 + n] = 0.0f);
        array[14 + n] = (array[13 + n] = 0.0f);
        array[15 + n] = 1.0f;
        return array;
    }
    
    public static float[] makeOrtho(final float[] array, final int n, final boolean b, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (b) {
            array[n + 1 + 0] = 0.0f;
            array[n + 3 + 0] = (array[n + 2 + 0] = 0.0f);
            array[n + 2 + 4] = (array[n + 0 + 4] = 0.0f);
            array[n + 0 + 8] = (array[n + 3 + 4] = 0.0f);
            array[n + 3 + 8] = (array[n + 1 + 8] = 0.0f);
        }
        final float n8 = n3 - n2;
        final float n9 = n5 - n4;
        final float n10 = n7 - n6;
        final float n11 = -1.0f * (n3 + n2) / n8;
        final float n12 = -1.0f * (n5 + n4) / n9;
        final float n13 = -1.0f * (n7 + n6) / n10;
        array[n + 0 + 0] = 2.0f / n8;
        array[n + 1 + 4] = 2.0f / n9;
        array[n + 2 + 8] = -2.0f / n10;
        array[n + 0 + 12] = n11;
        array[n + 1 + 12] = n12;
        array[n + 2 + 12] = n13;
        array[n + 3 + 12] = 1.0f;
        return array;
    }
    
    public static float[] makeFrustum(final float[] array, final int n, final boolean b, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) throws GLException {
        if (n6 <= 0.0f || n7 <= n6) {
            throw new GLException("Requirements zNear > 0 and zFar > zNear, but zNear " + n6 + ", zFar " + n7);
        }
        if (n2 == n3 || n5 == n4) {
            throw new GLException("GL_INVALID_VALUE: top,bottom and left,right must not be equal");
        }
        if (b) {
            array[n + 2 + 0] = (array[n + 1 + 0] = 0.0f);
            array[n + 0 + 4] = (array[n + 3 + 0] = 0.0f);
            array[n + 3 + 4] = (array[n + 2 + 4] = 0.0f);
            array[n + 1 + 12] = (array[n + 0 + 12] = 0.0f);
        }
        final float n8 = 2.0f * n6;
        final float n9 = n3 - n2;
        final float n10 = n5 - n4;
        final float n11 = n7 - n6;
        final float n12 = (n3 + n2) / n9;
        final float n13 = (n5 + n4) / n10;
        final float n14 = -1.0f * (n7 + n6) / n11;
        final float n15 = -2.0f * (n7 * n6) / n11;
        array[n + 0 + 0] = n8 / n9;
        array[n + 1 + 4] = n8 / n10;
        array[n + 0 + 8] = n12;
        array[n + 1 + 8] = n13;
        array[n + 2 + 8] = n14;
        array[n + 3 + 8] = -1.0f;
        array[n + 2 + 12] = n15;
        array[n + 3 + 12] = 0.0f;
        return array;
    }
    
    public static float[] makePerspective(final float[] array, final int n, final boolean b, final float n2, final float n3, final float n4, final float n5) throws GLException {
        final float n6 = tan(n2 / 2.0f) * n4;
        final float n7 = -1.0f * n6;
        return makeFrustum(array, n, b, n3 * n7, n3 * n6, n7, n6, n4, n5);
    }
    
    public static float[] makePerspective(final float[] array, final int n, final boolean b, final FovHVHalves fovHVHalves, final float n2, final float n3) throws GLException {
        final FovHVHalves tangents = fovHVHalves.toTangents();
        return makeFrustum(array, n, b, -1.0f * tangents.left * n2, tangents.right * n2, -1.0f * tangents.bottom * n2, tangents.top * n2, n2, n3);
    }
    
    public static float[] makeLookAt(final float[] array, final int n, final float[] array2, final int n2, final float[] array3, final int n3, final float[] array4, final int n4, final float[] array5) {
        array5[0] = array3[0 + n3] - array2[0 + n2];
        array5[1] = array3[1 + n3] - array2[1 + n2];
        array5[2] = array3[2 + n3] - array2[2 + n2];
        VectorUtil.normalizeVec3(array5);
        VectorUtil.crossVec3(array5, 3, array5, 0, array4, n4);
        VectorUtil.normalizeVec3(array5, 3);
        VectorUtil.crossVec3(array5, 6, array5, 3, array5, 0);
        array[n + 0 + 0] = array5[3];
        array[n + 0 + 1] = array5[6];
        array[n + 0 + 2] = -array5[0];
        array[n + 0 + 3] = 0.0f;
        array[n + 4 + 0] = array5[4];
        array[n + 4 + 1] = array5[7];
        array[n + 4 + 2] = -array5[1];
        array[n + 4 + 3] = 0.0f;
        array[n + 8 + 0] = array5[5];
        array[n + 8 + 1] = array5[8];
        array[n + 8 + 2] = -array5[2];
        array[n + 12 + 0] = (array[n + 8 + 3] = 0.0f);
        array[n + 12 + 2] = (array[n + 12 + 1] = 0.0f);
        array[n + 12 + 3] = 1.0f;
        makeTranslation(array5, true, -array2[0 + n2], -array2[1 + n2], -array2[2 + n2]);
        multMatrix(array, n, array5, 0);
        return array;
    }
    
    public static float[] makePick(final float[] array, final int n, final float n2, final float n3, final float n4, final float n5, final int[] array2, final int n6, final float[] array3) {
        if (n4 <= 0.0f || n5 <= 0.0f) {
            return null;
        }
        makeTranslation(array, n, true, (array2[2 + n6] - 2.0f * (n2 - array2[0 + n6])) / n4, (array2[3 + n6] - 2.0f * (n3 - array2[1 + n6])) / n5, 0.0f);
        makeScale(array3, true, array2[2 + n6] / n4, array2[3 + n6] / n5, 1.0f);
        multMatrix(array, n, array3, 0);
        return array;
    }
    
    public static float[] transposeMatrix(final float[] array, final int n, final float[] array2, final int n2) {
        array2[n2 + 0] = array[n + 0];
        array2[n2 + 1] = array[n + 4];
        array2[n2 + 2] = array[n + 8];
        array2[n2 + 3] = array[n + 12];
        array2[n2 + 0 + 4] = array[n + 1 + 0];
        array2[n2 + 1 + 4] = array[n + 1 + 4];
        array2[n2 + 2 + 4] = array[n + 1 + 8];
        array2[n2 + 3 + 4] = array[n + 1 + 12];
        array2[n2 + 0 + 8] = array[n + 2 + 0];
        array2[n2 + 1 + 8] = array[n + 2 + 4];
        array2[n2 + 2 + 8] = array[n + 2 + 8];
        array2[n2 + 3 + 8] = array[n + 2 + 12];
        array2[n2 + 0 + 12] = array[n + 3 + 0];
        array2[n2 + 1 + 12] = array[n + 3 + 4];
        array2[n2 + 2 + 12] = array[n + 3 + 8];
        array2[n2 + 3 + 12] = array[n + 3 + 12];
        return array2;
    }
    
    public static float[] transposeMatrix(final float[] array, final float[] array2) {
        array2[0] = array[0];
        array2[1] = array[4];
        array2[2] = array[8];
        array2[3] = array[12];
        array2[4] = array[1];
        array2[5] = array[5];
        array2[6] = array[9];
        array2[7] = array[13];
        array2[8] = array[2];
        array2[9] = array[6];
        array2[10] = array[10];
        array2[11] = array[14];
        array2[12] = array[3];
        array2[13] = array[7];
        array2[14] = array[11];
        array2[15] = array[15];
        return array2;
    }
    
    public static float matrixDeterminant(final float[] array, final int n) {
        final float n2 = array[5 + n];
        final float n3 = array[6 + n];
        final float n4 = array[7 + n];
        final float n5 = array[9 + n];
        final float n6 = array[10 + n];
        final float n7 = array[11 + n];
        final float n8 = array[13 + n];
        final float n9 = array[14 + n];
        final float n10 = array[15 + n];
        final float n11 = 0.0f + array[0 + n] * (n2 * (n6 * n10 - n9 * n7) - n5 * (n3 * n10 - n9 * n4) + n8 * (n3 * n7 - n6 * n4));
        final float n12 = array[1 + n];
        final float n13 = array[2 + n];
        final float n14 = array[3 + n];
        final float n15 = n11 - array[4 + n] * (n12 * (n6 * n10 - n9 * n7) - n5 * (n13 * n10 - n9 * n14) + n8 * (n13 * n7 - n6 * n14));
        final float n16 = array[5 + n];
        final float n17 = array[6 + n];
        final float n18 = array[7 + n];
        final float n19 = n15 + array[8 + n] * (n12 * (n17 * n10 - n9 * n18) - n16 * (n13 * n10 - n9 * n14) + n8 * (n13 * n18 - n17 * n14));
        final float n20 = array[9 + n];
        final float n21 = array[10 + n];
        final float n22 = array[11 + n];
        return n19 - array[12 + n] * (n12 * (n17 * n22 - n21 * n18) - n16 * (n13 * n22 - n21 * n14) + n20 * (n13 * n18 - n17 * n14));
    }
    
    public static float matrixDeterminant(final float[] array) {
        final float n = array[5];
        final float n2 = array[6];
        final float n3 = array[7];
        final float n4 = array[9];
        final float n5 = array[10];
        final float n6 = array[11];
        final float n7 = array[13];
        final float n8 = array[14];
        final float n9 = array[15];
        final float n10 = 0.0f + array[0] * (n * (n5 * n9 - n8 * n6) - n4 * (n2 * n9 - n8 * n3) + n7 * (n2 * n6 - n5 * n3));
        final float n11 = array[1];
        final float n12 = array[2];
        final float n13 = array[3];
        final float n14 = n10 - array[4] * (n11 * (n5 * n9 - n8 * n6) - n4 * (n12 * n9 - n8 * n13) + n7 * (n12 * n6 - n5 * n13));
        final float n15 = array[5];
        final float n16 = array[6];
        final float n17 = array[7];
        final float n18 = n14 + array[8] * (n11 * (n16 * n9 - n8 * n17) - n15 * (n12 * n9 - n8 * n13) + n7 * (n12 * n17 - n16 * n13));
        final float n19 = array[9];
        final float n20 = array[10];
        final float n21 = array[11];
        return n18 - array[12] * (n11 * (n16 * n21 - n20 * n17) - n15 * (n12 * n21 - n20 * n13) + n19 * (n12 * n17 - n16 * n13));
    }
    
    public static float[] invertMatrix(final float[] array, final int n, final float[] array2, final int n2) {
        float abs = Math.abs(array[0]);
        for (int i = 1; i < 16; ++i) {
            final float abs2 = Math.abs(array[i]);
            if (abs2 > abs) {
                abs = abs2;
            }
        }
        if (0.0f == abs) {
            return null;
        }
        final float n3 = 1.0f / abs;
        final float n4 = array[0 + n] * n3;
        final float n5 = array[1 + n] * n3;
        final float n6 = array[2 + n] * n3;
        final float n7 = array[3 + n] * n3;
        final float n8 = array[4 + n] * n3;
        final float n9 = array[5 + n] * n3;
        final float n10 = array[6 + n] * n3;
        final float n11 = array[7 + n] * n3;
        final float n12 = array[8 + n] * n3;
        final float n13 = array[9 + n] * n3;
        final float n14 = array[10 + n] * n3;
        final float n15 = array[11 + n] * n3;
        final float n16 = array[12 + n] * n3;
        final float n17 = array[13 + n] * n3;
        final float n18 = array[14 + n] * n3;
        final float n19 = array[15 + n] * n3;
        final float n20 = n9 * (n14 * n19 - n18 * n15) - n13 * (n10 * n19 - n18 * n11) + n17 * (n10 * n15 - n14 * n11);
        final float n21 = -(n5 * (n14 * n19 - n18 * n15) - n13 * (n6 * n19 - n18 * n7) + n17 * (n6 * n15 - n14 * n7));
        final float n22 = n5 * (n10 * n19 - n18 * n11) - n9 * (n6 * n19 - n18 * n7) + n17 * (n6 * n11 - n10 * n7);
        final float n23 = -(n5 * (n10 * n15 - n14 * n11) - n9 * (n6 * n15 - n14 * n7) + n13 * (n6 * n11 - n10 * n7));
        final float n24 = -(n8 * (n14 * n19 - n18 * n15) - n12 * (n10 * n19 - n18 * n11) + n16 * (n10 * n15 - n14 * n11));
        final float n25 = n4 * (n14 * n19 - n18 * n15) - n12 * (n6 * n19 - n18 * n7) + n16 * (n6 * n15 - n14 * n7);
        final float n26 = -(n4 * (n10 * n19 - n18 * n11) - n8 * (n6 * n19 - n18 * n7) + n16 * (n6 * n11 - n10 * n7));
        final float n27 = n4 * (n10 * n15 - n14 * n11) - n8 * (n6 * n15 - n14 * n7) + n12 * (n6 * n11 - n10 * n7);
        final float n28 = n8 * (n13 * n19 - n17 * n15) - n12 * (n9 * n19 - n17 * n11) + n16 * (n9 * n15 - n13 * n11);
        final float n29 = -(n4 * (n13 * n19 - n17 * n15) - n12 * (n5 * n19 - n17 * n7) + n16 * (n5 * n15 - n13 * n7));
        final float n30 = n4 * (n9 * n19 - n17 * n11) - n8 * (n5 * n19 - n17 * n7) + n16 * (n5 * n11 - n9 * n7);
        final float n31 = -(n4 * (n9 * n15 - n13 * n11) - n8 * (n5 * n15 - n13 * n7) + n12 * (n5 * n11 - n9 * n7));
        final float n32 = -(n8 * (n13 * n18 - n17 * n14) - n12 * (n9 * n18 - n17 * n10) + n16 * (n9 * n14 - n13 * n10));
        final float n33 = n4 * (n13 * n18 - n17 * n14) - n12 * (n5 * n18 - n17 * n6) + n16 * (n5 * n14 - n13 * n6);
        final float n34 = -(n4 * (n9 * n18 - n17 * n10) - n8 * (n5 * n18 - n17 * n6) + n16 * (n5 * n10 - n9 * n6));
        final float n35 = n4 * (n9 * n14 - n13 * n10) - n8 * (n5 * n14 - n13 * n6) + n12 * (n5 * n10 - n9 * n6);
        final float n36 = (n4 * n20 + n8 * n21 + n12 * n22 + n16 * n23) / n3;
        if (0.0f == n36) {
            return null;
        }
        array2[0 + n2] = n20 / n36;
        array2[1 + n2] = n21 / n36;
        array2[2 + n2] = n22 / n36;
        array2[3 + n2] = n23 / n36;
        array2[4 + n2] = n24 / n36;
        array2[5 + n2] = n25 / n36;
        array2[6 + n2] = n26 / n36;
        array2[7 + n2] = n27 / n36;
        array2[8 + n2] = n28 / n36;
        array2[9 + n2] = n29 / n36;
        array2[10 + n2] = n30 / n36;
        array2[11 + n2] = n31 / n36;
        array2[12 + n2] = n32 / n36;
        array2[13 + n2] = n33 / n36;
        array2[14 + n2] = n34 / n36;
        array2[15 + n2] = n35 / n36;
        return array2;
    }
    
    public static float[] invertMatrix(final float[] array, final float[] array2) {
        float abs = Math.abs(array[0]);
        for (int i = 1; i < 16; ++i) {
            final float abs2 = Math.abs(array[i]);
            if (abs2 > abs) {
                abs = abs2;
            }
        }
        if (0.0f == abs) {
            return null;
        }
        final float n = 1.0f / abs;
        final float n2 = array[0] * n;
        final float n3 = array[1] * n;
        final float n4 = array[2] * n;
        final float n5 = array[3] * n;
        final float n6 = array[4] * n;
        final float n7 = array[5] * n;
        final float n8 = array[6] * n;
        final float n9 = array[7] * n;
        final float n10 = array[8] * n;
        final float n11 = array[9] * n;
        final float n12 = array[10] * n;
        final float n13 = array[11] * n;
        final float n14 = array[12] * n;
        final float n15 = array[13] * n;
        final float n16 = array[14] * n;
        final float n17 = array[15] * n;
        final float n18 = n7 * (n12 * n17 - n16 * n13) - n11 * (n8 * n17 - n16 * n9) + n15 * (n8 * n13 - n12 * n9);
        final float n19 = -(n3 * (n12 * n17 - n16 * n13) - n11 * (n4 * n17 - n16 * n5) + n15 * (n4 * n13 - n12 * n5));
        final float n20 = n3 * (n8 * n17 - n16 * n9) - n7 * (n4 * n17 - n16 * n5) + n15 * (n4 * n9 - n8 * n5);
        final float n21 = -(n3 * (n8 * n13 - n12 * n9) - n7 * (n4 * n13 - n12 * n5) + n11 * (n4 * n9 - n8 * n5));
        final float n22 = -(n6 * (n12 * n17 - n16 * n13) - n10 * (n8 * n17 - n16 * n9) + n14 * (n8 * n13 - n12 * n9));
        final float n23 = n2 * (n12 * n17 - n16 * n13) - n10 * (n4 * n17 - n16 * n5) + n14 * (n4 * n13 - n12 * n5);
        final float n24 = -(n2 * (n8 * n17 - n16 * n9) - n6 * (n4 * n17 - n16 * n5) + n14 * (n4 * n9 - n8 * n5));
        final float n25 = n2 * (n8 * n13 - n12 * n9) - n6 * (n4 * n13 - n12 * n5) + n10 * (n4 * n9 - n8 * n5);
        final float n26 = n6 * (n11 * n17 - n15 * n13) - n10 * (n7 * n17 - n15 * n9) + n14 * (n7 * n13 - n11 * n9);
        final float n27 = -(n2 * (n11 * n17 - n15 * n13) - n10 * (n3 * n17 - n15 * n5) + n14 * (n3 * n13 - n11 * n5));
        final float n28 = n2 * (n7 * n17 - n15 * n9) - n6 * (n3 * n17 - n15 * n5) + n14 * (n3 * n9 - n7 * n5);
        final float n29 = -(n2 * (n7 * n13 - n11 * n9) - n6 * (n3 * n13 - n11 * n5) + n10 * (n3 * n9 - n7 * n5));
        final float n30 = -(n6 * (n11 * n16 - n15 * n12) - n10 * (n7 * n16 - n15 * n8) + n14 * (n7 * n12 - n11 * n8));
        final float n31 = n2 * (n11 * n16 - n15 * n12) - n10 * (n3 * n16 - n15 * n4) + n14 * (n3 * n12 - n11 * n4);
        final float n32 = -(n2 * (n7 * n16 - n15 * n8) - n6 * (n3 * n16 - n15 * n4) + n14 * (n3 * n8 - n7 * n4));
        final float n33 = n2 * (n7 * n12 - n11 * n8) - n6 * (n3 * n12 - n11 * n4) + n10 * (n3 * n8 - n7 * n4);
        final float n34 = (n2 * n18 + n6 * n19 + n10 * n20 + n14 * n21) / n;
        if (0.0f == n34) {
            return null;
        }
        array2[0] = n18 / n34;
        array2[1] = n19 / n34;
        array2[2] = n20 / n34;
        array2[3] = n21 / n34;
        array2[4] = n22 / n34;
        array2[5] = n23 / n34;
        array2[6] = n24 / n34;
        array2[7] = n25 / n34;
        array2[8] = n26 / n34;
        array2[9] = n27 / n34;
        array2[10] = n28 / n34;
        array2[11] = n29 / n34;
        array2[12] = n30 / n34;
        array2[13] = n31 / n34;
        array2[14] = n32 / n34;
        array2[15] = n33 / n34;
        return array2;
    }
    
    public static boolean mapObjToWinCoords(final float n, final float n2, final float n3, final float[] array, final int n4, final float[] array2, final int n5, final int[] array3, final int n6, final float[] array4, final int n7, final float[] array5, final float[] array6) {
        array5[0] = n;
        array5[1] = n2;
        array5[2] = n3;
        array5[3] = 1.0f;
        multMatrixVec(array, n4, array5, 0, array6, 0);
        multMatrixVec(array2, n5, array6, 0, array5, 0);
        if (array5[3] == 0.0f) {
            return false;
        }
        array5[3] = 1.0f / array5[3] * 0.5f;
        array5[0] = array5[0] * array5[3] + 0.5f;
        array5[1] = array5[1] * array5[3] + 0.5f;
        array5[2] = array5[2] * array5[3] + 0.5f;
        array4[0 + n7] = array5[0] * array3[2 + n6] + array3[0 + n6];
        array4[1 + n7] = array5[1] * array3[3 + n6] + array3[1 + n6];
        array4[2 + n7] = array5[2];
        return true;
    }
    
    public static boolean mapObjToWinCoords(final float n, final float n2, final float n3, final float[] array, final int[] array2, final int n4, final float[] array3, final int n5, final float[] array4, final float[] array5) {
        array5[0] = n;
        array5[1] = n2;
        array5[2] = n3;
        array5[3] = 1.0f;
        multMatrixVec(array, array5, array4);
        if (array4[3] == 0.0f) {
            return false;
        }
        array4[3] = 1.0f / array4[3] * 0.5f;
        array4[0] = array4[0] * array4[3] + 0.5f;
        array4[1] = array4[1] * array4[3] + 0.5f;
        array4[2] = array4[2] * array4[3] + 0.5f;
        array3[0 + n5] = array4[0] * array2[2 + n4] + array2[0 + n4];
        array3[1 + n5] = array4[1] * array2[3 + n4] + array2[1 + n4];
        array3[2 + n5] = array4[2];
        return true;
    }
    
    public static boolean mapWinToObjCoords(final float n, final float n2, final float n3, final float[] array, final int n4, final float[] array2, final int n5, final int[] array3, final int n6, final float[] array4, final int n7, final float[] array5, final float[] array6) {
        multMatrix(array2, n5, array, n4, array5, 0);
        if (null == invertMatrix(array5, array5)) {
            return false;
        }
        array6[0] = n;
        array6[1] = n2;
        array6[2] = n3;
        array6[3] = 1.0f;
        array6[0] = (array6[0] - array3[0 + n6]) / array3[2 + n6];
        array6[1] = (array6[1] - array3[1 + n6]) / array3[3 + n6];
        array6[0] = array6[0] * 2.0f - 1.0f;
        array6[1] = array6[1] * 2.0f - 1.0f;
        array6[2] = array6[2] * 2.0f - 1.0f;
        multMatrixVec(array5, 0, array6, 0, array6, 4);
        if (array6[7] == 0.0) {
            return false;
        }
        array6[7] = 1.0f / array6[7];
        array4[0 + n7] = array6[4] * array6[7];
        array4[1 + n7] = array6[5] * array6[7];
        array4[2 + n7] = array6[6] * array6[7];
        return true;
    }
    
    public static boolean mapWinToObjCoords(final float n, final float n2, final float n3, final float[] array, final int[] array2, final int n4, final float[] array3, final int n5, final float[] array4, final float[] array5) {
        array4[0] = n;
        array4[1] = n2;
        array4[2] = n3;
        array4[3] = 1.0f;
        array4[0] = (array4[0] - array2[0 + n4]) / array2[2 + n4];
        array4[1] = (array4[1] - array2[1 + n4]) / array2[3 + n4];
        array4[0] = array4[0] * 2.0f - 1.0f;
        array4[1] = array4[1] * 2.0f - 1.0f;
        array4[2] = array4[2] * 2.0f - 1.0f;
        multMatrixVec(array, array4, array5);
        if (array5[3] == 0.0) {
            return false;
        }
        array5[3] = 1.0f / array5[3];
        array3[0 + n5] = array5[0] * array5[3];
        array3[1 + n5] = array5[1] * array5[3];
        array3[2 + n5] = array5[2] * array5[3];
        return true;
    }
    
    public static boolean mapWinToObjCoords(final float n, final float n2, final float n3, final float n4, final float[] array, final int[] array2, final int n5, final float[] array3, final int n6, final float[] array4, final int n7, final float[] array5, final float[] array6) {
        array5[0] = n;
        array5[1] = n2;
        array5[3] = 1.0f;
        array5[0] = (array5[0] - array2[0 + n5]) / array2[2 + n5];
        array5[1] = (array5[1] - array2[1 + n5]) / array2[3 + n5];
        array5[0] = array5[0] * 2.0f - 1.0f;
        array5[1] = array5[1] * 2.0f - 1.0f;
        array5[2] = n3;
        array5[2] = array5[2] * 2.0f - 1.0f;
        multMatrixVec(array, array5, array6);
        if (array6[3] == 0.0) {
            return false;
        }
        array6[3] = 1.0f / array6[3];
        array3[0 + n6] = array6[0] * array6[3];
        array3[1 + n6] = array6[1] * array6[3];
        array3[2 + n6] = array6[2] * array6[3];
        array5[2] = n4;
        array5[2] = array5[2] * 2.0f - 1.0f;
        multMatrixVec(array, array5, array6);
        if (array6[3] == 0.0) {
            return false;
        }
        array6[3] = 1.0f / array6[3];
        array4[0 + n7] = array6[0] * array6[3];
        array4[1 + n7] = array6[1] * array6[3];
        array4[2 + n7] = array6[2] * array6[3];
        return true;
    }
    
    public static boolean mapWinToObjCoords(final float n, final float n2, final float n3, final float n4, final float[] array, final int n5, final float[] array2, final int n6, final int[] array3, final int n7, final float n8, final float n9, final float[] array4, final int n10, final float[] array5, final float[] array6) {
        multMatrix(array2, n6, array, n5, array5, 0);
        if (null == invertMatrix(array5, array5)) {
            return false;
        }
        array6[0] = n;
        array6[1] = n2;
        array6[2] = n3;
        array6[3] = 1.0f;
        array6[0] = (array6[0] - array3[0 + n7]) / array3[2 + n7];
        array6[1] = (array6[1] - array3[1 + n7]) / array3[3 + n7];
        array6[2] = (array6[2] - n8) / (n9 - n8);
        array6[0] = array6[0] * 2.0f - 1.0f;
        array6[1] = array6[1] * 2.0f - 1.0f;
        array6[2] = array6[2] * 2.0f - 1.0f;
        multMatrixVec(array5, 0, array6, 0, array6, 4);
        if (array6[7] == 0.0) {
            return false;
        }
        array6[7] = 1.0f / array6[7];
        array4[0 + n10] = array6[4];
        array4[1 + n10] = array6[5];
        array4[2 + n10] = array6[6];
        array4[3 + n10] = array6[7];
        return true;
    }
    
    public static boolean mapWinToRay(final float n, final float n2, final float n3, final float n4, final float[] array, final int n5, final float[] array2, final int n6, final int[] array3, final int n7, final Ray ray, final float[] array4, final float[] array5, final float[] array6) {
        multMatrix(array2, n6, array, n5, array4, 0);
        if (null == invertMatrix(array4, array4)) {
            return false;
        }
        if (mapWinToObjCoords(n, n2, n3, n4, array4, array3, n7, ray.orig, 0, ray.dir, 0, array5, array6)) {
            VectorUtil.normalizeVec3(VectorUtil.subVec3(ray.dir, ray.dir, ray.orig));
            return true;
        }
        return false;
    }
    
    public static float[] multMatrix(final float[] array, final int n, final float[] array2, final int n2, final float[] array3, final int n3) {
        final float n4 = array2[n2 + 0 + 0];
        final float n5 = array2[n2 + 1 + 0];
        final float n6 = array2[n2 + 2 + 0];
        final float n7 = array2[n2 + 3 + 0];
        final float n8 = array2[n2 + 0 + 4];
        final float n9 = array2[n2 + 1 + 4];
        final float n10 = array2[n2 + 2 + 4];
        final float n11 = array2[n2 + 3 + 4];
        final float n12 = array2[n2 + 0 + 8];
        final float n13 = array2[n2 + 1 + 8];
        final float n14 = array2[n2 + 2 + 8];
        final float n15 = array2[n2 + 3 + 8];
        final float n16 = array2[n2 + 0 + 12];
        final float n17 = array2[n2 + 1 + 12];
        final float n18 = array2[n2 + 2 + 12];
        final float n19 = array2[n2 + 3 + 12];
        final float n20 = array[n + 0];
        final float n21 = array[n + 4];
        final float n22 = array[n + 8];
        final float n23 = array[n + 12];
        array3[n3 + 0] = n20 * n4 + n21 * n5 + n22 * n6 + n23 * n7;
        array3[n3 + 4] = n20 * n8 + n21 * n9 + n22 * n10 + n23 * n11;
        array3[n3 + 8] = n20 * n12 + n21 * n13 + n22 * n14 + n23 * n15;
        array3[n3 + 12] = n20 * n16 + n21 * n17 + n22 * n18 + n23 * n19;
        final float n24 = array[n + 1 + 0];
        final float n25 = array[n + 1 + 4];
        final float n26 = array[n + 1 + 8];
        final float n27 = array[n + 1 + 12];
        array3[n3 + 1 + 0] = n24 * n4 + n25 * n5 + n26 * n6 + n27 * n7;
        array3[n3 + 1 + 4] = n24 * n8 + n25 * n9 + n26 * n10 + n27 * n11;
        array3[n3 + 1 + 8] = n24 * n12 + n25 * n13 + n26 * n14 + n27 * n15;
        array3[n3 + 1 + 12] = n24 * n16 + n25 * n17 + n26 * n18 + n27 * n19;
        final float n28 = array[n + 2 + 0];
        final float n29 = array[n + 2 + 4];
        final float n30 = array[n + 2 + 8];
        final float n31 = array[n + 2 + 12];
        array3[n3 + 2 + 0] = n28 * n4 + n29 * n5 + n30 * n6 + n31 * n7;
        array3[n3 + 2 + 4] = n28 * n8 + n29 * n9 + n30 * n10 + n31 * n11;
        array3[n3 + 2 + 8] = n28 * n12 + n29 * n13 + n30 * n14 + n31 * n15;
        array3[n3 + 2 + 12] = n28 * n16 + n29 * n17 + n30 * n18 + n31 * n19;
        final float n32 = array[n + 3 + 0];
        final float n33 = array[n + 3 + 4];
        final float n34 = array[n + 3 + 8];
        final float n35 = array[n + 3 + 12];
        array3[n3 + 3 + 0] = n32 * n4 + n33 * n5 + n34 * n6 + n35 * n7;
        array3[n3 + 3 + 4] = n32 * n8 + n33 * n9 + n34 * n10 + n35 * n11;
        array3[n3 + 3 + 8] = n32 * n12 + n33 * n13 + n34 * n14 + n35 * n15;
        array3[n3 + 3 + 12] = n32 * n16 + n33 * n17 + n34 * n18 + n35 * n19;
        return array3;
    }
    
    public static float[] multMatrix(final float[] array, final float[] array2, final float[] array3) {
        final float n = array2[0];
        final float n2 = array2[1];
        final float n3 = array2[2];
        final float n4 = array2[3];
        final float n5 = array2[4];
        final float n6 = array2[5];
        final float n7 = array2[6];
        final float n8 = array2[7];
        final float n9 = array2[8];
        final float n10 = array2[9];
        final float n11 = array2[10];
        final float n12 = array2[11];
        final float n13 = array2[12];
        final float n14 = array2[13];
        final float n15 = array2[14];
        final float n16 = array2[15];
        final float n17 = array[0];
        final float n18 = array[4];
        final float n19 = array[8];
        final float n20 = array[12];
        array3[0] = n17 * n + n18 * n2 + n19 * n3 + n20 * n4;
        array3[4] = n17 * n5 + n18 * n6 + n19 * n7 + n20 * n8;
        array3[8] = n17 * n9 + n18 * n10 + n19 * n11 + n20 * n12;
        array3[12] = n17 * n13 + n18 * n14 + n19 * n15 + n20 * n16;
        final float n21 = array[1];
        final float n22 = array[5];
        final float n23 = array[9];
        final float n24 = array[13];
        array3[1] = n21 * n + n22 * n2 + n23 * n3 + n24 * n4;
        array3[5] = n21 * n5 + n22 * n6 + n23 * n7 + n24 * n8;
        array3[9] = n21 * n9 + n22 * n10 + n23 * n11 + n24 * n12;
        array3[13] = n21 * n13 + n22 * n14 + n23 * n15 + n24 * n16;
        final float n25 = array[2];
        final float n26 = array[6];
        final float n27 = array[10];
        final float n28 = array[14];
        array3[2] = n25 * n + n26 * n2 + n27 * n3 + n28 * n4;
        array3[6] = n25 * n5 + n26 * n6 + n27 * n7 + n28 * n8;
        array3[10] = n25 * n9 + n26 * n10 + n27 * n11 + n28 * n12;
        array3[14] = n25 * n13 + n26 * n14 + n27 * n15 + n28 * n16;
        final float n29 = array[3];
        final float n30 = array[7];
        final float n31 = array[11];
        final float n32 = array[15];
        array3[3] = n29 * n + n30 * n2 + n31 * n3 + n32 * n4;
        array3[7] = n29 * n5 + n30 * n6 + n31 * n7 + n32 * n8;
        array3[11] = n29 * n9 + n30 * n10 + n31 * n11 + n32 * n12;
        array3[15] = n29 * n13 + n30 * n14 + n31 * n15 + n32 * n16;
        return array3;
    }
    
    public static float[] multMatrix(final float[] array, final int n, final float[] array2, final int n2) {
        final float n3 = array2[n2 + 0 + 0];
        final float n4 = array2[n2 + 1 + 0];
        final float n5 = array2[n2 + 2 + 0];
        final float n6 = array2[n2 + 3 + 0];
        final float n7 = array2[n2 + 0 + 4];
        final float n8 = array2[n2 + 1 + 4];
        final float n9 = array2[n2 + 2 + 4];
        final float n10 = array2[n2 + 3 + 4];
        final float n11 = array2[n2 + 0 + 8];
        final float n12 = array2[n2 + 1 + 8];
        final float n13 = array2[n2 + 2 + 8];
        final float n14 = array2[n2 + 3 + 8];
        final float n15 = array2[n2 + 0 + 12];
        final float n16 = array2[n2 + 1 + 12];
        final float n17 = array2[n2 + 2 + 12];
        final float n18 = array2[n2 + 3 + 12];
        final float n19 = array[n + 0];
        final float n20 = array[n + 4];
        final float n21 = array[n + 8];
        final float n22 = array[n + 12];
        array[n + 0] = n19 * n3 + n20 * n4 + n21 * n5 + n22 * n6;
        array[n + 4] = n19 * n7 + n20 * n8 + n21 * n9 + n22 * n10;
        array[n + 8] = n19 * n11 + n20 * n12 + n21 * n13 + n22 * n14;
        array[n + 12] = n19 * n15 + n20 * n16 + n21 * n17 + n22 * n18;
        final float n23 = array[n + 1 + 0];
        final float n24 = array[n + 1 + 4];
        final float n25 = array[n + 1 + 8];
        final float n26 = array[n + 1 + 12];
        array[n + 1 + 0] = n23 * n3 + n24 * n4 + n25 * n5 + n26 * n6;
        array[n + 1 + 4] = n23 * n7 + n24 * n8 + n25 * n9 + n26 * n10;
        array[n + 1 + 8] = n23 * n11 + n24 * n12 + n25 * n13 + n26 * n14;
        array[n + 1 + 12] = n23 * n15 + n24 * n16 + n25 * n17 + n26 * n18;
        final float n27 = array[n + 2 + 0];
        final float n28 = array[n + 2 + 4];
        final float n29 = array[n + 2 + 8];
        final float n30 = array[n + 2 + 12];
        array[n + 2 + 0] = n27 * n3 + n28 * n4 + n29 * n5 + n30 * n6;
        array[n + 2 + 4] = n27 * n7 + n28 * n8 + n29 * n9 + n30 * n10;
        array[n + 2 + 8] = n27 * n11 + n28 * n12 + n29 * n13 + n30 * n14;
        array[n + 2 + 12] = n27 * n15 + n28 * n16 + n29 * n17 + n30 * n18;
        final float n31 = array[n + 3 + 0];
        final float n32 = array[n + 3 + 4];
        final float n33 = array[n + 3 + 8];
        final float n34 = array[n + 3 + 12];
        array[n + 3 + 0] = n31 * n3 + n32 * n4 + n33 * n5 + n34 * n6;
        array[n + 3 + 4] = n31 * n7 + n32 * n8 + n33 * n9 + n34 * n10;
        array[n + 3 + 8] = n31 * n11 + n32 * n12 + n33 * n13 + n34 * n14;
        array[n + 3 + 12] = n31 * n15 + n32 * n16 + n33 * n17 + n34 * n18;
        return array;
    }
    
    public static float[] multMatrix(final float[] array, final float[] array2) {
        final float n = array2[0];
        final float n2 = array2[1];
        final float n3 = array2[2];
        final float n4 = array2[3];
        final float n5 = array2[4];
        final float n6 = array2[5];
        final float n7 = array2[6];
        final float n8 = array2[7];
        final float n9 = array2[8];
        final float n10 = array2[9];
        final float n11 = array2[10];
        final float n12 = array2[11];
        final float n13 = array2[12];
        final float n14 = array2[13];
        final float n15 = array2[14];
        final float n16 = array2[15];
        final float n17 = array[0];
        final float n18 = array[4];
        final float n19 = array[8];
        final float n20 = array[12];
        array[0] = n17 * n + n18 * n2 + n19 * n3 + n20 * n4;
        array[4] = n17 * n5 + n18 * n6 + n19 * n7 + n20 * n8;
        array[8] = n17 * n9 + n18 * n10 + n19 * n11 + n20 * n12;
        array[12] = n17 * n13 + n18 * n14 + n19 * n15 + n20 * n16;
        final float n21 = array[1];
        final float n22 = array[5];
        final float n23 = array[9];
        final float n24 = array[13];
        array[1] = n21 * n + n22 * n2 + n23 * n3 + n24 * n4;
        array[5] = n21 * n5 + n22 * n6 + n23 * n7 + n24 * n8;
        array[9] = n21 * n9 + n22 * n10 + n23 * n11 + n24 * n12;
        array[13] = n21 * n13 + n22 * n14 + n23 * n15 + n24 * n16;
        final float n25 = array[2];
        final float n26 = array[6];
        final float n27 = array[10];
        final float n28 = array[14];
        array[2] = n25 * n + n26 * n2 + n27 * n3 + n28 * n4;
        array[6] = n25 * n5 + n26 * n6 + n27 * n7 + n28 * n8;
        array[10] = n25 * n9 + n26 * n10 + n27 * n11 + n28 * n12;
        array[14] = n25 * n13 + n26 * n14 + n27 * n15 + n28 * n16;
        final float n29 = array[3];
        final float n30 = array[7];
        final float n31 = array[11];
        final float n32 = array[15];
        array[3] = n29 * n + n30 * n2 + n31 * n3 + n32 * n4;
        array[7] = n29 * n5 + n30 * n6 + n31 * n7 + n32 * n8;
        array[11] = n29 * n9 + n30 * n10 + n31 * n11 + n32 * n12;
        array[15] = n29 * n13 + n30 * n14 + n31 * n15 + n32 * n16;
        return array;
    }
    
    public static void multMatrix(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final float[] array) {
        final int position = floatBuffer.position();
        final int position2 = floatBuffer2.position();
        for (int i = 0; i < 4; ++i) {
            final int n = position + i;
            final float value = floatBuffer.get(n + 0);
            final float value2 = floatBuffer.get(n + 4);
            final float value3 = floatBuffer.get(n + 8);
            final float value4 = floatBuffer.get(n + 12);
            array[i + 0] = value * floatBuffer2.get(position2 + 0 + 0) + value2 * floatBuffer2.get(position2 + 1 + 0) + value3 * floatBuffer2.get(position2 + 2 + 0) + value4 * floatBuffer2.get(position2 + 3 + 0);
            array[i + 4] = value * floatBuffer2.get(position2 + 0 + 4) + value2 * floatBuffer2.get(position2 + 1 + 4) + value3 * floatBuffer2.get(position2 + 2 + 4) + value4 * floatBuffer2.get(position2 + 3 + 4);
            array[i + 8] = value * floatBuffer2.get(position2 + 0 + 8) + value2 * floatBuffer2.get(position2 + 1 + 8) + value3 * floatBuffer2.get(position2 + 2 + 8) + value4 * floatBuffer2.get(position2 + 3 + 8);
            array[i + 12] = value * floatBuffer2.get(position2 + 0 + 12) + value2 * floatBuffer2.get(position2 + 1 + 12) + value3 * floatBuffer2.get(position2 + 2 + 12) + value4 * floatBuffer2.get(position2 + 3 + 12);
        }
    }
    
    public static void multMatrix(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        final int position = floatBuffer.position();
        final int position2 = floatBuffer2.position();
        for (int i = 0; i < 4; ++i) {
            final int n = position + i;
            final float value = floatBuffer.get(n + 0);
            final float value2 = floatBuffer.get(n + 4);
            final float value3 = floatBuffer.get(n + 8);
            final float value4 = floatBuffer.get(n + 12);
            floatBuffer.put(n + 0, value * floatBuffer2.get(position2 + 0 + 0) + value2 * floatBuffer2.get(position2 + 1 + 0) + value3 * floatBuffer2.get(position2 + 2 + 0) + value4 * floatBuffer2.get(position2 + 3 + 0));
            floatBuffer.put(n + 4, value * floatBuffer2.get(position2 + 0 + 4) + value2 * floatBuffer2.get(position2 + 1 + 4) + value3 * floatBuffer2.get(position2 + 2 + 4) + value4 * floatBuffer2.get(position2 + 3 + 4));
            floatBuffer.put(n + 8, value * floatBuffer2.get(position2 + 0 + 8) + value2 * floatBuffer2.get(position2 + 1 + 8) + value3 * floatBuffer2.get(position2 + 2 + 8) + value4 * floatBuffer2.get(position2 + 3 + 8));
            floatBuffer.put(n + 12, value * floatBuffer2.get(position2 + 0 + 12) + value2 * floatBuffer2.get(position2 + 1 + 12) + value3 * floatBuffer2.get(position2 + 2 + 12) + value4 * floatBuffer2.get(position2 + 3 + 12));
        }
    }
    
    public static float[] multMatrixVec(final float[] array, final int n, final float[] array2, final int n2, final float[] array3, final int n3) {
        array3[0 + n3] = array2[0 + n2] * array[0 + n] + array2[1 + n2] * array[4 + n] + array2[2 + n2] * array[8 + n] + array2[3 + n2] * array[12 + n];
        final int n4 = 1 + n;
        array3[1 + n3] = array2[0 + n2] * array[0 + n4] + array2[1 + n2] * array[4 + n4] + array2[2 + n2] * array[8 + n4] + array2[3 + n2] * array[12 + n4];
        final int n5 = 2 + n;
        array3[2 + n3] = array2[0 + n2] * array[0 + n5] + array2[1 + n2] * array[4 + n5] + array2[2 + n2] * array[8 + n5] + array2[3 + n2] * array[12 + n5];
        final int n6 = 3 + n;
        array3[3 + n3] = array2[0 + n2] * array[0 + n6] + array2[1 + n2] * array[4 + n6] + array2[2 + n2] * array[8 + n6] + array2[3 + n2] * array[12 + n6];
        return array3;
    }
    
    public static float[] multMatrixVec(final float[] array, final float[] array2, final float[] array3) {
        array3[0] = array2[0] * array[0] + array2[1] * array[4] + array2[2] * array[8] + array2[3] * array[12];
        array3[1] = array2[0] * array[1] + array2[1] * array[5] + array2[2] * array[9] + array2[3] * array[13];
        array3[2] = array2[0] * array[2] + array2[1] * array[6] + array2[2] * array[10] + array2[3] * array[14];
        array3[3] = array2[0] * array[3] + array2[1] * array[7] + array2[2] * array[11] + array2[3] * array[15];
        return array3;
    }
    
    public static void multMatrixVec(final FloatBuffer floatBuffer, final float[] array, final float[] array2) {
        final int position = floatBuffer.position();
        for (int i = 0; i < 4; ++i) {
            final int n = i + position;
            array2[i] = array[0] * floatBuffer.get(0 + n) + array[1] * floatBuffer.get(4 + n) + array[2] * floatBuffer.get(8 + n) + array[3] * floatBuffer.get(12 + n);
        }
    }
    
    public static float[] copyMatrixColumn(final float[] array, final int n, final int n2, final float[] array2, final int n3) {
        array2[0 + n3] = array[0 + n2 * 4 + n];
        array2[1 + n3] = array[1 + n2 * 4 + n];
        array2[2 + n3] = array[2 + n2 * 4 + n];
        if (array2.length > 3 + n3) {
            array2[3 + n3] = array[3 + n2 * 4 + n];
        }
        return array2;
    }
    
    public static float[] copyMatrixRow(final float[] array, final int n, final int n2, final float[] array2, final int n3) {
        array2[0 + n3] = array[n2 + 0 + n];
        array2[1 + n3] = array[n2 + 4 + n];
        array2[2 + n3] = array[n2 + 8 + n];
        if (array2.length > 3 + n3) {
            array2[3 + n3] = array[n2 + 12 + n];
        }
        return array2;
    }
    
    public static StringBuilder matrixRowToString(StringBuilder sb, final String s, final FloatBuffer floatBuffer, final int n, final int n2, final int n3, final boolean b, final int n4) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final int n5 = n + floatBuffer.position();
        if (b) {
            for (int i = 0; i < n3; ++i) {
                sb.append(String.format(s + " ", floatBuffer.get(n5 + n4 * n3 + i)));
            }
        }
        else {
            for (int j = 0; j < n3; ++j) {
                sb.append(String.format(s + " ", floatBuffer.get(n5 + n4 + j * n2)));
            }
        }
        return sb;
    }
    
    public static StringBuilder matrixRowToString(StringBuilder sb, final String s, final float[] array, final int n, final int n2, final int n3, final boolean b, final int n4) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        if (b) {
            for (int i = 0; i < n3; ++i) {
                sb.append(String.format(s + " ", array[n + n4 * n3 + i]));
            }
        }
        else {
            for (int j = 0; j < n3; ++j) {
                sb.append(String.format(s + " ", array[n + n4 + j * n2]));
            }
        }
        return sb;
    }
    
    public static StringBuilder matrixToString(StringBuilder sb, final String s, final String s2, final FloatBuffer floatBuffer, final int n, final int n2, final int n3, final boolean b) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final String s3 = (null == s) ? "" : s;
        for (int i = 0; i < n2; ++i) {
            sb.append(s3).append("[ ");
            matrixRowToString(sb, s2, floatBuffer, n, n2, n3, b, i);
            sb.append("]").append(Platform.getNewline());
        }
        return sb;
    }
    
    public static StringBuilder matrixToString(StringBuilder sb, final String s, final String s2, final float[] array, final int n, final int n2, final int n3, final boolean b) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final String s3 = (null == s) ? "" : s;
        for (int i = 0; i < n2; ++i) {
            sb.append(s3).append("[ ");
            matrixRowToString(sb, s2, array, n, n2, n3, b, i);
            sb.append("]").append(Platform.getNewline());
        }
        return sb;
    }
    
    public static StringBuilder matrixToString(StringBuilder sb, final String s, final String s2, final FloatBuffer floatBuffer, final int n, final FloatBuffer floatBuffer2, final int n2, final int n3, final int n4, final boolean b) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final String s3 = (null == s) ? "" : s;
        for (int i = 0; i < n3; ++i) {
            sb.append(s3).append("[ ");
            matrixRowToString(sb, s2, floatBuffer, n, n3, n4, b, i);
            sb.append("=?= ");
            matrixRowToString(sb, s2, floatBuffer2, n2, n3, n4, b, i);
            sb.append("]").append(Platform.getNewline());
        }
        return sb;
    }
    
    public static StringBuilder matrixToString(StringBuilder sb, final String s, final String s2, final float[] array, final int n, final float[] array2, final int n2, final int n3, final int n4, final boolean b) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final String s3 = (null == s) ? "" : s;
        for (int i = 0; i < n3; ++i) {
            sb.append(s3).append("[ ");
            matrixRowToString(sb, s2, array, n, n3, n4, b, i);
            sb.append("=?= ");
            matrixRowToString(sb, s2, array2, n2, n3, n4, b, i);
            sb.append("]").append(Platform.getNewline());
        }
        return sb;
    }
    
    private static void calculateMachineEpsilonFloat() {
        float machEpsilon = 1.0f;
        int n = 0;
        do {
            machEpsilon /= 2.0f;
            ++n;
        } while (1.0f + machEpsilon / 2.0f != 1.0f);
        FloatUtil.machEpsilon = machEpsilon;
    }
    
    public static float getMachineEpsilon() {
        if (!FloatUtil.machEpsilonAvail) {
            synchronized (FloatUtil.class) {
                if (!FloatUtil.machEpsilonAvail) {
                    FloatUtil.machEpsilonAvail = true;
                    calculateMachineEpsilonFloat();
                }
            }
        }
        return FloatUtil.machEpsilon;
    }
    
    public static boolean isEqual(final float n, final float n2) {
        return Float.floatToIntBits(n) == Float.floatToIntBits(n2);
    }
    
    public static boolean isEqual(final float n, final float n2, final float n3) {
        return Math.abs(n - n2) < n3 || Float.floatToIntBits(n) == Float.floatToIntBits(n2);
    }
    
    public static int compare(final float n, final float n2) {
        if (n < n2) {
            return -1;
        }
        if (n > n2) {
            return 1;
        }
        final int floatToIntBits = Float.floatToIntBits(n);
        final int floatToIntBits2 = Float.floatToIntBits(n2);
        if (floatToIntBits == floatToIntBits2) {
            return 0;
        }
        if (floatToIntBits < floatToIntBits2) {
            return -1;
        }
        return 1;
    }
    
    public static int compare(final float n, final float n2, final float n3) {
        if (Math.abs(n - n2) < n3) {
            return 0;
        }
        return compare(n, n2);
    }
    
    public static boolean isZero(final float n, final float n2) {
        return Math.abs(n) < n2;
    }
    
    public static float abs(final float n) {
        return Math.abs(n);
    }
    
    public static float pow(final float n, final float n2) {
        return (float)Math.pow(n, n2);
    }
    
    public static float sin(final float n) {
        return (float)Math.sin(n);
    }
    
    public static float asin(final float n) {
        return (float)Math.asin(n);
    }
    
    public static float cos(final float n) {
        return (float)Math.cos(n);
    }
    
    public static float acos(final float n) {
        return (float)Math.acos(n);
    }
    
    public static float tan(final float n) {
        return (float)Math.tan(n);
    }
    
    public static float atan(final float n) {
        return (float)Math.atan(n);
    }
    
    public static float atan2(final float n, final float n2) {
        return (float)Math.atan2(n, n2);
    }
    
    public static float sqrt(final float n) {
        return (float)Math.sqrt(n);
    }
    
    public static float getZBufferEpsilon(final int n, final float n2, final float n3) {
        return n2 * n2 / (n3 * (1 << n) - n2);
    }
    
    public static int getZBufferValue(final int n, final float n2, final float n3, final float n4) {
        return (int)((1 << n) * (n4 / (n4 - n3) + n4 * n3 / (n3 - n4) / n2));
    }
    
    public static float getOrthoWinZ(final float n, final float n2, final float n3) {
        return (1.0f / n2 - 1.0f / n) / (1.0f / n2 - 1.0f / n3);
    }
    
    static {
        DEBUG = Debug.debug("Math");
        FloatUtil.machEpsilonAvail = false;
        FloatUtil.machEpsilon = 0.0f;
    }
}
