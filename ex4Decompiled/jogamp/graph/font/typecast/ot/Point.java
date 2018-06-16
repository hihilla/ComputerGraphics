// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot;

public class Point
{
    public int x;
    public int y;
    public boolean onCurve;
    public boolean endOfContour;
    
    public Point(final int x, final int y, final boolean onCurve, final boolean endOfContour) {
        this.x = 0;
        this.y = 0;
        this.onCurve = true;
        this.endOfContour = false;
        this.x = x;
        this.y = y;
        this.onCurve = onCurve;
        this.endOfContour = endOfContour;
    }
    
    @Override
    public String toString() {
        return "P[" + this.x + "/" + this.y + ", on " + this.onCurve + ", end " + this.endOfContour + "]";
    }
}
