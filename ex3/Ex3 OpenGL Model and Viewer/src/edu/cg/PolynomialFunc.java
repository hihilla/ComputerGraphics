package edu.cg;

public class PolynomialFunc {
    double a, b, c, d;

    PolynomialFunc() {
        a = b = c = d = 0;

    }

    public PolynomialFunc(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double calc(double t) {
        return ((a * Math.pow(t, 3)) + (b * Math.pow(t, 2)) + (c * t) + d);
    }

    public double firstNigzeret(double t) {
        return (3 * a * Math.pow(t, 2)) + (2 * b * t) + c;
    }

    public double secondNigzeret(double t) {
        return (6 * a * t) + (2 * b);
    }
}
