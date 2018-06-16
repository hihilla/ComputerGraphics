// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.glsl;

import java.nio.Buffer;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.glsl.ShaderState;
import com.jogamp.opengl.GL;
import jogamp.opengl.util.GLArrayHandlerFlat;
import com.jogamp.opengl.util.GLArrayDataEditable;
import jogamp.opengl.util.GLVBOArrayHandler;

public class GLSLArrayHandler extends GLVBOArrayHandler
{
    private final int[] tempI;
    
    public GLSLArrayHandler(final GLArrayDataEditable glArrayDataEditable) {
        super(glArrayDataEditable);
        this.tempI = new int[1];
    }
    
    @Override
    public final void setSubArrayVBOName(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void addSubHandler(final GLArrayHandlerFlat glArrayHandlerFlat) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final void enableState(final GL gl, final boolean b, final Object o) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        if (null != o) {
            this.enableShaderState(gl2ES2, b, (ShaderState)o);
        }
        else {
            this.enableSimple(gl2ES2, b);
        }
    }
    
    private final void enableShaderState(final GL2ES2 gl2ES2, final boolean b, final ShaderState shaderState) {
        if (b) {
            final Buffer buffer = this.ad.getBuffer();
            if (this.ad.isVBO()) {
                if (!this.ad.isVBOWritten()) {
                    gl2ES2.glBindBuffer(this.ad.getVBOTarget(), this.ad.getVBOName());
                    if (null != buffer) {
                        gl2ES2.glBufferData(this.ad.getVBOTarget(), this.ad.getSizeInBytes(), buffer, this.ad.getVBOUsage());
                    }
                    this.ad.setVBOWritten(true);
                    shaderState.vertexAttribPointer(gl2ES2, this.ad);
                    gl2ES2.glBindBuffer(this.ad.getVBOTarget(), 0);
                }
                else if (shaderState.getAttribLocation(gl2ES2, this.ad) >= 0) {
                    gl2ES2.glGetVertexAttribiv(this.ad.getLocation(), 34975, this.tempI, 0);
                    if (this.ad.getVBOName() != this.tempI[0]) {
                        gl2ES2.glBindBuffer(this.ad.getVBOTarget(), this.ad.getVBOName());
                        shaderState.vertexAttribPointer(gl2ES2, this.ad);
                        gl2ES2.glBindBuffer(this.ad.getVBOTarget(), 0);
                    }
                }
            }
            else if (null != buffer) {
                shaderState.vertexAttribPointer(gl2ES2, this.ad);
            }
            shaderState.enableVertexAttribArray(gl2ES2, this.ad);
        }
        else {
            shaderState.disableVertexAttribArray(gl2ES2, this.ad);
        }
    }
    
    private final void enableSimple(final GL2ES2 gl2ES2, final boolean b) {
        final int location = this.ad.getLocation();
        if (0 > location) {
            return;
        }
        if (b) {
            final Buffer buffer = this.ad.getBuffer();
            if (this.ad.isVBO()) {
                if (!this.ad.isVBOWritten()) {
                    gl2ES2.glBindBuffer(this.ad.getVBOTarget(), this.ad.getVBOName());
                    if (null != buffer) {
                        gl2ES2.glBufferData(this.ad.getVBOTarget(), this.ad.getSizeInBytes(), buffer, this.ad.getVBOUsage());
                    }
                    this.ad.setVBOWritten(true);
                    gl2ES2.glVertexAttribPointer(this.ad);
                    gl2ES2.glBindBuffer(this.ad.getVBOTarget(), 0);
                }
                else {
                    gl2ES2.glGetVertexAttribiv(location, 34975, this.tempI, 0);
                    if (this.ad.getVBOName() != this.tempI[0]) {
                        gl2ES2.glBindBuffer(this.ad.getVBOTarget(), this.ad.getVBOName());
                        gl2ES2.glVertexAttribPointer(this.ad);
                        gl2ES2.glBindBuffer(this.ad.getVBOTarget(), 0);
                    }
                }
            }
            else if (null != buffer) {
                gl2ES2.glVertexAttribPointer(this.ad);
            }
            gl2ES2.glEnableVertexAttribArray(location);
        }
        else {
            gl2ES2.glDisableVertexAttribArray(location);
        }
    }
}
