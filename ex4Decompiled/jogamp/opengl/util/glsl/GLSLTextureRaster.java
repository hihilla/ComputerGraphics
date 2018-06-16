// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.glsl;

import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.GL;
import java.nio.FloatBuffer;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.GLArrayDataServer;
import com.jogamp.opengl.GLUniformData;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class GLSLTextureRaster
{
    private final boolean textureVertFlipped;
    private final int textureUnit;
    private ShaderProgram sp;
    private PMVMatrix pmvMatrix;
    private GLUniformData pmvMatrixUniform;
    private GLUniformData activeTexUniform;
    private GLArrayDataServer interleavedVBO;
    static final String shaderBasename = "texture01_xxx";
    static final String shaderSrcPath = "../../shader";
    static final String shaderBinPath = "../../shader/bin";
    private static final float[] s_quadVertices;
    private static final float[] s_quadTexCoords00;
    private static final float[] s_quadTexCoords01;
    
    public GLSLTextureRaster(final int textureUnit, final boolean textureVertFlipped) {
        this.textureVertFlipped = textureVertFlipped;
        this.textureUnit = textureUnit;
    }
    
    public int getTextureUnit() {
        return this.textureUnit;
    }
    
    public void init(final GL2ES2 gl2ES2) {
        final ShaderCode create = ShaderCode.create(gl2ES2, 35633, this.getClass(), "../../shader", "../../shader/bin", "texture01_xxx", true);
        final ShaderCode create2 = ShaderCode.create(gl2ES2, 35632, this.getClass(), "../../shader", "../../shader/bin", "texture01_xxx", true);
        create.defaultShaderCustomization(gl2ES2, true, true);
        create2.defaultShaderCustomization(gl2ES2, true, true);
        (this.sp = new ShaderProgram()).add(create);
        this.sp.add(create2);
        if (!this.sp.link(gl2ES2, System.err)) {
            throw new GLException("Couldn't link program: " + this.sp);
        }
        this.sp.useProgram(gl2ES2, true);
        (this.pmvMatrix = new PMVMatrix()).glMatrixMode(5889);
        this.pmvMatrix.glLoadIdentity();
        this.pmvMatrix.glMatrixMode(5888);
        this.pmvMatrix.glLoadIdentity();
        this.pmvMatrixUniform = new GLUniformData("mgl_PMVMatrix", 4, 4, this.pmvMatrix.glGetPMvMatrixf());
        if (this.pmvMatrixUniform.setLocation(gl2ES2, this.sp.program()) < 0) {
            throw new GLException("Couldn't locate " + this.pmvMatrixUniform + " in shader: " + this.sp);
        }
        gl2ES2.glUniform(this.pmvMatrixUniform);
        this.activeTexUniform = new GLUniformData("mgl_Texture0", this.textureUnit);
        if (this.activeTexUniform.setLocation(gl2ES2, this.sp.program()) < 0) {
            throw new GLException("Couldn't locate " + this.activeTexUniform + " in shader: " + this.sp);
        }
        gl2ES2.glUniform(this.activeTexUniform);
        float[] array;
        if (this.textureVertFlipped) {
            array = GLSLTextureRaster.s_quadTexCoords01;
        }
        else {
            array = GLSLTextureRaster.s_quadTexCoords00;
        }
        this.interleavedVBO = GLArrayDataServer.createGLSLInterleaved(5, 5126, false, 8, 35044);
        final GLArrayData addGLSLSubArray = this.interleavedVBO.addGLSLSubArray("mgl_Vertex", 3, 34962);
        if (addGLSLSubArray.setLocation(gl2ES2, this.sp.program()) < 0) {
            throw new GLException("Couldn't locate " + addGLSLSubArray + " in shader: " + this.sp);
        }
        final GLArrayData addGLSLSubArray2 = this.interleavedVBO.addGLSLSubArray("mgl_MultiTexCoord", 2, 34962);
        if (addGLSLSubArray2.setLocation(gl2ES2, this.sp.program()) < 0) {
            throw new GLException("Couldn't locate " + addGLSLSubArray2 + " in shader: " + this.sp);
        }
        final FloatBuffer floatBuffer = (FloatBuffer)this.interleavedVBO.getBuffer();
        for (int i = 0; i < 4; ++i) {
            floatBuffer.put(GLSLTextureRaster.s_quadVertices, i * 3, 3);
            floatBuffer.put(array, i * 2, 2);
        }
        this.interleavedVBO.seal(gl2ES2, true);
        this.interleavedVBO.enableBuffer(gl2ES2, false);
        this.sp.useProgram(gl2ES2, false);
    }
    
    public void reshape(final GL2ES2 gl2ES2, final int n, final int n2, final int n3, final int n4) {
        if (null != this.sp) {
            this.pmvMatrix.glMatrixMode(5889);
            this.pmvMatrix.glLoadIdentity();
            this.pmvMatrix.glOrthof(-1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 10.0f);
            this.pmvMatrix.glMatrixMode(5888);
            this.pmvMatrix.glLoadIdentity();
            this.sp.useProgram(gl2ES2, true);
            gl2ES2.glUniform(this.pmvMatrixUniform);
            this.sp.useProgram(gl2ES2, false);
        }
    }
    
    public void dispose(final GL2ES2 gl2ES2) {
        if (null != this.pmvMatrixUniform) {
            this.pmvMatrixUniform = null;
        }
        this.pmvMatrix = null;
        if (null != this.interleavedVBO) {
            this.interleavedVBO.destroy(gl2ES2);
            this.interleavedVBO = null;
        }
        if (null != this.sp) {
            this.sp.destroy(gl2ES2);
            this.sp = null;
        }
    }
    
    public void display(final GL2ES2 gl2ES2) {
        if (null != this.sp) {
            this.sp.useProgram(gl2ES2, true);
            this.interleavedVBO.enableBuffer(gl2ES2, true);
            gl2ES2.glDrawArrays(5, 0, 4);
            this.interleavedVBO.enableBuffer(gl2ES2, false);
            this.sp.useProgram(gl2ES2, false);
        }
    }
    
    static {
        s_quadVertices = new float[] { -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f };
        s_quadTexCoords00 = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
        s_quadTexCoords01 = new float[] { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
    }
}
