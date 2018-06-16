// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.curve;

import jogamp.opengl.Debug;
import java.util.List;
import java.util.ArrayList;
import com.jogamp.graph.geom.Triangle;
import jogamp.graph.geom.plane.AffineTransform;
import com.jogamp.graph.geom.Vertex;
import com.jogamp.opengl.math.geom.Frustum;
import com.jogamp.opengl.math.geom.AABBox;

public abstract class Region
{
    public static final boolean DEBUG;
    public static final boolean DEBUG_INSTANCE;
    public static final int MSAA_RENDERING_BIT = 1;
    public static final int VBAA_RENDERING_BIT = 2;
    public static final int VARWEIGHT_RENDERING_BIT = 256;
    public static final int COLORCHANNEL_RENDERING_BIT = 512;
    public static final int COLORTEXTURE_RENDERING_BIT = 1024;
    public static final int MAX_QUALITY = 1;
    public static final int DEFAULT_TWO_PASS_TEXTURE_UNIT = 0;
    protected static final int DIRTY_SHAPE = 1;
    protected static final int DIRTY_STATE = 2;
    private final int renderModes;
    private int quality;
    private int dirty;
    private int numVertices;
    protected final AABBox box;
    protected Frustum frustum;
    final float[] coordsEx;
    private final AABBox tmpBox;
    
    public static boolean isVBAA(final int n) {
        return 0x0 != (n & 0x2);
    }
    
    public static boolean isMSAA(final int n) {
        return 0x0 != (n & 0x1);
    }
    
    public static boolean isTwoPass(final int n) {
        return 0x0 != (n & 0x3);
    }
    
    public static boolean hasVariableWeight(final int n) {
        return 0x0 != (n & 0x100);
    }
    
    public static boolean hasColorChannel(final int n) {
        return 0x0 != (n & 0x200);
    }
    
    public static boolean hasColorTexture(final int n) {
        return 0x0 != (n & 0x400);
    }
    
    public static String getRenderModeString(final int n) {
        final String s = hasVariableWeight(n) ? "-curve" : "";
        final String s2 = hasColorChannel(n) ? "-cols" : "";
        final String s3 = hasColorTexture(n) ? "-ctex" : "";
        if (isVBAA(n)) {
            return "vbaa" + s + s2 + s3;
        }
        if (isMSAA(n)) {
            return "msaa" + s + s2 + s3;
        }
        return "norm" + s + s2 + s3;
    }
    
    protected Region(final int renderModes) {
        this.dirty = 3;
        this.numVertices = 0;
        this.box = new AABBox();
        this.frustum = null;
        this.coordsEx = new float[3];
        this.tmpBox = new AABBox();
        this.renderModes = renderModes;
        this.quality = 1;
    }
    
    protected abstract void pushVertex(final float[] p0, final float[] p1, final float[] p2);
    
    protected abstract void pushIndex(final int p0);
    
    public final int getRenderModes() {
        return this.renderModes;
    }
    
    public final int getQuality() {
        return this.quality;
    }
    
    public final void setQuality(final int quality) {
        this.quality = quality;
    }
    
    protected void clearImpl() {
        this.dirty = 3;
        this.numVertices = 0;
        this.box.reset();
    }
    
    public final boolean isVBAA() {
        return isVBAA(this.renderModes);
    }
    
    public final boolean isMSAA() {
        return isMSAA(this.renderModes);
    }
    
    public final boolean hasVariableWeight() {
        return hasVariableWeight(this.renderModes);
    }
    
    public boolean hasColorChannel() {
        return hasColorChannel(this.renderModes);
    }
    
    public boolean hasColorTexture() {
        return hasColorTexture(this.renderModes);
    }
    
    public final Frustum getFrustum() {
        return this.frustum;
    }
    
    public final void setFrustum(final Frustum frustum) {
        this.frustum = frustum;
    }
    
    private void pushNewVertexImpl(final Vertex vertex, final AffineTransform affineTransform, final float[] array) {
        if (null != affineTransform) {
            final float[] coord = vertex.getCoord();
            affineTransform.transform(coord, this.coordsEx);
            this.coordsEx[2] = coord[2];
            this.box.resize(this.coordsEx[0], this.coordsEx[1], this.coordsEx[2]);
            this.pushVertex(this.coordsEx, vertex.getTexCoord(), array);
        }
        else {
            this.box.resize(vertex.getX(), vertex.getY(), vertex.getZ());
            this.pushVertex(vertex.getCoord(), vertex.getTexCoord(), array);
        }
        ++this.numVertices;
    }
    
    private void pushNewVertexIdxImpl(final Vertex vertex, final AffineTransform affineTransform, final float[] array) {
        this.pushIndex(this.numVertices);
        this.pushNewVertexImpl(vertex, affineTransform, array);
    }
    
