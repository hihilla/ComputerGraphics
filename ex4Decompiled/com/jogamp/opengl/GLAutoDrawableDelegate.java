// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.util.locks.LockFactory;
import jogamp.opengl.GLContextImpl;
import jogamp.opengl.GLDrawableImpl;
import com.jogamp.common.util.locks.RecursiveLock;
import jogamp.opengl.GLAutoDrawableBase;

public class GLAutoDrawableDelegate extends GLAutoDrawableBase implements GLAutoDrawable
{
    private Object upstreamWidget;
    private final RecursiveLock lock;
    
    public GLAutoDrawableDelegate(final GLDrawable glDrawable, final GLContext glContext, final Object upstreamWidget, final boolean b, final RecursiveLock recursiveLock) {
        super((GLDrawableImpl)glDrawable, (GLContextImpl)glContext, b);
        if (null == glDrawable) {
            throw new IllegalArgumentException("null drawable");
        }
        this.upstreamWidget = upstreamWidget;
        this.lock = ((null != recursiveLock) ? recursiveLock : LockFactory.createRecursiveLock());
    }
    
    public final void windowRepaintOp() {
        super.defaultWindowRepaintOp();
    }
    
    public final void windowResizedOp(final int n, final int n2) {
        super.defaultWindowResizedOp(n, n2);
    }
    
    public final void windowDestroyNotifyOp() {
        super.defaultWindowDestroyNotifyOp();
    }
    
    @Override
    public final RecursiveLock getUpstreamLock() {
        return this.lock;
    }
    
    @Override
    public final Object getUpstreamWidget() {
        return this.upstreamWidget;
    }
    
    public final void setUpstreamWidget(final Object upstreamWidget) {
        this.upstreamWidget = upstreamWidget;
    }
    
    @Override
    public final void destroy() {
        this.defaultDestroy();
    }
    
    @Override
    protected void destroyImplInLock() {
        super.destroyImplInLock();
    }
    
    @Override
    public void display() {
        this.defaultDisplay();
    }
    
    @Override
    public final GLDrawableFactory getFactory() {
        return this.drawable.getFactory();
    }
    
    @Override
    public final void swapBuffers() throws GLException {
        this.defaultSwapBuffers();
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[ \n\tHelper: " + this.helper + ", \n\tDrawable: " + this.drawable + ", \n\tContext: " + this.context + ", \n\tUpstreamWidget: " + this.upstreamWidget + "]";
    }
}
