// 
// Decompiled by Procyon v0.5.30
// 

package Jama.test;

import Jama.EigenvalueDecomposition;
import Jama.CholeskyDecomposition;
import Jama.LUDecomposition;
import Jama.SingularValueDecomposition;
import Jama.QRDecomposition;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.text.DecimalFormat;
import Jama.Matrix;

public class TestMatrix
{
    public static void main(final String[] array) {
        int n = 0;
        int n2 = 0;
        final double[] array2 = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0 };
        final double[] array3 = { 1.0, 4.0, 7.0, 10.0, 2.0, 5.0, 8.0, 11.0, 3.0, 6.0, 9.0, 12.0 };
        final double[][] array5;
        final double[][] array4 = array5 = new double[][] { { 1.0, 4.0, 7.0, 10.0 }, { 2.0, 5.0, 8.0, 11.0 }, { 3.0, 6.0, 9.0, 12.0 } };
        final double[][] array6 = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 }, { 7.0, 8.0, 9.0 }, { 10.0, 11.0, 12.0 } };
        final double[][] array7 = { { 5.0, 8.0, 11.0 }, { 6.0, 9.0, 12.0 } };
        final double[][] array8 = { { 1.0, 4.0, 7.0 }, { 2.0, 5.0, 8.0, 11.0 }, { 3.0, 6.0, 9.0, 12.0 } };
        final double[][] array9 = { { 4.0, 1.0, 1.0 }, { 1.0, 2.0, 3.0 }, { 1.0, 3.0, 6.0 } };
        final double[][] array10 = { { 1.0, 0.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0, 0.0 }, { 0.0, 0.0, 1.0, 0.0 } };
        final double[][] array11 = { { 0.0, 1.0, 0.0, 0.0 }, { 1.0, 0.0, 2.0E-7, 0.0 }, { 0.0, -2.0E-7, 0.0, 1.0 }, { 0.0, 0.0, 1.0, 0.0 } };
        final double[][] array12 = { { 166.0, 188.0, 210.0 }, { 188.0, 214.0, 240.0 }, { 210.0, 240.0, 270.0 } };
        final double[][] array13 = { { 13.0 }, { 15.0 } };
        final double[][] array14 = { { 1.0, 3.0 }, { 7.0, 9.0 } };
        final double[][] array15 = { { 0.0, 0.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0, 1.0, 0.0 }, { 1.0, 1.0, 0.0, 0.0, 1.0 }, { 1.0, 0.0, 1.0, 0.0, 1.0 } };
        final int n3 = 3;
        final int n4 = 4;
        final int n5 = 5;
        final int n6 = 0;
        final int n7 = 4;
        final int n8 = 3;
        final int n9 = 4;
        final int n10 = 1;
        final int n11 = 2;
        final int n12 = 1;
        final int n13 = 3;
        final int[] array16 = { 1, 2 };
        final int[] array17 = { 1, 3 };
        final int[] array18 = { 1, 2, 3 };
        final int[] array19 = { 1, 2, 4 };
        final double n14 = 33.0;
        final double n15 = 30.0;
        final double n16 = 15.0;
        final double n17 = 650.0;
        print("\nTesting constructors and constructor-like methods...\n");
        try {
            final Matrix matrix = new Matrix(array2, n5);
            n = try_failure(n, "Catch invalid length in packed constructor... ", "exception not thrown for invalid input");
        }
        catch (IllegalArgumentException ex) {
            try_success("Catch invalid length in packed constructor... ", ex.getMessage());
        }
        try {
            new Matrix(array8).get(n6, n7);
        }
        catch (IllegalArgumentException ex2) {
            try_success("Catch ragged input to default constructor... ", ex2.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException ex7) {
            n = try_failure(n, "Catch ragged input to constructor... ", "exception not thrown in construction...ArrayIndexOutOfBoundsException thrown later");
        }
        try {
            Matrix.constructWithCopy(array8).get(n6, n7);
        }
        catch (IllegalArgumentException ex3) {
            try_success("Catch ragged input to constructWithCopy... ", ex3.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException ex8) {
            n = try_failure(n, "Catch ragged input to constructWithCopy... ", "exception not thrown in construction...ArrayIndexOutOfBoundsException thrown later");
        }
        final Matrix matrix2 = new Matrix(array2, n8);
        final Matrix matrix3 = new Matrix(array4);
        final double value = matrix3.get(0, 0);
        array4[0][0] = 0.0;
        matrix3.minus(matrix2);
        array4[0][0] = value;
        final Matrix constructWithCopy = Matrix.constructWithCopy(array4);
        final double value2 = constructWithCopy.get(0, 0);
        array4[0][0] = 0.0;
        if (value2 - constructWithCopy.get(0, 0) != 0.0) {
            n = try_failure(n, "constructWithCopy... ", "copy not effected... data visible outside");
        }
        else {
            try_success("constructWithCopy... ", "");
        }
        array4[0][0] = array2[0];
        final Matrix matrix4 = new Matrix(array10);
        try {
            check(matrix4, Matrix.identity(3, 4));
            try_success("identity... ", "");
        }
        catch (RuntimeException ex9) {
            n = try_failure(n, "identity... ", "identity Matrix not successfully created");
        }
        print("\nTesting access methods...\n");
        final Matrix matrix5 = new Matrix(array4);
        if (matrix5.getRowDimension() != n3) {
            n = try_failure(n, "getRowDimension... ", "");
        }
        else {
            try_success("getRowDimension... ", "");
        }
        if (matrix5.getColumnDimension() != n4) {
            n = try_failure(n, "getColumnDimension... ", "");
        }
        else {
            try_success("getColumnDimension... ", "");
        }
        final Matrix matrix6 = new Matrix(array4);
        if (matrix6.getArray() != array4) {
            n = try_failure(n, "getArray... ", "");
        }
        else {
            try_success("getArray... ", "");
        }
        final double[][] arrayCopy = matrix6.getArrayCopy();
        if (arrayCopy == array4) {
            n = try_failure(n, "getArrayCopy... ", "data not (deep) copied");
        }
        try {
            check(arrayCopy, array4);
            try_success("getArrayCopy... ", "");
        }
        catch (RuntimeException ex10) {
            n = try_failure(n, "getArrayCopy... ", "data not successfully (deep) copied");
        }
        final double[] columnPackedCopy = matrix6.getColumnPackedCopy();
        try {
            check(columnPackedCopy, array2);
            try_success("getColumnPackedCopy... ", "");
        }
        catch (RuntimeException ex11) {
            n = try_failure(n, "getColumnPackedCopy... ", "data not successfully (deep) copied by columns");
        }
        final double[] rowPackedCopy = matrix6.getRowPackedCopy();
        try {
            check(rowPackedCopy, array3);
            try_success("getRowPackedCopy... ", "");
        }
        catch (RuntimeException ex12) {
            n = try_failure(n, "getRowPackedCopy... ", "data not successfully (deep) copied by rows");
        }
        try {
            matrix6.get(matrix6.getRowDimension(), matrix6.getColumnDimension() - 1);
            n = try_failure(n, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex13) {
            try {
                matrix6.get(matrix6.getRowDimension() - 1, matrix6.getColumnDimension());
                n = try_failure(n, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex14) {
                try_success("get(int,int)... OutofBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex15) {
            n = try_failure(n, "get(int,int)... ", "OutOfBoundsException expected but not thrown");
        }
        try {
            if (matrix6.get(matrix6.getRowDimension() - 1, matrix6.getColumnDimension() - 1) != array4[matrix6.getRowDimension() - 1][matrix6.getColumnDimension() - 1]) {
                n = try_failure(n, "get(int,int)... ", "Matrix entry (i,j) not successfully retreived");
            }
            else {
                try_success("get(int,int)... ", "");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex16) {
            n = try_failure(n, "get(int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        final Matrix matrix7 = new Matrix(array7);
        try {
            matrix6.getMatrix(n10, n11 + matrix6.getRowDimension() + 1, n12, n13);
            n = try_failure(n, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex17) {
            try {
                matrix6.getMatrix(n10, n11, n12, n13 + matrix6.getColumnDimension() + 1);
                n = try_failure(n, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex18) {
                try_success("getMatrix(int,int,int,int)... ArrayIndexOutOfBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex19) {
            n = try_failure(n, "getMatrix(int,int,int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        try {
            final Matrix matrix8 = matrix6.getMatrix(n10, n11, n12, n13);
            try {
                check(matrix7, matrix8);
                try_success("getMatrix(int,int,int,int)... ", "");
            }
            catch (RuntimeException ex20) {
                n = try_failure(n, "getMatrix(int,int,int,int)... ", "submatrix not successfully retreived");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex21) {
            n = try_failure(n, "getMatrix(int,int,int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        try {
            matrix6.getMatrix(n10, n11, array19);
            n = try_failure(n, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex22) {
            try {
                matrix6.getMatrix(n10, n11 + matrix6.getRowDimension() + 1, array18);
                n = try_failure(n, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex23) {
                try_success("getMatrix(int,int,int[])... ArrayIndexOutOfBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex24) {
            n = try_failure(n, "getMatrix(int,int,int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        try {
            final Matrix matrix9 = matrix6.getMatrix(n10, n11, array18);
            try {
                check(matrix7, matrix9);
                try_success("getMatrix(int,int,int[])... ", "");
            }
            catch (RuntimeException ex25) {
                n = try_failure(n, "getMatrix(int,int,int[])... ", "submatrix not successfully retreived");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex26) {
            n = try_failure(n, "getMatrix(int,int,int[])... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        try {
            matrix6.getMatrix(array17, n12, n13);
            n = try_failure(n, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex27) {
            try {
                matrix6.getMatrix(array16, n12, n13 + matrix6.getColumnDimension() + 1);
                n = try_failure(n, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex28) {
                try_success("getMatrix(int[],int,int)... ArrayIndexOutOfBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex29) {
            n = try_failure(n, "getMatrix(int[],int,int)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        try {
            final Matrix matrix10 = matrix6.getMatrix(array16, n12, n13);
            try {
                check(matrix7, matrix10);
                try_success("getMatrix(int[],int,int)... ", "");
            }
            catch (RuntimeException ex30) {
                n = try_failure(n, "getMatrix(int[],int,int)... ", "submatrix not successfully retreived");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex31) {
            n = try_failure(n, "getMatrix(int[],int,int)... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        try {
            matrix6.getMatrix(array17, array18);
            n = try_failure(n, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex32) {
            try {
                matrix6.getMatrix(array16, array19);
                n = try_failure(n, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex33) {
                try_success("getMatrix(int[],int[])... ArrayIndexOutOfBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex34) {
            n = try_failure(n, "getMatrix(int[],int[])... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        try {
            final Matrix matrix11 = matrix6.getMatrix(array16, array18);
            try {
                check(matrix7, matrix11);
                try_success("getMatrix(int[],int[])... ", "");
            }
            catch (RuntimeException ex35) {
                n = try_failure(n, "getMatrix(int[],int[])... ", "submatrix not successfully retreived");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex36) {
            n = try_failure(n, "getMatrix(int[],int[])... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        try {
            matrix6.set(matrix6.getRowDimension(), matrix6.getColumnDimension() - 1, 0.0);
            n = try_failure(n, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex37) {
            try {
                matrix6.set(matrix6.getRowDimension() - 1, matrix6.getColumnDimension(), 0.0);
                n = try_failure(n, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex38) {
                try_success("set(int,int,double)... OutofBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex39) {
            n = try_failure(n, "set(int,int,double)... ", "OutOfBoundsException expected but not thrown");
        }
        try {
            matrix6.set(n10, n12, 0.0);
            final double value3 = matrix6.get(n10, n12);
            try {
                check(value3, 0.0);
                try_success("set(int,int,double)... ", "");
            }
            catch (RuntimeException ex40) {
                n = try_failure(n, "set(int,int,double)... ", "Matrix element not successfully set");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex41) {
            n = try_failure(n, "set(int,int,double)... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        final Matrix matrix12 = new Matrix(2, 3, 0.0);
        try {
            matrix6.setMatrix(n10, n11 + matrix6.getRowDimension() + 1, n12, n13, matrix12);
            n = try_failure(n, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex42) {
            try {
                matrix6.setMatrix(n10, n11, n12, n13 + matrix6.getColumnDimension() + 1, matrix12);
                n = try_failure(n, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex43) {
                try_success("setMatrix(int,int,int,int,Matrix)... ArrayIndexOutOfBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex44) {
            n = try_failure(n, "setMatrix(int,int,int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        try {
            matrix6.setMatrix(n10, n11, n12, n13, matrix12);
            try {
                check(matrix12.minus(matrix6.getMatrix(n10, n11, n12, n13)), matrix12);
                try_success("setMatrix(int,int,int,int,Matrix)... ", "");
            }
            catch (RuntimeException ex45) {
                n = try_failure(n, "setMatrix(int,int,int,int,Matrix)... ", "submatrix not successfully set");
            }
            matrix6.setMatrix(n10, n11, n12, n13, matrix7);
        }
        catch (ArrayIndexOutOfBoundsException ex46) {
            n = try_failure(n, "setMatrix(int,int,int,int,Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        try {
            matrix6.setMatrix(n10, n11 + matrix6.getRowDimension() + 1, array18, matrix12);
            n = try_failure(n, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex47) {
            try {
                matrix6.setMatrix(n10, n11, array19, matrix12);
                n = try_failure(n, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex48) {
                try_success("setMatrix(int,int,int[],Matrix)... ArrayIndexOutOfBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex49) {
            n = try_failure(n, "setMatrix(int,int,int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        try {
            matrix6.setMatrix(n10, n11, array18, matrix12);
            try {
                check(matrix12.minus(matrix6.getMatrix(n10, n11, array18)), matrix12);
                try_success("setMatrix(int,int,int[],Matrix)... ", "");
            }
            catch (RuntimeException ex50) {
                n = try_failure(n, "setMatrix(int,int,int[],Matrix)... ", "submatrix not successfully set");
            }
            matrix6.setMatrix(n10, n11, n12, n13, matrix7);
        }
        catch (ArrayIndexOutOfBoundsException ex51) {
            n = try_failure(n, "setMatrix(int,int,int[],Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        try {
            matrix6.setMatrix(array16, n12, n13 + matrix6.getColumnDimension() + 1, matrix12);
            n = try_failure(n, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex52) {
            try {
                matrix6.setMatrix(array17, n12, n13, matrix12);
                n = try_failure(n, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex53) {
                try_success("setMatrix(int[],int,int,Matrix)... ArrayIndexOutOfBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex54) {
            n = try_failure(n, "setMatrix(int[],int,int,Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        try {
            matrix6.setMatrix(array16, n12, n13, matrix12);
            try {
                check(matrix12.minus(matrix6.getMatrix(array16, n12, n13)), matrix12);
                try_success("setMatrix(int[],int,int,Matrix)... ", "");
            }
            catch (RuntimeException ex55) {
                n = try_failure(n, "setMatrix(int[],int,int,Matrix)... ", "submatrix not successfully set");
            }
            matrix6.setMatrix(n10, n11, n12, n13, matrix7);
        }
        catch (ArrayIndexOutOfBoundsException ex56) {
            n = try_failure(n, "setMatrix(int[],int,int,Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        try {
            matrix6.setMatrix(array16, array19, matrix12);
            n = try_failure(n, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        catch (ArrayIndexOutOfBoundsException ex57) {
            try {
                matrix6.setMatrix(array17, array18, matrix12);
                n = try_failure(n, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
            }
            catch (ArrayIndexOutOfBoundsException ex58) {
                try_success("setMatrix(int[],int[],Matrix)... ArrayIndexOutOfBoundsException... ", "");
            }
        }
        catch (IllegalArgumentException ex59) {
            n = try_failure(n, "setMatrix(int[],int[],Matrix)... ", "ArrayIndexOutOfBoundsException expected but not thrown");
        }
        try {
            matrix6.setMatrix(array16, array18, matrix12);
            try {
                check(matrix12.minus(matrix6.getMatrix(array16, array18)), matrix12);
                try_success("setMatrix(int[],int[],Matrix)... ", "");
            }
            catch (RuntimeException ex60) {
                n = try_failure(n, "setMatrix(int[],int[],Matrix)... ", "submatrix not successfully set");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex61) {
            n = try_failure(n, "setMatrix(int[],int[],Matrix)... ", "Unexpected ArrayIndexOutOfBoundsException");
        }
        print("\nTesting array-like methods...\n");
        Matrix matrix13 = new Matrix(array2, n9);
        final Matrix random;
        final Matrix matrix14 = random = Matrix.random(matrix2.getRowDimension(), matrix2.getColumnDimension());
        try {
            matrix13 = random.minus(matrix13);
            n = try_failure(n, "minus conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex62) {
            try_success("minus conformance check... ", "");
        }
        if (random.minus(matrix14).norm1() != 0.0) {
            n = try_failure(n, "minus... ", "(difference of identical Matrices is nonzero,\nSubsequent use of minus should be suspect)");
        }
        else {
            try_success("minus... ", "");
        }
        final Matrix copy = matrix14.copy();
        copy.minusEquals(matrix14);
        final Matrix matrix15 = new Matrix(copy.getRowDimension(), copy.getColumnDimension());
        try {
            copy.minusEquals(matrix13);
            n = try_failure(n, "minusEquals conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex63) {
            try_success("minusEquals conformance check... ", "");
        }
        if (copy.minus(matrix15).norm1() != 0.0) {
            n = try_failure(n, "minusEquals... ", "(difference of identical Matrices is nonzero,\nSubsequent use of minus should be suspect)");
        }
        else {
            try_success("minusEquals... ", "");
        }
        final Matrix copy2 = matrix14.copy();
        final Matrix random2 = Matrix.random(copy2.getRowDimension(), copy2.getColumnDimension());
        final Matrix minus = copy2.minus(random2);
        try {
            matrix13 = copy2.plus(matrix13);
            n = try_failure(n, "plus conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex64) {
            try_success("plus conformance check... ", "");
        }
        try {
            check(minus.plus(random2), copy2);
            try_success("plus... ", "");
        }
        catch (RuntimeException ex65) {
            n = try_failure(n, "plus... ", "(C = A - B, but C + B != A)");
        }
        final Matrix minus2 = copy2.minus(random2);
        minus2.plusEquals(random2);
        try {
            copy2.plusEquals(matrix13);
            n = try_failure(n, "plusEquals conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex66) {
            try_success("plusEquals conformance check... ", "");
        }
        try {
            check(minus2, copy2);
            try_success("plusEquals... ", "");
        }
        catch (RuntimeException ex67) {
            n = try_failure(n, "plusEquals... ", "(C = A - B, but C = C + B != A)");
        }
        final Matrix uminus = matrix14.uminus();
        try {
            check(uminus.plus(matrix14), matrix15);
            try_success("uminus... ", "");
        }
        catch (RuntimeException ex68) {
            n = try_failure(n, "uminus... ", "(-A + A != zeros)");
        }
        final Matrix copy3 = matrix14.copy();
        final Matrix matrix16 = new Matrix(copy3.getRowDimension(), copy3.getColumnDimension(), 1.0);
        final Matrix arrayLeftDivide = copy3.arrayLeftDivide(matrix14);
        try {
            matrix13 = copy3.arrayLeftDivide(matrix13);
            n = try_failure(n, "arrayLeftDivide conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex69) {
            try_success("arrayLeftDivide conformance check... ", "");
        }
        try {
            check(arrayLeftDivide, matrix16);
            try_success("arrayLeftDivide... ", "");
        }
        catch (RuntimeException ex70) {
            n = try_failure(n, "arrayLeftDivide... ", "(M.\\M != ones)");
        }
        try {
            copy3.arrayLeftDivideEquals(matrix13);
            n = try_failure(n, "arrayLeftDivideEquals conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex71) {
            try_success("arrayLeftDivideEquals conformance check... ", "");
        }
        copy3.arrayLeftDivideEquals(matrix14);
        try {
            check(copy3, matrix16);
            try_success("arrayLeftDivideEquals... ", "");
        }
        catch (RuntimeException ex72) {
            n = try_failure(n, "arrayLeftDivideEquals... ", "(M.\\M != ones)");
        }
        final Matrix copy4 = matrix14.copy();
        try {
            copy4.arrayRightDivide(matrix13);
            n = try_failure(n, "arrayRightDivide conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex73) {
            try_success("arrayRightDivide conformance check... ", "");
        }
        final Matrix arrayRightDivide = copy4.arrayRightDivide(matrix14);
        try {
            check(arrayRightDivide, matrix16);
            try_success("arrayRightDivide... ", "");
        }
        catch (RuntimeException ex74) {
            n = try_failure(n, "arrayRightDivide... ", "(M./M != ones)");
        }
        try {
            copy4.arrayRightDivideEquals(matrix13);
            n = try_failure(n, "arrayRightDivideEquals conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex75) {
            try_success("arrayRightDivideEquals conformance check... ", "");
        }
        copy4.arrayRightDivideEquals(matrix14);
        try {
            check(copy4, matrix16);
            try_success("arrayRightDivideEquals... ", "");
        }
        catch (RuntimeException ex76) {
            n = try_failure(n, "arrayRightDivideEquals... ", "(M./M != ones)");
        }
        final Matrix copy5 = matrix14.copy();
        final Matrix random3 = Matrix.random(copy5.getRowDimension(), copy5.getColumnDimension());
        try {
            matrix13 = copy5.arrayTimes(matrix13);
            n = try_failure(n, "arrayTimes conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex77) {
            try_success("arrayTimes conformance check... ", "");
        }
        final Matrix arrayTimes = copy5.arrayTimes(random3);
        try {
            check(arrayTimes.arrayRightDivideEquals(random3), copy5);
            try_success("arrayTimes... ", "");
        }
        catch (RuntimeException ex78) {
            n = try_failure(n, "arrayTimes... ", "(A = R, C = A.*B, but C./B != A)");
        }
        try {
            copy5.arrayTimesEquals(matrix13);
            n = try_failure(n, "arrayTimesEquals conformance check... ", "nonconformance not raised");
        }
        catch (IllegalArgumentException ex79) {
            try_success("arrayTimesEquals conformance check... ", "");
        }
        copy5.arrayTimesEquals(random3);
        try {
            check(copy5.arrayRightDivideEquals(random3), matrix14);
            try_success("arrayTimesEquals... ", "");
        }
        catch (RuntimeException ex80) {
            n = try_failure(n, "arrayTimesEquals... ", "(A = R, A = A.*B, but A./B != R)");
        }
        print("\nTesting I/O methods...\n");
        try {
            final DecimalFormat decimalFormat = new DecimalFormat("0.0000E00");
            decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            final PrintWriter printWriter = new PrintWriter(new FileOutputStream("JamaTestMatrix.out"));
            copy5.print(printWriter, decimalFormat, 10);
            printWriter.close();
            if (copy5.minus(Matrix.read(new BufferedReader(new FileReader("JamaTestMatrix.out")))).norm1() < 0.001) {
                try_success("print()/read()...", "");
            }
            else {
                n = try_failure(n, "print()/read()...", "Matrix read from file does not match Matrix printed to file");
            }
        }
        catch (IOException ex81) {
            n2 = try_warning(n2, "print()/read()...", "unexpected I/O error, unable to run print/read test;  check write permission in current directory and retry");
        }
        catch (Exception ex4) {
            try {
                ex4.printStackTrace(System.out);
                n2 = try_warning(n2, "print()/read()...", "Formatting error... will try JDK1.1 reformulation...");
                final DecimalFormat decimalFormat2 = new DecimalFormat("0.0000");
                final PrintWriter printWriter2 = new PrintWriter(new FileOutputStream("JamaTestMatrix.out"));
                copy5.print(printWriter2, decimalFormat2, 10);
                printWriter2.close();
                if (copy5.minus(Matrix.read(new BufferedReader(new FileReader("JamaTestMatrix.out")))).norm1() < 0.001) {
                    try_success("print()/read()...", "");
                }
                else {
                    n = try_failure(n, "print()/read() (2nd attempt) ...", "Matrix read from file does not match Matrix printed to file");
                }
            }
            catch (IOException ex82) {
                n2 = try_warning(n2, "print()/read()...", "unexpected I/O error, unable to run print/read test;  check write permission in current directory and retry");
            }
        }
        final Matrix random4 = Matrix.random(copy5.getRowDimension(), copy5.getColumnDimension());
        final String s = "TMPMATRIX.serial";
        try {
            new ObjectOutputStream(new FileOutputStream(s)).writeObject(random4);
            final Matrix matrix17 = (Matrix)new ObjectInputStream(new FileInputStream(s)).readObject();
            try {
                check(matrix17, random4);
                try_success("writeObject(Matrix)/readObject(Matrix)...", "");
            }
            catch (RuntimeException ex83) {
                n = try_failure(n, "writeObject(Matrix)/readObject(Matrix)...", "Matrix not serialized correctly");
            }
        }
        catch (IOException ex84) {
            n2 = try_warning(n2, "writeObject()/readObject()...", "unexpected I/O error, unable to run serialization test;  check write permission in current directory and retry");
        }
        catch (Exception ex85) {
            n = try_failure(n, "writeObject(Matrix)/readObject(Matrix)...", "unexpected error in serialization test");
        }
        print("\nTesting linear algebra methods...\n");
        final Matrix matrix18 = new Matrix(array2, 3);
        final Matrix matrix19 = new Matrix(array6);
        final Matrix transpose = matrix18.transpose();
        try {
            check(matrix18.transpose(), transpose);
            try_success("transpose...", "");
        }
        catch (RuntimeException ex86) {
            n = try_failure(n, "transpose()...", "transpose unsuccessful");
        }
        matrix18.transpose();
        try {
            check(matrix18.norm1(), n14);
            try_success("norm1...", "");
        }
        catch (RuntimeException ex87) {
            n = try_failure(n, "norm1()...", "incorrect norm calculation");
        }
        try {
            check(matrix18.normInf(), n15);
            try_success("normInf()...", "");
        }
        catch (RuntimeException ex88) {
            n = try_failure(n, "normInf()...", "incorrect norm calculation");
        }
        try {
            check(matrix18.normF(), Math.sqrt(n17));
            try_success("normF...", "");
        }
        catch (RuntimeException ex89) {
            n = try_failure(n, "normF()...", "incorrect norm calculation");
        }
        try {
            check(matrix18.trace(), n16);
            try_success("trace()...", "");
        }
        catch (RuntimeException ex90) {
            n = try_failure(n, "trace()...", "incorrect trace calculation");
        }
        try {
            check(matrix18.getMatrix(0, matrix18.getRowDimension() - 1, 0, matrix18.getRowDimension() - 1).det(), 0.0);
            try_success("det()...", "");
        }
        catch (RuntimeException ex91) {
            n = try_failure(n, "det()...", "incorrect determinant calculation");
        }
        final Matrix matrix20 = new Matrix(array12);
        try {
            check(matrix18.times(matrix18.transpose()), matrix20);
            try_success("times(Matrix)...", "");
        }
        catch (RuntimeException ex92) {
            n = try_failure(n, "times(Matrix)...", "incorrect Matrix-Matrix product calculation");
        }
        try {
            check(matrix18.times(0.0), matrix15);
            try_success("times(double)...", "");
        }
        catch (RuntimeException ex93) {
            n = try_failure(n, "times(double)...", "incorrect Matrix-scalar product calculation");
        }
        final Matrix matrix21 = new Matrix(array2, 4);
        final QRDecomposition qr = matrix21.qr();
        final Matrix r = qr.getR();
        try {
            check(matrix21, qr.getQ().times(r));
            try_success("QRDecomposition...", "");
        }
        catch (RuntimeException ex94) {
            n = try_failure(n, "QRDecomposition...", "incorrect QR decomposition calculation");
        }
        final SingularValueDecomposition svd = matrix21.svd();
        try {
            check(matrix21, svd.getU().times(svd.getS().times(svd.getV().transpose())));
            try_success("SingularValueDecomposition...", "");
        }
        catch (RuntimeException ex95) {
            n = try_failure(n, "SingularValueDecomposition...", "incorrect singular value decomposition calculation");
        }
        final Matrix matrix22 = new Matrix(array5);
        try {
            check(matrix22.rank(), Math.min(matrix22.getRowDimension(), matrix22.getColumnDimension()) - 1);
            try_success("rank()...", "");
        }
        catch (RuntimeException ex96) {
            n = try_failure(n, "rank()...", "incorrect rank calculation");
        }
        final Matrix matrix23 = new Matrix(array14);
        final double[] singularValues = matrix23.svd().getSingularValues();
        try {
            check(matrix23.cond(), singularValues[0] / singularValues[Math.min(matrix23.getRowDimension(), matrix23.getColumnDimension()) - 1]);
            try_success("cond()...", "");
        }
        catch (RuntimeException ex97) {
            n = try_failure(n, "cond()...", "incorrect condition number calculation");
        }
        final int columnDimension = matrix21.getColumnDimension();
        final Matrix matrix24 = matrix21.getMatrix(0, columnDimension - 1, 0, columnDimension - 1);
        matrix24.set(0, 0, 0.0);
        final LUDecomposition lu = matrix24.lu();
        try {
            check(matrix24.getMatrix(lu.getPivot(), 0, columnDimension - 1), lu.getL().times(lu.getU()));
            try_success("LUDecomposition...", "");
        }
        catch (RuntimeException ex98) {
            n = try_failure(n, "LUDecomposition...", "incorrect LU decomposition calculation");
        }
        final Matrix inverse = matrix24.inverse();
        try {
            check(matrix24.times(inverse), Matrix.identity(3, 3));
            try_success("inverse()...", "");
        }
        catch (RuntimeException ex99) {
            n = try_failure(n, "inverse()...", "incorrect inverse calculation");
        }
        final Matrix matrix25 = new Matrix(matrix7.getRowDimension(), 1, 1.0);
        final Matrix matrix26 = new Matrix(array13);
        final Matrix matrix27 = matrix7.getMatrix(0, matrix7.getRowDimension() - 1, 0, matrix7.getRowDimension() - 1);
        try {
            check(matrix27.solve(matrix26), matrix25);
            try_success("solve()...", "");
        }
        catch (IllegalArgumentException ex5) {
            n = try_failure(n, "solve()...", ex5.getMessage());
        }
        catch (RuntimeException ex6) {
            n = try_failure(n, "solve()...", ex6.getMessage());
        }
        final Matrix matrix28 = new Matrix(array9);
        final CholeskyDecomposition chol = matrix28.chol();
        final Matrix l = chol.getL();
        try {
            check(matrix28, l.times(l.transpose()));
            try_success("CholeskyDecomposition...", "");
        }
        catch (RuntimeException ex100) {
            n = try_failure(n, "CholeskyDecomposition...", "incorrect Cholesky decomposition calculation");
        }
        final Matrix solve = chol.solve(Matrix.identity(3, 3));
        try {
            check(matrix28.times(solve), Matrix.identity(3, 3));
            try_success("CholeskyDecomposition solve()...", "");
        }
        catch (RuntimeException ex101) {
            n = try_failure(n, "CholeskyDecomposition solve()...", "incorrect Choleskydecomposition solve calculation");
        }
        final EigenvalueDecomposition eig = matrix28.eig();
        final Matrix d = eig.getD();
        final Matrix v = eig.getV();
        try {
            check(matrix28.times(v), v.times(d));
            try_success("EigenvalueDecomposition (symmetric)...", "");
        }
        catch (RuntimeException ex102) {
            n = try_failure(n, "EigenvalueDecomposition (symmetric)...", "incorrect symmetric Eigenvalue decomposition calculation");
        }
        final Matrix matrix29 = new Matrix(array11);
        final EigenvalueDecomposition eig2 = matrix29.eig();
        final Matrix d2 = eig2.getD();
        final Matrix v2 = eig2.getV();
        try {
            check(matrix29.times(v2), v2.times(d2));
            try_success("EigenvalueDecomposition (nonsymmetric)...", "");
        }
        catch (RuntimeException ex103) {
            n = try_failure(n, "EigenvalueDecomposition (nonsymmetric)...", "incorrect nonsymmetric Eigenvalue decomposition calculation");
        }
        try {
            print("\nTesting Eigenvalue; If this hangs, we've failed\n");
            new Matrix(array15).eig();
            try_success("EigenvalueDecomposition (hang)...", "");
        }
        catch (RuntimeException ex104) {
            n = try_failure(n, "EigenvalueDecomposition (hang)...", "incorrect termination");
        }
        print("\nTestMatrix completed.\n");
        print("Total errors reported: " + Integer.toString(n) + "\n");
        print("Total warnings reported: " + Integer.toString(n2) + "\n");
    }
    
    private static void check(final double n, final double n2) {
        final double pow = Math.pow(2.0, -52.0);
        if (n == 0.0 & Math.abs(n2) < 10.0 * pow) {
            return;
        }
        if (n2 == 0.0 & Math.abs(n) < 10.0 * pow) {
            return;
        }
        if (Math.abs(n - n2) > 10.0 * pow * Math.max(Math.abs(n), Math.abs(n2))) {
            throw new RuntimeException("The difference x-y is too large: x = " + Double.toString(n) + "  y = " + Double.toString(n2));
        }
    }
    
    private static void check(final double[] array, final double[] array2) {
        if (array.length == array2.length) {
            for (int i = 0; i < array.length; ++i) {
                check(array[i], array2[i]);
            }
            return;
        }
        throw new RuntimeException("Attempt to compare vectors of different lengths");
    }
    
    private static void check(final double[][] array, final double[][] array2) {
        check(new Matrix(array), new Matrix(array2));
    }
    
    private static void check(final Matrix matrix, final Matrix matrix2) {
        final double pow = Math.pow(2.0, -52.0);
        if (matrix.norm1() == 0.0 & matrix2.norm1() < 10.0 * pow) {
            return;
        }
        if (matrix2.norm1() == 0.0 & matrix.norm1() < 10.0 * pow) {
            return;
        }
        if (matrix.minus(matrix2).norm1() > 1000.0 * pow * Math.max(matrix.norm1(), matrix2.norm1())) {
            throw new RuntimeException("The norm of (X-Y) is too large: " + Double.toString(matrix.minus(matrix2).norm1()));
        }
    }
    
    private static void print(final String s) {
        System.out.print(s);
    }
    
    private static void try_success(final String s, final String s2) {
        print(">    " + s + "success\n");
        if (s2 != "") {
            print(">      Message: " + s2 + "\n");
        }
    }
    
    private static int try_failure(int n, final String s, final String s2) {
        print(">    " + s + "*** failure ***\n>      Message: " + s2 + "\n");
        return ++n;
    }
    
    private static int try_warning(int n, final String s, final String s2) {
        print(">    " + s + "*** warning ***\n>      Message: " + s2 + "\n");
        return ++n;
    }
    
    private static void print(final double[] array, final int n, final int n2) {
        System.out.print("\n");
        new Matrix(array, 1).print(n, n2);
        print("\n");
    }
}
