// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.math;

public class Ray
{
    public final float[] orig;
    public final float[] dir;
    
    public Ray() {
        this.orig = new float[3];
        this.dir = new float[3];
    }
    
    @Override
    public String toString() {
        return "Ray[orig[" + this.orig[0] + ", " + this.orig[1] + ", " + this.orig[2] + "], dir[" + this.dir[0] + ", " + this.dir[1] + ", " + this.dir[2] + "]]";
    }
}
