// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.geom;

import com.jogamp.opengl.math.FloatUtil;
import jogamp.graph.geom.plane.AffineTransform;
import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.opengl.math.geom.AABBox;
import java.util.ArrayList;

public class Outline implements Comparable<Outline>
{
    private ArrayList<Vertex> vertices;
    private boolean closed;
    private final AABBox bbox;
    private boolean dirtyBBox;
    
    public Outline() {
        this.vertices = new ArrayList<Vertex>(3);
        this.closed = false;
        this.bbox = new AABBox();
        this.dirtyBBox = false;
    }
    
    public Outline(final Outline outline) {
        this.vertices = new ArrayList<Vertex>(outline.vertices.size());
        for (int i = 0; i < this.vertices.size(); ++i) {
            this.vertices.add(outline.vertices.get(i).clone());
        }
        this.closed = outline.closed;
        this.bbox = new AABBox(outline.bbox);
        this.dirtyBBox = outline.dirtyBBox;
    }
    
    public final int getVertexCount() {
        return this.vertices.size();
    }
    
    public final void addVertex(final Vertex vertex) throws NullPointerException {
        this.addVertex(this.vertices.size(), vertex);
    }
    
    public final void addVertex(final int n, final Vertex vertex) throws NullPointerException, IndexOutOfBoundsException {
        if (null == vertex) {
            throw new NullPointerException("vertex is null");
        }
        this.vertices.add(n, vertex);
        if (!this.dirtyBBox) {
            this.bbox.resize(vertex.getCoord());
        }
    }
    
    public final void setVertex(final int n, final Vertex vertex) throws NullPointerException, IndexOutOfBoundsException {
        if (null == vertex) {
            throw new NullPointerException("vertex is null");
        }
        this.vertices.set(n, vertex);
        this.dirtyBBox = true;
    }
    
    public final Vertex getVertex(final int n) {
        return this.vertices.get(n);
    }
    
    public int getVertexIndex(final Vertex vertex) {
        return this.vertices.indexOf(vertex);
    }
    
    public final Vertex removeVertex(final int n) throws IndexOutOfBoundsException {
        this.dirtyBBox = true;
        return this.vertices.remove(n);
    }
    
    public final boolean isEmpty() {
        return this.vertices.size() == 0;
    }
    
    public final Vertex getLastVertex() {
        if (this.isEmpty()) {
            return null;
        }
        return this.vertices.get(this.vertices.size() - 1);
    }
    
    public final ArrayList<Vertex> getVertices() {
        return this.vertices;
    }
    
    public final void setVertices(final ArrayList<Vertex> vertices) {
        this.vertices = vertices;
        this.validateBoundingBox();
    }
    
    public final boolean isClosed() {
        return this.closed;
    }
    
    public final boolean setClosed(final boolean b) {
        this.closed = true;
        if (!this.isEmpty()) {
            final Vertex vertex = this.vertices.get(0);
            final Vertex lastVertex = this.getLastVertex();
            if (!VectorUtil.isVec3Equal(vertex.getCoord(), 0, lastVertex.getCoord(), 0, 1.1920929E-7f)) {
                if (b) {
                    this.vertices.add(vertex.clone());
                }
                else {
                    this.vertices.add(0, lastVertex.clone());
                }
                return true;
            }
        }
        return false;
    }
    
    public final Outline transform(final AffineTransform affineTransform, final Vertex.Factory<? extends Vertex> factory) {
        final Outline outline = new Outline();
        for (int size = this.vertices.size(), i = 0; i < size; ++i) {
            outline.addVertex(affineTransform.transform(this.vertices.get(i), (Vertex)factory.create()));
        }
        outline.closed = this.closed;
        return outline;
    }
    
    private final void validateBoundingBox() {
        this.dirtyBBox = false;
        this.bbox.reset();
        for (int i = 0; i < this.vertices.size(); ++i) {
            this.bbox.resize(this.vertices.get(i).getCoord());
        }
    }
    
    public final AABBox getBounds() {
        if (this.dirtyBBox) {
            this.validateBoundingBox();
        }
        return this.bbox;
    }
    
    @Override
    public final int compareTo(final Outline outline) {
        final float size = this.getBounds().getSize();
        final float size2 = outline.getBounds().getSize();
        if (FloatUtil.isEqual(size, size2, 1.1920929E-7f)) {
            return 0;
        }
        if (size < size2) {
            return -1;
        }
        return 1;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (null == o || !(o instanceof Outline)) {
            return false;
        }
        final Outline outline = (Outline)o;
        if (this.getVertexCount() != outline.getVertexCount()) {
            return false;
        }
        if (!this.getBounds().equals(outline.getBounds())) {
            return false;
        }
        for (int i = this.getVertexCount() - 1; i >= 0; --i) {
            if (!this.getVertex(i).equals(outline.getVertex(i))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public final int hashCode() {
        throw new InternalError("hashCode not designed");
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "@" + Integer.toHexString(super.hashCode());
    }
}
