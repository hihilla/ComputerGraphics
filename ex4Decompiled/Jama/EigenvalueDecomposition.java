// 
// Decompiled by Procyon v0.5.30
// 

package Jama;

import Jama.util.Maths;
import java.io.Serializable;

public class EigenvalueDecomposition implements Serializable
{
    private int n;
    private boolean issymmetric;
    private double[] d;
    private double[] e;
    private double[][] V;
    private double[][] H;
    private double[] ort;
    private transient double cdivr;
    private transient double cdivi;
    private static final long serialVersionUID = 1L;
    
    private void tred2() {
        for (int i = 0; i < this.n; ++i) {
            this.d[i] = this.V[this.n - 1][i];
        }
        for (int j = this.n - 1; j > 0; --j) {
            double n = 0.0;
            double n2 = 0.0;
            for (int k = 0; k < j; ++k) {
                n += Math.abs(this.d[k]);
            }
            if (n == 0.0) {
                this.e[j] = this.d[j - 1];
                for (int l = 0; l < j; ++l) {
                    this.d[l] = this.V[j - 1][l];
                    this.V[j][l] = 0.0;
                    this.V[l][j] = 0.0;
                }
            }
            else {
                for (int n3 = 0; n3 < j; ++n3) {
                    final double[] d = this.d;
                    final int n4 = n3;
                    d[n4] /= n;
                    n2 += this.d[n3] * this.d[n3];
                }
                final double n5 = this.d[j - 1];
                double sqrt = Math.sqrt(n2);
                if (n5 > 0.0) {
                    sqrt = -sqrt;
                }
                this.e[j] = n * sqrt;
                n2 -= n5 * sqrt;
                this.d[j - 1] = n5 - sqrt;
                for (int n6 = 0; n6 < j; ++n6) {
                    this.e[n6] = 0.0;
                }
                for (int n7 = 0; n7 < j; ++n7) {
                    final double n8 = this.d[n7];
                    this.V[n7][j] = n8;
                    double n9 = this.e[n7] + this.V[n7][n7] * n8;
                    for (int n10 = n7 + 1; n10 <= j - 1; ++n10) {
                        n9 += this.V[n10][n7] * this.d[n10];
                        final double[] e = this.e;
                        final int n11 = n10;
                        e[n11] += this.V[n10][n7] * n8;
                    }
                    this.e[n7] = n9;
                }
                double n12 = 0.0;
                for (int n13 = 0; n13 < j; ++n13) {
                    final double[] e2 = this.e;
                    final int n14 = n13;
                    e2[n14] /= n2;
                    n12 += this.e[n13] * this.d[n13];
                }
                final double n15 = n12 / (n2 + n2);
                for (int n16 = 0; n16 < j; ++n16) {
                    final double[] e3 = this.e;
                    final int n17 = n16;
                    e3[n17] -= n15 * this.d[n16];
                }
                for (int n18 = 0; n18 < j; ++n18) {
                    final double n19 = this.d[n18];
                    final double n20 = this.e[n18];
                    for (int n21 = n18; n21 <= j - 1; ++n21) {
                        final double[] array = this.V[n21];
                        final int n22 = n18;
                        array[n22] -= n19 * this.e[n21] + n20 * this.d[n21];
                    }
                    this.d[n18] = this.V[j - 1][n18];
                    this.V[j][n18] = 0.0;
                }
            }
            this.d[j] = n2;
        }
        for (int n23 = 0; n23 < this.n - 1; ++n23) {
            this.V[this.n - 1][n23] = this.V[n23][n23];
            this.V[n23][n23] = 1.0;
            final double n24 = this.d[n23 + 1];
            if (n24 != 0.0) {
                for (int n25 = 0; n25 <= n23; ++n25) {
                    this.d[n25] = this.V[n25][n23 + 1] / n24;
                }
                for (int n26 = 0; n26 <= n23; ++n26) {
                    double n27 = 0.0;
                    for (int n28 = 0; n28 <= n23; ++n28) {
                        n27 += this.V[n28][n23 + 1] * this.V[n28][n26];
                    }
                    for (int n29 = 0; n29 <= n23; ++n29) {
                        final double[] array2 = this.V[n29];
                        final int n30 = n26;
                        array2[n30] -= n27 * this.d[n29];
                    }
                }
            }
            for (int n31 = 0; n31 <= n23; ++n31) {
                this.V[n31][n23 + 1] = 0.0;
            }
        }
        for (int n32 = 0; n32 < this.n; ++n32) {
            this.d[n32] = this.V[this.n - 1][n32];
            this.V[this.n - 1][n32] = 0.0;
        }
        this.V[this.n - 1][this.n - 1] = 1.0;
        this.e[0] = 0.0;
    }
    
