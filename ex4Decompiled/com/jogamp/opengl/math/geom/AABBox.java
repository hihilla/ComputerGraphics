// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math.geom;

import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.Quaternion;
import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.opengl.math.Ray;
import jogamp.graph.geom.plane.AffineTransform;

public class AABBox
{
    private static final boolean DEBUG;
    private final float[] low;
    private final float[] high;
    private final float[] center;
    
    public AABBox() {
        this.low = new float[3];
        this.high = new float[3];
        this.center = new float[3];
        this.reset();
    }
    
    public AABBox(final AABBox aabBox) {
        this.low = new float[3];
        this.high = new float[3];
        this.center = new float[3];
        this.copy(aabBox);
    }
    
    public AABBox(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.low = new float[3];
        this.high = new float[3];
        this.center = new float[3];
        this.setSize(n, n2, n3, n4, n5, n6);
    }
    
    public AABBox(final float[] array, final float[] array2) {
        this.low = new float[3];
        this.high = new float[3];
        this.center = new float[3];
        this.setSize(array, array2);
    }
    
    public final AABBox reset() {
        this.setLow(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        this.setHigh(-3.4028235E38f, -3.4028235E38f, -3.4028235E38f);
        this.center[0] = 0.0f;
        this.center[1] = 0.0f;
        this.center[2] = 0.0f;
        return this;
    }
    
    public final float[] getHigh() {
        return this.high;
    }
    
    private final void setHigh(final float n, final float n2, final float n3) {
        this.high[0] = n;
        this.high[1] = n2;
        this.high[2] = n3;
    }
    
    public final float[] getLow() {
        return this.low;
    }
    
    private final void setLow(final float n, final float n2, final float n3) {
        this.low[0] = n;
        this.low[1] = n2;
        this.low[2] = n3;
    }
    
    private final void computeCenter() {
        this.center[0] = (this.high[0] + this.low[0]) / 2.0f;
        this.center[1] = (this.high[1] + this.low[1]) / 2.0f;
        this.center[2] = (this.high[2] + this.low[2]) / 2.0f;
    }
    
    public final AABBox copy(final AABBox aabBox) {
        System.arraycopy(aabBox.low, 0, this.low, 0, 3);
        System.arraycopy(aabBox.high, 0, this.high, 0, 3);
        System.arraycopy(aabBox.center, 0, this.center, 0, 3);
        return this;
    }
    
    public final AABBox setSize(final float[] array, final float[] array2) {
        return this.setSize(array[0], array[1], array[2], array2[0], array2[1], array2[2]);
    }
    
    public final AABBox setSize(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.low[0] = n;
        this.low[1] = n2;
        this.low[2] = n3;
        this.high[0] = n4;
        this.high[1] = n5;
        this.high[2] = n6;
        this.computeCenter();
        return this;
    }
    
    public final AABBox resize(final AABBox aabBox) {
        final float[] low = aabBox.getLow();
        final float[] high = aabBox.getHigh();
        if (low[0] < this.low[0]) {
            this.low[0] = low[0];
        }
        if (low[1] < this.low[1]) {
            this.low[1] = low[1];
        }
        if (low[2] < this.low[2]) {
            this.low[2] = low[2];
        }
        if (high[0] > this.high[0]) {
            this.high[0] = high[0];
        }
        if (high[1] > this.high[1]) {
            this.high[1] = high[1];
        }
        if (high[2] > this.high[2]) {
            this.high[2] = high[2];
        }
        this.computeCenter();
        return this;
    }
    
    public final AABBox resize(final AABBox aabBox, final AffineTransform affineTransform, final float[] array) {
        final float[] low = aabBox.getLow();
        affineTransform.transform(low, array);
        array[2] = low[2];
        if (array[0] < this.low[0]) {
            this.low[0] = array[0];
        }
        if (array[1] < this.low[1]) {
            this.low[1] = array[1];
        }
        if (array[2] < this.low[2]) {
            this.low[2] = array[2];
        }
        final float[] high = aabBox.getHigh();
        affineTransform.transform(high, array);
        array[2] = high[2];
        if (array[0] > this.high[0]) {
            this.high[0] = array[0];
        }
        if (array[1] > this.high[1]) {
            this.high[1] = array[1];
        }
        if (array[2] > this.high[2]) {
            this.high[2] = array[2];
        }
        this.computeCenter();
        return this;
    }
    
    public final AABBox resize(final float n, final float n2, final float n3) {
        if (n < this.low[0]) {
            this.low[0] = n;
        }
        if (n2 < this.low[1]) {
            this.low[1] = n2;
        }
        if (n3 < this.low[2]) {
            this.low[2] = n3;
        }
        if (n > this.high[0]) {
            this.high[0] = n;
        }
        if (n2 > this.high[1]) {
            this.high[1] = n2;
        }
        if (n3 > this.high[2]) {
            this.high[2] = n3;
        }
        this.computeCenter();
        return this;
    }
    
    public final AABBox resize(final float[] array, final int n) {
        return this.resize(array[0 + n], array[1 + n], array[2 + n]);
    }
    
    public final AABBox resize(final float[] array) {
        return this.resize(array[0], array[1], array[2]);
    }
    
    public final boolean contains(final float n, final float n2) {
        return n >= this.low[0] && n <= this.high[0] && n2 >= this.low[1] && n2 <= this.high[1];
    }
    
    public final boolean contains(final float n, final float n2, final float n3) {
        return n >= this.low[0] && n <= this.high[0] && n2 >= this.low[1] && n2 <= this.high[1] && n3 >= this.low[2] && n3 <= this.high[2];
    }
    
    public final boolean intersects2DRegion(final float n, final float n2, final float n3, final float n4) {
        if (n3 <= 0.0f || n4 <= 0.0f) {
            return false;
        }
        final float width = this.getWidth();
        final float height = this.getHeight();
        if (width <= 0.0f || height <= 0.0f) {
            return false;
        }
        final float minX = this.getMinX();
        final float minY = this.getMinY();
        return n + n3 > minX && n2 + n4 > minY && n < minX + width && n2 < minY + height;
    }
    
    public final boolean intersectsRay(final Ray ray) {
        final float n = ray.dir[0];
        final float n2 = ray.orig[0] - this.center[0];
        final float n3 = this.high[0] - this.center[0];
        if (Math.abs(n2) > n3 && n2 * n >= 0.0f) {
            return false;
        }
        final float n4 = ray.dir[1];
        final float n5 = ray.orig[1] - this.center[1];
        final float n6 = this.high[1] - this.center[1];
        if (Math.abs(n5) > n6 && n5 * n4 >= 0.0f) {
            return false;
        }
        final float n7 = ray.dir[2];
        final float n8 = ray.orig[2] - this.center[2];
        final float n9 = this.high[2] - this.center[2];
        if (Math.abs(n8) > n9 && n8 * n7 >= 0.0f) {
            return false;
        }
        final float abs = Math.abs(n4);
        final float abs2 = Math.abs(n7);
        if (Math.abs(n4 * n8 - n7 * n5) > n6 * abs2 + n9 * abs) {
            return false;
        }
        final float abs3 = Math.abs(n);
        return Math.abs(n7 * n2 - n * n8) <= n3 * abs2 + n9 * abs3 && Math.abs(n * n5 - n4 * n2) <= n3 * abs + n6 * abs3;
    }
    
    public final float[] getRayIntersection(final float[] array, final Ray ray, final float n, final boolean b, final float[] array2, final float[] array3, final float[] array4) {
        final float[] array5 = { -1.0f, -1.0f, -1.0f };
        final float[] orig = ray.orig;
        final float[] dir = ray.dir;
        boolean b2 = true;
        for (int i = 0; i < 3; ++i) {
            if (orig[i] < this.low[i]) {
                array[i] = this.low[i];
                b2 = false;
                if (0 != Float.floatToIntBits(dir[i])) {
                    array5[i] = (this.low[i] - orig[i]) / dir[i];
                }
            }
            else if (orig[i] > this.high[i]) {
                array[i] = this.high[i];
                b2 = false;
                if (0 != Float.floatToIntBits(dir[i])) {
                    array5[i] = (this.high[i] - orig[i]) / dir[i];
                }
            }
        }
        if (b2) {
            System.arraycopy(orig, 0, array, 0, 3);
            return array;
        }
        int n2 = 0;
        if (array5[1] > array5[n2]) {
            n2 = 1;
        }
        if (array5[2] > array5[n2]) {
            n2 = 2;
        }
        if (!b) {
            if (0x0 != (Float.floatToIntBits(array5[n2]) & Integer.MIN_VALUE)) {
                return null;
            }
            switch (n2) {
                case 0: {
                    array[1] = orig[1] + array5[n2] * dir[1];
                    if (array[1] < this.low[1] - n || array[1] > this.high[1] + n) {
                        return null;
                    }
                    array[2] = orig[2] + array5[n2] * dir[2];
                    if (array[2] < this.low[2] - n || array[2] > this.high[2] + n) {
                        return null;
                    }
                    break;
                }
                case 1: {
                    array[0] = orig[0] + array5[n2] * dir[0];
                    if (array[0] < this.low[0] - n || array[0] > this.high[0] + n) {
                        return null;
                    }
                    array[2] = orig[2] + array5[n2] * dir[2];
                    if (array[2] < this.low[2] - n || array[2] > this.high[2] + n) {
                        return null;
                    }
                    break;
                }
                case 2: {
                    array[0] = orig[0] + array5[n2] * dir[0];
                    if (array[0] < this.low[0] - n || array[0] > this.high[0] + n) {
                        return null;
                    }
                    array[1] = orig[1] + array5[n2] * dir[1];
                    if (array[1] < this.low[1] - n || array[1] > this.high[1] + n) {
                        return null;
                    }
                    break;
                }
                default: {
                    throw new InternalError("XXX");
                }
            }
        }
        else {
            switch (n2) {
                case 0: {
                    array[1] = orig[1] + array5[n2] * dir[1];
                    array[2] = orig[2] + array5[n2] * dir[2];
                    break;
                }
                case 1: {
                    array[0] = orig[0] + array5[n2] * dir[0];
                    array[2] = orig[2] + array5[n2] * dir[2];
                    break;
                }
                case 2: {
                    array[0] = orig[0] + array5[n2] * dir[0];
                    array[1] = orig[1] + array5[n2] * dir[1];
                    break;
                }
                default: {
                    throw new InternalError("XXX");
                }
            }
        }
        return array;
    }
    
    public final float getSize() {
        return VectorUtil.distVec3(this.low, this.high);
    }
    
    public final float[] getCenter() {
        return this.center;
    }
    
    public final AABBox scale(final float n, final float[] array) {
        array[0] = this.high[0] - this.center[0];
        array[1] = this.high[1] - this.center[1];
        array[2] = this.high[2] - this.center[2];
        VectorUtil.scaleVec3(array, array, n);
        VectorUtil.addVec3(this.high, this.center, array);
        array[0] = this.low[0] - this.center[0];
        array[1] = this.low[1] - this.center[1];
        array[2] = this.low[2] - this.center[2];
        VectorUtil.scaleVec3(array, array, n);
        VectorUtil.addVec3(this.low, this.center, array);
        return this;
    }
    
    public final AABBox translate(final float[] array) {
        VectorUtil.addVec3(this.low, this.low, array);
        VectorUtil.addVec3(this.high, this.high, array);
        this.computeCenter();
        return this;
    }
    
    public final AABBox rotate(final Quaternion quaternion) {
        quaternion.rotateVector(this.low, 0, this.low, 0);
        quaternion.rotateVector(this.high, 0, this.high, 0);
        this.computeCenter();
        return this;
    }
    
    public final float getMinX() {
        return this.low[0];
    }
    
    public final float getMinY() {
        return this.low[1];
    }
    
    public final float getMinZ() {
        return this.low[2];
    }
    
    public final float getMaxX() {
        return this.high[0];
    }
    
    public final float getMaxY() {
        return this.high[1];
    }
    
    public final float getMaxZ() {
        return this.high[2];
    }
    
    public final float getWidth() {
        return this.high[0] - this.low[0];
    }
    
    public final float getHeight() {
        return this.high[1] - this.low[1];
    }
    
    public final float getDepth() {
        return this.high[2] - this.low[2];
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (null == o || !(o instanceof AABBox)) {
            return false;
        }
        final AABBox aabBox = (AABBox)o;
        return VectorUtil.isVec2Equal(this.low, 0, aabBox.low, 0, 1.1920929E-7f) && VectorUtil.isVec3Equal(this.high, 0, aabBox.high, 0, 1.1920929E-7f);
    }
    
    @Override
    public final int hashCode() {
        throw new InternalError("hashCode not designed");
    }
    
    public AABBox mapToWindow(final AABBox aabBox, final float[] array, final int[] array2, final boolean b, final float[] array3, final float[] array4, final float[] array5) {
        final float n = b ? this.center[2] : this.getMinZ();
        FloatUtil.mapObjToWinCoords(this.getMinX(), this.getMinY(), n, array, array2, 0, array3, 0, array4, array5);
        aabBox.reset();
        aabBox.resize(array3, 0);
        FloatUtil.mapObjToWinCoords(this.getMinX(), this.getMaxY(), n, array, array2, 0, array3, 0, array4, array5);
        aabBox.resize(array3, 0);
        FloatUtil.mapObjToWinCoords(this.getMaxX(), this.getMinY(), n, array, array2, 0, array3, 0, array4, array5);
        aabBox.resize(array3, 0);
        FloatUtil.mapObjToWinCoords(this.getMaxX(), this.getMaxY(), n, array, array2, 0, array3, 0, array4, array5);
        aabBox.resize(array3, 0);
        if (!b) {
            final float maxZ = this.getMaxZ();
            FloatUtil.mapObjToWinCoords(this.getMinX(), this.getMinY(), maxZ, array, array2, 0, array3, 0, array4, array5);
            aabBox.resize(array3, 0);
            FloatUtil.mapObjToWinCoords(this.getMinX(), this.getMaxY(), maxZ, array, array2, 0, array3, 0, array4, array5);
            aabBox.resize(array3, 0);
            FloatUtil.mapObjToWinCoords(this.getMaxX(), this.getMinY(), maxZ, array, array2, 0, array3, 0, array4, array5);
            aabBox.resize(array3, 0);
            FloatUtil.mapObjToWinCoords(this.getMaxX(), this.getMaxY(), maxZ, array, array2, 0, array3, 0, array4, array5);
            aabBox.resize(array3, 0);
        }
        if (AABBox.DEBUG) {
            System.err.printf("AABBox.mapToWindow: view[%d, %d], this %s -> %s%n", array2[0], array2[1], this.toString(), aabBox.toString());
        }
        return aabBox;
    }
    
    @Override
    public final String toString() {
        return "[ dim " + this.getWidth() + " x " + this.getHeight() + " x " + this.getDepth() + ", box " + this.low[0] + " / " + this.low[1] + " / " + this.low[2] + " .. " + this.high[0] + " / " + this.high[1] + " / " + this.high[2] + ", ctr " + this.center[0] + " / " + this.center[1] + " / " + this.center[2] + " ]";
    }
    
    static {
        DEBUG = FloatUtil.DEBUG;
    }
}
