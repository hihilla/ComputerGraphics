// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util;

import com.jogamp.opengl.GL;

public interface GLArrayHandler
{
    boolean bindBuffer(final GL p0, final boolean p1);
    
    void enableState(final GL p0, final boolean p1, final Object p2);
    
    void addSubHandler(final GLArrayHandlerFlat p0) throws UnsupportedOperationException;
    
    void setSubArrayVBOName(final int p0);
}
