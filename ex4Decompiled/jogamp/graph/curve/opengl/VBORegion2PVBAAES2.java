// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.curve.opengl;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.math.FloatUtil;
import java.nio.FloatBuffer;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.graph.curve.opengl.RegionRenderer;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.math.geom.AABBox;
import com.jogamp.opengl.FBObject;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.opengl.util.GLArrayDataServer;
import com.jogamp.graph.curve.opengl.RenderState;
import com.jogamp.graph.curve.opengl.GLRegion;

public class VBORegion2PVBAAES2 extends GLRegion
{
    private static final boolean DEBUG_FBO_1 = false;
    private static final boolean DEBUG_FBO_2 = false;
    private static final int RESIZE_BOUNDARY;
    private final RenderState.ProgramLocal rsLocal;
    private GLArrayDataServer gca_VerticesAttr;
    private GLArrayDataServer gca_CurveParamsAttr;
    private GLArrayDataServer gca_ColorsAttr;
    private GLArrayDataServer indicesBuffer;
    private final GLUniformData gcu_ColorTexUnit;
    private final float[] colorTexBBox;
    private final GLUniformData gcu_ColorTexBBox;
    private ShaderProgram spPass1;
    private GLArrayDataServer gca_FboVerticesAttr;
    private GLArrayDataServer gca_FboTexCoordsAttr;
    private GLArrayDataServer indicesFbo;
    private final GLUniformData gcu_FboTexUnit;
    private final GLUniformData gcu_FboTexSize;
    private final float[] pmvMatrix02;
    private final GLUniformData gcu_PMVMatrix02;
    private ShaderProgram spPass2;
    private FBObject fbo;
    private FBObject.TextureAttachment texA;
    private int fboWidth;
    private int fboHeight;
    private boolean fboDirty;
    final int[] maxTexSize;
    private final AABBox drawWinBox;
    private final int[] drawView;
    private final float[] drawVec4Tmp0;
    private final float[] drawVec4Tmp1;
    private final float[] drawVec4Tmp2;
    private final float[] drawMat4PMv;
    private static final int border = 2;
    
    public void useShaderProgram(final GL2ES2 gl2ES2, final RegionRenderer regionRenderer, final int n, final boolean b, final int n2, final int n3) {
        final RenderState renderState = regionRenderer.getRenderState();
        final boolean useShaderProgram = regionRenderer.useShaderProgram(gl2ES2, n, b, n2, n3, this.colorTexSeq);
        final ShaderProgram shaderProgram = regionRenderer.getRenderState().getShaderProgram();
        if (b) {
            final boolean b2 = !shaderProgram.equals(this.spPass1);
            this.spPass1 = shaderProgram;
            if (VBORegion2PVBAAES2.DEBUG) {
                System.err.println("XXX changedSP.p1 updateLocation loc " + b2 + " / glob " + useShaderProgram);
            }
            if (b2) {
                renderState.updateAttributeLoc(gl2ES2, true, this.gca_VerticesAttr, true);
                renderState.updateAttributeLoc(gl2ES2, true, this.gca_CurveParamsAttr, true);
                if (null != this.gca_ColorsAttr) {
                    renderState.updateAttributeLoc(gl2ES2, true, this.gca_ColorsAttr, true);
                }
            }
            this.rsLocal.update(gl2ES2, renderState, b2, n, true, true);
            renderState.updateUniformLoc(gl2ES2, b2, this.gcu_PMVMatrix02, true);
            if (null != this.gcu_ColorTexUnit) {
                renderState.updateUniformLoc(gl2ES2, b2, this.gcu_ColorTexUnit, true);
                renderState.updateUniformLoc(gl2ES2, b2, this.gcu_ColorTexBBox, true);
            }
        }
        else {
            final boolean b3 = !shaderProgram.equals(this.spPass2);
            this.spPass2 = shaderProgram;
            if (VBORegion2PVBAAES2.DEBUG) {
                System.err.println("XXX changedSP.p2 updateLocation loc " + b3 + " / glob " + useShaderProgram);
            }
            if (b3) {
                renderState.updateAttributeLoc(gl2ES2, true, this.gca_FboVerticesAttr, true);
                renderState.updateAttributeLoc(gl2ES2, true, this.gca_FboTexCoordsAttr, true);
            }
            this.rsLocal.update(gl2ES2, renderState, b3, n, false, true);
            renderState.updateUniformDataLoc(gl2ES2, b3, false, this.gcu_FboTexUnit, true);
            renderState.updateUniformLoc(gl2ES2, b3, this.gcu_FboTexSize, n3 > 1);
        }
    }
    
