// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL;

public class RandomTileRenderer extends TileRendererBase
{
    private boolean tileRectSet;
    
    public RandomTileRenderer() {
        this.tileRectSet = false;
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
            default: {
                throw new IllegalArgumentException("Invalid pname: " + n);
            }
        }
    }
    
    public void setTileRect(final int currentTileXPos, final int currentTileYPos, final int currentTileWidth, final int currentTileHeight) throws IllegalStateException, IllegalArgumentException {
        if (0 > currentTileXPos || 0 > currentTileYPos) {
            throw new IllegalArgumentException("Tile pos must be >= 0/0");
        }
        if (0 >= currentTileWidth || 0 >= currentTileHeight) {
            throw new IllegalArgumentException("Tile size must be > 0x0");
        }
        this.currentTileXPos = currentTileXPos;
        this.currentTileYPos = currentTileYPos;
        this.currentTileWidth = currentTileWidth;
        this.currentTileHeight = currentTileHeight;
        this.tileRectSet = true;
    }
    
    @Override
    public final boolean isSetup() {
        return 0 < this.imageSize.getWidth() && 0 < this.imageSize.getHeight() && this.tileRectSet;
    }
    
    @Override
    public final boolean eot() {
        return false;
    }
    
    @Override
    public final void reset() {
    }
    
    @Override
    public final void beginTile(final GL gl) throws IllegalStateException, GLException {
        if (0 >= this.imageSize.getWidth() || 0 >= this.imageSize.getHeight()) {
            throw new IllegalStateException("Image size has not been set");
        }
        if (!this.tileRectSet) {
            throw new IllegalStateException("tileRect has not been set");
        }
        this.validateGL(gl);
        gl.glViewport(0, 0, this.currentTileWidth, this.currentTileHeight);
        if (RandomTileRenderer.DEBUG) {
            System.err.println("TileRenderer.begin.X: " + this.toString());
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
        if (RandomTileRenderer.DEBUG) {
            System.err.println("TileRenderer.end.0: readBuffer 0x" + Integer.toHexString(defaultReadBuffer) + ", " + this.toString());
        }
        final int[] array = { 0 };
        if (this.tileBuffer != null) {
            final GLPixelBuffer.GLPixelAttributes pixelAttributes = this.tileBuffer.pixelAttributes;
            final int currentTileWidth = this.currentTileWidth;
            final int currentTileHeight = this.currentTileHeight;
            final int sizeof = GLBuffers.sizeof(gl, array, pixelAttributes.pfmt.comp.bytesPerPixel(), currentTileWidth, currentTileHeight, 1, true);
            this.tileBuffer.clear();
            if (this.tileBuffer.requiresNewBuffer(gl, currentTileWidth, currentTileHeight, sizeof)) {
                throw new IndexOutOfBoundsException("Required " + sizeof + " bytes of buffer, only had " + this.tileBuffer);
            }
            gl.glReadPixels(0, 0, currentTileWidth, currentTileHeight, pixelAttributes.format, pixelAttributes.type, this.tileBuffer.buffer);
            gl.glFlush();
            this.tileBuffer.position(sizeof);
            this.tileBuffer.flip();
        }
        if (this.imageBuffer != null) {
            final GLPixelBuffer.GLPixelAttributes pixelAttributes2 = this.imageBuffer.pixelAttributes;
            final int currentTileWidth2 = this.currentTileWidth;
            final int currentTileHeight2 = this.currentTileHeight;
            final int width = this.imageSize.getWidth();
            this.psm.setPackRowLength(gl2ES3, width);
            final int sizeof2 = GLBuffers.sizeof(gl, array, pixelAttributes2.pfmt.comp.bytesPerPixel(), currentTileWidth2, currentTileHeight2, 1, true);
            final int n = (this.currentTileXPos + this.currentTileYPos * width) * pixelAttributes2.pfmt.comp.bytesPerPixel();
            final int n2 = n + sizeof2;
            this.imageBuffer.clear();
            if (this.imageBuffer.requiresNewBuffer(gl, currentTileWidth2, currentTileHeight2, sizeof2)) {
                throw new IndexOutOfBoundsException("Required " + n2 + " bytes of buffer, only had " + this.imageBuffer);
            }
            this.imageBuffer.position(n);
            gl.glReadPixels(0, 0, currentTileWidth2, currentTileHeight2, pixelAttributes2.format, pixelAttributes2.type, this.imageBuffer.buffer);
            gl.glFlush();
            this.imageBuffer.position(n2);
            this.imageBuffer.flip();
        }
        this.psm.restore(gl);
        this.beginCalled = false;
    }
    
    public void display(final int n, final int n2, final int n3, final int n4) throws IllegalStateException {
        this.setTileRect(n, n2, n3, n4);
        this.display();
    }
}
