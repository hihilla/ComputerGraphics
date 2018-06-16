// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

import java.nio.FloatBuffer;

public class Point
{
    public float x;
    public float y;
    public float z;
    
    public Point(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point(final double x, final double y, final double z) {
        this((float)x, (float)y, (float)z);
    }
    
    public Point(final Point other) {
        this(other.x, other.y, other.z);
    }
    
    public Point(final float val) {
        this(val, val, val);
    }
    
    public Point(final double val) {
        this((float)val);
    }
    
    public Point() {
        this(0.0f);
    }
    
    public Point add(final float t, final Vec v) {
        return Ops.add(this, t, v);
    }
    
    public Point add(final double t, final Vec v) {
        return Ops.add(this, t, v);
    }
    
    public Point add(final Vec v) {
        return Ops.add(this, v);
    }
    
    public Point mult(final float a) {
        return Ops.mult(a, this);
    }
    
    public Point mult(final Point p) {
        return Ops.mult(this, p);
    }
    
    public Point add(final Point p) {
        return Ops.add(this, p);
    }
    
    public float dist(final Point other) {
        return Ops.dist(this, other);
    }
    
    public float distSqr(final Point other) {
        return Ops.distSqr(this, other);
    }
    
    public Vec sub(final Point other) {
        return Ops.sub(this, other);
    }
    
    public Vec toVec() {
        return this.sub(new Point());
    }
    
    public FloatBuffer toGLVertex() {
        return FloatBuffer.wrap(this.toArray());
    }
    
    public float[] toArray() {
        return new float[] { this.x, this.y, this.z };
    }
    
    public boolean isFinite() {
        return Ops.isFinite(this);
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
