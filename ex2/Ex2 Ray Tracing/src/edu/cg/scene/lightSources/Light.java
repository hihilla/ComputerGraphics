package edu.cg.scene.lightSources;

import edu.cg.algebra.*;

public abstract class Light {
	protected Vec intensity = new Vec(1, 1, 1); //white color
	
	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Intensity: " + intensity + endl;
	}
	
	public Light initIntensity(Vec intensity) {
		this.intensity = intensity;
		return this;
	}
	
	//TODO: add some methods

	abstract public Ray getRayToLight(Point p);

	abstract public Vec calcIL(Point location);

	public int calcSi(Hit hit, Ray ray) {
		if (hit.successHit()) {
			return 0;
		}
		return 1;
	}
}
