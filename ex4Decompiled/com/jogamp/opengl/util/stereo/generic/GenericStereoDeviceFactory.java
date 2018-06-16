// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo.generic;

import com.jogamp.opengl.util.stereo.StereoDeviceConfig;
import com.jogamp.opengl.util.stereo.StereoDevice;
import com.jogamp.common.util.ReflectionUtil;
import jogamp.opengl.util.stereo.GenericStereoDevice;
import com.jogamp.opengl.util.stereo.StereoUtil;
import com.jogamp.nativewindow.util.Dimension;
import jogamp.opengl.util.stereo.DistortionMesh;
import com.jogamp.opengl.math.FovHVHalves;
import com.jogamp.opengl.util.stereo.EyeParameter;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.opengl.util.stereo.StereoDeviceFactory;

public class GenericStereoDeviceFactory extends StereoDeviceFactory
{
    private boolean isValid;
    
    public GenericStereoDeviceFactory() {
        this.isValid = true;
    }
    
    public static GenericStereoDeviceConfig createMono(final String s, final DimensionImmutable dimensionImmutable, final float[] array, final float[] array2) {
        return new GenericStereoDeviceConfig(s, GenericStereoDeviceConfig.ShutterType.RollingTopToBottom, dimensionImmutable, array, new DimensionImmutable[] { dimensionImmutable }, array[1] / 2.0f, 0.0f, new int[] { 0 }, new EyeParameter[] { new EyeParameter(0, array2, FovHVHalves.byFovyRadianAndAspect(0.7853982f, 1.6f), 0.0f, 0.0f, 0.0f) }, 0, null, 0, 0, 0);
    }
    
    public static GenericStereoDeviceConfig createStereoSBS(final String s, final DimensionImmutable dimensionImmutable, final float[] array, final float n, final float n2, final float[] array2) {
        final float n3 = array[1] / 2.0f;
        final Dimension dimension = new Dimension(dimensionImmutable.getWidth() / 2, dimensionImmutable.getHeight());
        final float[] horizPupilCenterFromLeft = StereoUtil.getHorizPupilCenterFromLeft(array[0], n);
        final float vertPupilCenterFromTop = StereoUtil.getVertPupilCenterFromTop(array[1], n3);
        final float n4 = dimension.getWidth() / dimension.getHeight();
        return new GenericStereoDeviceConfig(s, GenericStereoDeviceConfig.ShutterType.RollingTopToBottom, dimensionImmutable, array, new DimensionImmutable[] { dimension, dimension }, n3, n, new int[] { 0, 1 }, new EyeParameter[] { new EyeParameter(0, array2, FovHVHalves.byFovyRadianAndAspect(n2 * 0.017453292f, vertPupilCenterFromTop, n4, horizPupilCenterFromLeft[0]), n / 2.0f, 0.0f, 0.01f), new EyeParameter(1, array2, FovHVHalves.byFovyRadianAndAspect(n2 * 0.017453292f, vertPupilCenterFromTop, n4, horizPupilCenterFromLeft[1]), -n / 2.0f, 0.0f, 0.01f) }, 0, null, 0, 0, 0);
    }
    
    public static GenericStereoDeviceConfig createStereoSBSLense(final String s, final DimensionImmutable dimensionImmutable, final float[] array, final float n, final float n2, final DimensionImmutable dimensionImmutable2, final float[] array2) {
        DistortionMesh.Producer producer = null;
        try {
            producer = (DistortionMesh.Producer)ReflectionUtil.createInstance("jogamp.opengl.oculusvr.stereo.lense.DistortionMeshProducer", GenericStereoDevice.class.getClassLoader());
        }
        catch (Throwable t) {
            if (StereoDevice.DEBUG) {
                System.err.println("Caught: " + t.getMessage());
                t.printStackTrace();
            }
        }
        if (null == producer) {
            return null;
        }
        final float n3 = array[1] / 2.0f;
        final float[] horizPupilCenterFromLeft = StereoUtil.getHorizPupilCenterFromLeft(array[0], n);
        final float vertPupilCenterFromTop = StereoUtil.getVertPupilCenterFromTop(array[1], n3);
        final float n4 = dimensionImmutable2.getWidth() / dimensionImmutable2.getHeight();
        return new GenericStereoDeviceConfig(s, GenericStereoDeviceConfig.ShutterType.RollingTopToBottom, dimensionImmutable, array, new DimensionImmutable[] { dimensionImmutable2, dimensionImmutable2 }, n3, n, new int[] { 0, 1 }, new EyeParameter[] { new EyeParameter(0, array2, FovHVHalves.byFovyRadianAndAspect(n2 * 0.017453292f, vertPupilCenterFromTop, n4, horizPupilCenterFromLeft[0]), n / 2.0f, 0.0f, 0.01f), new EyeParameter(1, array2, FovHVHalves.byFovyRadianAndAspect(n2 * 0.017453292f, vertPupilCenterFromTop, n4, horizPupilCenterFromLeft[1]), -n / 2.0f, 0.0f, 0.01f) }, 0, producer, 7, 7, 1);
    }
    
    public static boolean isAvailable() {
        return true;
    }
    
    @Override
    protected final StereoDevice createDeviceImpl(final int n, final StereoDeviceConfig stereoDeviceConfig, final boolean b) {
        return new GenericStereoDevice(this, n, stereoDeviceConfig);
    }
    
    @Override
    public boolean isValid() {
        return this.isValid;
    }
    
    @Override
    public final void shutdown() {
        if (this.isValid) {
            this.isValid = false;
        }
    }
}
