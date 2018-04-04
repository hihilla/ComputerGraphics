package edu.cg;

import java.awt.image.BufferedImage;

public class SeamsCarver extends ImageProcessor {
	
	//MARK: An inner interface for functional programming.
	@FunctionalInterface
	interface ResizeOperation {
		BufferedImage apply();
	}
	
	//MARK: Fields
	private int numOfSeams;
	private ResizeOperation resizeOp;
	
	//TODO: Add some additional fields:
	
	
	//MARK: Constructor
	public SeamsCarver(Logger logger, BufferedImage workingImage,
			int outWidth, RGBWeights rgbWeights) {
		super(logger, workingImage, rgbWeights, outWidth, workingImage.getHeight()); 
		
		numOfSeams = Math.abs(outWidth - inWidth);
		
		if(inWidth < 2 | inHeight < 2)
			throw new RuntimeException("Can not apply seam carving: workingImage is too small");
		
		if(numOfSeams > inWidth/2)
			throw new RuntimeException("Can not apply seam carving: too many seams...");
		
		//Sets resizeOp with an appropriate method reference
		if(outWidth > inWidth)
			resizeOp = this::increaseImageWidth;
		else if(outWidth < inWidth)
			resizeOp = this::reduceImageWidth;
		else
			resizeOp = this::duplicateWorkingImage;
		
		//TODO: Initialize your additional fields and apply some preliminary calculations:
		numOfSeams = Math.abs(outWidth - inWidth);
	}
	
	//MARK: Methods
	public BufferedImage resize() {
		return resizeOp.apply();
	}
	
	//MARK: Unimplemented methods
	private BufferedImage reduceImageWidth() {
		return removeKseams();
	}
	
	private BufferedImage increaseImageWidth() {
		return addKseams();
	}
	
	public BufferedImage showSeams(int seamColorRGB) {
		return findKseams(seamColorRGB);
	}

	private BufferedImage findKseams(int seamColorRGB) {
		logger.log("findKseams");
		BufferedImage outImg = duplicateWorkingImage();
		BufferedImage greyImage = greyscale(outImg);
		int[][] seams = new int[inHeight][inWidth];
		for (int i = 0; i < numOfSeams; i++) {
			// for reach seam calculate the costs matrix
			int[][] pixelsEnergy = calcPixelsEnergy(greyImage);
			long[][] cost = calcCostMatrix(greyImage, pixelsEnergy);
			// optimal seam is minimum in lowest row
			int y = inHeight - 1;
			int minIndex = getMinIndex(cost[y]);
			logger.log("min index: " + minIndex);
			// backtrack cost matrix to get seam
			int[] seam = getSeam(cost, minIndex, greyImage, pixelsEnergy);
			// save seam, add all seams later
			seams[i] = seam;
			// remove the seam as to mark it done
			outImg = removeSeamFrom(outImg, seam);
			greyImage = greyscale(outImg);
		}

		// mark seams:
		outImg = duplicateWorkingImage();
		for (int i = 0; i < numOfSeams; i++) {
			markSeamIn(outImg, seams[i], seamColorRGB);
		}

		return outImg;
	}

	private BufferedImage addKseams() {
		logger.log("addKseams");
		BufferedImage outImg = duplicateWorkingImage();
		BufferedImage greyImage = greyscale(outImg);
		int[][] seams = new int[inHeight][inWidth];
		for (int i = 0; i < numOfSeams; i++) {
			// for reach seam calculate the costs matrix
			int[][] pixelsEnergy = calcPixelsEnergy(greyImage);
			long[][] cost = calcCostMatrix(greyImage, pixelsEnergy);
			// optimal seam is minimum in lowest row
			int y = inHeight - 1;
			int minIndex = getMinIndex(cost[y]);
			// backtrack cost matrix to get seam
			int[] seam = getSeam(cost, minIndex, greyImage, pixelsEnergy);
			// save seam, add all seams later
			seams[i] = seam;
			// remove the seam as to mark it done
			outImg = removeSeamFrom(outImg, seam);
			greyImage = greyscale(outImg);
		}

		// add seams:
		outImg = duplicateWorkingImage();
		for (int i = 0; i < numOfSeams; i++) {
			int[] seam = seams[i];
			outImg = addSeamTo(outImg, seam);
		}

		return outImg;
	}

	private BufferedImage removeKseams() {
		logger.log("removeKseams");
		BufferedImage outImg = duplicateWorkingImage();
		BufferedImage greyImage = greyscale(outImg);
		for (int i = 0; i < numOfSeams; i++) {
			// for reach seam calculate the costs matrix
			int[][] pixelsEnergy = calcPixelsEnergy(greyImage);
			long[][] cost = calcCostMatrix(greyImage, pixelsEnergy);
			// optimal seam is minimum in lowest row
			int y = inHeight - 1;
			int minIndex = getMinIndex(cost[y]);
			// backtrack cost matrix to get seam
			int[] seam = getSeam(cost, minIndex, greyImage, pixelsEnergy);
			// remove the seam
			outImg = removeSeamFrom(outImg, seam);
			greyImage = greyscale(outImg);
		}
		return outImg;
	}

	public BufferedImage removeSeamFrom(BufferedImage img, int[] seam) {
		logger.log("removeSeamFrom");
		int height = img.getHeight();
		int newWidth = img.getWidth() - 1;
		BufferedImage outImg = newEmptyImage(newWidth, height);
		setForEachParameters(newWidth, height);
		forEach((y, x) -> {
			if (x < seam[y]) {
				// current x is left to the seam - copy regularly
				outImg.setRGB(x, y, img.getRGB(x, y));
			} else if (x != newWidth) {
				// current x is right to the seam - shift
				outImg.setRGB(x, y, img.getRGB(x + 1, y));
			}
		});
		setForEachInputParameters();

		return outImg;
	}

