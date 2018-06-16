// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.awt;

import jogamp.opengl.Debug;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.Shape;
import com.jogamp.nativewindow.util.DimensionImmutable;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.util.Arrays;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.File;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.GLPixelBuffer;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import com.jogamp.opengl.GLEventListener;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.jogamp.opengl.util.awt.AWTGLPixelBuffer;
import com.jogamp.opengl.util.TileRenderer;

public class AWTTilePainter
{
    private static final boolean DEBUG_TILES;
    public final TileRenderer renderer;
    public final int componentCount;
    public final double scaleMatX;
    public final double scaleMatY;
    public final int customTileWidth;
    public final int customTileHeight;
    public final int customNumSamples;
    public final boolean verbose;
    public boolean flipVertical;
    public boolean originBottomLeft;
    private AWTGLPixelBuffer tBuffer;
    private BufferedImage vFlipImage;
    private Graphics2D g2d;
    private AffineTransform saveAT;
    private int scaledYOffset;
    final GLEventListener preTileGLEL;
    static int _counter;
    final GLEventListener postTileGLEL;
    
    public static void dumpHintsAndScale(final Graphics2D graphics2D) {
        final Set<Map.Entry<Object, Object>> entrySet = graphics2D.getRenderingHints().entrySet();
        int n = 0;
        for (final Map.Entry<Object, Object> entry : entrySet) {
            System.err.println("Hint[" + n + "]: " + entry.getKey() + " -> " + entry.getValue());
            ++n;
        }
        final AffineTransform transform = graphics2D.getTransform();
        if (null != transform) {
            System.err.println(" type " + transform.getType());
            System.err.println(" scale " + transform.getScaleX() + " x " + transform.getScaleY());
            System.err.println(" move " + transform.getTranslateX() + " x " + transform.getTranslateY());
            System.err.println(" mat  " + transform);
        }
        else {
            System.err.println(" null transform");
        }
    }
    
    public int getNumSamples(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        if (0 > this.customNumSamples) {
            return 0;
        }
        if (0 >= this.customNumSamples) {
            return glCapabilitiesImmutable.getNumSamples();
        }
        if (!glCapabilitiesImmutable.getGLProfile().isGL2ES3()) {
            return 0;
        }
        return Math.max(glCapabilitiesImmutable.getNumSamples(), this.customNumSamples);
    }
    
