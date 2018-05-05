package edu.cg.scene.lightSources;

import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

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

	/**
	 * IL(x,y,z,q,f,l) ...
	 • describes the intensity of energy,
	 • leaving a light source, …
	 • arriving at location (x,y,z), ...
	 • from direction (q,f), ...
	 • with wavelength l
	 */
	abstract public Vec calcIL(Point location, Vec direction, double wavelength);
}
