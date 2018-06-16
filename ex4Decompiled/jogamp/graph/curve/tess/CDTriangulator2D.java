// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.curve.tess;

import jogamp.opengl.Debug;
import com.jogamp.graph.geom.Vertex;
import com.jogamp.opengl.math.Vert2fImmutable;
import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.graph.geom.Outline;
import com.jogamp.graph.geom.Triangle;
import java.util.List;
import java.util.ArrayList;
import com.jogamp.graph.curve.tess.Triangulator;

public class CDTriangulator2D implements Triangulator
{
    protected static final boolean DEBUG;
    private static final boolean TEST_LINE_AA;
    private static final boolean TEST_MARK_LINE;
    private static final boolean TEST_ENABLED;
    private final ArrayList<Loop> loops;
    private int addedVerticeCount;
    private int maxTriID;
    
    public CDTriangulator2D() {
        this.loops = new ArrayList<Loop>();
        this.reset();
    }
    
    @Override
    public final void reset() {
        this.maxTriID = 0;
        this.addedVerticeCount = 0;
        this.loops.clear();
    }
    
    @Override
    public final int getAddedVerticeCount() {
        return this.addedVerticeCount;
    }
    
    @Override
    public final void addCurve(final List<Triangle> list, final Outline outline, final float n) {
        Loop containerLoop = null;
        if (!this.loops.isEmpty()) {
            containerLoop = this.getContainerLoop(outline);
        }
        if (containerLoop == null) {
            this.loops.add(new Loop(this.extractBoundaryTriangles(list, new GraphOutline(outline), false, n), VectorUtil.Winding.CCW));
        }
        else {
            containerLoop.addConstraintCurve(this.extractBoundaryTriangles(list, new GraphOutline(outline), true, n));
        }
    }
    
    @Override
    public final void generate(final List<Triangle> list) {
        for (int size = this.loops.size(), i = 0; i < size; ++i) {
            final Loop loop = this.loops.get(i);
            int n = 0;
            int computeLoopSize = loop.computeLoopSize();
            while (!loop.isSimplex()) {
                Triangle triangle;
                if (n > computeLoopSize) {
                    triangle = loop.cut(false);
                }
                else {
                    triangle = loop.cut(true);
                }
                ++n;
                if (triangle != null) {
                    n = 0;
                    --computeLoopSize;
                    triangle.setId(this.maxTriID++);
                    list.add(triangle);
                    if (CDTriangulator2D.DEBUG) {
                        System.err.println("CDTri.gen[" + i + "].0: " + triangle);
                    }
                }
                if (n > computeLoopSize * 2) {
                    if (CDTriangulator2D.DEBUG) {
                        System.err.println("CDTri.gen[" + i + "].X: Triangulation not complete!");
                        break;
                    }
                    break;
                }
            }
            final Triangle cut = loop.cut(true);
            if (cut != null) {
                list.add(cut);
                if (CDTriangulator2D.DEBUG) {
                    System.err.println("CDTri.gen[" + i + "].1: " + cut);
                }
            }
        }
        if (CDTriangulator2D.TEST_ENABLED) {
            final float[] array = new float[2];
            final CDTriangulator2DExpAddOn cdTriangulator2DExpAddOn = new CDTriangulator2DExpAddOn();
            final int size2 = list.size();
            if (CDTriangulator2D.TEST_MARK_LINE) {
                for (int j = 0; j < size2; ++j) {
                    cdTriangulator2DExpAddOn.markLineInTriangle(list.get(j), array);
                }
            }
            else if (CDTriangulator2D.TEST_LINE_AA) {
                for (int k = 0; k < size2 - 1; k += 2) {
                    cdTriangulator2DExpAddOn.processLineAA(k, list.get(k), list.get(k + 1), array);
                }
            }
        }
    }
    
    private GraphOutline extractBoundaryTriangles(final List<Triangle> list, final GraphOutline graphOutline, final boolean b, final float n) {
        final GraphOutline graphOutline2 = new GraphOutline();
        final ArrayList<GraphVertex> graphPoint = graphOutline.getGraphPoint();
        for (int size = graphPoint.size(), i = 0; i < size; ++i) {
            final GraphVertex graphVertex = graphPoint.get(i);
            final GraphVertex graphVertex2 = graphPoint.get((i + size - 1) % size);
            final GraphVertex graphVertex3 = graphPoint.get((i + 1) % size);
            if (!graphVertex.getPoint().isOnCurve()) {
                final Vertex clone = graphVertex2.getPoint().clone();
                final Vertex clone2 = graphVertex3.getPoint().clone();
                final Vertex clone3 = graphVertex.getPoint().clone();
                this.addedVerticeCount += 3;
                final boolean[] array = { true, true, true };
                graphVertex2.setBoundaryContained(true);
                graphVertex.setBoundaryContained(true);
                graphVertex3.setBoundaryContained(true);
                boolean b2;
                Triangle triangle;
                if (VectorUtil.ccw(clone, clone3, clone2)) {
                    b2 = false;
                    triangle = new Triangle(clone, clone3, clone2, array);
                }
                else {
                    b2 = true;
                    triangle = new Triangle(clone2, clone3, clone, array);
                }
                triangle.setId(this.maxTriID++);
                list.add(triangle);
                if (CDTriangulator2D.DEBUG) {
                    System.err.println(triangle);
                }
                if (b || b2) {
                    clone.setTexCoord(0.0f, -0.1f, 0.0f);
                    clone2.setTexCoord(1.0f, -0.1f, 0.0f);
                    clone3.setTexCoord(0.5f, -n - 0.1f, 0.0f);
                    graphOutline2.addVertex(graphVertex);
                }
                else {
                    clone.setTexCoord(0.0f, 0.1f, 0.0f);
                    clone2.setTexCoord(1.0f, 0.1f, 0.0f);
                    clone3.setTexCoord(0.5f, n + 0.1f, 0.0f);
                }
                if (CDTriangulator2D.DEBUG) {
                    System.err.println("CDTri.ebt[" + i + "].0: hole " + (b || b2) + " " + graphVertex + ", " + triangle);
                }
            }
            else {
                if (!graphVertex3.getPoint().isOnCurve() || !graphVertex2.getPoint().isOnCurve()) {
                    graphVertex.setBoundaryContained(true);
                }
                graphOutline2.addVertex(graphVertex);
                if (CDTriangulator2D.DEBUG) {
                    System.err.println("CDTri.ebt[" + i + "].1: " + graphVertex);
                }
            }
        }
        return graphOutline2;
    }
    
    private Loop getContainerLoop(final Outline outline) {
        final ArrayList<Vertex> vertices = outline.getVertices();
        for (int i = 0; i < this.loops.size(); ++i) {
            final Loop loop = this.loops.get(i);
            for (int j = 0; j < vertices.size(); ++j) {
                if (loop.checkInside(vertices.get(j))) {
                    return loop;
                }
            }
        }
        return null;
    }
    
    static {
        DEBUG = Debug.debug("graph.curve.Triangulation");
        TEST_LINE_AA = Debug.debug("graph.curve.triangulation.LINE_AA");
        TEST_MARK_LINE = Debug.debug("graph.curve.triangulation.MARK_AA");
        TEST_ENABLED = (CDTriangulator2D.TEST_LINE_AA || CDTriangulator2D.TEST_MARK_LINE);
    }
}
