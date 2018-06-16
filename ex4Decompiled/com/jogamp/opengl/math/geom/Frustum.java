// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math.geom;

import com.jogamp.opengl.math.FovHVHalves;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.opengl.math.FloatUtil;

public class Frustum
{
    protected final Plane[] planes;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int TOP = 3;
    public static final int NEAR = 4;
    public static final int FAR = 5;
    
    public Frustum() {
        this.planes = new Plane[6];
        for (int i = 0; i < 6; ++i) {
            this.planes[i] = new Plane();
        }
    }
    
    public final Plane[] getPlanes() {
        return this.planes;
    }
    
    public final void updateByPlanes(final Plane[] array) {
        for (int i = 0; i < 6; ++i) {
            final Plane plane = this.planes[i];
            final Plane plane2 = array[i];
            plane.d = plane2.d;
            System.arraycopy(plane2.n, 0, plane.n, 0, 3);
        }
    }
    
    public float[] updateByFovDesc(final float[] array, final int n, final boolean b, final FovDesc fovDesc) {
        FloatUtil.makePerspective(array, n, b, fovDesc.fovhv, fovDesc.zNear, fovDesc.zFar);
        this.updateByPMV(array, 0);
        return array;
    }
    
    public void updateByPMV(final float[] array, final int n) {
        final Plane plane = this.planes[0];
        final float[] n2 = plane.n;
        n2[0] = array[n + 3 + 0] + array[n + 0 + 0];
        n2[1] = array[n + 3 + 4] + array[n + 0 + 4];
        n2[2] = array[n + 3 + 8] + array[n + 0 + 8];
        plane.d = array[n + 3 + 12] + array[n + 0 + 12];
        final Plane plane2 = this.planes[1];
        final float[] n3 = plane2.n;
        n3[0] = array[n + 3 + 0] - array[n + 0 + 0];
        n3[1] = array[n + 3 + 4] - array[n + 0 + 4];
        n3[2] = array[n + 3 + 8] - array[n + 0 + 8];
        plane2.d = array[n + 3 + 12] - array[n + 0 + 12];
        final Plane plane3 = this.planes[2];
        final float[] n4 = plane3.n;
        n4[0] = array[n + 3 + 0] + array[n + 1 + 0];
        n4[1] = array[n + 3 + 4] + array[n + 1 + 4];
        n4[2] = array[n + 3 + 8] + array[n + 1 + 8];
        plane3.d = array[n + 3 + 12] + array[n + 1 + 12];
        final Plane plane4 = this.planes[3];
        final float[] n5 = plane4.n;
        n5[0] = array[n + 3 + 0] - array[n + 1 + 0];
        n5[1] = array[n + 3 + 4] - array[n + 1 + 4];
        n5[2] = array[n + 3 + 8] - array[n + 1 + 8];
        plane4.d = array[n + 3 + 12] - array[n + 1 + 12];
        final Plane plane5 = this.planes[4];
        final float[] n6 = plane5.n;
        n6[0] = array[n + 3 + 0] + array[n + 2 + 0];
        n6[1] = array[n + 3 + 4] + array[n + 2 + 4];
        n6[2] = array[n + 3 + 8] + array[n + 2 + 8];
        plane5.d = array[n + 3 + 12] + array[n + 2 + 12];
        final Plane plane6 = this.planes[5];
        final float[] n7 = plane6.n;
        n7[0] = array[n + 3 + 0] - array[n + 2 + 0];
        n7[1] = array[n + 3 + 4] - array[n + 2 + 4];
        n7[2] = array[n + 3 + 8] - array[n + 2 + 8];
        plane6.d = array[n + 3 + 12] - array[n + 2 + 12];
        for (int i = 0; i < 6; ++i) {
            final Plane plane7 = this.planes[i];
            final float[] n8 = plane7.n;
            final double sqrt = Math.sqrt(n8[0] * n8[0] + n8[1] * n8[1] + n8[2] * n8[2]);
            final float[] array2 = n8;
            final int n9 = 0;
            array2[n9] /= (float)sqrt;
            final float[] array3 = n8;
            final int n10 = 1;
            array3[n10] /= (float)sqrt;
            final float[] array4 = n8;
            final int n11 = 2;
            array4[n11] /= (float)sqrt;
            final Plane plane8 = plane7;
            plane8.d /= (float)sqrt;
        }
    }
    
