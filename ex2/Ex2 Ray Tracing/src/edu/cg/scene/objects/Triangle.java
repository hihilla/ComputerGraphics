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
                hitWithPlane.getNormalToSurface().z == 0){
            return new Hit();
        }
        else {
            Ray hitPointRay = new Ray(ray.source(), ray.getHittingPoint(hitWithPlane));
            if (isInside(ray.source(), hitPointRay.direction())) {
                return hitWithPlane;
            }
        }

        // hit not inside triangle
        return new Hit();
    }


    private boolean isInside(Point P0, Vec rayDirection) {
        // Algebraic Method
        Triangle V1 = new Triangle(p1, p2, P0);
        Triangle V2 = new Triangle(p2, p3, P0);
        Triangle V3 = new Triangle(p3, p1, P0);

        Vec N1 = V1.getTrianglePlane().normal();
        Vec N2 = V2.getTrianglePlane().normal();
        Vec N3 = V3.getTrianglePlane().normal();

        boolean sign1 = isPos(rayDirection.dot(N1));
        boolean sign2 = isPos(rayDirection.dot(N2));
        boolean sign3 = isPos(rayDirection.dot(N3));

        return sign1 == sign2 && sign2 == sign3;
    }

    private Boolean isPos(double cosAngle){
        return cosAngle > 0;
    }

    private Plain getTrianglePlane(){
	    return new Plain(p1, p2, p3);
    }
}
