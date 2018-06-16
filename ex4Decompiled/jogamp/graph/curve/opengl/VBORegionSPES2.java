// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.curve.opengl;

import com.jogamp.graph.curve.opengl.RegionRenderer;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import java.nio.FloatBuffer;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.opengl.util.GLArrayDataServer;
import com.jogamp.graph.curve.opengl.RenderState;
import com.jogamp.graph.curve.opengl.GLRegion;

public class VBORegionSPES2 extends GLRegion
{
    private final RenderState.ProgramLocal rsLocal;
    private GLArrayDataServer gca_VerticesAttr;
    private GLArrayDataServer gca_CurveParamsAttr;
    private GLArrayDataServer gca_ColorsAttr;
    private GLArrayDataServer indicesBuffer;
    private final GLUniformData gcu_ColorTexUnit;
    private final float[] colorTexBBox;
    private final GLUniformData gcu_ColorTexBBox;
    private ShaderProgram spPass1;
    private static final boolean throwOnError = false;
    
    public VBORegionSPES2(final int n, final TextureSequence textureSequence) {
        super(n, textureSequence);
        this.gca_VerticesAttr = null;
        this.gca_CurveParamsAttr = null;
        this.indicesBuffer = null;
        this.spPass1 = null;
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
    }
    
    @Override
    protected final void clearImpl(final GL2ES2 gl2ES2) {
        if (VBORegionSPES2.DEBUG_INSTANCE) {
            System.err.println("VBORegionSPES2 Clear: " + this);
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
        this.gca_VerticesAttr.seal(gl2ES2, true);
        this.gca_VerticesAttr.enableBuffer(gl2ES2, false);
        this.gca_CurveParamsAttr.seal(gl2ES2, true);
        this.gca_CurveParamsAttr.enableBuffer(gl2ES2, false);
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
        this.indicesBuffer.seal(gl2ES2, true);
        this.indicesBuffer.enableBuffer(gl2ES2, false);
        if (VBORegionSPES2.DEBUG_INSTANCE) {
            System.err.println("VBORegionSPES2 idx " + this.indicesBuffer);
            System.err.println("VBORegionSPES2 ver " + this.gca_VerticesAttr);
            System.err.println("VBORegionSPES2 tex " + this.gca_CurveParamsAttr);
        }
    }
    
    public void useShaderProgram(final GL2ES2 gl2ES2, final RegionRenderer regionRenderer, final int n, final int n2) {
        final RenderState renderState = regionRenderer.getRenderState();
        final boolean useShaderProgram = regionRenderer.useShaderProgram(gl2ES2, n, true, n2, 0, this.colorTexSeq);
        final ShaderProgram shaderProgram = regionRenderer.getRenderState().getShaderProgram();
        final boolean b = !shaderProgram.equals(this.spPass1);
        this.spPass1 = shaderProgram;
        if (VBORegionSPES2.DEBUG) {
            System.err.println("XXX changedSP.p1 updateLocation loc " + b + " / glob " + useShaderProgram);
        }
        if (b) {
            renderState.updateAttributeLoc(gl2ES2, true, this.gca_VerticesAttr, false);
            renderState.updateAttributeLoc(gl2ES2, true, this.gca_CurveParamsAttr, false);
            if (null != this.gca_ColorsAttr) {
                renderState.updateAttributeLoc(gl2ES2, true, this.gca_ColorsAttr, false);
            }
        }
        this.rsLocal.update(gl2ES2, renderState, b, n, true, false);
        if (null != this.gcu_ColorTexUnit) {
            renderState.updateUniformLoc(gl2ES2, b, this.gcu_ColorTexUnit, false);
            renderState.updateUniformLoc(gl2ES2, b, this.gcu_ColorTexBBox, false);
        }
    }
    
    @Override
    protected void drawImpl(final GL2ES2 gl2ES2, final RegionRenderer regionRenderer, final int[] array) {
        this.useShaderProgram(gl2ES2, regionRenderer, this.getRenderModes(), this.getQuality());
        if (0 >= this.indicesBuffer.getElementCount()) {
            if (VBORegionSPES2.DEBUG_INSTANCE) {
                System.err.printf("VBORegionSPES2.drawImpl: Empty%n", new Object[0]);
            }
            return;
        }
        this.gca_VerticesAttr.enableBuffer(gl2ES2, true);
        this.gca_CurveParamsAttr.enableBuffer(gl2ES2, true);
        if (null != this.gca_ColorsAttr) {
            this.gca_ColorsAttr.enableBuffer(gl2ES2, true);
        }
        this.indicesBuffer.bindBuffer(gl2ES2, true);
        if (regionRenderer.getRenderState().isHintMaskSet(1)) {
            gl2ES2.glBlendFunc(770, 771);
        }
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
        if (VBORegionSPES2.DEBUG_INSTANCE) {
            System.err.println("VBORegionSPES2 Destroy: " + this);
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
        this.spPass1 = null;
    }
}
