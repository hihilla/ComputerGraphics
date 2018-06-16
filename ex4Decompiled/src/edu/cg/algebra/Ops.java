// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

public class Ops
{
    public static final float epsilon = 1.0E-5f;
    public static final float infinity = 1.0E8f;
    
    public static float dot(final Vec u, final Vec v) {
        return u.x * v.x + u.y * v.y + u.z * v.z;
    }
    
    public static Vec cross(final Vec u, final Vec v) {
        return new Vec(u.y * v.z - u.z * v.y, u.z * v.x - u.x * v.z, u.x * v.y - u.y * v.x);
    }
    
    public static Vec mult(final float a, final Vec v) {
        return mult(new Vec(a), v);
    }
    
    public static Vec mult(final double a, final Vec v) {
        return mult((float)a, v);
    }
    
    public static Vec mult(final Vec u, final Vec v) {
        return new Vec(u.x * v.x, u.y * v.y, u.z * v.z);
    }
    
    public static Point mult(final float a, final Point p) {
        return mult(new Point(a), p);
    }
    
    public static Point mult(final double a, final Point p) {
        return mult((float)a, p);
    }
    
    public static Point mult(final Point p1, final Point p2) {
        return new Point(p1.x * p2.x, p1.y * p2.y, p1.z * p2.z);
    }
    
    public static float normSqr(final Vec v) {
        return dot(v, v);
    }
    
    public static float norm(final Vec v) {
        return (float)Math.sqrt(normSqr(v));
    }
    
    public static float lengthSqr(final Vec v) {
        return normSqr(v);
    }
    
    public static float length(final Vec v) {
        return norm(v);
    }
    
    public static float dist(final Point p1, final Point p2) {
        return length(sub(p1, p2));
    }
    
    public static float distSqr(final Point p1, final Point p2) {
        return lengthSqr(sub(p1, p2));
    }
    
    public static Vec normalize(final Vec v) {
        return mult(1.0f / norm(v), v);
    }
    
    public static Vec neg(final Vec v) {
        return mult(-1.0f, v);
    }
    
    public static Vec add(final Vec u, final Vec v) {
        return new Vec(u.x + v.x, u.y + v.y, u.z + v.z);
    }
    
    public static Point add(final Point p, final Vec v) {
        return new Point(p.x + v.x, p.y + v.y, p.z + v.z);
    }
    
    public static Point add(final Point p1, final Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }
    
    public static Point add(final Point p, final float t, final Vec v) {
        return add(p, mult(t, v));
    }
    
    public static Point add(final Point p, final double t, final Vec v) {
        return add(p, (float)t, v);
    }
    
    public static Vec sub(final Point p1, final Point p2) {
        return new Vec(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
    }
    
    public static boolean isFinite(final Vec v) {
        return Float.isFinite(v.x) & Float.isFinite(v.y) & Float.isFinite(v.z);
    }
    
    public static boolean isFinite(final Point p) {
        return Float.isFinite(p.x) & Float.isFinite(p.y) & Float.isFinite(p.z);
    }
    
    public static Vec reflect(final Vec u, final Vec normal) {
        return add(u, mult(-2.0f * dot(u, normal), normal));
    }
}
