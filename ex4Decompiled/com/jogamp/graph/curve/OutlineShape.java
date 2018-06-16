// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.curve;

import com.jogamp.opengl.math.FloatUtil;
import java.util.Collections;
import jogamp.graph.geom.plane.AffineTransform;
import com.jogamp.graph.curve.tess.Triangulator;
import java.util.List;
import com.jogamp.graph.curve.tess.Triangulation;
import java.util.Collection;
import com.jogamp.opengl.math.Vert2fImmutable;
import com.jogamp.opengl.math.VectorUtil;
import java.util.Comparator;
import com.jogamp.graph.geom.Triangle;
import com.jogamp.opengl.math.geom.AABBox;
import com.jogamp.graph.geom.Outline;
import java.util.ArrayList;
import com.jogamp.graph.geom.Vertex;

public class OutlineShape implements Comparable<OutlineShape>
{
    public static final float DEFAULT_SHARPNESS = 0.5f;
    public static final int DIRTY_BOUNDS = 1;
    public static final int DIRTY_VERTICES = 2;
    public static final int DIRTY_TRIANGLES = 4;
    private final Vertex.Factory<? extends Vertex> vertexFactory;
    final ArrayList<Outline> outlines;
    private final AABBox bbox;
    private final ArrayList<Triangle> triangles;
    private final ArrayList<Vertex> vertices;
    private int addedVerticeCount;
    private VerticesState outlineState;
    private int dirtyBits;
    private float sharpness;
    private final float[] tmpV1;
    private final float[] tmpV2;
    private final float[] tmpV3;
    private static Comparator<Outline> reversSizeComparator;
    
    public OutlineShape(final Vertex.Factory<? extends Vertex> vertexFactory) {
        this.tmpV1 = new float[3];
        this.tmpV2 = new float[3];
        this.tmpV3 = new float[3];
        this.vertexFactory = vertexFactory;
        (this.outlines = new ArrayList<Outline>(3)).add(new Outline());
        this.outlineState = VerticesState.UNDEFINED;
        this.bbox = new AABBox();
        this.triangles = new ArrayList<Triangle>();
        this.vertices = new ArrayList<Vertex>();
        this.addedVerticeCount = 0;
        this.dirtyBits = 0;
        this.sharpness = 0.5f;
    }
    
    public int getAddedVerticeCount() {
        return this.addedVerticeCount;
    }
    
    public float getSharpness() {
        return this.sharpness;
    }
    
    public void setSharpness(final float sharpness) {
        if (this.sharpness != sharpness) {
            this.clearCache();
            this.sharpness = sharpness;
        }
    }
    
    public void clear() {
        this.outlines.clear();
        this.outlines.add(new Outline());
        this.outlineState = VerticesState.UNDEFINED;
        this.bbox.reset();
        this.vertices.clear();
        this.triangles.clear();
        this.addedVerticeCount = 0;
        this.dirtyBits = 0;
    }
    
    public void clearCache() {
        this.vertices.clear();
        this.triangles.clear();
        this.dirtyBits |= 0x6;
    }
    
    public final Vertex.Factory<? extends Vertex> vertexFactory() {
        return this.vertexFactory;
    }
    
    public final int getOutlineNumber() {
        return this.outlines.size();
    }
    
    public final void addEmptyOutline() {
        if (!this.getLastOutline().isEmpty()) {
            this.outlines.add(new Outline());
        }
    }
    
    public final void addOutline(final Outline outline) throws NullPointerException {
        this.addOutline(this.outlines.size(), outline);
    }
    
    public final void addOutline(final int n, final Outline outline) throws NullPointerException, IndexOutOfBoundsException {
        if (null == outline) {
            throw new NullPointerException("outline is null");
        }
        if (this.outlines.size() == n) {
            final Outline lastOutline = this.getLastOutline();
            if (outline.isEmpty() && lastOutline.isEmpty()) {
                return;
            }
            if (lastOutline.isEmpty()) {
                this.outlines.set(n - 1, outline);
                if (0x0 == (this.dirtyBits & 0x1)) {
                    this.bbox.resize(outline.getBounds());
                }
                this.dirtyBits |= 0x6;
                return;
            }
        }
        this.outlines.add(n, outline);
        if (0x0 == (this.dirtyBits & 0x1)) {
            this.bbox.resize(outline.getBounds());
        }
        this.dirtyBits |= 0x6;
    }
    
    public final void addOutlineShape(final OutlineShape outlineShape) throws NullPointerException {
        if (null == outlineShape) {
            throw new NullPointerException("OutlineShape is null");
        }
        this.closeLastOutline(true);
        for (int i = 0; i < outlineShape.getOutlineNumber(); ++i) {
            this.addOutline(outlineShape.getOutline(i));
        }
    }
    
