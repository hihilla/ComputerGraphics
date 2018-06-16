// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu;

public class Glue
{
    private static String[] __gluNurbsErrors;
    private static String[] __gluTessErrors;
    
    public static String __gluNURBSErrorString(final int n) {
        return Glue.__gluNurbsErrors[n];
    }
    
    public static String __gluTessErrorString(final int n) {
        return Glue.__gluTessErrors[n];
    }
    
    static {
        Glue.__gluNurbsErrors = new String[] { " ", "spline order un-supported", "too few knots", "valid knot range is empty", "decreasing knot sequence knot", "knot multiplicity greater than order of spline", "gluEndCurve() must follow gluBeginCurve()", "gluBeginCurve() must precede gluEndCurve()", "missing or extra geometric data", "can't draw piecewise linear trimming curves", "missing or extra domain data", "missing or extra domain data", "gluEndTrim() must precede gluEndSurface()", "gluBeginSurface() must precede gluEndSurface()", "curve of improper type passed as trim curve", "gluBeginSurface() must precede gluBeginTrim()", "gluEndTrim() must follow gluBeginTrim()", "gluBeginTrim() must follow gluEndTrim()", "invalid or missing trim curve", "gluBeginTrim() must precede gluPwlCurve()", "piecewise linear trimming curve referenced twice", "piecewise linear trimming curve and nurbs curve mixed", "improper usage of trim data type", "nurbs curve referenced twice", "nurbs curve and piecewise linear trimming curve mixed", "nurbs surface referenced twice", "invalid property", "gluEndSurface() must follow gluBeginSurface()", "intersecting or misoriented trim curve", "intersecting trim curves", "UNUSED", "inconnected trim curves", "unknown knot error", "negative vertex count encountered", "negative byte-stride encountered", "unknown type descriptor", "null control point reference", "duplicate point on piecewise linear trimming curve" };
        Glue.__gluTessErrors = new String[] { " ", "gluTessBeginPolygon() must precede a gluTessEndPolygon", "gluTessBeginContour() must precede a gluTessEndContour()", "gluTessEndPolygon() must follow a gluTessBeginPolygon()", "gluTessEndContour() must follow a gluTessBeginContour()", "a coordinate is too large", "need combine callback" };
    }
}
