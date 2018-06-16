// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.nativewindow.util.Dimension;

public class TileRenderer extends TileRendererBase
{
    public static final int TR_IMAGE_CLIPPING_WIDTH = 7;
    public static final int TR_IMAGE_CLIPPING_HEIGHT = 8;
    public static final int TR_TILE_WIDTH = 9;
    public static final int TR_TILE_HEIGHT = 10;
    public static final int TR_TILE_BORDER = 11;
    public static final int TR_TILE_X_OFFSET = 12;
    public static final int TR_TILE_Y_OFFSET = 13;
    public static final int TR_ROWS = 14;
    public static final int TR_COLUMNS = 15;
    public static final int TR_CURRENT_TILE_NUM = 16;
    public static final int TR_CURRENT_ROW = 17;
    public static final int TR_CURRENT_COLUMN = 18;
    public static final int TR_ROW_ORDER = 19;
    public static final int TR_TOP_TO_BOTTOM = 20;
    public static final int TR_BOTTOM_TO_TOP = 21;
    private static final int DEFAULT_TILE_WIDTH = 256;
    private static final int DEFAULT_TILE_HEIGHT = 256;
    private static final int DEFAULT_TILE_BORDER = 0;
    private final Dimension tileSize;
    private final Dimension tileSizeNB;
    private boolean isInit;
    private Dimension imageClippingDim;
    private int tileBorder;
    private int rowOrder;
    private int rows;
    private int columns;
    private int currentTile;
    private int currentRow;
    private int currentColumn;
    private int offsetX;
    private int offsetY;
    
    @Override
    protected StringBuilder tileDetails(final StringBuilder sb) {
        sb.append("# " + this.currentTile + ": [" + this.currentColumn + "][" + this.currentRow + "] / " + this.columns + "x" + this.rows + ", ").append("rowOrder " + this.rowOrder + ", offset/size " + this.offsetX + "/" + this.offsetY + " " + this.tileSize.getWidth() + "x" + this.tileSize.getHeight() + " brd " + this.tileBorder + ", ");
        return super.tileDetails(sb);
    }
    
    public TileRenderer() {
        this.tileSize = new Dimension(256, 256);
        this.tileSizeNB = new Dimension(256, 256);
        this.isInit = false;
        this.imageClippingDim = null;
        this.tileBorder = 0;
        this.rowOrder = 21;
        this.currentTile = 0;
    }
    
    @Override
    public final void setImageSize(final int n, final int n2) {
        super.setImageSize(n, n2);
        this.reset();
    }
    
    public final void clipImageSize(final int n, final int n2) {
        if (null == this.imageClippingDim) {
            this.imageClippingDim = new Dimension(n, n2);
        }
        else {
            this.imageClippingDim.set(n, n2);
        }
        this.reset();
    }
    
    public final DimensionImmutable getClippedImageSize() {
        if (null != this.imageClippingDim) {
            return new Dimension(Math.min(this.imageClippingDim.getWidth(), this.imageSize.getWidth()), Math.min(this.imageClippingDim.getHeight(), this.imageSize.getHeight()));
        }
        return this.imageSize;
    }
    
    public final void setTileSize(final int n, final int n2, final int tileBorder) {
        if (0 > tileBorder) {
            throw new IllegalArgumentException("Tile border must be >= 0");
        }
        if (2 * tileBorder >= n || 2 * tileBorder >= n2) {
            throw new IllegalArgumentException("Tile size must be > 0x0 minus 2*border");
        }
        this.tileBorder = tileBorder;
        this.tileSize.set(n, n2);
        this.tileSizeNB.set(n - 2 * tileBorder, n2 - 2 * tileBorder);
        this.reset();
    }
    
