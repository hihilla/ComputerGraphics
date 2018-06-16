// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.curve.opengl;

import java.util.Iterator;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.graph.geom.Vertex;
import com.jogamp.graph.curve.OutlineShape;
import com.jogamp.graph.curve.Region;
import com.jogamp.graph.font.Font;
import java.util.ArrayList;
import java.util.HashMap;
import jogamp.graph.geom.plane.AffineTransform;

public class TextRegionUtil
{
    public final int renderModes;
    public static final int DEFAULT_CACHE_LIMIT = 256;
    public final AffineTransform tempT1;
    public final AffineTransform tempT2;
    private final HashMap<String, GLRegion> stringCacheMap;
    private final ArrayList<String> stringCacheArray;
    private int stringCacheLimit;
    
    public TextRegionUtil(final int renderModes) {
        this.tempT1 = new AffineTransform();
        this.tempT2 = new AffineTransform();
        this.stringCacheMap = new HashMap<String, GLRegion>(256);
        this.stringCacheArray = new ArrayList<String>(256);
        this.stringCacheLimit = 256;
        this.renderModes = renderModes;
    }
    
    public static int getCharCount(final String s, final char c) {
        final int length = s.length();
        int n = 0;
        for (int i = 0; i < length; ++i) {
            if (s.charAt(i) == c) {
                ++n;
            }
        }
        return n;
    }
    
    public static void processString(final ShapeVisitor shapeVisitor, final AffineTransform transform, final Font font, final float n, final CharSequence charSequence, final AffineTransform affineTransform, final AffineTransform affineTransform2) {
        final int length = charSequence.length();
        final Font.Metrics metrics = font.getMetrics();
        final float lineHeight = font.getLineHeight(n);
        final float scale = metrics.getScale(n);
        float n2 = 0.0f;
        float n3 = 0.0f;
        for (int i = 0; i < length; ++i) {
            final char char1 = charSequence.charAt(i);
            if ('\n' == char1) {
                n2 -= lineHeight;
                n3 = 0.0f;
            }
            else if (char1 == ' ') {
                n3 += font.getAdvanceWidth(3, n);
            }
            else {
                if (Region.DEBUG_INSTANCE) {
                    System.err.println("XXXXXXXXXXXXXXx char: " + char1 + ", scale: " + scale + "; translate: " + n3 + ", " + n2);
                }
                if (null != transform) {
                    affineTransform.setTransform(transform);
                }
                else {
                    affineTransform.setToIdentity();
                }
                affineTransform.translate(n3, n2, affineTransform2);
                affineTransform.scale(scale, scale, affineTransform2);
                final Font.Glyph glyph = font.getGlyph(char1);
                final OutlineShape shape = glyph.getShape();
                if (null != shape) {
                    shapeVisitor.visit(shape, affineTransform);
                    n3 += glyph.getAdvance(n, true);
                }
            }
        }
    }
    
    public static void addStringToRegion(final GLRegion glRegion, final Vertex.Factory<? extends Vertex> factory, final Font font, final float n, final CharSequence charSequence, final float[] array, final AffineTransform affineTransform, final AffineTransform affineTransform2) {
        processString(new ShapeVisitor() {
            @Override
            public final void visit(final OutlineShape outlineShape, final AffineTransform affineTransform) {
                glRegion.addOutlineShape(outlineShape, affineTransform, (float[])(glRegion.hasColorChannel() ? array : null));
            }
        }, null, font, n, charSequence, affineTransform, affineTransform2);
    }
    
    public void drawString3D(final GL2ES2 gl2ES2, final RegionRenderer regionRenderer, final Font font, final float n, final CharSequence charSequence, final float[] array, final int[] array2) {
        if (!regionRenderer.isInitialized()) {
            throw new GLException("TextRendererImpl01: not initialized!");
        }
        GLRegion glRegion = this.getCachedRegion(font, charSequence, n, 0);
        if (null == glRegion) {
            glRegion = GLRegion.create(this.renderModes, null);
            addStringToRegion(glRegion, regionRenderer.getRenderState().getVertexFactory(), font, n, charSequence, array, this.tempT1, this.tempT2);
            this.addCachedRegion(gl2ES2, font, charSequence, n, 0, glRegion);
        }
        glRegion.draw(gl2ES2, regionRenderer, array2);
    }
    