    public VBORegion2PVBAAES2(final int n, final TextureSequence textureSequence, final int n2) {
        super(n, textureSequence);
        this.spPass1 = null;
        this.pmvMatrix02 = new float[32];
        this.spPass2 = null;
        this.fboWidth = 0;
        this.fboHeight = 0;
        this.fboDirty = true;
        this.maxTexSize = new int[] { -1 };
        this.drawWinBox = new AABBox();
        this.drawView = new int[] { 0, 0, 0, 0 };
        this.drawVec4Tmp0 = new float[4];
        this.drawVec4Tmp1 = new float[4];
        this.drawVec4Tmp2 = new float[4];
        this.drawMat4PMv = new float[16];
        this.rsLocal = new RenderState.ProgramLocal();
        this.indicesBuffer = GLArrayDataServer.createData(3, 5122, 256, 35044, 34963);
        this.gca_VerticesAttr = GLArrayDataServer.createGLSL("gca_Vertices", 3, 5126, false, 256, 35044);
        this.gca_CurveParamsAttr = GLArrayDataServer.createGLSL("gca_CurveParams", 3, 5126, false, 256, 35044);
        if (this.hasColorChannel()) {
            this.gca_ColorsAttr = GLArrayDataServer.createGLSL("gca_Colors", 4, 5126, false, 256, 35044);
        }
        else {
            this.gca_ColorsAttr = null;
        }
        if (this.hasColorTexture()) {
            this.gcu_ColorTexUnit = new GLUniformData("gcu_ColorTexUnit", textureSequence.getTextureUnit());
            this.colorTexBBox = new float[4];
            this.gcu_ColorTexBBox = new GLUniformData("gcu_ColorTexBBox", 4, FloatBuffer.wrap(this.colorTexBBox));
        }
        else {
            this.gcu_ColorTexUnit = null;
            this.colorTexBBox = null;
            this.gcu_ColorTexBBox = null;
        }
        FloatUtil.makeIdentity(this.pmvMatrix02, 0);
        FloatUtil.makeIdentity(this.pmvMatrix02, 16);
        this.gcu_PMVMatrix02 = new GLUniformData("gcu_PMVMatrix02", 4, 4, FloatBuffer.wrap(this.pmvMatrix02));
        this.gcu_FboTexUnit = new GLUniformData("gcu_FboTexUnit", n2);
        this.gcu_FboTexSize = new GLUniformData("gcu_FboTexSize", 2, FloatBuffer.wrap(new float[2]));
        (this.indicesFbo = GLArrayDataServer.createData(3, 5122, 2, 35044, 34963)).puts((short)0);
        this.indicesFbo.puts((short)1);
        this.indicesFbo.puts((short)3);
        this.indicesFbo.puts((short)1);
        this.indicesFbo.puts((short)2);
        this.indicesFbo.puts((short)3);
        this.indicesFbo.seal(true);
        (this.gca_FboTexCoordsAttr = GLArrayDataServer.createGLSL("gca_FboTexCoords", 2, 5126, false, 4, 35044)).putf(0.0f);
        this.gca_FboTexCoordsAttr.putf(0.0f);
        this.gca_FboTexCoordsAttr.putf(0.0f);
        this.gca_FboTexCoordsAttr.putf(1.0f);
        this.gca_FboTexCoordsAttr.putf(1.0f);
        this.gca_FboTexCoordsAttr.putf(1.0f);
        this.gca_FboTexCoordsAttr.putf(1.0f);
        this.gca_FboTexCoordsAttr.putf(0.0f);
        this.gca_FboTexCoordsAttr.seal(true);
        this.gca_FboVerticesAttr = GLArrayDataServer.createGLSL("gca_FboVertices", 3, 5126, false, 4, 35044);
    }
    