    public final void setOutline(final int n, final Outline outline) throws NullPointerException, IndexOutOfBoundsException {
        if (null == outline) {
            throw new NullPointerException("outline is null");
        }
        this.outlines.set(n, outline);
        this.dirtyBits |= 0x7;
    }
    
    public final Outline removeOutline(final int n) throws IndexOutOfBoundsException {
        this.dirtyBits |= 0x7;
        return this.outlines.remove(n);
    }
    
    public final Outline getLastOutline() {
        return this.outlines.get(this.outlines.size() - 1);
    }
    
    public final Outline getOutline(final int n) throws IndexOutOfBoundsException {
        return this.outlines.get(n);
    }
    
    public final void addVertex(final Vertex vertex) {
        this.getLastOutline().addVertex(vertex);
        if (0x0 == (this.dirtyBits & 0x1)) {
            this.bbox.resize(vertex.getCoord());
        }
        this.dirtyBits |= 0x6;
    }
    
    public final void addVertex(final int n, final Vertex vertex) {
        this.getLastOutline().addVertex(n, vertex);
        if (0x0 == (this.dirtyBits & 0x1)) {
            this.bbox.resize(vertex.getCoord());
        }
        this.dirtyBits |= 0x6;
    }
    
    public final void addVertex(final float n, final float n2, final boolean b) {
        this.addVertex((Vertex)this.vertexFactory.create(n, n2, 0.0f, b));
    }
    
    public final void addVertex(final float n, final float n2, final float n3, final boolean b) {
        this.addVertex((Vertex)this.vertexFactory.create(n, n2, n3, b));
    }
    
    public final void addVertex(final float[] array, final int n, final int n2, final boolean b) {
        this.addVertex((Vertex)this.vertexFactory.create(array, n, n2, b));
    }
    
    public final void closeLastOutline(final boolean b) {
        if (this.getLastOutline().setClosed(true)) {
            this.dirtyBits |= 0x6;
        }
    }
    
    public final VerticesState getOutlineState() {
        return this.outlineState;
    }
    
    public final void setIsQuadraticNurbs() {
        this.outlineState = VerticesState.QUADRATIC_NURBS;
    }
    
    private void subdivideTriangle(final Outline outline, final Vertex vertex, final Vertex vertex2, final Vertex vertex3, final int n) {
        VectorUtil.midVec3(this.tmpV1, vertex.getCoord(), vertex2.getCoord());
        VectorUtil.midVec3(this.tmpV3, vertex2.getCoord(), vertex3.getCoord());
        VectorUtil.midVec3(this.tmpV2, this.tmpV1, this.tmpV3);
        vertex2.setCoord(this.tmpV2, 0, 3);
        vertex2.setOnCurve(true);
        outline.addVertex(n, (Vertex)this.vertexFactory.create(this.tmpV1, 0, 3, false));
        outline.addVertex(n + 2, (Vertex)this.vertexFactory.create(this.tmpV3, 0, 3, false));
        this.addedVerticeCount += 2;
    }
    
    private void checkOverlaps() {
        final ArrayList<Vertex> list = new ArrayList<Vertex>(3);
        final int outlineNumber = this.getOutlineNumber();
        int n = 1;
        do {
            for (int i = 0; i < outlineNumber; ++i) {
                final Outline outline = this.getOutline(i);
                int vertexCount = outline.getVertexCount();
                for (int j = 0; j < outline.getVertexCount(); ++j) {
                    final Vertex vertex = outline.getVertex(j);
                    if (!vertex.isOnCurve()) {
                        final Vertex vertex2 = outline.getVertex((j + 1) % vertexCount);
                        final Vertex vertex3 = outline.getVertex((j + vertexCount - 1) % vertexCount);
                        Vertex checkTriOverlaps0;
                        if (n != 0) {
                            checkTriOverlaps0 = this.checkTriOverlaps0(vertex3, vertex, vertex2);
                        }
                        else {
                            checkTriOverlaps0 = null;
                        }
                        if (list.contains(vertex) || checkTriOverlaps0 != null) {
                            list.remove(vertex);
                            this.subdivideTriangle(outline, vertex3, vertex, vertex2, j);
                            j += 3;
                            vertexCount += 2;
                            this.addedVerticeCount += 2;
                            if (checkTriOverlaps0 != null && !checkTriOverlaps0.isOnCurve() && !list.contains(checkTriOverlaps0)) {
                                list.add(checkTriOverlaps0);
                            }
                        }
                    }
                }
            }
            n = 0;
        } while (!list.isEmpty());
    }
    
