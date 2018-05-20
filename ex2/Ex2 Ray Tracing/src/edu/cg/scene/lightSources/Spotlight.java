package edu.cg.scene.lightSources;

import edu.cg.algebra.*;

public class Spotlight extends PointLight {
	private Vec direction;
	private double angle = 0.866; //cosine value ~ 30 degrees
	
	public Spotlight initDirection(Vec direction) {
		this.direction = direction;
		return this;
	}
	
	public Spotlight initAngle(double angle) {
		this.angle = angle;
		return this;
	}
	
	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Spotlight: " + endl +
				description() + 
				"Direction: " + direction + endl +
				"Angle: " + angle + endl;
	}
	
	@Override
	public Spotlight initPosition(Point position) {
		return (Spotlight)super.initPosition(position);
	}
	
	@Override
	public Spotlight initIntensity(Vec intensity) {
		return (Spotlight)super.initIntensity(intensity);
	}
	
	@Override
	public Spotlight initDecayFactors(double q, double l, double c) {
		return (Spotlight)super.initDecayFactors(q, l, c);
	}

	@Override
	public Vec calcIL(Point location) {
		Vec L = location.sub(position);
		double d = location.dist(position);
		double down = kc + (kl * d) + (kq * (d * d));
		Vec up = intensity.mult(this.direction.dot(L));

		return up.div(down);
	}

	@Override
	public double calcSi(Hit hit, Ray ray) {
		Point originPoint = ray.source();
		double tLight = originPoint.dist(position);
		if (hit.t() < tLight && hit.t() > Ops.epsilon) {
			// there's shadow!!
			return 0;
		}
		// no shadow - check if ray is in the direction and angle of light
		Vec u = ray.direction().neg();
		Vec v = direction;
		// find angle between ray and direction. if smaller than angle of light - object is lit!
		double a = v.findAngleWith(u);
		if (a < angle) {
			// lit!
			return intensity.norm();
		}
		return 0;
	}
}