    @Override
    protected final void clearImpl(final GL2ES2 gl2ES2) {
        if (VBORegion2PVBAAES2.DEBUG_INSTANCE) {
            System.err.println("VBORegion2PES2 Clear: " + this);
        }
        if (null != this.indicesBuffer) {
            this.indicesBuffer.seal(gl2ES2, false);
            this.indicesBuffer.rewind();
        }
        if (null != this.gca_VerticesAttr) {
            this.gca_VerticesAttr.seal(gl2ES2, false);
            this.gca_VerticesAttr.rewind();
        }
        if (null != this.gca_CurveParamsAttr) {
            this.gca_CurveParamsAttr.seal(gl2ES2, false);
            this.gca_CurveParamsAttr.rewind();
        }
        if (null != this.gca_ColorsAttr) {
            this.gca_ColorsAttr.seal(gl2ES2, false);
            this.gca_ColorsAttr.rewind();
        }
        this.fboDirty = true;
    }
    
    @Override
    protected final void pushVertex(final float[] array, final float[] array2, final float[] array3) {
        this.gca_VerticesAttr.putf(array[0]);
        this.gca_VerticesAttr.putf(array[1]);
        this.gca_VerticesAttr.putf(array[2]);
        this.gca_CurveParamsAttr.putf(array2[0]);
        this.gca_CurveParamsAttr.putf(array2[1]);
        this.gca_CurveParamsAttr.putf(array2[2]);
        if (null != this.gca_ColorsAttr) {
            if (null == array3) {
                throw new IllegalArgumentException("Null color given for COLOR_CHANNEL rendering mode");
            }
            this.gca_ColorsAttr.putf(array3[0]);
            this.gca_ColorsAttr.putf(array3[1]);
            this.gca_ColorsAttr.putf(array3[2]);
            this.gca_ColorsAttr.putf(array3[3]);
        }
    }
    
    @Override
    protected final void pushIndex(final int n) {
        this.indicesBuffer.puts((short)n);
    }
    
    @Override
    protected void updateImpl(final GL2ES2 gl2ES2) {
        this.indicesBuffer.seal(gl2ES2, true);
        this.indicesBuffer.enableBuffer(gl2ES2, false);
        this.gca_CurveParamsAttr.seal(gl2ES2, true);
        this.gca_CurveParamsAttr.enableBuffer(gl2ES2, false);
        this.gca_VerticesAttr.seal(gl2ES2, true);
        this.gca_VerticesAttr.enableBuffer(gl2ES2, false);
        if (null != this.gca_ColorsAttr) {
            this.gca_ColorsAttr.seal(gl2ES2, true);
            this.gca_ColorsAttr.enableBuffer(gl2ES2, false);
        }
        if (null != this.gcu_ColorTexUnit && this.colorTexSeq.isTextureAvailable()) {
            final Texture texture = this.colorTexSeq.getLastTexture().getTexture();
            final TextureCoords imageTexCoords = texture.getImageTexCoords();
            final float n = 1.0f / (imageTexCoords.right() - imageTexCoords.left());
            this.colorTexBBox[0] = this.box.getMinX() * n;
            this.colorTexBBox[2] = this.box.getMaxX() * n;
            if (texture.getMustFlipVertically()) {
                final float n2 = 1.0f / (imageTexCoords.bottom() - imageTexCoords.top());
                this.colorTexBBox[1] = this.box.getMaxY() * n2;
                this.colorTexBBox[3] = this.box.getMinY() * n2;
            }
            else {
                final float n3 = 1.0f / (imageTexCoords.top() - imageTexCoords.bottom());
                this.colorTexBBox[1] = this.box.getMinY() * n3;
                this.colorTexBBox[3] = this.box.getMaxY() * n3;
            }
        }
        this.gca_FboVerticesAttr.seal(gl2ES2, false);
        final FloatBuffer floatBuffer = (FloatBuffer)this.gca_FboVerticesAttr.getBuffer();
        floatBuffer.put(2, this.box.getMinZ());
        floatBuffer.put(5, this.box.getMinZ());
        floatBuffer.put(8, this.box.getMinZ());
        floatBuffer.put(11, this.box.getMinZ());
        this.indicesFbo.seal(gl2ES2, true);
        this.indicesFbo.enableBuffer(gl2ES2, false);
        this.fboDirty = true;
    }
    
