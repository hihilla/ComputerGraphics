package edu.cg;

import java.util.Arrays;
import java.util.List;

public class Constraint {
    private double a1, b1, c1, d1, a2, b2, c2, d2, B;
    private int i, length;

    public Constraint(double a1, double b1, double c1, double d1,
                      double a2, double b2, double c2, double d2,
                      double B, int i, int length) {
        this.a1 = a1;
        this.b1 = b1;
        this.c1 = c1;
        this.d1 = d1;
        this.a2 = a2;
        this.b2 = b2;
        this.c2 = c2;
        this.d2 = d2;
        this.B = B;
        this.i = i;
        this.length = length;
    }

    public Constraint(int i, int length) {
        this.i = i;
        this.length = length;
        a1 = b1 = c1 = d1 = a2 = b2 = c2 = d2 = B = 0;
    }

    public double b() {
        return B;
    }

    public double[] Ai() {
        double[] ans = new double[this.length * 4];
        int index = this.i * 4;
        ans[index++] = this.a1;
        ans[index++] = this.b1;
        ans[index++] = this.c1;
        ans[index++] = this.d1;
        index %= ans.length;
        ans[index++] = this.a2;
        ans[index++] = this.b2;
        ans[index++] = this.c2;
        ans[index++] = this.d2;
        return ans;
    }

    public static List<Constraint> getConstraints(double p, int i, int length) {
        Constraint[] constraints = {first(p, i, length), second(p, i, length), third(p, i, length), fourth(p, i, length)};
        return Arrays.asList(constraints);
    }

    private static Constraint first(double p, int i, int length) {
        return new Constraint(0, 0, 0, 0, 0, 0, 0, 1, p, i, length);
    }

    private static Constraint second(double p, int i, int length) {
        return new Constraint(0,0,0,1,0,0,0,-1,0,i,length);
    }

    private static Constraint third(double p, int i, int length) {
        return new Constraint(3, 2, 1, 0, 0, 0, -1, 0, 0, i, length);
    }

    private static Constraint fourth( double p,  int i,  int length) {
        return new Constraint(3, 1, 0, 0, 0, -1, 0, 0, 0, i, length);
    }
}
