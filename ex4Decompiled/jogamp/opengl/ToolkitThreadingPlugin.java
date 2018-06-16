// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.GLException;

public interface ToolkitThreadingPlugin
{
    boolean isToolkitThread() throws GLException;
    
    boolean isOpenGLThread() throws GLException;
    
    void invokeOnOpenGLThread(final boolean p0, final Runnable p1) throws GLException;
}
