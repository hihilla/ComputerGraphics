// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.gl2;

import java.nio.IntBuffer;
import com.jogamp.opengl.GL2;
import com.jogamp.common.nio.Buffers;
import java.nio.DoubleBuffer;

public class ProjectDouble
{
    private static final double[] IDENTITY_MATRIX;
    private final double[] matrix;
    private final double[][] tempMatrix;
    private final double[] in;
    private final double[] out;
    private final DoubleBuffer matrixBuf;
    private final DoubleBuffer tempMatrixBuf;
    private final DoubleBuffer inBuf;
    private final DoubleBuffer outBuf;
    private final DoubleBuffer forwardBuf;
    private final DoubleBuffer sideBuf;
    private final DoubleBuffer upBuf;
    
    public ProjectDouble() {
        this.matrix = new double[16];
        this.tempMatrix = new double[4][4];
        this.in = new double[4];
        this.out = new double[4];
        final DoubleBuffer directDoubleBuffer = Buffers.newDirectDoubleBuffer(128);
        final int n = 0;
        final int n2 = 16;
        this.matrixBuf = slice(directDoubleBuffer, n, n2);
        final int n3 = n + n2;
        this.tempMatrixBuf = slice(directDoubleBuffer, n3, n2);
        final int n4 = n3 + n2;
        final int n5 = 4;
        this.inBuf = slice(directDoubleBuffer, n4, n5);
        final int n6 = n4 + n5;
        this.outBuf = slice(directDoubleBuffer, n6, n5);
        final int n7 = n6 + n5;
        final int n8 = 3;
        this.forwardBuf = slice(directDoubleBuffer, n7, n8);
        final int n9 = n7 + n8;
        this.sideBuf = slice(directDoubleBuffer, n9, n8);
        this.upBuf = slice(directDoubleBuffer, n9 + n8, n8);
    }
    
    private static DoubleBuffer slice(final DoubleBuffer doubleBuffer, final int n, final int n2) {
        doubleBuffer.position(n);
        doubleBuffer.limit(n + n2);
        return doubleBuffer.slice();
    }
    
    private void __gluMakeIdentityd(final DoubleBuffer doubleBuffer) {
        final int position = doubleBuffer.position();
        doubleBuffer.put(ProjectDouble.IDENTITY_MATRIX);
        doubleBuffer.position(position);
    }
    
    private void __gluMakeIdentityd(final double[] array) {
        for (int i = 0; i < 16; ++i) {
            array[i] = ProjectDouble.IDENTITY_MATRIX[i];
        }
    }
    
    private void __gluMultMatrixVecd(final double[] array, final int n, final double[] array2, final double[] array3) {
        for (int i = 0; i < 4; ++i) {
            array3[i] = array2[0] * array[0 + i + n] + array2[1] * array[4 + i + n] + array2[2] * array[8 + i + n] + array2[3] * array[12 + i + n];
        }
    }
    
    private void __gluMultMatrixVecd(final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final DoubleBuffer doubleBuffer3) {
        final int position = doubleBuffer2.position();
        final int position2 = doubleBuffer3.position();
        final int position3 = doubleBuffer.position();
        for (int i = 0; i < 4; ++i) {
            doubleBuffer3.put(i + position2, doubleBuffer2.get(0 + position) * doubleBuffer.get(0 + i + position3) + doubleBuffer2.get(1 + position) * doubleBuffer.get(4 + i + position3) + doubleBuffer2.get(2 + position) * doubleBuffer.get(8 + i + position3) + doubleBuffer2.get(3 + position) * doubleBuffer.get(12 + i + position3));
        }
    }
    