    private void tql2() {
        for (int i = 1; i < this.n; ++i) {
            this.e[i - 1] = this.e[i];
        }
        this.e[this.n - 1] = 0.0;
        double n = 0.0;
        double max = 0.0;
        final double pow = Math.pow(2.0, -52.0);
        for (int j = 0; j < this.n; ++j) {
            int n2;
            for (max = Math.max(max, Math.abs(this.d[j]) + Math.abs(this.e[j])), n2 = j; n2 < this.n && Math.abs(this.e[n2]) > pow * max; ++n2) {}
            if (n2 > j) {
                int n3 = 0;
                do {
                    ++n3;
                    final double n4 = this.d[j];
                    final double n5 = (this.d[j + 1] - n4) / (2.0 * this.e[j]);
                    double hypot = Maths.hypot(n5, 1.0);
                    if (n5 < 0.0) {
                        hypot = -hypot;
                    }
                    this.d[j] = this.e[j] / (n5 + hypot);
                    this.d[j + 1] = this.e[j] * (n5 + hypot);
                    final double n6 = this.d[j + 1];
                    final double n7 = n4 - this.d[j];
                    for (int k = j + 2; k < this.n; ++k) {
                        final double[] d = this.d;
                        final int n8 = k;
                        d[n8] -= n7;
                    }
                    n += n7;
                    double n9 = this.d[n2];
                    double n12;
                    double n11;
                    double n10 = n11 = (n12 = 1.0);
                    final double n13 = this.e[j + 1];
                    double n14 = 0.0;
                    double n15 = 0.0;
                    for (int l = n2 - 1; l >= j; --l) {
                        n11 = n12;
                        n12 = n10;
                        n15 = n14;
                        final double n16 = n10 * this.e[l];
                        final double n17 = n10 * n9;
                        final double hypot2 = Maths.hypot(n9, this.e[l]);
                        this.e[l + 1] = n14 * hypot2;
                        n14 = this.e[l] / hypot2;
                        n10 = n9 / hypot2;
                        n9 = n10 * this.d[l] - n14 * n16;
                        this.d[l + 1] = n17 + n14 * (n10 * n16 + n14 * this.d[l]);
                        for (int n18 = 0; n18 < this.n; ++n18) {
                            final double n19 = this.V[n18][l + 1];
                            this.V[n18][l + 1] = n14 * this.V[n18][l] + n10 * n19;
                            this.V[n18][l] = n10 * this.V[n18][l] - n14 * n19;
                        }
                    }
                    final double n20 = -n14 * n15 * n11 * n13 * this.e[j] / n6;
                    this.e[j] = n14 * n20;
                    this.d[j] = n10 * n20;
                } while (Math.abs(this.e[j]) > pow * max);
            }
            this.d[j] += n;
            this.e[j] = 0.0;
        }
        for (int n21 = 0; n21 < this.n - 1; ++n21) {
            int n22 = n21;
            double n23 = this.d[n21];
            for (int n24 = n21 + 1; n24 < this.n; ++n24) {
                if (this.d[n24] < n23) {
                    n22 = n24;
                    n23 = this.d[n24];
                }
            }
            if (n22 != n21) {
                this.d[n22] = this.d[n21];
                this.d[n21] = n23;
                for (int n25 = 0; n25 < this.n; ++n25) {
                    final double n26 = this.V[n25][n21];
                    this.V[n25][n21] = this.V[n25][n22];
                    this.V[n25][n22] = n26;
                }
            }
        }
    }
    
