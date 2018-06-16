// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.awt;

import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.texture.TextureCoords;
import java.awt.Color;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;
import java.awt.Image;
import java.awt.Graphics2D;
import com.jogamp.opengl.GLException;
import java.awt.Dimension;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import java.awt.Rectangle;
import com.jogamp.opengl.util.texture.awt.AWTTextureData;
import com.jogamp.opengl.util.texture.Texture;
import java.awt.image.BufferedImage;

public class TextureRenderer
{
    private final boolean alpha;
    private final boolean intensity;
    private boolean mipmap;
    private boolean smoothing;
    private boolean smoothingChanged;
    private BufferedImage image;
    private Texture texture;
    private AWTTextureData textureData;
    private boolean mustReallocateTexture;
    private Rectangle dirtyRegion;
    private final GLUgl2 glu;
    private float r;
    private float g;
    private float b;
    private float a;
    private float[] compArray;
    
    public TextureRenderer(final int n, final int n2, final boolean b) {
        this(n, n2, b, false);
    }
    
    public TextureRenderer(final int n, final int n2, final boolean b, final boolean b2) {
        this(n, n2, b, false, b2);
    }
    
    private TextureRenderer(final int n, final int n2, final boolean alpha, final boolean intensity, final boolean mipmap) {
        this.smoothing = true;
        this.glu = new GLUgl2();
        this.r = 1.0f;
        this.g = 1.0f;
        this.b = 1.0f;
        this.a = 1.0f;
        this.alpha = alpha;
        this.intensity = intensity;
        this.mipmap = mipmap;
        this.init(n, n2);
    }
    
    public static TextureRenderer createAlphaOnlyRenderer(final int n, final int n2) {
        return createAlphaOnlyRenderer(n, n2, false);
    }
    
    public static TextureRenderer createAlphaOnlyRenderer(final int n, final int n2, final boolean b) {
        return new TextureRenderer(n, n2, false, true, b);
    }
    
    public int getWidth() {
        return this.image.getWidth();
    }
    
    public int getHeight() {
        return this.image.getHeight();
    }
    
    public Dimension getSize() {
        return this.getSize(null);
    }
    
    public Dimension getSize(Dimension dimension) {
        if (dimension == null) {
            dimension = new Dimension();
        }
        dimension.setSize(this.image.getWidth(), this.image.getHeight());
        return dimension;
    }
    
    public void setSize(final int n, final int n2) throws GLException {
        this.init(n, n2);
    }
    
    public void setSize(final Dimension dimension) throws GLException {
        this.setSize(dimension.width, dimension.height);
    }
    
    public void setSmoothing(final boolean smoothing) {
        this.smoothing = smoothing;
        this.smoothingChanged = true;
    }
    
    public boolean getSmoothing() {
        return this.smoothing;
    }
    
    public Graphics2D createGraphics() {
        return this.image.createGraphics();
    }
    
    public Image getImage() {
        return this.image;
    }
    
    public void markDirty(final int n, final int n2, final int n3, final int n4) {
        final Rectangle dirtyRegion = new Rectangle(n, n2, n3, n4);
        if (this.dirtyRegion == null) {
            this.dirtyRegion = dirtyRegion;
        }
        else {
            this.dirtyRegion.add(dirtyRegion);
        }
    }
    
    public Texture getTexture() throws GLException {
        if (this.dirtyRegion != null) {
            this.sync(this.dirtyRegion.x, this.dirtyRegion.y, this.dirtyRegion.width, this.dirtyRegion.height);
            this.dirtyRegion = null;
        }
        this.ensureTexture();
        return this.texture;
    }
    
    public void dispose() throws GLException {
        if (this.texture != null) {
            this.texture.destroy(GLContext.getCurrentGL());
            this.texture = null;
        }
        if (this.image != null) {
            this.image.flush();
            this.image = null;
        }
    }
    
    public void beginOrthoRendering(final int n, final int n2) throws GLException {
        this.beginOrthoRendering(n, n2, true);
    }
    
    public void beginOrthoRendering(final int n, final int n2, final boolean b) throws GLException {
        this.beginRendering(true, n, n2, b);
    }
    
    public void begin3DRendering() throws GLException {
        this.beginRendering(false, 0, 0, false);
    }
    
    public void setColor(final float n, final float n2, final float n3, final float a) throws GLException {
        final GL2 gl2 = GLContext.getCurrentGL().getGL2();
        this.r = n * a;
        this.g = n2 * a;
        this.b = n3 * a;
        this.a = a;
        gl2.glColor4f(this.r, this.g, this.b, this.a);
    }
    
    public void setColor(final Color color) throws GLException {
        if (this.compArray == null) {
            this.compArray = new float[4];
        }
        color.getRGBComponents(this.compArray);
        this.setColor(this.compArray[0], this.compArray[1], this.compArray[2], this.compArray[3]);
    }
    
    public void drawOrthoRect(final int n, final int n2) throws GLException {
        this.drawOrthoRect(n, n2, 0, 0, this.getWidth(), this.getHeight());
    }
    
