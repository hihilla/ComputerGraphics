// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.opengl.math.Ray;
import com.jogamp.opengl.math.Quaternion;
import java.nio.IntBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.math.geom.Frustum;
import com.jogamp.common.util.FloatStack;
import java.nio.FloatBuffer;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

public final class PMVMatrix implements GLMatrixFunc
{
    public static final int MODIFIED_PROJECTION = 1;
    public static final int MODIFIED_MODELVIEW = 2;
    public static final int MODIFIED_TEXTURE = 4;
    public static final int MODIFIED_ALL = 7;
    public static final int DIRTY_INVERSE_MODELVIEW = 1;
    public static final int DIRTY_INVERSE_TRANSPOSED_MODELVIEW = 2;
    public static final int DIRTY_FRUSTUM = 4;
    public static final int DIRTY_ALL = 7;
    private static final String msgCantComputeInverse = "Invalid source Mv matrix, can't compute inverse";
    private final float[] matrixArray;
    private final int mP_offset;
    private final int mMv_offset;
    private final int mTex_offset;
    private final FloatBuffer matrixPMvMvit;
    private final FloatBuffer matrixPMvMvi;
    private final FloatBuffer matrixPMv;
    private final FloatBuffer matrixP;
    private final FloatBuffer matrixTex;
    private final FloatBuffer matrixMv;
    private final FloatBuffer matrixMvi;
    private final FloatBuffer matrixMvit;
    private final float[] matrixTxSx;
    private final float[] mat4Tmp1;
    private final float[] mat4Tmp2;
    private final float[] mat4Tmp3;
    private final FloatStack matrixTStack;
    private final FloatStack matrixPStack;
    private final FloatStack matrixMvStack;
    private int matrixMode;
    private int modifiedBits;
    private int dirtyBits;
    private int requestMask;
    private Frustum frustum;
    
