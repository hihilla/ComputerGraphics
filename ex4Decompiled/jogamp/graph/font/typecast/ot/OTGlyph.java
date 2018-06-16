// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot;

import jogamp.graph.font.typecast.t2.T2Interpreter;
import jogamp.graph.font.typecast.ot.table.CharstringType2;
import jogamp.graph.font.typecast.ot.table.Charstring;
import jogamp.graph.font.typecast.ot.table.GlyphDescription;
import com.jogamp.opengl.math.geom.AABBox;

public final class OTGlyph
{
    private final short _leftSideBearing;
    private final int _advanceWidth;
    private Point[] _points;
    AABBox _bbox;
    
    public OTGlyph(final GlyphDescription glyphDescription, final short leftSideBearing, final int advanceWidth) {
        this._leftSideBearing = leftSideBearing;
        this._advanceWidth = advanceWidth;
        this.describe(glyphDescription);
    }
    
    public OTGlyph(final Charstring charstring, final short leftSideBearing, final int advanceWidth) {
        this._leftSideBearing = leftSideBearing;
        this._advanceWidth = advanceWidth;
        if (charstring instanceof CharstringType2) {
            this._points = new T2Interpreter().execute((CharstringType2)charstring);
        }
    }
    
    public final void clearPointData() {
        this._points = null;
    }
    
    public final AABBox getBBox() {
        return this._bbox;
    }
    
    public final int getAdvanceWidth() {
        return this._advanceWidth;
    }
    
    public final short getLeftSideBearing() {
        return this._leftSideBearing;
    }
    
    public final Point getPoint(final int n) {
        return this._points[n];
    }
    
    public final int getPointCount() {
        return (null != this._points) ? this._points.length : 0;
    }
    
    private final void describe(final GlyphDescription glyphDescription) {
        int n = 0;
        this._points = new Point[glyphDescription.getPointCount()];
        for (int i = 0; i < glyphDescription.getPointCount(); ++i) {
            final boolean b = glyphDescription.getEndPtOfContours(n) == i;
            if (b) {
                ++n;
            }
            this._points[i] = new Point(glyphDescription.getXCoordinate(i), glyphDescription.getYCoordinate(i), (glyphDescription.getFlags(i) & 0x1) != 0x0, b);
        }
        this._bbox = new AABBox(glyphDescription.getXMinimum(), glyphDescription.getYMinimum(), 0.0f, glyphDescription.getXMaximum(), glyphDescription.getYMaximum(), 0.0f);
    }
}