    @Override
    protected void drawImpl(final GL2ES2 gl2ES2, final RegionRenderer regionRenderer, final int[] array) {
        if (0 >= this.indicesBuffer.getElementCount()) {
            if (VBORegion2PVBAAES2.DEBUG_INSTANCE) {
                System.err.printf("VBORegion2PVBAAES2.drawImpl: Empty%n", new Object[0]);
            }
            return;
        }
        if (Float.isInfinite(this.box.getWidth()) || Float.isInfinite(this.box.getHeight())) {
            if (VBORegion2PVBAAES2.DEBUG_INSTANCE) {
                System.err.printf("VBORegion2PVBAAES2.drawImpl: Inf %s%n", this.box);
            }
            return;
        }
        final int width = regionRenderer.getWidth();
        final int height = regionRenderer.getHeight();
        if (width <= 0 || height <= 0 || null == array || array[0] <= 0) {
            this.renderRegion(gl2ES2);
        }
        else {
            if (0 > this.maxTexSize[0]) {
                gl2ES2.glGetIntegerv(3379, this.maxTexSize, 0);
            }
            final RenderState renderState = regionRenderer.getRenderState();
            this.drawView[2] = width;
            this.drawView[3] = height;
            regionRenderer.getMatrix().multPMvMatrixf(this.drawMat4PMv, 0);
            this.box.mapToWindow(this.drawWinBox, this.drawMat4PMv, this.drawView, true, this.drawVec4Tmp0, this.drawVec4Tmp1, this.drawVec4Tmp2);
            final float width2 = this.drawWinBox.getWidth();
            final float height2 = this.drawWinBox.getHeight();
            final int n = (int)Math.ceil(width2);
            final int n2 = (int)Math.ceil(height2);
            final float n3 = n - width2;
            final float n4 = n2 - height2;
            final float n5 = this.box.getWidth() / width2;
            final float n6 = this.box.getHeight() / height2;
            final float n7 = n3 * n5;
            final float n8 = n4 * n6;
            final float n9 = 2.0f * n5;
            final float n10 = 2.0f * n6;
            int n11 = (n + 4) * array[0];
            int n12 = (n2 + 4) * array[0];
            if (0 >= n11 || 0 >= n12) {
                return;
            }
            final int abs = Math.abs(n11 - this.fboWidth);
            final int abs2 = Math.abs(n12 - this.fboHeight);
            if (abs || abs2 || this.fboDirty || this.isShapeDirty() || null == this.fbo) {
                if (Math.max(n11, n12) > this.maxTexSize[0]) {
                    if (n11 > n12) {
                        array[0] = (int)Math.floor(this.maxTexSize[0] / (width2 + 4.0f));
                    }
                    else {
                        array[0] = (int)Math.floor(this.maxTexSize[0] / (height2 + 4.0f));
                    }
                    final float n13 = (width2 + 4.0f) * array[0];
                    final float n14 = (width2 + 4.0f) * array[0];
                    n11 = (int)Math.ceil(n13);
                    n12 = (int)Math.ceil(n14);
                    if (array[0] <= 0) {
                        this.renderRegion(gl2ES2);
                        return;
                    }
                }
                int fboWidth;
                int fboHeight;
                if (0 >= VBORegion2PVBAAES2.RESIZE_BOUNDARY) {
                    fboWidth = n11;
                    fboHeight = n12;
                }
                else if (0 >= this.fboWidth || 0 >= this.fboHeight || null == this.fbo) {
                    fboWidth = n11;
                    fboHeight = n12;
                }
                else if (n11 > this.fboWidth || n12 > this.fboHeight) {
                    fboWidth = ((n11 + VBORegion2PVBAAES2.RESIZE_BOUNDARY / 2 < this.maxTexSize[0]) ? (n11 + VBORegion2PVBAAES2.RESIZE_BOUNDARY / 2) : n11);
                    fboHeight = ((n12 + VBORegion2PVBAAES2.RESIZE_BOUNDARY / 2 < this.maxTexSize[0]) ? (n12 + VBORegion2PVBAAES2.RESIZE_BOUNDARY / 2) : n12);
                }
                else if (n11 < this.fboWidth && n12 < this.fboHeight && this.fboWidth - n11 < VBORegion2PVBAAES2.RESIZE_BOUNDARY && this.fboHeight - n12 < VBORegion2PVBAAES2.RESIZE_BOUNDARY) {
                    fboWidth = this.fboWidth;
                    fboHeight = this.fboHeight;
                }
                else {
                    fboWidth = n11;
                    fboHeight = n12;
                }
                final int n15 = fboWidth - n11;
                final int n16 = fboHeight - n12;
                final float n17 = n15 * n5;
                final float n18 = n16 * n6;
                final float n19 = this.box.getMinX() - n9;
                final float n20 = this.box.getMinY() - n10;
                final float n21 = this.box.getMaxX() + n9 + n7 + n17;
                final float n22 = this.box.getMaxY() + n10 + n8 + n18;
                this.gca_FboVerticesAttr.seal(false);
                final FloatBuffer floatBuffer = (FloatBuffer)this.gca_FboVerticesAttr.getBuffer();
                floatBuffer.put(0, n19);
                floatBuffer.put(1, n20);
                floatBuffer.put(3, n19);
                floatBuffer.put(4, n22);
                floatBuffer.put(6, n21);
                floatBuffer.put(7, n22);
                floatBuffer.put(9, n21);
                floatBuffer.put(10, n20);
                floatBuffer.position(12);
                this.gca_FboVerticesAttr.seal(true);
                FloatUtil.makeOrtho(this.pmvMatrix02, 0, true, n19, n21, n20, n22, -1.0f, 1.0f);
                this.useShaderProgram(gl2ES2, regionRenderer, this.getRenderModes(), true, this.getQuality(), array[0]);
                this.renderRegion2FBO(gl2ES2, renderState, n11, n12, fboWidth, fboHeight, width, height, array[0]);
            }
            else if (this.isStateDirty()) {
                this.useShaderProgram(gl2ES2, regionRenderer, this.getRenderModes(), true, this.getQuality(), array[0]);
                this.renderRegion2FBO(gl2ES2, renderState, n11, n12, this.fboWidth, this.fboHeight, width, height, array[0]);
            }
            this.useShaderProgram(gl2ES2, regionRenderer, this.getRenderModes(), false, this.getQuality(), array[0]);
            this.renderFBO(gl2ES2, renderState, n11, n12, width, height, array[0]);
        }
    }
    
