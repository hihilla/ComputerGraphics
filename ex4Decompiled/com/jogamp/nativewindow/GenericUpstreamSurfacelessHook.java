// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

public class GenericUpstreamSurfacelessHook extends UpstreamSurfaceHookMutableSize
{
    public GenericUpstreamSurfacelessHook(final int n, final int n2) {
        super(n, n2);
    }
    
    @Override
    public final void create(final ProxySurface proxySurface) {
        final AbstractGraphicsDevice device = proxySurface.getGraphicsConfiguration().getScreen().getDevice();
        device.lock();
        try {
            if (0L == device.getHandle()) {
                device.open();
                proxySurface.addUpstreamOptionBits(128);
            }
            if (0L != proxySurface.getSurfaceHandle()) {
                throw new InternalError("Upstream surface not null: " + proxySurface);
            }
            proxySurface.addUpstreamOptionBits(832);
        }
        finally {
            device.unlock();
        }
    }
    
    @Override
    public final void destroy(final ProxySurface proxySurface) {
        if (proxySurface.containsUpstreamOptionBits(64)) {
            final AbstractGraphicsDevice device = proxySurface.getGraphicsConfiguration().getScreen().getDevice();
            if (!proxySurface.containsUpstreamOptionBits(512)) {
                throw new InternalError("Owns upstream surface, but not a valid zero surface: " + proxySurface);
            }
            if (0L != proxySurface.getSurfaceHandle()) {
                throw new InternalError("Owns upstream valid zero surface, but non zero surface: " + proxySurface);
            }
            device.lock();
            try {
                proxySurface.clearUpstreamOptionBits(576);
            }
            finally {
                device.unlock();
            }
        }
    }
}
