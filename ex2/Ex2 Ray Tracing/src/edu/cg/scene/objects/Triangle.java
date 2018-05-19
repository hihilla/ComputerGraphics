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
		Vec V = ray.direction();
		Point P0 = ray.source();

		Vec V1 = p1.sub(P0);
		Vec V2 = p2.sub(P0);
		Vec V3 = p3.sub(P0);

		Vec n1 = V1.cross(V2);
		Vec n2 = V2.cross(V3);
		Vec n3 = V3.cross(V1);

		// if all normals turn in, dot product between rays vector and a normal will be positive.
		// if all normals turn out, negative.

		// p3-p1 and n1 has same direction -> tun in = true
		Vec v13 = p3.sub(p1);
		boolean turnIn = n1.dot(v13) > 0;
		if (V.dot(n1) > 0 && turnIn) {
			// hit!
			Vec v12 = p2.sub(p1);
			Vec N = v12.cross(v13).normalize();
			// create plane with N and p1
			Plain plain = new Plain(N, p1);
			// return hit with plain
			return plain.intersect(ray);
		}

		// no hit
		return new Hit();
	}
}
