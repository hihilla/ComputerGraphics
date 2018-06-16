// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.curve.opengl;

import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.graph.curve.Region;
import com.jogamp.opengl.util.GLArrayDataServer;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import java.nio.FloatBuffer;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.graph.geom.Vertex;

public class RenderState
{
    private static final String thisKey = "jogamp.graph.curve.RenderState";
    public static final int BITHINT_BLENDING_ENABLED = 1;
    public static final int BITHINT_GLOBAL_DEPTH_TEST_ENABLED = 2;
    private final Vertex.Factory<? extends Vertex> vertexFactory;
    private final PMVMatrix pmvMatrix;
    private final float[] weight;
    private final FloatBuffer weightBuffer;
    private final float[] colorStatic;
    private final FloatBuffer colorStaticBuffer;
    private ShaderProgram sp;
    private int hintBitfield;
    private final int id;
    private static int nextID;
    
    public static RenderState createRenderState(final Vertex.Factory<? extends Vertex> factory) {
        return new RenderState(factory, null);
    }
    
    public static RenderState createRenderState(final Vertex.Factory<? extends Vertex> factory, final PMVMatrix pmvMatrix) {
        return new RenderState(factory, pmvMatrix);
    }
    
    public static final RenderState getRenderState(final GL2ES2 gl2ES2) {
        return (RenderState)gl2ES2.getContext().getAttachedObject("jogamp.graph.curve.RenderState");
    }
    
    private static synchronized int getNextID() {
        return RenderState.nextID++;
    }
    
    protected RenderState(final Vertex.Factory<? extends Vertex> vertexFactory, final PMVMatrix pmvMatrix) {
        this.id = getNextID();
        this.sp = null;
        this.vertexFactory = vertexFactory;
        this.pmvMatrix = ((null != pmvMatrix) ? pmvMatrix : new PMVMatrix());
        this.weight = new float[1];
        this.weightBuffer = FloatBuffer.wrap(this.weight);
        this.colorStatic = new float[4];
        this.colorStaticBuffer = FloatBuffer.wrap(this.colorStatic);
        this.hintBitfield = 0;
    }
    
    public final int id() {
        return this.id;
    }
    
    public final ShaderProgram getShaderProgram() {
        return this.sp;
    }
    
    public final boolean isShaderProgramInUse() {
        return null != this.sp && this.sp.inUse();
    }
    
    public final boolean setShaderProgram(final GL2ES2 gl2ES2, final ShaderProgram sp) {
        if (sp.equals(this.sp)) {
            sp.useProgram(gl2ES2, true);
            return false;
        }
        if (null != this.sp) {
            this.sp.notifyNotInUse();
        }
        (this.sp = sp).useProgram(gl2ES2, true);
        return true;
    }
    
    public final Vertex.Factory<? extends Vertex> getVertexFactory() {
        return this.vertexFactory;
    }
    
    public final PMVMatrix getMatrix() {
        return this.pmvMatrix;
    }
    
    public static boolean isWeightValid(final float n) {
        return 0.0f <= n && n <= 1.9f;
    }
    
    public final float getWeight() {
        return this.weight[0];
    }
    
    public final void setWeight(final float n) {
        if (!isWeightValid(n)) {
            throw new IllegalArgumentException("Weight out of range");
        }
        this.weight[0] = n;
    }
    
    public final float[] getColorStatic(final float[] array) {
        System.arraycopy(this.colorStatic, 0, array, 0, 4);
        return array;
    }
    
    public final void setColorStatic(final float n, final float n2, final float n3, final float n4) {
        this.colorStatic[0] = n;
        this.colorStatic[1] = n2;
        this.colorStatic[2] = n3;
        this.colorStatic[3] = n4;
    }
    
    public final boolean updateUniformLoc(final GL2ES2 gl2ES2, final boolean b, final GLUniformData glUniformData, final boolean b2) {
        if (!b && 0 <= glUniformData.getLocation()) {
            return true;
        }
        final boolean b3 = 0 <= glUniformData.setLocation(gl2ES2, this.sp.program());
        if (b2 && !b3) {
            throw new GLException("Could not locate " + glUniformData);
        }
        return b3;
    }
    
    public final boolean updateUniformDataLoc(final GL2ES2 gl2ES2, final boolean b, boolean b2, final GLUniformData glUniformData, final boolean b3) {
        final boolean b4 = b || 0 > glUniformData.getLocation();
        if (b4) {
            b2 = (0 <= glUniformData.setLocation(gl2ES2, this.sp.program()));
            if (b3 && !b2) {
                throw new GLException("Could not locate " + glUniformData);
            }
        }
        if (b2) {
            gl2ES2.glUniform(glUniformData);
            return true;
        }
        return !b4;
    }
    
