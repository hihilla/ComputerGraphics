// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.FBObject;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import com.jogamp.opengl.GLAutoDrawableDelegate;

public class GLOffscreenAutoDrawableImpl extends GLAutoDrawableDelegate implements GLOffscreenAutoDrawable
{
    public GLOffscreenAutoDrawableImpl(final GLDrawable glDrawable, final GLContext glContext, final Object o, final RecursiveLock recursiveLock) {
        super(glDrawable, glContext, o, true, recursiveLock);
    }
    
    @Override
    public void setSurfaceSize(final int n, final int n2) throws NativeWindowException, GLException {
        this.defaultWindowResizedOp(n, n2);
    }
    
    public static class FBOImpl extends GLOffscreenAutoDrawableImpl implements FBO
    {
        public FBOImpl(final GLFBODrawableImpl glfboDrawableImpl, final GLContext glContext, final Object o, final RecursiveLock recursiveLock) {
            super(glfboDrawableImpl, glContext, o, recursiveLock);
        }
        
        @Override
        public boolean isInitialized() {
            return ((GLFBODrawableImpl)this.drawable).isInitialized();
        }
        
        @Override
        public final int getTextureUnit() {
            return ((GLFBODrawableImpl)this.drawable).getTextureUnit();
        }
        
        @Override
        public final void setTextureUnit(final int textureUnit) {
            ((GLFBODrawableImpl)this.drawable).setTextureUnit(textureUnit);
        }
        
        @Override
        public final int getNumSamples() {
            return ((GLFBODrawableImpl)this.drawable).getNumSamples();
        }
        
        @Override
        public final void setNumSamples(final GL gl, final int n) throws GLException {
            ((GLFBODrawableImpl)this.drawable).setNumSamples(gl, n);
            this.windowRepaintOp();
        }
        
        @Override
        public final int setNumBuffers(final int numBuffers) throws GLException {
            return ((GLFBODrawableImpl)this.drawable).setNumBuffers(numBuffers);
        }
        
        @Override
        public final int getNumBuffers() {
            return ((GLFBODrawableImpl)this.drawable).getNumBuffers();
        }
        
        @Override
        public final FBObject getFBObject(final int n) {
            return ((GLFBODrawableImpl)this.drawable).getFBObject(n);
        }
        
        @Override
        public final FBObject.Colorbuffer getColorbuffer(final int n) {
            return ((GLFBODrawableImpl)this.drawable).getColorbuffer(n);
        }
        
        @Override
        public void resetSize(final GL gl) throws GLException {
            ((GLFBODrawableImpl)this.drawable).resetSize(gl);
        }
        
        @Override
        public final void setFBOMode(final int fboMode) throws IllegalStateException {
            ((GLFBODrawableImpl)this.drawable).setFBOMode(fboMode);
        }
        
        @Override
        public final int getFBOMode() {
            return ((GLFBODrawableImpl)this.drawable).getFBOMode();
        }
    }
}
