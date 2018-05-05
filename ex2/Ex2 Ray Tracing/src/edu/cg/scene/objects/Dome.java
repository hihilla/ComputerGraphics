package edu.cg.scene.objects;

import edu.cg.UnimplementedMethodException;
import edu.cg.algebra.Hit;
import edu.cg.algebra.Point;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;

public class Dome extends Shape {
	private Sphere sphere;
	private Plain plain;

	public Dome() {
		sphere = new Sphere().initCenter(new Point(0, -0.5, -6));
		plain = new Plain(new Vec(-1, 0, -1), new Point(0, -0.5, -6));
	}

	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Dome:" + endl +
				sphere + plain + endl;
	}

	@Override
	public Hit intersect(Ray ray) {
		// start by finding hit with the sphere:
		Hit hit = sphere.intersect(ray);
		// only sphere in the plains normal side count!
		// if the dot product is positive, the hit is in the dome side
		double product = plain.normal().dot(hit.getNormalToSurface());
		if (product > 0) {
			return hit;
		}

		// check if hit is on the plane
		hit = plain.intersect(ray);

		// prepare to calculate
		double A = plain.getA();
		double B = plain.getB();
		double C = plain.getC();
		double D = plain.getD();
		double x = sphere.getCenter().x;
		double y = sphere.getCenter().y;
		double z = sphere.getCenter().z;
		double R = sphere.getRadius();

		// calculate the circle which is the intersection between the sphere and the plain
        double sqrt = Math.sqrt(A * A + B * B + C * C);
        double rho = (A * x + B * y + C * z + D) / sqrt;
        double r = Math.sqrt(R * R + rho * rho);
        Point c = sphere.getCenter().add(rho / sqrt, plain.normal());

        // check if hit is in circle
		Point p = ray.source().add(hit.t(), ray.direction());
		double dist = c.dist(p);
		if (dist > r) {
			return new Hit();
		}

		return hit;
	}
}