    public AWTTilePainter(final TileRenderer renderer, final int componentCount, final double scaleMatX, final double scaleMatY, final int customNumSamples, final int customTileWidth, final int customTileHeight, final boolean verbose) {
        this.tBuffer = null;
        this.vFlipImage = null;
        this.g2d = null;
        this.saveAT = null;
        this.preTileGLEL = new GLEventListener() {
            @Override
            public void init(final GLAutoDrawable glAutoDrawable) {
            }
            
            @Override
            public void dispose(final GLAutoDrawable glAutoDrawable) {
            }
            
            @Override
            public void display(final GLAutoDrawable glAutoDrawable) {
                final GL gl = glAutoDrawable.getGL();
                if (null == AWTTilePainter.this.tBuffer) {
                    final int param = AWTTilePainter.this.renderer.getParam(9);
                    final int param2 = AWTTilePainter.this.renderer.getParam(10);
                    final AWTGLPixelBuffer.AWTGLPixelBufferProvider awtglPixelBufferProvider = new AWTGLPixelBuffer.AWTGLPixelBufferProvider(true);
                    AWTTilePainter.this.tBuffer = awtglPixelBufferProvider.allocate(gl, awtglPixelBufferProvider.getHostPixelComp(gl.getGLProfile(), AWTTilePainter.this.componentCount), awtglPixelBufferProvider.getAttributes(gl, AWTTilePainter.this.componentCount, true), true, param, param2, 1, 0);
                    AWTTilePainter.this.renderer.setTileBuffer(AWTTilePainter.this.tBuffer);
                    if (AWTTilePainter.this.flipVertical) {
                        AWTTilePainter.this.vFlipImage = new BufferedImage(AWTTilePainter.this.tBuffer.width, AWTTilePainter.this.tBuffer.height, AWTTilePainter.this.tBuffer.image.getType());
                    }
                    else {
                        AWTTilePainter.this.vFlipImage = null;
                    }
                }
                if (AWTTilePainter.this.verbose) {
                    System.err.println("XXX tile-pre " + AWTTilePainter.this.renderer);
                }
            }
            
            @Override
            public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
            }
        };
        this.postTileGLEL = new GLEventListener() {
            @Override
            public void init(final GLAutoDrawable glAutoDrawable) {
            }
            
            @Override
            public void dispose(final GLAutoDrawable glAutoDrawable) {
            }
            
            @Override
            public void display(final GLAutoDrawable glAutoDrawable) {
                final DimensionImmutable clippedImageSize = AWTTilePainter.this.renderer.getClippedImageSize();
                final int param = AWTTilePainter.this.renderer.getParam(5);
                final int param2 = AWTTilePainter.this.renderer.getParam(6);
                final int param3 = AWTTilePainter.this.renderer.getParam(4);
                final int param4 = AWTTilePainter.this.renderer.getParam(13);
                final int n = AWTTilePainter.this.originBottomLeft ? 0 : (AWTTilePainter.this.renderer.getParam(10) - param2);
                final int param5 = AWTTilePainter.this.renderer.getParam(3);
                final int n2 = clippedImageSize.getHeight() - (param3 - param4 + param2) + AWTTilePainter.this.scaledYOffset;
                if (AWTTilePainter.DEBUG_TILES) {
                    final String replace = String.format("file_%03d_0_tile_[%02d][%02d]_sz_%03dx%03d_pos0_%03d_%03d_yOff_%03d_pos1_%03d_%03d.png", AWTTilePainter._counter, AWTTilePainter.this.renderer.getParam(18), AWTTilePainter.this.renderer.getParam(17), param, param2, param5, param3, param4, param5, n2).replace(' ', '_');
                    System.err.println("XXX file " + replace);
                    final File file = new File(replace);
                    try {
                        ImageIO.write(AWTTilePainter.this.tBuffer.image, "png", file);
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                BufferedImage bufferedImage;
                if (AWTTilePainter.this.flipVertical) {
                    final BufferedImage image = AWTTilePainter.this.tBuffer.image;
                    bufferedImage = AWTTilePainter.this.vFlipImage;
                    final int[] data = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
                    final int[] data2 = ((DataBufferInt)bufferedImage.getRaster().getDataBuffer()).getData();
                    if (AWTTilePainter.DEBUG_TILES) {
                        Arrays.fill(data2, 85);
                    }
                    final int width = AWTTilePainter.this.tBuffer.width;
                    int n3 = 0;
                    for (int i = (param2 - 1) * AWTTilePainter.this.tBuffer.width; i >= 0; i -= width) {
                        System.arraycopy(data, n3, data2, i, width);
                        n3 += width;
                    }
                }
                else {
                    bufferedImage = AWTTilePainter.this.tBuffer.image;
                }
                if (AWTTilePainter.DEBUG_TILES) {
                    final String replace2 = String.format("file_%03d_1_tile_[%02d][%02d]_sz_%03dx%03d_pos0_%03d_%03d_yOff_%03d_pos1_%03d_%03d.png", AWTTilePainter._counter, AWTTilePainter.this.renderer.getParam(18), AWTTilePainter.this.renderer.getParam(17), param, param2, param5, param3, param4, param5, n2).replace(' ', '_');
                    System.err.println("XXX file " + replace2);
                    final File file2 = new File(replace2);
                    try {
                        ImageIO.write(bufferedImage, "png", file2);
                    }
                    catch (IOException ex2) {
                        ex2.printStackTrace();
                    }
                    ++AWTTilePainter._counter;
                }
                final BufferedImage subimage = bufferedImage.getSubimage(0, n, param, param2);
                final boolean drawImage = AWTTilePainter.this.g2d.drawImage(subimage, param5, n2, null);
                if (AWTTilePainter.this.verbose) {
                    final Shape clip = AWTTilePainter.this.g2d.getClip();
                    System.err.println("XXX tile-post.X tile 0 / " + n + " " + param + "x" + param2 + ", clippedImgSize " + clippedImageSize);
                    System.err.println("XXX tile-post.X pYf " + clippedImageSize.getHeight() + " - ( " + param3 + " - " + param4 + " + " + param2 + " ) " + AWTTilePainter.this.scaledYOffset + " = " + n2);
                    System.err.println("XXX tile-post.X clip " + clip + " + " + param5 + " / [pY " + param3 + ", pYOff " + param4 + ", pYf " + n2 + "] -> " + AWTTilePainter.this.g2d.getClip());
                    AWTTilePainter.this.g2d.setColor(Color.BLACK);
                    AWTTilePainter.this.g2d.drawRect(param5, n2, param, param2);
                    if (null != clip) {
                        final Rectangle bounds = clip.getBounds();
                        AWTTilePainter.this.g2d.setColor(Color.YELLOW);
                        AWTTilePainter.this.g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
                    }
                    System.err.println("XXX tile-post.X " + AWTTilePainter.this.renderer);
                    System.err.println("XXX tile-post.X dst-img " + bufferedImage.getWidth() + "x" + bufferedImage.getHeight());
                    System.err.println("XXX tile-post.X out-img " + subimage.getWidth() + "x" + subimage.getHeight());
                    System.err.println("XXX tile-post.X y-flip " + AWTTilePainter.this.flipVertical + ", originBottomLeft " + AWTTilePainter.this.originBottomLeft + " -> " + param5 + "/" + n2 + ", drawDone " + drawImage);
                }
            }
            
            @Override
            public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
            }
        };
        (this.renderer = renderer).setGLEventListener(this.preTileGLEL, this.postTileGLEL);
        this.componentCount = componentCount;
        this.scaleMatX = scaleMatX;
        this.scaleMatY = scaleMatY;
        this.customNumSamples = customNumSamples;
        this.customTileWidth = customTileWidth;
        this.customTileHeight = customTileHeight;
        this.verbose = verbose;
        this.flipVertical = true;
    }
    
