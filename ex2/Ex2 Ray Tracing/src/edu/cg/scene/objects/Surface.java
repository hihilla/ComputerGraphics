package edu.cg.scene.objects;

import edu.cg.algebra.Hit;
import edu.cg.algebra.Point;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;

public class Surface implements Intersectable {
	private Shape shape;
	private Material material;
	
	public Surface(Shape shape, Material material) {
		this.shape = shape;
		this.material = material;
	}
	
	public Surface() {
		this(null, null);
	}
	
	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Surface:" + endl +
				"Shape:" + endl + shape + endl +
				"Material: " + endl + material + endl;
	}
	
	@Override
	public Hit intersect(Ray ray) {
		Hit hit = shape.intersect(ray);
		if (hit != null && hit.successHit()) {
			hit.setSurface(this);
		}
		return hit;
	}
	
	public Vec Ka() {
		return material.Ka;
	}
	
	public Vec Kd(Point p) {
		return shape.getDiffuseCoefficient(material, p);
	}
	
	public Vec Ks() {
		return material.Ks;
	}
	
	public double reflectionIntensity() {
		return material.reflectionIntensity;
	}
	
	public int shininess() {
		return material.shininess;
	}
	
	public Vec Kt() {
		return material.Kt;
	}
	
	public double refractionIndex() {
		return material.refractionIndex;
	}
	
	public double refractionIntensity() {
		return material.refractionIntensity;
	}
	
	public boolean isTransparent() {
		return material.isTransparent;
	}
	
	public double n1(Hit hit) {
		return hit.isWithinTheSurface() ? material.refractionIndex : 1;
	}
	
	public double n2(Hit hit) {
		return hit.isWithinTheSurface() ? 1 : material.refractionIndex;
	}

	public Vec calcPhong(Vec N, Vec V, Vec L){
		double n = material.shininess;
		Vec R = Vec.calcR(N, L).neg();
		double VdotR = V.neg().dot(R);
		double VRpowN = Math.pow(VdotR, n);

		return (Ks().mult(VRpowN));
	}
}
