// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.font;

import com.jogamp.graph.curve.OutlineShape;
import jogamp.graph.geom.plane.AffineTransform;
import com.jogamp.opengl.math.geom.AABBox;

public interface Font
{
    public static final int NAME_COPYRIGHT = 0;
    public static final int NAME_FAMILY = 1;
    public static final int NAME_SUBFAMILY = 2;
    public static final int NAME_UNIQUNAME = 3;
    public static final int NAME_FULLNAME = 4;
    public static final int NAME_VERSION = 5;
    public static final int NAME_MANUFACTURER = 8;
    public static final int NAME_DESIGNER = 9;
    
    String getName(final int p0);
    
    StringBuilder getName(final StringBuilder p0, final int p1);
    
    StringBuilder getFullFamilyName(final StringBuilder p0);
    
    StringBuilder getAllNames(final StringBuilder p0, final String p1);
    
    float getPixelSize(final float p0, final float p1);
    
    float getAdvanceWidth(final int p0, final float p1);
    
    Metrics getMetrics();
    
    Glyph getGlyph(final char p0);
    
    int getNumGlyphs();
    
    float getLineHeight(final float p0);
    
    float getMetricWidth(final CharSequence p0, final float p1);
    
    float getMetricHeight(final CharSequence p0, final float p1, final AABBox p2);
    
    AABBox getMetricBounds(final CharSequence p0, final float p1);
    
    AABBox getPointsBounds(final AffineTransform p0, final CharSequence p1, final float p2, final AffineTransform p3, final AffineTransform p4);
    
    boolean isPrintableChar(final char p0);
    
    String toString();
    
    public interface Glyph
    {
        public static final int ID_UNKNOWN = 0;
        public static final int ID_CR = 2;
        public static final int ID_SPACE = 3;
        
        Font getFont();
        
        char getSymbol();
        
        short getID();
        
        AABBox getBBox();
        
        float getScale(final float p0);
        
        AABBox getBBox(final AABBox p0, final float p1, final float[] p2);
        
        float getAdvance(final float p0, final boolean p1);
        
        OutlineShape getShape();
        
        int hashCode();
    }
    
    public interface Metrics
    {
        float getAscent(final float p0);
        
        float getDescent(final float p0);
        
        float getLineGap(final float p0);
        
        float getMaxExtend(final float p0);
        
        float getScale(final float p0);
        
        AABBox getBBox(final AABBox p0, final float p1, final float[] p2);
    }
}
