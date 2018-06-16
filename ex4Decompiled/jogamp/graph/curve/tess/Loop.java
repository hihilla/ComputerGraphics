// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.curve.tess;

import com.jogamp.graph.geom.Vertex;
import java.util.Iterator;
import java.util.ArrayList;
import com.jogamp.opengl.math.Vert2fImmutable;
import com.jogamp.graph.geom.Triangle;
import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.opengl.math.geom.AABBox;

public class Loop
{
    private HEdge root;
    private final AABBox box;
    private GraphOutline initialOutline;
    
    public Loop(final GraphOutline initialOutline, final VectorUtil.Winding winding) {
        this.root = null;
        this.box = new AABBox();
        this.initialOutline = null;
        this.initialOutline = initialOutline;
        this.root = this.initFromPolyline(this.initialOutline, winding);
    }
    
    public HEdge getHEdge() {
        return this.root;
    }
    
    public Triangle cut(final boolean b) {
        if (this.isSimplex()) {
            return new Triangle(this.root.getGraphPoint().getPoint(), this.root.getNext().getGraphPoint().getPoint(), this.root.getNext().getNext().getGraphPoint().getPoint(), this.checkVerticesBoundary(this.root));
        }
        final HEdge prev = this.root.getPrev();
        final HEdge next = this.root.getNext();
        final HEdge closestValidNeighbor = this.findClosestValidNeighbor(next.getNext(), b);
        if (closestValidNeighbor == null) {
            this.root = this.root.getNext();
            return null;
        }
        final GraphVertex graphPoint = this.root.getGraphPoint();
        final GraphVertex graphPoint2 = next.getGraphPoint();
        final GraphVertex graphPoint3 = closestValidNeighbor.getGraphPoint();
        final HEdge hEdge = new HEdge(graphPoint3, 1);
        HEdge.connect(hEdge, this.root);
        HEdge.connect(next, hEdge);
        HEdge sibling = hEdge.getSibling();
        if (sibling == null) {
            sibling = new HEdge(hEdge.getNext().getGraphPoint(), 1);
            HEdge.makeSiblings(hEdge, sibling);
        }
        HEdge.connect(prev, sibling);
        HEdge.connect(sibling, closestValidNeighbor);
        final Triangle triangle = this.createTriangle(graphPoint.getPoint(), graphPoint2.getPoint(), graphPoint3.getPoint(), this.root);
        this.root = closestValidNeighbor;
        return triangle;
    }
    
    public boolean isSimplex() {
        return this.root.getNext().getNext().getNext() == this.root;
    }
    
    private HEdge initFromPolyline(final GraphOutline graphOutline, final VectorUtil.Winding winding) {
        final ArrayList<GraphVertex> graphPoint = graphOutline.getGraphPoint();
        if (graphPoint.size() < 3) {
            throw new IllegalArgumentException("outline's vertices < 3: " + graphPoint.size());
        }
        final boolean b = VectorUtil.getWinding(graphPoint.get(0).getPoint(), graphPoint.get(1).getPoint(), graphPoint.get(2).getPoint()) != winding && winding == VectorUtil.Winding.CW;
        final int n = (winding == VectorUtil.Winding.CCW) ? 3 : 2;
        HEdge hEdge = null;
        HEdge prev = null;
        int size;
        int i;
        if (!b) {
            size = graphPoint.size();
            i = 0;
        }
        else {
            size = -1;
            i = graphPoint.size() - 1;
        }
        while (i != size) {
            final GraphVertex graphVertex = graphPoint.get(i);
            this.box.resize(graphVertex.getX(), graphVertex.getY(), graphVertex.getZ());
            final HEdge prev2 = new HEdge(graphVertex, n);
            graphVertex.addEdge(prev2);
            if (prev != null) {
                prev.setNext(prev2);
                prev2.setPrev(prev);
            }
            else {
                hEdge = prev2;
            }
            if (!b) {
                if (i == graphPoint.size() - 1) {
                    prev2.setNext(hEdge);
                    hEdge.setPrev(prev2);
                }
                ++i;
            }
            else {
                if (i == 0) {
                    prev2.setNext(hEdge);
                    hEdge.setPrev(prev2);
                }
                --i;
            }
            prev = prev2;
        }
        return hEdge;
    }
    
    public void addConstraintCurve(final GraphOutline graphOutline) {
        this.initFromPolyline(graphOutline, VectorUtil.Winding.CW);
        final HEdge boundEdge = this.locateClosestVertex(graphOutline).findBoundEdge();
        final HEdge prev = boundEdge.getPrev();
        final HEdge hEdge = new HEdge(this.root.getGraphPoint(), 1);
        HEdge.connect(this.root.getPrev(), hEdge);
        HEdge.connect(hEdge, boundEdge);
        HEdge sibling = hEdge.getSibling();
        if (sibling == null) {
            sibling = new HEdge(hEdge.getNext().getGraphPoint(), 1);
            HEdge.makeSiblings(hEdge, sibling);
        }
        HEdge.connect(prev, sibling);
        HEdge.connect(sibling, this.root);
    }
    