    public final void addOutlineShape(final OutlineShape outlineShape, final AffineTransform affineTransform, final float[] array) {
        if (null != this.frustum) {
            final AABBox bounds = outlineShape.getBounds();
            AABBox tmpBox;
            if (null != affineTransform) {
                affineTransform.transform(bounds, this.tmpBox);
                tmpBox = this.tmpBox;
            }
            else {
                tmpBox = bounds;
            }
            if (this.frustum.isAABBoxOutside(tmpBox)) {
                if (Region.DEBUG_INSTANCE) {
                    System.err.println("Region.addOutlineShape(): Dropping outside shapeBoxT: " + tmpBox);
                }
                return;
            }
        }
        final ArrayList<Triangle> triangles = outlineShape.getTriangles(OutlineShape.VerticesState.QUADRATIC_NURBS);
        final ArrayList<Vertex> vertices = outlineShape.getVertices();
        if (Region.DEBUG_INSTANCE) {
            final int addedVerticeCount = outlineShape.getAddedVerticeCount();
            final int n = vertices.size() + addedVerticeCount;
            final int n2 = triangles.size() * 3;
            System.err.println("Region.addOutlineShape().0: tris: " + triangles.size() + ", verts " + vertices.size() + ", transform " + affineTransform);
            System.err.println("Region.addOutlineShape().0: VerticeCount " + vertices.size() + " + " + addedVerticeCount + " = " + n);
            System.err.println("Region.addOutlineShape().0: IndexCount " + n2);
        }
        final int numVertices = this.numVertices;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        if (vertices.size() >= 3) {
            if (Region.DEBUG_INSTANCE) {
                System.err.println("Region.addOutlineShape(): Processing Vertices");
            }
            for (int i = 0; i < vertices.size(); ++i) {
                this.pushNewVertexImpl(vertices.get(i), affineTransform, array);
                ++n3;
            }
            if (Region.DEBUG_INSTANCE) {
                System.err.println("Region.addOutlineShape(): Processing Triangles");
            }
            for (int j = 0; j < triangles.size(); ++j) {
                final Triangle triangle = triangles.get(j);
                if (Region.DEBUG_INSTANCE) {
                    System.err.println("T[" + j + "]: " + triangle);
                }
                final Vertex[] vertices2 = triangle.getVertices();
                final int id = vertices2[0].getId();
                if (Integer.MAX_VALUE - numVertices > id) {
                    if (Region.DEBUG_INSTANCE) {
                        System.err.println("T[" + j + "]: Moved " + id + " + " + numVertices + " -> " + (id + numVertices));
                    }
                    this.pushIndex(id + numVertices);
                    this.pushIndex(vertices2[1].getId() + numVertices);
                    this.pushIndex(vertices2[2].getId() + numVertices);
                    n4 += 3;
                }
                else {
                    if (Region.DEBUG_INSTANCE) {
                        System.err.println("T[" + j + "]: New Idx " + this.numVertices);
                    }
                    this.pushNewVertexIdxImpl(vertices2[0], affineTransform, array);
                    this.pushNewVertexIdxImpl(vertices2[1], affineTransform, array);
                    this.pushNewVertexIdxImpl(vertices2[2], affineTransform, array);
                    n5 += 3;
                }
                ++n6;
            }
        }
        if (Region.DEBUG_INSTANCE) {
            System.err.println("Region.addOutlineShape().X: idxOffset " + numVertices + ", tris: " + n6 + ", verts [idx " + n5 + ", add " + n5 + " = " + (n3 + n5) + "]");
            System.err.println("Region.addOutlineShape().X: verts: idx[v-new " + n3 + ", t-new " + n5 + " = " + (n3 + n5) + "]");
            System.err.println("Region.addOutlineShape().X: verts: idx t-moved " + n4 + ", numVertices " + this.numVertices);
            System.err.println("Region.addOutlineShape().X: verts: v-dups 0, t-dups 0, t-known 0");
            System.err.println("Region.addOutlineShape().X: box " + this.box);
        }
        this.markShapeDirty();
    }
    
    public final void addOutlineShapes(final List<OutlineShape> list, final AffineTransform affineTransform, final float[] array) {
        for (int i = 0; i < list.size(); ++i) {
            this.addOutlineShape(list.get(i), affineTransform, array);
        }
    }
    
    public final AABBox getBounds() {
        return this.box;
    }
    
    public final void markShapeDirty() {
        this.dirty |= 0x1;
    }
    
    public final boolean isShapeDirty() {
        return 0x0 != (this.dirty & 0x1);
    }
    
    public final void markStateDirty() {
        this.dirty |= 0x2;
    }
    
    public final boolean isStateDirty() {
        return 0x0 != (this.dirty & 0x2);
    }
    
    protected final void clearDirtyBits(final int n) {
        this.dirty &= ~n;
    }
    
    protected final int getDirtyBits() {
        return this.dirty;
    }
    
    @Override
    public String toString() {
        return "Region[" + getRenderModeString(this.renderModes) + ", q " + this.quality + ", dirty " + this.dirty + ", vertices " + this.numVertices + ", box " + this.box + "]";
    }
    
    static {
        DEBUG = Debug.debug("graph.curve");
        DEBUG_INSTANCE = Debug.debug("graph.curve.Instance");
    }
}