	public BufferedImage addSeamTo(BufferedImage img, int[] seam) {
		logger.log("addSeamTo");
		int height = img.getHeight();
		int newWidth = img.getWidth() + 1;
		BufferedImage outImg = newEmptyImage(newWidth, height);
		setForEachParameters(newWidth, height);
		forEach((y, x) -> {
			if (x < seam[y]) {
				// current x is left to the seam - copy regularly
				outImg.setRGB(x, y, img.getRGB(x, y));
			} else {
				// current x is right to the seam - shift
				outImg.setRGB(x, y, img.getRGB(x - 1, y));
			}
		});
		setForEachInputParameters();

		return outImg;
	}

	public void markSeamIn(BufferedImage img, int[] seam, int seamColorRGB) {
		logger.log("markSeamIn");
		forEach((y, x) -> {
			if (x != seam[y]) {
				// current x is left to the seam - copy regularly
				img.setRGB(x, y, img.getRGB(x, y));
			} else {
				// current x is right to the seam - shift
				img.setRGB(x, y, seamColorRGB);
			}
		});
	}

	public int[] getSeam(long[][] cost, int minIndex, BufferedImage greyImage, int[][] pixelsEnergy) {
		logger.log("getSeam");
		//  the entry x at index y in the array represent pixel (y, x) in the seam
		int[] seam = new int[inHeight];
		int j = minIndex;
		seam[inHeight - 1] = j;

		for (int i = inHeight - 2; i > 0; i--) {
			int p = pixelsEnergy[i][j];
			if (cost[i][j] == p + cost[i - 1][j] + calcCV(j, i, greyImage)) {
				seam[i] = j;
			} else if (cost[i][j] == p + cost[i - 1][j - 1] + calcCL(j, i, greyImage)) {
				seam[i] = j - 1;
			} else {
				seam[i] = j + 1;
			}

			j = seam[i];
		}

		return seam;
	}

	public int getMinIndex(long[] inArray) {
		logger.log("getMinIndex");
		long minVal = inArray[0];
		int minIndex = 0;
		for (int i = 1; i < inArray.length; i++) {
			if (inArray[i] < minVal) {
				minVal = inArray[i];
				minIndex = i;
			}
		}
		return minIndex;
	}

	private long[][] calcCostMatrix(BufferedImage greyImage, int[][] pixelsEnergy) {
		logger.log("calcCostMatrix");
		int width = greyImage.getWidth();
		int height = greyImage.getHeight();
		long[][] cost = new long[height][width];
		setForEachParameters(width, height);
		forEach((y, x) -> {
			int p = pixelsEnergy[y][x];
			if (y == 0) {
				cost[y][x] = p;
			} else if (x == 0) {
				cost[y][x] = p +
						Math.min((cost[y - 1][x] + calcCV(x, y, greyImage)),
								(cost[y - 1][x + 1] + calcCR(x, y, greyImage)));
			} else {
				int CL = calcCL(x, y, greyImage);
				int CV = calcCV(x, y, greyImage);
				int CR = calcCR(x, y, greyImage);
				cost[y][x] = p +
						Math.min((Math.min((cost[y - 1][x - 1] + CL),
								(cost[y - 1][x] + CV))),
								(cost[y - 1][x + 1] + CR));
			}
		});
		setForEachInputParameters();

		return cost;
	}

	private int calcPixelEnergy(int x, int y, BufferedImage greyImage) {
		if (x < greyImage.getWidth() - 1) {
			int p = new Color(greyImage.getRGB(x, y)).getBlue();
			int p1 = new Color(greyImage.getRGB(x + 1, y)).getBlue();
			return Math.abs(p - p1);
		}

		int p = new Color(greyImage.getRGB(x, y)).getBlue();
		int p1 = new Color(greyImage.getRGB(x - 1, y)).getBlue();
		return Math.abs(p - p1);
	}

	private int[][] calcPixelsEnergy(BufferedImage greyImage) {
		logger.log("calcPixelsEnergy");
		int width = greyImage.getWidth();
		int height = greyImage.getHeight();
		int[][] pEnergy = new int[height][width];
		setForEachParameters(width, height);
		forEach((y, x) -> {
			pEnergy[y][x] = calcPixelEnergy(x, y, greyImage);
		});
		setForEachInputParameters();
		return pEnergy;
	}

	private int calcCL(int x, int y, BufferedImage greyImage) {
		logger.log("CL");
		int p1 = new Color(greyImage.getRGB(x, y - 1)).getBlue();
		int p2 = new Color(greyImage.getRGB(x - 1, y)).getBlue();
		int p3 = new Color(greyImage.getRGB(x + 1, y)).getBlue();
		return Math.abs(p1 - p2) + Math.abs(p2 - p3);
	}

	private int calcCV(int x, int y, BufferedImage greyImage) {
		logger.log("CV");
		int p2 = new Color(greyImage.getRGB(x - 1, y)).getBlue();
		int p3 = new Color(greyImage.getRGB(x + 1, y)).getBlue();
		return Math.abs(p3 - p2);
	}

	private int calcCR(int x, int y, BufferedImage greyImage) {
		logger.log("CR");
		int p1 = new Color(greyImage.getRGB(x, y - 1)).getBlue();
		int p2 = new Color(greyImage.getRGB(x - 1, y)).getBlue();
		int p3 = new Color(greyImage.getRGB(x + 1, y)).getBlue();
		return Math.abs(p1 - p3) + Math.abs(p2 - p3);
	}
}
