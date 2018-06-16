package edu.cg;

import Jama.Matrix;
import edu.cg.algebra.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mesila {
    Spline spline;
    double length;

    public double getLength() {
        return length;
    }

    public Mesila() {
        spline = null;
        length = 0;
    }

    public Mesila(Spline spline) {
        this.spline = spline;
        this.length = spline.calcLength();
    }

    public LocationOnMesila locationOnMesila(double t) {
        return new LocationOnMesila(spline.point(t), spline.tangent(t), spline.normal(t));
    }


    public static CyclicList<Mesila> getMesilot(CyclicList<Point> points) {
        CyclicList<PolynomialFunc> xPolynomials = polinomialFuncs(points, 1);
        CyclicList<PolynomialFunc> yPolynomials = polinomialFuncs(points, 2);
        CyclicList<PolynomialFunc> zPolynomials = polinomialFuncs(points, 3);

        CyclicList<Mesila> mesilot = new CyclicList<>();
        for (int i = 0; i < points.size(); i++) {
            Spline s = new Spline(xPolynomials.get(i), yPolynomials.get(i), zPolynomials.get(i));
            Mesila m = new Mesila(s);
            mesilot.add(m);
        }

        return mesilot;
    }

    // component of point is x (1), y (2), z(3)
    public static CyclicList<PolynomialFunc> polinomialFuncs(CyclicList<Point> points, int component) {
        int size = points.size();
        List<Constraint> constraints = new ArrayList<>(4 * size);

        for (int i = 0; i < size; i++) {
            Point point = points.get(i);
            double p = 0;
            switch (component) {
                case 1: p = point.x;
                case 2: p = point.y;
                case 3: p = point.z;
            }

            constraints.addAll(Constraint.getConstraints(p,i,size));
        }

        double[] polynomialFuncs = solveEquation(constraints);
        CyclicList<PolynomialFunc> cyclicPolynomials = new CyclicList<PolynomialFunc>();
        for (int i = 0; i < polynomialFuncs.length;) {
            PolynomialFunc p = new PolynomialFunc(polynomialFuncs[i++],
                    polynomialFuncs[i++],
                    polynomialFuncs[i++],
                    polynomialFuncs[i++]);
            cyclicPolynomials.add(p);
        }

        return cyclicPolynomials;
    }


    // solving equation A*x=b
    private static double[] solveEquation(List<Constraint> constraints) {
        int size = constraints.size();
        double[][] A = new double[size][0];
        double[] b = new double[size];

        for (int i = 0; i < size; ++i) {
            Constraint constraint = constraints.get(i);
            A[i] = constraint.Ai();
            b[i] = constraint.b();
        }

        Matrix matrixA = new Matrix(A);
        Matrix vectorB = new Matrix(b, size);
        Matrix sol = matrixA.solve(vectorB);

        double[] ans = new double[size];
        for (int j = 0; j < size; ++j) {
            ans[j] = (float) sol.get(j, 0);
        }

        return ans;
    }
}