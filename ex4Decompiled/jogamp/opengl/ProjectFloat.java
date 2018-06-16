// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

public class ProjectFloat
{
    private final float[] mat4Tmp1;
    private final float[] mat4Tmp2;
    private final float[] mat4Tmp3;
    
    public static final int getRequiredFloatBufferSize() {
        return 16;
    }
    
    public ProjectFloat() {
        this.mat4Tmp1 = new float[16];
        this.mat4Tmp2 = new float[16];
        this.mat4Tmp3 = new float[16];
    }
    
    public void gluOrtho2D(final GLMatrixFunc glMatrixFunc, final float n, final float n2, final float n3, final float n4) {
        glMatrixFunc.glOrthof(n, n2, n3, n4, -1.0f, 1.0f);
    }
    
    public void gluPerspective(final GLMatrixFunc glMatrixFunc, final float n, final float n2, final float n3, final float n4) throws GLException {
        glMatrixFunc.glMultMatrixf(FloatUtil.makePerspective(this.mat4Tmp1, 0, true, n * 3.1415927f / 180.0f, n2, n3, n4), 0);
    }
    
    public void gluLookAt(final GLMatrixFunc glMatrixFunc, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9) {
        this.mat4Tmp2[0] = n;
        this.mat4Tmp2[1] = n2;
        this.mat4Tmp2[2] = n3;
        this.mat4Tmp2[4] = n4;
        this.mat4Tmp2[5] = n5;
        this.mat4Tmp2[6] = n6;
        this.mat4Tmp2[8] = n7;
        this.mat4Tmp2[9] = n8;
        this.mat4Tmp2[10] = n9;
        glMatrixFunc.glMultMatrixf(FloatUtil.makeLookAt(this.mat4Tmp1, 0, this.mat4Tmp2, 0, this.mat4Tmp2, 4, this.mat4Tmp2, 8, this.mat4Tmp3), 0);
    }
    
    public boolean gluProject(final float n, final float n2, final float n3, final float[] array, final int n4, final float[] array2, final int n5, final int[] array3, final int n6, final float[] array4, final int n7) {
        return FloatUtil.mapObjToWinCoords(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7, this.mat4Tmp1, this.mat4Tmp2);
    }
    
