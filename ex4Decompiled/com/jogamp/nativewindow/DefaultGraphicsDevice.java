// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import jogamp.nativewindow.NativeWindowFactoryImpl;

public class DefaultGraphicsDevice implements Cloneable, AbstractGraphicsDevice
{
    private static final String separator = "_";
    private final String type;
    protected final String connection;
    protected final int unitID;
    protected final String uniqueID;
    protected long handle;
    protected ToolkitLock toolkitLock;
    
    public static String getDefaultDisplayConnection() {
        return NativeWindowFactory.getDefaultDisplayConnection();
    }
    
    public static String getDefaultDisplayConnection(final String s) {
        return NativeWindowFactory.getDefaultDisplayConnection(s);
    }
    
    public DefaultGraphicsDevice(final String s, final String s2, final int n) {
        this(s, s2, n, 0L, NativeWindowFactory.getDefaultToolkitLock(s));
    }
    
    public DefaultGraphicsDevice(final String s, final String s2, final int n, final long n2) {
        this(s, s2, n, n2, NativeWindowFactory.getDefaultToolkitLock(s, n2));
    }
    
    public DefaultGraphicsDevice(final String type, final String connection, final int unitID, final long handle, final ToolkitLock toolkitLock) {
        this.type = type;
        this.connection = connection;
        this.unitID = unitID;
        this.uniqueID = getUniqueID(type, connection, unitID);
        this.handle = handle;
        this.toolkitLock = ((null != toolkitLock) ? toolkitLock : NativeWindowFactoryImpl.getNullToolkitLock());
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new NativeWindowException(ex);
        }
    }
    
    @Override
    public final String getType() {
        return this.type;
    }
    
    @Override
    public final String getConnection() {
        return this.connection;
    }
    
    @Override
    public final int getUnitID() {
        return this.unitID;
    }
    
    @Override
    public final String getUniqueID() {
        return this.uniqueID;
    }
    
    @Override
    public final long getHandle() {
        return this.handle;
    }
    
    @Override
    public final void lock() {
        this.toolkitLock.lock();
    }
    
    @Override
    public final void validateLocked() throws RuntimeException {
        this.toolkitLock.validateLocked();
    }
    
    @Override
    public final void unlock() {
        this.toolkitLock.unlock();
    }
    
    @Override
    public boolean open() {
        return false;
    }
    
    @Override
    public boolean close() {
        this.toolkitLock.dispose();
        if (0L != this.handle) {
            this.handle = 0L;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean isHandleOwner() {
        return false;
    }
    
    @Override
    public void clearHandleOwner() {
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[type " + this.getType() + ", connection " + this.getConnection() + ", unitID " + this.getUnitID() + ", handle 0x" + Long.toHexString(this.getHandle()) + ", owner " + this.isHandleOwner() + ", " + this.toolkitLock + "]";
    }
    
    protected final long setHandle(final long handle) {
        final long handle2 = this.handle;
        this.handle = handle;
        return handle2;
    }
    
    protected Object getHandleOwnership() {
        return null;
    }
    
    protected Object setHandleOwnership(final Object o) {
        return null;
    }
    
    public static final void swapDeviceHandleAndOwnership(final DefaultGraphicsDevice defaultGraphicsDevice, final DefaultGraphicsDevice defaultGraphicsDevice2) {
        defaultGraphicsDevice.lock();
        try {
            defaultGraphicsDevice2.lock();
            try {
                defaultGraphicsDevice.setHandle(defaultGraphicsDevice2.setHandle(defaultGraphicsDevice.getHandle()));
                defaultGraphicsDevice.setHandleOwnership(defaultGraphicsDevice2.setHandleOwnership(defaultGraphicsDevice.getHandleOwnership()));
            }
            finally {
                defaultGraphicsDevice2.unlock();
            }
        }
        finally {
            defaultGraphicsDevice.unlock();
        }
    }
    
    protected ToolkitLock setToolkitLock(final ToolkitLock toolkitLock) {
        final ToolkitLock toolkitLock2 = this.toolkitLock;
        toolkitLock2.lock();
        try {
            this.toolkitLock = ((null == toolkitLock) ? NativeWindowFactoryImpl.getNullToolkitLock() : toolkitLock);
        }
        finally {
            toolkitLock2.unlock();
        }
        return toolkitLock2;
    }
    
    public final ToolkitLock getToolkitLock() {
        return this.toolkitLock;
    }
    
    private static String getUniqueID(final String s, final String s2, final int n) {
        return (s + "_" + s2 + "_" + n).intern();
    }
}
