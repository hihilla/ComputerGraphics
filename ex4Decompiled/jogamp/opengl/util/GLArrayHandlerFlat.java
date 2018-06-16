// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util;

import com.jogamp.opengl.util.GLArrayDataWrapper;
import com.jogamp.opengl.GL;

public interface GLArrayHandlerFlat
{
    void syncData(final GL p0, final Object p1);
    
    void enableState(final GL p0, final boolean p1, final Object p2);
    
    GLArrayDataWrapper getData();
}
