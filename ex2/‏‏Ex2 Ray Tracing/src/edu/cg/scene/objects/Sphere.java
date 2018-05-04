package edu.cg.scene.objects;

import edu.cg.UnimplementedMethodException;
import edu.cg.algebra.Hit;
import edu.cg.algebra.Point;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;

public class Sphere extends Shape {
	private Point center;
	private double radius;
	
	public Sphere(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Sphere() {
		this(new Point(0, -0.5, -6), 0.5);
	}
	
	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Sphere:" + endl + 
				"Center: " + center + endl +
				"Radius: " + radius + endl;
	}
	
	public Sphere initCenter(Point center) {
		this.center = center;
		return this;
	}
	
	public Sphere initRadius(double radius) {
		this.radius = radius;
		return this;
	}
	
	@Override
	public Hit intersect(Ray ray) {
		Vec V = ray.direction();
		Point P0 = ray.source();
		Point O = center;
		double r = radius;

		// Geometric method:
		Vec L = P0.sub(O);
		double tm = L.dot(V);
		double d2 = L.lengthSqr() - (tm * tm);
		if (d2 > (r * r)) {
			// no hit
			return new Hit();
		}

		double th = Math.sqrt((r * r) - d2);
		double t = tm - th;
		if (t < 0) {
			t = tm + th;
		}

		Point P = P0.add(t, V);
		Vec temp = P.sub(O);
		Vec N = temp.mult(1.0 / temp.length());

		return new Hit(t, N);
	}
}
