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
    private int maxX;

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
        maxX = inWidth - 1;
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
        BufferedImage outImg = duplicateWorkingImage();
        setForEachParameters(inHeight, numOfSeams);
        forEach((i, y) -> {
            int x = seams[i][y];
            outImg.setRGB(x, y, seamColorRGB);
        });
        setForEachInputParameters();
        return outImg;
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
            // update somewhere that this seam shouldn't be used
            updatePixelEnergyFor(seams[i], pixelEnergy);
        }

        return seams;
    }

    private long[][] calcCostMatrix(BufferedImage greyImg, int[][] pixelEnergy) {
        System.out.println("cost matrix");
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

//        forEach((y, x) -> {
//            long c = cost[y][x];
//            System.out.format("%7d", c);
//            if (x == width - 1) {
//                System.out.println();
//            }
//        });
        setForEachInputParameters();
        return cost;
    }

    private int[][] calcPixelEnergy(BufferedImage greyImg) {
        System.out.println("pixel energy");
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
//        forEach((y, x) -> {
//            long c = pEnergy[y][x];
//            System.out.format("%7d", c);
//            if (x == width - 1) {
//                System.out.println();
//            }
//        });
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
        System.out.println("min index has val " + minValue);
        return minIndex;
    }

    private int[] getSeam(long[][] cost, int[][] pixelEnergy, BufferedImage greyImg, int minIndex) {
        System.out.println("get seam");
        int[] seam = new int[inHeight];
        int j = minIndex;
        seam[inHeight - 1] = j;
        for (int i = inHeight - 1; i > 0; i--) {
            if (cost[i][j] == pixelEnergy[i][j] + cost[i - 1][j] + calcCV(greyImg, i, j)) {
                seam[i] = j;
            } else if (j > 0 && cost[i][j] == pixelEnergy[i][j] + cost[i - 1][j - 1] + calcCL(greyImg, i, j)) {
                seam[i] = j - 1;
            } else {
                seam[i] = j + 1;
            }
            j = seam[i];
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
            }
        });
        setForEachInputParameters();
        return outImg;
    }

    private void updatePixelEnergyFor(int[] seam, int[][] pixelEnergy) {
        System.out.println("update pixel energy");
        for (int i = 0; i < seam.length; i++) {
            int j = seam[i];
            pixelEnergy[i][j] = 10000;//Integer.MAX_VALUE;
        }
    }
}