    public void drawOrthoRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) throws GLException {
        this.draw3DRect(n, n2, 0.0f, n3, n4, n5, n6, 1.0f);
    }
    
    public void draw3DRect(final float n, final float n2, final float n3, final int n4, final int n5, final int n6, final int n7, final float n8) throws GLException {
        final GL2 gl2 = GLContext.getCurrentGL().getGL2();
        final TextureCoords subImageTexCoords = this.getTexture().getSubImageTexCoords(n4, n5, n4 + n6, n5 + n7);
        gl2.glBegin(7);
        gl2.glTexCoord2f(subImageTexCoords.left(), subImageTexCoords.bottom());
        gl2.glVertex3f(n, n2, n3);
        gl2.glTexCoord2f(subImageTexCoords.right(), subImageTexCoords.bottom());
        gl2.glVertex3f(n + n6 * n8, n2, n3);
        gl2.glTexCoord2f(subImageTexCoords.right(), subImageTexCoords.top());
        gl2.glVertex3f(n + n6 * n8, n2 + n7 * n8, n3);
        gl2.glTexCoord2f(subImageTexCoords.left(), subImageTexCoords.top());
        gl2.glVertex3f(n, n2 + n7 * n8, n3);
        gl2.glEnd();
    }
    
    public void endOrthoRendering() throws GLException {
        this.endRendering(true);
    }
    
    public void end3DRendering() throws GLException {
        this.endRendering(false);
    }
    
    public boolean isUsingAutoMipmapGeneration() {
        return this.mipmap;
    }
    
    private void beginRendering(final boolean b, final int n, final int n2, final boolean b2) {
        final GL2 gl2 = GLContext.getCurrentGL().getGL2();
        gl2.glPushAttrib(0x46000 | (b ? 4352 : 0));
        gl2.glDisable(2896);
        if (b) {
            if (b2) {
                gl2.glDisable(2929);
            }
            gl2.glDisable(2884);
            gl2.glMatrixMode(5889);
            gl2.glPushMatrix();
            gl2.glLoadIdentity();
            this.glu.gluOrtho2D(0.0f, n, 0.0f, n2);
            gl2.glMatrixMode(5888);
            gl2.glPushMatrix();
            gl2.glLoadIdentity();
            gl2.glMatrixMode(5890);
            gl2.glPushMatrix();
            gl2.glLoadIdentity();
        }
        gl2.glEnable(3042);
        gl2.glBlendFunc(1, 771);
        final Texture texture = this.getTexture();
        texture.enable(gl2);
        texture.bind(gl2);
        gl2.glTexEnvi(8960, 8704, 8448);
        gl2.glColor4f(this.r, this.g, this.b, this.a);
        if (this.smoothingChanged) {
            this.smoothingChanged = false;
            if (this.smoothing) {
                texture.setTexParameteri(gl2, 10240, 9729);
                if (this.mipmap) {
                    texture.setTexParameteri(gl2, 10241, 9987);
                }
                else {
                    texture.setTexParameteri(gl2, 10241, 9729);
                }
            }
            else {
                texture.setTexParameteri(gl2, 10241, 9728);
                texture.setTexParameteri(gl2, 10240, 9728);
            }
        }
    }
    
    private void endRendering(final boolean b) {
        final GL2 gl2 = GLContext.getCurrentGL().getGL2();
        this.getTexture().disable(gl2);
        if (b) {
            gl2.glMatrixMode(5889);
            gl2.glPopMatrix();
            gl2.glMatrixMode(5888);
            gl2.glPopMatrix();
            gl2.glMatrixMode(5890);
            gl2.glPopMatrix();
        }
        gl2.glPopAttrib();
    }
    
    private void init(final int n, final int n2) {
        final GL2 gl2 = GLContext.getCurrentGL().getGL2();
        if (this.image != null) {
            this.image.flush();
            this.image = null;
        }
        final int n3 = this.intensity ? 32841 : 0;
        this.image = new BufferedImage(n, n2, this.intensity ? 10 : (this.alpha ? 3 : 1));
        this.textureData = new AWTTextureData(gl2.getGLProfile(), n3, 0, this.mipmap, this.image);
        this.mustReallocateTexture = true;
    }
    
    private void sync(final int n, final int n2, final int n3, final int n4) throws GLException {
        if (!this.ensureTexture()) {
            this.texture.updateSubImage(GLContext.getCurrentGL(), this.textureData, 0, n, n2, n, n2, n3, n4);
        }
    }
    
    private boolean ensureTexture() {
        final GL currentGL = GLContext.getCurrentGL();
        if (this.mustReallocateTexture) {
            if (this.texture != null) {
                this.texture.destroy(currentGL);
                this.texture = null;
            }
            this.mustReallocateTexture = false;
        }
        if (this.texture == null) {
            this.texture = TextureIO.newTexture(this.textureData);
            if (this.mipmap && !this.texture.isUsingAutoMipmapGeneration()) {
                this.texture.destroy(currentGL);
                this.mipmap = false;
                this.textureData.setMipmap(false);
                this.texture = TextureIO.newTexture(this.textureData);
            }
            if (!this.smoothing) {
                this.texture.setTexParameteri(currentGL, 10241, 9728);
                this.texture.setTexParameteri(currentGL, 10240, 9728);
            }
            return true;
        }
        return false;
    }
}
