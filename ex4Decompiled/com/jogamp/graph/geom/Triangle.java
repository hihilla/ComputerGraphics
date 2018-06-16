// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.geom;

import com.jogamp.opengl.math.VectorUtil;
import jogamp.graph.geom.plane.AffineTransform;

public class Triangle
{
    private final Vertex[] vertices;
    private final boolean[] boundaryEdges;
    private boolean[] boundaryVertices;
    private int id;
    
    public Triangle(final Vertex vertex, final Vertex vertex2, final Vertex vertex3, final boolean[] boundaryVertices) {
        this.vertices = new Vertex[3];
        this.boundaryEdges = new boolean[3];
        this.boundaryVertices = null;
        this.id = Integer.MAX_VALUE;
        this.vertices[0] = vertex;
        this.vertices[1] = vertex2;
        this.vertices[2] = vertex3;
        this.boundaryVertices = boundaryVertices;
    }
    
    public Triangle(final Triangle triangle) {
        this.vertices = new Vertex[3];
        this.boundaryEdges = new boolean[3];
        this.boundaryVertices = null;
        this.id = triangle.id;
        this.vertices[0] = triangle.vertices[0].clone();
        this.vertices[1] = triangle.vertices[1].clone();
        this.vertices[2] = triangle.vertices[2].clone();
        System.arraycopy(triangle.boundaryEdges, 0, this.boundaryEdges, 0, 3);
        this.boundaryVertices = new boolean[3];
        System.arraycopy(triangle.boundaryVertices, 0, this.boundaryVertices, 0, 3);
    }
    
    private Triangle(final int id, final boolean[] array, final boolean[] array2) {
        this.vertices = new Vertex[3];
        this.boundaryEdges = new boolean[3];
        this.boundaryVertices = null;
        this.id = id;
        System.arraycopy(array, 0, this.boundaryEdges, 0, 3);
        System.arraycopy(array2, 0, this.boundaryVertices = new boolean[3], 0, 3);
    }
    
    public Triangle transform(final AffineTransform affineTransform, final Vertex.Factory<? extends Vertex> factory) {
        final Triangle triangle = new Triangle(this.id, this.boundaryEdges, this.boundaryVertices);
        triangle.vertices[0] = affineTransform.transform(this.vertices[0], (Vertex)factory.create());
        triangle.vertices[1] = affineTransform.transform(this.vertices[1], (Vertex)factory.create());
        triangle.vertices[2] = affineTransform.transform(this.vertices[2], (Vertex)factory.create());
        return triangle;
    }
    
    public final boolean isOnCurve() {
        return this.vertices[0].isOnCurve() && this.vertices[1].isOnCurve() && this.vertices[2].isOnCurve();
    }
    
    public final boolean isLine() {
        return VectorUtil.isVec2Zero(this.vertices[0].getTexCoord(), 0) && VectorUtil.isVec2Zero(this.vertices[1].getTexCoord(), 0) && VectorUtil.isVec2Zero(this.vertices[2].getTexCoord(), 0);
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public Vertex[] getVertices() {
        return this.vertices;
    }
    
    public boolean isEdgesBoundary() {
        return this.boundaryEdges[0] || this.boundaryEdges[1] || this.boundaryEdges[2];
    }
    
    public boolean isVerticesBoundary() {
        return this.boundaryVertices[0] || this.boundaryVertices[1] || this.boundaryVertices[2];
    }
    
    public boolean[] getEdgeBoundary() {
        return this.boundaryEdges;
    }
    
    public boolean[] getVerticesBoundary() {
        return this.boundaryVertices;
    }
    
    public void setVerticesBoundary(final boolean[] boundaryVertices) {
        this.boundaryVertices = boundaryVertices;
    }
    
    @Override
    public String toString() {
        return "Tri ID: " + this.id + ", onCurve " + this.isOnCurve() + "\n\t" + this.vertices[0] + ", bound " + this.boundaryVertices[0] + "\n\t" + this.vertices[1] + ", bound " + this.boundaryVertices[1] + "\n\t" + this.vertices[2] + ", bound " + this.boundaryVertices[2];
    }
}