    private static final boolean isOutsideImpl(final Plane plane, final AABBox aabBox) {
        final float[] low = aabBox.getLow();
        final float[] high = aabBox.getHigh();
        return plane.distanceTo(low[0], low[1], low[2]) <= 0.0f && plane.distanceTo(high[0], low[1], low[2]) <= 0.0f && plane.distanceTo(low[0], high[1], low[2]) <= 0.0f && plane.distanceTo(high[0], high[1], low[2]) <= 0.0f && plane.distanceTo(low[0], low[1], high[2]) <= 0.0f && plane.distanceTo(high[0], low[1], high[2]) <= 0.0f && plane.distanceTo(low[0], high[1], high[2]) <= 0.0f && plane.distanceTo(high[0], high[1], high[2]) <= 0.0f;
    }
    
    public final boolean isAABBoxOutside(final AABBox aabBox) {
        for (int i = 0; i < 6; ++i) {
            if (isOutsideImpl(this.planes[i], aabBox)) {
                return true;
            }
        }
        return false;
    }
    
    public final Location classifyPoint(final float[] array) {
        Location location = Location.INSIDE;
        for (int i = 0; i < 6; ++i) {
            final float distanceTo = this.planes[i].distanceTo(array);
            if (distanceTo < 0.0f) {
                return Location.OUTSIDE;
            }
            if (distanceTo == 0.0f) {
                location = Location.INTERSECT;
            }
        }
        return location;
    }
    
    public final boolean isPointOutside(final float[] array) {
        return Location.OUTSIDE == this.classifyPoint(array);
    }
    
    public final Location classifySphere(final float[] array, final float n) {
        Location location = Location.INSIDE;
        for (int i = 0; i < 6; ++i) {
            final float distanceTo = this.planes[i].distanceTo(array);
            if (distanceTo < -n) {
                return Location.OUTSIDE;
            }
            if (distanceTo < n) {
                location = Location.INTERSECT;
            }
        }
        return location;
    }
    
    public final boolean isSphereOutside(final float[] array, final float n) {
        return Location.OUTSIDE == this.classifySphere(array, n);
    }
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("Frustum[ Planes[ ").append(PlatformPropsImpl.NEWLINE).append(" L: ").append(this.planes[0]).append(", ").append(PlatformPropsImpl.NEWLINE).append(" R: ").append(this.planes[1]).append(", ").append(PlatformPropsImpl.NEWLINE).append(" B: ").append(this.planes[2]).append(", ").append(PlatformPropsImpl.NEWLINE).append(" T: ").append(this.planes[3]).append(", ").append(PlatformPropsImpl.NEWLINE).append(" N: ").append(this.planes[4]).append(", ").append(PlatformPropsImpl.NEWLINE).append(" F: ").append(this.planes[5]).append("], ").append(PlatformPropsImpl.NEWLINE).append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    public static class FovDesc
    {
        public final FovHVHalves fovhv;
        public final float zNear;
        public final float zFar;
        
        public FovDesc(final FovHVHalves fovhv, final float zNear, final float zFar) throws IllegalArgumentException {
            if (zNear <= 0.0f || zFar <= zNear) {
                throw new IllegalArgumentException("Requirements zNear > 0 and zFar > zNear, but zNear " + zNear + ", zFar " + zFar);
            }
            this.fovhv = fovhv;
            this.zNear = zNear;
            this.zFar = zFar;
        }
        
        @Override
        public final String toString() {
            return "FrustumFovDesc[" + this.fovhv.toStringInDegrees() + ", Z[" + this.zNear + " - " + this.zFar + "]]";
        }
    }
    
    public static class Plane
    {
        public final float[] n;
        public float d;
        
        public Plane() {
            this.n = new float[3];
        }
        
        public final float distanceTo(final float n, final float n2, final float n3) {
            return this.n[0] * n + this.n[1] * n2 + this.n[2] * n3 + this.d;
        }
        
        public final float distanceTo(final float[] array) {
            return this.n[0] * array[0] + this.n[1] * array[1] + this.n[2] * array[2] + this.d;
        }
        
        @Override
        public String toString() {
            return "Plane[ [ " + this.n[0] + ", " + this.n[1] + ", " + this.n[2] + " ], " + this.d + "]";
        }
    }
    
    public enum Location
    {
        OUTSIDE, 
        INSIDE, 
        INTERSECT;
    }
}
