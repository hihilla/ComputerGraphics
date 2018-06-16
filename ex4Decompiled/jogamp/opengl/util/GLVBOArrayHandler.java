// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util;

import java.nio.Buffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.GLArrayDataEditable;

public abstract class GLVBOArrayHandler implements GLArrayHandler
{
    protected GLArrayDataEditable ad;
    
    public GLVBOArrayHandler(final GLArrayDataEditable ad) {
        this.ad = ad;
    }
    
    @Override
    public final boolean bindBuffer(final GL gl, final boolean b) {
        if (!this.ad.isVBO()) {
            return false;
        }
        if (b) {
            gl.glBindBuffer(this.ad.getVBOTarget(), this.ad.getVBOName());
            if (!this.ad.isVBOWritten()) {
                final Buffer buffer = this.ad.getBuffer();
                if (null != buffer) {
                    gl.glBufferData(this.ad.getVBOTarget(), buffer.limit() * this.ad.getComponentSizeInBytes(), buffer, this.ad.getVBOUsage());
                }
                this.ad.setVBOWritten(true);
            }
        }
        else {
            gl.glBindBuffer(this.ad.getVBOTarget(), 0);
        }
        return true;
    }
}
