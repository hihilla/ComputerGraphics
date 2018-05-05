package edu.cg.algebra;

import edu.cg.scene.objects.Surface;

public class Hit implements Comparable<Hit> {
	private final double t;
	private final Vec normalToSurface;
	
	private boolean isWithin = false;
	private Surface surface = null;
	private boolean successHit = true;
	
	public Hit(double t, Vec normalToSurface) {
		this.t = t;
		this.normalToSurface = normalToSurface;
	}

	public Hit() {
		this(0, new Vec());
		this.successHit = false;
	}

	public Vec getNormalToSurface() {
		return normalToSurface;
	}

	public Surface getSurface() {
		return surface;
	}

	public void setSurface(Surface surface) {
		this.surface = surface;
	}
	
	//checks if the intersection was occurred inside the surface (for refraction)
	public boolean isWithinTheSurface() {
		return isWithin;
	}
	
	public Hit setIsWithin(boolean isWithin) {
		this.isWithin = isWithin;
		return this;
	}
	
	public Hit setWithin() {
		return setIsWithin(true);
	}
	
	public Hit setOutside() {
		return setIsWithin(false);
	}
	
	public double t() {
		return t;
	}

	public boolean successHit() { return successHit; }
	
	@Override
	public int compareTo(Hit other) {
		return t < other.t ? -1 : (t > other.t ? 1 : 0);
	}
}
