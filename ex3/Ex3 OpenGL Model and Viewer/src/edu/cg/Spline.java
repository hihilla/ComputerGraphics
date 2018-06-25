package edu.cg;

import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public  class Spline {
    PolynomialFunc[] polyFuncs;

    public Spline(PolynomialFunc px, PolynomialFunc py, PolynomialFunc pz) {
        PolynomialFunc[] polyfuncs = {px, py, pz};
        polyFuncs = polyfuncs;
    }

    public double calcLength() {
        double t = 0;
        double dt = 1.0 / 1000;
        double sum = 0;

        for (int i = 0; i < 1000; i++) {
            // calculate 𝑝0=𝑓𝑖(𝑡) and 𝑝1=𝑓𝑖(𝑡+Δ𝑡)
            Point p0 = point(t);
            t += dt;
            Point p1 = point(t);
            sum += p0.sub(p1).length();
        }

        return sum;
    }

    public Point point(double t) {
        return new Point(polyFuncs[0].calc(t), polyFuncs[1].calc(t), polyFuncs[2].calc(t));
    }

    // 𝑡𝑎𝑛𝑔𝑒𝑛𝑡(𝑓𝑖(𝑡))=𝑓𝑖′(𝑡)=(𝑥𝑖′(𝑡),𝑦𝑖′(𝑡),𝑧𝑖′(𝑡))
    public Vec tangent(double t) {
        return new Vec(polyFuncs[0].firstNigzeret(t),
                polyFuncs[1].firstNigzeret(t),
                polyFuncs[2].firstNigzeret(t)).normalize();
    }


    // 𝑛𝑜𝑟𝑚𝑎𝑙(𝑓𝑖(𝑡))=(𝑓𝑖′(𝑡)×𝑓𝑖′′(𝑡))×𝑓𝑖′(𝑡)‖(𝑓𝑖′(𝑡)×𝑓𝑖′′(𝑡))×𝑓𝑖′(𝑡)‖
    public Vec normal(double t) {
        Vec firstNigzeret = new Vec(polyFuncs[0].firstNigzeret(t),
                polyFuncs[1].firstNigzeret(t),
                polyFuncs[2].firstNigzeret(t));
        Vec secondNigzeret = new Vec(polyFuncs[0].secondNigzeret(t),
                polyFuncs[1].secondNigzeret(t),
                polyFuncs[2].secondNigzeret(t));

        Vec right = firstNigzeret.cross(secondNigzeret);
        Vec top = right.cross(firstNigzeret);
        return top.normalize();
    }
}