    private GraphVertex locateClosestVertex(final GraphOutline graphOutline) {
        HEdge boundEdge = null;
        GraphVertex graphVertex = null;
        float n = Float.MAX_VALUE;
        boolean inCircleVec2 = false;
        final ArrayList<GraphVertex> graphPoint = this.initialOutline.getGraphPoint();
        final ArrayList<GraphVertex> graphPoint2 = graphOutline.getGraphPoint();
        for (int i = 0; i < graphPoint.size() - 1; ++i) {
            final GraphVertex graphVertex2 = graphPoint.get(i);
            final GraphVertex graphVertex3 = graphPoint.get(i + 1);
            for (int j = 0; j < graphPoint2.size(); ++j) {
                final GraphVertex graphVertex4 = graphPoint2.get(j);
                final float distVec3 = VectorUtil.distVec3(graphVertex2.getCoord(), graphVertex4.getCoord());
                if (distVec3 < n) {
                    for (final GraphVertex graphVertex5 : graphPoint2) {
                        if (graphVertex5 != graphVertex2 && graphVertex5 != graphVertex3) {
                            if (graphVertex5 == graphVertex4) {
                                continue;
                            }
                            inCircleVec2 = VectorUtil.isInCircleVec2(graphVertex2.getPoint(), graphVertex3.getPoint(), graphVertex4.getPoint(), graphVertex5.getPoint());
                            if (inCircleVec2) {
                                break;
                            }
                            continue;
                        }
                    }
                    if (!inCircleVec2) {
                        graphVertex = graphVertex4;
                        n = distVec3;
                        boundEdge = graphVertex2.findBoundEdge();
                    }
                }
            }
        }
        if (boundEdge != null) {
            this.root = boundEdge;
        }
        return graphVertex;
    }
    
    private HEdge findClosestValidNeighbor(final HEdge hEdge, final boolean b) {
        final HEdge next = this.root.getNext();
        if (!VectorUtil.ccw(this.root.getGraphPoint().getPoint(), next.getGraphPoint().getPoint(), hEdge.getGraphPoint().getPoint())) {
            return null;
        }
        boolean inCircleVec2 = false;
        if (b) {
            final Vertex point = hEdge.getGraphPoint().getPoint();
            for (HEdge hEdge2 = hEdge.getNext(); hEdge2 != hEdge; hEdge2 = hEdge2.getNext()) {
                if (hEdge2.getGraphPoint() != this.root.getGraphPoint() && hEdge2.getGraphPoint() != next.getGraphPoint() && hEdge2.getGraphPoint().getPoint() != point) {
                    inCircleVec2 = VectorUtil.isInCircleVec2(this.root.getGraphPoint().getPoint(), next.getGraphPoint().getPoint(), point, hEdge2.getGraphPoint().getPoint());
                    if (inCircleVec2) {
                        break;
                    }
                }
            }
        }
        if (!inCircleVec2) {
            return hEdge;
        }
        return null;
    }
    
    private Triangle createTriangle(final Vertex vertex, final Vertex vertex2, final Vertex vertex3, final HEdge hEdge) {
        return new Triangle(vertex, vertex2, vertex3, this.checkVerticesBoundary(hEdge));
    }
    
    private boolean[] checkVerticesBoundary(final HEdge hEdge) {
        final boolean[] array = new boolean[3];
        if (hEdge.getGraphPoint().isBoundaryContained()) {
            array[0] = true;
        }
        if (hEdge.getNext().getGraphPoint().isBoundaryContained()) {
            array[1] = true;
        }
        if (hEdge.getNext().getNext().getGraphPoint().isBoundaryContained()) {
            array[2] = true;
        }
        return array;
    }
    
    public boolean checkInside(final Vertex vertex) {
        if (!this.box.contains(vertex.getX(), vertex.getY(), vertex.getZ())) {
            return false;
        }
        boolean b = false;
        HEdge root = this.root;
        HEdge hEdge = this.root.getNext();
        do {
            final Vertex point = root.getGraphPoint().getPoint();
            final Vertex point2 = hEdge.getGraphPoint().getPoint();
            if (point2.getY() > vertex.getY() != point.getY() > vertex.getY() && vertex.getX() < (point.getX() - point2.getX()) * (vertex.getY() - point2.getY()) / (point.getY() - point2.getY()) + point2.getX()) {
                b = !b;
            }
            root = hEdge;
            hEdge = root.getNext();
        } while (root != this.root);
        return b;
    }
    
    public int computeLoopSize() {
        int n = 0;
        HEdge hEdge = this.root;
        do {
            ++n;
            hEdge = hEdge.getNext();
        } while (hEdge != this.root);
        return n;
    }
}
