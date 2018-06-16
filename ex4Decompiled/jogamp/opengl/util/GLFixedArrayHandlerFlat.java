// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util;

import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.GLArrayDataWrapper;

public class GLFixedArrayHandlerFlat implements GLArrayHandlerFlat
{
    private final GLArrayDataWrapper ad;
    
    public GLFixedArrayHandlerFlat(final GLArrayDataWrapper ad) {
        this.ad = ad;
    }
    
    @Override
    public GLArrayDataWrapper getData() {
        return this.ad;
    }
    
    @Override
    public final void syncData(final GL gl, final Object o) {
        final GL2ES1 gl2ES1 = gl.getGL2ES1();
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
    }
    
    @Override
    public final void enableState(final GL gl, final boolean b, final Object o) {
        final GL2ES1 gl2ES1 = gl.getGL2ES1();
        if (b) {
            gl2ES1.glEnableClientState(this.ad.getIndex());
        }
        else {
            gl2ES1.glDisableClientState(this.ad.getIndex());
        }
    }
}
