// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util;

import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.GLArrayDataEditable;

public class GLFixedArrayHandler extends GLVBOArrayHandler
{
    public GLFixedArrayHandler(final GLArrayDataEditable glArrayDataEditable) {
        super(glArrayDataEditable);
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
        final GL2ES1 gl2ES1 = gl.getGL2ES1();
        if (b) {
            final boolean bindBuffer = this.bindBuffer(gl, true);
            switch (this.ad.getIndex()) {
                case 32884: {
                    gl2ES1.glVertexPointer(this.ad);
                    break;
                }
                case 32885: {
                    gl2ES1.glNormalPointer(this.ad);
                    break;
                }
                case 32886: {
                    gl2ES1.glColorPointer(this.ad);
                    break;
                }
                case 32888: {
                    gl2ES1.glTexCoordPointer(this.ad);
                    break;
                }
                default: {
                    throw new GLException("invalid glArrayIndex: " + this.ad.getIndex() + ":\n\t" + this.ad);
                }
            }
            if (bindBuffer) {
                this.bindBuffer(gl, false);
            }
            gl2ES1.glEnableClientState(this.ad.getIndex());
        }
        else {
            gl2ES1.glDisableClientState(this.ad.getIndex());
        }
    }
}
