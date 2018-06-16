// 
// Decompiled by Procyon v0.5.30
// 

package Jama;

import Jama.util.Maths;
import java.io.Serializable;

public class SingularValueDecomposition implements Serializable
{
    private double[][] U;
    private double[][] V;
    private double[] s;
    private int m;
    private int n;
    private static final long serialVersionUID = 1L;
    
    public SingularValueDecomposition(final Matrix matrix) {
        final double[][] arrayCopy = matrix.getArrayCopy();
        this.m = matrix.getRowDimension();
        this.n = matrix.getColumnDimension();
        final int min = Math.min(this.m, this.n);
        this.s = new double[Math.min(this.m + 1, this.n)];
        this.U = new double[this.m][min];
        this.V = new double[this.n][this.n];
        final double[] array = new double[this.n];
        final double[] array2 = new double[this.m];
        final boolean b = true;
        final boolean b2 = true;
        final int min2 = Math.min(this.m - 1, this.n);
        final int max = Math.max(0, Math.min(this.n - 2, this.m));
        for (int i = 0; i < Math.max(min2, max); ++i) {
            if (i < min2) {
                this.s[i] = 0.0;
                for (int j = i; j < this.m; ++j) {
                    this.s[i] = Maths.hypot(this.s[i], arrayCopy[j][i]);
                }
                if (this.s[i] != 0.0) {
                    if (arrayCopy[i][i] < 0.0) {
                        this.s[i] = -this.s[i];
                    }
                    for (int k = i; k < this.m; ++k) {
                        final double[] array3 = arrayCopy[k];
                        final int n = i;
                        array3[n] /= this.s[i];
                    }
                    final double[] array4 = arrayCopy[i];
                    final int n2 = i;
                    ++array4[n2];
                }
                this.s[i] = -this.s[i];
            }
            for (int l = i + 1; l < this.n; ++l) {
                if (i < min2 & this.s[i] != 0.0) {
                    double n3 = 0.0;
                    for (int n4 = i; n4 < this.m; ++n4) {
                        n3 += arrayCopy[n4][i] * arrayCopy[n4][l];
                    }
                    final double n5 = -n3 / arrayCopy[i][i];
                    for (int n6 = i; n6 < this.m; ++n6) {
                        final double[] array5 = arrayCopy[n6];
                        final int n7 = l;
                        array5[n7] += n5 * arrayCopy[n6][i];
                    }
                }
                array[l] = arrayCopy[i][l];
            }
            if (b & i < min2) {
                for (int n8 = i; n8 < this.m; ++n8) {
                    this.U[n8][i] = arrayCopy[n8][i];
                }
            }
            if (i < max) {
                array[i] = 0.0;
                for (int n9 = i + 1; n9 < this.n; ++n9) {
                    array[i] = Maths.hypot(array[i], array[n9]);
                }
                if (array[i] != 0.0) {
                    if (array[i + 1] < 0.0) {
                        array[i] = -array[i];
                    }
                    for (int n10 = i + 1; n10 < this.n; ++n10) {
                        final double[] array6 = array;
                        final int n11 = n10;
                        array6[n11] /= array[i];
                    }
                    final double[] array7 = array;
                    final int n12 = i + 1;
                    ++array7[n12];
                }
                array[i] = -array[i];
                if (i + 1 < this.m & array[i] != 0.0) {
                    for (int n13 = i + 1; n13 < this.m; ++n13) {
                        array2[n13] = 0.0;
                    }
                    for (int n14 = i + 1; n14 < this.n; ++n14) {
                        for (int n15 = i + 1; n15 < this.m; ++n15) {
                            final double[] array8 = array2;
                            final int n16 = n15;
                            array8[n16] += array[n14] * arrayCopy[n15][n14];
                        }
                    }
                    for (int n17 = i + 1; n17 < this.n; ++n17) {
                        final double n18 = -array[n17] / array[i + 1];
                        for (int n19 = i + 1; n19 < this.m; ++n19) {
                            final double[] array9 = arrayCopy[n19];
                            final int n20 = n17;
                            array9[n20] += n18 * array2[n19];
                        }
                    }
                }
                if (b2) {
                    for (int n21 = i + 1; n21 < this.n; ++n21) {
                        this.V[n21][i] = array[n21];
                    }
                }
            }
        }
        int min3 = Math.min(this.n, this.m + 1);
        if (min2 < this.n) {
            this.s[min2] = arrayCopy[min2][min2];
        }
        if (this.m < min3) {
            this.s[min3 - 1] = 0.0;
        }
        if (max + 1 < min3) {
            array[max] = arrayCopy[max][min3 - 1];
        }
        array[min3 - 1] = 0.0;
        if (b) {
            for (int n22 = min2; n22 < min; ++n22) {
                for (int n23 = 0; n23 < this.m; ++n23) {
                    this.U[n23][n22] = 0.0;
                }
                this.U[n22][n22] = 1.0;
            }
            for (int n24 = min2 - 1; n24 >= 0; --n24) {
                if (this.s[n24] != 0.0) {
                    for (int n25 = n24 + 1; n25 < min; ++n25) {
                        double n26 = 0.0;
                        for (int n27 = n24; n27 < this.m; ++n27) {
                            n26 += this.U[n27][n24] * this.U[n27][n25];
                        }
                        final double n28 = -n26 / this.U[n24][n24];
                        for (int n29 = n24; n29 < this.m; ++n29) {
                            final double[] array10 = this.U[n29];
                            final int n30 = n25;
                            array10[n30] += n28 * this.U[n29][n24];
                        }
                    }
                    for (int n31 = n24; n31 < this.m; ++n31) {
                        this.U[n31][n24] = -this.U[n31][n24];
                    }
                    ++this.U[n24][n24];
                    for (int n32 = 0; n32 < n24 - 1; ++n32) {
                        this.U[n32][n24] = 0.0;
                    }
                }
                else {
                    for (int n33 = 0; n33 < this.m; ++n33) {
                        this.U[n33][n24] = 0.0;
                    }
                    this.U[n24][n24] = 1.0;
                }
            }
        }
        if (b2) {
            for (int n34 = this.n - 1; n34 >= 0; --n34) {
                if (n34 < max & array[n34] != 0.0) {
                    for (int n35 = n34 + 1; n35 < min; ++n35) {
                        double n36 = 0.0;
                        for (int n37 = n34 + 1; n37 < this.n; ++n37) {
                            n36 += this.V[n37][n34] * this.V[n37][n35];
                        }
                        final double n38 = -n36 / this.V[n34 + 1][n34];
                        for (int n39 = n34 + 1; n39 < this.n; ++n39) {
                            final double[] array11 = this.V[n39];
                            final int n40 = n35;
                            array11[n40] += n38 * this.V[n39][n34];
                        }
                    }
                }
                for (int n41 = 0; n41 < this.n; ++n41) {
                    this.V[n41][n34] = 0.0;
                }
                this.V[n34][n34] = 1.0;
            }
        }
        final int n42 = min3 - 1;
        int n43 = 0;
        final double pow = Math.pow(2.0, -52.0);
        final double pow2 = Math.pow(2.0, -966.0);
        while (min3 > 0) {
            int n44;
            for (n44 = min3 - 2; n44 >= -1; --n44) {
                if (n44 == -1) {
                    break;
                }
                if (Math.abs(array[n44]) <= pow2 + pow * (Math.abs(this.s[n44]) + Math.abs(this.s[n44 + 1]))) {
                    array[n44] = 0.0;
                    break;
                }
            }
            int n45;
            if (n44 == min3 - 2) {
                n45 = 4;
            }
            else {
                int n46;
                for (n46 = min3 - 1; n46 >= n44; --n46) {
                    if (n46 == n44) {
                        break;
                    }
                    if (Math.abs(this.s[n46]) <= pow2 + pow * (((n46 != min3) ? Math.abs(array[n46]) : 0.0) + ((n46 != n44 + 1) ? Math.abs(array[n46 - 1]) : 0.0))) {
                        this.s[n46] = 0.0;
                        break;
                    }
                }
                if (n46 == n44) {
                    n45 = 3;
                }
                else if (n46 == min3 - 1) {
                    n45 = 1;
                }
                else {
                    n45 = 2;
                    n44 = n46;
                }
            }
            ++n44;
            switch (n45) {
                case 1: {
                    double n47 = array[min3 - 2];
                    array[min3 - 2] = 0.0;
                    for (int n48 = min3 - 2; n48 >= n44; --n48) {
                        final double hypot = Maths.hypot(this.s[n48], n47);
                        final double n49 = this.s[n48] / hypot;
                        final double n50 = n47 / hypot;
                        this.s[n48] = hypot;
                        if (n48 != n44) {
                            n47 = -n50 * array[n48 - 1];
                            array[n48 - 1] *= n49;
                        }
                        if (b2) {
                            for (int n51 = 0; n51 < this.n; ++n51) {
                                final double n52 = n49 * this.V[n51][n48] + n50 * this.V[n51][min3 - 1];
                                this.V[n51][min3 - 1] = -n50 * this.V[n51][n48] + n49 * this.V[n51][min3 - 1];
                                this.V[n51][n48] = n52;
                            }
                        }
                    }
                    continue;
                }
                case 2: {
                    double n53 = array[n44 - 1];
                    array[n44 - 1] = 0.0;
                    for (int n54 = n44; n54 < min3; ++n54) {
                        final double hypot2 = Maths.hypot(this.s[n54], n53);
                        final double n55 = this.s[n54] / hypot2;
                        final double n56 = n53 / hypot2;
                        this.s[n54] = hypot2;
                        n53 = -n56 * array[n54];
                        array[n54] *= n55;
                        if (b) {
                            for (int n57 = 0; n57 < this.m; ++n57) {
                                final double n58 = n55 * this.U[n57][n54] + n56 * this.U[n57][n44 - 1];
                                this.U[n57][n44 - 1] = -n56 * this.U[n57][n54] + n55 * this.U[n57][n44 - 1];
                                this.U[n57][n54] = n58;
                            }
                        }
                    }
                    continue;
                }
                case 3: {
                    final double max2 = Math.max(Math.max(Math.max(Math.max(Math.abs(this.s[min3 - 1]), Math.abs(this.s[min3 - 2])), Math.abs(array[min3 - 2])), Math.abs(this.s[n44])), Math.abs(array[n44]));
                    final double n59 = this.s[min3 - 1] / max2;
                    final double n60 = this.s[min3 - 2] / max2;
                    final double n61 = array[min3 - 2] / max2;
                    final double n62 = this.s[n44] / max2;
                    final double n63 = array[n44] / max2;
                    final double n64 = ((n60 + n59) * (n60 - n59) + n61 * n61) / 2.0;
                    final double n65 = n59 * n61 * (n59 * n61);
                    double n66 = 0.0;
                    if (n64 != 0.0 | n65 != 0.0) {
                        double sqrt = Math.sqrt(n64 * n64 + n65);
                        if (n64 < 0.0) {
                            sqrt = -sqrt;
                        }
                        n66 = n65 / (n64 + sqrt);
                    }
                    double n67 = (n62 + n59) * (n62 - n59) + n66;
                    double n68 = n62 * n63;
                    for (int n69 = n44; n69 < min3 - 1; ++n69) {
                        final double hypot3 = Maths.hypot(n67, n68);
                        final double n70 = n67 / hypot3;
                        final double n71 = n68 / hypot3;
                        if (n69 != n44) {
                            array[n69 - 1] = hypot3;
                        }
                        final double n72 = n70 * this.s[n69] + n71 * array[n69];
                        array[n69] = n70 * array[n69] - n71 * this.s[n69];
                        final double n73 = n71 * this.s[n69 + 1];
                        this.s[n69 + 1] *= n70;
                        if (b2) {
                            for (int n74 = 0; n74 < this.n; ++n74) {
                                final double n75 = n70 * this.V[n74][n69] + n71 * this.V[n74][n69 + 1];
                                this.V[n74][n69 + 1] = -n71 * this.V[n74][n69] + n70 * this.V[n74][n69 + 1];
                                this.V[n74][n69] = n75;
                            }
                        }
                        final double hypot4 = Maths.hypot(n72, n73);
                        final double n76 = n72 / hypot4;
                        final double n77 = n73 / hypot4;
                        this.s[n69] = hypot4;
                        n67 = n76 * array[n69] + n77 * this.s[n69 + 1];
                        this.s[n69 + 1] = -n77 * array[n69] + n76 * this.s[n69 + 1];
                        n68 = n77 * array[n69 + 1];
                        array[n69 + 1] *= n76;
                        if (b && n69 < this.m - 1) {
                            for (int n78 = 0; n78 < this.m; ++n78) {
                                final double n79 = n76 * this.U[n78][n69] + n77 * this.U[n78][n69 + 1];
                                this.U[n78][n69 + 1] = -n77 * this.U[n78][n69] + n76 * this.U[n78][n69 + 1];
                                this.U[n78][n69] = n79;
                            }
                        }
                    }
                    array[min3 - 2] = n67;
                    ++n43;
                    continue;
                }
                case 4: {
                    if (this.s[n44] <= 0.0) {
                        this.s[n44] = ((this.s[n44] < 0.0) ? (-this.s[n44]) : 0.0);
                        if (b2) {
                            for (int n80 = 0; n80 <= n42; ++n80) {
                                this.V[n80][n44] = -this.V[n80][n44];
                            }
                        }
                    }
                    while (n44 < n42 && this.s[n44] < this.s[n44 + 1]) {
                        final double n81 = this.s[n44];
                        this.s[n44] = this.s[n44 + 1];
                        this.s[n44 + 1] = n81;
                        if (b2 && n44 < this.n - 1) {
                            for (int n82 = 0; n82 < this.n; ++n82) {
                                final double n83 = this.V[n82][n44 + 1];
                                this.V[n82][n44 + 1] = this.V[n82][n44];
                                this.V[n82][n44] = n83;
                            }
                        }
                        if (b && n44 < this.m - 1) {
                            for (int n84 = 0; n84 < this.m; ++n84) {
                                final double n85 = this.U[n84][n44 + 1];
                                this.U[n84][n44 + 1] = this.U[n84][n44];
                                this.U[n84][n44] = n85;
                            }
                        }
                        ++n44;
                    }
                    n43 = 0;
                    --min3;
                    continue;
                }
            }
        }
    }
    
    public Matrix getU() {
        return new Matrix(this.U, this.m, Math.min(this.m + 1, this.n));
    }
    
    public Matrix getV() {
        return new Matrix(this.V, this.n, this.n);
    }
    
    public double[] getSingularValues() {
        return this.s;
    }
    
    public Matrix getS() {
        final Matrix matrix = new Matrix(this.n, this.n);
        final double[][] array = matrix.getArray();
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                array[i][j] = 0.0;
            }
            array[i][i] = this.s[i];
        }
        return matrix;
    }
    
    public double norm2() {
        return this.s[0];
    }
    
    public double cond() {
        return this.s[0] / this.s[Math.min(this.m, this.n) - 1];
    }
    
    public int rank() {
        final double n = Math.max(this.m, this.n) * this.s[0] * Math.pow(2.0, -52.0);
        int n2 = 0;
        for (int i = 0; i < this.s.length; ++i) {
            if (this.s[i] > n) {
                ++n2;
            }
        }
        return n2;
    }
}