    public void setTileOffset(final int offsetX, final int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    @Override
    public final void reset() {
        final DimensionImmutable clippedImageSize = this.getClippedImageSize();
        this.columns = (clippedImageSize.getWidth() + this.tileSizeNB.getWidth() - 1) / this.tileSizeNB.getWidth();
        this.rows = (clippedImageSize.getHeight() + this.tileSizeNB.getHeight() - 1) / this.tileSizeNB.getHeight();
        this.currentRow = 0;
        this.currentColumn = 0;
        this.currentTile = 0;
        this.currentTileXPos = 0;
        this.currentTileYPos = 0;
        this.currentTileWidth = 0;
        this.currentTileHeight = 0;
        assert this.columns >= 0;
        assert this.rows >= 0;
        this.beginCalled = false;
        this.isInit = true;
    }
    
    final int getCurrentTile() {
        return this.currentTile;
    }
    
    @Override
    public final int getParam(final int n) {
        switch (n) {
            case 1: {
                return this.imageSize.getWidth();
            }
            case 2: {
                return this.imageSize.getHeight();
            }
            case 3: {
                return this.currentTileXPos;
            }
            case 4: {
                return this.currentTileYPos;
            }
            case 5: {
                return this.currentTileWidth;
            }
            case 6: {
                return this.currentTileHeight;
            }
            case 7: {
                return (null != this.imageClippingDim) ? this.imageClippingDim.getWidth() : 0;
            }
            case 8: {
                return (null != this.imageClippingDim) ? this.imageClippingDim.getHeight() : 0;
            }
            case 9: {
                return this.tileSize.getWidth();
            }
            case 10: {
                return this.tileSize.getHeight();
            }
            case 11: {
                return this.tileBorder;
            }
            case 12: {
                return this.offsetX;
            }
            case 13: {
                return this.offsetY;
            }
            case 14: {
                return this.rows;
            }
            case 15: {
                return this.columns;
            }
            case 16: {
                return this.currentTile;
            }
            case 17: {
                return this.currentRow;
            }
            case 18: {
                return this.currentColumn;
            }
            case 19: {
                return this.rowOrder;
            }
            default: {
                throw new IllegalArgumentException("Invalid pname: " + n);
            }
        }
    }
    
    public final void setRowOrder(final int rowOrder) {
        if (rowOrder == 20 || rowOrder == 21) {
            this.rowOrder = rowOrder;
            return;
        }
        throw new IllegalArgumentException("Must pass TR_TOP_TO_BOTTOM or TR_BOTTOM_TO_TOP");
    }
    
    @Override
    public final boolean isSetup() {
        return 0 < this.imageSize.getWidth() && 0 < this.imageSize.getHeight();
    }
    
    @Override
    public final boolean eot() {
        if (!this.isInit) {
            this.reset();
        }
        return 0 > this.currentTile || 0 >= this.columns * this.rows;
    }
    
    @Override
    public final void beginTile(final GL gl) throws IllegalStateException, GLException {
        if (!this.isSetup()) {
            throw new IllegalStateException("Image size has not been set: " + this);
        }
        if (this.eot()) {
            throw new IllegalStateException("EOT reached: " + this);
        }
        this.validateGL(gl);
        if (this.rowOrder == 21) {
            this.currentRow = this.currentTile / this.columns;
            this.currentColumn = this.currentTile % this.columns;
        }
        else {
            this.currentRow = this.rows - this.currentTile / this.columns - 1;
            this.currentColumn = this.currentTile % this.columns;
        }
        assert this.currentRow < this.rows;
        assert this.currentColumn < this.columns;
        final int tileBorder = this.tileBorder;
        final DimensionImmutable clippedImageSize = this.getClippedImageSize();
        int height;
        if (this.currentRow < this.rows - 1) {
            height = this.tileSize.getHeight();
        }
        else {
            height = clippedImageSize.getHeight() - (this.rows - 1) * this.tileSizeNB.getHeight() + 2 * tileBorder;
        }
        int width;
        if (this.currentColumn < this.columns - 1) {
            width = this.tileSize.getWidth();
        }
        else {
            width = clippedImageSize.getWidth() - (this.columns - 1) * this.tileSizeNB.getWidth() + 2 * tileBorder;
        }
        this.currentTileXPos = this.currentColumn * this.tileSizeNB.getWidth() + this.offsetX;
        this.currentTileYPos = this.currentRow * this.tileSizeNB.getHeight() + this.offsetY;
        gl.glViewport(0, 0, this.currentTileWidth = width, this.currentTileHeight = height);
        if (TileRenderer.DEBUG) {
            System.err.println("TileRenderer.begin: " + this.toString());
        }
        this.beginCalled = true;
    }
    
    @Override
    public void endTile(final GL gl) throws IllegalStateException, GLException {
        if (!this.beginCalled) {
            throw new IllegalStateException("beginTile(..) has not been called");
        }
        this.validateGL(gl);
        gl.glFlush();
        this.psm.setPackAlignment(gl, 1);
        GL2ES3 gl2ES3;
        int defaultReadBuffer;
        if (gl.isGL2ES3()) {
            gl2ES3 = gl.getGL2ES3();
            defaultReadBuffer = gl2ES3.getDefaultReadBuffer();
            gl2ES3.glReadBuffer(defaultReadBuffer);
        }
        else {
            gl2ES3 = null;
            defaultReadBuffer = 0;
        }
        if (TileRenderer.DEBUG) {
            System.err.println("TileRenderer.end.0: readBuffer 0x" + Integer.toHexString(defaultReadBuffer) + ", " + this.toString());
        }
        final int[] array = { 0 };
        if (this.tileBuffer != null) {
            final GLPixelBuffer.GLPixelAttributes pixelAttributes = this.tileBuffer.pixelAttributes;
            final int tileBorder = this.tileBorder;
            final int tileBorder2 = this.tileBorder;
            final int width = this.tileSizeNB.getWidth();
            final int height = this.tileSizeNB.getHeight();
            final int sizeof = GLBuffers.sizeof(gl, array, pixelAttributes.pfmt.comp.bytesPerPixel(), width, height, 1, true);
            this.tileBuffer.clear();
            if (this.tileBuffer.requiresNewBuffer(gl, width, height, sizeof)) {
                throw new IndexOutOfBoundsException("Required " + sizeof + " bytes of buffer, only had " + this.tileBuffer);
            }
            gl.glReadPixels(tileBorder, tileBorder2, width, height, pixelAttributes.format, pixelAttributes.type, this.tileBuffer.buffer);
            gl.glFlush();
            this.tileBuffer.position(sizeof);
            this.tileBuffer.flip();
        }
        if (this.imageBuffer != null) {
            final GLPixelBuffer.GLPixelAttributes pixelAttributes2 = this.imageBuffer.pixelAttributes;
            final int tileBorder3 = this.tileBorder;
            final int tileBorder4 = this.tileBorder;
            final int n = this.currentTileWidth - 2 * this.tileBorder;
            final int n2 = this.currentTileHeight - 2 * this.tileBorder;
            final int width2 = this.imageSize.getWidth();
            this.psm.setPackRowLength(gl2ES3, width2);
            final int sizeof2 = GLBuffers.sizeof(gl, array, pixelAttributes2.pfmt.comp.bytesPerPixel(), n, n2, 1, true);
            final int n3 = (this.currentColumn * this.tileSizeNB.getWidth() + this.currentRow * this.tileSizeNB.getHeight() * width2) * pixelAttributes2.pfmt.comp.bytesPerPixel();
            final int n4 = n3 + sizeof2;
            this.imageBuffer.clear();
            if (this.imageBuffer.requiresNewBuffer(gl, n, n2, sizeof2)) {
                throw new IndexOutOfBoundsException("Required " + n4 + " bytes of buffer, only had " + this.imageBuffer);
            }
            this.imageBuffer.position(n3);
            gl.glReadPixels(tileBorder3, tileBorder4, n, n2, pixelAttributes2.format, pixelAttributes2.type, this.imageBuffer.buffer);
            gl.glFlush();
            this.imageBuffer.position(n4);
            this.imageBuffer.flip();
        }
        this.psm.restore(gl);
        this.beginCalled = false;
        ++this.currentTile;
        if (this.currentTile >= this.rows * this.columns) {
            this.currentTile = -1;
        }
    }
}
