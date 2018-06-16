// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import edu.cg.algebra.Vec;
import Jama.Matrix;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import edu.cg.algebra.Point;

public class TrackSegment
{
    private double length;
    private Spline spline;
    
    private TrackSegment() {
        this.length = 0.0;
        this.spline = null;
    }
    
    private TrackSegment(final Spline spline) {
        this.length = 0.0;
        this.spline = null;
        this.spline = spline;
        this.length = spline.length();
    }
    
    public static CyclicList<TrackSegment> calcTrackSegmants(final CyclicList<Point> points) {
        final CyclicList<Poly> xs = calcPolys(points, p -> p.x);
        final CyclicList<Poly> ys = calcPolys(points, p -> p.y);
        final CyclicList<Poly> zs = calcPolys(points, p -> p.z);
        final int size = points.size();
        final CyclicList<TrackSegment> ans = new CyclicList<TrackSegment>();
        for (int i = 0; i < size; ++i) {
            final Poly px = xs.get(i);
            final Poly py = ys.get(i);
            final Poly pz = zs.get(i);
            ans.add(new TrackSegment(new Spline(px, py, pz)));
        }
        return ans;
    }
    
    private static CyclicList<Poly> calcPolys(final CyclicList<Point> points, final Selector sel) {
        final int size = points.size();
        final List<Constraint> constraints = new ArrayList<Constraint>(size * 4);

        for (int i = 0; i < size; ++i) {
            final float p = sel.select(points.get(i));
            constraints.addAll(Constraint.calcConstraints(p, i, size));
        }

        final float[] polys = solve(constraints);
        final CyclicList<Poly> ans = new CyclicList<Poly>();
        int j = 0;
        while (j < polys.length) {
            final float a = polys[j++];
            final float b = polys[j++];
            final float c = polys[j++];
            final float d = polys[j++];
            ans.add(new Poly(a, b, c, d));
        }
        return ans;
    }
    
    private static float[] solve(final List<Constraint> constraints) {
        final int size = constraints.size();
        final double[][] A = new double[size][0];
        final double[] b = new double[size];
        for (int i = 0; i < size; ++i) {
            final Constraint constraint = constraints.get(i);
            A[i] = constraint.Ai();
            b[i] = constraint.b();
        }
        final Matrix AMat = new Matrix(A);
        final Matrix bMat = new Matrix(b, size);
        final Matrix sol = AMat.solve(bMat);
        final float[] ans = new float[size];
        for (int j = 0; j < size; ++j) {
            ans[j] = (float)sol.get(j, 0);
        }
        return ans;
    }
    
    public LocationOnSegment getLocationOnSegment(final double t) {
        final Point pos = this.spline.getPosition(t);
        final Vec tangent = this.spline.getTangent(t);
        final Vec normal = this.spline.getNormal(t);
        return new LocationOnSegment(pos, tangent, normal);
    }
    
    public double length() {
        return this.length;
    }
    
    private static class Poly
    {
        private float a;
        private float b;
        private float c;
        private float d;
        
        public Poly(final float a, final float b, final float c, final float d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
        
        public float p(final double t) {
            return (float)(this.d + (this.c + (this.b + this.a * t) * t) * t);
        }
        
        public float dp(final double t) {
            return (float)(this.c + (2.0f * this.b + 3.0f * this.a * t) * t);
        }
        
        public float d2p(final double t) {
            return (float)(2.0f * this.b + 6.0f * this.a * t);
        }
    }
    
    private static class Spline
    {
        private Poly[] polys;
        
        public Spline(final Poly px, final Poly py, final Poly pz) {
            (this.polys = new Poly[3])[0] = px;
            this.polys[1] = py;
            this.polys[2] = pz;
        }
        
        public double length() {
            double t = 0.0;
            final int num = 1024;
            final double dt = 1.0 / num;
            final double[] lengths = new double[num];
            for (int i = 0; i < num; ++i, t += dt) {
                final Point pos0 = this.getPosition(t);
                final Point pos2 = this.getPosition(t + dt);
                lengths[i] = pos0.sub(pos2).length();
            }
            return sum(lengths);
        }
        
        private static double sum(final double[] arr) {
            return sum(arr, 0, arr.length - 1);
        }
        
