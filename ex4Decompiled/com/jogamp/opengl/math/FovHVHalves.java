// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

public final class FovHVHalves
{
    public final float left;
    public final float right;
    public final float top;
    public final float bottom;
    public final boolean inTangents;
    
    public FovHVHalves(final float left, final float right, final float top, final float bottom, final boolean inTangents) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.inTangents = inTangents;
    }
    
    public static FovHVHalves byRadians(final float n, final float n2) {
        final float tan = FloatUtil.tan(n / 2.0f);
        final float tan2 = FloatUtil.tan(n2 / 2.0f);
        return new FovHVHalves(tan, tan, tan2, tan2, true);
    }
    
    public static FovHVHalves byFovyRadianAndAspect(final float n, final float n2) {
        final float tan = FloatUtil.tan(n / 2.0f);
        final float n3 = n2 * tan;
        return new FovHVHalves(n3, n3, tan, tan, true);
    }
    
    public static FovHVHalves byRadians(final float n, final float n2, final float n3, final float n4) {
        return new FovHVHalves(FloatUtil.tan(n * n2), FloatUtil.tan(n * (1.0f - n2)), FloatUtil.tan(n3 * n4), FloatUtil.tan(n3 * (1.0f - n4)), true);
    }
    
    public static FovHVHalves byFovyRadianAndAspect(final float n, final float n2, final float n3, final float n4) {
        return byRadians(FloatUtil.atan(n3 * FloatUtil.tan(n / 2.0f)) * 2.0f, n4, n, n2);
    }
    
    public final FovHVHalves toTangents() {
        if (this.inTangents) {
            return this;
        }
        return new FovHVHalves(FloatUtil.tan(this.left), FloatUtil.tan(this.right), FloatUtil.tan(this.top), FloatUtil.tan(this.bottom), true);
    }
    
    public final float horzFov() {
        return this.left + this.right;
    }
    
    public final float vertFov() {
        return this.top + this.bottom;
    }
    
    @Override
    public final String toString() {
        return "FovHVH[" + (this.inTangents ? "tangents" : "radians") + ": " + this.left + " l, " + this.right + " r, " + this.top + " t, " + this.bottom + " b]";
    }
    
    public final String toStringInDegrees() {
        final String s = this.inTangents ? "tangents" : "radians";
        if (this.inTangents) {
            return "FovHVH[degrees: " + FloatUtil.atan(this.left) * 57.295776f + " l, " + FloatUtil.atan(this.right) * 57.295776f + " r, " + FloatUtil.atan(this.top) * 57.295776f + " t, " + FloatUtil.atan(this.bottom) * 57.295776f + " b, stored-as: " + s + "]";
        }
        return "FovHVH[degrees: " + this.left * 57.295776f + " l, " + this.right * 57.295776f + " r, " + this.top * 57.295776f + " t, " + this.bottom * 57.295776f + " b, stored-as: " + s + "]";
    }
}
