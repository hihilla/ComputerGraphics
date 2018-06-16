// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.opengl.math.Quaternion;
import com.jogamp.opengl.math.FloatUtil;

public class StereoUtil
{
    public static float getVertPupilCenterFromTop(final float n, final float n2) {
        return n2 / n;
    }
    
    public static float[] getHorizPupilCenterFromLeft(final float n, final float n2) {
        final float n3 = 0.5f * n;
        final float n4 = (n - n2) * 0.5f;
        return new float[] { n4 / n3, (n4 + n2 - n3) / n3 };
    }
    
    public static boolean usesBarrelDistortion(final int n) {
        return 0x0 != (n & 0x1);
    }
    
    public static boolean usesTimewarpDistortion(final int n) {
        return 0x0 != (n & 0x8);
    }
    
    public static boolean usesChromaticDistortion(final int n) {
        return 0x0 != (n & 0x2);
    }
    
    public static boolean usesVignetteDistortion(final int n) {
        return 0x0 != (n & 0x4);
    }
    
    public static String distortionBitsToString(final int n) {
        int n2 = 0;
        final StringBuilder sb = new StringBuilder();
        if (usesBarrelDistortion(n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("barrel");
            n2 = 1;
        }
        if (usesVignetteDistortion(n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("vignette");
            n2 = 1;
        }
        if (usesChromaticDistortion(n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("chroma");
            n2 = 1;
        }
        if (usesTimewarpDistortion(n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("timewarp");
        }
        return sb.toString();
    }
    
    public static boolean usesOrientationSensor(final int n) {
        return 0x0 != (n & 0x1);
    }
    
    public static boolean usesYawCorrectionSensor(final int n) {
        return 0x0 != (n & 0x2);
    }
    
    public static boolean usesPositionSensor(final int n) {
        return 0x0 != (n & 0x4);
    }
    
    public static String sensorBitsToString(final int n) {
        int n2 = 0;
        final StringBuilder sb = new StringBuilder();
        if (usesOrientationSensor(n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("orientation");
            n2 = 1;
        }
        if (usesYawCorrectionSensor(n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("yaw-corr");
            n2 = 1;
        }
        if (usesPositionSensor(n)) {
            if (n2 != 0) {
                sb.append(", ");
            }
            sb.append("position");
        }
        return sb.toString();
    }
    
    public static void getSBSUpstreamPMV(final ViewerPose viewerPose, final StereoDeviceRenderer.Eye eye, final float n, final float n2, final float[] array, final float[] array2) {
        final float[] array3 = new float[16];
        final float[] array4 = new float[16];
        final float[] array5 = new float[3];
        final float[] array6 = new float[3];
        final float[] array7 = new float[3];
        final EyeParameter eyeParameter = eye.getEyeParameter();
        FloatUtil.makePerspective(array, 0, true, eyeParameter.fovhv, n, n2);
        final Quaternion quaternion = new Quaternion();
        final float[] rotateVector = quaternion.rotateVector(array5, 0, viewerPose.position, 0);
        VectorUtil.addVec3(rotateVector, rotateVector, eyeParameter.positionOffset);
        quaternion.mult(viewerPose.orientation);
        final float[] rotateVector2 = quaternion.rotateVector(array6, 0, VectorUtil.VEC3_UNIT_Y, 0);
        final float[] rotateVector3 = quaternion.rotateVector(array7, 0, VectorUtil.VEC3_UNIT_Z_NEG, 0);
        FloatUtil.multMatrix(FloatUtil.makeTranslation(array2, true, eyeParameter.distNoseToPupilX, eyeParameter.distMiddleToPupilY, eyeParameter.eyeReliefZ), FloatUtil.makeLookAt(array4, 0, rotateVector, 0, VectorUtil.addVec3(rotateVector3, rotateVector, rotateVector3), 0, rotateVector2, 0, array3));
    }
}