    private boolean __gluInvertMatrixd(final double[] array, final double[] array2) {
        final double[][] tempMatrix = this.tempMatrix;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                tempMatrix[i][j] = array[i * 4 + j];
            }
        }
        this.__gluMakeIdentityd(array2);
        for (int k = 0; k < 4; ++k) {
            int n = k;
            for (int l = k + 1; l < 4; ++l) {
                if (Math.abs(tempMatrix[l][k]) > Math.abs(tempMatrix[k][k])) {
                    n = l;
                }
            }
            if (n != k) {
                for (int n2 = 0; n2 < 4; ++n2) {
                    final double n3 = tempMatrix[k][n2];
                    tempMatrix[k][n2] = tempMatrix[n][n2];
                    tempMatrix[n][n2] = n3;
                    final double n4 = array2[k * 4 + n2];
                    array2[k * 4 + n2] = array2[n * 4 + n2];
                    array2[n * 4 + n2] = n4;
                }
            }
            if (tempMatrix[k][k] == 0.0) {
                return false;
            }
            final double n5 = tempMatrix[k][k];
            for (int n6 = 0; n6 < 4; ++n6) {
                final double[] array3 = tempMatrix[k];
                final int n7 = n6;
                array3[n7] /= n5;
                final int n8 = k * 4 + n6;
                array2[n8] /= n5;
            }
            for (int n9 = 0; n9 < 4; ++n9) {
                if (n9 != k) {
                    final double n10 = tempMatrix[n9][k];
                    for (int n11 = 0; n11 < 4; ++n11) {
                        final double[] array4 = tempMatrix[n9];
                        final int n12 = n11;
                        array4[n12] -= tempMatrix[k][n11] * n10;
                        final int n13 = n9 * 4 + n11;
                        array2[n13] -= array2[k * 4 + n11] * n10;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean __gluInvertMatrixd(final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final int position = doubleBuffer.position();
        final int position2 = doubleBuffer2.position();
        final DoubleBuffer tempMatrixBuf = this.tempMatrixBuf;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                tempMatrixBuf.put(i * 4 + j, doubleBuffer.get(i * 4 + j + position));
            }
        }
        this.__gluMakeIdentityd(doubleBuffer2);
        for (int k = 0; k < 4; ++k) {
            int n = k;
            for (int l = k + 1; l < 4; ++l) {
                if (Math.abs(tempMatrixBuf.get(l * 4 + k)) > Math.abs(tempMatrixBuf.get(k * 4 + k))) {
                    n = l;
                }
            }
            if (n != k) {
                for (int n2 = 0; n2 < 4; ++n2) {
                    final double value = tempMatrixBuf.get(k * 4 + n2);
                    tempMatrixBuf.put(k * 4 + n2, tempMatrixBuf.get(n * 4 + n2));
                    tempMatrixBuf.put(n * 4 + n2, value);
                    final double value2 = doubleBuffer2.get(k * 4 + n2 + position2);
                    doubleBuffer2.put(k * 4 + n2 + position2, doubleBuffer2.get(n * 4 + n2 + position2));
                    doubleBuffer2.put(n * 4 + n2 + position2, value2);
                }
            }
            if (tempMatrixBuf.get(k * 4 + k) == 0.0) {
                return false;
            }
            final double value3 = tempMatrixBuf.get(k * 4 + k);
            for (int n3 = 0; n3 < 4; ++n3) {
                tempMatrixBuf.put(k * 4 + n3, tempMatrixBuf.get(k * 4 + n3) / value3);
                doubleBuffer2.put(k * 4 + n3 + position2, doubleBuffer2.get(k * 4 + n3 + position2) / value3);
            }
            for (int n4 = 0; n4 < 4; ++n4) {
                if (n4 != k) {
                    final double value4 = tempMatrixBuf.get(n4 * 4 + k);
                    for (int n5 = 0; n5 < 4; ++n5) {
                        tempMatrixBuf.put(n4 * 4 + n5, tempMatrixBuf.get(n4 * 4 + n5) - tempMatrixBuf.get(k * 4 + n5) * value4);
                        doubleBuffer2.put(n4 * 4 + n5 + position2, doubleBuffer2.get(n4 * 4 + n5 + position2) - doubleBuffer2.get(k * 4 + n5 + position2) * value4);
                    }
                }
            }
        }
        return true;
    }
    
    private void __gluMultMatricesd(final double[] array, final int n, final double[] array2, final int n2, final double[] array3) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                array3[i * 4 + j] = array[i * 4 + 0 + n] * array2[0 + j + n2] + array[i * 4 + 1 + n] * array2[4 + j + n2] + array[i * 4 + 2 + n] * array2[8 + j + n2] + array[i * 4 + 3 + n] * array2[12 + j + n2];
            }
        }
    }
    
    private void __gluMultMatricesd(final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final DoubleBuffer doubleBuffer3) {
        final int position = doubleBuffer.position();
        final int position2 = doubleBuffer2.position();
        final int position3 = doubleBuffer3.position();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                doubleBuffer3.put(i * 4 + j + position3, doubleBuffer.get(i * 4 + 0 + position) * doubleBuffer2.get(0 + j + position2) + doubleBuffer.get(i * 4 + 1 + position) * doubleBuffer2.get(4 + j + position2) + doubleBuffer.get(i * 4 + 2 + position) * doubleBuffer2.get(8 + j + position2) + doubleBuffer.get(i * 4 + 3 + position) * doubleBuffer2.get(12 + j + position2));
            }
        }
    }
    
    private static void normalize(final DoubleBuffer doubleBuffer) {
        final int position = doubleBuffer.position();
        final double sqrt = Math.sqrt(doubleBuffer.get(0 + position) * doubleBuffer.get(0 + position) + doubleBuffer.get(1 + position) * doubleBuffer.get(1 + position) + doubleBuffer.get(2 + position) * doubleBuffer.get(2 + position));
        if (sqrt == 0.0) {
            return;
        }
        final double n = 1.0 / sqrt;
        doubleBuffer.put(0 + position, doubleBuffer.get(0 + position) * n);
        doubleBuffer.put(1 + position, doubleBuffer.get(1 + position) * n);
        doubleBuffer.put(2 + position, doubleBuffer.get(2 + position) * n);
    }
    
    private static void cross(final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final DoubleBuffer doubleBuffer3) {
        final int position = doubleBuffer.position();
        final int position2 = doubleBuffer2.position();
        final int position3 = doubleBuffer3.position();
        doubleBuffer3.put(0 + position3, doubleBuffer.get(1 + position) * doubleBuffer2.get(2 + position2) - doubleBuffer.get(2 + position) * doubleBuffer2.get(1 + position2));
        doubleBuffer3.put(1 + position3, doubleBuffer.get(2 + position) * doubleBuffer2.get(0 + position2) - doubleBuffer.get(0 + position) * doubleBuffer2.get(2 + position2));
        doubleBuffer3.put(2 + position3, doubleBuffer.get(0 + position) * doubleBuffer2.get(1 + position2) - doubleBuffer.get(1 + position) * doubleBuffer2.get(0 + position2));
    }
    
    public void gluOrtho2D(final GL2 gl2, final double n, final double n2, final double n3, final double n4) {
        gl2.glOrtho(n, n2, n3, n4, -1.0, 1.0);
    }
    
    public void gluPerspective(final GL2 gl2, final double n, final double n2, final double n3, final double n4) {
        final double n5 = n / 2.0 * 3.141592653589793 / 180.0;
        final double n6 = n4 - n3;
        final double sin = Math.sin(n5);
        if (n6 == 0.0 || sin == 0.0 || n2 == 0.0) {
            return;
        }
        final double n7 = Math.cos(n5) / sin;
        this.__gluMakeIdentityd(this.matrixBuf);
        this.matrixBuf.put(0, n7 / n2);
        this.matrixBuf.put(5, n7);
        this.matrixBuf.put(10, -(n4 + n3) / n6);
        this.matrixBuf.put(11, -1.0);
        this.matrixBuf.put(14, -2.0 * n3 * n4 / n6);
        this.matrixBuf.put(15, 0.0);
        gl2.glMultMatrixd(this.matrixBuf);
    }
    
    public void gluLookAt(final GL2 gl2, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8, final double n9) {
        final DoubleBuffer forwardBuf = this.forwardBuf;
        final DoubleBuffer sideBuf = this.sideBuf;
        final DoubleBuffer upBuf = this.upBuf;
        forwardBuf.put(0, n4 - n);
        forwardBuf.put(1, n5 - n2);
        forwardBuf.put(2, n6 - n3);
        upBuf.put(0, n7);
        upBuf.put(1, n8);
        upBuf.put(2, n9);
        normalize(forwardBuf);
        cross(forwardBuf, upBuf, sideBuf);
        normalize(sideBuf);
        cross(sideBuf, forwardBuf, upBuf);
        this.__gluMakeIdentityd(this.matrixBuf);
        this.matrixBuf.put(0, sideBuf.get(0));
        this.matrixBuf.put(4, sideBuf.get(1));
        this.matrixBuf.put(8, sideBuf.get(2));
        this.matrixBuf.put(1, upBuf.get(0));
        this.matrixBuf.put(5, upBuf.get(1));
        this.matrixBuf.put(9, upBuf.get(2));
        this.matrixBuf.put(2, -forwardBuf.get(0));
        this.matrixBuf.put(6, -forwardBuf.get(1));
        this.matrixBuf.put(10, -forwardBuf.get(2));
        gl2.glMultMatrixd(this.matrixBuf);
        gl2.glTranslated(-n, -n2, -n3);
    }
    
    public boolean gluProject(final double n, final double n2, final double n3, final double[] array, final int n4, final double[] array2, final int n5, final int[] array3, final int n6, final double[] array4, final int n7) {
        final double[] in = this.in;
        final double[] out = this.out;
        in[0] = n;
        in[1] = n2;
        in[2] = n3;
        in[3] = 1.0;
        this.__gluMultMatrixVecd(array, n4, in, out);
        this.__gluMultMatrixVecd(array2, n5, out, in);
        if (in[3] == 0.0) {
            return false;
        }
        in[3] = 1.0 / in[3] * 0.5;
        in[0] = in[0] * in[3] + 0.5;
        in[1] = in[1] * in[3] + 0.5;
        in[2] = in[2] * in[3] + 0.5;
        array4[0 + n7] = in[0] * array3[2 + n6] + array3[0 + n6];
        array4[1 + n7] = in[1] * array3[3 + n6] + array3[1 + n6];
        array4[2 + n7] = in[2];
        return true;
    }
    
    public boolean gluProject(final double n, final double n2, final double n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer3) {
        final DoubleBuffer inBuf = this.inBuf;
        final DoubleBuffer outBuf = this.outBuf;
        inBuf.put(0, n);
        inBuf.put(1, n2);
        inBuf.put(2, n3);
        inBuf.put(3, 1.0);
        this.__gluMultMatrixVecd(doubleBuffer, inBuf, outBuf);
        this.__gluMultMatrixVecd(doubleBuffer2, outBuf, inBuf);
        if (inBuf.get(3) == 0.0) {
            return false;
        }
        inBuf.put(3, 1.0 / inBuf.get(3) * 0.5);
        inBuf.put(0, inBuf.get(0) * inBuf.get(3) + 0.5);
        inBuf.put(1, inBuf.get(1) * inBuf.get(3) + 0.5);
        inBuf.put(2, inBuf.get(2) * inBuf.get(3) + 0.5);
        final int position = intBuffer.position();
        final int position2 = doubleBuffer3.position();
        doubleBuffer3.put(0 + position2, inBuf.get(0) * intBuffer.get(2 + position) + intBuffer.get(0 + position));
        doubleBuffer3.put(1 + position2, inBuf.get(1) * intBuffer.get(3 + position) + intBuffer.get(1 + position));
        doubleBuffer3.put(2 + position2, inBuf.get(2));
        return true;
    }
    
    public boolean gluUnProject(final double n, final double n2, final double n3, final double[] array, final int n4, final double[] array2, final int n5, final int[] array3, final int n6, final double[] array4, final int n7) {
        final double[] in = this.in;
        final double[] out = this.out;
        this.__gluMultMatricesd(array, n4, array2, n5, this.matrix);
        if (!this.__gluInvertMatrixd(this.matrix, this.matrix)) {
            return false;
        }
        in[0] = n;
        in[1] = n2;
        in[2] = n3;
        in[3] = 1.0;
        in[0] = (in[0] - array3[0 + n6]) / array3[2 + n6];
        in[1] = (in[1] - array3[1 + n6]) / array3[3 + n6];
        in[0] = in[0] * 2.0 - 1.0;
        in[1] = in[1] * 2.0 - 1.0;
        in[2] = in[2] * 2.0 - 1.0;
        this.__gluMultMatrixVecd(this.matrix, 0, in, out);
        if (out[3] == 0.0) {
            return false;
        }
        out[3] = 1.0 / out[3];
        array4[0 + n7] = out[0] * out[3];
        array4[1 + n7] = out[1] * out[3];
        array4[2 + n7] = out[2] * out[3];
        return true;
    }
    
    public boolean gluUnProject(final double n, final double n2, final double n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer3) {
        final DoubleBuffer inBuf = this.inBuf;
        final DoubleBuffer outBuf = this.outBuf;
        this.__gluMultMatricesd(doubleBuffer, doubleBuffer2, this.matrixBuf);
        if (!this.__gluInvertMatrixd(this.matrixBuf, this.matrixBuf)) {
            return false;
        }
        inBuf.put(0, n);
        inBuf.put(1, n2);
        inBuf.put(2, n3);
        inBuf.put(3, 1.0);
        final int position = intBuffer.position();
        final int position2 = doubleBuffer3.position();
        inBuf.put(0, (inBuf.get(0) - intBuffer.get(0 + position)) / intBuffer.get(2 + position));
        inBuf.put(1, (inBuf.get(1) - intBuffer.get(1 + position)) / intBuffer.get(3 + position));
        inBuf.put(0, inBuf.get(0) * 2.0 - 1.0);
        inBuf.put(1, inBuf.get(1) * 2.0 - 1.0);
        inBuf.put(2, inBuf.get(2) * 2.0 - 1.0);
        this.__gluMultMatrixVecd(this.matrixBuf, inBuf, outBuf);
        if (outBuf.get(3) == 0.0) {
            return false;
        }
        outBuf.put(3, 1.0 / outBuf.get(3));
        doubleBuffer3.put(0 + position2, outBuf.get(0) * outBuf.get(3));
        doubleBuffer3.put(1 + position2, outBuf.get(1) * outBuf.get(3));
        doubleBuffer3.put(2 + position2, outBuf.get(2) * outBuf.get(3));
        return true;
    }
    
    public boolean gluUnProject4(final double n, final double n2, final double n3, final double n4, final double[] array, final int n5, final double[] array2, final int n6, final int[] array3, final int n7, final double n8, final double n9, final double[] array4, final int n10) {
        final double[] in = this.in;
        final double[] out = this.out;
        this.__gluMultMatricesd(array, n5, array2, n6, this.matrix);
        if (!this.__gluInvertMatrixd(this.matrix, this.matrix)) {
            return false;
        }
        in[0] = n;
        in[1] = n2;
        in[2] = n3;
        in[3] = n4;
        in[0] = (in[0] - array3[0 + n7]) / array3[2 + n7];
        in[1] = (in[1] - array3[1 + n7]) / array3[3 + n7];
        in[2] = (in[2] - n8) / (n9 - n8);
        in[0] = in[0] * 2.0 - 1.0;
        in[1] = in[1] * 2.0 - 1.0;
        in[2] = in[2] * 2.0 - 1.0;
        this.__gluMultMatrixVecd(this.matrix, 0, in, out);
        if (out[3] == 0.0) {
            return false;
        }
        array4[0 + n10] = out[0];
        array4[1 + n10] = out[1];
        array4[2 + n10] = out[2];
        array4[3 + n10] = out[3];
        return true;
    }
    
    public boolean gluUnProject4(final double n, final double n2, final double n3, final double n4, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final IntBuffer intBuffer, final double n5, final double n6, final DoubleBuffer doubleBuffer3) {
        final DoubleBuffer inBuf = this.inBuf;
        final DoubleBuffer outBuf = this.outBuf;
        this.__gluMultMatricesd(doubleBuffer, doubleBuffer2, this.matrixBuf);
        if (!this.__gluInvertMatrixd(this.matrixBuf, this.matrixBuf)) {
            return false;
        }
        inBuf.put(0, n);
        inBuf.put(1, n2);
        inBuf.put(2, n3);
        inBuf.put(3, n4);
        final int position = intBuffer.position();
        inBuf.put(0, (inBuf.get(0) - intBuffer.get(0 + position)) / intBuffer.get(2 + position));
        inBuf.put(1, (inBuf.get(1) - intBuffer.get(1 + position)) / intBuffer.get(3 + position));
        inBuf.put(2, (inBuf.get(2) - n5) / (n6 - n5));
        inBuf.put(0, inBuf.get(0) * 2.0 - 1.0);
        inBuf.put(1, inBuf.get(1) * 2.0 - 1.0);
        inBuf.put(2, inBuf.get(2) * 2.0 - 1.0);
        this.__gluMultMatrixVecd(this.matrixBuf, inBuf, outBuf);
        if (outBuf.get(3) == 0.0) {
            return false;
        }
        final int position2 = doubleBuffer3.position();
        doubleBuffer3.put(0 + position2, outBuf.get(0));
        doubleBuffer3.put(1 + position2, outBuf.get(1));
        doubleBuffer3.put(2 + position2, outBuf.get(2));
        doubleBuffer3.put(3 + position2, outBuf.get(3));
        return true;
    }
    
    public void gluPickMatrix(final GL2 gl2, final double n, final double n2, final double n3, final double n4, final IntBuffer intBuffer) {
        if (n3 <= 0.0 || n4 <= 0.0) {
            return;
        }
        final int position = intBuffer.position();
        gl2.glTranslated((intBuffer.get(2 + position) - 2.0 * (n - intBuffer.get(0 + position))) / n3, (intBuffer.get(3 + position) - 2.0 * (n2 - intBuffer.get(1 + position))) / n4, 0.0);
        gl2.glScaled(intBuffer.get(2) / n3, intBuffer.get(3) / n4, 1.0);
    }
    
    public void gluPickMatrix(final GL2 gl2, final double n, final double n2, final double n3, final double n4, final int[] array, final int n5) {
        if (n3 <= 0.0 || n4 <= 0.0) {
            return;
        }
        gl2.glTranslated((array[2 + n5] - 2.0 * (n - array[0 + n5])) / n3, (array[3 + n5] - 2.0 * (n2 - array[1 + n5])) / n4, 0.0);
        gl2.glScaled(array[2 + n5] / n3, array[3 + n5] / n4, 1.0);
    }
    
    static {
        IDENTITY_MATRIX = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
    }
}