    public static final boolean isMatrixModeName(final int n) {
        switch (n) {
            case 2982:
            case 2983:
            case 2984: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static final int matrixModeName2MatrixGetName(final int n) {
        switch (n) {
            case 5888: {
                return 2982;
            }
            case 5889: {
                return 2983;
            }
            case 5890: {
                return 2984;
            }
            default: {
                throw new GLException("unsupported matrixName: " + n);
            }
        }
    }
    
    public static final boolean isMatrixGetName(final int n) {
        switch (n) {
            case 2976:
            case 2982:
            case 2983:
            case 2984: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static final int matrixGetName2MatrixModeName(final int n) {
        switch (n) {
            case 2982: {
                return 5888;
            }
            case 2983: {
                return 5889;
            }
            case 2984: {
                return 5890;
            }
            default: {
                throw new GLException("unsupported matrixGetName: " + n);
            }
        }
    }
    
    public static StringBuilder matrixToString(final StringBuilder sb, final String s, final FloatBuffer floatBuffer) {
        return FloatUtil.matrixToString(sb, null, s, floatBuffer, 0, 4, 4, false);
    }
    
    public static StringBuilder matrixToString(final StringBuilder sb, final String s, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        return FloatUtil.matrixToString(sb, null, s, floatBuffer, 0, floatBuffer2, 0, 4, 4, false);
    }
    
    public PMVMatrix() {
        this.matrixMode = 5888;
        this.modifiedBits = 7;
        this.dirtyBits = 7;
        this.requestMask = 0;
        this.matrixArray = new float[80];
        this.mP_offset = 0;
        this.mMv_offset = 16;
        this.mTex_offset = 64;
        this.matrixPMvMvit = Buffers.slice2Float(this.matrixArray, 0, 64);
        this.matrixPMvMvi = Buffers.slice2Float(this.matrixArray, 0, 48);
        this.matrixPMv = Buffers.slice2Float(this.matrixArray, 0, 32);
        this.matrixP = Buffers.slice2Float(this.matrixArray, 0, 16);
        this.matrixMv = Buffers.slice2Float(this.matrixArray, 16, 16);
        this.matrixMvi = Buffers.slice2Float(this.matrixArray, 32, 16);
        this.matrixMvit = Buffers.slice2Float(this.matrixArray, 48, 16);
        this.matrixTex = Buffers.slice2Float(this.matrixArray, 64, 16);
        this.mat4Tmp1 = new float[16];
        this.mat4Tmp2 = new float[16];
        this.mat4Tmp3 = new float[16];
        FloatUtil.makeIdentity(this.matrixTxSx = new float[16]);
        this.matrixTStack = new FloatStack(0, 32);
        this.matrixPStack = new FloatStack(0, 32);
        this.matrixMvStack = new FloatStack(0, 256);
        this.reset();
        this.frustum = null;
    }
    
    public final void reset() {
        FloatUtil.makeIdentity(this.matrixArray, this.mMv_offset);
        FloatUtil.makeIdentity(this.matrixArray, this.mP_offset);
        FloatUtil.makeIdentity(this.matrixArray, this.mTex_offset);
        this.modifiedBits = 7;
        this.dirtyBits = 7;
        this.requestMask = 0;
        this.matrixMode = 5888;
    }
    
    public final int glGetMatrixMode() {
        return this.matrixMode;
    }
    
    public final FloatBuffer glGetTMatrixf() {
        return this.matrixTex;
    }
    
    public final FloatBuffer glGetPMatrixf() {
        return this.matrixP;
    }
    
    public final FloatBuffer glGetMvMatrixf() {
        return this.matrixMv;
    }
    
    public final FloatBuffer glGetMviMatrixf() {
        this.requestMask |= 0x1;
        this.updateImpl(false);
        return this.matrixMvi;
    }
    
    public final FloatBuffer glGetMvitMatrixf() {
        this.requestMask |= 0x2;
        this.updateImpl(false);
        return this.matrixMvit;
    }
    
    public final FloatBuffer glGetPMvMatrixf() {
        return this.matrixPMv;
    }
    
    public final FloatBuffer glGetPMvMviMatrixf() {
        this.requestMask |= 0x1;
        this.updateImpl(false);
        return this.matrixPMvMvi;
    }
    
    public final FloatBuffer glGetPMvMvitMatrixf() {
        this.requestMask |= 0x3;
        this.updateImpl(false);
        return this.matrixPMvMvit;
    }
    
    public final Frustum glGetFrustum() {
        this.requestMask |= 0x4;
        this.updateImpl(false);
        return this.frustum;
    }
    
    public final FloatBuffer glGetMatrixf() {
        return this.glGetMatrixf(this.matrixMode);
    }
    
    public final FloatBuffer glGetMatrixf(final int n) {
        switch (n) {
            case 2982:
            case 5888: {
                return this.matrixMv;
            }
            case 2983:
            case 5889: {
                return this.matrixP;
            }
            case 2984:
            case 5890: {
                return this.matrixTex;
            }
            default: {
                throw new GLException("unsupported matrixName: " + n);
            }
        }
    }
    
    public final float[] multPMvMatrixf(final float[] array, final int n) {
        FloatUtil.multMatrix(this.matrixArray, this.mP_offset, this.matrixArray, this.mMv_offset, array, n);
        return array;
    }
    
    public final float[] multMvPMatrixf(final float[] array, final int n) {
        FloatUtil.multMatrix(this.matrixArray, this.mMv_offset, this.matrixArray, this.mP_offset, array, n);
        return array;
    }
    
    @Override
    public final void glMatrixMode(final int matrixMode) {
        switch (matrixMode) {
            case 5888:
            case 5889:
            case 5890: {
                this.matrixMode = matrixMode;
            }
            default: {
                throw new GLException("unsupported matrixName: " + matrixMode);
            }
        }
    }
    
    @Override
    public final void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        final int position = floatBuffer.position();
        if (n == 2976) {
            floatBuffer.put(this.matrixMode);
        }
        else {
            final FloatBuffer glGetMatrixf = this.glGetMatrixf(n);
            floatBuffer.put(glGetMatrixf);
            glGetMatrixf.reset();
        }
        floatBuffer.position(position);
    }
    
    @Override
    public final void glGetFloatv(final int n, final float[] array, final int n2) {
        if (n == 2976) {
            array[n2] = this.matrixMode;
        }
        else {
            final FloatBuffer glGetMatrixf = this.glGetMatrixf(n);
            glGetMatrixf.get(array, n2, 16);
            glGetMatrixf.reset();
        }
    }
    
    @Override
    public final void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        final int position = intBuffer.position();
        if (n == 2976) {
            intBuffer.put(this.matrixMode);
            intBuffer.position(position);
            return;
        }
        throw new GLException("unsupported pname: " + n);
    }
    
    @Override
    public final void glGetIntegerv(final int n, final int[] array, final int n2) {
        if (n == 2976) {
            array[n2] = this.matrixMode;
            return;
        }
        throw new GLException("unsupported pname: " + n);
    }
    
    @Override
    public final void glLoadMatrixf(final float[] array, final int n) {
        if (this.matrixMode == 5888) {
            this.matrixMv.put(array, n, 16);
            this.matrixMv.reset();
            this.dirtyBits |= 0x7;
            this.modifiedBits |= 0x2;
        }
        else if (this.matrixMode == 5889) {
            this.matrixP.put(array, n, 16);
            this.matrixP.reset();
            this.dirtyBits |= 0x4;
            this.modifiedBits |= 0x1;
        }
        else if (this.matrixMode == 5890) {
            this.matrixTex.put(array, n, 16);
            this.matrixTex.reset();
            this.modifiedBits |= 0x4;
        }
    }
    
    @Override
    public final void glLoadMatrixf(final FloatBuffer floatBuffer) {
        final int position = floatBuffer.position();
        if (this.matrixMode == 5888) {
            this.matrixMv.put(floatBuffer);
            this.matrixMv.reset();
            this.dirtyBits |= 0x7;
            this.modifiedBits |= 0x2;
        }
        else if (this.matrixMode == 5889) {
            this.matrixP.put(floatBuffer);
            this.matrixP.reset();
            this.dirtyBits |= 0x4;
            this.modifiedBits |= 0x1;
        }
        else if (this.matrixMode == 5890) {
            this.matrixTex.put(floatBuffer);
            this.matrixTex.reset();
            this.modifiedBits |= 0x4;
        }
        floatBuffer.position(position);
    }
    
    public final void glLoadMatrix(final Quaternion quaternion) {
        if (this.matrixMode == 5888) {
            quaternion.toMatrix(this.matrixArray, this.mMv_offset);
            this.matrixMv.reset();
            this.dirtyBits |= 0x7;
            this.modifiedBits |= 0x2;
        }
        else if (this.matrixMode == 5889) {
            quaternion.toMatrix(this.matrixArray, this.mP_offset);
            this.matrixP.reset();
            this.dirtyBits |= 0x4;
            this.modifiedBits |= 0x1;
        }
        else if (this.matrixMode == 5890) {
            quaternion.toMatrix(this.matrixArray, this.mTex_offset);
            this.matrixTex.reset();
            this.modifiedBits |= 0x4;
        }
    }
    
    @Override
    public final void glPopMatrix() {
        FloatStack floatStack;
        if (this.matrixMode == 5888) {
            floatStack = this.matrixMvStack;
        }
        else if (this.matrixMode == 5889) {
            floatStack = this.matrixPStack;
        }
        else {
            if (this.matrixMode != 5890) {
                throw new InternalError("XXX: mode " + this.matrixMode);
            }
            floatStack = this.matrixTStack;
        }
        floatStack.position(floatStack.position() - 16);
        this.glLoadMatrixf(floatStack.buffer(), floatStack.position());
    }
    
    @Override
    public final void glPushMatrix() {
        if (this.matrixMode == 5888) {
            this.matrixMvStack.putOnTop(this.matrixMv, 16);
            this.matrixMv.reset();
        }
        else if (this.matrixMode == 5889) {
            this.matrixPStack.putOnTop(this.matrixP, 16);
            this.matrixP.reset();
        }
        else if (this.matrixMode == 5890) {
            this.matrixTStack.putOnTop(this.matrixTex, 16);
            this.matrixTex.reset();
        }
    }
    
    @Override
    public final void glLoadIdentity() {
        if (this.matrixMode == 5888) {
            FloatUtil.makeIdentity(this.matrixArray, this.mMv_offset);
            this.dirtyBits |= 0x7;
            this.modifiedBits |= 0x2;
        }
        else if (this.matrixMode == 5889) {
            FloatUtil.makeIdentity(this.matrixArray, this.mP_offset);
            this.dirtyBits |= 0x4;
            this.modifiedBits |= 0x1;
        }
        else if (this.matrixMode == 5890) {
            FloatUtil.makeIdentity(this.matrixArray, this.mTex_offset);
            this.modifiedBits |= 0x4;
        }
    }
    
    @Override
    public final void glMultMatrixf(final FloatBuffer floatBuffer) {
        if (this.matrixMode == 5888) {
            FloatUtil.multMatrix(this.matrixMv, floatBuffer);
            this.dirtyBits |= 0x7;
            this.modifiedBits |= 0x2;
        }
        else if (this.matrixMode == 5889) {
            FloatUtil.multMatrix(this.matrixP, floatBuffer);
            this.dirtyBits |= 0x4;
            this.modifiedBits |= 0x1;
        }
        else if (this.matrixMode == 5890) {
            FloatUtil.multMatrix(this.matrixTex, floatBuffer);
            this.modifiedBits |= 0x4;
        }
    }
    
    @Override
    public final void glMultMatrixf(final float[] array, final int n) {
        if (this.matrixMode == 5888) {
            FloatUtil.multMatrix(this.matrixArray, this.mMv_offset, array, n);
            this.dirtyBits |= 0x7;
            this.modifiedBits |= 0x2;
        }
        else if (this.matrixMode == 5889) {
            FloatUtil.multMatrix(this.matrixArray, this.mP_offset, array, n);
            this.dirtyBits |= 0x4;
            this.modifiedBits |= 0x1;
        }
        else if (this.matrixMode == 5890) {
            FloatUtil.multMatrix(this.matrixArray, this.mTex_offset, array, n);
            this.modifiedBits |= 0x4;
        }
    }
    
    @Override
    public final void glTranslatef(final float n, final float n2, final float n3) {
        this.glMultMatrixf(FloatUtil.makeTranslation(this.matrixTxSx, false, n, n2, n3), 0);
    }
    
    @Override
    public final void glScalef(final float n, final float n2, final float n3) {
        this.glMultMatrixf(FloatUtil.makeScale(this.matrixTxSx, false, n, n2, n3), 0);
    }
    
    @Override
    public final void glRotatef(final float n, final float n2, final float n3, final float n4) {
        this.glMultMatrixf(FloatUtil.makeRotationAxis(this.mat4Tmp1, 0, n * 3.1415927f / 180.0f, n2, n3, n4, this.mat4Tmp2), 0);
    }
    
    public final void glRotate(final Quaternion quaternion) {
        this.glMultMatrixf(quaternion.toMatrix(this.mat4Tmp1, 0), 0);
    }
    
    @Override
    public final void glOrthof(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.glMultMatrixf(FloatUtil.makeOrtho(this.mat4Tmp1, 0, true, n, n2, n3, n4, n5, n6), 0);
    }
    
    @Override
    public final void glFrustumf(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws GLException {
        this.glMultMatrixf(FloatUtil.makeFrustum(this.mat4Tmp1, 0, true, n, n2, n3, n4, n5, n6), 0);
    }
    
    public final void gluPerspective(final float n, final float n2, final float n3, final float n4) throws GLException {
        this.glMultMatrixf(FloatUtil.makePerspective(this.mat4Tmp1, 0, true, n * 3.1415927f / 180.0f, n2, n3, n4), 0);
    }
    
    public final void gluLookAt(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        this.mat4Tmp2[0] = n;
        this.mat4Tmp2[1] = n2;
        this.mat4Tmp2[2] = n3;
        this.mat4Tmp2[4] = n4;
        this.mat4Tmp2[5] = n5;
        this.mat4Tmp2[6] = n6;
        this.mat4Tmp2[8] = n7;
        this.mat4Tmp2[9] = n8;
        this.mat4Tmp2[10] = n9;
        this.glMultMatrixf(FloatUtil.makeLookAt(this.mat4Tmp1, 0, this.mat4Tmp2, 0, this.mat4Tmp2, 4, this.mat4Tmp2, 8, this.mat4Tmp3), 0);
    }
    
    public final boolean gluProject(final float n, final float n2, final float n3, final int[] array, final int n4, final float[] array2, final int n5) {
        return FloatUtil.mapObjToWinCoords(n, n2, n3, this.matrixArray, this.mMv_offset, this.matrixArray, this.mP_offset, array, n4, array2, n5, this.mat4Tmp1, this.mat4Tmp2);
    }
    
    public final boolean gluUnProject(final float n, final float n2, final float n3, final int[] array, final int n4, final float[] array2, final int n5) {
        return FloatUtil.mapWinToObjCoords(n, n2, n3, this.matrixArray, this.mMv_offset, this.matrixArray, this.mP_offset, array, n4, array2, n5, this.mat4Tmp1, this.mat4Tmp2);
    }
    
    public boolean gluUnProject4(final float n, final float n2, final float n3, final float n4, final int[] array, final int n5, final float n6, final float n7, final float[] array2, final int n8) {
        return FloatUtil.mapWinToObjCoords(n, n2, n3, n4, this.matrixArray, this.mMv_offset, this.matrixArray, this.mP_offset, array, n5, n6, n7, array2, n8, this.mat4Tmp1, this.mat4Tmp2);
    }
    
    public final void gluPickMatrix(final float n, final float n2, final float n3, final float n4, final int[] array, final int n5) {
        if (null != FloatUtil.makePick(this.mat4Tmp1, 0, n, n2, n3, n4, array, n5, this.mat4Tmp2)) {
            this.glMultMatrixf(this.mat4Tmp1, 0);
        }
    }
    
    public final boolean gluUnProjectRay(final float n, final float n2, final float n3, final float n4, final int[] array, final int n5, final Ray ray) {
        return FloatUtil.mapWinToRay(n, n2, n3, n4, this.matrixArray, this.mMv_offset, this.matrixArray, this.mP_offset, array, n5, ray, this.mat4Tmp1, this.mat4Tmp2, this.mat4Tmp3);
    }
    
    public StringBuilder toString(StringBuilder sb, final String s) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final boolean b = 0x0 != (0x1 & this.dirtyBits);
        final boolean b2 = 0x0 != (0x2 & this.dirtyBits);
        final boolean b3 = 0x0 != (0x4 & this.dirtyBits);
        final boolean b4 = 0x0 != (0x1 & this.requestMask);
        final boolean b5 = 0x0 != (0x2 & this.requestMask);
        final boolean b6 = 0x0 != (0x4 & this.requestMask);
        sb.append("PMVMatrix[modified[P ").append(0x0 != (0x1 & this.modifiedBits)).append(", Mv ").append(0x0 != (0x2 & this.modifiedBits)).append(", T ").append(0x0 != (0x4 & this.modifiedBits));
        sb.append("], dirty/req[Mvi ").append(b).append("/").append(b4).append(", Mvit ").append(b2).append("/").append(b5).append(", Frustum ").append(b3).append("/").append(b6).append("]").append(PlatformPropsImpl.NEWLINE);
        sb.append(", Projection").append(PlatformPropsImpl.NEWLINE);
        matrixToString(sb, s, this.matrixP);
        sb.append(", Modelview").append(PlatformPropsImpl.NEWLINE);
        matrixToString(sb, s, this.matrixMv);
        sb.append(", Texture").append(PlatformPropsImpl.NEWLINE);
        matrixToString(sb, s, this.matrixTex);
        if (0x0 != (this.requestMask & 0x1)) {
            sb.append(", Inverse Modelview").append(PlatformPropsImpl.NEWLINE);
            matrixToString(sb, s, this.matrixMvi);
        }
        if (0x0 != (this.requestMask & 0x2)) {
            sb.append(", Inverse Transposed Modelview").append(PlatformPropsImpl.NEWLINE);
            matrixToString(sb, s, this.matrixMvit);
        }
        sb.append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null, "%10.5f").toString();
    }
    
    public final int getModifiedBits(final boolean b) {
        final int modifiedBits = this.modifiedBits;
        if (b) {
            this.modifiedBits = 0;
        }
        return modifiedBits;
    }
    
    public final int getDirtyBits() {
        return this.dirtyBits;
    }
    
    public final int getRequestMask() {
        return this.requestMask;
    }
    
    public final void clearAllUpdateRequests() {
        this.requestMask &= 0xFFFFFFF8;
    }
    
    public final boolean update() {
        return this.updateImpl(true);
    }
    
    private final boolean updateImpl(final boolean b) {
        boolean b2 = 0 != this.modifiedBits;
        if (b) {
            this.modifiedBits = 0;
        }
        if (0x0 != (this.dirtyBits & (0x4 & this.requestMask))) {
            if (null == this.frustum) {
                this.frustum = new Frustum();
            }
            FloatUtil.multMatrix(this.matrixArray, this.mP_offset, this.matrixArray, this.mMv_offset, this.mat4Tmp1, 0);
            this.frustum.updateByPMV(this.mat4Tmp1, 0);
            this.dirtyBits &= 0xFFFFFFFB;
            b2 = true;
        }
        if (0x0 == (this.dirtyBits & this.requestMask)) {
            return b2;
        }
        return this.setMviMvit() || b2;
    }
    
    private final boolean setMviMvit() {
        final float[] array = this.matrixMvi.array();
        final int position = this.matrixMvi.position();
        boolean b = false;
        if (0x0 != (this.dirtyBits & 0x1)) {
            if (null == FloatUtil.invertMatrix(this.matrixArray, this.mMv_offset, array, position)) {
                throw new GLException("Invalid source Mv matrix, can't compute inverse");
            }
            this.dirtyBits &= 0xFFFFFFFE;
            b = true;
        }
        if (0x0 != (this.requestMask & (this.dirtyBits & 0x2))) {
            FloatUtil.transposeMatrix(array, position, this.matrixMvit.array(), this.matrixMvit.position());
            this.dirtyBits &= 0xFFFFFFFD;
            b = true;
        }
        return b;
    }
}