    public final boolean updateAttributeLoc(final GL2ES2 gl2ES2, final boolean b, final GLArrayDataServer glArrayDataServer, final boolean b2) {
        if (!b && 0 <= glArrayDataServer.getLocation()) {
            return true;
        }
        final boolean b3 = 0 <= glArrayDataServer.setLocation(gl2ES2, this.sp.program());
        if (b2 && !b3) {
            throw new GLException("Could not locate " + glArrayDataServer);
        }
        return b3;
    }
    
    public final boolean isHintMaskSet(final int n) {
        return n == (this.hintBitfield & n);
    }
    
    public final void setHintMask(final int n) {
        this.hintBitfield |= n;
    }
    
    public final void clearHintMask(final int n) {
        this.hintBitfield &= ~n;
    }
    
    public void destroy(final GL2ES2 gl2ES2) {
        if (null != this.sp) {
            this.sp.destroy(gl2ES2);
            this.sp = null;
        }
    }
    
    public final RenderState attachTo(final GL2ES2 gl2ES2) {
        return (RenderState)gl2ES2.getContext().attachObject("jogamp.graph.curve.RenderState", this);
    }
    
    public final boolean detachFrom(final GL2ES2 gl2ES2) {
        if (gl2ES2.getContext().getAttachedObject("jogamp.graph.curve.RenderState") == this) {
            gl2ES2.getContext().detachObject("jogamp.graph.curve.RenderState");
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "RenderState[" + this.sp + "]";
    }
    
    static {
        RenderState.nextID = 1;
    }
    
    public static class ProgramLocal
    {
        public final GLUniformData gcu_PMVMatrix01;
        public final GLUniformData gcu_Weight;
        public final GLUniformData gcu_ColorStatic;
        private int rsId;
        
        public ProgramLocal() {
            this.rsId = -1;
            this.gcu_PMVMatrix01 = GLUniformData.creatEmptyMatrix("gcu_PMVMatrix01", 4, 4);
            this.gcu_Weight = GLUniformData.creatEmptyVector("gcu_Weight", 1);
            this.gcu_ColorStatic = GLUniformData.creatEmptyVector("gcu_ColorStatic", 4);
        }
        
        public final int getRenderStateId() {
            return this.rsId;
        }
        
        public final boolean update(final GL2ES2 gl2ES2, final RenderState renderState, final boolean b, final int n, final boolean b2, final boolean b3) {
            if (renderState.id() != this.rsId) {
                this.gcu_PMVMatrix01.setData(renderState.pmvMatrix.glGetPMvMatrixf());
                this.gcu_Weight.setData(renderState.weightBuffer);
                this.gcu_ColorStatic.setData(renderState.colorStaticBuffer);
                this.rsId = renderState.id();
            }
            boolean b4 = true;
            if (null != renderState.sp && renderState.sp.inUse()) {
                if (!Region.isTwoPass(n) || !b2) {
                    final boolean updateUniformDataLoc = renderState.updateUniformDataLoc(gl2ES2, b, true, this.gcu_PMVMatrix01, b3);
                    b4 = (b4 && updateUniformDataLoc);
                }
                if (b2) {
                    if (Region.hasVariableWeight(n)) {
                        final boolean updateUniformDataLoc2 = renderState.updateUniformDataLoc(gl2ES2, b, true, this.gcu_Weight, b3);
                        b4 = (b4 && updateUniformDataLoc2);
                    }
                    final boolean updateUniformDataLoc3 = renderState.updateUniformDataLoc(gl2ES2, b, true, this.gcu_ColorStatic, b3);
                    b4 = (b4 && updateUniformDataLoc3);
                }
            }
            return b4;
        }
        
        public StringBuilder toString(StringBuilder sb, final boolean b) {
            if (null == sb) {
                sb = new StringBuilder();
            }
            sb.append("ProgramLocal[rsID ").append(this.rsId).append(PlatformPropsImpl.NEWLINE);
            sb.append(this.gcu_PMVMatrix01).append(", ").append(PlatformPropsImpl.NEWLINE);
            sb.append(this.gcu_ColorStatic).append(", ");
            sb.append(this.gcu_Weight).append("]");
            return sb;
        }
        
        @Override
        public String toString() {
            return this.toString(null, false).toString();
        }
    }
}
