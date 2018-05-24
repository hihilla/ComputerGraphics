package edu.cg.scene.objects;

import edu.cg.algebra.Hit;
import edu.cg.algebra.Point;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;

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
        // First, intersect ray with plane

        Plain trianglePlane = new Plain(p1, p2, p3);
        Hit hitWithPlane = trianglePlane.intersect(ray);

        if (hitWithPlane.t() == 0 && hitWithPlane.getNormalToSurface().x == 0 &&
                hitWithPlane.getNormalToSurface().y == 0 &&
                hitWithPlane.getNormalToSurface().z ==0){
            return new Hit();
        }
        else {
            if (isInside(ray.source(), ray.direction())) {
                return hitWithPlane;
            }
        }
        // calc normal:
//        Vec v1 = p1.sub(p2);
//        Vec v2 = p3.sub(p2);
//        Vec temp = v2.cross(v1);
//        Vec N = temp.mult(1.0 / temp.length());
//        // calc hit
//        Vec V = ray.direction();
//        Point P0 = ray.source();
//
//        Vec up = p1.sub(P0);
//        double down = N.dot(V);
//        Vec shever = up.mult(1.0 / down);
//        double t = N.dot(shever);
//        Point P = P0.add(t, V);
        // Then, check if point is inside triangle
//        if (isInside(P0, P)) {
//            return new Hit(t, N);
//        }

        // hit not inside triangle
        return new Hit();
    }

//    private boolean isInside(Point P0, Point P) {
//        // Algebraic Method
//        Vec V1 = p1.sub(P0);
//        Vec V2 = p2.sub(P0);
//        Vec V3 = p3.sub(P0);
//
//        Vec N1 = V2.cross(V1).mult(1.0 / V2.cross(V1).length());
//        Vec N2 = V3.cross(V2).mult(1.0 / V3.cross(V2).length());
//        Vec N3 = V1.cross(V3).mult(1.0 / V1.cross(V3).length());
//
//        Vec temp = P.sub(P0);
//        boolean sign1 = isPos(temp.dot(N1));
//        boolean sign2 = isPos(temp.dot(N2));
//        boolean sign3 = isPos(temp.dot(N3));
//
//        return sign1 == sign2 && sign2 == sign3;
//    }
//
//    private Boolean isPos(double cosAngle){
//	    return cosAngle > 0;
//    }
//}

    private boolean isInside(Point P0, Vec rayDirection) {
        // Algebraic Method
        Vec V1 = p1.sub(P0);
        Vec V2 = p2.sub(P0);
        Vec V3 = p3.sub(P0);

        Vec N1 = V2.cross(V1).mult(1.0 / V2.cross(V1).length());
        Vec N2 = V3.cross(V2).mult(1.0 / V3.cross(V2).length());
        Vec N3 = V1.cross(V3).mult(1.0 / V1.cross(V3).length());

        boolean sign1 = isPos(rayDirection.dot(N1));
        boolean sign2 = isPos(rayDirection.dot(N2));
        boolean sign3 = isPos(rayDirection.dot(N3));

        return sign1 == sign2 && sign2 == sign3;
    }

    private Boolean isPos(double cosAngle){
        return cosAngle > 0;
    }
}
