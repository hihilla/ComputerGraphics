// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.awt;

import com.jogamp.opengl.GLException;
import java.awt.Graphics2D;
import com.jogamp.opengl.GLDrawable;

public class Overlay
{
    private final GLDrawable drawable;
    private TextureRenderer renderer;
    private boolean contentsLost;
    
    public Overlay(final GLDrawable drawable) {
        this.drawable = drawable;
    }
    
    public Graphics2D createGraphics() {
        this.validateRenderer();
        return this.renderer.createGraphics();
    }
    
    public boolean contentsLost() {
        return this.contentsLost;
    }
    
    public void markDirty(final int n, final int n2, final int n3, final int n4) {
        this.renderer.markDirty(n, n2, n3, n4);
    }
    
    public void drawAll() throws GLException {
        this.beginRendering();
        this.draw(0, 0, this.drawable.getSurfaceWidth(), this.drawable.getSurfaceHeight());
        this.endRendering();
    }
    
    public void beginRendering() throws GLException {
        this.renderer.beginOrthoRendering(this.drawable.getSurfaceWidth(), this.drawable.getSurfaceHeight());
    }
    
    public void endRendering() throws GLException {
        this.renderer.endOrthoRendering();
    }
    
    public void draw(final int n, final int n2, final int n3, final int n4) throws GLException {
        this.draw(n, n2, n, n2, n3, n4);
    }
    
    public void draw(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) throws GLException {
        this.renderer.drawOrthoRect(n, n2, n3, n4, n5, n6);
    }
    
    private void validateRenderer() {
        if (this.renderer == null) {
            this.renderer = new TextureRenderer(this.drawable.getSurfaceWidth(), this.drawable.getSurfaceHeight(), true);
            this.contentsLost = true;
        }
        else if (this.renderer.getWidth() != this.drawable.getSurfaceWidth() || this.renderer.getHeight() != this.drawable.getSurfaceHeight()) {
            this.renderer.setSize(this.drawable.getSurfaceWidth(), this.drawable.getSurfaceHeight());
            this.contentsLost = true;
        }
        else {
            this.contentsLost = false;
        }
    }
}
