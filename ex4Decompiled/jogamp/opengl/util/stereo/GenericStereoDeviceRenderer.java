// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.stereo;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ShortBuffer;
import java.nio.FloatBuffer;
import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.util.GLArrayDataServer;
import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.common.os.Platform;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.stereo.StereoDevice;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.opengl.util.stereo.EyeParameter;
import jogamp.common.os.PlatformPropsImpl;
import java.util.Arrays;
import com.jogamp.opengl.util.stereo.StereoUtil;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.opengl.util.stereo.ViewerPose;
import com.jogamp.opengl.util.stereo.StereoDeviceRenderer;

public class GenericStereoDeviceRenderer implements StereoDeviceRenderer
{
    private static final String shaderPrefix01 = "dist01";
    private static final String shaderTimewarpSuffix = "_timewarp";
    private static final String shaderChromaSuffix = "_chroma";
    private static final String shaderPlainSuffix = "_plain";
    private final GenericStereoDevice device;
    private final GenericEye[] eyes;
    private final ViewerPose viewerPose;
    private final int distortionBits;
    private final int textureCount;
    private final DimensionImmutable[] eyeTextureSizes;
    private final DimensionImmutable totalTextureSize;
    private final GLUniformData texUnit0;
    private ShaderProgram sp;
    private long frameStart;
    private static final DimensionImmutable zeroSize;
    
    @Override
    public String toString() {
        return "GenericStereo[distortion[" + StereoUtil.distortionBitsToString(this.distortionBits) + "], eyeTexSize " + Arrays.toString(this.eyeTextureSizes) + ", sbsSize " + this.totalTextureSize + ", texCount " + this.textureCount + ", texUnit " + ((null != this.texUnit0) ? this.texUnit0.intValue() : "n/a") + ", " + PlatformPropsImpl.NEWLINE + "  " + ((0 < this.eyes.length) ? this.eyes[0] : "none") + ", " + PlatformPropsImpl.NEWLINE + "  " + ((1 < this.eyes.length) ? this.eyes[1] : "none") + "]";
    }
    
    GenericStereoDeviceRenderer(final GenericStereoDevice device, final int n, final int textureCount, final float[] array, final EyeParameter[] array2, final float n2, final int n3, final DimensionImmutable[] eyeTextureSizes, final DimensionImmutable totalTextureSize, final RectangleImmutable[] array3) {
        this.frameStart = 0L;
        if (array2.length != eyeTextureSizes.length || array2.length != array3.length) {
            throw new IllegalArgumentException("eye arrays of different length");
        }
        this.device = device;
        this.eyes = new GenericEye[array2.length];
        this.distortionBits = ((n | device.getMinimumDistortionBits()) & device.getSupportedDistortionBits());
        DimensionImmutable[] array4;
        if (null != this.device.config.distortionMeshProducer && 0 != this.distortionBits) {
            if (1 > textureCount || 2 < textureCount) {
                this.textureCount = 2;
            }
            else {
                this.textureCount = textureCount;
            }
            this.eyeTextureSizes = eyeTextureSizes;
            this.totalTextureSize = totalTextureSize;
            if (1 == textureCount) {
                array4 = new DimensionImmutable[array2.length];
                for (int i = 0; i < array2.length; ++i) {
                    array4[i] = totalTextureSize;
                }
            }
            else {
                array4 = eyeTextureSizes;
            }
            this.texUnit0 = new GLUniformData("svr_Texture0", n3);
        }
        else {
            this.textureCount = 0;
            this.eyeTextureSizes = new DimensionImmutable[array2.length];
            array4 = new DimensionImmutable[array2.length];
            for (int j = 0; j < array2.length; ++j) {
                this.eyeTextureSizes[j] = GenericStereoDeviceRenderer.zeroSize;
                array4[j] = GenericStereoDeviceRenderer.zeroSize;
            }
            this.totalTextureSize = GenericStereoDeviceRenderer.zeroSize;
            this.texUnit0 = null;
        }
        this.viewerPose = new ViewerPose();
        for (int k = 0; k < array2.length; ++k) {
            this.eyes[k] = new GenericEye(device, this.distortionBits, array, array2[k], array4[k], array3[k]);
        }
        this.sp = null;
    }
    
