// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.opengl.math.FovHVHalves;

public final class EyeParameter
{
    public final int number;
    public final float[] positionOffset;
    public final FovHVHalves fovhv;
    public final float distNoseToPupilX;
    public final float distMiddleToPupilY;
    public final float eyeReliefZ;
    
    public EyeParameter(final int number, final float[] array, final FovHVHalves fovhv, final float distNoseToPupilX, final float distMiddleToPupilY, final float eyeReliefZ) {
        this.number = number;
        System.arraycopy(array, 0, this.positionOffset = new float[3], 0, 3);
        this.fovhv = fovhv;
        this.distNoseToPupilX = distNoseToPupilX;
        this.distMiddleToPupilY = distMiddleToPupilY;
        this.eyeReliefZ = eyeReliefZ;
    }
    
    @Override
    public final String toString() {
        return "EyeParam[num " + this.number + ", posOff[" + this.positionOffset[0] + ", " + this.positionOffset[1] + ", " + this.positionOffset[2] + "], " + this.fovhv + ", distPupil[noseX " + this.distNoseToPupilX + ", middleY " + this.distMiddleToPupilY + ", reliefZ " + this.eyeReliefZ + "]]";
    }
}