    public boolean gluProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final int[] array, final int n4, final float[] array2, final int n5) {
        final float[] mat4Tmp1 = this.mat4Tmp1;
        final float[] mat4Tmp2 = this.mat4Tmp2;
        mat4Tmp1[0] = n;
        mat4Tmp1[1] = n2;
        mat4Tmp1[2] = n3;
        mat4Tmp1[3] = 1.0f;
        FloatUtil.multMatrixVec(floatBuffer, mat4Tmp1, mat4Tmp2);
        FloatUtil.multMatrixVec(floatBuffer2, mat4Tmp2, mat4Tmp1);
        if (mat4Tmp1[3] == 0.0f) {
            return false;
        }
        mat4Tmp1[3] = 1.0f / mat4Tmp1[3] * 0.5f;
        mat4Tmp1[0] = mat4Tmp1[0] * mat4Tmp1[3] + 0.5f;
        mat4Tmp1[1] = mat4Tmp1[1] * mat4Tmp1[3] + 0.5f;
        mat4Tmp1[2] = mat4Tmp1[2] * mat4Tmp1[3] + 0.5f;
        array2[0 + n5] = mat4Tmp1[0] * array[2 + n4] + array[0 + n4];
        array2[1 + n5] = mat4Tmp1[1] * array[3 + n4] + array[1 + n4];
        array2[2 + n5] = mat4Tmp1[2];
        return true;
    }
    
    public boolean gluProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final FloatBuffer floatBuffer3) {
        final float[] mat4Tmp1 = this.mat4Tmp1;
        final float[] mat4Tmp2 = this.mat4Tmp2;
        mat4Tmp1[0] = n;
        mat4Tmp1[1] = n2;
        mat4Tmp1[2] = n3;
        mat4Tmp1[3] = 1.0f;
        FloatUtil.multMatrixVec(floatBuffer, mat4Tmp1, mat4Tmp2);
        FloatUtil.multMatrixVec(floatBuffer2, mat4Tmp2, mat4Tmp1);
        if (mat4Tmp1[3] == 0.0f) {
            return false;
        }
        mat4Tmp1[3] = 1.0f / mat4Tmp1[3] * 0.5f;
        mat4Tmp1[0] = mat4Tmp1[0] * mat4Tmp1[3] + 0.5f;
        mat4Tmp1[1] = mat4Tmp1[1] * mat4Tmp1[3] + 0.5f;
        mat4Tmp1[2] = mat4Tmp1[2] * mat4Tmp1[3] + 0.5f;
        final int position = intBuffer.position();
        final int position2 = floatBuffer3.position();
        floatBuffer3.put(0 + position2, mat4Tmp1[0] * intBuffer.get(2 + position) + intBuffer.get(0 + position));
        floatBuffer3.put(1 + position2, mat4Tmp1[1] * intBuffer.get(3 + position) + intBuffer.get(1 + position));
        floatBuffer3.put(2 + position2, mat4Tmp1[2]);
        return true;
    }
    
    public boolean gluUnProject(final float n, final float n2, final float n3, final float[] array, final int n4, final float[] array2, final int n5, final int[] array3, final int n6, final float[] array4, final int n7) {
        return FloatUtil.mapWinToObjCoords(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7, this.mat4Tmp1, this.mat4Tmp2);
    }
    
    public boolean gluUnProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final int[] array, final int n4, final float[] array2, final int n5) {
        FloatUtil.multMatrix(floatBuffer2, floatBuffer, this.mat4Tmp1);
        if (null == FloatUtil.invertMatrix(this.mat4Tmp1, this.mat4Tmp1)) {
            return false;
        }
        this.mat4Tmp2[0] = n;
        this.mat4Tmp2[1] = n2;
        this.mat4Tmp2[2] = n3;
        this.mat4Tmp2[3] = 1.0f;
        this.mat4Tmp2[0] = (this.mat4Tmp2[0] - array[0 + n4]) / array[2 + n4];
        this.mat4Tmp2[1] = (this.mat4Tmp2[1] - array[1 + n4]) / array[3 + n4];
        this.mat4Tmp2[0] = this.mat4Tmp2[0] * 2.0f - 1.0f;
        this.mat4Tmp2[1] = this.mat4Tmp2[1] * 2.0f - 1.0f;
        this.mat4Tmp2[2] = this.mat4Tmp2[2] * 2.0f - 1.0f;
        FloatUtil.multMatrixVec(this.mat4Tmp1, 0, this.mat4Tmp2, 0, this.mat4Tmp2, 4);
        if (this.mat4Tmp2[7] == 0.0) {
            return false;
        }
        this.mat4Tmp2[7] = 1.0f / this.mat4Tmp2[7];
        array2[0 + n5] = this.mat4Tmp2[4] * this.mat4Tmp2[7];
        array2[1 + n5] = this.mat4Tmp2[5] * this.mat4Tmp2[7];
        array2[2 + n5] = this.mat4Tmp2[6] * this.mat4Tmp2[7];
        return true;
    }
    
    public boolean gluUnProject(final float n, final float n2, final float n3, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final FloatBuffer floatBuffer3) {
        final int position = intBuffer.position();
        final int position2 = floatBuffer3.position();
        FloatUtil.multMatrix(floatBuffer2, floatBuffer, this.mat4Tmp1);
        if (null == FloatUtil.invertMatrix(this.mat4Tmp1, this.mat4Tmp1)) {
            return false;
        }
        this.mat4Tmp2[0] = n;
        this.mat4Tmp2[1] = n2;
        this.mat4Tmp2[2] = n3;
        this.mat4Tmp2[3] = 1.0f;
        this.mat4Tmp2[0] = (this.mat4Tmp2[0] - intBuffer.get(0 + position)) / intBuffer.get(2 + position);
        this.mat4Tmp2[1] = (this.mat4Tmp2[1] - intBuffer.get(1 + position)) / intBuffer.get(3 + position);
        this.mat4Tmp2[0] = this.mat4Tmp2[0] * 2.0f - 1.0f;
        this.mat4Tmp2[1] = this.mat4Tmp2[1] * 2.0f - 1.0f;
        this.mat4Tmp2[2] = this.mat4Tmp2[2] * 2.0f - 1.0f;
        FloatUtil.multMatrixVec(this.mat4Tmp1, 0, this.mat4Tmp2, 0, this.mat4Tmp2, 4);
        if (this.mat4Tmp2[7] == 0.0) {
            return false;
        }
        this.mat4Tmp2[7] = 1.0f / this.mat4Tmp2[7];
        floatBuffer3.put(0 + position2, this.mat4Tmp2[4] * this.mat4Tmp2[7]);
        floatBuffer3.put(1 + position2, this.mat4Tmp2[5] * this.mat4Tmp2[7]);
        floatBuffer3.put(2 + position2, this.mat4Tmp2[6] * this.mat4Tmp2[7]);
        return true;
    }
    
    public boolean gluUnProject4(final float n, final float n2, final float n3, final float n4, final float[] array, final int n5, final float[] array2, final int n6, final int[] array3, final int n7, final float n8, final float n9, final float[] array4, final int n10) {
        return FloatUtil.mapWinToObjCoords(n, n2, n3, n4, array, n5, array2, n6, array3, n7, n8, n9, array4, n10, this.mat4Tmp1, this.mat4Tmp2);
    }
    
    public boolean gluUnProject4(final float n, final float n2, final float n3, final float n4, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final IntBuffer intBuffer, final float n5, final float n6, final FloatBuffer floatBuffer3) {
        FloatUtil.multMatrix(floatBuffer2, floatBuffer, this.mat4Tmp1);
        if (null == FloatUtil.invertMatrix(this.mat4Tmp1, this.mat4Tmp1)) {
            return false;
        }
        this.mat4Tmp2[0] = n;
        this.mat4Tmp2[1] = n2;
        this.mat4Tmp2[2] = n3;
        this.mat4Tmp2[3] = n4;
        final int position = intBuffer.position();
        this.mat4Tmp2[0] = (this.mat4Tmp2[0] - intBuffer.get(0 + position)) / intBuffer.get(2 + position);
        this.mat4Tmp2[1] = (this.mat4Tmp2[1] - intBuffer.get(1 + position)) / intBuffer.get(3 + position);
        this.mat4Tmp2[2] = (this.mat4Tmp2[2] - n5) / (n6 - n5);
        this.mat4Tmp2[0] = this.mat4Tmp2[0] * 2.0f - 1.0f;
        this.mat4Tmp2[1] = this.mat4Tmp2[1] * 2.0f - 1.0f;
        this.mat4Tmp2[2] = this.mat4Tmp2[2] * 2.0f - 1.0f;
        FloatUtil.multMatrixVec(this.mat4Tmp1, 0, this.mat4Tmp2, 0, this.mat4Tmp2, 4);
        if (this.mat4Tmp2[7] == 0.0f) {
            return false;
        }
        final int position2 = floatBuffer3.position();
        floatBuffer3.put(0 + position2, this.mat4Tmp2[4]);
        floatBuffer3.put(1 + position2, this.mat4Tmp2[5]);
        floatBuffer3.put(2 + position2, this.mat4Tmp2[6]);
        floatBuffer3.put(3 + position2, this.mat4Tmp2[7]);
        return true;
    }
    
    public void gluPickMatrix(final GLMatrixFunc glMatrixFunc, final float n, final float n2, final float n3, final float n4, final IntBuffer intBuffer) {
        if (n3 <= 0.0f || n4 <= 0.0f) {
            return;
        }
        final int position = intBuffer.position();
        glMatrixFunc.glTranslatef((intBuffer.get(2 + position) - 2.0f * (n - intBuffer.get(0 + position))) / n3, (intBuffer.get(3 + position) - 2.0f * (n2 - intBuffer.get(1 + position))) / n4, 0.0f);
        glMatrixFunc.glScalef(intBuffer.get(2) / n3, intBuffer.get(3) / n4, 1.0f);
    }
    
    public void gluPickMatrix(final GLMatrixFunc glMatrixFunc, final float n, final float n2, final float n3, final float n4, final int[] array, final int n5) {
        if (null != FloatUtil.makePick(this.mat4Tmp1, 0, n, n2, n3, n4, array, n5, this.mat4Tmp2)) {
            glMatrixFunc.glMultMatrixf(this.mat4Tmp1, 0);
        }
    }
}
