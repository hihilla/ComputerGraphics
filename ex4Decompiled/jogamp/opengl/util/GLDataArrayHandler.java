// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util;

import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.GLArrayDataEditable;

public class GLDataArrayHandler extends GLVBOArrayHandler
{
    public GLDataArrayHandler(final GLArrayDataEditable glArrayDataEditable) {
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
        if (b) {
            if (!this.ad.isVBO()) {
                throw new GLException("GLDataArrayHandler can only handle VBOs.");
            }
            this.bindBuffer(gl, true);
            this.bindBuffer(gl, false);
        }
    }
}
