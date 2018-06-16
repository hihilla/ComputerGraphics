// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.curve.opengl;

import java.io.IOException;
import com.jogamp.common.os.Platform;
import jogamp.graph.curve.opengl.shader.AttributeNames;
import com.jogamp.graph.curve.Region;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.opengl.util.glsl.ShaderCode;
import java.util.Iterator;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.common.util.IntObjectHashMap;

public class RegionRenderer
{
    protected static final boolean DEBUG;
    protected static final boolean DEBUG_INSTANCE;
    public static final GLCallback defaultBlendEnable;
    public static final GLCallback defaultBlendDisable;
    private final RenderState rs;
    private final GLCallback enableCallback;
    private final GLCallback disableCallback;
    private int vp_width;
    private int vp_height;
    private boolean initialized;
    private boolean vboSupported;
    private static final String SHADER_SRC_SUB = "";
    private static final String SHADER_BIN_SUB = "bin";
    private static String GLSL_USE_COLOR_CHANNEL;
    private static String GLSL_USE_COLOR_TEXTURE;
    private static String GLSL_DEF_SAMPLE_COUNT;
    private static String GLSL_CONST_SAMPLE_COUNT;
    private static String GLSL_MAIN_BEGIN;
    private static final String gcuTexture2D = "gcuTexture2D";
    private static final String es2_precision_fp = "\nprecision mediump float;\nprecision mediump int;\nprecision mediump sampler2D;\n";
    private final IntObjectHashMap shaderPrograms;
    private static final int HIGH_MASK = 1536;
    private static final int TWO_PASS_BIT = Integer.MIN_VALUE;
    
    public static RegionRenderer create(final RenderState renderState, final GLCallback glCallback, final GLCallback glCallback2) {
        return new RegionRenderer(renderState, glCallback, glCallback2);
    }
    
    public final boolean isInitialized() {
        return this.initialized;
    }
    
    public final int getWidth() {
        return this.vp_width;
    }
    
    public final int getHeight() {
        return this.vp_height;
    }
    
    public final PMVMatrix getMatrix() {
        return this.rs.getMatrix();
    }
    
    protected RegionRenderer(final RenderState rs, final GLCallback enableCallback, final GLCallback disableCallback) {
        this.vboSupported = false;
        this.shaderPrograms = new IntObjectHashMap();
        this.rs = rs;
        this.enableCallback = enableCallback;
        this.disableCallback = disableCallback;
    }
    
    public final boolean isVBOSupported() {
        return this.vboSupported;
    }
    
    public final void init(final GL2ES2 gl2ES2, final int n) throws GLException {
        if (this.initialized) {
            return;
        }
        this.vboSupported = (gl2ES2.isFunctionAvailable("glGenBuffers") && gl2ES2.isFunctionAvailable("glBindBuffer") && gl2ES2.isFunctionAvailable("glBufferData") && gl2ES2.isFunctionAvailable("glDrawElements") && gl2ES2.isFunctionAvailable("glVertexAttribPointer") && gl2ES2.isFunctionAvailable("glDeleteBuffers"));
        if (RegionRenderer.DEBUG) {
            System.err.println("TextRendererImpl01: VBO Supported = " + this.isVBOSupported());
        }
        if (!this.vboSupported) {
            throw new GLException("VBO not supported");
        }
        this.rs.attachTo(gl2ES2);
        if (null != this.enableCallback) {
            this.enableCallback.run(gl2ES2, this);
        }
        this.initialized = true;
    }
    
    public final void destroy(final GL2ES2 gl2ES2) {
        if (!this.initialized) {
            if (RegionRenderer.DEBUG_INSTANCE) {
                System.err.println("TextRenderer: Not initialized!");
            }
            return;
        }
        final Iterator<IntObjectHashMap.Entry> iterator = this.shaderPrograms.iterator();
        while (iterator.hasNext()) {
            ((ShaderProgram)iterator.next().getValue()).destroy(gl2ES2);
        }
        this.shaderPrograms.clear();
        this.rs.destroy(gl2ES2);
        this.initialized = false;
    }
    
    public final RenderState getRenderState() {
        return this.rs;
    }
    
    public final void enable(final GL2ES2 gl2ES2, final boolean b) {
        if (b) {
            if (null != this.enableCallback) {
                this.enableCallback.run(gl2ES2, this);
            }
        }
        else if (null != this.disableCallback) {
            this.disableCallback.run(gl2ES2, this);
        }
        if (!b) {
            final ShaderProgram shaderProgram = this.rs.getShaderProgram();
            if (null != shaderProgram) {
                shaderProgram.useProgram(gl2ES2, false);
            }
        }
    }
    
    public final void reshapeNotify(final int vp_width, final int vp_height) {
        this.vp_width = vp_width;
        this.vp_height = vp_height;
    }
    
