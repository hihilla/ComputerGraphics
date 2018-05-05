package edu.cg.scene.objects;

import edu.cg.UnimplementedMethodException;
import edu.cg.algebra.Hit;
import edu.cg.algebra.Point;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;

public class Triangle extends Shape {
	private Point p1, p2, p3;
	
	public Triangle() {
		p1 = p2 = p3 = null;
	}
	
	public Triangle(Point p1, Point p2, Point p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
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
		// First, intersect ray with plane
		// calc normal:
		Vec v1 = p1.sub(p2);
		Vec v2 = p3.sub(p2);
		Vec temp = v2.cross(v1);
		Vec N = temp.div(temp.length());
		// calc hit
		Vec V = ray.direction();
		Point P0 = ray.source();

		Vec up = p1.sub(P0);
		double down = N.dot(V);
		Vec shever = up.div(down);
		double t = N.dot(shever);
		Point P = P0.add(t, V);
		// Then, check if point is inside triangle
		if (isInside(P0, P)) {
			return new Hit(t, N);
		}

		// no hit
		return new Hit();
	}

	private boolean isInside(Point P0, Point P) {
		// Algebraic Method
		Vec V1 = p1.sub(P0);
		Vec V2 = p2.sub(P0);
		Vec V3 = p3.sub(P0);

		Vec N1 = V2.cross(V1).div(V2.cross(V1).length());
		Vec N2 = V3.cross(V2).div(V3.cross(V2).length());
		Vec N3 = V1.cross(V3).div(V1.cross(V3).length());

		Vec temp = P.sub(P0);
		double sign1 = Math.signum(temp.dot(N1));
		double sign2 = Math.signum(temp.dot(N2));
		double sign3 = Math.signum(temp.dot(N3));

		return sign1 == sign2 && sign2 == sign3;
	}
}
