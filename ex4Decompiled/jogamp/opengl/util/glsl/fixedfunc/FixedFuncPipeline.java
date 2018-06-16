// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.glsl.fixedfunc;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.GLES2;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.fixedfunc.GLPointerFuncUtil;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.opengl.GLException;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.glsl.fixedfunc.ShaderSelectionMode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.util.glsl.ShaderState;
import com.jogamp.opengl.util.PMVMatrix;
import java.nio.IntBuffer;
import com.jogamp.common.util.IntIntHashMap;
import java.nio.FloatBuffer;
import com.jogamp.opengl.GLRunnable2;

public class FixedFuncPipeline
{
    protected static final boolean DEBUG;
    public static final int MAX_TEXTURE_UNITS = 8;
    public static final int MAX_LIGHTS = 8;
    private static final GLRunnable2<Object, Object> glDrawArraysAction;
    private static final String constMaxTextures0 = "#define MAX_TEXTURE_UNITS 0\n";
    private static final String constMaxTextures2 = "#define MAX_TEXTURE_UNITS 2\n";
    private static final String constMaxTextures4 = "#define MAX_TEXTURE_UNITS 4\n";
    private static final String constMaxTextures8 = "#define MAX_TEXTURE_UNITS 8\n";
    protected boolean verbose;
    private final FloatBuffer colorStatic;
    private int activeTextureUnit;
    private int clientActiveTextureUnit;
    private final IntIntHashMap texID2Format;
    private final int[] boundTextureObject;
    private int textureEnabledBits;
    private final IntBuffer textureEnabled;
    private boolean textureEnabledDirty;
    private final IntBuffer textureCoordEnabled;
    private boolean textureCoordEnabledDirty;
    private final IntBuffer textureEnvMode;
    private boolean textureEnvModeDirty;
    private final IntBuffer textureFormat;
    private boolean textureFormatDirty;
    private boolean colorVAEnabledDirty;
    private boolean lightingEnabled;
    private final IntBuffer lightsEnabled;
    private boolean lightsEnabledDirty;
    private boolean alphaTestDirty;
    private int alphaTestFunc;
    private float alphaTestRef;
    private boolean pointParamsDirty;
    private final FloatBuffer pointParams;
    private PMVMatrix pmvMatrix;
    private ShaderState shaderState;
    private ShaderProgram shaderProgramColor;
    private ShaderProgram shaderProgramColorTexture2;
    private ShaderProgram shaderProgramColorTexture4;
    private ShaderProgram shaderProgramColorTexture8;
    private ShaderProgram shaderProgramColorLight;
    private ShaderProgram shaderProgramColorTexture8Light;
    private ShaderProgram shaderProgramPoints;
    private ShaderSelectionMode requestedShaderSelectionMode;
    private ShaderSelectionMode currentShaderSelectionMode;
    private static final String mgl_PMVMatrix = "mgl_PMVMatrix";
    private static final String mgl_ColorEnabled = "mgl_ColorEnabled";
    private static final String mgl_ColorStatic = "mgl_ColorStatic";
    private static final String mgl_LightModel = "mgl_LightModel";
    private static final String mgl_LightSource = "mgl_LightSource";
    private static final String mgl_FrontMaterial = "mgl_FrontMaterial";
    private static final String mgl_LightsEnabled = "mgl_LightsEnabled";
    private static final String mgl_AlphaTestFunc = "mgl_AlphaTestFunc";
    private static final String mgl_AlphaTestRef = "mgl_AlphaTestRef";
    private static final String mgl_ShadeModel = "mgl_ShadeModel";
    private static final String mgl_PointParams = "mgl_PointParams";
    private static final String mgl_TextureEnabled = "mgl_TextureEnabled";
    private static final String mgl_Texture = "mgl_Texture";
    private static final String mgl_TexCoordEnabled = "mgl_TexCoordEnabled";
    private static final String mgl_TexEnvMode = "mgl_TexEnvMode";
    private static final String mgl_TexFormat = "mgl_TexFormat";
    private static final FloatBuffer neut4f;
    private static final FloatBuffer one4f;
    public static final FloatBuffer defAmbient;
    public static final FloatBuffer defDiffuseN;
    public static final FloatBuffer defSpecularN;
    public static final FloatBuffer defPosition;
    public static final FloatBuffer defSpotDir;
    public static final float defSpotExponent = 0.0f;
    public static final float defSpotCutoff = 180.0f;
    public static final float defConstantAtten = 1.0f;
    public static final float defLinearAtten = 0.0f;
    public static final float defQuadraticAtten = 0.0f;
    public static final FloatBuffer defLightModelAmbient;
    public static final FloatBuffer defMatAmbient;
    public static final FloatBuffer defMatDiffuse;
    public static final FloatBuffer defMatSpecular;
    public static final FloatBuffer defMatEmission;
    public static final float defMatShininess = 0.0f;
    private static final String vertexColorFileDef = "FixedFuncColor";
    private static final String vertexColorLightFileDef = "FixedFuncColorLight";
    private static final String fragmentColorFileDef = "FixedFuncColor";
    private static final String fragmentColorTextureFileDef = "FixedFuncColorTexture";
    private static final String shaderPointFileDef = "FixedFuncPoints";
    private static final String shaderSrcRootDef = "shaders";
    private static final String shaderBinRootDef = "shaders/bin";
    private final Class<?> shaderRootClass;
    private final String shaderSrcRoot;
    private final String shaderBinRoot;
    private final String vertexColorFile;
    private final String vertexColorLightFile;
    private final String fragmentColorFile;
    private final String fragmentColorTextureFile;
    
