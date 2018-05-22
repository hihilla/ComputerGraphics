package edu.cg.scene.objects;

import edu.cg.UnimplementedMethodException;
import edu.cg.algebra.Hit;
import edu.cg.algebra.Point;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends Shape {
	private Point p1, p2, p3;
	private Plain plain;
	
	public Triangle() {
		p1 = p2 = p3 = null;
	}
	
	public Triangle(Point p1, Point p2, Point p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public Triangle(int a, int b, int c) {
		this(new Point(a), new Point(b), new Point(c));
	}
	
	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Triangle:" + endl +
				"p1: " + p1 + endl + 
				"p2: " + p2 + endl +
				"p3: " + p3 + endl;
	}

	@Override
	public Hit intersect(Ray ray) {
		// create plain from triangle and intersect with it
		Hit hit = plain().intersect(ray);
		if (!hit.successHit()) {
			// no hit
			return hit;
		}

		// check if inside triangle
		// create the pyramid that connects P0 and triangle
		Point p0 = ray.source();
		Triangle[] pyramid = new Triangle[3];
		pyramid[0] = new Triangle(p0, p1, p2);
		pyramid[1] = new Triangle(p0, p2, p3);
		pyramid[2] = new Triangle(p0, p3, p1);

		Point P = ray.getP(hit.t());
		Ray rayToP = new Ray(p0, P);
		boolean[] signs = new boolean[3];
		int index = 0;
		for (Triangle t: pyramid) {
			signs[index++] = t.normal().dot(rayToP.direction()) > 0;
		}

		boolean allTrue = true;
		boolean allFalse = true;
		for (boolean sign: signs) {
			allTrue &= sign;
			allFalse &= !sign;
		}

		if (allTrue || allFalse) {
			// normals to sides of pyramid says ray hits inside!
			return hit;
		}

		// no hit
		return new Hit();
	}

	private synchronized Plain plain() {
		if (this.plain == null) {
			Vec v2 = this.p2.sub(this.p1);
			Vec v3 = this.p3.sub(this.p1);
			Vec n = v2.cross(v3).normalize();
			this.plain = new Plain(n, this.p1);
		}
		return this.plain;
	}

	private Vec normal() {
		return this.plain().normal();
	}
}
