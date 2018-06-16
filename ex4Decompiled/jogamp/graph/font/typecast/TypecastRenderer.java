// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast;

import jogamp.opengl.Debug;
import jogamp.graph.font.typecast.ot.OTGlyph;
import jogamp.graph.font.typecast.ot.Point;
import com.jogamp.graph.geom.Vertex;
import com.jogamp.graph.curve.OutlineShape;

public class TypecastRenderer
{
    private static final boolean DEBUG;
    
    private static void addShapeMoveTo(final OutlineShape outlineShape, final Vertex.Factory<? extends Vertex> factory, final Point point) {
        if (TypecastRenderer.DEBUG) {
            System.err.println("Shape.MoveTo: " + point);
        }
        outlineShape.closeLastOutline(false);
        outlineShape.addEmptyOutline();
        outlineShape.addVertex(0, (Vertex)factory.create(point.x, point.y, 0.0f, point.onCurve));
    }
    
    private static void addShapeLineTo(final OutlineShape outlineShape, final Vertex.Factory<? extends Vertex> factory, final Point point) {
        if (TypecastRenderer.DEBUG) {
            System.err.println("Shape.LineTo: " + point);
        }
        outlineShape.addVertex(0, (Vertex)factory.create(point.x, point.y, 0.0f, point.onCurve));
    }
    
    private static void addShapeQuadTo(final OutlineShape outlineShape, final Vertex.Factory<? extends Vertex> factory, final Point point, final Point point2) {
        if (TypecastRenderer.DEBUG) {
            System.err.println("Shape.QuadTo: " + point + ", " + point2);
        }
        outlineShape.addVertex(0, (Vertex)factory.create(point.x, point.y, 0.0f, point.onCurve));
        outlineShape.addVertex(0, (Vertex)factory.create(point2.x, point2.y, 0.0f, point2.onCurve));
    }
    
    private static void addShapeQuadTo(final OutlineShape outlineShape, final Vertex.Factory<? extends Vertex> factory, final Point point, final float n, final float n2, final boolean b) {
        if (TypecastRenderer.DEBUG) {
            System.err.println("Shape.QuadTo: " + point + ", p2 " + n + ", " + n2 + ", onCurve " + b);
        }
        outlineShape.addVertex(0, (Vertex)factory.create(point.x, point.y, 0.0f, point.onCurve));
        outlineShape.addVertex(0, (Vertex)factory.create(n, n2, 0.0f, b));
    }
    
    public static OutlineShape buildShape(final char c, final OTGlyph otGlyph, final Vertex.Factory<? extends Vertex> factory) {
        if (otGlyph == null) {
            return null;
        }
        final OutlineShape outlineShape = new OutlineShape(factory);
        buildShapeImpl(outlineShape, c, otGlyph, factory);
        outlineShape.setIsQuadraticNurbs();
        return outlineShape;
    }
    
    private static void buildShapeImpl(final OutlineShape outlineShape, final char c, final OTGlyph otGlyph, final Vertex.Factory<? extends Vertex> factory) {
        int n = 0;
        int n2 = 0;
        for (int pointCount = otGlyph.getPointCount(), i = 0; i < pointCount; ++i) {
            ++n2;
            if (otGlyph.getPoint(i).endOfContour) {
                int j = 0;
                while (j < n2 - 1) {
                    final Point point = otGlyph.getPoint(n + j % n2);
                    final Point point2 = otGlyph.getPoint(n + (j + 1) % n2);
                    final Point point3 = otGlyph.getPoint(n + (j + 2) % n2);
                    final Point point4 = (j + 3 < n2) ? otGlyph.getPoint(n + j + 3) : null;
                    if (TypecastRenderer.DEBUG) {
                        System.err.println("GlyphShape<" + c + ">: offset " + j + " of " + n2 + "/" + pointCount + " points");
                        final int n3 = (j == 0) ? (n + n2 - 1) : (n + (j - 1) % n2);
                        final Point point5 = otGlyph.getPoint(n3);
                        final int n4 = n + j % n2;
                        final int n5 = n + (j + 1) % n2;
                        final int n6 = n + (j + 2) % n2;
                        final int n7 = n + (j + 3) % n2;
                        System.err.println("\t pM[" + n3 + "] " + point5);
                        System.err.println("\t p0[" + n4 + "] " + point);
                        System.err.println("\t p1[" + n5 + "] " + point2);
                        System.err.println("\t p2[" + n6 + "] " + point3);
                        System.err.println("\t p3[" + n7 + "] " + point4);
                    }
                    if (j == 0) {
                        addShapeMoveTo(outlineShape, factory, point);
                    }
                    if (point.endOfContour) {
                        if (TypecastRenderer.DEBUG) {
                            System.err.println("B0 .. end-of-contour **** EOC");
                        }
                        outlineShape.closeLastOutline(false);
                        break;
                    }
                    if (point.onCurve) {
                        if (point2.onCurve) {
                            if (TypecastRenderer.DEBUG) {
                                System.err.println("B1 .. line-to p0-p1");
                            }
                            addShapeLineTo(outlineShape, factory, point2);
                            ++j;
                        }
                        else if (point3.onCurve) {
                            if (TypecastRenderer.DEBUG) {
                                System.err.println("B2 .. quad-to p0-p1-p2");
                            }
                            addShapeQuadTo(outlineShape, factory, point2, point3);
                            j += 2;
                        }
                        else if (null != point4 && point4.onCurve) {
                            if (TypecastRenderer.DEBUG) {
                                System.err.println("B3 .. 2-quad p0-p1-p1_2, p1_2-p2-p3 **** 2QUAD");
                            }
                            addShapeQuadTo(outlineShape, factory, point2, midValue(point2.x, point3.x), midValue(point2.y, point3.y), true);
                            addShapeQuadTo(outlineShape, factory, point3, point4);
                            j += 3;
                        }
                        else {
                            if (TypecastRenderer.DEBUG) {
                                System.err.println("B4 .. quad-to p0-p1-p2h **** MID");
                            }
                            addShapeQuadTo(outlineShape, factory, point2, midValue(point2.x, point3.x), midValue(point2.y, point3.y), true);
                            j += 2;
                        }
                    }
                    else if (!point2.onCurve) {
                        if (TypecastRenderer.DEBUG) {
                            System.err.println("B5 .. quad-to pMh-p0-p1h ***** MID");
                        }
                        addShapeQuadTo(outlineShape, factory, point, midValue(point.x, point2.x), midValue(point.y, point2.y), true);
                        ++j;
                    }
                    else {
                        if (TypecastRenderer.DEBUG) {
                            System.err.println("B6 .. quad-to pMh-p0-p1");
                        }
                        addShapeQuadTo(outlineShape, factory, point, point2);
                        ++j;
                    }
                }
                outlineShape.closeLastOutline(false);
                n = i + 1;
                n2 = 0;
            }
        }
    }
    
    private static float midValue(final float n, final float n2) {
        return n + (n2 - n) / 2.0f;
    }
    
    static {
        DEBUG = Debug.debug("graph.font.Renderer");
    }
}
