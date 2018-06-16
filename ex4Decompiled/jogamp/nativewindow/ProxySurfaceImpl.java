// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import java.io.PrintStream;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.SurfaceUpdatedListener;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.ProxySurface;

public abstract class ProxySurfaceImpl implements ProxySurface
{
    private final SurfaceUpdatedHelper surfaceUpdatedHelper;
    private AbstractGraphicsConfiguration config;
    private UpstreamSurfaceHook upstream;
    private long surfaceHandle_old;
    private final RecursiveLock surfaceLock;
    private int implBitfield;
    private boolean upstreamSurfaceHookLifecycleEnabled;
    
    protected ProxySurfaceImpl(final AbstractGraphicsConfiguration config, final UpstreamSurfaceHook upstream, final boolean b) {
        this.surfaceUpdatedHelper = new SurfaceUpdatedHelper();
        this.surfaceLock = LockFactory.createRecursiveLock();
        if (null == config) {
            throw new IllegalArgumentException("null AbstractGraphicsConfiguration");
        }
        if (null == upstream) {
            throw new IllegalArgumentException("null UpstreamSurfaceHook");
        }
        this.config = config;
        this.upstream = upstream;
        this.surfaceHandle_old = 0L;
        this.implBitfield = 0;
        this.upstreamSurfaceHookLifecycleEnabled = true;
        if (b) {
            this.addUpstreamOptionBits(128);
        }
    }
    
    @Override
    public final NativeSurface getUpstreamSurface() {
        return this.upstream.getUpstreamSurface();
    }
    
    @Override
    public final UpstreamSurfaceHook getUpstreamSurfaceHook() {
        return this.upstream;
    }
    
    @Override
    public void setUpstreamSurfaceHook(final UpstreamSurfaceHook upstream) {
        if (null == upstream) {
            throw new IllegalArgumentException("null UpstreamSurfaceHook");
        }
        this.upstream = upstream;
    }
    
    @Override
    public final void enableUpstreamSurfaceHookLifecycle(final boolean upstreamSurfaceHookLifecycleEnabled) {
        this.upstreamSurfaceHookLifecycleEnabled = upstreamSurfaceHookLifecycleEnabled;
    }
    
    @Override
    public void createNotify() {
        if (this.upstreamSurfaceHookLifecycleEnabled) {
            this.upstream.create(this);
        }
        this.surfaceHandle_old = 0L;
    }
    
    @Override
    public void destroyNotify() {
        if (this.upstreamSurfaceHookLifecycleEnabled) {
            this.upstream.destroy(this);
            if (this.containsUpstreamOptionBits(128)) {
                this.getGraphicsConfiguration().getScreen().getDevice().close();
                this.clearUpstreamOptionBits(128);
            }
            this.invalidateImpl();
        }
        this.surfaceHandle_old = 0L;
    }
    
    protected void invalidateImpl() {
        throw new InternalError("UpstreamSurfaceHook given, but required method not implemented.");
    }
    
    @Override
    public final AbstractGraphicsConfiguration getGraphicsConfiguration() {
        return this.config.getNativeGraphicsConfiguration();
    }
    
    @Override
    public final long getDisplayHandle() {
        return this.config.getNativeGraphicsConfiguration().getScreen().getDevice().getHandle();
    }
    
    @Override
    public final void setGraphicsConfiguration(final AbstractGraphicsConfiguration config) {
        this.config = config;
    }
    
    @Override
    public final int getScreenIndex() {
        return this.getGraphicsConfiguration().getScreen().getIndex();
    }
    
    @Override
    public abstract long getSurfaceHandle();
    
    @Override
    public abstract void setSurfaceHandle(final long p0);
    
    @Override
    public final int getSurfaceWidth() {
        return this.upstream.getSurfaceWidth(this);
    }
    
    @Override
    public final int getSurfaceHeight() {
        return this.upstream.getSurfaceHeight(this);
    }
    
    @Override
    public boolean surfaceSwap() {
        return false;
    }
    
