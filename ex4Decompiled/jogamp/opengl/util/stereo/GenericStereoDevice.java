// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.stereo;

import com.jogamp.opengl.util.stereo.generic.GenericStereoDeviceFactory;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.opengl.util.stereo.EyeParameter;
import com.jogamp.opengl.util.stereo.StereoDeviceRenderer;
import com.jogamp.opengl.util.stereo.LocationSensorParameter;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.nativewindow.util.PointImmutable;
import com.jogamp.opengl.util.stereo.StereoUtil;
import com.jogamp.opengl.util.stereo.StereoDeviceConfig;
import com.jogamp.opengl.math.FovHVHalves;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.util.stereo.StereoDeviceFactory;
import com.jogamp.opengl.util.stereo.generic.GenericStereoDeviceConfig;
import com.jogamp.opengl.util.stereo.StereoDevice;

public class GenericStereoDevice implements StereoDevice
{
    public static final GenericStereoDeviceConfig config01Mono01;
    public static final GenericStereoDeviceConfig config01Mono02;
    public static final GenericStereoDeviceConfig config02StereoSBS01;
    public static final GenericStereoDeviceConfig config02StereoSBS02;
    public static final GenericStereoDeviceConfig config03StereoSBSLense01;
    public static final GenericStereoDeviceConfig config03StereoSBSLense02;
    private static final GenericStereoDeviceConfig[] configs;
    private final StereoDeviceFactory factory;
    public final int deviceIndex;
    public final GenericStereoDeviceConfig config;
    public final Point surfacePos;
    private final FovHVHalves[] defaultEyeFov;
    private int usedSensorBits;
    private boolean sensorsStarted;
    
    public GenericStereoDevice(final StereoDeviceFactory factory, final int deviceIndex, final StereoDeviceConfig stereoDeviceConfig) {
        this.sensorsStarted = false;
        this.factory = factory;
        this.deviceIndex = deviceIndex;
        if (stereoDeviceConfig instanceof GenericStereoDeviceConfig) {
            this.config = (GenericStereoDeviceConfig)stereoDeviceConfig;
        }
        else {
            final int min = Math.min(deviceIndex % 10, GenericStereoDevice.configs.length - 1);
            this.config = ((null != GenericStereoDevice.configs[min]) ? GenericStereoDevice.configs[min] : GenericStereoDevice.config02StereoSBS01);
        }
        this.config.init();
        this.surfacePos = new Point(0, 0);
        this.defaultEyeFov = new FovHVHalves[this.config.defaultEyeParam.length];
        for (int i = 0; i < this.defaultEyeFov.length; ++i) {
            this.defaultEyeFov[i] = this.config.defaultEyeParam[i].fovhv;
        }
        this.usedSensorBits = 0;
    }
    
    @Override
    public final StereoDeviceFactory getFactory() {
        return this.factory;
    }
    
    @Override
    public String toString() {
        return "GenericStereoDevice[" + this.config + ", surfacePos " + this.surfacePos + ", sensorBits[enabled [" + StereoUtil.sensorBitsToString(this.getEnabledSensorBits()) + "]]]";
    }
    
    public void setSurfacePosition(final int n, final int n2) {
        this.surfacePos.set(n, n2);
    }
    
    @Override
    public final void dispose() {
        this.stopSensors();
    }
    
    @Override
    public boolean isValid() {
        return true;
    }
    
    @Override
    public final PointImmutable getPosition() {
        return this.surfacePos;
    }
    
    @Override
    public final DimensionImmutable getSurfaceSize() {
        return this.config.surfaceSizeInPixels;
    }
    
    @Override
    public int getRequiredRotation() {
        return 0;
    }
    
    @Override
    public float[] getDefaultEyePositionOffset() {
        return this.config.defaultEyeParam[0].positionOffset;
    }
    
    @Override
    public final FovHVHalves[] getDefaultFOV() {
        return this.defaultEyeFov;
    }
    
    @Override
    public final LocationSensorParameter getLocationSensorParams() {
        return null;
    }
    
    @Override
    public final void resetLocationSensorOrigin() {
    }
    
    @Override
    public final boolean startSensors(final int n, final int n2) {
        return this.sensorsStarted || (n2 == (this.config.supportedSensorBits & n2) && 0x0 != (this.config.supportedSensorBits & (n2 | n)) && this.startSensorsImpl(true, n, n2) && (this.sensorsStarted = true));
    }
    
    protected boolean startSensorsImpl(final boolean b, final int n, final int n2) {
        return false;
    }
    