        private static double sum(final double[] arr, final int lo, final int hi) {
            if (lo >= hi) {
                return arr[hi];
            }
            final int mid = (lo + hi) / 2;
            return sum(arr, lo, mid) + sum(arr, mid + 1, hi);
        }
        
        public Point getPosition(final double t) {
            final float x = this.polys[0].p(t);
            final float y = this.polys[1].p(t);
            final float z = this.polys[2].p(t);
            return new Point(x, y, z);
        }
        
        public Vec getTangent(final double t) {
            final float dx = this.polys[0].dp(t);
            final float dy = this.polys[1].dp(t);
            final float dz = this.polys[2].dp(t);
            return new Vec(dx, dy, dz).normalize();
        }
        
        public Vec getNormal(final double t) {
            final float d2x = this.polys[0].d2p(t);
            final float d2y = this.polys[1].d2p(t);
            final float d2z = this.polys[2].d2p(t);
            final Vec d2pt = new Vec(d2x, d2y, d2z);
            final float dx = this.polys[0].dp(t);
            final float dy = this.polys[1].dp(t);
            final float dz = this.polys[2].dp(t);
            final Vec dpt = new Vec(dx, dy, dz);
            final Vec right = dpt.cross(d2pt);
            return right.cross(dpt).normalize();
        }
    }
    
    private static class Constraint
    {
        private double a1;
        private double b1;
        private double c1;
        private double d1;
        private double a2;
        private double b2;
        private double c2;
        private double d2;
        private double e;
        private int i;
        private int length;
        
        private Constraint(final int i, final int length) {
            final double a1 = 0.0;
            this.e = a1;
            this.d2 = a1;
            this.c2 = a1;
            this.b2 = a1;
            this.a2 = a1;
            this.d1 = a1;
            this.c1 = a1;
            this.b1 = a1;
            this.a1 = a1;
            this.i = i;
            this.length = length;
        }
        
        public static List<Constraint> calcConstraints(final float p, final int i, final int length) {
            final List<Constraint> constraints = new ArrayList<Constraint>(4);
            constraints.add(first(p, i, length));
            constraints.add(second(p, i, length));
            constraints.add(third(p, i, length));
            constraints.add(fourth(p, i, length));
            return constraints;
        }
        
        private static Constraint fourth(final float p, final int i, final int length) {
            final Constraint ans = new Constraint(i, length);
            ans.a1 = 3.0;
            ans.b1 = 1.0;
            ans.b2 = -1.0;
            return ans;
        }
        
        private static Constraint third(final float p, final int i, final int length) {
            final Constraint ans = new Constraint(i, length);
            ans.a1 = 3.0;
            ans.b1 = 2.0;
            ans.c1 = 1.0;
            ans.c2 = -1.0;
            return ans;
        }
        
        private static Constraint second(final float p, final int i, final int length) {
            final Constraint constraint4;
            final Constraint constraint3;
            final Constraint constraint2;
            final Constraint constraint;
            final Constraint ans = constraint = (constraint2 = (constraint3 = (constraint4 = new Constraint(i, length))));
            final double n = 1.0;
            constraint.d1 = n;
            constraint2.c1 = n;
            constraint3.b1 = n;
            constraint4.a1 = n;
            ans.d2 = -1.0;
            return ans;
        }
        
        private static Constraint first(final float p, final int i, final int length) {
            final Constraint ans = new Constraint(i, length);
            ans.e = p;
            ans.d2 = 1.0;
            return ans;
        }
        
        public double b() {
            return this.e;
        }
        
        public double[] Ai() {
            int matrixSize = this.length * 4;
            final double[] ans = new double[matrixSize];
            int index = this.i * 4;
            ans[index++] = this.a1;
            ans[index++] = this.b1;
            ans[index++] = this.c1;
            ans[index++] = this.d1;
            index %= matrixSize;
            ans[index++] = this.a2;
            ans[index++] = this.b2;
            ans[index++] = this.c2;
            ans[index++] = this.d2;
            return ans;
        }
    }
    
    public static class LocationOnSegment
    {
        public Point pos;
        public Vec tangent;
        public Vec normal;
        
        public LocationOnSegment(final Point pos, final Vec tangent, final Vec normal) {
            this.pos = pos;
            this.tangent = tangent;
            this.normal = normal;
        }
        
        public Vec right() {
            return this.tangent.cross(this.normal);
        }
    }
    
    private interface Selector
    {
        float select(final Point p0);
    }
}
