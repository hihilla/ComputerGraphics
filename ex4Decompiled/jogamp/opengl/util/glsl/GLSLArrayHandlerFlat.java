// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.glsl;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.util.glsl.ShaderState;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.GLArrayDataWrapper;
import jogamp.opengl.util.GLArrayHandlerFlat;

public class GLSLArrayHandlerFlat implements GLArrayHandlerFlat
{
    private final GLArrayDataWrapper ad;
    
    public GLSLArrayHandlerFlat(final GLArrayDataWrapper ad) {
        this.ad = ad;
    }
    
    @Override
    public GLArrayDataWrapper getData() {
        return this.ad;
    }
    
    @Override
    public final void syncData(final GL gl, final Object o) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        if (null != o) {
            ((ShaderState)o).vertexAttribPointer(gl2ES2, this.ad);
        }
        else if (0 <= this.ad.getLocation()) {
            gl2ES2.glVertexAttribPointer(this.ad);
        }
    }
    
    @Override
    public final void enableState(final GL gl, final boolean b, final Object o) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        if (null != o) {
            final ShaderState shaderState = (ShaderState)o;
            if (b) {
                shaderState.enableVertexAttribArray(gl2ES2, this.ad);
            }
            else {
                shaderState.disableVertexAttribArray(gl2ES2, this.ad);
            }
        }
        else {
            final int location = this.ad.getLocation();
            if (0 <= location) {
                if (b) {
                    gl2ES2.glEnableVertexAttribArray(location);
                }
                else {
                    gl2ES2.glDisableVertexAttribArray(location);
                }
            }
        }
    }
}