    private void orthes() {
        final int n = 0;
        final int n2 = this.n - 1;
        for (int i = n + 1; i <= n2 - 1; ++i) {
            double n3 = 0.0;
            for (int j = i; j <= n2; ++j) {
                n3 += Math.abs(this.H[j][i - 1]);
            }
            if (n3 != 0.0) {
                double n4 = 0.0;
                for (int k = n2; k >= i; --k) {
                    this.ort[k] = this.H[k][i - 1] / n3;
                    n4 += this.ort[k] * this.ort[k];
                }
                double sqrt = Math.sqrt(n4);
                if (this.ort[i] > 0.0) {
                    sqrt = -sqrt;
                }
                final double n5 = n4 - this.ort[i] * sqrt;
                this.ort[i] -= sqrt;
                for (int l = i; l < this.n; ++l) {
                    double n6 = 0.0;
                    for (int n7 = n2; n7 >= i; --n7) {
                        n6 += this.ort[n7] * this.H[n7][l];
                    }
                    final double n8 = n6 / n5;
                    for (int n9 = i; n9 <= n2; ++n9) {
                        final double[] array = this.H[n9];
                        final int n10 = l;
                        array[n10] -= n8 * this.ort[n9];
                    }
                }
                for (int n11 = 0; n11 <= n2; ++n11) {
                    double n12 = 0.0;
                    for (int n13 = n2; n13 >= i; --n13) {
                        n12 += this.ort[n13] * this.H[n11][n13];
                    }
                    final double n14 = n12 / n5;
                    for (int n15 = i; n15 <= n2; ++n15) {
                        final double[] array2 = this.H[n11];
                        final int n16 = n15;
                        array2[n16] -= n14 * this.ort[n15];
                    }
                }
                this.ort[i] *= n3;
                this.H[i][i - 1] = n3 * sqrt;
            }
        }
        for (int n17 = 0; n17 < this.n; ++n17) {
            for (int n18 = 0; n18 < this.n; ++n18) {
                this.V[n17][n18] = ((n17 == n18) ? 1.0 : 0.0);
            }
        }
        for (int n19 = n2 - 1; n19 >= n + 1; --n19) {
            if (this.H[n19][n19 - 1] != 0.0) {
                for (int n20 = n19 + 1; n20 <= n2; ++n20) {
                    this.ort[n20] = this.H[n20][n19 - 1];
                }
                for (int n21 = n19; n21 <= n2; ++n21) {
                    double n22 = 0.0;
                    for (int n23 = n19; n23 <= n2; ++n23) {
                        n22 += this.ort[n23] * this.V[n23][n21];
                    }
                    final double n24 = n22 / this.ort[n19] / this.H[n19][n19 - 1];
                    for (int n25 = n19; n25 <= n2; ++n25) {
                        final double[] array3 = this.V[n25];
                        final int n26 = n21;
                        array3[n26] += n24 * this.ort[n25];
                    }
                }
            }
        }
    }
    
    private void cdiv(final double n, final double n2, final double n3, final double n4) {
        if (Math.abs(n3) > Math.abs(n4)) {
            final double n5 = n4 / n3;
            final double n6 = n3 + n5 * n4;
            this.cdivr = (n + n5 * n2) / n6;
            this.cdivi = (n2 - n5 * n) / n6;
        }
        else {
            final double n7 = n3 / n4;
            final double n8 = n4 + n7 * n3;
            this.cdivr = (n7 * n + n2) / n8;
            this.cdivi = (n7 * n2 - n) / n8;
        }
    }
    