    @Override
    public final boolean stopSensors() {
        if (!this.sensorsStarted) {
            return true;
        }
        if (this.startSensorsImpl(false, 0, 0)) {
            this.sensorsStarted = false;
            this.usedSensorBits = 0;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean getSensorsStarted() {
        return this.sensorsStarted;
    }
    
    @Override
    public final int getSupportedSensorBits() {
        return this.config.supportedSensorBits;
    }
    
    @Override
    public final int getEnabledSensorBits() {
        return this.usedSensorBits;
    }
    
    @Override
    public int[] getEyeRenderOrder() {
        return this.config.eyeRenderOrder;
    }
    
    @Override
    public int getSupportedDistortionBits() {
        return this.config.supportedDistortionBits;
    }
    
    @Override
    public int getRecommendedDistortionBits() {
        return this.config.recommendedDistortionBits;
    }
    
    @Override
    public int getMinimumDistortionBits() {
        return this.config.minimumDistortionBits;
    }
    
    @Override
    public final StereoDeviceRenderer createRenderer(final int n, final int n2, final float[] array, final FovHVHalves[] array2, final float n3, final int n4) {
        final EyeParameter[] array3 = new EyeParameter[array2.length];
        for (int i = 0; i < array3.length; ++i) {
            final EyeParameter eyeParameter = this.config.defaultEyeParam[i];
            array3[i] = new EyeParameter(i, array, array2[i], eyeParameter.distNoseToPupilX, eyeParameter.distMiddleToPupilY, eyeParameter.eyeReliefZ);
        }
        final boolean b = null != this.config.distortionMeshProducer && 0 != n;
        final RectangleImmutable[] array4 = new RectangleImmutable[array3.length];
        DimensionImmutable dimensionImmutable3;
        if (1 < array3.length) {
            final DimensionImmutable dimensionImmutable = this.config.eyeTextureSizes[0];
            final DimensionImmutable dimensionImmutable2 = this.config.eyeTextureSizes[1];
            final int max = Math.max(dimensionImmutable.getHeight(), dimensionImmutable2.getHeight());
            dimensionImmutable3 = new Dimension(dimensionImmutable.getWidth() + dimensionImmutable2.getWidth(), max);
            if (1 == n2) {
                array4[0] = new Rectangle(0, 0, dimensionImmutable.getWidth(), max);
                array4[1] = new Rectangle(dimensionImmutable.getWidth(), 0, dimensionImmutable2.getWidth(), max);
            }
            else if (b) {
                array4[0] = new Rectangle(0, 0, dimensionImmutable.getWidth(), dimensionImmutable.getHeight());
                array4[1] = new Rectangle(0, 0, dimensionImmutable2.getWidth(), dimensionImmutable2.getHeight());
            }
            else {
                array4[0] = new Rectangle(0, 0, dimensionImmutable.getWidth(), dimensionImmutable.getHeight());
                array4[1] = new Rectangle(dimensionImmutable.getWidth(), 0, dimensionImmutable2.getWidth(), dimensionImmutable2.getHeight());
            }
        }
        else {
            final DimensionImmutable dimensionImmutable4 = dimensionImmutable3 = this.config.eyeTextureSizes[0];
            array4[0] = new Rectangle(0, 0, dimensionImmutable4.getWidth(), dimensionImmutable4.getHeight());
        }
        return new GenericStereoDeviceRenderer(this, n, n2, array, array3, n3, n4, this.config.eyeTextureSizes, dimensionImmutable3, array4);
    }
    
    static {
        final float[] array = { 0.0f, 1.6f, -5.0f };
        final float[] array2 = { 0.0f, 0.3f, 3.0f };
        final float[] array3 = { 0.0f, 0.0f, 3.0f };
        final Dimension dimension = new Dimension(1280, 800);
        final float[] array4 = { 0.14976f, 0.0936f };
        final Dimension dimension2 = new Dimension(1122, 1553);
        final Dimension dimension3 = new Dimension(1920, 1080);
        final float[] array5 = { 0.12576f, 0.07074f };
        final Dimension dimension4 = new Dimension(1182, 1461);
        config01Mono01 = GenericStereoDeviceFactory.createMono("Def01Mono01", dimension, array4, array3);
        config02StereoSBS01 = GenericStereoDeviceFactory.createStereoSBS("Def02StereoSBS01", dimension, array4, 0.0635f, 45.0f, array2);
        config03StereoSBSLense01 = GenericStereoDeviceFactory.createStereoSBSLense("Def03StereoSBSLense01", dimension, array4, 0.0635f, 129.0f, dimension2, array);
        config01Mono02 = GenericStereoDeviceFactory.createMono("Def01Mono02", dimension3, array5, array3);
        config02StereoSBS02 = GenericStereoDeviceFactory.createStereoSBS("Def02StereoSBS02", dimension3, array5, 0.0635f, 45.0f, array2);
        config03StereoSBSLense02 = GenericStereoDeviceFactory.createStereoSBSLense("Def03StereoSBSLense02", dimension3, array5, 0.0635f, 129.0f, dimension4, array);
        configs = new GenericStereoDeviceConfig[] { GenericStereoDevice.config01Mono01, GenericStereoDevice.config02StereoSBS01, GenericStereoDevice.config03StereoSBSLense01, GenericStereoDevice.config01Mono02, GenericStereoDevice.config02StereoSBS02, GenericStereoDevice.config03StereoSBSLense02 };
    }
}
