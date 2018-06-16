// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.geom;

import com.jogamp.opengl.math.VectorUtil;

public class SVertex implements Vertex
{
    private int id;
    protected boolean onCurve;
    protected final float[] coord;
    private final float[] texCoord;
    static final Factory factory;
    
    public static Factory factory() {
        return SVertex.factory;
    }
    
    public SVertex() {
        this.coord = new float[3];
        this.texCoord = new float[3];
        this.id = Integer.MAX_VALUE;
    }
    
    public SVertex(final Vertex vertex) {
        this.coord = new float[3];
        this.texCoord = new float[3];
        this.id = Integer.MAX_VALUE;
        System.arraycopy(vertex.getCoord(), 0, this.coord, 0, 3);
        System.arraycopy(vertex.getTexCoord(), 0, this.texCoord, 0, 3);
        this.setOnCurve(vertex.isOnCurve());
    }
    
    public SVertex(final int id, final boolean onCurve, final float[] array) {
        this.coord = new float[3];
        this.texCoord = new float[3];
        this.id = id;
        this.onCurve = onCurve;
        System.arraycopy(array, 0, this.texCoord, 0, 3);
    }
    
    public SVertex(final float n, final float n2, final float n3, final boolean onCurve) {
        this.coord = new float[3];
        this.texCoord = new float[3];
        this.id = Integer.MAX_VALUE;
        this.setCoord(n, n2, n3);
        this.setOnCurve(onCurve);
    }
    
    public SVertex(final float[] array, final int n, final int n2, final boolean onCurve) {
        this.coord = new float[3];
        this.texCoord = new float[3];
        this.id = Integer.MAX_VALUE;
        this.setCoord(array, n, n2);
        this.setOnCurve(onCurve);
    }
    
    @Override
    public final void setCoord(final float n, final float n2, final float n3) {
        this.coord[0] = n;
        this.coord[1] = n2;
        this.coord[2] = n3;
    }
    
    @Override
    public final void setCoord(final float[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.coord, 0, n2);
    }
    
    @Override
    public int getCoordCount() {
        return 3;
    }
    
    @Override
    public final float[] getCoord() {
        return this.coord;
    }
    
    @Override
    public final void setX(final float n) {
        this.coord[0] = n;
    }
    
    @Override
    public final void setY(final float n) {
        this.coord[1] = n;
    }
    
    @Override
    public final void setZ(final float n) {
        this.coord[2] = n;
    }
    
    @Override
    public final float getX() {
        return this.coord[0];
    }
    
    @Override
    public final float getY() {
        return this.coord[1];
    }
    
    @Override
    public final float getZ() {
        return this.coord[2];
    }
    
    @Override
    public final boolean isOnCurve() {
        return this.onCurve;
    }
    
    @Override
    public final void setOnCurve(final boolean onCurve) {
        this.onCurve = onCurve;
    }
    
    @Override
    public final int getId() {
        return this.id;
    }
    
    @Override
    public final void setId(final int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (null == o || !(o instanceof Vertex)) {
            return false;
        }
        final Vertex vertex = (Vertex)o;
        return this == vertex || (this.isOnCurve() == vertex.isOnCurve() && VectorUtil.isVec3Equal(this.getTexCoord(), 0, vertex.getTexCoord(), 0, 1.1920929E-7f) && VectorUtil.isVec3Equal(this.getCoord(), 0, vertex.getCoord(), 0, 1.1920929E-7f));
    }
    
    @Override
    public final int hashCode() {
        throw new InternalError("hashCode not designed");
    }
    
    @Override
    public final float[] getTexCoord() {
        return this.texCoord;
    }
    
    @Override
    public final void setTexCoord(final float n, final float n2, final float n3) {
        this.texCoord[0] = n;
        this.texCoord[1] = n2;
        this.texCoord[2] = n3;
    }
    
    @Override
    public final void setTexCoord(final float[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.texCoord, 0, n2);
    }
    
    @Override
    public SVertex clone() {
        return new SVertex(this);
    }
    
    @Override
    public String toString() {
        return "[ID: " + this.id + ", onCurve: " + this.onCurve + ": p " + this.coord[0] + ", " + this.coord[1] + ", " + this.coord[2] + ", t " + this.texCoord[0] + ", " + this.texCoord[1] + ", " + this.texCoord[2] + "]";
    }
    
    static {
        factory = new Factory();
    }
    
    public static class Factory implements Vertex.Factory<SVertex>
    {
        @Override
        public SVertex create() {
            return new SVertex();
        }
        
        @Override
        public SVertex create(final Vertex vertex) {
            return new SVertex(vertex);
        }
        
        @Override
        public SVertex create(final int n, final boolean b, final float[] array) {
            return new SVertex(n, b, array);
        }
        
        @Override
        public SVertex create(final float n, final float n2, final float n3, final boolean b) {
            return new SVertex(n, n2, n3, b);
        }
        
        @Override
        public SVertex create(final float[] array, final int n, final int n2, final boolean b) {
            return new SVertex(array, n, n2, b);
        }
    }
}
