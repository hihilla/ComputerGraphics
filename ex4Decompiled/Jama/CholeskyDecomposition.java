// 
// Decompiled by Procyon v0.5.30
// 

package Jama;

import java.io.Serializable;

public class CholeskyDecomposition implements Serializable
{
    private double[][] L;
    private int n;
    private boolean isspd;
    private static final long serialVersionUID = 1L;
    
    public CholeskyDecomposition(final Matrix matrix) {
        final double[][] array = matrix.getArray();
        this.n = matrix.getRowDimension();
        this.L = new double[this.n][this.n];
        this.isspd = (matrix.getColumnDimension() == this.n);
        for (int i = 0; i < this.n; ++i) {
            final double[] array2 = this.L[i];
            double n = 0.0;
            for (int j = 0; j < i; ++j) {
                final double[] array3 = this.L[j];
                double n2 = 0.0;
                for (int k = 0; k < j; ++k) {
                    n2 += array3[k] * array2[k];
                }
                final double n3 = array2[j] = (array[i][j] - n2) / this.L[j][j];
                n += n3 * n3;
                this.isspd &= (array[j][i] == array[i][j]);
            }
            final double n4 = array[i][i] - n;
            this.isspd &= (n4 > 0.0);
            this.L[i][i] = Math.sqrt(Math.max(n4, 0.0));
            for (int l = i + 1; l < this.n; ++l) {
                this.L[i][l] = 0.0;
            }
        }
    }
    
    public boolean isSPD() {
        return this.isspd;
    }
    
    public Matrix getL() {
        return new Matrix(this.L, this.n, this.n);
    }
    
    public Matrix solve(final Matrix matrix) {
        if (matrix.getRowDimension() != this.n) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!this.isspd) {
            throw new RuntimeException("Matrix is not symmetric positive definite.");
        }
        final double[][] arrayCopy = matrix.getArrayCopy();
        final int columnDimension = matrix.getColumnDimension();
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < columnDimension; ++j) {
                for (int k = 0; k < i; ++k) {
                    final double[] array = arrayCopy[i];
                    final int n = j;
                    array[n] -= arrayCopy[k][j] * this.L[i][k];
                }
                final double[] array2 = arrayCopy[i];
                final int n2 = j;
                array2[n2] /= this.L[i][i];
            }
        }
        for (int l = this.n - 1; l >= 0; --l) {
            for (int n3 = 0; n3 < columnDimension; ++n3) {
                for (int n4 = l + 1; n4 < this.n; ++n4) {
                    final double[] array3 = arrayCopy[l];
                    final int n5 = n3;
                    array3[n5] -= arrayCopy[n4][n3] * this.L[n4][l];
                }
                final double[] array4 = arrayCopy[l];
                final int n6 = n3;
                array4[n6] /= this.L[l][l];
            }
        }
        return new Matrix(arrayCopy, this.n, columnDimension);
    }
}
