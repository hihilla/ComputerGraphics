// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.nativewindow.NativeSurface;
import java.util.ArrayList;
import com.jogamp.nativewindow.SurfaceUpdatedListener;

public class SurfaceUpdatedHelper implements SurfaceUpdatedListener
{
    private final Object surfaceUpdatedListenersLock;
    private final ArrayList<SurfaceUpdatedListener> surfaceUpdatedListeners;
    private volatile boolean isEmpty;
    
    public SurfaceUpdatedHelper() {
        this.surfaceUpdatedListenersLock = new Object();
        this.surfaceUpdatedListeners = new ArrayList<SurfaceUpdatedListener>();
        this.isEmpty = true;
    }
    
    public final int size() {
        return this.surfaceUpdatedListeners.size();
    }
    
    public final SurfaceUpdatedListener get(final int n) {
        return this.surfaceUpdatedListeners.get(n);
    }
    
    public final void addSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.addSurfaceUpdatedListener(-1, surfaceUpdatedListener);
    }
    
    public final void addSurfaceUpdatedListener(int size, final SurfaceUpdatedListener surfaceUpdatedListener) throws IndexOutOfBoundsException {
        if (surfaceUpdatedListener == null) {
            return;
        }
        synchronized (this.surfaceUpdatedListenersLock) {
            if (0 > size) {
                size = this.surfaceUpdatedListeners.size();
            }
            this.surfaceUpdatedListeners.add(size, surfaceUpdatedListener);
            this.isEmpty = false;
        }
    }
    
    public final boolean removeSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        if (surfaceUpdatedListener == null) {
            return false;
        }
        synchronized (this.surfaceUpdatedListenersLock) {
            final boolean remove = this.surfaceUpdatedListeners.remove(surfaceUpdatedListener);
            this.isEmpty = (0 == this.surfaceUpdatedListeners.size());
            return remove;
        }
    }
    
    @Override
    public final void surfaceUpdated(final Object o, final NativeSurface nativeSurface, final long n) {
        if (this.isEmpty) {
            return;
        }
        synchronized (this.surfaceUpdatedListenersLock) {
            for (int i = 0; i < this.surfaceUpdatedListeners.size(); ++i) {
                this.surfaceUpdatedListeners.get(i).surfaceUpdated(o, nativeSurface, n);
            }
        }
    }
}
