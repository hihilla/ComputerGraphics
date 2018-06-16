// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

import com.jogamp.opengl.GLException;

public class Matrix4
{
    private final float[] matrix;
    private final float[] matrixTxSx;
    private final float[] mat4Tmp1;
    private final float[] vec4Tmp1;
    
    public Matrix4() {
        this.matrix = new float[16];
        this.matrixTxSx = new float[16];
        this.mat4Tmp1 = new float[16];
        this.vec4Tmp1 = new float[4];
        FloatUtil.makeIdentity(this.matrixTxSx);
        this.loadIdentity();
    }
    
    public final float[] getMatrix() {
        return this.matrix;
    }
    
    public final void loadIdentity() {
        FloatUtil.makeIdentity(this.matrix);
    }
    
    public final void multMatrix(final float[] array, final int n) {
        FloatUtil.multMatrix(this.matrix, 0, array, n);
    }
    
    public final void multMatrix(final float[] array) {
        FloatUtil.multMatrix(this.matrix, array);
    }
    
    public final void multMatrix(final Matrix4 matrix4) {
        FloatUtil.multMatrix(this.matrix, matrix4.getMatrix());
    }
    
    public final void multVec(final float[] array, final float[] array2) {
        FloatUtil.multMatrixVec(this.matrix, array, array2);
    }
    
    public final void multVec(final float[] array, final int n, final float[] array2, final int n2) {
        FloatUtil.multMatrixVec(this.matrix, 0, array, n, array2, n2);
    }
    
    public final void translate(final float n, final float n2, final float n3) {
        this.multMatrix(FloatUtil.makeTranslation(this.matrixTxSx, false, n, n2, n3));
    }
    
    public final void scale(final float n, final float n2, final float n3) {
        this.multMatrix(FloatUtil.makeScale(this.matrixTxSx, false, n, n2, n3));
    }
    
    public final void rotate(final float n, final float n2, final float n3, final float n4) {
        this.multMatrix(FloatUtil.makeRotationAxis(this.mat4Tmp1, 0, n, n2, n3, n4, this.vec4Tmp1));
    }
    
    public final void rotate(final Quaternion quaternion) {
        this.multMatrix(quaternion.toMatrix(this.mat4Tmp1, 0));
    }
    
    public final void transpose() {
        System.arraycopy(this.matrix, 0, this.mat4Tmp1, 0, 16);
        FloatUtil.transposeMatrix(this.mat4Tmp1, this.matrix);
    }
    
    public final float determinant() {
        return FloatUtil.matrixDeterminant(this.matrix);
    }
    
    public final boolean invert() {
        return null != FloatUtil.invertMatrix(this.matrix, this.matrix);
    }
    
    public final void makeOrtho(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.multMatrix(FloatUtil.makeOrtho(this.mat4Tmp1, 0, true, n, n2, n3, n4, n5, n6));
    }
    
    public final void makeFrustum(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws GLException {
        this.multMatrix(FloatUtil.makeFrustum(this.mat4Tmp1, 0, true, n, n2, n3, n4, n5, n6));
    }
    
    public final void makePerspective(final float n, final float n2, final float n3, final float n4) throws GLException {
        this.multMatrix(FloatUtil.makePerspective(this.mat4Tmp1, 0, true, n, n2, n3, n4));
    }
}