    private void renderFBO(final GL2ES2 gl2ES2, final RenderState renderState, final int n, final int n2, final int n3, final int n4, final int n5) {
        gl2ES2.glViewport(0, 0, n3, n4);
        if (renderState.isHintMaskSet(1)) {
            gl2ES2.glBlendFunc(1, 771);
        }
        gl2ES2.glUniform(this.gcu_FboTexSize);
        gl2ES2.glActiveTexture(33984 + this.gcu_FboTexUnit.intValue());
        this.fbo.use(gl2ES2, this.texA);
        this.gca_FboVerticesAttr.enableBuffer(gl2ES2, true);
        this.gca_FboTexCoordsAttr.enableBuffer(gl2ES2, true);
        this.indicesFbo.bindBuffer(gl2ES2, true);
        gl2ES2.glDrawElements(4, this.indicesFbo.getElementCount() * this.indicesFbo.getComponentCount(), 5123, 0L);
        this.indicesFbo.bindBuffer(gl2ES2, false);
        this.gca_FboTexCoordsAttr.enableBuffer(gl2ES2, false);
        this.gca_FboVerticesAttr.enableBuffer(gl2ES2, false);
        this.fbo.unuse(gl2ES2);
    }
    
    private void renderRegion2FBO(final GL2ES2 gl2ES2, final RenderState renderState, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        if (0 >= n || 0 >= n2) {
            throw new IllegalArgumentException("fboSize must be greater than 0: " + n + "x" + n2);
        }
        final boolean hintMaskSet = renderState.isHintMaskSet(1);
        if (null == this.fbo) {
            this.fboWidth = n3;
            this.fboHeight = n4;
            final FloatBuffer floatBuffer = (FloatBuffer)this.gcu_FboTexSize.getBuffer();
            floatBuffer.put(0, this.fboWidth);
            floatBuffer.put(1, this.fboHeight);
            (this.fbo = new FBObject()).init(gl2ES2, this.fboWidth, this.fboHeight, 0);
            this.texA = this.fbo.attachTexture2D(gl2ES2, 0, true, 9728, 9728, 33071, 33071);
            if (!hintMaskSet) {
                this.fbo.attachRenderbuffer(gl2ES2, FBObject.Attachment.Type.DEPTH, 0);
            }
        }
        else if (n3 != this.fboWidth || n4 != this.fboHeight) {
            this.fbo.reset(gl2ES2, n3, n4, 0);
            this.fbo.bind(gl2ES2);
            this.fboWidth = n3;
            this.fboHeight = n4;
            final FloatBuffer floatBuffer2 = (FloatBuffer)this.gcu_FboTexSize.getBuffer();
            floatBuffer2.put(0, this.fboWidth);
            floatBuffer2.put(1, this.fboHeight);
        }
        else {
            this.fbo.bind(gl2ES2);
        }
        gl2ES2.glViewport(0, 0, this.fboWidth, this.fboHeight);
        if (hintMaskSet) {
            gl2ES2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            gl2ES2.glClear(16384);
            gl2ES2.glBlendFuncSeparate(770, 771, 1, 771);
        }
        else {
            gl2ES2.glClear(16640);
        }
        this.renderRegion(gl2ES2);
        this.fbo.unbind(gl2ES2);
        this.fboDirty = false;
    }
    
