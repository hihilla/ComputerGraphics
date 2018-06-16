// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util;

import com.jogamp.opengl.GL;
import java.util.ArrayList;
import com.jogamp.opengl.util.GLArrayDataEditable;
import java.util.List;

public class GLArrayHandlerInterleaved extends GLVBOArrayHandler
{
    private final List<GLArrayHandlerFlat> subArrays;
    
    public GLArrayHandlerInterleaved(final GLArrayDataEditable glArrayDataEditable) {
        super(glArrayDataEditable);
        this.subArrays = new ArrayList<GLArrayHandlerFlat>();
    }
    
    @Override
    public final void setSubArrayVBOName(final int vboName) {
        for (int i = 0; i < this.subArrays.size(); ++i) {
            this.subArrays.get(i).getData().setVBOName(vboName);
        }
    }
    
    @Override
    public final void addSubHandler(final GLArrayHandlerFlat glArrayHandlerFlat) {
        this.subArrays.add(glArrayHandlerFlat);
    }
    
    private final void syncSubData(final GL gl, final Object o) {
        for (int i = 0; i < this.subArrays.size(); ++i) {
            this.subArrays.get(i).syncData(gl, o);
        }
    }
    
    @Override
    public final void enableState(final GL gl, final boolean b, final Object o) {
        if (b) {
            final boolean bindBuffer = this.bindBuffer(gl, true);
            this.syncSubData(gl, o);
            if (bindBuffer) {
                this.bindBuffer(gl, false);
            }
        }
        for (int i = 0; i < this.subArrays.size(); ++i) {
            this.subArrays.get(i).enableState(gl, b, o);
        }
    }
}
