package edu.cg;

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
}