    private void hqr2() {
        final int n = this.n;
        int i = n - 1;
        final int n2 = 0;
        final int n3 = n - 1;
        final double pow = Math.pow(2.0, -52.0);
        double n4 = 0.0;
        double n5 = 0.0;
        double n6 = 0.0;
        double sqrt = 0.0;
        double sqrt2 = 0.0;
        double sqrt3 = 0.0;
        double n7 = 0.0;
        for (int j = 0; j < n; ++j) {
            if (j < n2 | j > n3) {
                this.d[j] = this.H[j][j];
                this.e[j] = 0.0;
            }
            for (int k = Math.max(j - 1, 0); k < n; ++k) {
                n7 += Math.abs(this.H[j][k]);
            }
        }
        int n8 = 0;
        while (i >= n2) {
            int l;
            for (l = i; l > n2; --l) {
                sqrt2 = Math.abs(this.H[l - 1][l - 1]) + Math.abs(this.H[l][l]);
                if (sqrt2 == 0.0) {
                    sqrt2 = n7;
                }
                if (Math.abs(this.H[l][l - 1]) < pow * sqrt2) {
                    break;
                }
            }
            if (l == i) {
                this.H[i][i] += n4;
                this.d[i] = this.H[i][i];
                this.e[i] = 0.0;
                --i;
                n8 = 0;
            }
            else if (l == i - 1) {
                final double n9 = this.H[i][i - 1] * this.H[i - 1][i];
                n5 = (this.H[i - 1][i - 1] - this.H[i][i]) / 2.0;
                n6 = n5 * n5 + n9;
                sqrt3 = Math.sqrt(Math.abs(n6));
                this.H[i][i] += n4;
                this.H[i - 1][i - 1] += n4;
                final double n10 = this.H[i][i];
                if (n6 >= 0.0) {
                    if (n5 >= 0.0) {
                        sqrt3 += n5;
                    }
                    else {
                        sqrt3 = n5 - sqrt3;
                    }
                    this.d[i - 1] = n10 + sqrt3;
                    this.d[i] = this.d[i - 1];
                    if (sqrt3 != 0.0) {
                        this.d[i] = n10 - n9 / sqrt3;
                    }
                    this.e[i - 1] = 0.0;
                    this.e[i] = 0.0;
                    final double n11 = this.H[i][i - 1];
                    sqrt2 = Math.abs(n11) + Math.abs(sqrt3);
                    final double n12 = n11 / sqrt2;
                    final double n13 = sqrt3 / sqrt2;
                    sqrt = Math.sqrt(n12 * n12 + n13 * n13);
                    n5 = n12 / sqrt;
                    n6 = n13 / sqrt;
                    for (int n14 = i - 1; n14 < n; ++n14) {
                        sqrt3 = this.H[i - 1][n14];
                        this.H[i - 1][n14] = n6 * sqrt3 + n5 * this.H[i][n14];
                        this.H[i][n14] = n6 * this.H[i][n14] - n5 * sqrt3;
                    }
                    for (int n15 = 0; n15 <= i; ++n15) {
                        sqrt3 = this.H[n15][i - 1];
                        this.H[n15][i - 1] = n6 * sqrt3 + n5 * this.H[n15][i];
                        this.H[n15][i] = n6 * this.H[n15][i] - n5 * sqrt3;
                    }
                    for (int n16 = n2; n16 <= n3; ++n16) {
                        sqrt3 = this.V[n16][i - 1];
                        this.V[n16][i - 1] = n6 * sqrt3 + n5 * this.V[n16][i];
                        this.V[n16][i] = n6 * this.V[n16][i] - n5 * sqrt3;
                    }
                }
                else {
                    this.d[i - 1] = n10 + n5;
                    this.d[i] = n10 + n5;
                    this.e[i - 1] = sqrt3;
                    this.e[i] = -sqrt3;
                }
                i -= 2;
                n8 = 0;
            }
            else {
                double n17 = this.H[i][i];
                double n18 = 0.0;
                double n19 = 0.0;
                if (l < i) {
                    n18 = this.H[i - 1][i - 1];
                    n19 = this.H[i][i - 1] * this.H[i - 1][i];
                }
                if (n8 == 10) {
                    n4 += n17;
                    for (int n20 = n2; n20 <= i; ++n20) {
                        final double[] array = this.H[n20];
                        final int n21 = n20;
                        array[n21] -= n17;
                    }
                    sqrt2 = Math.abs(this.H[i][i - 1]) + Math.abs(this.H[i - 1][i - 2]);
                    n18 = (n17 = 0.75 * sqrt2);
                    n19 = -0.4375 * sqrt2 * sqrt2;
                }
                if (n8 == 30) {
                    final double n22 = (n18 - n17) / 2.0;
                    sqrt2 = n22 * n22 + n19;
                    if (sqrt2 > 0.0) {
                        double sqrt4 = Math.sqrt(sqrt2);
                        if (n18 < n17) {
                            sqrt4 = -sqrt4;
                        }
                        sqrt2 = n17 - n19 / ((n18 - n17) / 2.0 + sqrt4);
                        for (int n23 = n2; n23 <= i; ++n23) {
                            final double[] array2 = this.H[n23];
                            final int n24 = n23;
                            array2[n24] -= sqrt2;
                        }
                        n4 += sqrt2;
                        n18 = (n17 = (n19 = 0.964));
                    }
                }
                ++n8;
                int n25;
                for (n25 = i - 2; n25 >= l; --n25) {
                    sqrt3 = this.H[n25][n25];
                    final double n26 = n17 - sqrt3;
                    final double n27 = n18 - sqrt3;
                    final double n28 = (n26 * n27 - n19) / this.H[n25 + 1][n25] + this.H[n25][n25 + 1];
                    final double n29 = this.H[n25 + 1][n25 + 1] - sqrt3 - n26 - n27;
                    final double n30 = this.H[n25 + 2][n25 + 1];
                    sqrt2 = Math.abs(n28) + Math.abs(n29) + Math.abs(n30);
                    n5 = n28 / sqrt2;
                    n6 = n29 / sqrt2;
                    sqrt = n30 / sqrt2;
                    if (n25 == l) {
                        break;
                    }
                    if (Math.abs(this.H[n25][n25 - 1]) * (Math.abs(n6) + Math.abs(sqrt)) < pow * (Math.abs(n5) * (Math.abs(this.H[n25 - 1][n25 - 1]) + Math.abs(sqrt3) + Math.abs(this.H[n25 + 1][n25 + 1])))) {
                        break;
                    }
                }
                for (int n31 = n25 + 2; n31 <= i; ++n31) {
                    this.H[n31][n31 - 2] = 0.0;
                    if (n31 > n25 + 2) {
                        this.H[n31][n31 - 3] = 0.0;
                    }
                }
                for (int n32 = n25; n32 <= i - 1; ++n32) {
                    final boolean b = n32 != i - 1;
                    if (n32 != n25) {
                        n5 = this.H[n32][n32 - 1];
                        n6 = this.H[n32 + 1][n32 - 1];
                        sqrt = (b ? this.H[n32 + 2][n32 - 1] : 0.0);
                        n17 = Math.abs(n5) + Math.abs(n6) + Math.abs(sqrt);
                        if (n17 == 0.0) {
                            continue;
                        }
                        n5 /= n17;
                        n6 /= n17;
                        sqrt /= n17;
                    }
                    sqrt2 = Math.sqrt(n5 * n5 + n6 * n6 + sqrt * sqrt);
                    if (n5 < 0.0) {
                        sqrt2 = -sqrt2;
                    }
                    if (sqrt2 != 0.0) {
                        if (n32 != n25) {
                            this.H[n32][n32 - 1] = -sqrt2 * n17;
                        }
                        else if (l != n25) {
                            this.H[n32][n32 - 1] = -this.H[n32][n32 - 1];
                        }
                        n5 += sqrt2;
                        n17 = n5 / sqrt2;
                        final double n33 = n6 / sqrt2;
                        sqrt3 = sqrt / sqrt2;
                        n6 /= n5;
                        sqrt /= n5;
                        for (int n34 = n32; n34 < n; ++n34) {
                            n5 = this.H[n32][n34] + n6 * this.H[n32 + 1][n34];
                            if (b) {
                                n5 += sqrt * this.H[n32 + 2][n34];
                                this.H[n32 + 2][n34] -= n5 * sqrt3;
                            }
                            this.H[n32][n34] -= n5 * n17;
                            this.H[n32 + 1][n34] -= n5 * n33;
                        }
                        for (int n35 = 0; n35 <= Math.min(i, n32 + 3); ++n35) {
                            n5 = n17 * this.H[n35][n32] + n33 * this.H[n35][n32 + 1];
                            if (b) {
                                n5 += sqrt3 * this.H[n35][n32 + 2];
                                this.H[n35][n32 + 2] -= n5 * sqrt;
                            }
                            this.H[n35][n32] -= n5;
                            this.H[n35][n32 + 1] -= n5 * n6;
                        }
                        for (int n36 = n2; n36 <= n3; ++n36) {
                            n5 = n17 * this.V[n36][n32] + n33 * this.V[n36][n32 + 1];
                            if (b) {
                                n5 += sqrt3 * this.V[n36][n32 + 2];
                                this.V[n36][n32 + 2] -= n5 * sqrt;
                            }
                            this.V[n36][n32] -= n5;
                            this.V[n36][n32 + 1] -= n5 * n6;
                        }
                    }
                }
            }
        }
        if (n7 == 0.0) {
            return;
        }
        for (int n37 = n - 1; n37 >= 0; --n37) {
            final double n38 = this.d[n37];
            final double n39 = this.e[n37];
            if (n39 == 0.0) {
                int n40 = n37;
                this.H[n37][n37] = 1.0;
                for (int n41 = n37 - 1; n41 >= 0; --n41) {
                    final double n42 = this.H[n41][n41] - n38;
                    sqrt = 0.0;
                    for (int n43 = n40; n43 <= n37; ++n43) {
                        sqrt += this.H[n41][n43] * this.H[n43][n37];
                    }
                    if (this.e[n41] < 0.0) {
                        sqrt3 = n42;
                        sqrt2 = sqrt;
                    }
                    else {
                        n40 = n41;
                        if (this.e[n41] == 0.0) {
                            if (n42 != 0.0) {
                                this.H[n41][n37] = -sqrt / n42;
                            }
                            else {
                                this.H[n41][n37] = -sqrt / (pow * n7);
                            }
                        }
                        else {
                            final double n44 = this.H[n41][n41 + 1];
                            final double n45 = this.H[n41 + 1][n41];
                            final double n46 = (n44 * sqrt2 - sqrt3 * sqrt) / ((this.d[n41] - n38) * (this.d[n41] - n38) + this.e[n41] * this.e[n41]);
                            this.H[n41][n37] = n46;
                            if (Math.abs(n44) > Math.abs(sqrt3)) {
                                this.H[n41 + 1][n37] = (-sqrt - n42 * n46) / n44;
                            }
                            else {
                                this.H[n41 + 1][n37] = (-sqrt2 - n45 * n46) / sqrt3;
                            }
                        }
                        final double abs = Math.abs(this.H[n41][n37]);
                        if (pow * abs * abs > 1.0) {
                            for (int n47 = n41; n47 <= n37; ++n47) {
                                this.H[n47][n37] /= abs;
                            }
                        }
                    }
                }
            }
            else if (n39 < 0.0) {
                int n48 = n37 - 1;
                if (Math.abs(this.H[n37][n37 - 1]) > Math.abs(this.H[n37 - 1][n37])) {
                    this.H[n37 - 1][n37 - 1] = n39 / this.H[n37][n37 - 1];
                    this.H[n37 - 1][n37] = -(this.H[n37][n37] - n38) / this.H[n37][n37 - 1];
                }
                else {
                    this.cdiv(0.0, -this.H[n37 - 1][n37], this.H[n37 - 1][n37 - 1] - n38, n39);
                    this.H[n37 - 1][n37 - 1] = this.cdivr;
                    this.H[n37 - 1][n37] = this.cdivi;
                }
                this.H[n37][n37 - 1] = 0.0;
                this.H[n37][n37] = 1.0;
                for (int n49 = n37 - 2; n49 >= 0; --n49) {
                    double n50 = 0.0;
                    double n51 = 0.0;
                    for (int n52 = n48; n52 <= n37; ++n52) {
                        n50 += this.H[n49][n52] * this.H[n52][n37 - 1];
                        n51 += this.H[n49][n52] * this.H[n52][n37];
                    }
                    final double n53 = this.H[n49][n49] - n38;
                    if (this.e[n49] < 0.0) {
                        sqrt3 = n53;
                        sqrt = n50;
                        sqrt2 = n51;
                    }
                    else {
                        n48 = n49;
                        if (this.e[n49] == 0.0) {
                            this.cdiv(-n50, -n51, n53, n39);
                            this.H[n49][n37 - 1] = this.cdivr;
                            this.H[n49][n37] = this.cdivi;
                        }
                        else {
                            final double n54 = this.H[n49][n49 + 1];
                            final double n55 = this.H[n49 + 1][n49];
                            double n56 = (this.d[n49] - n38) * (this.d[n49] - n38) + this.e[n49] * this.e[n49] - n39 * n39;
                            final double n57 = (this.d[n49] - n38) * 2.0 * n39;
                            if (n56 == 0.0 & n57 == 0.0) {
                                n56 = pow * n7 * (Math.abs(n53) + Math.abs(n39) + Math.abs(n54) + Math.abs(n55) + Math.abs(sqrt3));
                            }
                            this.cdiv(n54 * sqrt - sqrt3 * n50 + n39 * n51, n54 * sqrt2 - sqrt3 * n51 - n39 * n50, n56, n57);
                            this.H[n49][n37 - 1] = this.cdivr;
                            this.H[n49][n37] = this.cdivi;
                            if (Math.abs(n54) > Math.abs(sqrt3) + Math.abs(n39)) {
                                this.H[n49 + 1][n37 - 1] = (-n50 - n53 * this.H[n49][n37 - 1] + n39 * this.H[n49][n37]) / n54;
                                this.H[n49 + 1][n37] = (-n51 - n53 * this.H[n49][n37] - n39 * this.H[n49][n37 - 1]) / n54;
                            }
                            else {
                                this.cdiv(-sqrt - n55 * this.H[n49][n37 - 1], -sqrt2 - n55 * this.H[n49][n37], sqrt3, n39);
                                this.H[n49 + 1][n37 - 1] = this.cdivr;
                                this.H[n49 + 1][n37] = this.cdivi;
                            }
                        }
                        final double max = Math.max(Math.abs(this.H[n49][n37 - 1]), Math.abs(this.H[n49][n37]));
                        if (pow * max * max > 1.0) {
                            for (int n58 = n49; n58 <= n37; ++n58) {
                                this.H[n58][n37 - 1] /= max;
                                this.H[n58][n37] /= max;
                            }
                        }
                    }
                }
            }
        }
        for (int n59 = 0; n59 < n; ++n59) {
            if (n59 < n2 | n59 > n3) {
                for (int n60 = n59; n60 < n; ++n60) {
                    this.V[n59][n60] = this.H[n59][n60];
                }
            }
        }
        for (int n61 = n - 1; n61 >= n2; --n61) {
            for (int n62 = n2; n62 <= n3; ++n62) {
                double n63 = 0.0;
                for (int n64 = n2; n64 <= Math.min(n61, n3); ++n64) {
                    n63 += this.V[n62][n64] * this.H[n64][n61];
                }
                this.V[n62][n61] = n63;
            }
        }
    }
    
