// 
// Decompiled by Procyon v0.5.30
// 

package Jama;

import java.io.Serializable;

public class LUDecomposition implements Serializable
{
    private double[][] LU;
    private int m;
    private int n;
    private int pivsign;
    private int[] piv;
    private static final long serialVersionUID = 1L;
    
    public LUDecomposition(final Matrix matrix) {
        this.LU = matrix.getArrayCopy();
        this.m = matrix.getRowDimension();
        this.n = matrix.getColumnDimension();
        this.piv = new int[this.m];
        for (int i = 0; i < this.m; ++i) {
            this.piv[i] = i;
        }
        this.pivsign = 1;
        final double[] array = new double[this.m];
        for (int j = 0; j < this.n; ++j) {
            for (int k = 0; k < this.m; ++k) {
                array[k] = this.LU[k][j];
            }
            for (int l = 0; l < this.m; ++l) {
                final double[] array2 = this.LU[l];
                final int min = Math.min(l, j);
                double n = 0.0;
                for (int n2 = 0; n2 < min; ++n2) {
                    n += array2[n2] * array[n2];
                }
                final double[] array3 = array2;
                final int n3 = j;
                final double[] array4 = array;
                final int n4 = l;
                array3[n3] = (array4[n4] -= n);
            }
            int n5 = j;
            for (int n6 = j + 1; n6 < this.m; ++n6) {
                if (Math.abs(array[n6]) > Math.abs(array[n5])) {
                    n5 = n6;
                }
            }
            if (n5 != j) {
                for (int n7 = 0; n7 < this.n; ++n7) {
                    final double n8 = this.LU[n5][n7];
                    this.LU[n5][n7] = this.LU[j][n7];
                    this.LU[j][n7] = n8;
                }
                final int n9 = this.piv[n5];
                this.piv[n5] = this.piv[j];
                this.piv[j] = n9;
                this.pivsign = -this.pivsign;
            }
            if (j < this.m & this.LU[j][j] != 0.0) {
                for (int n10 = j + 1; n10 < this.m; ++n10) {
                    final double[] array5 = this.LU[n10];
                    final int n11 = j;
                    array5[n11] /= this.LU[j][j];
                }
            }
        }
    }
    
    public boolean isNonsingular() {
        for (int i = 0; i < this.n; ++i) {
            if (this.LU[i][i] == 0.0) {
                return false;
            }
        }
        return true;
    }
    
    public Matrix getL() {
        final Matrix matrix = new Matrix(this.m, this.n);
        final double[][] array = matrix.getArray();
        for (int i = 0; i < this.m; ++i) {
            for (int j = 0; j < this.n; ++j) {
                if (i > j) {
                    array[i][j] = this.LU[i][j];
                }
                else if (i == j) {
                    array[i][j] = 1.0;
                }
                else {
                    array[i][j] = 0.0;
                }
            }
        }
        return matrix;
    }
    
    public Matrix getU() {
        final Matrix matrix = new Matrix(this.n, this.n);
        final double[][] array = matrix.getArray();
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                if (i <= j) {
                    array[i][j] = this.LU[i][j];
                }
                else {
                    array[i][j] = 0.0;
                }
            }
        }
        return matrix;
    }
    
    public int[] getPivot() {
        final int[] array = new int[this.m];
        for (int i = 0; i < this.m; ++i) {
            array[i] = this.piv[i];
        }
        return array;
    }
    
    public double[] getDoublePivot() {
        final double[] array = new double[this.m];
        for (int i = 0; i < this.m; ++i) {
            array[i] = this.piv[i];
        }
        return array;
    }
    
    public double det() {
        if (this.m != this.n) {
            throw new IllegalArgumentException("Matrix must be square.");
        }
        double n = this.pivsign;
        for (int i = 0; i < this.n; ++i) {
            n *= this.LU[i][i];
        }
        return n;
    }
    
    public Matrix solve(final Matrix matrix) {
        if (matrix.getRowDimension() != this.m) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!this.isNonsingular()) {
            throw new RuntimeException("Matrix is singular.");
        }
        final int columnDimension = matrix.getColumnDimension();
        final Matrix matrix2 = matrix.getMatrix(this.piv, 0, columnDimension - 1);
        final double[][] array = matrix2.getArray();
        for (int i = 0; i < this.n; ++i) {
            for (int j = i + 1; j < this.n; ++j) {
                for (int k = 0; k < columnDimension; ++k) {
                    final double[] array2 = array[j];
                    final int n = k;
                    array2[n] -= array[i][k] * this.LU[j][i];
                }
            }
        }
        for (int l = this.n - 1; l >= 0; --l) {
            for (int n2 = 0; n2 < columnDimension; ++n2) {
                final double[] array3 = array[l];
                final int n3 = n2;
                array3[n3] /= this.LU[l][l];
            }
            for (int n4 = 0; n4 < l; ++n4) {
                for (int n5 = 0; n5 < columnDimension; ++n5) {
                    final double[] array4 = array[n4];
                    final int n6 = n5;
                    array4[n6] -= array[l][n5] * this.LU[n4][l];
                }
            }
        }
        return matrix2;
    }
}
