package edu.cg;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class SeamsCarver extends ImageProcessor {

    //MARK: An inner interface for functional programming.
    @FunctionalInterface
    interface ResizeOperation {
        BufferedImage apply();
    }

    //MARK: Fields
    private int numOfSeams;
    private ResizeOperation resizeOp;
    private int[][] stepCounter; // how many steps pixel was moved to the LEFT

    //TODO: Add some additional fields:


    //MARK: Constructor
    public SeamsCarver(Logger logger, BufferedImage workingImage,
                       int outWidth, RGBWeights rgbWeights) {
        super(logger, workingImage, rgbWeights, outWidth, workingImage.getHeight());

        numOfSeams = Math.abs(outWidth - inWidth);

        if (inWidth < 2 | inHeight < 2)
            throw new RuntimeException("Can not apply seam carving: workingImage is too small");

        if (numOfSeams > inWidth / 2)
            throw new RuntimeException("Can not apply seam carving: too many seams...");

        //Sets resizeOp with an appropriate method reference
        if (outWidth > inWidth)
            resizeOp = this::increaseImageWidth;
        else if (outWidth < inWidth)
            resizeOp = this::reduceImageWidth;
        else
            resizeOp = this::duplicateWorkingImage;

        //TODO: Initialize your additional fields and apply some preliminary calculations:
        stepCounter = new int[inHeight][inWidth];
        forEach((y, x) -> {
//            int[] indices = {x, y};
            stepCounter[y][x] = 0;
        });
    }

    //MARK: Methods
    public BufferedImage resize() {
        return resizeOp.apply();
    }

    //MARK: Unimplemented methods
    private BufferedImage reduceImageWidth() {
        BufferedImage outImg = duplicateWorkingImage();
        for (int i = 0; i < numOfSeams; i++) {
            BufferedImage greyImg = greyscale(outImg);
            int[][] pixelEnergy = calcPixelEnergy(greyImg);
            long[][] cost = calcCostMatrix(greyImg, pixelEnergy);
            // find the end of the seam: index of the minimum cost at the bottom row
            int minIndex = getMinIndex(cost[inHeight - 1]);
            int[] seam = getSeam(cost, pixelEnergy, greyImg, minIndex);
            // remove seam now
            outImg = removeSeamFrom(outImg, seam);
        }

        return outImg;
    }

    private BufferedImage increaseImageWidth() {
        //TODO: Implement this method, remove the exception.
        throw new UnimplementedMethodException("reduceImageWidth");
    }

    public BufferedImage showSeams(int seamColorRGB) {
        int[][] seams = getAllSeams();
        int[][] origSeams = getOriginalSeams(seams);
        BufferedImage outImg = duplicateWorkingImage();
        System.out.println("we have it");
        setForEachParameters(inHeight, numOfSeams);
        forEach((i, y) -> {
            int x = origSeams[i][y];
            outImg.setRGB(x, y, seamColorRGB);
        });
        setForEachInputParameters();
        return outImg;
    }

    private int[][] getOriginalSeams(int[][] seams) {
        int[][] origSeams = new int[numOfSeams][inHeight];
        // must start with the last seam that was removed
        for (int i = numOfSeams - 1; i >= 0; i--) {
            int[] seam = seams[i];
            for (int y = 0; y < inHeight; y++) {
                int x = seam[y];
                int steps = stepCounter[y][x];
                int origX = x + steps - 1;
                System.out.format("after shift x: %d original x: %d\n", x, origX);
                // update stepCounter:
                for (int j = x + 1; j < inWidth; j++) {
                    stepCounter[y][j]--;
                }
                origSeams[i][y] = origX;
            }
        }
        System.out.format("there are %d seams \n", origSeams.length);
        return origSeams;
    }

    private int[][] getAllSeams() {
        BufferedImage outImg = duplicateWorkingImage();
        BufferedImage greyImg = greyscale(outImg);
        int[][] seams = new int[numOfSeams][inHeight];
        int[][] pixelEnergy = calcPixelEnergy(greyImg);
        for (int i = 0; i < numOfSeams; i++) {
            long[][] cost = calcCostMatrix(greyImg, pixelEnergy);
            // find the end of the seam: index of the minimum cost at the bottom row
            int minIndex = getMinIndex(cost[inHeight - 1]);
            seams[i] = getSeam(cost, pixelEnergy, greyImg, minIndex);
            // remove seam and mark it
            outImg = removeSeamFrom(outImg, seams[i]);
        }

        return seams;
    }

    private long[][] calcCostMatrix(BufferedImage greyImg, int[][] pixelEnergy) {
        int height = greyImg.getHeight();
        int width = greyImg.getWidth();
        long[][] cost = new long[height][width];
        setForEachParameters(width, height);
        forEach((y, x) -> {
            int p = pixelEnergy[y][x];
            if (y == 0) {
                cost[y][x] = p;
            } else if (x == 0) {
                cost[y][x] = p +
                        Math.min(cost[y - 1][x] + calcCV(greyImg, y, x),
                                cost[y - 1][x + 1] + calcCR(greyImg, y, x));
            } else if (x < width - 1) {
                cost[y][x] = p +
                        Math.min(Math.min(cost[y - 1][x - 1] + calcCL(greyImg, y, x),
                                cost[y - 1][x] + calcCV(greyImg, y, x)),
                                cost[y - 1][x + 1] + calcCR(greyImg, y, x));
            } else {
                cost[y][x] = p +
                        Math.min(cost[y - 1][x - 1] + calcCL(greyImg, y, x),
                                cost[y - 1][x] + calcCV(greyImg, y, x));
            }
        });
        setForEachInputParameters();
        return cost;
    }

    private int[][] calcPixelEnergy(BufferedImage greyImg) {
        int height = greyImg.getHeight();
        int width = greyImg.getWidth();
        int[][] pEnergy = new int[height][width];
        setForEachParameters(width, height);
        forEach((y, x) -> {
            int p = new Color(greyImg.getRGB(x, y)).getBlue();
            if (x < width - 1) {
                pEnergy[y][x] = Math.abs(p - new Color(greyImg.getRGB(x + 1, y)).getBlue());
            } else {
                pEnergy[y][x] = Math.abs(p - new Color(greyImg.getRGB(x - 1, y)).getBlue());
            }
        });
        setForEachInputParameters();
        return pEnergy;
    }

    private int calcCL(BufferedImage greyImg, int i, int j) {
        int a = 0;
        int b = 0;
        int c = 0;
        if (j < greyImg.getWidth() - 1) {
            a = new Color(greyImg.getRGB(j + 1, i)).getBlue();
        }

        if (j > 0) {
            b = new Color(greyImg.getRGB(j - 1, i)).getBlue();
        }

        if (i > 0) {
            c = b = new Color(greyImg.getRGB(j, i - 1)).getBlue();
        }

        return Math.abs(a - b) + Math.abs(c - b);
    }

    private int calcCV(BufferedImage greyImg, int i, int j) {
        int a = 0;
        int b = 0;
        if (j < greyImg.getWidth() - 1) {
            a = new Color(greyImg.getRGB(j + 1, i)).getBlue();
        }

        if (j > 0) {
            b = new Color(greyImg.getRGB(j - 1, i)).getBlue();
        }

        return Math.abs(a - b);
    }

    private int calcCR(BufferedImage greyImg, int i, int j) {
        int a = 0;
        int b = 0;
        int c = 0;
        if (j < greyImg.getWidth() - 1) {
            a = new Color(greyImg.getRGB(j + 1, i)).getBlue();
        }

        if (j > 0) {
            b = new Color(greyImg.getRGB(j - 1, i)).getBlue();
        }

        if (i > 0) {
            c = b = new Color(greyImg.getRGB(j, i - 1)).getBlue();
        }

        return Math.abs(a - b) + Math.abs(c - a);
    }

    private int getMinIndex(long[] inArray) {
        int minIndex = 0;
        long minValue = inArray[minIndex];
        for (int i = 1; i < inArray.length; i++) {
            if (inArray[i] < minValue) {
                minValue = inArray[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private int[] getSeam(long[][] cost, int[][] pixelEnergy, BufferedImage greyImg, int minIndex) {
        int[] seam = new int[inHeight];
        int j = minIndex;
        seam[inHeight - 1] = j;
        for (int i = inHeight - 1; i > 0; i--) {
            if (cost[i][j] == pixelEnergy[i][j] + cost[i - 1][j] + calcCV(greyImg, i, j)) {
                seam[i - 1] = j;
            } else if (j > 0 && cost[i][j] == pixelEnergy[i][j] + cost[i - 1][j - 1] + calcCL(greyImg, i, j)) {
                seam[i - 1] = j - 1;
            } else {
                seam[i - 1] = j + 1;
            }
            j = seam[i - 1];
        }
        return seam;
    }

    private BufferedImage removeSeamFrom(BufferedImage img, int[] seam) {
        int newWidth = img.getWidth() - 1;
        int height = img.getHeight();
        BufferedImage outImg = newEmptyImage(newWidth, height);
        setForEachParameters(newWidth, height);
        forEach((y, x) -> {
            if (x < seam[y]) {
                // left to the seam. copy pixel.
                outImg.setRGB(x, y, img.getRGB(x, y));
            } else {
                // right to the seam. copy shift pixel.
                outImg.setRGB(x, y, img.getRGB(x + 1, y));
                // update indices
//                int[] indices = {x + 1, y};
                stepCounter[y][x]++;
                if (x == seam[y]) {
                    System.out.println("SEAM!");
                }
            }
        });
        setForEachInputParameters();
        return outImg;
    }

    private void updatePixelEnergyFor(int[] seam, int[][] pixelEnergy) {
        for (int i = 0; i < seam.length; i++) {
            int j = seam[i];
            pixelEnergy[i][j] = 10000;//Integer.MAX_VALUE;
        }
    }
}
