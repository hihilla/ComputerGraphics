package edu.cg.scene.lightSources;

import edu.cg.algebra.*;

public class PointLight extends Light {
	protected Point position;
	
	//Decay factors:
	protected double kq = 0.01;
	protected double kl = 0.1;
	protected double kc = 1;
	
	protected String description() {
		String endl = System.lineSeparator();
		return "Intensity: " + intensity + endl +
				"Position: " + position + endl +
				"Decay factors: kq = " + kq + ", kl = " + kl + ", kc = " + kc + endl;
	}
	
	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Point Light:" + endl + description();
	}
	
	@Override
	public PointLight initIntensity(Vec intensity) {
		return (PointLight)super.initIntensity(intensity);
	}
	
	public PointLight initPosition(Point position) {
		this.position = position;
		return this;
	}
	
	public PointLight initDecayFactors(double kq, double kl, double kc) {
		this.kq = kq;
		this.kl = kl;
		this.kc = kc;
		return this;
	}

	//TODO: add some methods

	@Override
	public Vec calcIL(Point location) {
		double d = location.dist(position);
		double down = kc + (kl * d) + (kq * (d * d));

		return intensity.div(down);
	}

	@Override
	public Ray getRayToLight(Point p){
		Ray ray = new Ray(p, this.position);
		return ray;
	}

	@Override
	public double calcSi(Hit hit, Ray ray) {
		Point originPoint = ray.source();
		double tLight = originPoint.dist(position);
		if (hit.t() < tLight && hit.t() > Ops.epsilon) {
			// theres shadow!!
			return 0;
		}
		return intensity.norm();
	}
}
