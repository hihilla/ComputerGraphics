// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.curve.tess;

import java.util.ArrayList;
import com.jogamp.graph.geom.Vertex;

public class GraphVertex
{
    private Vertex point;
    private ArrayList<HEdge> edges;
    private boolean boundaryContained;
    
    public GraphVertex(final Vertex point) {
        this.edges = null;
        this.boundaryContained = false;
        this.point = point;
    }
    
    public Vertex getPoint() {
        return this.point;
    }
    
    public float getX() {
        return this.point.getX();
    }
    
    public float getY() {
        return this.point.getY();
    }
    
    public float getZ() {
        return this.point.getZ();
    }
    
    public float[] getCoord() {
        return this.point.getCoord();
    }
    
    public void setPoint(final Vertex point) {
        this.point = point;
    }
    
    public ArrayList<HEdge> getEdges() {
        return this.edges;
    }
    
    public void setEdges(final ArrayList<HEdge> edges) {
        this.edges = edges;
    }
    
    public void addEdge(final HEdge hEdge) {
        if (this.edges == null) {
            this.edges = new ArrayList<HEdge>();
        }
        this.edges.add(hEdge);
    }
    
    public void removeEdge(final HEdge hEdge) {
        if (this.edges == null) {
            return;
        }
        this.edges.remove(hEdge);
        if (this.edges.size() == 0) {
            this.edges = null;
        }
    }
    
    public HEdge findNextEdge(final GraphVertex graphVertex) {
        for (int i = 0; i < this.edges.size(); ++i) {
            final HEdge hEdge = this.edges.get(i);
            if (hEdge.getNext().getGraphPoint() == graphVertex) {
                return hEdge;
            }
        }
        return null;
    }
    
    public HEdge findBoundEdge() {
        for (int i = 0; i < this.edges.size(); ++i) {
            final HEdge hEdge = this.edges.get(i);
            if (hEdge.getType() == 3 || hEdge.getType() == 2) {
                return hEdge;
            }
        }
        return null;
    }
    
    public HEdge findPrevEdge(final GraphVertex graphVertex) {
        for (int i = 0; i < this.edges.size(); ++i) {
            final HEdge hEdge = this.edges.get(i);
            if (hEdge.getPrev().getGraphPoint() == graphVertex) {
                return hEdge;
            }
        }
        return null;
    }
    
    public boolean isBoundaryContained() {
        return this.boundaryContained;
    }
    
    public void setBoundaryContained(final boolean boundaryContained) {
        this.boundaryContained = boundaryContained;
    }
    
    @Override
    public String toString() {
        return "GraphVertex[contained " + this.boundaryContained + ", " + this.point + "]";
    }
}
