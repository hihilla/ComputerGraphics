// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

import java.util.ArrayList;

public final class VectorUtil
{
    public static final float[] VEC3_ONE;
    public static final float[] VEC3_ZERO;
    public static final float[] VEC3_UNIT_Y;
    public static final float[] VEC3_UNIT_Y_NEG;
    public static final float[] VEC3_UNIT_Z;
    public static final float[] VEC3_UNIT_Z_NEG;
    
    public static float[] copyVec2(final float[] array, final int n, final float[] array2, final int n2) {
        System.arraycopy(array2, n2, array, n, 2);
        return array;
    }
    
    public static float[] copyVec3(final float[] array, final int n, final float[] array2, final int n2) {
        System.arraycopy(array2, n2, array, n, 3);
        return array;
    }
    
    public static float[] copyVec4(final float[] array, final int n, final float[] array2, final int n2) {
        System.arraycopy(array2, n2, array, n, 4);
        return array;
    }
    
    public static boolean isVec2Equal(final float[] array, final int n, final float[] array2, final int n2) {
        return FloatUtil.isEqual(array[0 + n], array2[0 + n2]) && FloatUtil.isEqual(array[1 + n], array2[1 + n2]);
    }
    
    public static boolean isVec3Equal(final float[] array, final int n, final float[] array2, final int n2) {
        return FloatUtil.isEqual(array[0 + n], array2[0 + n2]) && FloatUtil.isEqual(array[1 + n], array2[1 + n2]) && FloatUtil.isEqual(array[2 + n], array2[2 + n2]);
    }
    
    public static boolean isVec2Equal(final float[] array, final int n, final float[] array2, final int n2, final float n3) {
        return FloatUtil.isEqual(array[0 + n], array2[0 + n2], n3) && FloatUtil.isEqual(array[1 + n], array2[1 + n2], n3);
    }
    
    public static boolean isVec3Equal(final float[] array, final int n, final float[] array2, final int n2, final float n3) {
        return FloatUtil.isEqual(array[0 + n], array2[0 + n2], n3) && FloatUtil.isEqual(array[1 + n], array2[1 + n2], n3) && FloatUtil.isEqual(array[2 + n], array2[2 + n2], n3);
    }
    
    public static boolean isVec2Zero(final float[] array, final int n) {
        return 0.0f == array[0 + n] && 0.0f == array[1 + n];
    }
    
    public static boolean isVec3Zero(final float[] array, final int n) {
        return 0.0f == array[0 + n] && 0.0f == array[1 + n] && 0.0f == array[2 + n];
    }
    
    public static boolean isVec2Zero(final float[] array, final int n, final float n2) {
        return isZero(array[0 + n], array[1 + n], n2);
    }
    
    public static boolean isVec3Zero(final float[] array, final int n, final float n2) {
        return isZero(array[0 + n], array[1 + n], array[2 + n], n2);
    }
    
    public static boolean isZero(final float n, final float n2, final float n3) {
        return FloatUtil.isZero(n, n3) && FloatUtil.isZero(n2, n3);
    }
    
    public static boolean isZero(final float n, final float n2, final float n3, final float n4) {
        return FloatUtil.isZero(n, n4) && FloatUtil.isZero(n2, n4) && FloatUtil.isZero(n3, n4);
    }
    
    public static float distSquareVec3(final float[] array, final float[] array2) {
        final float n = array[0] - array2[0];
        final float n2 = array[1] - array2[1];
        final float n3 = array[2] - array2[2];
        return n * n + n2 * n2 + n3 * n3;
    }
    
    public static float distVec3(final float[] array, final float[] array2) {
        return FloatUtil.sqrt(distSquareVec3(array, array2));
    }
    
    public static float dotVec3(final float[] array, final float[] array2) {
        return array[0] * array2[0] + array[1] * array2[1] + array[2] * array2[2];
    }
    
    public static float cosAngleVec3(final float[] array, final float[] array2) {
        return dotVec3(array, array2) / (normVec3(array) * normVec3(array2));
    }
    
    public static float angleVec3(final float[] array, final float[] array2) {
        return FloatUtil.acos(cosAngleVec3(array, array2));
    }
    
