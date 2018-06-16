// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.egl;

import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.common.util.VersionNumber;
import com.jogamp.nativewindow.DefaultGraphicsDevice;

public class EGLGraphicsDevice extends DefaultGraphicsDevice implements Cloneable
{
    private final long[] nativeDisplayID;
    private EGLDisplayLifecycleCallback eglLifecycleCallback;
    private VersionNumber eglVersion;
    
    public EGLGraphicsDevice() {
        super(NativeWindowFactory.TYPE_EGL, DefaultGraphicsDevice.getDefaultDisplayConnection(), 0);
        this.nativeDisplayID = new long[1];
        this.eglVersion = VersionNumber.zeroVersion;
        this.nativeDisplayID[0] = 0L;
        this.eglLifecycleCallback = null;
    }
    
    public EGLGraphicsDevice(final AbstractGraphicsDevice abstractGraphicsDevice, final long n, final EGLDisplayLifecycleCallback eglLifecycleCallback) {
        super(NativeWindowFactory.TYPE_EGL, abstractGraphicsDevice.getConnection(), abstractGraphicsDevice.getUnitID(), n);
        this.nativeDisplayID = new long[1];
        this.eglVersion = VersionNumber.zeroVersion;
        long n2;
        if (abstractGraphicsDevice instanceof EGLGraphicsDevice) {
            n2 = ((EGLGraphicsDevice)abstractGraphicsDevice).getNativeDisplayID();
        }
        else {
            n2 = abstractGraphicsDevice.getHandle();
        }
        this.nativeDisplayID[0] = n2;
        this.eglLifecycleCallback = eglLifecycleCallback;
    }
    
    public EGLGraphicsDevice(final long n, final long n2, final String s, final int n3, final EGLDisplayLifecycleCallback eglLifecycleCallback) {
        super(NativeWindowFactory.TYPE_EGL, s, n3, n2);
        this.nativeDisplayID = new long[1];
        this.eglVersion = VersionNumber.zeroVersion;
        this.nativeDisplayID[0] = n;
        this.eglLifecycleCallback = eglLifecycleCallback;
    }
    
    public VersionNumber getEGLVersion() {
        return this.eglVersion;
    }
    
    public long getNativeDisplayID() {
        return this.nativeDisplayID[0];
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public boolean open() {
        if (null == this.eglLifecycleCallback || 0L != this.handle) {
            return false;
        }
        if (EGLGraphicsDevice.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - EGLGraphicsDevice.open(): " + this);
        }
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        this.handle = this.eglLifecycleCallback.eglGetAndInitDisplay(this.nativeDisplayID, array, array2);
        if (0L == this.handle) {
            this.eglVersion = VersionNumber.zeroVersion;
            throw new NativeWindowException("EGLGraphicsDevice.open() failed: " + this);
        }
        this.eglVersion = new VersionNumber(array[0], array2[0], 0);
        return true;
    }
    
    @Override
    public boolean close() {
        if (null != this.eglLifecycleCallback && 0L != this.handle) {
            if (EGLGraphicsDevice.DEBUG) {
                System.err.println(Thread.currentThread().getName() + " - EGLGraphicsDevice.close(): " + this);
            }
            this.eglLifecycleCallback.eglTerminate(this.handle);
        }
        return super.close();
    }
    
    @Override
    public boolean isHandleOwner() {
        return null != this.eglLifecycleCallback;
    }
    
    @Override
    public void clearHandleOwner() {
        this.eglLifecycleCallback = null;
    }
    
    @Override
    protected Object getHandleOwnership() {
        return this.eglLifecycleCallback;
    }
    
    @Override
    protected Object setHandleOwnership(final Object o) {
        final EGLDisplayLifecycleCallback eglLifecycleCallback = this.eglLifecycleCallback;
        this.eglLifecycleCallback = (EGLDisplayLifecycleCallback)o;
        return eglLifecycleCallback;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[type " + this.getType() + ", v" + this.eglVersion + ", connection " + this.getConnection() + ", unitID " + this.getUnitID() + ", handle 0x" + Long.toHexString(this.getHandle()) + ", owner " + this.isHandleOwner() + ", " + this.toolkitLock + "]";
    }
    
    public interface EGLDisplayLifecycleCallback
    {
        long eglGetAndInitDisplay(final long[] p0, final int[] p1, final int[] p2);
        
        void eglTerminate(final long p0);
    }
}
