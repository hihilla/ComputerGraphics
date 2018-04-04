package edu.cg;

import com.oracle.tools.packager.Log;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessor extends FunctioalForEachLoops {

    //MARK: Fields
    public final Logger logger;
    public final BufferedImage workingImage;
    public final RGBWeights rgbWeights;
    public final int inWidth;
    public final int inHeight;
    public final int workingImageType;
    public final int outWidth;
    public final int outHeight;

    //MARK: Constructors
    public ImageProcessor(Logger logger, BufferedImage workingImage,
                          RGBWeights rgbWeights, int outWidth, int outHeight) {
        super(); //Initializing for each loops...

        this.logger = logger;
        this.workingImage = workingImage;
        this.rgbWeights = rgbWeights;
        inWidth = workingImage.getWidth();
        inHeight = workingImage.getHeight();
        workingImageType = workingImage.getType();
        this.outWidth = outWidth;
        this.outHeight = outHeight;
        setForEachInputParameters();
    }

    public ImageProcessor(Logger logger,
                          BufferedImage workingImage,
                          RGBWeights rgbWeights) {
        this(logger, workingImage, rgbWeights,
                workingImage.getWidth(), workingImage.getHeight());
    }

    //MARK: Change picture hue - example
    public BufferedImage changeHue() {
        logger.log("Preparing for hue changing...");

        int r = rgbWeights.redWeight;
        int g = rgbWeights.greenWeight;
        int b = rgbWeights.blueWeight;
        int max = rgbWeights.maxWeight;

        BufferedImage ans = newEmptyInputSizedImage();

        forEach((y, x) -> {
            Color c = new Color(workingImage.getRGB(x, y));
            int red = r * c.getRed() / max;
            int green = g * c.getGreen() / max;
            int blue = b * c.getBlue() / max;
            Color color = new Color(red, green, blue);
            ans.setRGB(x, y, color.getRGB());
        });

        logger.log("Changing hue done!");

        return ans;
    }


    //MARK: Unimplemented methods
    public BufferedImage greyscale() {
        logger.log("creates a greyscale image.");

        int r = rgbWeights.redWeight;
        int g = rgbWeights.greenWeight;
        int b = rgbWeights.blueWeight;
        int amount = rgbWeights.weightsAmount;

        BufferedImage ans = newEmptyInputSizedImage();

        forEach((y, x) -> {
            Color c = new Color(workingImage.getRGB(x, y));
            int red = r * c.getRed();
            int green = g * c.getGreen();
            int blue = b * c.getBlue();
            int greyColor = (int) ((red + green + blue) / (double) amount);
            Color color = new Color(greyColor, greyColor, greyColor);
            ans.setRGB(x, y, color.getRGB());
        });

        return ans;
    }

    public BufferedImage greyscale(BufferedImage img) {
        int r = rgbWeights.redWeight;
        int g = rgbWeights.greenWeight;
        int b = rgbWeights.blueWeight;
        int amount = rgbWeights.weightsAmount;

        BufferedImage ans = newEmptyImage(img.getWidth(), img.getHeight());
        setForEachParameters(img.getWidth(), img.getHeight());
        forEach((y, x) -> {
            Color c = new Color(img.getRGB(x, y));
            int red = r * c.getRed();
            int green = g * c.getGreen();
            int blue = b * c.getBlue();
            int greyColor = (int) ((red + green + blue) / (double) amount);
            Color color = new Color(greyColor, greyColor, greyColor);
            ans.setRGB(x, y, color.getRGB());
        });
        setForEachInputParameters();
        return ans;
    }

    /**
     * gradient magnitude = sqrt((dx^2 + dy^2) / 2.0)
     * dx = current pixel - next horizontal pixel
     * dy = current pixel - next vertical pixel
     * in borders: Take the previous pixel instead of the next one
     * If the image dimensions are too small, throw an appropriate exception.
     * The gradient magnitude is based on the grey scaled image by using the given RGB weights.
     *
     * @return the resulted image
     */
    public BufferedImage gradientMagnitude() {
        logger.log("Preparing for Gradient magnitude changing...");

        // check for size:
        if (inWidth <= 1 || inHeight <= 1) {
            logger.log("Image is too small for calculating gradient magnitude.");
            throw new RuntimeException("Image is too small for calculating gradient magnitude.");
        }

        BufferedImage greyImage = greyscale();
        BufferedImage ans = newEmptyInputSizedImage();

        forEach((y, x) -> {
            Color current = new Color(greyImage.getRGB(x, y));
            Color nextHorizontal;
            Color nextVertical;

            if (x + 1 == inWidth) {
                // next horizontal is out of bound
                nextHorizontal = new Color(greyImage.getRGB(x - 1, y));
            } else {
                nextHorizontal = new Color(greyImage.getRGB(x + 1, y));
            }

            if (y + 1 == inHeight) {
                // next vertical is out of bound
                nextVertical = new Color(greyImage.getRGB(x, y - 1));
            } else {
                nextVertical = new Color(greyImage.getRGB(x, y + 1));
            }

            // since its greyscale, all components of color has same value
            int dx = current.getBlue() - nextHorizontal.getBlue();
            int dy = current.getBlue() - nextVertical.getBlue();

            int gradientMagnitude = (int) Math.sqrt((dx * dx + dy * dy) / 2.0);
            Color color = new Color(gradientMagnitude, gradientMagnitude, gradientMagnitude);
            ans.setRGB(x, y, color.getRGB());
        });

        return ans;
    }

    /**
     * Each new sized image pixel's color will be taken from the nearest appropriate pixel in the original image.
     * Each image has different dimensions.
     *
     * @return scaled image
     */
    public BufferedImage nearestNeighbor() {
        logger.log("Preparing for Nearest neighbor changing...");

        BufferedImage ans = newEmptyOutputSizedImage();
        double xFactor = (double) outWidth / inWidth;
        double yFactor = (double) outHeight / inHeight;

        setForEachOutputParameters();
        forEach((y, x) -> {
            int inX = (int) (x / xFactor);
            int inY = (int) (y / yFactor);
            ans.setRGB(x, y, workingImage.getRGB(inX, inY));
        });
        setForEachInputParameters();

        return ans;
    }

    /**
     * Each new sized image pixel's color will be calculated as a bilinear interpolation by using the four closest
     * surrounding pixels of the original image. Each image has different dimensions.
     *
     * @return scaled image
     */
    public BufferedImage bilinear() {
        logger.log("Preparing for Bilinear interpolation changing...");

        BufferedImage ans = newEmptyOutputSizedImage();
        double xFactor = (double) outWidth / inWidth;
        double yFactor = (double) outHeight / inHeight;

        setForEachOutputParameters();
        forEach((y, x) -> {
            double origX = x / xFactor;
            double origY = y / yFactor;
            int x1 = clipXtoInWidth((int) origX);
            int x2 = clipXtoInWidth((int) Math.ceil(origX));
            int y1 = clipYtoIHeight((int) origY);
            int y2 = clipYtoIHeight((int) Math.ceil(origY));

            Color color = getNewColor(origY, origX, x1, y1, x2, y2);

            ans.setRGB(x, y, color.getRGB());
        });
        setForEachInputParameters();

        return ans;
    }

    private int clipXtoInWidth(int x) {
        return (x > inWidth - 1) ? inWidth - 1 : (x < 0 ? 0 : x);
    }

    private int clipYtoIHeight(int y) {
        return (y > inHeight - 1) ? inHeight - 1 : (y < 0 ? 0 : y);
    }

    private int clipColorComponentToBounds(int c) {
        // unsigned byte:
        return c & 0xFF;
    }

    /**
     * The bilinear calculation of the color at pixel (x,y), with closest coordinates to it,
     * using the bilinear algorithm from wikipedia
     *
     * @param x,y         - coordinate at original image
     * @param x1,y1,x2,y2 - closest coordinates to (x,y) at new image
     * @return the color calculated by bilinear interpolation
     */
    private Color getNewColor(double y, double x, int x1, int y1, int x2, int y2) {
        int r = getColorComponent(y, x, x1, y1, x2, y2, 1);
        int g = getColorComponent(y, x, x1, y1, x2, y2, 2);
        int b = getColorComponent(y, x, x1, y1, x2, y2, 3);
        return new Color(r, g, b);
    }

    /**
     * The bilinear calculation of the color at pixel (x,y), with closest coordinates to it,
     * using the bilinear algorithm from wikipedia for each color component separately.
     *
     * @param x,y         - coordinate at original image
     * @param x1,y1,x2,y2 - closest coordinates to (x,y) at new image
     * @param i           - the color components index (1 for red, 2 for green, 3 for blue)
     * @return the color component calculated by bilinear interpolation
     */
    private int getColorComponent(double y, double x, int x1, int y1, int x2, int y2, int i) {
        int q11 = new Color(workingImage.getRGB(x1, y1)).getRGB();
        int q12 = new Color(workingImage.getRGB(x1, y2)).getRGB();
        int q21 = new Color(workingImage.getRGB(x2, y1)).getRGB();
        int q22 = new Color(workingImage.getRGB(x2, y2)).getRGB();

        switch (i) {
            case 1:
                q11 =  new Color(workingImage.getRGB(x1, y1)).getRed();
                q12 =  new Color(workingImage.getRGB(x1, y2)).getRed();
                q21 =  new Color(workingImage.getRGB(x2, y1)).getRed();
                q22 =  new Color(workingImage.getRGB(x2, y2)).getRed();
                break;
            case 2:
                q11 =  new Color(workingImage.getRGB(x1, y1)).getGreen();
                q12 =  new Color(workingImage.getRGB(x1, y2)).getGreen();
                q21 =  new Color(workingImage.getRGB(x2, y1)).getGreen();
                q22 =  new Color(workingImage.getRGB(x2, y2)).getGreen();
                break;
            case 3:
                q11 =  new Color(workingImage.getRGB(x1, y1)).getBlue();
                q12 =  new Color(workingImage.getRGB(x1, y2)).getBlue();
                q21 =  new Color(workingImage.getRGB(x2, y1)).getBlue();
                q22 =  new Color(workingImage.getRGB(x2, y2)).getBlue();
                break;
        }

        if (x1 == x2) {
            x2 = x1 + 1;
        }

        if (y1 == y2) {
            y2 = y1 + 1;
        }

        double R1 = ((x2 - x) / (x2 - x1)) * q11 + ((x - x1) / (x2 - x1)) * q21;
        double R2 = ((x2 - x) / (x2 - x1)) * q12 + ((x - x1) / (x2 - x1)) * q22;
        int colorComponent = (int) (((y2 - y) / (y2 - y1)) * R1 + ((y - y1) / (y2 - y1)) * R2);

        return clipColorComponentToBounds(colorComponent);
    }

    //MARK: Utilities
    public final void setForEachInputParameters() {
        setForEachParameters(inWidth, inHeight);
    }

    public final void setForEachOutputParameters() {
        setForEachParameters(outWidth, outHeight);
    }

    public final BufferedImage newEmptyInputSizedImage() {
        return newEmptyImage(inWidth, inHeight);
    }

    public final BufferedImage newEmptyOutputSizedImage() {
        return newEmptyImage(outWidth, outHeight);
    }

    public final BufferedImage newEmptyImage(int width, int height) {
        return new BufferedImage(width, height, workingImageType);
    }

    public final BufferedImage duplicateWorkingImage() {
        BufferedImage output = newEmptyInputSizedImage();

        forEach((y, x) ->
                output.setRGB(x, y, workingImage.getRGB(x, y))
        );

        return output;
    }
}