    public EigenvalueDecomposition(final Matrix matrix) {
        final double[][] array = matrix.getArray();
        this.n = matrix.getColumnDimension();
        this.V = new double[this.n][this.n];
        this.d = new double[this.n];
        this.e = new double[this.n];
        this.issymmetric = true;
        for (int n = 0; n < this.n & this.issymmetric; ++n) {
            for (int n2 = 0; n2 < this.n & this.issymmetric; ++n2) {
                this.issymmetric = (array[n2][n] == array[n][n2]);
            }
        }
        if (this.issymmetric) {
            for (int i = 0; i < this.n; ++i) {
                for (int j = 0; j < this.n; ++j) {
                    this.V[i][j] = array[i][j];
                }
            }
            this.tred2();
            this.tql2();
        }
        else {
            this.H = new double[this.n][this.n];
            this.ort = new double[this.n];
            for (int k = 0; k < this.n; ++k) {
                for (int l = 0; l < this.n; ++l) {
                    this.H[l][k] = array[l][k];
                }
            }
            this.orthes();
            this.hqr2();
        }
    }
    
    public Matrix getV() {
        return new Matrix(this.V, this.n, this.n);
    }
    
    public double[] getRealEigenvalues() {
        return this.d;
    }
    
    public double[] getImagEigenvalues() {
        return this.e;
    }
    
    public Matrix getD() {
        final Matrix matrix = new Matrix(this.n, this.n);
        final double[][] array = matrix.getArray();
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                array[i][j] = 0.0;
            }
            array[i][i] = this.d[i];
            if (this.e[i] > 0.0) {
                array[i][i + 1] = this.e[i];
            }
            else if (this.e[i] < 0.0) {
                array[i][i - 1] = this.e[i];
            }
        }
        return matrix;
    }
}
