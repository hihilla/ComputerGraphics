// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.curve.tess;

import com.jogamp.graph.geom.Vertex;
import java.util.ArrayList;
import com.jogamp.graph.geom.Outline;

public class GraphOutline
{
    private final Outline outline;
    private final ArrayList<GraphVertex> controlpoints;
    
    public GraphOutline() {
        this.controlpoints = new ArrayList<GraphVertex>(3);
        this.outline = new Outline();
    }
    
    public GraphOutline(final Outline outline) {
        this.controlpoints = new ArrayList<GraphVertex>(3);
        this.outline = outline;
        final ArrayList<Vertex> vertices = this.outline.getVertices();
        for (int i = 0; i < vertices.size(); ++i) {
            this.controlpoints.add(new GraphVertex(vertices.get(i)));
        }
    }
    
    public Outline getOutline() {
        return this.outline;
    }
    
    public ArrayList<GraphVertex> getGraphPoint() {
        return this.controlpoints;
    }
    
    public ArrayList<Vertex> getVertices() {
        return this.outline.getVertices();
    }
    
    public void addVertex(final GraphVertex graphVertex) {
        this.controlpoints.add(graphVertex);
        this.outline.addVertex(graphVertex.getPoint());
    }
}
