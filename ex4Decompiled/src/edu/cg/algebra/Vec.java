// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

import java.nio.FloatBuffer;

public class Vec
{
    public float x;
    public float y;
    public float z;
    
    public Vec(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vec(final double x, final double y, final double z) {
        this((float)x, (float)y, (float)z);
    }
    
    public Vec(final float val) {
        this(val, val, val);
    }
    
    public Vec(final double val) {
        this((float)val);
    }
    
    public Vec(final Vec other) {
        this(other.x, other.y, other.z);
    }
    
    public Vec() {
        this(0.0f);
    }
    
    public float norm() {
        return Ops.norm(this);
    }
    
    public float normSqr() {
        return Ops.normSqr(this);
    }
    
    public float length() {
        return Ops.length(this);
    }
    
    public float lengthSqr() {
        return Ops.lengthSqr(this);
    }
    
    public Vec normalize() {
        return Ops.normalize(this);
    }
    
    public Vec neg() {
        return Ops.neg(this);
    }
    
    public float dot(final Vec other) {
        return Ops.dot(this, other);
    }
    
    public Vec cross(final Vec other) {
        return Ops.cross(this, other);
    }
    
    public Vec mult(final float a) {
        return Ops.mult(a, this);
    }
    
    public Vec mult(final double a) {
        return Ops.mult(a, this);
    }
    
    public Vec mult(final Vec v) {
        return Ops.mult(this, v);
    }
    
    public Vec add(final Vec v) {
        return Ops.add(this, v);
    }
    
    public boolean isFinite() {
        return Ops.isFinite(this);
    }
    
    public FloatBuffer toGLColor() {
        return FloatBuffer.wrap(this.clip().toArray());
    }
    
    public float[] toArray() {
        return new float[] { this.x, this.y, this.z };
    }
    
    public Vec clip() {
        return new Vec(clip(this.x), clip(this.y), clip(this.z));
    }
    
    private static float clip(final float val) {
        return Math.min(1.0f, Math.max(0.0f, val));
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
