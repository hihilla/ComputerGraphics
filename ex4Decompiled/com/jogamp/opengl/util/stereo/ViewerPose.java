// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.opengl.math.Quaternion;

public final class ViewerPose
{
    public final float[] position;
    public final Quaternion orientation;
    
    public ViewerPose() {
        this.position = new float[3];
        this.orientation = new Quaternion();
    }
    
    public ViewerPose(final float[] array, final Quaternion quaternion) {
        this();
        this.set(array, quaternion);
    }
    
    public final void set(final float[] array, final Quaternion quaternion) {
        System.arraycopy(array, 0, this.position, 0, 3);
        this.orientation.set(quaternion);
    }
    
    public final void setPosition(final float n, final float n2, final float n3) {
        this.position[0] = n;
        this.position[1] = n2;
        this.position[2] = n3;
    }
    
    @Override
    public final String toString() {
        return "ViewerPose[pos[" + this.position[0] + ", " + this.position[1] + ", " + this.position[2] + "], " + this.orientation + "]";
    }
}