    public static float normSquareVec2(final float[] array) {
        return array[0] * array[0] + array[1] * array[1];
    }
    
    public static float normSquareVec2(final float[] array, final int n) {
        final float n2 = array[0 + n];
        final float n3 = n2 * n2;
        final float n4 = array[1 + n];
        return n3 + n4 * n4;
    }
    
    public static float normSquareVec3(final float[] array) {
        return array[0] * array[0] + array[1] * array[1] + array[2] * array[2];
    }
    
    public static float normSquareVec3(final float[] array, final int n) {
        final float n2 = array[0 + n];
        final float n3 = n2 * n2;
        final float n4 = array[1 + n];
        final float n5 = n3 + n4 * n4;
        final float n6 = array[2 + n];
        return n5 + n6 * n6;
    }
    
    public static float normVec2(final float[] array) {
        return FloatUtil.sqrt(normSquareVec2(array));
    }
    
    public static float normVec3(final float[] array) {
        return FloatUtil.sqrt(normSquareVec3(array));
    }
    
    public static float[] normalizeVec2(final float[] array, final float[] array2) {
        final float normSquareVec2 = normSquareVec2(array2);
        if (FloatUtil.isZero(normSquareVec2, 1.1920929E-7f)) {
            array[1] = (array[0] = 0.0f);
        }
        else {
            final float n = 1.0f / FloatUtil.sqrt(normSquareVec2);
            array[0] = array2[0] * n;
            array[1] = array2[1] * n;
        }
        return array;
    }
    
    public static float[] normalizeVec2(final float[] array) {
        final float normSquareVec2 = normSquareVec2(array);
        if (FloatUtil.isZero(normSquareVec2, 1.1920929E-7f)) {
            array[1] = (array[0] = 0.0f);
        }
        else {
            final float n = 1.0f / FloatUtil.sqrt(normSquareVec2);
            final int n2 = 0;
            array[n2] *= n;
            final int n3 = 1;
            array[n3] *= n;
        }
        return array;
    }
    
    public static float[] normalizeVec3(final float[] array, final float[] array2) {
        final float normSquareVec3 = normSquareVec3(array2);
        if (FloatUtil.isZero(normSquareVec3, 1.1920929E-7f)) {
            array[0] = 0.0f;
            array[2] = (array[1] = 0.0f);
        }
        else {
            final float n = 1.0f / FloatUtil.sqrt(normSquareVec3);
            array[0] = array2[0] * n;
            array[1] = array2[1] * n;
            array[2] = array2[2] * n;
        }
        return array;
    }
    
    public static float[] normalizeVec3(final float[] array) {
        final float normSquareVec3 = normSquareVec3(array);
        if (FloatUtil.isZero(normSquareVec3, 1.1920929E-7f)) {
            array[0] = 0.0f;
            array[2] = (array[1] = 0.0f);
        }
        else {
            final float n = 1.0f / FloatUtil.sqrt(normSquareVec3);
            final int n2 = 0;
            array[n2] *= n;
            final int n3 = 1;
            array[n3] *= n;
            final int n4 = 2;
            array[n4] *= n;
        }
        return array;
    }
    
    public static float[] normalizeVec3(final float[] array, final int n) {
        final float normSquareVec3 = normSquareVec3(array, n);
        if (FloatUtil.isZero(normSquareVec3, 1.1920929E-7f)) {
            array[0 + n] = 0.0f;
            array[2 + n] = (array[1 + n] = 0.0f);
        }
        else {
            final float n2 = 1.0f / FloatUtil.sqrt(normSquareVec3);
            final int n3 = 0 + n;
            array[n3] *= n2;
            final int n4 = 1 + n;
            array[n4] *= n2;
            final int n5 = 2 + n;
            array[n5] *= n2;
        }
        return array;
    }
    
    public static float[] scaleVec2(final float[] array, final float[] array2, final float n) {
        array[0] = array2[0] * n;
        array[1] = array2[1] * n;
        return array;
    }
    
    public static float[] scaleVec3(final float[] array, final float[] array2, final float n) {
        array[0] = array2[0] * n;
        array[1] = array2[1] * n;
        array[2] = array2[2] * n;
        return array;
    }
    