    public final void reshapePerspective(final float n, final int vp_width, final int vp_height, final float n2, final float n3) {
        this.vp_width = vp_width;
        this.vp_height = vp_height;
        final float n4 = vp_width / vp_height;
        final PMVMatrix matrix = this.rs.getMatrix();
        matrix.glMatrixMode(5889);
        matrix.glLoadIdentity();
        matrix.gluPerspective(n, n4, n2, n3);
    }
    
    public final void reshapeOrtho(final int vp_width, final int vp_height, final float n, final float n2) {
        this.vp_width = vp_width;
        this.vp_height = vp_height;
        final PMVMatrix matrix = this.rs.getMatrix();
        matrix.glMatrixMode(5889);
        matrix.glLoadIdentity();
        matrix.glOrthof(0.0f, vp_width, 0.0f, vp_height, n, n2);
    }
    
    private String getVersionedShaderName() {
        return "curverenderer01";
    }
    
    private final String getFragmentShaderPrecision(final GL2ES2 gl2ES2) {
        if (gl2ES2.isGLES()) {
            return "\nprecision mediump float;\nprecision mediump int;\nprecision mediump sampler2D;\n";
        }
        if (ShaderCode.requiresGL3DefaultPrecision(gl2ES2)) {
            return "\nprecision highp float;\nprecision mediump int;\n/*precision mediump sampler2D;*/\n";
        }
        return null;
    }
    
