package edu.cg;

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
			int red = r*c.getRed() / max;
			int green = g*c.getGreen() / max;
			int blue = b*c.getBlue() / max;
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
			int red = r*c.getRed();
			int green = g*c.getGreen();
			int blue = b*c.getBlue();
			int greyColor = (red + green + blue) / amount;
			Color color = new Color(greyColor, greyColor, greyColor);
			ans.setRGB(x, y, color.getRGB());
		});

		logger.log("Changing Greyscale done!");

		return ans;
	}

	/**
	 * gradient magnitude = sqrt((dx^2 + dy^2) / 2.0)
	 * dx = current pixel - next horizontal pixel
	 * dy = current pixel - next vertical pixel
	 * in borders: Take the previous pixel instead of the next one
	 * If the image dimensions are too small, throw an appropriate exception.
	 * The gradient magnitude is based on the grey scaled image by using the given RGB weights.
	 * @return the resulted image
	 */
    public BufferedImage gradientMagnitude() {
        logger.log("Preparing for Gradient magnitude changing...");

        // check for size:
        if (inWidth <=1 || inHeight <=1) {
            // TODO throw exeption
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

        logger.log("Changing Gradient magnitude done!");

        return ans;
    }
	
	public BufferedImage nearestNeighbor() {
		//TODO: Implement this method, remove the exception.
		throw new UnimplementedMethodException("nearestNeighbor");
	}
	
	public BufferedImage bilinear() {
		//TODO: Implement this method, remove the exception.
		throw new UnimplementedMethodException("bilinear");
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