    public static float[] scaleVec3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] * array3[0];
        array[1] = array2[1] * array3[1];
        array[2] = array2[2] * array3[2];
        return array;
    }
    
    public static float[] scaleVec2(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] * array3[0];
        array[1] = array2[1] * array3[1];
        return array;
    }
    
    public static float[] divVec2(final float[] array, final float[] array2, final float n) {
        array[0] = array2[0] / n;
        array[1] = array2[1] / n;
        return array;
    }
    
    public static float[] divVec3(final float[] array, final float[] array2, final float n) {
        array[0] = array2[0] / n;
        array[1] = array2[1] / n;
        array[2] = array2[2] / n;
        return array;
    }
    
    public static float[] divVec3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] / array3[0];
        array[1] = array2[1] / array3[1];
        array[2] = array2[2] / array3[2];
        return array;
    }
    
    public static float[] divVec2(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] / array3[0];
        array[1] = array2[1] / array3[1];
        return array;
    }
    
    public static float[] addVec2(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] + array3[0];
        array[1] = array2[1] + array3[1];
        return array;
    }
    
    public static float[] addVec3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] + array3[0];
        array[1] = array2[1] + array3[1];
        array[2] = array2[2] + array3[2];
        return array;
    }
    
    public static float[] subVec2(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] - array3[0];
        array[1] = array2[1] - array3[1];
        return array;
    }
    
    public static float[] subVec3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[0] - array3[0];
        array[1] = array2[1] - array3[1];
        array[2] = array2[2] - array3[2];
        return array;
    }
    
    public static float[] crossVec3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[1] * array3[2] - array2[2] * array3[1];
        array[1] = array2[2] * array3[0] - array2[0] * array3[2];
        array[2] = array2[0] * array3[1] - array2[1] * array3[0];
        return array;
    }
    
    public static float[] crossVec3(final float[] array, final int n, final float[] array2, final int n2, final float[] array3, final int n3) {
        array[0 + n] = array2[1 + n2] * array3[2 + n3] - array2[2 + n2] * array3[1 + n3];
        array[1 + n] = array2[2 + n2] * array3[0 + n3] - array2[0 + n2] * array3[2 + n3];
        array[2 + n] = array2[0 + n2] * array3[1 + n3] - array2[1 + n2] * array3[0 + n3];
        return array;
    }
    
    public static float[] mulColMat4Vec3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array3[0] * array2[0] + array3[1] * array2[4] + array3[2] * array2[8] + array2[12];
        array[1] = array3[0] * array2[1] + array3[1] * array2[5] + array3[2] * array2[9] + array2[13];
        array[2] = array3[0] * array2[2] + array3[1] * array2[6] + array3[2] * array2[10] + array2[14];
        return array;
    }
    
    public static float[] mulRowMat4Vec3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array3[0] * array2[0] + array3[1] * array2[1] + array3[2] * array2[2] + array2[3];
        array[1] = array3[0] * array2[4] + array3[1] * array2[5] + array3[2] * array2[6] + array2[7];
        array[2] = array3[0] * array2[8] + array3[1] * array2[9] + array3[2] * array2[10] + array2[11];
        return array;
    }
    
    public static float mid(final float n, final float n2) {
        return (n + n2) * 0.5f;
    }
    
    public static float[] midVec3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = (array2[0] + array3[0]) * 0.5f;
        array[1] = (array2[1] + array3[1]) * 0.5f;
        array[2] = (array2[2] + array3[2]) * 0.5f;
        return array;
    }
    
    public static float determinantVec3(final float[] array, final float[] array2, final float[] array3) {
        return array[0] * array2[1] * array3[2] + array[1] * array2[2] * array3[0] + array[2] * array2[0] * array3[1] - array[0] * array2[2] * array3[1] - array[1] * array2[0] * array3[2] - array[2] * array2[1] * array3[0];
    }
    
    public static boolean isCollinearVec3(final float[] array, final float[] array2, final float[] array3) {
        return FloatUtil.isZero(determinantVec3(array, array2, array3), 1.1920929E-7f);
    }
    
    public static boolean isInCircleVec2(final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3, final Vert2fImmutable vert2fImmutable4) {
        final float[] coord = vert2fImmutable.getCoord();
        final float[] coord2 = vert2fImmutable2.getCoord();
        final float[] coord3 = vert2fImmutable3.getCoord();
        final float[] coord4 = vert2fImmutable4.getCoord();
        return (coord[0] * coord[0] + coord[1] * coord[1]) * triAreaVec2(coord2, coord3, coord4) - (coord2[0] * coord2[0] + coord2[1] * coord2[1]) * triAreaVec2(coord, coord3, coord4) + (coord3[0] * coord3[0] + coord3[1] * coord3[1]) * triAreaVec2(coord, coord2, coord4) - (coord4[0] * coord4[0] + coord4[1] * coord4[1]) * triAreaVec2(coord, coord2, coord3) > 0.0f;
    }
    
    public static float triAreaVec2(final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3) {
        final float[] coord = vert2fImmutable.getCoord();
        final float[] coord2 = vert2fImmutable2.getCoord();
        final float[] coord3 = vert2fImmutable3.getCoord();
        return (coord2[0] - coord[0]) * (coord3[1] - coord[1]) - (coord2[1] - coord[1]) * (coord3[0] - coord[0]);
    }
    
    public static float triAreaVec2(final float[] array, final float[] array2, final float[] array3) {
        return (array2[0] - array[0]) * (array3[1] - array[1]) - (array2[1] - array[1]) * (array3[0] - array[0]);
    }
    
    public static boolean isInTriangleVec3(final float[] array, final float[] array2, final float[] array3, final float[] array4, final float[] array5, final float[] array6, final float[] array7) {
        subVec3(array5, array3, array);
        subVec3(array6, array2, array);
        subVec3(array7, array4, array);
        final float dotVec3 = dotVec3(array5, array5);
        final float dotVec4 = dotVec3(array5, array6);
        final float dotVec5 = dotVec3(array6, array6);
        final float dotVec6 = dotVec3(array5, array7);
        final float dotVec7 = dotVec3(array6, array7);
        final float n = 1.0f / (dotVec3 * dotVec5 - dotVec4 * dotVec4);
        final float n2 = (dotVec5 * dotVec6 - dotVec4 * dotVec7) * n;
        final float n3 = (dotVec3 * dotVec7 - dotVec4 * dotVec6) * n;
        return n2 >= 0.0f && n3 >= 0.0f && n2 + n3 < 1.0f;
    }
    
    public static boolean isVec3InTriangle3(final float[] array, final float[] array2, final float[] array3, final float[] array4, final float[] array5, final float[] array6, final float[] array7, final float[] array8, final float[] array9) {
        subVec3(array7, array3, array);
        subVec3(array8, array2, array);
        final float dotVec3 = dotVec3(array7, array7);
        final float dotVec4 = dotVec3(array7, array8);
        final float dotVec5 = dotVec3(array8, array8);
        final float n = 1.0f / (dotVec3 * dotVec5 - dotVec4 * dotVec4);
        subVec3(array9, array4, array);
        final float dotVec6 = dotVec3(array7, array9);
        final float dotVec7 = dotVec3(array8, array9);
        final float n2 = (dotVec5 * dotVec6 - dotVec4 * dotVec7) * n;
        final float n3 = (dotVec3 * dotVec7 - dotVec4 * dotVec6) * n;
        if (n2 >= 0.0f && n3 >= 0.0f && n2 + n3 < 1.0f) {
            return true;
        }
        subVec3(array9, array4, array);
        final float dotVec8 = dotVec3(array7, array9);
        final float dotVec9 = dotVec3(array8, array9);
        final float n4 = (dotVec5 * dotVec8 - dotVec4 * dotVec9) * n;
        final float n5 = (dotVec3 * dotVec9 - dotVec4 * dotVec8) * n;
        if (n4 >= 0.0f && n5 >= 0.0f && n4 + n5 < 1.0f) {
            return true;
        }
        subVec3(array9, array5, array);
        final float dotVec10 = dotVec3(array7, array9);
        final float dotVec11 = dotVec3(array8, array9);
        final float n6 = (dotVec5 * dotVec10 - dotVec4 * dotVec11) * n;
        final float n7 = (dotVec3 * dotVec11 - dotVec4 * dotVec10) * n;
        return n6 >= 0.0f && n7 >= 0.0f && n6 + n7 < 1.0f;
    }
    
    public static boolean isVec3InTriangle3(final float[] array, final float[] array2, final float[] array3, final float[] array4, final float[] array5, final float[] array6, final float[] array7, final float[] array8, final float[] array9, final float n) {
        subVec3(array7, array3, array);
        subVec3(array8, array2, array);
        final float dotVec3 = dotVec3(array7, array7);
        final float dotVec4 = dotVec3(array7, array8);
        final float dotVec5 = dotVec3(array8, array8);
        final float n2 = 1.0f / (dotVec3 * dotVec5 - dotVec4 * dotVec4);
        subVec3(array9, array4, array);
        final float dotVec6 = dotVec3(array7, array9);
        final float dotVec7 = dotVec3(array8, array9);
        final float n3 = (dotVec5 * dotVec6 - dotVec4 * dotVec7) * n2;
        final float n4 = (dotVec3 * dotVec7 - dotVec4 * dotVec6) * n2;
        if (FloatUtil.compare(n3, 0.0f, n) >= 0 && FloatUtil.compare(n4, 0.0f, n) >= 0 && FloatUtil.compare(n3 + n4, 1.0f, n) < 0) {
            return true;
        }
        subVec3(array9, array4, array);
        final float dotVec8 = dotVec3(array7, array9);
        final float dotVec9 = dotVec3(array8, array9);
        final float n5 = (dotVec5 * dotVec8 - dotVec4 * dotVec9) * n2;
        final float n6 = (dotVec3 * dotVec9 - dotVec4 * dotVec8) * n2;
        if (FloatUtil.compare(n5, 0.0f, n) >= 0 && FloatUtil.compare(n6, 0.0f, n) >= 0 && FloatUtil.compare(n5 + n6, 1.0f, n) < 0) {
            return true;
        }
        subVec3(array9, array5, array);
        final float dotVec10 = dotVec3(array7, array9);
        final float dotVec11 = dotVec3(array8, array9);
        final float n7 = (dotVec5 * dotVec10 - dotVec4 * dotVec11) * n2;
        final float n8 = (dotVec3 * dotVec11 - dotVec4 * dotVec10) * n2;
        return FloatUtil.compare(n7, 0.0f, n) >= 0 && FloatUtil.compare(n8, 0.0f, n) >= 0 && FloatUtil.compare(n7 + n8, 1.0f, n) < 0;
    }
    
    public static boolean ccw(final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3) {
        return triAreaVec2(vert2fImmutable, vert2fImmutable2, vert2fImmutable3) > 0.0f;
    }
    
    public static Winding getWinding(final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3) {
        return (triAreaVec2(vert2fImmutable, vert2fImmutable2, vert2fImmutable3) > 0.0f) ? Winding.CCW : Winding.CW;
    }
    
    public static float area(final ArrayList<? extends Vert2fImmutable> list) {
        final int size = list.size();
        float n = 0.0f;
        int n2 = size - 1;
        for (int i = 0; i < size; n2 = i++) {
            final float[] coord = ((Vert2fImmutable)list.get(n2)).getCoord();
            final float[] coord2 = ((Vert2fImmutable)list.get(i)).getCoord();
            n += coord[0] * coord2[1] - coord2[0] * coord[1];
        }
        return n;
    }
    
    public static Winding getWinding(final ArrayList<? extends Vert2fImmutable> list) {
        return (area(list) >= 0.0f) ? Winding.CCW : Winding.CW;
    }
    
    public static float[] getNormalVec2(final float[] array, final float[] array2, final float[] array3) {
        subVec2(array, array3, array2);
        final float n = array[0];
        array[0] = -array[1];
        array[1] = n;
        return normalizeVec2(array);
    }
    
    public static float[] getNormalVec3(final float[] array, final float[] array2, final float[] array3, final float[] array4, final float[] array5, final float[] array6) {
        subVec3(array5, array3, array2);
        subVec3(array6, array4, array2);
        return normalizeVec3(crossVec3(array, array5, array6));
    }
    
    public static float[] getPlaneVec3(final float[] array, final float[] array2, final float[] array3) {
        System.arraycopy(array2, 0, array, 0, 3);
        array[3] = -dotVec3(array2, array3);
        return array;
    }
    
    public static float[] getPlaneVec3(final float[] array, final float[] array2, final float[] array3, final float[] array4, final float[] array5, final float[] array6) {
        getNormalVec3(array, array2, array3, array4, array5, array6);
        array[3] = -dotVec3(array, array2);
        return array;
    }
    
    public static float[] line2PlaneIntersection(final float[] array, final Ray ray, final float[] array2, final float n) {
        final float dotVec3 = dotVec3(ray.dir, array2);
        if (Math.abs(dotVec3) < n) {
            return null;
        }
        scaleVec3(array, ray.dir, -(dotVec3(ray.orig, array2) + array2[3]) / dotVec3);
        return addVec3(array, array, ray.orig);
    }
    
    public static float[] seg2SegIntersection(final float[] array, final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3, final Vert2fImmutable vert2fImmutable4) {
        final float n = (vert2fImmutable.getX() - vert2fImmutable2.getX()) * (vert2fImmutable3.getY() - vert2fImmutable4.getY()) - (vert2fImmutable.getY() - vert2fImmutable2.getY()) * (vert2fImmutable3.getX() - vert2fImmutable4.getX());
        if (n == 0.0f) {
            return null;
        }
        final float n2 = vert2fImmutable.getX() * vert2fImmutable2.getY() - vert2fImmutable.getY() * vert2fImmutable2.getX();
        final float n3 = vert2fImmutable3.getX() * vert2fImmutable4.getY() - vert2fImmutable3.getY() * vert2fImmutable4.getY();
        final float n4 = ((vert2fImmutable3.getX() - vert2fImmutable4.getX()) * n2 - (vert2fImmutable.getX() - vert2fImmutable2.getX()) * n3) / n;
        final float n5 = ((vert2fImmutable3.getY() - vert2fImmutable4.getY()) * n2 - (vert2fImmutable.getY() - vert2fImmutable2.getY()) * n3) / n;
        final float n6 = (n4 - vert2fImmutable.getX()) / (vert2fImmutable2.getX() - vert2fImmutable.getX());
        final float n7 = (n4 - vert2fImmutable3.getX()) / (vert2fImmutable4.getX() - vert2fImmutable3.getX());
        if (n6 <= 0.0f || n6 >= 1.0f) {
            return null;
        }
        if (n7 <= 0.0f || n7 >= 1.0f) {
            return null;
        }
        array[0] = n4;
        array[1] = n5;
        array[2] = 0.0f;
        return array;
    }
    
    public static boolean testSeg2SegIntersection(final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3, final Vert2fImmutable vert2fImmutable4) {
        final float[] coord = vert2fImmutable.getCoord();
        final float[] coord2 = vert2fImmutable2.getCoord();
        final float[] coord3 = vert2fImmutable3.getCoord();
        final float[] coord4 = vert2fImmutable4.getCoord();
        final float n = (coord[0] - coord2[0]) * (coord3[1] - coord4[1]) - (coord[1] - coord2[1]) * (coord3[0] - coord4[0]);
        if (n == 0.0f) {
            return false;
        }
        final float n2 = ((coord3[0] - coord4[0]) * (coord[0] * coord2[1] - coord[1] * coord2[0]) - (coord[0] - coord2[0]) * (coord3[0] * coord4[1] - coord3[1] * coord4[1])) / n;
        final float n3 = (n2 - coord[0]) / (coord2[0] - coord[0]);
        final float n4 = (n2 - coord3[0]) / (coord4[0] - coord3[0]);
        return n3 > 0.0f && n3 < 1.0f && n4 > 0.0f && n4 < 1.0f;
    }
    
    public static boolean testSeg2SegIntersection(final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3, final Vert2fImmutable vert2fImmutable4, final float n) {
        final float[] coord = vert2fImmutable.getCoord();
        final float[] coord2 = vert2fImmutable2.getCoord();
        final float[] coord3 = vert2fImmutable3.getCoord();
        final float[] coord4 = vert2fImmutable4.getCoord();
        final float n2 = (coord[0] - coord2[0]) * (coord3[1] - coord4[1]) - (coord[1] - coord2[1]) * (coord3[0] - coord4[0]);
        if (FloatUtil.isZero(n2, n)) {
            return false;
        }
        final float n3 = ((coord3[0] - coord4[0]) * (coord[0] * coord2[1] - coord[1] * coord2[0]) - (coord[0] - coord2[0]) * (coord3[0] * coord4[1] - coord3[1] * coord4[1])) / n2;
        final float n4 = (n3 - coord[0]) / (coord2[0] - coord[0]);
        final float n5 = (n3 - coord3[0]) / (coord4[0] - coord3[0]);
        return FloatUtil.compare(n4, 0.0f, n) > 0 && FloatUtil.compare(n4, 1.0f, n) < 0 && FloatUtil.compare(n5, 0.0f, n) > 0 && FloatUtil.compare(n5, 1.0f, n) < 0 && n4 > 0.0f && n4 < 1.0f && n5 > 0.0f && n5 < 1.0f;
    }
    
    public static float[] line2lineIntersection(final float[] array, final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3, final Vert2fImmutable vert2fImmutable4) {
        final float n = (vert2fImmutable.getX() - vert2fImmutable2.getX()) * (vert2fImmutable3.getY() - vert2fImmutable4.getY()) - (vert2fImmutable.getY() - vert2fImmutable2.getY()) * (vert2fImmutable3.getX() - vert2fImmutable4.getX());
        if (n == 0.0f) {
            return null;
        }
        final float n2 = vert2fImmutable.getX() * vert2fImmutable2.getY() - vert2fImmutable.getY() * vert2fImmutable2.getX();
        final float n3 = vert2fImmutable3.getX() * vert2fImmutable4.getY() - vert2fImmutable3.getY() * vert2fImmutable4.getY();
        final float n4 = ((vert2fImmutable3.getX() - vert2fImmutable4.getX()) * n2 - (vert2fImmutable.getX() - vert2fImmutable2.getX()) * n3) / n;
        final float n5 = ((vert2fImmutable3.getY() - vert2fImmutable4.getY()) * n2 - (vert2fImmutable.getY() - vert2fImmutable2.getY()) * n3) / n;
        array[0] = n4;
        array[1] = n5;
        array[2] = 0.0f;
        return array;
    }
    
    public static boolean testTri2SegIntersection(final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3, final Vert2fImmutable vert2fImmutable4, final Vert2fImmutable vert2fImmutable5) {
        return testSeg2SegIntersection(vert2fImmutable, vert2fImmutable2, vert2fImmutable4, vert2fImmutable5) || testSeg2SegIntersection(vert2fImmutable2, vert2fImmutable3, vert2fImmutable4, vert2fImmutable5) || testSeg2SegIntersection(vert2fImmutable, vert2fImmutable3, vert2fImmutable4, vert2fImmutable5);
    }
    
    public static boolean testTri2SegIntersection(final Vert2fImmutable vert2fImmutable, final Vert2fImmutable vert2fImmutable2, final Vert2fImmutable vert2fImmutable3, final Vert2fImmutable vert2fImmutable4, final Vert2fImmutable vert2fImmutable5, final float n) {
        return testSeg2SegIntersection(vert2fImmutable, vert2fImmutable2, vert2fImmutable4, vert2fImmutable5, n) || testSeg2SegIntersection(vert2fImmutable2, vert2fImmutable3, vert2fImmutable4, vert2fImmutable5, n) || testSeg2SegIntersection(vert2fImmutable, vert2fImmutable3, vert2fImmutable4, vert2fImmutable5, n);
    }
    
    static {
        VEC3_ONE = new float[] { 1.0f, 1.0f, 1.0f };
        VEC3_ZERO = new float[] { 0.0f, 0.0f, 0.0f };
        VEC3_UNIT_Y = new float[] { 0.0f, 1.0f, 0.0f };
        VEC3_UNIT_Y_NEG = new float[] { 0.0f, -1.0f, 0.0f };
        VEC3_UNIT_Z = new float[] { 0.0f, 0.0f, 1.0f };
        VEC3_UNIT_Z_NEG = new float[] { 0.0f, 0.0f, -1.0f };
    }
    
    public enum Winding
    {
        CW(-1), 
        CCW(1);
        
        public final int dir;
        
        private Winding(final int dir) {
            this.dir = dir;
        }
    }
}
