package edu.cg.scene;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.cg.Logger;
import edu.cg.UnimplementedMethodException;
import edu.cg.algebra.*;
import edu.cg.scene.lightSources.Light;
import edu.cg.scene.objects.Surface;

public class Scene {
	private String name = "scene";
	private int maxRecursionLevel = 1;
	private int antiAliasingFactor = 1; //gets the values of 1, 2 and 3
	private boolean renderRefarctions = false;
	private boolean renderReflections = false;

	private Point camera = new Point(0, 0, 5);
	private Vec ambient = new Vec(1, 1, 1); //white
	private Vec backgroundColor = new Vec(0, 0.5, 1); //blue sky
	private List<Light> lightSources = new LinkedList<>();
	private List<Surface> surfaces = new LinkedList<>();


	//MARK: initializers
	public Scene initCamera(Point camera) {
		this.camera = camera;
		return this;
	}

	public Scene initAmbient(Vec ambient) {
		this.ambient = ambient;
		return this;
	}

	public Scene initBackgroundColor(Vec backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public Scene addLightSource(Light lightSource) {
		lightSources.add(lightSource);
		return this;
	}

	public Scene addSurface(Surface surface) {
		surfaces.add(surface);
		return this;
	}

	public Scene initMaxRecursionLevel(int maxRecursionLevel) {
		this.maxRecursionLevel = maxRecursionLevel;
		return this;
	}

	public Scene initAntiAliasingFactor(int antiAliasingFactor) {
		this.antiAliasingFactor = antiAliasingFactor;
		return this;
	}

	public Scene initName(String name) {
		this.name = name;
		return this;
	}

	public Scene initRenderRefarctions(boolean renderRefarctions) {
		this.renderRefarctions = renderRefarctions;
		return this;
	}

	public Scene initRenderReflections(boolean renderReflections) {
		this.renderReflections = renderReflections;
		return this;
	}

	//MARK: getters
	public String getName() {
		return name;
	}

	public int getFactor() {
		return antiAliasingFactor;
	}

	public int getMaxRecursionLevel() {
		return maxRecursionLevel;
	}

	public boolean getRenderRefarctions() {
		return renderRefarctions;
	}

	public boolean getRenderReflections() {
		return renderReflections;
	}

	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Camera: " + camera + endl +
				"Ambient: " + ambient + endl +
				"Background Color: " + backgroundColor + endl +
				"Max recursion level: " + maxRecursionLevel + endl +
				"Anti aliasing factor: " + antiAliasingFactor + endl +
				"Light sources:" + endl + lightSources + endl +
				"Surfaces:" + endl + surfaces;
	}

	private static class IndexTransformer {
		private final int max;
		private final int deltaX;
		private final int deltaY;

		IndexTransformer(int width, int height) {
			max = Math.max(width, height);
			deltaX = (max - width) / 2;
			deltaY = (max - height) / 2;
		}

		Point transform(double x, double y) {
			double xPos = (2*(x + deltaX) - max) / ((double)max);
			double yPos = (max - 2*(y + deltaY)) / ((double)max);
			return new Point(xPos, yPos, 0);
		}
	}

	private transient IndexTransformer transformaer = null;
	private transient ExecutorService executor = null;
	private transient Logger logger = null;

	private void initSomeFields(int imgWidth, int imgHeight, Logger logger) {
		this.logger = logger;
		//TODO: initialize your additional field here.
	}


	public BufferedImage render(int imgWidth, int imgHeight, Logger logger)
			throws InterruptedException, ExecutionException {

		initSomeFields(imgWidth, imgHeight, logger);

		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		transformaer = new IndexTransformer(imgWidth, imgHeight);
		int nThreads = Runtime.getRuntime().availableProcessors();
		nThreads = nThreads < 2 ? 2 : nThreads;
		this.logger.log("Intitialize executor. Using " + nThreads + " threads to render " + name);
		executor = Executors.newFixedThreadPool(nThreads);

		@SuppressWarnings("unchecked")
		Future<Color>[][] futures = (Future<Color>[][])(new Future[imgHeight][imgWidth]);

		this.logger.log("Starting to shoot " +
			(imgHeight*imgWidth*antiAliasingFactor*antiAliasingFactor) +
			" rays over " + name);

		for(int y = 0; y < imgHeight; ++y)
			for(int x = 0; x < imgWidth; ++x)
				futures[y][x] = calcColor(x, y);

		this.logger.log("Done shooting rays.");
		this.logger.log("Wating for results...");

		for(int y = 0; y < imgHeight; ++y)
			for(int x = 0; x < imgWidth; ++x) {
				Color color = futures[y][x].get();
				img.setRGB(x, y, color.getRGB());
			}

		executor.shutdown();

		this.logger.log("Ray tracing of " + name + " has been completed.");

		executor = null;
		transformaer = null;
		this.logger = null;

		return img;
	}