    private void renderRegion(final GL2ES2 gl2ES2) {
        gl2ES2.glUniform(this.gcu_PMVMatrix02);
        this.gca_VerticesAttr.enableBuffer(gl2ES2, true);
        this.gca_CurveParamsAttr.enableBuffer(gl2ES2, true);
        if (null != this.gca_ColorsAttr) {
            this.gca_ColorsAttr.enableBuffer(gl2ES2, true);
        }
        this.indicesBuffer.bindBuffer(gl2ES2, true);
        if (null != this.gcu_ColorTexUnit && this.colorTexSeq.isTextureAvailable()) {
            final TextureSequence.TextureFrame nextTexture = this.colorTexSeq.getNextTexture(gl2ES2);
            gl2ES2.glActiveTexture(33984 + this.colorTexSeq.getTextureUnit());
            final Texture texture = nextTexture.getTexture();
            texture.bind(gl2ES2);
            texture.enable(gl2ES2);
            this.gcu_ColorTexUnit.setData(this.colorTexSeq.getTextureUnit());
            gl2ES2.glUniform(this.gcu_ColorTexUnit);
            gl2ES2.glUniform(this.gcu_ColorTexBBox);
            gl2ES2.glDrawElements(4, this.indicesBuffer.getElementCount() * this.indicesBuffer.getComponentCount(), 5123, 0L);
            texture.disable(gl2ES2);
        }
        else {
            gl2ES2.glDrawElements(4, this.indicesBuffer.getElementCount() * this.indicesBuffer.getComponentCount(), 5123, 0L);
        }
        this.indicesBuffer.bindBuffer(gl2ES2, false);
        if (null != this.gca_ColorsAttr) {
            this.gca_ColorsAttr.enableBuffer(gl2ES2, false);
        }
        this.gca_CurveParamsAttr.enableBuffer(gl2ES2, false);
        this.gca_VerticesAttr.enableBuffer(gl2ES2, false);
    }
    
    @Override
    protected void destroyImpl(final GL2ES2 gl2ES2) {
        if (VBORegion2PVBAAES2.DEBUG_INSTANCE) {
            System.err.println("VBORegion2PES2 Destroy: " + this);
        }
        if (null != this.fbo) {
            this.fbo.destroy(gl2ES2);
            this.fbo = null;
            this.texA = null;
        }
        if (null != this.gca_VerticesAttr) {
            this.gca_VerticesAttr.destroy(gl2ES2);
            this.gca_VerticesAttr = null;
        }
        if (null != this.gca_CurveParamsAttr) {
            this.gca_CurveParamsAttr.destroy(gl2ES2);
            this.gca_CurveParamsAttr = null;
        }
        if (null != this.gca_ColorsAttr) {
            this.gca_ColorsAttr.destroy(gl2ES2);
            this.gca_ColorsAttr = null;
        }
        if (null != this.indicesBuffer) {
            this.indicesBuffer.destroy(gl2ES2);
            this.indicesBuffer = null;
        }
        if (null != this.gca_FboVerticesAttr) {
            this.gca_FboVerticesAttr.destroy(gl2ES2);
            this.gca_FboVerticesAttr = null;
        }
        if (null != this.gca_FboTexCoordsAttr) {
            this.gca_FboTexCoordsAttr.destroy(gl2ES2);
            this.gca_FboTexCoordsAttr = null;
        }
        if (null != this.indicesFbo) {
            this.indicesFbo.destroy(gl2ES2);
            this.indicesFbo = null;
        }
        this.spPass1 = null;
        this.spPass2 = null;
    }
    
    static {
        Debug.initSingleton();
        RESIZE_BOUNDARY = Math.max(0, PropertyAccess.getIntProperty("jogl.debug.graph.curve.vbaa.resizeLowerBoundary", true, 0));
        if (VBORegion2PVBAAES2.RESIZE_BOUNDARY > 0) {
            System.err.println("key: " + VBORegion2PVBAAES2.RESIZE_BOUNDARY);
        }
    }
}