    public static void drawString3D(final GL2ES2 gl2ES2, final int n, final RegionRenderer regionRenderer, final Font font, final float n2, final CharSequence charSequence, final float[] array, final int[] array2, final AffineTransform affineTransform, final AffineTransform affineTransform2) {
        if (!regionRenderer.isInitialized()) {
            throw new GLException("TextRendererImpl01: not initialized!");
        }
        final GLRegion create = GLRegion.create(n, null);
        addStringToRegion(create, regionRenderer.getRenderState().getVertexFactory(), font, n2, charSequence, array, affineTransform, affineTransform2);
        create.draw(gl2ES2, regionRenderer, array2);
        create.destroy(gl2ES2);
    }
    
    public static void drawString3D(final GL2ES2 gl2ES2, final GLRegion glRegion, final RegionRenderer regionRenderer, final Font font, final float n, final CharSequence charSequence, final float[] array, final int[] array2, final AffineTransform affineTransform, final AffineTransform affineTransform2) {
        if (!regionRenderer.isInitialized()) {
            throw new GLException("TextRendererImpl01: not initialized!");
        }
        glRegion.clear(gl2ES2);
        addStringToRegion(glRegion, regionRenderer.getRenderState().getVertexFactory(), font, n, charSequence, array, affineTransform, affineTransform2);
        glRegion.draw(gl2ES2, regionRenderer, array2);
    }
    
    public void clear(final GL2ES2 gl2ES2) {
        final Iterator<GLRegion> iterator = this.stringCacheMap.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().destroy(gl2ES2);
        }
        this.stringCacheMap.clear();
        this.stringCacheArray.clear();
    }
    
    public final void setCacheLimit(final int stringCacheLimit) {
        this.stringCacheLimit = stringCacheLimit;
    }
    
    public final void setCacheLimit(final GL2ES2 gl2ES2, final int stringCacheLimit) {
        this.stringCacheLimit = stringCacheLimit;
        this.validateCache(gl2ES2, 0);
    }
    
    public final int getCacheLimit() {
        return this.stringCacheLimit;
    }
    
    public final int getCacheSize() {
        return this.stringCacheArray.size();
    }
    
    protected final void validateCache(final GL2ES2 gl2ES2, final int n) {
        if (this.getCacheLimit() > 0) {
            while (this.getCacheSize() + n > this.getCacheLimit()) {
                this.removeCachedRegion(gl2ES2, 0);
            }
        }
    }
    
    protected final GLRegion getCachedRegion(final Font font, final CharSequence charSequence, final float n, final int n2) {
        return this.stringCacheMap.get(this.getKey(font, charSequence, n, n2));
    }
    
    protected final void addCachedRegion(final GL2ES2 gl2ES2, final Font font, final CharSequence charSequence, final float n, final int n2, final GLRegion glRegion) {
        if (0 != this.getCacheLimit()) {
            final String key = this.getKey(font, charSequence, n, n2);
            if (null == this.stringCacheMap.put(key, glRegion)) {
                this.validateCache(gl2ES2, 1);
                this.stringCacheArray.add(this.stringCacheArray.size(), key);
            }
        }
    }
    
    protected final void removeCachedRegion(final GL2ES2 gl2ES2, final Font font, final CharSequence charSequence, final int n, final int n2) {
        final String key = this.getKey(font, charSequence, n, n2);
        final GLRegion glRegion = this.stringCacheMap.remove(key);
        if (null != glRegion) {
            glRegion.destroy(gl2ES2);
        }
        this.stringCacheArray.remove(key);
    }
    
    protected final void removeCachedRegion(final GL2ES2 gl2ES2, final int n) {
        final String s = this.stringCacheArray.remove(n);
        if (null != s) {
            final GLRegion glRegion = this.stringCacheMap.remove(s);
            if (null != glRegion) {
                glRegion.destroy(gl2ES2);
            }
        }
    }
    
    protected final String getKey(final Font font, final CharSequence charSequence, final float n, final int n2) {
        return font.getName(new StringBuilder(), 3).append(".").append(charSequence.hashCode()).append(".").append(Float.floatToIntBits(n)).append(n2).toString();
    }
    
    public interface ShapeVisitor
    {
        void visit(final OutlineShape p0, final AffineTransform p1);
    }
}