	private Future<Color> calcColor(int x, int y) {
		return executor.submit(() -> {
			int alpha = antiAliasingFactor;

			if (alpha == 1){
				Point pointOnScreenPlain = transformaer.transform(x, y);
				Ray ray = new Ray(camera, pointOnScreenPlain);
				Color color = calcColor(ray, 0).toColor();
				return color;
			}

			float sumX = 0;
			float sumY = 0;
			float sumZ = 0;
			for (int i = 0; i < alpha; i++){
				for (int j = 0; j < alpha; j++){
					Point pointOnScreenPlain = transformaer.transform(x + i/(alpha*alpha), y + j/(alpha*alpha));
					Ray ray = new Ray(camera, pointOnScreenPlain);
					Color color = calcColor(ray, 0).toColor();
					sumX += color.getRed();
					sumY += color.getGreen();
					sumZ += color.getBlue();
					if (i == j && i == alpha)
					System.out.println(sumX+ " " + sumY + " " + sumZ);
				}
			}

			float avgX = sumX / (float)Math.pow(alpha, 2);
			float avgY = sumY / (float)Math.pow(alpha, 2);
			float avgZ = sumZ / (float)Math.pow(alpha, 2);
			//System.out.println(avgX+ " " + avgY + " " + avgZ);

			Color avgColor = new Color(getColorWeight(avgX), getColorWeight(avgY), getColorWeight(avgZ));

			return avgColor;
		});


	}

	public float getColorWeight(float color) {
		return (float) (color / 255.0);
	}

	/**
	 * This method simulates the ray tracing algorithm.
	 * @param ray
	 * @param recusionLevel
	 * @return
	 */
	private Vec calcColor(Ray ray, int recusionLevel) {
		// shoot ray to all surfaces to get closest hit
		Hit hit = findIntersection(ray);

		// if ray doesn't hit anything - return background color
		if (!hit.successHit()) {
			return backgroundColor;
		}
		Point hittingPoint = ray.getHittingPoint(hit);
		Vec N = hit.getNormalToSurface();
		Vec V = ray.direction().normalize(); // Adar added norm
		Surface surface = hit.getSurface();
		Vec ambientColor = surface.Ka().mult(this.ambient); // KAIA
		Vec sigma = new Vec();
		for (Light light: lightSources) {
			Ray lRay = light.getRayToLight(hittingPoint);
			Vec Li = lRay.direction().normalize(); // Adar normed
			Vec Ii = light.calcIL(hittingPoint);

			Vec first = surface.Kd(hittingPoint).mult(N.dot(Li));
			Vec second = surface.calcPhong(N, V, Li);

			Vec sum = first.add(second);

			double Si = calcSi(lRay, light);
			Vec IiSi = sum.mult(Ii).mult(Si);
			sigma = sigma.add(IiSi);
		}

		if (!renderReflections) {
			return ambientColor.add(sigma);
		}

		// recursive part:
		if (recusionLevel == maxRecursionLevel) {
			return new Vec();
		}
		Vec KRIR = new Vec();
		Ray outRay = new Ray(hittingPoint, Vec.calcR(N, V));
		double intensity = surface.reflectionIntensity();
		KRIR = KRIR.add(calcColor(outRay, recusionLevel + 1).mult(intensity));


		return ambientColor.add(sigma).add(KRIR);
	}

	private Hit findIntersection(Ray ray) {
		double minT = Double.MAX_VALUE;
		Hit hit = new Hit();
		for (Surface surface: surfaces) {
			Hit curHit = surface.intersect(ray);
			if (curHit.t() < minT && curHit.t() > Ops.epsilon) {
				minT = curHit.t();
				hit = curHit;
			}
		}

		return hit;
	}

	private double calcSi(Ray L, Light light) {
		Hit hit = findIntersection(L);
		return light.calcSi(hit, L);
	}
}