    @Override
    public StereoDevice getDevice() {
        return this.device;
    }
    
    @Override
    public final int getDistortionBits() {
        return this.distortionBits;
    }
    
    @Override
    public final boolean usesSideBySideStereo() {
        return true;
    }
    
    @Override
    public final DimensionImmutable[] getEyeSurfaceSize() {
        return this.eyeTextureSizes;
    }
    
    @Override
    public final DimensionImmutable getTotalSurfaceSize() {
        return this.totalTextureSize;
    }
    
    @Override
    public final int getTextureCount() {
        return this.textureCount;
    }
    
    @Override
    public final int getTextureUnit() {
        return this.ppAvailable() ? this.texUnit0.intValue() : 0;
    }
    
    @Override
    public final boolean ppAvailable() {
        return null != this.texUnit0;
    }
    
    @Override
    public final void init(final GL gl) {
        if (StereoDevice.DEBUG) {
            System.err.println(JoglVersion.getGLInfo(gl, null).toString());
        }
        if (null != this.sp) {
            throw new IllegalStateException("Already initialized");
        }
        if (!this.ppAvailable()) {
            return;
        }
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final boolean usesTimewarpDistortion = StereoUtil.usesTimewarpDistortion(this.distortionBits);
        final boolean usesChromaticDistortion = StereoUtil.usesChromaticDistortion(this.distortionBits);
        final StringBuilder sb = new StringBuilder();
        sb.append("dist01");
        if (!usesChromaticDistortion && !usesTimewarpDistortion) {
            sb.append("_plain");
        }
        else if (usesChromaticDistortion && !usesTimewarpDistortion) {
            sb.append("_chroma");
        }
        else if (usesTimewarpDistortion) {
            sb.append("_timewarp");
            if (usesChromaticDistortion) {
                sb.append("_chroma");
            }
        }
        final String string = sb.toString();
        sb.setLength(0);
        sb.append("dist01");
        if (usesChromaticDistortion) {
            sb.append("_chroma");
        }
        else {
            sb.append("_plain");
        }
        final String string2 = sb.toString();
        final ShaderCode create = ShaderCode.create(gl2ES2, 35633, GenericStereoDeviceRenderer.class, "shader", "shader/bin", string, true);
        final ShaderCode create2 = ShaderCode.create(gl2ES2, 35632, GenericStereoDeviceRenderer.class, "shader", "shader/bin", string2, true);
        create.defaultShaderCustomization(gl2ES2, true, true);
        create2.defaultShaderCustomization(gl2ES2, true, true);
        (this.sp = new ShaderProgram()).add(gl2ES2, create, System.err);
        this.sp.add(gl2ES2, create2, System.err);
        if (!this.sp.link(gl2ES2, System.err)) {
            throw new GLException("could not link program: " + this.sp);
        }
        this.sp.useProgram(gl2ES2, true);
        if (0 > this.texUnit0.setLocation(gl2ES2, this.sp.program())) {
            throw new GLException("Couldn't locate " + this.texUnit0);
        }
        for (int i = 0; i < this.eyes.length; ++i) {
            this.eyes[i].linkData(gl2ES2, this.sp);
        }
        this.sp.useProgram(gl2ES2, false);
    }
    
