// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.awt;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.GraphicsEnvironment;
import java.awt.image.WritableRaster;
import java.awt.image.BufferedImage;

public class ImageUtil
{
    public static void flipImageVertically(final BufferedImage bufferedImage) {
        final WritableRaster raster = bufferedImage.getRaster();
        Object dataElements = null;
        Object dataElements2 = null;
        for (int i = 0; i < bufferedImage.getHeight() / 2; ++i) {
            dataElements = raster.getDataElements(0, i, bufferedImage.getWidth(), 1, dataElements);
            dataElements2 = raster.getDataElements(0, bufferedImage.getHeight() - i - 1, bufferedImage.getWidth(), 1, dataElements2);
            raster.setDataElements(0, i, bufferedImage.getWidth(), 1, dataElements2);
            raster.setDataElements(0, bufferedImage.getHeight() - i - 1, bufferedImage.getWidth(), 1, dataElements);
        }
    }
    
    public static BufferedImage createCompatibleImage(final int n, final int n2) {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(n, n2);
    }
    
    public static BufferedImage createThumbnail(final BufferedImage bufferedImage, final int n) {
        if (n > bufferedImage.getWidth()) {
            throw new IllegalArgumentException("Thumbnail width must be greater than image width");
        }
        if (n == bufferedImage.getWidth()) {
            return bufferedImage;
        }
        final float n2 = bufferedImage.getWidth() / bufferedImage.getHeight();
        int i = bufferedImage.getWidth();
        BufferedImage bufferedImage2 = bufferedImage;
        do {
            i /= 2;
            if (i < n) {
                i = n;
            }
            final BufferedImage compatibleImage = createCompatibleImage(i, (int)(i / n2));
            final Graphics2D graphics = compatibleImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(bufferedImage2, 0, 0, compatibleImage.getWidth(), compatibleImage.getHeight(), null);
            graphics.dispose();
            bufferedImage2 = compatibleImage;
        } while (i != n);
        return bufferedImage2;
    }
}