    private Vertex checkTriOverlaps0(final Vertex vertex, final Vertex vertex2, final Vertex vertex3) {
        for (int outlineNumber = this.getOutlineNumber(), i = 0; i < outlineNumber; ++i) {
            final Outline outline = this.getOutline(i);
            for (int vertexCount = outline.getVertexCount(), j = 0; j < vertexCount; ++j) {
                final Vertex vertex4 = outline.getVertex(j);
                if (!vertex4.isOnCurve() && vertex4 != vertex && vertex4 != vertex2) {
                    if (vertex4 != vertex3) {
                        final Vertex vertex5 = outline.getVertex((j + 1) % vertexCount);
                        final Vertex vertex6 = outline.getVertex((j + vertexCount - 1) % vertexCount);
                        if (vertex6 != vertex3) {
                            if (vertex5 != vertex) {
                                if (VectorUtil.isVec3InTriangle3(vertex.getCoord(), vertex2.getCoord(), vertex3.getCoord(), vertex4.getCoord(), vertex5.getCoord(), vertex6.getCoord(), this.tmpV1, this.tmpV2, this.tmpV3)) {
                                    return vertex4;
                                }
                                if (VectorUtil.testTri2SegIntersection(vertex, vertex2, vertex3, vertex6, vertex4) || VectorUtil.testTri2SegIntersection(vertex, vertex2, vertex3, vertex4, vertex5) || VectorUtil.testTri2SegIntersection(vertex, vertex2, vertex3, vertex6, vertex5)) {
                                    return vertex4;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private Vertex checkTriOverlaps1(final Vertex vertex, final Vertex vertex2, final Vertex vertex3) {
        for (int outlineNumber = this.getOutlineNumber(), i = 0; i < outlineNumber; ++i) {
            final Outline outline = this.getOutline(i);
            for (int vertexCount = outline.getVertexCount(), j = 0; j < vertexCount; ++j) {
                final Vertex vertex4 = outline.getVertex(j);
                if (!vertex4.isOnCurve() && vertex4 != vertex && vertex4 != vertex2) {
                    if (vertex4 != vertex3) {
                        final Vertex vertex5 = outline.getVertex((j + 1) % vertexCount);
                        final Vertex vertex6 = outline.getVertex((j + vertexCount - 1) % vertexCount);
                        if (vertex6 != vertex3) {
                            if (vertex5 != vertex) {
                                if (VectorUtil.isVec3InTriangle3(vertex.getCoord(), vertex2.getCoord(), vertex3.getCoord(), vertex4.getCoord(), vertex5.getCoord(), vertex6.getCoord(), this.tmpV1, this.tmpV2, this.tmpV3, 1.1920929E-7f)) {
                                    return vertex4;
                                }
                                if (VectorUtil.testTri2SegIntersection(vertex, vertex2, vertex3, vertex6, vertex4, 1.1920929E-7f) || VectorUtil.testTri2SegIntersection(vertex, vertex2, vertex3, vertex4, vertex5, 1.1920929E-7f) || VectorUtil.testTri2SegIntersection(vertex, vertex2, vertex3, vertex6, vertex5, 1.1920929E-7f)) {
                                    return vertex4;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private void cleanupOutlines() {
        final boolean b = VerticesState.QUADRATIC_NURBS != this.outlineState;
        for (int outlineNumber = this.getOutlineNumber(), i = 0; i < outlineNumber; ++i) {
            final Outline outline = this.getOutline(i);
            int vertexCount = outline.getVertexCount();
            if (b) {
                for (int j = 0; j < vertexCount; ++j) {
                    final Vertex vertex = outline.getVertex(j);
                    final int n = (j + 1) % vertexCount;
                    final Vertex vertex2 = outline.getVertex(n);
                    if (!vertex.isOnCurve() && !vertex2.isOnCurve()) {
                        VectorUtil.midVec3(this.tmpV1, vertex.getCoord(), vertex2.getCoord());
                        System.err.println("XXX: Cubic: " + j + ": " + vertex + ", " + n + ": " + vertex2);
                        final Vertex create = (Vertex)this.vertexFactory.create(this.tmpV1, 0, 3, true);
                        ++j;
                        ++vertexCount;
                        ++this.addedVerticeCount;
                        outline.addVertex(j, create);
                    }
                }
            }
            if (0 >= vertexCount) {
                this.outlines.remove(outline);
                --i;
                --outlineNumber;
            }
            else if (0 < vertexCount && VectorUtil.isVec3Equal(outline.getVertex(0).getCoord(), 0, outline.getLastVertex().getCoord(), 0, 1.1920929E-7f)) {
                outline.removeVertex(vertexCount - 1);
            }
        }
        this.outlineState = VerticesState.QUADRATIC_NURBS;
        this.checkOverlaps();
    }
    
    private int generateVertexIds() {
        int n = 0;
        for (int i = 0; i < this.outlines.size(); ++i) {
            final ArrayList<Vertex> vertices = this.outlines.get(i).getVertices();
            for (int j = 0; j < vertices.size(); ++j) {
                vertices.get(j).setId(n++);
            }
        }
        return n;
    }
    
    public final ArrayList<Vertex> getVertices() {
        boolean b;
        if (0x0 != (0x2 & this.dirtyBits)) {
            this.vertices.clear();
            for (int i = 0; i < this.outlines.size(); ++i) {
                this.vertices.addAll(this.outlines.get(i).getVertices());
            }
            this.dirtyBits &= 0xFFFFFFFD;
            b = true;
        }
        else {
            b = false;
        }
        if (Region.DEBUG_INSTANCE) {
            System.err.println("OutlineShape.getVertices(): o " + this.outlines.size() + ", v " + this.vertices.size() + ", updated " + b);
        }
        return this.vertices;
    }
    
    private void triangulateImpl() {
        if (0 < this.outlines.size()) {
            this.sortOutlines();
            this.generateVertexIds();
            this.triangles.clear();
            final Triangulator create = Triangulation.create();
            for (int i = 0; i < this.outlines.size(); ++i) {
                create.addCurve(this.triangles, this.outlines.get(i), this.sharpness);
            }
            create.generate(this.triangles);
            this.addedVerticeCount += create.getAddedVerticeCount();
            create.reset();
        }
    }
    
    public ArrayList<Triangle> getTriangles(final VerticesState verticesState) {
        if (verticesState != VerticesState.QUADRATIC_NURBS) {
            throw new IllegalStateException("destinationType " + verticesState.name() + " not supported (currently " + this.outlineState.name() + ")");
        }
        boolean b;
        if (0x0 != (0x4 & this.dirtyBits)) {
            this.cleanupOutlines();
            this.triangulateImpl();
            b = true;
            this.dirtyBits |= 0x2;
            this.dirtyBits &= 0xFFFFFFFB;
        }
        else {
            b = false;
        }
        if (Region.DEBUG_INSTANCE) {
            System.err.println("OutlineShape.getTriangles().X: " + this.triangles.size() + ", updated " + b);
        }
        return this.triangles;
    }
    
    public final OutlineShape transform(final AffineTransform affineTransform) {
        final OutlineShape outlineShape = new OutlineShape(this.vertexFactory);
        for (int size = this.outlines.size(), i = 0; i < size; ++i) {
            outlineShape.addOutline(this.outlines.get(i).transform(affineTransform, this.vertexFactory));
        }
        return outlineShape;
    }
    
    private void sortOutlines() {
        Collections.sort(this.outlines, OutlineShape.reversSizeComparator);
    }
    
    @Override
    public final int compareTo(final OutlineShape outlineShape) {
        final float size = this.getBounds().getSize();
        final float size2 = outlineShape.getBounds().getSize();
        if (FloatUtil.isEqual(size, size2, 1.1920929E-7f)) {
            return 0;
        }
        if (size < size2) {
            return -1;
        }
        return 1;
    }
    
    private void validateBoundingBox() {
        this.dirtyBits &= 0xFFFFFFFE;
        this.bbox.reset();
        for (int i = 0; i < this.outlines.size(); ++i) {
            this.bbox.resize(this.outlines.get(i).getBounds());
        }
    }
    
    public final AABBox getBounds() {
        if (0x0 == (this.dirtyBits & 0x1)) {
            this.validateBoundingBox();
        }
        return this.bbox;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (null == o || !(o instanceof OutlineShape)) {
            return false;
        }
        final OutlineShape outlineShape = (OutlineShape)o;
        if (this.getOutlineState() != outlineShape.getOutlineState()) {
            return false;
        }
        if (this.getOutlineNumber() != outlineShape.getOutlineNumber()) {
            return false;
        }
        if (!this.getBounds().equals(outlineShape.getBounds())) {
            return false;
        }
        for (int i = this.getOutlineNumber() - 1; i >= 0; --i) {
            if (!this.getOutline(i).equals(outlineShape.getOutline(i))) {
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
    
    static {
        OutlineShape.reversSizeComparator = new Comparator<Outline>() {
            @Override
            public int compare(final Outline outline, final Outline outline2) {
                return outline2.compareTo(outline);
            }
        };
    }
    
    public enum VerticesState
    {
        UNDEFINED(0), 
        QUADRATIC_NURBS(1);
        
        public final int state;
        
        private VerticesState(final int state) {
            this.state = state;
        }
    }
}
