// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast;

import com.jogamp.common.util.IntIntHashMap;
import com.jogamp.opengl.math.geom.AABBox;
import com.jogamp.graph.curve.OutlineShape;
import com.jogamp.graph.font.Font;

public final class TypecastGlyph implements Font.Glyph
{
    public static final short INVALID_ID = -1;
    public static final short MAX_ID = -2;
    private final char symbol;
    private final OutlineShape shape;
    private final short id;
    private final Metrics metrics;
    
    protected TypecastGlyph(final Font font, final char symbol, final short id, final AABBox aabBox, final int n, final OutlineShape shape) {
        this.symbol = symbol;
        this.shape = shape;
        this.id = id;
        this.metrics = new Metrics(font, aabBox, n);
    }
    
    @Override
    public final Font getFont() {
        return this.metrics.getFont();
    }
    
    @Override
    public final char getSymbol() {
        return this.symbol;
    }
    
    final AABBox getBBoxUnsized() {
        return this.metrics.getBBox();
    }
    
    @Override
    public final AABBox getBBox() {
        return this.metrics.getBBox();
    }
    
    public final Metrics getMetrics() {
        return this.metrics;
    }
    
    @Override
    public final short getID() {
        return this.id;
    }
    
    @Override
    public final float getScale(final float n) {
        return this.metrics.getScale(n);
    }
    
    @Override
    public final AABBox getBBox(final AABBox aabBox, final float n, final float[] array) {
        return aabBox.copy(this.getBBox()).scale(this.getScale(n), array);
    }
    
    protected final void addAdvance(final float n, final float n2) {
        this.metrics.addAdvance(n, n2);
    }
    
    @Override
    public final float getAdvance(final float n, final boolean b) {
        return this.metrics.getAdvance(n, b);
    }
    
    @Override
    public final OutlineShape getShape() {
        return this.shape;
    }
    
    @Override
    public final int hashCode() {
        final int n = 31 + this.getFont().getName(3).hashCode();
        return (n << 5) - n + this.id;
    }
    
    public static final class Advance
    {
        private final Font font;
        private final float advance;
        private final IntIntHashMap size2advanceI;
        
        public Advance(final Font font, final float advance) {
            this.size2advanceI = new IntIntHashMap();
            this.font = font;
            this.advance = advance;
            this.size2advanceI.setKeyNotFoundValue(0);
        }
        
        public final void reset() {
            this.size2advanceI.clear();
        }
        
        public final Font getFont() {
            return this.font;
        }
        
        public final float getScale(final float n) {
            return this.font.getMetrics().getScale(n);
        }
        
        public final void add(final float n, final float n2) {
            this.size2advanceI.put(Float.floatToIntBits(n2), Float.floatToIntBits(n));
        }
        
        public final float get(final float n, final boolean b) {
            final int floatToIntBits = Float.floatToIntBits(n);
            final int value = this.size2advanceI.get(floatToIntBits);
            if (value != 0) {
                return Float.intBitsToFloat(value);
            }
            float n2;
            if (b) {
                n2 = this.advance * this.getScale(n);
            }
            else {
                n2 = Math.round(this.advance * this.getScale(n));
            }
            this.size2advanceI.put(floatToIntBits, Float.floatToIntBits(n2));
            return n2;
        }
        
        @Override
        public final String toString() {
            return "\nAdvance:\n  advance: " + this.advance + "\n advances: \n" + this.size2advanceI;
        }
    }
    
    public static final class Metrics
    {
        private final AABBox bbox;
        private final Advance advance;
        
        public Metrics(final Font font, final AABBox bbox, final float n) {
            this.bbox = bbox;
            this.advance = new Advance(font, n);
        }
        
        public final void reset() {
            this.advance.reset();
        }
        
        public final Font getFont() {
            return this.advance.getFont();
        }
        
        public final float getScale(final float n) {
            return this.advance.getScale(n);
        }
        
        public final AABBox getBBox() {
            return this.bbox;
        }
        
        public final void addAdvance(final float n, final float n2) {
            this.advance.add(n, n2);
        }
        
        public final float getAdvance(final float n, final boolean b) {
            return this.advance.get(n, b);
        }
        
        @Override
        public final String toString() {
            return "\nMetrics:\n  bbox: " + this.bbox + this.advance;
        }
    }
}
