// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo.generic;

import com.jogamp.opengl.util.stereo.StereoUtil;
import java.util.Arrays;
import jogamp.opengl.util.stereo.DistortionMesh;
import com.jogamp.opengl.util.stereo.EyeParameter;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.opengl.util.stereo.StereoDeviceConfig;

public class GenericStereoDeviceConfig extends StereoDeviceConfig
{
    public final String name;
    public final ShutterType shutterType;
    public final DimensionImmutable surfaceSizeInPixels;
    public final float[] screenSizeInMeters;
    public final DimensionImmutable[] eyeTextureSizes;
    public final float pupilCenterFromScreenTopInMeters;
    public final float interpupillaryDistanceInMeters;
    public final float[][] pupilCenterFromTopLeft;
    public final int[] eyeRenderOrder;
    public final EyeParameter[] defaultEyeParam;
    public final int supportedSensorBits;
    public final DistortionMesh.Producer distortionMeshProducer;
    public final int supportedDistortionBits;
    public final int recommendedDistortionBits;
    public final int minimumDistortionBits;
    private boolean isInitialized;
    
    public GenericStereoDeviceConfig(final String name, final ShutterType shutterType, final DimensionImmutable surfaceSizeInPixels, final float[] screenSizeInMeters, final DimensionImmutable[] eyeTextureSizes, final float pupilCenterFromScreenTopInMeters, final float interpupillaryDistanceInMeters, final int[] eyeRenderOrder, final EyeParameter[] defaultEyeParam, final int supportedSensorBits, final DistortionMesh.Producer distortionMeshProducer, final int supportedDistortionBits, final int recommendedDistortionBits, final int minimumDistortionBits) {
        this.isInitialized = false;
        if (eyeRenderOrder.length != defaultEyeParam.length) {
            throw new IllegalArgumentException("eye arrays of different length");
        }
        this.name = name;
        this.shutterType = shutterType;
        this.surfaceSizeInPixels = surfaceSizeInPixels;
        this.screenSizeInMeters = screenSizeInMeters;
        this.eyeTextureSizes = eyeTextureSizes;
        this.pupilCenterFromScreenTopInMeters = pupilCenterFromScreenTopInMeters;
        this.interpupillaryDistanceInMeters = interpupillaryDistanceInMeters;
        this.eyeRenderOrder = eyeRenderOrder;
        this.defaultEyeParam = defaultEyeParam;
        this.supportedSensorBits = supportedSensorBits;
        this.distortionMeshProducer = distortionMeshProducer;
        this.supportedDistortionBits = supportedDistortionBits;
        this.recommendedDistortionBits = recommendedDistortionBits;
        this.minimumDistortionBits = minimumDistortionBits;
        this.pupilCenterFromTopLeft = new float[2][2];
        this.calcPupilCenterFromTopLeft();
    }
    
    public GenericStereoDeviceConfig(final GenericStereoDeviceConfig genericStereoDeviceConfig, final DimensionImmutable surfaceSizeInPixels, final float[] screenSizeInMeters, final DimensionImmutable[] eyeTextureSizes) {
        this.isInitialized = false;
        this.name = genericStereoDeviceConfig.name;
        this.shutterType = genericStereoDeviceConfig.shutterType;
        this.surfaceSizeInPixels = surfaceSizeInPixels;
        this.screenSizeInMeters = screenSizeInMeters;
        this.eyeTextureSizes = eyeTextureSizes;
        this.pupilCenterFromScreenTopInMeters = genericStereoDeviceConfig.pupilCenterFromScreenTopInMeters;
        this.interpupillaryDistanceInMeters = genericStereoDeviceConfig.interpupillaryDistanceInMeters;
        this.eyeRenderOrder = genericStereoDeviceConfig.eyeRenderOrder;
        this.defaultEyeParam = genericStereoDeviceConfig.defaultEyeParam;
        this.supportedSensorBits = genericStereoDeviceConfig.supportedSensorBits;
        this.distortionMeshProducer = genericStereoDeviceConfig.distortionMeshProducer;
        this.supportedDistortionBits = genericStereoDeviceConfig.supportedDistortionBits;
        this.recommendedDistortionBits = genericStereoDeviceConfig.recommendedDistortionBits;
        this.minimumDistortionBits = genericStereoDeviceConfig.minimumDistortionBits;
        this.pupilCenterFromTopLeft = new float[2][2];
        this.calcPupilCenterFromTopLeft();
    }
    
    private void calcPupilCenterFromTopLeft() {
        final float n = 0.5f * this.screenSizeInMeters[0];
        final float n2 = (this.screenSizeInMeters[0] - this.interpupillaryDistanceInMeters) * 0.5f;
        final float n3 = n2 + this.interpupillaryDistanceInMeters - n;
        this.pupilCenterFromTopLeft[0][0] = n2 / n;
        this.pupilCenterFromTopLeft[0][1] = this.pupilCenterFromScreenTopInMeters / this.screenSizeInMeters[1];
        this.pupilCenterFromTopLeft[1][0] = n3 / n;
        this.pupilCenterFromTopLeft[1][1] = this.pupilCenterFromTopLeft[0][1];
    }
    
    public synchronized void init() {
        if (!this.isInitialized) {
            if (null != this.distortionMeshProducer) {
                final float[] array = new float[this.defaultEyeParam.length];
                if (0 < this.defaultEyeParam.length) {
                    array[0] = this.defaultEyeParam[0].eyeReliefZ;
                }
                if (1 < this.defaultEyeParam.length) {
                    array[1] = this.defaultEyeParam[1].eyeReliefZ;
                }
                this.distortionMeshProducer.init(this, array);
            }
            this.isInitialized = true;
        }
    }
    
    public final boolean isInitialized() {
        return this.isInitialized;
    }
    
    @Override
    public String toString() {
        return "StereoConfig[" + this.name + ", shutter " + this.shutterType + ", surfaceSize " + this.surfaceSizeInPixels + ", screenSize " + this.screenSizeInMeters[0] + " x " + this.screenSizeInMeters[0] + " [m], eyeTexSize " + Arrays.toString(this.eyeTextureSizes) + ", IPD " + this.interpupillaryDistanceInMeters + " [m], eyeParam " + Arrays.toString(this.defaultEyeParam) + ", distortionBits[supported [" + StereoUtil.distortionBitsToString(this.supportedDistortionBits) + "], recommended [" + StereoUtil.distortionBitsToString(this.recommendedDistortionBits) + "], minimum [" + StereoUtil.distortionBitsToString(this.minimumDistortionBits) + "]]" + ", sensorBits[supported [" + StereoUtil.sensorBitsToString(this.supportedSensorBits) + "]]]";
    }
    
    public enum ShutterType
    {
        Global, 
        RollingLeftToRight, 
        RollingRightToLeft, 
        RollingTopToBottom;
    }
}
