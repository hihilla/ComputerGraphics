// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

public class Quaternion
{
    private float x;
    private float y;
    private float z;
    private float w;
    public static final float ALLOWED_DEVIANCE = 1.0E-6f;
    
    public Quaternion() {
        final float x = 0.0f;
        this.z = x;
        this.y = x;
        this.x = x;
        this.w = 1.0f;
    }
    
    public Quaternion(final Quaternion quaternion) {
        this.set(quaternion);
    }
    
    public Quaternion(final float n, final float n2, final float n3, final float n4) {
        this.set(n, n2, n3, n4);
    }
    
    public final float magnitudeSquared() {
        return this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public final float magnitude() {
        final float magnitudeSquared = this.magnitudeSquared();
        if (FloatUtil.isZero(magnitudeSquared, 1.1920929E-7f)) {
            return 0.0f;
        }
        if (FloatUtil.isEqual(1.0f, magnitudeSquared, 1.1920929E-7f)) {
            return 1.0f;
        }
        return FloatUtil.sqrt(magnitudeSquared);
    }
    
    public final float getW() {
        return this.w;
    }
    
    public final void setW(final float w) {
        this.w = w;
    }
    
    public final float getX() {
        return this.x;
    }
    
    public final void setX(final float x) {
        this.x = x;
    }
    
    public final float getY() {
        return this.y;
    }
    
    public final void setY(final float y) {
        this.y = y;
    }
    
    public final float getZ() {
        return this.z;
    }
    
    public final void setZ(final float z) {
        this.z = z;
    }
    
    public final float dot(final float n, final float n2, final float n3, final float n4) {
        return this.x * n + this.y * n2 + this.z * n3 + this.w * n4;
    }
    
    public final float dot(final Quaternion quaternion) {
        return this.dot(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }
    
    public final boolean isIdentity() {
        return FloatUtil.isEqual(1.0f, this.w, 1.1920929E-7f) && VectorUtil.isZero(this.x, this.y, this.z, 1.1920929E-7f);
    }
    
    public final Quaternion setIdentity() {
        final float x = 0.0f;
        this.z = x;
        this.y = x;
        this.x = x;
        this.w = 1.0f;
        return this;
    }
    
    public final Quaternion normalize() {
        final float magnitude = this.magnitude();
        if (FloatUtil.isZero(magnitude, 1.1920929E-7f)) {
            this.setIdentity();
        }
        else {
            final float n = 1.0f / magnitude;
            this.w *= n;
            this.x *= n;
            this.y *= n;
            this.z *= n;
        }
        return this;
    }
    
    public Quaternion conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }
    
    public final Quaternion invert() {
        final float magnitudeSquared = this.magnitudeSquared();
        if (FloatUtil.isEqual(1.0f, magnitudeSquared, 1.1920929E-7f)) {
            this.conjugate();
        }
        else {
            final float n = 1.0f / magnitudeSquared;
            this.w *= n;
            this.x = -this.x * n;
            this.y = -this.y * n;
            this.z = -this.z * n;
        }
        return this;
    }
    
    public final Quaternion set(final Quaternion quaternion) {
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
        return this;
    }
    
    public final Quaternion set(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
    
    public final Quaternion add(final Quaternion quaternion) {
        this.x += quaternion.x;
        this.y += quaternion.y;
        this.z += quaternion.z;
        this.w += quaternion.w;
        return this;
    }
    
    public final Quaternion subtract(final Quaternion quaternion) {
        this.x -= quaternion.x;
        this.y -= quaternion.y;
        this.z -= quaternion.z;
        this.w -= quaternion.w;
        return this;
    }
    
    public final Quaternion mult(final Quaternion quaternion) {
        return this.set(this.w * quaternion.x + this.x * quaternion.w + this.y * quaternion.z - this.z * quaternion.y, this.w * quaternion.y - this.x * quaternion.z + this.y * quaternion.w + this.z * quaternion.x, this.w * quaternion.z + this.x * quaternion.y - this.y * quaternion.x + this.z * quaternion.w, this.w * quaternion.w - this.x * quaternion.x - this.y * quaternion.y - this.z * quaternion.z);
    }
    
    public final Quaternion scale(final float n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w *= n;
        return this;
    }
    
    public Quaternion rotateByAngleNormalAxis(final float n, final float n2, final float n3, final float n4) {
        if (VectorUtil.isZero(n2, n3, n4, 1.1920929E-7f)) {
            return this;
        }
        final float n5 = 0.5f * n;
        final float sin = FloatUtil.sin(n5);
        final float cos = FloatUtil.cos(n5);
        final float n6 = sin * n2;
        final float n7 = sin * n3;
        final float n8 = sin * n4;
        return this.set(this.x * cos + this.y * n8 - this.z * n7 + this.w * n6, -this.x * n8 + this.y * cos + this.z * n6 + this.w * n7, this.x * n7 - this.y * n6 + this.z * cos + this.w * n8, -this.x * n6 - this.y * n7 - this.z * n8 + this.w * cos);
    }
    
    public Quaternion rotateByAngleX(final float n) {
        final float n2 = 0.5f * n;
        final float sin = FloatUtil.sin(n2);
        final float cos = FloatUtil.cos(n2);
        return this.set(this.x * cos + this.w * sin, this.y * cos + this.z * sin, -this.y * sin + this.z * cos, -this.x * sin + this.w * cos);
    }
    
    public Quaternion rotateByAngleY(final float n) {
        final float n2 = 0.5f * n;
        final float sin = FloatUtil.sin(n2);
        final float cos = FloatUtil.cos(n2);
        return this.set(this.x * cos - this.z * sin, this.y * cos + this.w * sin, this.x * sin + this.z * cos, -this.y * sin + this.w * cos);
    }
    
    public Quaternion rotateByAngleZ(final float n) {
        final float n2 = 0.5f * n;
        final float sin = FloatUtil.sin(n2);
        final float cos = FloatUtil.cos(n2);
        return this.set(this.x * cos + this.y * sin, -this.x * sin + this.y * cos, this.z * cos + this.w * sin, -this.z * sin + this.w * cos);
    }
    
    public final Quaternion rotateByEuler(final float[] array) {
        return this.rotateByEuler(array[0], array[1], array[2]);
    }
    
    public final Quaternion rotateByEuler(final float n, final float n2, final float n3) {
        if (VectorUtil.isZero(n, n2, n3, 1.1920929E-7f)) {
            return this;
        }
        return this.rotateByAngleY(n2).rotateByAngleZ(n3).rotateByAngleX(n).normalize();
    }
    
    public final float[] rotateVector(final float[] array, final int n, final float[] array2, final int n2) {
        if (VectorUtil.isVec3Zero(array2, n2, 1.1920929E-7f)) {
            array[0 + n] = 0.0f;
            array[2 + n] = (array[1 + n] = 0.0f);
        }
        else {
            final float n3 = array2[0 + n2];
            final float n4 = array2[1 + n2];
            final float n5 = array2[2 + n2];
            final float n6 = this.x * this.x;
            final float n7 = this.y * this.y;
            final float n8 = this.z * this.z;
            final float n9 = this.w * this.w;
            array[0 + n] = n9 * n3 + n6 * n3 - n8 * n3 - n7 * n3 + 2.0f * (this.y * this.w * n5 - this.z * this.w * n4 + this.y * this.x * n4 + this.z * this.x * n5);
            array[1 + n] = n7 * n4 - n8 * n4 + n9 * n4 - n6 * n4 + 2.0f * (this.x * this.y * n3 + this.z * this.y * n5 + this.w * this.z * n3 - this.x * this.w * n5);
            array[2 + n] = n8 * n5 - n7 * n5 - n6 * n5 + n9 * n5 + 2.0f * (this.x * this.z * n3 + this.y * this.z * n4 - this.w * this.y * n3 + this.w * this.x * n4);
        }
        return array;
    }
    
    public final Quaternion setSlerp(final Quaternion quaternion, final Quaternion quaternion2, final float n) {
        if (n == 0.0f) {
            this.set(quaternion);
        }
        else if (n == 1.0f) {
            this.set(quaternion2);
        }
        else {
            float x = quaternion2.x;
            float y = quaternion2.y;
            float z = quaternion2.z;
            float w = quaternion2.w;
            float n2 = quaternion.x * x + quaternion.y * y + quaternion.z * z + quaternion.w * w;
            float n3;
            float n4;
            if (n2 >= 0.95f) {
                n3 = 1.0f - n;
                n4 = n;
            }
            else if (n2 <= -0.99f) {
                n3 = 0.5f;
                n4 = 0.5f;
            }
            else {
                if (n2 <= -1.1920929E-7f) {
                    x *= -1.0f;
                    y *= -1.0f;
                    z *= -1.0f;
                    w *= -1.0f;
                    n2 *= -1.0f;
                }
                final float acos = FloatUtil.acos(n2);
                final float sqrt = FloatUtil.sqrt(1.0f - n2 * n2);
                if (Math.abs(sqrt) < 0.001f) {
                    n3 = 0.5f;
                    n4 = 0.5f;
                }
                else {
                    n3 = FloatUtil.sin((1.0f - n) * acos) / sqrt;
                    n4 = FloatUtil.sin(n * acos) / sqrt;
                }
            }
            this.x = quaternion.x * n3 + x * n4;
            this.y = quaternion.y * n3 + y * n4;
            this.z = quaternion.z * n3 + z * n4;
            this.w = quaternion.w * n3 + w * n4;
        }
        return this;
    }
    
    public Quaternion setLookAt(final float[] array, final float[] array2, final float[] array3, final float[] array4, final float[] array5) {
        VectorUtil.normalizeVec3(array5, array);
        VectorUtil.normalizeVec3(array4, array2);
        VectorUtil.crossVec3(array3, array4, array5);
        VectorUtil.normalizeVec3(array3);
        VectorUtil.crossVec3(array4, array5, array3);
        VectorUtil.normalizeVec3(array4);
        return this.setFromAxes(array3, array4, array5).normalize();
    }
    
    public final Quaternion setFromVectors(final float[] array, final float[] array2, final float[] array3, final float[] array4) {
        final float n = VectorUtil.normVec3(array) * VectorUtil.normVec3(array2);
        if (FloatUtil.isZero(n, 1.1920929E-7f)) {
            return this.setIdentity();
        }
        final float n2 = VectorUtil.dotVec3(array, array2) / n;
        final float acos = FloatUtil.acos(Math.max(-1.0f, Math.min(n2, 1.0f)));
        VectorUtil.crossVec3(array3, array, array2);
        if (n2 < 0.0f && FloatUtil.isZero(VectorUtil.normVec3(array3), 1.1920929E-7f)) {
            int n3;
            if (Math.abs(array[0]) > Math.abs(array[1])) {
                if (Math.abs(array[0]) > Math.abs(array[2])) {
                    n3 = 0;
                }
                else {
                    n3 = 2;
                }
            }
            else if (Math.abs(array[1]) > Math.abs(array[2])) {
                n3 = 1;
            }
            else {
                n3 = 2;
            }
            array3[n3] = -array[(n3 + 1) % 3];
            array3[(n3 + 1) % 3] = array[n3];
            array3[(n3 + 2) % 3] = 0.0f;
        }
        return this.setFromAngleAxis(acos, array3, array4);
    }
    
    public final Quaternion setFromNormalVectors(final float[] array, final float[] array2, final float[] array3) {
        final float n = VectorUtil.normVec3(array) * VectorUtil.normVec3(array2);
        if (FloatUtil.isZero(n, 1.1920929E-7f)) {
            return this.setIdentity();
        }
        final float n2 = VectorUtil.dotVec3(array, array2) / n;
        final float acos = FloatUtil.acos(Math.max(-1.0f, Math.min(n2, 1.0f)));
        VectorUtil.crossVec3(array3, array, array2);
        if (n2 < 0.0f && FloatUtil.isZero(VectorUtil.normVec3(array3), 1.1920929E-7f)) {
            int n3;
            if (Math.abs(array[0]) > Math.abs(array[1])) {
                if (Math.abs(array[0]) > Math.abs(array[2])) {
                    n3 = 0;
                }
                else {
                    n3 = 2;
                }
            }
            else if (Math.abs(array[1]) > Math.abs(array[2])) {
                n3 = 1;
            }
            else {
                n3 = 2;
            }
            array3[n3] = -array[(n3 + 1) % 3];
            array3[(n3 + 1) % 3] = array[n3];
            array3[(n3 + 2) % 3] = 0.0f;
        }
        return this.setFromAngleNormalAxis(acos, array3);
    }
    
    public final Quaternion setFromAngleAxis(final float n, final float[] array, final float[] array2) {
        VectorUtil.normalizeVec3(array2, array);
        return this.setFromAngleNormalAxis(n, array2);
    }
    
    public final Quaternion setFromAngleNormalAxis(final float n, final float[] array) {
        if (VectorUtil.isVec3Zero(array, 0, 1.1920929E-7f)) {
            this.setIdentity();
        }
        else {
            final float n2 = n * 0.5f;
            final float sin = FloatUtil.sin(n2);
            this.x = array[0] * sin;
            this.y = array[1] * sin;
            this.z = array[2] * sin;
            this.w = FloatUtil.cos(n2);
        }
        return this;
    }
    
    public final float toAngleAxis(final float[] array) {
        final float n = this.x * this.x + this.y * this.y + this.z * this.z;
        float n2;
        if (FloatUtil.isZero(n, 1.1920929E-7f)) {
            n2 = 0.0f;
            array[0] = 1.0f;
            array[2] = (array[1] = 0.0f);
        }
        else {
            n2 = FloatUtil.acos(this.w) * 2.0f;
            final float n3 = 1.0f / FloatUtil.sqrt(n);
            array[0] = this.x * n3;
            array[1] = this.y * n3;
            array[2] = this.z * n3;
        }
        return n2;
    }
    
    public final Quaternion setFromEuler(final float[] array) {
        return this.setFromEuler(array[0], array[1], array[2]);
    }
    
    public final Quaternion setFromEuler(final float n, final float n2, final float n3) {
        if (VectorUtil.isZero(n, n2, n3, 1.1920929E-7f)) {
            return this.setIdentity();
        }
        final float n4 = n2 * 0.5f;
        final float sin = FloatUtil.sin(n4);
        final float cos = FloatUtil.cos(n4);
        final float n5 = n3 * 0.5f;
        final float sin2 = FloatUtil.sin(n5);
        final float cos2 = FloatUtil.cos(n5);
        final float n6 = n * 0.5f;
        final float sin3 = FloatUtil.sin(n6);
        final float cos3 = FloatUtil.cos(n6);
        final float n7 = cos * cos2;
        final float n8 = sin * sin2;
        final float n9 = cos * sin2;
        final float n10 = sin * cos2;
        this.w = n7 * cos3 - n8 * sin3;
        this.x = n7 * sin3 + n8 * cos3;
        this.y = n10 * cos3 + n9 * sin3;
        this.z = n9 * cos3 - n10 * sin3;
        return this.normalize();
    }
    
    public float[] toEuler(final float[] array) {
        final float n = this.w * this.w;
        final float n2 = this.x * this.x;
        final float n3 = this.y * this.y;
        final float n4 = this.z * this.z;
        final float n5 = n2 + n3 + n4 + n;
        final float n6 = this.x * this.y + this.z * this.w;
        if (n6 > 0.499f * n5) {
            array[0] = 0.0f;
            array[1] = 2.0f * FloatUtil.atan2(this.x, this.w);
            array[2] = 1.5707964f;
        }
        else if (n6 < -0.499f * n5) {
            array[0] = 0.0f;
            array[1] = -2.0f * FloatUtil.atan2(this.x, this.w);
            array[2] = -1.5707964f;
        }
        else {
            array[0] = FloatUtil.atan2(2.0f * this.x * this.w - 2.0f * this.y * this.z, -n2 + n3 - n4 + n);
            array[1] = FloatUtil.atan2(2.0f * this.y * this.w - 2.0f * this.x * this.z, n2 - n3 - n4 + n);
            array[2] = FloatUtil.asin(2.0f * n6 / n5);
        }
        return array;
    }
    
    public final Quaternion setFromMatrix(final float[] array, final int n) {
        return this.setFromMatrix(array[0 + n], array[4 + n], array[8 + n], array[1 + n], array[5 + n], array[9 + n], array[2 + n], array[6 + n], array[10 + n]);
    }
    
    public Quaternion setFromMatrix(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        final float n10 = n + n5 + n9 + 1.0f;
        if (n10 > 0.0f) {
            final float n11 = 0.5f / FloatUtil.sqrt(n10);
            this.w = 0.25f / n11;
            this.x = (n8 - n6) * n11;
            this.y = (n3 - n7) * n11;
            this.z = (n4 - n2) * n11;
        }
        else if (n > n5 && n > n9) {
            final float n12 = 0.5f / FloatUtil.sqrt(1.0f + n - n5 - n9);
            this.w = (n8 - n6) * n12;
            this.x = 0.25f / n12;
            this.y = (n4 + n2) * n12;
            this.z = (n3 + n7) * n12;
        }
        else if (n5 > n9) {
            final float n13 = 0.5f / FloatUtil.sqrt(1.0f + n5 - n - n9);
            this.w = (n3 - n7) * n13;
            this.x = (n7 + n2) * n13;
            this.y = 0.25f / n13;
            this.z = (n8 + n6) * n13;
        }
        else {
            final float n14 = 0.5f / FloatUtil.sqrt(1.0f + n9 - n - n5);
            this.w = (n4 - n2) * n14;
            this.x = (n3 + n7) * n14;
            this.y = (n8 + n6) * n14;
            this.z = 0.25f / n14;
        }
        return this;
    }
    
    public final float[] toMatrix(final float[] array, final int n) {
        final float magnitudeSquared = this.magnitudeSquared();
        if (FloatUtil.isZero(magnitudeSquared, 1.1920929E-7f)) {
            return FloatUtil.makeIdentity(array, n);
        }
        float n2;
        if (FloatUtil.isEqual(1.0f, magnitudeSquared, 1.1920929E-7f)) {
            n2 = 2.0f;
        }
        else {
            n2 = 2.0f / magnitudeSquared;
        }
        final float n3 = n2 * this.x;
        final float n4 = n2 * this.y;
        final float n5 = n2 * this.z;
        final float n6 = this.x * n3;
        final float n7 = this.x * n4;
        final float n8 = this.x * n5;
        final float n9 = n3 * this.w;
        final float n10 = this.y * n4;
        final float n11 = this.y * n5;
        final float n12 = n4 * this.w;
        final float n13 = this.z * n5;
        final float n14 = n5 * this.w;
        array[0 + n] = 1.0f - (n10 + n13);
        array[4 + n] = n7 - n14;
        array[8 + n] = n8 + n12;
        array[12 + n] = 0.0f;
        array[1 + n] = n7 + n14;
        array[5 + n] = 1.0f - (n6 + n13);
        array[9 + n] = n11 - n9;
        array[13 + n] = 0.0f;
        array[2 + n] = n8 - n12;
        array[6 + n] = n11 + n9;
        array[10 + n] = 1.0f - (n6 + n10);
        array[3 + n] = (array[14 + n] = 0.0f);
        array[11 + n] = (array[7 + n] = 0.0f);
        array[15 + n] = 1.0f;
        return array;
    }
    
    public float[] copyMatrixColumn(final int n, final float[] array, final int n2) {
        final float magnitudeSquared = this.magnitudeSquared();
        float n3;
        if (FloatUtil.isZero(magnitudeSquared, 1.1920929E-7f)) {
            n3 = 0.0f;
        }
        else if (FloatUtil.isEqual(1.0f, magnitudeSquared, 1.1920929E-7f)) {
            n3 = 2.0f;
        }
        else {
            n3 = 2.0f / magnitudeSquared;
        }
        final float n4 = this.x * n3;
        final float n5 = this.y * n3;
        final float n6 = this.z * n3;
        final float n7 = this.x * n4;
        final float n8 = this.x * n5;
        final float n9 = this.x * n6;
        final float n10 = this.w * n4;
        final float n11 = this.y * n5;
        final float n12 = this.y * n6;
        final float n13 = this.w * n5;
        final float n14 = this.z * n6;
        final float n15 = this.w * n6;
        switch (n) {
            case 0: {
                array[0 + n2] = 1.0f - (n11 + n14);
                array[1 + n2] = n8 + n15;
                array[2 + n2] = n9 - n13;
                break;
            }
            case 1: {
                array[0 + n2] = n8 - n15;
                array[1 + n2] = 1.0f - (n7 + n14);
                array[2 + n2] = n12 + n10;
                break;
            }
            case 2: {
                array[0 + n2] = n9 + n13;
                array[1 + n2] = n12 - n10;
                array[2 + n2] = 1.0f - (n7 + n11);
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid column index. " + n);
            }
        }
        return array;
    }
    
    public final Quaternion setFromAxes(final float[] array, final float[] array2, final float[] array3) {
        return this.setFromMatrix(array[0], array2[0], array3[0], array[1], array2[1], array3[1], array[2], array2[2], array3[2]);
    }
    
    public void toAxes(final float[] array, final float[] array2, final float[] array3, final float[] array4) {
        this.toMatrix(array4, 0);
        FloatUtil.copyMatrixColumn(array4, 0, 2, array3, 0);
        FloatUtil.copyMatrixColumn(array4, 0, 1, array2, 0);
        FloatUtil.copyMatrixColumn(array4, 0, 0, array, 0);
    }
    
    public final boolean isRotationMatrix3f(final float[] array) {
        return FloatUtil.abs(array[0] * array[3] + array[3] * array[4] + array[6] * array[7]) <= 0.01f && FloatUtil.abs(array[0] * array[2] + array[3] * array[5] + array[6] * array[8]) <= 0.01f && FloatUtil.abs(array[1] * array[2] + array[4] * array[5] + array[7] * array[8]) <= 0.01f && FloatUtil.abs(array[0] * array[0] + array[3] * array[3] + array[6] * array[6] - 1.0f) <= 0.01f && FloatUtil.abs(array[1] * array[1] + array[4] * array[4] + array[7] * array[7] - 1.0f) <= 0.01f && FloatUtil.abs(array[2] * array[2] + array[5] * array[5] + array[8] * array[8] - 1.0f) <= 0.01f && FloatUtil.abs(this.determinant3f(array) - 1.0f) < 0.01f;
    }
    
    private final float determinant3f(final float[] array) {
        return array[0] * array[4] * array[8] + array[3] * array[7] * array[2] + array[6] * array[1] * array[5] - array[0] * array[7] * array[5] - array[3] * array[1] * array[8] - array[6] * array[4] * array[2];
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quaternion)) {
            return false;
        }
        final Quaternion quaternion = (Quaternion)o;
        return Math.abs(this.x - quaternion.getX()) <= 1.0E-6f && Math.abs(this.y - quaternion.getY()) <= 1.0E-6f && Math.abs(this.z - quaternion.getZ()) <= 1.0E-6f && Math.abs(this.w - quaternion.getW()) <= 1.0E-6f;
    }
    
    @Override
    public final int hashCode() {
        throw new InternalError("hashCode not designed");
    }
    
    @Override
    public String toString() {
        return "Quaternion[x " + this.x + ", y " + this.y + ", z " + this.z + ", w " + this.w + "]";
    }
}