    public final boolean useShaderProgram(final GL2ES2 gl2ES2, final int n, final boolean b, final int n2, final int n3, final TextureSequence textureSequence) {
        int textureFragmentShaderHashCode;
        if (null != textureSequence) {
            textureFragmentShaderHashCode = textureSequence.getTextureFragmentShaderHashCode();
        }
        else {
            textureFragmentShaderHashCode = 0;
        }
        final ShaderModeSelector1 shaderModeSelector1 = b ? ShaderModeSelector1.selectPass1(n) : ShaderModeSelector1.selectPass2(n, n2, n3);
        final boolean twoPass = Region.isTwoPass(n);
        final boolean b2 = b && null != textureSequence;
        final int n4 = (textureFragmentShaderHashCode << 5) - textureFragmentShaderHashCode + (shaderModeSelector1.ordinal() | (0x600 & n) | (twoPass ? Integer.MIN_VALUE : 0));
        final ShaderProgram shaderProgram = (ShaderProgram)this.shaderPrograms.get(n4);
        if (null != shaderProgram) {
            final boolean setShaderProgram = this.getRenderState().setShaderProgram(gl2ES2, shaderProgram);
            if (RegionRenderer.DEBUG) {
                if (setShaderProgram) {
                    System.err.printf("RegionRendererImpl01.useShaderProgram.X1: GOT renderModes %s, sel1 %s, key 0x%X -> sp %d / %d (changed)%n", Region.getRenderModeString(n), shaderModeSelector1, n4, shaderProgram.program(), shaderProgram.id());
                }
                else {
                    System.err.printf("RegionRendererImpl01.useShaderProgram.X1: GOT renderModes %s, sel1 %s, key 0x%X -> sp %d / %d (keep)%n", Region.getRenderModeString(n), shaderModeSelector1, n4, shaderProgram.program(), shaderProgram.id());
                }
            }
            return setShaderProgram;
        }
        final String versionedShaderName = this.getVersionedShaderName();
        String s;
        if (twoPass) {
            s = versionedShaderName + "-pass" + (b ? 1 : 2);
        }
        else {
            s = versionedShaderName + "-single";
        }
        final ShaderCode create = ShaderCode.create(gl2ES2, 35633, AttributeNames.class, "", "bin", s, true);
        final ShaderCode create2 = ShaderCode.create(gl2ES2, 35632, AttributeNames.class, "", "bin", versionedShaderName + "-segment-head", true);
        if (b2 && 36197 == textureSequence.getTextureTarget() && !gl2ES2.isExtensionAvailable("GL_OES_EGL_image_external")) {
            throw new GLException("GL_OES_EGL_image_external requested but not available");
        }
        boolean b3 = false;
        if (b2 && 36197 == textureSequence.getTextureTarget() && Platform.OSType.ANDROID == Platform.getOSType() && gl2ES2.isGLES3()) {
            b3 = true;
        }
        int n5 = create.defaultShaderCustomization(gl2ES2, !b3, true);
        int n6 = b3 ? 0 : create2.addGLSLVersion(gl2ES2);
        if (b2) {
            n6 = create2.insertShaderSource(0, n6, textureSequence.getRequiredExtensionsShaderStub());
        }
        if ((b && b3) || (gl2ES2.isGLES2() && !gl2ES2.isGLES3())) {
            n6 = create2.insertShaderSource(0, n6, ShaderCode.createExtensionDirective("GL_OES_standard_derivatives", "enable"));
        }
        create2.addDefaultShaderPrecision(gl2ES2, n6);
        int n7 = -1;
        if (Region.hasColorChannel(n)) {
            n5 = create.insertShaderSource(0, n5, RegionRenderer.GLSL_USE_COLOR_CHANNEL);
            n7 = create2.insertShaderSource(0, n7, RegionRenderer.GLSL_USE_COLOR_CHANNEL);
        }
        if (Region.hasColorTexture(n)) {
            create.insertShaderSource(0, n5, RegionRenderer.GLSL_USE_COLOR_TEXTURE);
            n7 = create2.insertShaderSource(0, n7, RegionRenderer.GLSL_USE_COLOR_TEXTURE);
        }
        if (!b) {
            n7 = create2.insertShaderSource(0, create2.insertShaderSource(0, n7, RegionRenderer.GLSL_DEF_SAMPLE_COUNT + shaderModeSelector1.sampleCount + "\n"), RegionRenderer.GLSL_CONST_SAMPLE_COUNT + shaderModeSelector1.sampleCount + ".0;\n");
        }
        int n8;
        try {
            n8 = create2.insertShaderSource(0, create2.insertShaderSource(0, n7, AttributeNames.class, "uniforms.glsl"), AttributeNames.class, "varyings.glsl");
        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to read: includes", ex);
        }
        if (0 > n8) {
            throw new RuntimeException("Failed to read: includes");
        }
        String textureLookupFunctionName;
        if (b2) {
            final int insertShaderSource = create2.insertShaderSource(0, n8, "uniform " + textureSequence.getTextureSampler2DType() + " " + "gcu_ColorTexUnit" + ";\n");
            textureLookupFunctionName = textureSequence.getTextureLookupFunctionName("gcuTexture2D");
            n8 = create2.insertShaderSource(0, insertShaderSource, textureSequence.getTextureLookupFragmentShaderImpl());
        }
        else {
            textureLookupFunctionName = null;
        }
        final int insertShaderSource2 = create2.insertShaderSource(0, n8, RegionRenderer.GLSL_MAIN_BEGIN);
        final String string = versionedShaderName + (b ? "-pass1-" : "-pass2-") + shaderModeSelector1.tech + shaderModeSelector1.sub + ".glsl";
        if (RegionRenderer.DEBUG) {
            System.err.printf("RegionRendererImpl01.useShaderProgram.1: segment %s%n", string);
        }
        int insertShaderSource3;
        try {
            insertShaderSource3 = create2.insertShaderSource(0, insertShaderSource2, AttributeNames.class, string);
        }
        catch (IOException ex2) {
            throw new RuntimeException("Failed to read: " + string, ex2);
        }
        if (0 > insertShaderSource3) {
            throw new RuntimeException("Failed to read: " + string);
        }
        create2.insertShaderSource(0, insertShaderSource3, "}\n");
        if (b2) {
            create2.replaceInShaderSource("gcuTexture2D", textureLookupFunctionName);
        }
        final ShaderProgram shaderProgram2 = new ShaderProgram();
        shaderProgram2.add(create);
        shaderProgram2.add(create2);
        if (!shaderProgram2.init(gl2ES2)) {
            throw new GLException("RegionRenderer: Couldn't init program: " + shaderProgram2);
        }
        if (!shaderProgram2.link(gl2ES2, System.err)) {
            throw new GLException("could not link program: " + shaderProgram2);
        }
        this.getRenderState().setShaderProgram(gl2ES2, shaderProgram2);
        this.shaderPrograms.put(n4, shaderProgram2);
        if (RegionRenderer.DEBUG) {
            System.err.printf("RegionRendererImpl01.useShaderProgram.X1: PUT renderModes %s, sel1 %s, key 0x%X -> sp %d / %d (changed)%n", Region.getRenderModeString(n), shaderModeSelector1, n4, shaderProgram2.program(), shaderProgram2.id());
        }
        return true;
    }
    
    static {
        DEBUG = Region.DEBUG;
        DEBUG_INSTANCE = Region.DEBUG_INSTANCE;
        defaultBlendEnable = new GLCallback() {
            @Override
            public void run(final GL gl, final RegionRenderer regionRenderer) {
                if (regionRenderer.rs.isHintMaskSet(2)) {
                    gl.glDepthMask(false);
                }
                gl.glEnable(3042);
                gl.glBlendEquation(32774);
                regionRenderer.rs.setHintMask(1);
            }
        };
        defaultBlendDisable = new GLCallback() {
            @Override
            public void run(final GL gl, final RegionRenderer regionRenderer) {
                regionRenderer.rs.clearHintMask(1);
                gl.glDisable(3042);
                if (regionRenderer.rs.isHintMaskSet(2)) {
                    gl.glDepthMask(true);
                }
            }
        };
        RegionRenderer.GLSL_USE_COLOR_CHANNEL = "#define USE_COLOR_CHANNEL 1\n";
        RegionRenderer.GLSL_USE_COLOR_TEXTURE = "#define USE_COLOR_TEXTURE 1\n";
        RegionRenderer.GLSL_DEF_SAMPLE_COUNT = "#define SAMPLE_COUNT ";
        RegionRenderer.GLSL_CONST_SAMPLE_COUNT = "const float sample_count = ";
        RegionRenderer.GLSL_MAIN_BEGIN = "void main (void)\n{\n";
    }
    