    @Override
    public String toString() {
        return "AWTTilePainter[flipVertical " + this.flipVertical + ", startFromBottom " + this.originBottomLeft + ", " + this.renderer.toString() + "]";
    }
    
    public void setGLOrientation(final boolean flipVertical, final boolean originBottomLeft) {
        this.flipVertical = flipVertical;
        this.originBottomLeft = originBottomLeft;
    }
    
    private static Rectangle2D getClipBounds2D(final Graphics2D graphics2D) {
        final Shape clip = graphics2D.getClip();
        return (null != clip) ? clip.getBounds2D() : null;
    }
    
    private static Rectangle2D clipNegative(final Rectangle2D rectangle2D) {
        if (null == rectangle2D) {
            return null;
        }
        double x = rectangle2D.getX();
        double y = rectangle2D.getY();
        double width = rectangle2D.getWidth();
        double height = rectangle2D.getHeight();
        if (0.0 > x) {
            width += x;
            x = 0.0;
        }
        if (0.0 > y) {
            height += y;
            y = 0.0;
        }
        return new Rectangle2D.Double(x, y, width, height);
    }
    
    public void setupGraphics2DAndClipBounds(final Graphics2D g2d, final int n, final int n2) throws NoninvertibleTransformException {
        this.g2d = g2d;
        this.saveAT = g2d.getTransform();
        if (null == this.saveAT) {
            this.saveAT = new AffineTransform();
        }
        final Rectangle2D clipBounds2D = getClipBounds2D(g2d);
        final Rectangle2D clipNegative = clipNegative(clipBounds2D);
        final Rectangle2D.Double double1 = new Rectangle2D.Double(0.0, 0.0, n, n2);
        final AffineTransform affineTransform = new AffineTransform(this.saveAT);
        affineTransform.scale(this.scaleMatX, this.scaleMatY);
        final AffineTransform inverse = affineTransform.createInverse();
        final Rectangle2D bounds2D = inverse.createTransformedShape(this.saveAT.createTransformedShape(double1)).getBounds2D();
        Rectangle2D bounds2D2;
        if (null == clipNegative) {
            bounds2D2 = (Rectangle2D)bounds2D.clone();
        }
        else {
            bounds2D2 = inverse.createTransformedShape(this.saveAT.createTransformedShape(clipNegative)).getBounds2D();
        }
        final Rectangle bounds = bounds2D2.getBounds();
        final Rectangle bounds2 = bounds2D.getBounds();
        this.renderer.setImageSize(bounds2.width, bounds2.height);
        this.renderer.clipImageSize(bounds.width, bounds.height);
        final int min = Math.min(bounds2.height, bounds.height);
        this.scaledYOffset = bounds.y;
        this.renderer.setTileOffset(bounds.x, bounds2.height - (bounds.y + min));
        g2d.scale(this.scaleMatX, this.scaleMatY);
        if (this.verbose) {
            System.err.println("AWT print.0: image " + double1 + " -> " + bounds2D + " -> " + bounds2);
            System.err.println("AWT print.0: clip  " + clipBounds2D + " -> " + clipNegative + " -> " + bounds2D2 + " -> " + bounds);
            System.err.println("AWT print.0: " + this.renderer);
        }
    }
    
    public void resetGraphics2D() {
        this.g2d.setTransform(this.saveAT);
    }
    
    public void dispose() {
        this.renderer.detachAutoDrawable();
        this.g2d = null;
        if (null != this.tBuffer) {
            this.tBuffer.dispose();
            this.tBuffer = null;
        }
        if (null != this.vFlipImage) {
            this.vFlipImage.flush();
            this.vFlipImage = null;
        }
    }
    
    static {
        DEBUG_TILES = Debug.debug("TileRenderer.PNG");
        AWTTilePainter._counter = 0;
    }
}
