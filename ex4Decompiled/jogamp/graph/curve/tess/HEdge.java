// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.curve.tess;

import com.jogamp.graph.geom.Vertex;
import com.jogamp.graph.geom.Triangle;

public class HEdge
{
    public static final int BOUNDARY = 3;
    public static final int INNER = 1;
    public static final int HOLE = 2;
    private GraphVertex vert;
    private HEdge prev;
    private HEdge next;
    private HEdge sibling;
    private int type;
    private Triangle triangle;
    
    public HEdge(final GraphVertex vert, final int type) {
        this.prev = null;
        this.next = null;
        this.sibling = null;
        this.type = 3;
        this.triangle = null;
        this.vert = vert;
        this.type = type;
    }
    
    public HEdge(final GraphVertex vert, final HEdge prev, final HEdge next, final HEdge sibling, final int type) {
        this.prev = null;
        this.next = null;
        this.sibling = null;
        this.type = 3;
        this.triangle = null;
        this.vert = vert;
        this.prev = prev;
        this.next = next;
        this.sibling = sibling;
        this.type = type;
    }
    
    public HEdge(final GraphVertex vert, final HEdge prev, final HEdge next, final HEdge sibling, final int type, final Triangle triangle) {
        this.prev = null;
        this.next = null;
        this.sibling = null;
        this.type = 3;
        this.triangle = null;
        this.vert = vert;
        this.prev = prev;
        this.next = next;
        this.sibling = sibling;
        this.type = type;
        this.triangle = triangle;
    }
    
    public GraphVertex getGraphPoint() {
        return this.vert;
    }
    
    public void setVert(final GraphVertex vert) {
        this.vert = vert;
    }
    
    public HEdge getPrev() {
        return this.prev;
    }
    
    public void setPrev(final HEdge prev) {
        this.prev = prev;
    }
    
    public HEdge getNext() {
        return this.next;
    }
    
    public void setNext(final HEdge next) {
        this.next = next;
    }
    
    public HEdge getSibling() {
        return this.sibling;
    }
    
    public void setSibling(final HEdge sibling) {
        this.sibling = sibling;
    }
    
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    public Triangle getTriangle() {
        return this.triangle;
    }
    
    public void setTriangle(final Triangle triangle) {
        this.triangle = triangle;
    }
    
    public static <T extends Vertex> void connect(final HEdge prev, final HEdge next) {
        prev.setNext(next);
        next.setPrev(prev);
    }
    
    public static <T extends Vertex> void makeSiblings(final HEdge sibling, final HEdge sibling2) {
        sibling.setSibling(sibling2);
        sibling2.setSibling(sibling);
    }
    
    public boolean vertexOnCurveVertex() {
        return this.vert.getPoint().isOnCurve();
    }
}