    @Override
    public final void dispose(final GL gl) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        if (null != this.sp) {
            this.sp.useProgram(gl2ES2, false);
        }
        for (int i = 0; i < this.eyes.length; ++i) {
            this.eyes[i].dispose(gl2ES2);
        }
        if (null != this.sp) {
            this.sp.destroy(gl2ES2);
        }
    }
    
    @Override
    public final Eye getEye(final int n) {
        return this.eyes[n];
    }
    
    @Override
    public final ViewerPose updateViewerPose() {
        return this.viewerPose;
    }
    
    @Override
    public final ViewerPose getLastViewerPose() {
        return this.viewerPose;
    }
    
    @Override
    public final void beginFrame(final GL gl) {
        this.frameStart = Platform.currentTimeMillis();
    }
    
    @Override
    public final void endFrame(final GL gl) {
        if (0L == this.frameStart) {
            throw new IllegalStateException("beginFrame not called");
        }
        this.frameStart = 0L;
    }
    
    @Override
    public final void ppBegin(final GL gl) {
        if (null == this.sp) {
            throw new IllegalStateException("Not initialized");
        }
        if (0L == this.frameStart) {
            throw new IllegalStateException("beginFrame not called");
        }
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(16384);
        gl.glActiveTexture(33984 + this.getTextureUnit());
        gl2ES2.glDisable(2884);
        gl2ES2.glDisable(2929);
        gl2ES2.glDisable(3042);
        if (!gl2ES2.isGLcore()) {
            gl2ES2.glEnable(3553);
        }
        this.sp.useProgram(gl2ES2, true);
        gl2ES2.glUniform(this.texUnit0);
    }
    
    @Override
    public final void ppOneEye(final GL gl, final int n) {
        final GenericEye genericEye = this.eyes[n];
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        genericEye.updateUniform(gl2ES2, this.sp);
        genericEye.enableVBO(gl2ES2, true);
        gl2ES2.glDrawElements(4, genericEye.indexCount, 5123, 0L);
        this.eyes[n].enableVBO(gl2ES2, false);
    }
    
    @Override
    public final void ppEnd(final GL gl) {
        this.sp.useProgram(gl.getGL2ES2(), false);
    }
    
    static {
        zeroSize = new Dimension(0, 0);
    }
    
    public static class GenericEye implements Eye
    {
        private final int eyeName;
        private final int distortionBits;
        private final int vertexCount;
        private final int indexCount;
        private final RectangleImmutable viewport;
        private final GLUniformData eyeToSourceUVScale;
        private final GLUniformData eyeToSourceUVOffset;
        private final GLUniformData eyeRotationStart;
        private final GLUniformData eyeRotationEnd;
        private final GLArrayDataServer iVBO;
        private final GLArrayData vboPos;
        private final GLArrayData vboParams;
        private final GLArrayData vboTexCoordsR;
        private final GLArrayData vboTexCoordsG;
        private final GLArrayData vboTexCoordsB;
        private final GLArrayDataServer indices;
        private final EyeParameter eyeParameter;
        
        @Override
        public final RectangleImmutable getViewport() {
            return this.viewport;
        }
        
        @Override
        public final EyeParameter getEyeParameter() {
            return this.eyeParameter;
        }
        
        GenericEye(final GenericStereoDevice genericStereoDevice, final int distortionBits, final float[] array, final EyeParameter eyeParameter, final DimensionImmutable dimensionImmutable, final RectangleImmutable viewport) {
            this.eyeName = eyeParameter.number;
            this.distortionBits = distortionBits;
            this.viewport = viewport;
            final boolean b = null != genericStereoDevice.config.distortionMeshProducer && 0 != distortionBits;
            final boolean b2 = b && StereoUtil.usesTimewarpDistortion(distortionBits);
            final FloatBuffer directFloatBuffer = Buffers.newDirectFloatBuffer(4 + (b2 ? 32 : 0));
            if (b) {
                this.eyeToSourceUVScale = new GLUniformData("svr_EyeToSourceUVScale", 2, Buffers.slice2Float(directFloatBuffer, 0, 2));
                this.eyeToSourceUVOffset = new GLUniformData("svr_EyeToSourceUVOffset", 2, Buffers.slice2Float(directFloatBuffer, 2, 2));
            }
            else {
                this.eyeToSourceUVScale = null;
                this.eyeToSourceUVOffset = null;
            }
            if (b2) {
                this.eyeRotationStart = new GLUniformData("svr_EyeRotationStart", 4, 4, Buffers.slice2Float(directFloatBuffer, 4, 16));
                this.eyeRotationEnd = new GLUniformData("svr_EyeRotationEnd", 4, 4, Buffers.slice2Float(directFloatBuffer, 20, 16));
            }
            else {
                this.eyeRotationStart = null;
                this.eyeRotationEnd = null;
            }
            this.eyeParameter = eyeParameter;
            if (!b) {
                this.vertexCount = 0;
                this.indexCount = 0;
                this.iVBO = null;
                this.vboPos = null;
                this.vboParams = null;
                this.vboTexCoordsR = null;
                this.vboTexCoordsG = null;
                this.vboTexCoordsB = null;
                this.indices = null;
                if (StereoDevice.DEBUG) {
                    System.err.println("XXX." + this.eyeName + ": " + this);
                }
                return;
            }
            final ScaleAndOffset2D scaleAndOffset2D = new ScaleAndOffset2D(eyeParameter.fovhv, dimensionImmutable, viewport);
            if (StereoDevice.DEBUG) {
                System.err.println("XXX." + this.eyeName + ": eyeParam      " + eyeParameter);
                System.err.println("XXX." + this.eyeName + ": uvScaleOffset " + scaleAndOffset2D);
                System.err.println("XXX." + this.eyeName + ": textureSize   " + dimensionImmutable);
                System.err.println("XXX." + this.eyeName + ": viewport      " + viewport);
            }
            final FloatBuffer floatBufferValue = this.eyeToSourceUVScale.floatBufferValue();
            floatBufferValue.put(0, scaleAndOffset2D.scale[0]);
            floatBufferValue.put(1, scaleAndOffset2D.scale[1]);
            final FloatBuffer floatBufferValue2 = this.eyeToSourceUVOffset.floatBufferValue();
            floatBufferValue2.put(0, scaleAndOffset2D.offset[0]);
            floatBufferValue2.put(1, scaleAndOffset2D.offset[1]);
            final DistortionMesh create = genericStereoDevice.config.distortionMeshProducer.create(eyeParameter, distortionBits);
            if (null == create) {
                throw new GLException("Failed to create meshData for eye " + eyeParameter + ", and " + StereoUtil.distortionBitsToString(distortionBits));
            }
            this.vertexCount = create.vertexCount;
            this.indexCount = create.indexCount;
            final boolean usesChromaticDistortion = StereoUtil.usesChromaticDistortion(distortionBits);
            final boolean usesVignetteDistortion = StereoUtil.usesVignetteDistortion(distortionBits);
            this.iVBO = GLArrayDataServer.createGLSLInterleaved(6 + (usesChromaticDistortion ? 4 : 0), 5126, false, this.vertexCount, 35044);
            this.vboPos = this.iVBO.addGLSLSubArray("svr_Position", 2, 34962);
            this.vboParams = this.iVBO.addGLSLSubArray("svr_Params", 2, 34962);
            this.vboTexCoordsR = this.iVBO.addGLSLSubArray("svr_TexCoordR", 2, 34962);
            if (usesChromaticDistortion) {
                this.vboTexCoordsG = this.iVBO.addGLSLSubArray("svr_TexCoordG", 2, 34962);
                this.vboTexCoordsB = this.iVBO.addGLSLSubArray("svr_TexCoordB", 2, 34962);
            }
            else {
                this.vboTexCoordsG = null;
                this.vboTexCoordsB = null;
            }
            this.indices = GLArrayDataServer.createData(1, 5122, this.indexCount, 35044, 34963);
            final FloatBuffer floatBuffer = (FloatBuffer)this.iVBO.getBuffer();
            for (int i = 0; i < this.vertexCount; ++i) {
                final DistortionMesh.DistortionVertex distortionVertex = create.vertices[i];
                final int n = 0;
                if (StereoDevice.DUMP_DATA) {
                    System.err.println("XXX." + this.eyeName + ": START VERTEX " + i + " / " + this.vertexCount);
                }
                if (distortionVertex.pos_size >= 2) {
                    if (StereoDevice.DUMP_DATA) {
                        System.err.println("XXX." + this.eyeName + ": pos [" + distortionVertex.data[n] + ", " + distortionVertex.data[n + 1] + "]");
                    }
                    floatBuffer.put(distortionVertex.data[n]);
                    floatBuffer.put(distortionVertex.data[n + 1]);
                }
                else {
                    floatBuffer.put(0.0f);
                    floatBuffer.put(0.0f);
                }
                final int n2 = n + distortionVertex.pos_size;
                if (distortionVertex.vignetteFactor_size >= 1 && usesVignetteDistortion) {
                    if (StereoDevice.DUMP_DATA) {
                        System.err.println("XXX." + this.eyeName + ": vignette " + distortionVertex.data[n2]);
                    }
                    floatBuffer.put(distortionVertex.data[n2]);
                }
                else {
                    floatBuffer.put(1.0f);
                }
                final int n3 = n2 + distortionVertex.vignetteFactor_size;
                if (distortionVertex.timewarpFactor_size >= 1) {
                    if (StereoDevice.DUMP_DATA) {
                        System.err.println("XXX." + this.eyeName + ": timewarp " + distortionVertex.data[n3]);
                    }
                    floatBuffer.put(distortionVertex.data[n3]);
                }
                else {
                    floatBuffer.put(1.0f);
                }
                final int n4 = n3 + distortionVertex.timewarpFactor_size;
                if (distortionVertex.texR_size >= 2) {
                    if (StereoDevice.DUMP_DATA) {
                        System.err.println("XXX." + this.eyeName + ": texR [" + distortionVertex.data[n4] + ", " + distortionVertex.data[n4 + 1] + "]");
                    }
                    floatBuffer.put(distortionVertex.data[n4]);
                    floatBuffer.put(distortionVertex.data[n4 + 1]);
                }
                else {
                    floatBuffer.put(1.0f);
                    floatBuffer.put(1.0f);
                }
                final int n5 = n4 + distortionVertex.texR_size;
                if (usesChromaticDistortion) {
                    if (distortionVertex.texG_size >= 2) {
                        if (StereoDevice.DUMP_DATA) {
                            System.err.println("XXX." + this.eyeName + ": texG [" + distortionVertex.data[n5] + ", " + distortionVertex.data[n5 + 1] + "]");
                        }
                        floatBuffer.put(distortionVertex.data[n5]);
                        floatBuffer.put(distortionVertex.data[n5 + 1]);
                    }
                    else {
                        floatBuffer.put(1.0f);
                        floatBuffer.put(1.0f);
                    }
                    final int n6 = n5 + distortionVertex.texG_size;
                    if (distortionVertex.texB_size >= 2) {
                        if (StereoDevice.DUMP_DATA) {
                            System.err.println("XXX." + this.eyeName + ": texB [" + distortionVertex.data[n6] + ", " + distortionVertex.data[n6 + 1] + "]");
                        }
                        floatBuffer.put(distortionVertex.data[n6]);
                        floatBuffer.put(distortionVertex.data[n6 + 1]);
                    }
                    else {
                        floatBuffer.put(1.0f);
                        floatBuffer.put(1.0f);
                    }
                    final int n7 = n6 + distortionVertex.texB_size;
                }
                else {
                    final int n8 = n5 + distortionVertex.texG_size + distortionVertex.texB_size;
                }
            }
            if (StereoDevice.DUMP_DATA) {
                System.err.println("XXX." + this.eyeName + ": iVBO " + this.iVBO);
            }
            if (StereoDevice.DUMP_DATA) {
                System.err.println("XXX." + this.eyeName + ": idx " + this.indices + ", count " + this.indexCount);
                for (int j = 0; j < this.indexCount; ++j) {
                    if (0 == j % 16) {
                        System.err.printf("%n%5d: ", j);
                    }
                    System.err.printf("%5d, ", create.indices[j]);
                }
                System.err.println();
            }
            ((ShortBuffer)this.indices.getBuffer()).put(create.indices, 0, create.indexCount);
            if (StereoDevice.DEBUG) {
                System.err.println("XXX." + this.eyeName + ": " + this);
            }
        }
        
        private void linkData(final GL2ES2 gl2ES2, final ShaderProgram shaderProgram) {
            if (null == this.iVBO) {
                return;
            }
            if (0 > this.vboPos.setLocation(gl2ES2, shaderProgram.program())) {
                throw new GLException("Couldn't locate " + this.vboPos);
            }
            if (0 > this.vboParams.setLocation(gl2ES2, shaderProgram.program())) {
                throw new GLException("Couldn't locate " + this.vboParams);
            }
            if (0 > this.vboTexCoordsR.setLocation(gl2ES2, shaderProgram.program())) {
                throw new GLException("Couldn't locate " + this.vboTexCoordsR);
            }
            if (StereoUtil.usesChromaticDistortion(this.distortionBits)) {
                if (0 > this.vboTexCoordsG.setLocation(gl2ES2, shaderProgram.program())) {
                    throw new GLException("Couldn't locate " + this.vboTexCoordsG);
                }
                if (0 > this.vboTexCoordsB.setLocation(gl2ES2, shaderProgram.program())) {
                    throw new GLException("Couldn't locate " + this.vboTexCoordsB);
                }
            }
            if (0 > this.eyeToSourceUVScale.setLocation(gl2ES2, shaderProgram.program())) {
                throw new GLException("Couldn't locate " + this.eyeToSourceUVScale);
            }
            if (0 > this.eyeToSourceUVOffset.setLocation(gl2ES2, shaderProgram.program())) {
                throw new GLException("Couldn't locate " + this.eyeToSourceUVOffset);
            }
            if (StereoUtil.usesTimewarpDistortion(this.distortionBits)) {
                if (0 > this.eyeRotationStart.setLocation(gl2ES2, shaderProgram.program())) {
                    throw new GLException("Couldn't locate " + this.eyeRotationStart);
                }
                if (0 > this.eyeRotationEnd.setLocation(gl2ES2, shaderProgram.program())) {
                    throw new GLException("Couldn't locate " + this.eyeRotationEnd);
                }
            }
            this.iVBO.seal(gl2ES2, true);
            this.iVBO.enableBuffer(gl2ES2, false);
            this.indices.seal(gl2ES2, true);
            this.indices.enableBuffer(gl2ES2, false);
        }
        
        void dispose(final GL2ES2 gl2ES2) {
            if (null == this.iVBO) {
                return;
            }
            this.iVBO.destroy(gl2ES2);
            this.indices.destroy(gl2ES2);
        }
        
        void enableVBO(final GL2ES2 gl2ES2, final boolean b) {
            if (null == this.iVBO) {
                return;
            }
            this.iVBO.enableBuffer(gl2ES2, b);
            this.indices.bindBuffer(gl2ES2, b);
        }
        
        void updateUniform(final GL2ES2 gl2ES2, final ShaderProgram shaderProgram) {
            if (null == this.iVBO) {
                return;
            }
            gl2ES2.glUniform(this.eyeToSourceUVScale);
            gl2ES2.glUniform(this.eyeToSourceUVOffset);
            if (StereoUtil.usesTimewarpDistortion(this.distortionBits)) {
                gl2ES2.glUniform(this.eyeRotationStart);
                gl2ES2.glUniform(this.eyeRotationEnd);
            }
        }
        
        @Override
        public String toString() {
            return "Eye[" + this.eyeName + ", viewport " + this.viewport + ", " + this.eyeParameter + ", vertices " + this.vertexCount + ", indices " + this.indexCount + ((null == this.iVBO) ? ", no post-processing" : (", uvScale[" + this.eyeToSourceUVScale.floatBufferValue().get(0) + ", " + this.eyeToSourceUVScale.floatBufferValue().get(1) + "], uvOffset[" + this.eyeToSourceUVOffset.floatBufferValue().get(0) + ", " + this.eyeToSourceUVOffset.floatBufferValue().get(1) + "]")) + ", desc " + this.eyeParameter + "]";
        }
    }
}