    public FixedFuncPipeline(final GL2ES2 gl2ES2, final ShaderSelectionMode shaderSelectionMode, final PMVMatrix pmvMatrix) {
        this.verbose = FixedFuncPipeline.DEBUG;
        this.colorStatic = Buffers.copyFloatBuffer(FixedFuncPipeline.one4f);
        this.activeTextureUnit = 0;
        this.clientActiveTextureUnit = 0;
        this.texID2Format = new IntIntHashMap();
        this.boundTextureObject = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        this.textureEnabledBits = 0;
        this.textureEnabled = Buffers.newDirectIntBuffer(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        this.textureEnabledDirty = false;
        this.textureCoordEnabled = Buffers.newDirectIntBuffer(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        this.textureCoordEnabledDirty = false;
        this.textureEnvMode = Buffers.newDirectIntBuffer(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
        this.textureEnvModeDirty = false;
        this.textureFormat = Buffers.newDirectIntBuffer(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        this.textureFormatDirty = false;
        this.colorVAEnabledDirty = false;
        this.lightingEnabled = false;
        this.lightsEnabled = Buffers.newDirectIntBuffer(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        this.lightsEnabledDirty = false;
        this.alphaTestDirty = false;
        this.alphaTestFunc = -8;
        this.alphaTestRef = 0.0f;
        this.pointParamsDirty = false;
        this.pointParams = Buffers.newDirectFloatBuffer(new float[] { 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f });
        this.requestedShaderSelectionMode = ShaderSelectionMode.AUTO;
        this.currentShaderSelectionMode = this.requestedShaderSelectionMode;
        this.shaderRootClass = FixedFuncPipeline.class;
        this.shaderSrcRoot = "shaders";
        this.shaderBinRoot = "shaders/bin";
        this.vertexColorFile = "FixedFuncColor";
        this.vertexColorLightFile = "FixedFuncColorLight";
        this.fragmentColorFile = "FixedFuncColor";
        this.fragmentColorTextureFile = "FixedFuncColorTexture";
        this.init(gl2ES2, shaderSelectionMode, pmvMatrix);
    }
    
    public FixedFuncPipeline(final GL2ES2 gl2ES2, final ShaderSelectionMode shaderSelectionMode, final PMVMatrix pmvMatrix, final Class<?> shaderRootClass, final String shaderSrcRoot, final String shaderBinRoot, final String vertexColorFile, final String vertexColorLightFile, final String fragmentColorFile, final String fragmentColorTextureFile) {
        this.verbose = FixedFuncPipeline.DEBUG;
        this.colorStatic = Buffers.copyFloatBuffer(FixedFuncPipeline.one4f);
        this.activeTextureUnit = 0;
        this.clientActiveTextureUnit = 0;
        this.texID2Format = new IntIntHashMap();
        this.boundTextureObject = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        this.textureEnabledBits = 0;
        this.textureEnabled = Buffers.newDirectIntBuffer(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        this.textureEnabledDirty = false;
        this.textureCoordEnabled = Buffers.newDirectIntBuffer(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        this.textureCoordEnabledDirty = false;
        this.textureEnvMode = Buffers.newDirectIntBuffer(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
        this.textureEnvModeDirty = false;
        this.textureFormat = Buffers.newDirectIntBuffer(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        this.textureFormatDirty = false;
        this.colorVAEnabledDirty = false;
        this.lightingEnabled = false;
        this.lightsEnabled = Buffers.newDirectIntBuffer(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        this.lightsEnabledDirty = false;
        this.alphaTestDirty = false;
        this.alphaTestFunc = -8;
        this.alphaTestRef = 0.0f;
        this.pointParamsDirty = false;
        this.pointParams = Buffers.newDirectFloatBuffer(new float[] { 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f });
        this.requestedShaderSelectionMode = ShaderSelectionMode.AUTO;
        this.currentShaderSelectionMode = this.requestedShaderSelectionMode;
        this.shaderRootClass = shaderRootClass;
        this.shaderSrcRoot = shaderSrcRoot;
        this.shaderBinRoot = shaderBinRoot;
        this.vertexColorFile = vertexColorFile;
        this.vertexColorLightFile = vertexColorLightFile;
        this.fragmentColorFile = fragmentColorFile;
        this.fragmentColorTextureFile = fragmentColorTextureFile;
        this.init(gl2ES2, shaderSelectionMode, pmvMatrix);
    }
    
    public ShaderSelectionMode getShaderSelectionMode() {
        return this.requestedShaderSelectionMode;
    }
    
    public void setShaderSelectionMode(final ShaderSelectionMode requestedShaderSelectionMode) {
        this.requestedShaderSelectionMode = requestedShaderSelectionMode;
    }
    
    public ShaderSelectionMode getCurrentShaderSelectionMode() {
        return this.currentShaderSelectionMode;
    }
    
    public boolean verbose() {
        return this.verbose;
    }
    
    public void setVerbose(final boolean b) {
        this.verbose = (FixedFuncPipeline.DEBUG || b);
    }
    
    public boolean isValid() {
        return this.shaderState.linked();
    }
    
    public ShaderState getShaderState() {
        return this.shaderState;
    }
    
    public int getActiveTextureUnit() {
        return this.activeTextureUnit;
    }
    
    public void destroy(final GL2ES2 gl2ES2) {
        if (null != this.shaderProgramColor) {
            this.shaderProgramColor.release(gl2ES2, true);
        }
        if (null != this.shaderProgramColorLight) {
            this.shaderProgramColorLight.release(gl2ES2, true);
        }
        if (null != this.shaderProgramColorTexture2) {
            this.shaderProgramColorTexture2.release(gl2ES2, true);
        }
        if (null != this.shaderProgramColorTexture4) {
            this.shaderProgramColorTexture4.release(gl2ES2, true);
        }
        if (null != this.shaderProgramColorTexture4) {
            this.shaderProgramColorTexture4.release(gl2ES2, true);
        }
        if (null != this.shaderProgramColorTexture8Light) {
            this.shaderProgramColorTexture8Light.release(gl2ES2, true);
        }
        this.shaderState.destroy(gl2ES2);
    }
    
    public void glColor4f(final GL2ES2 gl2ES2, final float n, final float n2, final float n3, final float n4) {
        this.colorStatic.put(0, n);
        this.colorStatic.put(1, n2);
        this.colorStatic.put(2, n3);
        this.colorStatic.put(3, n4);
        this.shaderState.useProgram(gl2ES2, true);
        final GLUniformData uniform = this.shaderState.getUniform("mgl_ColorStatic");
        if (null != uniform) {
            this.shaderState.uniform(gl2ES2, uniform);
            return;
        }
        throw new GLException("Failed to update: mgl_ColorStatic");
    }
    
    public void glEnableClientState(final GL2ES2 gl2ES2, final int n) {
        this.glToggleClientState(gl2ES2, n, true);
    }
    
    public void glDisableClientState(final GL2ES2 gl2ES2, final int n) {
        this.glToggleClientState(gl2ES2, n, false);
    }
    
    private void glToggleClientState(final GL2ES2 gl2ES2, final int n, final boolean b) {
        final String predefinedArrayIndexName = GLPointerFuncUtil.getPredefinedArrayIndexName(n, this.clientActiveTextureUnit);
        if (null == predefinedArrayIndexName) {
            throw new GLException("arrayIndex " + this.toHexString(n) + " unknown");
        }
        this.shaderState.useProgram(gl2ES2, true);
        if (b) {
            this.shaderState.enableVertexAttribArray(gl2ES2, predefinedArrayIndexName);
        }
        else {
            this.shaderState.disableVertexAttribArray(gl2ES2, predefinedArrayIndexName);
        }
        switch (n) {
            case 32888: {
                final int n2 = b ? 1 : 0;
                if (this.textureCoordEnabled.get(this.clientActiveTextureUnit) != n2) {
                    this.textureCoordEnabled.put(this.clientActiveTextureUnit, n2);
                    this.textureCoordEnabledDirty = true;
                    break;
                }
                break;
            }
            case 32886: {
                this.colorVAEnabledDirty = true;
                break;
            }
        }
    }
    
    public void glVertexPointer(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        this.shaderState.useProgram(gl2ES2, true);
        this.shaderState.vertexAttribPointer(gl2ES2, glArrayData);
    }
    
    public void glColorPointer(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        this.shaderState.useProgram(gl2ES2, true);
        this.shaderState.vertexAttribPointer(gl2ES2, glArrayData);
    }
    
    public void glNormalPointer(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        this.shaderState.useProgram(gl2ES2, true);
        this.shaderState.vertexAttribPointer(gl2ES2, glArrayData);
    }
    
    private boolean glEnableTexture(final boolean b, final int n) {
        final boolean b2 = 0x0 != (this.textureEnabledBits & 1 << this.activeTextureUnit);
        if (b2 != b) {
            if (b) {
                this.textureEnabledBits |= 1 << n;
                this.textureEnabled.put(n, 1);
            }
            else {
                this.textureEnabledBits &= ~(1 << n);
                this.textureEnabled.put(n, 0);
            }
            this.textureEnabledDirty = true;
        }
        return b2;
    }
    
    public void glClientActiveTexture(int clientActiveTextureUnit) {
        clientActiveTextureUnit -= 33984;
        if (0 <= clientActiveTextureUnit && clientActiveTextureUnit < 8) {
            this.clientActiveTextureUnit = clientActiveTextureUnit;
            return;
        }
        throw new GLException("glClientActiveTexture textureUnit not within GL_TEXTURE0 + [0..8]: " + clientActiveTextureUnit);
    }
    
    public void glActiveTexture(int activeTextureUnit) {
        activeTextureUnit -= 33984;
        if (0 <= activeTextureUnit && activeTextureUnit < 8) {
            this.activeTextureUnit = activeTextureUnit;
            return;
        }
        throw new GLException("glActivateTexture textureUnit not within GL_TEXTURE0 + [0..8]: " + activeTextureUnit);
    }
    
    public void glTexCoordPointer(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        if (32888 != glArrayData.getIndex()) {
            throw new GLException("Invalid GLArrayData Index " + this.toHexString(glArrayData.getIndex()) + ", " + glArrayData);
        }
        this.shaderState.useProgram(gl2ES2, true);
        glArrayData.setName(GLPointerFuncUtil.getPredefinedArrayIndexName(glArrayData.getIndex(), this.clientActiveTextureUnit));
        this.shaderState.vertexAttribPointer(gl2ES2, glArrayData);
    }
    
    public void glBindTexture(final int n, final int n2) {
        if (3553 == n) {
            if (n2 != this.boundTextureObject[this.activeTextureUnit]) {
                this.boundTextureObject[this.activeTextureUnit] = n2;
                this.textureFormatDirty = true;
            }
        }
        else {
            System.err.println("FixedFuncPipeline: Unimplemented glBindTexture for target " + this.toHexString(n) + ". Texture name " + this.toHexString(n2));
        }
    }
    
    public void glTexImage2D(final int n, final int n2, final int n3) {
        if (3553 == n) {
            int n4 = 0;
            switch (n2) {
                case 3:
                case 6407:
                case 32849:
                case 32850:
                case 36194: {
                    n4 = 3;
                    break;
                }
                case 4:
                case 6408:
                case 32854:
                case 32855:
                case 32856:
                case 32857: {
                    n4 = 4;
                    break;
                }
                default: {
                    System.err.println("FixedFuncPipeline: glTexImage2D TEXTURE_2D: Unimplemented internalformat " + this.toHexString(n2));
                    n4 = 4;
                    break;
                }
            }
            if (n4 != this.texID2Format.put(this.boundTextureObject[this.activeTextureUnit], n4)) {
                this.textureFormatDirty = true;
            }
        }
        else {
            System.err.println("FixedFuncPipeline: Unimplemented glTexImage2D: target " + this.toHexString(n) + ", internalformat " + this.toHexString(n2));
        }
    }
    
    public void glTexEnvi(final int n, final int n2, final int n3) {
        if (8960 == n && 8704 == n2) {
            int textureEnvMode = 0;
            switch (n3) {
                case 260: {
                    textureEnvMode = 1;
                    break;
                }
                case 8448: {
                    textureEnvMode = 2;
                    break;
                }
                case 8449: {
                    textureEnvMode = 3;
                    break;
                }
                case 3042: {
                    textureEnvMode = 4;
                    break;
                }
                case 7681: {
                    textureEnvMode = 5;
                    break;
                }
                case 34160: {
                    textureEnvMode = 2;
                    System.err.println("FixedFuncPipeline: glTexEnv GL_TEXTURE_ENV_MODE: unimplemented mode: " + this.toHexString(n3));
                    break;
                }
                default: {
                    throw new GLException("glTexEnv GL_TEXTURE_ENV_MODE: invalid mode: " + this.toHexString(n3));
                }
            }
            this.setTextureEnvMode(textureEnvMode);
        }
        else if (this.verbose) {
            System.err.println("FixedFuncPipeline: Unimplemented TexEnv: target " + this.toHexString(n) + ", pname " + this.toHexString(n2) + ", mode: " + this.toHexString(n3));
        }
    }
    
    private void setTextureEnvMode(final int n) {
        if (n != this.textureEnvMode.get(this.activeTextureUnit)) {
            this.textureEnvMode.put(this.activeTextureUnit, n);
            this.textureEnvModeDirty = true;
        }
    }
    
    public void glGetTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        System.err.println("FixedFuncPipeline: Unimplemented glGetTexEnviv: target " + this.toHexString(n) + ", pname " + this.toHexString(n2));
    }
    
    public void glGetTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        System.err.println("FixedFuncPipeline: Unimplemented glGetTexEnviv: target " + this.toHexString(n) + ", pname " + this.toHexString(n2));
    }
    
    public void glPointSize(final float n) {
        this.pointParams.put(0, n);
        this.pointParamsDirty = true;
    }
    
    public void glPointParameterf(final int n, final float n2) {
        switch (n) {
            case 33062: {
                this.pointParams.put(2, n2);
                break;
            }
            case 33063: {
                this.pointParams.put(3, n2);
                break;
            }
            case 33064: {
                this.pointParams.put(7, n2);
                break;
            }
        }
        this.pointParamsDirty = true;
    }
    
    public void glPointParameterfv(final int n, final float[] array, final int n2) {
        switch (n) {
            case 33065: {
                this.pointParams.put(4, array[n2 + 0]);
                this.pointParams.put(5, array[n2 + 1]);
                this.pointParams.put(6, array[n2 + 2]);
                break;
            }
        }
        this.pointParamsDirty = true;
    }
    
    public void glPointParameterfv(final int n, final FloatBuffer floatBuffer) {
        final int position = floatBuffer.position();
        switch (n) {
            case 33065: {
                this.pointParams.put(4, floatBuffer.get(position + 0));
                this.pointParams.put(5, floatBuffer.get(position + 1));
                this.pointParams.put(6, floatBuffer.get(position + 2));
                break;
            }
        }
        this.pointParamsDirty = true;
    }
    
    private void glDrawPoints(final GL2ES2 gl2ES2, final GLRunnable2<Object, Object> glRunnable2, final Object o) {
        if (gl2ES2.isGL2GL3()) {
            gl2ES2.glEnable(34370);
        }
        if (gl2ES2.isGL2ES1()) {
            gl2ES2.glEnable(34913);
        }
        this.loadShaderPoints(gl2ES2);
        this.shaderState.attachShaderProgram(gl2ES2, this.shaderProgramPoints, true);
        this.validate(gl2ES2, false);
        glRunnable2.run(gl2ES2, o);
        if (gl2ES2.isGL2ES1()) {
            gl2ES2.glDisable(34913);
        }
        if (gl2ES2.isGL2GL3()) {
            gl2ES2.glDisable(34370);
        }
        this.shaderState.attachShaderProgram(gl2ES2, this.selectShaderProgram(gl2ES2, this.currentShaderSelectionMode), true);
    }
    
    private final void glDrawPointArrays(final GL2ES2 gl2ES2, final int n, final int n2) {
        this.glDrawPoints(gl2ES2, FixedFuncPipeline.glDrawArraysAction, new int[] { n, n2 });
    }
    
    public void glLightfv(final GL2ES2 gl2ES2, int n, final int n2, final FloatBuffer data) {
        this.shaderState.useProgram(gl2ES2, true);
        n -= 16384;
        if (0 <= n && n < 8) {
            GLUniformData glUniformData = null;
            switch (n2) {
                case 4608: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].ambient");
                    break;
                }
                case 4609: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].diffuse");
                    break;
                }
                case 4610: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].specular");
                    break;
                }
                case 4611: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].position");
                    break;
                }
                case 4612: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].spotDirection");
                    break;
                }
                case 4613: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].spotExponent");
                    break;
                }
                case 4614: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].spotCutoff");
                    break;
                }
                case 4615: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].constantAttenuation");
                    break;
                }
                case 4616: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].linearAttenuation");
                    break;
                }
                case 4617: {
                    glUniformData = this.shaderState.getUniform("mgl_LightSource[" + n + "].quadraticAttenuation");
                    break;
                }
                default: {
                    throw new GLException("glLightfv invalid pname: " + this.toHexString(n2));
                }
            }
            if (null != glUniformData) {
                glUniformData.setData(data);
                this.shaderState.uniform(gl2ES2, glUniformData);
            }
            return;
        }
        throw new GLException("glLightfv light not within [0..8]: " + n);
    }
    
    public void glMaterialfv(final GL2ES2 gl2ES2, final int n, final int n2, final FloatBuffer floatBuffer) {
        this.shaderState.useProgram(gl2ES2, true);
        switch (n) {
            case 1029: {
                System.err.println("FixedFuncPipeline: Unimplemented glMaterialfv GL_BACK face");
                return;
            }
        }
        GLUniformData glUniformData = null;
        switch (n2) {
            case 4608: {
                glUniformData = this.shaderState.getUniform("mgl_FrontMaterial.ambient");
                break;
            }
            case 5634: {
                final GLUniformData uniform = this.shaderState.getUniform("mgl_FrontMaterial.ambient");
                if (null != uniform) {
                    uniform.setData(floatBuffer);
                    this.shaderState.uniform(gl2ES2, uniform);
                }
            }
            case 4609: {
                glUniformData = this.shaderState.getUniform("mgl_FrontMaterial.diffuse");
                break;
            }
            case 4610: {
                glUniformData = this.shaderState.getUniform("mgl_FrontMaterial.specular");
                break;
            }
            case 5632: {
                glUniformData = this.shaderState.getUniform("mgl_FrontMaterial.emission");
                break;
            }
            case 5633: {
                glUniformData = this.shaderState.getUniform("mgl_FrontMaterial.shininess");
                break;
            }
            default: {
                throw new GLException("glMaterialfv invalid pname: " + this.toHexString(n2));
            }
        }
        if (null != glUniformData) {
            glUniformData.setData(floatBuffer);
            this.shaderState.uniform(gl2ES2, glUniformData);
        }
        else if (this.verbose) {}
    }
    
    public void glShadeModel(final GL2ES2 gl2ES2, final int data) {
        this.shaderState.useProgram(gl2ES2, true);
        final GLUniformData uniform = this.shaderState.getUniform("mgl_ShadeModel");
        if (null != uniform) {
            uniform.setData(data);
            this.shaderState.uniform(gl2ES2, uniform);
        }
    }
    
    public void glAlphaFunc(final int n, final float alphaTestRef) {
        int alphaTestFunc = 0;
        switch (n) {
            case 512: {
                alphaTestFunc = 1;
                break;
            }
            case 513: {
                alphaTestFunc = 2;
                break;
            }
            case 514: {
                alphaTestFunc = 3;
                break;
            }
            case 515: {
                alphaTestFunc = 4;
                break;
            }
            case 516: {
                alphaTestFunc = 5;
                break;
            }
            case 517: {
                alphaTestFunc = 6;
                break;
            }
            case 518: {
                alphaTestFunc = 7;
                break;
            }
            case 519: {
                alphaTestFunc = 8;
                break;
            }
            default: {
                throw new GLException("glAlphaFunc invalid func: " + this.toHexString(n));
            }
        }
        if (0 < alphaTestFunc) {
            if (0 > this.alphaTestFunc) {
                alphaTestFunc *= -1;
            }
            if (this.alphaTestFunc != alphaTestFunc || this.alphaTestRef != alphaTestRef) {
                this.alphaTestFunc = alphaTestFunc;
                this.alphaTestRef = alphaTestRef;
                this.alphaTestDirty = true;
            }
        }
    }
    
    public boolean glEnable(final int n, final boolean lightingEnabled) {
        switch (n) {
            case 2929:
            case 2960:
            case 3024:
            case 3042:
            case 3089:
            case 32823:
            case 32926:
            case 32928: {
                return true;
            }
            case 2884: {
                return true;
            }
            case 3553: {
                this.glEnableTexture(lightingEnabled, this.activeTextureUnit);
                return false;
            }
            case 2896: {
                this.lightingEnabled = lightingEnabled;
                return false;
            }
            case 3008: {
                int alphaTestFunc;
                if ((0 > this.alphaTestFunc && lightingEnabled) || (0 < this.alphaTestFunc && !lightingEnabled)) {
                    alphaTestFunc = this.alphaTestFunc * -1;
                }
                else {
                    alphaTestFunc = this.alphaTestFunc;
                }
                if (alphaTestFunc != this.alphaTestFunc) {
                    this.alphaTestDirty = true;
                    this.alphaTestFunc = alphaTestFunc;
                }
                return false;
            }
            case 2832: {
                this.pointParams.put(1, lightingEnabled ? 1.0f : 0.0f);
                this.pointParamsDirty = true;
                return false;
            }
            case 34913: {
                return false;
            }
            default: {
                final int n2 = n - 16384;
                if (0 <= n2 && n2 < 8 && this.lightsEnabled.get(n2) == 1 != lightingEnabled) {
                    this.lightsEnabled.put(n2, lightingEnabled ? 1 : 0);
                    this.lightsEnabledDirty = true;
                    return false;
                }
                System.err.println("FixedFunctionPipeline: " + (lightingEnabled ? "glEnable" : "glDisable") + " " + this.toHexString(n) + " not handled in emulation and not supported in ES2");
                return false;
            }
        }
    }
    
    public void glDrawArrays(final GL2ES2 gl2ES2, int n, final int n2, final int n3) {
        switch (n) {
            case 8: {
                n = 5;
                break;
            }
            case 9: {
                n = 6;
                break;
            }
            case 0: {
                this.glDrawPointArrays(gl2ES2, n2, n3);
                return;
            }
        }
        this.validate(gl2ES2, true);
        if (7 == n && !gl2ES2.isGL2()) {
            for (int i = n2; i < n3 - 3; i += 4) {
                gl2ES2.glDrawArrays(6, i, 4);
            }
        }
        else {
            gl2ES2.glDrawArrays(n, n2, n3);
        }
    }
    
    public void glDrawElements(final GL2ES2 gl2ES2, final int n, final int n2, final int n3, final Buffer buffer) {
        this.validate(gl2ES2, true);
        if (7 == n && !gl2ES2.isGL2()) {
            final int position = buffer.position();
            if (5121 == n3) {
                final ByteBuffer byteBuffer = (ByteBuffer)buffer;
                for (int i = 0; i < n2; ++i) {
                    gl2ES2.glDrawArrays(6, 0xFF & byteBuffer.get(position + i), 4);
                }
            }
            else if (5123 == n3) {
                final ShortBuffer shortBuffer = (ShortBuffer)buffer;
                for (int j = 0; j < n2; ++j) {
                    gl2ES2.glDrawArrays(6, 0xFFFF & shortBuffer.get(position + j), 4);
                }
            }
            else {
                final IntBuffer intBuffer = (IntBuffer)buffer;
                for (int k = 0; k < n2; ++k) {
                    gl2ES2.glDrawArrays(6, -1 & intBuffer.get(position + k), 4);
                }
            }
        }
        else {
            if (!gl2ES2.getContext().isCPUDataSourcingAvail()) {
                throw new GLException("CPU data sourcing n/a w/ " + gl2ES2.getContext());
            }
            ((GLES2)gl2ES2).glDrawElements(n, n2, n3, buffer);
        }
    }
    
    public void glDrawElements(final GL2ES2 gl2ES2, final int n, final int n2, final int n3, final long n4) {
        this.validate(gl2ES2, true);
        if (7 == n && !gl2ES2.isGL2()) {
            throw new GLException("Cannot handle indexed QUADS on !GL2 w/ VBO due to lack of CPU index access");
        }
        gl2ES2.glDrawElements(n, n2, n3, n4);
    }
    
    private final int textureEnabledCount() {
        int n = 0;
        for (int i = 7; i >= 0; --i) {
            if (0 != this.textureEnabled.get(i)) {
                ++n;
            }
        }
        return n;
    }
    
    public void validate(final GL2ES2 gl2ES2, final boolean b) {
        if (b) {
            if (ShaderSelectionMode.AUTO == this.requestedShaderSelectionMode) {
                ShaderSelectionMode shaderSelectionMode;
                if (0 != this.textureEnabledBits) {
                    if (this.lightingEnabled) {
                        shaderSelectionMode = ShaderSelectionMode.COLOR_TEXTURE8_LIGHT_PER_VERTEX;
                    }
                    else {
                        final int textureEnabledCount = this.textureEnabledCount();
                        if (4 < textureEnabledCount) {
                            shaderSelectionMode = ShaderSelectionMode.COLOR_TEXTURE8;
                        }
                        else if (2 < textureEnabledCount) {
                            shaderSelectionMode = ShaderSelectionMode.COLOR_TEXTURE4;
                        }
                        else {
                            shaderSelectionMode = ShaderSelectionMode.COLOR_TEXTURE2;
                        }
                    }
                }
                else if (this.lightingEnabled) {
                    shaderSelectionMode = ShaderSelectionMode.COLOR_LIGHT_PER_VERTEX;
                }
                else {
                    shaderSelectionMode = ShaderSelectionMode.COLOR;
                }
                this.shaderState.attachShaderProgram(gl2ES2, this.selectShaderProgram(gl2ES2, shaderSelectionMode), true);
            }
            else {
                this.shaderState.useProgram(gl2ES2, true);
            }
        }
        if (this.pmvMatrix.update()) {
            final GLUniformData uniform = this.shaderState.getUniform("mgl_PMVMatrix");
            if (null == uniform) {
                throw new GLException("Failed to update: mgl_PMVMatrix");
            }
            FloatBuffer data;
            if (ShaderSelectionMode.COLOR_TEXTURE8_LIGHT_PER_VERTEX == this.currentShaderSelectionMode || ShaderSelectionMode.COLOR_LIGHT_PER_VERTEX == this.currentShaderSelectionMode) {
                data = this.pmvMatrix.glGetPMvMvitMatrixf();
            }
            else {
                data = this.pmvMatrix.glGetPMvMatrixf();
            }
            if (data != uniform.getBuffer()) {
                uniform.setData(data);
            }
            this.shaderState.uniform(gl2ES2, uniform);
        }
        if (this.colorVAEnabledDirty) {
            final GLUniformData uniform2 = this.shaderState.getUniform("mgl_ColorEnabled");
            if (null == uniform2) {
                throw new GLException("Failed to update: mgl_ColorEnabled");
            }
            final int data2 = this.shaderState.isVertexAttribArrayEnabled("mgl_Color") ? 1 : 0;
            if (data2 != uniform2.intValue()) {
                uniform2.setData(data2);
                this.shaderState.uniform(gl2ES2, uniform2);
            }
            this.colorVAEnabledDirty = false;
        }
        if (this.alphaTestDirty) {
            final GLUniformData uniform3 = this.shaderState.getUniform("mgl_AlphaTestFunc");
            if (null != uniform3) {
                uniform3.setData(this.alphaTestFunc);
                this.shaderState.uniform(gl2ES2, uniform3);
            }
            final GLUniformData uniform4 = this.shaderState.getUniform("mgl_AlphaTestRef");
            if (null != uniform4) {
                uniform4.setData(this.alphaTestRef);
                this.shaderState.uniform(gl2ES2, uniform4);
            }
            this.alphaTestDirty = false;
        }
        if (this.pointParamsDirty) {
            final GLUniformData uniform5 = this.shaderState.getUniform("mgl_PointParams");
            if (null != uniform5) {
                this.shaderState.uniform(gl2ES2, uniform5);
            }
            this.pointParamsDirty = false;
        }
        if (this.lightsEnabledDirty) {
            final GLUniformData uniform6 = this.shaderState.getUniform("mgl_LightsEnabled");
            if (null != uniform6) {
                this.shaderState.uniform(gl2ES2, uniform6);
            }
            this.lightsEnabledDirty = false;
        }
        if (this.textureCoordEnabledDirty) {
            final GLUniformData uniform7 = this.shaderState.getUniform("mgl_TexCoordEnabled");
            if (null != uniform7) {
                this.shaderState.uniform(gl2ES2, uniform7);
            }
            this.textureCoordEnabledDirty = false;
        }
        if (this.textureEnvModeDirty) {
            final GLUniformData uniform8 = this.shaderState.getUniform("mgl_TexEnvMode");
            if (null != uniform8) {
                this.shaderState.uniform(gl2ES2, uniform8);
            }
            this.textureEnvModeDirty = false;
        }
        if (this.textureFormatDirty) {
            for (int i = 0; i < 8; ++i) {
                this.textureFormat.put(i, this.texID2Format.get(this.boundTextureObject[i]));
            }
            final GLUniformData uniform9 = this.shaderState.getUniform("mgl_TexFormat");
            if (null != uniform9) {
                this.shaderState.uniform(gl2ES2, uniform9);
            }
            this.textureFormatDirty = false;
        }
        if (this.textureEnabledDirty) {
            final GLUniformData uniform10 = this.shaderState.getUniform("mgl_TextureEnabled");
            if (null != uniform10) {
                this.shaderState.uniform(gl2ES2, uniform10);
            }
            this.textureEnabledDirty = false;
        }
        if (this.verbose) {
            System.err.println("validate: " + this.toString(null, FixedFuncPipeline.DEBUG).toString());
        }
    }
    
    public StringBuilder toString(StringBuilder sb, final boolean b) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("FixedFuncPipeline[");
        sb.append(", textureEnabled: " + this.toHexString(this.textureEnabledBits) + ", ");
        Buffers.toString(sb, null, this.textureEnabled);
        sb.append("\n\t, textureCoordEnabled: ");
        Buffers.toString(sb, null, this.textureCoordEnabled);
        sb.append("\n\t lightingEnabled: " + this.lightingEnabled);
        sb.append(", lightsEnabled: ");
        Buffers.toString(sb, null, this.lightsEnabled);
        sb.append("\n\t, shaderProgramColor: " + this.shaderProgramColor);
        sb.append("\n\t, shaderProgramColorTexture2: " + this.shaderProgramColorTexture2);
        sb.append("\n\t, shaderProgramColorTexture4: " + this.shaderProgramColorTexture4);
        sb.append("\n\t, shaderProgramColorTexture8: " + this.shaderProgramColorTexture8);
        sb.append("\n\t, shaderProgramColorLight: " + this.shaderProgramColorLight);
        sb.append("\n\t, shaderProgramColorTexture8Light: " + this.shaderProgramColorTexture8Light);
        sb.append("\n\t, ShaderState: ");
        this.shaderState.toString(sb, b);
        sb.append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null, FixedFuncPipeline.DEBUG).toString();
    }
    
    private final void customizeShader(final GL2ES2 gl2ES2, final ShaderCode shaderCode, final ShaderCode shaderCode2, final String s) {
        final int defaultShaderCustomization = shaderCode.defaultShaderCustomization(gl2ES2, true, true);
        final int defaultShaderCustomization2 = shaderCode2.defaultShaderCustomization(gl2ES2, true, true);
        shaderCode.insertShaderSource(0, defaultShaderCustomization, s);
        shaderCode2.insertShaderSource(0, defaultShaderCustomization2, s);
    }
    
    private final void loadShaderPoints(final GL2ES2 gl2ES2) {
        if (null != this.shaderProgramPoints) {
            return;
        }
        final ShaderCode create = ShaderCode.create(gl2ES2, 35633, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, "FixedFuncPoints", true);
        final ShaderCode create2 = ShaderCode.create(gl2ES2, 35632, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, "FixedFuncPoints", true);
        this.customizeShader(gl2ES2, create, create2, "#define MAX_TEXTURE_UNITS 2\n");
        (this.shaderProgramPoints = new ShaderProgram()).add(create);
        this.shaderProgramPoints.add(create2);
        if (!this.shaderProgramPoints.link(gl2ES2, System.err)) {
            throw new GLException("Couldn't link VertexColor program: " + this.shaderProgramPoints);
        }
    }
    
    private final void loadShader(final GL2ES2 gl2ES2, final ShaderSelectionMode shaderSelectionMode) {
        final boolean b = ShaderSelectionMode.COLOR == shaderSelectionMode;
        final boolean b2 = ShaderSelectionMode.COLOR_TEXTURE2 == shaderSelectionMode;
        final boolean b3 = ShaderSelectionMode.COLOR_TEXTURE4 == shaderSelectionMode;
        final boolean b4 = ShaderSelectionMode.COLOR_TEXTURE8 == shaderSelectionMode;
        final boolean b5 = b2 || b3 || b4;
        final boolean b6 = ShaderSelectionMode.COLOR_LIGHT_PER_VERTEX == shaderSelectionMode;
        final boolean b7 = ShaderSelectionMode.COLOR_TEXTURE8_LIGHT_PER_VERTEX == shaderSelectionMode;
        if ((null != this.shaderProgramColor && b) || (null != this.shaderProgramColorTexture2 && b2) || (null != this.shaderProgramColorTexture4 && b3) || (null != this.shaderProgramColorTexture8 && b4) || (null != this.shaderProgramColorLight && b6) || (null != this.shaderProgramColorTexture8Light && b7)) {
            return;
        }
        if (b) {
            final ShaderCode create = ShaderCode.create(gl2ES2, 35633, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, this.vertexColorFile, true);
            final ShaderCode create2 = ShaderCode.create(gl2ES2, 35632, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, this.fragmentColorFile, true);
            this.customizeShader(gl2ES2, create, create2, "#define MAX_TEXTURE_UNITS 0\n");
            (this.shaderProgramColor = new ShaderProgram()).add(create);
            this.shaderProgramColor.add(create2);
            if (!this.shaderProgramColor.link(gl2ES2, System.err)) {
                throw new GLException("Couldn't link VertexColor program: " + this.shaderProgramColor);
            }
        }
        else if (b5) {
            final ShaderCode create3 = ShaderCode.create(gl2ES2, 35633, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, this.vertexColorFile, true);
            final ShaderCode create4 = ShaderCode.create(gl2ES2, 35632, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, this.fragmentColorTextureFile, true);
            if (b2) {
                this.customizeShader(gl2ES2, create3, create4, "#define MAX_TEXTURE_UNITS 2\n");
                (this.shaderProgramColorTexture2 = new ShaderProgram()).add(create3);
                this.shaderProgramColorTexture2.add(create4);
                if (!this.shaderProgramColorTexture2.link(gl2ES2, System.err)) {
                    throw new GLException("Couldn't link VertexColorTexture2 program: " + this.shaderProgramColorTexture2);
                }
            }
            else if (b3) {
                this.customizeShader(gl2ES2, create3, create4, "#define MAX_TEXTURE_UNITS 4\n");
                (this.shaderProgramColorTexture4 = new ShaderProgram()).add(create3);
                this.shaderProgramColorTexture4.add(create4);
                if (!this.shaderProgramColorTexture4.link(gl2ES2, System.err)) {
                    throw new GLException("Couldn't link VertexColorTexture4 program: " + this.shaderProgramColorTexture4);
                }
            }
            else if (b4) {
                this.customizeShader(gl2ES2, create3, create4, "#define MAX_TEXTURE_UNITS 8\n");
                (this.shaderProgramColorTexture8 = new ShaderProgram()).add(create3);
                this.shaderProgramColorTexture8.add(create4);
                if (!this.shaderProgramColorTexture8.link(gl2ES2, System.err)) {
                    throw new GLException("Couldn't link VertexColorTexture8 program: " + this.shaderProgramColorTexture8);
                }
            }
        }
        else if (b6) {
            final ShaderCode create5 = ShaderCode.create(gl2ES2, 35633, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, this.vertexColorLightFile, true);
            final ShaderCode create6 = ShaderCode.create(gl2ES2, 35632, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, this.fragmentColorFile, true);
            this.customizeShader(gl2ES2, create5, create6, "#define MAX_TEXTURE_UNITS 0\n");
            (this.shaderProgramColorLight = new ShaderProgram()).add(create5);
            this.shaderProgramColorLight.add(create6);
            if (!this.shaderProgramColorLight.link(gl2ES2, System.err)) {
                throw new GLException("Couldn't link VertexColorLight program: " + this.shaderProgramColorLight);
            }
        }
        else if (b7) {
            final ShaderCode create7 = ShaderCode.create(gl2ES2, 35633, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, this.vertexColorLightFile, true);
            final ShaderCode create8 = ShaderCode.create(gl2ES2, 35632, this.shaderRootClass, this.shaderSrcRoot, this.shaderBinRoot, this.fragmentColorTextureFile, true);
            this.customizeShader(gl2ES2, create7, create8, "#define MAX_TEXTURE_UNITS 8\n");
            (this.shaderProgramColorTexture8Light = new ShaderProgram()).add(create7);
            this.shaderProgramColorTexture8Light.add(create8);
            if (!this.shaderProgramColorTexture8Light.link(gl2ES2, System.err)) {
                throw new GLException("Couldn't link VertexColorLight program: " + this.shaderProgramColorTexture8Light);
            }
        }
    }
    
    private ShaderProgram selectShaderProgram(final GL2ES2 gl2ES2, ShaderSelectionMode color) {
        if (ShaderSelectionMode.AUTO == color) {
            color = ShaderSelectionMode.COLOR;
        }
        this.loadShader(gl2ES2, color);
        ShaderProgram shaderProgram = null;
        switch (color) {
            case COLOR_LIGHT_PER_VERTEX: {
                shaderProgram = this.shaderProgramColorLight;
                break;
            }
            case COLOR_TEXTURE2: {
                shaderProgram = this.shaderProgramColorTexture2;
                break;
            }
            case COLOR_TEXTURE4: {
                shaderProgram = this.shaderProgramColorTexture4;
                break;
            }
            case COLOR_TEXTURE8: {
                shaderProgram = this.shaderProgramColorTexture8;
                break;
            }
            case COLOR_TEXTURE8_LIGHT_PER_VERTEX: {
                shaderProgram = this.shaderProgramColorTexture8Light;
                break;
            }
            default: {
                shaderProgram = this.shaderProgramColor;
                break;
            }
        }
        this.currentShaderSelectionMode = color;
        return shaderProgram;
    }
    
    private void init(final GL2ES2 gl2ES2, final ShaderSelectionMode requestedShaderSelectionMode, final PMVMatrix pmvMatrix) {
        if (null == pmvMatrix) {
            throw new GLException("PMVMatrix is null");
        }
        this.pmvMatrix = pmvMatrix;
        this.requestedShaderSelectionMode = requestedShaderSelectionMode;
        (this.shaderState = new ShaderState()).setVerbose(this.verbose);
        this.shaderState.attachShaderProgram(gl2ES2, this.selectShaderProgram(gl2ES2, this.requestedShaderSelectionMode), true);
        if (!this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_PMVMatrix", 4, 4, pmvMatrix.glGetPMvMvitMatrixf()))) {
            throw new GLException("Error setting PMVMatrix in shader: " + this);
        }
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_ColorEnabled", 0));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_ColorStatic", 4, this.colorStatic));
        this.texID2Format.setKeyNotFoundValue(0);
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_TexCoordEnabled", 1, this.textureCoordEnabled));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_TexEnvMode", 1, this.textureEnvMode));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_TexFormat", 1, this.textureFormat));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_TextureEnabled", 1, this.textureEnabled));
        for (int i = 0; i < 8; ++i) {
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_Texture" + i, i));
        }
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_ShadeModel", 0));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_AlphaTestFunc", this.alphaTestFunc));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_AlphaTestRef", this.alphaTestRef));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_PointParams", 4, this.pointParams));
        for (int j = 0; j < 8; ++j) {
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].ambient", 4, FixedFuncPipeline.defAmbient));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].diffuse", 4, (j == 0) ? FixedFuncPipeline.one4f : FixedFuncPipeline.defDiffuseN));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].specular", 4, (j == 0) ? FixedFuncPipeline.one4f : FixedFuncPipeline.defSpecularN));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].position", 4, FixedFuncPipeline.defPosition));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].spotDirection", 3, FixedFuncPipeline.defSpotDir));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].spotExponent", 0.0f));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].spotCutoff", 180.0f));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].constantAttenuation", 1.0f));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].linearAttenuation", 0.0f));
            this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightSource[" + j + "].quadraticAttenuation", 0.0f));
        }
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightModel.ambient", 4, FixedFuncPipeline.defLightModelAmbient));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_LightsEnabled", 1, this.lightsEnabled));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_FrontMaterial.ambient", 4, FixedFuncPipeline.defMatAmbient));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_FrontMaterial.diffuse", 4, FixedFuncPipeline.defMatDiffuse));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_FrontMaterial.specular", 4, FixedFuncPipeline.defMatSpecular));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_FrontMaterial.emission", 4, FixedFuncPipeline.defMatEmission));
        this.shaderState.uniform(gl2ES2, new GLUniformData("mgl_FrontMaterial.shininess", 0.0f));
        this.shaderState.useProgram(gl2ES2, false);
        if (this.verbose) {
            System.err.println("init: " + this.toString(null, FixedFuncPipeline.DEBUG).toString());
        }
    }
    
    private String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    static {
        Debug.initSingleton();
        DEBUG = PropertyAccess.isPropertyDefined("jogl.debug.FixedFuncPipeline", true);
        glDrawArraysAction = new GLRunnable2<Object, Object>() {
            @Override
            public Object run(final GL gl, final Object o) {
                final int[] array = (int[])o;
                gl.glDrawArrays(0, array[0], array[1]);
                return null;
            }
        };
        neut4f = Buffers.newDirectFloatBuffer(new float[] { 0.0f, 0.0f, 0.0f, 1.0f });
        one4f = Buffers.newDirectFloatBuffer(new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
        defAmbient = FixedFuncPipeline.neut4f;
        defDiffuseN = FixedFuncPipeline.neut4f;
        defSpecularN = FixedFuncPipeline.neut4f;
        defPosition = Buffers.newDirectFloatBuffer(new float[] { 0.0f, 0.0f, 1.0f, 0.0f });
        defSpotDir = Buffers.newDirectFloatBuffer(new float[] { 0.0f, 0.0f, -1.0f });
        defLightModelAmbient = Buffers.newDirectFloatBuffer(new float[] { 0.2f, 0.2f, 0.2f, 1.0f });
        defMatAmbient = Buffers.newDirectFloatBuffer(new float[] { 0.2f, 0.2f, 0.2f, 1.0f });
        defMatDiffuse = Buffers.newDirectFloatBuffer(new float[] { 0.8f, 0.8f, 0.8f, 1.0f });
        defMatSpecular = FixedFuncPipeline.neut4f;
        defMatEmission = FixedFuncPipeline.neut4f;
    }
}