    private enum ShaderModeSelector1
    {
        PASS1_SIMPLE("curve", "_simple", 0), 
        PASS1_WEIGHT("curve", "_weight", 0), 
        PASS2_MSAA("msaa", "", 0), 
        PASS2_VBAA_QUAL0_SAMPLES1("vbaa", "_flipquad3", 1), 
        PASS2_VBAA_QUAL0_SAMPLES2("vbaa", "_flipquad3", 2), 
        PASS2_VBAA_QUAL0_SAMPLES4("vbaa", "_flipquad3", 4), 
        PASS2_VBAA_QUAL0_SAMPLES8("vbaa", "_flipquad3", 8), 
        PASS2_VBAA_QUAL1_SAMPLES1("vbaa", "_bforce_odd", 1), 
        PASS2_VBAA_QUAL1_SAMPLES2("vbaa", "_bforce_even", 2), 
        PASS2_VBAA_QUAL1_SAMPLES3("vbaa", "_bforce_odd", 3), 
        PASS2_VBAA_QUAL1_SAMPLES4("vbaa", "_bforce_even", 4), 
        PASS2_VBAA_QUAL1_SAMPLES5("vbaa", "_bforce_odd", 5), 
        PASS2_VBAA_QUAL1_SAMPLES6("vbaa", "_bforce_even", 6), 
        PASS2_VBAA_QUAL1_SAMPLES7("vbaa", "_bforce_odd", 7), 
        PASS2_VBAA_QUAL1_SAMPLES8("vbaa", "_bforce_even", 8);
        
        public final String tech;
        public final String sub;
        public final int sampleCount;
        
        private ShaderModeSelector1(final String tech, final String sub, final int sampleCount) {
            this.tech = tech;
            this.sub = sub;
            this.sampleCount = sampleCount;
        }
        
        public static ShaderModeSelector1 selectPass1(final int n) {
            return Region.hasVariableWeight(n) ? ShaderModeSelector1.PASS1_WEIGHT : ShaderModeSelector1.PASS1_SIMPLE;
        }
        
        public static ShaderModeSelector1 selectPass2(final int n, final int n2, final int n3) {
            if (Region.isMSAA(n)) {
                return ShaderModeSelector1.PASS2_MSAA;
            }
            if (!Region.isVBAA(n)) {
                return null;
            }
            if (0 == n2) {
                if (n3 < 2) {
                    return ShaderModeSelector1.PASS2_VBAA_QUAL0_SAMPLES1;
                }
                if (n3 < 4) {
                    return ShaderModeSelector1.PASS2_VBAA_QUAL0_SAMPLES2;
                }
                if (n3 < 8) {
                    return ShaderModeSelector1.PASS2_VBAA_QUAL0_SAMPLES4;
                }
                return ShaderModeSelector1.PASS2_VBAA_QUAL0_SAMPLES8;
            }
            else {
                switch (n3) {
                    case 0:
                    case 1: {
                        return ShaderModeSelector1.PASS2_VBAA_QUAL1_SAMPLES1;
                    }
                    case 2: {
                        return ShaderModeSelector1.PASS2_VBAA_QUAL1_SAMPLES2;
                    }
                    case 3: {
                        return ShaderModeSelector1.PASS2_VBAA_QUAL1_SAMPLES3;
                    }
                    case 4: {
                        return ShaderModeSelector1.PASS2_VBAA_QUAL1_SAMPLES4;
                    }
                    case 5: {
                        return ShaderModeSelector1.PASS2_VBAA_QUAL1_SAMPLES5;
                    }
                    case 6: {
                        return ShaderModeSelector1.PASS2_VBAA_QUAL1_SAMPLES6;
                    }
                    case 7: {
                        return ShaderModeSelector1.PASS2_VBAA_QUAL1_SAMPLES7;
                    }
                    default: {
                        return ShaderModeSelector1.PASS2_VBAA_QUAL1_SAMPLES8;
                    }
                }
            }
        }
    }
    
    public interface GLCallback
    {
        void run(final GL p0, final RegionRenderer p1);
    }
}