    @Override
    public void addSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.surfaceUpdatedHelper.addSurfaceUpdatedListener(surfaceUpdatedListener);
    }
    
    @Override
    public void addSurfaceUpdatedListener(final int n, final SurfaceUpdatedListener surfaceUpdatedListener) throws IndexOutOfBoundsException {
        this.surfaceUpdatedHelper.addSurfaceUpdatedListener(n, surfaceUpdatedListener);
    }
    
    @Override
    public void removeSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.surfaceUpdatedHelper.removeSurfaceUpdatedListener(surfaceUpdatedListener);
    }
    
    @Override
    public void surfaceUpdated(final Object o, final NativeSurface nativeSurface, final long n) {
        this.surfaceUpdatedHelper.surfaceUpdated(o, nativeSurface, n);
    }
    
    @Override
    public int lockSurface() throws NativeWindowException, RuntimeException {
        this.surfaceLock.lock();
        int lockSurfaceImpl = (this.surfaceLock.getHoldCount() == 1) ? 1 : 3;
        if (lockSurfaceImpl != 0) {
            try {
                final AbstractGraphicsDevice device = this.getGraphicsConfiguration().getScreen().getDevice();
                device.lock();
                try {
                    lockSurfaceImpl = this.lockSurfaceImpl();
                    if (3 == lockSurfaceImpl && this.surfaceHandle_old != this.getSurfaceHandle()) {
                        lockSurfaceImpl = 2;
                        if (ProxySurfaceImpl.DEBUG) {
                            System.err.println("ProxySurfaceImpl: surface change 0x" + Long.toHexString(this.surfaceHandle_old) + " -> 0x" + Long.toHexString(this.getSurfaceHandle()));
                        }
                    }
                }
                finally {
                    if (1 >= lockSurfaceImpl) {
                        device.unlock();
                    }
                }
            }
            finally {
                if (1 >= lockSurfaceImpl) {
                    this.surfaceLock.unlock();
                }
            }
        }
        return lockSurfaceImpl;
    }
    
    @Override
    public final void unlockSurface() {
        this.surfaceLock.validateLocked();
        this.surfaceHandle_old = this.getSurfaceHandle();
        if (this.surfaceLock.getHoldCount() == 1) {
            final AbstractGraphicsDevice device = this.getGraphicsConfiguration().getScreen().getDevice();
            try {
                this.unlockSurfaceImpl();
            }
            finally {
                device.unlock();
            }
        }
        this.surfaceLock.unlock();
    }
    
    protected abstract int lockSurfaceImpl();
    
    protected abstract void unlockSurfaceImpl();
    
    public final void validateSurfaceLocked() {
        this.surfaceLock.validateLocked();
    }
    
    @Override
    public final boolean isSurfaceLockedByOtherThread() {
        return this.surfaceLock.isLockedByOtherThread();
    }
    
    @Override
    public final Thread getSurfaceLockOwner() {
        return this.surfaceLock.getOwner();
    }
    
    @Override
    public final StringBuilder getUpstreamOptionBits(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("UOB[ ");
        if (0 == this.implBitfield) {
            sb.append("]");
            return sb;
        }
        int n = 0;
        if (0x0 != (this.implBitfield & 0x40)) {
            sb.append("OWNS_SURFACE");
            n = 1;
        }
        if (0x0 != (this.implBitfield & 0x80)) {
            if (n != 0) {
                sb.append(" | ");
            }
            sb.append("OWNS_DEVICE");
            n = 1;
        }
        if (0x0 != (this.implBitfield & 0x100)) {
            if (n != 0) {
                sb.append(" | ");
            }
            sb.append("WINDOW_INVISIBLE");
            n = 1;
        }
        if (0x0 != (this.implBitfield & 0x200)) {
            if (n != 0) {
                sb.append(" | ");
            }
            sb.append("SURFACELESS");
        }
        sb.append(" ]");
        return sb;
    }
    
    @Override
    public final int getUpstreamOptionBits() {
        return this.implBitfield;
    }
    
    @Override
    public final boolean containsUpstreamOptionBits(final int n) {
        return n == (this.implBitfield & n);
    }
    
    @Override
    public final void addUpstreamOptionBits(final int n) {
        this.implBitfield |= n;
    }
    
    @Override
    public final void clearUpstreamOptionBits(final int n) {
        this.implBitfield &= ~n;
    }
    
    public static void dumpHierarchy(final PrintStream printStream, final ProxySurface proxySurface) {
        printStream.println("Surface Hierarchy of " + proxySurface.getClass().getName());
        dumpHierarchy(printStream, proxySurface, "");
        printStream.println();
    }
    
    private static void dumpHierarchy(final PrintStream printStream, final NativeSurface nativeSurface, String s) {
        s += "  ";
        printStream.println(s + "Surface device " + nativeSurface.getGraphicsConfiguration().getScreen().getDevice());
        printStream.println(s + "Surface size " + nativeSurface.getSurfaceWidth() + "x" + nativeSurface.getSurfaceHeight() + ", handle 0x" + Long.toHexString(nativeSurface.getSurfaceHandle()));
        if (nativeSurface instanceof ProxySurfaceImpl) {
            final ProxySurface proxySurface = (ProxySurface)nativeSurface;
            printStream.println(s + "Upstream options " + proxySurface.getUpstreamOptionBits(null).toString());
            final UpstreamSurfaceHook upstreamSurfaceHook = proxySurface.getUpstreamSurfaceHook();
            if (null != upstreamSurfaceHook) {
                printStream.println(s + "Upstream Hook " + upstreamSurfaceHook.getClass().getName());
                final NativeSurface upstreamSurface = upstreamSurfaceHook.getUpstreamSurface();
                s += "  ";
                if (null != upstreamSurface) {
                    printStream.println(s + "Upstream Hook's Surface " + upstreamSurface.getClass().getName());
                    dumpHierarchy(printStream, upstreamSurface, s);
                }
                else {
                    printStream.println(s + "Upstream Hook's Surface NULL");
                }
            }
        }
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("displayHandle 0x" + Long.toHexString(this.getDisplayHandle())).append("\n, surfaceHandle 0x" + Long.toHexString(this.getSurfaceHandle())).append("\n, size " + this.getSurfaceWidth() + "x" + this.getSurfaceHeight()).append("\n, ");
        this.getUpstreamOptionBits(sb);
        sb.append("\n, " + this.config).append("\n, surfaceLock " + this.surfaceLock + "\n, ").append(this.getUpstreamSurfaceHook()).append("\n, upstreamSurface " + (null != this.getUpstreamSurface()));
        return sb;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("[ ");
        this.toString(sb);
        sb.append(" ]");
        return sb.toString();
    }
}
