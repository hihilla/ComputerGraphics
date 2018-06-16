// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.opengl.math.geom.Frustum;

public final class LocationSensorParameter
{
    public final Frustum frustum;
    public final Frustum.FovDesc frustumDesc;
    public final float[] frustumProjMat;
    
    public LocationSensorParameter(final Frustum.FovDesc frustumDesc) {
        this.frustumDesc = frustumDesc;
        this.frustum = new Frustum();
        this.frustumProjMat = this.frustum.updateByFovDesc(new float[16], 0, true, frustumDesc);
    }
    
    @Override
    public final String toString() {
        return "LocationSensor[" + this.frustumDesc + "]";
    }
}
