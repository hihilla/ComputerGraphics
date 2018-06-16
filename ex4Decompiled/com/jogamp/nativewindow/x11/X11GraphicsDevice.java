// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.x11;

import jogamp.nativewindow.x11.X11Util;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.nativewindow.x11.X11Lib;
import com.jogamp.nativewindow.ToolkitLock;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.DefaultGraphicsDevice;

public class X11GraphicsDevice extends DefaultGraphicsDevice implements Cloneable
{
    boolean handleOwner;
    final boolean isXineramaEnabled;
    
    public X11GraphicsDevice(final String s, final int n) {
        super(NativeWindowFactory.TYPE_X11, s, n);
        this.handleOwner = false;
        this.isXineramaEnabled = false;
    }
    
    public X11GraphicsDevice(final long n, final int n2, final boolean b) {
        this(n, n2, NativeWindowFactory.getDefaultToolkitLock(NativeWindowFactory.TYPE_X11, n), b);
    }
    
    public X11GraphicsDevice(final long n, final int n2, final ToolkitLock toolkitLock, final boolean handleOwner) {
        super(NativeWindowFactory.TYPE_X11, X11Lib.XDisplayString(n), n2, n, toolkitLock);
        if (0L == n) {
            throw new NativeWindowException("null display");
        }
        this.handleOwner = handleOwner;
        this.isXineramaEnabled = X11Util.XineramaIsEnabled(this);
    }
    
    public X11GraphicsDevice(final String s, final int n, final ToolkitLock toolkitLock) {
        super(NativeWindowFactory.TYPE_X11, s, n, 0L, toolkitLock);
        this.handleOwner = true;
        this.open();
        this.isXineramaEnabled = X11Util.XineramaIsEnabled(this);
    }
    
    private static int getDefaultScreenImpl(final long n) {
        return X11Lib.DefaultScreen(n);
    }
    
    public int getDefaultScreen() {
        final long handle = this.getHandle();
        if (0L == handle) {
            throw new NativeWindowException("null display");
        }
        final int defaultScreenImpl = getDefaultScreenImpl(handle);
        if (X11GraphicsDevice.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - X11GraphicsDevice.getDefaultDisplay() of " + this + ": " + defaultScreenImpl + ", count " + X11Lib.ScreenCount(handle));
        }
        return defaultScreenImpl;
    }
    
    public int getDefaultVisualID() {
        final long handle = this.getHandle();
        if (0L == handle) {
            throw new NativeWindowException("null display");
        }
        return X11Lib.DefaultVisualID(handle, getDefaultScreenImpl(handle));
    }
    
    public final boolean isXineramaEnabled() {
        return this.isXineramaEnabled;
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public boolean open() {
        if (!this.handleOwner || 0L != this.handle) {
            return false;
        }
        if (X11GraphicsDevice.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - X11GraphicsDevice.open(): " + this);
        }
        this.handle = X11Util.openDisplay(this.connection);
        if (0L == this.handle) {
            throw new NativeWindowException("X11GraphicsDevice.open() failed: " + this);
        }
        return true;
    }
    
    @Override
    public boolean close() {
        if (this.handleOwner && 0L != this.handle) {
            if (X11GraphicsDevice.DEBUG) {
                System.err.println(Thread.currentThread().getName() + " - X11GraphicsDevice.close(): " + this);
            }
            X11Util.closeDisplay(this.handle);
        }
        return super.close();
    }
    
    @Override
    public boolean isHandleOwner() {
        return this.handleOwner;
    }
    
    @Override
    public void clearHandleOwner() {
        this.handleOwner = false;
    }
    
    @Override
    protected Object getHandleOwnership() {
        return this.handleOwner;
    }
    
    @Override
    protected Object setHandleOwnership(final Object o) {
        final Boolean value = this.handleOwner;
        this.handleOwner = (boolean)o;
        return value;
    }
}
