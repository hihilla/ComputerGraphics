// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import com.jogamp.opengl.math.FovHVHalves;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.nativewindow.util.PointImmutable;

public interface StereoDevice
{
    public static final boolean DEBUG = Debug.debug("StereoDevice");
    public static final boolean DUMP_DATA = PropertyAccess.isPropertyDefined("jogl.debug.StereoDevice.DumpData", true);
    public static final int SENSOR_ORIENTATION = 1;
    public static final int SENSOR_YAW_CORRECTION = 2;
    public static final int SENSOR_POSITION = 4;
    
    StereoDeviceFactory getFactory();
    
    void dispose();
    
    boolean isValid();
    
    PointImmutable getPosition();
    
    DimensionImmutable getSurfaceSize();
    
    int getRequiredRotation();
    
    float[] getDefaultEyePositionOffset();
    
    FovHVHalves[] getDefaultFOV();
    
    LocationSensorParameter getLocationSensorParams();
    
    void resetLocationSensorOrigin();
    
    boolean startSensors(final int p0, final int p1);
    
    boolean stopSensors();
    
    boolean getSensorsStarted();
    
    int getSupportedSensorBits();
    
    int getEnabledSensorBits();
    
    int[] getEyeRenderOrder();
    
    int getSupportedDistortionBits();
    
    int getRecommendedDistortionBits();
    
    int getMinimumDistortionBits();
    
    StereoDeviceRenderer createRenderer(final int p0, final int p1, final float[] p2, final FovHVHalves[] p3, final float p4, final int p5);
}
